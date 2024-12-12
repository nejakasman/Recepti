// Pridobivanje vseh receptov iz backend API
async function fetchRecepti() {
  try {
    const response = await fetch("http://localhost:8080/api/recepti/recepti");
    const recepti = await response.json();
    displayRecepti(recepti);
  } catch (error) {
    console.error("Napaka pri pridobivanju receptov:", error);
  }
}

// Funkcija za prikaz vseh receptov na glavni strani
function displayRecepti(recepti) {
  const receptiContainer = document.getElementById("recepti-container");
  receptiContainer.innerHTML = "";

  recepti.forEach((recept) => {
    const receptCard = document.createElement("div");
    receptCard.classList.add("recipe-card");

    const receptTitle = document.createElement("h2");
    receptTitle.textContent = recept.ime;

    const buttonContainer = document.createElement("div");

    const showMoreButton = document.createElement("button");
    showMoreButton.textContent = "Prikaži več";
    showMoreButton.onclick = () => viewReceptDetails(recept.id);

    const editButton = document.createElement("button");
    editButton.textContent = "Uredi";
    editButton.onclick = () => showEditForm(recept);

    buttonContainer.appendChild(showMoreButton);
    buttonContainer.appendChild(editButton);
    receptCard.appendChild(receptTitle);
    receptCard.appendChild(buttonContainer);

    receptiContainer.appendChild(receptCard);
  });
}

// Funkcija za prikaz podrobnosti recepta
async function viewReceptDetails(receptId) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/recepti/${receptId}`
    );
    const recept = await response.json();
    showReceptDetails(recept);
  } catch (error) {
    console.error("Napaka pri pridobivanju recepta:", error);
  }
}

// Funkcija za prikaz podrobnosti recepta na novi strani
function showReceptDetails(recept) {
  const receptDetailsPage = `
   <!DOCTYPE html>
<html lang="sl">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Spletna stran z recepti</title>
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    
      <header>
        <h1>Recepti</h1>
        <nav>
           <ul>
          <li><a href="index.html">Domov</a></li>
          <li><a href="kuharskiIzziv.html">Kuharski izzivi</a></li>
          <li><a href="dodajRecept.html">Dodaj recept</a></li>
          <li><a href="prijava.html">Prijava/registracija</a></li>
        </ul>
        </nav>
      </header>
  <main>
    <div class="container">
      <h1>Podrobnosti recepta: ${recept.ime}</h1>
      <div class="recipe-details">
        <h3>Opis:</h3>
        <p>${recept.opis}</p>
  
        <h3>Sestavine:</h3>
        <ul>
          ${
            recept.sestavine && recept.sestavine.length > 0
              ? recept.sestavine.map((s) => `<li>${s}</li>`).join("")
              : "<li>Nobene sestavine niso na voljo.</li>"
          }
        </ul>
  
        <h3>Navodila:</h3>
        <ul>
          ${
            recept.navodila && recept.navodila.length > 0
              ? recept.navodila.map((n) => `<li>${n}</li>`).join("")
              : "<li>Nobena navodila niso na voljo.</li>"
          }
        </ul>
  
        <h3>Čas priprave:</h3>
        <p>${recept.casPriprave} minut</p>
  
        <h3>Porcije:</h3>
        <p>${recept.porcije}</p>
  
        <h3>Kategorija:</h3>
        <p>${recept.kategorija}</p>
  
       <button class="delete" onclick="deleteRecept(${
         recept.id
       })">Izbriši recept</button>
      </div>
      </div>
    </div>
    </main>

    <footer>
      <p>&copy; 2024 Spletna stran z recepti</p>
    </footer>
    <script src="app.js"></script>
  </body>
</html>
    `;

  document.body.innerHTML = receptDetailsPage;
}

// Funkcija za brisanje recepta
async function deleteRecept(receptId) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/recepti/recepti/${receptId}`,
      {
        method: "DELETE",
      }
    );

    if (response.ok) {
      alert("Recept uspešno izbrisan");
      window.location.href = "/"; // Preusmeri nazaj na glavno stran
    } else {
      alert("Napaka pri brisanju recepta");
    }
  } catch (error) {
    console.error("Napaka pri brisanju recepta:", error);
  }
}

//prikaz obrazca za urejanje recepta
function showEditForm(recept) {
  document.getElementById("edit-form").style.display = "block";

  document.getElementById("receptId").value = recept.id;
  document.getElementById("editIme").value = recept.ime;
  document.getElementById("editOpis").value = recept.opis;
  document.getElementById("editCasPriprave").value = recept.casPriprave;
  document.getElementById("editPorcije").value = recept.porcije;
  // Predhodno sestavine in navodila (lahko bi dodali input za dodajanje novih)
  document.getElementById("editSestavine").value = recept.sestavine;
  document.getElementById("editNavodila").value = recept.navodila;

  document.getElementById("editKategorija").value = recept.kategorija;
}

// Funkcija za preklicanje urejanja
function hideEditForm() {
  document.getElementById("edit-form").style.display = "none";
}

// Funkcija za posodabljanje recepta
async function updateRecept(event) {
  event.preventDefault();

  const id = document.getElementById("receptId").value;
  const editIme = document.getElementById("editIme").value;
  const editOpis = document.getElementById("editOpis").value;
  const editCasPriprave = document.getElementById("editCasPriprave").value;
  const editPorcije = document.getElementById("editPorcije").value;
  const editSestavine = document
    .getElementById("editSestavine")
    .value.split(", ");
  const editNavodila = document
    .getElementById("editNavodila")
    .value.split(", ");
  const editKategorija = document.getElementById("editKategorija").value;

  const updatedRecept = {
    ime: editIme,
    opis: editOpis,
    sestavine: editSestavine,
    navodila: editNavodila,
    porcije: editPorcije,
    casPriprave: editCasPriprave,
    kategorija: editKategorija,
  };
  console.log("Submitting updated recipe", updatedRecept);

  try {
    const response = await fetch(
      `http://localhost:8080/api/recepti/update/${id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedRecept),
      }
    );

    if (response.ok) {
      alert("Recept posodobljen");
      fetchRecepti();
      document.getElementById("edit-form").style.display = "none";
    } else {
      alert("Napaka pri posodabljanju recepta");
    }
  } catch (error) {
    console.error("Napaka pri posodabljanju recepta:", error);
  }
}

function cancelEdit() {
  document.getElementById("edit-form").style.display = "none";
}

const editForm = document.getElementById("edit-form");

window.onload = () => {
  fetchRecepti();

  const editForm = document.getElementById("edit-form");
  if (editForm) {
    editForm.addEventListener("submit", updateRecept);
  }
};
