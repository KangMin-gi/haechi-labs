package com.flowerbun.haechilabs.wallet.infra;

import com.flowerbun.haechilabs.wallet.domain.Wallet;

// Can Be Interface
public interface InfraWalletCreator {

    Wallet create(String privateKey);

}
