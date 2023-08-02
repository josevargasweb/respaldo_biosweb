<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Firma Masiva</title>
<link
	href="<%=request.getContextPath()%>/resources/template/assets/css/style.bundle.css"
	rel="stylesheet" type="text/css" />

<link
	href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/header/base/light.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/header/menu/light.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/brand/dark.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/biosbo.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css" />
<link
	href="https://cdn.datatables.net/select/1.3.4/css/select.dataTables.min.css"
	rel="stylesheet" type="text/css" />

<style>
.fmr_container {
	display: grid;
	grid-template-columns: 1fr;
	grid-template-rows: 1fr;
	grid-column-gap: 0px;
	grid-row-gap: 0px;
}
</style>

</head>
<body id="kt_body"
	class="">
	<jsp:include page="Menu.jsp"></jsp:include>
	<jsp:include page="desarrollo/Bios_OrdenesBuscador.jsp"></jsp:include>
	<div id="" class="fmr_container">
		<table id="fmr_t_ordenes"
			style="border-style: solid; border-width: thin">
			<thead id="fmr_th_ordenes">
				<tr>

					<th>Selecci&oacute;n</th>
					<th>#&nbsp;</th>
					<th>N. Orden</th>
					<th>C&oacute;digo</th>
					<th>Examen&nbsp;</th>
					<th>Secci&oacute;n</th>
					<th>Estado&nbsp;</th>
					<th>Acci&oacute;n</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td colspan="8">
						<button id="fmr_btn_firmar">Firmar</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>

<script
	src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>

<!-- 
 <script src="https://cdn.datatables.net/select/1.3.4/js/dataTables.select.min.js"></script>
 -->
<script
	src="<%=request.getContextPath()%>/resources/js/dataTables.select.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosbo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/fmr.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/css/bootstrap-notify.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/utiles.js"></script>
</html>

<!-- 

<table>
<thead>
</thead>
<tbody>
<tr>
<td>
</td>
</tr>
</tbody>
</table>

 -->