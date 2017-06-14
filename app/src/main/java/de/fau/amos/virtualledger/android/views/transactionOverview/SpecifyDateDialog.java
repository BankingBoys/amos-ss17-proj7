package de.fau.amos.virtualledger.android.views.transactionOverview;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;

public class SpecifyDateDialog extends DialogFragment {

    private Calendar startDate = new GregorianCalendar();
    private Calendar endDate = new GregorianCalendar();
    private Button startDateButton;
    private Button endDateButton;

    public Calendar getStartCalendar() {
        return this.startDate;
    }

    public Calendar getEndCalendar() {
        return this.endDate;
    }

    DatePickerDialog.OnDateSetListener startDateListener = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    startDate.set(Calendar.YEAR, year);
                    startDate.set(Calendar.MONTH, monthOfYear);
                    startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    startDateButton.setText(generateButtonText(year, monthOfYear, dayOfMonth));
                    logger().info("Startdate changed to: " + startDate);
                }

                private Logger logger() {
                    return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.hashCode() + "}");
                }

            };

    @NonNull
    private String generateButtonText(int year, int monthOfYear, int dayOfMonth) {
        return dayOfMonth + "." + monthOfYear + "." + year;
    }

    DatePickerDialog.OnDateSetListener endDateListener = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    endDate.set(Calendar.YEAR, year);
                    endDate.set(Calendar.MONTH, monthOfYear);
                    endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    endDateButton.setText(generateButtonText(year, monthOfYear, dayOfMonth));
                    logger().info("Startdate changed to: " + endDate);
                }

                private Logger logger() {
                    return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.hashCode() + "}");
                }

            };


    static SpecifyDateDialog newInstance() {
        return new SpecifyDateDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.date_range_chooser_dialog, container, false);
        startDateButton = (Button) v.findViewById(R.id.start_date_button);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), startDateListener, startDate
                        .get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateButton = (Button) v.findViewById(R.id.end_date_button);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), endDateListener, endDate
                        .get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                        endDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return v;
    }
}