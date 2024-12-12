const osnovniUrl = "http://localhost:8080";

document
  .getElementById("registracijaObrazec")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const uporabniskoIme = document
      .getElementById("uporabniskoIme")
      .value.trim();
    const email = document.getElementById("email").value.trim();
    const geslo = document.getElementById("geslo").value.trim();

    if (!uporabniskoIme || !email || !geslo) {
      alert("Vsa polja so obvezna!");
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      alert("Neveljaven email naslov!");
      return;
    }

    const podatki = { uporabniskoIme, email, geslo };

    try {
      const odgovor = await fetch(`${osnovniUrl}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(podatki),
      });

      const rezultat = await odgovor.text();
      if (odgovor.ok) {
        alert(rezultat);
      } else {
        alert("Napaka pri registraciji: " + rezultat);
      }
    } catch (napaka) {
      console.error("Napaka pri registraciji:", napaka);
      alert("Napaka pri registraciji");
    }
  });
