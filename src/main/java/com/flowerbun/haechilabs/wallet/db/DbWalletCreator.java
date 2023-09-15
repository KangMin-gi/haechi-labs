package com.flowerbun.haechilabs.wallet.db;

import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbWalletCreator {

    private final WalletRepository walletRepository;

    public Wallet createWallet(Wallet wallet) {
        WalletEntity walletEntity = WalletEntity.of(wallet);
        return this.walletRepository.save(walletEntity);
    }


}
