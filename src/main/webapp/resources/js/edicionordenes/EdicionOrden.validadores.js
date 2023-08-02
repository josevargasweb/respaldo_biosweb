/**
 * 
 */
'use strict';

class ValidadorTipoDoc {
  constructor(classInvalid, jqid) {
    this.result = false;
    this.classInvalid = classInvalid;
    this.tipoDoc = $("#" + jqid).val();
    this.errorMessage = 'Datos tipo de documento inválidos.';
    this.el = document.getElementById(jqid);
    this.jqel = $("#" + jqid);
  }

  validate() {

    if (this.tipoDoc !== null && (this.tipoDoc === gCte.TipoDocPasaporte && $("#txtPasaporte").val().trim() !== "") || (this.tipoDoc === gCte.TipoDocRun && $("#txtRutP").val().trim() !== "")) {
      this.result = true;
      this.jqel.removeClass("is-invalid");
    }
    else {
      this.result = false;
      this.el.className = this.classInvalid;
      this.jqel.addClass("is-invalid");
    }
    return this.result;
  }

}

class ValidadorTipoAtencion {
  constructor(classInvalid, jqid, servicio,procedencia) {
    this.result = false;
    this.classInvalid = classInvalid;
    this.tipoAtencion = RegistroOden.tipoDeAtencion;
    this.errorMessage = 'Debe escoger un tipo de atención.';
    this.el = document.getElementById(jqid);
    this.jqel = $("#" + jqid);
    this.servicio = $("#" + servicio);
    this.tipoServicio = RegistroOden.ServicioComboBoxM;
    this.procedencia = $("#"+procedencia);
    this.tipoProcedencia =RegistroOden.ProcedenciaComboBoxM;
  }

  validate() {
    if (this.tipoAtencion == -1) {
      this.result = false;
      this.jqel.addClass("is-invalid");
      this.servicio.removeClass("is-invalid");
      this.procedencia.removeClass("is-invalid");
    } 
    else if ((this.tipoAtencion == 2 || this.tipoAtencion == 3) && this.tipoServicio == -1) {
      this.result = false;
      this.jqel.removeClass("is-invalid");
      this.servicio.addClass("is-invalid");
      this.procedencia.removeClass("is-invalid");
    }
    else if ((this.tipoAtencion == 1 ) && this.tipoProcedencia == -1) {
      this.result = false;
      this.jqel.removeClass("is-invalid");
      this.procedencia.addClass("is-invalid");
      this.servicio.removeClass("is-invalid");
    }
    else {
      this.result = true;
      this.jqel.removeClass("is-invalid");
      this.servicio.removeClass("is-invalid");
      this.procedencia.removeClass("is-invalid");
    }
    return this.result;
  }

}

class ValidadorRutPac {
  constructor(classInvalid, jqid,pasaporte) {
    this.result = false;
    this.classInvalid = classInvalid;
    this.rutPaciente = $("#" + jqid);
    this.rut = $("#" + jqid);
    this.errorMessage = 'Debe tener un rut.';
    this.el = document.getElementById(jqid);
    this.jqel = $("#" + jqid);
    this.pasaporte = $("#" + pasaporte);
  }

  validate() {
    if (this.rutPaciente.is(":visible") && this.rutPaciente.val() === '') {
      this.result = false;
      this.rutPaciente.addClass("is-invalid");
      if(typeof this.rutPaciente.attr('data-custom-class') === 'undefined'){
        ui.imprimirTooltip(this.rutPaciente, "RUN no válido");
      }
    }
    else  if (this.pasaporte.is(":visible") && this.pasaporte.val() === '') {
      this.result = false;
      this.pasaporte.addClass("is-invalid");
    }
    else {
      this.result = true;
      this.pasaporte.removeClass("is-invalid");
      this.rutPaciente.removeClass("is-invalid");
      if(typeof this.rutPaciente.attr('data-custom-class') !== 'undefined'){
        ui.limpiarTooltip(this.rutPaciente);
      }
    }
    return this.result;
  }
}

class ValidadorProcedencia {
  constructor(classInvalid, jqid) {
    this.result = false;
    this.classInvalid = classInvalid;
    this.procedencia = RegistroOden.ProcedenciaComboBoxM;
    this.errorMessage = 'Debe tener una procedencia.';
    this.jqel = $("#" + jqid);
    this.jqid = jqid;
  }

  validate() {
    if (this.procedencia  !== -1) {
      this.result = true;
      this.jqel.removeClass("is-invalid");
    }
    else {
      this.result = false;
      this.jqel.addClass("is-invalid");
    }
    return this.result;
  }
}

class ValidadorExamenesSeleccionados {
  constructor(jqid,listaexamenes) {
    this.result = false;
    this.examenes = $("#" + jqid);
    this.errorMessage = 'Debe seleccionar al menos un examen';
    this.listaexamenes = listaexamenes;
  }

  validate() {

    if (this.listaexamenes === 0) {
      this.result = false;
      //add data
      this.examenes.find('.dataTables_empty').html(`<span class="text-danger">${this.errorMessage}</span>`);
    }
    else {
      this.result = true;
      //remove data
    }
    return this.result;
  }
}



function stepPacValidate(idTipoAtencion,lstExamenes) {

  let isValid = false;
 let validadorRutPac = new ValidadorRutPac('is-invalid', 'txtRutP','txtPasaporte');
 isValid = validadorRutPac.validate();
  let validadorTipoAtencion = new ValidadorTipoAtencion('is-invalid', 'tipoDeAtencion','ServicioComboBoxM','ProcedenciaComboBoxM');
  isValid = validadorTipoAtencion.validate();// && isValid;
  let ValidadorExamenes = new ValidadorExamenesSeleccionados('idDataTarget', lstExamenes);
  isValid == false ? false : ValidadorExamenes.validate()
  return isValid;
}


function stepExValidate(dpDto) {
  return (dpDto.lstExamenes.length !== 0)
}

function stepConfValidate() {
  return true;
}


 function validaOrdenSubmit(dpDto) {
       
      const idTipoAtencion = $(g_jqIdTipoAtencion).val() != "" ? $(g_jqIdTipoAtencion).val() : -1;
    let validacion = stepPacValidate(idTipoAtencion,dpDto.lstExamenes.length);
     validacion = validacion && stepExValidate(dpDto);
     validacion = validacion && stepConfValidate();
     return validacion;    
    };
