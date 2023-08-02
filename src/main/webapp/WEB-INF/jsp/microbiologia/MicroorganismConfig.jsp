<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <div class="accordion col-12 pl-0 pr-0" id="accordionBusquedaGlosa">
                <div class="card border-15">
                    <div class="card-header border-15" id="headingOne8">
                        <div class="card-title d-flex justify-content-between">
                            <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseOne8" aria-expanded="true"
                                    aria-controls="collapseHeader">
                                    Búsqueda de Microorganismos
                            </h3>
                            <div class="card-buttons col-2 d-flex justify-content-end">
                                <a id="btnNewMO" class="navi-link">
                                    <span class='symbol symbol-30 symbol-circle'>
                                        <span id="circuloAgregarGlosa" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                            <i id="iNewMO" class='fas fa-plus text-blue'></i>
                                        </span>
                                    </span>
                                </a>
                                <a id="btnClearMOFilter" class="navi-link">
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circleNewMO" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                            <i id="iClearMOFilter" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>

                        </div>
                    </div>
                    <div id="collapseOne8" class="collapse show container-content" data-parent="#accordionBusquedaGlosa" >
                        <div class="card-body row">
                            <div class="col-6">
                                <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-3 col-form-label">Código</label>
                                        <div class="col-7">
                                            <input id="txtCodeMOFilter" class="form-control" name="" placeholder=""
                                                    type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNameMOFilter" class="form-control" name="" placeholder=""
                                            type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActiveMOFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tableAGFilterContainer" class="table-container mb-2">
                                    <table id="tableMOFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <th id="thIdTableFilter" scope="col">Código</th>
                                                <th id="thNameTableFilter" scope="col">Microorganismo</th>
                                            </tr>
                                        </thead>
                                        <tbody id="DataMOFilter">
    
                                        </tbody>
                                    </table>
                                    <label id="lblAGFilterError" class="textError text-red" hidden>No se encontraron
                                        antibiogramas con los datos provistos.</label>
                                </div>
                                <label class="mb-encontrados"></label>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="panelInferior" class="card mt-5 border-15">
                    <div class="card-header border-15">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 class="card-label col-4 pl-0">
                                Registro de Microorganismo
                            </h3>
                            <div class="justify-content-right iconos-registro">
                                <div class="float-right">
                                    <div id="divbtnLimpiar">
                                        <a id="btnClearMODetails" class="navi-link pointer">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleClearMODetails" data-toggle="tooltip" title="" class="symbol-label bg-hover-blue " data-original-title="Limpiar">
                                                    <i id="iLimpiar" class="la la-broom icon-xl  text-blue"></i></span>
                                            </span> <span class="navi-text"></span>
                                        </a>
                                    </div>
                                </div>   
                                <div class="float-right">
                                    <div id="divbtnEditar">
                                        <a id="btnEditMODetails" class="navi-link pointer"> 
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleEditMODetails" class="symbol-label circuloIcon" data-toggle="tooltip" title="" data-original-title="Editar Muestra">
                                                    <i id="iEditMO" class="far fa-edit text-dark-50" aria-hidden="true"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                                <!-- modificar no se encuentra creado -->
                                <div class="float-right">
                                    <div id="divbuscarMuestra">
                                        <a id="buscarMuestra" class="navi-link" href="#">
                                            <span class="symbol symbol-30 symbol-circle mr-3">
                                                <span id="circuloBuscarMuestra" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="" data-original-title="Buscar Muestra">
                                                    <i id="iBuscarMuestra" class="fas fa-search text-blue" aria-hidden="true"></i>
                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </div>
                                <!-- modificar no se encuentra creado -->
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="formMODetails" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12 row gap-1">
                                    <div class="col-6">
                                        <label for="">Código</label>
                                        <input id="txtCodeMODetails" name="ceCodeMODetails" type="text"
                                        class="form-control col-md-7" autocomplete="off" placeholder="">
                                    </div>
                                    <div class="col-5">
                                        <label for="">LIS Bac.</label>
                                        <input id="txtLISBacMODetails" name="ceLISBacMODetails" type="text"
                                            class="form-control col-md-7" autocomplete="off" placeholder="">
                                            <input type="hidden" id="txtIdMODetails" value="" disabled="true">
                                    </div>
                                    <div class="col-6">
                                        <label class="col-form-label">Nombre </label>
                                            <input id="txtNameMODetails" name="ceAbreviado" type="text"
                                                class="form-control col-md-7" autocomplete="off" placeholder="">
                                    </div>
                                    <h5 class="ch4 col-12 mt-3">Classificación</h5>
                                    <div class="col d-flex flex-wrap gap-1">
                                        <div class="col-12 p-0">
                                            <label class="">Tinción Gram</label>
                                            <select id="txStainingMODetails" class="form-control col-md-7">
                                                <option value="">-</option>
                                                <option value="Tincion 1">Tinción 1</option>
                                                <option value="Tincion 2">Tinción 2</option>
                                            </select>
                                        </div>
                                        <div class="col-12 p-0">
                                            <label for="">Morfología</label>
                                            <select id="txMorphologyDetails" class="form-control col-md-7">
                                                <option value="">-</option>
                                                <option value="Morfologia 1">Morfología 1</option>
                                                <option value="Morfologia 2">Morfología 2</option>
                                            </select>
                                        </div>
                                        <div class="col-12 p-0">
                                            <label for="">Género</label>
                                            <select id="txGenderDetails" class="form-control col-md-7">
                                                <option value="">-</option>
                                                <option value="Genero 1">Género 1</option>
                                                <option value="Genero 2">Género 2</option>
                                            </select>
                                        </div>
                                        <div class="col-12 pl-0">
                                            <div class="row">
                                                <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                                <div class="col-3">
                                                    <label class="switch-content switch-comprobante switch-active"> <input id="cbActiveMODetails" type="checkbox" tabindex="-1" name="cbActiveMODetails" class="formExamen" checked="" value="S">
                                                        <div></div>
                                                    </label> 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="col-12">
                                            <label for="">Nota</label>
                                            <textarea class="form-control" id="idNoteMODetails" type="text"
                                            rows="10"
                                            style="margin-top: 0px; margin-bottom: 0px; height: 180px;"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <button id="btnSaveMODetails" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " disabled=""><i class="far fa-save" aria-hidden="true"></i>Guardar</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
