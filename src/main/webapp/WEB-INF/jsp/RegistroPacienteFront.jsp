<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="font-size: 13px">
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
	<div class="container mt-1">
		<div id='idAccordion' class="accordion">
			<div id="panelSuperior" class="card mt-1">
				<div class="card-header pl-2 pb-1 pt-1" id="headingOne8">
					<div
						class="card-title row col-12 d-flex justify-content-between p-0"
						data-toggle="collapse" data-target="#panelBusquedaPaciente">
						<h3 class="card-label col-3 p-0">Búsqueda de Paciente</h3>
						<div class="col-4 justify-content-right ml-4 pl-4 pr-0">
							<div class="ml-1 float-right">
								<a id="btnLimpiarFiltro" class="navi-link"> <span
									class="symbol symbol-30 symbol-circle mr-3 "> <span
										id="circuloLimpiarFiltro"
										class="symbol-label bg-hover-primary  hoverIcon" href="#"
										data-toggle="tooltip" title="Limpiar"><i
											id="iLimpiarFiltro" class="la la-broom icon-xl text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
							</div>
							<div class="ml-1 float-right">
								<a id="nuevoPaciente" class="navi-link"> <span
									class='symbol symbol-30 symbol-circle mr-3'> <span
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
				<div id="panelBusquedaPaciente" class="collapse show"
					data-parent="#idAccordion">
					<div class="card-body  row  no-gutters">
						<!-- FORMULARIO FILTRO -->
						<div class="col">
							<div id='divSelTipoDocumento' class="form-group row mt-1 mb-1">
								<label class="col-form-label text-left col-4 ">T de
									documento</label>
								<div class="col-8 pl-0">
									<select id="selectTipoDocumentoFiltro"
										class="form-control selectpicker">
										<option value="6">TODOS</option>
										<c:forEach var="documento" items="${listaTiposDocumentos}">
											<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div id='divTxtRutFiltro' class="form-group row mt-1 mb-1">
								<label id="lblRutFiltro" class="col-4 col-form-label">RUN
								</label> <label id="lblRutFiltroMadre"
									class="col-4 col-form-label ocultar">RUN Madre </label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroRut"
										type="text" minlength="11" maxlength="12"
										onkeyup="this.value = this.value.toUpperCase();" />
								</div>
							</div>
							<div id='divTxtPasaporteFiltro'
								class="ocultar mt-1 mb-1 form-group row">
								<label class="col-4 col-form-label">Pasaporte </label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtPasaporteFiltro" type="text" />
								</div>
							</div>
							<div id='divTxtNNFiltro' class="form-group row ocultar mt-1 mb-1">
								<label class="col-4 col-form-label">Fecha Ingreso </label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroNN"
										type="text" data-provide="datepicker" data-date-language="es" />
								</div>
							</div>
							<div class="form-group row mt-1 mb-1">
								<label class="col-4 col-form-label">Nombre</label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroNombre"
										type="text" />
								</div>
							</div>
							<div class="form-group row mt-1 mb-1">
								<label class="col-4 col-form-label">Primer Apellido</label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtFiltroApellido" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label class="col-4 col-form-label">Segundo Apellido</label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtFiltroSegundoApellido" type="text" />
								</div>
							</div>

							<a id="btnBuscarFiltro" href="#"
								class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i
								class="la la-search"></i>Buscar</a>
						</div>
						<!-- FIN FORMULARIO FILTRO -->
						<!-- TABLA FILTRO -->
						<div class="col scroll scroll-pull" data-scroll="true"
							data-suppress-scroll-x="false" data-swipe-easing="false"
							style="height: 270px;">
							<table id="tableFiltro" class="table table-hover">
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
							<label id="lblErrorFiltro" class="textError text-red ocultar">No
								se encontraron pacientes con los datos provistos.</label>
						</div>
						<!-- FIN TABLA FILTRO -->
					</div>
				</div>
			</div>
			<!-- ********************************************************* FORMULARIO REGISTRO PACIENTE ***********************************************-->

			<div id="panelInferior" class=" card mt-1">
				<div class="card-header  p-0" id=headingRegistroPaciente"">
					<div
						class="card-title row col-12 d-flex justify-content-between p-0">
						<h3 class="card-label col-3 p-0">Registro de Paciente</h3>

						<!-- ************************************** Botonera Registro Paciente **************************-->
						<div class="col-4 justify-content-right ml-4 p-0">
							<div class="ml-1 float-right">
								<a id="btnLimpiar" class="navi-link pointer"> <span
									class="symbol symbol-50 symbol-circle mr-3 "> <span
										id="circuloLimpiar"
										class="symbol-label bg-hover-primary hoverIcon"
										data-toggle="tooltip" title="Limpiar"><i id="iLimpiar"
											class="la la-broom icon-xl  text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
							</div>
							<div class="ml-1 float-right">
								<a id="btnEditar" class="navi-link pointer"> <span
									class='symbol symbol-50 symbol-circle mr-3'> <span
										id="circuloEditarPaciente" class='symbol-label circuloIcon'
										data-toggle="tooltip" title="Editar paciente"><i
											id="iEditPaciente" class="far fa-edit text-dark-50"></i></span>
								</span>
								</a>
							</div>
							<div class="ml-1 float-right">
								<a id="buscarPaciente" class="navi-link" href="#"> <span
									class='symbol symbol-50 symbol-circle mr-3'> <span
										id="circuloBuscarPaciente"
										class='symbol-label bg-hover-primary hoverIcon'
										data-toggle="tooltip" title="Buscar paciente"><i
											id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
								</span>
								</a>
							</div>
						</div>
						<!-- ************************************** Botonera Registro Paciente **************************-->


					</div>
				</div>

				<!--  ********************************************************** Registro de paciente *****************************************************************  -->
				<div class="card-body">
					<div id="panelRegistroPaciente">

						<form id="formRegistroPaciente" method="post">
							<input id="txtIdPaciente" name="dpIdpaciente" type="number"
								value="0" class="ocultar" /> <input type="text" id="ipEquipo"
								name="ipEquipo" class="ocultar" />
							<div id="divForm" class="row">
								<div class="col-10">
									<div class="tab-content mt-1" id="myTabContent">
										<!-- --------------------------------Datos básicos --------------------------------           -->
										<div class="tab-pane fade show active" id="kt_tab_pane_1"
											role="tabpanel" aria-labelledby="kt_tab_pane_1">
											<div class="row">
												<div class="form-group align-items-center col">
													<div class="row">
														<!--  FIla 1 -->
														<div id="divTipoDocumento" class="form-group col-2 ">
															<label for="selectTipoDocumento" class="">T de
																documento :</label> <select id="selectTipoDocumento"
																class="form-control selectpicker mt-1"
																name="ldvTiposdocumentos">
																<c:forEach var="documento"
																	items="${listaTiposDocumentos}">
																	<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div id="divTxtRut" class="form-group col-2">
															<label>RUN: *</label> <input id="txtRut" name="dpRun"
																type="text" class="form-control mt-1" placeholder=""
																minlength="11" maxlength="12" autocomplete="off"
																onkeyup="this.value = this.value.toUpperCase();" />
															<div class="invalid-feedback">RUN inv&aacute;lido</div>
														</div>
														<div id="divTxtPasaporte" class="form-group col-2 ocultar">
															<label>Pasaporte: *</label> <input id="txtPasaporte"
																name="dpNrodocumento" type="text"
																class="form-control mt-1" autocomplete="off"
																placeholder="" />
														</div>
														<div id="divTxtNombre" class="form-group col-2 ">
															<label>Nombres: *<span class="label"
																data-toggle="popover" data-html="true"
																data-content="Nombre legal, debe ser idéntico al carné de identidad. Para variaciones usar campo nombre social."><i
																	class="fa la-info-circle"></i></span></label> <input id="txtNombre"
																name="dpNombres" type="text"
																class="form-control mt-1 disabledForm"
																autocomplete="off" placeholder="" />
															<div class="d-md-none mb-1"></div>
														</div>
														<div id="divPrimerApellido" class="form-group col-2 mt-1">
															<label>Primer Apellido: *</label> <input
																id="txtPrimerApellido" name="dpPrimerapellido"
																type="text" class="form-control mt-1 disabledForm"
																autocomplete="off" placeholder="" />
															<div class="d-md-none mb-1"></div>
														</div>
														<div id="divSegundoApellido" class="form-group col-2 mt-1">
															<label>Segundo Apellido: </label> <input
																id="txtSegundoApellido" name="dpSegundoapellido"
																type="text" class="form-control mt-1 disabledForm"
																autocomplete="off" placeholder="" />
															<div class="d-md-none mb-1"></div>
														</div>
													</div>
													<div class="row">
														<div class="form-group col-2 mt-1">
															<label for="selectSexo" class="">Sexo: *<span
																class="label" data-toggle="popover" data-html="true"
																data-content="Sexo biológico o sexo al nacer. Requerido para análisis objetivos de datos bioquímicos u hormonales."><i
																	class="fa la-info-circle"></i></span>
															</label> <select
																class="form-control selectpicker mt-1 disabledForm"
																id="selectSexo" name="ldvSexo">
																<option value="N" disabled selected>Seleccionar</option>
																<c:forEach var="sexo" items="${listaSexo}">
																	<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
																</c:forEach>
															</select>
														</div>
														<div class="form-group col-2 mt-1">
															<label for="txtFechaNacimiento">Fec
																Nacimiento:&nbsp*</label> <input id="txtFechaNacimiento"
																name="dpFnacimiento"
																class="form-control mt-1 disabledForm "
																data-provide="datepicker" data-date-language="es"
																autocomplete="off" placeholder="" />
														</div>
														<div class="form-group col-1 pr-0 mt-1">
															<label for="txtAnio">Edad :</label> <input id="txtAnio"
																name="" type="text" class="form-control mt-1 "
																placeholder="" disabled />

														</div>
														<div class="form-group col-1 pr-0 mt-1" hidden="true"">
															<label for="txtMeses">Meses :</label> <input
																id="txtMeses" name="" type="text"
																class="form-control mt-1 " placeholder="" disabled />
														</div>
														<div class="form-group col-1 pr-0 mt-1 " hidden="true">
															<label for="txtDias">Días :</label> <input id="txtDias"
																name="" type="text" class="form-control mt-1 "
																placeholder="" disabled />
														</div>
														<div class="form-group col-3 align-items-center ">
															<label>Teléfono: </label> <input id="txtTelefono"
																name="dpTelefono" type="text"
																class="form-control phone_cl mt-1"
																placeholder="[9|2]-dddd-dddd" />
														</div>
														<div class="form-group col-2 align-items-center ">
															<label>Email: </label> <input id="txtEmail"
																name="dpEmail" type="text" class="form-control mt-1"
																placeholder="" />
														</div>
													</div>
													<!-- Fin fila 2 -->
													<hr/>
													<p>Info contacto</p>
													<div class="row">
                            <div class="form-group col-3 align-items-center">
                              <label>Nombre: *</label> <input id="txtNombreContacto"
                                name="dcNombre" type="text" class="form-control "
                                placeholder="" />
                            </div>
                            <div class="form-group col-3 ">
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
                            <div class="form-group col-3 ">
                              <label>Tel&eacute;fono: *</label> <input
                                id="txtTelefonoContacto" name="dcTelefono" type="text"
                                class="form-control phone_cl"
                                placeholder="[9|2]-dddd-dddd" />
                            </div>
                            <div class="col-3">
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
										</div>
										<!-- -------------------------------- Fin Datos Basicos ------------------------------------------------------------------------ -->
										<!-- -------------------------------- Datos Extras ------------------------------------------------------------------------ -->
										<div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel"
											aria-labelledby="kt_tab_pane_2">

											<div class="form-group row align-items-center">

												<div class="checkbox-inline col-12 mb-1">
													<label class="checkbox checkbox-primary ml-5"> <input
														id="checkboxVIP" name="dpEsvip" type="checkbox"
														class="disabledForm" /> Paciente VIP <span></span>
													</label> <label class="checkbox checkbox-primary mr-0"> <input
														id="checkboxAfro" name="dpEsafroamericano" type="checkbox"
														class="disabledForm" /> Paciente Afroamericano <span></span>
													</label><span class="label" data-toggle="popover" data-html="true"
														data-content="Dato requerido para el análisis de la función renal mediante la fórmula MDRD."><i
														class="fa la-info-circle"></i></span> <label
														class="checkbox checkbox-primary ml-2"> <input
														id="checkBoxExitus" name="exitus" type="checkbox" disabled /><span></span>
														Fallecimiento
													</label>
												</div>
												<div class="col-md-3 mt-1">
													<label class="">Region: </label> <select id="selectRegion"
														name="ldvrIdregion"
														class="form-control selectpicker disabledForm mt-1"
														data-live-search="true">
														<!-- <option value="" disabled selected>Seleccionar</option> -->
														<c:forEach var="region" items="${listaRegiones}">
															<option value="${region.ldvrIdregion}">${region.ldvrDescripcion}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-3 mt-1">
													<label class="">Comuna : </label> <select id="selectComuna"
														name="ldvcIdcomuna"
														class="form-control selectpicker disabledForm mt-1"
														data-live-search="true"
														data-none-selected-text="Seleccionar">
														<!-- <option value="" disabled selected>Seleccionar</option> -->
													</select>
												</div>
												<div class="col-4 mt-1">
													<label>Dirección: </label> <input id="txtDireccion"
														name="dpDireccion" type="text"
														class="form-control disabledForm mt-1" autocomplete="off"
														placeholder="" />
													<div class="d-md-none mb-1"></div>
												</div>
                    <div class="col-2 ">
                      <label>Código postal: </label> <input id="txtCodigoPostal"
                        name="dpDireccodpostal" type="text"
                        class="form-control disabledForm mt-1" placeholder="" />
                      <div class="d-md-none mb-2"></div>
                    </div>
												
												<div class="col-md-4 mt-1">
													<label class="">Pais Origen: </label> <select
														id="selectPais" name="ldvpIdpais"
														class="form-control selectpicker disabledForm mt-1"
														data-live-search="true">
														<option value="" disabled selected>Seleccionar</option>
														<c:forEach var="pais" items="${listaPaises}">
															<option value="${pais.ldvpIdpais}">${pais.ldvpDescripcion}</option>
														</c:forEach>
													</select>
												</div>

												<div class="col-md-4 mt-1">
													<label class="">Estado Civil: </label> <select
														id="selectEstadoCivil" name="ldvecIdestadocivil"
														class="form-control selectpicker disabledForm mt-1">
														<option value="" disabled selected>Seleccionar</option>
														<c:forEach var="estadoCivil"
															items="${listaEstadosCiviles}">
															<option value="${estadoCivil.ldvecIdestadocivil}">${estadoCivil.ldvecDescripcion}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-4 mt-1">
													<label>Nombre Social: </label> <input id="txtNombreSocial"
														name="dpNombresocial" type="text"
														class="form-control disabledForm mt-1" placeholder="" />
													<div class="d-md-none mb-1"></div>
												</div>
												<div class="col-12 mt-1">
													<label>Observaciones: </label> <input id="txtObservaciones"
														name="dpObservacion" type="text"
														class="form-control disabledForm mt-1" placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
											</div>
										</div>
										<!-- -------------------------------- Fin Datos Extras ------------------------------------------------------------------------ -->
										<!--  **************************************************** Tab comunicacion*************************************************************************************/                                    
 -->
										<!--  **************************************************** Tab comunicacion*************************************************************************************/                                    
 -->
										<div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel"
											aria-labelledby="kt_tab_pane_4">
											<div class="form-group row align-items-center col-8">
												<div class="col-md-12">
													<label class="">Patologia: </label> <select
														id="selectPatologias" class="form-control selectpicker ">
														<option value="N" disabled selected>Seleccionar</option>
														<c:forEach var="patologia" items="${listaPatologias}">
															<option value="${patologia.ldvpatIdpatologia}">${patologia.ldvpatDescripcion}</option>
														</c:forEach>
													</select>
												</div>
												<button id="btnPatologia" type="button"
													class="btn btn-light-primary font-weight-bold mr-2 mt-1 ml-4">Agregar
													Patologia</button>
												<table id='tablaPatologia' class="col-12 mt-1">
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
<!-- -Contacto -->
<!-- -Contacto -->
										<div class="tab-pane fade" id="kt_tab_pane_6" role="tabpanel"
											aria-labelledby="kt_tab_pane_6">
											<div class="form-group row align-items-center">
												<div class="col-6">
													<label id="lblRunMadre">RUN Madre: </label> <input
														id="txtRutMadre" name="rutMadre" type="text"
														class="form-control " placeholder="" minlength="11"
														maxlength="12"
														onkeyup="this.value = this.value.toUpperCase();" />
													<div id="divFeedbackRutMadre" class="invalid-feedback">RUN
														inv&aacute;lido</div>
													<div class="d-md-none mb-2"></div>
												</div>
												<div class="checkbox-inline col-3 mb-5 right-align">
													<label class="checkbox checkbox-primary ml-5"> <input
														id="checkboxPartoMultiple" name="esPartoMultiple"
														type="checkbox" class="" /> Parto Múltiple <span></span>
													</label>
												</div>
												<div class="col-3">
													<label>N° Parto M&uacute;ltiple: </label> <input
														id="txtNroPartoMultiple" name="dpRnnumero" type="text"
														class="form-control " placeholder="" />
													<div class="d-md-none mb-2"></div>
												</div>
											</div>
										</div>
										<!-- Fin tab 6 -->
									</div>
									<!-- Fin myTabContent -->
								</div>
								<!-- Fin col-10 -->

								<!--  Inicio Tabuladores ********************************************************************************************************* -->

								<div class="col-2">
									<ul class="nav  flex-column nav-pills">
										<li class="nav-item" style="max-height: 38.5px;"><a
											id="anchorRegistro" class="nav-link active" data-toggle="tab"
											href="#kt_tab_pane_1"> <span class="nav-icon"><i
													class="flaticon2-paper"></i></span> <span class="nav-text">Info&nbsp;
													Básica</span>
										</a></li>
                    <li id="liDatosMadres" class="nav-item ocultar"><a
                      class="nav-link" data-toggle="tab" href="#kt_tab_pane_6">
                        <span class="nav-icon"><i class="fas fa-female"></i></span>
                        <span class="nav-text">Info&nbsp;Madre</span> <span
                        id="spanDatosMadre"
                        class="ocultar label label-danger font-weight-bold label-inline"><i
                          class="fa la-exclamation-circle text-white"></i></span>
                    </a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#kt_tab_pane_2"> <span
												class="nav-icon"><i class="flaticon2-copy"></i></span> <span
												class="nav-text">Datos Extras</span>
										</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#kt_tab_pane_4"> <span
												class="nav-icon"><i class="fas fa-book-medical"></i></span>
												<span class="nav-text">Patologias</span>
										</a></li>
									</ul>
								</div>

								<!--  Fin Tabuladores ********************************************************************************************************* -->

								<button id="btnAgregarPaciente" name="insert" type="submit"
									class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5">
									<i class="far fa-save"></i>Guardar
								</button>
								<button id="btnUpdatePaciente" name="update" type="button"
									class="btn btn-light-primary font-weight-bold mr-2 ocultar"
									value="">Confirmar</button>
								<a id="btnConfirmarDatosPac"
									class="btn btn-light-primary font-weight-bold mr-2 float-right ocultar"><i
									class="la la-search"></i>Confirmar Datos</a>
								<button id="btnCancelPaciente" name="cancel" type="button"
									class="btn btn-light-primary font-weight-bold mr-2 ocultar"
									value="">Cancelar</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- Fin Panel inferior  -->
		<!-- FIN FORMULARIO REGISTRO PACIENTE -->
	</div>
	<!-- Fin accordion  -->
	</div>
	<!-- Fin container -->
	<jsp:include page="common/Scripts.jsp" />
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/jquery.mask.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/RegistroPacienteValidaciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/RegistroPacienteFiltros.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/RegistroPacienteJs.js"></script>

</body>

</html>
