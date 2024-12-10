package com.example.recepti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties("kuharskiIzziv") // Ignoriraj kuharskiIzziv pri serializaciji receptov
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ime;
    private String opis;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "navodila", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> navodila = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sestavine", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> sestavine = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Kategorija kategorija;

    @OneToOne(cascade = CascadeType.ALL)
    private Komentar komentar;

    @JsonBackReference // Prepreči serializacijo polja `kuharskiIzziv`
    @ManyToOne
    @JoinColumn(name = "kuharski_izziv_id")
    private KuharskiIzziv kuharskiIzziv;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik; // Povezava z uporabnikom

    // Dodajamo novi polji za povprečno oceno in število ocen
    private double povprecnaOcena = 0.0;  // Privzeto vrednost postavimo na 0.0
    private int stOcen = 0;  // Privzeto število ocen je 0


    public Recept(String ime, List<String> navodila, List<String> sestavine, String opis) {
        this.id = id;
        this.ime = ime;
        this.navodila = navodila;
        this.sestavine = sestavine;
        this.opis =opis;
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


