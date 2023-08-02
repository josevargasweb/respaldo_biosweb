// Class definition
"use strict";

var KTWizard3 = function() {
  // Base elements
  var _wizardEl;
  var _formEl;
  var _wizard;

  // Private functions
  var initWizard = function() {
    // Initialize form wizard
    _wizard = new KTWizard(_wizardEl, {
      startStep: 1, // initial active step number
      clickableSteps: true, // allow step clicking
      navigation: false
    });

    // Validation before going to next page
    _wizard.on('beforeNext', function(wizard) {
      const idTipoAtencion = $(g_jqIdTipoAtencion).val();
      var validacionpaso1 = stepPacValidate(idTipoAtencion);
      var paso = wizard.getStep();
      switch (paso) {
        case 1:
          if (validacionpaso1 === true) {
            console.log("paso: " + paso);
            console.log(validacionpaso1);
            $('.selectpicker').selectpicker('refresh');
            wizard.goNext();
          } else {
            //            $("#txtRutP").addClass("is-invalid");
            $('.selectpicker').selectpicker('refresh');
            wizard.stop();
          }
          break;

        case 2:

          if (stepOrdValidate(idTipoAtencion)) {
            $('.selectpicker').selectpicker('refresh');
            wizard.goNext()
          }
          else {
            $('.selectpicker').selectpicker('refresh');
            wizard.stop();

          }
          break;

        case 3:
          if (stepExValidate()) {
            wizard.goNext();
          } else {
            wizard.stop();
            handlerMessageError("Debe seleccionar al menos un examen");
          }
          break;

        case 4:

          if (stepConfValidate()) {
            $("form").submit();
          }
          else {
            wizard.goStop();
          }
          break;
      }
    });
    // Change event
    _wizard.on('change', function(wizard) {
      KTUtil.scrollTop();
    });
  }

  return {
    // public functions
    init: function() {
      _wizardEl = KTUtil.getById('kt_wizard_v3');
      _formEl = KTUtil.getById('kt_form');

      initWizard();
    }

  };
}();

jQuery(document).ready(function() {
  KTWizard3.init();
});


function stepPacValidate(idTipoAtencion) {

  let isValid = false;
  let validadorTipoAtencion = new ValidadorTipoAtencion('is-invalid', 'tipoDeAtencion');
  let validadorRutPac = new ValidadorRutPac('is-invalid', 'txtRutP');
  isValid = validadorTipoAtencion.validate();// && isValid;
  isValid = isValid && validadorRutPac.validate();  
  return isValid;
}

function stepOrdValidate(idTipoAtencion) {

  let result = false;
  let isValid = false;

  if (idTipoAtencion === g_ambulatorio) {

    let validadorProcedencia = new ValidadorProcedencia('is-invalid', 'ProcedenciaComboBoxM');
    isValid = validadorProcedencia.validate();
    return isValid;

  }
  else {
    if ($("#ServicioComboBoxM option:selected").val() !== '-1') {
      result = true;
    }
    else {
      result = false;
      handlerMessageError("Debe especificar un servicio.");
    }

  }

  return result;
}

function stepExValidate() {
  return ($(".dual-listbox__selected li").length !== 0)
}

function stepConfValidate() {
  return true;
}
