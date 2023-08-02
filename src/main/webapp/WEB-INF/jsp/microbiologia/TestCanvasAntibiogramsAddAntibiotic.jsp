<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="TestCanvasAntibiogramsAddAntibiotic" tabindex="-1" role="dialog" aria-labelledby="TestCanvasAntibiogramsAddAntibiotic" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Añadir Antibióticos</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i aria-hidden="true" class="ki ki-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <div id="TestCanvasAddAntibioticListBoxDiv" class="form-group row mt-5 mx-auto col-8">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light-primary font-weight-bold" data-dismiss="modal" onclick="saveAddAntibiotics()">Guardar</button>
            </div>
        </div>
    </div>
</div>