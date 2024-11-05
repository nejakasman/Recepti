
# Naziv projekta
Spletna stran z recepti

## Skupina: Bitne Princeske
### Člani: Neja, Anastasiya, Stanislav

## Kazalo

- [O projektu](#o-projektu)
- [Vizija projekta](#vizija-projekta)
- [Besednjak](#besednjak)
- [Uporabljene tehnologije](#uporabljene-tehnologije)
- [Struktura projekta](#struktura-projekta)
- [Za začetek](#za-začetek)
- [Kaj potrebujete](#kaj-potrebujete)
- [inštalacija](#inštalacija)
- [Zagon aplikacije](#zagon-aplikacije)
- [Testiranje](#testiranje)
- [Kontribucija](#kontribucija)
- [Licenca](#licenca)

## O projektu
Aplikacija Moji Recepti je preprosta spletna stran za deljenje in iskanje receptov, namenjena kuharjem, ljubiteljem kulinarike in vsem, ki radi pripravljajo jedi doma. Uporabnikom omogoča preprost način za iskanje receptov po kategorijah, objavljanje lastnih receptov in dodajanje svojih priljubljenih receptov na seznam za kasnejšo uporabo. Zasnovana je kot platforma, kjer lahko uporabniki delijo kulinarične nasvete, ocenjujejo recepte in izmenjujejo ideje.


## Vizija projekta

VIZIJA PROJEKTA
Namen: Spletna aplikacija za recepte bo uporabnikom omogočala enostaven in intuitiven dostop do široke zbirke kuharskih receptov. Z aplikacijo želimo uporabnikom zagotoviti priročen način za raziskovanje novih kulinaričnih idej, vodenje skozi pripravo jedi in personalizacijo receptov glede na njihove prehranske preference in razpoložljive sestavine.

Cilji: Naš cilj je uporabnikom ponuditi centralizirano platformo, ki ne le izboljša njihovo kuharsko izkušnjo, temveč tudi optimizira čas, potreben za načrtovanje obrokov in iskanje receptov. Aplikacija bo vključevala funkcionalnosti za iskanje po kategorijah, shranjevanje priljubljenih receptov, personalizirane priporočila in možnost dodajanja lastnih prilagoditev ali opomb k receptom.

Aplikacija je namenjena ljubiteljem kuhanja vseh starosti, od začetnikov do izkušenih kuharjev, ki iščejo navdih ali želijo izboljšati svoje kuharske veščine. Prav tako je idealna za tiste z omejenim časom, ki bi radi hitro našli ustrezne recepte na osnovi razpoložljivih sestavin ali specifičnih prehranskih potreb.
Z našo aplikacijo uporabnikom omogočamo dostop do personaliziranih kuharskih rešitev, kar prispeva k zmanjšanju stresa pri načrtovanju obrokov, izboljšanju organizacije kuhanja ter večji angažiranosti uporabnikov, saj si lahko ustvarijo svoj nabor priljubljenih receptov. S tem želimo izboljšati celotno izkušnjo kuhanja – od ideje do izvedbe.

## Besednjak

1.Recept
  Natančen opis priprave določene jedi, ki vključuje sestavine, korake za pripravo in pogosto tudi čas kuhanja ali pečenja.

2.Sestavine
  Seznam potrebnih živil in količin, ki so potrebne za pripravo določenega recepta.

3.Priljubljeni recepti
  Recepti, ki jih uporabnik shrani kot priljubljene, da ima hiter dostop do njih za kasnejšo uporabo.

4.Iskanje po sestavinah
  Funkcionalnost, ki uporabnikom omogoča iskanje receptov glede na specifične sestavine, ki jih imajo na voljo.

5.Prehranske preference
  Nastavitve uporabnikov glede prehranskih omejitev, kot so vegetarijanstvo, veganstvo, brezglutenske diete ipd.

6.Koraki priprave
  Navodila po korakih, ki opisujejo način in vrstni red priprave jedi.

7.Kategorije receptov
  Razdelki za organizacijo receptov, npr. po vrsti jedi (predjed, glavna jed, sladica), času priprave ali sestavinah.

8.Čas priprave
  Čas, ki je potreben za pripravo določene jedi od začetka do konca.


## Uporabljene tehnologije
- Spring Boot backend
- Node.js frontend

## Struktura projekta

/recepti
├── /frontend          # Node.js frontend application
└── /backend           # Spring Boot backend application



## Za začetek
Ta navodila vam bodo pomagali pri namestitvi in zagonu našega projekta na vašem računalniku.

## Kaj potrebujete
- JDK 17 or higher
- Node.js 
- MySQL Server
- Maven (za backend)

## Inštalacija

1. Klonirajte Git-repozitorij

git clone https://github.com/nejakasman/Recepti.git

2. Usposobite back-end

v terminalu v mapi projekta: cd backend
ustvarite mySQL podatkovno bazo in jo prilagodite projektu, konfigurirajte nastavitve v src/main/resources/application.properties

3. Build-ajte backend z ukazom v terminalu: mvn clean install

4. Premaknite se v mapo frontend: cd ../frontend

5. inštalirajte vse potrebne pakete z ukazom: npm install



 ## Zagon aplikacije
 1. Frontend
V mapi projekta v teminalu poženite dva ukaza:
 - cd frontend 
 - node server.js

 2. Backend
 v terminalu:
 - cd backend
 - mvn spring-boot:run

## Testiranje
 1. Backend
mvn test
 2. Frontend
 npm test

## Kontribucija

 Če imate željo prispevati k projektu! Sledite tem korakom za prispevanje:

Forknite repozitorij.
Ustvarite novo vejo za vašo funkcionalnost ali odpravo napake.
Opravite potrebne spremembe in jih shranite.
Potisnite svojo vejo in pošljite pull request.

## Licenca

This project is licensed under the terms of the MIT license.

## Diagram primera uporabe 

![System Architecture Diagram](dpu.jpg)

## Scenarij

1. Ogled receptov: Uporabnik si lahko ogleda podrobnosti izbranih receptov, vključno z njihovimi sestavinami in navodili za pripravo.
2. Iskanje receptov: Uporabnik lahko išče recepte po različnih kriterijih. 
3. Izbira filtra iskanja: Ta funkcionalnost razširja osnovno iskanje, tako da uporabnik lahko filtrira rezultate iskanja glede na sestavine, čas priprave, ocene itd.
4. Komentiranje o receptih: Uporabnik lahko deli svoje mnenje in nasvete z drugimi uporabniki.
5. Ocenjevanje receptov: Uporabnik lahko oceni recepte, kar pomaga drugim uporabnikom pri iskanju kakovostnih receptov.
6. Objava receptov: Uporabnik lahko deli svoje lastne recepte.
7. Urejanje receptov: Uporabnik lahko po potrebi ureja svoje recepte pred deljenjem ali kasneje.
8. Dodajanje receptov med priljubljene: Uporabnik lahko doda posamezne recepte med priljubljene, kjer so lažje dostopni.
9. Odobritev receptov: Administrator pregleda in odobrava ali zavrača nove recepte, ki jih predložijo registrirani uporabniki, s čimer zagotavlja, da so recepti kakovostni in primerne vsebine.
10. Brisanje receptov: administrator lahko izbriše recept, če ta ni primeren ali nepopolen.
 
