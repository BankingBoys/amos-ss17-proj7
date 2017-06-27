package de.fau.amos.virtualledger.android.views.savings.add;

import android.support.v4.app.Fragment;

import de.fau.amos.virtualledger.dtos.AddSavingsAccountData;

public abstract class AddSavingsAccountPage extends Fragment {
    abstract void fillInData(AddSavingsAccountData addSavingsAccountResult);
}
