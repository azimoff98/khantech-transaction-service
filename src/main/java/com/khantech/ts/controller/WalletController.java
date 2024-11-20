package com.khantech.ts.controller;

import com.khantech.ts.controller.constants.ApiCodes;
import com.khantech.ts.controller.helper.TransactionApiBuilder;
import com.khantech.ts.dto.response.TransactionApiResponse;
import com.khantech.ts.dto.response.TransactionResponse;
import com.khantech.ts.mapper.TransactionMapper;
import com.khantech.ts.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController implements TransactionApiBuilder {

    private final TransactionService transactionService;
    private final TransactionMapper mapper;

    @GetMapping("/{userId}")
    public ResponseEntity<TransactionApiResponse<List<TransactionResponse>>> findTransactions(
            @PathVariable("userId") long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        var response = transactionService.findAllByUserId(userId, PageRequest.of(page, size))
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(build(response, ApiCodes.SUCCESSFUL_RESPONSE));
    }

}
