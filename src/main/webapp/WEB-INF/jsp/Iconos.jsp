<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BiosLIS | Indicaciones toma de muestra</title>
        <jsp:include page="common/Styles_1.jsp"/>
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
        <div class="container container-main-content container-configuracion mt-5">
            <div class="row d-flex justify-content-center mt-4">
                <div class="accordion col-12 pl-0 pr-0" id="examen-main">
                    <!-- iconos -->
                    <div class="card h-100">
                        <div class="card-header border-15">
                            <div class="card-title d-flex justify-content-between">
                                <h3 class="mb-0 btn-block text-left pl-0 col-10" data-toggle="collapse" data-target="#collapseHeader" aria-expanded="false" aria-controls="collapseHeader">
                                    Iconos
                                </h3>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="d-flex">
                                <div class="row">
                                    <div class="col-12">
                                        <h3 class="col-12">Iconos</h3>
                                        <div class="iconos-puestos"></div>
                                    </div>
                                    <div class="col-12 mt-3">
                                        <h3 class="col-12">Iconos con detalles</h3>
                                        <div class="iconos-detalles"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- iconos -->
                </div>
            </div>
        </div>
        <!-- FIN FORMULARIO PACIENTE --> 
        <jsp:include page="common/Scripts.jsp"/>
        <script src="<%=request.getContextPath()%>/resources/js/Indicaciones.js"></script>
    </body>
</html>

