package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
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

    public double getOcena() {

        return 0;
    }

    public void setUporabnik(Uporabnik uporabnik) {
    }

    public void setRecept(Recept recept) {
    }

    public void setKomentar(String komentarBesedilo) {
    }

    public void setOcena(float ocena) {
    }

    public void setDatumObjave(LocalDateTime now) {
    }

    // Getterji, setterji in metode
}
