
$("#btnTotalPorExamen").click(function (){	
	$("#bo_nOrdenIni").prop('disabled', true);
	$("#bo_nOrdenFin").prop('disabled', true);
	$("#bo_examen").prop('disabled', true);	
	$("#btnEstadisticaTotalPorExamen").removeAttr('hidden');
	$("#btnEstadisticaTiempoEntrega").prop('hidden', 'true');
	$("#btnEstadisticaMuestrasRechazadas").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivados").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticos").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntregaExcel").prop('hidden', 'true');
	$("#btnEstadisticaMuestrasRechazadasExcel").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivadosExcel").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticosExcel").prop('hidden', 'true');

	$("#TiempoEntregaSwitch").css("display", "");
	$("#filtroHistorico").css("display", "");		
	$("#estadisticaHistorico").css("display", "");
	//  $("#collapseOne8").toggle("false")
	 $("#collapseOne8").collapse('hide')
	$("#modalFiltroEstadistica").modal("show")
	
})

$("#queryTotalPorExamen").click(function (){

	html = "";
	html += "<h3 id='' class='text-center' id='obsExamenModalLabel'>Total por Examen </h3> "
	html += `<label>select ce.ce_idexamen as DFE_IDEXAMEN, ce.ce_codigoexamen as CE_CODIGOEXAMEN,
       ce.ce_codigofonasa as CE_CODIGOFONASA,  count (dfe.dfe_idexamen) as DFE_CANTIDAD , 
              ce.ce_abreviado as  CS_DESCRIPCION 
              from datos_fichas df 
               inner join datos_fichasexamenes dfe on dfe.dfe_norden = df.df_norden 
                inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen 
                inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio 
               inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion 
               inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente             
              </label>
              <br>
              <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mr-2" data-dismiss="modal">Salir</button>  `
	$("#modalbody").html(html)
              
                
    $("#modalPromociones").modal("show")
    
     let query = "Total por Examen"
     $.ajax({
        type: "GET",
        url: getctx + "/api/query/" + query,
        dataType: 'json',
        success: function (json) {
            if (json.status === 200){
               
            } else {
                handlerMessageError(json.message);
            }
        }
    });

})

$("#btnTotalPorExamenExcel").click(function (){	
	$("#bo_nOrdenIni").prop('disabled', true);
	$("#bo_nOrdenFin").prop('disabled', true);
	$("#bo_examen").prop('disabled', true);	
	$("#btnEstadisticaTotalPorExamenExcel").removeAttr('hidden');
	$("#btnEstadisticaTiempoEntrega").prop('hidden', 'true');
	$("#btnEstadisticaMuestrasRechazadas").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivados").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticos").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntregaExcel").prop('hidden', 'true');
	$("#btnEstadisticaMuestrasRechazadasExcel").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivadosExcel").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticosExcel").prop('hidden', 'true');
	$("#TiempoEntregaSwitch").css("display", "");
	$("#filtroHistorico").css("display", "");		
	$("#estadisticaHistorico").css("display", "");
	//  $("#collapseOne8").toggle("false")
	$("#collapseOne8").collapse('hide')
	 $("#modalFiltroEstadistica").modal("show")
})



