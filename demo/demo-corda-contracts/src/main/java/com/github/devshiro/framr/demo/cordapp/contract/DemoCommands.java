package com.github.devshiro.framr.demo.cordapp.contract;

import net.corda.core.contracts.CommandData;

public interface DemoCommands extends CommandData {
    class IssueDemoToken implements DemoCommands {}
}
