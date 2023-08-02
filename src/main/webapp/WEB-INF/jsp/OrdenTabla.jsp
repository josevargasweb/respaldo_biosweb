<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
	padding: 0px;
	margin: 0px;
	border-style: solid;
	border-width: thin;
	font-size: 13px;
}
</style>
<link
	href="https://cdn.jsdelivr.net/npm/dual-listbox/dist/dual-listbox.css">

</head>
<body>
	<table>
		<tr>
			<td>
				<!-- *************************************** Datos del Paciente ***************************************************** -->
				<table>
					<thead>
						<th colspan="6">Secci&oacute;n Datos del Paciente</th>
					</thead>
					<tbody>
						<tr>
							<td>
								<div  style="float: left; margin-right: 20px;">
									<label for="selectTipoDocumento">Tipo de documento: </label> 
									<select 
										id="selectTipoDocumento" name="ldvtdIdtipodocumento">
										<c:forEach var="documento" items="${listaTiposDocumentos}">
											<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
										</c:forEach>
									</select>
								</div>
							</td>
							<td>
              <div  style="float: left; margin-right: 20px;">
							
							<label for="txtRutP" id="tipoDocLabel">RUN</label>
								<div id="divTxtRutFiltro">
									<input id="txtRutP" type="text" placeholder="" />
								</div>
								<div id='divTxtPasaporteFiltro'>
									<input id="txtPasaporte" type="text" placeholder="" />
                </div>
                </div></td>
							<td>
								<div style="float: left; margin-right: 20px;">

									<label for="txtNombreP">Nombres: </label> <input disabled
										id="txtNombreP" type="text" placeholder="" />
								</div>
							</td>
							<td>
								<div style="float: left; margin-right: 20px;">

									<label for="txtPrimerApellidoP">Apellidos: </label> <input
										disabled id="txtPrimerApellidoP" type="text" placeholder="" />
								</div>
							</td>


							<td>
								<div style="float: left; margin-right: 20px;">
									<label for="sexoPacienteOrden">Sexo </label> 
									<select disabled
										id="sexoPacienteOrden">
										<option value="N" disabled selected></option>
										<c:forEach var="sexo" items="${listaSexo}">
					     	     <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
					           </c:forEach>
					         </select>
								</div>
							</td>
							<td>
								<div style="float: left; margin-right: 20px;">
									<label for="txtEdad">Edad: </label> <input disabled
										id="txtEdad" type="text" placeholder="" />
								</div>
							</td>
						</tr>

						<tr>

							<td>
								<div style="float: left; margin-right: 20px;">

									<label for="txtEmailP">Email </label> <input disabled
										id="txtEmailP" name="dfEnviarresultadosemail" type="text"
										class="form-control cleanPaciente" placeholder="" />
									<div class="d-md-none mb-2"></div>
								</div>
							</td>

							<td><label for="chbxRestutToMail" class="cleanPaciente">
									<input id="chbxRestutToMail" type="checkbox" /> ¿Env&iacute;o
									de resultado al mail?
							</label></td>

							<td>
								<div style="float: left; margin-right: 20px;">
									<label for="txtObservacionP">Observaci&oacute;n </label>
									<textarea disabled id="txtObservacionP" class="cleanPaciente"
										placeholder="" rows="1"></textarea>
								</div>

							</td>

							<td>
								<div style="float: left; margin-right: 20px;">
									<label for="" tipoDeAtencion>Tipo de atenci&oacute;n </label> <select
										class="" id="tipoDeAtencion" name="cfgTipoatencion">
										<option value="-1">Seleccionar</option>
										<c:forEach var="listaTipoAtt" items="${listaTipoAt}">
											<option value="${listaTipoAtt.ctaIdtipoatencion}">${listaTipoAtt.ctaDescripcion}</option>
										</c:forEach>
									</select>
								</div>
							</td>

							<td><label for="selectPrioridadAtencionPac" class="">Prioridad
									de atenci&oacute;n </label> <select id="selectPrioridadAtencionPac"
								name="cfgPrioridadatencion" class="form-control  "
								data-live-search="true">
									<option value="" disabled selected>Seleccionar</option>
									<c:forEach var="prior" items="${listaPrior}">
										<option value="${prior.cpaIdprioridadatencion}">${prior.cpaDescripcion}</option>
									</c:forEach>
							</select></td>
							<td>&nbsp</td>
						</tr>
						<tr>
							<td colspan="6">
								<!--align-items-center-->

								<table id="tableDiagnosticoPaciente" class="table"
									style="border-width: 3px;">
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
							</td>

						</tr>
					</tbody>
				</table> <!-- *************************************** Fin Datos del Paciente ***************************************************** -->
				<hr />
			</td>
		</tr>
		<tr>
			<td>
				<!-- *************************************** Datos Orden| ***************************************************** -->
				<table>
					<thead>
						<th colspan="4">Secci&oacute;n &Oacute;rdenes</th>
					</thead>
					<tr>
						<td>
							<table>
								<thead>
									<th>Medico</th>
								</thead>
								<tbody>
									<tr>
										<td><label>M&eacute;dicos </label> <select
											id="medicoComboBox" class="form-control ">
												<c:forEach var="med" items="${listaMedicos}">
													<option value="${med[0]}">${med[1]}${med[2]}</option>
												</c:forEach>
										</select></td>
										<td><label>RUT: </label> <input id="txtRutMedico"
											type="text" class="form-control alwaysMayus" placeholder="" />
										</td>
										<td><label>Nombres: </label> <input disabled
											id="txtNombreM" type="text" class="form-control"
											placeholder="" /></td>
										<td><label>Apellidos: </label> <input disabled
											id="txtPrimerApellidoM" type="text" class="form-control"
											placeholder="" /></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<th>Ubicaci&oacute;n</th>
								</tr>
								<tr>
									<td><label>Procedencia </label> <select
										id="ProcedenciaComboBoxM" class="form-control  "
										name="cfgProcedencias">
											<option value="-1" selected>Seleccionar</option>
											<c:forEach var="listaproce" items="${listaProce}">
												<option value="${listaproce.cpIdprocedencia}">${listaproce.cpDescripcion}
												</option>
											</c:forEach>
									</select></td>
									<td><label>Servicio </label> <select
										id="ServicioComboBoxM" class="form-control  "
										name="cfgServicios">
											<option value="-1" selected>Seleccionar</option>
											<c:forEach var="listaServicio" items="${listaServicios}">
												<option value="${listaServicio.csIdservicio}">${listaServicio.csDescripcion}</option>
											</c:forEach>
									</select></td>
									<td><label>Sala </label> <select disabled
										id="SalaComboBoxM" class="form-control  SSM" name="salas">
											<option disabled value="" selected>Seleccionar</option>

									</select></td>
									<td><label>Cama </label> <select disabled
										id="CamaComboBoxM" class="form-control  SSM" name="camas">
											<option disabled value="" selected>Seleccionar</option>

									</select></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<h4>Cl&iacute;nica</h4>
									</td>
									<td><label>Diagn&oacute;stico: </label> <select
										id="DiagnosticoComboBoxM" class="form-control "
										name="cfgDiagnosticos">
											<option value="0" disabled selected>Seleccionar</option>
											<c:forEach var="med" items="${listaDiag}">
												<option value="${med[0]}">${med[1]}</option>
											</c:forEach>
									</select></td>
									<td><label>Observaci&oacute;n </label> <input
										id="txtObservacion" name="" type="text" class="form-control"
										placeholder="" />
										<div class="d-md-none mb-2"></div></td>
									<td>
										<div class="col-12 border-bottom">
											<h4>Financiamiento</h4>
										</div> <label>Convenio </label> <select id="ConvencioComboBoxM"
										class="form-control  " name="cfgConvenios">
											<option value="N" disabled selected>Seleccionar</option>
											<c:forEach var="listaConvenio" items="${listaConvenio}">
												<option value="${listaConvenio.ccIdconvenio}">${listaConvenio.ccDescripcion}
												</option>
											</c:forEach>
									</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>

				</table> <!-- *************************************** Fin Datos Orden| ***************************************************** -->
			</td>
		</tr>
		<tr>
			<td>
				<hr /> <!-- *************************************** Datos Ex&aacute;menes| ***************************************************** -->
				<table>
					<thead>
						<th th colspan="4">Seccion Ex&aacute;menes</th>
					</thead>
					<tr>
						<td>

							<div id="examenContainer">
								<select id="kt_dual_listbox_2" class="selectDemo  dual-listbox">
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
						</td>
					</tr>
					<tr>
						<td>
							<button type="button" id="switchList" class="btn btn-primary"
								name="switchList">Examen|Panel</button>
						</td>
					</tr>
					<!-- *************************************** Fin Datos Ex&aacute;menes| ***************************************************** -->
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<hr> <!-- *************************************** Datos Confirmación| ***************************************************** -->
				<!-- 
				<table>
					<thead>
						<th colspan="6">Seccion Confirmacion
						<th>
					</thead>
					<tr>
						<td><label>Paciente </label> <input id="txtNombrePacienteR"
							name="" type="text" class="form-control" disabled placeholder="" />
						</td>
						<td><label>M&eacute;dico </label> <input
							id="txtNombreMedicoR" name="" type="text" class="form-control"
							disabled placeholder="" /></td>
						<td><label>Lugar </label> <input id="txtLugarR" name=""
							type="text" class="form-control" placeholder="" /></td>
						<td><label>Ubicaci&oacute;n </label> <input
							id="txtUbicacionR" name="dfCodigolocalizacion" type="text"
							class="form-control" placeholder="" /></td>
						<td><label>ExÃ¡menes </label>
							<ul id="txtExamenesR" name="">

							</ul></td>
						<td><label>Observaci&oacute;n </label> <input
							id="txtObservacionR" name="dfObservacion" type="text"
							class="form-control" placeholder="" /></td>

					</tr>

				</table>
 -->
			</td>
		</tr>
	</table>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/js/bootstrap-select.min.js"
	integrity="sha512-yDlE7vpGDP7o2eftkCiPZ+yuUyEcaBwoJoIhdXv71KZWugFqEphIS3PU60lEkFaz8RxaVsMpSvQxMBaKVwA5xg=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/notify/0.4.2/notify.js"
	integrity="sha512-uE2UhqPZkcKyOjeXjPCmYsW9Sudy5Vbv0XwAVnKBamQeasAVAmH6HR9j5Qpy6Itk1cxk+ypFRPeAZwNnEwNuzQ=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Orden.validadores.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/utiles.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/normalizarUTF8.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Orden.functions.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Orden.binding.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/localStorage.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/selectorFiltro.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/getAge.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/dual-listbox-env.js"></script>


</html>
