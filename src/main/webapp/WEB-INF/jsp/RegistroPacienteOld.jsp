<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="font-size: 10px">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="common/Styles.jsp" />
<title>Registro Paciente</title>
<link rel="icon" type="image/jpg"
	href="<%=request.getContextPath()%>/resources/img/gbios.png">
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
	<jsp:include page="common/Menu.jsp" />
	<jsp:include page="common/Header.jsp" />
	<div class="container">
		<div id='idAccordion' class="accordion">
			<div id="panelSuperior" class="card ">
				<div class="card-header " id="headingOne8">
					<div class="card-title row justify-content-between "
						data-toggle="collapse" data-target="#panelBusquedaPaciente">
						<h3 class="card-label col-3 ">Búsqueda de Paciente</h3>
						<div class="col-4 justify-content-right">
							<div class=" float-right">
								<a id="btnLimpiarFiltro" class="navi-link"> <span
									class="symbol symbol-30 symbol-circle "> <span
										id="circuloLimpiarFiltro"
										class="symbol-label bg-hover-primary  hoverIcon" href="#"
										data-toggle="tooltip" title="Limpiar"><i
											id="iLimpiarFiltro" class="la la-broom icon-xl text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
							</div>
							<div class=" float-right">
								<a id="nuevoPaciente" class="navi-link"> <span
									class='symbol symbol-30 symbol-circle '> <span
										id="circuloAgregarPaciente"
										class='symbol-label bg-hover-primary  hoverIcon' href="#"
										data-toggle="tooltip" title="Nuevo paciente"><i
											id="iAgregarPaciente"
											class='fa fas fa-user-plus  text-primary'></i></span>
								</span>
								</a>
							</div>
						</div>
					</div>
				</div>
        <jsp:include page="BuscarPaciente.jsp" />
			</div>
			<!-- ********************************************************* FORMULARIO REGISTRO PACIENTE ***********************************************-->

			<div id="panelInferior" class="card">
				<div class="card-header " id=headingRegistroPaciente"">
					<div class="card-title row justify-content-between  ">
						<h3 class="card-label col-3 ">Registro de Paciente</h3>

						<!-- ************************************** Botonera Registro Paciente **************************-->
						<div class="col-4 justify-content-right ">
							<div class="float-right">
<div id ="divbtnLimpiar">
								<a id="btnLimpiar" class="navi-link pointer"> <span
									class="symbol symbol-30 symbol-circle "> <span
										id="circuloLimpiar"
										class="symbol-label bg-hover-primary hoverIcon"
										data-toggle="tooltip" title="Limpiar"><i id="iLimpiar"
											class="la la-broom icon-xl  text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
