package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;

public class CalendarViewFragment extends Fragment {
    private static final String TAG = CalendarViewFragment.class.getSimpleName();

    @BindView(R.id.calendar_view_fragment_calendar)
    CalendarView calendarView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_view_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int backgroundColor = ContextCompat.getColor(this.getActivity(), R.color.colorBankingOverview);
        calendarView.setBackgroundColor(backgroundColor);
    }
}
