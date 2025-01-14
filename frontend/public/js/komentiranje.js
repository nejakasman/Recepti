// Osnovna URL pot API-ja
const API_BASE_URL = "http://localhost:8080/api/komentarji";

// ID recepta za prikaz
const receptId = localStorage.getItem("podrobni_recept");
console.log("Recept ID:", receptId); // Preveri, ali je ID pravilno shranjen

// Funkcija za nalaganje komentarjev
async function loadComments(receptId) {
  const commentsSection = document.getElementById("comments-section");
  commentsSection.innerHTML = "<p>Nalaganje komentarjev...</p>";

  try {
    const response = await fetch(
      `${API_BASE_URL}/komentarjiZaRecept/${receptId}`
    );
    if (!response.ok) {
      throw new Error("Napaka pri nalaganju komentarjev.");
    }

    const comments = await response.json();
    commentsSection.innerHTML = comments.length
      ? comments.map(commentToHTML).join("")
      : "<p>Ni komentarjev za ta recept.</p>";
  } catch (error) {
    commentsSection.innerHTML = `<p>${error.message}</p>`;
  }
}

// Funkcija za pretvorbo komentarja v HTML
function commentToHTML(comment) {
  return `
      <div class="comment">
        <p><strong>${new Date(
          comment.datumObjave
        ).toLocaleString()}</strong></p>
        <p>${comment.komentarBesedilo}</p>
      </div>
    `;
}

// Komponenta za dodajanje novega komentarja
async function addComment(event) {
  event.preventDefault(); // Preprečimo privzeto obnašanje obrazca

  const commentText = document.getElementById("comment-text").value.trim();
  console.log(commentText);
  if (!commentText) {
    alert("Komentar ne sme biti prazen!");
    return;
  }

  try {
    const response = await fetch(`${API_BASE_URL}/dodajKomentar`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        receptId: receptId,
        komentarBesedilo: commentText,
      }),
    });

    if (!response.ok) {
      throw new Error("Napaka pri dodajanju komentarja.");
    }

    document.getElementById("comment-text").value = ""; // Počistimo obrazec
    loadComments(receptId); // Osvežimo komentarje
  } catch (error) {
    alert(error.message);
  }
}

// Dodamo poslušalce dogodkov
document.addEventListener("DOMContentLoaded", function () {
  document
    //.getElementById("add-comment-form")
    .addEventListener("submit", addComment);
});

// Naložimo komentarje ob nalaganju strani
loadComments();
