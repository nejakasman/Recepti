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

<<<<<<< Updated upstream
    // Gumb za urejanje
    const editButton = document.createElement("button");
    editButton.textContent = "Uredi";
    editButton.onclick = () => showUpdateForm(recept);

    li.appendChild(deleteButton);
    li.appendChild(editButton);
    receptiList.appendChild(li);
  });
=======
        // Gumb za ocenjevanje
        const ratingButton = document.createElement('button');
        ratingButton.textContent = 'Ocenite recept';
        ratingButton.onclick = () => rateRecept(recept.id);

        // Gumb za komentar
        const commentButton = document.createElement('button');
        commentButton.textContent = 'Komentiraj';
        commentButton.onclick = () => commentOnRecept(recept.id);

        li.appendChild(deleteButton);
        li.appendChild(editButton);
        li.appendChild(ratingButton);
        li.appendChild(commentButton);
        receptiList.appendChild(li);
    });
>>>>>>> Stashed changes
}

// Ocenjevanje recepta
function rateRecept(receptId) {
    const ocena = prompt('Vnesite oceno od 1 do 5:');
    if (ocena >= 1 && ocena <= 5) {
        fetch('http://localhost:8080/api/komentarji/oceni', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                receptId: receptId,
                ocena: parseFloat(ocena)
            })
        })
        .then(response => {
            if (response.ok) {
                alert('Recept je bil ocenjen!');
                fetchRecepti();
            } else {
                alert('Napaka pri ocenjevanju recepta');
            }
        })
        .catch(error => {
            console.error('Napaka pri ocenjevanju recepta:', error);
        });
    } else {
        alert('Ocena mora biti med 1 in 5');
    }
}

// Komentiranje recepta
function commentOnRecept(receptId) {
    const komentar = prompt('Vnesite vaš komentar:');
    if (komentar) {
        fetch('http://localhost:8080/api/komentarji/dodaj', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                receptId: receptId,
                komentar: komentar
            })
        })
        .then(response => {
            if (response.ok) {
                alert('Komentar je bil dodan!');
                fetchRecepti();
            } else {
                alert('Napaka pri dodajanju komentarja');
            }
        })
        .catch(error => {
            console.error('Napaka pri dodajanju komentarja:', error);
        });
    }
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
