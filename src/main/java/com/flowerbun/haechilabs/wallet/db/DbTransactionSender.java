package com.flowerbun.haechilabs.wallet.db;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DbTransactionSender {

    private final WalletRepository walletRepository;

    public void send(Wallet wallet, WalletBalance transactionBalance) {
        WalletEntity entity = this.findEntity(wallet);
        entity.validWithdrawal(transactionBalance);
        entity.tryWithdrawal(transactionBalance);
    }

    private WalletEntity findEntity(Wallet wallet) {
        String address = wallet.address();
        if (wallet instanceof WalletEntity) {
            return (WalletEntity) wallet;
        } else {
            return this.walletRepository.findByAddress(address)
                    .orElseThrow(() -> new CustomException("Can Not Find Wallet"));
        }
    }
}
