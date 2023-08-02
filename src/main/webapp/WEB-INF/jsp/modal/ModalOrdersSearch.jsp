<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="ModalOrdersSearch" class="accordion accordion-solid accordion-panel accordion-svg-toggle" >
    <div class="card card-custom gutter-b">
        <div id="collapseOne8" class="collapse show" data-parent="#accordionExample8">
            <div class="card-body row">
                <div class="col">
                    <div class="row col-12">
                        <div class="col">
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Nº Orden Inicio:</label>
                                <div class="col-lg-7 pl-0">
                                    <input class="form-control" placeholder="" id="nOrden" type="text"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Nº Orden Fin:</label>
                                <div class="col-lg-7 pl-0">
                                    <input class="form-control" placeholder="" id="nOrdenFin" type="text"/>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4">Fecha Inicio</label>
                                <div class="col-lg-7 pl-0">
                                    <input id="fIni" type="date" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4">Fecha Fin</label>
                                <div class="col-lg-7 pl-0">
                                    <input id="fFin" type="date" class="form-control"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row col-12">
                        <div class="col">
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Nombres</label>
                                <div class="col-lg-7 pl-0">
                                    <input class="form-control" placeholder="" id="nombre" type="text"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Apellidos</label>
                                <div class="col-lg-7 pl-0">
                                    <input class="form-control" placeholder="" id="apellido" type="text"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Sección</label>
                                <div class="col-lg-7 pl-0">
                                    <select id="idSearchSection" class="form-control">
                                        <option value="" >-</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Examen</label>
                                <div class="col-lg-7 pl-0">
                                    <select id="idExamen" class="form-control">
                                        <option value="" >-</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4">Tipo Documento</label>
                                <div class="col-lg-7 pl-0">
                                    <select id="tipoDocumento" class="form-control">
                                        <option value="" >-</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4">Nº Documento</label>
                                <div class="col-lg-7 pl-0">
                                    <input class="form-control" placeholder="" id="nroDocumento" type="text"/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Servicio</label>
                                <div class="col-lg-7 pl-0">
                                    <select id="idServicio" class="form-control">
                                        <option value="" >-</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-4 ">Tipo de Atención</label>
                                <div class="col-lg-7 pl-0">
                                    <select id="tipoAtencion" class="form-control">
                                        <option value="" >-</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-group row">
                            <div class="col">
                                <div class="form-group row">
                                    <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                        <input id="idSearchPending" name="exitus" type="checkbox" class="">Pendientes
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group row">
                                    <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                        <input id="idSearchForSignature" name="exitus" type="checkbox" class="">Para Firma
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a id="searchModalOrderButton" href="#" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="la la-search"></i>Buscar</a>
                </div>
            </div>
        </div>
    </div>
</div>