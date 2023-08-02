<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registro Orden</title>
<jsp:include page="common/Styles.jsp" />
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
	<jsp:include page="common/Menu.jsp" />
	<jsp:include page="common/Header.jsp" />
	<jsp:include page="common/AlertaSession.jsp" />
	<div class="container">
		<div class="row " id="divform">
			<div class="col ">
				<form method="post" class="form" id="kt_form">
					<input id="idPaciente" name="datosPacientes" type="hidden" /> <input
						id="idMedico" type="hidden" name="dfIdmedico" /> <input
						id="txtEdad" type="hidden" /> <input id="txtEmailP"
						name="dfEnviarresultadosemail" type="hidden" /> <input
						id="sexoPacienteOrden" type="hidden" /> <input
						id="txtPrimerApellidoP" type="hidden" />
					<!-- *************************************** Datos del Form ***************************************************** -->
					<!-- contenedor paciente y orden-->
					<div class="row" id="botoneraPacienteOrden">
						<div class="col-6">
							<div class="row">
								<div class="col-6">Datos paciente</div>
								<div class="col-3">
									<button type="button" id="btnPacienteBasico">B&aacute;sicos</button>
								</div>
								<div class="col-3">
									<button type="button" id="btnPacienteExtra">Extras</button>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="row">
								<div class="col-6">Datos orden</div>
								<div class="col-3">
									<button type="button" id="btnOrdenBasico">B&aacute;sicos</button>
								</div>
								<div class="col-3">
									<button type="button" id="btnOrdenExtra">Extras</button>
								</div>
							</div>
						</div>
					</div>
					<div class="row" id="contenedorPacienteOrden">
						<!-- panel izq panelContenidoBasicoPaciente-->
						<div class="col-6 border border-info rounded"
							id="panelContenidoBasicoPaciente">


							<div class="row" id="filaDoc">
								<div class="col-4">
									<label>Tipo de documento </label> <select
										class="form-control selectpicker " id="selectTipoDocumento"
										name="ldvtdIdtipodocumento">
										<c:forEach var="documento" items="${listaTiposDocumentos}">
											<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-4">
									<label id="tipoDocLabel">RUN</label>
									<div id="divTxtRutFiltro">
										<input id="txtRutP" type="text"
											class="form-control alwaysMayus cleanPaciente" placeholder="" />
									</div>
									<div id='divTxtPasaporteFiltro'>
										<input id="txtPasaporte" type="text"
											class="form-control alwaysMayus cleanPaciente" placeholder="" />
									</div>
								</div>

								<!--  Botonera paciente-->
								<div class="col-4 ">
									<div class="float-right">
										<a href="#" id="BuscarPac" class="navi-link ">
											<span class='symbol symbol-30 symbol-circle mr-3'> <span
												id="circuloBuscarPaciente"
												class='symbol-label bg-hover-primary  hoverIcon '
												data-toggle="tooltip" title="Buscar paciente"> <i
													id="iBuscarPaciente" class="fas fa-search text-primary "></i>
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
												data-toggle="tooltip" title="Agregar paciente"> <i
													id="iAgregarPaciente" class='fas fa-plus text-primary'></i>
											</span>
										</span> <span class="navi-text"></span>
										</a>
									</div>
								</div>
								<!--  fin Botonera paciente -->


							</div>

							<div class="row" id="filaNombres">
								<div class="col ">

									<div id="divNombreCompleto"></div>

									<input id="txtNombreP" type="hidden"
										class="form-control cleanPaciente" placeholder="" />

								</div>
							</div>


							<div class="row" id="filaEnvioMail">
								<div class="col">
									<label class="checkbox checkbox-primary cleanPaciente">
										<input id="chbxRestutToMail" type="checkbox" /> Envío de
										resultado al mail? <span></span>
									</label>
								</div>
							</div>
							<div class="row" id="filaAtencionProcedencia">
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
									<label>Procedencia </label> <select id="ProcedenciaComboBoxM"
										class="form-control selectpicker " name="cfgProcedencias">
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
									<label>Servicio </label> <select id="ServicioComboBoxM"
										class="form-control selectpicker " name="cfgServicios">
										<option value="-1" selected>Seleccionar</option>
										<c:forEach var="listaServicio" items="${listaServicios}">
											<option value="${listaServicio.csIdservicio}">${listaServicio.csDescripcion}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-4">
									<label>Sala </label> <select disabled id="SalaComboBoxM"
										class="form-control selectpicker SSM" name="salas">
										<option disabled value="" selected>Seleccionar</option>

									</select>
								</div>
								<div class="col-4">
									<label>Cama </label> <select disabled id="CamaComboBoxM"
										class="form-control selectpicker SSM" name="camas">
										<option disabled value="" selected>Seleccionar</option>

									</select>
								</div>
							</div>

						</div>
						<!-- fin panel izq panelContenidoBasicoPaciente-->

						<!-- panel izq panelContenidoExtraPaciente-->

						<div class="col-6 border border-info rounded"
							id="panelContenidoExtraPaciente">

							<div class="row" id="filaExamenes">
								<div class="col">
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

						<!-- panel izq panelContenidoExtraPaciente-->




						<!-- panel der panelContenidoBasicoOrden-->
						<div class="col-6 border border-info rounded"
							id="panelContenidoBasicoOrden">

							<!-- Botonera medico -->
							<div class="row">
								<h4 class="col-3">Médico</h4>
								<div class="col-9">

									<div class="float-right">
										<a href="./RegistroMedico" id="BuscarMed" class="navi-link "
											href="#"> <span
											class='symbol symbol-30 symbol-circle mr-3'> <span
												class='symbol-label circuloBuscarMedico bg-hover-primary  hoverIcon'>
													<i class="fas fa-search text-primary icirculoBuscarMedico"></i>
											</span>
										</span> <span class="navi-text"></span>
										</a>
									</div>
									<div class="float-right">
										<a href="./RegistroMedico" id="AgregarMed" class="navi-link "
											href="#"> <span
											class='symbol symbol-30 symbol-circle mr-3'> <span
												class='circuloAgregarMedico symbol-label bg-hover-primary  hoverIcon'
												data-toggle="tooltip" title="Agregar médico"> <i
													class='fas fa-plus  text-primary iAgregarMedico'></i>
											</span>
										</span> <span class="navi-text"></span>
										</a>
									</div>
								</div>
							</div>
							<!-- Fin Botonera medico -->

							<!-- Panel Medico -->
							<div class="row" id="idPanelMedico">
								<div class="col-3">
									<label>M&eacute;dicos </label> <select id="medicoComboBox"
										class="form-control ">
										<c:forEach var="med" items="${listaMedicos}">
											<option value="${med[0]}">${med[1]}${med[2]}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-3">
									<label>RUT: </label> <input id="txtRutMedico" type="text"
										class="form-control alwaysMayus" placeholder="" />
								</div>
								<div class="col-6">
									<label>Nombres: </label> <input disabled id="txtNombreM"
										type="text" class="form-control" placeholder="" />
								</div>
							</div>

							<!-- Fin Panel Medico -->

							<!-- Panel Financiamiento -->
							<div clas="row">
								<div class="col border-bottom">
									<h4>Financiamiento</h4>
								</div>
							</div>

							<div clas="row">
								<div class="col">
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

							<!-- Fin Panel Financiamiento -->
						</div>
						<!-- fin panel der panelContenidoBasicoOrden-->

						<!-- panel der panelContenidoExtraOrden-->
						<div class="col-6  border border-info rounded"
							id="panelContenidoExtraOrden">
							<div class="row">
								<div class="col">
									<label>Observaci&oacute;n </label> <input id="txtObservacionR"
										name="dfObservacion" type="text" class="form-control"
										placeholder="" />
								</div>
							</div>

							<div class="row">
								<div class="col border-bottom">
									<h4>Clínica</h4>
								</div>
							</div>

							<div clas="row">
								<div class="col">
									<label>Diagn&oacute;stico: </label> <select
										id="DiagnosticoComboBoxM" class="form-control selectpicker"
										name="cfgDiagnosticos">
										<option value="0" disabled selected>Seleccionar</option>
										<c:forEach var="med" items="${listaDiag}">
											<option value="${med[0]}">${med[1]}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<!-- fin panel der panelContenidoExtraOrden-->



					</div>
					<!-- fin contenedor paciente  y orden-->

					<!-- Inicio contenedor ex&aacute;menes-->
					<div class="container">
						<div class="row mt-4 " id="contenedorExamenes">

							<div class="col-3 border  border-info rounded">
                <div class="row">
                  <div class="col">
                    <input type="text" id="idSearcher" inputmode="search"/>
                  </div>
                </div>
								<div class="row">
									<div class="col">
										<select id="idDualDataVisualSource" size="5">
											<option value="-1">Seleccionar</option>
										</select>
									</div>
								</div>
							</div>

							<div class="col-1 border  border-info rounded">
								<div id="dualDataButtonBar">
									<button id="clickAddBtn" type="button">-&gt;</button>
									<button id="clickDelBtn" type="button">&lt;-</button>
								</div>
							</div>

							<div class="col-8 border  border-info rounded">
								<table id="idDataTarget">
									<thead>
										<tr>
											<th>Sel</th>
											<th>C&oacute;digo</th>
											<th>Examen</th>
											<th>Tipo de Muestra</th>
										</tr>
									</thead>
								</table>
							</div>

						</div>
					</div>

					<div class="row" id="guardar">
						<div class="col">
							<button id="btnGuardarOrden"
								class="btn btn-primary font-weight-bold "
								data-wizard-type="action-submit" type="button">Guardar</button>
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
					<h6>Orden registrada satisfactoriamente</h6>
					<br>
					<h3 id="numeroOrdenTitulo"></h3>
					<div class="col-8 row">
						<div class="col-md-12">
							<label>Imprimir comprobante de atenci&oacute;n? </label>
						</div>
						<div class="col-3">
							<a id="imprimirComprobante" class="" href="#"> <span
								class='symbol symbol-50 mr-3'> <span class='symbol-label'><i
										class="la la-print"></i></i></span>
							</span> <span class="navi-text"></span>
							</a>
						</div>
						<div class="col-9">
							<label id="lblComprobanteOVoucher" class="col-12 col-form-label">Comprobante</label>
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
	<!--  Fin Modal  -->

  <!-- Modal Buscar Paciente
  <jsp:include page="modal/ModalPacienteBuscar.jsp" />
  -->

  <!-- Fin Modal Buscar Paciente-->

	<!-- ----------------------------------------  FIN MODALES  -->
	<jsp:include page="common/Scripts.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>

  <script
    src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.validadores.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.functions.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Orden.binding.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/dual-data.js"></script>



	</script>

</body>
</html>
