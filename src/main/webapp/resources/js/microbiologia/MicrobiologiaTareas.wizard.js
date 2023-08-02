// Class definition
"use strict";

var KTWizard3 = function () {
    // Base elements
    var _wizardEl;
    var _formEl;
    var _wizard;

    // Private functions
    var initWizard = function () {
        // Initialize form wizard
        _wizard = new KTWizard(_wizardEl, {
            startStep: 1, // initial active step number
            clickableSteps: true, // allow step clicking
            navigation: false
        });

        // Validation before going to next page
        _wizard.on('beforeNext', function (wizard) {
            var paso = wizard.getStep();
            switch (paso) {
                case 1:
                    if (step1Validate()) {
                        wizard.goNext();
                    } else {
                        wizard.stop();
                        alert("La muestra debe ser tomada y recepcionada para avanzar.")
                    }
                    break;

                case 2:

                    if (step2Validate()) {
                        wizard.goNext()
                    }
                    else {
                        alert("Debe maracar la etapa como completada para avanzar.");
                        wizard.stop();
                    }
                    break;

                case 3:
                    if (step3Validate()) {
                        loadStep4ToComplete(undefined);
                        wizard.goNext();
                    } else {
                        wizard.stop();
                        alert("Debe seleccionar al menos un medio de cultivo y " + "marcar la etapa como completada para avanzar");
                    }
                    break;

                case 4:

                    if (step4Validate()) {
                        LoadStep5TableData(undefined);
                        wizard.goNext();
                    }
                    else {
                        alert("Debe maracar la etapa como completada para avanzar.");
                        wizard.stop();
                    }
                    break;
                case 5:
                    if (step5Validate()) {
                        if (checkStep5State(true)) {
                            closeFromStep5();
                        }
                        else {
                            LoadStep6TableData();
                            wizard.goNext();
                        }
                    }
                    else {
                        alert("Debe maracar la etapa como completada para avanzar.");
                        wizard.stop();
                    }
                    break;
                case 6:
                    if (step6Validate()) {
                        LoadStep7TableData();
                        wizard.goNext();
                    }
                    else {
                        alert("Debe maracar la etapa como completada para avanzar.");
                        wizard.stop();
                    }
                    break;
                case 7:
                    if (step7Validate()) {
                        LoadStep8Tables();
                        wizard.goNext();
                    }
                    else {
                        alert("Debe maracar la etapa como completada para avanzar.");
                        wizard.stop();
                    }
                    break;
                case 8:
                    wizard.goNext();
                    break;
            }
        });
        // Change event
        _wizard.on('change', function (wizard) {
            KTUtil.scrollTop();
        });

    }

    var goToStep = function (step) {
        _wizard.goTo(step);
    }

    return {
        // public functions
        init: function () {
            _wizardEl = KTUtil.getById('kt_wizard_v3');
            _formEl = KTUtil.getById('kt_form');

            initWizard();
        },
        goTo: function (step) {
            goToStep(step);
        }
    };
}();

jQuery(document).ready(function () {
    KTWizard3.init();
});


function step1Validate() {
    return $("#txtTaskStep1SamplingState").val() == "TOMADA" && $("#txtTaskStep1ReceptionState").val() == "RECEPCIONADA";
}

function step2Validate() {
    return $("#chkStep2IsReady").is(":checked");
}

function step3Validate() {
    return $("#chkStep3IsReady").is(":checked");
}

function step4Validate() {
    return $("#chkStep4IsReady").is(":checked");
}

function step5Validate() {
    return $("#chkStep5IsReady").is(":checked");
}

function step6Validate() {
    return $("#chkStep6IsReady").is(":checked");
}

function step7Validate() {
    return $("#chkStep7IsReady").is(":checked");
}


function wizardGoToStep(step) {
    KTWizard3.goTo(step);
}
