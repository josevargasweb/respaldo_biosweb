/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function setLocalPaciente(rut) {
//    limpiarLocalStorage();
  localStorage.setItem('Rut',rut);
}


function setLocalPaciente2(rut) {
//    limpiarLocalStorage();
    setWithExpiry("rut2", rut, 100000);

}
function setLocalMedico(rutMec) {
//    limpiarLocalStorage();
    setWithExpiry("rutM", rutMec, 100000);

}
function editarPacienteLocal(rut) {
//    limpiarLocalStorage();
    setWithExpiry("rutEditPac", rut, 100000);

}
function limpiarLocalStorage() {
    localStorage.clear();
}

/*
 btnSet.addEventListener('click', () => {
 setWithExpiry('myKey', inputSet.value, 5000)
 })
 
 btnGet.addEventListener('click', () => {
 const value = getWithExpiry('myKey')
 valueDisplay.innerHTML = value
 })*/

function setWithExpiry(key, value, ttl) {
    const now = new Date();

    // `item` is an object which contains the original value
    // as well as the time when it's supposed to expire
    const item = {
        value: value,
        expiry: now.getTime() + ttl
    };
    localStorage.setItem(key, JSON.stringify(item));
}

function getWithExpiry(key) {
    const itemStr = localStorage.getItem(key);

    // if the item doesn't exist, return null
    if (!itemStr) {
        return null;
    }

    const item = JSON.parse(itemStr);
    const now = new Date();

    // compare the expiry time of the item with the current time
    if (now.getTime() > item.expiry) {
        // If the item is expired, delete the item from storage
        // and return null
        localStorage.removeItem(key);
        return null;
    }
    return item.value;
}