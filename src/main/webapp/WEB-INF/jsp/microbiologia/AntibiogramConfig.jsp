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
                                    Búsqueda de Antibiogramas
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnNewAG" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarGlosa" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                                <i id="iAgregarGlosa" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnClearAGFilter" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle mr-3 ">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
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
                                            <input id="txtCodeAGFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNameAGFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActiveAGFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tableAGFilterContainer" class="table-container mb-3">
                                    <table id="tableAGFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <!-- <th scope="col">#</th> -->
                                                <th id="thIdTableAGFilter" scope="col">Código</th>
                                                <th id="thNameTableAGFilter" scope="col">Antibiograma</th>
                                                <!-- <th id="thVerTableAGFilter" scope="col"> </th> -->
                                            </tr>
                                        </thead>
                                        <tbody id="DataAGFilter">
    
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
                    <div class="card mt-5 border-15">
                <div class="card-header border-15"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                             Registro de Antibiograma
                        </h3>
                        <div class="col-2 justify-content-right iconos-registro">
                            <div class="float-right">
                                <a id="btnClearAGDetails" class="navi-link pointer" >
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="float-right" >
                                <a id="btnEditAGDetails" class="navi-link pointer">
                                    <span class='symbol symbol-30 symbol-circle' >
                                        <span id="circuloEditarGlosa" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar glosa">
                                            <i id="iEditGlosa" class="far fa-edit text-dark-50"></i>
                                        </span>
                                    </span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
					<form id="formAGDetails" method="post">
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                <div class="col-6">
                                    <label for="">Código</label>
                                    <input id="txtCodeAGDetails" name="ceCodeAGDetails" type="text"
                                    class="form-control " autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-6">
                                    <label for="">Orden</label>
                                    <input id="txtOrderAGDetails" name="ceOrderAGDetails" type="text"
                                    class="form-control" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-6 mt-3">
                                    <label for="">Nombre</label>
                                    <input id="txtNameAGDetails" name="ceAbreviado" type="text"
                                    class="form-control" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-6 mt-3">
                                    <label for="">LIS Bac.</label>
                                    <input id="txtLISBacAGDetails" name="ceLISBacAGDetails" type="text"
                                                class="form-control" autocomplete="off" placeholder=""
                                                disabled="">
                                </div>
                                <div class="col-12 mt-3">
                                    <div class="row justify-content-end">
                                        <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                        <div class="col-3">
                                            <label class="switch-content switch-comprobante switch-active"> <input id="cbActiveAGDetails" type="checkbox" tabindex="-1" name="cbActiveAGDetails" class="formExamen" checked="" value="S">
                                                <div></div>
                                            </label> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card card-2 card-custom gutter-b shadow-none p-2">
                            <div class="card-body pt-0">
                                <div class="row" class="form-group row mt-5 mx-auto col-8" >
                                    <div class="col-6">
                                        <label>Antibióticos Disponibles</label>
                                        <table id="tableAvailableAGDetails" class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col"> </th>
                                                    <th scope="col">Antibioticos Disponibles</th>
                                                </tr>
                                                <tr>
                                                    <th scope="col"> </th>
                                                    <th scope="col">
                                                        <input id="txtSearchAvailableAGDetails" type="text" placeholder=""
                                                            class="column_search" />
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody id="tableDataAvailableAGDetails">
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-1">
                                        <div class="row mt-35" style="height:40px">
                                                <button id="btnAddABAGDetails" type="button" class="btn btn-blue-primary w-100">
                                                    Agregar <i class="fas fa-arrow-right" tabindex="-1" aria-hidden="true"></i>
                                                </button>
                                        </div>
                                        <div class="row mt-5" style="height:40px">
    
                                                <button id="btnRemoveABAGDetails" type="button" class="btn btn-blue-primary w-100">
                                                    <i class="fas fa-arrow-left" tabindex="-1" aria-hidden="true"></i> Quitar
                                                </button>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <label>Antibióticos Seleccionados</label>
                                        <table id="tableSelectedAGDetails" class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">#</th>
                                                    <th scope="col">Antibiótico</th>
                                                    <th scope="col">Opcional</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tableDataSelectedAGDetails">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <div class="row-12">
                                <div class="col">
                                    <div class="row">
                                        <div class="col-12">
                                            <a id="btnSaveAGDetails"
                                                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">
                                                <i class="far fa-save"></i>Guardar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                </div>
            <!-- FIN FORMULARIO REGISTRO GLOSAS -->
            </div>
        </div>