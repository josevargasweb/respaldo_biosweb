<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="card card-custom gutter-b  mt-5">
                    <div class="col-xxl-12 pt-5">
                        <div class="row">
                            <div class="form-group col-6 row pl-5 ml-5">
                                <!--align-items-center-->
                                <div id="tableBottomTestsContainer"
                                    class="row scroll scroll-pull ps mt-5 ml-5 mr-5 mb-10" data-scroll="true"
                                    data-suppress-scroll-x="false" data-swipe-easing="false"
                                    style="height: 270px; overflow: hidden; width: 100%;text-align: center;display:flex;justify-content: center;">

                                    <table id="tableBottomTests" class="table table-hover" width="100%">
                                        <thead>
                                            <tr id="trHeader">
                                                <th scope="col">#</th>
                                                <th id="thIdTableTests" scope="col">Tests</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tableBottomTestsData">

                                        </tbody>
                                    </table>
                                </div>
                                <!--FIN TABLA-->
                                <!--INICIO DETALLES-->
                                <div class="form-group col-12 row">
                                    <!--align-items-center-->
                                    <div class="col-12">
                                        <div class="form-group row">
                                            <label class="col-3 col-form-label">Paciente:</label>
                                            <div class="col-9">
                                                <input disabled="true" id="txtPatientName" class="form-control">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-3 col-form-label">N° Documento:</label>
                                            <div class="col-9">
                                                <input disabled="true" id="txtPatientDocumentN" class="form-control">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-3 col-form-label">Edad:</label>
                                            <div class="col-9">
                                                <input disabled="true" id="txtPatientAge" class="form-control">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-3 col-form-label">N° Orden:</label>
                                            <div class="col-9">
                                                <input disabled="true" id="txtPatientOrderN" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group col-6 row">
                                <!--align-items-center-->
                                <h2 id="txtBottomExamName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                    NOMBRE DE EXAMEN</h2>
                                <div class="col-12">

                                    <div class="form-group row mt-15">
                                        <label class="col-3 col-form-label">Tipo de muestra:</label>
                                        <div class="col-9">
                                            <select id="selPatientSampleType" class="form-control">
                                                <option>-</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-3 col-form-label">Sitio anat&oacute;mico:</label>
                                        <div class="col-9">
                                            <select id="selPatientBodyRegion" class="form-control">
                                                <option>-</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row mt-15">
                                        <div class="col">
                                            <div class="ml-1 float-left">
                                                <a id="btnBottomPatientData" class="navi-link" data-toggle="modal"
                                                    data-target="#modOrderDataModal">
                                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                                        <span id="circleClearAGFilter"
                                                            class="symbol-label bg-hover-primary  hoverIcon" href="#"
                                                            data-toggle="tooltip" title="Datos del paciente"><i id="iClearAGFilter"
                                                                class="la la-user icon-xl text-primary"></i></span>
                                                    </span>
                                                    <span class="navi-text"></span>
                                                </a>
                                                <label>Datos de Paciente</label>
                                            </div>
                                            <div class="ml-1 float-left">
                                                <a id="btnClearAGFilter" class="navi-link" data-toggle="modal"
                                                    data-target="#modExamNotesModal">
                                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                                        <span id="circleClearAGFilter"
                                                            class="symbol-label bg-hover-primary  hoverIcon" href="#"
                                                            data-toggle="tooltip" title="Registrar Notas">
                                                            <i id="iClearAGFilter"
                                                                class="la la-file-alt icon-xl text-primary">
                                                            </i>
                                                        </span>
                                                    </span>
                                                    <span class="navi-text"></span>
                                                </a>
                                                <label>Registrar Notas</label>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="ml-1 float-left">
                                                <a id="btnBottomExamEvents" class="navi-link" data-toggle="modal"
                                                    data-target="#modExamEventsModal">
                                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                                        <span id="circleClearAGFilter"
                                                            class="symbol-label bg-hover-primary  hoverIcon" href="#"
                                                            data-toggle="tooltip" title="Eventos del examen"><i id="iClearAGFilter"
                                                                class="la la-clock icon-xl text-primary"></i></span>
                                                    </span>
                                                    <span class="navi-text"></span>
                                                </a>
                                                <label>Eventos del Examen</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mt-15 float-right">
                                        <button id="sgtButton"
                                            class="btn btn-primary font-weight-bold px-9 py-4 mr-5 float-right"
                                            type="button" onclick="location.href='../Microbiologia'">Salir</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>