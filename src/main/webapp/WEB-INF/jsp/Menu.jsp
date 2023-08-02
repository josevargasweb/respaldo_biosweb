<%-- 
    Document   : menu
    Created on : 26-05-2021, 0:07:31
    Author     : marco.cortez

    Men� para ser incluido en todos los mantenedores

--%>
<div class="aside aside-left aside-fixed d-flex flex-column flex-row-auto bg-edwin" id="kt_aside">
    <!--begin::Brand-->
    <div class="brand flex-column-auto bg-edwin" id="kt_brand">
        <!--begin::Logo-->
        <a class="brand-logo bg-edwin mt-5 pointer">
            <img id="logoGB" alt="Logo" src="<%=request.getContextPath()%>/resources/img/logo-bioslis3.png" />
        </a>
        <!--end::Logo-->
        <!--begin::Toggle-->
        <button class="brand-toggle btn btn-sm px-0 bg-edwin" id="kt_aside_toggle">
            <span class="svg-icon svg-icon svg-icon-x bg-edwin">
                <!--begin::Svg Icon | path:assets/media/svg/icons/Navigation/Angle-double-left.svg-->
                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <polygon points="0 0 24 0 24 24 0 24" />
                        <path d="M5.29288961,6.70710318 C4.90236532,6.31657888 4.90236532,5.68341391 5.29288961,5.29288961 C5.68341391,4.90236532 6.31657888,4.90236532 6.70710318,5.29288961 L12.7071032,11.2928896 C13.0856821,11.6714686 13.0989277,12.281055 12.7371505,12.675721 L7.23715054,18.675721 C6.86395813,19.08284 6.23139076,19.1103429 5.82427177,18.7371505 C5.41715278,18.3639581 5.38964985,17.7313908 5.76284226,17.3242718 L10.6158586,12.0300721 L5.29288961,6.70710318 Z" fill="#000000" fill-rule="nonzero" transform="translate(8.999997, 11.999999) scale(-1, 1) translate(-8.999997, -11.999999)" />
                        <path d="M10.7071009,15.7071068 C10.3165766,16.0976311 9.68341162,16.0976311 9.29288733,15.7071068 C8.90236304,15.3165825 8.90236304,14.6834175 9.29288733,14.2928932 L15.2928873,8.29289322 C15.6714663,7.91431428 16.2810527,7.90106866 16.6757187,8.26284586 L22.6757187,13.7628459 C23.0828377,14.1360383 23.1103407,14.7686056 22.7371482,15.1757246 C22.3639558,15.5828436 21.7313885,15.6103465 21.3242695,15.2371541 L16.0300699,10.3841378 L10.7071009,15.7071068 Z" fill="#000000" fill-rule="nonzero" opacity="0.3" transform="translate(15.999997, 11.999999) scale(-1, 1) rotate(-270.000000) translate(-15.999997, -11.999999)" />
                    </g>
                </svg>
                <!--end::Svg Icon-->
            </span>
        </button>
        <!--end::Toolbar-->
    </div>
    <!--end::Brand-->
    <!--begin::Aside Menu-->
    <div class="aside-menu-wrapper flex-column-fluid bg-edwin" id="kt_aside_menu_wrapper">
        <!--begin::Menu Container-->
        <div id="kt_aside_menu" class="aside-menu my-4 bg-edwin" data-menu-vertical="1" data-menu-scroll="1" data-menu-dropdown-timeout="500">
            <!--begin::Menu Nav-->
            <ul class="menu-nav">

                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="javascript:;" class="menu-link menu-toggle">
                        <i class="fas fa-users"></i>
                        <span class="ml-5 menu-text">Atenci&oacute;n de pacientes</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                               <span class="menu-link">
                                    <span class="menu-text">Atenci&oacute;n de pacientes</span>
                                </span>
                            </li>
                               
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
                        </ul>
                    </div>
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="javascript:;" class="menu-link menu-toggle">
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
                        <span class="menu-text    ">Manejo de muestras</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                               <span class="menu-link">
                                    <span class="menu-text">Manejo de muestras</span>
                                </span>
                            </li>
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
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="javascript:;" class="menu-link menu-toggle">
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
                        <span class="menu-text    ">Resultados</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                               <span class="menu-link">
                                    <span class="menu-text">Resultados</span>
                                </span>
                            </li>
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
                        
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="javascript:;" class="menu-link menu-toggle">
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
                        <span class="menu-text">Informes</span>
                        <i class="menu-arrow"></i>
                     </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                               <span class="menu-link">
                                    <span class="menu-text">Informes</span>
                                </span>
                            </li>
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
               </li>

                                    
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="javascript:;" class="menu-link menu-toggle">
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
                        <span class="menu-text    ">Microbiolog&iacute;a</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/ConfiguracionExamen" class="menu-link">
                                    <span class="menu-text    ">Microbiolog&iacute;a</span>
                                    </span>
                                </a>
                            </li>
                            <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                <a href="${pageContext.request.contextPath}/Microbiologia" class="menu-link menu-toggle">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Resultados</span>
                                    <i class="menu-arrow"></i>
                                </a>
                                <div class="menu-submenu">
                                    <i class="menu-arrow"></i>
                                    <ul class="menu-subnav">
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
                            </li>
                           <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text">Configuraci&oacute;n</span>
                                    <i class="menu-arrow"></i>
                                </a>
                                <div class="menu-submenu">
                                    <i class="menu-arrow"></i>
                                    <ul class="menu-subnav">
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
                            </li>
                         </ul>
                    </div>
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
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
                        <span class="menu-text    ">Configuraci&oacute;n</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="menu-submenu">
                        <i class="menu-arrow"></i>
                        <ul class="menu-subnav">
                            <li class="menu-item menu-item-parent" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/ConfiguracionExamen" class="menu-link">
                                    <span class="menu-text    ">Ex&aacute;menes</span>
                                </a>
                            </li>
                            <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Ex&aacute;menes</span>
                                    <i class="menu-arrow"></i>
                                </a>
                                <div class="menu-submenu">
                                    <i class="menu-arrow"></i>
                                    <ul class="menu-subnav">
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
                                                <span class="menu-text    ">Contenedores</span>
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
                                                <span class="menu-text    ">Paneles Ex�menes</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Base</span>
                                    <i class="menu-arrow"></i>
                                </a>
                                <div class="menu-submenu">
                                    <i class="menu-arrow"></i>
                                    <ul class="menu-subnav">
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
                            </li>
                            <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Instituci&oacute;n</span>
                                    <i class="menu-arrow"></i>
                                </a>
                                <div class="menu-submenu">
                                    <i class="menu-arrow"></i>
                                    <ul class="menu-subnav">
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
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/RegistroUsuarios" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Usuarios</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Procesos</span>
                                </a>
                            </li>
                            <li class="menu-item" aria-haspopup="true">
                                <a href="${pageContext.request.contextPath}/Home" class="menu-link">
                                    <i class="menu-bullet menu-bullet-dot">
                                        <span></span>
                                    </i>
                                    <span class="menu-text    ">Seroteca</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                        <span class="svg-icon menu-icon">
                            <!--begin::Svg Icon | path:assets/media/svg/icons/Shopping/Barcode-read.svg-->
                            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                    <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>
                            </svg>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="ml-2 menu-text">Entorno</span>
                    </a>
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                        <span class="svg-icon menu-icon">
                            <!--begin::Svg Icon | path:assets/media/svg/icons/Shopping/Barcode-read.svg-->
                            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                    <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>
                            </svg>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="ml-2 menu-text">&Uacute;tiles</span>
                    </a>
                </li>
                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                    <a href="${pageContext.request.contextPath}/Home" class="menu-link menu-toggle">
                        <span class="svg-icon menu-icon">
                            <!--begin::Svg Icon | path:assets/media/svg/icons/Shopping/Barcode-read.svg-->
                            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="4" y="4" width="8" height="16" />
                                    <path d="M6,18 L9,18 C9.66666667,18.1143819 10,18.4477153 10,19 C10,19.5522847 9.66666667,19.8856181 9,20 L4,20 L4,15 C4,14.3333333 4.33333333,14 5,14 C5.66666667,14 6,14.3333333 6,15 L6,18 Z M18,18 L18,15 C18.1143819,14.3333333 18.4477153,14 19,14 C19.5522847,14 19.8856181,14.3333333 20,15 L20,20 L15,20 C14.3333333,20 14,19.6666667 14,19 C14,18.3333333 14.3333333,18 15,18 L18,18 Z M18,6 L15,6 C14.3333333,5.88561808 14,5.55228475 14,5 C14,4.44771525 14.3333333,4.11438192 15,4 L20,4 L20,9 C20,9.66666667 19.6666667,10 19,10 C18.3333333,10 18,9.66666667 18,9 L18,6 Z M6,6 L6,9 C5.88561808,9.66666667 5.55228475,10 5,10 C4.44771525,10 4.11438192,9.66666667 4,9 L4,4 L9,4 C9.66666667,4 10,4.33333333 10,5 C10,5.66666667 9.66666667,6 9,6 L6,6 Z" fill="#000000" fill-rule="nonzero" />
                                </g>
                            </svg>
                            <!--end::Svg Icon-->
                        </span>
                        <span class="ml-2 menu-text">Ayuda  </span>
                    </a>
                </li>
            </ul>
            <!--end::Menu Nav-->
        </div>
        <!--end::Menu Container-->
    </div>
    <!--end::Aside Menu-->
</div>
