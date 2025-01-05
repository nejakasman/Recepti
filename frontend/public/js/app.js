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
    console.log("Poglej podrobnosti za recept z ID:", receptId);
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
        showMoreButton.onclick = () => {
            viewReceptDetails(recept.id);
            zabeleziOgled(recept.id);  // Dodaj zabeležitev ogleda
        };

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
    getPogostostSestavin();

    const editForm = document.getElementById("edit-form");
    if (editForm) {
        editForm.addEventListener("submit", updateRecept);
    }
};



function normalizeSestavina(sestavina) {
    return sestavina.replace(/^\d+\s*(g|kg|ml|l|oz|tbsp|tsp|cup)?\s*/i, '').trim().toLowerCase();
}
async function getPogostostSestavin() {
    try {
        const response = await fetch('http://localhost:8080/api/recepti/pogostost-sestavin');
        const pogostost = await response.json();
        console.log("Pogostost sestavin:", pogostost);

        const receptResponse = await fetch('http://localhost:8080/api/recepti/recepti');
        let recepti = await receptResponse.json();

        if (Object.keys(pogostost).length === 0) {
            console.log("Ni sestavin. Prikazujemo recepte po navadnem vrstnem redu.");
            displayRecepti(recepti);
        } else {
            displayPogostostSestavin(pogostost);

            const sestavineList = Object.keys(pogostost).sort((a, b) => pogostost[b] - pogostost[a]);

            function sortReceptiPoSestavinah(recepti, sestavina) {
                recepti.forEach(recept => {
                    const vsebujeSestavino = recept.sestavine.some(s => normalizeSestavina(s) === sestavina);
                    recept.vsebujeSestavino = vsebujeSestavino;
                });

                recepti.sort((a, b) => {
                    if (a.vsebujeSestavino && !b.vsebujeSestavino) {
                        return -1;  
                    }
                    if (!a.vsebujeSestavino && b.vsebujeSestavino) {
                        return 1;  
                    }
                    return 0; 
                });

                return recepti;  
            }

            let razvrščeniRecepti = [];

            for (let i = 0; i < sestavineList.length; i++) {
                const sestavina = sestavineList[i];
                console.log("Razvrščamo po sestavini:", sestavina);

                recepti = sortReceptiPoSestavinah(recepti, sestavina);

                razvrščeniRecepti = [...razvrščeniRecepti, ...recepti.filter(r => r.vsebujeSestavino)];
            
                recepti = recepti.filter(r => !r.vsebujeSestavino);
            }

            displayRecepti(razvrščeniRecepti);
        }

    } catch (error) {
        console.error('Napaka pri pridobivanju pogostosti sestavin:', error);
    }
}




function displayPogostostSestavin(data) {
    const pogostostDiv = document.getElementById('pogostost-sestavin'); 
    pogostostDiv.innerHTML = ''; 
    const ul = document.createElement('ul');
    for (const [sestavina, count] of Object.entries(data)) {
        const li = document.createElement('li');
        li.textContent = `${sestavina}: ${count}x`;
        ul.appendChild(li);
    }
    pogostostDiv.appendChild(ul);
}


// Funkcija za beleženje ogleda recepta
async function zabeleziOgled(receptId) {
    try {
        console.log('Klicana funkcija zabeleziOgled za recept:', receptId); 
        
        const response = await fetch(`http://localhost:8080/api/recepti/${receptId}/ogled`, {
            method: 'POST',
        });

        if (!response.ok) {
            console.error('Napaka pri zabeležitvi ogleda');
        } else {
            console.log('Ogled uspešno zabeležen');

            let ogledaniRecepti = JSON.parse(localStorage.getItem('ogledaniRecepti')) || [];
            if (!ogledaniRecepti.includes(receptId)) {
                ogledaniRecepti.push(receptId);
                localStorage.setItem('ogledaniRecepti', JSON.stringify(ogledaniRecepti));
            }
        }
    } catch (error) {
        console.error('Napaka pri povezavi z backendom:', error);
    }
}
