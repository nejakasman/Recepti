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
    private int casPriprave;


    @ElementCollection
    @CollectionTable(name = "navodila", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> navodila = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "sestavine", joinColumns = @JoinColumn(name = "recept_id"))
    private List<String> sestavine = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private Kategorija kategorija;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recept")
    private List<Komentar> komentarji = new ArrayList<>();

    @JsonBackReference // Prepreči serializacijo polja `kuharskiIzziv`
    @ManyToOne
    @JoinColumn(name = "kuharski_izziv_id")
    private KuharskiIzziv kuharskiIzziv;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik; // Povezava z uporabnikom

    private double povprecnaOcena = 0.0;
    private int stOcen = 0;




    public Recept(String ime, List<String> navodila, List<String> sestavine, String opis, int casPriprave, Kategorija kategorija) {
        this.ime = ime;
        this.navodila = navodila;
        this.sestavine = sestavine;
        this.opis = opis;
        this.casPriprave = casPriprave;
        this.kategorija = kategorija;
    }
}



