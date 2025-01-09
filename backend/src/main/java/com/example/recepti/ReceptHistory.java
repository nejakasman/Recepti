package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recept_history")
public class ReceptHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ime;
    private String opis;
    private int casPriprave;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "navodila_history", joinColumns = @JoinColumn(name = "recept_history_id"))
    private List<String> navodila;

    @ElementCollection
    @CollectionTable(name = "sestavine_history", joinColumns = @JoinColumn(name = "recept_history_id"))
    private List<String> sestavine;

    @Enumerated(EnumType.STRING)
    private Kategorija kategorija;

    private String updatedBy;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "recept_id", nullable = false)
    private Recept recept;


    public ReceptHistory(Recept recept, String updatedBy) {
        this.recept = recept;
        this.ime = recept.getIme();
        this.opis = recept.getOpis();
        this.casPriprave = recept.getCasPriprave();
        this.navodila = new ArrayList<>(recept.getNavodila());
        this.sestavine = new ArrayList<>(recept.getSestavine());
        this.kategorija = recept.getKategorija();
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }


}

