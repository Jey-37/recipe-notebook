"use strict";

const ingredientMap = loadIngredients();
const shownIngredients = [];

const ingredSearchField = document.getElementById("ingredient-search");
const dropdownContent = document.getElementsByClassName("dropdown-content")[0]
const resList = document.getElementsByClassName("dropdown-results-list")[0];
const ingredientList = document.getElementById("ingredients-list");
const stageList = document.getElementById("stages-list");

document.forms["recipe-form"].addEventListener("keypress", e => {
    if (e.keyCode === 13) {
        e.preventDefault();
    }
});

document["recipe-form"]["mainPhoto"].addEventListener("change", evt => {
    const [file] = evt.target.files;
    if (file) {
        const mainPhoto = document.getElementById("main-photo-img");
        mainPhoto.src = URL.createObjectURL(file);
        document.getElementsByClassName("photo-upload-icon")[0].style.visibility = "hidden";
    }
}, false);

const showDropdown = () => {dropdownContent.style.display = "block"};
const hideDropdown = () => {dropdownContent.style.display = "none"};

ingredSearchField.addEventListener("input", showIngredientList);
ingredSearchField.addEventListener("focusin", showDropdown);
ingredSearchField.addEventListener("focusout", hideDropdown);

resList.addEventListener("mousedown", () => {
    document.addEventListener("mouseup", docOnMouseUpFunc);
    ingredSearchField.removeEventListener("focusout", hideDropdown);
});

document.getElementById("add-stage-btn").addEventListener("click", addStage);

/*--------------- Drag and drop event listeners -----------------*/
ingredientList.addEventListener("dragstart", evt => {
    const target = evt.target.closest("li");
    if (target) {
        setTimeout(() => target.classList.add("dragging"), 0);
    }
});
ingredientList.addEventListener("dragend", evt => {
    const target = evt.target.closest("li");
    if (target) {
        target.classList.remove("dragging");
    }
});
ingredientList.addEventListener("mousedown", evt => {
    const target = evt.target.closest("input[type=\"text\"], button");
    if (target) {
        target.closest("li").draggable = false;
    }
});
ingredientList.addEventListener("mouseup", evt => {
    const target = evt.target.closest("input[type=\"text\"], button");
    if (target) {
        target.closest("li").draggable = true;
    }
});
ingredientList.addEventListener("mouseleave", evt => {
    const target = evt.target.closest("input[type=\"text\"], button");
    if (target) {
        target.closest("li").draggable = true;
    }
});
ingredientList.addEventListener("dragover", changeListOrder);
ingredientList.addEventListener("dragenter", e => e.preventDefault());
/*---------------------------------------------------------------*/


function loadIngredients() {
    let ingMap = new Map();
    fetch("http://localhost:8080/data/ingredients")
        .then(response => response.json())
        .then(ingredientsData => ingredientsData.forEach(i => ingMap.set(i.name, i)));
    return ingMap;
}

function showIngredientList() {
    resList.innerHTML = "";

    if (ingredSearchField.value.length === 0) {
        hideDropdown();
        return;
    }

    let filteredIngredients = filterIngredients(ingredSearchField.value);

    if (filteredIngredients.length === 0) {
        hideDropdown();
        return;
    }

    for (let i of filteredIngredients) {
        let li = document.createElement("li");
        li.innerText = i.name;
        resList.appendChild(li);
    }

    showDropdown();
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

    ingredientList.appendChild(createIngredientTableRow(ingredient));

    shownIngredients.push(ingredient.id);
    resList.innerHTML = "";
    hideDropdown();
    ingredSearchField.value = "";

    document.querySelector("#ingredients-section .warn-msg").style.display = "none";
    ingredientList.style.display = "block";
}

