package de.fau.amos.virtualledger.android.views.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.CellView;

import java.util.Map;

import de.fau.amos.virtualledger.R;

/**
 * Created by Georg on 10.06.2017.
 */

public class CaldroidBankingCellAdapter extends CaldroidGridAdapter {
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
        TextView cellView = (TextView) convertView;

        if (convertView == null) {
            if (squareTextViewCell) {
                cellView = (TextView) inflater.inflate(R.layout.square_date_cell, null);
            } else {
                cellView = (TextView) inflater.inflate(R.layout.normal_date_cell, null);
            }
        }

        customizeTextView(position, (CellView) cellView);

        return cellView;
    }
}
