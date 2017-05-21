package de.fau.amos.virtualledger.android.deleteaction;

import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;

/**
 * Created by sebastian on 21.05.17.
 */

public class BankAccessNameExtractor implements Function<BankAccess,String>{
    @Override
    public String apply(BankAccess item) {
        return item.getName();
    }
}
