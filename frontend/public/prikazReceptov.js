// const baseUrl = "http://localhost:3306/api";

// let selectedChallengeId = null;

// // prikaz vseh receptov
// function fetchChallenges() {
//   fetch(`${baseUrl}/challenges`)
//     .then((response) => response.json())
//     .then((challenges) => {
//       const challengeList = document.getElementById("challenge-list");
//       challengeList.innerHTML = "";
//       challenges.forEach((challenge) => {
//         const li = document.createElement("li");
//         li.textContent = `${challenge.naziv} - ${challenge.opis}`;
//         const viewBtn = document.createElement("button");
//         viewBtn.textContent = "View Recipes";
//         viewBtn.onclick = () => viewChallengeRecipes(challenge.id);
//         li.appendChild(viewBtn);

//         const addRecipeBtn = document.createElement("button");
//         addRecipeBtn.textContent = "Add Recipe";
//         addRecipeBtn.onclick = () => showAddRecipeForm(challenge.id);
//         li.appendChild(addRecipeBtn);

//         challengeList.appendChild(li);
//       });
//     })
//     .catch((error) => console.error("Error fetching challenges:", error));
// }

// // prikaz receptov za dolocen izziv
// function viewChallengeRecipes(challengeId) {
//   selectedChallengeId = challengeId;
//   fetch(`${baseUrl}/challenges/${challengeId}/recipes`)
//     .then((response) => response.json())
//     .then((recipes) => {
//       const recipeList = document.getElementById("recipe-list");
//       recipeList.innerHTML = "";
//       recipes.forEach((recipe) => {
//         const li = document.createElement("li");
//         li.textContent = `${recipe.name} - ${recipe.description}`;

//         const deleteBtn = document.createElement("button");
//         deleteBtn.textContent = "Delete";
//         deleteBtn.onclick = () => deleteRecipe(recipe.id);
//         li.appendChild(deleteBtn);

//         recipeList.appendChild(li);
//       });
//       document.getElementById("challenge-recipes-section").style.display =
//         "block";
//     })
//     .catch((error) => console.error("Error fetching recipes:", error));
// }

// // forma za dodajanja recepta na izziv
// function showAddRecipeForm(challengeId) {
//   selectedChallengeId = challengeId;
//   fetchUserRecipes(); // Fetch recepte prijavljenega uporabnika
//   document.getElementById("add-recipe-section").style.display = "block";
// }

// // Prekini dodajanje recepta
// function cancelAddRecipe() {
//   document.getElementById("add-recipe-section").style.display = "none";
// }

// // fetcha uporabnikove recepte da nafilla izbiro za izziv
// function fetchUserRecipes() {
//   fetch(`${baseUrl}/recipes/user`) // preveri endpoint za fetchanje receptov prijavljenega uporabnika
//     .then((response) => response.json())
//     .then((recipes) => {
//       const recipeSelect = document.getElementById("recipe-select");
//       recipeSelect.innerHTML = "";

//       // default "izberi recept" opcija
//       const defaultOption = document.createElement("option");
//       defaultOption.value = "";
//       defaultOption.textContent = "Select a Recipe";
//       recipeSelect.appendChild(defaultOption);

//       // nafilla dropdown za izbiro recepta za izziv
//       recipes.forEach((recipe) => {
//         const option = document.createElement("option");
//         option.value = recipe.id;
//         option.textContent = `${recipe.name} - ${recipe.description}`;
//         recipeSelect.appendChild(option);
//       });
//     })
//     .catch((error) => console.error("Error fetching user recipes:", error));
// }

// // dodajanje obstojecega recepta na izziv
// function addRecipeToChallenge() {
//   const recipeId = document.getElementById("recipe-select").value;

//   if (!recipeId) {
//     alert("Prosimo, izberite recept.");
//     return;
//   }

//   fetch(`${baseUrl}/challenges/${selectedChallengeId}/add-recipe/${recipeId}`, {
//     method: "POST",
//   })
//     .then((response) => response.json())
//     .then(() => {
//       alert("Uspešno ste dodali recept na izziv!");
//       fetchChallenges(); // posodobi challenge list
//       document.getElementById("add-recipe-section").style.display = "none";
//     })
//     .catch((error) =>
//       console.error("Napaka pri dodajanju recepta na izziv:", error)
//     );
// }

// // zbrisi recept iz izziva
// function deleteRecipe(recipeId) {
//   fetch(`${baseUrl}/recipes/${recipeId}`, {
//     method: "DELETE",
//   })
//     .then(() => {
//       alert("Uspešno odstranjen recept!");
//       fetchChallenges(); // osvezi challenge list
//     })
//     .catch((error) => console.error("Napaka pri brisanju recepta:", error));
// }

// // Initializacija strani z nalaganjem izzivov
// document.addEventListener("DOMContentLoaded", () => {
//   fetchChallenges();
// });
