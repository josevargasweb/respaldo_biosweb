$(document).ready(function() {
	//filtrarByNombreRut("", "", "", "");
        filtrarByNombreRut();
        $("#confirmarDatos").hide();
	$("#txtFiltroRut").val("");
	$(".ocultar").hide();
	$('#antecedentesRN').hide();
	$('.neverSeen').hide();
	$("#divBtnUpdateMedico").hide();
	$("#divBtnEditar").hide();
	if (getWithExpiry('validadorBuscarMed') === '1') {			
		$("#validadorOrden").val("1");
	}
	$('.selectpicker').selectpicker('refresh');
	$("#btnLimpiar2").hide();
        let messageSuccess = $("#messageSuccess").val();
        let messageError = $("#messageError").val();
        if (typeof messageSuccess !== 'undefined' && messageSuccess!==null){
            handlerMessageOk(messageSuccess);
        }
        if (typeof messageError !== 'undefined' &&messageError!==null){
            handlerMessageError(messageError);
        }

		
		$('.selectpicker').selectpicker({
			noneSelectedText: 'Seleccionar',
			noneResultsText: 'No hay resultados'
		});
		$('.selectpicker').selectpicker('refresh');
	});
let idMedicoParaUpdate = 0;

$("#checkBoxLever").click(function() {
	if ($(this).is(":checked")) {
		$("#lblEstado").text("Activo");
	}
	if (!($(this).is(":checked"))) {
		$("#lblEstado").text("Inactivo");
	}
});

//coloca rut con puntos***********
$("#txtRut").rut({ formatOn: 'keyup', validateOn: 'blur' });
$("#txtFiltroRut").rut({ formatOn: 'keyup', validateOn: 'blur' });
//*********************** */

$("#nuevoMedico").click(function() {
	// $('#collapseOne8').collapse('hide');
	$("#collapseHeader").collapse('hide');
	limpiarFormulario();
	$("#txtRut").focus();
});

$("#buscarMedico").click(function() {
	// $('#collapseOne8').collapse('show');
	$("#collapseOne8").collapse('show')
});

$(".collapseMedico").click(function() {
	$("#btnLimpiarFiltro").show();
});

//aqui entra cuando se presiona el boton buscar 
$("#btnBuscarFiltro").click(function() {


	if ($("#txtFiltroRut").val().length == 0) {
		//filtrarByNombreRut($("#txtFiltroNombre").val(), $("#txtFiltroApellido").val(), $("#txtFiltroSegundoApellido").val(), "");
	filtrarByNombreRut();	
        limpiarFormulario();
	} else {
		var rut;
		rut = $("#txtFiltroRut").val();
		//filtrarByNombreRut("", "", "", rut);
                filtrarByNombreRut();
	}
});

