<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <div class="accordion col-12 pl-0 pr-0" id="PMSettings">
                <div class="card border-15">
                    <div class="card-header border-15" id="headingOne8">
                        <div class="card-title d-flex justify-content-between">
                            <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseOne8" aria-expanded="true"
                                    aria-controls="collapseHeader">
                                    Búsqueda de Pruebas
                            </h3>
                            <div class="card-buttons col-2 d-flex justify-content-end">
                                <a id="btnNewPM" class="navi-link">
                                    <span class='symbol symbol-30 symbol-circle'>
                                        <span id="circleNewPM" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                            <i id="iNewPM" class='fas fa-plus text-blue'></i>
                                        </span>
                                    </span>
                                </a>
                                <a id="btnClearPMFilter" class="navi-link">
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circleClearPMFilter" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                            <i id="iClearPMFilter" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>

                        </div>
                    </div>
                    <div id="collapseOne8" class="collapse show container-content" data-parent="#accordionExample8" >
                        <div class="card-body row">
                            <div class="col-6">
                                <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-3 col-form-label">Código</label>
                                        <div class="col-7">
                                            <input id="txtCodePMFilter" class="form-control" name="" placeholder=""
                                                    type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNamePMFilter" class="form-control" name="" placeholder=""
                                            type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActivePMFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tablePMFilterContainer" class="table-container mb-3">
                                    <table id="tablePMFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <!-- <th scope="col">#</th> -->
                                                <th id="thIdTablePMFilter" scope="col">Código</th>
                                                <th id="thNameTablePMFilter" scope="col">Prueba Manual</th>
                                                <!-- <th id="thVerTablePMFilter" scope="col"> </th> -->
                                            </tr>
                                        </thead>
                                        <tbody id="DataPMFilter">
    
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
                                Registro de Prueba
                            </h3>
                            <div class="justify-content-right iconos-registro">
                                <div class="float-right">
                                    <div id="divbtnLimpiar">
                                        <a id="btnClearPMDetails" class="navi-link pointer">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleClearPMDetails" data-toggle="tooltip" title="" class="symbol-label bg-hover-blue " data-original-title="Limpiar">
                                                    <i id="iLimpiar" class="la la-broom icon-xl  text-blue"></i></span>
                                            </span> <span class="navi-text"></span>
                                        </a>
                                    </div>
                                </div>   
                                <div class="float-right">
                                    <div id="divbtnEditar">
                                        <a id="btnEditPMDetails" class="navi-link pointer"> 
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleEditPMDetails" class="symbol-label circuloIcon" data-toggle="tooltip" title="" data-original-title="Editar Muestra">
                                                    <i id="iEditPM" class="far fa-edit text-dark-50" aria-hidden="true"></i>
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
                        <form id="formPMDetails" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12 row gap-1">
                                    <div class="col-6">
                                        <label for="">Código</label>
                                        <input id="txtCodePMDetails" name="ceCodePMDetails" type="text"
                                        class="form-control col-md-8" autocomplete="off" placeholder=""
                                        disabled="">
                                    </div>
                                    <div class="col-5">
                                        <label for="">LIS Bac.</label>
                                        <input id="txtLISBacPMDetails" name="ceLISBacPMDetails" type="text"
                                                        class="form-control col-md-8" autocomplete="off" placeholder=""
                                                        disabled="">
                                    </div>
                                    <div class="col-6">
                                        <label for="">Nombre</label>
                                        <input id="txtNamePMDetails" name="ceAbreviado" type="text"
                                                        class="form-control col-md-8" autocomplete="off" placeholder=""
                                                        disabled="">
                                    </div>
                                    <div class="col-5">
                                        <div class="row">
                                            <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                            <label class="switch-content switch-comprobante switch-active"> <input id="cbActivePMDetails" type="checkbox" tabindex="-1" name="cbActivePMDetails" class="formExamen" checked="" value="S">
                                                <div></div>
                                            </label> 
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <button id="btnSavePMDetails" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " disabled=""><i class="far fa-save" aria-hidden="true"></i>Guardar</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            