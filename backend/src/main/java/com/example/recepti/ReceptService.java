package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptService {
    @Autowired
    private KomentarRepository komentarRepository;

    public double izracunajPovprecnoOceno(Recept recept) {
        List<Komentar> komentarji = komentarRepository.findByRecept(recept);
        if (komentarji.isEmpty()) {
            return 0.0;
        }
        return komentarji.stream()
                .mapToDouble(Komentar::getOcena)
                .average()
                .orElse(0.0);
    }

    public Recept findById(int id) {
        return null;
    }
}
