package com.flowerbun.haechilabs.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowerbun.haechilabs.controller.TransactionDto.ListRequest;
import com.flowerbun.haechilabs.controller.TransactionDto.ListResponse;
import com.flowerbun.haechilabs.controller.TransactionDto.SendRequest;
import com.flowerbun.haechilabs.wallet.BlockReader;
import com.flowerbun.haechilabs.wallet.TransactionReader;
import com.flowerbun.haechilabs.wallet.TransactionSender;
import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tx")
public class TransactionController {

    private final TransactionReader TxReader;
    private final TransactionSender sender;

    private final BlockReader blockReader;


    @GetMapping
    public ResponseEntity<ListResponse> getTx(ListRequest request) {
        List<Tx> transactions = this.TxReader.txList(request.toRequest());
        Long blockNumber = this.blockReader.lastNumber();
        return ResponseEntity.ok(new ListResponse(transactions, blockNumber));
    }

    @PostMapping
    public void sendTx(@RequestBody SendRequest request) {
        this.sender.send(request.getPrivateKey(), request.getToAddress(), new BigDecimal(request.getAmount()), BalanceUnit.valueOf(request.getUnit()));
    }
}
