package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UporabnikController {

    @Autowired
    private UporabnikRepository uporabnikRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Uporabnik uporabnik) {
        // Preveri, če uporabnik že obstaja (po uporabniškem imenu ali emailu)
        if (uporabnikRepository.findByUporabniskoIme(uporabnik.getUporabniskoIme()) != null) {
            return ResponseEntity.badRequest().body("Uporabniško ime je že zasedeno");
        }
        if (uporabnikRepository.findByEmail(uporabnik.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email je že registriran");
        }

        // Kriptiraj geslo uporabnika
        uporabnik.setGeslo(passwordEncoder.encode(uporabnik.getGeslo()));

        uporabnik.setJeAdmin(false);

        uporabnikRepository.save(uporabnik);
        return ResponseEntity.ok("Registracija uspešna");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Uporabnik uporabnik) {
        Uporabnik foundUser = uporabnikRepository.findByUporabniskoIme(uporabnik.getUporabniskoIme());
        
        if (foundUser != null && passwordEncoder.matches(uporabnik.getGeslo(), foundUser.getGeslo())) {
            return ResponseEntity.ok("Prijava uspešna");
        }

        return ResponseEntity.badRequest().body("Neveljavni uporabniški podatki");
    }
}

