package de.fau.amos.virtualledger.server.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contacts", schema = "amos5db")
public class ContactsEntity {
    private int id;

    @OneToOne
    private User owner;

    @OneToOne
    private User contact;


    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "owner")
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "contact")
    public User getContact() {
        return this.contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }
}
