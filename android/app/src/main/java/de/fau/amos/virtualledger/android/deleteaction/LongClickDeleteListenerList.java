package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;

/**
 * LongClick lisntens to a list
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerList<T> implements AdapterView.OnItemLongClickListener {

    private final Activity listenedObject;
    private List<T> bankAccesses = new ArrayList<T>();
    private Function<T,String> getName;
    private BiConsumer<T,T> approvedAction;

    /**
     * LongClick lisntens to a list
     * @param listenedObject = the referenced activity
     * @param elementList = the list of modelobject presented in the view
     * @param getName = creates a name of a single model object out of the list
     * @param approvedAction = executes the action after user confirms the delete dialog
     */
    public LongClickDeleteListenerList(Activity listenedObject, List<T> elementList, Function<T,String> getName, BiConsumer<T, T> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccesses = elementList;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView bankBalance = (TextView) view.findViewById(R.id.bankAccessNameView);
        T clickedModelObject = getAccessByName(this.bankAccesses, bankBalance.getText().toString());


        DeleteDialog<T,T> tDeleteDialog = new DeleteDialog<>(listenedObject, clickedModelObject,clickedModelObject, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }


    private T getAccessByName(List<T> list, String name){
        for (T t: list) {
            if (getName.apply(t).equals(name))
                return t;
        }
        throw new IllegalArgumentException("No bank access found with name:"+name);
    }
}
