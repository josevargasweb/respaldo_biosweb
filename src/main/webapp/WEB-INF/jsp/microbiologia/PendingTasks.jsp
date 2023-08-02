<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div id="OrdersSearch" class="card card-custom gutter-b mt-5">
                    <div class="card-header" id="headingOne8">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 class="card-label col-6 pl-0 pb-3" data-toggle="collapse" data-target="#collapseOne8">
                                Tareas Pendientes de Microbiología
                            </h3>


                        </div>
                    </div>
                    <div id="collapseOne8" class="collapse show" data-parent="#accordionExample8">
                        <div class="card-body row">
                            <div class="col">
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">N° Orden:</label>
                                        <div class="col-7">
                                            <input id="idSearchOrderStart" class="form-control" name="" placeholder=""
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Fecha inicio:</label>
                                        <div class="col-7">
                                            <input id="idSearchStartDate" class="form-control" name="" placeholder=""
                                                type="date" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Fecha fin:</label>
                                        <div class="col-7">
                                            <input id="idSearchEndDate" class="form-control" name="" placeholder=""
                                                type="date" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Nombre:</label>
                                        <div class="col-7">
                                            <input id="idSearchName" class="form-control" name="" placeholder="2 caracteres mínimo"
                                                type="text" />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Apellidos:</label>
                                        <div class="col-7">
                                            <input id="idSearchSurname" class="form-control" name="" placeholder="2 caracteres mínimo"
                                                type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Tipo documento:</label>
                                        <div class="col-7">
                                            <select id="idSearchDocumentType" class="form-control">
                                                <option value="" ></option>
                                             </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">N° Documento:</label>
                                        <div class="col-7">
                                            <input id="idSearchDocumentNumber" class="form-control" name="" placeholder=""
                                                type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Tipo de atención:</label>
                                        <div class="col-7">
                                            <select id="idSearchAtentionType" class="form-control">
                                                <option value="" ></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>        
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Servicio:</label>
                                        <div class="col-7">
                                            <select id="idSearchService" class="form-control">
                                                <option value="" ></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-6">
                                    <a id="searchButton" href="#" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="la la-search"></i>Buscar</a>
                                 </div>

                            </div>
                            <div id="pendingTasksTableContainer" class="col scroll scroll-pull" data-scroll="true"
                                data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 540px">

                                <table id="tablePendingTasks" class="table table-hover">
                                    <thead>
                                        <tr id="trHeader">
                                            <th scope="col">N° Examen</th>
                                            <th id="thDateTableFilter" scope="col">Fecha</th>
                                            <th id="thOrderTableFilter" scope="col">N° Orden</th>
                                            <th id="thNameTableFilter" scope="col">Nombre</th>
                                            <th id="thTaskTableFilter" scope="col">Tarea</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody id="pendingTasksData">

                                    </tbody>
                                </table>
                                <label id="lblFilterError" class="textError text-red" hidden>No se encontraron
                                    tareas con los datos previstos.</label>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- FIN FORMULARIO TAREAS PENDIENTES -->
                </div>