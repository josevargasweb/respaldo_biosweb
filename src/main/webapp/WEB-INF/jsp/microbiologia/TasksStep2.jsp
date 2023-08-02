<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step2 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep2ExamName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Paciente:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep2PatientName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">N° Orden:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep2OrderN" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">N° Etiqueta:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep2ExamLabel" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-4 col-form-label">Re-asignar fecha:</label>
                                <div class="col-8">
                                    <input id="dateTaskStep2Date" type="date" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Tipo de Muestra:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep2SampleType" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label">Prefijo Muestra:</label>
                                <div class="col-9">
                                    <input disabled="true" id="txtTaskStep2SamplePrefix" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col">
                                    <div class="ml-1 float" style="text-align: center; float: none;">
                                        <a id="btnStep2PrintExam" class="navi-link">
                                            <span class="symbol symbol-50 symbol-circle mr-3 ">
                                                <span id="spnStep2PrintExam"
                                                    class="symbol-label bg-hover-primary  hoverIcon"
                                                    href="javascript:void(0)" data-toggle="tooltip"
                                                    title="Imprimir Etiquetas">
                                                    <i id="iClearAGFilter" class="la la-print icon-xl text-primary">
                                                    </i>
                                                </span>
                                            </span>
                                            <span class="navi-text"></span>
                                        </a>
                                        <label>Impresión de Etiquetas</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">

                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep2Preview"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep2Preview" style="float:left;">
                                    ¿Vista previa?
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep2IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep2IsReady" style="float:left;">
                                    Etapa Completada
                                </label>
                            </div>
                        </div>
                    </div>
                </div>