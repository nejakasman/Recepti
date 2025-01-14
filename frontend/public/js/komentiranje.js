document.addEventListener("DOMContentLoaded", () => {
  const komentarjiLista = document.getElementById("komentarji-lista");
  const dodajKomentarForm = document.getElementById("dodaj-komentar-form");
  const receptId = localStorage.getItem("podrobni_recept");

  // Pridobi komentarje za določen recept
  async function pridobiKomentarje() {
    try {
      const response = await fetch(
        `http://localhost:8080/api/komentarji?receptId=${receptId}`
      );
      if (!response.ok) throw new Error("Napaka pri pridobivanju komentarjev.");

      const komentarji = await response.json();
      komentarjiLista.innerHTML = "";

      komentarji.forEach((komentar) => {
        const komentarEl = document.createElement("div");
        komentarEl.classList.add("komentar");
        komentarEl.innerHTML = `
            <p><strong>${komentar.uporabnik.ime}</strong>: ${
          komentar.komentar
        }</p>
            <small>${new Date(komentar.datumObjave).toLocaleString()}</small>
          `;
        komentarjiLista.appendChild(komentarEl);
      });
    } catch (error) {
      console.error("Napaka:", error.message);
    }
  }

  // Dodaj komentar
  dodajKomentarForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const komentarBesedilo = document.getElementById("komentar-vnos").value;

    try {
      const response = await fetch(
        "http://localhost:8080/api/komentarji/dodaj",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            receptId: receptId,
            komentar: komentarBesedilo,
          }),
        }
      );

      if (!response.ok) {
        const errorMsg = await response.text();
        throw new Error(errorMsg);
      }

      const novKomentar = await response.json();
      document.getElementById("komentar-vnos").value = "";
      pridobiKomentarje(); // Osveži komentarje
    } catch (error) {
      console.error("Napaka pri dodajanju komentarja:", error.message);
    }
  });

  // Pridobi komentarje ob nalaganju strani
  pridobiKomentarje();
});
