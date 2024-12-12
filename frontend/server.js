const express = require("express");
const path = require("path");
const cors = require("cors");

const app = express();
const port = 3000; // Izberi katerikoli prost port (privzeto bo to 3000)

app.use(cors()); // Omogoči CORS, da frontend lahko komunicira z backendom

// Strežnik za statične datoteke (HTML, CSS, JS)
app.use(express.static(path.join(__dirname, "public")));

// API endpoint za pridobivanje receptov (komunicira z backendom)
const apiUrl = "http://localhost:8080/api/recepti";

// API za pridobivanje vseh receptov
app.get("/api/recepti", async (req, res) => {
  try {
    const response = await fetch(apiUrl + "/recepti");
    const data = await response.json();
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: "Napaka pri pridobivanju receptov" });
  }
});

// API za dodajanje recepta
app.post("/api/dodaj", express.json(), async (req, res) => {
  const recept = req.body;
  try {
    const response = await fetch(apiUrl + "/dodaj", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(recept),
    });
    const newRecept = await response.json();
    res.json(newRecept);
  } catch (error) {
    res.status(500).json({ error: "Napaka pri dodajanju recepta" });
  }
});

// Strežnik posluša na portu 3000
app.listen(port, () => {
  console.log(`Server je zagnan na http://localhost:${port}`);
});
