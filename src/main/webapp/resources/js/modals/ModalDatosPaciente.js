function MostrarDatosPaciente(nOrden) {
    $('#PatologiasModalBody').empty();
    $.ajax({
        type: "get",
        url: getctx + "/api/paciente/orden/"+nOrden,
        datatype: "json",
        success: function (message) {
            let paciente = message.dato;
            $('#RutPaciente').text(typeof paciente.dp.dpRun !== "undefined" ? cambiarDatoRut(paciente.dp.dpRun) : "");
            $('#TipoAntencionModal').text(paciente.tipoAtencion.ctaDescripcion);
            $('#ProcedenciaModal').text(paciente.procedencia);
            $('#ServicioModal').text(paciente.servicio);
            $('#ObservacionModal').text(paciente.dp.dpObservacion);
            /*if (typeof paciente.dp.dpObservacion !== 'undefined'){
                $('#ObservacionModal').val(paciente.dp.dpObservacion);
            } else {
                $('#ObservacionModal').val("");
            }*/
            let segundoApellido = paciente.dp.dpSegundoapellido || "";
            let nombres = paciente.dp.dpNombres + " " + paciente.dp.dpPrimerapellido + " " + segundoApellido;
            $('#tituloPacienteModal').text(nombres);
            var cont = 0;
            for (let pat of paciente.listaPatologias) {
                cont++;
                let obsPat = pat.dppObservacion || "";
                $("#PatologiasModalHead").show();
                $("#PatologiasModalBody").append("<tr>\
                                                    <th class='row mx-auto'>" + cont + "</th>\
                                                    <td>" + pat.ldvPatologias.ldvpatDescripcion + "</td>\
                                                    <td>" + obsPat + "</td></tr>");
            }
            if (cont===0){
                $("#PatologiasModalHead").hide();
                $("#PatologiasModalBody").append("<tr class=''><th class='row mx-auto'>Paciente no tiene patologías</td></tr>");
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });

}