<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BiosLIS | Firma masiva de resultados</title>
        <jsp:include page="common/Styles_1.jsp"/>
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                    <jsp:include page="common/Menu_dinamico.jsp"/>
            </div>
            <div class="col-md-6">
                    <jsp:include page="common/Header_1.jsp" />
            </div>
				</div>
        <jsp:include page="common/AlertaSession.jsp"/>
				<div class="container container-main-content mt-5">
					<div class="row d-flex justify-content-center mt-4">
							<div class="accordion col-12 pl-0 pr-0">
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
												<jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
												<div class="row mt-3">
													<div class="col-4"></div>
													<div class="col-8">
														<!-- <button class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"  id="fmr_btn_revisar_ordenes">Revisar</button> -->
														<!-- <button class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" id="fmr_btn_firmar_ordenes">Firmar</button> -->
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div id="panelInferior" class="card">
									<div class="card-header " id="headingRegistroPaciente">
										<div class="card-title d-flex justify-content-between">
											<h3 class="card-label col-3 pl-0">Firma masiva de resultados</h3>
										</div>
									</div>
									<div id="panelResultadoOrden ">
										<div class="card-body no-gutters">
											<div class="col-12">
												<div class="row">
													<div class="col-12">
															<div class="col-12 pl-0 pr-0" >
																<div class="card card-2 mt-3">
																	<div class="card-body p-0">
																		<table id="fmr_t_ordenes"
																		class="table table-hover table-striped nowrap compact-xs" width="100%">
																			<thead id="">
																				<tr>
																					<th>Selecci&oacute;n</th>
																					<th>N. Orden</th>
																					<th>C&oacute;digo</th>
																					<th>Examen&nbsp;</th>
																					<th>Secci&oacute;n</th>
																					<th>Estado&nbsp;</th>
																				</tr>
																			</thead>
																			<tfoot>
																				<tr>
																					<td colspan="6">
																						<button class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" id="fmr_btn_firmar">Firmar</button>
																					</td>
																				</tr>
																			</tfoot>
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
							</div>
						</div>
					</div>
	

<!-- FIN FORMULARIO PACIENTE -->     
<jsp:include page="common/Scripts.jsp"/>

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
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>

<script
  src="<%=request.getContextPath()%>/resources/js/common/dual-data-edicion.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
	<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>

	<script
	src="<%=request.getContextPath()%>/resources/js/firmaresultados/FirmaResultados.busqueda.js"></script>

	<script
	src="<%=request.getContextPath()%>/resources/js/firmaresultados/ModuloBiosboFirmaResultados.js"></script>
	<!-- <script
	src="<%=request.getContextPath()%>/resources/js/common/fmr.js"></script> -->

</html>
<!--end::Page Scripts-->
</body>
<!--end::Body-->
</html>