package de.fau.amos.virtualledger.dtos;

import java.util.Comparator;
import java.util.Locale;
import java.io.Serializable;

public class BankAccessComparator implements Comparator<BankAccess>, Serializable {

    private static final long serialVersionUID = 123456789L;

    @Override
    public int compare(BankAccess access1, BankAccess access2) {
        String access1Name = access1.getName().toUpperCase(Locale.GERMAN);
        String access2Name = access2.getName().toUpperCase(Locale.GERMAN);
        return access1Name.compareTo(access2Name);
    }

}
