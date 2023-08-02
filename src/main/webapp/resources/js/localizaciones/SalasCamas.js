const idCentroSaludUsuario = $("#idCentroSalud").val();

$(document).ready(function () {
    $(".ocultar").hide();
    cargarCentrosSalud(centrosSalud());
    listarServicios();
});

let centrosSalud = function () {
    var jqXHR = $.ajax({
        async: false,
        url: getctx + "/api/instituciones/centrosdesalud/list",
        type: 'GET'
    });
    return jqXHR.responseJSON;
};

function cargarCentrosSalud(centrossalud){
    let options = ``;
    centrossalud.forEach((element) => {
        options += `<option value="${element.ccdsIdcentrodesalud}" ${idCentroSaludUsuario == element.ccdsIdcentrodesalud ? 'selected' : ''}>${element.ccdsDescripcion}</option>`;
    });
    $("#idCentroFiltro").html(options);
    listarServicios();
}

$("#idCentroFiltro").on('change', function () {
    listarServicios();
});

$("#checkBoxInactivos").click(function () {
    listarServicios();
});

function listarServicios(){
    let filtros = JSON.stringify({
        idCentro: $("#idCentroFiltro").val(),
        activo: $("#checkBoxInactivos").is(":checked") ? "N" : "S"
    });
    
    $.ajax({
        type: "post",
        data: filtros,
        url: getctx + "/api/localizaciones/servicios",
        headers: {
          'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
            if (data.status === 200){
                let dato = data.dato;
                $("#listaServicios").empty();
                $("#lblError").hide();
                if (dato.length > 0){
                    dato.forEach(function (servicio) {
                        let textColor = servicio.csActivo === "S" ? "text-blue" : "text-danger";
                        let content = `<div id="S${servicio.csIdservicio}" class="card mb-2">
                                        <div class="card-header border-15">
                                            <h2 class="mb-0">
                                                <button id="btn-${servicio.csIdservicio}" class="btn btn-lg text-left icon-accordion ${textColor}" type="button" data-toggle="collapse" data-target="#salasServicio${servicio.csIdservicio}" aria-expanded="true" aria-controls="salasServicio${servicio.csIdservicio}" onclick="getSalasServicio(${servicio.csIdservicio})">
                                                    ${servicio.csDescripcion}
                                                </button>
                                            </h2>
                                        </div>
                                        <div id="salasServicio${servicio.csIdservicio}" class="collapse" data-parent="#listaServicios"></div>
                                    </div>`;
                        $("#listaServicios").append(content);
                        
                    });
                    
                } else {
                    $("#lblError").show();
                }
                
            } else {
                handlerMessageError(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Error al listar servicios.");
            console.log(msg);
        }
    });
}

function getSalasServicio(idServicio){
    $(`#salasServicio${idServicio}`).empty();
    let contentSala = "";
    $.ajax({
        type: "get",
        url: getctx + "/api/localizaciones/salas/servicio/" + idServicio,
        datatype: "json",
        async:false,
        success: function (data) {
            if (data.status === 200){
                contentSala = "";
                let dato = data.dato;
                if (dato.length > 0){
                    dato.forEach(function (sala) {
                        contentSala += `<div class='card mb-2'>
                                            <div class='card-header border-15'>
                                                <h2 class="mb-0">
                                                    <button id="btn-${sala.css_IDSALASERVICIO}" class="btn btn-lg text-left text-blue icon-accordion" type="button" data-toggle="collapse" data-target="#camasSala${sala.css_IDSALASERVICIO}" aria-expanded="true" aria-controls="camasSala${sala.css_IDSALASERVICIO}" onclick='getCamasSala(${sala.css_IDSALASERVICIO})'>
                                                        ${sala.css_DESCRIPCION}
                                                    </button>
                                                    <button class="btn btn-sm btn-blue-primary font-weight-bold mr-2 pointer" data-toggle="modal" data-target="#modalSalas" onclick='habilitarEdicionSala(${JSON.stringify(sala)})'><i class="fas fa-edit"></i></button>
                                                </h2>
                                            </div>
                                            <div id="camasSala${sala.css_IDSALASERVICIO}" class="collapse" data-parent=""></div>
                                        </div>`;
                    });
                }
            } else {
                handlerMessageError(data.message);
            }
        },
        error: function (msg) {
            handlerMessageError("Error al listar salas.");
            console.log(msg);
        }
    });
    
    let contenido = `<div class="card-body">
                        <div class="accordion accordion-toggle-arrow" id="listaSalasServicio${idServicio}">
                            ${contentSala}
                        </div>
                        <a id="newSala" data-toggle="modal" data-target="#modalSalas" onclick="activarModalNuevaSala(${idServicio})" class="btn btn-lg btn-blue-primary font-weight-bold mt-2 ml-4 pointer"><i class="fas fa-plus"></i>Nueva Sala</a>
                    </div>`;
    
    $(`#salasServicio${idServicio}`).append(contenido);
}

function getCamasSala(idSala){
    $(`#camasSala${idSala}`).empty();
    let contentCamas = "";
    $.ajax({
        type: "get",
        url: getctx + "/api/localizaciones/camas/sala/" + idSala,
        datatype: "json",
        async: false,
        success: function (data) {
            if (data.status === 200){
                contentCamas = "";
                let dato = data.dato;
                if (dato.length > 0){
                    dato.forEach(function(cama){
                        contentCamas += `<div class='card mb-2'>
                                            <div class='card-header border-15'>
                                                <h6 class="mb-0 btn-block text-left pl-2">
                                                    ${cama.ccs_DESCRIPCION}
                                                    <button class="btn btn-sm btn-blue-primary font-weight-bold mr-2 pointer" data-toggle="modal" data-target="#modalCamas" onclick='habilitarEdicionCama(${JSON.stringify(cama)})'><i class="fas fa-edit"></i></button>
                                                </h6>
                                            </div>
                                        </div>`;
                    });
                }
            }
        },
        error: function (msg) {
            handlerMessageError("Error al listar camas.");
            console.log(msg);
        }
    });
    
    let contenido = `<div class="card-body">
                        <div class="accordion accordion-toggle-arrow" id="listaCamasSala${idSala}">
                            ${contentCamas}
                        </div>
                        <a id="newCama" data-toggle="modal" data-target="#modalCamas" onclick="activarModalNuevaCama(${idSala})" class="btn btn-lg btn-blue-primary font-weight-bold mt-2 ml-4 pointer"><i class="fas fa-plus"></i>Nueva Cama</a>
                    </div>`;
    
    $(`#camasSala${idSala}`).append(contenido);
}