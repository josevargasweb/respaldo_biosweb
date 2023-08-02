<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BIOS-LIS | Toma de Muestras</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tables.css"/>
        <jsp:include page="common/FileInputStyles.jsp"/>
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                    <jsp:include page="common/Menu_dinamico.jsp"/>
            </div>
            <div class="col-md-6">
                    <jsp:include page="common/Header_1.jsp" />
            </div>
	    </div>
        <div class="container container-main-content container-toma-muestras">
            <div class="sinpadding h-100">
                <div class="card h-100">
                    <div class="card-header mb-0">
                        <h3 class="mb-0 btn-block text-left pl-0 pt-1 pb-1 col-10">Toma de Muestra</h3>
                    </div>
                        <div class="card-body p-0">
                            <div class="col-12">
                                <div class="card mt-3 ml-1" id="divTablaOrdenes">
                                    <div class="card-header mb-0  cursor-pointer">
                                    <input class="col-12 ocultar" id="volverNomalidad" type="button" value="TMclick activo" style="visibility: hidden" />
                                    <input type="hidden" id="sesionUsuario" name="sesionUsuario" value="${usuario.duIdusuario}" />
                                    <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
                                    <input type="hidden" id="estadosPacienteLoad" name="estadosPacienteLoad" value="${listaEstadosStr}" />
                                    <input type="hidden" id="flebotomista" name="flebotomista" value="${perfilUsuario.cpuFlebotomista}" />
                                    <div class="card-title d-flex justify-content-between">
                                        <h4 class="mb-0 btn-block text-left pl-0 col-9  cursor-pointer icon-accordion-sm" data-toggle="collapse" data-target="#tablaOrdenesCollapse" aria-expanded="true">
                                            &Oacute;rdenes
                                        </h4>
                                        <div class="col-3 justify-content-right">
                                            <div class="float-right">
                                                <button id="actualizarTabla" type="button" class="btn btn-sm btn-secondary mr-2">
                                                    <i class="fas fa-redo-alt" aria-hidden="true"></i>
                                                </button>
                                                <label class="mb-0">
                                                    <input id="checkRealTime" class="mr-2" name="exitus" type="checkbox" checked>Autom&aacute;tica
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    </div>
                                    <div class="agregandoClass card-body p-0">
                                        <div id="tablaOrdenesCollapse" class="collapse show pl-2 pr-2 no-th-first hidden">
                                            <div class="col-12">
                                                <div class="row justify-content-between">
                                                    <div class="col-2">
                                                        <div class="row">
                                                            <label for="filtroFecha" class="mb-0 font-weight-bold">Fecha</label>
                                                            <input type="text" id="filtroFecha" class="form-control form-control-sm datatable-input kt_datepicker_1_modal col-6 ml-1" readonly placeholder="" id="kt_datepicker_1_modal"
                                                            data-col-index="1"/>
                                                        </div>
                                                    </div>
                                                    
                                                    <span id="dt_registros" class="pl-2"></span>
                                                </div>
                                            </div>
                                            <div class="card card-2 mt-3">
                                                <div id="tableContainer" class="card-body p-0 cp-container">
                                                    <!-- <table class="table table-hover table-striped nowrap compact-xs tabla-wd" id="tablaPrioridadAtencion"> -->
                                                    <table class="table table-hover table-striped stripe row-border order-column compact-xs-2" id="tablaPrioridadAtencion" style="width:100%">
                                                        <thead>
                                                            <tr>
                                                                <th>Sel.</th>
                                                                <th>Hora</th>
                                                                <th>Orden</th>
                                                                <th>Paciente</th>
                                                                <th>Edad</th>
                                                                <th>Estado</th>
                                                                <th>Prioridad</th>
                                                                <th>Observaci&oacute;n</th>
                                                                <th>Tipo de Atenci&oacute;n</th>
                                                                <th>Procedencia</th>
                                                                <th>Servicio</th>
                                                                <th>Acciones</th>
                                                            </tr>
                                                        </thead>
                                                      
                                                    </table>
                                                </div>
                                            </div>
                                            <span id="dt_info_registro"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12" id="divTablaMuestras" style="display:none;">
                                <div class="containerTableMuestras col-12 pl-0 pr-0 hidden">
                                    <div class="row">
                                        <div class="col-10 pr-0">
                                            <div class="card ml-1 " >
                                                <div class="card-header mb-0" >
                                                    <h4 class="pl-2"  >
                                                        Toma de muestra - Muestras (Nro. Orden: <label id="lblNroOrden"></label>)
                                                    </h4>
                                                </div>
                                                <div class="card-body p-0" id="tablaMuestrasCollapse">
                                                    <span id="dt_registros_m" class="p-3"></span>
                                                    <div class="card card-2 mt-3">
                                                        <div class="card-body p-0 no-th-first">
                                                            <table class='table table-hover table-striped nowrap compact-xs tabla-wd' id="tablaMuestras">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Sel</th>
                                                                        <th>Muestra</th>
                                                                        <th>Contenedor</th>
                                                                        <th>Estado</th>
                                                                        <th>Flebotomista</th>
                                                                        <th>N&deg; Muestra</th>
                                                                        <th>Observaci&oacute;n</th>
                                                                        <th>Acciones</th>
                                                                    </tr>
    
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <label id="idPacienteTabla"></label>
                                                    <label id="idOrdenTabla"></label>
                                                    <label id="idUsuario"></label>
                                                </div>
                                            </div>
                                        </div>
                                          <!-- PANEL DE ACCIONES -->
                                        <div class="col-2 pl-0 mt-3" style="">
                                            <div class="card  card-acciones" style="border:1px solid #f0f0f0;">
                                                <div class="card card-custom mt-5" style="display:none">
                                        
                                                    <label id="lblPaciente"></label>
                                                </div>
                                                <c:if test = "${perfilUsuario.cpuFlebotomista == 'S'}">
                                                    <div class="card-body">
                                                       
                                                        <div class="col-12 pl-0 pr-0 d-flex justify-content-center">
                                                            <div class="col-xl-11 col-md-12 p-1">
                                                                <div class="circle-container d-flex justify-content-center mt-3 mb-2">
                                                                    <div class="circle">1</div>
                                                                </div>
                                                                <div class="d-flex align-items-end justify-content-center">
                                                                    <button id="btnImpresionEtiquetas" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 no-cursor w-100" disabled>
                                                                        <img src="<%=request.getContextPath()%>/resources/img/barcode.png" width="30" />
                                                                        Imprimir etiquetas
                                                                    </button>
                    
                                                                </div>
                                                                <div class="circle-container d-flex justify-content-center mt-3 mb-2">
                                                                    <div class="circle">2</div>
                                                                </div>
                                                                <div class="mb-2 d-flex align-items-end justify-content-center">
                                                                    <button id="btnConfirmarTomaMuestra" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 w-100" disabled>
                                                                        <img src="<%=request.getContextPath()%>/resources/img/confirm-file.png" width="30" style="filter: brightness(0) invert(1);"/>
                                                                        Tomar muestras
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="mt-4 d-flex align-items-end justify-content-center">
                                                            <button type="button" class="btn p-1" disabled>
                                                                <img src="<%=request.getContextPath()%>/resources/img/scanner.png" width="30" height="30" />
                                                            </button>
                                                            <label>
                                                                Registro c&oacute;digo de barras
                                                            </label>
                                                        </div>
                                                        <div class="ml-4 mr-4">
                                                            <input type='text' class="form-control form-control-sm p-1" id="txtIngresoCodigoBarras" onkeypress="ingresoCodigoBarras()" style="text-transform:uppercase" />
                                                        </div>
                                                        <div class="mt-4 ml-3">
                                                            <button type="button" class="btn p-1" disabled>
                                                                <img src="<%=request.getContextPath()%>/resources/img/syringe.png" width="30" height="30" />
                                                            </button>
                                                            <label>
                                                                Flebotomista 
                                                            </label>
                                                        </div>
                                                        <div class="ml-4 mb-5 mr-4">
                                                            <input type="hidden" value="${listaUsuarios}" id="listaFlebotomistas" />
                                                            <select id="selectUsuario" name="dUsuario" class="form-control selectpicker" disabled>
                                                                <option value="N" selected>Elegir</option>
                                                                <c:forEach var="user" items="${listaUsuarios}">
                                                                    <option value="${user.duIdusuario}" ${user.duIdusuario == usuario.duIdusuario ? 'selected' : ''} >${user.duNombres} ${user.duPrimerapellido}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </c:if>
                                                    <c:if test = "${perfilUsuario.cpuFlebotomista == 'N'}">
                                                        <p>Usuario no posee permisos de flebotomista</p>
                                                    </c:if>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>
            
            </div>
        </div>
        <jsp:include page="common/AlertaSession.jsp"/>
        <jsp:include page="modal/ModalAntecedentesPac.jsp"/>
        <jsp:include page="modal/ModalDatosPaciente.jsp"/>
        <jsp:include page="modal/ModalDatosOrden.jsp"/>
        <jsp:include page="modal/ModalDocumentosOrden.jsp"/>
        <!-- MODAL INDICACIONES -->
        <div id="exampleModal4" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-body">
                        <h4 id="DatosOrdenModalTitulo" class="text-center">Indicaciones Toma de Muestra  </h4>
                            <div class="row">
                                <div class="col-8">
                                    <div class="col-12">
                                        <label><b>Indicaciones para el Paciente</b></label> 
                                        <div class="incidacionPacienteContainer">
                                            <ul id="indicacionesPaciente"></ul>
                                        </div>
                                    </div>
                                    <div class="col-12 mt-5">
                                        <label><b>Indicaciones para Toma de Muestra</b></label> 
                                        <div class="incidacionPacienteContainer">
                                            <ul id="indicacionesTM"></ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="col-12 d-flex justify-content-center">
                                        <button type="button" class="btn btn-secondary" ><i class="fas fa-print"></i></button>
                                    </div>
                                    <div class="col-12 mt-5">
                                        <h5>Volumen m&iacute;nimo</h5> 
                                        <div id="antecedentesEstatura" class="col-12 pl-0 mt-2 line-height-1">
                                            <div class="row">
                                                <label class="col-md-4 col-xl-3 pr-0 line-height-2">Adulto</label> 
                                                <label  id="adultoVolumen"  class=" mr-1 ml-md-1 ml-xl-0"  style="width: 30%"></label>
                                                <!-- <input disabled id="adultoVolumen" type="text" class="form-control mr-1 ml-md-1 ml-xl-0" autocomplete="off" style="width: 30%"/> -->
                                                <label class="line-height-2">mL</label>
                                            </div>
                                        </div>
                                        <div id="antecedentesEstatura" class="col-12 pl-0 mt-3 line-height-1">
                                            <div class="row">
                                                <label class="col-md-4 col-xl-3 pr-0 line-height-2">Pedi&aacute;trico</label> 
                                                <label  id="pediVolumen"  class="mr-1 ml-md-1 ml-xl-0"  style="width: 30%"></label>
                                                <!-- <input disabled id="pediVolumen" type="text" class="form-control mr-1 ml-md-1 ml-xl-0" autocomplete="off" style="width: 30%"/> -->
                                                <label class="line-height-2">mL</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div id="ModalDatosOrdenTablaExamenes" class="col-12 mt-5" style="overflow-x:auto;">
                                        <h5 >Estabilidad</h5>
                                        <table class='table-fixed table table-hover  table-separate table-head-custom table-checkable dataTable dtr-inline collapsed' id="tablaPrioridadAtencion4">
                                            <thead>
                                                <tr>
                                                    <td>Examen</td>
                                                    <td>T&deg; Ambiente</td>
                                                    <td>Refrigerado 4 - 8&deg;C</td>
                                                    <td>Congelado - 20 &deg;C</td>
                                                </tr>
                                            </thead>
                                            <tbody id="tablaTomaMuestra"></tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                            </div>
                    </div>
               
                </div>
            </div>
        </div>
        <!-- MODAL DETALLE MUESTRA -->
        <div id="modalDetalleMuestra" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-body">
                        <h4 class="text-center">Cambio Tipo de Muestra</h4>
                        <div class="datosPacienteOrden col-12 mt-3 row d-flex flex-column"></div>
                        <div id="tableTipoMuestra" class="col-12 mt-3">
                            <table class='table table-hover table-striped nowrap dataTable no-footer' id="tablaTipoMuestra" style="width:100%;">
                                <thead>
                                    <tr>
                                        <th>Examen</th>
                                        <th>C&oacute;digo</th>
                                        <th>Muestra</th>
                                        <th>Contenedor</th>
                                        <th>Comparte Muestra</th>
                                    </tr>
                                </thead>
                            </table>

                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between">
                            <button id="guardarDetalleMuestra" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2">Guardar</button>
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="modalZonaCuerpo" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <h4 class="text-center">Cambio de Sitio anat&oacute;mico</h4>
                        <div class="d-flex flex-wrap justify-content-center align-items-center">
                            <div class="datosPacienteOrden col-12 mt-2 line-height-1-5"></div>
                            <div id="rowZonaCuerpo" class="col-12 mt-3 row">
                                <div class="col-12">
                                    <label class="col-form-label">Sitio anat&oacute;mico</label>
                                    <select id="selZonaCuerpo" class="form-control selectpicker" data-live-search="true"></select>
                                </div>
                            </div>
                            <div id="rowErrorZC" class="col-12 mt-5">
                                <label class="col-form-label">Debe primero imprimir etiquetas para cambiar sitio anat&oacute;micoo.</label>
                            </div>
                            <div id="rowSinPermiso" class="col-12 mt-5">
                                <label class="col-form-label">Usuario no tiene permisos para editar sitio anat&oacute;mico.</label>
                            </div>
                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between">
                            <button id="guardarZonaCuerpo" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Guardar</button>
                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL CURVAS -->
        <div id="modalCurvaTolerancia" class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-body">
                        <h4 class="text-center">Curva de Tolerancia</h4>

                        <div class="col-12 row">
                            <div class="col-8 line-height-1-5">
                                 <div class="row">
                                    <div class="col-12 mt-1">
                                        <div class="col-12 pl-0 pr-0">
                                            <label class="text-label">Paciente</label>
                                        </div>
                                        <span class="" id="pacienteCurva"></span>
                                    </div>
                                    <div class="col-12 mt-4">
                                        <div class="col-12 pl-0 pr-0">
                                            <label class="text-label">Examen</label>
                                        </div>
                                        <span class="" id="examenCurva"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-4 row line-height-2">
                                <label for="" class="d-flex align-items-center"> Tiempo transcurrido desde la muestra basal</label>
                                <input disabled id="minsCurva" type="text" placeholder="mm:ss" class="form-control col-3 mr-1" autocomplete="off" /> minutos
                            </div>
                        </div>
                        <div id="divTablaTests">
                            <table class='table table-hover table-striped table-condensed nowrap dataTable no-footer' id="tablaTestsCurva" style="width:100%;">
                                <thead>
                                    <tr>
                                        <th>Tomado</th>
                                        <th>Tests</th>
                                        <th>Minutos post basal</th>
                                        <th>Tomar a las</th>
                                        <th>Tomado a las</th>
                                        <th>N&deg; Muestra</th>
                                        <th>Muestra</th>
                                        <th>Flebotomista</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-end">
                            <button id="guardarDetalleMuestra" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        				<!-- Modal aceptacion -->
				<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog">
					<div class="modal-dialog" role="document">
					  <div class="modal-content">
						<div class="modal-body">
                            <h5 class="modal-title titulo-color"></h5>
                             <div class="modal-text mt-3  d-flex flex-wrap justify-content-center align-items-center"></div>
                             <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                 <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si">Confirmar</button>
                             <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no" data-dismiss="modal">Cerrar</button>
                            </div>						
                        </div>
					  </div>
					</div>
				  </div>
				<!-- Fin Modal -->
        <jsp:include page="common/Scripts.jsp"/>
        <jsp:include page="common/FileInputScripts.jsp"/>
       <script src="https://unpkg.com/imask"></script>
         <script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalAntecedentesPaciente.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDatosPaciente.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDatosOrden.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/common/FileInputBiosbo.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modals/ModalDocumentosOrden.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.init.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.datatables.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.muestras.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.ordenes.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.curvaTolerancia.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/tomamuestras/TomaMuestras.tipoMuestra.js"></script>


    </body>
</html>

