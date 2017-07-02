package de.fau.amos.virtualledger.dtos;

import java.util.Comparator;
import java.util.Locale;

public class BankAccountComparator implements Comparator<BankAccount> {

    @Override
    public int compare(BankAccount access1, BankAccount access2) {
        String access1Name = access1.getName().toUpperCase(Locale.GERMAN);
        String access2Name = access2.getName().toUpperCase(Locale.GERMAN);
        return access1Name.compareTo(access2Name);
    }

}
