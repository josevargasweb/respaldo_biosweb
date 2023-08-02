<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BiosLIS | Impresión de informes</title>
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

	<div class="container container-main-content container-impresion-etiquetas">
		 <!-- VARIABLES DE PERMISOS DE INFORME RESULTADOS -->
		 <input type="hidden" id="enviaResultadosEmail" name="enviaResultadosEmail" value="${perfilUsuario.cpuEnviaresultadosemail}" />
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0" id="impresion-etiquetas-main">
				<div id="panelSuperior" class="card">
					<div class="card-header border-15" id="impresion-etiquetas-title">
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
					aria-labelledby="impresion-etiquetas-title"
					data-parent="#impresion-etiquetas-main">
						<div class="card-body">
							<div class="col-12">
								<input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
								<jsp:include page="desarrollo/Bios_OrdenesBuscador.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
				<div id="panelInferior" class="card">
					<div class="card-header " id="headingRegistroPaciente">
						<div class="card-title d-flex justify-content-between">
							<h3 class="card-label col-3 pl-0">Informe de Resultados</h3>
						</div>
					</div>
					<div id="panelResultadoOrden ">
						<div class="card-body no-gutters">
							<div class="col-12">
								<div class="row">
									<div class="col-8">
										<div class="card card-2 panelExamanes h-100" id="divTablaMuestras">
											<div class="card-body h-100">
												<h3 class="col-12 pl-0 pr-0" data-toggle="collapse"
												data-target="#collapseInformeResultado">Informe de Resultados</h3>
												<div class="col-12 pl-0 pr-0" >
													<div class="card card-2 mt-3">
														<div class="card-body p-0">
															<table
															class="table table-hover table-striped nowrap compact-xs" width="100%"
																id="kt_datatable1">
																<thead>
																	<tr>
																		<th>Sel</th>
																		<th>N&deg; orden</th>
																		<th class="info-cod">C&oacute;digo</th>
																		<th class="info-examen">Examen</th>
																		<th>Estado</th>
																		<th>Fecha impresi&oacute;n</th>
																		<th>Usuario imprime</th>
																		<th>Copias</th>
																		<th>Fecha <br/> e-mail</th>
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
										</div>
									</div>
			
									<div class="col-4">
										<!-- ****************************************** Seccion Datos ************************************************************* -->
										<div class="row">
											<div class="col">
												<div class="card card-2 panelExamanes h-100" id="">
													<div class="card-body h-100" id="headingResultadoExamen">
														<h3 class="col-12 pl-0 pr-0">Datos</h3>
														<div class="row">
														  <div class="col-12 pr-0" id="panelPacienteOrden">
															<div class="card-title d-flex justify-content-between">
																<b>Paciente</b>
															</div>
															<div class="row">
															  <div class="col-12 mb-2 form-row line-height-1">
																<div class="col-lg-4 col-xl-4 form-group mb-0">
																  <label class="col-form-label pb-0 col-12 pl-0"
																	>Nombre</label
																  >
																  <span id="idDatosCardNombre"></span>
																</div>
																<div class="col-lg-8 col-xl-3 form-group mb-0">
																  <label class="col-form-label pb-0 col-12 pl-0"
																	>Sexo</label
																  >
																  <span id="idDatosCardSexo"></span>
																</div>
																<div class="col-lg-4 col-xl-5 form-group mb-0">
																  <label
																	class="col-form-label pb-0 col-12 pl-0 pr-0"
																	>Edad</label
																  >
																  <span id="idDatosCardEdad"></span>
																</div>
																<div
																  class="col-lg-8 col-xl-4 form-group mt-1 mb-0"
																>
																  <label class="col-form-label pb-0 col-12 pl-0"
																	>Tipo de Atenci&oacute;n</label
																  >
																  <span id="idDatosCardTipoAtencion"></span>
																</div>
																<div
																  class="col-lg-4 col-xl-6 form-group mt-1 mb-0"
																>
																  <label class="col-form-label pb-0 col-12 pl-0"
																	>e-mail</label
																  >
																  <span id="idDatosCardEmail2"></span>
																</div>
															  </div>
															</div>
														  </div>
															<div class="col-12">
																<hr />
															</div>
														  <div class="col-12">
															<div class="card-label d-flex justify-content-between">
																<b>Orden</b>
															</div>
															<div class="row">
															  <div class="col-12 mb-2 form-row line-height-1">
																<div class="col-lg-12 form-group mb-0 mt-2 d-flex align-items-end">
																  <!-- ---------- ini botonera -> --------------------------------------------------------------->
																  <div class="row">
																	<div class="col-xl-6 col-md-5">
																		<div class="row">
																			<div class="col-12 mb-2 form-row line-height-1">
																			  <div class="col-lg-12 form-group mb-0">
																				<label class="col-form-label pb-0 col-12 pl-0"
																				  >N&deg; orden</label
																				>
																				<span id="txtNroOrden"></span>
																			  </div>
																			  <div class="col-lg-12 form-group mb-0">
																				<label class="col-form-label pb-0 col-12 pl-0"
																				  >Fecha</label
																				>
																				<span id="idDatosCardFecha"></span>
																			  </div>
																			</div>
																		  </div>
																	</div>
																	<div class="col-xl-6 col-md-5">
																		<div class="col-12">
																			<div class="row">
																				<div class="col-lg-2 col-xl-3">
																				  <button id="btnFirstOrder" class="btn btn-light-primary font-weight-bold btn-capt">
																					<i class="fas fa-fast-backward" aria-hidden="true"></i>
																				  </button>
																				</div>
																				<div class="col-lg-2 col-xl-3">
																				  <button id="btnPrevOrder" class="btn btn-light-primary font-weight-bold btn-capt">
																					<i class="fas fa-step-backward" aria-hidden="true"></i>
																				  </button>
																				</div>
																				<div class="col-lg-2 col-xl-3">
																				  <button id="btnNextOrder" class="btn btn-light-primary font-weight-bold btn-capt">
																					<i class="fas fa-step-forward" aria-hidden="true"></i>
																				  </button>
																				</div>
																				<div class="col-lg-2 col-xl-3">
																				  <button id="btnLastOrder" class="btn btn-light-primary font-weight-bold btn-capt">
																					<i class="fas fa-fast-forward" aria-hidden="true"></i>
																				  </button>
																				</div>
																				<div class="col-xl-12 mt-2 pr-0">
																				  <div class="progress">
																					<div id="order-progress" class="progress-bar bg-success" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="0"></div>
																				  </div>
																				  <div class="col-12">
																					<label class="col-form-label pb-0 col-12 pl-0 text-center"><span id="count_orden_inicio">0</span>
																					  de
																					  <span id="count_orden_fin">0</span></label>
																				  </div>
																				</div>
																			  </div>
																		</div>
																		<!-- <label>BOTONERA</label>
					
																		<button id="btnNext" type="button" />
					
																		<button id="btnPrev" type="button" /> -->
																		
					
																	</div>
																  </div>
																  <!-- ---------- <- Fin botonera ---------------------------------------------------------------->
																</div>
															  </div>
															</div>
														  </div>
														</div>
													  </div>
												</div>
											
											</div>
										</div>
			
										<!-- ****************************************** Seccion Acciones ************************************************************* -->
			
										<div class="row mt-4">
											<div class="col">
												<div class="card card-2 panelExamanes h-100" id="">
													<div class="card-body h-100">
														<h3 class="col-12 pl-0 pr-0">Acciones</h3>
														<div class="col-12 pl-0 pr-0 container-exam-micro" >
															<div class="row">
																<div class="col-6">
																	<div class="row">
																		<div class="col">
																			<button id="btnGlosas" type="button"
																				class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 mb-4 disabledForm"
																				data-toggle="modal">
																				<span id="pdfId" class="svg-icon svg-icon-lg"> <!--
																				<img src="<%=request.getContextPath()%>/resources/template/assets/media/svg/files/pdf.svg" />
																						
																						-->
																					<i class="far fa-file-pdf" aria-hidden="true" style="font-size:1.5rem;"></i>
																				</span> Guardar PDF
																			</button>
																		</div>
																	</div>
																	<div class="row">
																		<div class="col">
																			<button id="btnDatosPaciente" type="button"
																				class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 mb-4 disabledForm">
																				<i class="fas fa-user-md" aria-hidden="true"></i>
																				Datos de paciente 
																			</button>
																		</div>
																	</div>
																</div>
																<div class="col-6">
																	<div class="row">
																		<div class="col">
																			<button id="btnSendMail" type="button"
																				class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 mb-4 disabledForm">
																				<span id="mailIdIcon" class="svg-icon svg-icon-lg">
																					<i class="fas fa-paper-plane" aria-hidden="true"></i>
																				</span> Enviar mail
																			</button>
																		</div>
																	</div>
																	<div class="row">
																		<div class="col">
																			<button id="btnRegistrarRecepcion" type="button"
																				class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 mb-4 disabledForm">
																				<img
																				src="<%=request.getContextPath()%>/resources/img/add_files.png"
																				width="20"
																				style="filter: brightness(0) invert(1)"
																			  />
																				Registrar recepci&oacute;n
																			</button>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row justify-content-center">
																<div class="col-6">
																	<button id="btnVistaPrevia" type="button"
																		class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 disabledForm">
																		Vista previa <i
																		class="la la-print" style="font-size: 5rem;"></i>
																	</button>
																</div>
																<div class="col-6">
																	<button id="btnImprimir" type="button"
																		class="btn btn-capt btn-blue-primary btn-normal font-weight-bold mr-2 w-100 disabledForm">
																		Imprimir <i
																		class="la la-print" style="font-size: 5rem;"></i>
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
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- ********************************************** Mail ******************************************************************************** -->



	<div class="modal fade" id="enviarMailModal" tabindex="-1"
		role="dialog" aria-labelledby="enviarMailModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h5 class="modal-title" id="exampleModalLabel">Env&iacute;o de
						Informe de Resultados por e-mail</h5>
						<div class="form-row align-items-center mt-5">
							<div class="col-3 my-1">
							  <div class="custom-control custom-checkbox mr-sm-2">
								<input class="form-check-input position-static" type="checkbox"
								id="mailPacienteCheckbox" value="option1" aria-label="...">
								<label class="" for="mailPacienteCheckbox">Paciente</label>
							  </div>
							</div>
							<div class="col-8 my-1">
								<input class="form-control" id="idModalPacienteNombre"
								type="text" readonly="readonly" />
							</div>
						</div>
						<div class="form-row align-items-center mt-5">
							<div class="col-3 my-1">
								<label class="pl-4">E-mail</label>
							</div>
							<div class="col-8 my-1">
								<input class="form-control" id="idModalPacienteEmail"
										type="text" disabled="disabled" 
										/>
							</div>
						</div>
						<hr>
						<div class="form-row align-items-center mt-5">
							<div class="col-3 my-1">
							  <div class="custom-control custom-checkbox mr-sm-2">
								<input class="form-check-input position-static" type="checkbox"
										id="mailServicioCheckbox" value="option1" aria-label="...">
								<label class="" for="mailServicioCheckbox">Servicio</label>
							  </div>
							</div>
							<div class="col-8 my-1">
								<select id="idModalSvcMailList"
									class="form-control selectpicker">
									<c:forEach var="servicio" items="${listaServiciosConMail}">
										<option value="${servicio.csIdservicio}">${servicio.csDescripcion}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-row align-items-center mt-5">
							<div class="col-3 my-1">
							  <div class="custom-control custom-checkbox mr-sm-2">
								<label class="" for="">E-mail</label>
							  </div>
							</div>
							<div class="col-8 my-1">
								<input class="form-control" id="idModalSvcDescEmail"
											type="text" disabled="disabled" />
							</div>
						</div>
					<div class="form-group mt-5 mx-auto">
					
						<div class='row'>
							<div class="col-6">
								<div class="row">
									<div class="col-3 d-flex justify-content-center align-items-center">
										<img
										src="<%=request.getContextPath()%>/resources/img/shield-lock.png"	width="50"/>
									</div>
									<div class="col-9">
										<div class="custom-control custom-checkbox mr-sm-2">
											<input class="form-check-input position-static" type="checkbox"
											id="blankCheckbox" value="option1" aria-label="...">
											<label class="form-check-label" for="defaultCheck1">
												<b>Proteger PDF</b>
											</label>
										  </div>
										<label class="form-check-label">
											La clave ser&aacute; el RUT del paciente
										</label>
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-group row">
									<button id="mailId" type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  disabledForm">
										<span id="mailIdIcon" class="svg-icon svg-icon-lg">
											<i class="fas fa-paper-plane" aria-hidden="true"></i>
										</span> Enviar por mail
									</button>
								</div>
							</div>
							<div class="col-3">
								<div class="form-group row">
									<button id="recepcionResultados" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ********************************************** Guardar PDF ******************************************************************************** -->



	<div class="modal fade" id="guardarPdfModal" tabindex="-1"
		role="dialog" aria-labelledby="guardarPdfModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h5 class="modal-title" id="exampleModalLabelPdf">Guardar Informe de Resultados</h5>

						<div class="form-row align-items-center mt-5">
							<div class="col-2 my-1">
							  <div class="custom-control custom-checkbox mr-sm-2">
								<i class="far fa-folder-open" style="font-size: 4rem;color:#000"></i>
							  </div>
							</div>
							<div class="col-8 my-1">
								<label class="h4 mb-0">Cambiar ubicaci&oacute;n</label>
							</div>
						</div>
					
						<div class="form-row align-items-center mt-3">
							<div class="col-3 my-1">
							  <div class="custom-control custom-checkbox mr-sm-2">
								<label class="">Ubicación de Archivo</label>
							  </div>
							</div>
							<div class="col-8 my-1">
								<input class="form-control" id="ubicacionPdf"
								type="text" readonly="readonly" />
							</div>
						</div>
						
											

					<div class="form-group mt-5 mx-auto">
					
						<div class='row'>
							<div class="col-6">
								<div class="row">
									<div class="col-3 d-flex justify-content-center align-items-center">
										<img
										src="<%=request.getContextPath()%>/resources/img/shield-lock.png"	width="50"/>
									</div>
									<div class="col-9">
										<div class="custom-control custom-checkbox mr-sm-2">
											<input class="form-check-input position-static" type="checkbox"
											id="blankCheckboxPdf" value="option1" aria-label="...">
											<label class="form-check-label" for="defaultCheck1pdf">
												<b>Proteger PDF</b>
											</label>
										  </div>
										<label class="form-check-label">
											La clave ser&aacute; el RUT del paciente
										</label>
									</div>
								</div>
							</div>
							<div class="col-3">
								<div class="form-group row">
									<button id="mailIdPdf" type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  disabledForm">
										<span id="mailIdPdfIcon" class="svg-icon svg-icon-lg">
											<i class="far fa-file-pdf" aria-hidden="true" style="font-size:1.5rem;"></i>
										</span> Guardar PDF
									</button>
								</div>
							</div>
							<div class="col-3">
								<div class="form-group row">
									<button id="btnCerrarPdf" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ********************************** Registro de recepcion *********************************************************************************** -->

	<div class="modal fade" id="registroRecepcionModal" tabindex="-1"
		role="dialog" aria-labelledby="registroRecepcionModal"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg"
			role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h5 class="modal-title" id="exampleModalLabel">Registrar
						Recepci&oacute;n de Informe de Resultados</h5>
					<div class="form-group mt-5 mx-auto">
							<div class="col-12">
								<div class="form-group row">
									<div class="col-form-label col-2">
										<label>Nombre</label>
									</div>
									<div class="col-10 ">
										<input class="form-control" id="registroRecepcionModalNombre"
											type="text" />
									</div>
								</div>
								<div class="form-group row">
									<div class="col-form-label col-2">
										<label>RUN</label>
									</div>
									<div class="col-10 ">
										<input class="form-control" id="registroRecepcionModalRut" minlength="11" maxlength="12" autocomplete="off" onkeyup="this.value = this.value.toUpperCase();" 
											type="text" />
									</div>
								</div>
							</div>
							<div class="col-12">
								<table 	class="table table-striped  display nowrap " width="100%">
									<thead class="">
										<tr>
											<th scope="col">#</th>
											<th scope="col">C&oacute;digo</th>
											<th scope="col">Examen</th>
											<th scope="col">Estado</th>
											<th scope="col">Fecha de retiro</th>
										</tr>
									</thead>
									<tbody id="tbodyRegistrarRecepcion">
									</tbody>
								</table>
							</div>
							<div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between ">
								<div class="container-recepcion-registro">
									<button id="recepcionRegistro" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 ">Guardar</button>
								</div>
								<button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************* Datos del paciente ************************************************************************************-->

	<div class="modal fade" id="datosPacienteModal" tabindex="-1"
		role="dialog" aria-labelledby="datosPacienteModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl"
			role="document">
			<div class="modal-content">
				<div class="modal-body">		
					<!-- nuevo  -->
					<div class="tab-pane fade show solo-texto active" id="OrderCanvasData" role="tabpanel" aria-labelledby="OrderCanvasData">
						<div class="row">
							<div class="col">
						  
								<div class="col-12">
									<div class="row">
										<h5 class="title-paciente"> Datos del Paciente</h5>
										<div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
										 <div class="col-6 form-group mb-0">
												<label
													class="col-form-label pb-0 col-12 pl-0">Nombre</label>
													<span id="datosPacienteModalNombre"></span>
											</div>
											<div class="col-3 form-group  mb-0">
												<label
													class="col-form-label pb-0 col-12 pl-0">Sexo</label>
													<span id="datosPacienteModalSexo"></span>
											</div>
											<div class="col-3 form-group mb-0">
												<label
													class="col-form-label pb-0 col-12 pl-0">Edad</label>
													<span id="datosPacienteModalEdad"></span>
											</div>
											<div class="col-6 form-group mt-1 mb-0">
												<label class="col-form-label pb-0 col-12 pl-0">Fecha de Nacimiento</label>
													<span id="datosPacienteModalFNacimiento"></span>
											</div>
											<div class="col-6 form-group mt-1 mb-0">
												<label
													class=" col-form-label pb-0 col-12 pl-0">e-mail</label>
													<span id="datosPacienteModalEmail"></span>
											</div>
											<div class="col-6 form-group mt-1 mb-0">
												<label class="col-form-label pb-0 col-12 pl-0">Tipo de Atenci&oacute;n</label>
													<span id="datosPacienteModalTipoAtencion"></span>
											</div>
											 <div class="col-3 form-group mt-1 mb-0">
												<label
													class=" col-form-label pb-0 col-12 pl-0">Servicio</label>
													<span id="datosPacienteModalServicio"></span>
											</div>
											 <div class="col-3 form-group mt-1 mb-0">
												<label
													class=" col-form-label pb-0 col-12 pl-0">Procedencia</label>
													<span id="datosPacienteModalLocalizacion"></span>
											</div>
											<div class="col-6 form-group mt-1 mb-0">
												<label class="col-form-label pb-0 col-12 pl-0">Observaci&oacute;n</label>
													<span id="datosPacienteModalObservacion"></span>
											</div>
											<div class="col-6 form-group mt-1 mb-0">
												<label class="col-form-label pb-0 col-12 pl-0">Patolog&iacute;as</label>
													<span id="datosPacienteModalPatologia"></span>
											</div>
										</div>
										<input class="form-control" placeholder="" id="idOrderCanvasDataLocalization" type="hidden"/>
									</div>
								</div>
							
								<h5>Datos de la Orden</h5>
								<div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
									<div class="col-6 form-group mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">N° Orden</label>
											<span id="datosPacienteOrdenModalNro"></span>
									</div>
									<div class="col-3 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Fecha</label>
											<span id="datosPacienteOrdenModalFecha"></span>
									</div>
									<div class="col-3 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Convenio</label>
											<span id="datosPacienteOrdenModalConvenio"></span>
									</div>
									<div class="col-6 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">M&eacute;dico</label>
											<span id="datosPacienteOrdenModalMedico"></span>
									</div>
									<div class="col-6 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Diagn&oacute;stico</label>
											<span id="datosPacienteOrdenModalDiagnostico"></span>
									</div>
									<div class="col-12 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Observaci&oacute;n</label>
											<span id="datosPacienteOrdenModalObservacion"></span>
									</div>
								</div>
								<h6 class="divOrdenesPrevias">Órdenes previas</h6>
								<div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
									<div class="col-6 form-group mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Toma de muestras</label>
											<span id="idModalSvcDesc"></span>
									</div>
									<div class="col-6 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Nota interna</label>
											<span id="idModalSvcEmail"></span>
									</div>
									<div class="col-6 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Recepci&oacute;n de Muestra</label>
											<span id="idModalSvcDesc"></span>
									</div>
									<div class="col-6 form-group  mb-0">
										<label
											class="col-form-label pb-0 col-12 pl-0">Nota de Examen</label>
											<span id="idModalSvcEmail"></span>
									</div>
								</div>
							</div>
							<div class="col-12">
								<div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
									<button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" data-dismiss="modal">
									  Cerrar
									</button>
								  </div>
							</div>
						</div>
					</div>
					
					<!-- nuevo  -->
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="common/Scripts.jsp" />
	<script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
  <script
    src="<%=request.getContextPath()%>/resources/js/Dto/FiltroOrden.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/queue.js"></script>
		<script
        src="<%=request.getContextPath()%>/resources/datetimepicker/moment.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
        <script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>
		<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>
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
