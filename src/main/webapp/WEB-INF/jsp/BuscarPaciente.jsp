				<div id="panelBusquedaPaciente" class="collapse">
					<div class="row">
						<!-- FORMULARIO FILTRO -->
						<div class="col">
							<div id='divSelTipoDocumento' class=" row  ">
								<label class="col-form-label text-left col-4 ">T de
									documento</label>
								<div class="col-8">
									<select id="selectTipoDocumentoFiltro"
										class="form-control">
										<option value="6">TODOS</option>
									</select>
								</div>
							</div>
							<div id='divTxtRutFiltro' class=" row  ">
								<label id="lblRutFiltro" class="col-4 col-form-label">RUN
								</label> <label id="lblRutFiltroMadre" class="col-4 col-form-label">RUN
									Madre </label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroRut"
										type="text" minlength="11" maxlength="12"
										onkeyup="this.value = this.value.toUpperCase();" />
								</div>
							</div>
							<div id='divTxtPasaporteFiltro' class="ocultar    row">
								<label class="col-4 col-form-label">Pasaporte </label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtPasaporteFiltro" type="text" />
								</div>
							</div>
							<div id='divTxtNNFiltro' class=" row ocultar  ">
								<label class="col-4 col-form-label">Fecha Ingreso </label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroNN"
										type="text" data-provide="datepicker" data-date-language="es" />
								</div>
							</div>
							<div class=" row  ">
								<label class="col-4 col-form-label">Nombre</label>
								<div class="col-8">
									<input class="form-control" placeholder="" id="txtFiltroNombre"
										type="text" />
								</div>
							</div>
							<div class=" row  ">
								<label class="col-4 col-form-label">Primer Apellido</label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtFiltroApellido" type="text" />
								</div>
							</div>
							<div class=" row">
								<label class="col-4 col-form-label">Segundo Apellido</label>
								<div class="col-8">
									<input class="form-control" placeholder=""
										id="txtFiltroSegundoApellido" type="text" />
								</div>
							</div>

							<a id="btnBuscarFiltro" href="#"
								class="btn btn-light-primary font-weight-bold"><i
								class="la la-search"></i>Buscar</a>
						</div>
						<!-- FIN FORMULARIO FILTRO -->
						<!-- TABLA FILTRO -->
						<div class="col scroll scroll-pull" data-scroll="true"
							data-suppress-scroll-x="false" data-swipe-easing="false"
							style="height: 270px;">
							<table id="tableFiltro" class="table table-hover">
								<thead>
									<tr id="trHeader">
										<th scope="col">#</th>
										<th id="thIdentificador" scope="col">Identificador</th>
										<th id="thNombre" scope="col">Nombre</th>
									</tr>
								</thead>
								<tbody id="tbodyFiltro">

								</tbody>
							</table>
							<label id="lblErrorFiltro" class="textError text-red ocultar">No
								se encontraron pacientes con los datos provistos.</label>
						</div>
						<!-- FIN TABLA FILTRO -->
					</div>
				</div>
