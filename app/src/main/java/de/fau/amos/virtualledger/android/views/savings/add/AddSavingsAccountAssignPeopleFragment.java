package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class AddSavingsAccountAssignPeopleFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAssignPeopleFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_friends, container, false);
        ButterKnife.bind(this, view);

        ListView peopleList = (ListView) view.findViewById(R.id.list);
        ArrayList<String> exampleList = new ArrayList<>();
        exampleList.add("Person 1");
        exampleList.add("Person 2");

        peopleList.setAdapter(new PeopleAdapter(getActivity(), R.id.list, exampleList));
        return view;
    }


    @Override
    public void fillInData(final SavingsAccount savingsAccount) {

    }
}
