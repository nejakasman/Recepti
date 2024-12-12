
function showReceptDetails(recept) {
    const detailsDiv = document.getElementById('recept-podrobnosti');
    if (!detailsDiv) {
        console.error('Manjka element z ID "recept-podrobnosti".');
        return;
    }

    detailsDiv.style.display = 'block'; 

    const shranjenePorcije = localStorage.getItem('porcije') || 1;

    localStorage.setItem("receptId", recept.id);

    detailsDiv.innerHTML = `
        <h3>${recept.ime}</h3>
        <label for="porcije">Število porcij:</label>
        <input type="number" id="porcije" value="${shranjenePorcije}" min="1" onchange="updatePorcije(${recept.id}, this.value)">
        <p id="sestavine"><strong>Sestavine:</strong> ${recept.sestavine.join(', ')}</p>
        <p><strong>Navodila:</strong> ${recept.navodila.join(', ')}</p>
        <p><strong>Opis:</strong> ${recept.opis || 'Ni opisa.'}</p>
    `;

    updatePorcije(recept.id, shranjenePorcije);
}


function updatePorcije(receptId, porcije) {
    localStorage.setItem("porcije", porcije); 

    fetch(`http://localhost:8080/recepti/${receptId}/sestavine?porcije=${porcije}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Napaka pri pridobivanju sestavin.');
            }
            return response.json();
        })
        .then(preracunaneSestavine => {
            const sestavineElement = document.querySelector("#sestavine");
            sestavineElement.innerHTML = `<strong>Sestavine:</strong> ${preracunaneSestavine.join(', ')}`;
        })
        .catch(error => {
            console.error("Napaka pri posodabljanju sestavin:", error);
        });
}

window.onload = function() {
    const receptId = localStorage.getItem("receptId");
    const porcije = localStorage.getItem("porcije");

    if (receptId && porcije) {
        fetch(`http://localhost:8080/recepti/${receptId}`)
            .then(response => response.json())
            .then(recept => {

                const detailsDiv = document.getElementById("recept-podrobnosti");
                detailsDiv.innerHTML = `
                    <h3>${recept.ime}</h3>
                    <label for="porcije">Število porcij:</label>
                    <input type="number" id="porcije" value="${porcije}" min="1" onchange="updatePorcije(${recept.id}, this.value)">
                    <p id="sestavine"><strong>Sestavine:</strong> ${recept.sestavine.join(', ')}</p>
                    <p><strong>Navodila:</strong> ${recept.navodila.join(', ')}</p>
                    <p><strong>Opis:</strong> ${recept.opis || 'Ni opisa.'}</p>
                `;
                
                updatePorcije(recept.id, porcije);
            })
            .catch(error => console.error("Napaka pri pridobivanju recepta:", error));
    }
}