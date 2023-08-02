<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registro Orden</title>
    <jsp:include page="common/Styles.jsp"/>
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
	<jsp:include page="common/Menu.jsp"/>
  <jsp:include page="common/Header.jsp"/>
	<div class="container mt-1">

		<!--begin: Wizard-->
		<div class="wizard wizard-3" id="kt_wizard_v3"
			data-wizard-state="step-first" data-wizard-clickable="false">
			<!--begin: Wizard Body-->
			<div class="card card-custom mt-1">
				<div class="card-header">
					<div class="card-title">
						<h3 class="card-label">Orden</h3>
					</div>
				</div>
				<div class="card-body p-0">
					<div class="row justify-content-center pt-0 pb-1">
						<div class="wizard-nav col-12">
							<div class="wizard-steps px-1 py-1 px-lg-15 py-lg-3">
								<!--begin::Wizard Step 1 Nav-->
								<div class="wizard-step" data-wizard-type="step"
									data-wizard-state="current">
									<div class="wizard-label">
										<h3 class="wizard-title">
											<span>1.</span>Datos del Paciente
										</h3>
										<div class="wizard-bar"></div>
									</div>
								</div>
								<!--end::Wizard Step 1 Nav-->
								<!--begin::Wizard Step 2 Nav-->
								<div class="wizard-step" data-wizard-type="step">
									<div class="wizard-label">
										<h3 class="wizard-title">
											<span>2.</span>Datos de la Orden
										</h3>
										<div class="wizard-bar"></div>
									</div>
								</div>
								<!--end::Wizard Step 2 Nav-->
								<!--begin::Wizard Step 3 Nav-->
								<div class="wizard-step" data-wizard-type="step">
									<div class="wizard-label">
										<h3 class="wizard-title">
											<span>3.</span>Selecci&oacute;n de Exámenes
										</h3>
										<div class="wizard-bar"></div>
									</div>
								</div>
								<!--end::Wizard Step 3 Nav-->
								<!--begin::Wizard Step 4 Nav-->
								<div class="wizard-step" data-wizard-type="step">
									<div class="wizard-label">
										<h3 class="wizard-title">
											<span>4.</span>Confirmaci&oacute;n
										</h3>
										<div class="wizard-bar"></div>
									</div>
								</div>
								<!--end::Wizard Step 4 Nav-->
								<!--begin::Wizard Step 5 Nav-->

								<!--end::Wizard Step 5 Nav-->
							</div>
						</div>
						<div class="col-xxl-12 pt-1">

							<!--begin: Wizard Form-->

							<form method="post" class="form" id="kt_form">
								<!--begin: Wizard Step 1-->
								<!-- *************************************** Datos del Paciente ***************************************************** -->
								<div class="pb-1 row col-11 mx-auto"
									data-wizard-type="step-content" data-wizard-state="current">
									<div class="row">
										<div
											class="col-12 row d-flex justify-content-between border-bottom pb-1"
											style="margin-bottom: 20px">
											<h4 class="col-3 pl-0 pt-1">Paciente</h4>
											<div class="col-4 justify-content-right row">
												<div class="ml-1 float-right">
													<a href="./RegistroPaciente" id="BuscarPac"
														class="navi-link "> <span
														class='symbol symbol-50 symbol-circle mr-3'> <span
															id="circuloBuscarPaciente"
															class='symbol-label bg-hover-primary  hoverIcon '
															data-toggle="tooltip" title="Buscar paciente"> <i
																id="iBuscarPaciente" class="fas fa-search text-primary "></i>
														</span>
													</span> <span class="navi-text"></span>
													</a>
												</div>
												<div class="ml-1 float-right">
													<a href="./RegistroPaciente" id="AgregarPac"
														class="navi-link "> <span
														class='symbol symbol-50 symbol-circle mr-3'> <span
															id="circuloAgregarPaciente"
															class='symbol-label bg-hover-primary  hoverIcon'
															data-toggle="tooltip" title="Agregar paciente"> <i
																id="iAgregarPaciente" class='fas fa-plus text-primary'></i>
														</span>
													</span> <span class="navi-text"></span>
													</a>
												</div>
											</div>

										</div>
										<div class="form-group col-6 row">
											<!--align-items-center-->
											<div class="col-6">
												<label>Tipo de documento </label> <select
													class="form-control selectpicker " id="selectTipoDocumento"
													name="ldvtdIdtipodocumento">
													<c:forEach var="documento" items="${listaTiposDocumentos}">
														<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
													</c:forEach>
												</select>
											</div>

											<div class="col-md-6">
												<label id="tipoDocLabel">RUN</label>
												<div id="divTxtRutFiltro">
													<input id="txtRutP" type="text"
														class="form-control alwaysMayus cleanPaciente"
														placeholder="" />
												</div>
												<div id='divTxtPasaporteFiltro'>
													<input id="txtPasaporte" type="text"
														class="form-control alwaysMayus cleanPaciente"
														placeholder="" />
												</div>
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-6 ">
												<label>Nombres: </label> <input disabled id="txtNombreP"
													type="text" class="form-control cleanPaciente"
													placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-6 ">
												<label>Apellidos: </label> <input disabled
													id="txtPrimerApellidoP" type="text"
													class="form-control cleanPaciente" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-6">
												<label>Sexo </label> <select disabled id="sexoPacienteOrden"
													class="form-control cleanPaciente">
													<option value="N" disabled selected></option>
													<c:forEach var="sexo" items="${listaSexo}">
														<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-6 ">
												<label>Edad: </label> <input disabled id="txtEdad"
													type="text" class="form-control cleanPaciente"
													placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<br>
											<div class="col-md-12 ">
												<label>Email </label> <input disabled id="txtEmailP"
													name="dfEnviarresultadosemail" type="text"
													class="form-control cleanPaciente" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-12">
												<label
													class="col-md-12 checkbox checkbox-primary cleanPaciente">
													<input id="chbxRestutToMail" type="checkbox" /> Envío de
													resultado al mail? <span></span>
												</label>
											</div>
											<div class="col-md-12">
												<label>Observaci&oacute;n </label>
												<textarea disabled id="txtObservacionP"
													class="form-control cleanPaciente" placeholder="" rows="3"></textarea>
												<div class="d-md-none mb-2"></div>
											</div>
										</div>
										<div class="form-group col-6 row pl-5 ml-5">
											<!--align-items-center-->
											<div class="col-12 scroll scroll-pull" data-scroll="true"
												data-suppress-scroll-x="false" data-swipe-easing="false"
												style="height: 59%">
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


										<div
											class="form-group col-12 row d-flex justify-content-between border-top">
											<!--align-items-center-->
											<div class="col-md-6 mt-1">
												<label>Tipo de atenci&oacute;n </label> <select
													class="form-control selectpicker" id="tipoDeAtencion"
													name="cfgTipoatencion">
													<option value="-1">Seleccionar</option>
													<c:forEach var="listaTipoAtt" items="${listaTipoAt}">
														<option value="${listaTipoAtt.ctaIdtipoatencion}">${listaTipoAtt.ctaDescripcion}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-6 mt-1">
												<label class="">Prioridad de atenci&oacute;n </label> <select
													id="selectPrioridadAtencionPac" name="cfgPrioridadatencion"
													class="form-control selectpicker " data-live-search="true">
													<option value="" disabled selected>Seleccionar</option>
													<c:forEach var="prior" items="${listaPrior}">
														<option value="${prior.cpaIdprioridadatencion}">${prior.cpaDescripcion}</option>
													</c:forEach>
												</select>
											</div>

										</div>
									</div>
								</div>
								<!-- *************************************** Datos de la  Orden ***************************************************** -->
								<!--end: Wizard Step 1-->
								<!--begin: Wizard Step 2-->
								<div class="pb-1 row col-11 mx-auto"
									data-wizard-type="step-content">
									<div class="form-group row col-12">

										<div class="form-group col-6 border-right2 row py-5">
											<div class="row col-12 border-bottom2"
												style="min-height: 315px;">
												<div
													class="col-12  d-flex justify-content-center row border-bottom">
													<h4 class="col-4">Médico</h4>
													<div class="col-8 row d-flex justify-content-end pr-0">
														<div class="col-4 text-right">
															<a href="./RegistroMedico" id="BuscarMed"
																class="navi-link " href="#"> <span
																class='symbol symbol-50 symbol-circle'> <span
																	class='symbol-label circuloBuscarPaciente bg-hover-primary  hoverIcon'>
																		<i class="la la-search iBuscarPaciente"></i>
																</span>
															</span> <span class="navi-text"></span>
															</a>
														</div>
														<div class="col-4 text-right">
															<a href="./RegistroMedico" id="AgregarMed"
																class="navi-link " href="#"> <span
																class='symbol symbol-50 symbol-circle'> <span
																	class='circuloBuscarPaciente symbol-label bg-hover-primary  hoverIcon'>
																		<i class='fas fa-plus  text-primary iBuscarPaciente'></i>
																</span>
															</span> <span class="navi-text"></span>
															</a>
														</div>
													</div>
												</div>
												<!--align-items-center-->
												<div class="col-6">
													<label>M&eacute;dicos </label> <select id="medicoComboBox"
														class="form-control ">
														<c:forEach var="med" items="${listaMedicos}">
															<option value="${med[0]}">${med[1]} ${med[2]}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-6">
													<label>RUT: </label> <input id="txtRutMedico" type="text"
														class="form-control alwaysMayus" placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
												<div class="col-md-12">
													<label>Nombres: </label> <input disabled id="txtNombreM"
														type="text" class="form-control" placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
												<div class="col-md-12 " style="margin-bottom: 64px">
													<label>Apellidos: </label> <input disabled
														id="txtPrimerApellidoM" type="text" class="form-control"
														placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
											</div>
											<div class="col-12 row ">
												<div class="col-12 border-bottom">
													<h4>Ubicaci&oacute;n</h4>
												</div>
												<div class="col-md-12 ">
													<label>Procedencia </label> <select
														id="ProcedenciaComboBoxM"
														class="form-control selectpicker " name="cfgProcedencias">
														<option value="-1" selected>Seleccionar</option>
														<c:forEach var="listaproce" items="${listaProce}">
															<option value="${listaproce.cpIdprocedencia}">${listaproce.cpDescripcion}
															</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-4">
													<label>Servicio </label> <select id="ServicioComboBoxM"
														class="form-control selectpicker " name="cfgServicios">
														<option value="-1" selected>Seleccionar</option>
														<c:forEach var="listaServicio" items="${listaServicios}">
															<option value="${listaServicio.csIdservicio}">${listaServicio.csDescripcion}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-4">
													<label>Sala </label> <select disabled id="SalaComboBoxM"
														class="form-control selectpicker SSM" name="salas">
														<option disabled value="" selected>Seleccionar</option>

													</select>
												</div>
												<div class="col-md-4">
													<label>Cama </label> <select disabled id="CamaComboBoxM"
														class="form-control selectpicker SSM" name="camas">
														<option disabled value="" selected>Seleccionar</option>

													</select>
												</div>
											</div>
										</div>
										<div class="form-group col-6 row pl-5 ml-5">
											<!--align-items-center-->
											<div class="col-12 row border-bottom2 pl-5 py-3 pb-1 ml-3">
												<div class="col-12 border-bottom" style="max-height: 56px;">
													<h4>Clínica</h4>
												</div>
												<div class="col-md-12">
													<label>Diagn&oacute;stico: </label> <select
														id="DiagnosticoComboBoxM"
														class="form-control selectpicker" name="cfgDiagnosticos">
														<option value="0" disabled selected>Seleccionar</option>
														<c:forEach var="med" items="${listaDiag}">
															<option value="${med[0]}">${med[1]}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-12">
													<label>Observaci&oacute;n </label> <input
														id="txtObservacion" name="" type="text"
														class="form-control" placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
											</div>
											<div class="col-md-12 mt-1">
												<div class="col-12 border-bottom">
													<h4>Financiamiento</h4>
												</div>
												<label>Convenio </label> <select id="ConvencioComboBoxM"
													class="form-control selectpicker " name="cfgConvenios">
													<option value="N" disabled selected>Seleccionar</option>
													<c:forEach var="listaConvenio" items="${listaConvenio}">
														<option value="${listaConvenio.ccIdconvenio}">${listaConvenio.ccDescripcion}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>

									</div>
								</div>
								<!--end: Wizard Step 2-->
								<!-- *************************************** Seleccion de ex&aacute;menes ***************************************************** -->
								<!--begin: Wizard Step 3-->
								<div class="pb-1" data-wizard-type="step-content">
									<div class="row justify-content-center">
										<div class="col-8 offset-2">
											<div class="form-group ml-5 ">

												<div id="examenContainer">
													<select id="kt_dual_listbox_2" class="selectDemo">
														<c:forEach var="listaExamen" items="${listaExamen}">
															<option value="${listaExamen.ceIdexamen}" data-search=>${listaExamen.ceDescripcion}|${listaExamen.ceCodigoexamen}
																| ${listaExamen.ceAbreviado}</option>
														</c:forEach>
													</select>
													<div id="panelContainer" style="display: none;">
														<select id="panelesSelect" class="selectPaneles">
														</select>
													</div>
												</div>

												<ul id="examenesTest" class="ocultar">
												</ul>
											</div>
										</div>
									</div>
									<div class="row justify-content-center">
                    <div class="col-6 offset-3">

										<!-- 
