<div id="" class="bo_container">
	<div id="izq">

		<form id="bo_formFiltro">
			<fieldset>
				<div id="bo_div_nOrdenIni" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_nOrdenIni">N. Orden Inicio</label> <input
							type="text" id="bo_nOrdenIni" tittle="Nro de orden inicial" /></td>
					</tr>
				</div>
				<div id="bo_div_nOrdenFin" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_nOrdenFin">N. Orden Fin</label> <input
							type="text" id="bo_nOrdenFin" /></td>
					</tr>
				</div>
				<div id="bo_div_fIni" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_fIni">Fecha Inicio</label> <input
							type="text" id="bo_fIni" /></td>
					</tr>
				</div>
				<div id="bo_div_fFin" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_fFin">Fecha Fin</label> <input type="text"
							id="bo_fFin" /></td>
					</tr>
				</div>
				<div id="bo_div_nombre" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_nombre">Nombre</label> <input type="text"
							id="bo_nombre" /></td>
					</tr>
				</div>
				<div id="bo_div_apellido" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_apellido">Apellido</label> <input
							type="text" id="bo_apellido" /></td>
					</tr>
				</div>
				<div id="bo_div_tipoDocumento" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_tipoDocumento">Tipo Documento</label> <select
							id="bo_tipoDocumento"><option selected="true" disabled="disabled" value="" hidden>Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_nroDocumento" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_nroDocumento">N. Documento</label> <input
							type="text" id="bo_nroDocumento" /></td>
					</tr>
				</div>
				<div id="bo_div_tipoAtencion" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_tipoAtencion">Tipo Atenci&oacute;n</label>
							<select id="bo_tipoAtencion">
								<option value="">Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_localizacion" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_localizacion">Localizaci&oacute;n</label>
							<select id="bo_localizacion">
								<option value="">Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_procedencia" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_procedencia">Procedencia</label> <select
							id="bo_procedencia">
								<option value="">Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_servicio" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_servicio">Servicio</label> <select
							id="bo_servicio">
								<option value="">Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_seccion" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_seccion">Secci&oacute;n</label> <select
							id="bo_seccion">
								<option value="">Seleccionar</option>
						</select></td>
					</tr>
				</div>
				<div id="bo_div_examen" hidden="true" class="bo_row">
					<tr>
						<td><label for="bo_examen">Examen</label> <input type="text"
							id="bo_examen" /></td>
					</tr>
				</div>
				<div id="" class="bo_row">
					<tr>
						<td>
							<button id="bo_btnBuscarOrden">Buscar</button>
						</td>
					</tr>
				</div>
			</fieldset>
		</form>
	</div>
	<div id="der">
		<table id="bo_t_ordenes" style="font-size: 10px;">
			<thead id="bo_th_ordenes">
				<tr>
					<!-- 
           <th>Selecci&oacute;n</th>
 -->
					<th>Selecci&oacute;n</th>
					<th>N. Orden</th>
					<th>Fecha&nbsp;Orden</th>
					<th>Observaci&oacute; Orden</th>
					<th>Id Paciente</th>
					<th>T. Documento</th>
					<th>N. Documento</th>
					<th>Nombre</th>
					<th>A. Paterno</th>
					<th>A. Materno</th>
					<th>F.Nacimiento</th>
					<th>email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<th>Sexo</th>
					<th>Observaci�n Paciente</th>
					<th>T. Atenci�n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<th>Procedencia&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<th>Convenio</th> .
					<th>Previsi�n</th>
					<th>Localizaci�n</th>
					<th>Diagn�stico</th>
					<th>Centro de Salud</th>
					<th>Prioridad</th>
					<th>Servicio</th>
				</tr>
			</thead>

		</table>

	</div>

</div>
