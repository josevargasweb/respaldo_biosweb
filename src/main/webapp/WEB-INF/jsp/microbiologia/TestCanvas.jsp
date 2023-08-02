<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="TestCanvas" class="card card-custom gutter-b mt-5">
    <div class="card-header pl-2"> 
        <div class="card-title row col-12 d-flex justify-content-between">
            <h3 class="card-label col-3 pl-0">
                Test
            </h3>
            <div class="col-4 justify-content-right ml-4 pl-4 pr-0">
                <div class="ml-1 float-right" >
                    <a class="navi-link" href="#" >
                        <span class='symbol symbol-50 symbol-circle mr-3'>
                            <span class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Imprimir"><i class="flaticon2-paper icon-xl text-primary"></i></span>
                        </span>
                    </a>
                </div>
                <div class="ml-1 float-right" >
                    <a class="navi-link" href="#" >
                        <span class='symbol symbol-50 symbol-circle mr-3'>
                            <span class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Bloquear test"><i class="flaticon2-copy icon-xl text-primary"></i></span>
                        </span>
                    </a>
                </div>
                <div class="ml-1 float-right" >
                    <a id="btnTasksOpenTest" class="navi-link" href="#" >
                        <span class='symbol symbol-50 symbol-circle mr-3'>
                            <span class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Tareas manuales"><i class="flaticon2-copy icon-xl text-primary"></i></span>
                        </span>
                    </a>
                </div>
                <div class="ml-1 float-right" >
                    <a class="navi-link pointer">
                        <span class='symbol symbol-50 symbol-circle mr-3' >
                            <span class='symbol-label circuloIcon' data-toggle="tooltip" title="Autorizar resultados"><i class="flaticon2-paper icon-xl text-primary"></i></span>
                        </span>
                    </a>
                </div>
                <div class="ml-1 float-right">
                    <a class="navi-link pointer" >
                        <span class="symbol symbol-50 symbol-circle mr-3 ">
                            <span class="symbol-label bg-hover-primary hoverIcon" data-toggle="tooltip" title="Firmar resultados"><i class="far fa-edit icon-xl text-primary"></i></span>
                        </span>
                        <span class="navi-text"></span>
                    </a>
                </div>   

            </div>

        </div>
    </div>
    <div class="card-body">
        <form id="formRegistroPaciente" method="post">
            <div id="divForm" class="row">
                <div class="col-10">
                    <div class="tab-content mt-5" id="myTabContent">
                        <jsp:include page="TestCanvasData.jsp"/>
                        <jsp:include page="TestCanvasActions.jsp"/>
                        <jsp:include page="TestCanvasNotifications.jsp"/>
                        <jsp:include page="TestCanvasEvents.jsp"/>
                        <jsp:include page="TestCanvasAntibiograms.jsp"/>

                        <jsp:include page="TestCanvasLabels.jsp"/>
                        <jsp:include page="TestCanvasAntibiogramsResistanceMethod.jsp"/>
                        <jsp:include page="TestCanvasAntibiogramsAddAntibiotic.jsp"/>
                    </div>
                </div>
                <div class="col-2">
                    <ul class="nav  flex-column nav-pills">
                        <li class="nav-item" style="max-height: 38.5px;">
                            <a class="nav-link active" id="TestCanvasDataToggle" data-toggle="tab" href="#TestCanvasData">
                                <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                <span class="nav-text">Datos</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="TestCanvasActionsToggle" data-toggle="tab" href="#TestCanvasActions">
                                <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                <span class="nav-text">Acciones</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="TestCanvasNotificationsToggle" data-toggle="tab" href="#TestCanvasNotifications">
                                <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                <span class="nav-text">Notificación</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="TestCanvasEventsToggle" data-toggle="tab" href="#TestCanvasEvents">
                                <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                <span class="nav-text">Eventos</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="TestCanvasAntibiogramsToggle" data-toggle="tab" href="#TestCanvasAntibiograms">
                                <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                <span class="nav-text">Antibiogramas</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
</div>