<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step6 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep6TestName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>
                        <div class="col-12">
                            <table id="tableStep6TestsDetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Prueba</th>
                                        <th scope="col">Resultado</th>
                                        <th scope="col">Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody id="tableStep6DataTestsDetails">
                                </tbody>
                            </table>

                        </div>

                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep6IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep6IsReady" style="float:left;">
                                    Etapa Completada:
                                </label>
                            </div>
                        </div>
                    </div>
                </div>