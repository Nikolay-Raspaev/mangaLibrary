let calculateButton = document.getElementById("calculate-button");
let binaryButton = document.getElementById("binary-button");
let numberOneInput = document.getElementById("first");
let numberTwoInput = document.getElementById("second");
let operationType = document.getElementById("type");
let select = document.getElementById("operation");
let resultInput = document.getElementById("result");

calculateButton.onclick = function() {
    executeRequest(select.value);
};

binaryButton.onclick = function() {
    let int_result = parseInt(resultInput.value)
    executeBinaryRequest(int_result);
};

function executeRequest(address) {
    let first = numberOneInput.value;
    let second = numberTwoInput.value;
    let type = operationType.value;
    fetch(`http://localhost:8080/${address}?first=${first}&second=${second}&type=${type}`)
        .then(response => response.text())
        .then(res => resultInput.value = String(res) );
}

function executeBinaryRequest(result) {
    fetch(`http://localhost:8080/binary?result=${result}`)
        .then(response => response.text())
        .then(res => resultInput.value = res);
}