<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade" id="TestCanvasActions" role="tabpanel" aria-labelledby="TestCanvasActions">
    <div class="row">
        <div class="col">
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3">Tipo de muestra</label>
                        <div class="col-lg pl-0">
                            <select id="idTestCanvasActionsSample" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-3">Sitio anat&oacute;mico</label>
                        <div class="col-lg pl-0">
                            <select id="idTestCanvasActionsBodyPart" class="form-control">
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="checkbox checkbox-primary ml-2 ocultar col-lg-5">
                            <input id="" type="checkbox" class="">Fecha de Siembra
                            <span></span>
                        </label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsSeedingDate" type="date"/>
                        </div>
                        <div class="col-lg-3 pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsSeedingTime" type="time"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="checkbox checkbox-primary ml-2 ocultar col-lg-5">
                            <input id="" type="checkbox" class="">Fecha de Resiembra
                            <span></span>
                        </label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsReseedingDate" type="date"/>
                        </div>
                        <div class="col-lg-3 pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsReseedingTime" type="time"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="checkbox checkbox-primary ml-2 ocultar col-lg-5">
                            <input id="" type="checkbox" class="">Fecha de Resultado
                            <span></span>
                        </label>
                        <div class="col-lg pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsResultDate" type="date"/>
                        </div>
                        <div class="col-lg-3 pl-0">
                            <input class="form-control" placeholder="" id="idTestCanvasActionsResultTime" type="time"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <button id="idTestCanvasActionsLabels" type="button" class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5 disabledForm" data-toggle="modal" data-target="#TestCanvasLabels" ><i class="flaticon2-paper"></i>Imprimir Etiquetas</button>
                    </div>

                </div>
            </div>
            <a id="idTestCanvasActionsSign" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="flaticon2-paper"></i>Firmar</a>
            <a id="idTestCanvasActionsSave" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="flaticon2-paper"></i>Guardar</a>
        </div>
    </div>
</div>