function buscarByRut(rutMedico, validadorRutSinFiltro,collapse = true) {
	$("#btnLimpiarFiltro").hide();
	$("#divBtnEditar").show();
	$("#btnAgregarMedico").prop('disabled', true);
	// $('#collapseOne8').collapse('hide');
	$("#collapseOne8").collapse('hide')
	$("#tbodyFiltroMedico").empty();
	$.ajax({
		type: "post",
		data: "rutFiltroMedico=" + rutMedico,
		datatype: "json",
		success: function(Medico) {
			if(collapse){
				$("#collapseHeader").collapse('hide');
			}
			var Medico = JSON.parse(Medico);
			var Medico = Medico.pacientes[0]; //PACIENTE QUE SE RETORNO DESDE JAVA

			if (Medico.EspecialidadMedica == "0") {
				Medico.EspecialidadMedica = 'N'
			}
			if (Medico.Institucion == "0") {
				Medico.Institucion = 'N'
			}
			if (Medico.Sexo == "0") {
				Medico.Sexo = 'N'
			}
			if (Medico.Rut === "0") {
				if (validadorRutSinFiltro == 1) {

				} else {
					$("#txtRut").val($("#txtFiltroRut").val());
				}
				$("#txtRut").prop("disabled", false);
				// $('html, body').animate({
				// 	scrollTop: $("div#divForm").offset().top
				// }, 700);
				$("#txtNombre").focus();
				$("#btnAgregarMedico").prop('disabled', false);

				$("#divBtnEditar").hide();
				$("#confirmarDatos").hide();
			} else {
				//RELLENAR FORMULARIO
                                //handlerMessageError("Rut ya se encuentra registrado");
                idMedicoParaUpdate = Medico.idMedico;
				$("#idMedico").val(Medico.idMedico);
				$("#txtRut").val(cambiarDatoRut(Medico.Rut));
				$("#txtNombre").val(Medico.Nombres);
				$("#txtNombre").prop('disabled', true);
				$("#txtPrimerApellido").val(Medico.PrimerApellido);
				$("#txtPrimerApellido").prop('disabled', true);
				$("#txtSegundoApellido").val(Medico.SegundoApellido);
				$("#txtSegundoApellido").prop('disabled', true);
				$("#selectSexo").val(Medico.Sexo);
				$('#selectSexo').change();
				$("#selectSexo").prop('disabled', true);
				$("#SelectEspecialidadMedic").val(Medico.EspecialidadMedica);
				$("#SelectEspecialidadMedic").change()
				$("#SelectEspecialidadMedic").prop('disabled', true);
				$("#SelectInstitMedic").val(Medico.Institucion);
				$("#SelectInstitMedic").change()
				$("#SelectInstitMedic").prop('disabled', true);
				$("#txtTelefono").val(Medico.Telefono);
				$("#txtTelefono").prop('disabled', true);
				$("#txtEmail").val(Medico.Email);
				$("#txtEmail").prop('disabled', true);
				$("#RegistroSalud").val(Medico.RegistroSaludMedico);
				$("#RegistroSalud").prop('disabled', true);
				$(".switch-content").addClass("disabled");
				Medico.Estado === "S" ?  $(".switch-content").removeClass("inactivo") : $(".switch-content").addClass("inactivo")
				if (Medico.Estado == "N") {
					$("#checkBoxLever").val("N");
					$("#checkBoxLever").prop("checked", false)
					$("#lblEstado").text("Inactivo");
				}else{
					$("#checkBoxLever").val("S");
					$("#checkBoxLever").prop("checked", true)
					$("#lblEstado").text("Activo");
				}
				//                $('select').select2({width: "100%"});
				$("#txtRut").prop("disabled", true);
				// $('html, body').animate({
				// 	scrollTop: $("div#divForm").offset().top
				// }, 700);
				//$('#divBtnEditar').show();
				$("#btnGuardarUpdate").prop('disabled', true);

				if (getWithExpiry('validadorBuscarMed') === '1') {
					$("#confirmarDatos").show();
					setLocalMedico(Medico.Rut);
				}
				activarEdit();
				//VARIABLES 

				$('.selectpicker').selectpicker('refresh');
			}
		},
		error: function(msg) {
			console.log(msg);
		}
	});
}

function filtrarByNombreRut(/*nombreMedico, apellidoMedico, segundoApellidoMedico, rut*/) {
	let nombreMedico = $("#txtFiltroNombre").val();
        let apellidoMedico = $("#txtFiltroApellido").val();
        let segundoApellidoMedico = $("#txtFiltroSegundoApellido").val();
        let rut = $("#txtFiltroRut").val();
        let activo = $("#chkMostrarActivos").is(":checked") ? "S" : "N";
    
        $("#tbodyFiltro").empty();
	let rut1 = rut.replace(/\./g, '');
	$.ajax({
		type: "post",
		data: "nombreFiltroMedico=" + nombreMedico + "&apellidoFiltroMedico=" + apellidoMedico + "&segundoApellidoFiltroMedico=" + segundoApellidoMedico + "&rut=" + rut1 + "&activo=" + activo,
		datatype: "json",
		success: function(medicos) {
                    var infoMedicos = JSON.parse(medicos);
                    if(!infoMedicos.hasOwnProperty('error')){
                        if (infoMedicos.medicos.length > 0){
                            var cont = 0;
                            infoMedicos.medicos.forEach(function(medico) {
                                    cont++;
                                    /*if (medico.SegundoApellido == undefined) {
                                            medico.SegundoApellido = "";
                                    }*/
                                    let rut = cambiarDatoRut(medico.Rut)
                                    $("#tbodyFiltro").append("<tr href='#!' class='pointer' onclick='buscarRut(this)'><th class='row mx-auto'>" + cont + "</th><td class='rutFiltro'>" + rut + "</td><td class='nombrefiltro'>" + medico.Nombre + " " + medico.Apellido + " " + medico.SegundoApellido + "</td></tr>");
                            });
                            $("#lblErrorFiltro").hide();
                            $("#lblTotalFiltro").show();
                            $("#totalFiltro").text(cont);
                        } else {
                            $("#lblErrorFiltro").show();
                            $("#lblTotalFiltro").hide();
                        }
                    } else {
                        handlerMessageError(infoMedicos.error);
                    }
		},
		error: function(msg) {
			console.log(msg);
		}
	});
}

