package de.fau.amos.virtualledger.android.transactionOverview;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.fau.amos.virtualledger.R;

public class TransactionAdapter extends ArrayAdapter<Transaction> {


    public TransactionAdapter(Activity activity, int layout, ArrayList<Transaction> data) {
        super(activity, layout, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("++##############Position" + position);
        Transaction transaction = super.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_item, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.id_datum);
        DateFormat formatter = new SimpleDateFormat("dd.MM.YY");
        dateTextView.setText(formatter.format(transaction.booking().getDate()));

        TextView bankTextView = (TextView) convertView.findViewById(R.id.id_bankname);
        bankTextView.setText(transaction.bankName());

        setAmount(convertView, transaction);

        TextView usageTextView = (TextView) convertView.findViewById(R.id.id_usage);
        usageTextView.setText(transaction.booking().getUsage());

        return convertView;
    }

    private void setAmount(View convertView, Transaction transaction) {
        TextView amountTextView = (TextView) convertView.findViewById(R.id.id_amount);

        if (transaction.booking().getAmount() > 0) {
            amountTextView.setText("+" + transaction.booking().getAmount());
        }
        if (transaction.booking().getAmount() < 0) {
            amountTextView.setText("-" + Math.abs(transaction.booking().getAmount()));
            ColorStateList redColor = convertView.getResources().getColorStateList(R.color.colorNegativeAmount);
            amountTextView.setTextColor(redColor);
        }
    }


}
