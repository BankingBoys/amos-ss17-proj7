package de.fau.amos.virtualledger.android.views.calendar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Locale;
import java.util.Map;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import hirondelle.date4j.DateTime;

public class CaldroidBankingCellAdapter extends CaldroidGridAdapter {

    TextView dateTextView;
    TextView amountDeltaTextView;
    TextView amountTextView;

    View cellView;

    Context context;

    private Map<DateTime, BankingDateInformation> bankingDateInformationMap;

    /**
     * Constructor
     *
     * @param context
     * @param month
     * @param year
     * @param caldroidData
     * @param extraData
     * @param bankingDateInformationMap key must be a DateTime in Year, Month, Day and time set completely to 0
     * Hint: use new DateTime(year, month, day, new Integer(0), new Integer(0), new Integer(0), new Integer(0));
     */
    public CaldroidBankingCellAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData, Map<DateTime, BankingDateInformation> bankingDateInformationMap) {
        super(context, month, year, caldroidData, extraData);
        this.context = context;
        this.bankingDateInformationMap = bankingDateInformationMap;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DateTime dateTime = this.datetimeList.get(position);
        cellView = convertView;

        BankingDateInformation bankingDateInformation = this.bankingDateInformationMap.get(dateTime);
        double amountDelta = 0.0;
        double amount = 0.0;
        if(bankingDateInformation != null) {
            amountDelta = bankingDateInformation.getAmountDelta();
            amount = bankingDateInformation.getAmount();
        }
        final double amountPassed = amountDelta;

        // load custom cell
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.calendar_view_cell, null);
        }
        dateTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_date);
        amountDeltaTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount_delta);
        amountTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount);

        // Set color of today
        if(dateTime.equals(getToday())) {
            styleToday();
        }

        styleDefault();
        
        if (dateTime.getMonth() == month) {
            dateTextView.setText("" + dateTime.getDay());

            // only show amount + amount delta if there are changes:
            if(amountDelta != 0.0) {
                changeAmountDeltaTextColor(amountDelta);
                changeAmountBackgroundColor(amount);

                amountTextView.setText(getFormatedDouble(amount));
                setAmountDeltaText(amountDelta);
            }
        }

        cellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderDayTransactionFragment calenderDayTransactionFragment = CalenderDayTransactionFragment.newInstance(null, amountPassed);
                openFragment(calenderDayTransactionFragment);
            }
        });

        // Set custom color if required
        setCustomResources(dateTime, cellView, dateTextView);

        return cellView;
    }

    /**
     * opens a fragment through replacing another fragment
     */
    private void openFragment(Fragment fragment) {
        if (null != fragment) {
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_menu_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private void styleDefault() {
        dateTextView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void styleToday() {
        dateTextView.setBackgroundColor(Color.GRAY);
    }

    private void setAmountDeltaText(double amountDelta) {
        String amountDeltaText = getFormatedDouble(amountDelta);
        if (amountDelta > 0) {
            amountDeltaText = "+" + amountDeltaText;
        }
        amountDeltaTextView.setText(amountDeltaText);
    }

    private void changeAmountBackgroundColor(double amount) {
        if (amount < 0) {
            int redColor = ContextCompat.getColor(cellView.getContext(), R.color.colorNegativeAmount);
            amountTextView.setBackgroundColor(redColor);
        } else if (amount == 0) {
            int blueColor = ContextCompat.getColor(cellView.getContext(), R.color.colorBankingOverview);
            amountTextView.setBackgroundColor(blueColor);
        } else {
            int greenColor = ContextCompat.getColor(cellView.getContext(), R.color.colorBankingOverviewLightGreen);
            amountTextView.setBackgroundColor(greenColor);
        }
    }

    private void changeAmountDeltaTextColor(double amountDelta) {
        if (amountDelta < 0) {
            int redColor = ContextCompat.getColor(cellView.getContext(), R.color.colorNegativeAmount);
            amountDeltaTextView.setTextColor(redColor);
        } else if (amountDelta == 0) {
            int blueColor = ContextCompat.getColor(cellView.getContext(), R.color.colorBankingOverview);
            amountDeltaTextView.setTextColor(blueColor);
        } else {
            int greenColor = ContextCompat.getColor(cellView.getContext(), R.color.colorBankingOverviewLightGreen);
            amountDeltaTextView.setTextColor(greenColor);
        }
    }

    private String getFormatedDouble(double number) {
        return String.format(Locale.GERMAN, "%.0f", number);
    }

}
