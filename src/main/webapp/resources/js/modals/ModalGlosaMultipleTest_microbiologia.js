class GMEvent {
}

//$(".switch-gm input")
class switchButton {

  jqId;
  myGmValueArray;
  

  constructor(jqId,gmValueArray) {
    this.jqId = jqId;
    this.myGmValueArray = gmValueArray;
  }

  cambiarOrdenGMSwitch() {

    let dad = $(this).parent();
    if ($(this).is(":checked")) {
      dad.addClass("switch-orden-checked");
      $('.label-panel').removeClass('text-black-50');
      $('.label-examen').addClass('text-black-50');
      this.myGmValueArray.alphOrder = true;
    } else {
      dad.removeClass("switch-orden-checked");
      $('.label-panel').addClass('text-black-50');
      $('.label-examen').removeClass('text-black-50');
      this.myGmValueArray.alphOrder = true;
    }
  }
}

function cambiarOrdenGMSwitch() {
  var dad = $(this).parent();
  if ($(this).is(":checked")) {
    dad.addClass("switch-orden-checked");
    $('.label-panel').removeClass('text-black-50');
    $('.label-examen').addClass('text-black-50');
    this._gmValueArray.alphOrder = true;
  } else {
    dad.removeClass("switch-orden-checked");
    $('.label-panel').addClass('text-black-50');
    $('.label-examen').removeClass('text-black-50');
    this._gmValueArray.alphOrder = true;
  }
}

const eventoLoaded = new Event('gmLoaded');
let eventoSave = null;

class DataGlosaMultiple extends EventTarget {

  lstGlosas;
  gmViewerId;
  jqIdgmViewerId;
  subscribed;

  constructor(pgmViewerId) {
    super();
    this.gmViewerId = pgmViewerId;
    this.jqIdgmViewerId = "#".concat(pgmViewerId)
  }

  subscribeLoad(object) {
    this.subscribed = object;
  }

  get lstGlosas() { return lstGlosas; }

  getGMTestDomain(idTest) {

    let callback = this.setLstGlosas.bind(this);
    $.ajax({
      type: "GET",
      url: gCte.getctx + `/api/test/${idTest}/glosamultiple`,
      success: function(data) {
        switch (data.status) {
          case 404:
            handlerMessageWarn(data.message);
            return;
          case 500:
            handlerMessageError(data.message);
            return;
          case 200:
            callback(data.dato);
        }
      },
      error: function(msg) {
        console.log(msg);
        handlerMessageError(msg);
      },
      contentType: 'application/json; charset=utf-8'
    });
  }

