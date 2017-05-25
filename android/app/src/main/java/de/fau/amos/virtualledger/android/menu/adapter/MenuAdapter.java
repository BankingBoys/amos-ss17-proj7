package de.fau.amos.virtualledger.android.menu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.menu.model.ItemSlidingMenu;

/**
 * Created by Simon on 13.05.2017.
 */

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemSlidingMenu> listItem;

    /**
     * @param context
     * @param listItem
     * @methodtype constructor
     */
    public MenuAdapter(Context context, List<ItemSlidingMenu> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    /**
     * @return size
     * @methodtype getter
     */
    @Override
    public int getCount() {
        return listItem.size();
    }

    /**
     * @param position
     * @return item
     * @methodtype getter
     */
    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    /**
     * @param position
     * @return itemId
     * @methodtype getter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return view
     * @methodtype getter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.main_menu_sliding_items, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_icon);
        TextView textView = (TextView) view.findViewById(R.id.item_title);
        ItemSlidingMenu item = listItem.get(position);
        imageView.setImageResource(item.getImageId());
        textView.setText(item.getImageTitle());

        return view;
    }
}
