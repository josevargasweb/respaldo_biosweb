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
                                    Búsqueda de Zonas del Cuerpo
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnNewBA" class="navi-link">
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloAgregarGlosa" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva glosa">
                                                <i id="iAgregarGlosa" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnClearBAFilter" class="navi-link">
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
                                <!-- <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-3 col-form-label">Código</label>
                                        <div class="col-7">
                                            <input id="txtCodeBAFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div> -->
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre</label>
                                        <div class="col-7">
                                            <input id="txtNameBAFilter" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label class="col-md-12 checkbox checkbox-primary">
                                        <input id="chkActiveBAFilter" type="checkbox" name="activo" value="N" />
                                        Mostrar solo
                                        activos
                                        <span></span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div id="tableBAFilterContainer" class="table-container mb-3">
    
                                    <table id="tableBAFilter" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <!-- <th scope="col">#</th> -->
                                                <th id="thNameTableBAFilter" scope="col">Sitio anat&oacute;mico</th>
                                                <th id="thVerTableBAFilter" scope="col"> </th>
                                            </tr>
                                        </thead>
                                        <tbody id="DataBAFilter">
    
                                        </tbody>
                                    </table>
                                    <label id="lblBAFilterError" class="textError text-red" hidden>No se encontraron
                                        zonas del cuerpo con los datos provistos.</label>
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
                            Registro de Sitio anat&oacute;mico
                        </h3>
                        <div class="col-2 justify-content-right iconos-registro">
                            <div class="float-right">
                                <a id="btnClearBADetails" class="navi-link pointer" >
                                    <span class="symbol symbol-30 symbol-circle mr-3 ">
                                        <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="float-right" >
                                <a id="btnEditBADetails" class="navi-link pointer">
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
					<form id="formBADetails" method="post">
                        <div id="divForm" class="row">
                            <div class="col-12 row gap-1">
                                <div class="col-6">
                                    <label for="">Nombre</label>
                                    <input id="txtNameBADetails" name="ceAbreviado" type="text"
                                    class="form-control col-md-6" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-5">
                                    <label for="">Sort</label>
                                    <input id="txtLISBacBADetails" name="ceLISBacBADetails" type="number"
                                    class="form-control col-md-6" autocomplete="off" placeholder=""
                                    disabled="">
                                </div>
                                <div class="col-12 pl-0">
                                    <div class="col-4">
                                        <div class="row">
                                            <label id="lblEstado" class="col-3 col-form-label">Activo</label>
                                            <div class="col-3">
                                                <label class="switch-content switch-comprobante switch-active"> <input id="cbActiveBADetails" type="checkbox" tabindex="-1" name="cbActiveBADetails" class="formExamen" checked="" value="S">
                                                    <div></div>
                                                </label> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <a id="btnSaveBADetails"
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