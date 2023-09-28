"use strict";

const ingredientMap = loadIngredients();
const shownIngredients = [];

const ingredSearchField = document.getElementById("ingredient-search");
const dropdownContent = document.getElementsByClassName("dropdown-content")[0]
const resList = document.getElementsByClassName("dropdown-results-list")[0];
const ingredientTable = document.querySelector("#ingredients-table tbody");
const stageTable = document.querySelector("#stage-table tbody");

ingredSearchField.addEventListener("input", showDropdownSearchList);

let isResListWasMouseDown = false;
ingredSearchField.addEventListener("focusin", () => {
    dropdownContent.style.display = "block";
});
ingredSearchField.addEventListener("focusout", () => {
    if (!isResListWasMouseDown)
        dropdownContent.style.display = "none";
});

resList.addEventListener("mousedown", () => {
    isResListWasMouseDown = true;
});
document.addEventListener("mouseup", evt => {
    if (isResListWasMouseDown) {
        if (resList.contains(evt.target))
            addIngredient(evt);
        isResListWasMouseDown = false;
        ingredSearchField.focus();
    }
});

document.getElementById("add-stage-btn").addEventListener("click", addStage);


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
        if (!shownIngredients.includes(ing.id) && (iName.startsWith(searchQuery) || iName.includes(" "+searchQuery))) {
            res.push(ing);
        }
    }

    return res;
}

function addIngredient(evt) {
    const ingredient = ingredientMap.get(evt.target.innerText);

    createIngredientTableRow(ingredient);

    shownIngredients.push(ingredient.id);
    resList.innerHTML = "";
    dropdownContent.style.display = "none";
    ingredSearchField.value = "";
}

function createIngredientTableRow(ingredient) {
    const ingNumber = ingredientTable.childElementCount;

    let ingMeasureSelectOptions = [];
    for (let m of ingredient.type.measures) {
        ingMeasureSelectOptions.push(`<option value="${m.id}" selected="selected">${m.title}</option>`);
    }

    const ingredientRow = `<tr>
        <td>
            <input type="hidden" id="ingredients${ingNumber}.ingredient" 
                   name="ingredients[${ingNumber}].ingredient" value="${ingredient.id}">
        </td>
        <td class="ing-name-cell">${ingredient.name}</td>
        <td>
            <input id="ingredients${ingNumber}.quantity" name="ingredients[${ingNumber}].quantity" value="1.0"
                   class="number-input" type="text" size="6" maxlength="6" placeholder="Кількість" 
                   pattern=" *(?:[1-9]\\d*|(?:0|[1-9]\\d*)[\\.,]\\d) *">
        </td>
        <td>
            <select class="ing-measure-select" id="ingredients${ingNumber}.measure"
                    name="ingredients[${ingNumber}].measure">
                ${ingMeasureSelectOptions.join("\n")}
            </select>
        </td>
        <td>
            <input type="text" placeholder="Примітка" maxlength="20" id="ingredients${ingNumber}.note" 
                   name="ingredients[${ingNumber}].note">
        </td>
        <td>
            <button type="button" class="remove-ingredient-btn" tabindex="-1" 
                    onclick="removeIngredientRow(this)">❌</button>
        </td>
    </tr>`;

    ingredientTable.innerHTML += ingredientRow;
}

function removeIngredientRow(btn) {
    let remIngId = btn.parentNode.parentElement.querySelector("input[type=\"hidden\"]").value;
    shownIngredients.splice(shownIngredients.indexOf(remIngId), 1);

    btn.parentNode.parentElement.remove();

    const ingTableRows = ingredientTable.children;

    for (let i = 0; i < ingTableRows.length; i++) {
        const rowInputs = ingTableRows[i].getElementsByTagName("input");
        for (let input of rowInputs) {
            input.id = input.id.replace(/ingredients\d+/, "ingredients"+i);
            input.name = input.name.replace(/ingredients\[\d+]/, "ingredients["+i+"]");
        }
    }
}

function addStage() {
    const stageNumber = stageTable.childElementCount;

    const stageRow = `<tr>
        <td>
            <textarea class="stage-input-area" id="stages${stageNumber}" name="stages[${stageNumber}]"
                      placeholder="Введіть вказівки з приготування"></textarea>
        </td>
        <td>
            <button type="button" class="remove-stage-btn" tabindex="-1"
                    onclick="removeStage(this)">❌</button>
        </td>
    </tr>`;

    stageTable.innerHTML += stageRow;
}

function removeStage(btn) {
    btn.parentNode.parentElement.remove();

    const stageTableRows = stageTable.children;

    for (let i = 0; i < stageTableRows.length; i++) {
        const textarea = stageTableRows[i].getElementsByTagName("textarea")[0];
        textarea.id = textarea.id.replace(/stages\d+/, "stages"+i);
        textarea.name = textarea.name.replace(/stages\[\d+]/, "stages["+i+"]");
    }
}
