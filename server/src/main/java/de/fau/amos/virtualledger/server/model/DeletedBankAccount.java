package de.fau.amos.virtualledger.server.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "deleted_bank_accounts")
public class DeletedBankAccount {

    @EmbeddedId
    private DeletedBankAccountId id;

    @ManyToMany
    private Set<User> users = new HashSet<>();


    public DeletedBankAccount() {
    }

    public DeletedBankAccount(DeletedBankAccountId id, Set<User> users) {
        this.id = id;
        this.users = users;
    }

    public DeletedBankAccount(DeletedBankAccountId id) {
        this.id = id;
    }

    public DeletedBankAccountId getId() {
        return id;
    }

    public void setId(DeletedBankAccountId id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
