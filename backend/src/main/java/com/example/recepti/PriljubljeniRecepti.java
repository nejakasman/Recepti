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

    // Getterji, setterji in metode
}


