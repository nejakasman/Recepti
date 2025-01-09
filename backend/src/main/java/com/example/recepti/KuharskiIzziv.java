//package com.example.recepti;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "kuharski_izziv")
//
//public class KuharskiIzziv {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Getter
//    @Setter
//    private String naziv;
//
//    @Getter
//    @Setter
//    private String opis;
//
//    @Setter
//    @Getter
//    private LocalDate trajanjeDo;
//    @Setter
//    @Getter
//    private LocalDate trajanjeOd;
//
//    @JsonManagedReference // Serializira povezane recepte
//    @OneToMany(mappedBy = "kuharskiIzziv", cascade = CascadeType.ALL)
//    private List<Recept> recepti;
//
//    public KuharskiIzziv(String naziv, String opis, LocalDate trajanjeDo, LocalDate trajanjeOd) {
//        this.naziv = naziv;
//        this.opis = opis;
//        this.trajanjeDo = trajanjeDo;
//        this.trajanjeOd = trajanjeOd;
//    }
//
//    public KuharskiIzziv(int i, String s, String s1, LocalDate now) {
//
//    }
//
//    public Recept izracunajZmagovalca(VoteService glasService) {
//        if (recepti.isEmpty()) return null;
//
//        Recept zmagovalec = null;
//        int najvecGlasov = 0;
//
//        for (Recept recept : recepti) {
//            int glasovi = glasService.prestejGlasove(recept, this);
//            if (glasovi > najvecGlasov) {
//                najvecGlasov = glasovi;
//                zmagovalec = recept;
//            }
//        }
//
//        return zmagovalec;
//    }
//
//    @Override
//    public String toString() {
//        return "KuharskiIzziv{" +
//                "id=" + id +
//                ", naziv='" + naziv + '\'' +
//                ", opis='" + opis + '\'' +
//                ", trajanjeDo=" + trajanjeDo +
//                '}';
//    }
//
//}

package com.example.recepti;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "kuharski_izziv")
public class KuharskiIzziv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naziv;
    private String opis;

    private LocalDate trajanjeDo;
    private LocalDate trajanjeOd;

    @JsonManagedReference // Serializira povezane recepte
    @OneToMany(mappedBy = "kuharskiIzziv", cascade = CascadeType.ALL)
    private List<Recept> recepti;

    public KuharskiIzziv(String naziv, String opis, LocalDate trajanjeDo, LocalDate trajanjeOd) {
        this.naziv = naziv;
        this.opis = opis;
        this.trajanjeDo = trajanjeDo;
        this.trajanjeOd = trajanjeOd;
    }

    public KuharskiIzziv(int i, String s, String s1, LocalDate now) {}

    public Recept izracunajZmagovalca(VoteService glasService) {
        if (recepti.isEmpty()) return null;

        Recept zmagovalec = null;
        int najvecGlasov = 0;

        for (Recept recept : recepti) {
            int glasovi = glasService.prestejGlasove(recept, this);
            if (glasovi > najvecGlasov) {
                najvecGlasov = glasovi;
                zmagovalec = recept;
            }
        }

        return zmagovalec;
    }

    // Manjkajoči getterji (če se uporabljajo zunaj razreda)
    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }
}
