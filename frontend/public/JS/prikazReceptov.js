document.addEventListener("DOMContentLoaded", () => {
  fetchRecepti();
});

// Pridobi vse recepte
function fetchRecepti() {
  fetch("http://localhost:3306/recepti")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      displayRecepti(data);
    })
    .catch((error) => {
      console.error(
        "There has been a problem with your fetch operation:",
        error
      );
    });
}

// Prikaži seznam receptov
function displayRecepti(recepti) {
  const receptiList = document.getElementById("recepti");
  receptiList.innerHTML = ""; // Počisti seznam

  if (recepti.length === 0) {
    receptiList.innerHTML = "<p>Ni rezultatov.</p>"; // Prikaz sporočila, če ni rezultatov
    return;
  }

  recepti.forEach((recept) => {
    const li = document.createElement("li");
    li.textContent = `${recept.ime}: ${recept.opis}`;

    // Gumb za brisanje
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Izbriši";
    deleteButton.onclick = () => deleteRecept(recept.id);

    // Gumb za urejanje
    const editButton = document.createElement("button");
    editButton.textContent = "Uredi";
    editButton.onclick = () => showUpdateForm(recept);

    li.appendChild(deleteButton);
    li.appendChild(editButton);
    receptiList.appendChild(li);
  });
}

// Brisanje recepta
function deleteRecept(id) {
  fetch(`http://localhost:3306/recepti/${id}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (response.ok) {
        fetchRecepti();
      } else {
        throw new Error("Failed to delete the recept");
      }
    })
    .catch((error) => {
      console.error(
        "There has been a problem with your delete operation:",
        error
      );
    });
}

// urejanje recepta
function showUpdateForm(recept) {
  document.getElementById("update-section").style.display = "block";
  document.getElementById("update-ime").value = recept.ime;
  document.getElementById("update-opis").value = recept.opis;
  document.getElementById("update-sestavine").value = recept.sestavine;
  document.getElementById("update-navodila").value = recept.navodila;

  document.getElementById("update-form").dataset.receptId = recept.id;
}

// Posodobitev recepta
function updateRecept() {
  const id = document.getElementById("update-form").dataset.receptId;
  const updatedRecept = {
    ime: document.getElementById("update-ime").value,
    opis: document.getElementById("update-opis").value,
    sestavine: document.getElementById("update-sestavine").value,
    navodila: document.getElementById("update-navodila").value,
  };

  fetch(`http://localhost:3306/recepti/update/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedRecept),
  })
    .then((response) => {
      if (response.ok) {
        fetchRecepti();
        document.getElementById("update-section").style.display = "none";
      } else {
        throw new Error("Failed to update the recept");
      }
    })
    .catch((error) => {
      console.error(
        "There has been a problem with your update operation:",
        error
      );
    });
}

// Dodaj iskalno funkcionalnost
async function searchRecept() {
  const searchTerm = document.getElementById("search-input").value;
  const response = await fetch(
    `http://localhost:3306/recepti/search?ime=${searchTerm}`
  );

  if (response.ok) {
    const recepti = await response.json();
    displayRecepti(recepti);
  } else {
    console.error("Failed to fetch search results");
  }
}

// Dodajanje recepta med priljubljene
function dodajMedPriljubljene(receptId) {
    const uporabnik = 'userId';  // To mora biti pridobljeno iz trenutne seje uporabnika (npr. preko localStorage)

    fetch('http://localhost:8080/api/priljubljeniRecepti/dodaj', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            uporabnik: uporabnik,
            receptId: receptId
        })
    })
    .then(response => {
        if (response.ok) {
            alert('Recept je bil dodan med priljubljene!');
        } else {
            throw new Error('Napaka pri dodajanju med priljubljene');
        }
    })
    .catch(error => {
        console.error('Napaka pri dodajanju recepta med priljubljene:', error);
    });
}
