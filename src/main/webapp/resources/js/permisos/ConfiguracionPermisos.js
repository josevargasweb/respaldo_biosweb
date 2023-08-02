$(document).ready(function () {
    $(".ocultar").hide();
    listarCargos();
});

function listarCargos(){
    $.ajax({
       type: "get",
       url: getctx + "/api/permisos/cargos/list",
       datatype: "json",
       success: function (json){
           $("#tbodyCargos").empty();
           let cont = 0;
           json.dato.forEach(function (cargo){
               cont++;
               $("#tbodyCargos").append('<tr class="pointer" onclick="seleccionarCargo(this)" >\
                                            <th class="row mx-auto">' + cont + '</th>\
                                            <td>\
                                                <span class="txtCargo">'+ cargo.ldvcuDescripcion +'</span>\
                                                <input type="text" class="editCargo" value="'+ cargo.ldvcuDescripcion +'" onblur="editarCargo(this)" style="display:none;" />\
                                            </td>\
                                            <td>\
                                                <button class="btn" onclick="habilitarEdicionCargo(this)"><i class="far fa-edit"></i></button>\
                                                <button class="btn" onclick="inhabilitarCargo(this)"><i class="far fa-trash-alt"></i></button>\
                                            <td class="idCargo" style="display:none">' + cargo.ldvcuIdcargo + "</td></tr>");
           });
       },
       error: function (msg) {
            handlerMessageError(msg);
       }
    });
}

function seleccionarCargo(a){
    var $row = $(a).closest("tr"); // Find the row
    let idCargo = $row.find(".idCargo").text();
    if (idCargo!==null){
        $("#btnGuardar").show();
        $("#btnCancelar").show();
    }
    $("#hIdCargo").val(idCargo);
    let txtCargo = $row.find(".txtCargo").text();
    $("#cargoLbl").text(txtCargo);
    resetearCheckboxes();
    $.ajax({
        type: "get",
        url: getctx + "/api/accesos/cargo/"+idCargo,
        datatype: "json",
        async: false,
        success: function (json){
            $("#listaModulosCargo").empty();
            let cont = 0;
            json.dato.listItems.forEach(function (modulo){
                const str = modulo.cbmnombremodulo.toString().toLowerCase();
                const nomModulo = str.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
                let checked = modulo.tieneAcceso === "S" ? "checked" : "";
                $("#listaModulosCargo")
                        .append('<li class="listNvl1" style="list-style:none;">\n\
                                    <input id="item_'+modulo.cbmidbioslismodulo+'" name="item-'+modulo.cbmidbioslismodulo+'" class="checkModulo checkNvl1" value="'+modulo.cbmidbioslismodulo+'" type="checkbox" '+checked+'>\n\
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
                                .append('<li class="listNvl2" style="list-style:none;">\n\
                                    <input class="checkModulo checkNvl2" id="item_'+modulo2.cbmidbioslismodulo+'" value="'+modulo2.cbmidbioslismodulo+'" type="checkbox" '+checked_2+'>\n\
                                    <label class="cursor-pointer" for="item_'+modulo2.cbmidbioslismodulo+'">'+nomModulo2+'\
                                    </label>'+(modulo2.subitems!==null ? '<ul id="'+subitem+'"></ul>' : '')+'\n\
                                </li>');
                        if (modulo2.subitems!==null){
                            modulo2.subitems.forEach(modulo3=>{
                                const str3 = modulo3.cbmnombremodulo.toString().toLowerCase();
                                const nomModulo3 = str3.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
                                let checked_3 = modulo3.tieneAcceso === "S" ? "checked" : "";
                                $("#"+subitem).
                                        append('<li class="listNvl3" style="list-style:none;">\n\
                                                    <input class="checkModulo checkNvl3" id="item_'+modulo3.cbmidbioslismodulo+'" value="'+modulo3.cbmidbioslismodulo+'" type="checkbox" '+checked_3+'>\n\
                                                    <label class="cursor-pointer" for="item_'+modulo3.cbmidbioslismodulo+'">'+nomModulo3+'</label>\n\
                                                </li>');
                            });
                        }
                    });
                }

            });
            
        },
        error: function (msg) {
            handlerMessageError(msg);
        }
    });
    
    /*
     * Esto valida que se checkeen los items hijos, y también si es que se marca una opción level 2 o 3, se marque el item padre
     */ 
    $(".checkNvl1").on( "click", function(e) {
        $(this).parent().find('input:checkbox').not(this).prop('checked', this.checked);
    });
    
    $(".checkNvl2").on( "click", function(e) {
        $(this).parent().find('input:checkbox').not(this).prop('checked', this.checked);
        $(this).parents('.listNvl1').find('.checkNvl1:input:checkbox').prop('checked', true);
        //se descheckea el item padre si ninguno de sus items hijos están checkeados
        if (!$(this).parent().siblings().find('input:checkbox').is(':checked') && !$(this).is(':checked')){
            $(this).parents('.listNvl1').find('.checkNvl1:input:checkbox').prop('checked', false);
        }
    });
    
    $(".checkNvl3").on( "click", function(e) {
        $(this).parents('.listNvl2').find('.checkNvl2:input:checkbox').prop('checked', true);
        $(this).parents('.listNvl1').find('.checkNvl1:input:checkbox').prop('checked', true);
        //se descheckean los items padres si ninguno de sus items hijos están checkeados
        if (!$(this).parent().siblings().find('input:checkbox').is(':checked') && !$(this).is(':checked')){
            $(this).parents('.listNvl2').find('.checkNvl2:input:checkbox').prop('checked', false);
            $(this).parents('.listNvl1').find('.checkNvl1:input:checkbox').prop('checked', false);
        }
    });
}