<div class="btn-group" role="group" aria-label="Basic example">
  <button type="button" class="btn btn-primary" id="switchExamen" name="switchExamen">Examen</button>
  <button type="button" class="btn btn-info" id="switchPanel" name="switchPanel">Panel</button>
</div>
 -->
										<button type="button" id="switchList" class="btn btn-primary"
											name="switchList">Examen|Panel</button>
										<br />
                  </div>
                  </div>

								</div>
								<!--end: Wizard Step 3-->
								<!-- *************************************** Confirmacion ***************************************************** -->
								<!--begin: Wizard Step 4-->
								<div class="pb-1 container" data-wizard-type="step-content">
									<div class="row">
										<div class="form-group col-sm">
											<div class="col-md-10">
												<label>Paciente </label> <input id="txtNombrePacienteR"
													name="" type="text" class="form-control" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-10">
												<label>M&eacute;dico </label> <input id="txtNombreMedicoR"
													name="" type="text" class="form-control" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-10" id="divtxtLugarR">
												<label>Lugar </label> <input id="txtLugarR" name=""
													type="text" class="form-control" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-10" id="divtxtUbicacionR">
												<label>Ubicaci&oacute;n </label> <input id="txtUbicacionR"
													name="dfCodigolocalizacion" type="text"
													class="form-control" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-10">
												<label>Exámenes </label>
												<ul id="txtExamenesR" name="">

												</ul>
												<div class="d-md-none mb-2"></div>
											</div>
											<div class="col-md-10">
												<label>Observaci&oacute;n </label> <input
													id="txtObservacionR" name="dfObservacion" type="text"
													class="form-control" placeholder="" />
												<div class="d-md-none mb-2"></div>
											</div>

										</div>
										<div class="col-sm" style="margin-top: 10%; font-size: 15px;">
											<!--align-items-center-->
											<div class="col-8 row">
												<div class="col-md-12">
													<label>Imprimir comprobante de atenci&oacute;n? </label>
												</div>
												<div class="col-3">
													<a id="imprimirComprobante" class="" href="#"> <span
														class='symbol symbol-50 mr-3'> <span
															class='symbol-label'><i class="la la-print"></i></span>
													</span> <span class="navi-text"></span>
													</a>
												</div>
												<div class="col-9">
													<label id="lblComprobanteOVoucher"
														class="col-12 col-form-label">Comprobante</label>
													<div class="col-6">
														<span id="spanCheck"
															class="switch switch-outline switch-icon switch-primary">
															<label> <input id='checkBoxLeverCromp' name=''
																class=" mt-1" type='checkbox' checked value='S' /> <span></span>
														</label>
														</span>
													</div>
													<input id="  " name="estado" type="text"
														class="form-control mt-1 ocultar" autocomplete="off"
														placeholder="" />
												</div>
											</div>
											<div class="col-12">
												<label class="col-md-12 checkbox checkbox-primary">
													<input id="chbxvistaPrevia" type="checkbox" /> Vista
													previa? <span></span>
												</label>
											</div>
										</div>
									</div>
								</div>
								<!--end: Wizard Step 4-->
								<!--begin: Wizard Step 5-->
								<!--end: Wizard Step 5-->
								<!--begin: Wizard Actions-->
								<div
									class="d-flex justify-content-between border-top mt-1 pt-1">
									<div class="mr-2">
										<button id="antButton"
											class="btn btn-light-primary font-weight-bold px-9 py-4 ml-5"
											data-wizard-type="action-prev">Anterior</button>
									</div>
									<div>
										<button
											class="btn btn-primary font-weight-bold  px-9 py-4 mr-5"
											data-wizard-type="action-submit" type="submit">Guardar</button>
										<button id="sgtButton"
											class="btn btn-primary font-weight-bold px-9 py-4 mr-5"
											data-wizard-type="action-next" type="button">Siguiente</button>
									</div>
								</div>
								<input id="idPaciente" name="datosPacientes" /> <input
									id="idMedico" class="ocultar" name="dfIdmedico" /> <input
									id="idNumPage" class="ocultar" name="" />
								<!--end: Wizard Actions-->
							</form>
							<!-- Button trigger modal -->
							<button id="BotonAbrirModalExitoso" type="button"
								class="ocultar btn btn-primary" data-toggle="modal"
								data-target="#exampleModal"></button>

							<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalLabel"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-body">
											<h6>Orden registrada satisfactoriamente</h6>
											<br>
											<h3 id="numeroOrdenTitulo"></h3>
											<div class="col-8 row">
												<div class="col-md-12">
													<label>Imprimir comprobante de atenci&oacute;n? </label>
												</div>
												<div class="col-3">
													<a id="imprimirComprobante" class="" href="#"> <span
														class='symbol symbol-50 mr-3'> <span
															class='symbol-label'><i class="la la-print"></i></i></span>
													</span> <span class="navi-text"></span>
													</a>
												</div>
												<div class="col-9">
													<label id="lblComprobanteOVoucher"
														class="col-12 col-form-label">Comprobante</label>
													<div class="col-6">
														<span id="spanCheck"
															class="switch switch-outline switch-icon switch-primary">
															<label> <input id='checkBoxLeverCromp' name=''
																class=" mt-1" type='checkbox' checked value='S' /> <span></span>
														</label>
														</span>
													</div>
													<input id="txtEstado" name="estado" type="text"
														class="form-control mt-1 ocultar" autocomplete="off"
														placeholder="" />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-dismiss="modal">Ok</button>
										</div>
									</div>
								</div>
							</div>
							<!--end: Wizard Form-->
						</div>
					</div>
					<!--end: Wizard Body-->
				</div>
			</div>
		</div>
		<!--end: Wizard-->
	</div>
	<!-- FIN FORMULARIO PACIENTE -->
        <jsp:include page="common/Scripts.jsp"/>
	<script src="<%=request.getContextPath()%>/resources/js/Orden.validadores.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Orden.wizard.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Orden.functions.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Orden.binding.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/dual-listbox-env.js"></script>

</body>
</html>