  saveGMTestResult(tstLst) {
    console.log("tstLst",tstLst)

    let table = $("#resultadosExamenesOrdenesDatatable").DataTable();
    let fila = document.getElementById(`${tstLst.df_NORDEN}_${tstLst.dfe_IDEXAMEN}_${tstLst.dfet_IDTEST}`);
    let row = table.row($(`tr#${tstLst.df_NORDEN}_${tstLst.dfe_IDEXAMEN}_${tstLst.dfet_IDTEST}`));
    let data = row.data();
    data.result = tstLst.dfet_RESULTADO.replace(/,$/, '');
    let url =""
    $.ajax({
      url: `${gCte.getctx}/api/microbiologia/updateTestList`,
      method: 'POST',
      data:JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json',
      success: function(datos) {
        if(datos.status !== undefined && datos.status == 200){
          console.log('dato recibido',datos);
          let recibido = {
            "id": datos.dato.id,
            "estado":datos.dato.estado,
            "examid": datos.dato.examid,
            "orderid": datos.dato.orderid,
            "decimales": datos.dato.decimales,
            "testid": datos.dato.testid,
            "isculture": datos.dato.isculture,
            "idmicrobiologystatus": datos.dato.idmicrobiologystatus,
            "idstatus": datos.dato.idstatus,
            "samplecode": datos.dato.samplecode,
            "labelcode": datos.dato.labelcode,
            "cultivo": datos.dato.cultivo,
            "resulttype":datos.dato.resulttype,
            "status":  data.status,
            "ctrcodigo":datos.dato.ctrcodigo,
            "result": datos.dato.result,
            "microbiologystatus": datos.dato.microbiologystatus,
            "test": datos.dato.test,
            "resultadomision":  datos.dato.resultadomision,
            "resultadoobligado": datos.dato.resultadoobligado,
            "resultadonumerico": datos.dato.resultadonumerico,
          }
          row.data(recibido);
          table.draw(false);

          $("#glosaMultipleModal").modal('hide');
        }
      }
    });

    // $.ajax({
    //   type: "POST",
    //   data: JSON.stringify(tstLst),
    //   url: gCte.getctx + "/api/orden/update/examen/test/resultado",
    //   success: function(data) {
    //     switch (data.status) {
    //       case 404:
    //         handlerMessageWarn(data.message);
    //         return;
    //       case 500:
    //         handlerMessageError(data.message);
    //         return;
    //       case 200:
    //         handlerMessageOk(data.message);
    //     }
    //   },
    //   error: function(msg) {
    //     console.log(msg);
    //     handlerMessageError(msg);
    //   },
    //   contentType: 'application/json; charset=utf-8'
    // });
  }

  setLstGlosas(datos) {
    this.lstGlosas = datos;
    this.subscribed.dispatchEvent(eventoLoaded);
  }
}

class VisualGlosaMutiple {

  gmViewerId;
  jqIdgmViewerId;
  subscribed;
  _gmValueArray;

  constructor(pgmViewerId) {
    this.gmViewerId = pgmViewerId;
    this.jqIdgmViewerId = "#".concat(pgmViewerId);
    this.getGMTestResults = this.getGMTestResults.bind(this);
    $("#btnSaveGlosaMultiple").click(this.getGMTestResults);
    this._gmValueArray = new GMValueArray(false);
  }

  fillGMModal(listaGlosas,listaCheckeada) {
    const jqTag = $(this.jqIdgmViewerId);
    jqTag.html("");
    listaGlosas.forEach(glosa => { 
      let checked = "";
      if (listaCheckeada.indexOf(glosa.cgDescripcion) !== -1) {
        checked = "checked";
      }
      jqTag.append(`<label><input type="checkbox" id="chkgmId-${glosa.cgIdglosa}" data-idGlosa="${glosa.cgIdglosa}"  value="${glosa.cgDescripcion}"  ${checked}/>${glosa.cgDescripcion}</label>`); 
    
    });
    let inputs = $("#valGlosaMultiple > label>input");

    let n = inputs.length;
    let me = this;
    $("#valResultados").html("");
    if(listaCheckeada.length > 1){
      $("#valResultados").html(listaCheckeada.toString());
      listaCheckeada.forEach(element => {
        me._gmValueArray.add(element);
      });
    }

    for (let i = 0; i < n; i++) {
      $("#" + inputs[i].id + "").click(function(e) {
        if ($(this).is(':checked')) {
          e.currentTarget.value;
          me._gmValueArray.add(e.currentTarget.value);
          //          alert(me._gmValueArray.getText());          
        }
        else {
          e.currentTarget.value;
          me._gmValueArray.remove(e.currentTarget.value);
          //          alert(me._gmValueArray.getText());          
        }
        $("#valResultados").html(me._gmValueArray.getText());

      });
    }
  }

  subscribe(object) {
    this.subscribed = object;
  }


  getGMTestResults() {
    let inputs = $("#valGlosaMultiple > label>input");
    // alert(inputs.length);
    if (inputs.length === 0) {
      handleMessagerError('Debe escoger al menos una glosa.');
      return;
    };
    eventoSave = new CustomEvent('gmSave', {
      detail: {
        name: inputs
      }
    });
    this.subscribed.dispatchEvent(eventoSave);
  }

}

