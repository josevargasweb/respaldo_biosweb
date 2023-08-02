<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <jsp:include page="common/Styles_1.jsp" />
<link
  href="<%=request.getContextPath()%>/resources/template/assets/css/pages/wizard/wizard-3.css"
  rel="stylesheet" type="text/css" />
                    <title>BiosLIS | MB - Tareas de microbiología</title>
                </head>

                <body id="kt_body"
                    class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
                    <div class="row menu-container">
                        <div class="col-md-6">
                            <jsp:include page="common/Menu_dinamico.jsp" />
                        </div>
                        <div class="col-md-6">
                            <jsp:include page="common/Header_1.jsp" />
                        </div>
                    </div>
                    <div class="container mt-5" id="TasksCanvas">
                        <jsp:include page="microbiologia/TasksHome.jsp" />
                        <jsp:include page="microbiologia/TasksBottom.jsp" />
                        <jsp:include page="microbiologia/ExamNotesModal.jsp" />
                        <jsp:include page="microbiologia/ExamEventsModal.jsp" />
                        <jsp:include page="microbiologia/OrderDataModal.jsp" />
                        <jsp:include page="microbiologia/OptionalABModal.jsp" />
                        <jsp:include page="microbiologia/AdditionalABModal.jsp" />
                    </div>
                    <jsp:include page="common/Scripts.jsp"/>

                    <script
                        src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/datatables/datatables.bundle.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.wizard.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step3.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step4.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step5.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step6.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step7.js"></script>
                    <script
                        src="<%=request.getContextPath()%>/resources/js/microbiologia/MicrobiologiaTareas.Step8.js"></script>
                    

                    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/ExamNotesModal.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/ExamEventsModal.js"></script>
                    <script src="<%=request.getContextPath()%>/resources/js/microbiologia/OrderDataModal.js"></script>

                </body>

                </html>