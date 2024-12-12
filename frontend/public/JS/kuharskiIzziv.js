const osnovniUrl = "http://localhost:8080/kuharski-izziv";

//pridobivanje vseh kuharskih izzivov
async function getIzzivi() {
  try {
    const response = await fetch(osnovniUrl);
    if (response.ok) {
      const izzivi = await response.json();
      displayIzzivi(izzivi);
    } else {
      console.error("Napaka pri pridobivanju izzivov");
    }
  } catch (error) {
    console.error("Napaka pri povezavi s strežnikom:", error);
  }
}

// prikaz kuharskih izzivov na strani
function displayIzzivi(izzivi) {
  const izziviList = document.getElementById("izziviList");
  izziviList.innerHTML = "";

  izzivi.forEach((izziv) => {
    const li = document.createElement("li");
    li.textContent = `${izziv.naziv}: ${izziv.opis} (Trajanje do: ${izziv.trajanjeDo})`;

    const editButton = document.createElement("button");
    editButton.textContent = "Uredi";
    editButton.onclick = () => showEditForm(izziv);

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Izbriši";
    deleteButton.onclick = () => deleteIzziv(izziv.id);

    li.appendChild(editButton);
    li.appendChild(deleteButton);

    izziviList.appendChild(li);
  });
}

// brisanje kuharskega izziva
async function deleteIzziv(id) {
  try {
    const response = await fetch(`${osnovniUrl}/${id}`, {
      method: "DELETE",
    });

    if (response.ok) {
      alert("Izziv uspešno izbrisan");
      getIzzivi(); // Ponovno naloži seznam
    } else {
      const result = await response.text();
      alert(result);
    }
  } catch (error) {
    console.error("Napaka pri brisanju izziva:", error);
  }
}

//prikaz obrazca za urejanje kuharskega izziva
function showEditForm(izziv) {
  document.getElementById("urediIzziv").style.display = "block";
  document.getElementById("izzivId").value = izziv.id;
  document.getElementById("novoIme").value = izziv.naziv;
  document.getElementById("novoOpis").value = izziv.opis;
  document.getElementById("novoTrajanjeDo").value = izziv.trajanjeDo; // datuma od ne bomo spreminjali
}

// posodobitev kuharskega izziva
async function updateIzziv(event) {
  event.preventDefault();

  const id = document.getElementById("izzivId").value;
  const novoIme = document.getElementById("novoIme").value;
  const novoOpis = document.getElementById("novoOpis").value;
  const novoTrajanjeDo = document.getElementById("novoTrajanjeDo").value;

  const podatki = {
    naziv: novoIme,
    opis: novoOpis,
    trajanjeDo: novoTrajanjeDo,
  };

  try {
    const response = await fetch(`${osnovniUrl}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(podatki),
    });

    if (response.ok) {
      alert("Izziv uspešno posodobljen");
      getIzzivi();
      document.getElementById("urediIzziv").style.display = "none";
    } else {
      const result = await response.text();
      alert(result);
    }
  } catch (error) {
    console.error("Napaka pri posodabljanju izziva:", error);
  }
}

// dodajanje kuharskega izziva
async function addIzziv(event) {
  event.preventDefault();

  const naziv = document.getElementById("izzivIme").value;
  const opis = document.getElementById("izzivOpis").value;
  const trajanjeDo = document.getElementById("izzivTrajanjeDo").value;

  const podatki = { naziv, opis, trajanjeDo };

  try {
    const response = await fetch(osnovniUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(podatki),
    });

    if (response.ok) {
      alert("Izziv uspešno dodan");
      document.getElementById("dodajIzziv").reset(); // Počisti obrazec
      getIzzivi(); // Ponovno naloži seznam
    } else {
      const result = await response.text();
      alert(result);
    }
  } catch (error) {
    console.error("Napaka pri dodajanju izziva:", error);
  }
}

document.getElementById("dodajIzziv").addEventListener("submit", addIzziv);
document.getElementById("urediIzziv").addEventListener("submit", updateIzziv);

getIzzivi();
