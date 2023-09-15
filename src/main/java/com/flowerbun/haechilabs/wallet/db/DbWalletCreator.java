package com.flowerbun.haechilabs.wallet.db;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbWalletCreator {

    private final WalletRepository walletRepository;

    public Wallet createWallet(Wallet wallet) {
        WalletEntity walletEntity = WalletEntity.of(wallet);
        Optional<WalletEntity> byPrivateKey = this.walletRepository.findByPrivateKey(wallet.privateKey().encrypted());
        return byPrivateKey.orElseGet(() -> this.walletRepository.save(walletEntity));
    }


}
