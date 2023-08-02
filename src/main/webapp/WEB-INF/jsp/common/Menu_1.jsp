<nav id="menu-lateral" class="navbar navbar-dark bg-dark menu-lateral menu-lateral-hide" tabindex="-1">
    <button class="navbar-toggler menu-lateral-logo" type="button" data-toggle="collapse" data-target="#menu-lateral-contenido" aria-controls="menu-lateral-contenido" aria-expanded="false" aria-label="Toggle navigation" tabindex="-1">
        <img id="logoEmpresa" alt="Logo" src="<%=request.getContextPath()%>/resources/img/logo-bioslis3.png" />
    </button>

    <div class="collapse navbar-collapse" id="menu-lateral-contenido">
        <div class="accordion" id="accordion-items">
            <div class="card">
                <div class="card-header" id="atencion-pacientes">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main arrow-menu collapsed" type="button" data-toggle="collapse" data-target="#atencion-pacientes-contenido" aria-expanded="false" aria-controls="atencion-pacientes-contenido">
                            <div>
                                <i class="fas fa-users"></i>                                
                                <span class="ml-4 ">Atenci&oacute;n de pacientes</span>
                            </div>
                        </button>
                    </h2>

                </div>

                <div id="atencion-pacientes-contenido" class="collapse" aria-labelledby="atencion-pacientes" data-parent="#accordion-items">
                    <div class="card-body">
                        <ul class="menu-subnav">
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/RegistroPaciente?origen=nuevo" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Nuevo paciente</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/RegistroPaciente?origen=antiguo" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Modificar paciente</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Orden" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Nueva Orden</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/EdicionOrdenes" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Modificar Orden</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/ReasignacionOrdenes" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Reasignar &oacute;rdenes</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Registro de documentos</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/ImpresionEtiquetas" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Impresi&oacute;n de etiquetas</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="manejo-muestras">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block d-flex text-left btn-main arrow-menu justify-content-between collapsed" type="button" data-toggle="collapse" data-target="#manejo-muestras-contenido" aria-expanded="false" aria-controls="manejo-muestras-contenido">
                            <div>                                
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M5,5 L5,15 C5,15.5948613 5.25970314,16.1290656 5.6719139,16.4954176 C5.71978107,16.5379595 5.76682388,16.5788906 5.81365532,16.6178662 C5.82524933,16.6294602 15,7.45470952 15,7.45470952 C15,6.9962515 15,6.17801499 15,5 L5,5 Z M5,3 L15,3 C16.1045695,3 17,3.8954305 17,5 L17,15 C17,17.209139 15.209139,19 13,19 L7,19 C4.790861,19 3,17.209139 3,15 L3,5 C3,3.8954305 3.8954305,3 5,3 Z" fill="#000000" fill-rule="nonzero" transform="translate(10.000000, 11.000000) rotate(-315.000000) translate(-10.000000, -11.000000)" />
                                    <path d="M20,22 C21.6568542,22 23,20.6568542 23,19 C23,17.8954305 22,16.2287638 20,14 C18,16.2287638 17,17.8954305 17,19 C17,20.6568542 18.3431458,22 20,22 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="ml-4 ">Manejo de muestras</span>
                            </div>
                        </button>
                    </h2>
                </div>
                <div id="manejo-muestras-contenido" class="collapse" aria-labelledby="manejo-muestras" data-parent="#accordion-items">
                    <div class="card-body">
                        <ul class="menu-subnav">

                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Transporte de muestras</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/TomaMuestras" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text  menu-bullet-dot">Toma de muestras</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/RecepcionMuestras" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text  menu-bullet-dot    ">Recepci&oacute;n de muestras</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Recepci&oacute;n de muestras por lote</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Puntos de control</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Indicaciones" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Indicaciones de toma de muestra</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="resultados">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main  arrow-menu collapsed" type="button" data-toggle="collapse" data-target="#resultados-contenido" aria-expanded="false" aria-controls="atencion-pacientes-contenido">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <path d="M5,5 L5,15 C5,15.5948613 5.25970314,16.1290656 5.6719139,16.4954176 C5.71978107,16.5379595 5.76682388,16.5788906 5.81365532,16.6178662 C5.82524933,16.6294602 15,7.45470952 15,7.45470952 C15,6.9962515 15,6.17801499 15,5 L5,5 Z M5,3 L15,3 C16.1045695,3 17,3.8954305 17,5 L17,15 C17,17.209139 15.209139,19 13,19 L7,19 C4.790861,19 3,17.209139 3,15 L3,5 C3,3.8954305 3.8954305,3 5,3 Z" fill="#000000" fill-rule="nonzero" transform="translate(10.000000, 11.000000) rotate(-315.000000) translate(-10.000000, -11.000000)" />
                                <path d="M20,22 C21.6568542,22 23,20.6568542 23,19 C23,17.8954305 22,16.2287638 20,14 C18,16.2287638 17,17.8954305 17,19 C17,20.6568542 18.3431458,22 20,22 Z" fill="#000000" opacity="0.3" />
                                </g>
                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Resultados</span>
                        </button>
                    </h2>

                </div>

                <div id="resultados-contenido" class="collapse" aria-labelledby="resultados" data-parent="#accordion-items">
                    <div class="card-body">
                        <ul class="menu-subnav">
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/CapturaResultados" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Captura de resultados</span>
                                    <span class="menu-label">
                                        <span class="label label-danger label-inline">new</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/RechazoMuestras" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Rechazo de muestras</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Hojas de trabajo</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Ex&aacute;menes pendientes</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="informes">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main arrow-menu collapsed" type="button" data-toggle="collapse" data-target="#informes-contenido" aria-expanded="false" aria-controls="atencion-pacientes-contenido">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>

                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Informes</span>
                        </button>
                    </h2>

                </div>

                <div id="informes-contenido" class="collapse" aria-labelledby="informes" data-parent="#accordion-items">
                    <div class="card-body">
                        <ul class="menu-subnav">
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/InformeResultados" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Impresi&oacute;n de informes</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Impresi&oacute;n de informes por lote</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Resultados fuera de V. de referencia</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="microbiologia">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main arrow-menu collapsed" type="button" id="microbiologia-header" data-toggle="collapse" data-target="#microbiologia-contenido" aria-expanded="false" aria-controls="atencion-pacientes-contenido">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <path d="M5,5 L5,15 C5,15.5948613 5.25970314,16.1290656 5.6719139,16.4954176 C5.71978107,16.5379595 5.76682388,16.5788906 5.81365532,16.6178662 C5.82524933,16.6294602 15,7.45470952 15,7.45470952 C15,6.9962515 15,6.17801499 15,5 L5,5 Z M5,3 L15,3 C16.1045695,3 17,3.8954305 17,5 L17,15 C17,17.209139 15.209139,19 13,19 L7,19 C4.790861,19 3,17.209139 3,15 L3,5 C3,3.8954305 3.8954305,3 5,3 Z" fill="#000000" fill-rule="nonzero" transform="translate(10.000000, 11.000000) rotate(-315.000000) translate(-10.000000, -11.000000)" />
                                <path d="M20,22 C21.6568542,22 23,20.6568542 23,19 C23,17.8954305 22,16.2287638 20,14 C18,16.2287638 17,17.8954305 17,19 C17,20.6568542 18.3431458,22 20,22 Z" fill="#000000" opacity="0.3" />
                                </g>
                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Microbiolog&iacute;a</span>
                        </button>
                    </h2>

                </div>

                <div id="microbiologia-contenido" class="collapse" aria-labelledby="microbiologia" data-parent="#accordion-items">
                    <div class="card-body">
                        <div class="menu-subnav">
                            <div class="accordion" id="microbiologia-items">
                                <div class="card">
                                    <div class="card-header ml-0 mr-0" id="microbiologia-resultados">
                                        <h2 class="mb-0">
                                            <button class="btn btn-link btn-block text-left d-flex arrow-menu justify-content-between collapsed" type="button" data-toggle="collapse" data-target="#microbiologia-resultados-sub" aria-expanded="false" aria-controls="collapseOne">
                                                Resultados
                                            </button>
                                        </h2>
                                    </div>

                                    <div id="microbiologia-resultados-sub" class="collapse" aria-labelledby="microbiologia-resultados" data-parent="#microbiologia-items">
                                        <div class="card-body">
                                            <ul class="menu-subnav pl-0 pr-0">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Captura de resultados</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Tareas detalladas</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/TareasPendientes" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Tareas pendientes</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Reglas de alertas</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="card">
                                    <div class="card-header ml-0 mr-0" id="microbiologia-configuracion">
                                        <h2 class="mb-0">
                                            <button class="btn btn-link btn-block  text-left d-flex arrow-menu justify-content-between  collapsed" type="button" data-toggle="collapse" data-target="#microbiologia-configuracion-sub" aria-expanded="false" aria-controls="collapseTwo">
                                                Configuraci&oacute;n
                                            </button>
                                        </h2>
                                    </div>
                                    <div id="microbiologia-configuracion-sub" class="collapse" aria-labelledby="microbiologia-configuracion" data-parent="#microbiologia-items">
                                        <div class="card-body">
                                            <ul class="menu-subnav pl-0 pr-0">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/Microorganismos" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Microorganismos</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/Antibioticos" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Antibi&oacute;ticos</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/Antibiogramas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Antibiogramas</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/RecuentoColonias" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Recuentos</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/ZonasCuerpo" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Zonas del cuerpo</span>
                                                    </a>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/PruebasManuales" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Pruebas manuales</span>
                                                    </a>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/MediosCultivo" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Medios de cultivo</span>
                                                    </a>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Microbiologia/Mantenedores/MetodosResistencia" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">M&eacute;todos de resistencia</span>
                                                    </a>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                            </div>


                        </div>
                    </div>
                </div>


            </div>

            <div class="card">
                <div class="card-header" id="configuracion">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main arrow-menu collapsed" type="button" id="configuracion-header" data-toggle="collapse" data-target="#configuracion-contenido" aria-expanded="false" aria-controls="atencion-pacientes-contenido">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <path d="M5,5 L5,15 C5,15.5948613 5.25970314,16.1290656 5.6719139,16.4954176 C5.71978107,16.5379595 5.76682388,16.5788906 5.81365532,16.6178662 C5.82524933,16.6294602 15,7.45470952 15,7.45470952 C15,6.9962515 15,6.17801499 15,5 L5,5 Z M5,3 L15,3 C16.1045695,3 17,3.8954305 17,5 L17,15 C17,17.209139 15.209139,19 13,19 L7,19 C4.790861,19 3,17.209139 3,15 L3,5 C3,3.8954305 3.8954305,3 5,3 Z" fill="#000000" fill-rule="nonzero" transform="translate(10.000000, 11.000000) rotate(-315.000000) translate(-10.000000, -11.000000)" />
                                <path d="M20,22 C21.6568542,22 23,20.6568542 23,19 C23,17.8954305 22,16.2287638 20,14 C18,16.2287638 17,17.8954305 17,19 C17,20.6568542 18.3431458,22 20,22 Z" fill="#000000" opacity="0.3" />
                                </g>
                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Configuraci&oacute;n</span>
                        </button>
                    </h2>

                </div>

                <div id="configuracion-contenido" class="collapse" aria-labelledby="configuracion" data-parent="#accordion-items">
                    <div class="card-body">
                        <div class="menu-subnav">
                            <div class="accordion" id="configuracion-items">
                                <div class="card">
                                    <div class="card-header ml-0 mr-0" id="configuracion-examenes">
                                        <h2 class="mb-0">
                                            <button class="btn btn-link btn-block text-left d-flex arrow-menu justify-content-between collapsed" type="button" data-toggle="collapse" data-target="#configuracion-examenes-sub" aria-expanded="false" aria-controls="collapseOne">
                                                Ex&aacute;menes
                                            </button>
                                        </h2>
                                    </div>

                                    <div id="configuracion-examenes-sub" class="collapse" aria-labelledby="configuracion-examenes" data-parent="#configuracion-items">
                                        <div class="card-body">
                                            <ul class="menu-subnav pl-0 pr-0">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionExamen" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Examen</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionTest" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Test</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionMuestras" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Muestras</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionEnvases" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Contenedor</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionUnidadesMedidas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Unidades de medida</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionMetodos" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">M&eacute;todos</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionFormulas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">F&eacute;rmulas</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionGlosas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Glosas</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionValoresReferencias" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Valores de referencia</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Pesquisas</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionPanelesExamenes" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Paneles Ex&aacute;menes</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionPanelesExamenes" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">INDICACIONES</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="card">
                                    <div class="card-header ml-0 mr-0" id="configuracion-configuracion">
                                        <h2 class="mb-0">
                                            <button class="btn btn-link btn-block  text-left d-flex arrow-menu justify-content-between  collapsed" type="button" data-toggle="collapse" data-target="#configuracion-configuracion-sub" aria-expanded="false" aria-controls="collapseTwo">
                                                Base
                                            </button>
                                        </h2>
                                    </div>
                                    <div id="configuracion-configuracion-sub" class="collapse" aria-labelledby="configuracion-configuracion" data-parent="#configuracion-items">
                                        <div class="card-body">
                                            <ul class="menu-subnav pl-0 pr-0">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/RegistroMedico" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">M&eacute;dicos</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Diagn&oacute;sticos</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionPrioridadAtencion" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Prioridad de atenci&oacute;n</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionNotas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Notas</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/CausasRechazoMuestras" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Causas de rechazo</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Feriados</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Puntos de control</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="card">
                                    <div class="card-header ml-0 mr-0" id="configuracion-intuicion">
                                        <h2 class="mb-0">
                                            <button class="btn btn-link btn-block  text-left d-flex arrow-menu justify-content-between  collapsed" type="button" data-toggle="collapse" data-target="#configuracion-intuicion-sub" aria-expanded="false" aria-controls="collapseTwo">
                                                Instituci&oacute;n
                                            </button>
                                        </h2>
                                    </div>
                                    <div id="configuracion-intuicion-sub" class="collapse" aria-labelledby="configuracion-intuicion" data-parent="#configuracion-items">
                                        <div class="card-body">
                                            <ul class="menu-subnav pl-0 pr-0">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionCentrosDeSalud" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Centro de salud</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Centro de an&aacute;lisis</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionProcedencias" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Procedencias (UTM)</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionServicios" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Servicios cl&iacute;nicos</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/LocalizacionSalasCamas" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Localizaciones (sala / cama)</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionLaboratorios" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Laboratorios</span>
                                                        <span class="menu-label">
                                                            <span class="label label-danger label-inline">new</span>
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionSecciones" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Secciones</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="${pageContext.request.contextPath}/ConfiguracionDerivadores" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text    ">Derivadores</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="card">
                                    <div class="card-header ml-0 mr-0">
                                        <h2 class="mb-0">
                                            <a href="${pageContext.request.contextPath}/RegistroUsuarios" class="btn btn-link button-white btn-block  text-left d-flex justify-content-between collapsed" type="button">
                                                Usuarios
                                            </a>
                                        </h2>
                                    </div>
                                </div>
                                <div class="card">
                                    <div class="card-header ml-0 mr-0">
                                        <h2 class="mb-0">
                                            <a href="${pageContext.request.contextPath}/Home" class="btn btn-link button-white btn-block  text-left d-flex justify-content-between collapsed" type="button">
                                                Procesos
                                            </a>
                                        </h2>
                                    </div>
                                </div>
                                <div class="card">
                                    <div class="card-header ml-0 mr-0">
                                        <h2 class="mb-0">
                                            <a href="${pageContext.request.contextPath}/Home" class="btn btn-link button-white btn-block  text-left d-flex justify-content-between collapsed" type="button">
                                                Seroteca
                                            </a>
                                        </h2>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>


            </div>
            <div class="card">
                <div class="card-header" id="entorno">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main collapsed" type="button">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>

                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Entorno</span>
                        </button>
                    </h2>
                </div>
            </div> 
            <div class="card">
                <div class="card-header" id="utiles">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main collapsed" type="button">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>

                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">&Uacute;tiles</span>
                        </button>
                    </h2>
                </div>
            </div> 
            <div class="card">
                <div class="card-header" id="ayuda">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left btn-main collapsed" type="button">
                            <span class="svg-icon menu-icon">
                                <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Bucket.svg-->
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <rect x="0" y="0" width="24" height="24" />
                                <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>

                                </svg>
                                <!--end::Svg Icon-->
                            </span>
                            <span class="ml-4 ">Ayuda</span>
                        </button>
                    </h2>
                </div>
            </div> 

        </div>  
    </div>

</nav>



