<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step7 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep7TestName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>
                        <div class="col-12">
                            <table id="tableStep7MODetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">N°</th>
                                        <th scope="col">Microorganismo</th>
                                        <th scope="col">Recuento de colonias</th>
                                        <th scope="col">Tipo de infección</th>
                                        <th scope="col">Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody id="tableStep7DataMODetails">
                                </tbody>
                            </table>

                        </div>

                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep7IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep7IsReady" style="float:left;">
                                    Etapa Completada:
                                </label>
                            </div>
                        </div>
                    </div>
                </div>