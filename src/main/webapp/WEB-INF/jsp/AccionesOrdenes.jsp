<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BiosLIS | Eventos orden</title>
        <jsp:include page="common/Styles_1.jsp"/>
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
        <jsp:include page="common/AlertaSession.jsp"/>
        <div class="container container-main-content container-edicion-ordenes">
            <input type="hidden" id="sesionUsuario" name="sesionUsuario" value="${usuario.duIdusuario}" />
            <!-- VARIABLES DE PERMISOS DE EDICIÓN ORDENES -->
            <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
            <input type="hidden" id="editaSoloOrdsProcedencia" name="editaSoloOrdsProcedencia" value="${perfilUsuario.cpuEditasoloordsprocedencia}" />
            <input type="hidden" id="anulaExamenes" name="anulaExamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
            <!-- -->
            <div class="row d-flex justify-content-center">
                <div class="accordion col-12 pl-0 pr-0" id="edicion-orden-main">
                    <div id="panelSuperior" class="card">
                        <div class="card-header mb-0 border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10"
                                    data-toggle="collapse"
                                    data-target="#panelBusquedaOrden" aria-expanded="true"
                                    aria-controls="panelBusquedaOrden">B&uacute;squeda de Orden</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="btnLimpiarFiltro" class="navi-link"> <span
                                        class="symbol symbol-30 symbol-circle "> <span
                                            id="circuloLimpiarFiltro"
                                            class="symbol-label bg-hover-blue  hoverIcon" href="#"
                                            data-toggle="tooltip" title="Limpiar"> <i
                                                id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span> <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="panelBusquedaOrden"
                        class="collapse container-content show"
                        aria-labelledby="edicion-orden-title"
                        data-parent="#edicion-orden-main">
                            <div class="card-body">
                                <div class="col-12">
                                    <input type="hidden" id="procedenciaUsuario" name="procedenciaUsuario" value="${perfilUsuario.cpuIdprocedencia}" />
                                     <input type="hidden" id="pideautorizeditorden" name="pideautorizeditorden" value="${perfilUsuario.cpuPideautorizeditorden}" />
                                      <input type="hidden" id="anulaExamenes" name="anulaExamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
                                      <input type="hidden" id="puedeEditarORden" name="puedeEditarORden" value="${perfilUsuario.cpuEditasoloordsprocedencia}" />
                                      <input type="hidden" id="registraExamanesDerivados" name="registraExamanesDerivados" value="${perfilUsuario.cpuRegistraexamenesderivados}" />
                                      <input type="hidden" id="eliminarexamenes" name="eliminarexamenes" value="${perfilUsuario.cpuEliminarexamenes}" />
                                    <input type="hidden" id="idSeccion" value="${idSeccion}" /> 
                                    <jsp:include page="desarrollo/Bios_OrdenesEdicion.jsp"></jsp:include>
                                </div>
                            </div>
                        </div>
                    </div>

                                <!-- ********************************************************* EDICION DE ORDENES ***********************************************-->


                    <div id="panelInferior" class="card">
                        <div class="card-header " id="headingRegistroPaciente">
                            <div class="card-title row justify-content-between  ">
                                <h3 class="card-label col-3 pl-0">Trazabilidad de Orden:</h3>
                                 <input type="hidden" id="idOrdenEvento" >
                             <div class=" d-flex justify-content-end">
                             
                             <!--  
                               <button id="btnRetroceder"   type="button" class="btn btn-sm  btn-blue-primary mr-6" title="Retroceder">
                         		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-left-square-fill" viewBox="0 0 16 16">
 									 <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm10.5 10V4a.5.5 0 0 0-.832-.374l-4.5 4a.5.5 0 0 0 0 .748l4.5 4A.5.5 0 0 0 10.5 12z"/>
							</svg>
                       		 </button>
                              <button id="btnAvanzar"   type="button" class="btn btn-sm  btn-blue-primary mr-6" title="Avanzar">
                         		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-square-fill" viewBox="0 0 16 16">
  									<path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm5.5 10a.5.5 0 0 0 .832.374l4.5-4a.5.5 0 0 0 0-.748l-4.5-4A.5.5 0 0 0 5.5 4v8z"/>
								</svg>
                       		 </button>
                       		 -->
                              <input  type="text" id="textBuscarEvento" class=" form-control-sm mr-4"/>
	             			<input  type="button" class="btn btn-blue-primary btn-normal font-weight-bold  " id="buscarEventoOrden" value="Buscar"/>
                            
                                        <button id="btnExcelOrden" class="btn btn-blue-primary btn-normal font-weight-bold ml-8 mr-4">
										<i class="fa fa-download" aria-hidden="true"></i>Descargar Excel</button>
										
										 <a id="btnLimpiarTabla" class="navi-link"> 
                                        <span class="symbol symbol-30 symbol-circle "> 
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span> 
                                        <span class="navi-text"></span>
                                    </a>
                                                    <!-- Inicio Tabuladores -->
                                                   
                                                    <!-- Fin Tabuladores -->

                            </div>
                             
                        </div>
                        <div>
                            	<h3 id="tituloAcciones" class="card-label col-12 pl-0 text-center">
                                 
                                </h3>
                            </div>
                        <!--  ********************************************************** Edición de Orden *****************************************************************  -->
                        
                               
                                    <input id="idPaciente" name="datosPacientes" type="hidden" /> <input
                                    id="idMedico" type="hidden" name="dfIdmedico" /> <input
                                    id="txtEdad" type="hidden" /> <input id="txtEmailP"
                                    name="dfEnviarresultadosemail" type="hidden" /> <input
                                    id="sexoPacienteOrden" type="hidden" /> <input
                                    id="txtPrimerApellidoP" type="hidden" />
                                    
                                  
                                        <!-- inicio Formulario registro -->
                                        <!-- Fin Formulario registro -->
                                        
                                         <div class="tab-pane fade hide active" id="OrdenEvents" role="tabpanel" aria-labelledby="OrdenEvents">
							    <div class="form-group row align-items-center col-12">
							       <div class="col scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 600px;overflow-y: auto;margin-right: 0px;padding-right: 0px;">
							            <table id="tb" class="table table-hover table-striped nowrap compact-xs">
							                <thead>
							                    <tr>
							                        <th scope="col">Fecha</th>
							                        <th scope="col">Usuario</th>
							                        <th scope="col">IP</th>							                      					                       
							                        <th scope="col">Paciente</th>
							                         <th scope="col">N°Orden</th>
							                          <th scope="col">Examen</th>
							                           <th scope="col">Test</th>
							                            <th scope="col">Muestra</th>
							                             <th scope="col">Evento</th>
							                    </tr>  
							                </thead>
							               <tbody id="idOrderCanvasEventsTable" class="nowrap">
							                </tbody>
							            </table>
							        </div>
							    </div>
							</div>   
                      

                       
                    </div>
                <!-- Fin Panel inferior  -->
                </div>
            </div>
        </div>


    
