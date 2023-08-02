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
                                    Búsqueda de Antibióticos
                            </h3>
                            <div class="card-buttons col-2 d-flex justify-content-end">
                                <a id="btnNewAB" class="navi-link">
                                    <span class='symbol symbol-30 symbol-circle'>
                                        <span id="circleNewAB" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                            <i id="iNewAB" class='fas fa-plus text-blue'></i>
                                        </span>
                                    </span>
                                </a>
                                <a id="btnClearABFilter" class="navi-link">
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circleClearABFilter" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                            <i id="iClearABFilter" class="la la-broom icon-xl text-blue"></i>
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
                                            <input id="txtCodeABFilter" class="form-control" name="" placeholder=""
                                                    type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNameABFilter" class="form-control" name="" placeholder=""
                                            type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Clase</label>
                                        <div class="col-7">
                                            <select id="txClassABFilter" class="form-control">
                                                <option value="">-</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActiveABFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tableABFilterContainer" class="table-container mb-3">
                                    <table id="tableABFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <th id="thIdTableABFilter" scope="col">Código</th>
                                                <th id="thNameTableABFilter" scope="col">Antibiótico</th>
                                            </tr>
                                        </thead>
                                        <tbody id="DataABFilter">
    
                                        </tbody>
                                    </table>
                                    <label id="lblABFilterError" class="textError text-red" hidden>No se encontraron
                                        microorganismos con
                                        los
                                        datos provistos.</label>
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
                                Registro de Antibiótico
                            </h3>
                            <div class="justify-content-right iconos-registro">
                                <div class="float-right">
                                    <div id="divbtnLimpiar">
                                        <a id="btnClearABDetails" class="navi-link pointer">
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleClearABDetails" data-toggle="tooltip" title="" class="symbol-label bg-hover-blue " data-original-title="Limpiar">
                                                    <i id="iLimpiar" class="la la-broom icon-xl  text-blue"></i></span>
                                            </span> <span class="navi-text"></span>
                                        </a>
                                    </div>
                                </div>   
                                <div class="float-right">
                                    <div id="divbtnEditar">
                                        <a id="btnEditABDetails" class="navi-link pointer"> 
                                            <span class="symbol symbol-30 symbol-circle">
                                                <span id="circleEditABDetails" class="symbol-label circuloIcon" data-toggle="tooltip" title="" data-original-title="Editar Muestra">
                                                    <i id="iEditAB" class="far fa-edit text-dark-50" aria-hidden="true"></i>
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
                        <form id="formABDetails" method="post">
                            <div id="divForm" class="row">
                                <div class="col-12 row gap-1">
                                    <div class="col-4">
                                        <label for="">Código</label>
                                        <input id="txtCodeABDetails" name="ceCodeABDetails" type="text"
                                        class="form-control" autocomplete="off" placeholder="">
                                    </div>
                                    <div class="col-4">
                                        <label for="">Clase</label>
                                        <select id="txClassABDetails" class="form-control">
                                            <option value="">-</option>
                                            <option value="Clase 1">Clase 1</option>
                                            <option value="Clase 2">Clase 2</option>
                                        </select>
                                    </div>
                                    <div class="col-4">
                                        <label class="col-form-label">LIS Bac. </label>
                                        <input id="txtLISBacABDetails" name="ceLISBacABDetails" type="text"
                                        class="form-control" autocomplete="off" placeholder="">
                                        <input type="hidden" id="txtIdABDetails" value="" disabled="true">
                                    </div>
                                    <div class="col-4">
                                        <label class="col-form-label">Nombre</label>
                                        <input id="txtNameABDetails" name="ceAbreviado" type="text"
                                        class="form-control" autocomplete="off" placeholder="">
                                    </div>
                                    <div class="col-4">
                                        <label class="col-form-label">Orden</label>
                                        <input id="txtOrderABDetails" name="ceOrder" type="text"
                                            class="form-control" autocomplete="off" placeholder="">
                                    </div>
                                    <div class="col-4">
                                        <div class="row">
                                            <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active"> <input id="cbActiveABDetails" type="checkbox" tabindex="-1" name="cbActiveABDetails" class="formExamen" checked="" value="S">
                                                    <div></div>
                                                </label> 
                                            </div>
                                        </div>
                                    </div>
                                    <h5 class="ch4 col-12 mt-3">Resistencia</h5>
                                    <div class="col-12 align-items-center">
                                        <div class="row-12 form-group align-items-center row">
                                            <div class="col-2 align-items-righ">
                                            </div>
                                            <div class="col-2">
                                                <input type="radio" id="radMethodABDetails3" name="radTypeABDetails"
                                                    value="3" checked>
                                                <label for="radMethodABDetails3">Kirby Bauer</label>
                                            </div>
                                            <div class="col-2">
                                                <input type="radio" id="radMethodABDetails2" name="radTypeABDetails"
                                                    value="2">
                                                <label for="radMethodABDetails2">CIM</label>
                                            </div>
                                            <div class="col-2">
                                                <input type="radio" id="radMethodABDetails1" name="radTypeABDetails"
                                                    value="1">
                                                <label for="radMethodABDetails1">e-test</label>
                                            </div>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <select id="txMOABDetailsReference" class="form-control" hidden="" disabled="">
                                            <option value="">-</option>
                                        </select>
                                        <div id="tableABDetailsContainer" class="col-10 scroll scroll-pull ps"
                                            data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false"
                                            style="height: 270px; overflow: hidden;text-align: center">
                                            <table id="tableABDetails" class="table table-hover">
                                                <thead>
                                                    <tr id="trHeader">
                                                        <th scope="col" rowspan="2">Microorganismo</th>
                                                        <th id="thIdTableABDetails" scope="col" colspan="3">Halo</th>
                                                    </tr>
                                                    <tr>
                                                        <th scope="col">Sensible</th>
                                                        <th scope="col">Intermedio</th>
                                                        <th scope="col">Resistente</th>
    
                                                    </tr>
                                                </thead>
                                                <tbody id="DataABDetails">
                                                </tbody>
                                            </table>
                                            <label id="lblABDetailsError" class="textError text-red" hidden="">No se
                                                encontraron
                                                antibioticos con
                                                los
                                                datos provistos.</label>
                                            <div class="ps__rail-x" style="left: 0px; bottom: 0px;">
                                                <div class="ps__thumb-x" tabindex="0" style="left: 0px; width: 0px;"></div>
                                            </div>
                                            <div class="ps__rail-y" style="top: 0px; right: -2px;">
                                                <div class="ps__thumb-y" tabindex="0" style="top: 0px; height: 0px;"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <button id="btnSaveABDetails" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " disabled=""><i class="far fa-save" aria-hidden="true"></i>Guardar</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
