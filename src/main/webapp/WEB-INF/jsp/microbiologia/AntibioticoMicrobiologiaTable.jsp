<div class="row" id="contenedorExamenes">
	<div class="col-5">
		<div class="row pt-3">
			<div class="col pr-0">
				<input type="text" id="idSearcher" inputmode="search"
					tabindex="5"  class="w-100"/>
			</div>
		</div>
		<div class="row">
			<div class="col pr-0">
				<select id="idDualDataVisualSource" size="5" tabindex="-1" class="w-100">
					<option value="-1" disabled style="display: none;">Seleccionar</option>
				</select> 
			</div>
		</div>
	</div>

	<div class="col-2 d-flex align-items-center pr-0">
		<div id="dualDataButtonBar" class="mx-auto">
			<div class="col-12 pl-0 pr-0">
				<button id="clickAddBtn" type="button"
					class="btn btn-blue-primary w-100">
					Agregar <i class="fas fa-arrow-right" tabindex="-1"></i>
				</button>
			</div>
			<div class="col-12 pl-0 pr-0 mt-3">
				<button id="clickDelBtn" type="button"
					class="btn btn-blue-primary w-100">
					<i class="fas fa-arrow-left" tabindex="-1"></i> Quitar
				</button>
			</div>
		</div>
	</div>
	<div class="col-5 d-flex flex-column justify-content-between">
		<table id="idDataTarget" tabindex="-1">
			<thead>
				<tr>
					<th>Antibiotico</th>
				</tr>
			</thead>
		</table>
	</div>

</div>