<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registro Orden</title>
<jsp:include page="common/Styles.jsp" />
<link
	href="<%=request.getContextPath()%>/resources/template/assets/css/pages/wizard/wizard-3.css"
	rel="stylesheet" type="text/css" />
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
	<jsp:include page="common/Menu.jsp" />
	<jsp:include page="common/Header.jsp" />
	<jsp:include page="common/AlertaSession.jsp" />
	<div class="container">

		<!--begin: Wizard-->
		<!--begin: Wizard Body-->
		<div class="card pt-0">
			<!--Begin: Wizard Card Body-->
			<div class="card-body pt-1">
				<div class="row justify-content-center ">
					<div class="col">
						<!--  Tabuladores -->
						<!--  Fin Tabuladores -->
						<!-- Contenedor form -->

						<div class="row " id="divform">
							<div class="col ">
								<!--begin: Wizard Form-->
								<form method="post" class="form" id="kt_form">
									<!--begin: Wizard Step 1-->
									<!-- *************************************** Datos del Paciente ***************************************************** -->
									<div class="row">
										<div class="col">
											<!--  Botonera paciente-->
											<!--  fin Botonera paciente -->
											<!-- contenedor paciente-->
											<div class="row" id="contenedorPaciente">
												<div class="col">
													<div class="row"">
														<!-- panel izq paciente-->
														<div class="col-6" id="panelIzqPaciente">
															<div class="row">
																<h4 class="col-3">Paciente</h4>
																<div class="col-9 ">
																	<div class="float-right">
																		<a href="./RegistroPaciente" id="BuscarPac"
																			class="navi-link "> <span
																			class='symbol symbol-30 symbol-circle mr-3'> <span
																				id="circuloBuscarPaciente"
																				class='symbol-label bg-hover-primary  hoverIcon '
																				data-toggle="tooltip" title="Buscar paciente">
																					<i id="iBuscarPaciente"
																					class="fas fa-search text-primary "></i>
																			</span>
																		</span> <span class="navi-text"></span>
																		</a>
																	</div>
																	<div class="float-right">
																		<a href="./RegistroPaciente" id="AgregarPac"
																			class="navi-link "> <span
																			class='symbol symbol-30 symbol-circle mr-3'> <span
																				id="circuloAgregarPaciente"
																				class='symbol-label bg-hover-primary  hoverIcon'
																				data-toggle="tooltip" title="Agregar paciente">
																					<i id="iAgregarPaciente"
																					class='fas fa-plus text-primary'></i>
																			</span>
																		</span> <span class="navi-text"></span>
																		</a>
																	</div>
																</div>
															</div>

															<div class="row" id="filaDoc">
																<div class="col-3">
																	<label>T documento </label> <select
																		class=""
																		id="selectTipoDocumento" name="ldvtdIdtipodocumento">
																		<c:forEach var="documento"
																			items="${listaTiposDocumentos}">
																			<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-3">
																	<label id="tipoDocLabel">RUN</label>
																	<div id="divTxtRutFiltro">
																		<input id="txtRutP" type="text"
																			class="alwaysMayus cleanPaciente"
																			placeholder="" />
																	</div>
																	<div id='divTxtPasaporteFiltro'>
																		<input id="txtPasaporte" type="text"
																			class="alwaysMayus cleanPaciente"
																			placeholder="" />
																	</div>
																</div>
																<div class="col-3 ">
																	<label>Nombres: </label> <input disabled
																		id="txtNombreP" type="text"
																		class="cleanPaciente" placeholder="" />
																</div>
																<div class="col-3 ">
																	<label>Apellidos: </label> <input disabled
																		id="txtPrimerApellidoP" type="text"
																		class="cleanPaciente" placeholder="" />
																</div>
															</div>

															<div class="row" id="filaSexoEdadMail">
																<div class="col-3">
																	<label>Sexo </label> <select disabled
																		id="sexoPacienteOrden"
																		class="form-control cleanPaciente">
																		<option value="N" disabled selected></option>
																		<c:forEach var="sexo" items="${listaSexo}">
																			<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-3 ">
																	<label>Edad: </label> <input disabled id="txtEdad"
																		type="text" class="cleanPaciente"
																		placeholder="" />
																</div>
																<div class="col-3 ">
																	<label>Email </label> <input disabled id="txtEmailP"
																		name="dfEnviarresultadosemail" type="text"
																		class="form-control cleanPaciente" placeholder="" />
																	<div class="d-md-none mb-2"></div>
																</div>
																<div class="col-3">
																	<label class="checkbox checkbox-primary cleanPaciente">
																		<input id="chbxRestutToMail" type="checkbox" /> Envío
																		de resultado al mail? <span></span>
																	</label>
																</div>
															</div>

															<div class="row" id="idUbicacionPaciente1">

																<div class="col-6">
																	<label>Tipo de atenci&oacute;n </label> <select
																		class="form-control selectpicker" id="tipoDeAtencion"
																		name="cfgTipoatencion">
																		<option value="-1">Seleccionar</option>
																		<c:forEach var="listaTipoAtt" items="${listaTipoAt}">
																			<option value="${listaTipoAtt.ctaIdtipoatencion}">${listaTipoAtt.ctaDescripcion}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-6">
																	<label>Procedencia </label> <select
																		id="ProcedenciaComboBoxM"
																		class="form-control selectpicker "
																		name="cfgProcedencias">
																		<option value="-1" selected>Seleccionar</option>
																		<c:forEach var="listaproce" items="${listaProce}">
																			<option value="${listaproce.cpIdprocedencia}">${listaproce.cpDescripcion}
																			</option>
																		</c:forEach>
																	</select>
																</div>


															</div>
															<div class="row" id="filaServicio">
																<div class="col-4">
																	<label for="ServicioComboBoxM">Svc </label> <select id="ServicioComboBoxM"
																		class="SSM" name="cfgServicios">
																		<option value="-1" selected>Seleccionar</option>
																		<c:forEach var="listaServicio"
																			items="${listaServicios}">
																			<option value="${listaServicio.csIdservicio}">${listaServicio.csDescripcion}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-4">
																	<label for="SalaComboBoxM">Sala </label> <select disabled
																		id="SalaComboBoxM"
																		class="SSM" name="salas">
																		<option disabled value="" selected>Seleccionar</option>

																	</select>
																</div>
																<div class="col-4">
																	<label for="CamaComboBoxM">Cama </label> <select disabled
																		id="CamaComboBoxM"
																		class="SSM" name="camas">
																		<option disabled value="" selected>Seleccionar</option>

																	</select>
																</div>
															</div>


														</div>
														<!-- fin panel izq paciente-->
														<!-- panel derpaciente-->
														<div class="col-6" id="panelDerPaciente">
															<!-- Botonera medico -->
															<div class="row" id="filaToolbarMedico">
																<h4 class="col-4">Médico</h4>
																<div class="col-4 text-right">
																	<a href="./RegistroMedico" id="BuscarMed"
																		class="navi-link " href="#"> <span
																		class='symbol symbol-30 symbol-circle'> <span
																			class='symbol-label circuloBuscarPaciente bg-hover-primary  hoverIcon'>
																				<i class="la la-search iBuscarPaciente"></i>
																		</span>
																	</span> <span class="navi-text"></span>
																	</a>
																</div>
																<div class="col-4 text-right">
																	<a href="./RegistroMedico" id="AgregarMed"
																		class="navi-link " href="#"> <span
																		class='symbol symbol-30 symbol-circle'> <span
																			class='circuloBuscarPaciente symbol-label bg-hover-primary  hoverIcon'>
																				<i class='fas fa-plus  text-primary iBuscarPaciente'></i>
																		</span>
																	</span> <span class="navi-text"></span>
																	</a>
																</div>
															</div>
															<!-- Fin Botonera medico -->

															<!-- Panel SuperiorOrden -->
															<div class="row" id="filaMedico">
																<div class="col-3">
																	<label>M&eacute;dicos </label> <select
																		id="medicoComboBox" class="form-control ">
																		<c:forEach var="med" items="${listaMedicos}">
																			<option value="${med[0]}">${med[1]}${med[2]}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-3">
																	<label>RUT: </label> <input id="txtRutMedico"
																		type="text" class="form-control alwaysMayus"
																		placeholder="" />
																</div>
																<div class="col-3">
																	<label>Nombres: </label> <input disabled
																		id="txtNombreM" type="text" class="form-control"
																		placeholder="" />
																</div>
																<div class="col-3">
																	<label>Apellidos: </label> <input disabled
																		id="txtPrimerApellidoM" type="text"
																		class="form-control" placeholder="" />
																</div>
															</div>

															<div class="row" id="filaObservacionR">
																<div class="col">
																	<label>Observaci&oacute;n </label> <input
																		id="txtObservacionR" name="dfObservacion" type="text"
																		class="form-control" placeholder="" />
																</div>
															</div>

															<div clas="row" id="filaDiagnosticoR">
																<div class="col">
																	<label>Diagn&oacute;stico: </label> <select
																		id="DiagnosticoComboBoxM"
																		class="form-control selectpicker"
																		name="cfgDiagnosticos">
																		<option value="0" disabled selected>Seleccionar</option>
																		<c:forEach var="med" items="${listaDiag}">
																			<option value="${med[0]}">${med[1]}</option>
																		</c:forEach>
																	</select>
																</div>
															</div>

															<div clas="row" id="filaConvenioR">
																<div class="col">
																	<label>Convenio </label> <select
																		id="ConvencioComboBoxM"
																		class="form-control selectpicker " name="cfgConvenios">
																		<option value="N" disabled selected>Seleccionar</option>
																		<c:forEach var="listaConvenio"
																			items="${listaConvenio}">
																			<option value="${listaConvenio.ccIdconvenio}">
																				${listaConvenio.ccDescripcion}</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<!-- fin panel derpaciente-->
													</div>
												</div>
											</div>
											<!-- fin contenedor paciente-->
											<!--  Datos ex&aacute;menes -->
											<div class="row" id="contenedorPaciente">

												<div class="col">
													<div class="form-group">
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
											<!--  Fin Datos ex&aacute;menes -->
										</div>
									</div>
									<!--end: Wizard Step 1-->
									<!-- *************************************** Fin Datos de Paciente***************************************************** -->
								</form>
								<!--end: Wizard Form-->
							</div>
						</div>
						<!-- Fin Contenedor form -->
					</div>
				</div>

			</div>
			<!--end: Wizard Card Body-->
		</div>
		<!-- fin card -->
	</div>
	<!--end: Wizard-->

	<!-- ----------------------------------------  MODALES  -->
	<!-- Button trigger modal 
		<button id="BotonAbrirModalExitoso" type="button"
			class="ocultar btn btn-primary" data-toggle="modal"
			data-target="#exampleModal"></button>
    -->

	<!-- Modal 
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
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
								<label id="lblComprobanteOVoucher" class="col-12 col-form-label">Comprobante</label>
								<div class="col-6">
									<span id="spanCheck"
										class="switch switch-outline switch-icon switch-primary">
										<label> <input id='checkBoxLeverCromp' name=''
											class=" " type='checkbox' checked value='S' /> <span></span>
									</label>
									</span>
								</div>
								<input id="txtEstado" name="estado" type="text"
									class="form-control  ocultar" autocomplete="off" placeholder="" />
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
		-->

	<!-- ----------------------------------------  FIN MODALES  -->
	</div>
	<jsp:include page="common/Scripts.jsp" />
	<script
		src="<%=request.getContextPath()%>/resources/template/assets/js/pages/custom/wizard/wizard-3.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.validadores.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.wizard.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.functions.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.binding.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/dual-listbox-env.js"></script>

