//posodobitev porcij in preračunavanje sestavin
async function updatePorcije(receptId, porcije) {
  try {
      if (isNaN(porcije) || porcije <= 0 || !Number.isInteger(Number(porcije))) {
          alert("Napaka: Število porcij mora biti pozitivno celo število.");
          return; 
      }

      localStorage.setItem(`porcije_${receptId}`, porcije);

      const response = await fetch(`http://localhost:8080/api/recepti/${receptId}/sestavine?porcije=${porcije}`);
      if (!response.ok) throw new Error('Napaka pri pridobivanju sestavin.');

      const preracunaneSestavine = await response.json();

      const sestavineDiv = document.getElementById("sestavine");
      sestavineDiv.innerHTML = `
          <h3>Sestavine za ${porcije} porcij:</h3>
          <ul>${preracunaneSestavine.map(s => `<li>${s}</li>`).join("")}</ul>
      `;
  } catch (error) {
      console.error("Napaka pri preračunavanju sestavin:", error);
  }
}


// Funkcija za prikaz podrobnosti recepta
async function viewReceptDetails(receptId) {
    try {
        const response = await fetch(`http://localhost:8080/api/recepti/${receptId}`);
        if (!response.ok) throw new Error('Napaka pri pridobivanju recepta.');

        const recept = await response.json();

        const porcije = localStorage.getItem(`porcije_${receptId}`) || 1;

        const receptDetailsDiv = document.getElementById("recept-details");
        receptDetailsDiv.innerHTML = `
            <h1>${recept.ime}</h1>
            <label for="porcije">Število porcij:</label>
            <input type="number" id="porcije" value="${porcije}" min="1" onchange="updatePorcije(${recept.id}, this.value)" />
            
            <div id="sestavine">
                <h3>Sestavine za ${porcije} porcij:</h3>
                <ul>${recept.sestavine.map((s) => `<li>${s}</li>`).join("")}</ul>
            </div>
            
            <h3>Navodila:</h3>
            <p>${recept.navodila.join(", ")}</p>
            
            <h3>Čas priprave:</h3>
            <p>${recept.casPriprave} minut</p>
            
            <h3>Kategorija:</h3>
            <p>${recept.kategorija}</p>
        `;

        updatePorcije(receptId, porcije);
    } catch (error) {
        console.error("Napaka pri prikazu podrobnosti recepta:", error);
    }
}

// Funkcija za pridobivanje vseh receptov
async function fetchRecepti() {
    try {
        const response = await fetch("http://localhost:8080/api/recepti/recepti");
        const recepti = await response.json();
        displayRecepti(recepti);

        fetchPogostostSestavin(); // Posodobi pogostost sestavin

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

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Izbriši";
        deleteButton.onclick = () => {
            if (confirm("Ste prepričani, da želite izbrisati ta recept?")) {
                deleteRecept(recept.id);
            }
        };

        buttonContainer.appendChild(showMoreButton);
        buttonContainer.appendChild(editButton);
        buttonContainer.appendChild(deleteButton);
        receptCard.appendChild(receptTitle);
        receptCard.appendChild(buttonContainer);

        receptiContainer.appendChild(receptCard);
    });
}

// Funkcija za brisanje recepta
async function deleteRecept(receptId) {
    try {
        const response = await fetch(
            `http://localhost:8080/api/recepti/izbrisi/${receptId}`,
            {
                method: "DELETE",
            }
        );

        if (response.ok) {
            alert("Recept uspešno izbrisan");
            window.location.href = "/"; 
        } else {
            alert("Napaka pri brisanju recepta");
        }
    } catch (error) {
        console.error("Napaka pri brisanju recepta:", error);
    }
}

// Funkcija za prikaz obrazca za urejanje recepta
function showEditForm(recept) {
    const editForm = document.getElementById("edit-form");

    if (!editForm) {
        console.error("Element z ID 'edit-form' ne obstaja!");
        return;
    }

    editForm.style.display = "block";

    document.getElementById("receptId").value = recept.id;
    document.getElementById("editIme").value = recept.ime;
    document.getElementById("editOpis").value = recept.opis;
    document.getElementById("editCasPriprave").value = recept.casPriprave;
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

  // Preveri, ali je vrednost sestavin seznam, sicer jo razdeli na seznam
  const editSestavine = document
      .getElementById("editSestavine")
      .value
      .split(",")
      .map(s => s.trim());

  // Preveri, ali je vrednost navodil seznam, sicer jo razdeli na seznam
  const editNavodila = document
      .getElementById("editNavodila")
      .value
      .split(",")
      .map(s => s.trim());

  const editKategorija = document.getElementById("editKategorija").value;

  const updatedRecept = {
      ime: editIme,
      opis: editOpis,
      sestavine: editSestavine,
      navodila: editNavodila,
      casPriprave: editCasPriprave,
      kategorija: editKategorija,
  };

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


// Funkcija za preklicanje urejanja
function cancelEdit() {
    document.getElementById("edit-form").style.display = "none";
}

// Inicializacija ob nalaganju strani
window.onload = () => {
    fetchRecepti();

    const editForm = document.getElementById("edit-form");
    if (editForm) {
        editForm.addEventListener("submit", updateRecept);
    }
};

async function fetchPogostostSestavin() {
    try {
        const response = await fetch('http://localhost:8080/api/recepti/pogostost-sestavin'); // Pokliči API endpoint
        if (!response.ok) {
            throw new Error('Napaka pri pridobivanju podatkov.');
        }
        const data = await response.json(); // Preberi JSON odgovor
        displayPogostostSestavin(data); // Pokliči funkcijo za prikaz podatkov
    } catch (error) {
        console.error('Napaka:', error);
    }
}

function displayPogostostSestavin(data) {
    const pogostostDiv = document.getElementById('pogostost-sestavin'); // Poišči mesto za izpis
    pogostostDiv.innerHTML = ''; // Počisti obstoječo vsebino

    // Naredi seznam iz pogostosti
    const ul = document.createElement('ul');
    for (const [sestavina, count] of Object.entries(data)) {
        const li = document.createElement('li');
        li.textContent = `${sestavina}: ${count}x`;
        ul.appendChild(li);
    }
    pogostostDiv.appendChild(ul); // Dodaj seznam na stran
}
