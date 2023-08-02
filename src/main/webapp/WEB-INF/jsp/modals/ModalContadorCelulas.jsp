

<h5 class="text-center mb-0">Paciente: <label id="pacienteContadorCelulas" class=""></label></h5>
<h5 class="text-center">Orden: <label id="ordenContadorCelulas" class=""></label></h5>
<div class="row justify-content-center mt-4">
	<div class="col-9">
		<div class="progress" id="myProgress">
			<div id="myBar" class="progress-bar bg-success" role="progressbar" style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
		  </div>
	</div>
	<div class="col-2">
		<div class="row">
			<div class="col-12 d-flex justify-content-start">
				<label><b>Total</b></label>
				<input id="valorTotalContador" class="form-control col-5 ml-1"  type="number" id="" value="0" readonly />
			</div>
		</div>
	</div>
</div>
<div class="row justify-content-center container-cel-cont mt-4">
	<div class="col-1">
		<label for="">Basofilos</label>
		<input type="number" class="form-control" id="valor1" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra1"></span>
	</div>
	<div class="col-1">
		<label for="">Eosinofilos</label>
		<input type="number" class="form-control" id="valor2" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra2"></span>
	</div>
	<div class="col-1">
		<label for="">Mielocitos</label>
		<input type="number" class="form-control" id="valor3" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra3"></span>
	</div>
	<div class="col-1">
		<label for="">Juveniles</label>
		<input type="number" class="form-control" id="valor4" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra4"></span>
	</div>
	<div class="col-auto">
		<label for="">Baciliformes</label>
		<div class="col-12">
			<div class="row justify-content-center">
				<input type="number" class="form-control col-9" id="valor5" readonly value="0" min="0" max="100">
			</div>
		</div>
		<div class="col-12">
			<div class="row justify-content-center">
				<span class="form-control border border-dark" id="letra5"></span>
			</div>
		</div>
	</div>
	<div class="col-auto">
		<label for="">Segmentados</label>
		<div class="col-12">
			<div class="row justify-content-center">
			<input type="number" class="form-control col-9" id="valor6" readonly value="0" min="0" max="100">
			</div>
		</div>
		<div class="col-12">
			<div class="row justify-content-center">
			<span class="form-control border border-dark" id="letra6"></span>
			</div>
		</div>
	</div>
	<div class="col-1">
		<label for="">Linfocitos</label>
		<input type="number" class="form-control" id="valor7" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra7"></span>
	</div>
	<div class="col-1">
		<label for="">Monocitos</label>
		<input type="number" class="form-control" id="valor8" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra8"></span>
	</div>
	<div class="col-auto">
		<label for="">Promielocitos</label>
		<div class="col-12">
			<div class="row justify-content-center">
			<input type="number" class="form-control col-9" id="valor9" readonly value="0" min="0" max="100">
			</div>
		</div>
		<div class="col-12">
			<div class="row justify-content-center">
			<span class="form-control border border-dark" id="letra9"></span>
			</div>
		</div>
	</div>
	<div class="col-1">
		<label for="">Blastos</label>
		<input type="number" class="form-control" id="valor10" readonly value="0" min="0" max="100">
		<span class="form-control border border-dark" id="letra10"></span>
	</div>
</div>
<div class="row mt-3 justify-content-center mt-4">
	<div class="col-11 d-flex justify-content-end">
		<label><b>Valor</b></label>
		<input class="form-control col-1 ml-1" type="number" id="valorNuevoContador" value="1" min="1"/>
	</div>
</div>

<div class="row mt-4">
	<div class="col-6">
		<button id="btnIniciarContador" type="button" class="btn btn-success btn-lg font-weight-bold mt-2 mr-2 "
			onclick="inicioContador()">Iniciar</button>
		<button id="btnCancelarContador" type="button" class="btn btn-danger btn-lg font-weight-bold mt-2 mr-2"
			onclick="getContador()">Cancelar</button>
	</div>
	<div class="col-6 d-flex justify-content-end">
		<button id="btnGuardarContador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 "
		onclick="saveContadorCelulas()">Guardar</button>
		<button id="btnSalirContador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 "
		data-dismiss="modal">Cerrar</button>
	</div>
</div>