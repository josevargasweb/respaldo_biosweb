<%-- 
    Document   : ConfiguracionMuestras
    Created on : 11-06-2021, 0:58:30
    Author     : marco.c
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Muestras</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <link href="<%=request.getContextPath()%>/resources/ColorPicker/css/farbtastic.css" rel="stylesheet" type="text/css" />
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
        <jsp:include page="common/AlertaSession.jsp"/>
        
        <c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        <c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
        
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
            <div class="accordion col-12 pl-0 pr-0">
                <div id="panelMuestras" class="card border-15">
                    <div class="card-header border-15" id="headingMuestras">
                        <div class="card-title d-flex justify-content-between">
                            <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true"
                                        aria-controls="collapseHeader">
                                Búsqueda de Muestras
                            </h3>
                            <div class="card-buttons col-2 d-flex justify-content-end">
                                <a id="nuevaMuestra" class="navi-link">
                                    <span class='symbol symbol-30 symbol-circle'>
                                        <span id="circuloAgregarMuestra" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nueva Muestra">
                                            <i id="iAgregarMuestra" class='fas fa-plus text-blue'></i>
                                        </span>
                                    </span>
                                </a>
                                <a id="btnLimpiarFiltro" class="navi-link">
                                    <span class="symbol symbol-30 symbol-circle ">
                                        <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar">
                                            <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>

                        </div>
                    </div>
                    <div id="collapseHeader" class="collapse show" data-parent="#headingMuestras">
                        <div class="card-body row">
                            <!-- FORMULARIO FILTRO -->
                            <div class="col">
                                <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-4 col-form-label">Prefijo</label>
                                        <div class="col-8">
                                            <input id="txtCodigoFiltro" class="form-control" type="text" maxlength="2" style="text-transform:uppercase" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row mb-0">
                                        <label class="col-4 col-form-label">Tipo de muestras</label>
                                        <div class="col-8">
                                            <input id="txtMuestraFiltro" class="form-control" type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 mt-3">
                                    <label>
                                        <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                    </label>
                                </div>
                            </div>
                            <!-- FIN FORMULARIO FILTRO -->
                            <!-- TABLA FILTRO -->
                            <div class="col">
                                <div class="table-container mb-3">
                                    <table id="tableFiltro" class="table table-hover table-striped">
                                        <thead>
                                            <tr id="trHeader">
                                                <th scope="col">#</th>
                                                <th scope="col">Prefijo</th>
                                                <th id="thMuestra" scope="col">Tipo de muestras</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tbodyFiltro"></tbody>
                                    </table>
                                    <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron muestras con los datos provistos.</label>
                                </div>
                                <label id="lblTotalFiltro" class="">Muestras encontradas: <span id="contRegistros"></span></label>
                            </div>
                            <!-- FIN TABLA FILTRO -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- FORMULARIO REGISTRO TEST -->
            <div id="panelMuestrasForm" class="card mt-5 border-15">
                <div class="card-header border-15"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                            Registro de Muestras
                        </h3>
                        <div class="justify-content-right iconos-registro">
                            <div class="float-right">
                                <div id="divbtnLimpiar">
                                    <a id="btnLimpiar" class="navi-link pointer">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiar" data-toggle="tooltip"
                                                title="Limpiar"
                                                class="symbol-label bg-hover-blue ">
                                                <i id="iLimpiar"
                                                    class="la la-broom icon-xl  text-blue"></i></span>
                                        </span> <span class="navi-text"></span>
                                    </a>
                                </div>
                            </div>   
                            <div class="float-right">
                                <div id="divbtnEditar">
                                    <a id="btnEditarMuestra" class="navi-link pointer"> 
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloEditarMuestra"
                                                class='symbol-label circuloIcon'
                                                data-toggle="tooltip" title="Editar Muestra">
                                                <i id="iEditMuestra"
                                                    class="far fa-edit text-dark-50 "></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                            <div class="float-right" >
                                <div id="divbuscarMuestra">
                                    <a id="buscarMuestra" class="navi-link" href="#" >
                                        <span class='symbol symbol-30 symbol-circle mr-3'>
                                            <span id="circuloBuscarMuestra" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar Muestra">
                                                <i id="iBuscarMuestra" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div id="formRegistroTest" >
                        <div id="divForm" class="row">
                            <div class="col-12 row">
                                <input id="txtIdTipoMuestra" name="idTipoMuestra" type="text" class="form-control ocultar" autocomplete="off" placeholder="" />
                                <input id="ipEquipo" name="c" type="text" class="form-control ocultar">
                                <input id="prefijoDefault" name="c" type="text" value="${prefijo}" class="form-control ocultar">
                                <div id="" class="col-4">
                                    <label>Muestra</label>
                                    <input id="txtDescripcionAbrev" maxlength="35" name="cmueDescripcionabreviada" type="text" class="form-control formMuestras isRequired" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Descripcion</label>
                                    <input id="txtDescripcion" maxlength="50" name="cmueDescripcion" type="text" class="form-control formMuestras" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
  
                                </div>
                                <div class="col-4">
                                    <label>Orden:</label>
                                    <input id="numOrden" name="cmueSort" type="number" value="1" class="form-control formMuestras" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>Prefijo</label>
                                    <div class="form-group row">
                                        <c:if test="${prefijo!=''}">
                                            <input id="txtPrefijo" name="cmuePrefijotipomuestra" type="text" class="col-4 form-control" value="${prefijo}" maxlength="2" autocomplete="off" placeholder="" readonly />
                                            <button id="btnElegirPrefijo" type="button" class="btn btn-blue-primary col-8" data-toggle="modal" data-target="#modalPrefijos">
                                                Elegir Prefijo
                                            </button>
                                        </c:if>
                                        <c:if test="${prefijo==''}">
                                            <input id="txtPrefijo" name="cmuePrefijotipomuestra" type="text" style="color:red; font-weight: bold;" class="form-control" value="No hay prefijos disponibles" maxlength="2" autocomplete="off" placeholder="" readonly /> 
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label>Host</label>
                                    <input id="txtHost" maxlength="20" name="cmueHost" type="text" class="form-control formMuestras" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4">
                                    <label>LIS-Bac</label>
                                    <input id="txtLISBac" maxlength="20" name="cmueHostmicro" type="text" class="form-control formMuestras" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                    
                                    
                                    
                                        <label class="col-md-12 mt-6 checkbox checkbox-primary">
                                            <input id="checkBoxMicrobiología" class="formMuestras" type="checkbox" name="cmueEsmicrobiologia" value="S" />Microbiología
                                            <span></span>
                                        </label>
                                        <label class="col-md-12 checkbox checkbox-primary">
                                            <input id="checkBoxPuntodeCurva" class="formMuestras" type="checkbox" name="cmueEstipocurva" value="S" class="isRequired" />
                                            <b>Punto de curva:</b> 
                                            <span></span>
                                        </label>
                                        <span id="activaCurvasMinutos" class="ocultar formMuestras">a los <input id="txtCurvasMinutos" name="cmueCurvasminutos" type="number" class="form-control col-3 formMuestras"  /> minutos</span>
                                    
                                    
                                </div>
                                <div class="col-4 row">
                                    <label>Color</label>
                                    <input class="col-3 form-control mt-25 formMuestras" type="text" id="color" name="cmueColor" value="#123456" style="height: 30px;" />
                                    <div class="col-6" id="colorpicker"></div>
                                </div>
                                
                                <div class="col-4">
                                    <label id="lblEstado" class="col-2 col-form-label d-flex align-items-center">Activo</label>
                                    <div class="col-3">
                                        <label class="switch-content switch-normal switch-active">
                                            <input id='checkBoxActivo' name='cmueActivo' class="formMuestras" type='checkbox' checked value='S' />
                                            <div></div>
                                        </label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <button id="btnVerExamenes" type="button" class="btn btn-blue-primary btn-lg font-weight-bold" data-toggle="modal" data-target="#modalExamenes">
                                        Ver Exámenes
                                    </button>
                                </div>
                                <input id="txtEstado" name="estado" type="text" class="form-control ocultar" autocomplete="off" placeholder=""/>
                            </div>
                            <div class="col-md-6 form-group mt-6 row">
                                <div id="divAgregarBtn" class="col-6">
                                    <button id="btnAgregar" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Guardar</button>
                                </div>
                                <div id="divActualizarBtn" class="col-6 ocultar">
                                    <button id="btnUpdate" type="submit" name="update" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"><i class="far fa-save"></i>Confirmar</button>
                                    <button id="btnCancelUpdate" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                </div>
                            </div>
                            
                            <!-- Modal prefijos -->
                            <div class="modal fade" id="modalPrefijos" tabindex="-1" role="dialog" aria-labelledby="modalPrefijosLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Selección de prefijos</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <i aria-hidden="true" class="ki ki-close"></i>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="col-4">
                                                <label for="" class="">Prefijo:</label>
                                                <input id="txtBuscaPrefijo" class="form-control" name="" placeholder="" type="text" maxlength="2" style="text-transform:uppercase" />
                                            </div>
                                            <div class="col table-wrapper-scroll-y my-custom-scrollbar" >
                                                <table id="tablePrefijos" class="table table-hover  scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="max-height: 200px">
                                                    <thead>
                                                        <tr id="trHeader">
                                                            <th scope="col">Prefijo</th>
                                                            <th scope="col">Muestra</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tbodyFiltro">
                                                        <c:forEach items="${listaPrefijosMuestras}" var="pref">
                                                            <tr>
                                                                <td><c:out value="${pref[0].toString()}" /></td>
                                                                <td class="tdPrefijo">
                                                                    <c:if test="${pref[1].toString()!=''}">
                                                                        <c:out value="${pref[1].toString()}" />
                                                                    </c:if>
                                                                    <c:if test="${pref[1].toString()==''}">
                                                                        <button type="button" onclick="seleccionarPrefijo('${pref[0].toString()}')" class="btn btn-blue-primary btn-lg font-weight-bold">Seleccionar</button>
                                                                    </c:if>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <label id="lblErrorEx" class="textError text-red ocultar">No se encontraron exámenes con los datos provistos.</label>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold" data-dismiss="modal">Cancelar</button>
                                            <!--<button type="button" class="btn btn-primary font-weight-bold">Seleccionar</button>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                                    
                            <!-- Modal examenes -->
                            <div class="modal fade" id="modalExamenes" tabindex="-1" role="dialog" aria-labelledby="modalExamenesLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Exámenes Asociados</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <i aria-hidden="true" class="ki ki-close"></i>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="col-4">
                                                <label for="" class="">Muestra:</label>
                                                <select class="form-control selectpicker" id="selectMuestraEx" name="muestrasEx" data-live-search="true">
                                                    <c:forEach var="muestra" items="${listaMuestras}">
                                                        <option value="${muestra[0]}">${muestra[2]}</option>
                                                    </c:forEach>
                                                </select>
                                                <label for="" class="">Prefijo:</label>
                                                <input id="txtBuscaPrefijoEx" class="form-control" name="" placeholder="" type="text" maxlength="2" style="text-transform:uppercase" />
                                            </div>
                                            <div class="col table-wrapper-scroll-y my-custom-scrollbar">
                                                <table id="tableExamenes" class="table table-hover scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="max-height: 200px">
                                                    <thead>
                                                        <tr id="trHeader">
                                                            <th scope="col">Exámenes</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tbodyEx"></tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold" data-dismiss="modal">Cerrar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO PACIENTE -->
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="https://unpkg.com/imask"></script>
        <script src="<%=request.getContextPath()%>/resources/js/ConfiguracionMuestras.js"></script>
        <script src="<%=request.getContextPath()%>/resources/ColorPicker/js/farbtastic.js"></script>
    </body>
</html>
