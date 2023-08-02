<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="card card-custom gutter-b mt-5" id="ListsCanvas">
        <div class="card-header pl-2"> 
            <div class="card-title row col-12 d-flex justify-content-between">

                <h3 class="card-label col-3 pl-0">
                    Selección Orden
                </h3>

                <div class="col-4 justify-content-right ml-4 pl-4 pr-0">
                    <div class="ml-1 float-right" >
                        <a id="filterButton" class="navi-link" href="#">
                            <span class='symbol symbol-50 symbol-circle mr-3'>
                                <span class='symbol-label bg-hover-primary hoverIcon' data-toggle="tooltip" title="Buscar paciente"><i id="iBuscarPaciente" class="fas fa-search text-primary"></i></span>
                            </span>
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
                            <jsp:include page="OrdersList.jsp"/>
                            <jsp:include page="ExamsList.jsp"/>
                            <jsp:include page="TestsList.jsp"/>
                        </div>
                    </div>

                    <div class="col-2">
                        <ul class="nav  flex-column nav-pills">
                            <li class="nav-item" style="max-height: 38.5px;">
                                <a class="nav-link active" id="OrdersListToggle" data-toggle="tab" href="#OrdersList">
                                    <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                    <span class="nav-text">&Oacute;rdenes</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="ExamsListToggle" data-toggle="tab" href="#ExamsList">
                                    <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                    <span class="nav-text">Ex&aacute;menes</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="TestsListToggle" data-toggle="tab" href="#TestsList">
                                    <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                    <span class="nav-text">Tests</span>
                                </a>
                            </li>
                        </ul>
                    </div>

                </div>
            </form>
        </div>
</div>