function cambiarDatoRut(rut) {
	let value = rut.replace(/\./g, '').replace('-', '');

	if (value.match(/^(\d{2})(\d{3}){2}(\w{1})$/)) {
		value = value.replace(/^(\d{2})(\d{3})(\d{3})(\w{1})$/, '$1.$2.$3-$4');
	}
	else if (value.match(/^(\d)(\d{3}){2}(\w{0,1})$/)) {
		value = value.replace(/^(\d)(\d{3})(\d{3})(\w{0,1})$/, '$1.$2.$3-$4');
	}
	else if (value.match(/^(\d)(\d{3})(\d{0,2})$/)) {
		value = value.replace(/^(\d)(\d{3})(\d{0,2})$/, '$1.$2.$3');
	}
	else if (value.match(/^(\d)(\d{0,2})$/)) {
		value = value.replace(/^(\d)(\d{0,2})$/, '$1.$2');
	}
	return rut = value;
}

function limpiarFormulario() {
	$(".switch-content").removeClass("disabled inactivo");
	$("#txtRut").val("");
	$("#txtRut").removeClass('is-invalid');
	$("#txtRut").prop("disabled", false);
	$("#txtNombre").val("");
	$("#txtNombre").prop("disabled", false);
	$("#txtPrimerApellido").val("");
	$("#txtPrimerApellido").removeClass('is-invalid');
	$("#txtPrimerApellido").prop("disabled", false);
	$("#txtSegundoApellido").val("");
	$("#txtSegundoApellido").removeClass('is-invalid');
	$("#txtSegundoApellido").prop("disabled", false);
	$("#selectSexo").val("0");
	$("#selectSexo").change();
	$("#selectSexo").prop("disabled", false);
	$("#SelectEspecialidadMedic").val("0");
	$("#SelectEspecialidadMedic").change();
	$("#SelectEspecialidadMedic").prop("disabled", false);
	$("#SelectInstitMedic").val("0");
	$("#SelectInstitMedic").change();
	$("#SelectInstitMedic").prop("disabled", false);

	$("#txtTelefono").val("");
	$("#txtTelefono").prop("disabled", false);
	$("#txtEmail").val("");
	$("#txtEmail").prop("disabled", false);
	$("#idMedico").val("");
	$("#idMedico").prop("disabled", false);
	$("#RegistroSalud").val("");
	$("#RegistroSalud").prop('disabled', false);
	$("#divBtnEditar").hide();

	//    $('select').select2({width: "100%"});
}
$("#txtFiltroNombre, #txtFiltroApellido, #txtFiltroSegundoApellido").keyup(function(input) {
	if (input.currentTarget.value.length > 0) {
		$("#txtFiltroRut").val("");
		$("#txtFiltroRut").prop("disabled", true);
	} else {
		$("#txtFiltroRut").prop("disabled", false);
	}
    filtrarByNombreRut();
});

