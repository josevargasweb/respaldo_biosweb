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
                                    Búsqueda de Recuentos
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnNewCC" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarGlosa" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                                <i id="iAgregarGlosa" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnClearCCFilter" class="navi-link">
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
                                            <input id="txtCodeCCFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNameCCFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActiveCCFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tableCCFilterContainer" class="table-container mb-3">
    
                                    <table id="tableCCFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <th id="thIdTableCCFilter" scope="col">Código</th>
                                                <th id="thNameTableCCFilter" scope="col">Recuento</th>
                                            </tr>
                                        </thead>
                                        <tbody id="DataCCFilter">
    
                                        </tbody>
                                    </table>
                                    <label id="lblCCFilterError" class="textError text-red" hidden>No se encontraron
                                        recuentos de colonias con los datos provistos.</label>
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
                            Registro de Recuento
                        </h3>
                        <div class="col-2 justify-content-right iconos-registro">
                            <div class="float-right">
                                <a id="btnClearCCDetails" class="navi-link pointer" >
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="float-right" >
                                <a id="btnEditCCDetails" class="navi-link pointer">
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
					<form id="formCCDetails" method="post">
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                <div class="col-4">
                                    <label for="">Código</label>
                                    <input id="txtCodeCCDetails" name="ceCodeCCDetails" type="text"
                                    class="form-control" autocomplete="off" placeholder="">
                                </div>
                                <div class="col-4">
                                    <label for="">Orden</label>
                                    <input id="txtOrderCCDetails" name="ceOrderCCDetails" type="text"
                                        class="form-control" autocomplete="off" placeholder=""
                                        disabled="">
                                </div>
                                <div class="col-4">
                                    <label for="">Nombre</label>
                                    <input id="txtNameCCDetails" name="ceAbreviado" type="text"
                                    class="form-control" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-4 mt-3">
                                    <label for="">LIS Bac.</label>
                                    <input id="txtLISBacCCDetails" name="ceLISBacCCDetails" type="text"
                                    class="form-control" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-6 mt-3">
                                    <div class="row">
                                        <div class="col-12">
                                            <a id="btnAddPwr2"
                                                class="btn btn-light-primary font-weight-bold ml-0 pointer mt-5">
                                                <i class="far"></i>x<sup>2</sup></a>
                                            <a id="btnAddPwr3"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>3</sup></a>
                                            <a id="btnAddPwr4"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>4</sup></a>
                                            <a id="btnAddPwr5"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>5</sup></a>
                                            <a id="btnAddPwr6"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>6</sup></a>
                                            <a id="btnAddPwr7"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>7</sup></a>
                                            <a id="btnAddPwr8"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>8</sup></a>
                                            <a id="btnAddPwr9"
                                                class="btn btn-light-primary font-weight-bold ml-1 pointer mt-5">
                                                <i class="far"></i>x<sup>9</sup></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 mt-3">
                                    <div class="col-4">
                                        <div class="row">
                                            <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active"> <input id="cbActiveCCDetails" type="checkbox" tabindex="-1" name="cbActiveCCDetails" class="formExamen" checked="" value="S">
                                                    <div></div>
                                                </label> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <a id="btnSaveCCDetails"
                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">
                                        <i class="far fa-save"></i>Guardar</a>
                                </div>
                            </div>
                        </div>
                        
                        </form>
                </div>
            <!-- FIN FORMULARIO REGISTRO GLOSAS -->
            </div>
         </div>