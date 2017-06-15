package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.roomorama.caldroid.CaldroidFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;
import de.fau.amos.virtualledger.dtos.Booking;

public class CalendarViewFragment extends Fragment {
    private static final String TAG = CalendarViewFragment.class.getSimpleName();

    @BindView(R.id.calendar_view_fragment_calendar_wrapper)
    LinearLayout calendarWrapper;

    // need FragmentActivity because of Caldroid workaround
    private FragmentActivity context;





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*((App) getActivity().getApplication()).getNetComponent().inject(this);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_view_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();
        Transaction testTransaction = new Transaction("avc", new Booking(cal.getTime(), 50.00));
        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(testTransaction);

        CaldroidBankingFragment caldroidFragment = CaldroidBankingFragment.newInstance(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR), transactionList, 2000.00);
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
}
