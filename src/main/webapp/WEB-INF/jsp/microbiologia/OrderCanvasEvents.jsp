<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="eventosOrdenModal" tabindex="-1"
            role="dialog" aria-labelledby="eventosOrdenModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">

        <div class="modal-content">
            <div class="modal-body">
                <h3 id="tituloEvento" class="text-center" id="eventosOrdenModalLabel">
                </h3>
                <input type="hidden" id="idOrdenEvento" >
                <input type="hidden" id="idTestEvento" >
                <div class="mt-3 d-flex justify-content-end">	
                    <input  type="text" id="textBuscarEvento" class="mr-2 col-3 form-control form-control-sm" autocomplete="off "/>
                    <input  type="button" class="btn btn-blue-primary btn-normal mr-4 " id="buscarEventoOrden" value="Buscar"/>

                    <input type="button" class="btn btn-blue-primary btn-normal mr-4 " id="buscarEventoTest" value="Buscar"/>
                    <!--
                    <button id="limpiarBusqueda" class="btn btn-blue-primary btn-normal font-weight-bold mr-4"><i id="" class="la la-broom icon-xl"></i></button>
                    -->
                    <button id="btnExcelEvento" class="btn btn-blue-primary btn-normal ml-8">
                        <i class="fa fa-download" aria-hidden="true"></i>Descargar Excel
                    </button>
                    
                </div>
                <div class="tab-pane fade show active" id="OrderCanvasEvents" role="tabpanel" aria-labelledby="OrderCanvasEvents">
                    <div class="form-group row align-items-center col-12">
                        <div class="col scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 270px;overflow-y: auto;margin-right: 0px;padding-right: 0px;">
                            <table id="tb" class="table table-hover table-striped nowrap compact-xs">
                                <thead>
                                    <tr>
                                        <th scope="col">Fecha</th>
                                        <th scope="col">Usuario</th>
                                        <th scope="col">IP</th>
                                        <th scope="col">Paciente</th>
                                        <th scope="col">N°Orden</th>
                                        <th scope="col">Examen</th>
                                        <th scope="col">Test</th>
                                         <th scope="col">Muestra</th>
                                        <th scope="col">Evento</th>
                                    </tr>
                                </thead>
                                <tbody id="idOrderCanvasEventsTable" class="nowrap">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                    <button type="button" id="btnCerrarModalEvento"
                            class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
                            data-dismiss="modal">
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>