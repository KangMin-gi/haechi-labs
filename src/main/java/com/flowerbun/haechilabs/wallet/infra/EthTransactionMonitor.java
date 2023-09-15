package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.flowerbun.haechilabs.exception.CustomException;
import com.flowerbun.haechilabs.wallet.domain.Block;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
@Slf4j
@RequiredArgsConstructor
public class EthTransactionMonitor {

    private final Web3j web3j;
    private static final Integer REQUIRED_BLOCK_CONFIRM_COUNT = 12;
    private static final String SUCCESS = "0x1";
    private static final String FAIL = "0x0";
    private static final String NONE = "NONE";

    public Flowable<Tx> pendingFlowable() {

        return this.web3j.pendingTransactionFlowable()
                .map(v -> ((TransactionObject) v).get())
                .map(EthTransaction::ofPending);

    }

    public Flowable<Block> minedFlowable(Optional<BigInteger> blockNumber) {
        Flowable<EthBlock> startFlowable = null;
        if (blockNumber.isPresent()) {
            startFlowable = this.web3j.replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(blockNumber.get()), true);
        } else {
            startFlowable = this.web3j.blockFlowable(true);
        }
        return startFlowable
                .map(ethBlock -> {
                    List<EthTransaction> transactions = ethBlock.getBlock().getTransactions()
                            .stream()
                            .map(v -> ((TransactionObject) v).get())
                            .map(EthTransaction::ofMined)
                            .collect(Collectors.toList());
                    transactions.forEach(this::setStatus);
                    return new EthBlockWrapper(ethBlock, transactions);
                });
    }

    public Flowable<Block> confirmBlockFlowable(Optional<BigInteger> blockNumber) {
        Flowable<EthBlock> startFlowable = null;
        if (blockNumber.isPresent()) {
            startFlowable = this.web3j.replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(blockNumber.get()), false);
        } else {
            startFlowable = this.web3j.blockFlowable(false);
        }
        return startFlowable
                .map(block -> block.getBlock().getNumber())
                .map(flowBlockNumber -> flowBlockNumber.subtract(BigInteger.valueOf(REQUIRED_BLOCK_CONFIRM_COUNT)))
                .flatMap(confirmBlockNumber ->
                        Flowable.fromFuture(
                                this.web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(confirmBlockNumber), true)
                                        .sendAsync()))
                .map(ethBlock -> {
                    List<EthTransaction> transactions = ethBlock.getBlock().getTransactions()
                            .stream()
                            .map(v -> ((TransactionObject) v).get())
                            .map(EthTransaction::ofConfirm)
                            .collect(Collectors.toList());
                    transactions.forEach(this::setStatus);
                    return new EthBlockWrapper(ethBlock, transactions);
                });
    }

    private EthTransaction setStatus(EthTransaction param) {
        EthTransaction result = param;
        try {
            String status = this.web3j.ethGetTransactionReceipt(param.hash()).send().getTransactionReceipt()
                    .map(TransactionReceipt::getStatus)
                    .orElse(NONE);

            if (SUCCESS.equalsIgnoreCase(status)) {
                result.success();
            } else if (NONE.equals(status)) {
                result.unknown();
            } else {
                result.fail();
            }
        } catch (Exception e) {
            log.error("Block Monitoring 중단 : {} {} ", e.getMessage(), e);
            throw new CustomException("Block Monitoring 중단");
        }
        return result;
    }

    public Flowable<Tx> confirmedTransactionFlowable() {
        Flowable<EthBlock> ethBlockFlowable = this.web3j.blockFlowable(false);
        Flowable<EthBlock> pastBlockFlowable = ethBlockFlowable.flatMap(v -> {
            BigInteger blockNumber = v.getBlock().getNumber();
            BigInteger needBlockNumber = blockNumber.subtract(BigInteger.valueOf(REQUIRED_BLOCK_CONFIRM_COUNT));

            EthBlock send = this.web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(needBlockNumber), true)
                    .send();
            return Flowable.create(emitter -> {
                emitter.onNext(send);
                emitter.onComplete();
            }, BackpressureStrategy.BUFFER);

        });
        Flowable<Tx> map = pastBlockFlowable.map(v -> v.getBlock().getTransactions())
                .flatMapIterable(v -> v)
                .map(v -> ((TransactionObject) v).get())
                .map(EthTransaction::ofConfirm);

        return map;
    }

    // public Flowable<Tx> confirmTx() {
    //     return this.web3j.blockFlowable(false)
    //             .map(block -> {
    //                 BigInteger blockNumber = block.getBlock().getNumber();
    //                 return blockNumber.subtract(BigInteger.valueOf(REQUIRED_BLOCK_CONFIRM_COUNT));
    //             })
    //             .flatMap(blockNumber -> Flowable.fromFuture(
    //                     this.web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).sendAsync()))
    //             .map(v -> v.getBlock().getTransactions())
    //             .flatMap(Flowable::fromIterable)
    //             .map(v -> ((TransactionObject) v).get())
    //             .map(EthTransaction::ofConfirm)
    //             .flatMap(ethTransaction -> Flowable.fromFuture(
    //                             this.web3j.ethGetTransactionReceipt(ethTransaction.hash()).sendAsync())
    //                     .flatMap(receipt -> {
    //                         if (SUCCESS.equalsIgnoreCase(
    //                                 receipt.getTransactionReceipt().map(v -> v.getStatus()).orElse(NONE))) { // success of failure
    //                             ethTransaction.success();
    //                         }
    //                         return Flowable.just(ethTransaction);
    //                     })
    //             );
    // }


}
