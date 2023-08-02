<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade" id="OrderCanvasActions" role="tabpanel" aria-labelledby="OrderCanvasEvents">
    <div class="row">
        <div class="col">
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Firmar resultados"><i id="iLimpiar" class="far fa-edit icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-4 ">Firmar resultados</label>
                    </div>
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Autorizar resultados"><i id="iLimpiar" class="flaticon2-paper icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-4 ">Autorizar resultados</label>
                    </div>
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Tareas manuales"><i id="iLimpiar" class="flaticon2-copy icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-4 ">Tareas manuales</label>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Retirar firma"><i id="iLimpiar" class="la la-broom icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-4 ">Retirar firma</label>
                    </div>
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Bloquear test"><i id="iLimpiar" class="flaticon2-copy icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-4 ">Bloquear test</label>
                    </div>
                </div>
            </div>
            <div class="row col-12">
                <div class="col">
                    <div class="form-group row">
                        <div class="ml-1 float-right">
                            <a id="btnLimpiar" class="navi-link pointer" >
                                <span class="symbol symbol-50 symbol-circle mr-3 ">
                                    <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Firmar resultados"><i id="iLimpiar" class="flaticon2-paper icon-xl  text-primary"></i></span>
                                </span>
                                <span class="navi-text"></span>
                            </a>
                        </div>   
                        <label class="col-form-label text-left col-lg-2 ">Imprimir</label>
                        <div class="col-lg-2 pl-0">
                            <select id="selectTipoDocumentoFiltro" class="form-control selectpicker">
                                <option value="1" >Vista previa</option>
                                <option value="2" >Preinforme</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>