package com.flowerbun.haechilabs.wallet.infra;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.flowerbun.haechilabs.config.Web3jConfig;
import com.flowerbun.haechilabs.wallet.domain.Block;
import com.flowerbun.haechilabs.wallet.domain.Tx;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.junit.jupiter.api.Test;


@EnableConfigurationProperties
        // @SpringBootTest(classes = EthTransactionMonitor.class)
class EthTransactionMonitorTest {

    EthTransactionMonitor sut;
    private String url = "https://tn.henesis.io/ethereum/goerli?clientId=815fcd01324b8f75818a755a72557750";

    ConcurrentHashMap<BigInteger, Addable> test = new ConcurrentHashMap<>();
    Set<String> s = new HashSet<>();
    Addable erra = new Addable();

    private Db db;

    @Test
    public void doTest() {
        Web3jConfig web3jConfig = new Web3jConfig();
        sut = new EthTransactionMonitor(web3jConfig.web3j(url));

        Flowable<Tx> transactionObjectFlowable = sut.confirmedTransactionFlowable();

    }

    @Test
    public void doTest2() throws Exception {
        // Web3jConfig web3jConfig = new Web3jConfig();
        // sut = new EthTransactionMonitor(web3jConfig.web3j(url));
        //
        // Flowable<Tx> transactionObjectFlowable = sut.confirmTx();
        // transactionObjectFlowable.subscribe(v -> {
        //     Addable a = test.getOrDefault(v.hash(), new Addable());
        //     a.add();
        //     test.put(v.hash(), a);
        // }, on -> {
        //     String message = on.getMessage();
        //     s.add(message);
        //     erra.add();
        // });
        //
        // Thread.sleep(60000);
        // System.out.println("--------=-----");
        // System.out.println("T =+" + test);
        // System.out.println("T Size =+" + test.size());
        // System.out.println(s);
        // System.out.println(erra);
        // System.out.println("----------------");

    }

    // @Test
    // public void block() throws Exception {
    //     Web3jConfig web3jConfig = new Web3jConfig();
    //     sut = new EthTransactionMonitor(web3jConfig.web3j(url));
    //
    //     Flowable<Block> transactionObjectFlowable = sut.confirmBlockFlowable();
    //     Disposable subscribe = transactionObjectFlowable.subscribe(b -> {
    //         b.transactions().forEach(v -> {
    //             Addable a = test.getOrDefault(v.hash(), new Addable());
    //             a.add();
    //             test.put(b.number(), a);
    //         });
    //         ;
    //     }, on -> {
    //         String message = on.getMessage();
    //         s.add(message);
    //         erra.add();
    //     });
    //
    //     subscribe.dispose();
    // }


    private static class Addable {

        int number = 0;

        synchronized public void add() {
            ++number;
        }

        @Override public String toString() {
            return String.valueOf(this.number);
        }
    }

    private static class Db {
        public void add() {
        }
    }

}