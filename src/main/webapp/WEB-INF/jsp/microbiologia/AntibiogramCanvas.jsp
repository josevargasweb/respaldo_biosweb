<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="AntibiogramCanvas" class="card card-custom gutter-b mt-5">
    <div class="card-header pl-2"> 
        <div class="card-title row col-12 d-flex justify-content-between">
            <h3 class="card-label col-3 pl-0">
                Registro de Paciente
            </h3>
            <div class="col-4 justify-content-right ml-4 pl-4 pr-0">
                <div class="ml-1 float-right">
                    <a id="btnLimpiar" class="navi-link pointer" >
                        <span class="symbol symbol-50 symbol-circle mr-3 ">
                            <span id="circuloLimpiar" class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Limpiar"><i id="iLimpiar" class="la la-broom icon-xl  text-primary"></i></span>
                        </span>
                        <span class="navi-text"></span>
                    </a>
                </div>   
                <div class="ml-1 float-right" >
                    <a id="btnEditar" class="navi-link pointer">
                        <span class='symbol symbol-50 symbol-circle mr-3' >
                            <span id="circuloEditarPaciente" class='symbol-label circuloIcon' data-toggle="tooltip" title="Editar paciente"><i id="iEditPaciente" class="far fa-edit text-dark-50"></i></span>
                        </span>
                    </a>
                </div>
                <div class="ml-1 float-right" >
                    <a id="buscarPaciente" class="navi-link" href="#" >
                        <span class='symbol symbol-50 symbol-circle mr-3'>
                            <span id="circuloBuscarPaciente" class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Buscar paciente"><i id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
                        </span>
                    </a>
                </div>

            </div>

        </div>
    </div>
    <div class="card-body">
        <form id="formRegistroPaciente" method="post">
            <div id="divForm" class="row">
                <div class="col-10">
                    <div class="tab-content mt-5" id="myTabContent">
                        <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                            <div class="row">
                                <div class="form-group row align-items-center col-12">
                                    <input id="validadorEditPac" name="validadorEditPac" type="text" class="form-control ocultar" placeholder="" />
                                    <input type="text"  id="ipEquipo" name="ipEquipo" class="ocultar">
                                    <div class="col-md-4 ">
                                        <label for="selectTipoDocumento" class="">Tipo de documento :</label>
                                        <select id="selectTipoDocumento" class="form-control selectpicker mt-5" name="ldvtdIdtipodocumento" >
                                            <c:forEach var="documento" items="${listaTiposDocumentos}">
                                                <option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
                                            </c:forEach>
                                        </select>
                                        </select>
                                    </div>
                                    <div id="divTxtRut" class="col-md-4">
                                        <label>RUN: *</label>
                                        <input id="txtRut" name="dpRun" type="text" class="form-control mt-5" placeholder="" minlength="11" maxlength="12" autocomplete="off" onkeyup="this.value = this.value.toUpperCase();"/>
                                        <div class="invalid-feedback">RUN inválido</div>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <input id="txtIdPaciente" name="idPaciente" type="text" class="ocultar"/>
                                    <div id="divTxtPasaporte" class="col-md-4 ocultar">
                                        <label>Pasaporte: *</label>
                                        <input id="txtPasaporte" name="dpNrodocumento" type="text" class="form-control mt-5" autocomplete="off" placeholder="" />
                                        <div class="invalid-feedback">RUN inválido</div>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="w-100"></div>
                                    <div id="divTxtNombre" class="col-md-4 mt-6">
                                        <label>Nombres: *<span class="label" data-toggle="popover" data-html="true" data-content="Nombre legal, debe ser idéntico al carné de identidad. Para variaciones usar campo nombre social."><i class="fa la-info-circle"></i></span></label>
                                        <input id="txtNombre" name="dpNombres" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-md-4 mt-6">
                                        <label>Primer Apellido: *</label>
                                        <input id="txtPrimerApellido" name="dpPrimerapellido"  type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-md-4 mt-6">
                                        <label>Segundo Apellido: </label>
                                        <input id="txtSegundoApellido" name="dpSegundoapellido" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                        <div class="d-md-none mb-2"></div>
                                    </div>
                                    <div class="col-md-4 mt-6">
                                        <label for="selectSexo" class="">Sexo: *<span class="label" data-toggle="popover" data-html="true" data-content="Sexo biológico o sexo al nacer. Requerido para análisis objetivos de datos bioquímicos u hormonales."><i class="fa la-info-circle"></i></span></label>
                                        <select class="form-control selectpicker mt-5 disabledForm" id="selectSexo" name="ldvsIdsexo">
                                            <option value="N" disabled selected>Seleccionar</option>
                                            <c:forEach var="sexo" items="${listaSexo}">
                                                <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mt-6">
                                        <label>Fecha Nacimiento: *</label>
                                        <input id="txtFechaNacimiento" name="dpFnacimiento" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                    </div>
                                    <div class="col-md-4 row pr-0 mt-6">
                                        <div class="col-md-4 pr-0">
                                            <label>Años :</label>
                                            <input id="txtAnio" name="" type="text" class="form-control mt-5 " placeholder="" disabled />
                                        </div>
                                        <div class="col-md-4 pr-0">
                                            <label>Meses :</label>
                                            <input id="txtMeses" name="" type="text" class="form-control mt-5 " placeholder="" disabled />
                                        </div>
                                        <div class="col-md-4 pr-0">
                                            <label>Días :</label>
                                            <input id="txtDias" name="" type="text" class="form-control mt-5 " placeholder="" disabled />
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                            <div class="form-group row align-items-center col-12">
                                <div class="checkbox-inline col-12 mb-5">
                                    <label class="checkbox checkbox-primary ml-5">
                                        <input  id="checkboxVIP" name="pacienteVip" type="checkbox" class="disabledForm"/> Paciente VIP
                                        <span></span>
                                    </label>
                                    <label class="checkbox checkbox-primary mr-0">
                                        <input id="checkboxAfro" name="pacienteAfro" type="checkbox" class="disabledForm" /> Paciente Afroamericano 
                                        <span></span>
                                    </label><span class="label" data-toggle="popover" data-html="true" data-content="Dato requerido para el análisis de la función renal mediante la fórmula MDRD."><i class="fa la-info-circle"></i></span>
                                    <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                        <input id="checkBoxExitus" name="exitus" type="checkbox" class="" /> Fallecimiento
                                        <span></span>
                                    </label>
                                </div>
                                <div class="col-md-4 mt-6">
                                    <label class="">Region: </label>
                                    <select id="selectRegion" name="ldvrIdregion" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                        <option value="" disabled selected>Seleccionar</option>
                                        <c:forEach var="region" items="${listaRegiones}">
                                            <option value="${region.ldvrIdregion}">${region.ldvrDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4 mt-6">
                                    <label class="">Comuna : </label>
                                    <select id="selectComuna" name="ldvcIdcomuna" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                        <option value="" disabled selected>Seleccionar</option>
                                    </select>
                                </div>
                                <div class="col-4 mt-6">
                                    <label>Dirección:  </label>
                                    <input id="txtDireccion" name="dpDireccion" type="text" class="form-control disabledForm mt-5" autocomplete="off" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-4 mt-6">
                                    <label class="">Pais Origen: </label>
                                    <select id="selectPais" name="ldvpIdpais" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                        <option value="" disabled selected>Seleccionar</option>
                                        <c:forEach var="pais" items="${listaPaises}">
                                            <option value="${pais.ldvpIdpais}">${pais.ldvpDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4 mt-6">
                                    <label class="">Estado Civil: </label>
                                    <select id="selectEstadoCivil" name="ldvecIdestadocivil" class="form-control selectpicker disabledForm mt-5">
                                        <option value="" disabled selected>Seleccionar</option>
                                        <c:forEach var="estadoCivil" items="${listaEstadosCiviles}">
                                            <option value="${estadoCivil.ldvecIdestadocivil}">${estadoCivil.ldvecDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-4 mt-6">
                                    <label>Nombre Social: </label>
                                    <input id="txtNombreSocial" name="dpNombresocial" type="text" class="form-control disabledForm mt-5"  placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-12 mt-6">
                                    <label>Observaciones: </label>
                                    <input id="txtObservaciones" name="dpObservacion" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
                            <div class="form-group row align-items-center col-12">
                                <div class="col-4 ">
                                    <label>Teléfono: </label>
                                    <input id="txtTelefono" name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4 ">
                                    <label>Email: </label>
                                    <input id="txtEmail" name="dpEmail" type="email" class="form-control disabledForm mt-5" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-4 ">
                                    <label>Código postal:  </label>
                                    <input  id="txtCodigoPostal" name="dpDireccodpostal" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
                            <div  class="form-group row align-items-center col-8">
                                <div class="col-md-12">
                                    <label class="">Patolog&iacute;a: </label>
                                    <select id="selectPatologias" class="form-control selectpicker ">
                                        <option value="N" disabled selected>Seleccionar</option>
                                        <c:forEach var="patologia" items="${listaPatologias}">
                                            <option value="${patologia.ldvpatIdpatologia}">${patologia.ldvpatDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button id="btnPatologia" type="button" class="btn btn-light-primary font-weight-bold mr-2 mt-5 ml-4">Agregar Patolog&iacute;a</button>
                                <table id='tablaPatologia' class="col-12 mt-5">
                                    <thead>
                                        <tr>
                                            <th>Patolog&iacute;a</th>
                                            <th>Observacion</th>
                                            <th>Eliminar</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tbodyPatologias">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="kt_tab_pane_5" role="tabpanel" aria-labelledby="kt_tab_pane_5">
                            <div class="form-group row align-items-center col-8">
                                <div class="col-6">
                                    <label>Nombre: *</label>
                                    <input id="txtNombreContacto" name="dcNombre" type="text" class="form-control " placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-6">
                                    <label class="">Relacion: </label>
                                    <select id="selectPacienteRelacion" name="ldvcprIdcontactopacienterel" class="form-control selectpicker ">
                                        <option value="N" disabled selected>Seleccionar</option>
                                        <c:forEach var="pacRelacion" items="${listaPacienteRelacion}">
                                            <option value="${pacRelacion.ldvcprIdcontactopacienterel}">${pacRelacion.ldvcprDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label>Teléfono: *</label>
                                    <input id="txtTelefonoContacto" name="dcTelefono" type="text" class="form-control " placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="col-md-6">
                                    <label class="">Sexo: </label>
                                    <select id="selectSexoContacto" name="ldvsIdsexoContacto" class="form-control selectpicker ">
                                        <option value="N" disabled selected>Seleccionar</option>
                                        <c:forEach var="sexo" items="${listaSexo}">
                                            <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="kt_tab_pane_6" role="tabpanel" aria-labelledby="kt_tab_pane_6">
                            <div class="form-group row align-items-center col-12">
                                <div class="col-6">
                                    <label id="lblRunMadre">RUN Madre: </label>
                                    <input id="txtRutMadre" name="rutMadre" type="text" class="form-control " placeholder="" minlength="11" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"/>
                                    <div id="divFeedbackRutMadre" class="invalid-feedback">RUN inválido</div>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                                <div class="checkbox-inline col-3 mb-5 right-align">
                                    <label class="checkbox checkbox-primary ml-5">
                                        <input id="checkboxPartoMultiple" name="esPartoMultiple" type="checkbox" class=""/> Parto Múltiple
                                        <span></span>
                                    </label>
                                </div>
                                <div class="col-3">
                                    <label>N° Parto Múltiple: </label>
                                    <input id="txtNroPartoMultiple" name="dpRnnumero" type="text" class="form-control " placeholder=""/>
                                    <div class="d-md-none mb-2"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-2">
                    <ul class="nav  flex-column nav-pills">
                        <li class="nav-item" style="max-height: 38.5px;">
                            <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1">
                                <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                <span class="nav-text">Datos Paciente</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2">
                                <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                <span class="nav-text">Eventos</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3">
                                <span class="nav-icon"><i class="la la-phone"></i></span>
                                <span class="nav-text">Acciones</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4">
                                <span class="nav-icon"><i class="fas fa-book-medical"></i></span>
                                <span class="nav-text">Patolog&iacute;as</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_5">
                                <span class="nav-icon"><i class="flaticon2-avatar"></i></span>
                                <span class="nav-text">Contacto</span>
                                <span id="spanContacto" class="ocultar label label-danger font-weight-bold label-inline"><i class="fa la-exclamation-circle text-white"></i></span>
                            </a>
                        </li>
                        <li id="liDatosMadres" class="nav-item ocultar">
                            <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_6">
                                <span class="nav-icon"><i class="fas fa-female"></i></span>
                                <span class="nav-text">Datos Madre</span>
                                <span id="spanDatosMadre" class="ocultar label label-danger font-weight-bold label-inline"><i class="fa la-exclamation-circle text-white"></i></span>
                            </a>
                        </li>
                    </ul>
                </div>
                <button id="btnAgregarPaciente" name="insert" type="submit" class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5 disabledForm" ><i class="far fa-save"></i>Guardar</button>

                <input id="btnUpdatePaciente" name="update" type="submit" class="btn btn-light-primary font-weight-bold mr-2 ocultar" value="Confirmar"/>
                <a id="btnConfirmarDatosPac" onclick="goBack()"  class="btn btn-light-primary font-weight-bold mr-2 float-right ocultar"><i class="la la-search"></i>Confirmar Datos</a>
            </div>
        </form>
    </div>
</div>