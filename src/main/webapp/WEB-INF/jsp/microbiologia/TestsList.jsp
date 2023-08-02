<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade" id="TestsList" role="tabpanel" aria-labelledby="TestsList">

    <div class="form-group row align-items-center mb-5">
        <div class="col-3">
            <label><b>Test</b></label>
            <input name="" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col-3">
            <label><b>Resultado</b></label>
            <input name="" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col-2">
            <label><b>Estado</b></label>
            <input name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col-2">
            <label><b>Microbiología</b></label>
            <input name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col-1">
            <label><b>Acciones</b></label>
            <a id="" class="navi-link" href="#" >
                <span class='symbol symbol-10 symbol-circle mr-3'>
                    <span id="circuloBuscarPaciente" class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Buscar paciente"><i id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
                </span>
            </a>
            <div class="d-md-none mb-5"></div>
        </div>
    </div>

    <div id="testsListData"></div>

</div>
