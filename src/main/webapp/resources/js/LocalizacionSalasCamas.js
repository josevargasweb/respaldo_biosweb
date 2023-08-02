/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $(".ocultar").hide();
    listarSalasServicios(1,0);
});

$("#idCentroFiltro").on('change', function () {
    var idcentro = $("#idCentroFiltro").val();
    if ($("#checkBoxInactivos").is(":checked")) {
        listarSalasServicios(idcentro, 1);
    }else{
        listarSalasServicios(idcentro, 0);
    }
});

$("#checkBoxInactivos").click(function () {
    var idcentro = $("#idCentroFiltro").val();
    if ($("#checkBoxInactivos").is(":checked")) {
        listarSalasServicios(idcentro, 1);
    }else{
        listarSalasServicios(idcentro, 0);
    }
});

function listarSalasServicios(idCentro, swInactivos){
    $.ajax({
        type: "post",
        data: "filtro=1&idCentro="+idCentro+"&swInactivos="+swInactivos,
        url: "LocalizacionSalasCamas",
        datatype: "json",
        success: function (mensaje) {
            var dato = JSON.parse(mensaje);
            //console.log(JSON.stringify(dato));
            $("#listaServicios").empty();
            $("#lblErrorFiltro").hide();
            if (dato.servicio.length > 0) {
                dato.servicio.forEach(function (servicio) {
                    
                    let salasArray = servicio.salas;
                    let salasContent = "";
                    
                    for (let i in salasArray) {
                        //console.log (salasArray[i]);
                        
                        let camasArray = salasArray[i].camas;
                        let camasContent = "";
                        for (let j in camasArray){
                            let contentC = "<div class='card'>"
                                            + "<span class='col-3' id='camaDescripcion"+camasArray[j].idCama+"'>" + camasArray[j].descripcionCama + "</span>"
                                            + "&nbsp;<button class='btn btn-light-primary font-weight-bold col-3 mr-2 pointer' onclick='habilitarEdicionCama("+camasArray[j].idCama+")'><i class='fas fa-edit'></i></button>"
                                            + "<div id='editCama"+camasArray[j].idCama+"'></div>"
                                        + "</div>";
                            camasContent += contentC;
                        }
                        
                        let contentS = "<div class='card'>"
                                        + "<div class='card-header'>"
                                            + "<div class='card-title' data-toggle='collapse' data-target='#camasSala"+salasArray[i].idSala+"'>"
                                                + "<span id='salaDescripcion"+salasArray[i].idSala+"'>" + salasArray[i].descripcionSala + "</span>"
                                                + "&nbsp;<button class='btn btn-light-primary font-weight-bold mr-2 pointer' onclick='habilitarEdicionSala("+salasArray[i].idSala+")'><i class='fas fa-edit'></i></button>"
                                                + "<div id='editSala"+salasArray[i].idSala+"'></div>"
                                            + "</div>"
                                        + "</div>"
                                        + "<div id='camasSala"+salasArray[i].idSala+"' class='collapse' data-parent='#listaSalasServicio"+servicio.id+"'>"
                                            + "<div class='card-body' id='listaCamasSala"+salasArray[i].idSala+"'>"
                                                + camasContent
                                            + "</div>"
                                            + "<a id='newCama' onclick='agregarInputNuevaCama("+salasArray[i].idSala+")' class='btn btn-light-primary font-weight-bold mr-2 ml-4 pointer'><i class='fas fa-plus'></i>Nueva Cama</a>"
                                            + "<div id='formCamaSala"+salasArray[i].idSala+"' class='col-12' style='display:none'>"
                                                + "Código: <input id='formCodigoCamaS"+salasArray[i].idSala+"' name='' type='text' class='col-3 mt-5 mr-2' autocomplete='off' placeholder='' />"
                                                + "Descripción: <input id='formDescripcionCamaS"+salasArray[i].idSala+"' name='' type='text' class='col-3 mt-5 mr-2' autocomplete='off' placeholder='' />"
                                                + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='agregarNuevaCama("+salasArray[i].idSala+","+servicio.id+")'>Agregar</button>"
                                            + "</div>"
                                        + "</div>"
                                + "</div>";
                        salasContent += contentS;
                    }

                    let content = "<div class='card cardServicio"+servicio.id+"'>"
                                    + "<div class='card-header' >"
                                        + "<div class='card-title' data-toggle='collapse' data-target='#salasServicio"+servicio.id+"'>"
                                            + servicio.descripcion
                                        + "</div>"
                                    + "</div>"
                                    + "<div id='salasServicio"+servicio.id+"' class='collapse' data-parent='#listaServicios'>"
                                        + "<div class='card-body'>"
                                            + "<div class='accordion accordion-toggle-arrow' id='listaSalasServicio"+servicio.id+"'>"
                                                + salasContent
                                            + "</div>"
                                            + "<a id='newSala' onclick='agregarInputNuevaSala("+servicio.id+")' class='btn btn-light-primary font-weight-bold mr-2 ml-4 pointer'><i class='fas fa-plus'></i>Nueva Sala</a>"
                                            + "<div id='formSalaServicio"+servicio.id+"' class='col-12' style='display:none'>"
                                                + "Código: <input id='formCodigoSalaS"+servicio.id+"' name='' type='text' class='col-3 mt-5 mr-2' autocomplete='off' placeholder='' />"
                                                + "Descripción: <input id='formDescripcionSalaS"+servicio.id+"' name='' type='text' class='col-3 mt-5 mr-2' autocomplete='off' placeholder='' />"
                                                + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='agregarNuevaSala("+servicio.id+","+idCentro+")'>Agregar</button>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>";
                    $("#listaServicios").append(content);
                });
                $("#listaServicios").append("<a id='newServicio' href='./ConfiguracionServicios' class='btn btn-light-primary font-weight-bold mr-2 ml-4 pointer'><i class='fas fa-plus'></i>Nuevo Servicio</a>");
            } else {
                $("#lblErrorFiltro").show();
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

//funciones de sala

function agregarInputNuevaSala(idServicio){
    let clase="#formSalaServicio"+idServicio;
    $(clase).show();
}

function agregarNuevaSala(idServicio, idCentro){
    let idCodigo = "formCodigoSalaS"+idServicio;
    let codigoNuevaSala = document.getElementById(idCodigo).value;
    let idDescripcion = "formDescripcionSalaS"+idServicio;
    let descripcionNuevaSala = document.getElementById(idDescripcion).value;
    //let ipEquipo = document.getElementById(ipEquipo).value;
    
    //console.log("codigo: " + codigoNuevaSala + ", descripcion: " + descripcionNuevaSala);
    //console.log("ip: " + ipEquipo);
    
    if (codigoNuevaSala != "" && descripcionNuevaSala != ""){
        $.ajax({
            type: "post",
            data: "nuevaSala=1&idServicio="+idServicio+"&ipEquipo="+ipEquipo+"&codigoNuevaSala="+codigoNuevaSala+"&descripcionNuevaSala="+descripcionNuevaSala,
            url: "LocalizacionSalasCamas",
            //datatype: "json",
            success: function (mensaje) {
                //console.log("***RESPUESTA******"+mensaje);
                idSala = "";
                let content = "<div class='card'>"
                                + "<div class='card-header'>"
                                    + "<div class='card-title' data-toggle='collapse' data-target='#camasSala"+idSala+"'>"
                                        + descripcionNuevaSala
                                    + "</div>"
                                + "</div>"
                                + "<div id='camasSala"+idSala+"' class='collapse' data-parent='#listaSalasServicio"+idServicio+"'>"
                                    + "<div class='card-body'>"
                                        + "<a id='newCama' class='btn btn-light-primary font-weight-bold mr-2 ml-4 pointer'><i class='fas fa-plus'></i>Nueva Cama</a>"
                                    + "</div>"
                                + "</div>"
                        + "</div>";
                //listarSalasServicios(idCentro);
                $("#listaSalasServicio"+idServicio).append(content);
                //$("#newSalaServicio"+idServicio).html("<div class='alert alert-primary' role='alert'>Agregado correctamente</div>");
            }
        });
    
    } else {
        alert ("¡Campos requeridos!");
    }
}

function habilitarEdicionSala(idSala){
    let descripcionSala = document.getElementById("salaDescripcion"+idSala).innerText;
    //console.log(descripcionSala);
    //Para ocultar otros formularios de edición de salas
    $(".formEditSalas").hide();
    
    let content = "<div id='formSala"+idSala+"' class='col-12 formEditSalas'>"
                    + "Descripción: <input id='fDescripcionSala"+idSala+"' name='' type='text' class='col-6 mt-5 mr-2' autocomplete='off' placeholder='' value='"+descripcionSala+"' />"
                    + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='editarSala("+idSala+")'>Confirmar</button>"
                    + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='cancelarEdicion()'>Cancelar</button>"
                + "</div>";
        //console.log(content);
    $("#editSala"+idSala).append(content);
}

function cancelarEdicion(){
    $(".formEditSalas").hide();
}

function editarSala(idSala){
    let fDescripcionSala = document.getElementById("fDescripcionSala"+idSala).value;
    $.ajax({
        type: "post",
        data: "updateSala=1&idSala="+idSala+"&descripcionSala="+fDescripcionSala,
        url: "LocalizacionSalasCamas",
        success: function (mensaje) {
            $("#salaDescripcion"+idSala).html(fDescripcionSala);
            $(".formEditSalas").hide();
        }
    });
}

//funciones de cama

function agregarInputNuevaCama(idSala){
    let clase="#formCamaSala"+idSala;
    $(clase).show();
}

function agregarNuevaCama(idSala, idServicio){
    let idCodigo = "formCodigoCamaS"+idSala;
    let codigoNuevaCama = document.getElementById(idCodigo).value;
    let idDescripcion = "formDescripcionCamaS"+idSala;
    let descripcionNuevaCama = document.getElementById(idDescripcion).value;
    let ipEquipo = "";
    //let ipEquipo = document.getElementById(ipEquipo).value;
    
    //console.log("codigo: " + codigoNuevaCama + ", descripcion: " + descripcionNuevaCama);
    //console.log("ip: " + ipEquipo);
    
    if (codigoNuevaCama != "" && descripcionNuevaCama != ""){
        $.ajax({
            type: "post",
            data: "nuevaCama=1&idSala="+idSala+"&idServicio="+idServicio+"&ipEquipo="+ipEquipo+"&codigoNuevaCama="+codigoNuevaCama+"&descripcionNuevaCama="+descripcionNuevaCama,
            url: "LocalizacionSalasCamas",
            //datatype: "json",
            success: function (mensaje) {
                //console.log("***RESPUESTA******"+mensaje);
                idCama = "";
                let content = "<div class='card'>"
                                + descripcionNuevaCama
                            + "</div>";
                
                
                //listarSalasServicios(idCentro);
                $("#listaCamasSala"+idSala).append(content);
                //$("#newSalaServicio"+idServicio).html("<div class='alert alert-primary' role='alert'>Agregado correctamente</div>");
            }
        });
    
    } else {
        alert ("¡Campos requeridos!");
    }
}

function habilitarEdicionCama(idCama){
    let descripcionCama = document.getElementById("camaDescripcion"+idCama).innerText;
    //console.log(descripcionCama);
    //Para ocultar otros formularios de edición de salas
    $(".formEditCamas").hide();
    
    let content = "<div id='formCama"+idCama+"' class='col-12 formEditCamas'>"
                    + "Descripción: <input id='fDescripcionCama"+idCama+"' name='' type='text' class='col-6 mt-5 mr-2' autocomplete='off' placeholder='' value='"+descripcionCama+"' />"
                    + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='editarCama("+idCama+")'>Confirmar</button>"
                    + "<button class='btn btn-light-primary font-weight-bold mr-2 col-3 pointer' onclick='cancelarEdicionCama()'>Cancelar</button>"
                + "</div>";
        //console.log(content);
    $("#editCama"+idCama).append(content);
}

function cancelarEdicionCama(){
    $(".formEditCamas").hide();
}

function editarCama(idCama){
    let fDescripcionCama = document.getElementById("fDescripcionCama"+idCama).value;
    $.ajax({
        type: "post",
        data: "updateCama=1&idCama="+idCama+"&descripcionCama="+fDescripcionCama,
        url: "LocalizacionSalasCamas",
        success: function () {
            $("#camaDescripcion"+idCama).html(fDescripcionCama);
            $(".formEditCamas").hide();
        }
    });
}