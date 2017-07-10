package de.fau.amos.virtualledger.server.model;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class ContactsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User owner;

    @OneToOne
    private User contact;


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
