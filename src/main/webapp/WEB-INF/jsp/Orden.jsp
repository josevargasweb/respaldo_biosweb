<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BiosLIS | Nueva orden</title>
<jsp:include page="common/Styles_1.jsp" />
<jsp:include page="common/FileInputStyles.jsp" />
<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />


</head>

<body>
	<div class="row menu-container">
		<div class="col-md-6">
			<jsp:include page="common/Menu_dinamico.jsp" />
		</div>
		<div class="col-md-6">
			<jsp:include page="common/Header_1.jsp" />
		</div>
	</div>

	<div class="container container-main-content container-orden">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0">
				<form method="post" class="form" id="kt_form">
					<input id="idPaciente" name="datosPacientes" type="hidden" /> <input
						id="idMedico" type="hidden" name="dfIdmedico" /> <input
						id="txtEdad" type="hidden" /> <input id="txtEmailP"
						name="dfEnviarresultadosemail" type="hidden" /> <input
						id="sexoPacienteOrden" type="hidden" /> <input
						id="txtPrimerApellidoP" type="hidden" />

					<!-- input hidden (registra examenes derivados) añadido por Marco Caracciolo 30/11/2022 -->
					<input type="hidden" id="registraExamanesDerivados"
						name="registraExamanesDerivados"
						value="${perfilUsuario.cpuRegistraexamenesderivados}" />

					<!-- *************************************** Datos del Form ***************************************************** -->
					<div class="card card-orden">
						<div class="card-header mb-0">
							<div class="card-title d-flex justify-content-between pt-2 pb-2">
								<h3 class="mb-0 btn-block text-left pl-0 col-10">Nueva
									Orden</h3>
								<div class="card-buttons col-2 d-flex justify-content-end">
									<a id="btnLimpiarOrden" class="navi-link"> <span
										class="symbol symbol-30 symbol-circle "> <span
											id="circuloLimpiarFiltro"
											class="symbol-label bg-hover-blue  hoverIcon" href="#"
											data-toggle="tooltip" title="Limpiar orden"> <i
												id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
										</span>
									</span> <span class="navi-text"></span>
									</a>
								</div>
							</div>
						</div>

						<div class="card-body pt-2">
							<!-- contenedor paciente y orden-->
							<div class="row" id="botoneraPacienteOrden">
								<div class="col-6 orden-hr-vertical">
									<div class="row">
										<h4 class="mb-0 col-md-4 col-xl-6">Datos paciente</h4>
										<!-- Inicio Tabuladores -->
										<div class="col-md-8 col-xl-6">
												<ul class="nav nav-pills justify-content-end nav-fill nav-justified">
														<li class="nav-item"><a id="btnPacienteBasico" class="nav-link active" data-toggle="tab" href=""> <span class="nav-icon"><i class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp; Básicos</span></a></li>
														<li id="btnPacienteExtra" class="nav-item"><a class="nav-link" data-toggle="tab" href=""> <span class="nav-icon"><i class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp;Extras</span></a></li>
												</ul>
										</div>
										<!-- Fin Tabuladores -->
									</div>
								</div>
								<div class="col-6">
									<div class="row">
										<h4 class="mb-0 col-md-4 col-xl-6">Datos orden</h4>
										<!-- Inicio Tabuladores -->
										<div class="col-md-8 col-xl-6">
												<ul class="nav nav-pills justify-content-end nav-fill nav-justified">
														<li class="nav-item"><a id="btnOrdenBasico" class="nav-link active" data-toggle="tab" href=""> <span class="nav-icon"><i class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp; Básicos</span></a></li>
														<li id="btnOrdenExtra" class="nav-item"><a class="nav-link" data-toggle="tab" href=""> <span class="nav-icon"><i class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp;Extras</span></a></li>
												</ul>
										</div>
										<!-- Fin Tabuladores -->
									</div>
								</div>
							</div>
							<div class="row" id="contenedorPacienteOrden">
								<!-- panel izq panelContenidoBasicoPaciente-->
								<div class="col-6 orden-hr-vertical"
									id="panelContenidoBasicoPaciente">
									<div class="row" id="filaDoc">
										<div class="col-4">
											<label>Tipo de documento </label> <select
												class="form-control selectpicker " id="selectTipoDocumento"
												name="ldvtdIdtipodocumento">
												<c:forEach var="documento" items="${listaTiposDocumentos}">
													<option value="${documento.ldvtdIdtipodocumento}">
														${documento.ldvtdDescripcion}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-4">
											<label id="tipoDocLabel">RUN</label>
											<div id="divTxtRutFiltro">
												<input id="txtRutP" type="text"
													class="form-control alwaysMayus cleanPaciente"
													minlength="11" maxlength="12" autocomplete="off"
													onkeyup="this.value = this.value.toUpperCase();"
													placeholder="" tabindex="-1" />
											</div>
											<div id='divTxtPasaporteFiltro' style="display: none;">
												<input id="txtPasaporte" type="text"
													class="form-control alwaysMayus cleanPaciente"
													placeholder="" />
											</div>
											<div id='divTxtDniFiltro' style="display: none;">
												<input id="txtDni" type="text"
													class="form-control alwaysMayus cleanPaciente"
													placeholder="" />
											</div>
										</div>

										<!--  Botonera paciente-->
										<div class="col-4 ">
											<div class="float-right">
												<a href="./RegistroPaciente?origen=antiguo" id="BuscarPac"
													class="navi-link " tabindex="-1"> <span
													class='symbol symbol-30 symbol-circle mr-3'> <span
														id="circuloBuscarPaciente"
														class='symbol-label bg-hover-blue  hoverIcon '
														data-toggle="tooltip" title="Buscar paciente"> <i
															id="iBuscarPaciente" class="fas fa-search text-blue "></i>
													</span>
												</span> <span class="navi-text"></span>
												</a>
											</div>
											<div class="float-right">
												<a href="./RegistroPaciente?origen=nuevo" tabindex="-1"
													id="AgregarPac" class="navi-link "> <span
													class='symbol symbol-30 symbol-circle mr-3'> <span
														id="circuloAgregarPaciente"
														class='symbol-label bg-hover-blue  hoverIcon'
														data-toggle="tooltip" title="Agregar paciente"> <i
															id="iAgregarPaciente" class='fas fa-plus text-blue'></i>
													</span>
												</span> <span class="navi-text"></span>
												</a>
											</div>
										</div>
										<!--  fin Botonera paciente -->


									</div>

									<div class="row" id="filaNombres">
										<div class="col ">

											<div id="divNombreCompleto">
												<div class="col-12">
													<div class="row">
														<div class="col-4 p-0">
															<label id="" class="col-form-label pb-0 col-12 pl-0">Nombre</label>
															<span id="nombre_orden"></span>
														</div>
														<div class="col-2 pr-0">
															<label id="" class="col-form-label pb-0 col-12 pl-0">Sexo</label>
															<span id="sexo_orden"></span>
														</div>
														<div class="col-2 pl-0">
															<label id="" class="col-form-label pb-0 col-12 pl-0">Edad</label>
															<span id="edad_orden"></span>
														</div>
													</div>
												</div>
												<div class="col-12">
													<div class="row">
														<div class="col-4 p-0">
															<label id="" class="col-form-label pb-0 col-12 pl-0">E-mail</label>
															<span id="email_orden"> </span>
														</div>

														<div
															class=" col-5 p-0  form-check cleanPaciente d-flex align-items-end">
															<input class="form-check-input" type="checkbox" value=""
																id="chbxRestutToMail" tabindex="-1"> <label
																class="form-check-label" for="chbxRestutToMail"
																tabindex="-1"> ¿Envío de resultado al mail? </label>
														</div>
													</div>
												</div>

											</div>

											<input id="txtNombreP" type="hidden"
												class="form-control cleanPaciente" placeholder="" />

										</div>
									</div>
									<div class="row" id="filaAtencionProcedencia">
										<div class="col-6">
											<label>Tipo de atenci&oacute;n </label> <select
												class="form-control selectpicker" id="tipoDeAtencion"
												tabindex="1" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="tipoDeAtencion"
												type="text" tabindex="1"> -->
										</div>

										<div class="col-6">
											<label>Procedencia </label> <select
												class="form-control selectpicker" id="ProcedenciaComboBoxM"
												tabindex="-1" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="ProcedenciaComboBoxM"
												type="text" tabindex="-1"> -->
										</div>
									</div>

									<div class="row" id="filaServicio">
										<div class="col-4">
											<label>Servicio </label> <select
												class="form-control selectpicker" id="ServicioComboBoxM"
												tabindex="-1" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="ServicioComboBoxM"
												type="text" tabindex="-1"> -->
										</div>
										<div class="col-4">
											<label>Sala </label> <select
												class="form-control selectpicker" id="SalaComboBoxM"
												tabindex="" data-live-search="true" disabled>
											</select>
											<!-- <input class="form-control typeahead"
												id="SalaComboBoxM" data-set="" type="text" tabindex=""
												disabled> -->
										</div>
										<div class="col-4">
											<label>Cama </label> <select
												class="form-control selectpicker" id="CamaComboBoxM"
												tabindex="" data-live-search="true" disabled>
											</select>
											<!-- <input class="form-control typeahead"
												id="CamaComboBoxM" type="text" tabindex="" disabled> -->
										</div>
									</div>

								</div>
								<!-- fin panel izq panelContenidoBasicoPaciente-->

								<!-- panel izq panelContenidoExtraPaciente-->

								<div class="col-6 orden-hr-vertical"
									id="panelContenidoExtraPaciente">

									<div class="row pl-3 pr-3" id="filaExamenes">
										<div class="col table-container pl-0 pr-0">
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
									</div>


									<div class="row" id="filaObservaciones">
										<div class="col">
											<label>Observaci&oacute;n </label>
											<textarea disabled id="txtObservacionP"
												class="form-control cleanPaciente" placeholder="" rows="2"></textarea>
										</div>
									</div>

									<div class="row" id="filaPrioridad">
										<div class="col">
											<label class="">Prioridad de atenci&oacute;n </label> <select
												class="form-control selectpicker"
												id="selectPrioridadAtencionPac" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead"
												id="selectPrioridadAtencionPac" type="text"> -->
										</div>
									</div>
								</div>

								<!-- panel izq panelContenidoExtraPaciente-->

								<!-- panel der panelContenidoBasicoOrden-->
								<div class="col-6" id="panelContenidoBasicoOrden">
									<h6 class="subtitulo">M&eacute;dico</h6>
									<!-- Panel Medico -->
									<div class="row" id="idPanelMedico">
										<div id="medico-container" class="col-8">
											<label>M&eacute;dicos </label> <select
												class="form-control selectpicker" id="medicoBox"
												tabindex="4" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="medicoBox" type="text"
												tabindex="4"> -->
										</div>
										<div class="col-4">
											<label>RUN </label> <input id="txtRutMedico" type="text"
												minlength="11" maxlength="12" autocomplete="off"
												class="form-control alwaysMayus" placeholder=""
												tabindex="-1" />
										</div>
										<input disabled id="txtNombreM" type="hidden"
											class="form-control" placeholder="" />

									</div>

									<!-- Fin Panel Medico -->

									<!-- Panel Financiamiento -->
									<h6 class="subtitulo mt-2">Financiamiento</h6>
									<div clas="row">
										<div class="col pl-0 pr-0">
											<label>Convenio </label> <select
												class="form-control selectpicker" id="ConvencioComboBoxM"
												tabindex="-1" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="ConvencioComboBoxM"
												type="text" tabindex="-1"> -->
										</div>
									</div>

									<!-- Fin Panel Financiamiento -->
								</div>
								<!-- fin panel der panelContenidoBasicoOrden-->

								<!-- panel der panelContenidoExtraOrden-->
								<div class="col-6" id="panelContenidoExtraOrden">
									<div class="row">
										<div class="col">
											<label>Observaci&oacute;n </label> <input
												id="txtObservacionR" name="dfObservacion" type="text"
												class="form-control" placeholder="" />
										</div>
									</div>

									<h6 class="subtitulo mt-2">Clínica</h6>
									<div clas="row">
										<div class="col pl-0 pr-0">
											<label>Diagn&oacute;stico: </label> <select
												class="form-control selectpicker" id="DiagnosticoComboBoxM"
												tabindex="-1" data-live-search="true">
											</select>
											<!-- <input
												class="form-control typeahead" id="DiagnosticoComboBoxM"
												type="text" tabindex="-1"> -->
										</div>
									</div>
									<button id='btnDocumentos' type="button" data-toggle="modal"
										data-target="#adjuntarDocumentosModal"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-3">Adjuntar
										documento</button>

								</div>

								<!-- fin panel der panelContenidoExtraOrden-->

								<div class="modal fade" id="adjuntarDocumentosModal"
									tabindex="-1" role="dialog"
									aria-labelledby="adjuntarDocumentosModal" aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered" role="document">
										<div class="modal-content">
											<div class="modal-body">
												<div class="col-12">
													<h5>Adjuntar documento orden</h5>
													<form id="formOM" method="post"
														enctype="multipart/form-data">
														<input id="ordenMedicaFile" name="ordenMedicaFile"
															type="file" class="file doc-orden"
															data-show-upload="false" data-browse-on-zone-click="true">
													</form>
												</div>
												<div
													class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
													<button id="cerrarModalDocumentoOrden" type="button"
														class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
														data-dismiss="modal">Cerrar</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- fin contenedor paciente  y orden-->
						</div>
					</div>


					<!-- Inicio contenedor examenes-->
					<div class="card card-orden mt-4">
						<div class="card-header mb-0">
							<div class="card-title d-flex justify-content-between  pt-2 pb-2">
								<h3 class="mb-0 btn-block text-left pl-0 col-10">Selecci&oacute;n
									Ex&aacute;menes</h3>
							</div>
						</div>
						<div class="card-body pt-0">
							<div class="row" id="contenedorExamenes">

								<div class="col-3">
									<div class="row pt-3">
										<div class="col pr-0">
											<input type="text" id="idSearcher" inputmode="search"
												tabindex="5" />
										</div>
									</div>
									<div class="row">
										<div class="col pr-0">
											<select id="idDualDataVisualSource" size="5" tabindex="-1">
												<option value="-1" disabled style="display: none;">Seleccionar</option>
											</select> <select id="idPanelDualDataVisualSource" size="5"
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
														type="checkbox" tabindex="-1" />
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
												class="btn btn-blue-primary btn-lg font-weight-bold w-100">
												Agregar <i class="fas fa-arrow-right" tabindex="-1"></i>
											</button>
										</div>
										<div class="col-12 pl-0 pr-0 mt-3">
											<button id="clickDelBtn" type="button"
												class="btn btn-blue-primary btn-lg font-weight-bold w-100">
												<i class="fas fa-arrow-left" tabindex="-1"></i> Quitar
											</button>
										</div>
									</div>
								</div>
								<div class="col-8 d-flex flex-column justify-content-between">
									<div class="card card-2 mt-3">
										<div class="card-body p-0">
											<table id="idDataTarget"
												class="table display nowrap compact-xs table-striped table-selected"
												tabindex="-1" style="width: 100%">
												<thead>
													<tr>
														<th class="orden_codigo">C&oacute;digo</th>
														<th class="orden_examen">Examen</th>
														<th>Muestra</th>
														<th>Secci&oacute;n</th>
														<th>Laboratorio</th>
														<th>Centro</th>
														<th>Urgente</th>
														<th></th>
														<th></th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
									<div class="col-2 d-flex align-items-center pl-0">
										<button id="btnGuardarOrden"
											class="btn btn-blue-primary btn-lg font-weight-bold"
											data-wizard-type="action-submit" type="button" tabindex="6">
											<i class="far fa-save" aria-hidden="true"></i> Guardar
										</button>
									</div>
								</div>

							</div>
						</div>
					</div>

					<!-- *************************************** Fin Datos del Form ***************************************************** -->
				</form>
				<!--end: Form-->
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
							<a id="imprimirComprobante" class=""> <span
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
							</label> <input id="txtEstado" name="estado" type="text"
								class="form-control mt-1 ocultar" autocomplete="off"
								placeholder="" />
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
	<div class="modal fade" id="idOrderConfirmationModal" tabindex="-1"
		role="dialog">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title titulo-color">Confirmaci&oacute;n</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="jsonFormData" />
					<div class="row">
						<div class="form-group col-6 resumen-container">
							<div class="row">

								<div class="col-md mb-3 resumen-control">
									<label class="pl-0">Paciente </label> <span
										id="txtNombrePacienteR"></span>
								</div>
							</div>
							<div class="row">
								<div class="col-md pr-0 mb-3 resumen-control">
									<label class="pl-0 ">M&eacute;dico </label> <span
										id="txtNombreMedicoR"></span>
								</div>
							</div>
							<div class="row">
								<div class="col-md pl-0 pr-0 mb-0 resumen-control"
									id="divtxtLugarR">
									<label hidden>Lugar </label> <input id="txtLugarR" name=""
										type="text" class="form-control" placeholder="" hidden />
								</div>
							</div>
							<div class="row">
								<div class="col-md pl-0 pr-0 mb-0 resumen-control"
									id="divtxtUbicacionR">
									<label hidden>Ubicaci&oacute;n </label> <input
										id="txtUbicacionR" name="dfCodigolocalizacion" type="text"
										class="form-control" placeholder="" hidden />
								</div>
							</div>
							<div class="row">

								<div class="col-md-12 mb-3 resumen-control"
									id="divtxtProcedenciaR">
									<label class="pl-0 ">Procedencia </label> <span
										id="txtProcedenciaR"></span>
								</div>
								<div class="col-md-12  resumen-control" id="divtxtServicioR">
									<label class="pl-0 ">Servicio </label> <span id="txtServicioR"></span>
								</div>
							</div>
							<div class="row">

								<div class="col-md resumen-control">
									<label>Observaci&oacute;n </label> <input id="txtObservacionRC"
										name="dfObservacion" type="text" class="form-control"
										placeholder="" />
								</div>
							</div>

						</div>
						<div class="col-6" style="font-size: 15px;">
							<!--align-items-center-->
							<div class="col-12  pl-0 pr-0 row">
								<div class="col-md-12 pl-0 pr-0 d-flex justify-content-center">
									<label>¿Imprimir comprobante de atenci&oacute;n? </label>
								</div>
								<div class="col-6 d-flex justify-content-center">
									<div class="col-12 d-flex align-items-end pl-0 pr-0">
										<button id="imprimirComprobante2" type="button"
											class="btn btn-blue-primary btn-lg font-weight-bold">
											<i class="la la-print"></i> Imprimir
										</button>
										<!--  	<a id="imprimirComprobante"
											class="btn btn-blue-primary btn-lg font-weight-bold">
											<i class="la la-print"></i> Imprimir
										</a>-->
									</div>
								</div>
								<div class="col-6 pl-0 pr-0 mt-3">
									<div class="col-12 d-flex justify-content-center">
										<label for="" class="label-voucher pl-1 h6 text-black-50">Voucher</label>/<label
											for="" class="label-comprobante pl-1 h6">Comprobante</label>
									</div>
									<div class="col-12 d-flex justify-content-center">
										<label class="switch-content switch-comprobante"> <input
											id='checkBoxLeverCromp' type="checkbox" tabindex="-1" checked
											value='S'>
											<div></div>
										</label> <input id="  " name="estado" type="text"
											class="form-control mt-1 ocultar" autocomplete="off"
											placeholder="" />
									</div>
								</div>
							</div>
							<div class="col-12 d-flex justify-content-center mt-3">
								<label class="col-md-5 checkbox checkbox-primary"> <input
									id="chbxvistaPrevia" type="checkbox" /> <label for=""
									style="padding-left: 1rem;">¿Vista previa?</label> <span></span>
								</label>
							</div>
						</div>

						<div class="col-md-12">
							<label><b>Exámenes</b></label>
							<div class="previa-examenes-container">
								<div class="examenes-scroll">
									<table id="txtExamenesR" class="table table-bordered">
										<thead>
											<tr>
												<th>Examen</th>
												<th>Indicaci&oacuten</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>

							<div class="d-md-none mb-2"></div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button id="btnModalCancel" type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold">Cancelar</button>
					<button id="btnModalSave" type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold">
						<i class="far fa-save" aria-hidden="true"></i> Guardar
					</button>
				</div>


			</div>
		</div>
	</div>

	<!-- Modal paciente nuevo -->
	<div class="modal fade" id="idModalPacienteNuevo" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title titulo-color" id="idModalPacienteNuevo">Nuevo
						Paciente</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">Paciente no registrado, &iquest;Desea
					a&ntilde;adirlo?</div>
				<div class="modal-footer">
					<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
						data-dismiss="modal">No</button>
					<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si">Si</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin Modal -->
	<!-- Modal orden registrada -->
	<div class="modal fade" id="usuarioConOrden" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title titulo-color"></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no-orden"
						data-dismiss="modal">No</button>
					<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si-orden" id="btn_si_ordenRegistrada">Si</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin Modal -->
	<!-- Modal orden registrada -->
	<div class="modal fade" id="idOrdenYaRegistrada" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title titulo-color"></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" id="btnOrdenYaRegistradaNo"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
						data-dismiss="modal">No</button>
					<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si">Si</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin Modal -->


	<jsp:include page="common/AlertaSession.jsp" />

	<!-- ----------------------------------------  FIN MODALES  -->
	<jsp:include page="common/Scripts.jsp" />


	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/common/dual-data.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/registrodocumentos/UploadDocumentos.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/orden/Orden.validadores.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/orden/Orden.functions.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/orden/Orden.filtros.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/orden/Orden.binding.js"></script>
	<jsp:include page="common/FileInputScripts.jsp" />

</body>

</html>