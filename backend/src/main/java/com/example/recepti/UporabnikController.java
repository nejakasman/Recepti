package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UporabnikController {

    @Autowired
    private UporabnikRepository uporabnikRepository;

    // Preprosta metoda za registracijo uporabnikov brez varnosti
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Uporabnik uporabnik) {
        // Preveri, če uporabnik že obstaja (po uporabniškem imenu ali emailu)
        if (uporabnikRepository.findByUporabniskoIme(uporabnik.getUporabniskoIme()) != null) {
            return ResponseEntity.badRequest().body("Uporabniško ime je že zasedeno");
        }
        if (uporabnikRepository.findByEmail(uporabnik.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email je že registriran");
        }

        // Preprosto kriptiranje gesla (priporočam uporabo bolj varnih metod v produkciji)
        String encryptedPassword = uporabnik.getGeslo(); // Tukaj lahko uporabiš enostavno metodo, npr. Base64
        uporabnik.setGeslo(encryptedPassword);

        // Nastavi uporabnika kot navadnega (ne administratorja)
        uporabnik.setJeAdmin(false);

        // Shrani uporabnika v bazo
        uporabnikRepository.save(uporabnik);

        return ResponseEntity.ok("Registracija uspešna");
    }

    // Preprosta metoda za prijavo uporabnika brez Spring Security
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Uporabnik uporabnik) {
        // Poišči uporabnika v bazi
        Uporabnik foundUser = uporabnikRepository.findByUporabniskoIme(uporabnik.getUporabniskoIme());

        if (foundUser != null && uporabnik.getGeslo().equals(foundUser.getGeslo())) {
            return ResponseEntity.ok("Prijava uspešna");
        }
        return ResponseEntity.badRequest().body("Neveljavni uporabniški podatki");
    }
}
