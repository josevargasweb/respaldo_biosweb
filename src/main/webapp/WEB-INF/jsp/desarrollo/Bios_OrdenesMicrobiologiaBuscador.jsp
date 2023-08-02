
<h3 class="col-12 pl-0 pr-0 text-center">B&uacute;squeda Detallada</h3>

<table class="col-12 mt-3">
	<form id="bo_formFiltro">
		<div class="form-row">
			<div id="bo_div_nOrdenIni" hidden="true" class="bo_row form-group col-md-6 flex-column">
			  <label for="bo_nOrdenIni">N. Orden Inicio</label> 
			  <input type="text" class="form-control" id="bo_nOrdenIni" tittle="Nro de orden inicial" />
			</div>
			<div id="bo_div_fIni" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_fIni">Fecha Inicio</label> 
				<input type="text" id="bo_fIni" class="form-control"/>
			</div>
			
		</div>
		<div class="form-row">
			<div  id="bo_div_nOrdenFin" hidden="true"  class="bo_row form-group col-md-6 flex-column" >
				<label for="bo_nOrdenFin">N. Orden Fin</label> 
				<input type="text" class="form-control" id="bo_nOrdenFin" />
			</div>
			<div id="bo_div_fFin" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_fFin">Fecha Fin</label> 
				<input type="text" id="bo_fFin" class="form-control"/>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_nombre" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_nombre">Nombre</label> 
				<input type="text" id="bo_nombre" class="form-control"/>
			</div>
			<div id="bo_div_fechaIngreso" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_fechaIngreso">Fecha Ingreso</label> 
				<input type="text" id="bo_fechaIngreso" class="form-control"/>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_apellido" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_apellido">Apellido</label> 
				<input type="text" id="bo_apellido" class="form-control"/>
			</div>
			<div id="bo_div_tipoDocumento" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_tipoDocumento">Tipo Documento</label> 
				<select
					id="bo_tipoDocumento" class="form-control">
					<option selected="true" disabled="disabled" value="" hidden>Seleccionar</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_seccion" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_seccion">Secci&oacute;n</label> 
				<select id="bo_seccion" class="form-control">
					<option selected="true" disabled="disabled" value="">Seleccionar</option>
				</select>
			</div>
			<div id="bo_div_nroDocumento" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_nroDocumento">N. Documento</label> 
				<input type="text" id="bo_nroDocumento" class="form-control" minlength="1" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"/>
			</div>
		</div>
	
		<div class="form-row">
			<div id="bo_div_examen" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_examen">Examen</label> 
				<select id="bo_examen" class="form-control">
					<option selected="true" disabled="disabled" value="">Seleccionar</option>
				</select>
			</div>
			<div id="bo_div_servicio" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_servicio">Servicio</label> 
				<select id="bo_servicio" class="form-control">
					<option selected="true" disabled="disabled" value="">Seleccionar</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_procedencia" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_procedencia">Procedencia</label> 
				<select id="bo_procedencia"  class="form-control">
					<option  selected="true" disabled="disabled"value="">Seleccionar</option>
				</select>
			</div>
			<div id="bo_div_tipoAtencion" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_procedencia">Tipo Atenci&oacute;n</label> 
				<select id="bo_tipoAtencion"  class="form-control">
					<option  selected="true" disabled="disabled"value="">Seleccionar</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_localizacion" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_localizacion">Localizaci&oacute;n</label> 
				<select id="bo_localizacion"  class="form-control">
					<option  selected="true" disabled="disabled"value="">Seleccionar</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div id="bo_div_nroPasaporte" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_nroPasaporte">N. Pasaporte</label> 
				<input type="text" id="bo_nroPasaporte"  class="form-control"/>
			</div>
			<div id="bo_div_nroRunMadre" hidden="true" class="bo_row form-group col-md-6 flex-column">
				<label for="bo_nroRunMadre">RUN Madre</label> 
				<input type="text" id="bo_nroRunMadre"  class="form-control"/>
			</div>
		</div>
		<div class="form-row">
                    <div class="col-6">
			<button id="bo_btnBuscarOrden"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2 "><i
							class="la la-search"></i>Buscar</button>
                    </div>
                    <div class="col-6 d-flex justify-content-end">
			<button type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-5 mr-2"
							data-dismiss="modal">Cerrar</button>
                    </div>
		</div>
	</form>

</table>