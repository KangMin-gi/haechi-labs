package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.wallet.domain.BalanceUnit;
import com.flowerbun.haechilabs.wallet.domain.SendTx;
import com.flowerbun.haechilabs.wallet.domain.Wallet;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.RequiredArgsConstructor;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

@Service
@RequiredArgsConstructor
public class EthTransactionSender {

    private final Web3j web3j;

    public SendTx send(Wallet owner, String to, WalletBalance balance) {
        Credentials credentials = Credentials.create(owner.privateKey().get());
        BigDecimal amount = balance.balance(BalanceUnit.WEI);

        try {
            TransactionReceipt receipt = Transfer.sendFunds(
                    web3j,
                    credentials,
                    to,
                    amount,
                    Unit.WEI
            ).send();
            return new EthSendTransaction(receipt, balance);
        } catch (Exception e) {
            return null;
        }


    }

}
