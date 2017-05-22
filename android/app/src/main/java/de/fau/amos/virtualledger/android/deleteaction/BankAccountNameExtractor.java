package de.fau.amos.virtualledger.android.deleteaction;

import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 */

public class BankAccountNameExtractor implements Function<BankAccount,String>{
    @Override
    public String apply(BankAccount item) {
        return item.getName();
    }
}
