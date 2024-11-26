package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
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
    private LocalDate trajanjeDo;

    @OneToMany(mappedBy = "kuharskiIzziv", cascade = CascadeType.ALL)
    private List<Recept> recepti;

    public KuharskiIzziv(String naziv, String opis, LocalDate trajanjeDo) {
        this.naziv = naziv;
        this.opis = opis;
        this.trajanjeDo = trajanjeDo;
    }

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
