var dualListbox = null;

function cargarListBox(lista){
    let options = [];
    $.ajax({
        type: "GET",
        url: getctx + "/api/dominio/cfgexamen/list",
        async: false,
        datatype: "json",
        success: function(data){
            for (let examen of data) {
                let seleccionar = false;
                if (lista !== null){
                    for (let l of lista){
                        if (l.examenId === examen.ceIdexamen){
                            seleccionar = true
                            break;
                        }
                    }
                }
                options.push({
                    value: examen.ceIdexamen,
                    text: +examen.ceCodigoexamen+'|'+examen.ceAbreviado+' '+examen.ceDescripcion,
                    selected: seleccionar
                });
            }
        },
        error: function (msg) {
           console.log(msg);
        } 
    });
    
    
    dualListbox = new DualListbox('#listbox_examenes',{
        addEvent: function(value) {
            console.log(value);
        },
        removeEvent: function(value) {
            console.log(value);
        },
        searchPlaceholder: 'Buscar',
        availableTitle: 'Exámenes',
        selectedTitle: 'Exámenes seleccionados',
        addButtonText: '<i class="flaticon2-next"></i>',
        removeButtonText: '<i class="flaticon2-back"></i>',
        options: options
    });
}

/*
 * Esto elimina el listbox actual (para que no se duplique el listbox) y lo hace de nuevo al seleccionar algun panel ya existente
 * Intenté de mil formas de hacer que quedaran seleccionados los examenes correspondientes al panel
 * Hasta que decidí hacer que se volviera a cargar todo y pasar como parámetro la lista de examenes del panel 
 * Al cargar la página el parámetro lista es null
 */

function recargarDatos(lista){
    $(".dual-listbox").empty();
    cargarListBox(lista);
}