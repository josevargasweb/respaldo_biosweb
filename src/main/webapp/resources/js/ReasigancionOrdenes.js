

$(document).ready(function () {
    $(".ocultar").hide();
    if (getWithExpiry('rut') !== null) {
        var rutPac = getWithExpiry('rut');

        $("#txtRutP").val(rutPac);
        filtrarByRut(rutPac);
    }
    if (getWithExpiry('rut2') !== null) {
        var rutPac = getWithExpiry('rut2');

        $("#txtRutP2").val(rutPac);
        filtrarByRut2(rutPac);
    }
});
$('#BuscarPac').click(function () {
    limpiarLocalStorage();
    var documento = $('#selectTipoDocumento').val();
    if (documento != 1) {
        setWithExpiry('validadorBuscarPac', 'reasignacionOrdenBlockRut', 100000);
    }
    setWithExpiry('validadorBuscarPac', 'reasignacionOrden', 100000);
});
$('#BuscarPac2').click(function () {
    limpiarLocalStorage();


    setWithExpiry('validadorBuscarPac', 'reasignacionOrden2', 100000);
});
function filtrarByRut(rutPaciente) {//rut
    rutPaciente = rutPaciente.replace(/\./g, '');

    $('#tablaROrdenesBody').empty();
    $.ajax({
        type: "post",
        data: "rutFiltro=" + rutPaciente,
        datatype: "json",
        success: function (pacientes) {
            var infoPaciente = JSON.parse(pacientes);
            var paciente = infoPaciente.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA
            //$("#nDocumento").text('N° DOCUMENTO');
            console.table(paciente);
            if (paciente.Rut === "0") {

                alert('Ingresa algo po');

                /*
                 $("#txtRut").val($("#txtFiltroRut").val());
                 $('html, body').animate({
                 scrollTop: $("div#divForm").offset().top
                 }, 700);*/
            } else {
                //formato fecha
                var dia = paciente.FechaNacimiento.substring(0, 2);
                var mes = paciente.FechaNacimiento.substring(3, 5);
                var anio = paciente.FechaNacimiento.substring(6, 11);
                var fecha = dia + "-" + mes + "-" + anio;

                fecha = getAge(fecha, 3);

                fecha = fecha.split("-");

                //RELLENAR FORMULARIO .substr(0, addy.indexOf(',')
                $('#idPaciente').val(paciente.idPaciente);
                $("#selectTipoDocumento").val(paciente.TipoDocumento);
                //$("#rut").val(paciente.Rut);
                $("#txtNombreP").val(paciente.Nombres);
                $("#sexoPaciente").val(paciente.Sexo);
                if (paciente.SegundoApellido === undefined) {
                    paciente.SegundoApellido = '';
                }
                $("#txtPrimerApellidoP").val(paciente.PrimerApellido + " " + paciente.SegundoApellido);
                $("#txtEdad").val(fecha[0] + " años, " + fecha[1] + " meses, " + fecha[2] + " días");
                var contador = 1;
                if (paciente.Ordenes == null) {

                } else {
                    for (variable of paciente.Ordenes) {

                        $("#tablaROrdenesBody").append("<tr class=''><th >" + contador + "</th><td>" + variable.Fecha + "</td><td>" + variable.NOrden + "</td> <td align='center'><input type='checkbox' class='form-check-input checbox'></td> </tr>");
                        contador++;
                    }
                }
                $("#tablaROrdenesBody").append(" <tfoot><tr> <td ><input id='selectAll' type='button' value='Seleccionar todos' class='btn btn-primary'/></td> </tr></tfoot>");

                //FINAL
                $('#selectTipoDocumento').selectpicker('refresh');


            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
//BUSCA PACIENTE HACIA CON COOKIES 2
function filtrarByRut2(rutPaciente) {//rut
    rutPaciente = rutPaciente.replace(/\./g, '');
    $('#tablaROrdenesBody2').empty();
    //$("#tbodyFiltro").empty();
    $.ajax({
        type: "post",
        data: "rutFiltro=" + rutPaciente,
        datatype: "json",
        success: function (pacientes) {
            var infoPaciente = JSON.parse(pacientes);
            var paciente = infoPaciente.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA
            //$("#nDocumento").text('N° DOCUMENTO');
            console.table(paciente);
            if (paciente.Rut === "0") {
                alert('Ingresa algo po');
                //document.getElementById("AgregarPac").click();

                /*
                 $("#txtRut").val($("#txtFiltroRut").val());
                 $('html, body').animate({
                 scrollTop: $("div#divForm").offset().top
                 }, 700);*/
            } else {
                //formato fecha
                var dia = paciente.FechaNacimiento.substring(0, 2);
                var mes = paciente.FechaNacimiento.substring(3, 5);
                var anio = paciente.FechaNacimiento.substring(6, 11);
                var fecha = dia + "-" + mes + "-" + anio;

                fecha = getAge(fecha, 3);

                fecha = fecha.split("-");

                //RELLENAR FORMULARIO .substr(0, addy.indexOf(',')
                $('#idPaciente2').val(paciente.idPaciente);

                //$("#rut").val(paciente.Rut);
                $("#txtNombreP2").val(paciente.Nombres);
                $("#sexoPaciente2").val(paciente.Sexo);
                if (paciente.SegundoApellido === undefined) {
                    paciente.SegundoApellido = '';
                }
                $("#txtPrimerApellidoP2").val(paciente.PrimerApellido + " " + paciente.SegundoApellido);
                $("#txtEdad2").val(fecha[0] + " años, " + fecha[1] + " meses, " + fecha[2] + " días");

                if (paciente.Ordenes == null) {

                } else {
                    var contador = 1;
                    for (variable of paciente.Ordenes) {
                        console.log(variable.Fecha);
                        console.log(variable.NOrden);
                        $("#tablaROrdenesBody2").append("<tr class=''><th >" + contador + "</th><td>" + variable.Fecha + "</td><td>" + variable.NOrden + "</td>  </tr>");
                        contador++;
                    }
                }
                //FINAL
                $('#selectTipoDocumento2').selectpicker('refresh');
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}
$(function () {

    $("#txtRutP2").rut({formatOn: 'keyup', validateOn: 'blur'}).on('rutInvalido', function () {
        var rut;
        rut = $("#txtRutP").val();
        $("#txtRutP").addClass('is-invalid');
        // $(".disabledForm, #rut").prop("disabled", true);
        if (rut.length === 0) {
            $("#txtRutP2").removeClass('is-invalid');
        }
    }).on('txtRutP', function () {
        var rut;
        rut = $("#txtRutP2").val();
        $("#txtRutP2").addClass('is-invalid');
        if (rut.length > 10) {
            $("#txtRutP2").removeClass('is-invalid');
            // $(".disabledForm, #txtRut").prop("disabled", false);
        }

    });
});

//Activar buscador paciente DESDE con enter
$('#txtRutP').bind("enterKey", function (e) {
    filtrarByRut($("#txtRutP").val());
});
$('#txtRutP').keyup(function (e) {
    if (e.keyCode == 13)
    {
        $(this).trigger("enterKey");
    }
});
//Activar buscador paciente HACIA con enter
$('#txtRutP2').bind("enterKey", function (e) {
    filtrarByRut2($(this).val());
});
$('#txtRutP2').keyup(function (e) {
    if (e.keyCode == 13)
    {
        $(this).trigger("enterKey");
    }
});

//LETRAS AL MODAL
$('#ActivarModal').click(function () {
    //DESDE
    var nombre1 = $('#txtNombreP').val();
    var apellidos1 = $('#txtPrimerApellidoP').val();
    $('#nombreDesde').text(nombre1 + ' ' + apellidos1);
    //HACIA
    var nombre2 = $('#txtNombreP2').val();
    var apellidos2 = $('#txtPrimerApellidoP2').val();
    $('#nombreHacia').text(nombre2 + ' ' + apellidos2);


});
//UPDATEAR PACIENTE
$('#UpdatePaciente').click(function () {
    var cheked = [];
    var checkedBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
    checkedBoxes.forEach(chk => {
        chk.closest("tr").querySelectorAll('td:not(:first-child)').forEach((td, index) => {

            if (index == 1) {

                cheked.push(td.innerHTML);
            }
        });
    });

    var idPac = $('#idPaciente').val();
    var idPac2 = $('#idPaciente2').val();


    $.ajax({
        type: "post",
        data: "UpdatePaciente=" + idPac + "&UpdatePaciente2=" + idPac2 + "&Ordenes=" + cheked,
        datatype: "json",
        success: function () {


            filtrarByRut($('#txtRutP').val());
            filtrarByRut2($('#txtRutP2').val());
        },
        error: function (msg) {
            console.log(msg);
        }
    });
});
function limpearDesde() {
    //limepar texto DESDE
    $('.LimpearTexto1').val('');
    $('#tablaROrdenesBody').empty();
    $('.limpearSelect1').val('N');
}
function limpearHacia() {
    //limepar texto HACIA
    $('.LimpearTexto2').val('');
    $('#tablaROrdenesBody2').empty();
}



//checked all buttons
$('body').on('click', '#selectAll', function () {
    if ($(this).hasClass('allChecked')) {
        $('input[type="checkbox"]', '#tablaROrdenesBody').prop('checked', false);
    } else {
        $('input[type="checkbox"]', '#tablaROrdenesBody').prop('checked', true);
    }
    $(this).toggleClass('allChecked');
    console.log('apretados')
});

$('#limpearPac1').click(function () {
    limpearDesde();
    limpiarLocalStorage();
});
$('#limpearPac2').click(function () {
    limpearHacia();
    limpiarLocalStorage();
});
