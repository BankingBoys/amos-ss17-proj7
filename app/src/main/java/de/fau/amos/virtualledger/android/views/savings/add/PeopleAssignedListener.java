package de.fau.amos.virtualledger.android.views.savings.add;

import android.widget.TextView;

import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by sebastian on 14.07.17.
 */

public class PeopleAssignedListener {
    private Set<Contact> peopleSelected = new HashSet<>();
    private TextView textView;
    private String baseText;
    private SavingsAccount account;

    public PeopleAssignedListener(TextView textView, String baseText, SavingsAccount account) {
        this.textView = textView;
        this.baseText = baseText;
        this.account = account;
    }

    public void seĺectPerson(Contact person) {
        this.logger().info("Selecting:" + person);
        this.peopleSelected.add(person);
        this.updateText();
    }

    public void updateText() {
        int goalAmount = (int) this.account.getGoalbalance();
        if (goalAmount == 0) {
            Logger.getLogger(this.getClass().getCanonicalName()).warning("No Amount set!");
            goalAmount = 800;
        }

        int amount = (int) goalAmount / peopleSelected.size();
        String newConclusionText = new Formatter().format(this.baseText, this.peopleSelected.size() + "", amount + "").toString();
        logger().info("People changed. Updating new conclusion text to: " + newConclusionText);
        this.textView.setText(newConclusionText);
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

    public void deselectPerson(Contact person) {
        this.logger().info("Deselecting: " + person);
        this.peopleSelected.remove(person);
        this.updateText();
    }
}
