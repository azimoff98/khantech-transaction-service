package com.khantech.ts.mapper;

import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.dto.response.TransactionResponse;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements BaseMapper<TransactionRequest, TransactionResponse, Transaction> {

    @Override
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getUserId(),
                transaction.getAmount(),
                TransactionStatus.fromCode(transaction.getTransactionStatus()),
                transaction.getCreatedAt()
        );
    }

    @Override
    public Transaction toEntity(TransactionRequest request) {
        return Transaction.builder()
                .userId(request.userId())
                .amount(request.amount())
                .transactionType(request.transactionType().getId())
                .build();
    }
}