$("#txtFiltroRut").keyup(function() {
	if ($("#txtFiltroRut").val().length > 0) {
		$("#txtFiltroNombre").val("");
		$("#txtFiltroApellido").val("");
		$("#txtFiltroSegundoApellido").val("");
		$("#txtFiltroNombre").prop("disabled", true);
		$("#txtFiltroApellido").prop("disabled", true);
		$("#txtFiltroSegundoApellido").prop("disabled", true);
	} else {
		$("#txtFiltroNombre").prop("disabled", false);
		$("#txtFiltroApellido").prop("disabled", false);
		$("#txtFiltroSegundoApellido").prop("disabled", false);
	}
        filtrarByNombreRut();
});
$("#chkMostrarActivos").change(function(){
    filtrarByNombreRut();
});
function buscarRut(a) {
	var $row = $(a).closest("tr"); // Find the row
	seleccionarFila(a,'#tableFiltro');
	var rut = $row.find(".rutFiltro").text(); // Find the text
	buscarByRut(rut);
}
$("#divBtnEditar").click(function() {
	$(".switch-content").removeClass("disabled");
	$("#btnLimpiar2").show();
	$("#btnLimpiar").hide();
	$('#divBtnEditar').hide();
	$("#btnAgregarMedico").hide();
	$("#divBtnUpdateMedico").show();
	//**modificado por cristian 07-11 no se debe modificar rut */
	//$("#txtRut").prop("disabled", false);
	$("#txtNombre").prop('disabled', false);
	$("#txtPrimerApellido").prop('disabled', false);
	$("#txtSegundoApellido").prop('disabled', false);
	$("#selectSexo").prop('disabled', false);
	$("#SelectEspecialidadMedic").prop('disabled', false);
	$("#SelectInstitMedic").prop('disabled', false);
	$("#txtTelefono").prop('disabled', false);
	$("#txtEmail").prop('disabled', false);
	$("#RegistroSalud").prop('disabled', false);
	$('#divCancelEdit').show();
	$("#btnGuardarUpdate").prop('disabled', false);
	$("#txtNombre").focus();
	$('#btnLimpiar').addClass('disabled');
	$('.selectpicker').selectpicker('refresh');
});

$("#btnCancelarUpdate").click(function() {
	$(".switch-content").addClass("disabled");
	$("#btnLimpiar2").hide();
	$("#btnLimpiar").show();
	$('#divCancelEdit').hide();
	$('#divBtnEditar').show();
	$("#btnAgregarMedico").show();
	$("#divBtnUpdateMedico").hide();
	$("#txtRut").prop("disabled", true);
	$("#txtNombre").prop('disabled', true);
	$("#txtPrimerApellido").prop('disabled', true);
	$("#txtSegundoApellido").prop('disabled', true);
	$("#selectSexo").prop('disabled', true);
	$("#SelectEspecialidadMedic").prop('disabled', true);
	$("#SelectInstitMedic").prop('disabled', true);
	$("#txtTelefono").prop('disabled', true);
	$("#txtEmail").prop('disabled', true);
	$("#RegistroSalud").prop('disabled', true);
	$('#btnLimpiar').removeClass('disabled');
	$('.selectpicker').selectpicker('refresh');

});

$("#btnLimpiar").click(function() {
	limpiarFormulario();
	$("#divBtnUpdateMedico").hide();
	$('#divCancelEdit').hide();
	$(".disabledForm").prop("disabled", true);
	$("#btnAgregarMedico").show();
	$("#btnAgregarMedico").prop('disabled', false);
	$('#txtRut').prop('disabled', false);
	$('#divBtnEditar').hide();
	$("#collapseHeader").collapse('show');
	$('.selectpicker').selectpicker('refresh');

});

$("#btnLimpiarFiltro").click(function() {
	$("#txtFiltroRut").val("");
	$("#txtFiltroNombre").val("")
	$("#txtFiltroApellido").val("")
	$("#txtFiltroSegundoApellido").val("")
	$("#txtFiltroRut").prop("disabled", false);
	$("#txtFiltroNombre").prop("disabled", false);
	$("#txtFiltroApellido").prop("disabled", false);
	$("#txtFiltroSegundoApellido").prop("disabled", false);
        $("#chkMostrarActivos").prop("checked", false);
	//	limpiarFormulario();
	filtrarByNombreRut();

	$(".disabledForm").prop("disabled", true);

	$('#txtRut').prop('disabled', false);

	$('.selectpicker').selectpicker('refresh');

})

