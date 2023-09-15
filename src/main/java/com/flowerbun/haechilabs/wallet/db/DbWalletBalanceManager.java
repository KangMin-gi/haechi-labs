package com.flowerbun.haechilabs.wallet.db;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowerbun.haechilabs.wallet.domain.Tx;
import com.flowerbun.haechilabs.wallet.domain.WalletBalance;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbWalletBalanceManager {

    private final WalletRepository walletRepository;

    @Transactional
    public void update(Tx tx) {
        String toAddress = tx.to();
        String fromAddress = tx.from();
        WalletBalance balance = tx.balance();

        this.walletRepository.findByAddressWithPessimisticLock(toAddress)
                .ifPresent(v -> v.addBalance(balance));
        this.walletRepository.findByAddressWithPessimisticLock(fromAddress)
                .ifPresent(v -> v.withdrawal(balance));
    }

    @Transactional // Todo : Transactional Pessimistic Lock Size Check , Can Merge Transaction User?
    public void updateAll(List<Tx> tx) {
        tx.forEach(this::update);
    }

}
