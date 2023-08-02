<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BiosLIS | Impresión etiquetas</title>

<!-- <link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/biosbo.css"> -->
	<jsp:include page="common/Styles_1.jsp"/>
	<!-- <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" /> -->
	<!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css"/> -->

<!-- <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.10.5/viewer.css"
	integrity="sha512-c7kgo7PyRiLnl7mPdTDaH0dUhJMpij4aXRMOHmXaFCu96jInpKc8sZ2U6lby3+mOpLSSlAndRtH6dIonO9qVEQ=="
	crossorigin="anonymous" referrerpolicy="no-referrer" /> -->

<style type="text/css">
/* .boDisabled {
	color: grey;
}

.boDisabled  input {
	color: grey;
}

.boDisabled  select {
	color: grey;
} */
</style>

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
								<!-- <jsp:include page="desarrollo/Bios_OrdenesBuscador.jsp"></jsp:include> -->
								<jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
				<div id="panelInferior" class="card">
					<div class="card-header " id="headingRegistroPaciente">
						<div class="card-title d-flex justify-content-between">
							<h3 class="card-label col-3 pl-0">Muestras</h3>
						</div>
					</div>
					<div id="panelResultadoOrden ">
						<div class="card-body no-gutters">
							<div class="col-12">
								<jsp:include page="desarrollo/TomaMuestras.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>


<!--end::Global Theme Bundle-->
<jsp:include page="common/AlertaSession.jsp" />
<jsp:include page="common/Scripts.jsp" />
<script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
<!--begin::Global Theme Bundle(used by all pages)-->
<script
  src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<!--end::Global Theme Bundle-->
<script src="https://unpkg.com/imask"></script>
<script
  src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
<script
  src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/css/bootstrap-select.min.css"
  integrity="sha512-ARJR74swou2y0Q2V9k0GbzQ/5vJ2RBSoCWokg4zkfM29Fb3vZEQyv0iWBMW/yvKgyHSR/7D64pFMmU8nYmbRkg=="
  crossorigin="anonymous" referrerpolicy="no-referrer" />


<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>


<script
	src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/common/EtiquetaImpresion.js"></script>

<script src="<%=request.getContextPath()%>/resources/js/viewer.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-viewer.js"></script>

</html>