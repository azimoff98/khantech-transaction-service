package com.khantech.ts.service;

import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAll(Pageable pageable);

    Transaction findById(long id);
    List<Transaction> findAllByStatus(TransactionStatus transactionStatus);

    Transaction saveAndFlush(Transaction transaction);

    Transaction save(Transaction transaction);
}
