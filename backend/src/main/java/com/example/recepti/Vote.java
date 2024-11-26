package com.example.recepti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "Uporabnik_id")
    private Uporabnik uporabnik;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "Recept_id")
    private Recept recept;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "KuharskoiIzziv_id")
    private KuharskiIzziv challenge;

    @Getter
    @Setter
    private int glasovanje; // za glasovanje, 1glasovanje=1tocka



}

