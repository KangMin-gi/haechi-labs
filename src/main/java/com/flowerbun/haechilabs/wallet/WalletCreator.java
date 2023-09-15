package com.flowerbun.haechilabs.wallet;

import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.db.DbWalletCreator;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import com.flowerbun.haechilabs.wallet.infra.InfraWalletCreator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletCreator {

    private final InfraWalletCreator infraWalletCreator;
    private final DbWalletCreator dbWalletCreator;

    public Wallet create(String privateKey) {
        Wallet wallet = this.infraWalletCreator.create(privateKey);
        return this.dbWalletCreator.createWallet(wallet);
    }

}
