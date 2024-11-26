package com.example.recepti;

import com.example.recepti.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recepti")
public class ReceptController {
    @Autowired
    private ReceptService receptService;

    @GetMapping("/{id}/povprecna-ocena")
    public ResponseEntity<Double> getPovprecnaOcena(@PathVariable int id) {
        Recept recept = receptService.findById(id);
        double povprecnaOcena = receptService.izracunajPovprecnoOceno(recept);
        return ResponseEntity.ok(povprecnaOcena);
    }
}