// ESTO NO LO TOMA, SE TIENE QUE VALIDAR AL HACER SUBMIT ****************************************************
$('form').submit(function(e) {
	$("#txtRut").prop("disabled", false);
	let rut = $("#txtRut").val();
	
	if(checkRut(rut) == false){
		 e.preventDefault();
		 console.log("rut invalido")
		 $("#invalid-feedback").show();
	}else{
		 $("#invalid-feedback").hide();
	}
	

	let nombres = $("#txtNombre").val();
	let primerApellido = $("#txtPrimerApellido").val();
	let segundoApellido = $("#txtSegundoApellido").val();
	let telefono = $("#txtTelefono").val();
	let email = $("#txtEmail").val();
	let valorSexo = $("#selectSexo :selected").val();
	let valorInstitucion = $("#SelectEspecialidadMedic :selected").val();
	let valorEspecialidad = $("#SelectInstitMedic :selected").val();

	if (/*valorEspecialidad === "N" || valorInstitucion === "N" ||  valorSexo === "N" || */nombres.length <= 0 || primerApellido.length <= 0 || rut.length <= 0) {
		if (nombres.length <= 0) {
			$("#txtNombre").addClass('is-invalid');
		}
		if (primerApellido.length <= 0) {
			$("#txtPrimerApellido").addClass('is-invalid');
		}
		if (rut.length <= 0) {
			$("#txtRut").addClass('is-invalid');
		}
		/*
		 if (telefono.length <= 0) {
		 $("#txtTelefono").addClass('is-invalid');
		 }
		 if (email.length <= 0) {
		 $("#txtEmail").addClass('is-invalid');
		 }
		 
		 if (valorSexo === "N") {
		 $("#divSelectSexo>.select-wrapper").addClass('is-invalid');
		 }
		 if (valorInstitucion === "N") {
		 $("#divSelectInstitucionMedic>.select-wrapper").addClass('is-invalid');
		 }
		 if (valorEspecialidad === "N") {
		 $("#divSelectEspecialidadMedic>.select-wrapper").addClass('is-invalid');
		 }*/
		return false;
	} else {
		if (getWithExpiry('validadorBuscarMed') === '1') {
			setLocalMedico(rut);
		}
		return true;
	}
});
//*************************************************************************************************************** */

$("#checkBoxLever").change(function() {
	if ($(this).is(":checked")) {
		$("#checkBoxLever").val("S");
	} else {
		$("#checkBoxLever").val("N");
	}
});

$.getJSON("https://api.ipify.org?format=json", function(data) {
	$("#ipEquipo").val(data.ip);
});

$("#txtRut").change(function() {
	setTimeout(function() {
		if (!($("#txtRut").hasClass('invalid'))) {
			buscarByRut($("#txtRut").val(), 1);
		}
	}, 10);


});

$("#circuloLimpiar").hover(
	function() {
		$("#iLimpiar").removeClass("text-primary");
		$("#iLimpiar").addClass("text-white");
	},
	function() {
		$("#iLimpiar").removeClass("text-white");
		$("#iLimpiar").addClass("text-primary");
	}
);

$("#circuloBuscarPaciente").hover(
	function() {
		$("#iBuscarMedico").removeClass("text-primary");
		$("#iBuscarMedico").addClass("text-white");
	},
	function() {
		$("#iBuscarMedico").removeClass("text-white");
		$("#iBuscarMedico").addClass("text-primary");
	}
);

$("#circuloCancelEdit").hover(
	function() {
		$("#iCancelEdit").removeClass("text-danger");
		$("#iCancelEdit").addClass("text-white");
	},
	function() {
		$("#iCancelEdit").removeClass("text-white");
		$("#iCancelEdit").addClass("text-danger");
	}
);

$("#circuloEditarMedico").hover(
	function() {
		if (localStorage.getItem("botonEditar") === "activo") {
			$("#circuloEditarMedico").addClass("bg-hover-blue");
			$("#iEditMedico").removeClass("text-blue");
			$("#iEditMedico").addClass("text-white");
		}
	},
	function() {
		if (localStorage.getItem("botonEditar") === "activo") {
			$("#iEditMedico").removeClass("text-white");
			$("#iEditMedico").addClass("text-blue");
		}
	}

);

//dar  vuelta
var KTBootstrapSelect = function() {
	// Private functions
	var demos = function() {
		// minimum setup
		$('selectpicker').selectpicker();
	}
	return {
		// public functions
		init: function() {
			demos();
		}
	};
}();


function activarEdit() {
	$("#iEditMedico").removeClass("text-dark-50");
	$("#iEditMedico").addClass("text-primary");
	localStorage.setItem("botonEditar", "activo");
}

