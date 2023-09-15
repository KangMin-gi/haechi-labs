package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.flowerbun.haechilabs.wallet.domain.Block;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import org.web3j.protocol.core.methods.response.EthBlock;

public class EthBlockWrapper implements Block {

    private final EthBlock ethBlock;
    private final EthBlock.Block block;
    private final List<EthTransaction> ethTransactions;


    public EthBlockWrapper(EthBlock ethBlock, List<EthTransaction> ethTransactions) {
        this.ethBlock = ethBlock;
        this.block = ethBlock.getBlock();
        this.ethTransactions = ethTransactions;
    }

    @Override public BigInteger number() {
        return this.block.getNumber();
    }

    public EthBlock getEthBlock() {
        return this.ethBlock;
    }

    @Override public List<Tx> transactions() {
        return new ArrayList<>(this.ethTransactions);
    }
}
