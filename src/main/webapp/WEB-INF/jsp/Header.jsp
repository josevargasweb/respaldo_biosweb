<!-- navbar header-->
<header class="header">
    <nav class="navbar-nav navbar-expand-lg degrade-menu ml-auto">
        <ul class="navbar-nav navbar-left d-flex align-items-center list-unstyled">
            <li class="navbar-nav dropdown">
                <form class="mr-auto me-4 d-none d-lg-block" id="searchForm">
                    <div class="input-group input-group-sm input-group-navbar">
                        <input class="form-control p-3" id="searchInput" type="Buscar" placeholder="Buscar">
                        <button class="btn" type="button"><i class="fas fa-search"></i></button>
                    </div>
                </form>
            </li>
            <span>Bienvenido ${usuario.duNombres} ${usuario.duPrimerapellido}</span>
            <li class="navbar-nav dropdown">
                <a class="nav-link pe-0" id="userInfo" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img class="avatar p-1" src="https://d19m59y37dris4.cloudfront.net/bubbly/1-2/img/avatar-6.jpg"
                        alt="Jason Doe" style="max-width: 60px; width: 100%;">
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userInfo">
                  <a class="dropdown-item" href="#">Ver perfil</a>
                  <a class="dropdown-item" href="#">Configuración</a>
                  <a class="dropdown-item" href="#">Cambiar contraseña</a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="#">Cerrar Sesión</a>
                </div>
            </li>
        </ul>
    </nav>
</header>
    

    

