
$("#btnMuestrasRechazadas").click(function (){	
	
	$("#bo_nOrdenIni").prop('disabled', true);
	$("#bo_nOrdenFin").prop('disabled', true);
	$("#bo_examen").prop('disabled', true);
	$("#bo_laboratorio").prop('disabled', true);
	$("#bo_seccion").prop('disabled', true);
	
	$("#btnEstadisticaMuestrasRechazadas").removeAttr('hidden');
	$("#btnEstadisticaTotalPorExamen").prop('hidden', 'true');
	$("#btnEstadisticaTotalPorExamenExcel").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntrega").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivados").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticos").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntregaExcel").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivadosExcel").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticosExcel").prop('hidden', 'true');

	$("#TiempoEntregaSwitch").css("display", "");
	$("#filtroHistorico").css("display", "");		
	$("#estadisticaHistorico").css("display", "");
	//  $("#collapseOne8").toggle("false")
	 $("#collapseOne8").collapse('hide')
	  $("#modalFiltroEstadistica").modal("show")
})


$("#btnMuestrasRechazadasExcel").click(function (){	
	$("#bo_nOrdenIni").prop('disabled', true);
	$("#bo_nOrdenFin").prop('disabled', true);
	$("#bo_examen").prop('disabled', true);
	$("#bo_laboratorio").prop('disabled', true);
	$("#bo_seccion").prop('disabled', true);
	$("#btnEstadisticaMuestrasRechazadasExcel").removeAttr('hidden');
	$("#btnEstadisticaTotalPorExamen").prop('hidden', 'true');
	$("#btnEstadisticaTotalPorExamenExcel").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntrega").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivados").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticos").prop('hidden', 'true');
	$("#btnEstadisticaTiempoEntregaExcel").prop('hidden', 'true');
	$("#btnEstadisticaExamenesDerivadosExcel").prop('hidden', 'true');
	$("#btnEstadisticaResultadosCriticosExcel").prop('hidden', 'true');
	$("#TiempoEntregaSwitch").css("display", "");
	$("#filtroHistorico").css("display", "");		
	$("#estadisticaHistorico").css("display", "");
	//  $("#collapseOne8").toggle("false")
	 $("#collapseOne8").collapse('hide')
	  $("#modalFiltroEstadistica").modal("show")
})

$("#queryMuestrasRechazadas").click(function (){
	html = "";
	html += "<h3 id='' class='text-center' id='obsExamenModalLabel'>SQL Muestras Rechazadas </h3> "
	html += `<label>select dmr.dmr_codigobarra as mr_codigobarras , ccrm.ccrm_descripcion as mr_causarechazo, 
             concat(concat(concat(concat(du.du_nombres , ' '), du.du_primerapellido), ' '), du.du_segundoapellido) as mr_usuario, 
               to_char(dmr.dmr_fecharechazo, 'dd/mm/yyyy hh:mi' ) as MR_FECHARM ,  dmr.dmr_rechazoparcialototal as mr_tiporechazo ,
               cmue.cmue_descripcionabreviada as mr_muestra , concat(concat(concat(concat(du.du_nombres , ' '), du.du_primerapellido), ' '), du.du_segundoapellido) as mr_usuariotm, 
               cenv.cenv_descripcion as mr_envase 
              from datos_muestrasrechazadas dmr 
               left join cfg_causasrechazosmuestras ccrm 
               on ccrm.ccrm_idcausarechazo = dmr.dmr_idcausarechazo 
              left  join datos_usuarios du 
             on du.du_idusuario = dmr.dmr_idusuariorechazo 
               left join cfg_muestras cmue 
             on cmue.cmue_idtipomuestra = (select dfet.dfet_idtipomuestra from datos_fichasexamenestest dfet where dfet.dfet_idmuestra = dmr.dmr_idnuevamuestra FETCH FIRST 1 ROW only) 
             left join cfg_envases cenv 
              on cenv.cenv_idenvase = (select dfet.dfet_idenvase from datos_fichasexamenestest dfet where dfet.dfet_idmuestra = dmr.dmr_idnuevamuestra FETCH FIRST 1 ROW only) 
            inner join datos_fichasmuestras dfm 
              on dfm.dfm_idmuestra = dmr.dmr_idmuestra 
             left  join datos_usuarios dutm 
              on dutm.du_idusuario = dfm.dfm_idusuariotm 
             inner join datos_fichas df on df.df_norden = dfm.dfm_norden 
             inner join datos_fichasexamenes dfe  on dfe.dfe_norden = df.df_norden 
             inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen  
              inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente 
            inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio 
            inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion  
              </label>
              <br>
              <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mr-2" data-dismiss="modal">Salir</button>  `
	$("#modalbody").html(html)
              
                
    $("#modalPromociones").modal("show")
    
      let query = "Muestras rechazadas"
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

$("#btnEstadisticaMuestrasRechazadasExcel").click(async function() {	
	
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
	        
	let data = await  getMuestrasRechazadasDescarga(boFormData);	

   llenarTablaMuestrasRechazadas(data);	
    $("#estadisticaHistorico").css("display", "none");
	 $("#modalFiltroEstadistica").modal("hide")
	agregarExcel("Muestras Rechazadas")
	limpiar;
	
	
	}
})


$("#btnEstadisticaMuestrasRechazadas").click(async function() {	
	
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
	        
	let data = await  getMuestrasRechazadas(boFormData);	

   llenarTablaMuestrasRechazadas(data);	
 $("#filtroHistorico").css("display", "none");	
	$("#btnExcel").click(function(){
		agregarExcel("Muestras Rechazadas")
	});
	$("#btnCancelar").click(limpiar);
	 $("#modalFiltroEstadistica").modal("hide")
	}
})



