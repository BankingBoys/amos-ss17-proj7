package de.fau.amos.virtualledger.android.ExpandableList.model;

/**
 * Created by Simon on 21.05.2017.
 */

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class Group {

    public BankAccess bankAccess;
    public final List<BankAccount> children = new ArrayList<BankAccount>();

    public Group(BankAccess bankAccess) {
        this.bankAccess = bankAccess;
    }

}