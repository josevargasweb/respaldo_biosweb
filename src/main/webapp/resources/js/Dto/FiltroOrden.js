/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function FiltroOrden(){
    
    this.nOrden=0;
    this.fIni=null;
    this.fFin=null;
    this.nombre=null;
    this.tipoDocumento=null;
    this.nroDocumento=null;
    this.tipoAtencion = null; 
    this.procedencia=null;
    this.localizacion=null;
    this.apellido=null;
    
    this.fill= function(id_nOrden,id_fechaIni,id_fechaFin,id_nombrePaciente,
                        id_apellido,id_tipoDocumento,id_nDocumento,id_tipoAtencion,
                        id_procedencia,id_servicio){
        
        this.nOrden = $(id_nOrden).val();
        this.fIni=$(id_fechaIni).val();
        this.fFin=$(id_fechaFin).val();
//        new Intl.DateTimeFormat('es-ES').format(date);
        this.nombre=$(id_nombrePaciente).val();
        this.tipoDocumento=$(id_tipoDocumento).val();
        this.nroDocumento=$(id_nDocumento).val();
        this.tipoAtencion = $(id_tipoAtencion).val(); 
        this.procedencia=$(id_procedencia).val();
        this.localizacion=$(id_servicio).val();
        this.apellido=$(id_apellido).val();
//        this.eMail=$(id_eMail).value();
    };
}

