package de.fau.amos.virtualledger.android.views.calendar;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.CalendarView;

/**
 * Created by Georg on 10.06.2017.
 */

public class BankDataCalendarView extends CalendarView {

    public BankDataCalendarView(@NonNull Context context) {
        super(context);
    }

    public BankDataCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BankDataCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
