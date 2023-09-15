package com.flowerbun.haechilabs.wallet.db;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import com.flowerbun.haechilabs.util.TwoWayEncrypted;
import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.PrivateKey;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import org.junit.jupiter.api.Test;

;

class WalletEntityTest {

    PrivateKey privateKey = new PrivateKey(TwoWayEncrypted.ofOrigin("abab1111abab1111"));
    String address = "Addr";
    WalletEntity wallet = new WalletEntity(
            1L, privateKey.encrypted(), address, new BigDecimal(0.0), BalanceUnit.WEI, new BigDecimal(0.0)
    );

    @Test
    public void addBalance() {
        WalletBalance walletBalance = new WalletBalance(new BigDecimal(1.121111), BalanceUnit.ETH);
        wallet.addBalance(walletBalance);

        assertThat(wallet.balance())
                .isEqualTo(walletBalance);

    }

    @Test
    public void withdrawal() {
        WalletBalance walletBalance = new WalletBalance(new BigDecimal(1.121111), BalanceUnit.ETH);
        wallet.addBalance(walletBalance);
        WalletBalance withdrawlBalance = new WalletBalance(new BigDecimal(1.121111), BalanceUnit.ETH);

    }
}