package com.example.recepti;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "priljubljeni_recepti")
public class PriljubljeniRecepti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime datumDodajanja;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id", nullable = false)
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "recept_id", nullable = false)
    private Recept recept;

    // Getterji in setterji
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatumDodajanja() {
        return datumDodajanja;
    }

    public void setDatumDodajanja(LocalDateTime datumDodajanja) {
        this.datumDodajanja = datumDodajanja;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Recept getRecept() {
        return recept;
    }

    public void setRecept(Recept recept) {
        this.recept = recept;
    }
}
