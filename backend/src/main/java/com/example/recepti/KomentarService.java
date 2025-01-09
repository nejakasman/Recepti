package com.example.recepti;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
@Service
public class KomentarService {

    @Autowired
    private KomentarRepository komentarRepository;

    @Autowired
    private ReceptRepository receptRepository;

    public Komentar dodajKomentar(Uporabnik uporabnik, Recept recept, String komentarBesedilo, float ocena) {
        if (ocena < 1 || ocena > 5) {
            throw new IllegalArgumentException("Ocena mora biti med 1 in 5.");
        }

        Komentar komentar = new Komentar();
        komentar.setUporabnik(uporabnik);
        komentar.setRecept(recept);
        komentar.setKomentar(komentarBesedilo);
        komentar.setOcena(ocena);
        komentar.setDatumObjave(LocalDateTime.now());

        // Preračunaj povprečno oceno recepta
        double novaPovprecnaOcena = (recept.getPovprecnaOcena() * recept.getStOcen()) + ocena;
        recept.setStOcen(recept.getStOcen() + 1); // Povečaj število ocen
        recept.setPovprecnaOcena(novaPovprecnaOcena / recept.getStOcen()); // Izračunaj novo povprečje

        receptRepository.save(recept);
        return komentarRepository.save(komentar);
    }
}
