<div id="" class="bo_container">
	<div id="izq">
			<div class="form-row">
				<div class="col-6">
					<div class="card card-2">
						<div class="card-body">
							<div id="bo_div_nOrdenIni" hidden="true" class="bo_row form-group col-md-12 pl-0">
								<label for="bo_nOrdenIni" class="mt-1">N. Orden Inicio</label>
								<input type="text" id="bo_nOrdenIni"title="Nro de orden inicial" class="form-control bo_input" />
							</div>
						
							<div id="bo_div_nOrdenFin" hidden="true" class=" bo_row form-group col-md-12 pl-0">
								<label for="bo_nOrdenFin" class="mt-1">N. Orden Fin</label>
								<input type="text" id="bo_nOrdenFin" class="form-control bo_input" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-6">
					<div class="card card-2">
						<div class="card-body">
							<div id="bo_div_fIni" hidden="true" class=" bo_row form-group col-md-12 pl-0">
								<label for="bo_fIni" class="mt-1">Fecha Inicio</label>
								<input type="text" id="bo_fIni" class="form-control bo_input" autocomplete="off"/>
							</div>
							<div id="bo_div_fFin" hidden="true" class=" bo_row form-group col-md-12 pl-0">
								<label for="bo_fFin" class="mt-1">Fecha Fin</label>
								<input type="text" id="bo_fFin" class="form-control bo_input" autocomplete="off"/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-row">	
				<div class="col-6 pb-3">
					<div class="card card-2 mt-3 h-100">
						<div class="card-body">
							<div class="form-row">
								<div class="col-12">
									<div id="bo_div_nombre" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_nombre" class="mt-1">Nombre</label>
										<input type="text" id="bo_nombre" class="form-control valizq bo_input" />
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_apellido" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_apellido" class="mt-1">Apellido</label>
										<input type="text" id="bo_apellido" class="form-control valizq bo_input" />
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_tipoDocumento" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_tipoDocumento"
											class="mt-1">Tipo Documento</label> 
											<select id="bo_tipoDocumento"
											class="form-control selectpicker bo_select">
										</select>
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_nroDocumento" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_nroDocumento"
										class="mt-1">N. Documento</label> <input type="text"
										id="bo_nroDocumento"  class="form-control valizq bo_input" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-6">
					<div class="card card-2 mt-3">
						<div class="card-body">
							<div class="form-row">
								<div class="col-12">
									<div id="bo_div_tipoAtencion" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_tipoAtencion"
										class="mt-1">Tipo Atenci&oacute;n</label> <select data-live-search="true"
										id="bo_tipoAtencion" class="form-control selectpicker bo_select">
										</select>
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_procedencia" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_procedencia"
											class="mt-1">Procedencia</label> <select id="bo_procedencia" data-live-search="true"
											class="form-control selectpicker bo_select">
											</select>
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_servicio" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_servicio" class="mt-1">Servicio</label>
										<select data-live-search="true" id="bo_servicio" class="form-control selectpicker bo_select">
										</select>
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_seccion" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_seccion" class="mt-1">Secci&oacute;n</label>
										<select data-live-search="true" id="bo_seccion" class="form-control selectpicker bo_select">
										</select>
									</div>
								</div>
								<div class="col-12">
									<div id="bo_div_examen" hidden="true" class="bo_row form-group col-md-12 pl-0">
										<label for="bo_examen" class="mt-1">Examen</label>
										<select data-live-search="true" id="bo_examen" class="form-control selectpicker bo_select">
											<option selected value=""></option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="bo_checkbox_filtros" class="card card-2 mt-3">
				<div class="card-body">
					<div class="from-row d-flex">
						<div class="col-6">
							<label class="text-capt2">Pendientes</label>
							<input type="checkbox" id="estadoPendiente2" class="filtros-estados">
						</div>
						<div class="col-6">
							<label class="text-capt-firmar">Por firmar</label>
							<input type="checkbox" id="estadoFirmar" class="filtros-estados">
						</div>
					</div>
				</div>
			</div>
			<div class="form-row mt-3">
				<div id="" class="bo_row form-group col-md-12 pl-0 d-flex justify-content-end">
					<button type="button" id="bo_btnCerrarModal"
						class="btn btn-blue-primary btn-lg font-weight-bold mr-2 mt-1"
						data-dismiss="modal">Cerrar</button>
					<button id="bo_btnBuscarOrden"
						class="btn btn-blue-primary btn-lg font-weight-bold mr-2 mt-1">Buscar</button>
				</div>
			</div>
	</div>
</div>