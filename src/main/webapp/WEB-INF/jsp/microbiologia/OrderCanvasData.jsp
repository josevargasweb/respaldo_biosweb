<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade show solo-texto active" id="OrderCanvasData" role="tabpanel" aria-labelledby="OrderCanvasData">
    <div class="row">
        <div class="col">
      
            <div class="col-12">
                <div class="row">
                    <h5 class="title-paciente"> Datos del Paciente</h5>
                    <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                     <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                                <span id="idOrderCanvasDataName"></span>
                        </div>
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">N° Documento</label>
                                <span id="idOrderCanvasDataDocumento"></span>
                        </div>
                        
                        <div class="col-2 form-group  mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Sexo</label>
                                <span id="idOrderCanvasDataGender"></span>
                        </div>
                        <div class="col-2 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Edad</label>
                                <span id="idOrderCanvasDataAge"></span>
                        </div>
                        <div class="col-6 form-group mt-1 mb-0">
                            <label class="col-form-label pb-0 col-12 pl-0">Fecha de Nacimiento</label>
                                <span id="idOrderCanvasDataDateOfBirth"></span>
                        </div>
                        <div class="col-6 form-group mt-1 mb-0">
                            <label
                                class=" col-form-label pb-0 col-12 pl-0">Teléfono</label>
                                <span id="idOrderCanvasDataPhoneNumber"></span>
                        </div>
                        <div class="col-6 form-group mt-1 mb-0">
                            <label class="col-form-label pb-0 col-12 pl-0">Tipo de Atención</label>
                                <span id="idOrderCanvasDataAtentionType"></span>
                        </div>
                         <div class="col-3 form-group mt-1 mb-0">
                            <label
                                class=" col-form-label pb-0 col-12 pl-0">Servicio</label>
                                <span id="idOrderCanvasDataService"></span>
                        </div>
                         <div class="col-3 form-group mt-1 mb-0">
                            <label
                                class=" col-form-label pb-0 col-12 pl-0">Procedencia</label>
                                <span id="idOrderCanvasDataOrigin"></span>
                        </div>
                        <div class="col-6 form-group mt-1 mb-0">
                            <label class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                <span id="idOrderCanvasDataPatientObservation"></span>
                        </div>
                        <div class="col-6 form-group mt-1 mb-0">
                            <label class="col-form-label pb-0 col-12 pl-0">Patologías</label>
                                <span id="idOrderCanvasDataPathologies"></span>
                        </div>
                    </div>
                    <input class="form-control" placeholder="" id="idOrderCanvasDataLocalization" type="hidden"/>
                </div>
            </div>
        
            <h5>Datos de la Orden</h5>
            <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                <div class="col-4 form-group mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">N° Orden</label>
                        <span id="idOrderCanvasDataOrderId"></span>
                </div>
                <div class="col-4 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                        <span id="idOrderCanvasDataDate"></span>
                </div>
                <div class="col-4 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">Convenio</label>
                        <span id="idOrderCanvasDataContract"></span>
                </div>
                <div class="col-4 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">Medico</label>
                        <span id="idOrderCanvasDataPhysician"></span>
                </div>
                <div class="col-4 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">N° Host</label>
                        <span id="idOrderCanvasDataHost"></span>
                </div>
                <div class="col-4 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">Diagnóstico</label>
                        <span id="idOrderCanvasDataDiagnostic"></span>
                </div>
                <div class="col-12 form-group  mb-0">
                    <label
                        class="col-form-label pb-0 col-12 pl-0">Observacion</label>
                        <span id="idOrderCanvasDataOrderObservation"></span>
                </div>
            </div>
            <h6 class="divOrdenesPrevias">Órdenes previas</h6>
            <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1 divOrdenesPrevias'>
                <div class="col-5 form-group mb-0">
                        <span id="idOrderCanvasDataPreviousOrders"></span>
                </div>
            </div>
        </div>
    </div>
</div>
