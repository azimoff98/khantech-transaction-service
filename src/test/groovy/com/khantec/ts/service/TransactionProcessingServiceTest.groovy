package com.khantech.ts.service.impl

import com.khantech.ts.dto.request.TransactionType
import com.khantech.ts.entity.Transaction
import com.khantech.ts.entity.TransactionStatus
import com.khantech.ts.entity.User
import com.khantech.ts.service.TransactionService
import com.khantech.ts.service.UserService
import com.khantech.ts.validator.BaseUserTransactionValidator
import com.khantech.ts.validator.TransactionValidationResult
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title


@Title("Unit tests for TransactionProcessingServiceImpl")
class TransactionProcessingServiceImplTest extends Specification {

    def userService = Mock(UserService)
    def transactionService = Mock(TransactionService)
    def manualApproveLimit = new BigDecimal("1000.00")

    @Subject
    TransactionProcessingServiceImpl service = new TransactionProcessingServiceImpl(userService, transactionService)

    void setup() {
        ReflectionTestUtils.setField(service, "manualApproveLimit", manualApproveLimit)
    }

    def "should approve transaction successfully"() {
        given: "A transaction to approve"
        def transaction = new Transaction(userId: 1, amount: new BigDecimal("500"), transactionStatus: TransactionStatus.PENDING.getCode())

        when: "The approveTransaction method is called"
        transactionService.save(_) >> { Transaction t -> t.setTransactionStatus(TransactionStatus.AWAITING_PROCESSING.getCode()); t }
        def approvedTransaction = service.approveTransaction(transaction)

        then: "The transaction is approved and returned"
        approvedTransaction.transactionStatus == TransactionStatus.AWAITING_PROCESSING.getCode()
        approvedTransaction.manuallyApproved
    }

    def "should process a valid credit transaction successfully"() {
        given: "A valid credit transaction and user"
        def transaction = new Transaction(userId: 1, amount: new BigDecimal("500"), transactionType: TransactionType.CREDIT.getId(), manuallyApproved: false)
        def user = new User(id: 1, balance: new BigDecimal("1000"))

        userService.findById(transaction.userId) >> user
        transactionService.saveAndFlush(_) >> { Transaction t -> t.setTransactionStatus(TransactionStatus.PENDING.getCode()); t }
        transactionService.save(_) >> { Transaction t -> t.setTransactionStatus(TransactionStatus.APPROVED.getCode()); t }
        userService.save(_) >> user

        and: "Validation result is VALID"
        TransactionType.CREDIT.getValidator() >> Mock(BaseUserTransactionValidator) {
            isValid(_, _) >> TransactionValidationResult.VALID
        }

        when: "The transaction is processed"
        def processedTransaction = service.processTransaction(transaction)

        then: "The transaction is approved, and the user's balance is updated"
        processedTransaction.transactionStatus == TransactionStatus.APPROVED.getCode()
        user.balance == new BigDecimal("1500")
    }

    def "should set transaction status to AWAITING_APPROVAL for transactions exceeding manual approval limit"() {
        given: "A credit transaction exceeding the manual approval limit"
        def transaction = new Transaction(userId: 1, amount: new BigDecimal("1500"), transactionType: TransactionType.CREDIT.getId())

        userService.findById(transaction.userId) >> new User(id: 1, balance: new BigDecimal("1000"))
        transactionService.saveAndFlush(_) >> { Transaction t -> t.setTransactionStatus(TransactionStatus.PENDING.getCode()); t }
        TransactionType.CREDIT.getValidator() >> Mock(BaseUserTransactionValidator) {
            isValid(_, _) >> TransactionValidationResult.VALID
        }
        transactionService.save(_) >> {Transaction t -> t.setTransactionStatus(TransactionStatus.AWAITING_APPROVAL.getCode()); t}

        when: "The transaction is processed"
        def processedTransaction = service.processTransaction(transaction)

        then: "The transaction is set to AWAITING_APPROVAL"
        processedTransaction.transactionStatus == TransactionStatus.AWAITING_APPROVAL.getCode()
    }

    def "should reject transaction with invalid validation result"() {
        given: "A transaction and a user with an invalid validation result"
        def transaction = new Transaction(userId: 1, amount: new BigDecimal("500"), transactionType: TransactionType.CREDIT.getId())
        def user = new User(id: 1, balance: new BigDecimal("1000"))

        userService.findById(transaction.userId) >> user
        transactionService.saveAndFlush(_) >> { Transaction t -> t.setTransactionStatus(TransactionStatus.PENDING.getCode()); t }
        TransactionType.CREDIT.getValidator() >> Mock(BaseUserTransactionValidator) {
            isValid(_, _) >> TransactionValidationResult.INSUFFICIENT_BALANCE
        }
        transactionService.save(_) >> {Transaction t -> t.setTransactionStatus(TransactionStatus.REJECTED.getCode()); t}


        when: "The transaction is processed"
        def processedTransaction = service.processTransaction(transaction)

        then: "The transaction status is set to REJECTED"
        processedTransaction.transactionStatus == TransactionStatus.REJECTED.getCode()
    }
}
