package de.fau.amos.virtualledger.server.model;


import javax.persistence.*;

@Entity
@Table(name = "Sessions")
public class Session {

    public String email;
    public String sessionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
}
