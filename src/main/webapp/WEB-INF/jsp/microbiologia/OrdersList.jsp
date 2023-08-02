<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane fade show active" id="OrdersList" role="tabpanel" aria-labelledby="OrdersList">

    <div class="form-group row align-items-center mb-5">
        <div class="col">
            <label><b>Id</b></label>
            <input id="txtEmail" name="dpEmail" type="email" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Fecha</b></label>
            <input  id="txtCodigoPostal" name="dpDireccodpostal" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col-2">
            <label><b>Paciente</b></label>
            <input id="txtTelefono" name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Sexo</b></label>
            <input id="txtEmail" name="dpEmail" type="email" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Edad</b></label>
            <input  id="txtCodigoPostal" name="dpDireccodpostal" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Tipo</b></label>
            <input  id="txtCodigoPostal" name="dpDireccodpostal" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Institución</b></label>
            <input id="txtTelefono" name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Servicio</b></label>
            <input id="txtEmail" name="dpEmail" type="email" class="form-control disabledForm mt-5" placeholder=""/>
            <div class="d-md-none mb-5"></div>
        </div>
        <div class="col">
            <label><b>Acciones</b></label>
            <a id="buscarPaciente" class="navi-link" href="#" >
                <span class='symbol symbol-10 symbol-circle mr-3'>
                    <span id="circuloBuscarPaciente" class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Buscar paciente"><i id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
                </span>
            </a>
            <div class="d-md-none mb-5"></div>
        </div>
    </div>

    <div id="ordersListData"></div>

</div>
