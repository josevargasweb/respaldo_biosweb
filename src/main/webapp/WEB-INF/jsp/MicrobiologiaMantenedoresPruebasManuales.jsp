<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <jsp:include page="common/Styles_1.jsp"/>
                    <title>BiosLIS | MB - Pruebas Manuales</title>
                </head>

                <body>
                    <div class="row menu-container">
                        <div class="col-md-6">
                            <jsp:include page="common/Menu_dinamico.jsp" />
                        </div>
                        <div class="col-md-6">
                            <jsp:include page="common/Header_1.jsp" />
                        </div>
                    </div>
                    <div class="container container-main-content container-configuracion mt-5" >
	                    <div class="row d-flex justify-content-center mt-4">
                            <jsp:include page="microbiologia/PruebasManualesConfig.jsp" />
	                    </div>
                    </div>

                    <jsp:include page="common/Scripts.jsp"/>

                    <script
                        src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaMantenedoresPruebasManuales.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/templates.js"></script>

                </body>

                </html>