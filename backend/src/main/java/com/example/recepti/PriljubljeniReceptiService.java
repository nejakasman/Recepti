package com.example.recepti;

import com.example.recepti.PriljubljeniRecepti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriljubljeniReceptiService {
    @Autowired
    private PriljubljeniReceptiRepository priljubljeniReceptiRepository;

    public void dodajMedPriljubljene(Uporabnik uporabnik, Recept recept) {
        PriljubljeniRecepti priljubljeniRecepti = new PriljubljeniRecepti();
        priljubljeniRecepti.setUporabnik(uporabnik);
        priljubljeniRecepti.setRecept(recept);

        priljubljeniReceptiRepository.save(priljubljeniRecepti);
    }
}
