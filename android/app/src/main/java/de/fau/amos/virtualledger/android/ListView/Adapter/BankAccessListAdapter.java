package de.fau.amos.virtualledger.android.ListView.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.BankAccess;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccessListAdapter extends BaseAdapter {

    private Context context;
    private List<BankAccess> bankAccessList;
    final static String TAG = "BankAccessListAdapter";

    /**
     *
     * @param context
     * @param bankAccessList
     * @methodtype constructor
     */
    public BankAccessListAdapter(Context context, List<BankAccess> bankAccessList) {
        this.context = context;
        this.bankAccessList = bankAccessList;
    }

    /**
     *
     * @return size of the list of bank accesses
     * @methodtype getter
     */
    @Override
    public int getCount() {
        return bankAccessList.size();
    }

    /**
     *
     * @param position
     * @return Bank Access from this position
     * @methodtype getter
     */
    @Override
    public Object getItem(int position) {
        assertionOfValidPosition(position);
        return bankAccessList.get(position);
    }

    /**
     *
     * @param position
     * @return position
     * @methodtype getter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return View of the List at this position
     * @methodtype getter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.list_item_bankaccess, parent, false);
        TextView textViewBankAccess = (TextView) view.findViewById(R.id.bankAccessNameView);
        TextView textViewBankBalance = (TextView) view.findViewById(R.id.bankAccessBalanceView);
       // textViewBankAccess.setText(bankAccessList.get(position).getBankAccessName());

        textViewBankBalance.setText(String.valueOf(bankAccessList.get(position).getBalance()));
        return view;
    }

    /**
     *
     * @param pos
     * @throws IllegalArgumentException
     *
     */
    public void assertionOfValidPosition(int pos) throws IllegalArgumentException{
        if((pos < 0) || (pos >= bankAccessList.size())) {
            String errorMsg = "Tried to access non existing list entry";
            Log.e(TAG, errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

}
