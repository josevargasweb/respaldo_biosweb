<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<jsp:include page="common/Styles.jsp"/>
<title>BIOS-LIS</title>
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize" >
	<jsp:include page="common/Menu.jsp"/>
        <jsp:include page="common/Header.jsp"/>
	<div class="container mt-5" style="background-color: green">

		<div class="row mr-1 ml-0">
			<div class="col-8" style="background-color: red">

				<div class="card card-custom gutter-b">
					<div class="card-header" id="headingOne8">
						<div class="card-title row col-12 d-flex justify-content-between">
							<h3 class="card-label col-3 pl-0 pb-3">Selecci&oacute;n de
								Orden:</h3>
						</div>
					</div>
					<div class="card-body row">
						<!-- FORMULARIO FILTRO -->
						<div class="col-12">
							<table
								class="table table-separate table-head-custom table-checkable table-hover"
								id="ordenesDatatable" name="ordenesDatatable">
								<thead>
									<tr>
										<th>Nro. orden</th>
										<th>Fecha Orden</th>
										<th>Nombre</th>
										<th>Sexo</th>
										<th>Edad</th>
										<th>Tipo Atención</th>
										<th>Procedencia</th>
										<th>Servicio</th>
										<th>Acciones</th>
										<th>Acciones</th>
										<th>Acciones</th>
									</tr>
								</thead>
							</table>
						</div>
						<!-- FIN TABLA FILTRO -->
					</div>

				</div>
			</div>
			<div class="col-4" style="background-color: blue"></div>
		</div>
	</div>
</body>
<jsp:include page="common/Scripts.jsp"/>


</html>
