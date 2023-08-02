<%-- 
    Document   : menu
    Created on : 29-11-2021
    Author     : juan.nunez

    Menú para ser incluido en layout
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/cssfront/master.css" id="theme-stylesheet">
<!-- Menu vertical--> 
    <div class="sidebar py-3 background-menu" id="sidebar">
        <a class="bg-edwin  pointer none" id="logoc">
        <svg class="logo-c ocultar" id="bioslistc" data-name="Capa 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 189 193.32"><defs><style>.cls-1{fill:#fff;}</style></defs><title>Mesa de trabajo 3</title><path class="cls-1" d="M91.4,121.66c-10.4,0-20.1.14-29.79-.07-3.34-.07-5.6.55-5.65,5.56-1.63-3.63-2.65-5.92-3.67-8.21-.64-2.55-1.29-5.1-1.93-7.65-.19-7.7-.61-15.41,2-22.88h0A80.33,80.33,0,0,1,60.41,75.1c4.34-3.88,8.18-8.32,13-11.63C79,60.91,83,56.51,86.91,52h0c4.15-2.68,6.9-6.61,9.56-10.62h0a32.89,32.89,0,0,0,6.85-12.16c1.65-2.42,2.11-5.25,2.78-8,.51-2.1-.35-3.3-2.67-3.46-15.29-1.07-30,.68-43.6,8.39h0A58.64,58.64,0,0,0,40,40.61,39.42,39.42,0,0,0,31.2,51.1a31.93,31.93,0,0,0-6,10.41h0a42.82,42.82,0,0,0-5.53,15.36h0c-1.72,3-2.95,20.09-2.95,23.91,0,.64,1.09,8.6,2.83,11.61h0A11.7,11.7,0,0,0,21.38,119h0c.67,5,3.16,9.3,5.85,13.48h0a39.74,39.74,0,0,0,8.51,12.37,50.72,50.72,0,0,0,12.77,12.11A37.43,37.43,0,0,0,52.68,160c10,5.55,20.73,10,28.11,19.36.27.34.81.46,1.22.68-3.13-8-6.08-16.09-6.56-24.8h0c.33-3.12.65-6.23,1-9.35h0c2-5.26,3.28-10.85,6.8-15.46h0Z"/><path class="cls-1" d="M129.68,82.05c-13.57,0-26.85.49-40.07-.2-7.12-.38-10.7,3-14.29,8.59,14.12,0,27.69-.42,41.21.17C123.26,90.91,127.45,89,129.68,82.05Z"/><path class="cls-1" d="M77.34,100.64c-6.7-.32-11.79,1.17-15.42,6.84-1.5,3.43,1.6,1.77,2.33,1.78,13.75.12,27.51.09,41.26.07,1.22,0,2.61.39,3.48-1,1.57-2.31,3.14-4.61,5.17-7.56C101.26,100.81,89.27,101.22,77.34,100.64Z"/><path class="cls-1" d="M172.56,90.27h0a32.62,32.62,0,0,0-1.83-12.81v0c-.38-3.32-1-6.56-2.86-9.43h0c-.49-3.12-2-5.8-3.61-8.45a40,40,0,0,0-7.16-11.24c-1.61-2.46-3.18-5-5.79-6.53-2.58-2.26-4-5.83-7.69-6.83-6.58-5.1-13.12-10.38-21.38-12.51A9.21,9.21,0,0,1,117,19.28a106.18,106.18,0,0,0-8-7.73c1.77,5.84,3.3,11.23,4.88,16.59.33,7,.56,14.1-.94,21.06h0L108,59.67c-3,3.59-5.9,7.25-10.59,10.81,11.92,0,22.57,0,33.22,0,2.3,0,3-1,3.25-3.09.41-3.59,1.28.07,1.93-.06h0q1.93,6.23,3.85,12.44c.1,1,.2,1.91.31,2.86q-.15,7.07-.28,14.13h0q-1.4,4.36-2.8,8.73h0q-1.91,3.36-3.83,6.72h0l-5.72,6.88h0L111,132.31h0c-3.08,2.34-6.22,4.61-8.63,7.7h0c-4.89,4.86-9.42,10-12.49,16.24C87,160,85.75,164.42,84.4,168.8c-.66,2.17,0,3.33,2.37,3.5,19.93,1.45,38.35-2.52,54.4-15h0A55.29,55.29,0,0,0,157,141.85a29.78,29.78,0,0,0,5.71-8.58h0a45.47,45.47,0,0,0,5.77-12.42,20,20,0,0,0,2.09-7.65,6.8,6.8,0,0,0,1-4.77c1.4-3.38,1-7,1.14-10.47A12,12,0,0,0,172.56,90.27Z"/></svg>
        </a>
        <a class="bg-edwin logo mt-5 pointer" id="logog">
        <svg class="logo" id="Capa_1" data-name="Capa 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 699 179"><defs><style>.cls-1,.cls-2{fill:#fff;}.cls-2{font-size:146.09px;font-family:FranklinGothic-Demi, Franklin Gothic Demi;font-weight:300;}</style></defs><title>Bioslist</title><path class="cls-1" d="M79.8,113.62c-10.39,0-20.09.13-29.79-.08-3.33-.07-5.6.56-5.65,5.57-1.62-3.64-2.64-5.93-3.67-8.21q-1-3.83-1.93-7.65c-.18-7.7-.61-15.42,2-22.88h0a80,80,0,0,1,8.09-13.31c4.33-3.89,8.17-8.32,13-11.64,5.58-2.55,9.56-7,13.47-11.44h0c4.15-2.68,6.91-6.61,9.57-10.62h0A33.33,33.33,0,0,0,91.73,21.2c1.64-2.42,2.11-5.26,2.78-8,.51-2.1-.36-3.31-2.68-3.47-15.29-1.06-30,.69-43.59,8.4h0A58.81,58.81,0,0,0,28.36,32.56,39.61,39.61,0,0,0,19.6,43.05a31.83,31.83,0,0,0-6,10.41h0A42.51,42.51,0,0,0,8.06,68.82h0c-1.72,3-2.95,20.09-2.95,23.92,0,.63,1.09,8.6,2.82,11.6h0a11.75,11.75,0,0,0,1.85,6.59h0c.67,5,3.17,9.3,5.85,13.48h0a39.61,39.61,0,0,0,8.52,12.36,50.51,50.51,0,0,0,12.76,12.11,40,40,0,0,0,4.18,3.1c10,5.55,20.72,10.05,28.11,19.36.26.34.8.46,1.22.68-3.13-8-6.09-16.09-6.56-24.81h0c.32-3.11.65-6.23,1-9.34h0c2-5.27,3.27-10.85,6.79-15.46h0Z"/><path class="cls-1" d="M118.09,74c-13.57,0-26.86.5-40.08-.2-7.12-.37-10.7,3-14.28,8.59,14.12,0,27.68-.42,41.21.18C111.66,82.86,115.85,80.93,118.09,74Z"/><path class="cls-1" d="M65.74,92.6c-6.69-.32-11.79,1.17-15.42,6.83-1.5,3.44,1.6,1.78,2.34,1.78,13.75.12,27.5.1,41.25.07,1.22,0,2.61.39,3.48-1l5.17-7.56C89.67,92.76,77.68,93.18,65.74,92.6Z"/><path class="cls-1" d="M161,82.22h0a32.83,32.83,0,0,0-1.83-12.81h0A21.29,21.29,0,0,0,156.27,60h0c-.48-3.12-2-5.8-3.61-8.45a40,40,0,0,0-7.16-11.23c-1.6-2.47-3.18-5-5.79-6.53-2.58-2.26-4-5.84-7.68-6.83-6.59-5.1-13.12-10.39-21.39-12.51a9.23,9.23,0,0,1-5.29-3.18,104,104,0,0,0-8-7.72c1.77,5.84,3.29,11.23,4.87,16.59.33,7,.56,14.09-.93,21.06h0q-2.44,5.24-4.88,10.46c-3,3.59-5.91,7.25-10.59,10.81,11.92,0,22.57,0,33.21,0,2.31,0,3-1,3.25-3.1.42-3.59,1.28.08,1.94,0h0q1.92,6.23,3.85,12.44l.3,2.85q-.13,7.08-.28,14.14h0l-2.79,8.73h0l-3.84,6.71h0l-5.72,6.89h0l-16.4,13.18h0c-3.08,2.35-6.22,4.62-8.63,7.7h0c-4.89,4.87-9.42,10-12.5,16.25-2.82,3.75-4.08,8.17-5.42,12.55-.67,2.17,0,3.32,2.36,3.5,19.94,1.45,38.36-2.52,54.4-15h0a55.16,55.16,0,0,0,15.85-15.45,29.57,29.57,0,0,0,5.71-8.58h0a45.82,45.82,0,0,0,5.77-12.43,20.38,20.38,0,0,0,2.09-7.64c1.07-1.44,1.07-3.1,1-4.78,1.39-3.38,1-6.95,1.13-10.46A12,12,0,0,0,161,82.22Z"/><text class="cls-2" transform="translate(182.89 107.28)">BIOSLIS</text><path class="cls-1" d="M191.69,139.06h1.81v21.79h12.61l-.34,1.68H191.69Z"/><path class="cls-1" d="M217.3,132.32l-5.63,4.59h-2.08l5.17-4.59Zm-5.76,6.74v23.47h-1.81V139.06Z"/><path class="cls-1" d="M218.44,139.06h7.78c6.44,0,10.9,4.49,10.9,11.53s-4.36,11.94-10.93,11.94h-7.75Zm1.81,21.79h5.67c6.2,0,9.25-4.36,9.25-10.16,0-5.16-2.75-9.95-9.25-9.95h-5.67Z"/><path class="cls-1" d="M256.05,151h-12v9.86h13.1l-.26,1.68H242.28V139.06h14.38v1.68H244.09v8.58h12Z"/><path class="cls-1" d="M263.66,151.56v11h-1.84V139.06h8.41c4.63,0,7.08,2.48,7.08,6.17a5.32,5.32,0,0,1-4.39,5.5c2.41.57,4,2.14,4,6.13v.91c0,1.64-.14,3.85.3,4.76H275.4c-.44-1-.34-2.89-.34-4.9V157c0-3.86-1.14-5.47-5.63-5.47Zm0-1.67h5.67c4.12,0,6-1.54,6-4.63,0-2.88-1.87-4.52-5.53-4.52h-6.17Z"/><path class="cls-1" d="M305.36,151h-12v9.86h13.1l-.26,1.68H291.59V139.06H306v1.68H293.4v8.58h12Z"/><path class="cls-1" d="M311.13,162.53V139.06h2.68c3.65,5.63,12.24,18.74,13.71,21.29h0c-.21-3.39-.17-6.84-.17-10.66V139.06h1.84v23.47h-2.48c-3.49-5.43-12.2-19.08-13.88-21.66h0c.17,3.08.13,6.5.13,10.76v10.9Z"/><path class="cls-1" d="M346.33,139.06v23.47h-1.81V139.06Z"/><path class="cls-1" d="M353.23,162.53V139.06h2.68c3.66,5.63,12.24,18.74,13.71,21.29h0c-.2-3.39-.17-6.84-.17-10.66V139.06h1.84v23.47h-2.48c-3.48-5.43-12.2-19.08-13.88-21.66h0c.17,3.08.14,6.5.14,10.76v10.9Z"/><path class="cls-1" d="M378.24,139.06h14.21v1.68h-12.4v8.85h11.63v1.67H380.05v11.27h-1.81Z"/><path class="cls-1" d="M416.69,150.63c0,6.4-3.42,12.23-10.66,12.23-6.61,0-10.46-5.33-10.46-12.07s3.72-12.07,10.69-12.07C412.7,138.72,416.69,143.79,416.69,150.63Zm-19.18.06c0,5.4,3,10.5,8.62,10.5,6.07,0,8.61-5,8.61-10.53s-2.88-10.26-8.55-10.26C400.16,140.4,397.51,145.4,397.51,150.69Z"/><path class="cls-1" d="M423.69,151.56v11h-1.84V139.06h8.41c4.63,0,7.08,2.48,7.08,6.17a5.32,5.32,0,0,1-4.39,5.5c2.41.57,4,2.14,4,6.13v.91c0,1.64-.14,3.85.3,4.76h-1.84c-.44-1-.34-2.89-.34-4.9V157c0-3.86-1.14-5.47-5.63-5.47Zm0-1.67h5.67c4.12,0,6-1.54,6-4.63,0-2.88-1.88-4.52-5.53-4.52h-6.17Z"/><path class="cls-1" d="M463.55,151.73c0-4.59,0-9,.13-11.33h-.1c-.94,3.15-5.7,14.72-8.68,22.13h-1.78c-2.24-5.74-7.17-18.61-8.24-22.16h-.07c.17,2.68.23,8.14.23,12.17v10h-1.81V139.06H446c3.22,8.05,7.27,18.57,8.15,21.22h.06c.54-1.71,5.23-13.41,8.52-21.22h2.68v23.47h-1.81Z"/><path class="cls-1" d="M474.34,154.41l-3,8.12h-1.84l8.61-23.47h2.15l9,23.47h-2l-3-8.12Zm9.29-1.67c-2.62-7-4-10.33-4.43-11.9h0c-.57,1.77-2.08,5.93-4.19,11.9Zm2.21-20.42-5.63,4.59h-2.08l5.16-4.59Z"/><path class="cls-1" d="M495.26,140.74h-7.88v-1.68H505v1.68H497.1v21.79h-1.84Z"/><path class="cls-1" d="M510.31,139.06v23.47H508.5V139.06Z"/><path class="cls-1" d="M534.58,156.26a8.56,8.56,0,0,1-8.82,6.54c-6.73,0-10.29-5.37-10.29-12,0-6.34,3.49-12.11,10.33-12.11,5.06,0,8,2.82,8.82,6.74H532.8c-1.07-3.05-3-5.06-7.1-5.06-5.84,0-8.28,5.33-8.28,10.39s2.41,10.33,8.41,10.33c3.82,0,5.77-2,6.94-4.86Z"/><path class="cls-1" d="M541.82,154.41l-3,8.12H537l8.62-23.47h2.14l9,23.47h-2l-3-8.12Zm9.29-1.67c-2.62-7-4-10.33-4.43-11.9h0c-.57,1.77-2.08,5.93-4.19,11.9Z"/><path class="cls-1" d="M587.31,156.26a8.57,8.57,0,0,1-8.82,6.54c-6.74,0-10.29-5.37-10.29-12,0-6.34,3.49-12.11,10.33-12.11,5.06,0,8,2.82,8.81,6.74h-1.81c-1.07-3.05-3-5.06-7.1-5.06-5.84,0-8.28,5.33-8.28,10.39s2.41,10.33,8.41,10.33c3.82,0,5.77-2,6.94-4.86Z"/><path class="cls-1" d="M592.37,139.06h1.81v21.79h12.61l-.34,1.68H592.37Z"/><path class="cls-1" d="M618,132.32l-5.63,4.59h-2.08l5.16-4.59Zm-5.77,6.74v23.47H610.4V139.06Z"/><path class="cls-1" d="M619.12,162.53V139.06h2.68c3.66,5.63,12.24,18.74,13.71,21.29h0c-.21-3.39-.17-6.84-.17-10.66V139.06h1.84v23.47h-2.48c-3.49-5.43-12.2-19.08-13.88-21.66h0c.17,3.08.13,6.5.13,10.76v10.9Z"/><path class="cls-1" d="M645.94,139.06v23.47h-1.81V139.06Z"/><path class="cls-1" d="M670.21,156.26a8.58,8.58,0,0,1-8.82,6.54c-6.74,0-10.29-5.37-10.29-12,0-6.34,3.49-12.11,10.32-12.11,5.07,0,8.05,2.82,8.82,6.74h-1.81c-1.07-3.05-3-5.06-7.11-5.06-5.83,0-8.28,5.33-8.28,10.39s2.42,10.33,8.42,10.33c3.82,0,5.76-2,6.94-4.86Z"/><path class="cls-1" d="M677.45,154.41l-3,8.12h-1.84l8.61-23.47h2.15l9,23.47h-2l-3-8.12Zm9.28-1.67c-2.61-7-4-10.33-4.42-11.9h0c-.57,1.77-2.07,5.93-4.19,11.9Z"/></svg> 
        </a>
     <!-- <h6 class="sidebar-heading">Menu</h6> -->
      <ul class="list-unstyled pl-2 pr-2">
            <li class="sidebar-list-item"><a class="sidebar-link text-muted active" href="#" data-bs-target="#atencion" role="button" aria-expanded="false" data-bs-toggle="collapse">
                      <i class="fas fa-user margen-icon" ></i>
                    <span class="sidebar-link-title">Atenci&oacute;n de pacientes</span></a>
                    <ul class="sidebar-menu list-unstyled collapse " id="atencion">
                        <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/RegistroPaciente">Nuevo Paciente</a></li>
                        <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/RegistroPaciente">Modificar Paciente</a></li>
                        <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Orden" >Nueva Orden</a></li>
                        <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/EdicionOrdenes" >Modificar Orden</a></li>
                      </ul>
                </li>

            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#cmsDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
              <i class="fas fa-flask margen-icon"></i>
                      
                    <span class="sidebar-link-title">Manejo de muestras </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="cmsDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Transporte de muestras</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/TomaMuestras">Toma de muestras</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/RecepcionMuestras">Recepci&oacute;n de muestras</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Recepci&oacute;n de muestras por lote</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Puntos de control</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Indicaciones">Indicaciones de toma de muestra</a></li>              </ul>
            </li>
            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#widgetsDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
                      <i class="fab fa-creative-commons-sampling-plus margen-icon"></i>
                    <span class="sidebar-link-title">Resultados </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="widgetsDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/CapturaResultados">Captura de resultados</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/RechazoMuestras">Rechazo de muestras</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Hojas de trabajo</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Ex&aacute;menes pendientes</a></li>

              </ul>
            </li>
            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#e-commerceDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
                      <i class="fas fa-file-medical-alt margen-icon"></i>
                    <span class="sidebar-link-title">Informes </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="e-commerceDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/InformeResultados">Impresi&oacute;n de informes</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Impresi&oacute;n de informes por lote</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Home">Resultados fuera de V. de referencia</a></li>
              </ul>
            </li>
            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#pagesDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse">   
                <i class="fas fa-capsules margen-icon"></i>
                    <span class="sidebar-link-title">Microbiología </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="pagesDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/ConfiguracionExamen">Microbiolog&iacute;a</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="${pageContext.request.contextPath}/Microbiologia">Resultados</a></li>
              </ul>
            </li>
            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#userDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
                      <i class="fas fa-cogs margen-icon" ></i>
                    <span class="sidebar-link-title">Configuración </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="userDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="login.html">Login page</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="register.html">Register</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="login-2.html">Login v.2 <span class="badge bg-info ms-2 text-decoration-none">New</span></a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="register-2.html">Register v.2 <span class="badge bg-info ms-2 text-decoration-none">New</span></a></li>
              </ul>
            </li>
            <li class="sidebar-list-item">
              <a class="sidebar-link text-muted " href="#" data-bs-target="#componentsDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
                      <i class="fas fa-bezier-curve margen-icon"></i>
                    <span class="sidebar-link-title">Entorno </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="componentsDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-cards.html">Cards</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-calendar.html">Calendar</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-gallery.html">Gallery</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-loading-buttons.html">Loading buttons</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-map.html">Maps</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-notifications.html">Notifications</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="components-preloader.html">Preloaders</a></li>
              </ul>
            </li>
            <li class="sidebar-list-item"><a class="sidebar-link text-muted " href="#" data-bs-target="#chartsDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"> 
                      <i class="fas fa-question-circle margen-icon"></i>
                    <span class="sidebar-link-title">Útiles </span></a>
              <ul class="sidebar-menu list-unstyled collapse " id="chartsDropdown">
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="charts.html">Charts</a></li>
                <li class="sidebar-list-item"><a class="sidebar-link text-muted" href="charts-gauge-sparkline.html">Gauge + Sparkline</a></li>
              </ul>
            </li>  
      </ul>

      <ul class="list-unstyled pl-2 pr-2 background-sidebar">
        <li class="sidebar-list-item boton-ayuda mt-6">
          <a class="sidebar-link text-muted " href="#" data-bs-target="#formsDropdown" role="button" aria-expanded="false" data-bs-toggle="collapse"  style="
    padding: 0.1rem 0.1rem!important;"> 
          <div class="icon icon-lg shadow me-3  background-red">
            <i class="fas fa-phone-volume margen-icon-ayuda"></i></div>
         <span class="sidebar-link-title text-gray-800">Soporte 24/7 </span></a>
        </li>
      </ul>
    </div>
  
  

