package de.fau.amos.virtualledger.android.deleteaction;

import de.fau.amos.virtualledger.android.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 * This ussed to be a lambda
 * Extracts the name out of a BankAccount
 */

public class BankAccountNameExtractor implements BiFunction<BankAccess,BankAccount,String>{
    @Override
    public String apply(BankAccess bankAccess,BankAccount bankAccount) {
        return bankAccount.getName();
    }
}
