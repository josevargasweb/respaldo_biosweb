/*
class CurvaTolerancia {
    tablaCurvas;
    intervalo;
    timePrimeraToma;
    estadoAnterior;
    examenCurva;
    examenDesc;
    tomasPendientes
}
*/
var tablaCurvas = null;
var intervalo = null;
var mins = 0;
var segs = 0;
var timePrimeraToma = null;
var estadoAnterior = null;
var examencurva = null;
var examendesc = null;
var tomasPendientes = false;

function cargarCurvasTolerancia(norden, idmuestra) {
    
    tomasPendientes = false;
    
    $('#tablaTestsCurva tbody').empty();
    
    if ( $.fn.DataTable.isDataTable('#tablaTestsCurva') ){
        $('#tablaTestsCurva').DataTable().clear().destroy();
    }
    
    $.ajax({
        type: "get",
        url: getctx + "/api/examenes/"+norden+"/"+idmuestra,
        datatype: "json",
        async: false,
        success: function (data) {
            examencurva = data.ce_IDEXAMEN;
            $("#pacienteCurva").text(data.nombres);
            $("#examenCurva").text(data.ce_ABREVIADO);
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    
    /*
    let nombres = null;
    
    $.ajax({
        type: "get",
        url: getctx + "/api/paciente/orden/"+norden,
        datatype: "json",
        async: false,
        success: function (message) {
            let paciente = message.dato;
            if (typeof paciente.dp.dpNombreencriptado !== 'undefined'){
                nombres = paciente.dp.dpNombreencriptado;
            } else if (typeof paciente.dp.dpNombresocial !== 'undefined'){
                nombres = paciente.dp.dpNombresocial;
            } else {
                nombres = paciente.dp.dpNombres + " " + paciente.dp.dpPrimerapellido + " " + paciente.dp.dpSegundoapellido;
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    
    $.ajax({
        type: "GET",
        url: getctx + "/api/tomaMuestras/examenes/muestra/"+idmuestra,
        datatype: "json",
        async: false,
        success: function (data) {
            for (let examen of data){
                if (examen.ceEscurvatolerancia === 'S'){
                    examencurva = examen.ceIdexamen;
                    examendesc = examen.ceAbreviado;
                }
            }
        }
    });
    */
    
    
    tablaCurvas = $('#tablaTestsCurva').DataTable({
        responsive: false,
        searchDelay: 500,
        orderCellsTop: true,
        scrollY: 200,
        scrollX: false,
        paging: false,
        ordering: false,
        "info": false,
        //@GetMapping(value="/api/tomaMuestras/curvaTolerancia/{nOrden}/{idExamen}")
        ajax: {
                url: getctx + "/api/tomaMuestras/curvaTolerancia/"+norden+"/"+examencurva,
                type: "GET",
                contentType: "application/json",
                dataSrc: "",
                dataType: "json",
                async: false
        },
        columns: [
                {data: 'estadomuestra', responsivePriority: -2},
                {data: 'test'},
                {data: 'minutos'},
                {data: ''},
                {data: 'tomada'},
                {data: 'nromuestra'},
                {data: 'muestra'},
                {data: 'flebotomista'}
        ],
        columnDefs: [
            {
                //Estado Muestra
                targets: 0,
                title: 'Tomado',
                className: 'dt-center',
                render: function(data, type, row) {
                    let estadoCheck = "";
                    let titulo = "";
                    if (estadoAnterior !== null && estadoAnterior === 'P'){
                        estadoCheck = "disabled";
                        titulo = "Primero debe tomar muestra anterior";
                    }
                    if (data === 'T') {
                        estadoCheck = "checked disabled";
                    }
                    estadoAnterior = data;
                    //console.log("estadoAnterior: "+estadoAnterior);
                    return '<label class="checkbox checkbox-single checkboxMuestra" title="'+titulo+'">\
                                <input type="checkbox" class="checkable" onclick=\'tomarMuestraCurva('+row['idmuestra']+', '+norden+')\' ' + estadoCheck + ' />\
                                <span></span></label>';
                }

            },{
                //Tomar a las
                targets: 3,
                title: 'Tomar a las',
                data: null,
                render: function ( data, type, row ) {
                    //Esto es por si queda corriendo el tiempo cuando se vuelven todas las tomas a pendiente
                    if (row['estadomuestra']==='P'){
                        tomasPendientes = true;
                        if (row['minutos']==0){
                            timePrimeraToma = null;
                        }
                    }
                    
                    //Calcular según la hora en que se tomó la primera muestra
                    if (row['estadomuestra']==='T' && row['minutos']==0){
                        let fechaTM = row['tomada'];
                        let dia = fechaTM.substring(0, 2);
                        let mes = fechaTM.substring(3, 5);
                        let year = fechaTM.substring(6, 10);
                        let hora = fechaTM.substring(11, 13);
                        let minutes = fechaTM.substring(14, 16);
                        let seconds = fechaTM.substring(17, 19);
                        timePrimeraToma = new Date(year, mes-1, dia, hora, minutes, seconds);
                    }
                    //Calcula la hora que se debe tomar cada muestra a partir de la fecha de primera toma y los minutos basales de cada muestra
                    if(timePrimeraToma !==null){
                        let epochFechaFormat = Math.floor(timePrimeraToma.getTime()/1000.0)
                        //console.log("test: "+ row['test'])
                        //console.log("mins basal: "+ row['minutos'])
                        let epochMinsBasal = row['minutos']*60;
                        let epochFinal = epochFechaFormat + epochMinsBasal;
                        let myDate = new Date(epochFinal*1000);
                        //console.log(myDate.toLocaleString());
                        return myDate.getHours().toString().padStart(2,"0") + ":" + myDate.getMinutes().toString().padStart(2,"0");
                    }
                    return null;
                }
            },{
                //Tomado a las
                targets: 4,
                title: 'Tomado a las',
                render: function ( data, type, row ) {
                    //const fechaTM = data;
                    let tomado = null;
                    if (data !== null){
                        //dia = fechaTM.substring(0, 2);
                        //mes = fechaTM.substring(3, 5);
                        //year = fechaTM.substring(6, 10);
                        let horaTomada = data.substring(11, 13);
                        let minsTomada = data.substring(14, 16);
                        tomado = horaTomada + ":" + minsTomada;
                        //por requerimiento se elimina el input time, y solo se deja el valor de la hora tomada
                        //return "<input id='timeManual"+row['idmuestra']+"' type='time' value='"+tomado+"' onchange=\'tomarHoraManual("+row['idmuestra']+","+norden+")\'></input>";
                        return tomado;
                    }
                    return null;
                }
            }
        ],
        initComplete: function () {
            if (estadoAnterior !== null){
                estadoAnterior = null;
            }
            if (intervalo!==null){
                clearInterval(intervalo);
            }
            if(timePrimeraToma !== null){
                if (tomasPendientes){
                    crearIntervalo(timePrimeraToma);
                }
                
            }
        }
    });
}

    /*
    tablaCurvas.on( 'xhr', function ( e, settings, json ) {
        console.log("event")
        mostrarRegistroAcciones(norden);
    } );
    */

function tomarMuestraCurva(idMuestra, norden){
    let usuario = $('select[name=dUsuario] option').filter(':selected').val();
    $.ajax({
        type: "post",
        url: getctx + "/api/tomaMuestras/tests/curvaTolerancia/save/" + idMuestra + "/" + usuario,
        datatype: "json",
        success: function (data) {
            switch (data.status){
                case 200:
                    $('#divTablaMuestras').css('display', 'none');
                    $("#tablaOrdenesCollapse").collapse('show');
                    $("#dt_info_registro").html("Última atención: Orden: "+data.dato.norden +" ; Paciente: "+data.dato.nombres);                       
                    table.ajax.reload( null, false );
                    tableMuestras.ajax.reload( null, false );
                    tablaCurvas.ajax.reload( null, false );
                    handlerMessageOk(data.message);
                    break;
                case 202:
                    tableMuestras.ajax.reload( null, false );
                    tablaCurvas.ajax.reload( null, false );
                    handlerMessageOk(data.message);
                    break;
                case 404:
                    handlerMessageError(data.message)
            }
            
            if (intervalo===null){
                $("#minsCurva").val("00:00");
                let hora = null;
                intervalo = setInterval( function () {
                    if (segs < 59){
                        segs++;
                    } else {
                        segs = 0;
                        mins++;
                    }
                    if (mins>59){
                        if (hora===null){
                            hora = 1
                        } else {
                            hora++;
                        }
                    }
                    let timeCurva = (hora!==null ? hora + "H:" : "") + mins.toString().padStart(2,"0") + ":" + segs.toString().padStart(2,"0");
                    $("#minsCurva").val(timeCurva);

                }, 1000 );
            }
        },
        error: function (msg) {
            handlerMessageError("Error");
            console.log(msg);
        }
    });
    //mostrarRegistroAcciones(norden)
}

function tomarHoraManual(idMuestra, norden){
    let key = window.event.keyCode;
    //if (key === 13) {
        let usuario = $('select[name=dUsuario] option').filter(':selected').val();
        let time = $("#timeManual"+idMuestra).val();
        let timeArray = time.split(":");
        let hora = parseInt(timeArray[0]);
        let min = parseInt(timeArray[1]);
        let fechaToma = new Date(timePrimeraToma.getFullYear(), timePrimeraToma.getMonth(), timePrimeraToma.getDate(), hora, min);
        //console.log("fechaToma: "+fechaToma)
        
        if (hora < timePrimeraToma.getHours() || (hora == timePrimeraToma.getHours() && min < timePrimeraToma.getMinutes())){
            //let fechaToma = new Date();
            fechaToma.setDate(timePrimeraToma.getDate()+1)
            fechaToma.setHours(hora)
            fechaToma.setMinutes(min)
            //console.log("fechaToma+1: "+fechaToma)
        }
        
        let epochFechaToma = Math.floor(fechaToma.getTime() /1000.0);
        //console.log("epochFechaToma: "+epochFechaToma)
        $.ajax({
            type: "post",
            url: getctx + "/api/tomaMuestras/tests/curvaTolerancia/tomaManual/" + idMuestra + "/" + epochFechaToma + "/" +usuario,
            datatype: "json",
            success: function () {
                tablaCurvas.ajax.reload( null, false );
                table.ajax.reload( null, false );
                tableMuestras.ajax.reload( null, false );
                $.notify({ message: "Flebotomista cambiado exitosamente" }, { type: "success" });
            },
            error: function (msg) {
                $.notify({ message: "Error al cambiar flebotomista" }, { type: "danger" });
                console.log(msg);
            }
        });
        if (intervalo===null){
            $("#minsCurva").val("00:00");
            intervalo = setInterval( function () {
                if (segs < 59){
                    segs++;
                } else {
                    segs = 0;
                    mins++;
                }
                if (mins>59){
                    if (hora===null){
                        hora = 1
                    } else {
                        hora++;
                    }
                }
                let timeCurvaNew = (hora!==null ? hora + "H:" : "") + mins.toString().padStart(2,"0") + ":" + segs.toString().padStart(2,"0");
                $("#minsCurva").val(timeCurvaNew);

            }, 1000 );
        }
    //}
    mostrarRegistroAcciones(norden)
}

function crearIntervalo(timePT){
    let today = new Date();
    console.log("today: "+today)
    console.log("time 1° toma: "+timePT)
    let epochToday = Math.floor(today.getTime() /1000.0);
    let epochFechaToma = Math.floor(timePT.getTime() /1000.0);
    let time = epochToday - epochFechaToma;
    console.log("time: "+time)
    mins = Math.floor(time / 60);
    segs = time - (mins * 60);
    console.log("segs: "+segs)
    let hora = null;
    if (mins >= 60){
        hora = Math.floor(time / 3600);
        mins = mins - (hora * 60);
    }
    let timeCurva = (hora!==null ? hora + "H:" : "") + mins.toString().padStart(2,"0") + ":" + segs.toString().padStart(2,"0");
    $("#minsCurva").val(timeCurva);
    //crea el intervalo para configurar el tiempo transcurrido
    intervalo = setInterval( function () {
        if (segs < 59){
            segs++;
        } else {
            segs = 0;
            mins++;
        }
        if (mins>59){
            if (hora===null){
                hora = 1
            } else {
                hora++;
            }
        }
        timeCurva = (hora!==null ? hora + "H:" : "") + mins.toString().padStart(2,"0") + ":" + segs.toString().padStart(2,"0");
        $("#minsCurva").val(timeCurva);

    }, 1000 );
}