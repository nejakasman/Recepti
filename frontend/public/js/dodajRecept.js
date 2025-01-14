//// dodajanje recepta ////

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
      console.log("Pridobljene kategorije:", kategorije); 
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

document
  .getElementById("recipeForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); 

    const recipe = {
      ime: document.getElementById("ime").value,
      opis: document.getElementById("opis").value,
      sestavine: document
        .getElementById("sestavine")
        .value.split(",")
        .map(s => s.trim()),
      navodila: document
        .getElementById("navodila")
        .value.split(",")
        .map(s => s.trim()),
      casPriprave: document.getElementById("casPriprave").value,
      kategorija: document.getElementById("kategorija").value,
    };
    console.log(recipe); 

    fetch("http://localhost:8080/api/recepti/dodaj", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(recipe), 
    })
      .then((response) => {
        if (response.ok) {
          return response.json(); 
        } else {
          throw new Error("Napaka pri dodajanju recepta");
        }
      })
      .then((data) => {
        alert("Recept uspešno dodan: " + data.ime); 
        document.getElementById("recipeForm").reset();
        getPogostostSestavin();
      })
      .catch((error) => {
        console.error("Napaka:", error); 
        alert("Napaka pri dodajanju recepta."); 
      });
  });

window.onload = () => {
  fetchKategorije();
  getPogostostSestavin();
};