</div>
							</div>
							<div class="float-right">
								<div id ="divbtnEditar">
									<a id="btnEditar" class="navi-link pointer"> <span
										class='symbol symbol-30 symbol-circle'> <span
											id="circuloEditarPaciente" class='symbol-label circuloIcon'
											data-toggle="tooltip" title="Editar paciente"><i
												id="iEditPaciente" class="far fa-edit text-dark-50"></i></span>
									</span>
									</a>
								</div>
							</div>
							<div class="float-right">
                <div id ="divbuscarPaciente">
								<a id="buscarPaciente" class="navi-link" href="#"> <span
									class='symbol symbol-30 symbol-circle mr-3'> <span
										id="circuloBuscarPaciente"
										class='symbol-label bg-hover-primary hoverIcon'
										data-toggle="tooltip" title="Buscar paciente"><i
											id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
								</span>
								</a>
                </div>
							</div>
						</div>
						<!-- ************************************** Botonera Registro Paciente **************************-->


					</div>
				</div>

				<!--  ********************************************************** Registro de paciente *****************************************************************  -->
				<div id="panelRegistroPaciente ">
					<div class="card-body no-gutters">
						<form id="formRegistroPaciente" method="post">
							<input type="hidden" id="ipEquipo" name="ipEquipo"> <input
								id="txtIdPaciente" name="dpIdpaciente" type="hidden" value="0" />
							<input type="hidden" id="dpIdmadre" name="dpIdmadre">


							<div class="row" id="divForm">
								<!-- inicio Formulario registro -->
								<!-- Fin Formulario registro -->

								<!-- Inicio Panel Tabulados -->
								<div class="col-10">
									<div class="tab-content " id="myTabContent">

										<!-- Inicio Panel Datos básicos -->
										<div class="row tab-pane fade show active"
											id="tab_pane_datos_basicos" role="tabpanel"
											aria-labelledby="tab_pane_datos_basicos"">
											<div class="col">
												<div class="row" id="filaDocNombre">
													<div id="divTipoDocumento" class=" col-3">
														<label for="selectTipoDocumento" class="">Tipo
															documento :</label> <select id="selectTipoDocumento"
															class="form-control selectpicker "
															name="ldvTiposdocumentos">
															<c:forEach var="documento"
																items="${listaTiposDocumentos}">
																<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div id="divTxtRut" class=" col-2">
														<label>RUN: *</label> <input id="txtRut" name="dpRun"
															type="text" class="form-control " placeholder=""
															minlength="11" maxlength="12" autocomplete="off"
															onkeyup="this.value = this.value.toUpperCase();" />
														<div class="invalid-feedback">RUN inv&aacute;lido</div>
													</div>
													<div id="divTxtPasaporte" class=" col-2 ocultar"
														style="display: none;">
														<label>Pasaporte: *</label> <input id="txtPasaporte"
															name="dpNrodocumento" type="text" class="form-control"
															autocomplete="off" placeholder="" />
													</div>
													<div id="divTxtNombre" class=" col-3">
														<label>Nombres: *<span class="label"
															data-toggle="popover" data-html="true"
															data-content="Nombre legal, debe ser idéntico al carné de identidad. Para variaciones usar campo nombre social."><i
																class="fa la-info-circle"></i></span></label> <input id="txtNombre"
															name="dpNombres" type="text"
															class="form-control  disabledForm" autocomplete="off"
															placeholder="" />
													</div>
													<div id="divPrimerApellido" class=" col-2 ">
														<label>Primer Apellido: *</label> <input
															id="txtPrimerApellido" name="dpPrimerapellido"
															type="text" class="form-control disabledForm"
															autocomplete="off" placeholder="" />
													</div>
													<div id="divSegundoApellido" class=" col-2">
														<label>Segundo Apellido: </label> <input
															id="txtSegundoApellido" name="dpSegundoapellido"
															type="text" class="form-control disabledForm"
															autocomplete="off" placeholder="" />
													</div>

												</div>

												<div class="row" id="filaSexoNacimiento">
													<div class=" col-2 ">
														<label for="selectSexo" class="">Sexo: *<span
															class="label" data-toggle="popover" data-html="true"
															data-content="Sexo biológico o sexo al nacer. Requerido para análisis objetivos de datos bioquímicos u hormonales."><i
																class="fa la-info-circle"></i></span>
														</label> <select class="form-control disabledForm"
															id="selectSexo" name="ldvSexo">
															<option value="N" disabled selected>Seleccionar</option>
                              <c:forEach var="sexo" items="${listaSexo}">
                                <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                              </c:forEach>
														</select>
													</div>
													<div class=" col-2 ">
														<label for="txtFechaNacimiento">Fec
															Nacimiento:&nbsp*</label> <input id="txtFechaNacimiento"
															name="dpFnacimiento" class="form-control disabledForm "
															data-provide="datepicker" data-date-language="es" data-autoclose="true"
															autocomplete="off" placeholder="" />
													</div>
													<div class=" col-2 ">
														<label for="txtEdad">Edad (a-m-d):</label> <input
															id="txtEdad" name="" type="text" class="form-control "
															placeholder="" disabled />
													</div>

													<div class=" col-2  " hidden="true">
														<label for="txtAnio">Edad :</label>

													</div>
													<div class=" col-1 " hidden="true">
														<label for="txtMeses">Meses :</label> <input id="txtMeses"
															name="" type="text" class="form-control  " placeholder=""
															disabled />
													</div>
													<div class=" col-1 " hidden="true">
														<label for="txtDias">Días :</label> <input id="txtDias"
															name="" type="text" class="form-control  " placeholder=""
															disabled />
													</div>
													<div class=" col-2 ">
														<label>Teléfono: </label> <input id="txtTelefono"
															name="dpTelefono" type="text"
															class="form-control phone_cl "
															placeholder="" />
													</div>
													<div class=" col-4 ">
														<label>Email: </label> <input id="txtEmail" name="dpEmail"
															type="text" class="form-control email_cl" placeholder="" />
													</div>
												</div>

												<div class="row" id="filaDireccion">

													<div class="col-3 ">
														<label class="">Region: </label> <select id="selectRegion"
															name="ldvrIdregion"
															class="form-control disabledForm"
															data-live-search="true">
															<option value="" disabled selected>Seleccionar</option>
                              <c:forEach var="region" items="${listaRegiones}">
                                <option value="${region.ldvrIdregion}">${region.ldvrDescripcion}</option>
                              </c:forEach>
														</select>
													</div>
													<div class="col-3 ">
														<label class="">Comuna : </label> <select
															id="selectComuna" name="ldvcIdcomuna"
															class="form-control selectpicker disabledForm"
															data-live-search="true"
															data-none-selected-text="Seleccionar">
															<option value="" disabled selected>Seleccionar</option>
														</select>
													</div>
													<div class="col-4 ">
														<label>Dirección: </label> <input id="txtDireccion"
															name="dpDireccion" type="text"
															class="form-control disabledForm" autocomplete="off"
															placeholder="" />
													</div>
													<div class="col-2 ">
														<label>Código postal: </label> <input id="txtCodigoPostal"
															name="dpDireccodpostal" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>

												</div>

												<hr />
												<p>Datos contacto</p>
												<div class="row border-top" id="filaDatosContacto">
													<div class=" col-3 align-items-center">
														<label>Nombre:</label> <input id="txtNombreContacto"
															name="dcNombre" type="text" class="form-control "
															placeholder="" />
													</div>
													<div class=" col-3 ">
														<label class="">Relaci&oacute;n: </label> <select
															id="selectPacienteRelacion"
															name="LdvContactospacientesrelacion.ldvcprIdcontactopacienterel"
															class="form-control selectpicker ">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="pacRelacion"
																items="${listaPacienteRelacion}">
																<option
																	value="${pacRelacion.ldvcprIdcontactopacienterel}">${pacRelacion.ldvcprDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class=" col-3 ">
														<label>Tel&eacute;fono: </label> <input
															id="txtTelefonoContacto" name="dcTelefono" type="text"
															class="form-control phone_cl"
															placeholder="[9|2]-dddd-dddd" />
													</div>
													<div class="col-3 ">
														<label class="">Sexo: </label> <select
															id="selectSexoContacto" name="dcIdsexo"
															class="form-control selectpicker "
															data-header="Seleccionar">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="sexo" items="${listaSexo}">
																<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
															</c:forEach>
														</select>
													</div>
												</div>

											</div>
										</div>
										<!-- Fin Panel Datos básicos -->
										<!-- Inicio Panel Datos Extras -->
										<div class="row tab-pane fade" id="tab_pane_datos_extras"
											role="tabpanel" aria-labelledby="tab_pane_datos_extras">
											<div class="col">
												<div class="row" id="filaCheckBox">
													<div class="col">
														<div class="checkbox-inline">
															<label class="checkbox checkbox-primary "> <input
																id="checkboxVIP" name="dpEsvip" type="checkbox"
																class="disabledForm" /> Paciente VIP <span></span>
															</label> <label class="checkbox checkbox-primary"> <input
																id="checkboxAfro" name="dpEsafroamericano"
																type="checkbox" class="disabledForm" /> Paciente
																Afroamericano <span></span>
															</label><span class="label" data-toggle="popover"
																data-html="true"
																data-content="Dato requerido para el análisis de la función renal mediante la fórmula MDRD."><i
																class="fa la-info-circle"></i></span> <label
																class="checkbox checkbox-primary "> <input
																id="checkBoxExitus" name="exitus" type="checkbox"
																disabled /><span></span> Fallecimiento
															</label>
														</div>
													</div>
												</div>
												<div class="row" id="filaNacEstCivNomSocial">
													<div class="col-4">
														<label class="">Pais Origen: </label> <select
															id="selectPais" name="ldvpIdpais"
															class="form-control selectpicker disabledForm"
															data-live-search="true">
															<option value="" disabled selected>Seleccionar</option>
															<c:forEach var="pais" items="${listaPaises}">
																<option value="${pais.ldvpIdpais}">${pais.ldvpDescripcion}</option>
															</c:forEach>
														</select>
													</div>

													<div class="col-4">
														<label class="">Estado Civil: </label> <select
															id="selectEstadoCivil" name="ldvecIdestadocivil"
															class="form-control selectpicker disabledForm">
															<option value="" disabled selected>Seleccionar</option>
															<c:forEach var="estadoCivil"
																items="${listaEstadosCiviles}">
																<option value="${estadoCivil.ldvecIdestadocivil}">${estadoCivil.ldvecDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-4">
														<label>Nombre Social: </label> <input id="txtNombreSocial"
															name="dpNombresocial" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>


												</div>
												<div class="row" id="filaObservaciones">
													<div class="col">
														<label>Observaciones: </label> <input
															id="txtObservaciones" name="dpObservacion" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>
												</div>
											</div>

										</div>
										<!-- Fin Panel Datos Extras -->
										<!-- Inicio Panel Patologías -->
										<div class="row tab-pane fade" id="tab_pane_patologias"
											role="tabpanel" aria-labelledby="tab_pane_patologias">
											<div class="col">
												<div class="row" id="filaPAtologias">
													<div class="col-4">
														<label class="">Patologia: </label> <select
															id="selectPatologias" class="form-control selectpicker ">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="patologia" items="${listaPatologias}">
																<option value="${patologia.ldvpatIdpatologia}">${patologia.ldvpatDescripcion}</option>
															</c:forEach>
														</select>
														<button id="btnPatologia" type="button"
															class="btn btn-light-primary font-weight-bold">Agregar
															Patologia</button>
													</div>
													<div class="col-8 ">
														<table id='tablaPatologia'>
															<thead>
																<tr>
																	<th>Patologia</th>
																	<th>Observacion</th>
																	<th>Eliminar</th>
																</tr>
															</thead>
															<tbody id="tbodyPatologias">

															</tbody>
														</table>
													</div>
												</div>
											</div>

										</div>
										<!-- Fin Panel Datos Patologías -->
										<!--  Inicio panel datos madre -->
										<div class="row tab-pane fade" id="tab_pane_madre"
											role="tabpanel" aria-labelledby="tab_pane_madre">
											<div class="col">
												<div class="row" id="filaDatoMadre">
													<div class="col-6">
														<label id="lblRunMadre">RUN Madre: </label> <input
															id="txtRutMadre" name="rutMadre" type="text"
															class="form-control " placeholder="" minlength="11"
															maxlength="12"
															onkeyup="this.value = this.value.toUpperCase();" />
														<div id="divFeedbackRutMadre" class="invalid-feedback">RUN
															inv&aacute;lido</div>
													</div>
													<div class="checkbox-inline col-3 ">
														<label class="checkbox checkbox-primary "> <input
															id="checkboxPartoMultiple" name="esPartoMultiple"
															type="checkbox" class="" /> Parto Múltiple <span></span>
														</label>
													</div>
													<div class="col-3">
														<label>N° Parto M&uacute;ltiple: </label> <input
															id="txtNroPartoMultiple" name="dpRnnumero" type="number"
															min="1" class="form-control " placeholder="" />
													</div>

												</div>
											</div>
										</div>
										<!--  Fin panel datos madre -->
									</div>
								</div>
								<!-- Fin Formulario registro -->



								<!-- Inicio Tabuladores -->
								<div class="col-2">
									<ul class="nav  flex-column nav-pills">
										<li class="nav-item"><a id="anchorRegistro"
											class="nav-link active" data-toggle="tab"
											href="#tab_pane_datos_basicos"> <span class="nav-icon"><i
													class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp;
													Básicos</span>
										</a></li>
										<li id="liDatosMadres" class="nav-item ocultar"><a
											class="nav-link" data-toggle="tab" href="#tab_pane_madre">
												<span class="nav-icon"><i class="fas fa-female"></i></span>
												<span class="nav-text">Datos&nbsp;Madre</span> <span
												id="spanDatosMadre"
												class="ocultar label label-danger font-weight-bold label-inline"><i
													class="fa la-exclamation-circle text-white"></i></span>
										</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#tab_pane_datos_extras"> <span
												class="nav-icon"><i class="flaticon2-copy"></i></span> <span
												class="nav-text">Datos Extras</span>
										</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#tab_pane_patologias"> <span
												class="nav-icon"><i class="fas fa-book-medical"></i></span>
												<span class="nav-text">Patologias</span>
										</a></li>
									</ul>

								</div>
								<!-- Fin Tabuladores -->
								<button id="btnAgregarPaciente" name="insert" type="submit"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 ">
									<i class="far fa-save"></i>Guardar
								</button>
								<button id="btnUpdatePaciente" name="update" type="button"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 ocultar"
									value="">Confirmar Datos</button>
								<a id="btnConfirmarDatosPac"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 float-right ocultar"><i
									class="la la-search"></i>Confirmar</a>
								<button id="btnCancelEdit" name="cancel" type="button"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2"
									value="">Cancelar</button>
							</div>
						</form>

					</div>
				</div>
			</div>
			<!-- Fin Panel inferior  -->
		</div>
		<!-- Fin accordion  -->
	</div>
	<!-- Fin container -->
	<jsp:include page="common/Scripts.jsp" />
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/jquery.mask.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteFunciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteVisualizacion.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteValidaciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteFiltros.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteJs.js"></script>

</body>

</html>
