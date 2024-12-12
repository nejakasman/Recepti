document
  .getElementById("prijavaObrazec")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const uporabniskoIme = document
      .getElementById("prijavaUporabniskoIme")
      .value.trim();
    const geslo = document.getElementById("prijavaGeslo").value.trim();

    if (!uporabniskoIme || !geslo) {
      alert("Vsa polja so obvezna!");
      return;
    }

    const podatki = { uporabniskoIme, geslo };

    try {
      const odgovor = await fetch(`${osnovniUrl}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(podatki),
      });

      const rezultat = await odgovor.text();
      if (odgovor.ok) {
        alert(rezultat);
      } else {
        alert("Napaka pri prijavi: " + rezultat);
      }
    } catch (napaka) {
      console.error("Napaka pri prijavi:", napaka);
      alert("Napaka pri prijavi");
    }
  });
