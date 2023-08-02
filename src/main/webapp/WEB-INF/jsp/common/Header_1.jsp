<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- navbar header-->
<nav id="navegacion-superior" class="navbar-nav navbar-expand-lg degrade-menu d-flex justify-content-end pr-4 pb-4">
    <ul class="navbar-nav navbar-right align-items-center list-unstyled user-menu">
        <div class="collapse navbar-collapse" id="navbar-list-4">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link  position-relative d-flex pb-0" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" tabindex="-1">
                        <span class="nombre-user">${usuario.duNombres} ${usuario.duPrimerapellido}</span> 
                        <span class="icono-user">
                            <c:choose>
                                <c:when test="${base64Foto==null || base64Foto==''}">
                                    <img class=" icon-profile rounded-circle " src="${pageContext.request.contextPath}/resources/img/user-default.jpg">
                                </c:when>
                                <c:otherwise>
                                    <img class="icon-profile rounded-circle" src="data:image/jpeg;base64, ${base64Foto}" >
                                </c:otherwise>
                            </c:choose>
                        </span>
                      
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <div class="info-container line-height-1">
                            <label  class="dropdown-info title" >Centro </label> 
                            <span   class="dropdown-info">${centro} </span> 
                            <label  class="dropdown-info title" >Laboratorio </label> 
                            <span   class="dropdown-info" >${laboratorio} </span> 
                            <label  class="dropdown-info title" >Procedencia </label> 
                            <span  class="dropdown-info" >${procedencia} </span> 
                            <label  class="dropdown-info title" >Seccion </label> 
                            <span  class="dropdown-info" >${seccion} </span> 
                        </div>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/CambioPassword">Cambiar contrase&ntilde;a</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/Logout">Cerrar Sesi&oacute;n</a>
                    </div>
                </li>   
            </ul>
        </div>
    </ul>
</nav>