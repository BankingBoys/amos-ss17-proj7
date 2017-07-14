package de.fau.amos.virtualledger.android.views.savings.add;

import android.widget.TextView;

import java.util.Formatter;
import java.util.logging.Logger;

/**
 * Created by sebastian on 14.07.17.
 */

public class PeopleAssignetListener {
    private int peopleSelected = 0;
    private TextView textView;
    private String baseText;
    private int totalAmount;

    public PeopleAssignetListener(TextView textView, String baseText, int totalAmount) {
        this.textView = textView;
        this.baseText = baseText;
        this.totalAmount = totalAmount;
    }

    public void seĺectPerson() {
        this.peopleSelected++;
        this.updateText();
    }

    private void updateText() {
        int amount = (int) this.totalAmount / peopleSelected;
        String newConclusionText = new Formatter().format(this.baseText, this.peopleSelected, amount).toString();
        logger().info("People changed. Updating new conclusion text to: " + newConclusionText);
        this.textView.setText(newConclusionText);
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

    public void deselectPerson() {
        this.peopleSelected--;
        this.updateText();
    }
}
