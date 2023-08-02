
let  GvalorInicioRow = 0;

function selectUsuario(a){
    var $row = $(a).closest("tr"); // Find the row
    var id = $row.find(".id").text();
  
   
    GvalorInicioRow = 0;
   $("#idUsuarioEvento").val(id)
   getUsuarios(id, "", "0", "100")
}

$("#btnLimpiarTabla").click( function (){
	 GvalorInicioRow = 0;
	  $("#idUsuarioEvento").val("")
	  $("#UsuarioEventsTable").html("") 
	  $("#tituloAcciones").text("")
	  $("#textBuscarEvento").val("")
	     $("#collapseHeader").collapse('show')
		$("#UsuarioEvents").collapse('hide')
})

$("#btnAvanzar").click(function (){
	GvalorInicioRow += 100
	let id = $("#idUsuarioEvento").val();
	let filtro =  $("#textBuscarEvento").val()
	 getUsuarios(id, filtro, GvalorInicioRow, "100")
})

$("#btnRetroceder").click(function (){
	if(GvalorInicioRow != 0){
		GvalorInicioRow -= 100
		let id = $("#idUsuarioEvento").val();
		let filtro =  $("#textBuscarEvento").val()
		 getUsuarios(id, filtro, GvalorInicioRow, "100")
	 }
})






$("#buscarEventoUsuario").click(function(){	
	let id = $("#idUsuarioEvento").val();
	let filtro =  $("#textBuscarEvento").val();
	 getUsuarios(id, filtro, "0", "100")
})


function getUsuarios(id, filtro, inicio, fin){
	 $.getJSON(gCte.getctx+"/api/usuario/eventos/usuarios/", { id: id , filtro:filtro , inicio:inicio, fin:fin} )
    .done(function(usuario) {	    
   let html =  showUsuarioEvents(usuario.dato);
    $("#UsuarioEventsTable").empty();
    $("#collapseHeader").collapse('hide')
	$("#UsuarioEvents").collapse('show')
	  if( usuario.dato.length > 0){
	$("#tituloAcciones").text("Acciones del usuario : "+usuario.dato[0].nombreusuario)
}	
	$("#UsuarioEventsTable").html(html);
     // $('#OrderCanvasEvents').addClass('show active');
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}

$("#btnExcelEvento").click(function(){	
	$("#UsuarioEventsExcel").empty()	
	let id = $("#idUsuarioEvento").val();
	getUsuariosExcel(id)

	
	
})

function getUsuariosExcel(id){
	 $.getJSON(gCte.getctx+"/api/usuario/eventos/usuarios/", { id: id } )
    .done(function(usuario) {	 
		   
   		let eventos =  crearObjetoDatos(usuario.dato); 	
		DescargarExcel(eventos ,  usuario.dato[0].nombreusuario) 
	
    })
    .fail(function(jqxhr, textStatus, error) {
      var err = textStatus + ", " + error;
      console.log("Request Failed: " + err);
    });
}



function showUsuarioEvents(events) { 
	
  let html = ""; 

 events.forEach(function (dato) {	
	
		let mensaje = "";
		
		if(dato.leu_IDEVENTO === 7 ){
			mensaje = " Ingresa a  "
		}
		if(dato.leu_IDEVENTO === 8 ){
			mensaje = " Sale de  "
		}
		if(dato.leu_IDEVENTO === 9 && dato.leu_IDACCIONDATO === 4 ){
			mensaje = " Imprime  "
		}	
				
		html += "<tr><td scope='col'>" + (new Date(dato.leu_FECHAEVENTO).toLocaleString())
		 + "</td><td scope='col'>" + dato.nombreusuario
		  + "</td><td scope='col'>"+   (dato.leu_IPUSUARIO == null ? "" : dato.leu_IPUSUARIO  ) +" </td><td scope='col'>"+ mensaje +dato.leu_VALORNUEVO + " </td></tr>"			

  });
 
	return html
}





//*******************************  Creando objeto  **************************** */



function crearObjetoDatos(dato ){

	const eventos = dato.map(({ leu_FECHAEVENTO, nombreusuario, leu_IPUSUARIO, Ip , leu_VALORNUEVO, leu_IDACCIONDATO, leu_IDEVENTO }) => {  		
  
  		let mensaje = "";
		
		if(leu_IDEVENTO === 7 ){
			mensaje = " Ingresa a  "
		}
		if(leu_IDEVENTO === 8 ){
			mensaje = " Sale de  "
		}
		if(leu_IDEVENTO === 9 && leu_IDACCIONDATO === 4 ){
			mensaje = " Imprime  "
		}	
  		 Fecha = new Date(leu_FECHAEVENTO).toLocaleString()
  		 Usuario = nombreusuario
  		 IP = leu_IPUSUARIO == null ? "" : leu_IPUSUARIO 
  		Evento = mensaje + leu_VALORNUEVO
  		
  		return { Fecha , Usuario, IP, Evento };
   });

return eventos
}


function DescargarExcel(evento , usuario){
	let texto = "Eventos usuario "+usuario;	
// Crear un libro de Excel
var wb = XLSX.utils.book_new();

// Crear una hoja de cálculo a partir del array
var ws = XLSX.utils.json_to_sheet(evento);

// Agregar la hoja de cálculo al libro de Excel
XLSX.utils.book_append_sheet(wb, ws, "Eventos");

// Generar el archivo Excel y descargarlo
XLSX.writeFile(wb, texto+".xlsx");
}