//funcion que toma datos y realiza excel sin pasar a mostrar tabla
$("#btnEstadisticaTotalPorExamenExcel").click(async function() {
	
	let fechaIni=" "
	let fechaFin =" "
	if($("#bo_fIni").val() != ""){
		 fechaIni = convertirDateDDMMYYYY(new Date($("#bo_fIni").val() + 'T00:00:00'))
	}
	if($("#bo_fFin").val() != ""){
		fechaFin = convertirDateDDMMYYYY(new Date($("#bo_fFin").val()+'T00:00:00'))
	}

	if(validarDatosVacios() === true){
	
		 let boFormData = new Object();
		  boFormData.bo_nOrdenIni = $("#bo_nOrdenIni").val();
	      boFormData.bo_nOrdenFin = $("#bo_nOrdenFin").val();
	      boFormData.bo_nombre = $("#bo_nombre").val();
	      boFormData.bo_apellido = $("#bo_apellido").val();
	      boFormData.bo_segundoApellido = $("#bo_segundoApellido").val();
	      boFormData.bo_fIni = fechaIni;
	      boFormData.bo_fFin = fechaFin;  	      
	      boFormData.bo_nroDocumento = $("#bo_nroDocumento").val();	      	    
	      boFormData.bo_procedencia =  $("#bo_procedencia option:selected").val();  
	      boFormData.bo_servicio =  $("#bo_servicio option:selected").val();
	      boFormData.bo_convenio =  $("#bo_convenio option:selected").val();
	      boFormData.bo_tipoAtencion =  $("#bo_tipoAtencion option:selected").val();
	      boFormData.bo_laboratorio =  $("#bo_laboratorio option:selected").val(); 
	      boFormData.bo_examen =  $("#bo_examen option:selected").val(); 
	      
		let data = await  getTotalPorExamenDescarga(boFormData);		
	 		llenarTablaTotalporExamen(data);
	 		 $("#estadisticaHistorico").css("display", "none");
	 		 $("#modalFiltroEstadistica").modal("hide")
	 		agregarExcel("Total por examen")
			limpiar			
	 	   		
			
 	  }	
})

//funcion que toma datos y y muestra en tabla para despues preguntar si bajar exel
$("#btnEstadisticaTotalPorExamen").click(async function() {
	
	let fechaIni=""
	let fechaFin =""
	if($("#bo_fIni").val() != ""){
		 fechaIni = convertirDateDDMMYYYY(new Date($("#bo_fIni").val() + 'T00:00:00'))
	}
	if($("#bo_fFin").val() != ""){
		fechaFin = convertirDateDDMMYYYY(new Date($("#bo_fFin").val()+'T00:00:00'))
	}
	

	
	if(validarDatosVacios() === true){
	
		 let boFormData = new Object();
		  boFormData.bo_nOrdenIni = $("#bo_nOrdenIni").val();
	      boFormData.bo_nOrdenFin = $("#bo_nOrdenFin").val();
	      boFormData.bo_nombre = $("#bo_nombre").val();
	      boFormData.bo_apellido = $("#bo_apellido").val();
	      boFormData.bo_segundoApellido = $("#bo_segundoApellido").val();
	      boFormData.bo_fIni = fechaIni;
	      boFormData.bo_fFin = fechaFin;  	      
	      boFormData.bo_nroDocumento = $("#bo_nroDocumento").val();	      	    
	      boFormData.bo_procedencia =  $("#bo_procedencia option:selected").val();  
	      boFormData.bo_servicio =  $("#bo_servicio option:selected").val();
	      boFormData.bo_convenio =  $("#bo_convenio option:selected").val();
	      boFormData.bo_tipoAtencion =  $("#bo_tipoAtencion option:selected").val();
	      boFormData.bo_laboratorio =  $("#bo_laboratorio option:selected").val(); 
	      boFormData.bo_examen =  $("#bo_examen option:selected").val(); 
	      

			let data = await  getTotalPorExamen(boFormData);	
	
	 	  llenarTablaTotalporExamen(data);
	 	  
	 	  
	 	 $("#filtroHistorico").css("display", "none");	
	 	   $("#btnExcel").click(function(){
			agregarExcel("Total por examen")
			});
		$("#btnCancelar").click(limpiar);
		$("#modalFiltroEstadistica").modal("hide")
 	  }	
})



async function getTotalPorExamen(data) {
	 let url= getctx +"/api/estadistica/totalporexamen";
	 let postData=JSON.stringify(data)
     let result = await $.post({
		type: "post",
		url : url,
		data: postData,      
		success: function (){return data},
		error: function (){console.log("error")}, 
		 contentType: 'application/json; charset=utf-8' 
    });
  
	return result.dato
	
}


async function getTotalPorExamenDescarga(data) {
	 let url= getctx +"/api/descarga/estadistica/totalporexamen";
	 let postData=JSON.stringify(data)
     let result = await $.post({
		type: "post",
		url : url,
		data: postData,      
		success: function (){return data},
		error: function (){console.log("error")}, 
		 contentType: 'application/json; charset=utf-8' 
    });
  
	return result.dato
	
}



