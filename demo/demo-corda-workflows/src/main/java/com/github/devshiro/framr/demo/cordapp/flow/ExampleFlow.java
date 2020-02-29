package com.github.devshiro.framr.demo.cordapp.flow;

import co.paralleluniverse.fibers.Suspendable;
import com.github.devshiro.framr.annotation.FramrStartableFlow;
import com.github.devshiro.framr.demo.cordapp.contract.DemoCommands;
import com.github.devshiro.framr.demo.cordapp.state.ExampleState;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Collections;

public class ExampleFlow {

    @InitiatingFlow
    @StartableByRPC
    @AllArgsConstructor
    @FramrStartableFlow(displayName = "Example Flow Initiator", inputClass = Integer.class)
    public static class Initiator extends FlowLogic<SignedTransaction> {

        private final int value;

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {

            final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

            Party issuer = getOurIdentity();

            ExampleState state = new ExampleState(value, issuer, new UniqueIdentifier());
            final Command<DemoCommands.IssueDemoToken> txCommand = new Command<>(
                    new DemoCommands.IssueDemoToken(),
                    state.getParticipants().stream().map(AbstractParty::getOwningKey).collect(ImmutableList.toImmutableList())
            );
            final TransactionBuilder txBuilder = new TransactionBuilder(notary)
                    .addCommand(txCommand)
                    .addOutputState(state);

            txBuilder.verify(getServiceHub());

            // only participant is the issuer
            final SignedTransaction fullySignedTx = getServiceHub().signInitialTransaction(txBuilder);

            return subFlow(new FinalityFlow(fullySignedTx, Collections.emptyList()));
        }
    }
}
