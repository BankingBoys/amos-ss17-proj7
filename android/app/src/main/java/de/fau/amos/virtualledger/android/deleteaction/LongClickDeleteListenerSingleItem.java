package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;

/**
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerSingleItem<T> implements View.OnLongClickListener {

    private final Activity listenedObject;
    private T element;
    private Function<T,String> getName;
    private Consumer<T> approvedAction;

    public LongClickDeleteListenerSingleItem(Activity listenedObject, T element, Function<T,String> getName, Consumer<T> approvedAction) {
        this.listenedObject = listenedObject;
        this.element = element;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }


    @Override
    public boolean onLongClick(View v) {
        DeleteDialog<T> tDeleteDialog = new DeleteDialog<>(listenedObject, element, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
