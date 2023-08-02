<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step8 modal fade" id="step8AddAB" tabindex="-1" role="dialog"
                    aria-labelledby="datosPacienteModal" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title" id="exampleModalLabel">Añadir Antibióticos</h1>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                                </button>
                            </div>
                            <div class="modal-body mb-10">
                                <div class="row">
                                    <div class="col-5">
                                        <h3>Antibióticos Cultivo Disponibles</h3>
                                        <table id="tableAvailableAddABModal" class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col"> </th>
                                                    <th scope="col"> Antibióticos disponibles </th>
                                                </tr>
                                                <tr>
                                                    <th scope="col"> </th>
                                                    <th scope="col">
                                                        <input id="txtSearchAvailableABDetails" type="text" placeholder=""
                                                            class="column_search" spellcheck="false" data-ms-editor="true">
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody id="tableDataAvailableAddABModal">
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-2">
                                        <div class="row mt-25">
                                            <a id="btnAddStep8ABDetails"
                                                class="btn btn-light-primary font-weight-bold mr-2 ml-4 pointer mt-5">
                                                <i class="far"></i>Agregar &gt;&gt;</a>
                                        </div>
                                        <div class="row">
                                            <a id="btnRemoveStep8ABDetails"
                                                class="btn btn-light-primary font-weight-bold mr-2 ml-4 pointer mt-5">
                                                <i class="far"></i>
                                                &lt;&lt; Quitar </a>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <h3>Antibióticos Seleccionados</h3>
                                        <table id="tableSelectedAddABModal" class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">#</th>
                                                    <th scope="col">Antibiótico</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tableDataSelectedeAddABModal">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>