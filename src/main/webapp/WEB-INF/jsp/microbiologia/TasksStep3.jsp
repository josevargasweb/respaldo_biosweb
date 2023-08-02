<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step3 pb-5 row col-11 mx-auto" data-wizard-type="step-content" data-wizard-state="current">
                    <h2 id="txtTaskStep3TestName" style="text-align: center;width: 100%" class="col-12 mt-10 mb-20">
                        Cargando...</h2>
                    <div class="row">
                        <div class="col-4">
                            <h3>Medios de Cultivo Disponibles</h3>
                            <table id="tableAvailableCMDetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col"> </th>
                                        <th scope="col"> </th>
                                        <th scope="col"> </th>
                                    </tr>
                                    <tr>
                                        <th scope="col"> </th>
                                        <th scope="col">
                                            <input id="txtSearchAvailableCMDetails" type="text" placeholder=""
                                                class="column_search" spellcheck="false" data-ms-editor="true">
                                        </th>
                                        <th scope="col"> </th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataAvailableCMDetails">
                                </tbody>
                            </table>
                        </div>
                        <div class="col-1" style="width: 100%;
                        flex: 0 0 10%;max-width: 100%;">
                            <div class="row mt-25">
                                <a id="btnAddCMDetails"
                                    class="btn btn-light-primary btn-block font-weight-bold mr-2 ml-4 pointer mt-5">
                                    <i class="far"></i>Agregar <br> &gt;&gt;</a>
                            </div>
                            <div class="row">
                                <a id="btnRemoveCMDetails"
                                    class="btn btn-light-primary btn-block font-weight-bold mr-2 ml-4 pointer mt-5">
                                    <i class="far"></i>
                                    &lt;&lt; <br> Quitar </a>
                            </div>
                        </div>
                        <div class="col-4">
                            <h3>Medios de Cultivo Seleccionados</h3>
                            <table id="tableSelectedCMDetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Medio de Cultivo</th>
                                        <th scope="col">Imprimir Etiqueta</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataSelectedCMDetails">
                                </tbody>
                            </table>
                        </div>
                        <div class="col">
                            <label class="col col-form-label">Tipo de Muestra:</label>
                            <input disabled="true" id="txtTaskStep3SampleType" class="form-control">
                            <input disabled="true" id="txtTaskStep3SampleTypeId" class="form-control" hidden>
                            <div class="ml-1 mt-10 float" style="text-align: center; float: none;">
                                <a id="btnStep3PrintExam" class="navi-link">
                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                        <span id="spnStep3PrintExam" class="symbol-label bg-hover-primary  hoverIcon"
                                            href="javascript:void(0)" data-toggle="tooltip" title="Imprimir Etiquetas">
                                            <i id="iClearAGFilter" class="la la-print icon-xl text-primary">
                                            </i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                                <label>Impresión de Etiquetas</label>
                            </div>
                            <div class="mt-10 ml-10 form-check justify-content-end">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep3Preview"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep3Preview" style="float:left;">
                                    ¿Vista previa?
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-10">
                        <div class="form-group row">
                            <label class="checkbox checkbox-primary ml-1 ocultar col-lg-3">
                                <input id="" type="checkbox" class="">Fecha de Siembra
                                <span></span>
                            </label>
                            <div class="col-lg-5 pl-0">
                                <input class="form-control" placeholder="" id="idStep3SeedingDate" type="date">
                            </div>
                            <div class="col-lg-3 pl-0">
                                <input class="form-control" placeholder="" id="idStep3SeedingTime" type="time">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="checkbox checkbox-primary ml-2 ocultar col-lg-3">
                                <input id="" type="checkbox" class="">Fecha de Resiembra
                                <span></span>
                            </label>
                            <div class="col-lg-5 pl-0">
                                <input class="form-control" placeholder="" id="idStep3ReseedingDate" type="date">
                            </div>
                            <div class="col-lg-3 pl-0">
                                <input class="form-control" placeholder="" id="idStep3ReseedingTime" type="time">
                            </div>
                        </div>

                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep3IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep3IsReady" style="float:left;">
                                    Etapa Completada:
                                </label>
                            </div>
                        </div>
                    </div>
                </div>