<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="TestCanvasNotifications"  >
    <input type="hidden" id="nOrden"/>
    <input type="hidden" id="examenId"/>
    <input type="hidden" id="testId"/>
    <div class="row">
        <div class="col">
            <div class="row col-12 solo-texto">
                <h6>Notificaciones</h6>
                <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                    
                    <div class="col-6 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Resultado</label>
                            <span id="idTestCanvasNotificationsResult"></span>
                    </div>
                    <div class="col-6 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Resultado Crítico</label>
                            <span id="idTestCanvasNotificationsCriticalResult"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Médico</label>
                            <span id="idTestCanvasNotificationsPhysician"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Teléfono</label>
                            <span id="idTestCanvasNotificationsPhysicianPhone"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">e-mail</label>
                            <span id="idTestCanvasNotificationsPhysicianEmail"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Paciente</label>
                            <span id="idTestCanvasNotificationsPatient"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Teléfono</label>
                            <span id="idTestCanvasNotificationsPatientPhone"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">e-mail</label>
                            <span id="idTestCanvasNotificationsPatientEmail"></span>
                    </div>
                </div>
                <h6 class="mt-4">Mensaje de notificación</h6>
            </div>
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Mensaje</label>
                        <div class="col-12 pl-0">
                            <textarea class="form-control" id="idTestCanvasNotificationsMesage" type="text" rows=15></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3 pl-0">Enviar a</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsSendTo" type="text"/>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Notificación</label>
                        <div class="col-lg pl-0">
                            <select id="selectNotificacionTipoReceptor" class="form-control ">
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsName" type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Vía de comunicación</label>
                        <div class="col-lg pl-0">
                            <select id="idTestCanvasNotificationsComunicationChannel" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Intentos de comunicación</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsCommunicationAttempts" type="number" min="1" max="99"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label
                        class="col-form-label pb-0 col-12 pl-0">Nota</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsNote" type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label id="idTestCanvasNotificationsConfirmsReception" class="checkbox checkbox-primary ml-2 ocultar">
                            <input id="" type="checkbox" class="">Confirma Recepción
                            <span></span>
                        </label>
                    </div>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-6">
                    <button type="button" id="testSendMailButton" class="btn btn-blue-primary btn-lg font-weight-bold mr-2"><i class="flaticon2-paper" style="color:#fff;"></i></button>
                    <button type="button" id="testNotificationSaveButton" class="btn btn-blue-primary btn-lg font-weight-bold mr-2"><i class="far fa-save" aria-hidden="true"></i> Guardar</button>
                </div>
                <div class="col-6 d-flex justify-content-end">
                    <button id="btnSalirContador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
</div>