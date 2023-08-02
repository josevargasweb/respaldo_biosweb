<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>

                <div class="modal fade" id="modExamNotesModal" tabindex="-1" role="dialog"
                    aria-labelledby="notasExamenModal" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title" id="txtExamNotesModalLabel">Notas del Examen</h1>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col">
                                        <div class="row col-12 form-group">
                                            <label class="col-form-label text-left col-lg-4 "><b>Notas del
                                                    Examen</b></label>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col-12">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Nota
                                                        Interna</label>
                                                    <div class="col-lg pl-0">
                                                        <textarea class="form-control"
                                                            id="txtExamenNotesModalInternalNote" type="text"></textarea>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Nota de
                                                        Examen</label>
                                                    <div class="col-lg pl-0">
                                                        <textarea class="form-control" id="txtExamenNotesModalExamNote"
                                                            type="text"></textarea>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Nota Pie de
                                                        Informe</label>
                                                    <div class="col-lg pl-0">
                                                        <textarea class="form-control"
                                                            id="txtExamenNotesModalFooterNote" type="text"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>