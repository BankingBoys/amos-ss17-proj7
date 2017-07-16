package de.fau.amos.virtualledger.android.views.savings.add;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
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
    private Context context;

    public PeopleAssignedListener(Context context, TextView textView, String baseText, SavingsAccount account) {
        this.textView = textView;
        this.baseText = baseText;
        this.account = account;
        this.context = context;
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

        int amount = (int) goalAmount / peopleSelected.size();
        String newConclusionText = new Formatter().format(this.baseText, personString, amount + "").toString();
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
        this.account.setAdditionalAssignedContacts(contacts);
    }
}
