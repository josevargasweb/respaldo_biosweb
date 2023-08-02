<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="tab-pane fade" id="TestCanvasAntibiograms" role="tabpanel"
                    aria-labelledby="TestCanvasAntibiograms">

                    <div class="form-group row align-items-center mb-5">
                        <div class="col-3">
                            <div class="form-group row">
                                <h4>Origen: Nombre Test - Muestra</h4>
                            </div>
                            <div class="form-group row">
                                <button id="idTestCanvasAntibiogramsAddResistanceMethod" type="button"
                                    class="btn btn-light-primary font-weight-bold disabledForm" data-toggle="modal"
                                    data-target="#TestCanvasAntibiogramsResistanceMethod">Anñadir método
                                    resistencia</button>
                            </div>
                            <div class="form-group row">
                                <button id="idTestCanvasAntibiogramsAddAntibiotic" type="button"
                                    class="btn btn-light-primary font-weight-bold disabledForm" data-toggle="modal"
                                    data-target="#TestCanvasAntibiogramsAddAntibiotic">Añadir antibiotico</button>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group row">
                                <label class="col-form-label text-left col-lg-3">Numero Aislado</label>
                                <div class="col-lg-2 pl-0">
                                    <select id="idTestCanvasAntibiogramIsolatedNumber" class="form-control">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </div>
                                <button type="button" id="btnTestCanvasAntibiogramClearIsolatedNumber"
                                    class="btn btn-light-primary font-weight-bold">Borrar</button>
                            </div>

                            <div class="form-group row">
                                <div class="col">
                                    <div class="row mb-3">
                                        <label class="col-form-label text-left col-lg-5">
                                            Microorganismo
                                        </label>
                                        <div class="col-lg pl-0">
                                            <select id="idTestCanvasAntibiogramMicroorganism" class="form-control">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <label class="col-form-label text-left col-lg-5">
                                            Antibiograma
                                        </label>
                                        <div class="col-lg pl-0">
                                            <select id="idTestCanvasAntibiogramType" class="form-control">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="row mb-3">
                                        <label class="col-form-label text-left col-lg-5">
                                            Recuento de colonias
                                        </label>
                                        <div class="col-lg pl-0">
                                            <select id="idTestCanvasAntibiogramColoniesCount" class="form-control">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <label class="col-form-label text-left col-lg-5">
                                            Tipo de infección
                                        </label>
                                        <div class="col-lg pl-0">
                                            <select id="idTestCanvasAntibiogramInfectionType" class="form-control">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>


                    <h4>Antibióticos</h4>

                    <div class="form-group row align-items-center mb-5">
                        <div class="col-2">
                            <label><b>Antibiotico</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-1">
                            <label><b>CIM</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-1">
                            <label><b>Diámetro</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-2">
                            <label><b>Interpretación</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-1">
                            <label><b>Informe</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-1">
                            <label><b>Acciones</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                    </div>

                    <div id="antibiogramsAntibioticList"></div>

                    <h4>Marcadores de Resistencia</h4>

                    <div class="form-group row align-items-center mb-5">
                        <div class="col-4">
                            <label><b>Marcador de resistencia</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-4">
                            <label><b>Resultado</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                        <div class="col-1">
                            <label><b>Acciones</b></label>
                            <div class="d-md-none mb-5"></div>
                        </div>
                    </div>

                    <div id="antibiogramsResistanceMethodList"></div>

                    <button type="button" class="btn btn-light-primary font-weight-bold"
                        onclick="saveAddAntibiogram()">Guardar</button>

                </div>