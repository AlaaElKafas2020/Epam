module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;

    uses com.example.bank.api.Bank;

    exports com.example.cloud.bank.impl;

    provides com.example.bank.api.Bank with com.example.cloud.bank.impl.CloudBankImpl;

}