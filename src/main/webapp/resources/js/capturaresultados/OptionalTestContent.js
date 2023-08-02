class OptionalTestContent {

  examId;
  orderId;
  showExamOptionalTest;
  optionalTestHtmlTemplate

   

  constructor() {
    this.me = this;
    this.showExamOptionalTest = this.showExamOptionalTests.bind(this);
    this.optionalTestHtmlTemplate = `<div class="col">
                                    <div class="form-group row">
                                        <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                            <input data-testid="{ctIdtest}" id="optionalTest-{ctIdtest}" name="exopcional" type="checkbox" class="" onchange="optionalTestDisplay( '{ctIdtest}' )" onclick="comprobarSeleccionado()">{ctAbreviado}
                                            <span></span>
                                        </label>
                                    </div>
                                  </div>`;
 

  }
  
    

  examOpcTest(examId, orderId) {
    this.examId = examId;
    this.orderId = orderId;
    let url = gCte.getctx + "/api/orden/".concat(orderId).concat("/examen/").concat(examId).concat("/test/opcionales");
    let ctx = this;
    $.ajax({
      type: "get",
      url: url,
      datatype: "json",
      success: function(data) {
        if (data.status === 200) {
          $('#ExamCanvasOptionalTests').addClass('show active');     
          ctx.showExamOptionalTests(data.dato);
        }
      },
      error: function(msg) {
        console.log(msg);
      }
    });
  };

  showExamOptionalTests(tests) {
    $("#optionalTestsContainer").empty();
    $("#examOptionalTestsButton").addClass('disabled')
    let ctx = this;
    console.log("este es el test")
    console.log(tests)
    if(!tests.length == 0){
  		$("#optionalTestsContainer").append(`<div class="col">
                                    <div class="form-group row">
                                        <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                            <input data-testid="" id="selecionartodo" name="seleccionar" type="checkbox" class="" onchange="SelecionarTodos() " >Seleccionar todo
                                            <span></span>
                                        </label>
                                    </div>
                                  </div>`)
    }
    tests.forEach(function(test) {
      $("#optionalTestsContainer").append(ctx.fillTemplate(ctx.optionalTestHtmlTemplate, test));
      $("#optionalTest-" + test.ctIdtest).prop("checked", false);
      ctx.optionalTestDisplay(test.id);
    });


    let formulario = $("#optionalTestsContainer")
    const inputTetOpcionales = formulario.find("input:checkbox[data-testid]:not(#selecionartodo)")
    inputTetOpcionales.off("change", verificarTestOpcionales)
    inputTetOpcionales.on("change", verificarTestOpcionales)

  function verificarTestOpcionales(){
    let unoChecked = false
      inputTetOpcionales.each(function() {
          let input = $(this)
          if (input.prop("checked")) {
            unoChecked = true
          }
        })
        
        if(unoChecked){
          $("#examOptionalTestsButton").removeClass('disabled')
        }else{
          $("#examOptionalTestsButton").addClass('disabled')
        }
  }



  };
  


  optionalTestDisplay(id) {
    if ($("#optionalTest-" + id).is(':checked')) {
      $("#testsListDataRow-" + id).attr('style', '');
    } else {
      $("#testsListDataRow-" + id).attr('style', 'display: none');
    }
  };

  fillTemplate(template, data) {
    const pattern = /{\s*(\w+?)\s*}/g;
    return template.replace(pattern, (_, token) => data[token] || '');
  };

  getTestSelected() {

    let testsSeleccionados = new Array();
    tests.forEach((n) => {
      if (n.checked) { let testId = n.dataset.testid; testsSeleccionados.push(testId); }
    }
    );
    return testsSeleccionados;
  }

  guardarTestOpcionales() {

    let tests = getTestSelected();
     if(tests.length > 0){
    let url = gCte.getctx + `/api/orden/${this.orderId}/examen/${this.examId}/test/opcionales`;
    let postData = JSON.stringify(tests);
    $.ajax({
      type: "POST",
      url: url,
      data: postData,
      success: exito,
      error: fracaso,
      contentType: 'application/json; charset=utf-8'
    });
       handlerMessageOk("test opcionales agregados");
	   $("#opcTestExamenModal").modal('hide');
	   
   }else{
		 handlerMessageWarning("debe seleccionar algun test")
	}
  }
 


}

function comprobarSeleccionado(){

	  $('#selecionartodo').prop("checked", false)
}
function SelecionarTodos(){
	
if( $('#selecionartodo').prop('checked') ) {  
	document.querySelectorAll('#ExamCanvasOptionalTests input[type=checkbox]').forEach(function(checkElement) {
        checkElement.checked = true;
         });
         $("#examOptionalTestsButton").removeClass('disabled')
	}else{	
	document.querySelectorAll('#ExamCanvasOptionalTests input[type=checkbox]').forEach(function(checkElement) {
        checkElement.checked = false;
    });

    $("#examOptionalTestsButton").addClass('disabled')
    }
    
 
}