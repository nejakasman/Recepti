package com.example.recepti;

import com.example.recepti.Komentar;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "uporabnik")
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String uporabniskoIme;

    private String geslo;

    private String email;

    private boolean jeAdmin;

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL)
    private List<Komentar> komentarji;



}
