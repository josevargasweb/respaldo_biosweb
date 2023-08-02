<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step8 modal fade" id="step8OptAB" tabindex="-1" role="dialog"
                    aria-labelledby="datosPacienteModal" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title" id="exampleModalLabel">Antibióticos opcionales</h1>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                                </button>
                            </div>
                            <div class="modal-body mb-10">
                                <table id="tableStep8OptABModalDetails" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Tests</th>
                                            <th scope="col">Incluir?</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableDataStep8OptABModalDetails">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>