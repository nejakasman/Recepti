package com.example.recepti;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "komentar")
public class Komentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float ocena;

    private String komentar;

    private LocalDateTime datumObjave;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "recept_id")
    private Recept recept;

    // Getterji, setterji in metode
}
