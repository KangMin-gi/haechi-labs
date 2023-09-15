package com.flowerbun.haechilabs.wallet.infra;

import org.springframework.stereotype.Component;

import com.flowerbun.haechilabs.wallet.domain.Wallet;
import org.web3j.crypto.Credentials;

@Component
public class EthWalletCreator implements InfraWalletCreator {

    @Override
    public Wallet create(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        return new EthWallet(credentials);
    }

}