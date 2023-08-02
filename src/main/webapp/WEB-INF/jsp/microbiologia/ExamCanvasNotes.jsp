<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade" id="ExamCanvasNotes" role="tabpanel" aria-labelledby="OrderCanvasData">
    <div class="row">
        <div class="col">
            <div class="row col-12 form-group">
                <label class="col-form-label text-left col-lg-4 "><b>Notas del Examen</b></label>
            </div>
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
            <a id="examNotesButton" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="flaticon2-paper"></i>Guardar</a>
        </div>
    </div>
</div>