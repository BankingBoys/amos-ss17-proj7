package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;

/**
 * LongClick lisntens to a list
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerList<T> implements AdapterView.OnItemLongClickListener {

    private final Activity listenedObject;
    private List<T> bankAccesses = new ArrayList<T>();
    private Function<T,String> getName;
    private Consumer<T> approvedAction;

    /**
     * LongClick lisntens to a list
     * @param listenedObject = the referenced activity
     * @param elementList = the list of modelobject presented in the view
     * @param getName = creates a name of a single model object out of the list
     * @param approvedAction = executes the action after user confirms the delete dialog
     */
    public LongClickDeleteListenerList(Activity listenedObject, List<T> elementList, Function<T,String> getName, Consumer<T> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccesses = elementList;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        T clickedModelObject = bankAccesses.get((int) id);
        DeleteDialog<T> tDeleteDialog = new DeleteDialog<>(listenedObject, clickedModelObject, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
