package com.example.recepti;

import com.example.recepti.Recept;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kuharski_izziv")
public class KuharskiIzziv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String naziv;

    @Getter
    @Setter
    private String opis;

    @Setter
    @Getter
    private LocalDateTime trajanjeDo;

    @OneToMany(mappedBy = "kuharskiIzziv", cascade = CascadeType.ALL)
    private List<Recept> recepti;

    @Override
    public String toString() {
        return "KuharskiIzziv{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", opis='" + opis + '\'' +
                ", trajanjeDo=" + trajanjeDo +
                '}';
    }

}
