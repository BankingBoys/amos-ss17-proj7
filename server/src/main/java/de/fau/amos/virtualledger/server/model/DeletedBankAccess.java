package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "deleted_bank_accesses")
public class DeletedBankAccess {


    @javax.persistence.Id
    private String bankAccessId;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public DeletedBankAccess() {
    }
    public DeletedBankAccess(String bankAccessId, Set<User> users) {
        this.bankAccessId = bankAccessId;
        this.users = users;
    }
    public DeletedBankAccess(String bankAccessId) {
        this.bankAccessId = bankAccessId;
    }

    public String getBankAccessId() {
        return bankAccessId;
    }

    public void setBankAccessId(String bankAccessId) {
        this.bankAccessId = bankAccessId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
