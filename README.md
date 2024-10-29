
# Project Title
Spletna stran z recepti

## Table of Contents

- [About the Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the App](#running-the-app)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## About the Project
Aplikacija Moji Recepti je preprosta spletna stran za deljenje in iskanje receptov, namenjena kuharjem, ljubiteljem kulinarike in vsem, ki radi pripravljajo jedi doma. Uporabnikom omogoča preprost način za iskanje receptov po kategorijah, objavljanje lastnih receptov in dodajanje svojih priljubljenih receptov na seznam za kasnejšo uporabo. Zasnovana je kot platforma, kjer lahko uporabniki delijo kulinarične nasvete, ocenjujejo recepte in izmenjujejo ideje.

## Technologies Used
- Spring Boot backend
- Node.js frontend

## Project Structure

/recepti
├── /frontend          # Node.js frontend application
└── /backend           # Spring Boot backend application



## Getting Started
Ta navodila vam bodo pomagali pri namestitvi in zagonu našega projekta na vašem računalniku.

## Prerequisites
- JDK 17 or higher
- Node.js 
- MySQL Server
- Maven (za backend)

## Installation

1. Klonirajte Git-repozitorij

git clone https://github.com/nejakasman/Recepti.git

2. Usposobite back-end

v terminalu v mapi projekta: cd backend
ustvarite mySQL podatkovno bazo in jo prilagodite projektu, konfigurirajte nastavitve v src/main/resources/application.properties

3. Build-ajte backend z ukazom v terminalu: mvn clean install

4. Premaknite se v mapo frontend: cd ../frontend

5. inštalirajte vse potrebne pakete z ukazom: npm install



 ## Running the App
 1. Frontend
V mapi projekta v teminalu poženite dva ukaza:
 cd frontend 
 npm start

 2. Backend
 v terminalu:
 cd backend
 mvn spring-boot:run

## Testing
 1. Backend
mvn test
 2. Frontend
 npm test

## Contributing

 Če imate željo prispevati k projektu! Sledite tem korakom za prispevanje:

Forknite repozitorij.
Ustvarite novo vejo za vašo funkcionalnost ali odpravo napake.
Opravite potrebne spremembe in jih shranite.
Potisnite svojo vejo in pošljite pull request.

## License

This project is licensed under the terms of the MIT license.

