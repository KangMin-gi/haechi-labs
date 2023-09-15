package com.flowerbun.haechilabs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowerbun.haechilabs.controller.WalletDto.WalletRequest;
import com.flowerbun.haechilabs.controller.WalletDto.WalletResponse;
import com.flowerbun.haechilabs.wallet.WalletCreator;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletCreator creator;

    @PostMapping
    public ResponseEntity<WalletResponse> create(@RequestBody WalletRequest walletRequest) {
        Wallet wallet = this.creator.create(walletRequest.getPrivateKey());
        return ResponseEntity.ok(new WalletResponse(wallet));
    }
}
