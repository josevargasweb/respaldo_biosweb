<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BIOS-LIS | Toma de Muestras</title>
        <jsp:include page="common/Styles_1.jsp"/>
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/pages/wizard/wizard-3.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/css/estilo.css" rel="stylesheet" type="text/css" />
        <!--end::Layout Themes-->
        <link rel="icon" type="image/jpg" href="<%=request.getContextPath()%>/resources/img/gbios.png">
    </head>
    <body>
        <div class="row menu-container">
            <div class="col-md-6">
                    <jsp:include page="common/Menu_dinamico.jsp"/>
            </div>
            <div class="col-md-6">
                    <jsp:include page="common/Header_1.jsp" />
            </div>
	</div>
        <jsp:include page="common/AlertaSession.jsp"/>
        <div class="container mt-5">
            <c:if test="${mensaje!=null}"><div class="alert alert-success" role="alert">${mensaje}</div></c:if>
            <div class="accordion accordion-solid accordion-panel accordion-svg-toggle" id="accordionExample8">
                <div class="card card-custom gutter-b">
                    <div class="card-header" id="headingOne8">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 class="card-label col-3 pl-0 pb-3"  data-toggle="collapse" data-target="#collapseOne8">
                                Cambiar Contraseña
                            </h3>
                        </div>
                    </div>
                    <form id="formCambioPassword" method="post">
                        <input id="txtIdUsuario" name="idUsuario" value="${usuario.duIdusuario}" type="hidden" class="form-control mt-5" />
                        <div class="col-8">
                            <div class="form-group row">
                                <label class="col-3 col-form-label mt-5">Ingrese Contraseña Actual: </label>
                                <div class="col-7 mt-5 input-group">
                                    <input id="txtPasswordActual" class="form-control" name="passwordActual" type="password" autocomplete="off" />
                                    <div class="input-group-append">
                                        <button id="showPassword1" class="form-control btn btn-primary" type="button"> <span class="fa fa-eye-slash icon1"></span> </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-8">
                            <div class="form-group row mt-5">
                                <label id="" class="col-3 col-form-label mt-5">Ingrese Nueva Contraseña:</label>
                                <div class="col-7 mt-5 input-group">
                                    <input id="txtPasswordNueva" class="form-control" name="passwordNueva" type="password" autocomplete="off" />
                                    <div class="input-group-append">
                                        <button id="showPassword2" class="form-control btn btn-primary" type="button"> <span class="fa fa-eye-slash icon2"></span> </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-8">
                            <div class="form-group row mt-5">
                                <label id="" class="col-3 col-form-label mt-5">Confirme Nueva Contraseña:</label>
                                <div class="col-7 mt-5 input-group">
                                    <input id="txtPasswordConfirm" class="form-control" name="passwordConfirm" type="password" autocomplete="off" />
                                    <div class="input-group-append">
                                        <button id="showPassword3" class="form-control btn btn-primary" type="button"> <span class="fa fa-eye-slash icon3"></span> </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6 form-group mt-6 row">
                            <button id="btnConfirmar" type="submit" name="update" class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5"><i class="far fa-save"></i>Confirmar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/CambioPassword.js"></script>
    </body>
</html>