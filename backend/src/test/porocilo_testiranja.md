# Poročilo o testiranju

## Opis testov
- Testi preizkušajo osnovne funkcionalnosti glasovanja v izzivih:
    - Uspešno glasovanje za recept.
    - Poskus ponovnega glasovanja s strani istega uporabnika.
    - Preverjanje, ali je glasovanje pravilno odstranjeno.

## Člani skupine in odgovornosti
- Stas je razvil teste za `VoteService` (pozitivni in negativni scenariji) in `VoteController` in preverjanje HTTP odgovorov.

## Analiza uspešnosti
- Testi so uspešno pokrili večino scenarijev.
- Med testiranjem smo odkrili, da v primeru ponovnega glasovanja ni bilo pravilno obravnavanih napak (sistem ni vrnil napake). Napako smo odpravili z izboljšanjem `VoteService`.
