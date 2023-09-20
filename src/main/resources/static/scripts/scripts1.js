const ingredientMap = new Map();
const ingredSearchField = document.getElementById("ingredient-search");
const dropdownContent = document.getElementsByClassName("dropdown-content")[0]
const resList = document.getElementsByClassName("dropdown-results-list")[0];

ingredSearchField.addEventListener("input", seekIngredient);
ingredSearchField.addEventListener("focusin", () => {
    dropdownContent.style.display = "block";
});
ingredSearchField.addEventListener("focusout", () => {
    dropdownContent.style.display = "none";
});

resList.addEventListener("click", addIngredient);


function seekIngredient() {
    let searchQuery = ingredSearchField.value;

    if (searchQuery.length === 0) {
        resList.innerHTML = "";
        dropdownContent.style.display = "none";
        return;
    }

    fetch("http://localhost:8080/data/ingredients?query="+searchQuery)
        .then(response => response.json())
        .then(ingredientsData => {
            ingredientsData.forEach(i => ingredientMap.set(i.name, i));
            showDropdownSearchList(ingredientsData);
        });
}

function showDropdownSearchList(ingredients) {
    resList.innerHTML = "";

    if (ingredients.length === 0) {
        dropdownContent.style.display = "none";
        return;
    }

    for (let i of ingredients) {
        let li = document.createElement("li");
        li.innerText = i.name;
        resList.appendChild(li);
    }

    dropdownContent.style.display = "block";
}

function addIngredient(evt) {
    const ingredient = ingredientMap.get(evt.target.innerText);
    //createIngredientTableRow(ingredient);
}

function createIngredientTableRow(ingredient) {
    const ingredientTableRow = document.createElement("tr");

    const ingNameCell = document.createElement("td");
    ingNameCell.innerText = ingredient.name;
    //const ingName

    ingredientTableRow.append(ingNameCell);
}
