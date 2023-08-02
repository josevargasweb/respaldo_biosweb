function validaArrayElementosRepetidos(arreglo){
    const tempArray = arreglo.sort();
    for (let i=0; i<tempArray.length; i++){
        if (tempArray[i] === tempArray[i+1]){
            return true;
        }
    }
    return false;
}