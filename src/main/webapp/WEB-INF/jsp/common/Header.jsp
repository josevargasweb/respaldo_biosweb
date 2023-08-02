<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- navbar header-->
<nav class="navbar-nav navbar-expand-lg degrade-menu navbar-light bg-light ml-auto">
    <ul class="navbar-nav navbar-right align-items-center list-unstyled">
            <span class="navbar-brand pt-0 pb-0">Bienvenido ${usuario.duNombres} ${usuario.duPrimerapellido}
                <br/><span style="color:red;font-size:12px;">Tiempo de inactividad: <span id="lblIdleTime"></span> minuto(s)</span>
            </span>
            <li class="nav-item dropdown">
                <a class="nav-link pe-0" id="userInfo" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:choose>
                        <c:when test="${base64Foto==null || base64Foto==''}">
                            <img class="user-default" src="${pageContext.request.contextPath}/resources/img/user-default.jpg" style="max-width: 50px; width: 100%;height:30px;">
                        </c:when>
                        <c:otherwise>
                            <img src="data:image/jpeg;base64, ${base64Foto}" style="max-width: 50px; width: 100%;height:30px;">
                        </c:otherwise>
                    </c:choose>
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" href="#">Ver perfil</a>
                    <a class="dropdown-item" href="#">Configuración</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/CambioPassword">Cambiar contraseña</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/Logout">Cerrar Sesión</a>
                </div>
            </li>
    </ul>
</nav>