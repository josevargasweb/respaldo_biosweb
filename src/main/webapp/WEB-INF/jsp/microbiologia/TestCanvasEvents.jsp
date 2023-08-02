<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="tab-pane" id="TestCanvasEvents" role="tabpanel" aria-labelledby="TestCanvasEvents">
    <div class="form-group row align-items-center col-12">
        <div class="col scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 270px;overflow-y: auto;margin-right: 0px;padding-right: 0px;">
            <table id="tableFiltro" class="table table-hover table-striped nowrap compact-xs">
                <thead>
                    <tr>
                        <th scope="col">Fecha</th>
                        <th scope="col">Usuario</th>
                        <th scope="col">Campo</th>
                        <th scope="col">Dato anterior</th>
                        <th scope="col">Dato nuevo</th>
                    </tr>
                </thead>
                <tbody id="idTestCanvasEventsTable">
                </tbody>
            </table>
        </div>
    </div>
</div>