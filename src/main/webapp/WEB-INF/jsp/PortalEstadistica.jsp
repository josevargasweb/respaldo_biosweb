
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BiosLIS | Visualización de datos</title>



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

	<div class="container container-main-content container-captura">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0" id="accordionExample8">
				<div id="consulta-datos" class="card border-15">
					<div class="card-header border-15" id="estadisticaTable">
						<div class="card-title d-flex justify-content-between">
							<h3 class="mb-0 btn-block text-left pl-0 col-12"
								data-toggle="collapse" data-target="#collapseOne8"
								aria-expanded="true" aria-controls="collapseOne8">Consulta
								de Datos</h3>
						</div>
					</div>
					<div id="collapseOne8" class="collapse show container-content"
						data-parent="#estadisticaTable">
						<div class="card-body d-flex">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th scope="col" style="width: 3%;">#</th>
										<th scope="col" style="width: 70%;">Titulo</th>
										<th scope="col">Visualizar</th>
										<th scope="col">Descargar</th>
										<th scope="col">Query</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="row" style="width: 3%;">1</th>
										<td style="width: 70%;">Total por Examen</td>
										<td>
											<button type="button" id="btnTotalPorExamen"
												class="btn  col-1 ">
												<i style="font-size: 24px; color: black" class="fa fa-eye"
													aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="btnTotalPorExamenExcel"
												class="btn  col-3 ">
												<i style="font-size: 24px; color: green"
													class="fa fa-file-excel" aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="queryTotalPorExamen"
												class="btn  col-1">
												<i style="font-size: 24px; color: black" class="fa fa-table"
													aria-hidden="true"></i>
											</button>
										</td>
									</tr>
									<tr>
										<th scope="row" style="width: 3%;">2</th>
										<td style="width: 70%;">Muestras rechazadas</td>
										<td>
											<button type="button" id="btnMuestrasRechazadas"
												class="btn  col-1 ">
												<i style="font-size: 24px; color: black" class="fa fa-eye"
													aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="btnMuestrasRechazadasExcel"
												class="btn  col-3 ">
												<i style="font-size: 24px; color: green"
													class="fa fa-file-excel" aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="queryMuestrasRechazadas"
												class="btn  col-1">
												<i style="font-size: 24px; color: black" class="fa fa-table"
													aria-hidden="true"></i>
											</button>
										</td>
									</tr>
									<tr>
										<th scope="row" style="width: 3%;">3</th>
										<td style="width: 70%;">Ex&aacute;menes Derivados</td>
										<td>
											<button type="button" id="btnExamenesDerivados"
												class="btn  col-1 ">
												<i style="font-size: 24px; color: black" class="fa fa-eye"
													aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="btnExamenesDerivadosExcel"
												class="btn  col-3 ">
												<i style="font-size: 24px; color: green"
													class="fa fa-file-excel" aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="queryExamenesDerivados"
												class="btn  col-1">
												<i style="font-size: 24px; color: black" class="fa fa-table"
													aria-hidden="true"></i>
											</button>
										</td>
									</tr>
									<tr>
										<th scope="row" style="width: 3%;">4</th>
										<td style="width: 70%;">Tiempo entrega ex&aacute;menes</td>
										<td>
											<button type="button" id="btnTiempoEntrega"
												class="btn  col-1 ">
												<i style="font-size: 24px; color: black" class="fa fa-eye"
													aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="btnTiempoEntregaExcel"
												class="btn  col-3 ">
												<i style="font-size: 24px; color: green"
													class="fa fa-file-excel" aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="queryTiempoEntrega"
												class="btn  col-1">
												<i style="font-size: 24px; color: black" class="fa fa-table"
													aria-hidden="true"></i>
											</button>
										</td>
									</tr>
									<tr>
										<th scope="row" style="width: 3%;">5</th>
										<td style="width: 70%;">Resultados críticos</td>
										<td>
											<button type="button" id="btnResultadosCriticos"
												class="btn  col-1 ">
												<i style="font-size: 24px; color: black" class="fa fa-eye"
													aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="btnResultadosCriticosExcel"
												class="btn  col-3 ">
												<i style="font-size: 24px; color: green"
													class="fa fa-file-excel" aria-hidden="true"></i>
											</button>
										</td>
										<td>
											<button type="button" id="queryResultadosCriticos"
												class="btn  col-1">
												<i style="font-size: 24px; color: black" class="fa fa-table"
													aria-hidden="true"></i>
											</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div id="estadisticaHistorico" class="card border-15 mt-5"
					style="display: none">
					<div class="card-header border-15" id="estadisticaTable">
						<div class="card-title d-flex justify-content-between">
							<h3 id="tituloBusqueda" class="col-12 pl-0 pr-0 text-left">Visualización
								de Datos</h3>
						</div>
					</div>
					<div id="bodyTablaBusqueda" class="col-12"></div>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="modalFiltroEstadistica" tabindex="-1"
		role="dialog" aria-labelledby="opcTestExamenModal1" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-md"
			role="document">
			<div class="modal-content">
				<div class="modal-body" id="modalbody1">
					<div class="card card-2">
						<div class="card-body">
							<h3 class="col-12  text-center">Establecer Parámetros</h3>
							<div class="col-12">
								<div class="row">
									<div class="col-6">
										<label for="bo_nOrdenIni"
											class="col-form-label text-left col-12">N.Orden
											Inicio</label>
										<div class="col-12">
											<input type="text" class="form-control" id="bo_nOrdenIni" />
										</div>

										<label for="bo_nOrdenFin"
											class="col-form-label text-left col-12 ">N.Orden Fin</label>
										<div class="col-12">
											<input type="text" class="form-control" id="bo_nOrdenFin" />
										</div>

										<label for="bo_fIni" class="col-form-label text-left col-12">Fecha
											Inicio</label>
										<div class="col-12">
											<input type="Date" id="bo_fIni" class="form-control" />
										</div>

										<label for="bo_fFin" class="col-form-label text-left col-12">Fecha
											Fin</label>
										<div class="col-12">
											<input type="Date" id="bo_fFin" class="form-control" />
										</div>

										<label for="bo_laboratorio"
											class="col-form-label text-left col-12">Laboratorio</label>
										<div class="col-12">
											<select id="bo_laboratorio" class="form-control">
												<option selected="true" disabled="disabled" value="-1">Seleccionar</option>
											</select>
										</div>

										<label for="bo_seccion"
											class="col-form-label text-left col-12">Secci&oacute;n</label>
										<div class="col-12">
											<select id="bo_seccion" class="form-control">
												<option selected="true" disabled="disabled" value="-1">Seleccionar</option>
											</select>
										</div>

										<label for="bo_examen" class="col-form-label text-left col-12">Examen</label>
										<div class="col-12">
											<select id="bo_examen" class="form-control">
												<option selected="true" disabled="disabled" value="-1">Seleccionar</option>
											</select>
										</div>
										<!--  hasta aca los parametros ****************************************************************************************   - -->
										<div hidden class="hidden">
											<label for="bo_localizacion"
												class="col-form-label text-left col-12">Localizaci&oacute;n</label>
											<div class="col-12 ">
												<select id="bo_localizacion" class="form-control">
													<option selected="true" disabled="disabled" value="">Seleccionar</option>
												</select>
											</div>
										</div>

										<div hidden class="hidden">
											<label for="bo_tipoDocumento"
												class="col-form-label text-left col-12">Tipo
												Documento</label>
											<div class="col-12">
												<select id="bo_tipoDocumento" class="form-control">
													<option selected="true" disabled="disabled" value="" hidden>Seleccionar</option>
												</select>
											</div>
										</div>
										<!-- **************************************************************************************************************** -->
									</div>
									<div class="col-6">
										<label for="bo_procedencia"
											class="col-form-label text-left col-12">Procedencia</label>
										<div class="col-12">
											<select id="bo_procedencia" class="form-control">
												<option selected="true" value="-1">Seleccionar</option>
											</select>
										</div>
										<label for="bo_servicio"
											class="col-form-label text-left col-12">Servicio</label>
										<div class="col-12">
											<select id="bo_servicio" class="form-control">
												<option selected="true" value="-1">Seleccionar</option>
											</select>
										</div>

										<label for="bo_convenio"
											class="col-form-label text-left col-12">Convenio</label>
										<div class="col-12">
											<select id="bo_convenio" class="form-control">
												<option selected="true" value="-1">Seleccionar</option>
											</select>
										</div>

										<label for="bo_tipoAtencion"
											class="col-form-label text-left col-12">Tipo Atención</label>
										<div class="col-12">
											<select id="bo_tipoAtencion" class="form-control">
												<option selected="true" value="-1">Seleccionar</option>
											</select>
										</div>

										<label for="bo_nroDocumento"
											class="col-form-label text-left col-12">RUN</label>
										<div class="col-12">
											<input type="text" id="bo_nroDocumento" class="form-control" />
										</div>

										<label for="bo_nombre" class="col-form-label text-left col-12">Nombre</label>
										<div class="col-12">
											<input type="text" id="bo_nombre" class="form-control" />
										</div>

										<label for="bo_apellido"
											class="col-form-label text-left col-12">1er Apellido</label>
										<div class="col-10">
											<input type="text" id="bo_apellido" class="form-control" />
										</div>

										<label for="bo_segundoApellido"
											class="col-form-label text-left col-12">2do Apellido</label>
										<div class="col-12">
											<input type="text" id="bo_segundoApellido"
												class="form-control" />
										</div>
									</div>
									<div class="col-12">
										<button hidden id="btnEstadisticaTiempoEntrega"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="la la-search"></i>Buscar
										</button>
										<button hidden id="btnEstadisticaTotalPorExamen"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="la la-search"></i>Buscar
										</button>
										<button hidden id="btnEstadisticaMuestrasRechazadas"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="la la-search"></i>Buscar
										</button>
										<button hidden id="btnEstadisticaExamenesDerivados"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="la la-search"></i>Buscar
										</button>
										<button hidden id="btnEstadisticaResultadosCriticos"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="la la-search"></i>Buscar
										</button>

										<button hidden id="btnEstadisticaTiempoEntregaExcel"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="fa fa-download"></i>Descargar
										</button>
										<button hidden id="btnEstadisticaTotalPorExamenExcel"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="fa fa-download"></i>Descargar
										</button>
										<button hidden id="btnEstadisticaMuestrasRechazadasExcel"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="fa fa-download"></i>Descargar
										</button>
										<button hidden id="btnEstadisticaExamenesDerivadosExcel"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="fa fa-download"></i>Descargar
										</button>
										<button hidden id="btnEstadisticaResultadosCriticosExcel"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
											<i class="fa fa-download"></i>Descargar
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

	<div class="modal fade" id="modalPromociones" tabindex="-1"
		role="dialog" aria-labelledby="opcTestExamenModal" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-xl"
			role="document">
			<div class="modal-content pl-10 ">
				<div class="modal-body" id="modalbody" style="width: 90%"></div>
			</div>
		</div>
	</div>

	<jsp:include page="common/Scripts.jsp" />

	<!--begin::Global Theme Bundle(used by all pages)-->

	<script
		src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
	<!--end::Global Theme Bundle-->
	<script src="https://unpkg.com/imask"></script>
	<script
		src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
	<script
		src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>


	<script
		src="<%=request.getContextPath()%>/resources/js/common/dual-data.js"></script>
	<!--   js para crear excel ************************** -->

	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>


	<!-- ******************************************** -->

	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/EstadisticaExcel.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/TotalPorExamen.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/MuestrasRechazadas.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/ExamenesDerivados.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/TiempoEntregaExamenes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/portalEstadistica/ResultadosCriticos.js"></script>

	<script
		src="<%=request.getContextPath()%>/resources/js/common/ModuloFecha.js"></script>

</body>
</html>