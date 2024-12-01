package com.example.recepti;

import com.example.recepti.KomentarService;
import com.example.recepti.KomentarRequest;
import com.example.recepti.Komentar;
import com.example.recepti.Uporabnik;
import com.example.recepti.Recept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/komentarji")
public class KomentarController {

    @Autowired
    private KomentarService komentarService;

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private UporabnikRepository uporabnikRepository;

    // Dodaj komentar k receptu
    @PostMapping("/dodaj")
    public ResponseEntity<?> dodajKomentar(@RequestBody KomentarRequest request) {
        try {
            Recept recept = receptRepository.findById(request.getReceptId())
                    .orElseThrow(() -> new IllegalArgumentException("Recept ne obstaja"));

            Uporabnik uporabnik = (Uporabnik) uporabnikRepository.findById((int) request.getUporabnikId())
                    .orElseThrow(() -> new IllegalArgumentException("Uporabnik ne obstaja"));

            Komentar komentar = komentarService.dodajKomentar(
                    uporabnik, recept, request.getKomentar(), request.getOcena()
            );

            return ResponseEntity.ok(komentar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Napaka pri obdelavi zahtevka: " + e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