class ControllerGlosaMultiple extends EventTarget {

  _visualGM;
  _dataGM;
  _nOrden;
  _idExamen;
  _idTest;
  _value;
  _gmValueArray;

  clickController(e) {
    this._dataGM.getGMTest(idTest);
  }

  constructor(visualGM, dataGM, _nOrden,
    _idExamen,
    _idTest,
    _value
  ) {
    super();
    this._nOrden = _nOrden;
    this._idExamen = _idExamen;
    this._idTest = _idTest;
    this._value = _value;

    this._visualGM = visualGM;
    this._dataGM = dataGM;
    const jqTag = $(this._visualGM.jqIdgmViewerId);
    this._visualGM.click = this.clickController.bind(this);
    this.saveResult = this.saveResult.bind(this);
    jqTag.click = this._visualGM.click;
    this.addEventListener('gmLoaded', (e) => {
      e.currentTarget._visualGM.fillGMModal(this._dataGM.lstGlosas,this._value);
    }, false);
    this.addEventListener('gmSave', this.saveResult);

  }

  saveResult(e) {
    const inputs = e.detail.name;
    const n = inputs.length;;
    if (n === 0) {
      handlerMessageError('Debe escoger al menos una glosa.');
      return;
    }
    let testResult = new Object();
    testResult.df_NORDEN = this._nOrden;
    testResult.dfe_IDEXAMEN = this._idExamen;
    testResult.dfet_IDTEST = this._idTest;
    testResult.dfet_RESULTADO = "";
    testResult.ctr_CODIGO = "GM";

    for (var i = 0; i < n; i++) {
      if (inputs[i].checked === true) {
        testResult.dfet_RESULTADO = testResult.dfet_RESULTADO.concat(inputs[i].value).concat(',');
      };
    }
    this._dataGM.saveGMTestResult(testResult);
  }
}


class GMValueArray {

  strArray;
  alphOrder;

  get alphOrder() { return this.alphOrder; }
  set alphOrder(_alphOrder) { this.alphOrder = _alphOrder; }

  constructor(alphOrderBool) {
    this.strArray = new Array();
    this.alphOrder = alphOrderBool;
  }

  getText() {
    let text = "";
    if (this.alphOrder === true) {
      this.strArray = this.strArray.sort();
    }
    this.strArray.forEach(s => { text = text.concat(s).concat(',') });
    if (text.length > 0 && text.endsWith(',')) {
      text = text.substr(0, text.length - 1);
    }
    return text;
  }

  add(value) {
    this.strArray.push(value);
  }

  remove(value) {
    this.strArray = this.strArray.filter(s => (s !== value));
  }
}


/* glosa multiple */

function setGMClick() {
  let gmButtons = $("button[data-tipo-resultado='GM']");
  n = gmButtons.length;
  for (let i = 0; i < n; i++) {
    gmButtons[i].onclick = gmClick;
  }
}

/* Responde a click en boton glosa multiple */

function gmClick(e) {
  var gGlosaMultiple = new DataGlosaMultiple("valGlosaMultiple");
  var gVisualGlosaMutiple = new VisualGlosaMutiple("valGlosaMultiple");
  var gControllerGlosaMultiple = new ControllerGlosaMultiple(gVisualGlosaMutiple, gGlosaMultiple, parseInt(e.target.dataset['norden']), parseInt(e.target.dataset['idexamen']), parseInt(e.target.dataset['idtest']), e.target.dataset['value'].split(","));
  gGlosaMultiple.subscribeLoad(gControllerGlosaMultiple);
  gVisualGlosaMutiple.subscribe(gControllerGlosaMultiple);
  gGlosaMultiple.getGMTestDomain(e.target.dataset['idtest']); //parseInt();
  $("#glosaMultipleModal").modal('show');
}
