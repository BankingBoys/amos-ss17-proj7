package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Long click lisntens to an single element
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerSingleItem<R,T> implements View.OnLongClickListener {

    private final Activity listenedObject;
    private R element;
    private T element2;
    private Function<T,String> getName;
    private BiConsumer<R,T>  approvedAction;

    /**
     * Long Click listens to an single element
     * @param listenedObject = the referenced activity
     * @param element = the model object, that is presented in the view
     * @param getName = creates the name shown in the dialog out of a single model element
     * @param approvedAction = ction that is fired after user approves the shown delete dialog
     */
    public LongClickDeleteListenerSingleItem(Activity listenedObject, R element,T element2, Function<T,String> getName, BiConsumer<R,T> approvedAction) {
        this.listenedObject = listenedObject;
        this.element = element;
        this.element2 = element2;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }


    @Override
    public boolean onLongClick(View v) {
        DeleteDialog<R,T> tDeleteDialog = new DeleteDialog<>(listenedObject,element, element2, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
