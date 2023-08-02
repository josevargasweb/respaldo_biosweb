  <jsp:include page="./Bios_ModalPreview.jsp" />
  <div class="row">
	  <div class="col-xl-10 col-md-9">
		  <div class="card card-2">
			  <div class="card-body">
				  <h3>Nro. Orden: <label id="lblNroOrden"></label></h3>
				  <span id="dt_registros_m" class="p-3"></span>
				<div class="card card-2 mt-3">
					<div class="card-body p-0">
						<table
							class='table table-hover table-striped nowrap compact-xs'
							id="tablaMuestras" style="width: 100%;">
							<thead>
								<tr>
									<th>Selecci&oacute;n</th>
									<th>Muestra</th>
									<th>Estado</th>
									<th>Contenedor</th>
									<th>N&deg; Muestra</th>
									<th>Observaci&oacute;n</th>
								</tr>
								<tr>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				  <label id="idPacienteTabla"></label> <label id="idOrdenTabla"></label>
				  <label id="idUsuario"></label>
			  </div>
		  </div>
	  </div>
	  <div class="col-xl-2 col-md-3">
			<div class="row">
				<div class="d-flex align-items-end justify-content-center w-100">
					<button id="btnPreviewEtiquetas" type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 w-100 no-cursor">
						<img
							src="<%=request.getContextPath()%>/resources/img/barcode.png"
							width="30" /> Prevista etiquetas
					</button>

				</div>
				<div class="d-flex align-items-end justify-content-center w-100">
					<button id="btnImpresionEtiquetas" type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 w-100 no-cursor">
						<img
							src="<%=request.getContextPath()%>/resources/img/barcode.png"
							width="30" /> Imprimir etiquetas
					</button>

				</div>
			</div>
	  </div>
  </div>




