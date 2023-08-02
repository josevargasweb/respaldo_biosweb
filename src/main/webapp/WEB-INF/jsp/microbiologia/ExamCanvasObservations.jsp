<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade show active solo-texto" id="ExamCanvasObservations" role="tabpanel" aria-labelledby="OrderCanvasData">
    <div class="row">
        <div class="col">
            <div class="col-12">
                <div class="row">
                    <h5 class=""> Datos del Paciente</h5>
                    <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                        <div class="col-12 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                                <span id="idExamCanvasObservationsPatientName"></span>
                        </div>
                        <div class="col-12 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                <span id="idExamCanvasObservationsPatientObservation"></span>
                        </div>
                    </div>
                    <h5 class=""> Datos de la Orden</h5>
                    <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">N° Orden</label>
                                <span id="idExamCanvasObservationsOrderId"></span>
                        </div>
                        
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Usuario</label>
                                <span id="idExamCanvasObservationsOrderUser"></span>
                        </div>
                     
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                <span id="idExamCanvasObservationsOrderObservation"></span>
                        </div>
                     
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                                <span id="idExamCanvasObservationsOrderDate"></span>
                        </div>
                        <div class="col-6 form-group mb-0">
                          <label
                                class="col-form-label pb-0 col-12 pl-0"> Examen </label>
                                <strong><span id="idExamCanvasObservationsOrderExam"></span></strong>
                        </div>
                     
                    </div>
                    <h5>Toma de Muestra</h5>
                    <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                <span id="idExamCanvasObservationsSamplingObservation"></span>
                        </div>
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Usuario</label>
                                <span id="idExamCanvasObservationsSamplingUser"></span>
                        </div>
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Estado</label>
                                <span id="idExamCanvasObservationsSamplingStatus"></span>
                        </div>
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                                <span id="idExamCanvasObservationsSamplingDate"></span>
                        </div>
                    </div>
                    <h5>Recepción de Muestra</h5>
                    <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                <span id="idExamCanvasObservationsReceptionObservation"></span>
                        </div>
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Usuario</label>
                                <span id="idExamCanvasObservationsReceptionUser"></span>
                        </div>
                        <div class="col-8 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Estado</label>
                                <span id="idExamCanvasObservationsReceptionStatus"></span>
                        </div>
                        <div class="col-4 form-group mb-0">
                            <label
                                class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                                <span id="idExamCanvasObservationsReceptionDate"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>