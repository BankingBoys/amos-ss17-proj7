package de.fau.amos.virtualledger.android.views.menu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.menu.model.ItemSlidingMenu;

/**
 * Created by Simon on 13.05.2017.
 */

public class MenuAdapter extends BaseAdapter {

    /**
     *
     */
    private Context context;

    private List<ItemSlidingMenu> listItem;

    /**
     *
     */
    public MenuAdapter(Context context, List<ItemSlidingMenu> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    /**
     *
     */
    @Override
    public int getCount() {
        return listItem.size();
    }

    /**
     *
     */
    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    /**
     *
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position where the current element of the menu is shown
     * @param convertView reference to the view from the calling instance
     * @param parent of the View - type: ViewGroup
     * @return view which was modified by the method
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
