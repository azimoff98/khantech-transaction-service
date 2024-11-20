package com.khantech.ts;

import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.User;
import com.khantech.ts.repository.TransactionRepository;
import com.khantech.ts.repository.UserRepository;
import com.khantech.ts.service.TransactionProcessingService;
import com.khantech.ts.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TransactionProcessingService transactionProcessingService;

    @Override
    public void run(String... args) throws Exception {
        User satoshi = userService.save(
                new User("satoshi")
        );

        Transaction genesis =  transactionProcessingService.processTransaction(
                Transaction.builder()
                        .transactionType(0)
                        .amount(new BigDecimal(1000))
                        .userId(satoshi.getId())
                        .build()
        );

        log.info("User : {} and Transaction {} are created", satoshi.getUsername(), genesis.getId());
    }
}
