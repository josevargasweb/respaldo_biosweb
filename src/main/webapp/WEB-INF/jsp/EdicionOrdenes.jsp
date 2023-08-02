<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BiosLIS | Modificar orden</title>
        <jsp:include page="common/Styles_1.jsp"/>
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                    <jsp:include page="common/Menu_dinamico.jsp"/>
            </div>
            <div class="col-md-6">
                    <jsp:include page="common/Header_1.jsp" />
            </div>
	    </div>
        <jsp:include page="common/AlertaSession.jsp"/>
        <div class="container container-main-content container-edicion-ordenes">
            <input type="hidden" id="sesionUsuario" name="sesionUsuario" value="${usuario.duIdusuario}" />
            <!-- VARIABLES DE PERMISOS DE EDICIÓN ORDENES -->
            <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
            <input type="hidden" id="editaSoloOrdsProcedencia" name="editaSoloOrdsProcedencia" value="${perfilUsuario.cpuEditasoloordsprocedencia}" />
            <input type="hidden" id="anulaExamenes" name="anulaExamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
            <!-- -->
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0" id="edicion-orden-main">
                    <div id="panelSuperior" class="card">
                        <div class="card-header mb-0 border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10"
                                    data-toggle="collapse"
                                    data-target="#panelBusquedaOrden" aria-expanded="true"
                                    aria-controls="panelBusquedaOrden">B&uacute;squeda de Orden</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnLimpiarFiltro" class="navi-link"> <span
                                        class="symbol symbol-30 symbol-circle "> <span
                                            id="circuloLimpiarFiltro"
                                            class="symbol-label bg-hover-blue  hoverIcon" href="#"
                                            data-toggle="tooltip" title="Limpiar"> <i
                                                id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span> <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="panelBusquedaOrden"
                        class="collapse container-content show"
                        aria-labelledby="edicion-orden-title"
                        data-parent="#edicion-orden-main">
                            <div class="card-body">
                                <div class="col-12">
                                    <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
                                     <input type="hidden" id="pideautorizeditorden" name="pideautorizeditorden" value="${perfilUsuario.cpuPideautorizeditorden}" />
                                      <input type="hidden" id="anulaExamenes" name="anulaExamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
                                      <input type="hidden" id="puedeEditarORden" name="puedeEditarORden" value="${perfilUsuario.cpuEditasoloordsprocedencia}" />
                                      <input type="hidden" id="registraExamanesDerivados" name="registraExamanesDerivados" value="${perfilUsuario.cpuRegistraexamenesderivados}" />
                                      <input type="hidden" id="eliminarexamenes" name="eliminarexamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
                                    <input type="hidden" id="idSeccion" value="${idSeccion}" /> 
                                    <jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
                                </div>
                            </div>
                        </div>
                    </div>

                                <!-- ********************************************************* EDICION DE ORDENES ***********************************************-->


                    <div id="panelInferior" class="card">
                        <div class="card-header " id="headingRegistroPaciente">
                            <div class="card-title row justify-content-between  ">
                                <h3 class="card-label col-3 pl-0">Edici&oacute;n de Orden</h3>
                                                    <!-- Inicio Tabuladores -->
                                                    <div class="col-7">
                                                        <ul class="nav nav-pills">
                                                            <li class="nav-item"><a id="edicionOrdenBasico"
                                                                    class="nav-link active" data-toggle="tab"
                                                                    href="#tab_pane_datos_basicos"> <span
                                                                        class="nav-icon"><i
                                                                            class="flaticon2-paper"></i></span> <span
                                                                        class="nav-text">Datos&nbsp;
                                                                        Básicos</span>
                                                                </a></li>
                                                            <li class="nav-item"><a id="edicionOrdenExamanes" class="nav-link" data-toggle="tab"
                                                                    href="#tab_pane_datos_extras"> <span class="nav-icon"><i
                                                                            class="flaticon2-copy"></i></span> <span
                                                                        class="nav-text">Selecci&oacute;n de ex&aacute;menes</span>
                                                                </a></li>
                                                        </ul>

                                                    </div>
                                                    <!-- Fin Tabuladores -->

                            </div>
                        </div>
                        <!--  ********************************************************** Edición de Orden *****************************************************************  -->
                        <div id="panelResultadoOrden ">
                            <div class="card-body no-gutters">
                                <form id="formEdicionOrden" method="post">
                                    <input id="idPaciente" name="datosPacientes" type="hidden" /> <input
                                    id="idMedico" type="hidden" name="dfIdmedico" /> <input
                                    id="txtEdad" type="hidden" /> <input id="txtEmailP"
                                    name="dfEnviarresultadosemail" type="hidden" /> <input
                                    id="sexoPacienteOrden" type="hidden" /> <input
                                    id="txtPrimerApellidoP" type="hidden" />
                                    <input type="hidden" id="ipEquipo" name="ipEquipo"> <input
                                        id="txtIdPaciente" name="dpIdpaciente" type="hidden" value="0" />
                                    <input type="hidden" id="dpIdmadre" name="dpIdmadre">
									<input type="hidden" id="dfOrden" name="dfOrden">
									<input type="hidden" id="fechaOrden" name="fechaOrden">
                                    <div class="row" id="divForm">
                                        <!-- inicio Formulario registro -->
                                        <!-- Fin Formulario registro -->

                                        <!-- Inicio Panel Tabulados -->
                                        <div class="col-12">
                                            <div class="tab-content " id="myTabContent">

                                                <!-- Inicio Panel Datos básicos -->
                                                <div class="row tab-pane fade show active"
                                                    id="tab_pane_datos_basicos" role="tabpanel"
                                                    aria-labelledby="tab_pane_datos_basicos">
                                                    <div class="col-12">
                                                        <div class="row">
                                                            <div class="col-6">
                                                                <div class="card card-2">
                                                                    <div class="card-body">
                                                                        <h3 class="col-12 pl-0 pr-0">Datos del Paciente</h3>
                                                                        <div class="row filaNombres-container" id="filaNombres">
                                                                            <div class="col ">
                                                                                <div id="divNombreCompleto">
                                                                                    <div class="col-12">
                                                                                        <div class="row">
                                                                                            <div class="col-4 p-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Tipo de documento</label>
                                                                                                <span id="tipo_documento"></span>
                                                                                            </div>
                                                                                            <div class="col-4 p-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Nro Documento</label>
                                                                                                <span id="numero_doc"></span>
                                                                                            </div>
                                                                                            <div class="col-4 p-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                                                                                                <span id="nombre_orden"></span>
                                                                                            </div>
                                                                                            <div class="col-2 p-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Sexo</label>
                                                                                                <span id="sexo_orden"></span>
                                                                                            </div>
                                                                                            <div class="col-2 pl-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Edad</label>
                                                                                                <span id="edad_orden"></span>
                                                                                            </div>
                                                                                            <div class="col-4 p-0">
                                                                                                <label id="" class="col-form-label pb-0 col-12 pl-0">Email</label>
                                                                                                <span id="email_orden"> </span>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12 p-0 mt-3">
                                                                                        <div class="form-group form-check">
                                                                                            <input type="checkbox" class="form-check-input disabledForm" id="chbxRestutToMail">
                                                                                            <label class="form-check-label disabled" for="chbxRestutToMail">¿Envío de resultado al mail?</label>
                                                                                            </div>
                                                                                    </div>
                                    
                                                                                </div>
                                    
                                                                                <input id="txtNombreP" type="hidden"
                                                                                    class="form-control cleanPaciente" placeholder="" />
                                    
                                                                            </div>
                                                                        </div>
                                                                        <div class="row" id="filaAtencionProcedencia">
                                                                            <div class="col-6">
                                                                                <label>Tipo de atenci&oacute;n </label> 
                                                                                <select class="form-control selectpicker disabledForm" id="tipoDeAtencion" tabindex="1" data-live-search="true">
                                                                                </select>
                                                                                <!-- <input
                                                                                    class="form-control typeahead disabledForm " id="tipoDeAtencion"
                                                                                    type="text" tabindex="1"> -->
                                                                            </div>
                                    
                                                                            <div class="col-6">
                                                                                <label>Procedencia </label> 
                                                                                <select class="form-control selectpicker disabledForm" id="ProcedenciaComboBoxM" tabindex="-1" data-live-search="true">
                                                                                </select>
                                                                                <!-- <input
                                                                                    class="form-control typeahead disabledForm" id="ProcedenciaComboBoxM"
                                                                                    type="text" tabindex="-1"> -->
                                                                            </div>
                                                                        </div>
                                    
                                                                        <div class="row" id="filaServicio">
                                                                            <div class="col-4">
                                                                                <label>Servicio </label>
                                                                                <select class="form-control selectpicker disabledForm" id="ServicioComboBoxM" tabindex="-1" data-live-search="true">
                                                                                </select>
                                                                                <!-- <input
                                                                                    class="form-control typeahead disabledForm " id="ServicioComboBoxM"
                                                                                    type="text" tabindex="-1"> -->
                                                                            </div>
                                                                            <div class="col-4">
                                                                                <label>Sala </label> 
                                                                                <select class="form-control selectpicker" id="SalaComboBoxM" tabindex="" data-live-search="true" disabled>
                                                                                </select>
                                                                                <!-- <input class="form-control typeahead  "
                                                                                    id="SalaComboBoxM" data-set="" type="text" tabindex=""
                                                                                    disabled> -->
                                                                            </div>
                                                                            <div class="col-4">
                                                                                <label>Cama </label> 
                                                                                <select class="form-control selectpicker" id="CamaComboBoxM" tabindex="" data-live-search="true" disabled>
                                                                                </select>
                                                                                <!-- <input class="form-control typeahead  "
                                                                                    id="CamaComboBoxM" type="text" tabindex="" disabled> -->
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-12 orden-hr-vertical">
                                                                            <div class="row" id="filaExamenes">
                                                                                <div class="col-12 table-container">
                                                                                    <table id="tableDiagnosticoPaciente" class="table">
                                                                                        <thead>
                                                                                            <tr>
                                                                                                <th scope="col">#</th>
                                                                                                <th scope="col">Patolog&iacute;a</th>
                                                                                                <th scope="col">Observaci&oacute;n</th>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody id="tableBodyDiagnosticoPaciente">
                                        
                                                                                        </tbody>
                                                                                    </table>
                                                                                </div>
                                                                                <div class="col">
                                                                                    <label>Observaci&oacute;n </label>
                                                                                    <textarea disabled id="txtObservacionP"
                                                                                        class="form-control cleanPaciente" ></textarea>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row">
                                                                                <div class="col">
                                                                                    <label class="">Prioridad de atenci&oacute;n </label> 
                                                                                    <select class="form-control selectpicker disabledForm" id="selectPrioridadAtencionPac" tabindex="" data-live-search="true" >
                                                                                    </select>
                                                                                    <!-- <input
                                                                                        class="form-control typeahead disabledForm "
                                                                                        id="selectPrioridadAtencionPac" type="text"> -->
                                                                                </div>
                                                                            </div>
                                                                    </div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-6">
                                                                <div class="card card-2 h-100 card-subtitulo">
                                                                    <div class="card-body">
                                                                        <h3 class="col-12 pl-0 pr-0">Datos de la Orden</h3>
                                                                        <!-- panel der panelContenidoBasicoOrden-->
                                                                        <div class="col-12" id="panelContenidoBasicoOrden">
                                                                            <h6 class="subtitulo">M&eacute;dico</h6>
                                                                            <!-- Panel Medico -->
                                                                            <div class="row" id="idPanelMedico">
                                                                                <div id="medico-container" class="col-8">
                                                                                    <label>M&eacute;dicos </label> 
                                                                                    <select class="form-control selectpicker disabledForm" id="medicoBox" tabindex="4" data-live-search="true" >
                                                                                    </select>
                                                                                    <!-- <input
                                                                                        class="form-control typeahead disabledForm" id="medicoBox" type="text"
                                                                                        tabindex="4"> -->
                                                                                </div>
                                                                                <div class="col-4">
                                                                                    <label id="" class="col-form-label pb-0 col-12 pl-0">RUN</label>
                                                                                    <span id="txtRutMedico"></span>
                                                                                </div>
                                                                            </div>

                                                                            <!-- Fin Panel Medico -->

                                                                            <!-- Panel Financiamiento -->
                                                                            <h6 class="subtitulo mt-2">Financiamiento</h6>
                                                                            <div clas="row">
                                                                                <div class="col pl-0 pr-0">
                                                                                    <label>Convenio </label> 
                                                                                    <select class="form-control selectpicker" id="ConvencioComboBoxM" tabindex="-1" data-live-search="true">
																					</select>
                                                                                    <!--  <input
                                                                                        class="form-control typeahead disabledForm" id="ConvencioComboBoxM"
                                                                                        type="text" tabindex="-1">-->
                                                                                </div>
                                                                            </div>

                                                                            <!-- Fin Panel Financiamiento -->
                                                                        </div>
                                                                        <!-- fin panel der panelContenidoBasicoOrden-->

                                                                        <!-- panel der panelContenidoExtraOrden-->
                                                                        <div class="col-12" id="panelContenidoExtraOrden">
                                                                            <div class="row">
                                                                                <div class="col">
                                                                                    <label>Observaci&oacute;n </label> <textarea
                                                                                        id="txtObservacionR" name="dfObservacion" type="text"
                                                                                        class="form-control disabledForm" placeholder=""></textarea>
                                                                                </div>
                                                                            </div>

                                                                            <h6 class="subtitulo mt-2">Clínica</h6>
                                                                            <div clas="row">
                                                                                <div class="col pl-0 pr-0">
                                                                                    <label>Diagn&oacute;stico: </label> 
                                                                                    <select class="form-control selectpicker disabledForm" id="DiagnosticoComboBoxM" tabindex="-1" data-live-search="true" >
                                                                                    </select>
                                                                                    <!-- <input
                                                                                        class="form-control typeahead disabledForm"  id="DiagnosticoComboBoxM"
                                                                                        type="text" tabindex="-1"> -->
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <!-- fin panel der panelContenidoExtraOrden-->
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Fin Panel Datos básicos -->
                                                    <!-- Inicio Panel Datos Extras -->
                                                <div class="row tab-pane fade" id="tab_pane_datos_extras"
                                                role="tabpanel" aria-labelledby="tab_pane_datos_extras">
                                                    <div class="col-12">
                                                        <div class="card card-2">
                                                            <div class="card-body">
                                                                <div class="row" id="contenedorExamenes">

                                                                    <div class="col-3">
                                                                        <div class="row pt-3">
                                                                            <div class="col pr-0">
                                                                                <input type="text" id="idSearcher" class="disabledForm" inputmode="search"
                                                                                    tabindex="5" />
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col pr-0">
                                                                                <select id="idDualDataVisualSource" class="disabledForm" size="5" tabindex="-1">
                                                                                    <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                                </select> <select id="idPanelDualDataVisualSource" class="disabledForm" size="5"
                                                                                    tabindex="-1">
                                                                                    <option value="-1" disabled style="display: none;">Seleccionar</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-12 pr-0">
                                                                                <div class="col d-flex justify-content-start pl-0">
                                    
                                                                                    <div class="col-12 d-flex flex-wrap justify-content-center">
                                                                                        <div class="col-12 pr-0 pl-0 d-flex justify-content-center">
                                                                                            <label for="" class="label-examen pr-1 h6">Examen</label>/<label
                                                                                                for="" class="label-panel pl-1 h6 text-black-50">Panel</label>
                                                                                        </div>
                                                                                        <label class="switch-content switch-orden"> <input
                                                                                            type="checkbox" class="disabledForm" tabindex="-1" />
                                                                                            <div></div>
                                                                                        </label>
                                                                                    </div>
                                    
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                    
                                                                    <div class="col-1 d-flex align-items-center pr-0">
                                                                        <div id="dualDataButtonBar" class="mx-auto">
                                                                            <div class="col-12 pl-0 pr-0">
                                                                                <button id="clickAddBtn" type="button"
                                                                                    class="btn btn-blue-primary w-100 disabledForm">
                                                                                    Agregar <i class="fas fa-arrow-right" tabindex="-1"></i>
                                                                                </button>
                                                                            </div>
                                                                            <div class="col-12 pl-0 pr-0 mt-3">
                                                                                <button id="clickDelBtn" type="button"
                                                                                    class="btn btn-blue-primary w-100 disabledForm">
                                                                                    <i class="fas fa-arrow-left" tabindex="-1"></i> Quitar
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-8 d-flex flex-column justify-content-between">
                                                                        <div class="card card-2 mt-3">
                                                                            <div class="card-body p-0">
                                                                                <table id="idDataTarget" class="table display nowrap compact-xs table-striped table-selected" tabindex="-1">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th class="orden_codigo">C&oacute;digo</th>
                                                                                            <th class="orden_examen">Examen</th>
                                                                                            <th>Muestra</th>
                                                                                            <th>Secci&oacute;n</th>
                                                                                            <th>Laboratorio</th>
                                                                                            <th>Centro</th>
                                                                                            <th>Urgente</th>
                                                                                            <th>Anulado</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            <!-- Fin Panel Datos Extras -->
                                            </div>
                                        </div>
                                    
                                        <!-- Fin Formulario registro -->
                                        <div class="col-12">

                                            <button id="btnEditarOrden" name="insert" type="button"
                                                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 d-none">
                                                <i class="far fa-save"></i>Guardar
                                            </button>

                                            <button id="btnCancelEdit" name="cancel" type="button" 
                                            class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 d-none">Cancelar</button>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                <!-- Fin Panel inferior  -->
                </div>
            </div>
        </div>


	<!-- Modal Aviso Orden Creada-->
	<div class="modal fade" id="idOrderCreatedModal" tabindex="-1"
		role="dialog" aria-labelledby="idOrderCreatedModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h4 class="text-center pt-3 titulo-color">Orden registrada
						satisfactoriamente</h4>
					<label id="msjeRegistro" class="bg-warning text-dark"></label>
					<h3 id="numeroOrdenTitulo"
						class="text-center display-3 font-weight-bold">N° ######</h3>
					<div class="row">
						<div class="col-2">
							<a id="imprimirComprobante" class="" href="#"> <span
								class='symbol symbol-60 mr-3'> <span class='symbol-label'><i
										class="la la-print" style="font-size: 5rem"></i></i></span>
							</span> <span class="navi-text"></span>
							</a>
						</div>
						<div
							class="col-7 d-flex flex-row flex-wrap justify-content-center">
							<div class="col-12 pr-0 pl-0 d-flex justify-content-center">
								<label for="" class="label-voucher pl-1 h6 text-black-50">Voucher</label>/<label
									for="" class="label-comprobante pl-1 h6">Comprobante</label>
							</div>
							<label class="switch-content switch-comprobante"> <input
								id='checkBoxLeverCromp' type="checkbox" tabindex="-1" checked
								value='S'>
								<div></div>
							</label> 

                            <input id="txtEstado" name="estado" type="text"
								class="form-control mt-1 " autocomplete="off"
								placeholder="" hidden/>
						</div>
						<div class="col-lg-3 d-flex align-items-end">
							<button id="btnCreatedModalOk" type="button"
								class="btn btn-blue-primary btn-lg font-weight-bold"
								data-dismiss="modal" style="width: 100%;">Ok</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin Modal  -->
	<!-- Modal examen anulada -->
	<div class="modal fade" id="usuarioAnula" tabindex="-1"
		role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- Fin Modal -->
	
    <!-- Modal examen anulada -->
	<div class="modal fade" id="ordenFirmada" tabindex="-1"
		role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- Fin Modal -->





<!-- FIN FORMULARIO PACIENTE -->     
<jsp:include page="common/Scripts.jsp"/>

<!--begin::Global Theme Bundle(used by all pages)-->
<script
  src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<!--end::Global Theme Bundle-->
<script src="https://unpkg.com/imask"></script>
<script
  src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
<script
  src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>

  <script
  src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>

<script
  src="<%=request.getContextPath()%>/resources/js/common/dual-data-edicion.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
	<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>
    <script
    src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.typeahead.js"></script> 
    <script
		src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.validadores.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrdenes.busqueda.js"></script>
<script
		src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.paneles.js"></script>
  
<script
		src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.formulario.js"></script>

</body>
</html>

