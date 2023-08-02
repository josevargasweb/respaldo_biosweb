<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step4 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep4TestName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>
                        <div class="col-9">

                            <table id="tableIncubationDetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Medio de cultivo</th>
                                        <th scope="col">Concentración de Oxígeno</th>
                                        <th scope="col">Temperatura</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataIncubationDetails">
                                                                        
                                </tbody>
                            </table>


                        </div>
                        <div class="col-3">
                            <div class="form-group row">
                            </div>
                            <div class="form-group row">
                                <div class="col-12">
                                    <div class="form-group" style="text-align: center;">
                                        <label class="col-form-label">Tipo de Muestra:</label>
                                        <br>
                                            <input disabled="true" id="txtTaskStep4SampleType" class="form-control">
                                            <input disabled="true" id="txtTaskStep4SampleTypeId" class="form-control" hidden>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group row">
                            </div>
                        </div>
                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep4IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep4IsReady" style="float:left;">
                                    Etapa Completada:
                                </label>
                            </div>
                        </div>
                    </div>
                </div>