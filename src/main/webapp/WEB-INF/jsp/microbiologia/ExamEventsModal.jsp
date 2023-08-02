<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>

                <div class="modal" id="modExamEventsModal" tabindex="-1" role="dialog"
                    aria-labelledby="notasExamenModal" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title" id="txtExamEventsModalLabel">Eventos del Examen</h1>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group row align-items-center col-12">
                                    <div class="col scroll scroll-pull" data-scroll="true"
                                        data-suppress-scroll-x="false" data-swipe-easing="false"
                                        style="height: 270px;width:100%">
                                        <table id="tableExamEventsModal" class="table table-hover" width="100%">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Fecha</th>
                                                    <th scope="col">Usuario</th>
                                                    <th scope="col">Campo</th>
                                                    <th scope="col">Dato anterior</th>
                                                    <th scope="col">Dato nuevo</th>
                                                </tr>
                                            </thead>
                                            <tbody id="dataTableExamEventsModal">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>