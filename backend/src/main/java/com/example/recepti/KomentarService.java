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

    public Komentar dodajKomentar( Recept recept, String komentarBesedilo) {

        Komentar komentar = new Komentar();
        komentar.setRecept(recept);
        komentar.setKomentar(komentarBesedilo);
        komentar.setDatumObjave(LocalDateTime.now());

        receptRepository.save(recept);
        return komentarRepository.save(komentar);
    }
    public Iterable<Komentar> pridobiVseKomentarje() {
        return komentarRepository.findAll();
    }

    public Iterable<Komentar> pridobiKomentarjeZaRecept(int receptId) {
        return komentarRepository.findByReceptId(receptId);
    }

}
