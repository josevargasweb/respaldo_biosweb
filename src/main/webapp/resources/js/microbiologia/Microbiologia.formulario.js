

//IIFE 
$(function() {    
  $(document).ready(function(){
    $("#btnEditarAntibiograma").click(editarAntibiograma);
  });

    function editarAntibiograma(e) {//rut
      e.preventDefault();
      
      let dpDto = getFormData();
      if (!validaAntibiogramaSubmit(dpDto)) {
        handlerMessageError('Faltan campos obligatorios');
        return;
      }
      let datos = JSON.stringify(dpDto);
      

      console.log("json data",datos);
      $.ajax({
        url: `${gCte.getctx}/Microbiologia/api/putAntibiogram`,
        method: 'PUT',
        headers: {
          "Content-Type": "application/json"
        },
        data: JSON.stringify(dpDto),
        success: function(datos) {
          try {
            handlerMessageOk("Se actualizo el antibiograma con exito");
            return;
          } catch (e) {
              handlerMessageError("No se pudo modificar la orden." + e.message);
          }
        }
      });
    }


    function getFormData() {
      console.log("antibioticosObj",antibioticosObj);
      let datosAntibiograma = new Object();
      datosAntibiograma.id = antibioticosObj.id;
      datosAntibiograma.orderId = antibioticosObj.orderid;
      datosAntibiograma.examId = antibioticosObj.examid;
      datosAntibiograma.testId = antibioticosObj.testid;
      datosAntibiograma.test = antibioticosObj.test;
      datosAntibiograma.colonyCount = antibioticosObj.colonycount;
      datosAntibiograma.infectionType = antibioticosObj.infectiontype;
      datosAntibiograma.antibioticLists =  antibioticosObj.antibioticLists
      
      

      // cambiar datosAntibiogramaEliminados a datosAntibiograma y eliminar  let datosAntibiogramaEliminados = new Object(); en caso que necesite los objetos eliminados
      let datosAntibiogramaEliminados = new Object();
      datosAntibiogramaEliminados.antibioticListsEliminados = new Object();
      datosAntibiogramaEliminados.methodsListsEliminados = new Object();
      
      let keys = Object.keys(objectoNuevo.antibioticLists);

      $.each(keys, function(index, key0) {
        const difAntibiotics = getDifferenceAntibiotics(antibioticosObj.antibioticLists[key0].antibiotics,objectoNuevo.antibioticLists[key0].antibiotics, "id");
        const difMethods = getDifferenceAntibiotics(antibioticosObj.antibioticLists[key0].methods,objectoNuevo.antibioticLists[key0].methods, "method");

        datosAntibiogramaEliminados.antibioticListsEliminados[key0] = difAntibiotics;

        datosAntibiogramaEliminados.methodsListsEliminados[key0] = difMethods;
      });

      
        return datosAntibiograma;
      
      }

      //copia un objeto de objectos
      function deepFreeze(obj) {
        Object.freeze(obj);
        for (const key in obj) {
          const value = obj[key];
          if (typeof value === "object") {
            deepFreeze(value);
          }
        }
      }

      //diferencia de objectos
      function getDifferenceAntibiotics(leftObjects, rightObjects, property) {
        const result = {};
      
        if(leftObjects != null || rightObjects != null){
            return result;
        }

        const propertyValues = Object.keys(leftObjects).map(key => leftObjects[key][property].toString());

        for (const key in rightObjects) {
          const value = rightObjects[key][property].toString();
      
        if (!propertyValues.includes(value)) {
            result[key] = rightObjects[key];
          }
        }
        return result;
      }


  

      //validaciones si tiene datos
      function validaAntibiogramaSubmit(dpDto) {
       
       validacion = validaAntibiotico(dpDto);
       return validacion;    
      };


      function validaAntibiotico(dpDto){
        let keys = Object.keys(dpDto.antibioticLists)
        let valida = false;
        $.each(keys, function(index, key0) {
          let tieneData = TieneDatosObject(dpDto.antibioticLists[key0].antibiotics);
          if(!valida){
            valida = tieneData;
          }
        });
        return valida;
      }




      function TieneDatosObject(obj) {
        for (const key in obj) {
          const object = obj[key];
          if (Object.keys(object).length > 0) {
            return true;
          }
        }
        return false;
      }
  
});

