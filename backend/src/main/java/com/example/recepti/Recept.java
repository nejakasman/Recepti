package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Recept {

    public Recept(String ime, String opis, String sestavine, String navodila){
        this.ime = ime;
        this.opis = opis;
        this.sestavine = sestavine;
        this.navodila = navodila;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String ime;

    private String opis;

    private String sestavine;

    private String navodila;


}

