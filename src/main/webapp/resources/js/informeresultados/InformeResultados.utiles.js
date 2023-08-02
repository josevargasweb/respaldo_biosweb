

function clear(classLimpiar) {
    $(classLimpiar).val('');
    $('#tablaOrdenesTotalesBody').empty();
};



function Validator () {
    this.message = "No se cumple"
    this.regla = function (){
        
        let b = true;
        if (b === false){
            this.message = "No se cumple"
        }
        this.message = "ok";
        return b;
    };
    this.validate = this.regla();
    
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

function NroOrdenValidator (jqId) {

    this.message="";
    this.jqId = jqId;
    this.regla = function (){
        
        if ($(this.jqId) === undefined){
            this.message = "Valor indefinido.";
            return false;
        }

        let valor = $(this.jqId).val();

        valor = valor.replaceAll('.','');

        if (isNaN(valor)){
            this.message = "Valor no es un número.";
            return false;
        }

        if (valor === ""){
          return true;
        }

        if (valor<=0){
            this.message = "Valor debe ser positivo";
            return false;
        }
        
        

        return true;
    }
    
    this.validate = function(){
        
        return this.regla();
        
        
    }
} 

function FechaValidator (jqId) {

    this.message="";
    this.jqId = jqId;
    this.regla = function (){
        
        if ($(this.jqId) === undefined){
            this.message = "Valor indefinido.";
            return false;
        }
        
        let valor = $(this.jqId).val() ;
        
        if (valor instanceof Date){
            return true;
        }
        
        let d = ParseFechaCL(valor);
        
        if (d === null){
            return false;
        }
        return true;

    };
    
    this.validate = function(){
        
        return this.regla();
        
        
    };
} 


function FechaIniMenorFinValidator(jqIdFini,jqIdFfin){

    this.message = "Rango de fechas incorrecto.";
    this.fini = $(jqIdFini).val();
    this.ffin = $(jqIdFfin).val();
    
    this.regla = function (){
    
        try{
            if (this.fini === undefined || this.ffin === undefined ){
                    return true;
            }

            if (this.ffin === "" || this.fini === ""){
                return true;
            }

            if (this.fini instanceof Date && this.ffin instanceof Date ){
                return ((this.ffin.getMilliseconds() - this.fini.getMilliseconds())) >= 0
            }

            if (typeof(this.fini === 'string')){
                    this.fini = ParseFechaCL(this.fini);
            }

            if (typeof(this.ffin === 'string')){
                this.ffin = ParseFechaCL(this.ffin);
            }

            if (this.fini !== null && this.ffin !== null){
                return ((this.ffin.getMilliseconds() - this.fini.getMilliseconds())) >= 0
            }
        }
        catch(e){
//            handleMessageError(e);
            console.log(e);
        }
        return false;
    };
    
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

function RutValidator(sRut){
    
    this.dvRegex = /k|K|[0-9]/;
    this.numRegex = /[1-9](0-9)*/;
    this.MyRut = sRut;
    this.separator = '-';
    this.numSeparator = '.';
    this.parts = [];
    this.mantisa = 0;
    this.dv='';
    
    this.normalize = function(){
        
        if (!this.MyRut instanceof String){
            return false;
        }
        this.parts = this.MyRut.split(this.separator);
        if (this.parts.length !== 2){
            return false;
        }
        if (!this.dvRegex.test(this.parts[1])){
            return false;
        }
        this.parts[0] = this.parts[0].replaceAll(this.numSeparator,''); 

        if (!this.numRegex.test(this.parts[0])){
            return false;
        }
        this.mantisa = parseInt(this.parts[0]);
        this.dv = this.parts[1];
 
    };

    this.calculaDV = function (){

        if (isNaN(this.mantisa)){
            return false;
        }
    
        let m=0,s=1;
		let t = this.mantisa;
        for(;t;t=Math.floor(t/10))
            s=(s+t%10*(9-m++%6))%11;
        return s?s-1:'K';
        
    };

	this.verificaDV = function(){
	
		if (this.dv == this.calculaDV()){
			return true;
		}
		else{
			return false;
		}

	}
	
	this.normalize();
    
};


function Usuario(){
    
    this.ldvtd_DESCRIPCION = null;
    this.dp_NRODOCUMENTO = null;
    this.cta_DESCRIPCION = null;
    this.cs_DESCRIPCION = null;
    this.dp_PRIMERAPELLIDO = null;
    this.sdf_FECHAORDEN = null;
    this.dp_SEGUNDOAPELLIDO = null;
    this.cp_DESCRIPCION = null;
    this.sdp_FNACIMIENTO = null; 
    this.cd_DESCRIPCION = null;
    this.ldvs_DESCRIPCION = null;
    this.dp_OBSERVACION = null;
    this.cm_PRIMERAPELLIDO = null;
    this.df_OBSERVACION = null;
    this.cm_SEGUNDOAPELLIDO = null;
    this.dp_EMAIL = null;
    this.dp_NOMBRES = null;
    this.cc_ABREVIADO = null;
    this.df_NORDEN = null;
    this.cm_NOMBRES = null;
    this.dp_IDPACIENTE = null;
    
}



function TableIterator(table){
    
    this.table = table;
    this.currentIndex=-1;
    this.next=function(){
        this.currentIndex++;
        var data = table.rows( this.currentIndex ,{order:'current',page:'current'}).data();
        fillOrdenData(data);    
    }

    this.prev=function(){
        this.currentIndex--;
        var data = table.rows( this.currentIndex ,{order:'current',page:'current'}).data();
        fillOrdenData(data);    
    }
    
    this.setIndex= function(i){
        this.currentIndex=i;
    };

}