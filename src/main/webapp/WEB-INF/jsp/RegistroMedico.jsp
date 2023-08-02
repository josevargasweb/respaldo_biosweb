<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>


        
     <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Configuración de Médicos</title>
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
        
        <c:if test="${mensaje!=null}"><input type="hidden" id="messageSuccess" value="${mensaje}" /></c:if>
        <c:if test="${mensajeError!=null}"><input type="hidden" id="messageError" value="${mensajeError}" /></c:if>
        
        
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0">
                    <div id="panelMedicos" class="card border-15">
                        <div class="card-header border-15" id="headingMedicos">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="true" aria-controls="collapseHeader">
                                    B&uacute;squeda de M&eacute;dicos
                                </h3>
                                <div class="card-buttons col-2 d-flex justify-content-end">
                                    <a id="nuevoMedico" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloAgregarMedico" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Nuevo M&eacute;dico">
                                                <i id="iAgregarMedico" class='fa fas fa-user-plus text-blue'></i>
                                            </span>
                                        </span>
                                    </a>
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-30 symbol-circle mr-3 ">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i></span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>
                             
                            </div>
                        </div>
                        <div id="collapseHeader" class="collapse show container-content" aria-labelledby="headingMedicos" data-parent="#headingMedicos">
                            <div class="card-body row">
                                <!-- FORMULARIO FILTRO -->
                                <div class="col">
                                    <div class="col-12">
                                        <div id='divTxtRutFiltro' class="col-12">
                                            <div class="form-group row mb-0">
                                                <label id="lblRutFiltro" class="col-3 col-form-label">RUN
                                                </label>
                                                <div class="col-9">
                                                  <!--  <input class="form-control" placeholder="" id="txtFiltroRut" type="text" minlength="11" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"/> -->
                                                <input  id="txtFiltroRut"
                                                                type="text" class="form-control" 
                                                                minlength="11" maxlength="12" autocomplete="off"
                                                                onkeyup="this.value = this.value.toUpperCase();" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label  class="col-3 col-form-label">Nombre</label>
                                                <div class="col-9">
                                                    <input  class="form-control" placeholder="" id="txtFiltroNombre" type="text"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label  class="col-3 col-form-label">Primer Apellido</label>
                                                <div class="col-9">
                                                    <input  class="form-control" placeholder="" id="txtFiltroApellido" type="text"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <div class="form-group row mb-0">
                                                <label  class="col-3 col-form-label">Segundo Apellido</label>
                                                <div class="col-9">
                                                    <input class="form-control" placeholder="" id="txtFiltroSegundoApellido" type="text"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12">
                                            <label>
                                                <input id="chkMostrarActivos" type="checkbox" /> Mostrar solo activos
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- FIN FORMULARIO FILTRO -->
                                <!-- TABLA FILTRO -->
                                <div class="col">
                                    <div class=" table-container mb-3">
                                        <table id="tableFiltro" class="table table-hover table-striped">
                                            <thead>
                                                <tr id="trHeader">
                                                    <th scope="col">#</th>
                                                    <th>Identificador</th>
                                                    <th class="">M&eacute;dico</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tbodyFiltro">

                                            </tbody>
                                        </table>
                                        <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron m&eacute;dicos con los datos provistos.</label>
                                    </div>
                                    <label id="lblTotalFiltro">M&eacute;dicos encontrados: <span id="totalFiltro"></span></label>
                                </div>
                                <!-- FIN TABLA FILTRO -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- FORMULARIO REGISTRO MEDICO -->
                <div class="card mt-5 border-15">
                    <div class="card-header border-15"> 
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 id="tituloRegistro" class="card-label col-4 pl-0">
                                Registro de M&eacute;dico
                            </h3>
                            <div class="col-2 justify-content-right ml-4 pl-4 pr-0">
                                <div class="float-right">
                                    <a id="btnLimpiar" class="navi-link" href="#">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                     <a id="btnLimpiar2" class="navi-link" href="#">
                                        <span class="symbol symbol-30 symbol-circle">
                                            <span id="circuloLimpiar" class="symbol-label bg-hover-blue hoverIcon" data-toggle="tooltip" title="Limpiar">
                                                <i id="iLimpiar" class="la la-broom icon-xl text-blue"></i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>   
                                <div id="divBtnEditar" class="float-right" >
                                    <a id="btnUpdater" class="navi-link" href="#" >
                                        <span class='symbol symbol-30 symbol-circle' >
                                            <span id="circuloEditarMedico" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar m&eacute;dico">
                                                <i id="iEditMedico" class="far fa-edit text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div id="divCancelEdit" class="ml-1 float-right neverSeen" >
                                    <a id="btnCancelarUpdate" class="navi-link" href="#" >
                                        <span class='symbol symbol-30 symbol-circle' >
                                            <span id="circuloCancelEdit" class='symbol-label bg-hover-danger circuloIcon' data-toggle="tooltip" title="Cancelar edición">
                                                <i id="iCancelEdit" class="fas fa-times icon-xl text-danger"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="ml-1 float-right" >
                                    <a id="buscarMedico" class="navi-link collapseMedico" href="#" >
                                        <span class='symbol symbol-30 symbol-circle'>
                                            <span id="circuloBuscarPaciente" class='symbol-label bg-hover-blue hoverIcon' data-toggle="tooltip" title="Buscar m&eacute;dico">
                                                <i id="iBuscarMedico" class="fas fa-search text-blue"></i>
                                            </span>
                                        </span>
                                    </a>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="card-body" >
                    <form id="" method="POST" >
                        <input type="hidden"  id="idMedico" name="idMedico" class="neverSeen">
                        <input type="hidden"  id="validadorOrden" name="validadorOrden" class="neverSeen">
                        <input type="hidden"  id="ipEquipo" name="ipEquipo" class="neverSeen">
                        <div id="divForm">
                            <div class="col-12">
                                <div class="col-12 row">
                                    <div class="form-group row align-items-center col-12">
                                        <div id="divTxtRut" class="col-md-3 mt-3">
                                            <label>RUN:*</label>
                                          <!--   <input required id="txtRut" name="cmRun" type="text" class="form-control mt-3" placeholder="" onkeypress="return check(event)" class="validate alwaysMayus" minlength="11" maxlength="12" /> -->
                                          <input required id="txtRut" name="cmRun"
                                                            type="text" class="form-control mt-3 " 
                                                            minlength="11" maxlength="12" autocomplete="off"
                                                            onkeyup="this.value = this.value.toUpperCase();" />
                                                            
                                            <div id="invalid-feedback" class="invalid-feedback">RUN inválido</div>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-md-3 mt-3">
                                            <label>Nombre:*</label>
                                            <input required id="txtNombre"  name="cmNombres" type="text" maxlength="30" class="form-control mt-3 " placeholder="" onkeypress="return check(event)" class="validate"/>
                                            <div class="invalid-feedback">Ingrese Nombre</div>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-md-3 mt-3">
                                            <label for="txtPrimerApellido">Primer Apellido:*</label>
                                            <input required placeholder="" id="txtPrimerApellido" maxlength="30" name="cmPrimerapellido" onkeypress="return check(event)" type="text" class="form-control mt-3 " onkeypress="">
                                            <div class="invalid-feedback">Ingrese Primer Apellido</div>
                                            <div class="d-md-none mb-2"></div>
                                        </div> 
                                        <div class="col-md-3 mt-3">
                                            <label for="txtSegundoApellido">Segundo Apellido:</label>
                                            <input placeholder="" id="txtSegundoApellido" maxlength="30" name="cmSegundoapellido" type="text" class="form-control mt-3 " onkeypress="">
                                        </div>
                                        <div id="divSelectSexo" class="col-md-3 mt-3">
                                            <label for="selectSexo" class="">Sexo: *</label>
                                            <select id="selectSexo" name="ldvSexo" class="form-control mt-3" required>
                                                <c:forEach var="sexo" items="${listaSexo}">
                                                    <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div> 
                                        <div class="col-md-3 mt-3">
                                            <label for="txtEmail">Email:</label>
                                            <input placeholder="" id="txtEmail" maxlength="100" name="cmEmail" type="email" class="form-control mt-3 " onkeypress="">
                                            <div class="invalid-feedback">Ingrese Email Valido</div>
                                            <div class="d-md-none mb-2"></div>
                                        </div>
                                        <div class="col-md-3 mt-3">
                                            <label for="txtTelefono">Teléfono:</label>
                                            <input placeholder="" id="txtTelefono" maxlength="30" name="cmTelefono" type="text" class="form-control mt-3 " onkeypress="">
                                            <div class="invalid-feedback">Ingrese Telefono</div>
                                            <div class="d-md-none mb-2"></div>
                                        </div> 
                                        <div id="" class="col-md-3 mt-3">
                                            <label for="selectSexo" class="">Institucion:</label>
                                            <select  id="SelectInstitMedic" name="cfgInstitucionesdesalud" class="form-control mt-3"  >
                                                <c:forEach var="institMedicas" items="${listaInsitucionesSalud}">
                                                    <option value="${institMedicas.cidsIdinstituciondesalud}">${institMedicas.cidsCodigo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div id="divSelectEspecialidadMedic" class="col-md-3 mt-3">
                                            <label for="selectSexo" class="">Especialidad:</label>
                                            <select id="SelectEspecialidadMedic" name="ldvEspecialidadesmedicas" class="form-control selectpicker mt-3 " data-live-search="true">
                                                <c:forEach var="espMedicas" items="${listEspecialidadesMedicas}">
                                                    <option value="${espMedicas[0]}">${espMedicas[1]}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-3 mt-3">
                                            <label for="RegistroSalud">Registro de salud del medico:</label>
                                            <input placeholder="" id="RegistroSalud" name="cmRegistrodesalud" maxlength="8" type="text" class="form-control mt-3 " onkeypress="">
                                        </div>
                                        <div class="col-lg-4 form-group mt-3">
                                            <label id="lblEstado" class="col-4 col-form-label">Activo</label>
                                            <div class="col-4">
                                                <label class="switch-content switch-comprobante switch-active"> <input
                                                    id='checkBoxLever' type="checkbox" tabindex="-1" name='mcActivo' class="formExamen" checked value='S'>
                                                    <div></div>
                                                </label> 
                                            </div>
                                        </div>
                                    </div>
                                    <button id="btnAgregarMedico" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="insert"  type="submit" ><i class="far fa-save"></i>Guardar</button>

                                    <div class="" id="divBtnUpdateMedico">
                                        <button id="btnGuardarUpdate" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" name="update"  type="submit"><i class="far fa-save"></i>Confirmar</button>
                                    </div>
                                    
                                    <!--
                                    <div class="">
                                        <button id="btnUpdater" class="btn white right black-text"  type="button">Editar</button>
                                    </div>
                                    
                                    <div class="">
                                        <button id="btnCancelarUpdate" class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5"  type="button"><i class="fas fa-times"></i>Cancelar Edicion</button>
                                    </div>
                                    -->
                                    
                                    <div class="">
                                        <a id="confirmarDatos" onclick="goBack()" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2" type="" value="">CONFIRMAR DATOS</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </div>

        <!--
                <div class="row col s3 right">
                    <div class="row">
                        <div class="col s12">
                            <div class="" style='min-height: 200px;'>
                                <label class="titulo">Búsqueda de Medico</label>
                                <hr>
                                <div class="row mt20">
                                    <div class="col s6 black-text separacion">
                                        <div id="divTxtRutFiltro" class="filtroColorInput input-field col s12">
                                            <input placeholder="" id="txtFiltroRut" type="text"  minlength="11" maxlength="12"  class="alwaysMayus">
                                            <label  for="txtFiltroRut">RUT</label> 
                                            <span class="helper-text" data-error="Ingrese RUT Valido"></span>
                                        </div>
                                        <div id="divTxtPasaporteFiltro" class="filtroColorInput col s12 input-field ocultar">
                                            <input placeholder="" id="txtPasaporteFiltro" type="text" class="alwaysMayus">
                                            <label id="lblPasaporteFiltro" for="txtPasaporteFiltro">Pasaporte</label>
                                        </div>
                                        <div class="filtroColorInput input-field col s12">
                                            <input placeholder="" id="txtFiltroNombre" type="text" class="alwaysMayus" onkeypress="">
                                            <label for="txtFiltroNombre">Nombre</label> 
                                        </div>
                                        <div class="filtroColorInput input-field col s6">
                                            <input placeholder="" id="txtFiltroApellido" type="text" class="alwaysMayus" onkeypress="">
                                            <label for="txtFiltroApellido">Primer Apellido</label> 
                                        </div>
                                        <div class="filtroColorInput input-field col s6">
                                            <input placeholder="" id="txtFiltroSegundoApellido" type="text" class="alwaysMayus" onkeypress="">
                                            <label for="txtFiltroSegundoApellido">Segundo Apellido</label> 
                                        </div>
                                        <div class="col s12">
                                            <a id="btnBuscarFiltro"  class="ml10 waves-effect waves-light btn blue right">BUSCAR<i class="material-icons right">search</i></a>
                                        </div>
                                    </div>
                                    <div class="col s6">
                                        <div class="grey lighten-5 borderTable">
                                            <table id="tableFiltro" class="striped black-text text-darken-4 ">
                                                <thead>
                                                    <tr>
                                                        <th>N Documento</th>
                                                        <th class="">Medico</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tbodyFiltroMedico">
        
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
        
                    <form id="" method="POST" autocomplete="off">
                        <input type="text"  id="idMedico" name="idMedico" class="neverSeen">
                        <input type="text"  id="validadorOrden" name="validadorOrden" class="neverSeen">
                        <input type="text"  id="ipEquipo" name="ipEquipo" class="neverSeen">
                        <label class="titulo">Registro de Medico</label>
                        <hr>
                        <div id="divForm">
                            <div id="divTxtRut" class="col s3 input-field ">
                                <input placeholder="" id="txtRut" name="cmRun" type="text" onkeypress="return check(event)" class="validate alwaysMayus" minlength="11" maxlength="12" >
                                <label id="lblTxtRut" for="txtRut">RUT *</label>
                                <span class="helper-text" data-error="Ingrese RUT Valido"></span>
                            </div>
        
                            <div class="col s3 input-field ">
                                <input  placeholder="" id="txtNombre" maxlength="30" name="cmNombres" onkeypress="return check(event)" type="text" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="txtNombre">Nombre *</label>
                                <span class="helper-text" data-error="Ingrese Nombre"></span>
                            </div> 
                            <div class="col s3 input-field ">
                                <input placeholder="" id="txtPrimerApellido" maxlength="30" name="cmPrimerapellido" onkeypress="return check(event)" type="text" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="txtPrimerApellido">Primer Apellido *</label>
                                <span class="helper-text" data-error="Ingrese Primer Apellido"></span>
                            </div> 
        
                            <div class="col s3 input-field ">
                                <input placeholder="" id="txtSegundoApellido" maxlength="30" name="cmSegundoapellido" type="text" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="txtSegundoApellido">Segundo Apellido</label>
                            </div> 
                            <div class="col s3 input-field ">
                                <input placeholder="" id="txtTelefono" maxlength="30" name="cmTelefono" type="text" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="txtTelefono">Telefono</label>
                                <span class="helper-text" data-error="Ingrese Telefono"></span>
                            </div> 
                            <div class="col s3 input-field ">
                                <input placeholder="" id="txtEmail" maxlength="100" name="cmEmail" type="email" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="txtEmail">Email</label>
                                <span class="helper-text" data-error="Ingrese Email Valido"></span>
                            </div> 
                            <div class="col s3 input-field ">
                                <input placeholder="" id="RegistroSalud" name="cmRegistrodesalud" maxlength="8" type="text" class="validate disabledForm alwaysMayus" onkeypress="">
                                <label for="RegistroSalud">registro de salud del médico</label>
                                <span class="helper-text" data-error="Ingrese Email Valido"></span>
                            </div> 
                            <div id="divSelectSexo" class="input-field col s3">
                                <select disabled id="selectSexo" name="ldvSexo" class="validate disabledForm">
                                    <option value="N" disabled selected>Elegir sexo</option>
                                </select>
                            </div>
        
                            <div id="divSelectEspecialidadMedic" class="input-field col s3">
        
                                <select disabled id="SelectEspecialidadMedic" name="ldvEspecialidadesmedicas" class="disabledForm">
                                    <option value="N" disabled selected>Elegir Especialidad</option>
                                </select>
                            </div>
                            <div id="divSelectInstitucionMedic" class="input-field col s3">
                                <select disabled id="SelectInstitMedic" name="cfgInstitucionesdesalud" class="disabledForm">
                                    <option value="N" disabled selected>Elegir Institucion</option>
                            </div>
        
                            <div class="input-field col s3">
                                <div>
                                    <label >Estado medico</label  >
                                </div>
        
                                <div class="switch">
                                    <label>
                                        Activo
                                        <input id='checkBoxLever' name='mcActivo' class="disabledForm" type='checkbox' checked value='S'>
                                        <span class="lever"></span>
                                        Inactivo
                                    </label>
                                </div>
                            </div>
        
                            <div class="col s4 right">
        
                                <div class="col s4" id="divbtnAgregarMedico">
                                    <input id="btnAgregarPaciente" class="btn blue" name="insert"  type="submit" value="AGREGAR"/>
                                </div>  
        
                                <div class="col s4" id="divBtnUpdateMedico">
                                    <input id="btnGuardarUpdate" class="btn blue" name="update"  type="submit" value="Updatear"/>
                                </div>
                                <div class="col s4">
                                    <button id="btnLimpiar" class="btn white right black-text" type="button">Limpiar</button>
                                </div>
                                <div class="col s4">
                                    <button id="btnUpdater" class="btn white right black-text"  type="button">Editar</button>
                                </div>
                                <div class="col s4">
                                    <button id="btnCancelarUpdate" class="btn white right black-text"  type="button">Cancelar Edicion</button>
                                </div>
                                <div class="col s4">
                                    <a id="confirmarDatos" href="./Orden" class="btn blue" type="" value="">CONFIRMAR DATOS</a>
                                </div>
        
                            </div>
                        </div>
                    </form> 
                </div>
                <div class="fixed-action-btn">
                    <a class="btn-floating btn-large blue">
                        <i class="large material-icons">more_horiz</i>
                    </a>
                    <ul>
                        <li><a class="btn-floating red"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating yellow darken-1"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating green"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating blue"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating red"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating yellow darken-1"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating green"><i class="material-icons">person_add</i></a></li>
                        <li><a class="btn-floating blue"><i class="material-icons">person_add</i></a></li>
                    </ul>
                </div>
            </div>
        -->
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/RegistroMedico.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/rutValidator.js"></script>
        
    </body>
</html>