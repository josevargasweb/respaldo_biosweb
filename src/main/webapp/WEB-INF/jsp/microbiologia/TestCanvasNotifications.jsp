<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane show fade" id="TestCanvasNotifications" role="tabpanel" aria-labelledby="TestCanvasNotifications">
    <div class="row">
        <div class="col">
            <div class="row col-12 form-group">
                <label class="col-form-label text-left col-lg-4 "><b>Notificaciones</b></label>
            </div>
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3 ">Resultado</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsResult" disabled type="text"/>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Resultado Crítico</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsCriticalResult" disabled type="text"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row col-12">
                <div class="col-4">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Médico</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPhysician" disabled type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Paciente</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPatient" disabled type="text"/>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Teléfono</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPhysicianPhone" disabled type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Teléfono</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPatientPhone" disabled type="text"/>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3 ">e-mail</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPhysicianEmail" disabled type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3 ">e-mail</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsPatientEmail" disabled type="text"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row col-12 form-group">
                <label class="col-form-label text-left col-lg-4 "><b>Mensaje de notificación</b></label>
            </div>
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Mensaje</label>
                        <div class="col-lg pl-0">
                            <textarea class="form-control" id="idTestCanvasNotificationsMesage" type="text" rows=15></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4 ">Enviar a</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsSendTo" type="text"/>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4">Notificación a</label>
                        <div class="col-lg pl-0">
                            <select id="selectTipoDocumentoFiltro" class="form-control selectpicker">
                                <option value="1" >Seccion Uno</option>
                                <option value="2" >Seccion Dos</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4">Nombre</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsName" type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4">Vía de comunicación</label>
                        <div class="col-lg pl-0">
                            <select id="idTestCanvasNotificationsComunicationChannel" class="form-control selectpicker">
                                <option value="1" >Seccion Uno</option>
                                <option value="2" >Seccion Dos</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4">Intentos de comunicación</label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasNotificationsCommunicationAttempts" type="text"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-4">Nota</label>
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
            <a id="testSendMailButton" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="flaticon2-paper"></i>Enviar</a>
        </div>
    </div>
</div>