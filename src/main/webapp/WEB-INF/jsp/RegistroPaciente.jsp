<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1" />
<title>BiosLIS | Nuevo Paciente</title>
<link rel="icon" type="image/jpg"
	href="<%=request.getContextPath()%>/resources/img/gbios.png">
                    <jsp:include page="common/Styles_1.jsp" />
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
  <jsp:include page="common/AlertaSession.jsp" />
	
        <!-- input hidden (permiso para editar paciente) añadido por Marco Caracciolo 30/11/2022 -->
        <input type="hidden" id="editaPaciente" name="editaPaciente" value="${perfilUsuario.cpuEditadatospaciente}" />
        
	<div class="container container-main-content">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0" id="registro-paciente-main">

				<div id="panelSuperior" class="card">
					<div class="card-header" id="registro-paciente-title">
						<div class="card-title d-flex justify-content-between">
							<h3 class="mb-0 btn-block text-left pl-0 col-10"
								data-toggle="collapse"
								data-target="#panelBusquedaPaciente" aria-expanded="false"
								aria-controls="panelBusquedaPaciente">B&uacute;squeda de Pacientes</h3>
							<div class="card-buttons col-2 d-flex justify-content-end">
								<a id="nuevoPaciente" class="navi-link"> <span
									class='symbol symbol-30 symbol-circle '> <span
										id="circuloAgregarPaciente"
										class='symbol-label bg-hover-blue  hoverIcon' href="#"
										data-toggle="tooltip" title="Nuevo paciente"> 
										<div class="icon-buenos icon-blue icon-hover"></div>
										<!-- <span class="mi-icono circulo-plus"> -->
											<!-- <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-circle-plus" viewBox="0 0 24 24" stroke-width="1.5" stroke="#09425A" fill="none" stroke-linecap="round" stroke-linejoin="round">
												<path stroke="none" d="M0 0h24v24H0z" fill="none"/>
												<circle cx="12" cy="12" r="11" />
												<line x1="12" y1="6" x2="12" y2="18" />
												<line x1="18" y1="12" x2="6" y2="12" />
											</svg> -->
										<!-- </span> -->
										<!-- <i
											id="iAgregarPaciente" class='fa fas fa-user-plus  text-blue'></i> -->
									</span>
								</span>
								</a> <a id="btnLimpiarFiltro" class="navi-link"> <span
									class="symbol symbol-30 symbol-circle "> 
									<span
										id="circuloLimpiarFiltro"
										class="symbol-label bg-hover-blue  hoverIcon limpiar-icon icon-blue icon-hover" href="#"
										data-toggle="tooltip" title="Limpiar"> 
										
										<!-- <i
											id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i> -->
									</span>
								</span> <span class="navi-text"></span>
								</a>
							</div>
						</div>
					</div>
					<div id="panelBusquedaPaciente"
						class="collapse container-content"
						aria-labelledby="registro-paciente-title"
						data-parent="#registro-paciente-main">
						<div class="card-body d-flex">
							<!-- FORMULARIO FILTRO -->
							<div class="col-6 pl-0">
								<div id='divSelTipoDocumento' class=" row">
									<label class="col-form-label text-left col-4 ">Tipo de
										documento</label>
									<div class="col-8">
										<select id="selectTipoDocumentoFiltro"
											class="form-control selectpicker">
											<option value="-1">TODOS</option>
											<c:forEach var="documento" items="${listaTiposDocumentos}">
												<option value="${documento.ldvtdIdtipodocumento}">
													${documento.ldvtdDescripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div id='divTxtRutFiltro' class=" row  ">
									<label id="lblRutFiltro" class="col-4 col-form-label">RUN
									</label> <label id="lblRutFiltroMadre" class="col-4 col-form-label"
										style="display: none;">RUN Madre </label>
									<div class="col-8">
										<input class="form-control" placeholder="" id="txtFiltroRut"
											type="text" minlength="11" maxlength="12"
											onkeyup="this.value = this.value.toUpperCase();" />
									</div>
								</div>
								<div id='divTxtPasaporteFiltro' class="ocultar    row"
									style="display: none;">
									<label class="col-4 col-form-label">Pasaporte </label>
									<div class="col-8">
										<input class="form-control" placeholder="" maxlength="10"
											id="txtPasaporteFiltro" type="text" />
									</div>
								</div>
								<div id='divTxtDniFiltro' class="ocultar    row"
									style="display: none;">
									<label class="col-4 col-form-label">DNI </label>
									<div class="col-8">
										<input class="form-control" placeholder="" maxlength="10"
											id="txtDniFiltro" type="text" />
									</div>
								</div>
								<div id='divTxtNNFiltro' class=" row ocultar  "
									style="display: none;">
									<label class="col-4 col-form-label">Fecha Ingreso </label>
									<div class="col-8">
										<input class="form-control" placeholder="" id="txtFiltroNN"
											type="text" data-provide="datepicker" data-date-language="es" />
									</div>
								</div>
								<div class=" row  ">
									<label class="col-4 col-form-label">Nombre</label>
									<div class="col-8">
										<input class="form-control" placeholder=""
											id="txtFiltroNombre" type="text" />
									</div>
								</div>
								<div class=" row  ">
									<label class="col-4 col-form-label">Primer Apellido</label>
									<div class="col-8">
										<input class="form-control" placeholder=""
											id="txtFiltroApellido" type="text" />
									</div>
								</div>
								<div class=" row">
									<label class="col-4 col-form-label">Segundo Apellido</label>
									<div class="col-8">
										<input class="form-control" placeholder=""
											id="txtFiltroSegundoApellido" type="text" />
									</div>
								</div>

								<a id="btnBuscarFiltro" href="#"
									class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 "><i
									class="la la-search"></i>Buscar</a>
							
							</div>
							
							<!-- FIN FORMULARIO FILTRO -->
							<!-- TABLA FILTRO -->
							<div class="col">
								<div class="table-container mb-3">
									<table id="tableFiltro" class="table table-hover table-striped">
										<thead>
											<tr id="trHeader">
												<th scope="col">#</th>
												<th id="thIdentificador" scope="col">Identificador</th>
												<th id="thNombre" scope="col">Nombre</th>
											</tr>
										</thead>
										<tbody id="tbodyFiltro">
	
										</tbody>
									</table>
	
									<label id="lblErrorFiltro" class="textError text-red ocultar"
										style="display: none;">No se encontraron pacientes con
										los datos provistos.</label>
								</div>
								
								<label id="lblTotalFiltro" hidden>Pacientes encontrados: <span id="totalFiltro"></span></label>
								
							</div>
							<!-- FIN TABLA FILTRO -->
						</div>
					</div>
				</div>

				<!-- ********************************************************* FORMULARIO REGISTRO PACIENTE ***********************************************-->


				<div id="panelInferior" class="card">
					<div class="card-header " id="headingRegistroPaciente">
						<div class="card-title row justify-content-between  ">
							<div class="col-10">
								<div class="row">
									<h3 class="card-label col-md-3 col-xl-4 pl-0" data-toggle="collapse" data-target="#panelRegistroPaciente" aria-expanded="true" aria-controls="panelRegistroPaciente">Registro de Paciente</h3>
									<!-- Inicio Tabuladores -->
									<div class="col-md-9 col-xl-8">
										<ul class="nav nav-pills nav-fill nav-justified">
												<li class="nav-item"><a id="anchorRegistro" class="nav-link active" data-toggle="tab" href="#tab_pane_datos_basicos"> <span class="nav-icon"><i class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp;Básicos</span></a></li>
												<li id="liDatosMadres" class="nav-item ocultar" style="display: none;"><a class="nav-link" data-toggle="tab" href="#tab_pane_madre"><span class="nav-icon"><i class="fas fa-female" aria-hidden="true"></i></span><span class="nav-text">Datos&nbsp;Madre</span></a></li>
												<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab_pane_datos_extras"><span class="nav-icon"><i class="flaticon2-copy"></i></span><span class="nav-text">Datos&nbsp;Extras</span></a></li>
												<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab_pane_patologias"><span class="nav-icon"><i class="fas fa-book-medical" aria-hidden="true"></i></span><span class="nav-text">Patologías</span></a></li>
										</ul>
								</div>
									<!-- Fin Tabuladores -->
								</div>
							</div>

                                             
                                                <!-- ************************************** Botonera Registro Paciente **************************-->
                                                <div class="col-2 justify-content-right iconos-registro ">
                                                    <div class="float-right">
                                                        <div id="divbtnLimpiar">
                                                            <a id="btnLimpiar" class="navi-link pointer">
                                                                <span class="symbol symbol-30 symbol-circle">
                                                                    <span id="circuloLimpiar" data-toggle="tooltip"
                                                                        title="Limpiar"
                                                                        class="symbol-label bg-hover-blue limpiar-icon icon-blue icon-hover">
                                                                        <!-- <i id="iLimpiar"
                                                                            class="la la-broom icon-xl  text-blue"></i> -->
																																			</span>
                                                                </span> <span class="navi-text"></span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <!-- c:if añadido por Marco Caracciolo 01/12/2022 -->
                                                    <c:if test = "${perfilUsuario.cpuEditadatospaciente == 'S'}">
                                                    <div class="float-right">
                                                        <div id="divbtnEditar">
                                                            <a id="btnEditar" class="navi-link pointer"> <span
                                                                    class='symbol symbol-30 symbol-circle'>
                                                                    <span id="circuloEditarPaciente"
                                                                        class='symbol-label circuloIcon'
                                                                        data-toggle="tooltip" title="Editar paciente">
                                                                        <i id="iEditPaciente"
                                                                            class="far fa-edit text-dark-50 "></i>
                                                                    </span>
                                                                </span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    </c:if>
                                                    <div class="float-right">
                                                        <div id="divbuscarPaciente">
                                                            <a id="buscarPaciente" class="navi-link" href="#"> <span
                                                                    class='symbol symbol-30 symbol-circle mr-3'> 
																																		<span
                                                                        id="circuloBuscarPaciente"
                                                                        class='symbol-label bg-hover-blue hoverIcon buscar-icon icon-blue icon-hover'
                                                                         data-toggle="tooltip" title="Buscar paciente">
																																				 <!-- <i
                                                                            id="iBuscarPaciente"
                                                                            class="fas fa-search text-blue"></i> -->
																																		</span>
                                                                </span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- ************************************** Botonera Registro Paciente **************************-->


						</div>
					</div>
					<!--  ********************************************************** Registro de paciente *****************************************************************  -->
					<div id="panelRegistroPaciente" class="collapse show">
						<div class="card-body no-gutters">
							<form id="formRegistroPaciente" method="post">
								<input type="hidden" id="ipEquipo" name="ipEquipo"> <input
									id="txtIdPaciente" name="dpIdpaciente" type="hidden" value="0" />
								<input type="hidden" id="dpIdmadre" name="dpIdmadre">


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
												<div class="col">
													<div class="row" id="filaDocNombre">
														<div id="divTipoDocumento" class=" col-3">
															<label for="selectTipoDocumento" class="">Tipo
																documento</label> <select id="selectTipoDocumento"
																class="form-control selectpicker "
																name=ldvtdIdtipodocumento>
																<c:forEach var="documento"
																	items="${listaTiposDocumentos}">
																	<option value="${documento.ldvtdIdtipodocumento}">
																		${documento.ldvtdDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div id="divTxtRut" class=" col-2">
															<label>RUN *</label> <input id="txtRut" name="dpRun"
																type="text" class="form-control " placeholder=""
																minlength="11" maxlength="12" autocomplete="off"
																onkeyup="this.value = this.value.toUpperCase();" />
														</div>
														<div id="divTxtPasaporte" class=" col-2 ocultar"
															style="display: none;">
															<label>Pasaporte *</label> <input id="txtPasaporte"
																name="dpNrodocumento" type="text" class="form-control"
																autocomplete="off" placeholder="" maxlength="40"/>
														</div>
														<div id="divTxtDni" class=" col-2 ocultar"
															style="display: none;">
															<label>DNI *</label> <input id="txtDni"
																name="dpDni" type="text" class="form-control"
																autocomplete="off" placeholder="" maxlength="40"/>
														</div>
														<div id="divTxtNombre" class=" col-3">
															<label>Nombres *<span class="label"
																data-toggle="popover" data-html="true"
																data-content="Nombre legal, debe ser idéntico al de la cédula de identidad. Para variaciones usar campo nombre social."><i
																	class="fa la-info-circle"></i></span></label> <input id="txtNombre" 
																name="dpNombres" type="text"
																class="form-control  disabledForm" autocomplete="off"
																placeholder="" />
														</div>
														<div id="divPrimerApellido" class=" col-2 ">
															<label>Primer Apellido *</label> <input
																id="txtPrimerApellido" name="dpPrimerapellido"
																type="text" class="form-control disabledForm"
																autocomplete="off" placeholder="" />
														</div>
														<div id="divSegundoApellido" class=" col-2">
															<label>Segundo Apellido </label> <input
																id="txtSegundoApellido" name="dpSegundoapellido"
																type="text" class="form-control disabledForm"
																autocomplete="off" placeholder="" />
														</div>

													</div>

													<div class="row pt-1" id="filaSexoNacimiento">
														<div class=" col-2 ">
															<label for="selectSexo" class="">Sexo *<span
																class="label" data-toggle="popover" data-html="true"
																data-content="Sexo biológico o sexo al nacer. Requerido para análisis objetivos de datos bioquímicos u hormonales."><i
																	class="fa la-info-circle"></i></span>
															</label> <select class="form-control selectpicker disabledForm"
																id="selectSexo" name="ldvSexo">
																<c:forEach var="sexo" items="${listaSexo}">
																	<option value="${sexo.ldvsIdsexo}">
																		${sexo.ldvsDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div class=" col-2 ">
															<label for="txtFechaNacimiento">Fecha de Nacimiento *</label> <input id="txtFechaNacimiento"
																name="dpFnacimiento" class="form-control disabledForm "
																data-provide="datepicker" data-date-language="es" type="text" 
																data-autoclose="true" autocomplete="off" placeholder="" />
														</div>
														<div class=" col-2 ">
															<label for="txtEdad">Edad</label> <input id="txtEdad"
																name="" type="text" class="form-control " placeholder=""
																disabled />
														</div>

														<div class=" col-2  " hidden="true">
															<label for="txtAnio">Edad </label>

														</div>
														<div class=" col-1 " hidden="true">
															<label for="txtMeses">Meses </label> <input id="txtMeses"
																name="" type="text" class="form-control  "
																placeholder="" disabled />
														</div>
														<div class=" col-1 " hidden="true">
															<label for="txtDias">Días </label> <input id="txtDias"
																name="" type="text" class="form-control  "
																placeholder="" disabled />
														</div>
														<div class=" col-2 ">
															<label>Teléfono </label> <input id="txtTelefono"
																name="dpTelefono" type="text" disabled
																class="form-control phone_cl disabledForm" placeholder="" />
														</div>
														<div class=" col-4 ">
															<label>E-mail </label> <input id="txtEmail" name="dpEmail" disabled
																type="text" class="form-control email_cl disabledForm" placeholder="" />
														</div>
													</div>

													<div class="row pt-1" id="filaDireccion">

                                                                            <div class="col-4 ">
                                                                                <label class="">Regi&oacute;n  </label> <select
                                                                                    id="selectRegion"
                                                                                    name="ldvrIdregion"
                                                                                    class="form-control selectpicker disabledForm"
                                                                                    data-live-search="true">   
                                                                                    <c:forEach var="region"
                                                                                        items="${listaRegiones}">
                                                                                        <option
                                                                                            value="${region.ldvrIdregion}">
                                                                                            ${region.ldvrDescripcion}
                                                                                        </option>
                                                                                    </c:forEach>
                                                                                </select>
                                                                            </div>
                                                                            <div class="col-4 comuna-container">
                                                                                <label class="">Comuna </label> <select
                                                                                    id="selectComuna"
                                                                                    name="ldvcIdcomuna"
                                                                                    class="form-control selectpicker disabledForm"
                                                                                    data-live-search="true"
                                                                                    data-none-selected-text="">
                                                                                </select>
                                                                            </div>
                                                                            <div class="col-4 ">
                                                                                <label>Dirección </label> <input
                                                                                    id="txtDireccion" name="dpDireccion"
                                                                                    type="text"
                                                                                    class="form-control disabledForm"
                                                                                    autocomplete="off" placeholder="" />
                                                                            </div>
                                                                        </div>

													<hr />
													<p>
														<b>Datos contacto</b>
													</p>
													<div class="row border-top" id="filaDatosContacto">
														<div class=" col-3 align-items-center">
															<label>Nombre</label> <input id="txtNombreContacto" disabled
																name="dcNombre" type="text" class="form-control disabledForm"
																placeholder="" />
														</div>
														<div class=" col-3 ">
															<label class="">Relaci&oacute;n </label> <select
																id="selectPacienteRelacion"
																name="LdvContactospacientesrelacion.ldvcprIdcontactopacienterel"
																class="form-control selectpicker disabledForm" 	data-live-search="true">
																<c:forEach var="pacRelacion"
																	items="${listaPacienteRelacion}">
																	<option
																		value="${pacRelacion.ldvcprIdcontactopacienterel}">
																		${pacRelacion.ldvcprDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div class=" col-3 ">
															<label>Tel&eacute;fono</label> <input
																id="txtTelefonoContacto" name="dcTelefono" type="text"
																class="form-control phone_cl disabledForm" disabled
																placeholder="[9|2]-dddd-dddd" />
														</div>
														<div class="col-3 ">
															<label class="">Sexo </label> <select
																id="selectSexoContacto" name="dcIdsexo"
																class="form-control selectpicker disabledForm"
																data-header="Seleccionar">
																<c:forEach var="sexo" items="${listaSexo}">
																	<option value="${sexo.ldvsIdsexo}">
																		${sexo.ldvsDescripcion}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div>
														<a  id="btnTrazabilidad" href="#"
														class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 float-right"><i
														class="la la-search"></i>Trazabilidad</a>
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
																<label class=" "> <input
																	id="checkboxVIP" name="dpEsvip" type="checkbox"
																	class="disabledForm" /> Paciente VIP <span></span>
																</label> 
																<label class=""> <input
																	id="checkboxAfro" name="dpEsafroamericano"
																	type="checkbox" class="disabledForm" /> Paciente
																	Afroamericano <span></span>
																</label><span class="label" data-toggle="popover"
																	data-html="true"
																	data-content="Dato requerido para el análisis de la función renal mediante la fórmula MDRD."><i
																	class="fa la-info-circle"></i></span>
																	
																	<div id ="divIdCheckBoxExitus">
																	 <label
																	class="fallecimiento-checkbox checkbox checkbox-primary ocultar">
																	<input id="checkBoxExitus" name="exitus"
																	type="checkbox" disabled /><span></span> Fallecimiento
																</label>
																</div>
															</div>
														</div>
													</div>
													<div class="row" id="filaNacEstCivNomSocial">
														<div class="col-3">
															<label class="">Pais Origen </label> <select
																id="selectPais" name="ldvpIdpais"
																class="form-control selectpicker disabledForm"
																data-live-search="true">
																<c:forEach var="pais" items="${listaPaises}">
																	<option value="${pais.ldvpIdpais}">
																		${pais.ldvpDescripcion}</option>
																</c:forEach>
															</select>
														</div>

														<div class="col-3">
															<label class="">Estado Civil </label> <select
																id="selectEstadoCivil" name="ldvecIdestadocivil"
																class="form-control selectpicker disabledForm" data-live-search="true">
																<c:forEach var="estadoCivil"
																	items="${listaEstadosCiviles}">
																	<option value="${estadoCivil.ldvecIdestadocivil}">
																		${estadoCivil.ldvecDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div class="col-4">
															<label>Nombre Social </label> <input id="txtNombreSocial"
																name="dpNombresocial" type="text"
																class="form-control disabledForm " placeholder="" />
														</div>
														<div class="col-2 ">
															<label>Código postal </label> <input id="txtCodigoPostal"
																name="dpDireccodpostal" type="text" maxlength="10"
																class="form-control disabledForm " placeholder="" />
														</div>
													</div>
													<div class="row" id="filaObservaciones">
														<div class="col">
															<label>Observaci&oacute;n </label> <input
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
												<input type="hidden" id="txtdppIdpacientepatologia" value=""/>
												<div class="col">
													<div class="row" id="filaPAtologias">
														<div class="col-4">
															<label class="">Patolog&iacute;a </label> <select
																id="selectPatologias" class="form-control selectpicker " 	data-live-search="true">
																<c:forEach var="patologia" items="${listaPatologias}">
																	<option value="${patologia.ldvpatIdpatologia}">
																		${patologia.ldvpatDescripcion}</option>
																</c:forEach>
															</select>
															<div class="col-md-12 pl-0 pt-2">

																<button id="btnPatologia" type="button"
																	class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ">Agregar
																	Patolog&iacute;a</button>
															</div>
														</div>
														<div class="col-8 ">
															<table id='tablaPatologia'>
																<thead>
																	<tr>
																		<th>Patolog&iacute;a</th>
																		<th>Observaci&oacute;n</th>
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
															<label id="lblRunMadre">RUN Madre</label> <input
																id="txtRutMadre" name="rutMadre" type="text"
																class="form-control " placeholder="" minlength="11"
																maxlength="12"
																onkeyup="this.value = this.value.toUpperCase();" />
														</div>
														<div class="checkbox-inline col-3 ">
															<label class="checkbox checkbox-primary "> <input
																id="checkboxPartoMultiple" name="esPartoMultiple" value="N"
																type="checkbox" class="" /> Parto Múltiple <span></span>
															</label>
														</div>
														<div class="col-3">
															<label>N° Parto M&uacute;ltiple </label> <input
																id="txtNroPartoMultiple" name="dpRnnumero" type="number" max="9"
																min="1" class="form-control " placeholder="" />
														</div>

													</div>
												</div>
											</div>
											<!--  Fin panel datos madre -->
										</div>
									</div>
									<!-- Fin Formulario registro -->



									<div class="col-12">

										<button id="btnAgregarPaciente" name="insert" type="submit"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ">
											<i class="far fa-save"></i>Guardar
										</button>
										<button id="btnUpdatePaciente" name="update" type="button"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ocultar"
											value="">Confirmar Datos</button>
										<a id="btnConfirmarDatosPac"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 float-right ocultar"><i
											class="la la-search"></i>Confirmar</a>
										<button id="btnCancelEdit" name="cancel" type="button"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
											value="">Cancelar</button>
									</div>
								</div>
							</form>

						</div>
					</div>
				</div>
				<!-- Fin Panel inferior  -->

			</div>
		</div>
		<!-- Fin Panel inferior  -->
		<!-- FIN FORMULARIO REGISTRO PACIENTE -->
	</div>
	<!-- Fin accordion  -->
	<!-- Fin container -->
	   <!-- **************************** Modal Test Eventos******************************************                    -->
    <div
      class="modal"
      id="eventosPacienteModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="eventosPacienteModal"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 id="" class="text-center" id="eventosTestLabel">
              Eventos Paciente
            </h3>
            <jsp:include page="modal/ModalEventsPaciente.jsp" />
            <div
              class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
            >
              <button
                type="button"
                class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                data-dismiss="modal"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- **************************** Fin Modal Test Eventos******************************************                    -->
	
	</div>
	<jsp:include page="modal/ModalPacienteNuevo.jsp" />
	<jsp:include page="common/Scripts.jsp" />
	<script src="https://unpkg.com/imask"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/jquery.mask.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
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
	<script
		src="<%=request.getContextPath()%>/resources/js/FuncionesGenerales.js"></script>
</body>

</html>