<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1" />
<title>BIOS-LIS</title>
<link rel="icon" type="image/jpg"
	href="<%=request.getContextPath()%>/resources/img/gbios.png">
                    <jsp:include page="common/Styles_1.jsp" />
                
      <%  // agregado para probar tomar ip al conectarce al login *** %>   
              <%@ page import ="java.net.InetAddress"  %> 
               <%@ page import ="java.net.UnknownHostException"  %>         
          <%  try {
        	  String[] ipLocal = new String[2];
              InetAddress address = InetAddress.getLocalHost();
              // IP en formato String
              String paso = address.toString();
              ipLocal =paso.split("/");
 //        System.out.println(paso);
              
          } catch (UnknownHostException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } %>
         
   <%   //************************************************************ %>

</head>

<body class="login"> 
	<div class="flex-containe">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-12 pl-0 pr-0 d-flex justify-content-center">
				<!-- ********************************************************* INICIO PORTAL ***********************************************-->
                <div class="col-6">
                    <div class="card rounded-0 border-shadow-default">
                        <div id="panelRegistroPaciente ">
                            <div class="card-body no-gutters pl-0 pr-0">
                                <div class="col-12">
                                    <div class="col-12 pl-0 pr-0 mt-5 d-flex">
                                        <div class="col-12 d-flex justify-content-center mb-2">
                                            <img src="<%=request.getContextPath()%>/resources/img/Logo BiosLis Web_Login Inicio.png"  width="60%"/>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-5 mt-4 line-height-1">
                                        <h3 class="text-center mb-0 text-dark">Acceso de Usuario</h3>
                                        <span class="d-block text-center">Introduce tus credenciales</span>
                                    </div>
                                    <form id="login"  method="post" >
                                        
                                        <div class=" col-12 mb-4">
                                            
                                            <input id="txtUsuario" name="username"
                                                type="text" class="form-control " placeholder="Usuario"
                                                autocomplete="off"/>
                                        </div>
                                        <div class=" col-12 mb-5">
                                            
                                            <div class="input-group">
                                                <input id="password" type="password" name="password" class="form-control" required="" placeholder="Contraseña">
                                                <div class="input-group-append">
                                                    <button id="showPassword" class="form-control btn btn-primary" type="button"> <span class="fa fa-eye-slash icon1"></span> </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 d-flex justify-content-center">
                                            <button type="submit" class="btn btn-blue-login btn-normal font-weight-bold mr-2 pt-1 pb-1 w-100">
                                                Inicio Sesi&oacute;n
                                            </button>
                                        </div>
                                        <div class="col-12 mt-4 line-height-1">
                                            <span class="d-block text-center">BiosLIS web v. 1.1.3</span>
                                            <span class="d-block text-center">12/07/2023</span>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
			</div>
		</div>
	</div>
	</div>
  <!--begin::Global Theme Bundle(used by all pages)-->
  <script>var KTAppSettings = {"breakpoints": {"sm": 576, "md": 768, "lg": 992, "xl": 1200, "xxl": 1200}, "colors": {"theme": {"base": {"white": "#ffffff", "primary": "#6993FF", "secondary": "#E5EAEE", "success": "#1BC5BD", "info": "#8950FC", "warning": "#FFA800", "danger": "#F64E60", "light": "#F3F6F9", "dark": "#212121"}, "light": {"white": "#ffffff", "primary": "#E1E9FF", "secondary": "#ECF0F3", "success": "#C9F7F5", "info": "#EEE5FF", "warning": "#FFF4DE", "danger": "#FFE2E5", "light": "#F3F6F9", "dark": "#D6D6E0"}, "inverse": {"white": "#ffffff", "primary": "#ffffff", "secondary": "#212121", "success": "#ffffff", "info": "#ffffff", "warning": "#ffffff", "danger": "#ffffff", "light": "#464E5F", "dark": "#ffffff"}}, "gray": {"gray-100": "#F3F6F9", "gray-200": "#ECF0F3", "gray-300": "#E5EAEE", "gray-400": "#D6D6E0", "gray-500": "#B5B5C3", "gray-600": "#80808F", "gray-700": "#464E5F", "gray-800": "#1B283F", "gray-900": "#212121"}}, "font-family": "Poppins"};</script>
  <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/global/plugins.bundle.js"></script>
  <script src="<%=request.getContextPath()%>/resources/template/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
  <script src="<%=request.getContextPath()%>/resources/template/assets/js/scripts.bundle.js"></script>
  <!--end::Global Theme Bundle-->
  <script src="<%=request.getContextPath()%>/resources/js/Login.js"></script>   
   <script src="<%=request.getContextPath()%>/resources/js/common/utiles.js"></script>
  
  
</body>

</html>