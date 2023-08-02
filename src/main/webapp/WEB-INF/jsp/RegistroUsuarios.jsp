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
                                    <a id="nuevoUsuario" class="navi-link"> 
                                        <span  class='symbol symbol-30 symbol-circle '> 
                                            <span id="circuloAgregar" class='symbol-label bg-hover-blue  hoverIcon' href="#" data-toggle="tooltip" title="Nuevo Usuario"> 
                                                <i id="iAgregar" class='fas fa-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a> 
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
                                                    <option value="0">TODOS</option>
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
                                                <select class="form-control selectpicker disabledForm" id="codCargoFiltro" data-live-search="true" data-none-selected-text>
                                                    <option value="0">TODOS</option>
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
                        <div class="card-header pl-2  border-15"> 
                            <div class="card-title row col-12 d-flex justify-content-between">
                                <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                    Registro de Usuarios
                                </h3>
                                <div class="col-6">
                                    <ul class="nav nav-pills">
                                        <li class="nav-item"><a id="anchorRegistro"
                                                class="nav-link active" data-toggle="tab"
                                                href="#panelDatosBasicos"> <span
                                                    class="nav-icon"><i
                                                        class="flaticon2-paper"></i></span> <span
                                                    class="nav-text">Datos&nbsp;
                                                    B&aacute;sicos</span>
                                            </a></li>
                                        <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                                href="#panelSeguridad"> <span class="nav-icon"><i
                                                        class="flaticon2-shield"></i></span> <span
                                                    class="nav-text">Seguridad</span>
                                            </a></li>
                                        <li class="nav-item"><a class="nav-link" data-toggle="tab"
                                                href="#panelDatosExtras"> <span class="nav-icon"><i
                                                        class="fas fa-book-medical"></i></span>
                                                <span class="nav-text">Datos Extra</span>
                                            </a></li>
                                    </ul>
                                </div>

                                  <!-- Fin Tabuladores -->
                                <!-- ************************************** Botonera Registro Paciente **************************-->
                                <div class="col-2 justify-content-right iconos-registro ">
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
                                            <a id="btnEditarUsuario" class="navi-link pointer"> <span
                                                    class='symbol symbol-30 symbol-circle'>
                                                    <span id="circuloEditarUsuario"
                                                        class='symbol-label circuloIcon'
                                                        data-toggle="tooltip" title="Editar Usuario">
                                                        <i id="iEditUsuario"
                                                            class="far fa-edit text-dark-50 "></i>
                                                    </span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="float-right">
                                        <div id="divbuscarPaciente">
                                            <a id="buscarUsuario" class="navi-link" href="#"> <span
                                                    class='symbol symbol-30 symbol-circle mr-3'> <span
                                                        id="circuloBuscarU"
                                                        class='symbol-label bg-hover-blue hoverIcon'
                                                         data-toggle="tooltip" title="Buscar Usuario"><i
                                                            id="iBuscarUsuario"
                                                            class="fas fa-search text-blue"></i></span>
                                                </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!-- ************************************** Botonera Registro Paciente **************************-->
                             
                            </div>
                        </div>
                        <div class="card-body">
                            <form id="formRegistroUsuario" method="post" enctype="multipart/form-data">
                                <div id="divForm" class="row">
                                    <div class="col-12">
                                        <h5 class="card-label text-center titleContainer" >Nombre <span class="titleNombre"></span>&nbsp;<span class="titleApellido"></span></h5>
                                        <div class="tab-content" id="myTabContent">
                                            <div class="tab-pane fade show active" id="panelDatosBasicos" role="tabpanel" aria-labelledby="panelDatosBasicos">
                                                <div class="row">
                                                        <input id="txtID" name="idUsuario" type="hidden" class="form-control" autocomplete="off"  />
                                                        <div class="col-md-4">
                                                            <label for="txtRun">RUN *</label>
                                                            <input id="txtRun" maxlength="15" name="duRun" type="text" class="form-control" autocomplete="off" />
                                                            <!--<div class="invalid-feedback">RUN inv&aacute;lido</div>-->
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="txtNombre">Nombres *</label>
                                                            <input id="txtNombre" maxlength="50" name="duNombres" type="text" class="form-control" autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="txtPrimerApellido">Primer Apellido *</label>
                                                            <input id="txtPrimerApellido" maxlength="30" name="duPrimerapellido"  type="text" class="form-control" autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label>Segundo Apellido </label>
                                                            <input id="txtSegundoApellido" maxlength="30" name="duSegundoapellido" type="text" class="form-control" autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="selectCargo">Cargo *</label>
                                                            <select class="form-control selectpicker" id="selectCargo" name='cargoUsuario' data-live-search="true">
                                                                <c:forEach var="cargo" items="${listaCargosUsuario}">
                                                                    <option value="${cargo.ldvcuIdcargo}">${cargo.ldvcuDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="selectCentro" class="">Centro de salud</label>
                                                            <select class="form-control selectpicker" id="selectCentro" name="centroUsuario">
                                                                <option value="0">SIN ESPECIFICAR</option>
                                                                <c:forEach var="centrodesalud" items="${listaCentrosdeSalud}">
                                                                    <option value="${centrodesalud.ccdsIdcentrodesalud}">${centrodesalud.ccdsDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="" class="">Laboratorio</label>
                                                            <select class="form-control selectpicker" id="selectLab" name="labUsuario" data-none-selected-text>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="" class="">Secci&oacute;n</label>
                                                            <select class="form-control selectpicker" id="selectSecc" name="seccUsuario" data-none-selected-text>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label for="" class="">Procedencia</label>
                                                            <select class="form-control selectpicker selectProc" id="selectProc" name="procUsuario" data-none-selected-text>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
        
                                                        <div class="col-md-4 mt-2">
                                                            <label>Usuario *</label>
                                                            <input id="txtUsuario" maxlength="20" name="duUsuario" type="text" class="form-control" autocomplete="off" /><!--name debe ser reemplazado por atributos de la entity-->
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4 mt-2">
                                                            <label id="lblPassword">Clave *</label>
                                                            <div id="divTxtPassword" class="input-group">
                                                                <input id="txtPassword" maxlength="30" name="duPassword" type="password" class="form-control" autocomplete="off" />
                                                                <div class="input-group-append">
                                                                    <button id="showPassword" class="form-control btn btn-blue-primary" type="button"> <span class="fa fa-eye-slash icon"></span> </button>
                                                                </div>
                                                            </div>
                                                            <div id="divBtnCambioPassword" class="input-group ocultar">
                                                                <a id="btnCambiarPasword" class="col-md-9 col-xl-7 w-100 btn btn-blue-primary btn-lg font-weight-bold mt-2 mt-5">Cambiar contraseña</a>
                                                            </div>
                                                        </div>
                                                        <div class="ml-md-3 ml-xl-4 mt-2">
                                                            <label>Foto</label>
                                                            <div>
                                                                <div class="image-input image-input-outline" id="inputFotoUsuario" >
                                                                    <div id="fotoUsuarioWrapper" class="image-input-wrapper"></div>
                                                                    <label id="cambiarFotoUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="change" data-toggle="tooltip" title="" data-original-title="Cambiar foto">
                                                                        <i class="fa fa-pen icon-sm text-muted"></i>
                                                                        <input type="file" id="fotoUsuario" name="fotoUsuario" accept=".png, .jpg, .jpeg"/>
                                                                        <input type="hidden" name="profile_avatar_remove"/>
                                                                    </label>
                                                                    <span id="eliminarFotoUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="cancel" data-toggle="tooltip" title="Eliminar foto">
                                                                        <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                    </span>
                                                                    <span id="removeFotoUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="remove" data-toggle="tooltip" title="Eliminar foto">
                                                                        <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="ml-10 mt-2">
                                                            <label>Firma</label>
                                                            <div>
                                                                <div class="image-input image-input-outline" id="inputFirmaUsuario">
                                                                    <div id="firmaUsuarioWrapper" class="image-input-wrapper" style="height: 90px;width: 150px;"></div>
                                                                    <label id="cambiarFirmaUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="change" data-toggle="tooltip" title="" data-original-title="Cambiar firma">
                                                                        <i class="fa fa-pen icon-sm text-muted"></i>
                                                                        <input type="file" id="firmaUsuario" name="firmaUsuario" accept=".png, .jpg, .jpeg"/>
                                                                        <input type="hidden" name="profile_avatar_remove"/>
                                                                    </label>
                                                                    <span id="eliminarFirmaUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="cancel" data-toggle="tooltip" title="Eliminar firma">
                                                                        <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                    </span>
                                                                    <span id="removeFirmaUsuario" class="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow" data-action="remove" data-toggle="tooltip" title="Eliminar firma">
                                                                        <i class="ki ki-bold-close icon-xs text-muted"></i>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                            
                                                        </div>
                                                        
                                                        
                                                        
                                                </div>
                                            </div>
                                            <!--  **************************************************** DATOS EXTRAS ******************************************************************************-->
                                            <div class="tab-pane fade" id="panelDatosExtras" role="tabpanel" aria-labelledby="panelDatosExtras">
                                                <div class="row">
                                                    <div class="form-group row align-items-center col-12">
                                                        <div class="col-md-4">
                                                            <label>Prefijo</label>
                                                            <input id="txtPrefijo" name="cpuPrefijoprofesion" type="text" class="form-control" autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label>Sexo</label>
                                                            <select class="form-control selectpicker" id="selectSexo" name="sexoUsuario">
                                                                <!-- <option value="0" disabled selected>Seleccionar</option> -->
                                                                <c:forEach var="sexo" items="${listaSexo}">
                                                                    <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label>Profesión</label>
                                                            <select class="form-control selectpicker" id="selectProfesion" name="profesionUsuario">
                                                                <!-- <option value="0" disabled selected>Seleccionar</option> -->
                                                                <c:forEach var="profesion" items="${listaProfesiones}">
                                                                    <option value="${profesion.ldvpuIdprofesion}">${profesion.ldvpuDescripcion}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <label>E-mail</label>
                                                            <input id="txtEmail" name="duEmail" type="email" class="form-control" autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
        
                                                        <div class="col-md-4">
                                                            <div id="divBtnInoperabilidad" class="mt-4">
                                                                <!-- Button modal inoperabilidad (host) -->
                                                                <button id="btnInoperabilidad" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 col-md-9 col-xl-7 w-100" data-toggle="modal" data-target="#modalInoperabilidad">
                                                                    Interoperabilidad (Host)
                                                                </button>
                                                                <!-- MODAL INOPERABILIDAD (HOST) -->    
                                                                <div class="modal fade" id="modalInoperabilidad" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdrop" aria-hidden="true">
                                                                    <div class="modal-dialog modal-dialog-scrollable" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-body" >
                                                                                <div class="col">
                                                                                    <h5 class="modal-title text-center" id="exampleModalLabel">Interoperabilidad (Host)</h5>
                                                                                    <div class="col-12">
                                                                                        <div class="form-group row">
                                                                                            <label id="" class="col-3 col-form-label">Host</label>
                                                                                            <div class="col-7">
                                                                                                <input id="txtHost" class="form-control" name="cpuHost" type="text" />
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-12">
                                                                                        <div class="form-group row">
                                                                                            <label id="" class="col-3 col-form-label">LIS BAC</label>
                                                                                            <div class="col-7">
                                                                                                <input id="txtHostMicro" class="form-control" name="cpuHostmicro" type="text" />
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-12 pl-0 pr-0 d-flex justify-content-between">
                                                                                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 cerrarModal" data-dismiss="modal">Ok</button>
                                                                                    <!-- <a id="btnLimpiarModal" class="btn btn-light-primary font-weight-bold"><i id="iLimpiarModal" class="la la-broom icon-xl  text-primary"></i>Limpiar</a> -->
                                                                                </div>
                                                                            </div>
                                                                            
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- PERMISOS DE USUARIO -->
                                                        <div class="col-md-4">
                                                            <div class="mt-4">
                                                                <button id="btnPermisos" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 col-md-9 col-xl-7 w-100" data-toggle="modal" data-target="#modalPermisosUsuario">
                                                                    Permisos/Accesos
                                                                </button>
                                                                <div class="modal fade" id="modalPermisosUsuario" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdrop" aria-hidden="true">
                                                                    <div class="modal-dialog modal-lg" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-body row">
                                                                                <h5 class="modal-title col-12 text-center mb-2" id="exampleModalLabel">Accesos y Permisos de Usuario</h5>
                                                                                <div class="col-6">
                                                                                    <h5>
                                                                                        Accesos
                                                                                    </h5>
                                                                                    <ul id="listAccesosUsuario"></ul>
                                                                                </div>
                                                                                <div class="col-6" >
                                                                                    <h5>
                                                                                        Permisos
                                                                                    </h5>
                                                                                    <ul id="listPermisosUsuario" class="pl-0">
                                                                                        <li style="list-style:none;">
                                                                                            <input id="editadatospaciente" name="cpuEditadatospaciente" type="checkbox">
                                                                                            <label class="cursor-pointer" for="editadatospaciente">Edita datos de paciente</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="registraexamenesderivados" name="cpuRegistraexamenesderivados" type="checkbox">
                                                                                            <label class="cursor-pointer" for="registraexamenesderivados">Registra exámenes derivados</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="editasoloordsprocedencia" name="cpuEditasoloordsprocedencia" type="checkbox">
                                                                                            <label class="cursor-pointer" for="editasoloordsprocedencia">Edita solo órdenes de su procedencia</label>
                                                                                        </li>
                                                                                        <!--
                                                                                        <li style="list-style:none;">
                                                                                            <input id="pideautorizeditorden" name="cpuPideautorizeditorden" type="checkbox">
                                                                                            <label class="cursor-pointer" for="pideautorizeditorden">Requiere autorización para edición orden</label>
                                                                                        </li>-->
                                                                                        <li style="list-style:none;">
                                                                                            <input id="eliminarexamenes" name="cpuEliminarexamenes" type="checkbox">
                                                                                            <label class="cursor-pointer" for="eliminarexamenes">Anula exámenes</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="flebotomista" name="cpuFlebotomista" type="checkbox">
                                                                                            <label class="cursor-pointer" for="flebotomista">Flebotomista</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="recepcionxexamen" name="cpuRecepcionxexamen" type="checkbox">
                                                                                            <label class="cursor-pointer" for="recepcionxexamen">Recepciona muestras</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="firmaexamenes" name="cpuFirmaexamenes" type="checkbox">
                                                                                            <label class="cursor-pointer" for="firmaexamenes">Firma resultados de test</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="autorizaexamenes" name="cpuAutorizaexamenes" type="checkbox">
                                                                                            <label class="cursor-pointer" for="autorizaexamenes">Autoriza exámenes</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="quitarautorizacion" name="cpuQuitarautorizacion" type="checkbox">
                                                                                            <label class="cursor-pointer" for="quitarautorizacion">Retira autorización de exámenes</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="editaresultadoscriticos" name="cpuEditaresultadoscriticos" type="checkbox">
                                                                                            <label class="cursor-pointer" for="editaresultadoscriticos">Edita resultados críticos</label>
                                                                                        </li>
                                                                                        <li style="list-style:none;">
                                                                                            <input id="enviaresultadosemail" name="cpuEnviaresultadosemail" type="checkbox">
                                                                                            <label class="cursor-pointer" for="enviaresultadosemail">Envía resultados por mail</label>
                                                                                        </li>
                                                                                    </ul>
                                                                                </div>
                                                                                <div class="col-12 d-flex justify-content-between">
                                                                                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 cerrarModal" data-dismiss="modal">Ok</button>
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
                                            <!--  **************************************************** SEGURIDAD **********************************************************************************-->
                                            <div class="tab-pane fade" id="panelSeguridad" role="tabpanel" aria-labelledby="panelSeguridad">
                                                <div class="row">
                                                    <div class="form-group row align-items-center col-12">
                                                        <div class="col-xl-2 col-md-auto ml-2 mt-4">
                                                            <label class="checkbox checkbox-primary col-12 mt-3">
                                                                <input id="checkboxExpira" name="duPasswordexpira" type="checkbox" />
                                                                &nbsp;&nbsp;&nbsp;La clave caduca
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                        <div class="col-md-4 col-xl-3 mt-3">
                                                            <label class="mt-3">Vence cada <input type="text" id="diasCaducaPwd" disabled /> días</label>
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div class="col-3">
                                                            <label>Fecha vencimiento</label>
                                                            <input id="txtFechaVencimiento" name="duFechacaducapassword" type="text" class="form-control" autocomplete="off" disabled />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <!--
                                                        <label class="mt-5 col-12">
                                                            <h4>IMED</h4>
                                                        </label>
                                                        <div class="col-md-4 mt-5">
                                                            <label>Usuario:</label>
                                                            <input id="txtUsuarioImed" name="cpuUsuarioimed" type="text" class="form-control mt-5 " autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        <div id="" class="col-md-4 mt-5">
                                                            <label>Clave:</label>
                                                            <input id="txtClaveImed" name="cpuClaveimed" type="password" maxlength="10" class="form-control mt-5 " autocomplete="off" />
                                                            <div class="d-md-none mb-2"></div>
                                                        </div>
                                                        -->
                                                        <div class="col-xl-12 ml-2 mt-4">
                                                            <label class="checkbox checkbox-primary">
                                                                <input id="checkboxRestringido" name="cpuUsuariorestringido" type="checkbox" /> <strong>Restringido:</strong>&nbsp;Acceso a funcionalidades seleccionadas
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 form-group mt-4 row">
                                            <div class="col-12 row">
                                                <label id="lblEstado" class="col-2 pl-0 col-form-label d-flex align-items-center">Activo</label>
                                                <div class="col-3">
                                                    <label class="switch-content switch-normal switch-active"> 
                                                        <input id='checkBoxActivo' name="duActivo" type="checkbox" tabindex="-1" checked/>
                                                        <div></div>
                                                    </label>
                                                  
                                                </div>
                                            </div>
                                            <div class="col-12 pl-0">
                                                <div id="divAgregarBtn" class="col-6">
                                                    <button id="btnAgregar" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Guardar</button>
                                                </div>
                                                <div id="divActualizarBtn" class="col-6 ocultar">
                                                    <button id="btnUpdate" name="update" type="submit" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " ><i class="far fa-save"></i>Confirmar</button>
                                                    <button id="btnCancel" name="" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " >Cancelar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                			<!-- Modal Cargo -->
                            <div class="modal fade" id="confirmModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"
                            aria-labelledby="" aria-hidden="true" >
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4>Cambio de Cargo</h4>
                                    </div>
                                    <div class="modal-body">
                                        Modificar el cargo cambiar&aacute; los permisos y accesos concedidos al usuario ¿Desea continuar?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si"
                                            data-dismiss="modal">Si</button>
                                        <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-no"
                                            data-dismiss="modal">No</button>
                                    </div>
                                </div>
                            </div>
                        </div>

				<!-- Fin Modal -->
                <!-- Modal ok -->
                        <div class="modal fade" id="confirmok" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"
                        aria-labelledby="" aria-hidden="true" >
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4>Imagen fuera de las dimensiones</h4>
                                </div>
                                <div class="modal-body">
                                    La firma debe tener dimensiones de 300x180
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2  btn-si"
                                        data-dismiss="modal">Ok</button>
                                </div>
                            </div>
                        </div>
                    </div>
				<!-- Fin Modal -->
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/registrousuarios/RegistroUsuarios.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/registrousuarios/RegistroUsuariosForm.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/registrousuarios/ConfigAccesos.js"></script>
    </body>
</html>

