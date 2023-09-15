package com.flowerbun.haechilabs.wallet.db;

import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbWalletBalanceReader {

    private final WalletRepository walletRepository;

    public WalletBalance getWalletBalance(String address) {
        return this.walletRepository.findByAddress(address)
                .map(WalletEntity::balance)
                .orElse(WalletBalance.ZERO);
    }

}
