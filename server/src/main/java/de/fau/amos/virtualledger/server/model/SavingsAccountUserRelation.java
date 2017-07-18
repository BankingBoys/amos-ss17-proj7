package de.fau.amos.virtualledger.server.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "savings_acccount_user_relation")
public class SavingsAccountUserRelation {

    @OneToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "savings_account_user_banking_identifier") // Table name was too long for DB
    private List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList = new ArrayList<>();

    public SavingsAccountUserRelation() {
    }

    public SavingsAccountUserRelation(User user) {
        this.user = user;
    }

    public SavingsAccountUserRelation(User user, List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList) {
        this.user = user;
        this.bankAccountIdentifierEntityList = bankAccountIdentifierEntityList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BankAccountIdentifierEntity> getBankAccountIdentifierEntityList() {
        return bankAccountIdentifierEntityList;
    }

    public void setBankAccountIdentifierEntityList(List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList) {
        this.bankAccountIdentifierEntityList = bankAccountIdentifierEntityList;
    }
}
