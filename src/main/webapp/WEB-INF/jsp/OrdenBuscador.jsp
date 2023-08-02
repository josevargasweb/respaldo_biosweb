<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prueba Buscador de Ordenes</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/biosbo.css">
<link rel="stylesheet"
  href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

<style type="text/css">
.boDisabled {
  color:grey;
}
.boDisabled  input{
  color:grey;
   
}
.boDisabled  select{
  color:grey;
}
</style>

</head>
<body>
	<jsp:include page="desarrollo/Bios_OrdenesBuscador.jsp"></jsp:include>

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>


<script
  src="<%=request.getContextPath()%>/resources/js/common/biosvalidador.js"></script>
<script 
  src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosbo.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/common/biosbo.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/css/bootstrap-notify.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/utiles.js"></script>
<script
src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>



</html>