</body>
</html>



<!-- Panel Ex&aacute;menes 
                                  <div class="row" id="filaExamenes">
                                    <div class="col">
                                      <div class="form-group">
                                        <div id="examenContainer">
                                          <select id="kt_dual_listbox_2" class="selectDemo">
                                            <c:forEach var="listaExamen"
                                              items="${listaExamen}">
                                              <option value="${listaExamen.ceIdexamen}"
                                                data-search=>${listaExamen.ceDescripcion}|${listaExamen.ceCodigoexamen}
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
                                  -->
<!-- Fin Panel Ex&aacute;menes -->


<!-- Panel izquierdo Confirmacion 
                            <div class="col-6" id="panelIzqConfirmacion">
                              Panel izquierdo Confirmacion
                              <div class="row" id="filaPacienteR">
                                <div class="col">
                                  <label>Paciente </label> <input id="txtNombrePacienteR"
                                    name="" type="text" class="form-control"
                                    placeholder="" />
                                </div>
                              </div>

                              <div class="row" id="filaMedicoR">
                                <div class="col">
                                  <label>M&eacute;dico </label> <input
                                    id="txtNombreMedicoR" name="" type="text"
                                    class="form-control" placeholder="" />
                                </div>
                              </div>
                              <div class="row" id="filaLugarR">
                                <div class="col" id="divtxtLugarR">
                                  <label>Lugar </label> <input id="txtLugarR" name=""
                                    type="text" class="form-control" placeholder="" />
                                </div>
                              </div>

                              <div class="row" id="filaUbicacionR">
                                <div class="col" id="divtxtUbicacionR">
                                  <label>Ubicaci&oacute;n </label> <input
                                    id="txtUbicacionR" name="dfCodigolocalizacion"
                                    type="text" class="form-control" placeholder="" />
                                </div>
                              </div>
                              <div class="row" id="filaExamenesR">
                                <div class="col">
                                  <label>Exámenes </label>
                                  <ul id="txtExamenesR" name="">
                                  </ul>
                                </div>
                              </div>


                            </div>
                                                        -->

<!-- Fin Panel izquierdo Confirmacion -->

<!-- 

                              <div class="row" id="filaObservaciones">
                                <div class="col">
                                  <label>Observaci&oacute;n </label>
                                  <textarea disabled id="txtObservacionP"
                                    class="form-control cleanPaciente" placeholder=""
                                    rows="2"></textarea>
                                </div>
                              </div>


 -->
<!-- 
 
                                    <div class="col-6">
                                      <label class="">Prioridad de atenci&oacute;n
                                      </label> <select id="selectPrioridadAtencionPac"
                                        name="cfgPrioridadatencion"
                                        class="form-control selectpicker "
                                        data-live-search="true">
                                        <option value="" disabled selected>Seleccionar</option>
                                        <c:forEach var="prior" items="${listaPrior}">
                                          <option value="${prior.cpaIdprioridadatencion}">${prior.cpaDescripcion}</option>
                                        </c:forEach>
                                      </select>
                                    </div>
 
 
  -->
