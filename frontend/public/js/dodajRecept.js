//// dodajanje recepta ////
// Pridobi kategorije iz backend API-ja
function fetchKategorije() {
  fetch("http://localhost:8080/api/recepti/kategorije")
    .then((response) => {
      if (!response.ok) {
        throw new Error(
          "Napaka pri pridobivanju kategorij: " + response.status
        );
      }
      return response.json();
    })
    .then((kategorije) => {
      console.log("Pridobljene kategorije:", kategorije); // Preverite, kaj je prejeto
      const kategorijaSelect = document.getElementById("kategorija");
      kategorije.forEach((kategorija) => {
        const option = document.createElement("option");
        option.value = kategorija;
        option.textContent = kategorija;
        kategorijaSelect.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Napaka pri pridobivanju kategorij:", error);
      alert("Napaka pri nalaganju kategorij.");
    });
}

// Pošlji POST zahtevek za dodajanje recepta
document
  .getElementById("recipeForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prepreči privzeto obnašanje obrazca

    // Ustvarite objekt za recept s pridobljenimi vrednostmi iz obrazca
    const recipe = {
      ime: document.getElementById("ime").value,
      opis: document.getElementById("opis").value,
      sestavine: document.getElementById("sestavine").value.split("\n"), // Razdelite sestavine po vrsticah
      navodila: document.getElementById("navodila").value.split("\n"), // Razdelite navodila po vrsticah
      casPriprave: document.getElementById("casPriprave").value,
      porcije: document.getElementById("porcije").value,
      kategorija: document.getElementById("kategorija").value,
    };
    console.log(recipe); // Preverite podatke v konzoli

    // Pošlji POST zahtevek na strežnik
    fetch("http://localhost:8080/api/recepti/dodaj", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(recipe), // Pošljite recept kot JSON
    })
      .then((response) => {
        if (response.ok) {
          return response.json(); // Če je odgovor uspešen, preberite JSON
        } else {
          throw new Error("Napaka pri dodajanju recepta");
        }
      })
      .then((data) => {
        alert("Recept uspešno dodan: " + data.ime); // Obveščanje uporabnika o uspehu
        // Počisti obrazec po uspešni oddaji
        document.getElementById("recipeForm").reset();
        // Osveži seznam receptov po uspešnem dodajanju
        fetchRecepti();
      })
      .catch((error) => {
        console.error("Napaka:", error); // Če pride do napake, jo izpišemo v konzoli
        alert("Napaka pri dodajanju recepta."); // Obveščanje uporabnika o napaki
      });
  });

//inicializacija
window.onload = () => {
  fetchKategorije();
  fetchRecepti();
};