async function getMuestrasRechazadas(data) {
	 let url= getctx +"/api/estadistica/muestrasrechazadas";
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

async function getMuestrasRechazadasDescarga(data) {
	 let url= getctx +"/api/descarga/estadistica/muestrasrechazadas";
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



async function llenarTablaMuestrasRechazadas(data) {	
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
				<button  id="btnEstadisticaMuestrasRechazadas1"class="btn btn-blue-primary btn-lg font-weight-bold mr-2 ">
				<i class="la la-search"></i>Buscar</button>
				<button id="btnCancelar" class="btn btn-blue-primary btn-lg font-weight-bold mr-2 ">
				<i class="fas fa-sign-out-alt p-0 " aria-hidden="true"></i>Volver</button>
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
			
				<h3 id="tituloBusqueda" class="col-12 pl-0 pr-0 text-center">Estadistica Muestras Rechazadas </h3>
				<table id="tablaPrincipal" class="table table-striped table-bordered display nowrap" width="80%">
					<tbody id="bodyTablaBusqueda">					
						<tr Style=' font-size: 60%;'>			
						<th># Muestra</th>
						<th>Causa de Rechazo </th> 						
						<th>Usuario Rechaza</th>
						<th>Fecha Rechazo</th>					    
				        <th>Tipo de Rechazo </th>
						<th>Muestra </th>
				        <th>Contenedor </th>
				        <th>Flebotomista </th>		
						</tr>`
	
	data.forEach(function (dato) {
		html += `<tr Style=' font-size: 80%;'>
					<td>${dato.mr_CODIGOBARRAS}</td>
					<td>${dato.mr_CAUSARECHAZO}</td>
					<td>${dato.mr_USUARIO === null || dato.mr_USUARIO ? "" : dato.mr_USUARIO}</td>
					<td>${dato.mr_FECHARM === null ? "------------" : dato.mr_FECHARM}</td>
					<td>${dato.mr_TIPORECHAZO === null ? "------------" : dato.mr_TIPORECHAZO === "T" ? "Total" : "Parcial"}</td>
					<td>${dato.mr_MUESTRA === null ? "------------" : dato.mr_MUESTRA}</td>
					<td>${dato.mr_ENVASE === null ? "------------" : dato.mr_ENVASE}</td>
					<td>${dato.mr_USUARIOTM === null ? "------------" : dato.mr_USUARIOTM}</td>
				</tr>`;
	});
	
	html += `
				</tbody>
			</table>
   				 `
				
		// TABLA QUE SOLO SE MUESTRA EN EL EXCEL	****************************************		
				
				html += `	<table id="tb" class="table table-striped table-bordered display nowrap" width="80%" style='display:none'>
					<thead id="bo_th_ordenes">
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
						<th># Muestra</th>
						<th>Causa de Rechazo </th> 						
						<th>Usuario Rechaza</th>
						<th>Fecha Rechazo</th>					    
				        <th>Tipo de Rechazo </th>
						<th>Muestra </th>
				        <th>Contenedor </th>
				        <th>Flebotomista </th>
				        
				        <th> </th>
				        <th> </th>						
				        <th> </th>
				        <th></th>
			            <th> </th>
					    <th> </th>			
						</tr>`
	
	data.forEach(function (dato) {
		
		html += "<tr Style=' font-size: 60%;'>"		
			
						html +="<td>"+dato.mr_CODIGOBARRAS+"</td>"		
						html += "<td>"+dato.mr_CAUSARECHAZO+"</td>"
						
						if(dato.mr_USUARIO === null || dato.mr_USUARIO ===" "){
							html += "<td>----------------</td>"
						}else{
							html += "<td>"+dato.mr_USUARIO+"</td>"
						}
						
						
						if(dato.mr_FECHARM === null){
							html += "<td>------------</td>"
						}else{
							html += "<td>"+dato.mr_FECHARM+"</td>"
						}
						
						if(dato.mr_TIPORECHAZO === null){
							html += "<td>------------</td>"
						}else{
							if(dato.mr_TIPORECHAZO === "T"){
								html += "<td>Total</td>"
							}else{
							html += "<td>Parcial</td>"
							}
						}
						
						if(dato.mr_MUESTRA=== null){
							html += "<td>------------</td>"
						}else{
							html += "<td>"+dato.mr_MUESTRA+"</td>"
						}
						
						if(dato.mr_ENVASE=== null){
							html += "<td>------------</td>"
						}else{
							html += "<td>"+dato.mr_ENVASE+"</td>"
						}
						
					if(dato.mr_USUARIOTM === null){
							html += "<td>------------</td>"
						}else{
							html += "<td>"+dato.mr_USUARIOTM+"</td>"
						}
						
						
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
				
			
	$("#btnEstadisticaMuestrasRechazadas1").click(async function() {
	
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
		
				let data = await  getMuestrasRechazadas(boFormData);	
			
		 	  llenarTablaMuestrasRechazadas(data);		 	  
		 	  $("#filtroHistorico").css("display", "none");	
			$("#btnExcel").click(function(){
				agregarExcel("Muestras Rechazadas")
			});
			$("#btnCancelar").click(limpiar);
		
	 	  }	
})

	
}



