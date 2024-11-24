package com.example.recepti;

import com.example.recepti.Recept;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kuharski_izziv")
public class KuharskiIzziv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naziv;

    private String opis;

    private LocalDateTime trajanjeDo;

    @OneToMany(mappedBy = "kuharskiIzziv", cascade = CascadeType.ALL)
    private List<Recept> recepti;


}
