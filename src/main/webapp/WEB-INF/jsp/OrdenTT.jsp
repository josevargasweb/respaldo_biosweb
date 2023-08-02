<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BIOS-LIS | Orden</title>
        <jsp:include page="common/Styles.jsp"/>
    </head>
    <body>
        
        <jsp:include page="common/Menu.jsp"/>
        <jsp:include page="common/Header.jsp"/>
        <jsp:include page="common/AlertaSession.jsp"/>
        
        <header>

            <ul id="slide-out" class="sidenav sidenav-fixed ">
                <li>
                    <div class="user-view">
                        <!--    <div class="">
                                <img src="<%=request.getContextPath()%>/resources/img/background.jpg">
                            </div> -->
                        <img id="imgBiosLis" class="responsive-img right col s8 mt10 mr20" src="<%=request.getContextPath()%>/resources/img/Logo_BiosLis_Web_Login_color.png" />
                        <a href="#user"><img class="circle" src="<%=request.getContextPath()%>/resources/img/icon-man.png"></a>
                        <a href="#name"><span class="grey-text text-darken-3 name">PATRICIO SANCHEZ</span></a>
                        <a href="#email"><span class="grey-text text-darken-3 email">patricio.sanchez@grupobios.cl</span></a>
                    </div>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Atencion de Pacientes<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons blue-text">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Resultados<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Reportes<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Configuración<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Entorno<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Utiles<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones toma de muestra</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li class="bold"><a class="collapsible-header waves-effect waves-teal"><i class="material-icons blue-text">people_alt</i>Ayuda<i class="material-icons right" style="margin-right:0;">arrow_drop_down</i></a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="color.html"><i class="material-icons">edit</i>Modificar orden</a></li>
                                    <li><a href="color.html"><i class="material-icons">assignment_turned_in</i>Comprobante de atención</a></li>
                                    <li><a href="color.html"><i class="material-icons">cached</i>Reasignar &oacute;rdenes</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">person_add</i>Registro de paciente</a></li>
                                    <li><a href="color.html"><i class="material-icons">add_circle</i>Registro de documento</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir etiquetas por lotes</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir &oacute;rdenes de trabajo</a></li>
                                    <li><a href="color.html"><i class="material-icons">print</i>Imprimir listas de trabajo</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">local_shipping</i>Transporte de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">done_all </i>Recepcion de muestras</a></li>
                                    <li><a href="color.html"><i class="material-icons">location_on</i>Puntos de control</a></li>
                                    <li><a href="color.html"><i class="material-icons">fullscreen</i>Atención de radiología</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">chrome_reader_mode</i>Indicaciones</a></li>
                                    <li><div class="divider"></div></li>
                                    <li><a href="color.html"><i class="material-icons">exit_to_app</i>Salir</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                </li>
            </ul>
            <!-- <a href="#" data-target="slide-out" class="sidenav-trigger"><i class="material-icons">menu</i></a> -->
        </header>
        <main>
            <div class="container">
                <div class="row col s12 ">
                    <div class="col s12">
                        <div class="" style='min-height: 200px;'>
                            <label class="titulo">Orden</label>
                            <hr>
                            <div class="row mt20 ">
                                <div class="col s7 black-text separacion">
                                    <div  class="input-field col s12">
                                        <select id="selectTipoDocumentoFiltro" name="ldvtdIdtipodocumento">
                                            <option value="N" disabled selected>Seleccionar</option>
                                            <c:forEach var="documento" items="${listaTiposDocumentos}">
                                                <option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
                                            </c:forEach>
                                        </select>
                                        <label>Tipo de Documento </label>
                                        <span class="helper-text" data-error="Seleccione Tipo de Documento"></span>
                                    </div>
                                    <div id="run" class="filtroColorInput input-field col s12">
                                        <input placeholder="" id="rut" type="text" class="" >
                                        <label id="lblRutFiltro"  for="txtFiltroRut">RUN</label> 
                                        <span class="helper-text" data-error="Ingrese RUN Valido"></span>
                                    </div>

                                    <div class="filtroColorInput input-field col s12">
                                        <input placeholder="" id="nombre" type="text" class="" >
                                        <label for="txtFiltroNombre">Nombre</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input placeholder="" id="apellido" type="text" class="" >
                                        <label for="txtFiltroApellido">Primer Apellido</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input placeholder="" id="apellido2" type="text" class="" >
                                        <label for="txtFiltroSegundoApellido">Segundo Apellido</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input placeholder="" id="edad" type="text" class="" >
                                        <label for="txtFiltroSegundoApellido">Edad</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input placeholder="" id="id" type="text" class="" >
                                        <label for="txtFiltroSegundoApellido">id</label> 
                                    </div>
                                    <div class="col s12">

                                        <a id="btnBuscarPaciente" href="./RegistroPaciente" class="ml10 waves-effect right waves-light btn blue">Buscar/Agregar Paciente<i class="material-icons right">search</i></a>
                                    </div>
                                </div>
                                <div class="col s5 ">
                                    <div class="col s12">
                                        <div class=" input-field col s6">
                                            <input placeholder="" id="" type="text" class="" >
                                            <label for="Cama">Cama</label> 
                                        </div>
                                        <div class="input-field col s6">
                                            <select class="selectNormal" id="" name="">
                                                <option value="1" >Sin especificar</option>
                                                <option value="2" >2</option>

                                            </select>
                                            <label>Cama </label>
                                            <span class="helper-text" data-error=""></span>
                                        </div>

                                    </div>
                                    <div class="input-field col s12">
                                        <select class="selectNormal" id="tipoPaciente" name="">
                                            <option value="1" >Ambulatorio</option>
                                            <option value="2" >2</option>

                                        </select>
                                        <label>Tipo paciente </label>
                                        <span class="helper-text" data-error=""></span>
                                    </div>
                                    <div class="input-field col s12">
                                        <select class="selectNormal" id="Procedencia" name="">
                                            <option value="1" >Ambulatorio</option>
                                            <option value="2" >2</option>

                                        </select>
                                        <label>Procedencia </label>
                                        <span class="helper-text" data-error=""></span>
                                    </div>
                                    <div class="input-field col s12">
                                        <select class="selectNormal" id="servicio" name="">
                                            <option value="1" >Ambulatorio</option>
                                            <option value="2" >2</option>

                                        </select>
                                        <label>Tipo de Documento </label>
                                        <span class="helper-text" data-error=""></span>
                                    </div>
                                    <div class="input-field col s12">
                                        <select class="selectNormal" id="convenio" name="">
                                            <option value="1" >Ambulatorio</option>
                                            <option value="2" >2</option>

                                        </select>
                                        <label>Servicio </label>
                                        <span class="helper-text" data-error=""></span>
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input  placeholder="" id="txtRutMedico" type="text" onkeypress="return check(event)" class="validate alwaysMayus cleanMedic" minlength="11" maxlength="12"  >
                                        <label for="txtRutMedico">RutMedico</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input disabled placeholder="" id="nombreM" type="text" onkeypress="return check(event)" class="validate alwaysMayus cleanMedic" >
                                        <label for="nombreM">Nombre</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input disabled placeholder="" id="apellido1M" type="text" onkeypress="return check(event)" class="validate alwaysMayus cleanMedic" >
                                        <label for="apellido1M">Primer apellido</label> 
                                    </div>
                                    <div class="filtroColorInput input-field col s6">
                                        <input disabled placeholder="" id="apellido2M" type="text" onkeypress="return check(event)" class="validate alwaysMayus cleanMedic" >
                                        <label for="apellido2M">Segundo apellido</label> 
                                    </div>
                                    <div class="col s12 input-field">
                                        <select id="medicoComboBox" class="" name="">
                                            <c:forEach var="med" items="${listaMedicos}">
                                                <option value="${med[0]}">${med[1]} ${med[2]}</option>
                                            </c:forEach>
                                        </select>
                                        <label>Medico </label>
                                        <a id="btnBuscarMedico" href="./RegistroMedico" class="ml10 waves-effect right waves-light btn blue">Agregar/Buscar Medico<i class="material-icons right">search</i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row step-no-background-thin">
            <div class="mt-step-desc">
                <div class="font-dark bold uppercase">No Background Thin</div>
                <div class="caption-desc font-grey-cascade">Thin variation for default no background element style. Activate by adding <pre class="mt-code">.step-no-background-thin</pre> class to the <pre class="mt-code">.row</pre> element</div>
                <br/> </div>
            <div class="col-md-4 mt-step-col">
                <div class="mt-step-number font-grey-cascade">1</div>
                <div class="mt-step-title uppercase font-grey-cascade">Purchase</div>
                <div class="mt-step-content font-grey-cascade">Purchasing the item</div>
            </div>
            <div class="col-md-4 mt-step-col error">
                <div class="mt-step-number bg-white font-grey">2</div>
                <div class="mt-step-title uppercase font-grey-cascade">Payment</div>
                <div class="mt-step-content font-grey-cascade">Complete your payment</div>
            </div>
            <div class="col-md-4 mt-step-col done">
                <div class="mt-step-number bg-white font-grey">3</div>
                <div class="mt-step-title uppercase font-grey-cascade">Deploy</div>
                <div class="mt-step-content font-grey-cascade">Receive item integration</div>
            </div>
        </div>

    </main>
    <jsp:include page="common/Scripts.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Orden.js"></script>

</body>
</html>