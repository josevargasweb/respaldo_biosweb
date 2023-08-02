<div class="row">
	<div class="col-6">
		<div class="d-flex flex-column text-center">
			<div class="col-12 d-flex justify-content-center">
				<div class="col-12">

				<input type="hidden" id="idTest">
				<input type="hidden" id="idPaciente">

					<h5 class="mb-0">Paciente: <label id="pacienteHistorico" class="mb-0"></label></h5>
					<h5 class="mb-0">Test: <label id="testHistorico" class="mb-0"></label></h5>
					<div id="graficaHistorico"  style="height: 200px;"></div>
					  <div id="container"></div>
				</div>
			</div>

			<div class="col-12  d-flex justify-content-center mt-3">

				<div class="col-12">
					<div class="table-container" style="">
						<table class="table table-hover border" >
							<thead class="trHeader">
								<tr>
									<th scope="col">Fecha Resultado</th>
									<th scope="col">#Orden</th>
									<th scope="col">Resultado</th>
									<th scope="col">Unidad</th>
								</tr>
							</thead>
							<tbody id="tabla1ModalHistorico">
			
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="vr portal"></div>
	<div class="col">
		<div class="d-flex flex-column text-center">
			<div class="col-12 d-flex justify-content-center">
				<div class="col-12 text-center" >
					<h5 class="mb-0">Orden: <label id="ordenHistorico" class="mb-0"></label></h5>
					<h5 class="mb-0">Fecha Orden: <label id="fechaHistorico" class="mb-0"></label></h5>
					
					<div class="table-container-tabla2">
						<table class="table table-hover border" >
							<thead class="trHeader">
								<tr>
									<th scope="col">Test</th>
									<th scope="col">Resultado</th>
									<th scope="col">Unidad</th>
									<th scope="col">Examen</th>						
								</tr>
							</thead>
							<tbody id="tabla2ModalHistorico" >
								
							</tbody>
						</table>
						</div>
				</div>
			</div>

			<hr class="hr-portal">
			<div class="col-12 mt-3" >

				<h5 class="card-title text-center">Delta Check</h5>
				<div class="row">
					<div class="col">
						<div class="row justify-content-end">
							<div class="col-auto">
								<label>Valores a evaluar</label>
							</div>
							<div class="col-3 p-0">
								<input id="cantidadHistorico" type="text" class="form-control">
							</div>
						</div>
					</div>
					<div class="col">
						<div class="row">
							<div class="col-auto">
								<label>Porcentaje </label>
							</div>
							<div class="col-5 pr-0 row">
								<input id="porcentajeHistorico" type="text" class="form-control col-9 mr-1" > %
							</div>
						</div>
					</div>
				</div>
				
				<div class="row mt-4">
				<div class="col-12 row justify-content-center">
					<label>Intervalo</label>
					<input id="intervaloHistorico" readOnly type="text" class="form-control col-xl-2 col-md-3 ml-2" placeholder="inf - sup">
				</div>
				<div class="col-12 row justify-content-center mt-3">
					<label>Resultado</label>
					<input readOnly id="resultadoDeltaCheck" class="form-control col-2 ml-2" type="text" >
				</div>
				</div>
			</div>
		</div>
	</div>
</div>	

