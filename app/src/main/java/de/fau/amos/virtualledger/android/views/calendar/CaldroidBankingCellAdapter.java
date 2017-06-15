package de.fau.amos.virtualledger.android.views.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;




import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.Booking;
import hirondelle.date4j.DateTime;

/**
 * Created by Georg on 10.06.2017.
 */

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

        // load custom cell
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.calendar_view_cell, null);
        }
        dateTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_date);
        amountDeltaTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount_delta);
        amountTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount);

        // Set color of the dates in previous / next month
        if (dateTime.getMonth() != month) {
            styleDayOutOfMonth();
        }

        // Set color of today
        if(dateTime.equals(getToday())) {
            styleToday();
        }

        styleDefault();
        dateTextView.setText("" + dateTime.getDay());

        // only show amount + amount delta if there are changes:
        if(amountDelta != 0.0) {
            changeAmountDeltaTextColor(amountDelta);
            changeAmountBackgroundColor(amount);

            amountTextView.setText(getFormatedDouble(amount));
            setAmountDeltaText(amountDelta);
        }

        // Set custom color if required
        setCustomResources(dateTime, cellView, dateTextView);

        return cellView;
    }

    private void styleDefault() {
        dateTextView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void styleToday() {
        dateTextView.setBackgroundColor(Color.GRAY);
    }

    private void setAmountDeltaText(double amountDelta) {
        String amountDeltaText = getFormatedDouble(amountDelta);
        if (amountDelta < 0) {
            amountDeltaText = "-" + amountDeltaText;
        }
        if (amountDelta == 0) {
            amountDeltaText = "";
        }else {
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

    private void styleDayOutOfMonth() {
        dateTextView.setTypeface(Typeface.DEFAULT);
    }

    private String getFormatedDouble(double number) {
        return String.format(Locale.GERMAN, "%.0f", number);
    }

}
