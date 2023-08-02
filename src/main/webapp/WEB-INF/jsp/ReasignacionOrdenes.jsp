<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>BiosLIS | Reasignar &oacute;rdenes</title>
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
                            <div class="accordion col-12 pl-0 pr-0">
                                <div class="card mt-5 border-15">
                                    <div class="card-header border-15">
                                        <div class="card-title row col-12 d-flex justify-content-between">
                                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                                Reasignacion orden
                                            </h3>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="col-12 row">
                                            <div class="card card-2 col-5">
                                                <div class="card-body">
                                                    <div class="col-12">
                                                        <div class="col-12 row">
                                                            <h3 class="col-10">Desde</h3>
                                                            <div
                                                                class="col-2 justify-content-right iconos-registro d-flex align-items-center">
                                                                <div class="float-right">
                                                                    <div id="divbtnLimpiar">
                                                                        <a id="limpearPac1" class="navi-link pointer">
                                                                            <span
                                                                                class="symbol symbol-30 symbol-circle">
                                                                                <span id="circuloLimpiar"
                                                                                    data-toggle="tooltip" title=""
                                                                                    class="symbol-label bg-hover-blue "
                                                                                    data-original-title="Limpiar">
                                                                                    <i id="iLimpiar"
                                                                                        class="la la-broom icon-xl  text-blue"></i></span>
                                                                            </span> <span class="navi-text"></span>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                                <div class="float-right">
                                                                    <div id="divbuscarPaciente">
                                                                        <a href="./RegistroPaciente?menosRut=1"
                                                                            id="BuscarPac" class="navi-link"> <span
                                                                                class="symbol symbol-30 symbol-circle mr-3">
                                                                                <span id="circuloBuscarExamen"
                                                                                    class="symbol-label bg-hover-blue hoverIcon"
                                                                                    data-toggle="tooltip" title=""
                                                                                    data-original-title="Buscar paciente"><i
                                                                                        id="iBuscarPaciente"
                                                                                        class="fas fa-search text-blue"
                                                                                        aria-hidden="true"></i></span>
                                                                            </span>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <input id="txtIdEnvase" name="idEnvase" type="text"
                                                            class="form-control ocultar" autocomplete="off"
                                                            placeholder="" />
                                                        <div class="col-12">
                                                            <label>Tipo de documento</label>
                                                            <select class="form-control selectpicker limpearSelect1"
                                                                id="selectTipoDocumento" name="ldvtdIdtipodocumento">
                                                                <option value="N" disabled selected>Seleccionar</option>
                                                                <c:forEach var="documento"
                                                                    items="${listaTiposDocumentos}">
                                                                    <option value="${documento.ldvtdIdtipodocumento}">
                                                                        ${documento.ldvtdDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="col-12">
                                                            <label>Pasaporte</label>
                                                            <input id="txtRutP" type="text"
                                                                class=" form-control alwaysMayus LimpearTexto1"
                                                                placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Nombres</label>
                                                            <input disabled id="txtNombreP" type="text"
                                                                class="form-control LimpearTexto1" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Apellidos</label>
                                                            <input disabled id="txtPrimerApellidoP" type="text"
                                                                class="form-control LimpearTexto1" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Sexo</label>
                                                            <select disabled id="sexoPaciente"
                                                                class="form-control  limpearSelect1">
                                                                <option value="N" disabled selected></option>
                                                                <c:forEach var="sexo" items="${listaSexo}">
                                                                    <option value="${sexo.ldvsIdsexo}">
                                                                        ${sexo.ldvsDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Edad</label>
                                                            <input disabled id="txtEdad" type="text"
                                                                class="form-control  LimpearTexto1" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <table class='table table-hover' id="tabla1">
                                                                <thead id="tablaROrdenesTitulos">
                                                                    <tr>
                                                                        <td>#</td>
                                                                        <td>Fecha</td>
                                                                        <td>N° Orden</td>
                                                                        <td>Reasignar?</td>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tablaROrdenesBody">
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-2">
                                                <input class="mt-40 ml-11" id="ActivarModal" type="image" alt="button"
                                                    src="<%=request.getContextPath()%>/resources/img/Flecha.png"
                                                    data-toggle="modal" data-target="#exampleModal" />
                                            </div>
                                            <div class="card card-2 col-5">
                                                <div class="card-body">
                                                    <div class="col-12">
                                                        <div class="col-12 row">
                                                            <h3 class="col-10">Hacia</h3>
                                                            <div
                                                                class="col-2 justify-content-right iconos-registro d-flex align-items-center">
                                                                <div class="float-right">
                                                                    <div id="">
                                                                        <a id="limpearPac2" class="navi-link pointer">
                                                                            <span
                                                                                class="symbol symbol-30 symbol-circle">
                                                                                <span id="circuloLimpiar"
                                                                                    data-toggle="tooltip" title=""
                                                                                    class="symbol-label bg-hover-blue "
                                                                                    data-original-title="Limpiar">
                                                                                    <i id="iLimpiar"
                                                                                        class="la la-broom icon-xl  text-blue"></i></span>
                                                                            </span> <span class="navi-text"></span>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                                <div class="float-right">
                                                                    <div id="divbuscarPaciente">
                                                                        <a href="./RegistroPaciente" id="BuscarPac2"
                                                                            class="navi-link"> <span
                                                                                class="symbol symbol-30 symbol-circle mr-3">
                                                                                <span id="circuloBuscarExamen"
                                                                                    class="symbol-label bg-hover-blue hoverIcon"
                                                                                    data-toggle="tooltip" title=""
                                                                                    data-original-title="Buscar paciente"><i
                                                                                        id="iBuscarPaciente"
                                                                                        class="fas fa-search text-blue"
                                                                                        aria-hidden="true"></i></span>
                                                                            </span>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Run</label>
                                                            <input maxlength="12" id="txtRutP2" type="text"
                                                                class=" form-control alwaysMayus LimpearTexto2"
                                                                placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Nombres</label>
                                                            <input disabled id="txtNombreP2" type="text"
                                                                class="form-control LimpearTexto2" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Apellidos</label>
                                                            <input disabled id="txtPrimerApellidoP2" type="text"
                                                                class="form-control LimpearTexto2" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Sexo</label>
                                                            <select disabled id="sexoPaciente2"
                                                                class="form-control limpearSelect2">
                                                                <option value="N" disabled selected></option>
                                                                <c:forEach var="sexo" items="${listaSexo}">
                                                                    <option value="${sexo.ldvsIdsexo}">
                                                                        ${sexo.ldvsDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="col-12">
                                                            <label for="">Edad</label>
                                                            <input disabled id="txtEdad2" type="text"
                                                                class="form-control LimpearTexto2" placeholder="" />
                                                        </div>
                                                        <div class="col-12">
                                                            <table class='table table-hover'>
                                                                <thead id="tablaROrdenesTitulos2">
                                                                    <tr>
                                                                        <td>#</td>
                                                                        <td>Fecha</td>
                                                                        <td>N° Orden</td>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tablaROrdenesBody2">
                                                                </tbody>
                                                            </table>
                                                            <input id="idPaciente2" class="ocultar" name="" />
                                                            <input id="idPaciente" class="ocultar" name="" />
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

                    <div class="modal fade show" id="exampleModal" tabindex="-1" role="dialog"
                        aria-labelledby="exampleModalLabel" aria-hidden="false">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-body">

                                    <div class="col-8 row center">
                                        <h6 class="col-12">Las órdenes seleccionadas de:</h6>
                                        <p class="col-12" id="nombreDesde"></p>
                                        <br>
                                        <h6 class="col-12">Serán reasignadas a</h6>
                                        <br>
                                        <p class="col-12" id="nombreHacia"></p>
                                        <br>
                                        <p class="col-12 text-danger">(Esta acción no podrá ser revertida
                                            posteriormente)</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- FIN FORMULARIO PACIENTE -->
                    <jsp:include page="common/Scripts.jsp" />
                    <script src="<%=request.getContextPath()%>/resources/js/ReasigancionOrdenes.js"></script>
                </body>

                </html>