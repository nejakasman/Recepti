document.addEventListener('DOMContentLoaded', () => {
    fetchRecepti();
});


// Funkcija za pridobivanje in prikazovanje receptov
function fetchRecepti() {
    fetch('http://localhost:8080/recepti')  // URL za pridobitev receptov
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Napaka pri pridobivanju receptov');
        }
    })
    .then(data => {
        console.log(data);  // Preverite, kaj vrne strežnik
    if (document.getElementById('receptiList')) {
        // Če je element receptiList prisoten, smo na dodajRecept.html
        displayRecepti(data, 'receptiList');
    } else if (document.getElementById('recepti')) {
        // Če je element recepti prisoten, smo na recept.html
        displayRecepti(data, 'recepti');
    }
})
.catch(error => {
    console.error('Napaka:', error);
    alert('Napaka pri pridobivanju receptov');
});
}

/*
function displayRecepti(recepti, targetId) {
    const targetElement = document.getElementById(targetId);
    if (!targetElement) {
        console.error(`Element z ID '${targetId}' ne obstaja.`);
        return;
    }

    targetElement.innerHTML = ''; // Počisti seznam pred prikazom novih receptov

    if (recepti.length === 0) {
        targetElement.innerHTML = '<p>Ni receptov še na voljo.</p>';
        return;
    }

    recepti.forEach(recept => {
        const receptDiv = document.createElement('div');
        receptDiv.classList.add('recept-card');  // Razred za lepši izgled recepta 

        // Ime recepta
        const ime = document.createElement('h3');
        ime.textContent = recept.ime;
        receptDiv.appendChild(ime);

        // Sestavine
        if (recept.sestavine && recept.sestavine.length > 0) {
            const sestavine = document.createElement('p');
            sestavine.innerHTML = `<strong>Sestavine:</strong> ${recept.sestavine.join(', ')}`;
            receptDiv.appendChild(sestavine);
        }

        // Navodila
        if (recept.navodila && recept.navodila.length > 0) {
            const navodila = document.createElement('p');
            navodila.innerHTML = `<strong>Navodila:</strong> ${recept.navodila.join(', ')}`;
            receptDiv.appendChild(navodila);
        }

        // Opis (če obstaja)
        if (recept.opis) {
            const opis = document.createElement('p');
            opis.innerHTML = `<strong>Opis:</strong> ${recept.opis}`;
            receptDiv.appendChild(opis);
        }

        // Gumbi za urejanje in brisanje
        const buttonsDiv = document.createElement('div');
        buttonsDiv.classList.add('buttons');

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Izbriši';
        deleteButton.onclick = () => deleteRecept(recept.id);
        buttonsDiv.appendChild(deleteButton);

        const editButton = document.createElement('button');
        editButton.textContent = 'Uredi';
        editButton.onclick = () => showUpdateForm(recept);
        buttonsDiv.appendChild(editButton);

        receptDiv.appendChild(buttonsDiv);

        // Dodaj celoten recept v ciljni element
        targetElement.appendChild(receptDiv);
    });
} */

    function displayRecepti(recepti, targetId) {
        const targetElement = document.getElementById(targetId);
        if (!targetElement) {
            console.error(`Element z ID '${targetId}' ne obstaja.`);
            return;
        }
    
        targetElement.innerHTML = ''; // Počisti seznam pred prikazom novih receptov
    
        if (recepti.length === 0) {
            targetElement.innerHTML = '<p>Ni receptov še na voljo.</p>';
            return;
        }
    
        recepti.forEach(recept => {
            const receptDiv = document.createElement('div');
            receptDiv.classList.add('recept-card'); // Razred za lepši izgled recepta 
    
            // Ime recepta kot gumb
            const ime = document.createElement('h3');
            ime.textContent = recept.ime;
            ime.classList.add('recept-title'); // Razred za slog
            ime.onclick = () => showReceptDetails(recept); // Klik za prikaz podrobnosti
            receptDiv.appendChild(ime);
    
            // Gumbi za urejanje in brisanje
            const buttonsDiv = document.createElement('div');
            buttonsDiv.classList.add('buttons');
    
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Izbriši';
            deleteButton.onclick = () => deleteRecept(recept.id);
            buttonsDiv.appendChild(deleteButton);
    
            const editButton = document.createElement('button');
            editButton.textContent = 'Uredi';
            editButton.onclick = () => showUpdateForm(recept);
            buttonsDiv.appendChild(editButton);
    
            receptDiv.appendChild(buttonsDiv);
    
            // Dodaj celoten recept v ciljni element
            targetElement.appendChild(receptDiv);
        });
    }
    
    // Funkcija za prikaz podrobnosti recepta
    function showReceptDetails(recept) {
        const detailsDiv = document.getElementById('recept-podrobnosti');
        if (!detailsDiv) {
            console.error('Manjka element z ID "recept-podrobnosti".');
            return;
        }
    
        detailsDiv.style.display = 'block'; // Prikaži sekcijo podrobnosti
    
        // Napolni podrobnosti recepta
        detailsDiv.innerHTML = `
            <h3>${recept.ime}</h3>
            <label for="porcije">Število porcij:</label>
            <input type="number" id="porcije" value="1" min="1" onchange="updatePorcije(${recept.id}, this.value)">
            <p><strong>Sestavine:</strong> ${recept.sestavine.join(', ')}</p>
            <p><strong>Navodila:</strong> ${recept.navodila.join(', ')}</p>
            <p><strong>Opis:</strong> ${recept.opis || 'Ni opisa.'}</p>
        `;
    }
    
    // Funkcija za posodobitev porcij (če bo potrebno)
    function updatePorcije(receptId, porcije) {
        console.log(`Recept ${receptId} posodobljen na ${porcije} porcij.`);
        // Tukaj lahko dodate logiko za dinamično posodobitev količin sestavin.
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


// Funkcija za brisanje recepta (po potrebi)
function deleteRecept(id) {
    fetch(`http://localhost:8080/recepti/${id}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            fetchRecepti();  // Osveži seznam po brisanju
        } else {
            throw new Error('Napaka pri brisanju recepta');
        }
    })
    .catch(error => {
        console.error('Napaka:', error);
        alert('Napaka pri brisanju recepta');
    });
}

// urejanje recepta
function showUpdateForm(recept) {

    console.log("Podatki recepta:", recept);

    
    document.getElementById('update-ime').value = recept.ime || '';
    document.getElementById('update-sestavine').value = recept.sestavine.join(', ') || '';
    document.getElementById('update-navodila').value = recept.navodila.join(', ') || '';
    document.getElementById('update-opis').value = recept.opis;

  document.getElementById("update-form").dataset.receptId = recept.id;
  document.getElementById('update-section').style.display = 'block';
}

// Posodobitev recepta
function updateRecept() {
    console.log("Funkcija updateRecept je bila sprožena.");
    const form = document.getElementById('update-form');
    const id = document.getElementById('update-form').dataset.receptId;
    if (!id) {
        alert('Napaka: ID recepta ni nastavljen.');
        return;
    }
    const updatedRecept = {
        ime: document.getElementById('update-ime').value,
        sestavine: document.getElementById('update-sestavine').value.split(',').map(item => item.trim()),
        navodila: document.getElementById('update-navodila').value.split(',').map(item => item.trim()),
        opis: document.getElementById('update-opis').value
    };

    console.log("Posodabljam recept z ID:", id, "Podatki:", updatedRecept); // Debugging

    fetch(`http://localhost:8080/recepti/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedRecept)
    })
    .then(response => {
        if (response.ok) {
            alert('Recept uspešno posodobljen!');
            fetchRecepti();
            document.getElementById('update-section').style.display = 'none';
        } else {
            return response.text().then(err => { throw new Error(err); }); // Preberi napako iz strežnika
        }
    })
    .catch((error) => {
        console.error('Napaka pri posodabljanju recepta:', error);
        alert('Napaka pri posodabljanju recepta: ' + error.message);
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