function desactivarEdit() {
	$("#iEditMedico").removeClass("text-primary");
	$("#iEditMedico").addClass("text-dark-50");
	localStorage.setItem("botonEditar", "inactivo");
}
function goBack() {
	window.history.back();
}


var getUrlParameter = function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1),
		sURLVariables = sPageURL.split('&'),
		sParameterName,
		i;
	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
		}
	}
};


var mensaje = getUrlParameter('message');
let retornoMensaje = "ingresadoCorrectamente";
let retornoExitoso = "Paciente ingresado correctamente";





const tableNav = new TableNavigation("tableFiltro",selectCausaAuto);
tableNav.changeContenedor(".table-container");
function selectCausaAuto(a = null) {
    let $row;
    if(a == null){
        let tabla = document.getElementById("tableFiltro");
        let tbody = tabla.querySelector("tbody");
        let primerTR = tbody.querySelector("tr:first-child");
        $row = primerTR;
    }else{
        $row = a;
    }
    var idCausa = $row.querySelector('td.rutFiltro').textContent;
    buscarByRut(idCausa,1,false);
	$('.selectpicker').selectpicker('refresh');
}




//******************************************************* */

$("#btnAgregarMedico").click(function(event) {
	event.preventDefault();
	insertMedico();
});
function insertMedico() {
	
	let rut = $("#txtRut").val().toUpperCase().replaceAll(".", "");
    let data = JSON.stringify({
		//cpidProcedencia: idProcedenciaParaUpdate,
        cmRun: rut,
        cmNombres: $("#txtNombre").val().toUpperCase(),
        cmPrimerapellido: $("#txtPrimerApellido").val().toUpperCase(),
        cmSegundoapellido: $("#txtSegundoApellido").val().toUpperCase(),
        ldvSexo: $("#selectSexo").val(),
        cmEmail: $("#txtEmail").val().toUpperCase(),
        cmTelefono: $("#txtTelefono").val().toUpperCase(),
        cfgInstitucionesdesalud: $("#SelectInstitMedic").val(),
        ldvEspecialidadesmedicas: $("#SelectEspecialidadMedic").val(),
        cmRegistrodesalud: $("#RegistroSalud").val().toUpperCase(),
        mcActivo: $("#checkBoxLever").is(":checked") ? "S" : "N",
    });
    
    $.ajax({
        type: "POST",
        url: getctx + "/api/medico/insert",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
	console.log(data)
			//filtrarLike();
			limpiarFormulario();
			//$('#btnLimpiar').removeClass('disabled');
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al ingresar medico" );
        }
    });
}
$("#btnGuardarUpdate").click(function(event) {
	event.preventDefault();
	updateMedico();
});
function updateMedico() {
		
	let rut = $("#txtRut").val().toUpperCase().replaceAll(".", "");
    let data = JSON.stringify({
		cmIdmedico: idMedicoParaUpdate,
        cmRun: rut,
        cmNombres: $("#txtNombre").val().toUpperCase(),
        cmPrimerapellido: $("#txtPrimerApellido").val().toUpperCase(),
        cmSegundoapellido: $("#txtSegundoApellido").val().toUpperCase(),
        ldvSexo: $("#selectSexo").val(),
        cmEmail: $("#txtEmail").val().toUpperCase(),
        cmTelefono: $("#txtTelefono").val().toUpperCase(),
        cfgInstitucionesdesalud: $("#SelectInstitMedic").val(),
        ldvEspecialidadesmedicas: $("#SelectEspecialidadMedic").val(),
        cmRegistrodesalud: $("#RegistroSalud").val().toUpperCase(),
        cmActivo: $("#checkBoxLever").is(":checked") ? "S" : "N",
    });
    
    $.ajax({
        type: "PUT",
        url: getctx + "/api/medico/update",
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        datatype: "json",
        success: function (data) {
	console.log(data)

			$('#btnCancelarUpdate').click();
			if(data.status == 200)
				handlerMessageOk(data.message);
			if(data.status == 300)
				handlerMessageError(data.message);
        },
        error: function (msg) {
            console.log(msg.responseText);
            handlerMessageError("Error al actualizar medico");
        }
    });
}


