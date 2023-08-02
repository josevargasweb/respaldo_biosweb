<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form"%> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags"%> <%@page
contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>BiosLIS | Indicaciones toma de muestra</title>
    <jsp:include page="common/Styles_1.jsp" />
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

    <div class="container container-main-content container-configuracion mt-5">
      <div class="row d-flex justify-content-center mt-4">
        <div class="accordion col-12 pl-0 pr-0" id="examen-main">
          <div class="card h-100">
            <div class="card-header border-15">
              <div class="card-title d-flex justify-content-between">
                <h3
                  class="mb-0 btn-block text-left pl-0 col-10"
                  data-toggle="collapse"
                  data-target="#collapseHeader"
                  aria-expanded="false"
                  aria-controls="collapseHeader"
                >
                  Indicaciones para la Toma de Muestras
                </h3>
              </div>
            </div>
            <div class="card-body">
              <div class="form-inline">
                <div class="form-group col-12">
                  <label for="buscarNuevoIndiTM">Buscar / Nueva</label>
                  <input
                    id="BuscarIndicadoresTM"
                    onkeyup="FiltradoTablaKeyUp()"
                    name=""
                    type="text"
                    class="form-control col-10 ml-5 mr-4"
                    autocomplete="off"
                    placeholder=""
                    maxlength="250"
                  />
                  <a
                    id="AgregarIndicadoresTM"
                    autocomplete="off"
                    placeholder=""
                    class="navi-link"
                  >
                    <span class="symbol symbol-30 symbol-circle">
                      <i
                        id="iAgregarTest"
                        class="fas fa-plus text-blue"
                        aria-hidden="true"
                        style="font-size: 2rem"
                      ></i>
                    </span>
                  </a>
                </div>
              </div>
              <div class="col-12 mt-4 mb-4 row p-0 ml-0 mr-0">
                <div
                  id="contenedor-atencion"
                  class="col-12"
                  style="overflow-y: scroll"
                >
                  <table
                    class="table table-hover table-striped nowrap display compact-xs"
                    id="tablaPrioridadAtencion"
                    style="font-size: 1rem"
                  >
                    <thead id="">
                      <tr>
                        <td>#</td>
                        <td>Indicacion</td>
                      </tr>
                    </thead>
                    <tbody id="TablaIndicadoresTM"></tbody>
                  </table>
                </div>
                <div class="col-9"></div>
                <!--  <div class="col-3">
                                    <input disabled id="GuardarIndicacionExamen" value="Guardar" type="button" class="form-control btn-primary mb-5 mt-5 " autocomplete="off" placeholder="" />
                                </div>-->
              </div>
            </div>
          </div>
        </div>
        <input
          id="guardarDescripcion"
          name=""
          type="text"
          style="visibility: hidden"
        />
        <input id="guardarId" name="" type="text" style="visibility: hidden" />
        <input
          id="guardarDescripcionTM"
          name=""
          type="text"
          style="visibility: hidden"
        />
        <input
          id="guardarIdTM"
          name=""
          type="text"
          style="visibility: hidden"
        />
      </div>
    </div>

    <!-- FIN FORMULARIO PACIENTE -->
    <jsp:include page="common/Scripts.jsp" />
    <script src="<%=request.getContextPath()%>/resources/js/Indicacionestm.js"></script>
  </body>
</html>
