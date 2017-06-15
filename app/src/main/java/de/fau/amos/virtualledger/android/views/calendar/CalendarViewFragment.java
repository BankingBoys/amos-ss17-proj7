package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;

public class CalendarViewFragment extends Fragment {
    private static final String TAG = CalendarViewFragment.class.getSimpleName();

    private static final String BUNDLE_PARAMETER_TRANSACTIONLIST = "transactionlist";
    private static final String BUNDLE_PARAMETER_TOTALAMOUNT = "totalamount";

    @BindView(R.id.calendar_view_fragment_calendar_wrapper)
    LinearLayout calendarWrapper;

    // need FragmentActivity because of Caldroid workaround
    private FragmentActivity context;
    private ArrayList<Transaction> transactionList;
    private double totalAmount;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_view_fragment, container, false);
        readBundle(getArguments());
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();
        CaldroidBankingFragment caldroidFragment = CaldroidBankingFragment.newInstance(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR), transactionList, totalAmount);
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.calendar_view_fragment_calendar_wrapper, caldroidFragment, "calendar_view_fragment_calendar");
        transaction.commit();
    }


    @Override
    public void onAttach(Context context) {
        // workaround because we need getSupportFragmentManager() for Caldroid
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }



    public static CalendarViewFragment newInstance(ArrayList<Transaction> transactionList, double totalAmount) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CalendarViewFragment.BUNDLE_PARAMETER_TRANSACTIONLIST, transactionList);
        bundle.putDouble(CalendarViewFragment.BUNDLE_PARAMETER_TOTALAMOUNT, totalAmount);
        CalendarViewFragment fragment = new CalendarViewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            transactionList = bundle.getParcelableArrayList(CalendarViewFragment.BUNDLE_PARAMETER_TRANSACTIONLIST);
            totalAmount = bundle.getDouble(CalendarViewFragment.BUNDLE_PARAMETER_TOTALAMOUNT);
        } else {
            throw new InvalidParameterException("No data found in bundle! Please check if you instantiate CaldroidBankingFragment with " + CalendarViewFragment.BUNDLE_PARAMETER_TRANSACTIONLIST + " and " + CalendarViewFragment.BUNDLE_PARAMETER_TOTALAMOUNT + " !");
        }
    }
}
