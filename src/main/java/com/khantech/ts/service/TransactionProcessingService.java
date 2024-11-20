package com.khantech.ts.service;

import com.khantech.ts.entity.Transaction;

public interface TransactionProcessingService {

    Transaction processTransaction(Transaction request);

    Transaction approveTransaction(Transaction transaction);
}
