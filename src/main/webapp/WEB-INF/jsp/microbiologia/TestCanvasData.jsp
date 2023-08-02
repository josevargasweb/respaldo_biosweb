<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade show active solo-texto" id="TestCanvasData" role="tabpanel" aria-labelledby="TestCanvasData">
    <div class="row">
        <div class="col">
            <div class="row col-12 form-group">
                <label class="col-form-label text-left col-lg-4 pl-0"><h4 id="idTestCanvasDataName"></h4></label>
            </div>
            <div class="row col-12">
                 <div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>
                    <div class="col-xxl-8 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Examen</label>
                            <span id="idTestCanvasDataExam"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Código</label>
                            <span id="idTestCanvasDataCode"></span>
                    </div>
                    <div class="col-8 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Muestra</label>
                            <span id="idTestCanvasDataSample"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Prefijo</label>
                            <span id="idTestCanvasDataPrefix"></span>
                    </div>
                    <div class="col-12 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Tipo resultado</label>
                            <span id="idTestCanvasDataResultType"></span>
                    </div>
                    <div class="col-8 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Resultado</label>
                            <span id="idTestCanvasDataPrefix"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Sitio anat&oacute;mico</label>
                            <span id="idTestCanvasDataBodyPart"></span>
                    </div>
                    <div class="col-8 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Valor crítico</label>
                            <span id="idTestCanvasDataCriticalValue"></span>
                    </div>
                    <div class="col-4 form-group mb-0">
                        <label
                            class="col-form-label pb-0 col-12 pl-0">Estado</label>
                            <span id="idTestCanvasDataStatus"></span>
                    </div>
                 </div>
           
            </div>
        </div>
    </div>
</div>