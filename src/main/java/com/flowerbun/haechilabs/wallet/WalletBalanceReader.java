package com.flowerbun.haechilabs.wallet;

import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.db.DbWalletBalanceReader;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletBalanceReader {

    private final DbWalletBalanceReader balanceReader;

    public WalletBalance read(String address) {
        return this.balanceReader.getWalletBalance(address);
    }
}
