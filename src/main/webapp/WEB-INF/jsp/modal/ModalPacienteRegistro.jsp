
				<!--  ********************************************************** Registro de paciente *****************************************************************  -->
				<div id="panelRegistroPaciente ">
					<div class="card-body no-gutters">
						<form id="formRegistroPaciente" method="post">
							<input type="hidden" id="ipEquipo" name="ipEquipo"> <input
								id="txtIdPaciente" name="dpIdpaciente" type="hidden" value="0" />
							<input type="hidden" id="dpIdmadre" name="dpIdmadre">


							<div class="row" id="divForm">
								<!-- inicio Formulario registro -->
								<!-- Fin Formulario registro -->

								<!-- Inicio Panel Tabulados -->
								<div class="col-10">
									<div class="tab-content " id="myTabContent">

										<!-- Inicio Panel Datos b�sicos -->
										<div class="row tab-pane fade show active"
											id="tab_pane_datos_basicos" role="tabpanel"
											aria-labelledby="tab_pane_datos_basicos"">
											<div class="col">
												<div class="row" id="filaDocNombre">
													<div id="divTipoDocumento" class=" col-3">
														<label for="selectTipoDocumento" class="">Tipo
															documento :</label> <select id="selectTipoDocumento"
															class="form-control selectpicker "
															name="ldvTiposdocumentos">
															<c:forEach var="documento"
																items="${listaTiposDocumentos}">
																<option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div id="divTxtRut" class=" col-2">
														<label>RUN: *</label> <input id="txtRut" name="dpRun"
															type="text" class="form-control " placeholder=""
															minlength="11" maxlength="12" autocomplete="off"
															onkeyup="this.value = this.value.toUpperCase();" />
														<div class="invalid-feedback">RUN inv&aacute;lido</div>
													</div>
													<div id="divTxtPasaporte" class=" col-2 ocultar"
														style="display: none;">
														<label>Pasaporte: *</label> <input id="txtPasaporte"
															name="dpNrodocumento" type="text" class="form-control"
															autocomplete="off" placeholder="" />
													</div>
													<div id="divTxtNombre" class=" col-3">
														<label>Nombres: *<span class="label"
															data-toggle="popover" data-html="true"
															data-content="Nombre legal, debe ser id�ntico al carn� de identidad. Para variaciones usar campo nombre social."><i
																class="fa la-info-circle"></i></span></label> <input id="txtNombre"
															name="dpNombres" type="text"
															class="form-control  disabledForm" autocomplete="off"
															placeholder="" />
													</div>
													<div id="divPrimerApellido" class=" col-2 ">
														<label>Primer Apellido: *</label> <input
															id="txtPrimerApellido" name="dpPrimerapellido"
															type="text" class="form-control disabledForm"
															autocomplete="off" placeholder="" />
													</div>
													<div id="divSegundoApellido" class=" col-2">
														<label>Segundo Apellido: </label> <input
															id="txtSegundoApellido" name="dpSegundoapellido"
															type="text" class="form-control disabledForm"
															autocomplete="off" placeholder="" />
													</div>

												</div>

												<div class="row" id="filaSexoNacimiento">
													<div class=" col-2 ">
														<label for="selectSexo" class="">Sexo: *<span
															class="label" data-toggle="popover" data-html="true"
															data-content="Sexo biol�gico o sexo al nacer. Requerido para an�lisis objetivos de datos bioqu�micos u hormonales."><i
																class="fa la-info-circle"></i></span>
														</label> <select class="form-control selectpicker disabledForm"
															id="selectSexo" name="ldvSexo">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="sexo" items="${listaSexo}">
																<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class=" col-2 ">
														<label for="txtFechaNacimiento">Fec
															Nacimiento:&nbsp*</label> <input id="txtFechaNacimiento"
															name="dpFnacimiento" class="form-control disabledForm "
															data-provide="datepicker" data-date-language="es" data-autoclose="true"
															autocomplete="off" placeholder="" />
													</div>
													<div class=" col-2 ">
														<label for="txtEdad">Edad (a-m-d):</label> <input
															id="txtEdad" name="" type="text" class="form-control "
															placeholder="" disabled />
													</div>

													<div class=" col-2  " hidden="true">
														<label for="txtAnio">Edad :</label>

													</div>
													<div class=" col-1 " hidden="true">
														<label for="txtMeses">Meses :</label> <input id="txtMeses"
															name="" type="text" class="form-control  " placeholder=""
															disabled />
													</div>
													<div class=" col-1 " hidden="true">
														<label for="txtDias">D�as :</label> <input id="txtDias"
															name="" type="text" class="form-control  " placeholder=""
															disabled />
													</div>
													<div class=" col-2 ">
														<label>Tel�fono: </label> <input id="txtTelefono"
															name="dpTelefono" type="text"
															class="form-control phone_cl "
															placeholder="" />
													</div>
													<div class=" col-4 ">
														<label>Email: </label> <input id="txtEmail" name="dpEmail"
															type="text" class="form-control email_cl" placeholder="" />
													</div>
												</div>

												<div class="row" id="filaDireccion">

													<div class="col-3 ">
														<label class="">Region: </label> <select id="selectRegion"
															name="ldvrIdregion"
															class="form-control selectpicker disabledForm"
															data-live-search="true">
															<option value="" disabled selected>Seleccionar</option>
															<c:forEach var="region" items="${listaRegiones}">
																<option value="${region.ldvrIdregion}">${region.ldvrDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-3 ">
														<label class="">Comuna : </label> <select
															id="selectComuna" name="ldvcIdcomuna"
															class="form-control selectpicker disabledForm"
															data-live-search="true"
															data-none-selected-text="Seleccionar">
															<option value="" disabled selected>Seleccionar</option>
														</select>
													</div>
													<div class="col-4 ">
														<label>Direcci�n: </label> <input id="txtDireccion"
															name="dpDireccion" type="text"
															class="form-control disabledForm" autocomplete="off"
															placeholder="" />
													</div>
													<div class="col-2 ">
														<label>C�digo postal: </label> <input id="txtCodigoPostal"
															name="dpDireccodpostal" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>

												</div>

												<hr />
												<p>Datos contacto</p>
												<div class="row border-top" id="filaDatosContacto">
													<div class=" col-3 align-items-center">
														<label>Nombre:</label> <input id="txtNombreContacto"
															name="dcNombre" type="text" class="form-control "
															placeholder="" />
													</div>
													<div class=" col-3 ">
														<label class="">Relaci&oacute;n: </label> <select
															id="selectPacienteRelacion"
															name="LdvContactospacientesrelacion.ldvcprIdcontactopacienterel"
															class="form-control selectpicker ">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="pacRelacion"
																items="${listaPacienteRelacion}">
																<option
																	value="${pacRelacion.ldvcprIdcontactopacienterel}">${pacRelacion.ldvcprDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class=" col-3 ">
														<label>Tel&eacute;fono: </label> <input
															id="txtTelefonoContacto" name="dcTelefono" type="text"
															class="form-control phone_cl"
															placeholder="[9|2]-dddd-dddd" />
													</div>
													<div class="col-3 ">
														<label class="">Sexo: </label> <select
															id="selectSexoContacto" name="dcIdsexo"
															class="form-control selectpicker "
															data-header="Seleccionar">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="sexo" items="${listaSexo}">
																<option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
															</c:forEach>
														</select>
													</div>
												</div>

											</div>
										</div>
										<!-- Fin Panel Datos b�sicos -->
										<!-- Inicio Panel Datos Extras -->
										<div class="row tab-pane fade" id="tab_pane_datos_extras"
											role="tabpanel" aria-labelledby="tab_pane_datos_extras">
											<div class="col">
												<div class="row" id="filaCheckBox">
													<div class="col">
														<div class="checkbox-inline">
															<label class="checkbox checkbox-primary "> <input
																id="checkboxVIP" name="dpEsvip" type="checkbox"
																class="disabledForm" /> Paciente VIP <span></span>
															</label> <label class="checkbox checkbox-primary"> <input
																id="checkboxAfro" name="dpEsafroamericano"
																type="checkbox" class="disabledForm" /> Paciente
																Afroamericano <span></span>
															</label><span class="label" data-toggle="popover"
																data-html="true"
																data-content="Dato requerido para el an�lisis de la funci�n renal mediante la f�rmula MDRD."><i
																class="fa la-info-circle"></i></span> <label
																class="checkbox checkbox-primary "> <input
																id="checkBoxExitus" name="exitus" type="checkbox"
																disabled /><span></span> Fallecimiento
															</label>
														</div>
													</div>
												</div>
												<div class="row" id="filaNacEstCivNomSocial">
													<div class="col-4">
														<label class="">Pais Origen: </label> <select
															id="selectPais" name="ldvpIdpais"
															class="form-control selectpicker disabledForm"
															data-live-search="true">
															<option value="" disabled selected>Seleccionar</option>
															<c:forEach var="pais" items="${listaPaises}">
																<option value="${pais.ldvpIdpais}">${pais.ldvpDescripcion}</option>
															</c:forEach>
														</select>
													</div>

													<div class="col-4">
														<label class="">Estado Civil: </label> <select
															id="selectEstadoCivil" name="ldvecIdestadocivil"
															class="form-control selectpicker disabledForm">
															<option value="" disabled selected>Seleccionar</option>
															<c:forEach var="estadoCivil"
																items="${listaEstadosCiviles}">
																<option value="${estadoCivil.ldvecIdestadocivil}">${estadoCivil.ldvecDescripcion}</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-4">
														<label>Nombre Social: </label> <input id="txtNombreSocial"
															name="dpNombresocial" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>


												</div>
												<div class="row" id="filaObservaciones">
													<div class="col">
														<label>Observaciones: </label> <input
															id="txtObservaciones" name="dpObservacion" type="text"
															class="form-control disabledForm " placeholder="" />
													</div>
												</div>
											</div>

										</div>
										<!-- Fin Panel Datos Extras -->
										<!-- Inicio Panel Patolog�as -->
										<div class="row tab-pane fade" id="tab_pane_patologias"
											role="tabpanel" aria-labelledby="tab_pane_patologias">
											<div class="col">
												<div class="row" id="filaPAtologias">
													<div class="col-4">
														<label class="">Patolog&iacute;a: </label> <select
															id="selectPatologias" class="form-control selectpicker ">
															<option value="N" disabled selected>Seleccionar</option>
															<c:forEach var="patologia" items="${listaPatologias}">
																<option value="${patologia.ldvpatIdpatologia}">${patologia.ldvpatDescripcion}</option>
															</c:forEach>
														</select>
														<button id="btnPatologia" type="button"
															class="btn btn-light-primary font-weight-bold">Agregar
															Patolog&iacute;a</button>
													</div>
													<div class="col-8 ">
														<table id='tablaPatologia'>
															<thead>
																<tr>
																	<th>Patolog&iacute;a</th>
																	<th>Observacion</th>
																	<th>Eliminar</th>
																</tr>
															</thead>
															<tbody id="tbodyPatologias">

															</tbody>
														</table>
													</div>
												</div>
											</div>

										</div>
										<!-- Fin Panel Datos Patolog�as -->
										<!--  Inicio panel datos madre -->
										<div class="row tab-pane fade" id="tab_pane_madre"
											role="tabpanel" aria-labelledby="tab_pane_madre">
											<div class="col">
												<div class="row" id="filaDatoMadre">
													<div class="col-6">
														<label id="lblRunMadre">RUN Madre: </label> <input
															id="txtRutMadre" name="rutMadre" type="text"
															class="form-control " placeholder="" minlength="11"
															maxlength="12"
															onkeyup="this.value = this.value.toUpperCase();" />
														<div id="divFeedbackRutMadre" class="invalid-feedback">RUN
															inv&aacute;lido</div>
													</div>
													<div class="checkbox-inline col-3 ">
														<label class="checkbox checkbox-primary "> <input
															id="checkboxPartoMultiple" name="esPartoMultiple"
															type="checkbox" class="" /> Parto M�ltiple <span></span>
														</label>
													</div>
													<div class="col-3">
														<label>N� Parto M&uacute;ltiple: </label> <input
															id="txtNroPartoMultiple" name="dpRnnumero" type="number"
															min="1" class="form-control " placeholder="" />
													</div>

												</div>
											</div>
										</div>
										<!--  Fin panel datos madre -->
									</div>
								</div>
								<!-- Fin Formulario registro -->



								<!-- Inicio Tabuladores -->
								<div class="col-2">
									<ul class="nav  flex-column nav-pills">
										<li class="nav-item"><a id="anchorRegistro"
											class="nav-link active" data-toggle="tab"
											href="#tab_pane_datos_basicos"> <span class="nav-icon"><i
													class="flaticon2-paper"></i></span> <span class="nav-text">Datos&nbsp;
													B�sicos</span>
										</a></li>
										<li id="liDatosMadres" class="nav-item ocultar"><a
											class="nav-link" data-toggle="tab" href="#tab_pane_madre">
												<span class="nav-icon"><i class="fas fa-female"></i></span>
												<span class="nav-text">Datos&nbsp;Madre</span> <span
												id="spanDatosMadre"
												class="ocultar label label-danger font-weight-bold label-inline"><i
													class="fa la-exclamation-circle text-white"></i></span>
										</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#tab_pane_datos_extras"> <span
												class="nav-icon"><i class="flaticon2-copy"></i></span> <span
												class="nav-text">Datos Extras</span>
										</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#tab_pane_patologias"> <span
												class="nav-icon"><i class="fas fa-book-medical"></i></span>
												<span class="nav-text">Patolog&iacute;as</span>
										</a></li>
									</ul>

								</div>
								<!-- Fin Tabuladores -->
								<button id="btnAgregarPaciente" name="insert" type="submit"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 ">
									<i class="far fa-save"></i>Guardar
								</button>
								<button id="btnUpdatePaciente" name="update" type="button"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 ocultar"
									value="">Confirmar Datos</button>
								<a id="btnConfirmarDatosPac"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2 float-right ocultar"><i
									class="la la-search"></i>Confirmar</a>
								<button id="btnCancelEdit" name="cancel" type="button"
									class="btn btn-light-primary btn-lg font-weight-bold mt-2 mr-2"
									value="">Cancelar</button>
							</div>
						</form>

					</div>
				</div>

