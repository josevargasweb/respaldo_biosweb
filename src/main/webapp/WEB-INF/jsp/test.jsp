<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Paciente</title>
        <!--begin::Fonts-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
        <!--end::Fonts-->
        <!--begin::Page Vendors Styles(used by this page)-->
        <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Page Vendors Styles-->
        <!--begin::Global Theme Styles(used by all pages)-->
        <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/prismjs/prismjs.bundle.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
        <!--end::Global Theme Styles-->
        <!--begin::Layout Themes(used by all pages)-->
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/header/base/light.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/header/menu/light.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/brand/dark.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/template/assets/css/themes/layout/aside/dark.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/estilo.css"/>
        <!--end::Layout Themes-->
        <link rel="shortcut icon" href="assets/media/logos/favicon.ico" />
    </head>
    <body id="kt_body" class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable page-loading">
        <div class="aside aside-left aside-fixed d-flex flex-column flex-row-auto bg-edwin" id="kt_aside">
            <!--begin::Brand-->
            <div class="brand flex-column-auto bg-edwin" id="kt_brand">
                <!--begin::Logo-->
                <a href="#" class="brand-logo bg-edwin mt-5">
                    <img id="logoGB" alt="Logo" src="<%=request.getContextPath()%>/resources/img/logo-bioslis3.png" />
                </a>
                <!--end::Logo-->
                <!--begin::Toggle-->
                <button class="brand-toggle btn btn-sm px-0 bg-edwin  text-white" id="kt_aside_toggle">
                    <span class="svg-icon svg-icon svg-icon-x bg-edwinl">
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
                <div id="kt_aside_menu" class="aside-menu my-4  text-white bg-edwin" data-menu-vertical="1" data-menu-scroll="1" data-menu-dropdown-timeout="500">
                    <!--begin::Menu Nav-->
                    <ul class="menu-nav">
                        <!--
                        <li class="menu-item menu-item-active " aria-haspopup="true">
                            <a href="index.html" class="menu-link  text-white bg-edwin">
                                <span class="svg-icon menu-icon text-white ">
                        <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Layers.svg-->
                        <!--
                        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <polygon points="0 0 24 0 24 24 0 24" />
                        <path d="M12.9336061,16.072447 L19.36,10.9564761 L19.5181585,10.8312381 C20.1676248,10.3169571 20.2772143,9.3735535 19.7629333,8.72408713 C19.6917232,8.63415859 19.6104327,8.55269514 19.5206557,8.48129411 L12.9336854,3.24257445 C12.3871201,2.80788259 11.6128799,2.80788259 11.0663146,3.24257445 L4.47482784,8.48488609 C3.82645598,9.00054628 3.71887192,9.94418071 4.23453211,10.5925526 C4.30500305,10.6811601 4.38527899,10.7615046 4.47382636,10.8320511 L4.63,10.9564761 L11.0659024,16.0730648 C11.6126744,16.5077525 12.3871218,16.5074963 12.9336061,16.072447 Z" fill="#000000" fill-rule="nonzero" />
                        <path d="M11.0563554,18.6706981 L5.33593024,14.122919 C4.94553994,13.8125559 4.37746707,13.8774308 4.06710397,14.2678211 C4.06471678,14.2708238 4.06234874,14.2738418 4.06,14.2768747 L4.06,14.2768747 C3.75257288,14.6738539 3.82516916,15.244888 4.22214834,15.5523151 C4.22358765,15.5534297 4.2250303,15.55454 4.22647627,15.555646 L11.0872776,20.8031356 C11.6250734,21.2144692 12.371757,21.2145375 12.909628,20.8033023 L19.7677785,15.559828 C20.1693192,15.2528257 20.2459576,14.6784381 19.9389553,14.2768974 C19.9376429,14.2751809 19.9363245,14.2734691 19.935,14.2717619 L19.935,14.2717619 C19.6266937,13.8743807 19.0546209,13.8021712 18.6572397,14.1104775 C18.654352,14.112718 18.6514778,14.1149757 18.6486172,14.1172508 L12.9235044,18.6705218 C12.377022,19.1051477 11.6029199,19.1052208 11.0563554,18.6706981 Z" fill="#000000" opacity="0.3" />
                        </g>
                        </svg>
                        <!--end::Svg Icon-->
                        <!--
                    </span>
                    <span class="menu-text text-white bg-edwin">Dashboard</span>
                </a>
            </li> -->
                        <li class="menu-section">
                            <h4 class="menu-text text-white  text-white">Paciente</h4>
                            <i class="menu-icon ki ki-bold-more-hor icon-md"></i>
                        </li>
                        <li class="menu-item menu-item-submenu bg-hover-primary" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle text-white ">
                                <i class="fab la-accusoft  text-white"></i>
                                <span class="ml-5 menu-text text-white ">Atención de pacientes</span>
                                <i class="menu-arrow text-white"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow text-white"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="./test" class="menu-link">
                                            <i class="menu-bullet menu-bullet-line text-white">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white text-white ">Registrar una orden</span>
                                        </a>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-line text-white">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Editar una orden</span>
                                        </a>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-line text-white">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Reasignar &oacute;rdenes</span>
                                        </a>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-line text-white">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Registrar un paciente</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
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
                                <span class="menu-text text-white  text-white ">Pages</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Pages</span>
                                        </span>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Wizard</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/wizard/wizard-1.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Wizard 1</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/wizard/wizard-2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Wizard 2</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/wizard/wizard-3.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Wizard 3</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/wizard/wizard-4.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Wizard 4</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Pricing Tables</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/pricing/pricing-1.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Pricing Tables 1</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/pricing/pricing-2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Pricing Tables 2</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/pricing/pricing-3.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Pricing Tables 3</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/pricing/pricing-4.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Pricing Tables 4</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Invoices</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/invoices/invoice-1.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Invoice 1</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/invoices/invoice-2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Invoice 2</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">User Pages</span>
                                            <span class="menu-label">
                                                <span class="label label-rounded label-primary">2</span>
                                            </span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-1.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 1</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 2</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-3.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 3</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-4.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 4</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-5.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 5</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/users/login-6.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Login 6</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Error Pages</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-1.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 1</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 2</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-3.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 3</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-4.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 4</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-5.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 5</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="custom/pages/errors/error-6.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Error 6</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-section">
                            <h4 class="menu-text text-white  text-white ">Layout</h4>
                            <i class="menu-icon ki ki-bold-more-hor icon-md"></i>
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
                                <span class="menu-text text-white  text-white ">Themes</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Themes</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/themes/aside-light.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Light Aside</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/themes/header-dark.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Dark Header</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Code/Compiling.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M2.56066017,10.6819805 L4.68198052,8.56066017 C5.26776695,7.97487373 6.21751442,7.97487373 6.80330086,8.56066017 L8.9246212,10.6819805 C9.51040764,11.267767 9.51040764,12.2175144 8.9246212,12.8033009 L6.80330086,14.9246212 C6.21751442,15.5104076 5.26776695,15.5104076 4.68198052,14.9246212 L2.56066017,12.8033009 C1.97487373,12.2175144 1.97487373,11.267767 2.56066017,10.6819805 Z M14.5606602,10.6819805 L16.6819805,8.56066017 C17.267767,7.97487373 18.2175144,7.97487373 18.8033009,8.56066017 L20.9246212,10.6819805 C21.5104076,11.267767 21.5104076,12.2175144 20.9246212,12.8033009 L18.8033009,14.9246212 C18.2175144,15.5104076 17.267767,15.5104076 16.6819805,14.9246212 L14.5606602,12.8033009 C13.9748737,12.2175144 13.9748737,11.267767 14.5606602,10.6819805 Z" fill="#000000" opacity="0.3" />
                                    <path d="M8.56066017,16.6819805 L10.6819805,14.5606602 C11.267767,13.9748737 12.2175144,13.9748737 12.8033009,14.5606602 L14.9246212,16.6819805 C15.5104076,17.267767 15.5104076,18.2175144 14.9246212,18.8033009 L12.8033009,20.9246212 C12.2175144,21.5104076 11.267767,21.5104076 10.6819805,20.9246212 L8.56066017,18.8033009 C7.97487373,18.2175144 7.97487373,17.267767 8.56066017,16.6819805 Z M8.56066017,4.68198052 L10.6819805,2.56066017 C11.267767,1.97487373 12.2175144,1.97487373 12.8033009,2.56066017 L14.9246212,4.68198052 C15.5104076,5.26776695 15.5104076,6.21751442 14.9246212,6.80330086 L12.8033009,8.9246212 C12.2175144,9.51040764 11.267767,9.51040764 10.6819805,8.9246212 L8.56066017,6.80330086 C7.97487373,6.21751442 7.97487373,5.26776695 8.56066017,4.68198052 Z" fill="#000000" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Subheaders</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Subheaders</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/subheader/toolbar.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Toolbar Nav</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/subheader/actions.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Actions Buttons</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/subheader/tabbed.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Tabbed Nav</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/subheader/classic.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Classic</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/subheader/none.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">None</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/General/Settings-1.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M7,3 L17,3 C19.209139,3 21,4.790861 21,7 C21,9.209139 19.209139,11 17,11 L7,11 C4.790861,11 3,9.209139 3,7 C3,4.790861 4.790861,3 7,3 Z M7,9 C8.1045695,9 9,8.1045695 9,7 C9,5.8954305 8.1045695,5 7,5 C5.8954305,5 5,5.8954305 5,7 C5,8.1045695 5.8954305,9 7,9 Z" fill="#000000" />
                                    <path d="M7,13 L17,13 C19.209139,13 21,14.790861 21,17 C21,19.209139 19.209139,21 17,21 L7,21 C4.790861,21 3,19.209139 3,17 C3,14.790861 4.790861,13 7,13 Z M17,19 C18.1045695,19 19,18.1045695 19,17 C19,15.8954305 18.1045695,15 17,15 C15.8954305,15 15,15.8954305 15,17 C15,18.1045695 15.8954305,19 17,19 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">General</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">General</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/fluid-content.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Fluid Content</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/minimized-aside.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Minimized Aside</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/no-aside.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">No Aside</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/empty-page.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Empty Page</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/fixed-footer.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white " >Fixed Footer</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="layout/general/no-header-menu.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">No Header Menu</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item" aria-haspopup="true">
                            <a target="_blank" href="https://keenthemes.com/metronic/preview/demo1/builder.html" class="menu-link">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Home/Library.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M5,3 L6,3 C6.55228475,3 7,3.44771525 7,4 L7,20 C7,20.5522847 6.55228475,21 6,21 L5,21 C4.44771525,21 4,20.5522847 4,20 L4,4 C4,3.44771525 4.44771525,3 5,3 Z M10,3 L11,3 C11.5522847,3 12,3.44771525 12,4 L12,20 C12,20.5522847 11.5522847,21 11,21 L10,21 C9.44771525,21 9,20.5522847 9,20 L9,4 C9,3.44771525 9.44771525,3 10,3 Z" fill="#000000" />
                                    <rect fill="#000000" opacity="0.3" transform="translate(17.825568, 11.945519) rotate(-19.000000) translate(-17.825568, -11.945519)" x="16.3255682" y="2.94551858" width="3" height="18" rx="1" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Builder</span>
                            </a>
                        </li>
                        <li class="menu-section">
                            <h4 class="menu-text text-white  text-white ">CRUD</h4>
                            <i class="menu-icon ki ki-bold-more-hor icon-md"></i>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Design/PenAndRuller.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M3,16 L5,16 C5.55228475,16 6,15.5522847 6,15 C6,14.4477153 5.55228475,14 5,14 L3,14 L3,12 L5,12 C5.55228475,12 6,11.5522847 6,11 C6,10.4477153 5.55228475,10 5,10 L3,10 L3,8 L5,8 C5.55228475,8 6,7.55228475 6,7 C6,6.44771525 5.55228475,6 5,6 L3,6 L3,4 C3,3.44771525 3.44771525,3 4,3 L10,3 C10.5522847,3 11,3.44771525 11,4 L11,19 C11,19.5522847 10.5522847,20 10,20 L4,20 C3.44771525,20 3,19.5522847 3,19 L3,16 Z" fill="#000000" opacity="0.3" />
                                    <path d="M16,3 L19,3 C20.1045695,3 21,3.8954305 21,5 L21,15.2485298 C21,15.7329761 20.8241635,16.200956 20.5051534,16.565539 L17.8762883,19.5699562 C17.6944473,19.7777745 17.378566,19.7988332 17.1707477,19.6169922 C17.1540423,19.602375 17.1383289,19.5866616 17.1237117,19.5699562 L14.4948466,16.565539 C14.1758365,16.200956 14,15.7329761 14,15.2485298 L14,5 C14,3.8954305 14.8954305,3 16,3 Z" fill="#000000" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Forms</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Forms</span>
                                        </span>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Form Controls</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/base.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Base Inputs</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/input-group.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Input Groups</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/checkbox.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Checkbox</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/radio.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Radio</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/switch.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Switch</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/controls/option.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Mega Options</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Form Widgets</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-datepicker.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Datepicker</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-datetimepicker.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Datetimepicker</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-timepicker.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Timepicker</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-daterangepicker.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Daterangepicker</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/tagify.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Tagify</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-touchspin.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Touchspin</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-maxlength.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Maxlength</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-switch.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Switch</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-multipleselectsplitter.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Multiple Select Splitter</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/bootstrap-select.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Bootstrap Select</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/select2.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Select2</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/typeahead.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Typeahead</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/nouislider.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">noUiSlider</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/form-repeater.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Form Repeater</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/ion-range-slider.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Ion Range Slider</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/input-mask.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Input Masks</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/autosize.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Autosize</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/clipboard.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Clipboard</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/widgets/recaptcha.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Google reCaptcha</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Form Text Editors</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/editors/tinymce.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">TinyMCE</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                                    <a href="javascript:;" class="menu-link menu-toggle">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">CKEditor</span>
                                                        <i class="menu-arrow"></i>
                                                    </a>
                                                    <div class="menu-submenu">
                                                        <i class="menu-arrow"></i>
                                                        <ul class="menu-subnav">
                                                            <li class="menu-item" aria-haspopup="true">
                                                                <a href="crud/forms/editors/ckeditor-classic.html" class="menu-link">
                                                                    <i class="menu-bullet menu-bullet-line">
                                                                        <span></span>
                                                                    </i>
                                                                    <span class="menu-text text-white  text-white ">CKEditor Classic</span>
                                                                </a>
                                                            </li>
                                                            <li class="menu-item" aria-haspopup="true">
                                                                <a href="crud/forms/editors/ckeditor-inline.html" class="menu-link">
                                                                    <i class="menu-bullet menu-bullet-line">
                                                                        <span></span>
                                                                    </i>
                                                                    <span class="menu-text text-white  text-white ">CKEditor Inline</span>
                                                                </a>
                                                            </li>
                                                            <li class="menu-item" aria-haspopup="true">
                                                                <a href="crud/forms/editors/ckeditor-balloon.html" class="menu-link">
                                                                    <i class="menu-bullet menu-bullet-line">
                                                                        <span></span>
                                                                    </i>
                                                                    <span class="menu-text text-white  text-white ">CKEditor Balloon</span>
                                                                </a>
                                                            </li>
                                                            <li class="menu-item" aria-haspopup="true">
                                                                <a href="crud/forms/editors/ckeditor-balloon-block.html" class="menu-link">
                                                                    <i class="menu-bullet menu-bullet-line">
                                                                        <span></span>
                                                                    </i>
                                                                    <span class="menu-text text-white  text-white ">CKEditor Balloon Block</span>
                                                                </a>
                                                            </li>
                                                            <li class="menu-item" aria-haspopup="true">
                                                                <a href="crud/forms/editors/ckeditor-document.html" class="menu-link">
                                                                    <i class="menu-bullet menu-bullet-line">
                                                                        <span></span>
                                                                    </i>
                                                                    <span class="menu-text text-white  text-white ">CKEditor Document</span>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/editors/quill.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Quill Text Editor</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/editors/summernote.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Summernote WYSIWYG</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/editors/bootstrap-markdown.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Markdown Editor</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Form Layouts</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/layouts/default-forms.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Default Forms</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/layouts/multi-column-forms.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Multi Column Forms</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/layouts/action-bars.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Basic Action Bars</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/layouts/sticky-action-bar.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Sticky Action Bar</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Form Validation</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/validation/states.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Validation States</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/validation/form-controls.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Form Controls</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/forms/validation/form-widgets.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Form Widgets</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Layout/Layout-left-panel-2.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M10,4 L21,4 C21.5522847,4 22,4.44771525 22,5 L22,7 C22,7.55228475 21.5522847,8 21,8 L10,8 C9.44771525,8 9,7.55228475 9,7 L9,5 C9,4.44771525 9.44771525,4 10,4 Z M10,10 L21,10 C21.5522847,10 22,10.4477153 22,11 L22,13 C22,13.5522847 21.5522847,14 21,14 L10,14 C9.44771525,14 9,13.5522847 9,13 L9,11 C9,10.4477153 9.44771525,10 10,10 Z M10,16 L21,16 C21.5522847,16 22,16.4477153 22,17 L22,19 C22,19.5522847 21.5522847,20 21,20 L10,20 C9.44771525,20 9,19.5522847 9,19 L9,17 C9,16.4477153 9.44771525,16 10,16 Z" fill="#000000" />
                                    <rect fill="#000000" opacity="0.3" x="2" y="4" width="5" height="16" rx="1" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">KTDatatable</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">KTDatatable</span>
                                        </span>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Base</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/data-local.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Local Data</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/data-json.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">JSON Data</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/data-ajax.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Ajax Data</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/html-table.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">HTML Table</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/local-sort.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Local Sort</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/base/translation.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Translation</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Advanced</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/record-selection.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Record Selection</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/row-details.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Row Details</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/modal.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Modal Examples</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/column-rendering.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Column Rendering</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/column-width.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Column Width</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/advanced/vertical.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Vertical Scrolling</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Child Datatables</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/child/data-local.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Local Data</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/child/data-ajax.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i> 
                                                        <span class="menu-text text-white  text-white ">Remote Data</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">API</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/api/methods.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">API Methods</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/ktdatatable/api/events.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Events</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Layout/Layout-horizontal.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="4" y="5" width="16" height="6" rx="1.5" />
                                    <rect fill="#000000" x="4" y="13" width="16" height="6" rx="1.5" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Datatables.net</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Datatables.net</span>
                                        </span>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Basic</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/basic/basic.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Basic Tables</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/basic/scrollable.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Scrollable Tables</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/basic/headers.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Complex Headers</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/basic/paginations.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Pagination Options</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Advanced</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/column-rendering.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Column Rendering</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/multiple-controls.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Multiple Controls</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/column-visibility.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Column Visibility</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/row-callback.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Row Callback</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/row-grouping.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Row Grouping</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/advanced/footer-callback.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Footer Callback</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Data sources</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/data-sources/html.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">HTML</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/data-sources/javascript.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Javascript</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/data-sources/ajax-client-side.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Ajax Client-side</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/data-sources/ajax-server-side.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Ajax Server-side</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Search Options</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/search-options/column-search.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Column Search</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/search-options/advanced-search.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Advanced Search</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Extensions</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/buttons.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Buttons</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/colreorder.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">ColReorder</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/keytable.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">KeyTable</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/responsive.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Responsive</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/rowgroup.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">RowGroup</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/rowreorder.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">RowReorder</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/scroller.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Scroller</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="crud/datatables/extensions/select.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white  text-white ">Select</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Files/Upload.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M2,13 C2,12.5 2.5,12 3,12 C3.5,12 4,12.5 4,13 C4,13.3333333 4,15 4,18 C4,19.1045695 4.8954305,20 6,20 L18,20 C19.1045695,20 20,19.1045695 20,18 L20,13 C20,12.4477153 20.4477153,12 21,12 C21.5522847,12 22,12.4477153 22,13 L22,18 C22,20.209139 20.209139,22 18,22 L6,22 C3.790861,22 2,20.209139 2,18 C2,15 2,13.3333333 2,13 Z" fill="#000000" fill-rule="nonzero" opacity="0.3" />
                                    <rect fill="#000000" opacity="0.3" x="11" y="2" width="2" height="14" rx="1" />
                                    <path d="M12.0362375,3.37797611 L7.70710678,7.70710678 C7.31658249,8.09763107 6.68341751,8.09763107 6.29289322,7.70710678 C5.90236893,7.31658249 5.90236893,6.68341751 6.29289322,6.29289322 L11.2928932,1.29289322 C11.6689749,0.916811528 12.2736364,0.900910387 12.6689647,1.25670585 L17.6689647,5.75670585 C18.0794748,6.12616487 18.1127532,6.75845471 17.7432941,7.16896473 C17.3738351,7.57947475 16.7415453,7.61275317 16.3310353,7.24329415 L12.0362375,3.37797611 Z" fill="#000000" fill-rule="nonzero" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">File Upload</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="crud/file-upload/image-input.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Image Input</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="crud/file-upload/dropzonejs.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">DropzoneJS</span>
                                            <span class="menu-label">
                                                <span class="label label-danger label-inline">new</span>
                                            </span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="crud/file-upload/uppy.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu- text-white ">Uppy</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-section">
                            <h4 class="menu-text text-white  text-white ">Features</h4>
                            <i class="menu-icon ki ki-bold-more-hor icon-md"></i>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Shopping/Box2.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M4,9.67471899 L10.880262,13.6470401 C10.9543486,13.689814 11.0320333,13.7207107 11.1111111,13.740321 L11.1111111,21.4444444 L4.49070127,17.526473 C4.18655139,17.3464765 4,17.0193034 4,16.6658832 L4,9.67471899 Z M20,9.56911707 L20,16.6658832 C20,17.0193034 19.8134486,17.3464765 19.5092987,17.526473 L12.8888889,21.4444444 L12.8888889,13.6728275 C12.9050191,13.6647696 12.9210067,13.6561758 12.9368301,13.6470401 L20,9.56911707 Z" fill="#000000" />
                                    <path d="M4.21611835,7.74669402 C4.30015839,7.64056877 4.40623188,7.55087574 4.5299008,7.48500698 L11.5299008,3.75665466 C11.8237589,3.60013944 12.1762411,3.60013944 12.4700992,3.75665466 L19.4700992,7.48500698 C19.5654307,7.53578262 19.6503066,7.60071528 19.7226939,7.67641889 L12.0479413,12.1074394 C11.9974761,12.1365754 11.9509488,12.1699127 11.9085461,12.2067543 C11.8661433,12.1699127 11.819616,12.1365754 11.7691509,12.1074394 L4.21611835,7.74669402 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Bootstrap</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Bootstrap</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/typography.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Typography</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/buttons.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Buttons</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/button-group.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Button Group</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/dropdown.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Dropdown</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/navs.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Navs</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/tables.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Tables</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/progress.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Progress</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/modal.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Modal</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/alerts.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Alerts</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/popover.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Popover</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/bootstrap/tooltip.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Tooltip</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Files/Pictures1.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M3.5,21 L20.5,21 C21.3284271,21 22,20.3284271 22,19.5 L22,8.5 C22,7.67157288 21.3284271,7 20.5,7 L10,7 L7.43933983,4.43933983 C7.15803526,4.15803526 6.77650439,4 6.37867966,4 L3.5,4 C2.67157288,4 2,4.67157288 2,5.5 L2,19.5 C2,20.3284271 2.67157288,21 3.5,21 Z" fill="#000000" opacity="0.3" />
                                    <polygon fill="#000000" opacity="0.3" points="4 19 10 11 16 19" />
                                    <polygon fill="#000000" points="11 19 15 14 19 19" />
                                    <path d="M18,12 C18.8284271,12 19.5,11.3284271 19.5,10.5 C19.5,9.67157288 18.8284271,9 18,9 C17.1715729,9 16.5,9.67157288 16.5,10.5 C16.5,11.3284271 17.1715729,12 18,12 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Custom</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Custom</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/utilities.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Utilities</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/label.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Labels</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/line-tabs.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Line Tabs</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/advance-navs.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Advance Navs</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/timeline.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Timeline</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/pagination.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Pagination</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/symbol.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Symbol</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/overlay.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Overlay</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/spinners.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Spinners</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/iconbox.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Iconbox</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/callout.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Callout</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/ribbons.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Ribbons</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/custom/accordions.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Accordions</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Layout/Layout-arrange.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M5.5,4 L9.5,4 C10.3284271,4 11,4.67157288 11,5.5 L11,6.5 C11,7.32842712 10.3284271,8 9.5,8 L5.5,8 C4.67157288,8 4,7.32842712 4,6.5 L4,5.5 C4,4.67157288 4.67157288,4 5.5,4 Z M14.5,16 L18.5,16 C19.3284271,16 20,16.6715729 20,17.5 L20,18.5 C20,19.3284271 19.3284271,20 18.5,20 L14.5,20 C13.6715729,20 13,19.3284271 13,18.5 L13,17.5 C13,16.6715729 13.6715729,16 14.5,16 Z" fill="#000000" />
                                    <path d="M5.5,10 L9.5,10 C10.3284271,10 11,10.6715729 11,11.5 L11,18.5 C11,19.3284271 10.3284271,20 9.5,20 L5.5,20 C4.67157288,20 4,19.3284271 4,18.5 L4,11.5 C4,10.6715729 4.67157288,10 5.5,10 Z M14.5,4 L18.5,4 C19.3284271,4 20,4.67157288 20,5.5 L20,12.5 C20,13.3284271 19.3284271,14 18.5,14 L14.5,14 C13.6715729,14 13,13.3284271 13,12.5 L13,5.5 C13,4.67157288 13.6715729,4 14.5,4 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Cards</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Cards</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/general.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">General Cards</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/stacked.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Stacked Cards</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/tabbed.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Tabbed Cards</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/draggable.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Draggable Cards</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/tools.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Cards Tools</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/sticky.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Sticky Cards</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/cards/stretched.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Stretched Cards</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Devices/Diagnostics.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="2" y="3" width="20" height="18" rx="2" />
                                    <path d="M9.9486833,13.3162278 C9.81256925,13.7245699 9.43043041,14 9,14 L5,14 C4.44771525,14 4,13.5522847 4,13 C4,12.4477153 4.44771525,12 5,12 L8.27924078,12 L10.0513167,6.68377223 C10.367686,5.73466443 11.7274983,5.78688777 11.9701425,6.75746437 L13.8145063,14.1349195 L14.6055728,12.5527864 C14.7749648,12.2140024 15.1212279,12 15.5,12 L19,12 C19.5522847,12 20,12.4477153 20,13 C20,13.5522847 19.5522847,14 19,14 L16.118034,14 L14.3944272,17.4472136 C13.9792313,18.2776054 12.7550291,18.143222 12.5298575,17.2425356 L10.8627389,10.5740611 L9.9486833,13.3162278 Z" fill="#000000" fill-rule="nonzero" />
                                    <circle fill="#000000" opacity="0.3" cx="19" cy="6" r="1" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Widgets</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white  text-white ">Widgets</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/lists.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Lists</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/stats.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Stats</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/charts.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Charts</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/mixed.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Mixed</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/tiles.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Tiles</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/engage.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Engage</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/base-tables.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Base Tables</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/advance-tables.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Advance Tables</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/widgets/forms.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Forms</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/General/Attachment2.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M11.7573593,15.2426407 L8.75735931,15.2426407 C8.20507456,15.2426407 7.75735931,15.6903559 7.75735931,16.2426407 C7.75735931,16.7949254 8.20507456,17.2426407 8.75735931,17.2426407 L11.7573593,17.2426407 L11.7573593,18.2426407 C11.7573593,19.3472102 10.8619288,20.2426407 9.75735931,20.2426407 L5.75735931,20.2426407 C4.65278981,20.2426407 3.75735931,19.3472102 3.75735931,18.2426407 L3.75735931,14.2426407 C3.75735931,13.1380712 4.65278981,12.2426407 5.75735931,12.2426407 L9.75735931,12.2426407 C10.8619288,12.2426407 11.7573593,13.1380712 11.7573593,14.2426407 L11.7573593,15.2426407 Z" fill="#000000" opacity="0.3" transform="translate(7.757359, 16.242641) rotate(-45.000000) translate(-7.757359, -16.242641)" />
                                    <path d="M12.2426407,8.75735931 L15.2426407,8.75735931 C15.7949254,8.75735931 16.2426407,8.30964406 16.2426407,7.75735931 C16.2426407,7.20507456 15.7949254,6.75735931 15.2426407,6.75735931 L12.2426407,6.75735931 L12.2426407,5.75735931 C12.2426407,4.65278981 13.1380712,3.75735931 14.2426407,3.75735931 L18.2426407,3.75735931 C19.3472102,3.75735931 20.2426407,4.65278981 20.2426407,5.75735931 L20.2426407,9.75735931 C20.2426407,10.8619288 19.3472102,11.7573593 18.2426407,11.7573593 L14.2426407,11.7573593 C13.1380712,11.7573593 12.2426407,10.8619288 12.2426407,9.75735931 L12.2426407,8.75735931 Z" fill="#000000" transform="translate(16.242641, 7.757359) rotate(-45.000000) translate(-16.242641, -7.757359)" />
                                    <path d="M5.89339828,3.42893219 C6.44568303,3.42893219 6.89339828,3.87664744 6.89339828,4.42893219 L6.89339828,6.42893219 C6.89339828,6.98121694 6.44568303,7.42893219 5.89339828,7.42893219 C5.34111353,7.42893219 4.89339828,6.98121694 4.89339828,6.42893219 L4.89339828,4.42893219 C4.89339828,3.87664744 5.34111353,3.42893219 5.89339828,3.42893219 Z M11.4289322,5.13603897 C11.8194565,5.52656326 11.8194565,6.15972824 11.4289322,6.55025253 L10.0147186,7.96446609 C9.62419433,8.35499039 8.99102936,8.35499039 8.60050506,7.96446609 C8.20998077,7.5739418 8.20998077,6.94077682 8.60050506,6.55025253 L10.0147186,5.13603897 C10.4052429,4.74551468 11.0384079,4.74551468 11.4289322,5.13603897 Z M0.600505063,5.13603897 C0.991029355,4.74551468 1.62419433,4.74551468 2.01471863,5.13603897 L3.42893219,6.55025253 C3.81945648,6.94077682 3.81945648,7.5739418 3.42893219,7.96446609 C3.0384079,8.35499039 2.40524292,8.35499039 2.01471863,7.96446609 L0.600505063,6.55025253 C0.209980772,6.15972824 0.209980772,5.52656326 0.600505063,5.13603897 Z" fill="#000000" opacity="0.3" transform="translate(6.014719, 5.843146) rotate(-45.000000) translate(-6.014719, -5.843146)" />
                                    <path d="M17.9142136,15.4497475 C18.4664983,15.4497475 18.9142136,15.8974627 18.9142136,16.4497475 L18.9142136,18.4497475 C18.9142136,19.0020322 18.4664983,19.4497475 17.9142136,19.4497475 C17.3619288,19.4497475 16.9142136,19.0020322 16.9142136,18.4497475 L16.9142136,16.4497475 C16.9142136,15.8974627 17.3619288,15.4497475 17.9142136,15.4497475 Z M23.4497475,17.1568542 C23.8402718,17.5473785 23.8402718,18.1805435 23.4497475,18.5710678 L22.0355339,19.9852814 C21.6450096,20.3758057 21.0118446,20.3758057 20.6213203,19.9852814 C20.2307961,19.5947571 20.2307961,18.9615921 20.6213203,18.5710678 L22.0355339,17.1568542 C22.4260582,16.76633 23.0592232,16.76633 23.4497475,17.1568542 Z M12.6213203,17.1568542 C13.0118446,16.76633 13.6450096,16.76633 14.0355339,17.1568542 L15.4497475,18.5710678 C15.8402718,18.9615921 15.8402718,19.5947571 15.4497475,19.9852814 C15.0592232,20.3758057 14.4260582,20.3758057 14.0355339,19.9852814 L12.6213203,18.5710678 C12.2307961,18.1805435 12.2307961,17.5473785 12.6213203,17.1568542 Z" fill="#000000" opacity="0.3" transform="translate(18.035534, 17.863961) scale(1, -1) rotate(45.000000) translate(-18.035534, -17.863961)" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Icons</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/svg.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">SVG Icons</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/custom-icons.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Custom Icons</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/flaticon.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Flaticon</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/fontawesome5.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Fontawesome 5</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/lineawesome.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Lineawesome</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/icons/socicons.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white  text-white ">Socicons</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Design/Select.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <polygon points="0 0 24 0 24 24 0 24" />
                                    <path d="M18.5,8 C17.1192881,8 16,6.88071187 16,5.5 C16,4.11928813 17.1192881,3 18.5,3 C19.8807119,3 21,4.11928813 21,5.5 C21,6.88071187 19.8807119,8 18.5,8 Z M18.5,21 C17.1192881,21 16,19.8807119 16,18.5 C16,17.1192881 17.1192881,16 18.5,16 C19.8807119,16 21,17.1192881 21,18.5 C21,19.8807119 19.8807119,21 18.5,21 Z M5.5,21 C4.11928813,21 3,19.8807119 3,18.5 C3,17.1192881 4.11928813,16 5.5,16 C6.88071187,16 8,17.1192881 8,18.5 C8,19.8807119 6.88071187,21 5.5,21 Z" fill="#000000" opacity="0.3" />
                                    <path d="M5.5,8 C4.11928813,8 3,6.88071187 3,5.5 C3,4.11928813 4.11928813,3 5.5,3 C6.88071187,3 8,4.11928813 8,5.5 C8,6.88071187 6.88071187,8 5.5,8 Z M11,4 L13,4 C13.5522847,4 14,4.44771525 14,5 C14,5.55228475 13.5522847,6 13,6 L11,6 C10.4477153,6 10,5.55228475 10,5 C10,4.44771525 10.4477153,4 11,4 Z M11,18 L13,18 C13.5522847,18 14,18.4477153 14,19 C14,19.5522847 13.5522847,20 13,20 L11,20 C10.4477153,20 10,19.5522847 10,19 C10,18.4477153 10.4477153,18 11,18 Z M5,10 C5.55228475,10 6,10.4477153 6,11 L6,13 C6,13.5522847 5.55228475,14 5,14 C4.44771525,14 4,13.5522847 4,13 L4,11 C4,10.4477153 4.44771525,10 5,10 Z M19,10 C19.5522847,10 20,10.4477153 20,11 L20,13 C20,13.5522847 19.5522847,14 19,14 C18.4477153,14 18,13.5522847 18,13 L18,11 C18,10.4477153 18.4477153,10 19,10 Z" fill="#000000" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white  text-white ">Calendar</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white ">Calendar</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/calendar/basic.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Basic Calendar</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/calendar/list-view.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">List Views</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/calendar/google.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Google Calendar</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/calendar/external-events.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">External Events</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/calendar/background-events.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Background Events</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Media/Equalizer.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <rect fill="#000000" opacity="0.3" x="13" y="4" width="3" height="16" rx="1.5" />
                                    <rect fill="#000000" x="8" y="9" width="3" height="11" rx="1.5" />
                                    <rect fill="#000000" x="18" y="11" width="3" height="9" rx="1.5" />
                                    <rect fill="#000000" x="3" y="13" width="3" height="7" rx="1.5" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white ">Charts</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white ">Charts</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/charts/apexcharts.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Apexcharts</span>
                                        </a>
                                    </li>
                                    <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                                        <a href="javascript:;" class="menu-link menu-toggle">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">amCharts</span>
                                            <i class="menu-arrow"></i>
                                        </a>
                                        <div class="menu-submenu">
                                            <i class="menu-arrow"></i>
                                            <ul class="menu-subnav">
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="features/charts/amcharts/charts.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white ">amCharts Charts</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="features/charts/amcharts/stock-charts.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white ">amCharts Stock Charts</span>
                                                    </a>
                                                </li>
                                                <li class="menu-item" aria-haspopup="true">
                                                    <a href="features/charts/amcharts/maps.html" class="menu-link">
                                                        <i class="menu-bullet menu-bullet-dot">
                                                            <span></span>
                                                        </i>
                                                        <span class="menu-text text-white ">amCharts Maps</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/charts/flotcharts.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Flot Charts</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/charts/google-charts.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Google Charts</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Home/Book-open.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M13.6855025,18.7082217 C15.9113859,17.8189707 18.682885,17.2495635 22,17 C22,16.9325178 22,13.1012863 22,5.50630526 L21.9999762,5.50630526 C21.9999762,5.23017604 21.7761292,5.00632908 21.5,5.00632908 C21.4957817,5.00632908 21.4915635,5.00638247 21.4873465,5.00648922 C18.658231,5.07811173 15.8291155,5.74261533 13,7 C13,7.04449645 13,10.79246 13,18.2438906 L12.9999854,18.2438906 C12.9999854,18.520041 13.2238496,18.7439052 13.5,18.7439052 C13.5635398,18.7439052 13.6264972,18.7317946 13.6855025,18.7082217 Z" fill="#000000" />
                                    <path d="M10.3144829,18.7082217 C8.08859955,17.8189707 5.31710038,17.2495635 1.99998542,17 C1.99998542,16.9325178 1.99998542,13.1012863 1.99998542,5.50630526 L2.00000925,5.50630526 C2.00000925,5.23017604 2.22385621,5.00632908 2.49998542,5.00632908 C2.50420375,5.00632908 2.5084219,5.00638247 2.51263888,5.00648922 C5.34175439,5.07811173 8.17086991,5.74261533 10.9999854,7 C10.9999854,7.04449645 10.9999854,10.79246 10.9999854,18.2438906 L11,18.2438906 C11,18.520041 10.7761358,18.7439052 10.4999854,18.7439052 C10.4364457,18.7439052 10.3734882,18.7317946 10.3144829,18.7082217 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white ">Maps</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white ">Maps</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/maps/google-maps.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Google Maps</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/maps/leaflet.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Leaflet</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/maps/jqvmap.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">JQVMap</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="menu-item menu-item-submenu" aria-haspopup="true" data-menu-toggle="hover">
                            <a href="javascript:;" class="menu-link menu-toggle">
                                <span class="svg-icon menu-icon">
                                    <!--begin::Svg Icon | path:assets/media/svg/icons/Home/Mirror.svg-->
                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
                                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <rect x="0" y="0" width="24" height="24" />
                                    <path d="M13,17.0484323 L13,18 L14,18 C15.1045695,18 16,18.8954305 16,20 L8,20 C8,18.8954305 8.8954305,18 10,18 L11,18 L11,17.0482312 C6.89844817,16.5925472 3.58685702,13.3691811 3.07555009,9.22038742 C3.00799634,8.67224972 3.3975866,8.17313318 3.94572429,8.10557943 C4.49386199,8.03802567 4.99297853,8.42761593 5.06053229,8.97575363 C5.4896663,12.4577884 8.46049164,15.1035129 12.0008191,15.1035129 C15.577644,15.1035129 18.5681939,12.4043008 18.9524872,8.87772126 C19.0123158,8.32868667 19.505897,7.93210686 20.0549316,7.99193546 C20.6039661,8.05176407 21.000546,8.54534521 20.9407173,9.09437981 C20.4824216,13.3000638 17.1471597,16.5885839 13,17.0484323 Z" fill="#000000" fill-rule="nonzero" />
                                    <path d="M12,14 C8.6862915,14 6,11.3137085 6,8 C6,4.6862915 8.6862915,2 12,2 C15.3137085,2 18,4.6862915 18,8 C18,11.3137085 15.3137085,14 12,14 Z M8.81595773,7.80077353 C8.79067542,7.43921955 8.47708263,7.16661749 8.11552864,7.19189981 C7.75397465,7.21718213 7.4813726,7.53077492 7.50665492,7.89232891 C7.62279197,9.55316612 8.39667037,10.8635466 9.79502238,11.7671393 C10.099435,11.9638458 10.5056723,11.8765328 10.7023788,11.5721203 C10.8990854,11.2677077 10.8117724,10.8614704 10.5073598,10.6647638 C9.4559885,9.98538454 8.90327706,9.04949813 8.81595773,7.80077353 Z" fill="#000000" opacity="0.3" />
                                    </g>
                                    </svg>
                                    <!--end::Svg Icon-->
                                </span>
                                <span class="menu-text text-white ">Miscellaneous</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="menu-submenu">
                                <i class="menu-arrow"></i>
                                <ul class="menu-subnav">
                                    <li class="menu-item menu-item-parent" aria-haspopup="true">
                                        <span class="menu-link">
                                            <span class="menu-text text-white ">Miscellaneous</span>
                                        </span>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/kanban-board.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Kanban Board</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/sticky-panels.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Sticky Panels</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/blockui.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Block UI</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/perfect-scrollbar.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Perfect Scrollbar</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/treeview.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Tree View</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/bootstrap-notify.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Bootstrap Notify</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/toastr.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Toastr</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/sweetalert2.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">SweetAlert2</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/dual-listbox.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Dual Listbox</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/session-timeout.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Session Timeout</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/idle-timer.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Idle Timer</span>
                                        </a>
                                    </li>
                                    <li class="menu-item" aria-haspopup="true">
                                        <a href="features/miscellaneous/cropper.html" class="menu-link">
                                            <i class="menu-bullet menu-bullet-dot">
                                                <span></span>
                                            </i>
                                            <span class="menu-text text-white ">Cropper</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                    <!--end::Menu Nav-->
                </div>
                <!--end::Menu Container-->
            </div>
            <!--end::Aside Menu-->
        </div>
        <div class="container mt-5">
            <div class="accordion accordion-solid accordion-panel accordion-svg-toggle" id="accordionExample8">
                <div class="card card-custom gutter-b">
                    <div class="card-header" id="headingOne8">
                        <div class="card-title row col-12 d-flex justify-content-between">
                            <h3 class="card-label col-3 pl-0 pb-3"  data-toggle="collapse" data-target="#collapseOne8">
                                Búsqueda de Paciente
                            </h3>
                            <div class="col-4 justify-content-right">
                                <div class="ml-1 float-right">
                                    <a id="btnLimpiarFiltro" class="navi-link">
                                        <span class="symbol symbol-50 symbol-circle mr-3 ">
                                            <span id="circuloLimpiarFiltro" class="symbol-label bg-hover-primary  hoverIcon"  href="#" data-toggle="tooltip" title="Limpiar"><i id="iLimpiarFiltro" class="la la-broom icon-xl text-primary"></i></span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div> 
                                <div class="ml-1 float-right" >
                                    <a id="nuevoPaciente" class="navi-link">
                                        <span class='symbol symbol-50 symbol-circle mr-3'>
                                            <span id="circuloAgregarPaciente" class='symbol-label bg-hover-primary  hoverIcon' href="#" data-toggle="tooltip" title="Nuevo paciente"><i id="iAgregarPaciente" class='fa fas fa-user-plus  text-primary'></i></span>
                                        </span>
                                    </a>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div id="collapseOne8" class="collapse show" data-parent="#accordionExample8">
                        <div class="card-body row">
                            <!-- FORMULARIO FILTRO -->
                            <div class="col">
                                <div class="row col-12">
                                    <div class="col-10">
                                        <div class="form-group row">
                                            <label class="col-form-label text-left col-lg-4 ">Tipo de documento</label>
                                            <div class="col-lg-7 pl-0">
                                                <select id="selectTipoDocumentoFiltro" class="form-control selectpicker">
                                                    <option value="6" >TODOS</option>
                                                    <c:forEach var="documento" items="${listaTiposDocumentos}">
                                                        <option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id='divTxtRutFiltro' class="col-12">
                                    <div class="form-group row">
                                        <label id="lblRutFiltro" class="col-3 col-form-label">RUN
                                        </label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtFiltroRut" type="text" minlength="11" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"/>
                                        </div>
                                    </div>
                                </div>
                                <div id='divTxtPasaporteFiltro' class="col-12 ocultar">
                                    <div class="form-group row">
                                        <label  class="col-3 col-form-label">Pasaporte </label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtPasaporteFiltro" type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div id='divTxtNNFiltro' class="col-12 ocultar">
                                    <div class="form-group row">
                                        <label  class="col-3 col-form-label">Fecha Ingreso </label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtFiltroNN" type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label  class="col-3 col-form-label">Nombre</label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtFiltroNombre" type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label  class="col-3 col-form-label">Primer Apellido</label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtFiltroApellido" type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group row">
                                        <label  class="col-3 col-form-label">Segundo Apellido</label>
                                        <div class="col-9">
                                            <input class="form-control" placeholder="" id="txtFiltroSegundoApellido" type="text"/>
                                        </div>
                                    </div>
                                </div>
                                <a id="btnBuscarFiltro" href="#" class="btn btn-light-primary font-weight-bold mr-2 ml-4"><i class="la la-search"></i>Buscar</a>
                            </div>
                            <!-- FIN FORMULARIO FILTRO -->
                            <!-- TABLA FILTRO -->
                            <div class="col scroll scroll-pull" data-scroll="true" data-suppress-scroll-x="false" data-swipe-easing="false" style="height: 270px">
                                <table id="tableFiltro" class="table table-hover">
                                    <thead>
                                        <tr id="trHeader">
                                            <th scope="col">#</th>
                                            <th id="thIdentificador" scope="col">Identificador</th>
                                            <th id="thNombre" scope="col">Nombre</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tbodyFiltro">

                                    </tbody>
                                </table>
                                <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron pacientes con los datos provistos.</label>
                            </div>
                            <!-- FIN TABLA FILTRO -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- FORMULARIO REGISTRO PACIENTE -->
            <div class="card card-custom gutter-b  mt-5">
                <div class="card-header pl-2 bg-primary"> 
                    <div class="card-title row col-12 d-flex justify-content-between">
                        <h3 class="card-label col-3 pl-0 text-white">
                            Registro de Paciente
                        </h3>
                        <div class="col-4 justify-content-right ml-4 pl-4 pr-0">
                            <div class="ml-1 float-right">
                                <a id="btnLimpiar" class="navi-link" href="#">
                                    <span class="symbol symbol-50 symbol-circle mr-3 ">
                                        <span id="circuloLimpiar" class="symbol-label bg-primary hoverIcon" data-toggle="tooltip" title="Limpiar"><i id="iLimpiar" class="la la-broom icon-xl  text-white"></i></span>
                                    </span>
                                    <span class="navi-text"></span>
                                </a>
                            </div>   
                            <div class="ml-1 float-right" >
                                <a id="btnEditar" class="navi-link" href="#" >
                                    <span class='symbol symbol-50 symbol-circle mr-3' >
                                        <span id="circuloEditarPaciente" class='symbol-label bg-primary circuloIcon' data-toggle="tooltip" title="Editar paciente"><i id="iEditPaciente" class="far fa-edit text-dark-50"></i></span>
                                    </span>
                                </a>
                            </div>
                            <div class="ml-1 float-right" >
                                <a id="buscarPaciente" class="navi-link" href="#" >
                                    <span class='symbol symbol-50 symbol-circle mr-3'>
                                        <span id="circuloBuscarPaciente" class='symbol-label bg-primary hoverIcon' data-toggle="tooltip" title="Buscar paciente"><i id="iBuscarPaciente" class="fas fa-search text-white"></i></span>
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
                                    <div class="tab-pane fade show active" id="kt_tab_pane_1" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                                        <div class="row">
                                            <div class="form-group row align-items-center col-12">
                                                <input id="validadorEditPac" name="validadorEditPac" type="text" class="form-control ocultar" placeholder="" />
                                                <!--<input id="validadorBuscarAgregarPac" name="validadorBuscarAgregarPac" type="text" class="form-control ocultar" placeholder="" />-->
                                                <div class="col-md-4 ">
                                                    <label for="selectTipoDocumento" class="">Tipo de documento :</label>
                                                    <select id="selectTipoDocumento" class="form-control selectpicker mt-5" name="ldvtdIdtipodocumento" >
                                                        <c:forEach var="documento" items="${listaTiposDocumentos}">
                                                            <option value="${documento.ldvtdIdtipodocumento}">${documento.ldvtdDescripcion}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </select>
                                                </div>
                                                <div id="divTxtRut" class="col-md-4">
                                                    <label>RUN: *</label>
                                                    <input id="txtRut" name="dpRun" type="text" class="form-control mt-5" placeholder="" minlength="11" maxlength="12" autocomplete="off" onkeyup="this.value = this.value.toUpperCase();"/>
                                                    <div class="invalid-feedback">RUN inválido</div>
                                                    <div class="d-md-none mb-2"></div>
                                                </div>
                                                <input id="txtIdPaciente" name="idPaciente" type="text" class="ocultar"/>
                                                <div id="divTxtPasaporte" class="col-md-4 ocultar">
                                                    <label>Pasaporte: *</label>
                                                    <input id="txtPasaporte" name="dpNrodocumento" type="text" class="form-control mt-5" autocomplete="off" placeholder="" />
                                                    <div class="invalid-feedback">RUN inválido</div>
                                                    <div class="d-md-none mb-2"></div>
                                                </div>
                                                <div class="w-100"></div>
                                                <!--
                                                <div id="divBtnCancelEdit" class="col-1 ocultar">
                                                    <a id="btnCancelEdit" class="navi-link" href="#">
                                                        <span class="symbol symbol-50 symbol-circle mr-3">
                                                            <span class="symbol-label"><i class="la la-user-times text-danger"></i></span>
                                                        </span>
                                                        <span class="navi-text"></span>
                                                    </a>
                                                </div> -->
                                                <div id="divTxtNombre" class="col-md-4 mt-6">
                                                    <label>Nombres: *<span class="label" data-toggle="popover" data-html="true" data-content="Nombre legal, debe ser idéntico al carné de identidad. Para variaciones usar campo nombre social."><i class="fa la-info-circle"></i></span></label>
                                                    <input id="txtNombre" name="dpNombres" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                                    <div class="d-md-none mb-2"></div>
                                                </div>
                                                <div class="col-md-4 mt-6">
                                                    <label>Primer Apellido: *</label>
                                                    <input id="txtPrimerApellido" name="dpPrimerapellido"  type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                                    <div class="d-md-none mb-2"></div>
                                                </div>
                                                <div class="col-md-4 mt-6">
                                                    <label>Segundo Apellido: </label>
                                                    <input id="txtSegundoApellido" name="dpSegundoapellido" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                                    <div class="d-md-none mb-2"></div>
                                                </div>
                                                <div class="col-md-4 mt-6">
                                                    <label for="selectSexo" class="">Sexo: *<span class="label" data-toggle="popover" data-html="true" data-content="Sexo biológico o sexo al nacer. Requerido para análisis objetivos de datos bioquímicos u hormonales."><i class="fa la-info-circle"></i></span></label>
                                                    <select class="form-control selectpicker mt-5 disabledForm" id="selectSexo" name="ldvsIdsexo">
                                                        <option value="N" disabled selected>Seleccionar</option>
                                                        <c:forEach var="sexo" items="${listaSexo}">
                                                            <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-md-4 mt-6">
                                                    <label>Fecha Nacimiento: *</label>
                                                    <input id="txtFechaNacimiento" name="dpFnacimiento" type="text" class="form-control mt-5 disabledForm" autocomplete="off" placeholder=""/>
                                                </div>
                                                <div class="col-md-4 row pr-0 mt-6">
                                                    <div class="col-md-4 pr-0">
                                                        <label>Años :</label>
                                                        <input id="txtAnio" name="" type="text" class="form-control mt-5 disabledForm" placeholder=""/>
                                                    </div>
                                                    <div class="col-md-4 pr-0">
                                                        <label>Meses :</label>
                                                        <input id="txtMeses" name="" type="text" class="form-control mt-5 disabledForm" placeholder=""/>
                                                    </div>
                                                    <div class="col-md-4 pr-0">
                                                        <label>Días :</label>
                                                        <input id="txtDias" name="" type="text" class="form-control mt-5 disabledForm" placeholder=""/>
                                                    </div>

                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_2" role="tabpanel" aria-labelledby="kt_tab_pane_2">
                                        <div class="form-group row align-items-center col-12">
                                            <div class="checkbox-inline col-12 mb-5">
                                                <label class="checkbox checkbox-primary ml-5">
                                                    <input  id="checkboxVIP" name="pacienteVip" type="checkbox" class="disabledForm"/> Paciente VIP
                                                    <span></span>
                                                </label>
                                                <label class="checkbox checkbox-primary mr-0">
                                                    <input id="checkboxAfro" name="pacienteAfro" type="checkbox" class="disabledForm" /> Paciente Afroamericano 
                                                    <span></span>
                                                </label><span class="label" data-toggle="popover" data-html="true" data-content="Dato requerido para el análisis de la función renal mediante la fórmula MDRD."><i class="fa la-info-circle"></i></span>
                                                <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                                    <input id="checkBoxExitus" name="exitus" type="checkbox" class="" /> Fallecimiento
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col-md-4 mt-6">
                                                <label class="">Region: </label>
                                                <select id="selectRegion" name="ldvrIdregion" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                                    <option value="" disabled selected>Seleccionar</option>
                                                    <c:forEach var="region" items="${listaRegiones}">
                                                        <option value="${region.ldvrIdregion}">${region.ldvrDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-4 mt-6">
                                                <label class="">Comuna : </label>
                                                <select id="selectComuna" name="ldvcIdcomuna" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                                    <option value="" disabled selected>Seleccionar</option>
                                                </select>
                                            </div>
                                            <div class="col-4 mt-6">
                                                <label>Dirección:  </label>
                                                <input id="txtDireccion" name="dpDireccion" type="text" class="form-control disabledForm mt-5" autocomplete="off" placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-md-4 mt-6">
                                                <label class="">Pais Origen: </label>
                                                <select id="selectPais" name="ldvpIdpais" class="form-control selectpicker disabledForm mt-5" data-live-search="true">
                                                    <option value="" disabled selected>Seleccionar</option>
                                                    <c:forEach var="pais" items="${listaPaises}">
                                                        <option value="${pais.ldvpIdpais}">${pais.ldvpDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="col-md-4 mt-6">
                                                <label class="">Estado Civil: </label>
                                                <select id="selectEstadoCivil" name="ldvecIdestadocivil" class="form-control selectpicker disabledForm mt-5">
                                                    <option value="" disabled selected>Seleccionar</option>
                                                    <c:forEach var="estadoCivil" items="${listaEstadosCiviles}">
                                                        <option value="${estadoCivil.ldvecIdestadocivil}">${estadoCivil.ldvecDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-4 mt-6">
                                                <label>Nombre Social: </label>
                                                <input id="txtNombreSocial" name="dpNombresocial" type="text" class="form-control disabledForm mt-5"  placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-12 mt-6">
                                                <label>Observaciones: </label>
                                                <input id="txtObservaciones" name="dpObservacion" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_3" role="tabpanel" aria-labelledby="kt_tab_pane_3">
                                        <div class="form-group row align-items-center col-12">
                                            <div class="col-4 ">
                                                <label>Teléfono: </label>
                                                <input id="txtTelefono" name="dpTelefono" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-4 ">
                                                <label>Email: </label>
                                                <input id="txtEmail" name="dpEmail" type="email" class="form-control disabledForm mt-5" placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-4 ">
                                                <label>Código postal:  </label>
                                                <input  id="txtCodigoPostal" name="dpDireccodpostal" type="text" class="form-control disabledForm mt-5" placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_4" role="tabpanel" aria-labelledby="kt_tab_pane_4">
                                        <div  class="form-group row align-items-center col-8">
                                            <div class="col-md-12">
                                                <label class="">Patolog&iacute;a: </label>
                                                <select id="selectPatologias" class="form-control selectpicker ">
                                                    <option value="N" disabled selected>Seleccionar</option>
                                                    <c:forEach var="patologia" items="${listaPatologias}">
                                                        <option value="${patologia.ldvpatIdpatologia}">${patologia.ldvpatDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button id="btnPatologia" type="button" class="btn btn-light-primary font-weight-bold mr-2 mt-5 ml-4">Agregar Patologia</button>
                                            <table id='tablaPatologia' class="col-12 mt-5">
                                                <thead>
                                                    <tr>
                                                        <th>Patolog&iacute;a</th>
                                                        <th>Observacion</th>
                                                        <th>Eliminar</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tbodyPatologias">

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_5" role="tabpanel" aria-labelledby="kt_tab_pane_5">
                                        <div class="form-group row align-items-center col-8">
                                            <div class="col-6">
                                                <label>Nombre: </label>
                                                <input id="txtNombreContacto" name="dcNombre" type="text" class="form-control " placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="">Relacion: </label>
                                                <select id="selectPacienteRelacion" name="ldvcprIdcontactopacienterel" class="form-control selectpicker ">
                                                    <option value="" disabled selected>Seleccionar</option>
                                                    <c:forEach var="pacRelacion" items="${listaPacienteRelacion}">
                                                        <option value="${pacRelacion.ldvcprIdcontactopacienterel}">${pacRelacion.ldvcprDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-6">
                                                <label>Teléfono: </label>
                                                <input id="txtTelefonoContacto" name="dcTelefono" type="text" class="form-control " placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="">Sexo: </label>
                                                <select id="selectSexoContacto" name="ldvsIdsexoContacto" class="form-control selectpicker ">
                                                    <option value="N" disabled selected>Seleccionar</option>
                                                    <c:forEach var="sexo" items="${listaSexo}">
                                                        <option value="${sexo.ldvsIdsexo}">${sexo.ldvsDescripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="kt_tab_pane_6" role="tabpanel" aria-labelledby="kt_tab_pane_6">
                                        <div class="form-group row align-items-center col-12">
                                            <div class="col-6">
                                                <label id="lblRunMadre">RUN Madre: </label>
                                                <input id="txtRutMadre" name="rutMadre" type="text" class="form-control " placeholder="" minlength="11" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                            <div class="checkbox-inline col-3 mb-5 right-align">
                                                <label class="checkbox checkbox-primary ml-5">
                                                    <input id="checkboxPartoMultiple" name="esPartoMultiple" type="checkbox" class=""/> Parto Múltiple
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col-3">
                                                <label>N° Parto Múltiple: </label>
                                                <input id="txtNroPartoMultiple" name="dpRnnumero" type="text" class="form-control " placeholder=""/>
                                                <div class="d-md-none mb-2"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <ul class="nav  flex-column nav-pills">
                                    <li class="nav-item" style="max-height: 38.5px;">
                                        <a class="nav-link active" data-toggle="tab" href="#kt_tab_pane_1">
                                            <span class="nav-icon"><i class="flaticon2-paper"></i></span>
                                            <span class="nav-text">Datos Básicos</span>
                                        </a>
                                    </li>
                                    <li class="nav-item" style="max-height: 38.5px;">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_2">
                                            <span class="nav-icon"><i class="flaticon2-copy"></i></span>
                                            <span class="nav-text">Datos Extras</span>
                                        </a>
                                    </li>
                                    <li class="nav-item" style="max-height: 38.5px;">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_3">
                                            <span class="nav-icon"><i class="la la-phone"></i></span>
                                            <span class="nav-text">Comunicacion</span>
                                        </a>
                                    </li>
                                    <li class="nav-item" style="max-height: 38.5px;">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_4">
                                            <span class="nav-icon"><i class="fas fa-book-medical"></i></span>
                                            <span class="nav-text">Patolog&iacute;as</span>
                                        </a>
                                    </li>
                                    <li class="nav-item" style="max-height: 38.5px;">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_5">
                                            <span class="nav-icon"><i class="flaticon2-avatar"></i></span>
                                            <span class="nav-text">Contacto</span>
                                            <span id="spanContacto" class="ocultar label label-danger font-weight-bold label-inline"><i class="fa la-exclamation-circle text-white"></i></span>
                                        </a>
                                    </li>
                                    <li id="liDatosMadres" class="nav-item ocultar">
                                        <a class="nav-link" data-toggle="tab" href="#kt_tab_pane_6">
                                            <span class="nav-icon"><i class="fas fa-female"></i></span>
                                            <span class="nav-text">Datos Madre</span>
                                            <span id="spanDatosMadre" class="ocultar label label-danger font-weight-bold label-inline"><i class="fa la-exclamation-circle text-white"></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <button id="btnAgregarPaciente" name="insert" type="submit" class="btn btn-light-primary font-weight-bold mr-2 ml-4 mb-5 disabledForm" ><i class="far fa-save"></i>Guardar</button>

                            <input id="btnUpdatePaciente" name="update" type="submit" class="btn btn-light-primary font-weight-bold mr-2 ocultar" value="Confirmar"/>
                            <a id="btnConfirmarDatosPac" href="./Orden" href="#" class="btn btn-light-primary font-weight-bold mr-2 float-right ocultar"><i class="la la-search"></i>Confirmar Datos</a>
                        </div>
                    </form>
                </div>
            </div>
            <!-- FIN FORMULARIO REGISTRO PACIENTE -->
        </div>
        <script>var KTAppSettings = {"breakpoints": {"sm": 576, "md": 768, "lg": 992, "xl": 1200, "xxl": 1200}, "colors": {"theme": {"base": {"white": "#ffffff", "primary": "#6993FF", "secondary": "#E5EAEE", "success": "#1BC5BD", "info": "#8950FC", "warning": "#FFA800", "danger": "#F64E60", "light": "#F3F6F9", "dark": "#212121"}, "light": {"white": "#ffffff", "primary": "#E1E9FF", "secondary": "#ECF0F3", "success": "#C9F7F5", "info": "#EEE5FF", "warning": "#FFF4DE", "danger": "#FFE2E5", "light": "#F3F6F9", "dark": "#D6D6E0"}, "inverse": {"white": "#ffffff", "primary": "#ffffff", "secondary": "#212121", "success": "#ffffff", "info": "#ffffff", "warning": "#ffffff", "danger": "#ffffff", "light": "#464E5F", "dark": "#ffffff"}}, "gray": {"gray-100": "#F3F6F9", "gray-200": "#ECF0F3", "gray-300": "#E5EAEE", "gray-400": "#D6D6E0", "gray-500": "#B5B5C3", "gray-600": "#80808F", "gray-700": "#464E5F", "gray-800": "#1B283F", "gray-900": "#212121"}}, "font-family": "Poppins"};</script>
        <!--begin::Global Theme Bundle(used by all pages)-->
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/global/plugins.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
        <script src="<%=request.getContextPath()%>/resources/template/assets/js/scripts.bundle.js"></script>
        <!--end::Global Theme Bundle-->
        <!--begin::Page Vendors(used by this page)-->
        <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
        <!--end::Page Vendors-->
        <!--begin::Page Scripts(used by this page)-->
        <script src="<%=request.getContextPath()%>/resources/template/assets/js/pages/widgets.js"></script>
        <!--end::Page Scripts-->
        <script src="<%=request.getContextPath()%>/resources/js/jquery.rut.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/normalizarUTF8.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/localStorage.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap-datepicker.es.js" charset="UTF-8"></script>
        <script src="<%=request.getContextPath()%>/resources/js/RegistroPacienteJs.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/localStorage.js"></script>
    </body>

</html>
