<div class="modal fade" id="registroDocumentosModal" tabindex="-1"
	role="dialog" aria-labelledby="registroDocumentosModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-xl"
		role="document">
		<div class="modal-content">

			<div class="card" id="divRegistroDocumentos">
				<div class="card-header" data-toggle="collapse"
					data-target="#tablaOrdenesCollapse" aria-expanded="true">
					<h4 class="pl-2">Registro de documentos</h4>
				</div>
				<div class="card-body">
					<b>Paciente: </b><label id="lblPaciente"></label><br /> <b>Orden:
					</b><label id="lblOrden"></label><br />
					<button id='btnVerOrden' class="btn btn-primary">Datos de
						Ex&aacute;menes</button>
					<div class="row mt-5">
						<div class="col">
							<h5>Orden m&eacute;dica</h5>
							<form id="formOM" method="post" enctype="multipart/form-data">
								<input id="ordenMedicaFile" name="ordenMedicaFile" type="file"
									class="file doc-orden" data-browse-on-zone-click="true">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
