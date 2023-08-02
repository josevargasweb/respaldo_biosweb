<%-- Document : ConfiguracionMetodos Created on : Sep 21, 2020, 4:01:40 PM
Author : Nacho --%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>BIOS-LIS | Configuración de Métodos</title>
    <jsp:include page="common/Styles_1.jsp" />
    <link
      href="<%=request.getContextPath()%>/resources/ColorPicker/css/farbtastic.css"
      rel="stylesheet"
      type="text/css"
    />
  </head>
  <body>
    <div class="row menu-container">
      <div class="col-md-6">
        <jsp:include page="common/Menu_dinamico.jsp" />
      </div>
      <div class="col-md-6">
        <jsp:include page="common/Header_1.jsp" />
      </div>
    </div>
    <jsp:include page="common/AlertaSession.jsp" />
    <c:if test="${mensaje!=null}">
      <input type="hidden" id="messageSuccess" value="${mensaje}" />
    </c:if>
    <c:if test="${mensajeError!=null}">
      <input type="hidden" id="messageError" value="${mensajeError}" />
    </c:if>
    <div class="container container-main-content container-configuracion mt-5">
      <div class="row d-flex justify-content-center mt-4">
        <div class="accordion col-12 pl-0 pr-0" id="accordionMetodos">
          <div class="card border-15">
            <div class="card-header border-15" id="headingMetodos">
              <div class="card-title d-flex justify-content-between">
                <h3
                  class="mb-0 btn-block text-left pl-0 col-10 icon-accordion"
                  data-toggle="collapse"
                  data-target="#collapseHeader"
                  aria-expanded="true"
                  aria-controls="collapseHeader"
                >
                  Búsqueda de Métodos
                </h3>
                <div class="card-buttons col-2 d-flex justify-content-end">
                  <a id="nuevoMetodo" class="navi-link">
                    <span class="symbol symbol-30 symbol-circle">
                      <span
                        id="circuloAgregarMetodo"
                        class="symbol-label bg-hover-blue hoverIcon"
                        data-toggle="tooltip"
                        title="Nuevo método"
                      >
                        <i
                          id="iAgregarMetodo"
                          class="fas fa-plus text-blue"
                        ></i>
                      </span>
                    </span>
                  </a>
                  <a id="btnLimpiarFiltro" class="navi-link">
                    <span class="symbol symbol-30 symbol-circle">
                      <span
                        id="circuloLimpiarFiltro"
                        class="symbol-label bg-hover-blue hoverIcon"
                        data-toggle="tooltip"
                        title="Limpiar"
                      >
                        <i
                          id="iLimpiarFiltro"
                          class="la la-broom icon-xl text-blue"
                        ></i>
                      </span>
                    </span>
                    <span class="navi-text"></span>
                  </a>
                </div>
              </div>
            </div>
            <div
              id="collapseHeader"
              class="collapse show container-content"
              aria-labelledby="headingMetodos"
              data-parent="#headingMetodos"
            >
              <div class="card-body row">
                <!-- FORMULARIO FILTRO -->
                <div class="col">
                  <div class="col-12">
                    <div class="form-group row mb-0">
                      <label id="" class="col-3 col-form-label">Método</label>
                      <div class="col-7">
                        <input
                          id="txtNombreMetodoFiltro"
                          class="form-control"
                          type="text"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-12">
                    <label>
                      <input id="chkMostrarActivos" type="checkbox" /> Mostrar
                      solo activos
                    </label>
                  </div>
                  <!--<a id="btnBuscarFiltro" class="btn btn-light-primary font-weight-bold mr-2 ml-4 pointer" style="margin-top: 120px;" ><i class="la la-search"></i>Buscar</a>-->
                </div>
                <!-- FIN FORMULARIO FILTRO -->
                <!-- TABLA FILTRO -->
                <div class="col">
                  <div class="table-container mb-3">
                    <table
                      id="tableFiltro"
                      class="table table-hover table-striped"
                    >
                      <thead>
                        <tr id="trHeader">
                          <th scope="col">#</th>
                          <th id="thNombre" scope="col">Métodos</th>
                        </tr>
                      </thead>
                      <tbody id="tbodyFiltro"></tbody>
                    </table>
                    <label
                      id="lblErrorFiltro"
                      class="textError text-red ocultar"
                      >No se encontraron métodos con los datos provistos.</label
                    >
                  </div>
                  <label id="lblTotalFiltro"
                    >M&eacute;todos encontrados: <span id="totalFiltro"></span
                  ></label>
                </div>
                <!-- FIN TABLA FILTRO -->
              </div>
            </div>
          </div>
          <!-- FORMULARIO REGISTRO MÉTODOS -->
          <div class="card mt-5 border-15">
            <div class="card-header pl-2 border-15">
              <div class="card-title row col-12 d-flex justify-content-between">
                <h3 id="tituloRegistro" class="card-label col-6 pl-0">
                  Registro de Métodos
                </h3>
                <div class="col-2 justify-content-right iconos-registro">
                  <div class="ml-1 float-right">
                    <a id="btnLimpiar" class="navi-link pointer">
                      <span class="symbol symbol-30 symbol-circle">
                        <span
                          id="circuloLimpiar"
                          class="symbol-label bg-hover-blue hoverIcon"
                          data-toggle="tooltip"
                          title="Limpiar"
                        >
                          <i
                            id="iLimpiar"
                            class="la la-broom icon-xl text-blue"
                          ></i>
                        </span>
                      </span>
                      <span class="navi-text"></span>
                    </a>
                  </div>
                  <div class="ml-1 float-right">
                    <a id="btnEditar" class="navi-link pointer">
                      <span class="symbol symbol-30 symbol-circle mr-3">
                        <span
                          id="circuloEditarMetodo"
                          class="symbol-label circuloIcon"
                          data-toggle="tooltip"
                          title="Editar método"
                        >
                          <i
                            id="iEditMetodo"
                            class="far fa-edit text-dark-50"
                          ></i>
                        </span>
                      </span>
                    </a>
                  </div>
                  <div class="ml-1 float-right">
                    <a id="buscarMetodo" class="navi-link" href="#">
                      <span class="symbol symbol-30 symbol-circle mr-3">
                        <span
                          id="circuloBuscarMetodo"
                          class="symbol-label bg-hover-blue hoverIcon"
                          data-toggle="tooltip"
                          title="Buscar método"
                        >
                          <i
                            id="iBuscarMetodo"
                            class="fas fa-search text-blue"
                          ></i>
                        </span>
                      </span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
            <div class="card-body">
              <div id="formRegistroMetodo">
                <div id="divForm" class="row">
                  <div class="col-12 row">
                    <input
                      id="txtIdMetodo"
                      name="idMetodo"
                      type="text"
                      class="form-control ocultar"
                      autocomplete="off"
                    />
                    <div class="col-4">
                      <label>Método:</label>
                      <input
                        id="txtNombreMetodo"
                        maxlength="100"
                        name="cmetDescripcion"
                        type="text"
                        class="form-control"
                        autocomplete="off"
                      />
                      <div class="d-md-none mb-2"></div>
                    </div>
                    <div class="col-4">
                      <label id="lblEstado" class="col-form-label"
                        >Activo</label
                      >
                      <div class="col-3">
                        <label
                          class="switch-content switch-comprobante switch-active"
                        >
                          <input
                            id="checkBoxLever"
                            name="cmetActivo"
                            class=""
                            type="checkbox"
                            checked
                            value="S"
                          />
                          <div></div>
                        </label>
                      </div>
                    </div>
                    <div class="col-4 row">
                      <label>Color</label>
                      <input
                        class="col-3 form-control mt-25"
                        type="text"
                        id="color"
                        name=""
                        value="#123456"
                        style="
                          height: 30px;
                          background-color: rgb(76, 18, 86);
                          color: rgb(255, 255, 255);
                        "
                      />
                      <div class="col-6" id="colorpicker">
                        <div class="farbtastic">
                          <div
                            class="color"
                            style="background-color: rgb(217, 0, 255)"
                          ></div>
                          <div class="wheel"></div>
                          <div class="overlay"></div>
                          <div
                            class="h-marker marker"
                            style="left: 18px; top: 67px"
                          ></div>
                          <div
                            class="sl-marker marker"
                            style="left: 82px; top: 127px"
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 form-group mt-6 row">
                    <div id="divAgregarBtn" class="col-6">
                      <button
                        id="btnAgregarMetodo"
                        type="submit"
                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                      >
                        <i class="far fa-save"></i>Guardar
                      </button>
                    </div>
                    <div id="divActualizarBtn" class="col-6 ocultar">
                      <button
                        id="btnUpdateMetodo"
                        type="submit"
                        name="update"
                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                      >
                        <i class="far fa-save"></i>Confirmar
                      </button>
                      <button
                        id="btnCancelMetodo"
                        type="button"
                        class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                      >
                        Cancelar
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO MÉTODOS -->
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="common/Scripts.jsp" />
    <script src="<%=request.getContextPath()%>/resources/ColorPicker/js/farbtastic.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionMetodos.js"></script>
  </body>
</html>
