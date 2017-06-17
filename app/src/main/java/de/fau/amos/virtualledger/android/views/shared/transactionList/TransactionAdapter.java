package de.fau.amos.virtualledger.android.views.shared.transactionList;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.fau.amos.virtualledger.R;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Activity activity;

    public TransactionAdapter(Activity activity, int layout, ArrayList<Transaction> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = super.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_item, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.id_datum);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
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

        if (transaction.booking().getAmount() >= 0) {
            String bankBalanceString = String.format(Locale.GERMAN, "%.2f", transaction.booking().getAmount());
            amountTextView.setText("+" + bankBalanceString);
            int greenColor = ContextCompat.getColor(this.activity, R.color.colorBankingOverviewLightGreen);
            amountTextView.setTextColor(greenColor);
        }
        if (transaction.booking().getAmount() < 0) {
            String bankBalanceString = String.format(Locale.GERMAN, "%.2f", Math.abs(transaction.booking().getAmount()));
            amountTextView.setText("-" + bankBalanceString);
            int redColor = ContextCompat.getColor(this.activity, R.color.colorNegativeAmount);
            amountTextView.setTextColor(redColor);
        }
    }


}