function createIngredientTableRow(ingredient) {
    const ingNumber = ingredientList.childElementCount;

    let ingMeasureSelectOptions = [];
    for (let m of ingredient.type.measures) {
        ingMeasureSelectOptions.push(`<option value="${m.id}" selected="selected">${m.title}</option>`);
    }

    const ingredientRow = document.createElement("li");
    ingredientRow.draggable = true;

    ingredientRow.innerHTML = `
        <div><i class="drag-indicator"></i></div>
        <div class="ing-id-cell">
            <input type="hidden" id="ingredients${ingNumber}.ingredient" 
                   name="ingredients[${ingNumber}].ingredient" value="${ingredient.id}">
        </div>
        <div class="ing-name-cell">${ingredient.name}</div>
        <div>
            <input id="ingredients${ingNumber}.quantity" name="ingredients[${ingNumber}].quantity" value="1"
                   class="qty-input" type="text" size="6" maxlength="6" placeholder="Кількість" 
                   pattern=" *(?:[1-9]\\d*|(?:0|[1-9]\\d*)[\\.,]\\d) *" autocomplete="off" required>
        </div>
        <div>
            <select class="ing-measure-select" id="ingredients${ingNumber}.measure"
                    name="ingredients[${ingNumber}].measure">
                ${ingMeasureSelectOptions.join("\n")}
            </select>
        </div>
        <div>
            <input type="text" placeholder="Примітка" maxlength="20" id="ingredients${ingNumber}.note" 
                   name="ingredients[${ingNumber}].note" autocomplete="off">
        </div>
        <div>
            <button type="button" class="remove-ingredient-btn" tabindex="-1" 
                    onclick="removeIngredientRow(this)">❌</button>
        </div>`;

    return ingredientRow;
}

function removeIngredientRow(btn) {
    let remIngId = parseInt(btn.parentNode.parentElement.querySelector(".ing-id-cell input").value);
    shownIngredients.splice(shownIngredients.indexOf(remIngId), 1);

    btn.parentNode.parentElement.remove();

    if (ingredientList.childElementCount === 0) {
        ingredientList.style.display = "none";
    }
    correctIngRowIndexes();
}

function correctIngRowIndexes() {
    const ingTableRows = ingredientList.children;

    for (let i = 0; i < ingTableRows.length; i++) {
        const rowInputs = ingTableRows[i].getElementsByTagName("input");
        for (let input of rowInputs) {
            input.id = input.id.replace(/ingredients\d+/, "ingredients"+i);
            input.name = input.name.replace(/ingredients\[\d+]/, "ingredients["+i+"]");
        }
    }
}

function addStage() {
    const stageNumber = stageList.childElementCount;

    const stageRow = document.createElement("li");
    stageRow.innerHTML = `
        <div>
            <textarea class="stage-input-area" id="stages${stageNumber}" name="stages[${stageNumber}]"
                      placeholder="Введіть вказівки до приготування"></textarea>
        </div>
        <div>
            <button type="button" class="remove-stage-btn" tabindex="-1"
                    onclick="removeStage(this)">❌</button>
        </div>`;

    stageList.appendChild(stageRow);
}

function removeStage(btn) {
    btn.parentNode.parentElement.remove();

    const stageListRows = stageList.children;

    for (let i = 0; i < stageListRows.length; i++) {
        const textarea = stageListRows[i].getElementsByTagName("textarea")[0];
        textarea.id = textarea.id.replace(/stages\d+/, "stages"+i);
        textarea.name = textarea.name.replace(/stages\[\d+]/, "stages["+i+"]");
    }
}

function docOnMouseUpFunc(evt) {
    if (resList.contains(evt.target))
        addIngredient(evt);
    ingredSearchField.focus();
    ingredSearchField.addEventListener("focusout", hideDropdown);
    document.removeEventListener("mouseup", docOnMouseUpFunc);
}

function changeListOrder(evt) {
    evt.preventDefault();

    const draggingRow = document.querySelector(".dragging");
    let siblings = [...ingredientList.querySelectorAll("li:not(.dragging)")];

    let nextSibling = siblings.find(sibling => evt.pageY <= sibling.offsetTop + sibling.offsetHeight / 2);
    ingredientList.insertBefore(draggingRow, nextSibling);

    correctIngRowIndexes();
}
