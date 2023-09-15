package com.flowerbun.haechilabs.controller;

import com.flowerbun.haechilabs.wallet.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WalletDto {

    @Data
    public static class WalletRequest {
        private String privateKey;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WalletResponse {
        private String address;

        public WalletResponse(Wallet wallet) {
            this.address = wallet.address();
        }
    }
}
