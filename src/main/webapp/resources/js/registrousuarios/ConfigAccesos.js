//Esta función devuelve todos los items y marca los que el usuario (si es que existe) tiene disponibles
function generarAccesosUsuario(idUsr){
    $.ajax({
        type: "get",
        url: getctx + "/api/accesos/usuario/" + idUsr,
        datatype: "json",
        async: false,
        success: function (json) {
            $("#listAccesosUsuario").empty();
            let cont = 0;
            json.dato.forEach(modulo=>{
                const str = modulo.cbmnombremodulo.toString().toLowerCase();
                const nomModulo = str.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
                let checked = modulo.tieneAcceso === "S" ? "checked" : "";
                
                $("#listAccesosUsuario")
                        .append('<li class="listLvl1" style="list-style:none;">\n\
                                    <input id="item_'+modulo.cbmidbioslismodulo+'" name="item-'+modulo.cbmidbioslismodulo+'" class="checkModulo checkLvl1" value="'+modulo.cbmidbioslismodulo+'" type="checkbox" '+checked+'>\n\
                                    <label class="cursor-pointer" for="item_'+modulo.cbmidbioslismodulo+'">'+nomModulo+'</label>\n\
                                    <ul id="subitem-'+modulo.cbmprimernivel+'"></ul>\n\
                                </li>');
                if (modulo.subitems!==null){
                    modulo.subitems.forEach(modulo2=>{
                        cont++;
                        const str2 = modulo2.cbmnombremodulo.toString().toLowerCase();
                        const nomModulo2 = str2.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
                        let subitem = "subitem_" + cont;
                        let checked_2 = modulo2.tieneAcceso === "S" ? "checked" : "";
                        $("#subitem-"+modulo.cbmprimernivel)
                                .append('<li class="listLvl2" style="list-style:none;">\n\
                                    <input class="checkModulo checkLvl2" id="item_'+modulo2.cbmidbioslismodulo+'" value="'+modulo2.cbmidbioslismodulo+'" type="checkbox" '+checked_2+'>\n\
                                    <label class="cursor-pointer" for="item_'+modulo2.cbmidbioslismodulo+'">'+nomModulo2+'\
                                    </label>'+(modulo2.subitems!==null ? '<ul id="'+subitem+'"></ul>' : '')+'\n\
                                </li>');
                        
                        if (modulo2.subitems!==null){
                            modulo2.subitems.forEach(modulo3=>{
                                const str3 = modulo3.cbmnombremodulo.toString().toLowerCase();
                                const nomModulo3 = str3.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
                                let checked_3 = modulo3.tieneAcceso === "S" ? "checked" : "";
                                $("#"+subitem).
                                        append('<li class="listLvl3" style="list-style:none;">\n\
                                                    <input class="checkModulo checkLvl3" id="item_'+modulo3.cbmidbioslismodulo+'" value="'+modulo3.cbmidbioslismodulo+'" type="checkbox" '+checked_3+'>\n\
                                                    <label class="cursor-pointer" for="item_'+modulo3.cbmidbioslismodulo+'">'+nomModulo3+'</label>\n\
                                                </li>');
                            });
                        };
                    });
                }
            });
        }
    });
    
    /*
     * Esto valida que se checkeen los items hijos, y también si es que se marca una opción level 2 o 3, se marque el item padre
     */ 
    $(".checkLvl1").on( "click", function(e) {
        $(this).parent().find('input:checkbox').not(this).prop('checked', this.checked);
    });
    
    $(".checkLvl2").on( "click", function(e) {
        $(this).parent().find('input:checkbox').not(this).prop('checked', this.checked);
        $(this).parents('.listLvl1').find('.checkLvl1:input:checkbox').prop('checked', true);
        //se descheckea el item padre si ninguno de sus items hijos están checkeados
        if (!$(this).parent().siblings().find('input:checkbox').is(':checked') && !$(this).is(':checked')){
            $(this).parents('.listLvl1').find('.checkLvl1:input:checkbox').prop('checked', false);
        }
    });
    
    $(".checkLvl3").on( "click", function(e) {
        $(this).parents('.listLvl2').find('.checkLvl2:input:checkbox').prop('checked', true);
        $(this).parents('.listLvl1').find('.checkLvl1:input:checkbox').prop('checked', true);
        //se descheckean los items padres si ninguno de sus items hijos están checkeados
        if (!$(this).parent().siblings().find('input:checkbox').is(':checked') && !$(this).is(':checked')){
            $(this).parents('.listLvl2').find('.checkLvl2:input:checkbox').prop('checked', false);
            $(this).parents('.listLvl1').find('.checkLvl1:input:checkbox').prop('checked', false);
        }
    });
    
}

$("#selectCargo").change(function() {
    let idCargo = $(this).val();
    let idUser = $("#txtID").val();
    if (idUser>0){
        $("#confirmModal").modal();
        $("#confirmModal .btn-si").click(function(){
            cargarCheckboxesAccesosUsuario(idCargo)
            //se almacena el nuevo cargo en la variable
            cargoUsuario = idCargo;
            $('#confirmModal').modal('hide')
        });
        $("#confirmModal .btn-no").click(function(){
              //si no confirma, se devuelve al cargo guardado y no se efectuan cambios en los módulos
              $("#selectCargo").val(cargoUsuario).change();
              $('#confirmModal').modal('hide')
        });
    } else {
        cargarCheckboxesAccesosUsuario(idCargo);
    }
});

function cargarCheckboxesAccesosUsuario(idCargo){
    //borra todos los checkboxes checkeados previamente
    $("#listAccesosUsuario").find('input:checkbox').prop('checked', false);
    $.ajax({
        type: "get",
        url: getctx + "/api/permisos/cargo/"+idCargo,
        datatype: "json",
        success: function (json){
            if(json.dato.listItems !== null){
                json.dato.listItems.forEach(function (modulo){
                    $("#item_"+modulo.cbmidbioslismodulo).prop("checked", true);
                });
            }
        },
        error: function (msg) {
            handlerMessageError(msg);
        }
    });
}