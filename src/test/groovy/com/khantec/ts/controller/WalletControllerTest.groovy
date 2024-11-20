package com.khantec.ts.controller

import com.khantech.ts.controller.WalletController
import com.khantech.ts.controller.constants.ApiCodes
import com.khantech.ts.dto.response.TransactionApiResponse
import com.khantech.ts.dto.response.TransactionResponse
import com.khantech.ts.entity.Transaction
import com.khantech.ts.entity.TransactionStatus
import com.khantech.ts.mapper.TransactionMapper
import com.khantech.ts.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Unit tests for WalletController")
class WalletControllerTest extends Specification {

    def transactionService = Mock(TransactionService)
    def transactionMapper = Mock(TransactionMapper)

    @Subject
    WalletController walletController = new WalletController(transactionService, transactionMapper)

    def "should return paginated transactions for a user"() {
        given: "A userId, page, size, and mocked transaction service responses"
        long userId = 1
        int page = 0
        int size = 5

        def transactions = [
                new Transaction(1, 1, new BigDecimal(100), 1, 1, false, System.currentTimeMillis(), System.currentTimeMillis()),
                new Transaction(2, 1, new BigDecimal(200), 1, 1, false, System.currentTimeMillis(), System.currentTimeMillis()),
        ]
        def transactionResponses = [
                new TransactionResponse(transactions[0].userId, transactions[0].amount, TransactionStatus.fromCode(transactions[0].transactionStatus), transactions[0].createdAt),
                new TransactionResponse(transactions[1].userId, transactions[1].amount, TransactionStatus.fromCode(transactions[1].transactionStatus), transactions[1].createdAt),
        ]

        transactionService.findAll(PageRequest.of(page, size)) >> transactions
        transactionMapper.toResponse(_) >> { Transaction transaction ->
            transactionResponses.find { it.createTime() == transaction.createdAt }
        }

        when: "The findTransactions endpoint is called"
        ResponseEntity<TransactionApiResponse<List<TransactionResponse>>> response = walletController.findTransactions(userId, page, size)

        then: "The response should be correct"
        response.statusCode == HttpStatus.OK
        response.body != null
        println response.body.data[0]
        response.body.data == transactionResponses
        response.body.code == ApiCodes.SUCCESSFUL_RESPONSE.code
    }
}
