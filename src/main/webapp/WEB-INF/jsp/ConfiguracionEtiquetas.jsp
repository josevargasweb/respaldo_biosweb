<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Configuraci&oacute;n Etiquetas</title>
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

		<div class="container container-main-content">
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0" id="registro-paciente-main">
					<div id="panelInferior" class="card">
						<div class="card-header border-15">
                            <div class="card-title row justify-content-between  ">
                                <h3 class="card-label col-10 pl-0">Configuraci&oacute;n de Etiquetas</h3>
                            <!-- Inicio Tabuladores -->
                            <!-- Fin Tabuladores -->
                            <!-- ************************************** Botonera  **************************-->
								<div class="col-2 justify-content-right iconos-registro">
									<div class="float-right" id="divBtnLimpiar">
										<a id="btnLimpiar" class="navi-link" href="#">
											<span class="symbol symbol-30 symbol-circle">
												<span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar"><i id="iLimpiar" class="la la-broom icon-xl  text-blue"></i></span>
											</span>
											<span class="navi-text"></span>
										</a>
									</div>   
									<div class="float-right" id="divBtnLimpiar2">
										<a id="btnLimpiar2" class="navi-link" href="#">
											<span class="symbol symbol-30 symbol-circle">
												<span id="circuloLimpiar2" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar"><i id="iLimpiar" class="la la-broom icon-xl  text-blue"></i></span>
											</span>
											<span class="navi-text"></span>
										</a>
									</div>   
									<div id="divBtnEditar" class="float-right" >
										<a id="btnUpdater" class="navi-link" href="#" >
											<span class='symbol symbol-30 symbol-circle' >
												<span id="circuloEditarPaciente" class='symbol-label bg-hover-blue circuloIcon' data-toggle="tooltip" title="Editar Etiqueta"><i id="iEditEtiqueta" class="far fa-edit text-blue"></i></span>
											</span>
										</a>
									</div>
									<div id="divCancelEdit" class="float-right neverSeen"  >
										<a id="btnCancelarUpdate" class="navi-link" href="#" >
											<span class='symbol symbol-30 symbol-circle' >
												<span id="circuloCancelEdit" class='symbol-label bg-hover-danger circuloIcon' data-toggle="tooltip" title="Cancelar edición"><i id="iCancelEdit" class="fas fa-times icon-xl text-danger"></i></span>
											</span>
										</a>
									</div>
									<div class="float-right" >
										<a id="agregarEtiqueta" class="navi-link collapseMedico"> 
											<span  class='symbol symbol-30 symbol-circle'> 
												<span id="circuloBuscarPaciente" class='symbol-label bg-hover-blue   hoverIcon' href="#" data-toggle="tooltip" title="Agregar Etiqueta"> 
													<i id="iAgregarTest" class='fas fa-plus text-blue'></i>
												</span>
											</span>
										</a> 
									</div>
                            	</div>
                            <!-- ************************************** Botonera  **************************-->
						</div>
                        </div>
						<div id="panelConfigurarEtiqueta">
							<div class="card-body">
								<div class="col-12 h-80">
									<input type="hidden" id="procedenciaUsuario"
										name="procedenciaUsuario"
										value="${perfilUsuario.cpuIdprocedencia}" />
									<div id="" class=" row">
	
										<div class="col-4">
											<div class="card h-80">
												<div class="card-body ">
													<table id="ce_t_etiquetas"
														class="table table-sm table-striped table-bordered display nowrap"
														width="100%">
														<thead id="bo_th_ordenes">
															<tr>
																<th>#</th>
																<th>Modelo</th>
															</tr>
														</thead>
														<tbody id="bodyEtiquetas">
															
														</tbody>
	
													</table>
												</div>
											</div>
										</div>
	
										<div class="col-4">
											<div class="row mt-4">
												<label for="codigoEtiqueta"
													class="col-form-label text-left col-3">C&oacute;digo</label>
												<div class="col-8">
													<input type="text" class="form-control limpiarEtiqueta "
														id="codigoEtiqueta" tittle="Nro de orden inicial" />
												</div>
											</div>
											<div class="row mt-4">
												<label for="nombreEtiqueta"
													class="col-form-label text-left col-3">Nombre</label>
												<div class="col-8">
													<input type="text" id="nombreEtiqueta"
														class="form-control limpiarEtiqueta" />
												</div>
											</div>
											<div class="row mt-4">
												<label for="selectFont"
													class="col-form-label text-left col-3">Font</label>
												<div class="col-8">		
													<Select  id="selectFont" class="form-control">
														<option value="Sin especificar" >Sin especificar</option>		
														<option value="Code 128">Code 128 </option>	
													</Select>
												</div>
											</div>
										</div>
	
										<div class="col-4">
											<div class="row">
												<div class="col-6">
													<div class="form-check form-check-inline col-12 mt-4">
														<input class="form-check-input col-2" type="checkbox" id="checkboxProcedencia" value="" />
														<label class="form-check-label" for="checkboxProcedencia">Procedencia</label>
													</div>
													<div class="form-check form-check-inline  col-12 mt-4">
														<input class="form-check-input col-2" type="checkbox" id="checkboxServicio" value="" />
														<label class="form-check-label" for="checkboxServicio">Servicio</label>
													</div>
													<div class="form-check form-check-inline  col-12 mt-4">
														<input class="form-check-input col-2" type="checkbox" id="checkboxParametrizada" value="" />
														<label class="form-check-label" for="checkboxParametrizada">Parametrizada</label>
													</div>
												</div>
												<div class="col-6">
													<div class="col-12 mt-4">
														<label> Nota</label>
														<input class="limpiarEtiqueta"  type="text" id="textNota"/>
													</div>
												</div>
											</div>
										</div>
	
	
									</div>
								
								<div id="" class="col-12" style="max-height:300px;overflow-y: auto;">
									<table id="bo_tt_ordenes" class="table table-sm table-bordered"
										width="90">
										<thead id="ordenes">
											<tr>
												<th style="width:15%"></th>
												<th class="text-center" style="width:9%"><label>Fila</label></th>
												<th class="text-center" style="width:9%"><label>Columna</label></th>
												<th class="text-center" style="width:9%"><label>Largo</label></th>
												<th class="text-center" style="width:9%"><label>Activo</label></th>
												<th class="text-center" style="width:9%"><label>Negrita</label></th>
												<th class="text-center" style="width:9%"><label>Tamaño</label></th>
												<th class="text-center" style="width:9%"><label>Prefijo</label></th>
												<th class="text-center" style="width:9%"><label>Sufijo</label></th>
											</tr>
										</thead>
										<tbody>
	
											<tr>
												<td>C&oacute;digo de Barras</td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idCodigoBarraF0" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idCodigoBarraC0"
													style="width: 50%" /></td>
												<td></td>
												<td class="text-center"><div  class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxCodigoBarraA0" value="" aria-label="..." />
													</div></td>
												<td class="text-center"></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idCodigoBarraT0"
													style="width: 50%" /></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
											</tr>
											<tr>
												<td>N&deg; C&oacute;digo de Barras</td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNumeroCodigoBarraF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNumeroCodigoBarraC"
													style="width: 50%" /></td>
												<td></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxNumeroCodigoBarraA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxNumeroCodigoBarraN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNumeroCodigoBarraT"
													style="width: 50%" /></td>
												<td class="text-center"></td>
												<td class="text-center"></td>
											</tr>
											<tr>
												<td>Tipo de Atenci&oacute;n</td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="iTipoAtencionF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="iTipoAtencionC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="iTipoAtencionL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxTipoAtencionA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxTipoAtencionN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idTipoAtencionT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idTipoAtencionPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idTipoAtencionSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Procedencia</td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idProcedenciaF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idProcedenciaC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idProcedenciaL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxProcedenciaA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxProcedenciaN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idProcedenciaT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idProcedenciaPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idProcedenciaSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Servicio</td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idServicioF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idServicioC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idServicioL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxServicioA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxServicioN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idServicioT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idServicioPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idServicioSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Nombre de Paciente</td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNombrePacienteF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNombrePacienteC"
													style="width: 50%" /></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idNombrePacienteL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxNombrePacienteA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxNombrePacienteN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idNombrePacienteT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idNombrePacientePrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idNombrePacienteSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>RUN</td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idRunF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idRunC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta"  type="number" id="idRunL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxRunA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxRunN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idRunT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idRunSufijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idRunPrefijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Sexo</td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idSexoF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idSexoC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idSexoL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxSexoA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxSexoN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idSexoT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idSexoPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idSexoSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Edad</td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idEdadF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idEdadC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idEdadL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxEdadA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input" type="checkbox" id="checkboxEdadN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idEdadT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idEdadPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idEdadSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Tipo de Muestra</td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idTipoMuestraF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idTipoMuestraC"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idTipoMuestraL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxTipoMuestraA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxTipoMuestraN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="number" id="idTipoMuestraT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idTipoMuestraPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idTipoMuestraSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Contenedor</td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idEnvaseF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idEnvaseC"
													style="width: 50%" /></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idEnvaseL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxEnvaseA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxEnvaseN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idEnvaseT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idEnvasePrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idEnvaseSufijo"
													style="width: 80%" /></td>
											</tr>
											<tr>
												<td>Fecha de Registro</td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idFechaRegistroF" min="0"
													style="width: 50%" /></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idFechaRegistroC"
													style="width: 50%" /></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idFechaRegistroL"
													style="width: 50%" /></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxFechaRegistroA" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><div class="form-check">
														 <input class="form-check-input " type="checkbox" id="checkboxFechaRegistroN" value="" aria-label="..." />
													</div></td>
												<td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idFechaRegistroT"
													style="width: 50%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idFechaRegistroPrefijo"
													style="width: 80%" /></td>
												<td class="text-center"><input class="limpiarEtiqueta" type="text" id="idFechaRegistroSufijo"
													style="width: 80%" /></td>
											</tr>
                      <tr>
                        <td>Ex&aacute;menes</td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="number" id="idExamenesF" min="0"
                          style="width: 50%" /></td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="number" id="idExamenesC"
                          style="width: 50%" /></td>
                        <td class="text-center"></td>
                        <td class="text-center"><div class="form-check">
                             <input class="form-check-input " type="checkbox" id="checkboxExamenesA" value="" aria-label="..." />
                          </div></td>
                        <td class="text-center"><div class="form-check">
                             <input class="form-check-input " type="checkbox" id="checkboxExamenesN" value="" aria-label="..." />
                          </div></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                      </tr>
                      <tr>
                        <td>Nro. Orden</td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="number" id="idNOrdenF" min="0"
                          style="width: 50%" /></td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="number" id="idNOrdenC"
                          style="width: 50%" /></td>
                        <td class="text-center"></td>
                        <td class="text-center"><div class="form-check">
                             <input class="form-check-input " type="checkbox" id="checkboxNOrdenA" value="" aria-label="..." />
                          </div></td>
                        <td class="text-center"><div class="form-check">
                             <input class="form-check-input " type="checkbox" id="checkboxNOrdenN" value="" aria-label="..." />
                          </div></td>
                        <td class="text-center"><input  class="limpiarEtiqueta" type="number" id="idNOrdenT"
                          style="width: 50%" /></td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="text" id="idNOrdenPrefijo"
                          style="width: 80%" /></td>
                        <td class="text-center"><input class="limpiarEtiqueta" type="text" id="idNOrdenSufijo"
                          style="width: 80%" /></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                      </tr>
										</tbody>
	
									</table>
								</div>
								<div class="col-12">
									<div class="row">
										<div class="col-10">
											<button id="ce_btnVisualizarEtiqueta"
												Class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
												<i class="la la-search"></i>Visualizar
											</button>
											<button id="ce_btnImprimirEtiqueta"
												Class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
												<i class="fas fa-print"></i>Imprimir
											</button>
										</div>
										<div class="col-2">
											<button id="ce_btnGuardarEtiqueta"
												Class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 ">
												<i class="far fa-save"></i>Guardar
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

	<!--  <div class="container container-main-content container-edicion-ordenes"> -->
	<!-- <div class="container "> 
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0" >
				<div  class="card">
					<div class="card-header" >
						<div class="card-title row col-12 d-flex justify-content-between">
							<h3 	data-toggle="collapse" data-target="#panelBusquedaOrden"
								aria-expanded="true" aria-controls="panelBusquedaOrden">Configuración
								de Etiquetas:</h3>
								
					
                            
							
						</div>
					</div>
				</div> -->






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
<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/css/bootstrap-select.min.css"
  integrity="sha512-ARJR74swou2y0Q2V9k0GbzQ/5vJ2RBSoCWokg4zkfM29Fb3vZEQyv0iWBMW/yvKgyHSR/7D64pFMmU8nYmbRkg=="
  crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script
  src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>

<script
  src="<%=request.getContextPath()%>/resources/js/common/dual-data.js"></script>

					<script
						src="<%=request.getContextPath()%>/resources/js/ConfiguracionEtiquetas.js"></script>

</body>
</html>

