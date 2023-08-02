<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BIOS-LIS | Configuración de Fórmulas</title>
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
	<div class="container container-main-content">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0" id="registro-paciente-main">
				<div id="panelSuperior" class="card">
					<div class="card-header" id="registro-paciente-title">
						<div class="card-title d-flex justify-content-between">
							<h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion"
							data-toggle="collapse"
							data-target="#panelBusquedaPaciente" aria-expanded="true"
							aria-controls="panelBusquedaPaciente">B&uacute;squeda de F&oacute;rmulas </h3>
							<div class="card-buttons col-2 d-flex justify-content-end">
								<a id="nuevoMetodo" class="navi-link"> <span
									class='symbol symbol-30 symbol-circle '> <span
										id="circuloAgregarTest"
										class='symbol-label bg-hover-blue  hoverIcon' href="#"
										data-toggle="tooltip" title="Nueva formula"> <i
											id="iAgregarTest" class='fas fa-plus  text-blue'></i>
									</span>
								</span>
								</a> <a id="btnLimpiarFiltro" class="navi-link"> <span
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
					<div id="panelBusquedaPaciente"
						class="collapse show container-content">
						<div class="card-body d-flex">
							<!-- FORMULARIO FILTRO -->
							<div class="col-6">
								<div class=" row">
									<label class="col-form-label text-left col-4 ">Laboratorio</label>
									<div class="col-7">
										<select class="form-control selectpicker disabledForm"
										id="selectLaboFormula" data-live-search="true">
										<option value=null selected disabled hidden></option>
											
										</select>
									</div>
								</div>
								<div class=" row">
									<label class="col-form-label text-left col-4 ">Secci&oacute;n</label>
									<div class="col-7">
										<select class="form-control selectpicker disabledForm"
										id="selectSeccionFormula" data-live-search="true">
										<option value=null selected disabled hidden></option>
											
										</select>
									</div>
								</div>
								<div class=" row">
									<label class="col-form-label text-left col-4 ">Examen</label>
									<div class="col-7">
										<select class="form-control selectpicker disabledForm"
											id="selectExamenFormula" data-live-search="true">
											<option value=null selected disabled hidden></option>
											
										</select>
									</div>
								</div>
							
								<a id="btnBuscarFiltro" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2"><i class="la la-search"></i>Buscar</a>
							</div>

							<!-- FIN FORMULARIO FILTRO -->
							<!-- TABLA FILTRO -->
							<div class="col">
								<div class="table-container mb-3">
									<table id="tableFiltro" class="table table-hover table-striped">
										<thead>
											<tr id="trHeader">
												<th scope="col">#</th>
												<th id="" scope="col">C&oacute;digo</th>
												<th id="thNombre" scope="col">Test</th>
											</tr>
										</thead>
										<tbody id="tbodyFiltro">
	
										</tbody>
									</table>
									<label id="lblErrorFiltro" class="textError text-red ocultar">No
										se encontraron tests con los datos provistos.</label>
								</div>
								<label id="lblTotalFiltro">Test encontrados: <span id="totalFiltro"></span></label>
							</div>
							<!-- FIN TABLA FILTRO -->
						</div>
					</div>
				</div>
				<div class="card mt-5 border-15" id="panelInferior">
					<div class="card-header border-15"> 
						<div class="card-title row col-12 d-flex justify-content-between">
							<h3 class="card-label col-4 pl-0">
								Registro de Fórmulas
							</h3>
							<div class="col-2 justify-content-right iconos-registro">
								<div class="float-right">
									<a id="btnLimpiar" class="navi-link pointer">
										<span class="symbol symbol-30 symbol-circle">
											<span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
												<i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
											</span>
										</span>
										<span class="navi-text"></span>
									</a>
								</div>   
								<div class="float-right">
									<a id="btnEditar" class="navi-link pointer">
										<span class='symbol symbol-30 symbol-circle'>
											<span id="circuloEditarFormula" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar fórmula">
												<i id="iEditFormula" class="far fa-edit text-dark-50"></i>
											</span>
										</span>
									</a>
								</div>
								<div class="float-right">
									<a id="buscarUnidad" class="navi-link" href="#" >
										<span class='symbol symbol-30 symbol-circle mr-3'>
											<span id="circuloBuscarPaciente" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar fórmula">
												<i id="iBuscarPaciente" class="fas fa-search text-blue"></i>
											</span>
										</span>
									</a>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
							<div id="formRegistroTest">
								<div id="divForm" class="row">
									<div class="col-12">
										<input id="txtIdMetodo" name="idMetodo" type="text"
											class="form-control mt-5 ocultar" autocomplete="off"
											placeholder="" />
										<div class="form-group col-12 mb-1">
											<h5 id="tituloRegistro" ></h5> 
										
										</div>
										<div class="col-12">
											<div class="card card-2 h-100">
												<div class="card-body">
													<div class="row">
														<div class=" form-group col-7">
															<label for="exampleTextarea">F&oacute;rmula</label> 
															<textarea
															id="textAreaFormula" type="text" name="formula"
															class="form-control disabledForm" autocomplete="off"
															onkeypress="return isNumberKey(event);" placeholder="" rows="8"></textarea> <input
															id="txtFormula" name="formula" hidden>
														</div>
														<div class="col-md-5">
															<div class="form-group row">
																<label class="col-sm-4 pr-0 col-form-label">Test</label>
																<div class="col-sm-6 pl-0">
																	<select
																	class="form-control selectpicker  disabledForm"
																	id="selectTest" data-live-search="true">
																		<option value=null selected disabled hidden></option>

																	</select>
																</div>
																<div class="col-sm-2">
																	<label id="" class="checkbox checkbox-primary ml-2 ">
																		<input id="chkOtrosTest" name="" type="checkbox" class="" />
																		Otros <span></span>
																	</label>
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-4 pr-0 col-form-label">Antecedentes</label>
																<div class="col-sm-6 pl-0">
																	<select
																	class="form-control selectpicker disabledForm"
																	id="selectAntecedente" data-live-search="true">
																	<option value=null selected disabled hidden></option>

																</select>
																</div>
															</div>
															<div class="row justify-content-center">
							
																<label for="" class="col-12 mt-2 text-center">Operadores</label>
																<div class="col-12">
																	<div class="row justify-content-center">
																		<div class="btn-group mt-1" role="group"
																			aria-label="Basic example">
																			<button id="sumar" type="button"
																				class="btn btn-outline-secondary">+</button>
																			<button id="restar" type="button"
																				class="btn btn-outline-secondary">-</button>
																			<button id="multiplicar" type="button"
																				class="btn btn-outline-secondary">x</button>
																			<button id="dividir" type="button"
																				class="btn btn-outline-secondary">÷</button>
																			<button id="potencia" type="button"
																				class="btn btn-outline-secondary">^</button>
																			<button id="parizq" type="button"
																				class="btn btn-outline-secondary">(</button>
																			<button id="parder" type="button"
																				class="btn btn-outline-secondary">)</button>
																		</div>
																	</div>
																</div>
																<div class="col-12">
																	<div class="row justify-content-center">
																		<div class="btn-group mt-1" role="group">
																			<button id="raiz" type="button"
																				class="btn btn-outline-secondary">sqrt()</button>
																			<button id="log" type="button"
																				class="btn btn-outline-secondary">log()</button>
																			<button id="min" type="button"
																			class="btn btn-outline-secondary">min(,)</button>
																			<button id="max" type="button"
																			class="btn btn-outline-secondary">max(,)</button>
																			<button id="if" type="button"
																			class="btn btn-outline-secondary">if(Cond,V,F)</button>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-md-12 mt-5">
															<button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2" data-toggle="modal"
																data-target="#exampleModal">Condici&oacute;n</button>
															<button id="btnRevisar" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2">
																Revisar</button>
														</div>
														<input id="txtEstado" name="estado" type="text"
															class="form-control mt-5 ocultar" autocomplete="off"
															placeholder="" />
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
								<div class="col-md-6 form-group mt-6 row">
									<div class="col-6">
										<button id="btnAgregarFormula" type="submit"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2">
											<i class="far fa-save"></i>Guardar
										</button>
									</div>
									<div class="col-6">
										<button id="btnGuardarUpdate"
											class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2 ocultar"
											name="update" type="submit">
											<i class="far fa-save"></i>Confirmar
										</button>
									</div>
			
								</div>
								
								<!-- 
								********************************************** Modal condicion ****************************************
								-->
								<div class="modal fade" id="exampleModal" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered modal-lg"
										role="document">
										<div class="modal-content">
											<div class="modal-body">
												<!-- AGREGANDO TABS -->
												<ul class="nav nav-tabs" id="myTab" role="tablist">
													<li class="nav-item condition" role="presentation" id="presentacion">
													  <button class="nav-link active" id="condicion" data-toggle="tab"  data-target="#condicion-sub" type="button" role="tab" aria-controls="condicion-sub" aria-selected="true">Condicion 1</button>
													</li>
													<li class="nav-item" id="nav-item-agregar">
													  <!--<a class="nav-link formConditional" id="agregar-tab" onclick="agregarTabs(2)" disabled>+</a>-->
													  <button type="button" class="nav-link formConditional" id="agregar-tab" onclick="agregarTabs(2)">+</button>
													</li>
												</ul>
												<div class="tab-content border" id="myTabContent">
													<div class="tab-pane fade show active" id="condicion-sub" role="tabpanel" data-id-contenedor="1" aria-labelledby="condicion">
														<div class="form-group row ml-3 mt-3">
															<div class="col-3 ml-3">
																<input class="form-check-input rbSelector" name="rbSelector"
																	checked="checked" type="radio" id="rbTest" data-id="rbTest">
																<label class="form-check-label" for="rbTest"> Test </label>
															</div>
															<div class="col-3">
																<input class="form-check-input rbSelector" name="rbSelector"
																	type="radio" id="rbAntecedente" data-id="rbAntecedente">
																<label class="form-check-label" for="rbAntecedente">
																	Antecedente </label>
															</div>
														</div>
					
														<div class="form-group row ml-2 mr-2">
															<h6 class='col-1 line-height-2'>Si</h6>
															<div class="col-5">
																<select id="selectTestCond" class='form-control  mr-1 selectpicker' data-live-search="true">
																	<option value=null selected disabled hidden></option>
																	<c:forEach var="test" items="${listaTest}">
																		<option value="${test.ctIdtest}">${test.ctAbreviado}</option>
																	</c:forEach>
																</select> <select id="selectAntecedenteCond" style="display: none;"
																	class=' form-control  mr-1'>
																	<option value=null selected disabled hidden></option>
																	<c:forEach var="cond" items="${listaAntecedentes}">
																		<option value="${cond.caIdantecedente}">${cond.caDescripcion}</option>
																	</c:forEach>
																</select>
															</div>
															<div class="col-1 pl-0 pr-0">
																<select id="selectRelation"
																	class='form-control mr-1'>
																	<option value=">">></option>
																	<option value=">=">>=</option>
																	<option value="=">=</option>
																	<option value="<="><=</option>
																	<option value="<"><</option>
																	<option value="<>"><></option>
																</select>
															</div>
															<div class="col-5">
																<input id="condBorde" type='text' class='form-control ' />
															</div>
														</div>
														<div class="form-group row ml-2 mr-2">
															<h6 class="col-12 mt-5" id="exampleModalLabel">Entonces</h6>
														</div>
														<div class="row">
															<div class='col-12 mt-3 row'>
																<label id="" class="checkbox checkbox-primary ml-5 col-3">
																	<input id="checkAssignResult" type="checkbox" class="" />
																	Asignar resultado <span></span>
																</label> <input id="txtAsignarResultado" type="text" name="formula"
																	class="form-control col-7" autocomplete="off" placeholder="" />
															</div>
														</div>
														<div class="row">
															<div class='col-12 mt-3 row'>
																<label id="" class="checkbox checkbox-primary ml-5 col-3">
																	<input id="checkCambiarEstado" type="checkbox" class="" />
																	Cambiar estado a <span></span>
																</label>
																<div class="col-7 px-0">
																	<select class="form-control disabledForm"
																		id="selectEstado" name="ldvsEstadoTest">
																		<option value="N" disabled selected hidden></option>
																		<c:forEach var="estado" items="${listaEstadosTest}">
																			<option value="${estado.certIdestadoresultadotest}">${estado.certDescripcionestadotest}</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<div class="row">
															<div class='col-12 mt-3 row'>
																<label id="" class="checkbox checkbox-primary ml-5 col-3 ">
																	<input id="checkAnadirNota" type="checkbox" class="" />
																	A&ntilde;adir nota <span></span>
					
																</label> <input id="txtAnadirNota" type="text" name="formula"
																	class="form-control col-7" autocomplete="off" placeholder="" />
															</div>
														</div>
														<div class="form-group row ml-2 mr-2 mt-4">
															<div class="col-12 d-flex flex-wrap justify-content-start">
																<div class="col-12 pr-0 pl-0 d-flex justify-content-start">
																	<label id="lblEstado" for="" class="label-examen pr-1 h6">Activo</label> / <label for="" class="label-panel pl-1 h6 text-black-50">Inactivo</label>
																</div>
																<label class="switch-content switch-comprobante switch-active">
																	<input id='checkBoxLever' name='' class="formProceso" type='checkbox' checked />
																	<div></div>
																</label>
															</div>
														</div>
													</div>
												</div>
												<!-- AGREGANDO TABS -->


												
								
												<div class="row">
													<div class="col-12 d-flex flex-wrap justify-content-end">
														<button type="button"
															class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2"
															data-dismiss="modal">Ok</button>
													</div>
												</div>
											</div>
										</div>
									</div>
							</div>
					<!-- 
					********************************************** Fin Modal condicion ****************************************
					-->
						</div>
					</div>
				</div>
			
			</div>
		</div>
	</div>

	<jsp:include page="common/Scripts.jsp" />
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/mathjs/9.3.2/math.js"
		integrity="sha512-Imer9iTeuCPbyZUYNTKsBWsAk3m7n1vOgPsAmw4OlkGSS9qK3/WlJZg7wC/9kL7nUUOyb06AYS8DyYQV7ELEbg=="
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgTests.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgAntecedentes.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgLaboratorios.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/configuraciones/ConfiguracionFormulas.js"></script>
</body>
</html>
