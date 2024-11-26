package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import com.example.recepti.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recept")
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ime;

    @ElementCollection
    @CollectionTable(name = "navodila", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> navodila = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "sestavine", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> sestavine = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Kategorija kategorija;

    @OneToOne(cascade = CascadeType.ALL)
    private Komentar komentar;

    @ManyToOne
    @JoinColumn(name = "kuharski_izziv_id")
    private KuharskiIzziv kuharskiIzziv;

<<<<<<< Updated upstream
    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik; // Povezava z uporabnikom
=======
    // Dodajamo novi polji za povprečno oceno in število ocen
    private double povprecnaOcena = 0.0;  // Privzeto vrednost postavimo na 0.0
    private int stOcen = 0;  // Privzeto število ocen je 0
>>>>>>> Stashed changes

    public Recept(String ime, List<String> navodila, List<String> sestavine) {
        this.id = id;
        this.ime = ime;
        this.navodila = navodila;
        this.sestavine = sestavine;
    }
}
enum Kategorija {
    ZAJTRK,
    KOSILO,
    VECERJA,
    MALICA,
    SLADICA,
    DRUGO
}