function resetearCheckboxes(){
    for (let i=1; i<=14; i++) {
        $("#chkModulo"+i).prop("checked",false);
    }
}

function guardarPermisos(){
    let idCargo = $("#hIdCargo").val();
    //console.log(idCargo)
    let arrayPermisos = [];
    $("input:checkbox:checked").each(function(){
        let idModulo = this.value;
        //let idModulo = checkId.replace("item",""); 
        arrayPermisos.push({
            "id": {
                "ccmIdcargo": idCargo,
                "ccmIdbioslismodulo": idModulo
            },
            "ldvCargosusuarios": {
                "ldvcuIdcargo": idCargo
            }
        });
    });    
    //console.log(JSON.stringify(arrayPermisos))
    
    $.ajax({
        method: 'post',
        dataType: 'json',
        url: getctx + "/api/permisos/list/cargos/modulos/save/"+idCargo,
        data: JSON.stringify(arrayPermisos),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (data, textStatus, jqXHR) {
            if (data.status !== 200) {
                handlerMessageError(data.message);
              } else {
                handlerMessageOk('Actualizado correctamente.');
                $("#btnGuardar").hide();
                $("#btnCancelar").hide();
                $("#listaModulosCargo").html("");
                $("#cargoLbl").text("Seleccionar")
              }
        },
        error: function(jqxhr, textStatus, error) {
            let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
            handlerMessageError(err);
        }
    });
    
}

function agregarCargo(){
    let cargo = $("#txtNuevoCargo").val().toUpperCase();
    if (cargo !== ""){
        $.ajax({
           method: 'post',
           url: getctx + "/api/cargo/save/"+cargo,
           success: function (data, textStatus, jqXHR) {
                if (data.status !== 200) {
                    handlerMessageError(data.message);
                } else {
                    handlerMessageOk('Actualizado correctamente.');
                }
                $("#txtNuevoCargo").val("");
                listarCargos();
            },
            error: function(jqxhr, textStatus, error) {
                let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
                handlerMessageError(err);
            }
        });
    } else {
        handlerMessageError("El campo está vacío");
    }
}

function habilitarEdicionCargo(a){
    var $row = $(a).closest("tr"); // Find the row
    $row.find(".txtCargo").hide();
    $row.find(".editCargo").show();
}

function editarCargo(a){
    var $row = $(a).closest("tr"); // Find the row
    let idCargo = $row.find(".idCargo").text();
    let txtCargo = $row.find(".txtCargo").text();
    let editCargo = $row.find(".editCargo").val().toUpperCase();
    if (editCargo !== ""){
        if (txtCargo !== editCargo){
            //console.log("Se está modificando cargo con id " + idCargo + ": "+editCargo);
            $.ajax({
                method: 'put',
                url: getctx + '/api/cargo/update/' + idCargo + "/" + editCargo,
                success: function (data, textStatus, jqXHR) {
                    if (data.status !== 200) {
                        handlerMessageError(data.message);
                    } else {
                        handlerMessageOk('Actualizado correctamente.');
                    }
                    listarCargos();
                },
                error: function(jqxhr, textStatus, error) {
                    let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
                    handlerMessageError(err);
                }
            });
        }   
    } else {
        handlerMessageError("El campo está vacío");
    }
    $row.find(".txtCargo").show();
    $row.find(".editCargo").hide();
}

function inhabilitarCargo(a){
    var $row = $(a).closest("tr"); // Find the row
    let idCargo = $row.find(".idCargo").text();
    $.ajax({
        type: "delete",
        url: getctx + "/api/cargo/delete/" + idCargo,
        success: function() {
            handlerMessageOk('Eliminado correctamente.');
        },
        error: function(jqxhr, textStatus, error) {
            let err = 'Status:' + jqxhr.status + ' ' + textStatus + ", " + error;
            handlerMessageError(err);
        }
    });
}


$("#btnCancelar").click(function() {
    $("#btnGuardar").hide();
    $("#btnCancelar").hide();
    $("#listaModulosCargo").html("");
    $("#cargoLbl").text("Seleccionar")
});
    