package com.flowerbun.haechilabs.wallet;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.wallet.db.DbTransactionLogger;
import com.flowerbun.haechilabs.wallet.db.DbWalletBalanceManager;
import com.flowerbun.haechilabs.wallet.domain.BlockNumberCounter;
import com.flowerbun.haechilabs.wallet.infra.EthTransactionMonitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletBalanceMonitor {

    private final EthTransactionMonitor ethTransactionMonitor;
    private final DbWalletBalanceManager balanceManager;
    private final BlockNumberCounter dbBlockNumberCounter;
    private final DbTransactionLogger transactionLogger;

    @Transactional
    @PostConstruct
    protected void monitor() {
        this.ethTransactionMonitor
                .confirmBlockFlowable(dbBlockNumberCounter.lastNumber())
                .subscribe(block -> {
                            this.balanceManager.updateAll(block.transactions());
                            this.transactionLogger.confirmBlockUpdate(block);
                        },
                        onError -> log.error("Error WhileMonitoring {} {}", onError.getMessage(), onError));
    }

    @Transactional
    @PostConstruct
    protected void monitorMined() {
        this.ethTransactionMonitor
                .minedFlowable(dbBlockNumberCounter.lastNumber())
                .subscribe(block -> {
                    block.transactions().forEach(this.transactionLogger::write);
                    this.dbBlockNumberCounter.addCount(block);
                },onError -> log.error("Error WhileMonitoringMined {} {}", onError.getMessage(), onError));
    }

    // @Transactional
    // @PostConstruct // 멈췄는데, 해당 pending
    // protected void monitorPending() {
    //     this.ethTransactionMonitor
    //             .pendingFlowable()
    //             .subscribe(this.transactionLogger::write,onError -> {
    //                 log.error("Error WhileMonitoringPending {} {}", onError.getMessage(), onError);
    //             });
    // }


}
