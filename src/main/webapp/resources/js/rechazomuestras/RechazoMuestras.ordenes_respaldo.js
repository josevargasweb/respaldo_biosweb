var nOrden = null;
if (getWithExpiry('nOrden') !== null) {
    nOrden = getWithExpiry('nOrden');
    $("#txtNOrden").val(nOrden);
    let idMuestra = getWithExpiry('idMuestra');
    rellenarTablaMuestras(nOrden, idMuestra);
    llenarFormRechazo(idMuestra);
} else {
    nOrden = -1;
}

var datos = {"nOrden":nOrden,"fIni":null,"nombre":"","apellido":""};

//var initTableRechMueOrdenes = function(){
//function rellenarTablaOrdenes(datos){

    tablaOrdenesRechazo = $('#tablaOrdenes').DataTable({
        responsive: false,
        searchDelay: 500,
        processing: true,
        orderCellsTop: true,
        scrollY: 250,
        scrollX: true,
        paging: false,
        dom: 'lrt',
        "ajax": {
            url: getctx + "/api/rechazoMuestras/ordenes/",
            type: "POST",
            contentType: "application/json",
            dataSrc:"",
            data:function(d){
                return JSON.stringify(datos);
            }
        },
        "columns": [
                {data: 'norden'},
                {data: 'fechaorden'},
                {data: 'nombres'},
                {data: 'fechanac'},
                {data: 'estado'},
                {data: 'Acciones'}
        ],
        "columnDefs": [
            {
                targets: -1,
                title: 'Acciones',
                orderable: false,
                render: function(data, type, row) {
                        let datos = JSON.stringify(row)
                        return '\<div class="row">\
                                    <a href="#" class="btn btn-sm btn-clean btn-icon" title="Datos Paciente" data-toggle="modal" data-target="#ModalDatosPaciente" onclick=\'MostrarDatosPaciente(' + row['norden'] + ',' + row['idpaciente'] +') \'>\
                                        <i class="fas fa-user-md"></i><span class="nav-text"></span>\
                                    </a>\
                                    <a href="#" class="btn btn-sm btn-clean btn-icon" title="Orden/Exámenes" data-toggle="modal" data-target="#ModalDatosOrden" onclick=\'MostrarDatosOrden(' + row['norden'] + ') \'>\
                                        <i class="fa fa-search" aria-hidden="true"></i><span class="nav-text"></span>\
                                    </a>\
                                </div>';
                }
            },{
                //Estado
                targets: -2,
                render: function(data, type, row, meta) {
                    let bgColor = "";
                    switch(data.trim()) {
                        case 'ATENDIDO':
                          bgColor = "#ACECD6";
                          break;
                        case 'ESPERA':
                          bgColor = "#FF9AA2";
                          break;
                        case 'BLOQUEADA':
                          bgColor = "#DFC7C1";
                          break;
                        default:
                          bgColor = "#808080";
                          break;
                    }
                    return '<span style="background-color:' + bgColor + '" class="label label-lg font-weight-bold label-inline">' + data + '</span>';
                }
            },{
                //Edad
                targets: -3,
                render: function(data, type, full, meta) {
                    return data!==null ? calcularEdadTM(data) : "";
                }
            },{
                //Fecha
                targets: -5,
                render: function(data, type, full, meta) {

                    var dia = data.substring(0, 2);
                    var mes = data.substring(3, 5);
                    var year = data.substring(8, 10);
                    var hora = data.substring(11, 16);

                    return hora + " " + dia + "/" + nombresMeses(mes) + "/" + year;
                }
            },
        ],
        select: {
            style:    'os',
            selector: 'td:first-child'
            //selector: 'td:first-child input[type="checkbox"]'
        },               
        "language": {
            url: "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"
        }
    });
    
    $('#tablaOrdenes tbody').on('click', 'tr', function () {
        let data = tablaOrdenesRechazo.row(this).data();
        let nOrden = data['norden'];
        rellenarTablaMuestras(nOrden, null);
    });
    
 //}
 
$("#btnBuscarFiltro").click(function () {
    buscarOrdenesReMue(); 
});



function buscarOrdenesReMue(){
    let formValidator = new FormValidator();
    let nroOrdenValidator = new NroOrdenValidator("#txtNOrden",0,0);
    formValidator.add(nroOrdenValidator);
    let lenNombreMinValidator = new LenMinValidator("#txtFiltroNombre",2);
    formValidator.add(lenNombreMinValidator);
    let lenApellidoMinValidator = new LenMinValidator("#txtFiltroApellido",2);
    formValidator.add(lenApellidoMinValidator);

    let parmOk = formValidator.validate();
    if (parmOk === false){
        handlerMessageError(formValidator.message);
        return;
    }
    let filtroOrden = new FiltroOrden();
    filtroOrden.fill("#txtNOrden","#txtFecha","","#txtFiltroNombre","#txtFiltroApellido",
                      "#selectTipoDocumentoFiltro","#txtNDocumento","",
                      "","");
    obtenerDatos(filtroOrden);
}

function obtenerDatos(filtro){
    datos = filtro;
    $('#divTablaMuestras').css('display', 'none');
    $('#divRechazo').css('display', 'none');
    tablaOrdenesRechazo.ajax.reload();
}

function FormValidator () {
 
    this.validators = [];
    this.message = "";
    this.add = function(val){
        this.validators.push(val);
    }
    this.validate = function(){
        
        let n = this.validators.length;
        let b = true;
        
        for (i=0; i< n; i++){
            if (this.validators[i].validate()=== false){
                this.message += "\n" + this.validators[i].message;
                b = false;
            }
        }
        return b;
    }
    
} 

function NroOrdenValidator (jqId,minLen,maxLen) {

    this.message="";
    this.jqId = jqId;
    this.minLen = minLen;
    this.maxLen = maxLen;
    this.regla = function (){
        
        if ($(this.jqId) === undefined){
            this.message = "Valor indefinido.";
            return false;
        }

        let valor = $(this.jqId).val();

        valor = valor.replaceAll('.','');

        if (isNaN(valor)){
            this.message = "Valor no es un n�mero.";
            return false;
        }

        return true;
    }
    
    this.validate = function(){
        
        return this.regla();
        
        
    }
} 

function LenMinValidator(jqid,len){
    this.message = "Valor de largo menor a " + len + ".";
    this.texto = $(jqid).val();

    this.regla = function (){
    
        try{
            
            if (this.texto === undefined || this.texto === null ){
                return true;
            }

            if (this.texto.trim() !== '' && this.texto.lenght < len ){
                return false;
            }


        }
        catch(e){
            //handleMessageError(e);
            console.log(e);
            return false;
        }
        return true;
    };
    
    this.validate = function(){
        
        return this.regla();
    }

}