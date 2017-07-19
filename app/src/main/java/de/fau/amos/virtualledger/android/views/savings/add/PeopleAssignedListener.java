package de.fau.amos.virtualledger.android.views.savings.add;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

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

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

    public void seĺectPerson(Contact person) {
        this.logger().info("Selecting:" + person);
        this.peopleSelected.add(person);
        this.syncToSavingsAccount();
        this.updateText();
    }

    public void deselectPerson(Contact person) {
        this.logger().info("Deselecting: " + person);
        this.peopleSelected.remove(person);
        this.syncToSavingsAccount();
        this.updateText();
    }

    public void updateText() {
        int goalAmount = (int) this.account.getGoalbalance();
        if (goalAmount == 0) {
            Logger.getLogger(this.getClass().getCanonicalName()).warning("No Amount set!");
            this.textView.setText(R.string.add_savings_account_enter_goal_info_needed_message);
            return;
        }
        String personString = this.peopleSelected.size() + " " + "persons";
        if (this.peopleSelected.size() == 1) {
            personString = "one person";
        }

        int amount = goalAmount / peopleSelected.size();
        Formatter formatter = new Formatter();
        String newConclusionText = formatter.format(this.baseText, personString, amount + "").toString();
        formatter.close();
        logger().info("People changed. Updating new conclusion text to: " + newConclusionText);
        this.textView.setText(newConclusionText);
    }

    private void syncToSavingsAccount() {
        List<Contact> contacts = new ArrayList<>();
        for (Contact contact : this.peopleSelected) {
            if ("Me".equals(contact.getFirstName())) {
                continue;
            }
            contacts.add(contact);
        }
        this.account.setAdditionalAssignedUsers(contacts);
    }
}
