package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "glas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"uporabnik_id", "recept_id", "kuharski_izziv_id"})
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id", nullable = false)
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "recept_id", nullable = false)
    private Recept recept;

    @ManyToOne
    @JoinColumn(name = "kuharski_izziv_id", nullable = false)
    private KuharskiIzziv kuharskiIzziv;

    private LocalDateTime datumGlasovanja;

    public Vote (Uporabnik uporabnik, Recept recept, KuharskiIzziv kuharskiIzziv) {
        this.uporabnik = uporabnik;
        this.recept = recept;
        this.kuharskiIzziv = kuharskiIzziv;
        this.datumGlasovanja = LocalDateTime.now();
    }
}
