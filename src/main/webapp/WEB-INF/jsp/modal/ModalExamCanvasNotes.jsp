<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade" id="ExamCanvasNotes" role="tabpanel" aria-labelledby="OrderCanvasData">
    <div class="row">
        <div class="col">
            <div class="row col-12">
                <div class="col-12">
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-2 ">Nota Interna</label>
                        <div class="col-lg pl-0">
                            <textarea class="form-control" id="idExamCanvasNotesInternalNote" type="text"></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-2 ">Nota de Examen</label>
                        <div class="col-lg pl-0">
                            <textarea class="form-control" id="idExamCanvasNotesExamNote" type="text"></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-form-label text-left col-lg-2 ">Nota Pie de Informe</label>
                        <div class="col-lg pl-0">
                            <textarea class="form-control" id="idExamCanvasNotesFooterNote" type="text"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-6">
                    <button id="examModalNotesButton" class="btn btn-blue-primary btn-lg font-weight-bold mr-2"><i class="flaticon2-paper"></i>Guardar</button>
                </div>
                <div class="col-6 d-flex justify-content-end">
                    <button id="btnSalirContador" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
</div>