async function llenarTablaTotalporExamen(data) {	
	let fechaFin ="" ;
	let fechaIni = " ";
	if($("#bo_fFin").val() != ""){
		 fechaFin = convertirDateDDMMYYYY(new Date($("#bo_fFin").val()+'T00:00:00'))
	}
	
	if($("#bo_fIni").val() != ""){
		 fechaIni = convertirDateDDMMYYYY(new Date($("#bo_fIni").val()+'T00:00:00'))
	}


	let procedencia = $("#bo_procedencia option:selected").val();
	let laboratorio = $("#bo_laboratorio option:selected").val();
	let servicio =  $("#bo_servicio option:selected").val();
	let seccion = $("#bo_seccion option:selected").val();
	let convenio = $("#bo_convenio option:selected").val();
	let tipoAtencion = $("#bo_tipoAtencion option:selected").val();
	
let html = `	
	
		<div class="card h-100">
			<div class="card-body motor-busqueda overflow-hidden">
				<div class="col-12 pl-0 pr-0">
					<button id="btnExcel" class="btn btn-blue-primary btn-lg font-weight-bold mr-2 ">
					<i class="fa fa-download" aria-hidden="true"></i>Descargar Excel</button>
					<button  id="btnEstadisticaTotalPorExamen1"class="btn btn-blue-primary btn-lg font-weight-bold mr-2 ">
					<i class="la la-search"></i>Buscar</button>
					<button id="btnCancelar" class="btn btn-blue-primary btn-lg font-weight-bold mr-2 ">
					<i class="fas fa-sign-out-alt p-0 " ></i>Volver</button>
				</div>
				<div class="row">
					<div class="col-3">
						<div class="row mt-3">
							<label class="col-5 col-form-label">Fecha de Inicio</label>
							<div class="col-7">
								<input id='bo_fIni' class="form-control" type='Date' value="${$("#bo_fIni").val()}"/>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Fecha Fin</label>
							<div class="col-7">
								<input id='bo_fFin' class="form-control" type='Date' value="${$("#bo_fFin").val()}"/>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Orden Inicio</label>
							<div class="col-7">
								${$("#bo_nOrdenIni").val()}
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Orden Fin</label>
							<div class="col-7">
								${$("#bo_nOrdenFin").val()}
							</div>
						</div>
					</div>
					<div class="col-3">
						<div class="row mt-3">
							<label class="col-5 col-form-label">Procedencia</label>
							<div class="col-7">
								<select id="bo_procedencia"  class="form-control">
									<option    value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Servicio</label>
							<div class="col-7">
								<select id="bo_servicio"  class="form-control">
									<option  value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Convenio</label>
							<div class="col-7">
								<select id="bo_convenio"  class="form-control">
									<option  value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Tipo de Atención</label>
							<div class="col-7">
								<select id="bo_tipoAtencion"  class="form-control">
									<option value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-3">
						<div class="row mt-3">
							<label class="col-5 col-form-label">Laboratorio</label>
							<div class="col-7">
								<select id="bo_laboratorio"  class="form-control">
									<option  value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Sección</label>
							<div class="col-7">
								<select id="bo_seccion"  class="form-control">
									<option  value="-1">Seleccionar</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Examen</label>
							<div class="col-7">
							</div>
						</div>
					</div>
					<div class="col-3">
						<div class="row mt-3">
							<label class="col-5 col-form-label">RUN</label>
							<div class="col-7">
								<input id='bo_nroDocumento' class="form-control"  type='text' value="${$("#bo_nroDocumento").val()}"/>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">Nombre</label>
							<div class="col-7">
								<input id='bo_nombre' class="form-control" type='text' value="${$("#bo_nombre").val()}"/>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">1er Apellido</label>
							<div class="col-7">
								<input id='bo_apellido' class="form-control" type='text' value="${$("#bo_apellido").val()}"/>
							</div>
						</div>
						<div class="row mt-3">
							<label class="col-5 col-form-label">2do Apellido</label>
							<div class="col-7">
								<input id='bo_segundoApellido' class="form-control"  type='text' value="${$("#bo_segundoApellido").val()}"/>
							</div>
						</div>
					</div>
				</div>
				<h3 id="tituloBusqueda" class="col-12 pl-0 pr-0 mt-3 text-center">Estadistica Total por Examen </h3>

					<table id="tablaPrincipal" class="table table-striped table-bordered display nowrap" width="80%">
					<tbody id="bodyTablaBusqueda">					
						<tr Style=' font-size: 60%;'>			
						<th>Código</th>
						<th>Código Fonasa </th> 						
						<th>Examen</th>
						<th>Total</th>
						</tr>`;
	
	data.forEach(function (dato) {
				html += `<tr Style=' font-size: 80%;'>
							<td>${dato.ce_CODIGOEXAMEN}</td>
							<td>${dato.ce_CODIGOFONASA === null ? " Sin Codigo" : dato.ce_CODIGOFONASA}</td>
							<td>${dato.cs_DESCRIPCION}</td>
							<td>${dato.dfe_CANTIDAD}</td>
						</tr>`;
	});
	
	html += `</tbody>
				</table>`;	
				
					
		// TABLA QUE SOLO SE MUESTRA EN EL EXCEL			
			html += `<table id="tb" class="table table-striped table-bordered display nowrap" width="80%" style='display:none'>
	
					<thead id="bo_th_ordenes">
					
					<tr>
					
					<th></th>
					<th></th>
					<th></th>
						<th class="text-center">Total por Examen</th>
					</tr>
					<tr></tr>
					<tr>
						<th>Fecha de Inicio </th>`					
						
								if($("#bo_fIni").val() == null){
									html+="<th></th>"
								}else{									
									html+="<th>"+ fechaIni+" </th>"
								}							
									
						
						
					html +=`</th>
						<th>Procedencia</th>
						`
								if($("#bo_procedencia option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_procedencia option:selected").text()+" </th>"
								}							
										
					
											
					html +=`
						<th>Laboratorio</th>
						`
								if($("#bo_laboratorio option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_laboratorio option:selected").text()+" </th>"
								}							
									
						
						
						html +=`						
						<th>RUT</th>
						<th> `
						html += "<input id='bo_nroDocumento'  type='text' value='"+$("#bo_nroDocumento").val()+"'/>" ;
						
						html +=`	 </th>
					</tr>
					<tr>
					    <th>Fecha Fin</th>`					
						
								if($("#bo_fFin").val() == null){
									html+="<th></th>"
								}else{									
									html+="<th>"+ fechaFin+" </th>"
								}							
									
							
										
						
					html +=`</th>
						<th>Servicio</th>`
								if($("#bo_servicio option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_servicio option:selected").text()+" </th>"
								}	
						 
						
						html +=`<th>Seccion</th>`
								if($("#bo_seccion option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_seccion option:selected").text()+" </th>"
								}	
						
						
						
						
						html +=` <th>Nombre</th>
						<th>`
						
						html += $("#bo_nombre").val() ;
					html +=`	</th>
					</tr>
					<tr>
					    <th>Orden Inicio</th>
						<th>`
						
						html+= $("#bo_nOrdenIni").val();
						
					html +=`</th>
						<th>Convenio</th>`
								if($("#bo_convenio option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_convenio option:selected").text()+" </th>"
								}	
						
						html +=`						
						<th>Examen</th>`
						
							html+="<th></th>"
						
						
						
						html +=`						
						<th>1er Apellido</th>
						<th>`
						
						html += $("#bo_apellido").val() ;
					html +=`</th>
					</tr>
					<tr>
					    <th>Orden Fin</th>
						<th>`
						
						html+= $("#bo_nOrdenFin").val();
						
					html +=`</th>
						<th>Tipo de Atencion</th>`
								if($("#bo_tipoAtencion option:selected").val() == -1){
								html+="<th></th>"
								}else{
								html+="<th>"+ $("#bo_tipoAtencion option:selected").text()+" </th>"
								}	
						
						
						
						html +=`
						<th></th>
						<th></th>
						<th>2do Apellido</th>
						<th>`
						
						html += $("#bo_segundoApellido").val() ;
					html +=`</th>
					</tr>
					<tr><th></th></tr>
					<tr><th></th></tr>
					
				</thead>	<tbody id="bodyTablaBusqueda">					
						<tr Style=' font-size: 60%;'>			
						<th>Código</th>
						<th>Código Fonasa </th> 						
						<th>Examen</th>
						<th>Total</th>
					    
				        <th> </th>
						<th> </th>
				        <th> </th>
				        <th> </th>
				        <th> </th>
				        <th> </th>						
				        <th> </th>
				        <th></th>
			            <th> </th>
					    <th> </th>			
						</tr>`
	
	data.forEach(function (dato) {
		
		html += "<tr Style=' font-size: 60%;'>"		
			
						html +="<td>"+dato.ce_CODIGOEXAMEN+"</td>"
						if(dato.ce_CODIGOFONASA === null){
							html +="<td> Sin Codigo</td>"
						}else{
							html +="<td>"+dato.ce_CODIGOFONASA+"</td>"
						}
										
						html += "<td>"+dato.cs_DESCRIPCION+"</td>"
						html += "<td>"+dato.dfe_CANTIDAD+"</td>"
	});
	
	html += `	</tr></tbody>
				</table>
			</div>
		</div> `			
				
				
				$("#bodyTablaBusqueda").html(html);	
				
			
				 data = await  getProcedencias();			
				llenarSelectProcedencia(data);				
				$("#bo_procedencia").val(procedencia)
				data = await  getLaboratorios();			
				llenarSelectLaboratorios(data);				
				$("#bo_laboratorio").val(laboratorio)
				data = await  getServicios();			
				llenarSelectServicios(data);				
				$("#bo_servicio").val(servicio)
				data = await  getSecciones();			
				llenarSelectSecciones(data);				
				$("#bo_seccion").val(seccion)
				data = await  getConvenios();			
				llenarSelectConvenios(data);				
				$("#bo_convenio").val(convenio)
				data = await  getTipoAtencion();			
				llenarSelectTipoAtencion(data);				
				$("#bo_tipoAtencion").val(tipoAtencion)
			
			
	$("#btnEstadisticaTotalPorExamen1").click(async function() {
		let fechaIni=""
		let fechaFin =""
		if($("#bo_fIni").val() != ""){
			 fechaIni = convertirDateDDMMYYYY(new Date($("#bo_fIni").val() + 'T00:00:00'))
		}
		if($("#bo_fFin").val() != ""){
			fechaFin = convertirDateDDMMYYYY(new Date($("#bo_fFin").val()+'T00:00:00'))
		}
	

	
		if(validarDatosVacios() === true){
		
			 let boFormData = new Object();
			  boFormData.bo_nOrdenIni = $("#bo_nOrdenIni").val();
		      boFormData.bo_nOrdenFin = $("#bo_nOrdenFin").val();
		      boFormData.bo_nombre = $("#bo_nombre").val();
		      boFormData.bo_apellido = $("#bo_apellido").val();
		      boFormData.bo_segundoApellido = $("#bo_segundoApellido").val();
		      boFormData.bo_fIni = fechaIni;
		      boFormData.bo_fFin = fechaFin;  	      
		      boFormData.bo_nroDocumento = $("#bo_nroDocumento").val();	      	    
		      boFormData.bo_procedencia =  $("#bo_procedencia option:selected").val();  
		      boFormData.bo_servicio =  $("#bo_servicio option:selected").val();
		      boFormData.bo_convenio =  $("#bo_convenio option:selected").val();
		      boFormData.bo_tipoAtencion =  $("#bo_tipoAtencion option:selected").val();
		      boFormData.bo_laboratorio =  $("#bo_laboratorio option:selected").val(); 
		      boFormData.bo_examen =  $("#bo_examen option:selected").val(); 
		 
			let data = await  getTotalPorExamen(boFormData);	
			
		 	  llenarTablaTotalporExamen(data);
		 	 
		 	  
		 	$("#filtroHistorico").css("display", "none");	
			$("#btnExcel").click(function(){
				agregarExcel("Total por examen")
			});
			$("#btnCancelar").click(limpiar);
		
	 	  }	
})

}



