package com.flowerbun.haechilabs.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowerbun.haechilabs.controller.TransactionDto.ListRequest;
import com.flowerbun.haechilabs.controller.TransactionDto.ListResponse;
import com.flowerbun.haechilabs.wallet.BlockReader;
import com.flowerbun.haechilabs.wallet.TransactionReader;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tx")
public class TransactionController {

    private final TransactionReader TxReader;

    private final BlockReader blockReader;


    @GetMapping
    public ResponseEntity<ListResponse> getTx(ListRequest request) {
        List<Tx> transactions = this.TxReader.txList(request.toRequest());
        Long blockNumber = this.blockReader.lastNumber();
        return ResponseEntity.ok(new ListResponse(transactions, blockNumber));
    }
}
