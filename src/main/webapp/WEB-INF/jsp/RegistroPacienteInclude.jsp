<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="font-size: 10px">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="common/Styles.jsp" />
<title>Registro Paciente</title>
<link rel="icon" type="image/jpg"
	href="<%=request.getContextPath()%>/resources/img/gbios.png">
</head>
<body id="kt_body"
	class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
	<jsp:include page="common/Menu.jsp" />
	<jsp:include page="common/Header.jsp" />
	<div class="container">
		<div id='idAccordion' class="accordion">
			<div id="panelSuperior" class="card ">
				<div class="card-header " id="headingOne8">
					<div class="card-title row justify-content-between "
						data-toggle="collapse" data-target="#panelBusquedaPaciente">
						<h3 class="card-label col-3 ">Búsqueda de Paciente</h3>
						<div class="col-4 justify-content-right">
							<div class=" float-right">
								<a id="btnLimpiarFiltro" class="navi-link"> <span
									class="symbol symbol-30 symbol-circle "> <span
										id="circuloLimpiarFiltro"
										class="symbol-label bg-hover-primary  hoverIcon" href="#"
										data-toggle="tooltip" title="Limpiar"><i
											id="iLimpiarFiltro" class="la la-broom icon-xl text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
							</div>
							<div class=" float-right">
								<a id="nuevoPaciente" class="navi-link"> <span
									class='symbol symbol-30 symbol-circle '> <span
										id="circuloAgregarPaciente"
										class='symbol-label bg-hover-primary  hoverIcon' href="#"
										data-toggle="tooltip" title="Nuevo paciente"><i
											id="iAgregarPaciente"
											class='fa fas fa-user-plus  text-primary'></i></span>
								</span>
								</a>
							</div>
						</div>
					</div>
				</div>
        <jsp:include page="BuscarPaciente.jsp" />
			</div>
			<!-- ********************************************************* FORMULARIO REGISTRO PACIENTE ***********************************************-->

			<div id="panelInferior" class="card">
				<div class="card-header " id=headingRegistroPaciente"">
					<div class="card-title row justify-content-between  ">
						<h3 class="card-label col-3 ">Registro de Paciente</h3>

						<!-- ************************************** Botonera Registro Paciente **************************-->
						<div class="col-4 justify-content-right ">
							<div class="float-right">
<div id ="divbtnLimpiar">
								<a id="btnLimpiar" class="navi-link pointer"> <span
									class="symbol symbol-30 symbol-circle "> <span
										id="circuloLimpiar"
										class="symbol-label bg-hover-primary hoverIcon"
										data-toggle="tooltip" title="Limpiar"><i id="iLimpiar"
											class="la la-broom icon-xl  text-primary"></i></span>
								</span> <span class="navi-text"></span>
								</a>
</div>
							</div>
							<div class="float-right">
								<div id ="divbtnEditar">
									<a id="btnEditar" class="navi-link pointer"> <span
										class='symbol symbol-30 symbol-circle'> <span
											id="circuloEditarPaciente" class='symbol-label circuloIcon'
											data-toggle="tooltip" title="Editar paciente"><i
												id="iEditPaciente" class="far fa-edit text-dark-50"></i></span>
									</span>
									</a>
								</div>
							</div>
							<div class="float-right">
                <div id ="divbuscarPaciente">
								<a id="buscarPaciente" class="navi-link" href="#"> <span
									class='symbol symbol-30 symbol-circle mr-3'> <span
										id="circuloBuscarPaciente"
										class='symbol-label bg-hover-primary hoverIcon'
										data-toggle="tooltip" title="Buscar paciente"><i
											id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
								</span>
								</a>
                </div>
							</div>
						</div>
						<!-- ************************************** Botonera Registro Paciente **************************-->


					</div>
				</div>

				<!--  ********************************************************** Registro de paciente *****************************************************************  -->
        <!--  ********************************************************** finRegistro de paciente *****************************************************************  -->
        <jsp:include page="RegistrarPaciente.jsp" />

			</div>
			<!-- Fin Panel inferior  -->
		</div>
		<!-- Fin accordion  -->
	</div>
	<!-- Fin container -->
	<jsp:include page="common/Scripts.jsp" />
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/common/jquery.mask.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteFunciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteVisualizacion.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteValidaciones.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteFiltros.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/paciente/RegistroPacienteJs.js"></script>

</body>

</html>
