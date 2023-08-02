/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let selectDerivadores = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/dominio/derivadores/list",
        type: 'GET',
        async: false,
    });
    return jqXHR.responseJSON;
};