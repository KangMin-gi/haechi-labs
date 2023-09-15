package com.flowerbun.haechilabs.wallet;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.util.TwoWayEncrypted;
import com.flowerbun.haechilabs.wallet.db.DbTransactionLogger;
import com.flowerbun.haechilabs.wallet.db.DbTransactionSender;
import com.flowerbun.haechilabs.wallet.db.DbWalletReader;
import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.PrivateKey;
import com.flowerbun.haechilabs.wallet.domain.SendTx;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import com.flowerbun.haechilabs.wallet.infra.EthTransactionSender;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionSender {

    private final EthTransactionSender ethTransactionSender;
    private final DbWalletReader walletReader;
    private final DbTransactionSender dbTransactionSender;
    private final DbTransactionLogger logger;


    @Transactional
    public void send(String privateKey, String toAddress, BigDecimal amount, BalanceUnit unit) {
        PrivateKey pk = new PrivateKey(TwoWayEncrypted.ofOrigin(privateKey));
        Wallet wallet = this.walletReader.findWallet(pk);
        WalletBalance transactionBalance = new WalletBalance(amount, unit);
        this.dbTransactionSender.send(wallet, transactionBalance);
        SendTx send = this.ethTransactionSender.send(wallet, toAddress, transactionBalance);
        this.logger.write(send);
    }
}
