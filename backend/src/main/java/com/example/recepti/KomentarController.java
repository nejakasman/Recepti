package com.example.recepti;

import com.example.recepti.KomentarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/komentarji")
public class KomentarController {
    @Autowired
    private KomentarService komentarService;

    @PostMapping("/dodaj")
    public ResponseEntity<?> dodajKomentar(@RequestBody KomentarRequest request) {
        try {
            Komentar komentar = komentarService.dodajKomentar(
                    request.getUporabnik(),
                    request.getRecept(),
                    request.getKomentar(),
                    request.getOcena()
            );
            return ResponseEntity.ok(komentar);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

