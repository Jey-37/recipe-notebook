const ingredientMap = loadIngredients();

const ingredSearchField = document.getElementById("ingredient-search");
const dropdownContent = document.getElementsByClassName("dropdown-content")[0]
const resList = document.getElementsByClassName("dropdown-results-list")[0];

ingredSearchField.addEventListener("input", showDropdownSearchList);
ingredSearchField.addEventListener("focusin", () => {
    dropdownContent.style.display = "block";
});
ingredSearchField.addEventListener("focusout", () => {
    dropdownContent.style.display = "none";
});

resList.addEventListener("click", addIngredient);

const rmvIngButtons = document.getElementsByClassName("remove-ingredient-btn");
for (let i = 0; i < rmvIngButtons.length; i++) {
    rmvIngButtons.item(i).addEventListener("click", removeIngredientRow);
}

const rmvStageButtons = document.getElementsByClassName("remove-stage-btn");
for (let i = 0; i < rmvStageButtons.length; i++) {
    rmvStageButtons.item(i).addEventListener("click", removeStage);
}

function loadIngredients() {
    let ingMap = new Map();
    fetch("http://localhost:8080/data/ingredients")
        .then(response => response.json())
        .then(ingredientsData => ingredientsData.forEach(i => ingMap.set(i.name, i)));
    return ingMap;
}

function showDropdownSearchList() {
    resList.innerHTML = "";

    if (ingredSearchField.value.length === 0) {
        dropdownContent.style.display = "none";
        return;
    }

    let filteredIngredients = filterIngredients(ingredSearchField.value);

    if (filteredIngredients.length === 0) {
        dropdownContent.style.display = "none";
        return;
    }

    for (let i of filteredIngredients) {
        let li = document.createElement("li");
        li.innerText = i.name;
        resList.appendChild(li);
    }

    dropdownContent.style.display = "block";
}

function filterIngredients(searchQuery) {
    let res = [];
    searchQuery = searchQuery.toLowerCase();

    for (let [iName, ing] of ingredientMap) {
        iName = iName.toLowerCase();
        if (iName.startsWith(searchQuery) || iName.includes(" "+searchQuery)) {
            res.push(ing);
        }
    }

    return res;
}

function addIngredient(evt) {
    const ingredient = ingredientMap.get(evt.target.innerText);
    //createIngredientTableRow(ingredient);
}

function createIngredientTableRow(ingredient) {
    const ingredientTableRow = document.createElement("tr");

    const ingNameCell = document.createElement("td");
    ingNameCell.innerText = ingredient.name;

    ingredientTableRow.append(ingNameCell);
}

function removeIngredientRow(evt) {
    evt.stopPropagation();
    evt.target.parentNode.parentElement.remove();
}

function removeStage(evt) {
    evt.stopPropagation();
    evt.target.parentNode.parentElement.remove();
}
