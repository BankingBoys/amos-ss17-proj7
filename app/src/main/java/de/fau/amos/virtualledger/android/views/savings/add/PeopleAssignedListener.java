package de.fau.amos.virtualledger.android.views.savings.add;

import android.widget.TextView;

import java.util.Formatter;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.model.SavingsAccount;

/**
 * Created by sebastian on 14.07.17.
 */

public class PeopleAssignedListener {
    private int peopleSelected = 0;
    private TextView textView;
    private String baseText;
    private SavingsAccount account;

    public PeopleAssignedListener(TextView textView, String baseText, SavingsAccount account) {
        this.textView = textView;
        this.baseText = baseText;
        this.account = account;
    }

    public void seĺectPerson() {
        this.peopleSelected++;
        this.updateText();
    }

    public void updateText() {
        int goalAmount = (int) this.account.getGoalbalance();
        if (goalAmount == 0) {
            Logger.getLogger(this.getClass().getCanonicalName()).warning("No Amount set!");
            goalAmount = 800;
        }

        int amount = (int) goalAmount / peopleSelected;
        String newConclusionText = new Formatter().format(this.baseText, this.peopleSelected + "", amount + "").toString();
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
