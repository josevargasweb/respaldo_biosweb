<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step5 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep5TestName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>
                    
                        <div class="row">
                            <div class="col-1 ml-5" style="text-align: left; float: left;">
                                <a id="btnStep5ScanTest" class="navi-link">
                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                        <span id="spnStep5ScanTest" class="symbol-label bg-hover-primary  hoverIcon" href="javascript:void(0)" data-toggle="tooltip" title="" data-original-title="Lectura de código de barras">
                                            <i id="iClearAGFilteddddd" class="la la-barcode icon-xl text-primary">
                                            </i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div> 
                            <div class="col-4 mt-2 ml-3" style="text-align: left; float: left;">
                                <label>Lectura o búsqueda de <br> código de barras:</label>
                            </div>
                            <div class="col-4 mt-2" style="text-align: left; float: left;">    
                                <input id="txtTaskStep5ScanTest" class="form-control">
                            </div>
                        </div>


                        <div class="col-9">

                            <table id="tablePlateReviewDetails" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Medio de cultivo</th>
                                        <th scope="col">Tiempo de Revisión</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Nota</th>
                                        <th scope="col">Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataPlateReviewDetails">
                                    
                                </tbody>
                            </table>

                        </div>
                        <div class="col-3">
                            <div class="col-12">
                                <div class="form-group" style="text-align: center;">
                                    <label class="col-form-label">Tipo de Muestra:</label>
                                    <br>
                                        <input disabled="true" id="txtTaskStep5SampleType" class="form-control">
                                        <input disabled="true" id="txtTaskStep5SampleTypeId" class="form-control" hidden>
                                </div>
                            </div>    

                            <div class="form-group row">
                            <div class="col-1"> </div>

                            <div class="col-3">
                            <div class="float" style="text-align: center; float: none;">
                                <a id="btnStep5ScanTest" class="navi-link">
                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                        <span id="spnStep5ScanTest" class="symbol-label bg-hover-primary  hoverIcon"
                                            href="javascript:void(0)" data-toggle="tooltip" title="Imprimir">
                                            <i id="iClearAGFilteddddd" class="la la-print icon-xl text-primary">
                                            </i>
                                        </span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>
                            </div>

                            <div class="col-7">
                                <div class="form-group row">
                                    <div class="mt-1 form-check justify-content-end">
                                        <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep5Preview"
                                        style="margin-left: 10px;">
                                        <label class="form-check-label pull-right" for="chkStep5Preview" style="float:left;">
                                            Vista previa
                                        </label>
                                        <br>
                                        <input class="form-check-input-reverse" disabled="true" checked="true" type="checkbox" value="" id="chkStep5PreReport"
                                            style="margin-left: 15px;">
                                        <label class="form-check-label pull-right" for="chkStep5PreReport" style="float:left;">
                                            Preinforme
                                        </label>
                                    </div>
                                </div>
                            </div>   
                            </div> 
                        </div>
                        
                        <div class="col-12 row d-flex justify-content-end pb-3" style="margin-bottom: 20px">
                            <div class="form-check">
                                <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep5IsReady"
                                    style="margin-left: 10px;">
                                <label class="form-check-label pull-right" for="chkStep5IsReady" style="float:left;">
                                    Etapa Completada:
                                </label>
                            </div>
                        </div>
                    </div>
                </div>