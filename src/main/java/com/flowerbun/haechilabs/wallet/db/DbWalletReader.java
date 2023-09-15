package com.flowerbun.haechilabs.wallet.db;

import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.wallet.domain.PrivateKey;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbWalletReader {

    private final WalletRepository walletRepository;

    public Wallet findWallet(PrivateKey privateKey) {
        return this.walletRepository.findByPrivateKey(privateKey.encrypted())
                .orElseThrow(() -> new CustomException("Can Not Find Wallet"));
    }
}
