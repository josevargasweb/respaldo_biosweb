<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="pb-5 row col-11 mx-auto" data-wizard-type="step-content" data-wizard-state="current">
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Paciente:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1PatientName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">N° Orden:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1OrderN" class="form-control">
                                </div>
                            </div>

                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Tipo de Muestra:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1SampleType" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Sitio anat&oacute;mico:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1BodyPart" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h6 class="col-3 pl-0 pt-3">Toma de Muestra</h6>
                        </div>
                        <div class="form-group col-8">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Estado:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1SamplingState" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Observación:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1SamplingObservation" class="form-control">
                                </div>
                            </div>
                            <!--align-items-center-->

                        </div>
                        <div class="form-group col-4">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Usuario:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1SamplingUser" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Fecha:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1SamplingDate" class="form-control">
                                </div>
                            </div>
                            <!--align-items-center-->

                        </div>
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h6 class="col-3 pl-0 pt-3">Recepción de Muestra</h6>
                        </div>
                        <div class="form-group col-8">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Estado:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1ReceptionState" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Observación:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1ReceptionObservation" class="form-control">
                                </div>
                            </div>
                            <!--align-items-center-->

                        </div>
                        <div class="form-group col-4">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Usuario:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1ReceptionUser" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Fecha:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep1ReceptionDate" class="form-control">
                                </div>
                            </div>
                            <!--align-items-center-->

                        </div>
                    </div>
                </div>