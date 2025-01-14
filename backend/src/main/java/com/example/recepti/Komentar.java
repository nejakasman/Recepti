package com.example.recepti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

@Entity
@Table(name = "komentar")
public class Komentar {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String komentarBesedilo;
        private LocalDateTime datumObjave;

        @ManyToOne
        @JoinColumn(name = "recept_id")
        @JsonBackReference
        private Recept recept;

    public void setKomentar(String komentarBesedilo) {
        this.komentarBesedilo = komentarBesedilo;
    }
}





//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String komentarBesedilo;
//
//    @Setter
//    private LocalDateTime datumObjave;
//
//    @Setter
//    @ManyToOne
//    @JoinColumn(name = "recept_id")
//    private Recept recept;
//
//    public void setKomentar(String komentarBesedilo) {
//        this.komentarBesedilo = komentarBesedilo;
//    }