<jsp:include page="common/Scripts.jsp"/>

<!--begin::Global Theme Bundle(used by all pages)-->
<script
  src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<!--end::Global Theme Bundle-->
<script src="https://unpkg.com/imask"></script>
<script
  src="https://cdn.datatables.net/scroller/2.0.6/js/dataTables.scroller.min.js"></script>
<script
  src="https://cdn.datatables.net/select/1.4.0/js/dataTables.select.min.js"></script>
<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/css/bootstrap-select.min.css"
  integrity="sha512-ARJR74swou2y0Q2V9k0GbzQ/5vJ2RBSoCWokg4zkfM29Fb3vZEQyv0iWBMW/yvKgyHSR/7D64pFMmU8nYmbRkg=="
  crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script
  src="<%=request.getContextPath()%>/resources/js/typeahead/typeahead.bundle.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>

<script
  src="<%=request.getContextPath()%>/resources/js/common/dual-data-edicion.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/Dto/CfgServicios.js"></script>
<script
		src="<%=request.getContextPath()%>/resources/js/Dto/CfgPaneles.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloBiosboActualizado.js"></script>
	<script
	src="<%=request.getContextPath()%>/resources/js/common/ModuloMuestras.js"></script>

 

<script
		src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.paneles.js"></script>
<script
		src="<%=request.getContextPath()%>/resources/js/edicionordenes/EdicionOrden.paneles.js"></script>  

		  <script src="<%=request.getContextPath()%>/resources/js/trazabilidad/AccionesOrdenes.js"></script>
          <script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>
           <script  src="<%=request.getContextPath()%>/resources/js/microbiologia/Microbiologia.js"></script>
</body>
</html>

