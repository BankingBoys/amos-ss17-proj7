package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Created by Georg on 22.05.2017.
 */
@Entity
@Table(name = "DeletedBankAccesses")
public class DeletedBankAccess {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String userEmail;

    public String bankAccessId;


    public DeletedBankAccess() {
    }

}
