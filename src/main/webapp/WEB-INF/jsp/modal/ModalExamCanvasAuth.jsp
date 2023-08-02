
<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<div class="modal-body">
			<h3 class="text-center">Autorizaci&oacute;n de Ex&aacute;menes <br> 
			<div id="autorPaciente"></div></h3>
		  <input id="nOrden" type="hidden" />
		  <div class="examen-selecc-container">
			<div class="card card-2 mt-3">
				<div class="card-body p-0">
					<table class="table table-hover table-striped nowrap compact-xs dataTable no-footer" style="padding: 0px; margin: 0px;" width="100%" id="idTableExamenesSeleccionados">
						<thead>
							<tr>
								<th>C&oacute;digo</th>
								<th>Examen</th>
								<th>Estado</th>
								<th><input type="checkbox" name="" id="selectAllExam" class="checkable"  ></th>
							</tr>
						</thead>
						<tbody id="me_examenes">
						</tbody>
					</table>
				</div>
			</div>
		  </div>
			<div class="d-flex align-items-end justify-content-around mt-4">
				<button id="examModalAuthUndoButton"
						class="btn btn-danger btn-lg  font-weight-bold  mt-2 mr-2 col-3 "  >
					<!-- <i class="flaticon2-paper"></i> -->Retirar Autorizaci&oacute;n
						</button>
				<button id="examModalAuthOkButton"
						class="btn btn-success btn-lg font-weight-bold mt-2 mr-2 col-3" style="background-color: #28a745;border-color: #28a745;">
					<!-- <i class="flaticon2-paper"></i> -->Autorizar
						</button>
				<button id="examModalAuthExitButton"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 col-3">
						<!-- <i class="flaticon2-paper text-white"></i>--> Salir
				</button>
			</div>
		</div>
	</div>
	
</div>
