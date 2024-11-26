package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KomentarService {
    @Autowired
    private KomentarRepository komentarRepository;

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

        return komentarRepository.save(komentar);
    }
}
