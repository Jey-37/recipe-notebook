"use strict";

document.forms["recipe-form"].querySelector("input[type=\"submit\"]").addEventListener("click", validateForm);

const nameInput = document.getElementById("name");
const pnInput = document.getElementById("portionsNumber");

nameInput.addEventListener("input", () => nameInput.classList.remove("invalid"));
pnInput.addEventListener("input", () => pnInput.classList.remove("invalid"));

ingredientList.addEventListener("input", evt => {
    if (evt.target.classList.contains("qty-input")) {
        evt.target.classList.remove("invalid");
    }
});

stageList.addEventListener("input", evt => {
    if (evt.target.classList.contains("stage-input-area")) {
        evt.target.classList.remove("invalid");
    }
});

document.getElementById("add-stage-btn").addEventListener("click",
    () => document.querySelector("#stages-section .warn-msg").style.display = "none");

let tagCheckboxes = document.querySelectorAll("#tags-section input[type=\"checkbox\"]");
for (let chb of tagCheckboxes) {
    chb.addEventListener("click",
        () => document.querySelector("#tags-section .warn-msg").style.display = "none");
}


let formIsValid = true;

function validateForm(evt) {
    checkNameField();
    checkPortionsNumberField();
    checkNumberOfIngredients();
    checkIngredQuantityFields();
    checkNumberOfStages();
    checkFillingOfStages();
    checkTags();

    if (!formIsValid) {
        evt.preventDefault();
        formIsValid = true;
    }
}

function checkNameField() {
    if (!nameInput.validity.valid) {
        nameInput.classList.add("invalid");
        formIsValid = false;
        return false;
    }
    return true;
}

function checkPortionsNumberField() {
    if (!pnInput.validity.valid) {
        pnInput.classList.add("invalid");
        formIsValid = false;
        return false;
    }
    return true;
}

function checkNumberOfIngredients() {
    if (ingredientList.childElementCount === 0) {
        formIsValid = false;
        document.querySelector("#ingredients-section .warn-msg").style.display = "block";
        return false;
    }
    return true;
}

function checkIngredQuantityFields() {
    const ingredQuantityFields = ingredientList.getElementsByClassName("qty-input");
    let ingQtyFieldsAreValid = true;
    for (let iqf of ingredQuantityFields) {
        if (!iqf.validity.valid) {
            iqf.classList.add("invalid");
            formIsValid = false;
            ingQtyFieldsAreValid = false;
        }
    }
    return ingQtyFieldsAreValid;
}

function checkNumberOfStages() {
    if (stageList.childElementCount === 0) {
        formIsValid = false;
        document.querySelector("#stages-section .warn-msg").style.display = "block";
        return false;
    }
    return true;
}

function checkFillingOfStages() {
    const stageInputFields = document.getElementsByClassName("stage-input-area");
    let stagesAreValid = true;
    for (let sif of stageInputFields) {
        if (sif.value.trim().length === 0) {
            sif.classList.add("invalid");
            formIsValid = false;
            stagesAreValid = false;
        }
    }
    return stagesAreValid;
}

function checkTags() {
    const numberOfCheckedTagFields =
        document.querySelectorAll("#tags-section input[type=\"checkbox\"]:checked").length;

    if (numberOfCheckedTagFields === 0) {
        formIsValid = false;
        document.querySelector("#tags-section .warn-msg").style.display = "block";
        return false;
    }
    return true;
}
