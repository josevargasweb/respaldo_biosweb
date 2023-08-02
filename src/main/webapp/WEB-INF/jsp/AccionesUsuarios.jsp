<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Registro de Usuarios</title>
        <jsp:include page="common/Styles_1.jsp"/>
    </head>
    <body id="kt_body" class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
        <div class="row menu-container">
            <div class="col-md-6">
                <jsp:include page="common/Menu_dinamico.jsp" />
            </div>
            <div class="col-md-6">
                <jsp:include page="common/Header_1.jsp" />
            </div>
        </div>
        <jsp:include page="common/AlertaSession.jsp"/>
        <div class="container container-main-content container-configuracion  mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <c:if test="${mensaje!=null}"><div class="alert alert-success" role="alert">${mensaje}</div></c:if>
                    <div class="card border-15">
                        <div class="card-header border-15" id="accordionHeader">
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10"
                                data-toggle="collapse"
                                data-target="#collapseHeader" aria-expanded="false"
                                aria-controls="collapseHeader">B&uacute;squeda de Usuarios</h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    
                                    <a id="btnLimpiarFiltro" class="navi-link"> 
                                        <span class="symbol symbol-30 symbol-circle "> 
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span> 
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content"
                            aria-labelledby="accordionHeader"
                            data-parent="#accordionHeader">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Nombre</label>
                                            <div class="col-8">
                                                <input id="txtNombreFiltro" class="form-control" type="text" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label id="" class="col-4 col-form-label">Centro de salud</label>
                                            <div class="col-8">
                                                <select class="form-control selectpicker disabledForm" id="codCentroFiltro">
                                                    <option value="N">Seleccionar</option>
                                                    <c:forEach var="centrodesalud" items="${listaCentrosdeSalud}">
                                                        <option value="${centrodesalud.ccdsIdcentrodesalud}">${centrodesalud.ccdsDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group row mb-0">
                                            <label class="col-4 col-form-label">Cargo</label>
                                            <div class="col-8">
                                                <select class="form-control selectpicker disabledForm" id="codCargoFiltro">
                                                    <option value="N">Seleccionar</option>
                                                    <c:forEach var="cargo" items="${listaCargosUsuario}">
                                                        <option value="${cargo.ldvcuIdcargo}">${cargo.ldvcuDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <label>
                                            <input id="chkMostrarActivos" type="checkbox"> Mostrar solo activos
                                      </label>
                                </div>
                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
    
                               
                                <div class="col">
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Usuario</th>
                                                    <th scope="col">Nombre</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron m&eacute;dicos con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">Usuarios encontrados: <span id="spanCantidad"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                    <div id="panelUsuariosResultados" class="card border-15 mt-5">
                        <div class="card-header pl-2  border-10"> 
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                   Trazabilidad de Usuario :
                                </h3>           
                              <input type="hidden" id="idUsuarioEvento" >
                             <div class=" d-flex justify-content-end">
                              
                              <input  type="text" id="textBuscarEvento" class=" form-control-sm mr-4"/>
	             			<input  type="button" class="btn btn-blue-primary btn-normal" id="buscarEventoUsuario" value="Buscar"/>
                            
                                        <button id="btnExcelEvento" class="btn btn-blue-primary btn-normal ml-8 mr-4">
										<i class="fa fa-download" aria-hidden="true"></i>Descargar Excel</button>
										
										 <a id="btnLimpiarTabla" class="navi-link"> 
                                        <span class="symbol symbol-30 symbol-circle "> 
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon" href="#" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span> 
                                        <span class="navi-text"></span>
                                    </a>
                             </div>
                            </div>
                            <div>
                            	<h3 id="tituloAcciones" class="card-label col-12 pl-0 text-center">
                                 
                                </h3>
                            </div>
                        </div>
                         </div>   
						 <div class="tab-pane fade hide active" id="UsuarioEvents" role="tabpanel" aria-labelledby="UsuarioEvents">
							    <div class="form-group row align-items-center col-12">
							       <div class="col scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 500px;overflow-y: auto;margin-right: 0px;padding-right: 0px;">
							            <table id="tb-tabla" class="table table-hover table-striped nowrap compact-xs">
							                <thead>
							                    <tr>
							                        <th scope="col">Fecha</th>
							                        <th scope="col">Usuario</th>
							                        <th scope="col">IP</th>							                      					                       
							                        <th scope="col">Evento</th>
							                    </tr>  
							                </thead>
							               <tbody id="UsuarioEventsTable" class="nowrap">
							                </tbody>
							            </table>
							        </div>
							    </div>
							</div>   
							<br>
						        <div class="text-center">       
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
                       		 </div>
	
            </div>
            				
           
        </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.15.6/xlsx.full.min.js"></script>
        <jsp:include page="common/Scripts.jsp"/>
            <script src="<%=request.getContextPath()%>/resources/js/Constantes.js"></script>
      
          <script src="<%=request.getContextPath()%>/resources/js/registrousuarios/RegistroUsuarios.js"></script>
           <script src="<%=request.getContextPath()%>/resources/js/trazabilidad/AccionesUsuarios.js"></script>
             <script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>
    </body>
</html>
