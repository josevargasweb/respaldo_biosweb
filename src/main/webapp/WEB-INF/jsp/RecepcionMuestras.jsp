<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BIOS-LIS | Recepción de Muestras</title>
<jsp:include page="common/Styles_1.jsp" />
<!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css"/> -->
<!-- <link rel="stylesheet"
                    href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
                    integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
                    crossorigin="anonymous"> -->
                    <link
                        href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.css"
                        rel="stylesheet" type="text/css" />
                    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css" />
                    <!-- <link
                    rel="stylesheet"
                    href="<%=request.getContextPath()%>/resources/css/tables.css" -->
                    <!-- <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"
                    integrity="sha512-mSYUmp1HYZDFaVKK//63EcZq4iFWFjxSL+Z3T/aCt4IO9Cejm03q3NKKYN6pFQzY0SBOr8h+eCIAZHPXcpZaNw=="
                    crossorigin="anonymous" referrerpolicy="no-referrer" /> -->
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
                    <div class="container container-main-content container-recepcion">
                        <input type="hidden" id="sesionUsuario" name="sesionUsuario"
                            value="${usuario.duNombres} ${usuario.duPrimerapellido}" />
                        <div class="row d-flex justify-content-center container-content-recepcion">
                            <div class="accordion col-12" id="">
                                <input type="hidden" id="sesionUsuario" name="sesionUsuario"
                                    value="${usuario.duNombres} ${usuario.duPrimerapellido}" />
                                <input type="hidden" id="recepcionaMuestras" name="recepcionaMuestras"
                                    value="${perfilUsuario.cpuRecepcionxexamen}" />
                                <div class="card card-custom card-recepcion mb-0">
                                    <div class="card-header" id="headingOne8">
                                        <div class="card-title row d-flex justify-content-between col-12">
                                            <h3 class="mb-0 text-left pl-0 col-10">
                                                Recepción Muestras
                                            </h3>
                                        </div>
                                    </div>
                                    <div class="row ">
                                        <!-- contenedor izquierdo -->
                                        <div class="col-2-5 recep-cont-izquierda h-100">
                                            <div class="card card-2 recep-topcont-izquierda h-80 m-3 p-1">
                                                <div class="card-header mb-1 pt-3 pl-2 pr-2">
                                                    <div class="col-12 pl-0 pr-0">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="h5 ch4">Pendientes</div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <button id="actualizarTabla" type="button"
                                                                    class="btn btn-sm btn-secondary mr-2">
                                                                    <i class="fas fa-redo-alt" aria-hidden="true"></i>
                                                                </button>
                                                                <label class="checkbox checkbox-primary mb-0">
                                                                    <input id="chkActivos" name="activo" type="checkbox"
                                                                        value="N">Automática
                                                                    <span></span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <!--<a class="pointer" id="actualizarTabla"><i class="flaticon2-refresh icon-x black-text">Actualizar</i></a>-->
                                                    </div>
                                                </div>
                                                <div id="divTablaPendientes">
                                                    <table id="tablaPendientes"
                                                        class="table  table-hover table-striped">
                                                        <thead>
                                                            <tr class="trHeader">
                                                                <th>Fecha</th>
                                                                <th id="thNombre" scope="col">N&deg; Muestra</th>
                                                                <th>Muestra-Contenedor</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="tbodyPendientesFiltro" class="scroll scroll-pull"
                                                            data-scroll="true" data-suppress-scroll-x="false"
                                                            data-swipe-easing="false" style="max-height: 670px">

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <!-- codigo de barras -->
                                            <div class="card card-custom gutter-b shadow-none">
                                                <div class="card-body row">
                                                    <div class="col-12">
                                                        <!-- este codigo no se debe borra debido a que se encuentra en evaluacion -->
                                                        <c:if test="${perfilUsuario.cpuRecepcionxexamen == 'S'}">
                                                            <select id="selectUsuario" name="dUsuario"
                                                            class="form-control" disabled hidden>
                                                            <c:forEach var="user" items="${listaUsuarios}">
                                                                <option value="${user.duIdusuario}"
                                                                    ${user.duIdusuario==usuario.duIdusuario
                                                                                    ? 'selected' : '' }>${user.duNombres}
                                                                                    ${user.duPrimerapellido}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                        </c:if>
                                                       
                                                        <!-- <c:if test="${perfilUsuario.cpuRecepcionxexamen == 'S'}">
                                                            <div class="col-12 pl-0 pr-0 form-group row mb-0">

                                                                <div class="col-12 mt-3">
                                                                    <img src="<%=request.getContextPath()%>/resources/img/receptor.png"
                                                                        width="30" height="30" />
                                                                    <label class="">
                                                                        Receptor
                                                                    </label>
                                                                </div>
                                                                <div class="col-12">
                                                                    <input type="hidden" value="${listaUsuarios}"
                                                                        id="listaFlebotomistas" />
													<select id="selectUsuario" name="dUsuario"
														class="form-control selectpicker" disabled>
														<c:forEach var="user" items="${listaUsuarios}">
															<option value="${user.duIdusuario}"
																${user.duIdusuario==usuario.duIdusuario
                                                                                ? 'selected' : '' }>${user.duNombres}
                                                                                ${user.duPrimerapellido}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${perfilUsuario.cpuRecepcionxexamen == 'N'}">
                                                            <p>Usuario no posee permisos para recepcionar muestra</p>
                                                        </c:if> -->
                                                        <!-- este codigo no se debe borra debido a que se encuentra en evaluacion -->
                                                    </div>
                                                </div>

                                            </div>
                                            <!-- fin codigo de barras -->
                                        </div>
                                        <!-- fin contenedor izquierdo -->
                                        <!-- contenedor medio -->
                                        <div class="col-5-5 recep-cont-mid">
                                            <div class="row mt-3">
                                                <div class="d-flex align-items-end justify-content-center col-3">
                                                    <button type="button" class="btn p-1" disabled="">
                                                        <img src="<%=request.getContextPath()%>/resources/img/scanner.png" width="30"
                                                            height="30">
                                                    </button>
                                                    <label>
                                                        Código de barras
                                                    </label>
                                                </div>
                                                <div class="col-9">
                                                    <input id="txtCodigoBarras" class="form-control" type="text"
                                                        style="text-transform:uppercase" />
                                                </div>
                                            </div>

                                            <!-- paciente -->
                                            <div id="recep-botomcont-mid" class="recep-botomcont-mid pl-0 mt-3">
                                                <div class="card card-2 card-custom gutter-b shadow-none p-2">
                                                    <div class="card-body pt-0">
                                                        <div class=" mx-auto">
                                                            <h6 class="title-paciente"> Paciente</h6>
                                                            <div class='col-12 pl-0 pr-0 mb-2 form-row'>
                                                                <div class="col-5 form-group mb-0">
                                                                    <label id=""
                                                                        class="col-form-label pb-0 col-12 pl-0">Nombre</label>
                                                                    <span id="txtNombrePaciente"></span>
                                                                </div>
                                                                <div class="col-4 form-group  mb-0">
                                                                    <label id=""
                                                                        class="col-form-label pb-0 col-12 pl-0">Sexo</label>
                                                                    <span id="txtSexoPaciente"></span>
                                                                </div>
                                                                <div class="col-3 form-group mb-0">
                                                                    <label id=""
                                                                        class="col-form-label pb-0 col-12 pl-0">Edad</label>
                                                                    <span id="txtEdadPaciente"></span>
                                                                </div>
                                                                <div class="col-4 form-group mt-1 mb-0">
                                                                    <label id=""
                                                                        class="col-form-label pb-0 col-12 pl-0">Tipo
                                                                        de Atención</label>
                                                                    <span id="txtTipoAtencion"></span>
                                                                </div>
                                                                <div class="col-4 form-group mt-1 mb-0">
                                                                    <label id=""
                                                                        class=" col-form-label pb-0 col-12 pl-0">Procedencia</label>
                                                                    <span id="txtProcedencia"></span>
                                                                </div>
                                                                <div class="col-4 form-group mt-1 mb-0">
                                                                    <label id=""
                                                                        class=" col-form-label pb-0 col-12 pl-0">Servicio</label>
                                                                    <span id="txtServicio"></span>
                                                                </div>

                                                            </div>
                                                            <h6 class="mt-1  title-orden"> Orden</h6>
                                                            <div class="col-12 pl-0 pr-0 form-row">
                                                                <div class='col-6 d-flex flex-wrap mr-0'>
                                                                    <div class="col-6 form-group pl-0 mb-0">
                                                                        <label id=""
                                                                            class="col-form-label pb-0 col-12 pl-0">#Orden</label>
                                                                        <span id="txtNOrden"></span>
                                                                    </div>
                                                                    <div class="col-6 form-group pl-0 pr-0 mb-0 ">
                                                                        <label id=""
                                                                            class="col-form-label pb-0 col-12 pl-0">Fecha</label>
                                                                        <span id="txtFechaOrden"></span>
                                                                    </div>
                                                                    <div class="col-12 form-group pl-0 pr-0 mb-0">
                                                                        <label id=""
                                                                            class=" col-form-label pb-0 col-12 pl-0">Observación</label>
                                                                        <span id="txtObservacionOrden"></span>
                                                                    </div>
                                                                </div>
                                                                <div class="col-6 d-flex">
                                                                    <div
                                                                        class="col-10 form-group pl-0 pr-0 examenes-recepcion">
                                                                        <label for="exampleTextarea"
                                                                            class=" col-form-label pb-0 col-12 pl-0">Exámenes</label>
                                                                        <ul id="ulExamenes">

                                                                        </ul>
                                                                    </div>
                                                                    <div class="col-2 form-group">
                                                                        <button class="btn" id="modalAntecedentesRM"
                                                                            data-toggle="modal"
                                                                            data-target="#modalAntecedentesPac">
                                                                            <img src="<%=request.getContextPath()%>/resources/img/antecedentes.png"
                                                                                width="30" height="30" />
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <h6 class="mt-1 mb-0 title-muestra"> Toma de Muestra</h6>
                                                            <div class='col-12 form-row pr-0'>
                                                                <div class="col-6 form-group row mb-0">
                                                                    <label id=""
                                                                        class="col-form-label mr-1 pb-0 col-12 pl-0">Fecha</label>
                                                                    <span id="txtFechaTM"></span>
                                                                </div>
                                                                <div class="col-6 form-group row mb-0 ml-5">
                                                                    <label id=""
                                                                        class="col-form-label pb-0 col-12 pl-0">Observación</label>
                                                                    <span id="txtObservacionTM"></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- fin paciente -->

                                            <div class="card recep-topcont-mid">
                                                <span id="dt_registros_m" class="pl-3 mt-3"></span>
                                                <div class="col-12 card-body table-container p-0">
                                                    <div class="card card-2">
                                                        <div class="card-body p-0">
                                                            <table id="tablaMuestras"
                                                                class="table table-hover table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>N&deg; Muestra</th>
                                                                        <th>Fecha</th>
                                                                        <th>Muestra</th>
                                                                        <th>Contenedor</th>
                                                                        <th>Estado</th>
                                                                        <th>Orden</th>
                                                                        <th>Recepcionada por</th>
                                                                        <th>Observación</th>
                                                                        <th>Derivador</th>
                                                                        <th>Acciones</th>
                                                                    </tr>
                                                                </thead>
                                                                <thead>
                                                                    <tr>
                                                                        <th></th>
                                                                        <th></th>
                                                                        <th id="thMuestra"></th>
                                                                        <th id="thEnvase"></th>
                                                                        <th></th>
                                                                        <th></th>
                                                                        <th></th>
                                                                        <th></th>
                                                                        <th></th>
                                                                        <th></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodyFiltro"></tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <!-- fin contenedor medio -->
                                        <!-- contenedor derecho -->


                                        <div id="accordion" class="col-2-5 recep-cont-derecho h-100">
                                            <div class="card card-2 h-80 m-3 p-1">
                                                <ul class="nav nav-pills mb-3 d-flex justify-content-around"
                                                    id="pills-tab" role="tablist">
                                                    <li class="nav-item" role="presentation">
                                                        <button class="nav-link nav-link-pills active"
                                                            id="recepcionadas-tab" data-toggle="pill"
                                                            data-target="#tab-recepcionadas" type="button" role="tab"
                                                            aria-controls="recepcionadas"
                                                            aria-selected="true">Recepcionadas</button>
                                                    </li>
                                                    <li class="nav-item" role="presentation">
                                                        <button class="nav-link nav-link-pills" id="derivadas-tab"
                                                            data-toggle="pill" data-target="#tab-derivadas"
                                                            type="button" role="tab" aria-controls="derivadas"
                                                            aria-selected="false">Derivadas</button>
                                                    </li>
                                                </ul>
                                                <div class="tab-content mt-3" id="nav-tabContent">
                                                    <div class="tab-pane fade show active" id="tab-recepcionadas"
                                                        role="tabpanel" aria-labelledby="nav-recepcionadas-tab">
                                                        <table id="tablaRecepcionadas"
                                                            class="table  table-hover table-striped">
                                                            <thead>
                                                                <tr class="trHeader">
                                                                    <th>Fecha</th>
                                                                    <th id="thNombre" scope="col">N&deg; Muestra
                                                                    </th>
                                                                    <th>Muestra-Contenedor</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="tbodyRecepcionadasFiltro">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="tab-pane fade" id="tab-derivadas" role="tabpanel"
                                                        aria-labelledby="nav-derivadas-tab">
                                                        <table id="tablaDerivadas"
                                                            class="table  table-hover table-striped">
                                                            <thead>
                                                                <tr class="trHeader">
                                                                    <th scope="col">Fecha</th>
                                                                    <th id="thNombre" scope="col">N&deg;
                                                                    </th>
                                                                    <th>Muestra - Contenedor</th>
                                                                    <th>Derivador</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="tbodyFiltroDerivadas">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- fin contenedor derecho -->
                                        </div>
                                    </div>


                                    <div id="modaAntecedentesRM" class="modal fade bd-example-modal-lg" tabindex="-1"
                                        role="dialog" aria-labelledby="" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-body">
                                                    <h4 id="tituloAntecendetesPaciente">Antecedentes Paciente </h4>

                                                    <div class="col-12 row mt-15">
                                                        <div class="col-6" id="formAntecedentes1"></div>
                                                    </div>
                                                </div>
                                                <input id="nOrdenAntecedentes" name="" type="text"
                                                    class="form-control ml-22 " autocomplete="off" placeholder=""
                                                    style="visibility:hidden " />
                                                <ul id="idAntecedentes" style="visibility:hidden ">
                                                </ul>
                                                <div class="modal-footer">
                                                    <button id="" onclick="guardarAntecedentes()" type="button"
                                                        class="btn btn-primary">Guardar</button>
                                                    <button type="button" class="btn btn-secondary"
                                                        data-dismiss="modal">Cerrar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="confirmVolverPendiente" tabindex="-1" role="dialog"
                                        aria-labelledby="myModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4>Cambiar estado muestra</h4>
                                                </div>
                                                <div class="modal-body">
                                                    La muestra nro. <span id="codMuestraP"></span> ya se encuentra
                                                    Recepcionada.<br />
                                                    ¿Está seguro que desea retornar el estado de Recepción de la muestra
                                                    a Pendiente?
                                                    <input id="idMuestraP" class="form-control" name="" type="hidden"
                                                        disabled="true" />
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 btn-si"
                                                        data-dismiss="modal">Si</button>
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 btn-no"
                                                        data-dismiss="modal">No</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="muestraNoTomada" tabindex="-1" role="dialog"
                                        aria-labelledby="" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4>Muestra no tomada</h4>
                                                </div>
                                                <div class="modal-body">
                                                    La muestra nro. <span id="codMuestraNoTomada"></span> no se
                                                    encuentrada Tomada.
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                                                        data-dismiss="modal">Ok</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="receptorMuestra" tabindex="-1" role="dialog"
                                        aria-labelledby="" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4>Cambio de Receptor</h4>
                                                </div>
                                                <div class="modal-body">
                                                    ¿Est&aacute; seguro de cambiar el usuario receptor de la muestra?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si"
                                                        data-dismiss="modal">Si</button>
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
                                                        data-dismiss="modal">No</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Modal aceptacion -->
                                    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title titulo-color"></h5>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">

                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
                                                        data-dismiss="modal">No</button>
                                                    <button type="button"
                                                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si">Si</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Fin Modal -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="common/AlertaSession.jsp" />
                    <jsp:include page="modal/ModalAntecedentesPac.jsp"></jsp:include>


					<div id="modaAntecedentesRM" class="modal fade bd-example-modal-lg"
						tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-body">
									<h4 id="tituloAntecendetesPaciente">Antecedentes Paciente
									</h4>

									<div class="col-12 row mt-15">
										<div class="col-6" id="formAntecedentes1"></div>
									</div>
								</div>
								<input id="nOrdenAntecedentes" name="" type="text"
									class="form-control ml-22 " autocomplete="off" placeholder=""
									style="visibility: hidden" />
								<ul id="idAntecedentes" style="visibility: hidden">
								</ul>
								<div class="modal-footer">
									<button id="" onclick="guardarAntecedentes()" type="button"
										class="btn btn-primary">Guardar</button>
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Cerrar</button>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="confirmVolverPendiente" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h4>Cambiar estado muestra</h4>
								</div>
								<div class="modal-body">
									La muestra nro. <span id="codMuestraP"></span> ya se encuentra
									Recepcionada.<br /> ¿Está seguro que desea retornar el estado
									de Recepción de la muestra a Pendiente? <input id="idMuestraP"
										class="form-control" name="" type="hidden" disabled="true" />
								</div>
								<div class="modal-footer">
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 btn-si"
										data-dismiss="modal">Si</button>
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 btn-no"
										data-dismiss="modal">No</button>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="muestraNoTomada" tabindex="-1"
						role="dialog" aria-labelledby="" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h4>Muestra no tomada</h4>
								</div>
								<div class="modal-body">
									La muestra nro. <span id="codMuestraNoTomada"></span> no se
									encuentrada Tomada.
								</div>
								<div class="modal-footer">
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
										data-dismiss="modal">Ok</button>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="receptorMuestra" tabindex="-1"
						role="dialog" aria-labelledby="" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h4>Cambio de Receptor</h4>
								</div>
								<div class="modal-body">¿Est&aacute; seguro de cambiar el
									usuario receptor de la muestra?</div>
								<div class="modal-footer">
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si"
										data-dismiss="modal">Si</button>
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
										data-dismiss="modal">No</button>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal aceptacion -->
					<div class="modal fade" id="confirmModal" tabindex="-1"
						role="dialog">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title titulo-color"></h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body"></div>
								<div class="modal-footer">
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
										data-dismiss="modal">No</button>
									<button type="button"
										class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si">Si</button>
								</div>
							</div>
						</div>
					</div>
					<!-- Fin Modal -->
				</div>
			</div>
		</div>
		<jsp:include page="common/AlertaSession.jsp" />
		<jsp:include page="modal/ModalAntecedentesPac.jsp"></jsp:include>


		<jsp:include page="common/Scripts.jsp" />
		<script
			src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
		<script src="https://unpkg.com/imask"></script>
		<!--begin::Page Scripts(used by this page)-->
		<!-- <script
                        src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script> -->

                    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
                    <!--end::Global Theme Bundle-->
                    <script src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
                    <script src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>

		<!--end::Page Vendors-->
		<!-- <script src="<%=request.getContextPath()%>/resources/js/dataTables.cellEdit.js"></script> -->
		<!-- <script src="https://cdn.datatables.net/datetime/1.1.1/js/dataTables.dateTime.min.js"></script> -->
		<!-- <script src="<%=request.getContextPath()%>/resources/colResize/jquery.dataTables.colResize.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/colReorder/dataTables.colReorder.js"></script> -->
                    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgMuestras.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/Dto/CfgEnvases.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/modals/ModalAntecedentesPaciente.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/recepcionMuestras/RecepcionMuestras.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/recepcionMuestras/RecepcionMuestrasDatatable.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/FuncionesGenerales.js"></script>

		<script type="text/javascript">
                        $.fn.dataTable.Api.register("row().show()", function () {
                            var page_info = this.table().page.info();
                            // Get row index
                            var new_row_index = this.index();
                            // Row position
                            var row_position = this.table()
                                .rows({ search: "applied" })[0]
                                .indexOf(new_row_index);
                            // Already on right page ?
                            if (
                                (row_position >= page_info.start && row_position < page_info.end) ||
                                row_position < 0
                            ) {
                                // Return row object
                                return this;
                            }
                            // Find page number
                            var page_to_display = Math.floor(
                                row_position / this.table().page.len()
                            );
                            // Go to that page
                            this.table().page(page_to_display);
                            // Return row object
                            return this;
                        });
                    </script>
                </body>

                </html>
