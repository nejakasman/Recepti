package com.example.recepti;

import com.example.recepti.Komentar;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "uporabnik")
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String uporabniskoIme;

    private String geslo;

    private String email;

    private boolean jeAdmin;

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<Komentar> komentarji;

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<PriljubljeniRecepti> priljubljeniRecepti;


}
