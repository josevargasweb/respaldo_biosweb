<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registro Orden</title>
<jsp:include page="common/Styles_1.jsp" />
<jsp:include page="common/FileInputStyles.jsp" />
<link
	href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.css"
	rel="stylesheet" type="text/css" />
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
	<div class="container mt-5">
		<div class="row">
			<div class="col">
				<div id="panelSuperior"
					class="accordion accordion-solid accordion-panel accordion-svg-toggle">
					<div class="card card-custom gutter-b">
						<div class="card-header" id="headingInformeResultado">
							<div class="card-title row col-12 d-flex justify-content-between">
								<h3 class="card-label col-3 pl-0 pb-3" data-toggle="collapse"
									data-target="#collapseInformeResultado">B&uacute;squeda de
									Orden:</h3>
								<div class="col-4 justify-content-right">
									<div class="ml-1 float-right">
										<a id="btnLimpiarFiltro" class="navi-link"> <span
											class="symbol symbol-50 symbol-circle mr-3 "> <span
												id="circuloLimpiarFiltro"
												class="symbol-label bg-hover-primary  hoverIcon" href="#"
												data-toggle="tooltip" title="Limpiar"><i
													id="iLimpiarFiltro"
													class="la la-broom icon-xl text-primary"></i></span>
										</span> <span class="navi-text"></span>
										</a>
									</div>
								</div>
							</div>
						</div>
						<div id="collapseInformeResultado" class="collapse show"
							data-parent="#panelSuperior">
							<div id="FORMULARIO_FILTRO" class="card-body row">
								<div class="col-3">
									<div class="form-group row mb-0 ">
										<label id="lblNOrden" class="col-5 col-form-label">N°
											Orden:</label>
										<div class="col-7">
											<input class="form-control" placeholder="" id="txtNOrden"
												type="text" minlength="1" maxlength="10" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-5 col-form-label">Fecha inicio:</label>
										<div class="col-7">
											<input id="txtFInicio" class="form-control "
												data-provide="datepicker" data-date-language="es" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-5 col-form-label">Fecha fin:</label>
										<div class="col-7">
											<input id="txtFFin" class="form-control"
												data-provide="datepicker" data-date-language="es" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-5 col-form-label">Nombre</label>
										<div class="col-7">
											<input class="form-control" placeholder=""
												id="txtFiltroNombre" type="text" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-5 col-form-label">Apellidos</label>
										<div class="col-7">
											<input class="form-control" placeholder=""
												id="txtFiltroApellido" type="text" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-form-label col-5 ">Tipo documento</label>
										<div class="col-7">
											<select id="selectTipoDocumentoFiltro" class="form-control">
												<option value="-1">TODOS</option>
												<c:forEach var="documento" items="${listaTiposDocumentos}">
													<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label id="lblNDocumento" class="col-5 col-form-label">N°
											Documento:</label>
										<div class="col-7">
											<input class="form-control" placeholder="" id="txtNDocumento"
												type="text" minlength="11" maxlength="12"
												onkeyup="this.value = this.value.toUpperCase();" />
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-form-label text-left col-5 ">Tipo
											atenci&oacute;n:</label>
										<div class="col-7">
											<select id="selectTipoAtencionFiltro" class="form-control">
												<option value="-1">TODOS</option>
												<c:forEach var="tipoAtencion" items="${listaTiposAtencion}">
													<option value="${tipoAtencion.ctaIdtipoatencion}">${tipoAtencion.ctaDescripcion}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group row mb-0 ">
										<label class="col-form-label text-left col-5 ">Servicio:</label>
										<div class="col-7">
											<select id="selectServicio" class="form-control">
												<option value="-1">TODOS</option>
												<c:forEach var="servicio" items="${listaServicios}">
													<option value="${servicio.csIdservicio}">${servicio.csDescripcion}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div>
										<a id="btnBuscarFiltro" href="#"
											class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i
											class="la la-search"></i>Buscar</a>
									</div>
								</div>
								<div class="col-9">
									<div class="card card-custom gutter-b">
										<div class="card-body">
											<table
												class="table table-separate table-head-custom table-checkable table-hover"
												style="width: 100%" id="kt_datatable">
												<thead>
													<tr>
														<th>Fecha Orden</th>
														<th>Nro. orden</th>
														<th>Nombre</th>
														<th>Tipo Documento</th>
														<th>Nro. Documento</th>
														<th>Tipo Atención</th>
														<th>Procedencia</th>
														<th>Servicio</th>
														<th>e-mail</th>
														<th>Paciente Id</th>
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
		</div>
		<!-- end Panel Superior -->
		<!-- ****************************************** Panel Inferior *******************************************************************************-->
		<div class="row">
			<div class="col">
				<div id="panelInferior" class="card card-custom gutter-b  mt-5">
					<div class="row">
						<div class="col-8">
							<div class="card card-custom gutter-b">
								<div class="card-header" id="headingResultadoExamen">
									<div class="card-title row d-flex justify-content-between">
										<h3 class="card-label pl-0 pb-3" data-toggle="collapse"
											data-target="#collapseInformeResultado">Informe de
											Resultados :</h3>
									</div>
								</div>

								<div class="card card-custom gutter-b">
									<div class="card-body">
										<table
											class="table table-separate table-head-custom table-checkable"
											id="kt_datatable1">
											<thead>
												<tr>
													<th>Selecci&oacute;n</th>
													<th>#</th>
													<th>C&oacute;digo</th>
													<th>Examen</th>
													<th>Fecha impresi&oacute;n</th>
													<th>Usuario impresi&oacute;n</th>
													<th>Copias</th>
													<th>Fecha e-mail</th>
													<th>Usuario e-mail</th>
													<th>Servicio</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

						<div class="col-4">
							<!-- ****************************************** Seccion Datos ************************************************************* -->
							<div class="row">
								<div class="col">
									<div class="card card-custom gutter-b">
										<div class="card-header" id="headingResultadoExamen">
											<div class="card-title row d-flex justify-content-between">
												<h3 class="card-label pl-0 pb-3">Datos:</h3>
											</div>
										</div>
										<div class="card-body" id="headingResultadoExamen">
											<div class="card-title d-flex justify-content-between">
												<b>Paciente:</b>
											</div>
											<div class="row">
												<div class="col-6">
													<label>Nombre</label>
												</div>
												<div class="col-6">
													<label id="idDatosCardNombre"></label>
												</div>
											</div>
											<div class="row">
												<div class="col-2">
													<label>Sexo</label>
												</div>
												<div class="col-4">
													<label id="idDatosCardSexo"></label>
												</div>
												<div class="col-2">
													<label>Edad</label>
												</div>
												<div class="col-4">
													<label id="idDatosCardEdad"></label>
												</div>
											</div>
											<div class="row">
												<div class="col-6">
													<label>Tipo atenci&oacute;n</label>
												</div>
												<div class="col-6">
													<label id="idDatosCardTipoAtencion"></label>
												</div>
											</div>
											<div class="row">
												<div class="col-6">
													<label>e-mail</label>
												</div>
												<div class="col-6">
													<label id="idDatosCardEmail"></label>
												</div>
											</div>
											<div class="row">
												<div class="col">
													<hr />
												</div>
											</div>
										</div>
										<div class="card-body" id="headingResultadoExamen">
											<div class="card-label d-flex justify-content-between">
												<b>Orden: </b>
											</div>
											<div class="row">
												<div class="col-6">
													<div class="row">
														<div class="col-4">
															<label>#Orden</label>
														</div>
														<div class="col-8">
															<label id="idDatosCardNOrden"></label>
														</div>
													</div>
													<div class="row">
														<div class="col-4">
															<label>Fecha</label>
														</div>
														<div class="col-8">
															<label id="idDatosCardFecha"></label>
														</div>
													</div>
												</div>
												<div class="col-6">
													<label>BOTONERA</label>

													<button id="btnNext" type="button" />

													<button id="btnPrev" type="button" />


												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<!-- ****************************************** Seccion Acciones ************************************************************* -->

							<div class="row">
								<div class="col">
									<div class="card card-custom gutter-b">
										<div class="card-header" id="headingResultadoExamen">
											<div class="card-title row d-flex justify-content-between">
												<h3 class="card-label pl-0 pb-3">Acciones:</h3>
											</div>
										</div>
										<div class="card-body" id="headingResultadoExamen">
											<div class="row">
												<div class="col-6">
													<div class="row">
														<div class="col">
															<button id="btnGlosas" type="button"
																class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm"
																data-toggle="modal">
																<span id="pdfId" class="svg-icon svg-icon-lg"> <!--
                                                                <img src="<%=request.getContextPath()%>/resources/template/assets/media/svg/files/pdf.svg" />
                                                                        
                                                                        -->
																	<i class="far fa-file-pdf icon-2x"></i>
																</span> Guardar PDF
															</button>
														</div>
													</div>
													<div class="row">
														<div class="col">
															<button id="btnDatosPaciente" type="button"
																class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm">
																<!--
                                                                    <img src="<%=request.getContextPath()%>/resources/template/assets/media/svg/files/pdf.svg" />
                                                                    -->
																Datos de paciente <i class="fas fa-diagnoses icon-2x"></i>
															</button>
														</div>
													</div>
												</div>
												<div class="col-6">
													<div class="row">
														<div class="col">
															<button id="btnSendMail" type="button"
																class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm">
																<span id="mailIdIcon" class="svg-icon svg-icon-lg">
																	<img
																	src="<%=request.getContextPath()%>/resources/template/assets/media/svg/icons/Communication/Mail.svg" />
																</span> Enviar por mail
															</button>
														</div>
													</div>
													<div class="row">
														<div class="col">
															<button id="btnRegistrarRecepcion" type="button"
																class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm">
																<img
																	src="<%=request.getContextPath()%>/resources/template/assets/media/svg/icons/Home/Library.svg" />
																Registrar recepci&oacute;n
															</button>
														</div>
													</div>
												</div>
											</div>
											<div class="row justify-content-center">
												<div class="col-6 offset-3 ">
													<button id="btnVistaPrevia" type="button"
														class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm">
														Vista previa <img
															src="<%=request.getContextPath()%>/resources/template/assets/media/svg/icons/Devices/Printer.svg" />
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end Panel Inferior -->

	</div>

	<!-- ********************************************** Mail ******************************************************************************** -->



	<div class="modal fade" id="enviarMailModal" tabindex="-1"
		role="dialog" aria-labelledby="enviarMailModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl"
			role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Env&iacute;o de
						Informe de Resultados por e-mail</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<i aria-hidden="true" class="ki ki-close"></i>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group mt-5 mx-auto">
						<div class='row'>
							<div class="col-2">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										id="mailPacienteCheckbox" value="option1" aria-label="...">
								</div>
							</div>
							<div class="col-10">
								<div class="form-group row">
									<div class="col-form-label col-4">
										<label>Paciente</label>
									</div>
									<div class="col-8 ">
										<input class="form-control" id="idModalPacienteNombre"
											type="text" readonly="readonly" />
									</div>
								</div>
								<div class="form-group row">
									<div class="col-form-label col-4">
										<label>e-mail</label>
									</div>
									<div class="col-8 ">

										<input class="form-control" id="idModalPacienteEmail"
											type="text" disabled="disabled" />

									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class="col-2">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										id="mailServicioCheckbox" value="option1" aria-label="...">
								</div>
							</div>
							<div class="col-10">
								<div class="form-group row">
									<div class="col-form-label col-4">
										<label>Servicio</label>
									</div>
									<div class="col-8 ">
										<select id="idModalSvcMailList"
											class="form-control selectpicker">
											<c:forEach var="servicio" items="${listaServiciosConMail}">
												<option value="${servicio.csIdservicio}">${servicio.csDescripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-form-label col-4">
										<label>e-mail</label>
									</div>
									<div class="col-8 ">
										<input class="form-control" id="idModalSvcDescEmail"
											type="text" disabled="disabled" />
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class="col-4">
								<div class="form-check">
									<input class="form-check-input position-static" type="checkbox"
										id="blankCheckbox" value="option1" aria-label="...">
								</div>
								<label class="form-check-label" for="defaultCheck1">
									Proteger PDF <br>La clave ser&aacute; el RUT del paciente
								</label>
							</div>
							<div class="col-4">
								<div class="form-group row">

									<button id="mailId" type="button"
										class="btn btn-white btn-hover-primary  font-weight-bold mr-2 ml-4 mb-5 disabledForm">

										<!--            <span id="mailId" class="svg-icon svg-icon-lg"> -->
										<img
											src="<%=request.getContextPath()%>/resources/template/assets/media/svg/icons/Communication/Mail.svg" />
										</span> Enviar por mail
									</button>
								</div>
							</div>
							<div class="col-4">
								<div class="form-group row">
									<button type="button"
										class="btn btn-light-primary font-weight-bold"
										data-dismiss="modal">Cerrar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 
<div class="modal-footer">
<button type="button" class="btn btn-light-primary font-weight-bold" data-dismiss="modal">Cerrar</button>
</div>
                    -->
			</div>
		</div>
	</div>

	<!-- ********************************** Registro de recepcion *********************************************************************************** -->

	<div class="modal fade" id="registroRecepcionModal" tabindex="-1"
		role="dialog" aria-labelledby="registroRecepcionModal"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl"
			role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Registrar
						Recepci&oacute;n de Informe de Resultados</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<i aria-hidden="true" class="ki ki-close"></i>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group mt-5 mx-auto">
						<div class='row'>
							<div class="col">
								<div class="form-group row">
									<div class="col-form-label col-2">
										<label>Nombre:</label>
									</div>
									<div class="col-10 ">
										<input class="form-control" id="registroRecepcionModalNombre"
											type="text" />
									</div>
								</div>
								<div class="form-group row">
									<div class="col-form-label col-2">
										<label>RUT:</label>
									</div>
									<div class="col-10 ">
										<input class="form-control" id="registroRecepcionModalRut"
											type="text" />
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class="col">
								<table class="table">
									<thead class="thead-light">
										<tr>
											<th scope="col">#</th>
											<th scope="col">C&oacute;digo</th>
											<th scope="col">Ex&aacute;</th>
											<th scope="col">Estado</th>
											<th scope="col">Fecha de retiro</th>
										</tr>
									</thead>
									<tbody id="tbodyRegistrarRecepcion">
									</tbody>
								</table>
							</div>
						</div>
						<div class='row'>
							<div class="col-6">
								<div class="form-group row">
									<button type="button"
										class="btn btn-light-primary font-weight-bold"
										data-dismiss="modal">Guardar</button>
								</div>
							</div>
							<div class="col-6">
								<div class="form-group row">
									<button type="button"
										class="btn btn-light-primary font-weight-bold"
										data-dismiss="modal">Salir</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 
<div class="modal-footer">
<button type="button" class="btn btn-light-primary font-weight-bold" data-dismiss="modal">Cerrar</button>
</div>
                    -->
			</div>
		</div>
	</div>

	<!-- ******************************* Datos del paciente ************************************************************************************-->

	<div class="modal fade" id="datosPacienteModal" tabindex="-1"
		role="dialog" aria-labelledby="datosPacienteModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl"
			role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title" id="exampleModalLabel">Datos Paciente</h1>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<i aria-hidden="true" class="ki ki-close"></i>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
							<b>Datos de paciente</b>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Nombre</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalNombre"
								type="text" />
						</div>
						<div class="col-form-label col-1">
							<label>Sexo</label>
						</div>
						<div class="col-2 ">
							<input class="form-control" id="datosPacienteModalSexo"
								type="text" />
						</div>
						<div class="col-form-label col-1">
							<label>Edad</label>
						</div>
						<div class="col-2">
							<input class="form-control" id="datosPacienteModalEdad"
								type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Fecha de Nacimiento</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalFNacimiento"
								type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>e-mail</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalEmail"
								type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Tipo de Atenci&oacute;n</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalTipoAtencion"
								type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>Localizaci&oacute;n</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalLocalizacion"
								type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Observaci&oacute;n</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalObservacion"
								type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>Patologi&aacute;s</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteModalPatologia"
								type="text" />
						</div>
					</div>
					<div class='row'>
						<div class="col">
							<hr />
						</div>
					</div>


					<!--Datos de la orden -->

					<div class="row">
						<div class="col">
							<b>Datos de la orden</b>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label># Orden</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteOrdenModalNro"
								type="text" />

						</div>
						<div class="col-form-label col-1">
							<label>Fecha</label>
						</div>
						<div class="col-2 ">
							<input class="form-control" id="datosPacienteOrdenModalFecha"
								type="text" />
						</div>
						<div class="col-form-label col-1">
							<label>Convenio</label>
						</div>
						<div class="col-2">
							<input class="form-control" id="datosPacienteOrdenModalConvenio"
								type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>M&eacute;dico</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="datosPacienteOrdenModalMedico"
								type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>Diagn&oacute;stico</label>
						</div>
						<div class="col-4 ">
							<input class="form-control"
								id="datosPacienteOrdenModalDiagnostico" type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Observaci&oacute;n</label>
						</div>
						<div class="col-4 ">
							<input class="form-control"
								id="datosPacienteOrdenModalObservacion" type="text" />
						</div>
						<div class="col-form-label col-2"></div>
					</div>
					<div class='row'>
						<div class="col">
							<hr />
						</div>
					</div>

					<div class="row">
						<div class="col">
							<b>Observaciones</b>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Toma de muestras</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="idModalSvcDesc" type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>Nota interna</label>
						</div>
						<div class="col-4">
							<input class="form-control" id="idModalSvcEmail" type="text" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-form-label col-2">
							<label>Recepci&oacute;n de Muestra</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="idModalSvcDesc" type="text" />
						</div>
						<div class="col-form-label col-2">
							<label>Nota de Examen</label>
						</div>
						<div class="col-4 ">
							<input class="form-control" id="idModalSvcEmail" type="text" />
						</div>
					</div>
					<div class='row'>
						<div class="col">
							<div class="form-group row">
								<button type="button"
									class="btn btn-light-primary font-weight-bold"
									data-dismiss="modal">Salir</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="common/Scripts.jsp" />
	<script
		src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
  <script
    src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
  <script
    src="<%=request.getContextPath()%>/resources/js/Dto/FiltroOrden.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/queue.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/informeresultados/InformeResultados.utiles.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/informeresultados/InformeResultados.bind.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/informeresultados/InformeResultados.examenes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/informeresultados/InformeResultados.js"></script>
</body>
<!--end::Body-->
</html>