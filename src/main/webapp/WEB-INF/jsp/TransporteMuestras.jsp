<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BIOS-LIS | Transporte de Muestras</title>
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
      <div class="container container-main-content">
        <div class="row d-flex justify-content-center">
            <div class="accordion col-12 pl-0 pr-0 mb-4">
              <div id="panelInferior" class="card panelInferior border-15">
                <div class="card-header border-15">
                  <div class="card-title row justify-content-between  ">
                      <h3 class="card-label pl-0">Transporte de Muestras </h3>
                  </div>
                </div>
                <div class="card-body">
                  <div class="col-12">
                    <div class="row justify-content-between">
                      <div class="col-7 mt-3">
                        <div class="card card-2 h-100">
                            <div class="card-body">
                                <h3 class="col-12 pl-0 pr-0">Nueva caja</h3>
                                <div class="row mt-4 justify-content-between">
                                    <div class="col-6 row">
                                      <div class="col-xl-6 col-md-8">
                                        <label class="" for="agregarContenedor">Agregar contenedores</label>
                                        <div class="col-12 row ml-0 p-0">
                                            <input id="agregarContenedor" class="form-control formExamen" type="text" onkeypress="ingresoCodigoBarrasTr()" style="text-transform:uppercase"/>
                                        </div>
                                      </div>
                                      <div class="col-4 pl-0">
                                        <button type="button" class="btn pt-3 pl-0" disabled="">
                                          <span class="responsive-image">
                                            <img src="<%=request.getContextPath()%>/resources/img/scanner.png" alt="Scanner">
                                          </span>
                                        </button>
                                      </div>
                                    </div>
                                  <div class="col-4 container-nueva-caja">
                                    <div class="row">
                                      <label class="col-12 pl-0 pr-0" for="agregarEstafeta">Estafeta</label>
                                      <input id="agregarEstafeta" class="form-control formExamen col-xl-6 col-md-6" type="text" />
                                    </div>
                                    <div class="mt-4 row container-nueva-caja">
                                      <label class="col-12 pl-0 pr-0" for="agregarTemp">Temperatura</label>
                                      <input id="agregarTemp" class="form-control formExamen col-xl-6 col-md-6" type="number" />
                                      <label class="col-6" for="agregarTemp">&deg;C</label>
                                    </div>
                                  </div>
                                </div>
                            </div>
                        </div>
                      </div>
                      <div class="col-4 mt-3">
                          <div class="card card-2 h-100">
                              <div class="card-body">
                                  <h3 class="col-12 pl-0 pr-0">Búsqueda</h3>
                                  <div class="col-12 mt-4">
                                      <div class="col-12 row m-0 p-0">
                                          <label class="col-xl-4 col-md-6" for="buscarCaja">N&deg; Caja</label>
                                          <input id="buscarCaja" class="form-control formExamen col-xl-6 col-md-6"  type="number" style="text-transform:uppercase"/>
                                      </div>
                                  </div>
                                  <div class="col-12 mt-4">
                                      <div class="col-12 row m-0 p-0">
                                          <label class="col-xl-4 col-md-6" for="buscarMuestra">N&deg; Muestra</label>
                                          <input id="buscarMuestra"  class="form-control formExamen col-xl-6 col-md-6"  type="number" style="text-transform:uppercase"/>
                                      </div>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="col-12 mt-4">
                        <h5 id="fechaCreacion" class="nav-text" hidden>Fecha: DD - MM -AAAA</h5>
                      </div>
                      <div class="col-12">
                        <div class="card card-2 h-100 no-th-first" id="divTablaTransporte">
                          <div class="card-body p-0">
                              <table id="tablaTransporte" class="table display nowrap compact-xs table-selected">
                                  <thead>
                                      <tr>
                                          <th>Selección</th>
                                          <th>N&deg; Muestra</th>
                                          <th>Tipo de Muestra</th>
                                          <th>Contenedor</th>
                                          <th>Flebotomista</th>
                                          <th>Estado</th>
                                      </tr>
                                  </thead>
                              </table>
                          </div>
                        </div>
                      </div>
                      <div class="col-12 pl-0 pr-0 d-flex justify-content-around">
                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2">
                            <img src="<%=request.getContextPath()%>/resources/img/barcode.png" width="30">
                            Imprimir lista
                        </button>
                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2">
                            <img src="<%=request.getContextPath()%>/resources/img/barcode.png" width="30">
                            Imprimir etiqueta
                        </button>
                        <button id="crearCaja"  type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2">
                            <img src="<%=request.getContextPath()%>/resources/img/barcode.png" width="30">
                            Crear caja
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
        </div>
      </div>

      <jsp:include page="common/Scripts.jsp"/>
      <jsp:include page="common/FileInputScripts.jsp"/>
      <script src="<%=request.getContextPath()%>/resources/js/common/InicioFunciones.js"></script>
      
      <!-- FIN FORMULARIO PACIENTE -->     
      <!--begin::Page Vendors(used by this page)-->
      <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
      <!--end::Page Vendors-->
      <script src="https://unpkg.com/imask"></script>

      <script src="<%=request.getContextPath()%>/resources/js/transportemuestras/TransporteMuestras.init.js"></script>
      <script src="<%=request.getContextPath()%>/resources/js/transportemuestras/TransporteMuestras.ordenes.js"></script>
    </body>
</html>

