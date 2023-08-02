<script
src="<%=request.getContextPath()%>/resources/datetimepicker/moment.min.js"></script>
<div class="row">
	<div class="col-12 pl-0 pr-0">
		<div class="d-flex flex-column text-center">
			<div class="col-12 d-flex justify-content-center">
				<div class="col-12">
					<input type="hidden" id="idTestLineaTiempo"> <input
						type="hidden" id="idExamenLineaTiempo">

					<h5 class="mb-0">
						Examen : <label id="nombreExamenLineaTiempo" class="mb-0"></label>
					</h5>
					<h5 class="mb-0">
						Test: <label id="nombreTestLineaTiempo" class="mb-0"></label>
					</h5>


				</div>
			</div>
			<div class="col-xl-12 col-md-11 d-flex justify-content-center mt-3">
				<div class="col-12 mt-3">
					<div class="table-container" style="">
						<table class="table table-sm table-bordered">
							<thead class="trHeader">
								<tr>
									<th scope="col"></th>
									<th scope="col">Registro de Orden</th>
									<th scope="col">Toma de Muestra</th>
									<th scope="col">Recepcion de Muestra</th>
									<th scope="col">Registro de Resultado</th>
									<th scope="col">Firma de Resultado</th>
									<th scope="col">Autorizacion de Examen</th>
									<th scope="col">Impresi&oacute;n de Informe</th>
								</tr>
							</thead>
							<tbody id="tablaLineaTiempo">
								<tr id="filaLineaTiempoFechas">
								</tr>

								<tr id="filaLineaTiempoUsuarios">
								</tr>

								<tr id="filatiempoAccion"></tr>

								<tr id="filatiempoAcumulado"></tr>

							</tbody>
						</table>


					</div>
				</div>
			</div>

		</div>
		<div class="col-xl-12  d-flex  ">
			<div class="col-5">
				<div class="form-checkLineaTiempo">
					<input class="form-check-input" type="checkbox" value="1"
						id="checkedOrdenLineaTiempo" checked> <label class="form-check-label"	for="flexCheckDefault"> Incluir Registro de Orden </label>
				</div>
				<div class="form-checkLineaTiempo">
					<input class="form-check-input" type="checkbox" value="2"
						id="checkedInformeLineaTiempo" checked> <label class="form-check-label" for="flexCheckChecked"> Incluir Impresi&oacute;n de Informe </label>
				</div>
			</div>
			<div class="col-5">
				<label>
					<span class="">Tiempo TOTAL</span>  
					<h4 id="tiempoTotalLineaTiempo"></h4>
				</label>
			</div>

		</div>
	</div>
</div>