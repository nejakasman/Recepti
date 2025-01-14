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

    private String komentar;

    private LocalDateTime datumObjave;

    @ManyToOne
    @JoinColumn(name = "recept_id")
    private Recept recept;

    public void setRecept(Recept recept) {
    }

    public void setKomentar(String komentarBesedilo) {
    }

    public void setDatumObjave(LocalDateTime now) {
    }

}
