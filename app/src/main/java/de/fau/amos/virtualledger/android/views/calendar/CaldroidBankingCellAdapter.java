package de.fau.amos.virtualledger.android.views.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import hirondelle.date4j.DateTime;

/**
 * Created by Georg on 10.06.2017.
 */

public class CaldroidBankingCellAdapter extends CaldroidGridAdapter {

    TextView dateTextView;
    TextView amountDeltaTextView;
    TextView amountTextView;

    /**
     * Constructor
     *
     * @param context
     * @param month
     * @param year
     * @param caldroidData
     * @param extraData
     */
    public CaldroidBankingCellAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;

        //TODO
        double amountDelta = 100.00;
        double amount = 500.00;

        // load custom cell
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.calendar_view_cell, null);
        }
        dateTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_date);
        amountDeltaTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount_delta);
        amountTextView = (TextView) cellView.findViewById(R.id.calendar_view_cell_amount);

        dateTextView.setTextColor(Color.BLACK);
        changeAmountDeltaTextColor(amountDelta);
        changeAmountBackgroundColor(amount);

        DateTime dateTime = this.datetimeList.get(position);

        // Set color of the dates in previous / next month
        if (dateTime.getMonth() != month) {
            setColorsOfDayOutOfMonth();
        }

        dateTextView.setText("" + dateTime.getDay());
        amountTextView.setText(getFormatedDouble(amount));
        amountDeltaTextView.setText(getFormatedDouble(amountDelta));

        // Set custom color if required
        setCustomResources(dateTime, cellView, dateTextView);

        return cellView;
    }

    private void changeAmountBackgroundColor(double amount) {
        // TODO
    }

    private void changeAmountDeltaTextColor(double amountDelta) {
        // TODO
    }

    private void setColorsOfDayOutOfMonth() {
        dateTextView.setTextColor(resources
                .getColor(com.caldroid.R.color.caldroid_darker_gray));
    }

    private String getFormatedDouble(double number) {
        return String.format(Locale.GERMAN, "%.2f", number);
    }
}
