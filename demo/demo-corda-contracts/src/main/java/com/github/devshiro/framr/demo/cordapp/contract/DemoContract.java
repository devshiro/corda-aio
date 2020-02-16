package com.github.devshiro.framr.demo.cordapp.contract;

import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

public class DemoContract implements Contract {
    public static final String ID = "com.github.devshiro.framr.demo.corda.contract.DemoContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {

    }
}
