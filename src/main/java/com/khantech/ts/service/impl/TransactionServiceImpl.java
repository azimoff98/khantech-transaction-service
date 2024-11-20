package com.khantech.ts.service.impl;

import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import com.khantech.ts.exception.ErrorCodes;
import com.khantech.ts.exception.ResourceNotFoundException;
import com.khantech.ts.repository.TransactionRepository;
import com.khantech.ts.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction findById(long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable).stream().toList();
    }

    @Override
    public List<Transaction> findAllByStatus(TransactionStatus transactionStatus) {
        return transactionRepository.findAllByTransactionStatus(transactionStatus.getCode());
    }

    @Override
    public Transaction saveAndFlush(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
