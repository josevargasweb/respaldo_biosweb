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
</head>

<body>
    <div class="row menu-container">
        <div class="col-md-6">
                <jsp:include page="common/Menu_dinamico.jsp"/>
        </div>
        <div class="col-md-6">
                <jsp:include page="common/Header_1.jsp" />
        </div>
</div>
<jsp:include page="common/AlertaSession.jsp"/>
	<div class="container container-main-content  h-100 d-flex justify-content-center align-items-center">
		<div class="row d-flex justify-content-center">
			<div class="accordion col-5 pl-0 pr-0">
				<!-- ********************************************************* INICIO PORTAL ***********************************************-->
                <div class="card">
					<div id="panelRegistroPaciente ">
						<div class="card-body no-gutters">
								<div class="row">
                                    <div class="col-12">
                                        <div class="col">
                                            <div class="row">
                                                <div class="col-7 d-flex align-items-center justify-content-end">
                                                    <h3>Saludos, ${usuario.duNombres} ${usuario.duPrimerapellido}</h3>
                                                </div>
                                                
                                                <div class="col-5 d-flex justify-content-start">
                                                    <c:choose>
                                                        <c:when test="${base64Foto==null || base64Foto==''}">
                                                            <img class="rounded-circle img-home" src="${pageContext.request.contextPath}/resources/img/user-default.jpg">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img class="rounded-circle img-home" src="data:image/jpeg;base64, ${base64Foto}" >
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <!-- <span class="symbol symbol-30 symbol-circle">
                                                        <span id="" class="symbol-label" style="width:100%;height:100%;">
                                                            <i class="fas fa-user icon-xl  text-blue" style="font-size: 10rem !important;padding: 3rem;"></i>
                                                    </span> -->
                                                </div>
                                            </div>
                                                <div class="col-10 d-flex align-items-center justify-content-center">
                                                     <c:if test="${mensaje!=null}"><div class="alert alert-danger" role="alert">${mensaje}</div></c:if>
                                                </div>
                                            <div class="row h-100">
                                                <div class="col-12 pl-0 pr-0 mt-2 d-flex">
                                                    <div class="col-12 d-flex justify-content-center">
                                                        <img src="<%=request.getContextPath()%>/resources/img/Logo GrupoBios Web_Menu.png" width="60%" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-12 mb-3">
                                                    <h3 class="text-center">BiosLIS web, revolucionando la experiencia del Laboratorio</h3>
                                                </div>
                                            </div>
                                        </div>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
    <jsp:include page="common/Scripts.jsp"/> 
    <script src="<%=request.getContextPath()%>/resources/js/MenuHome.js"></script>
    
</body>
<script>
$(document).ready(function () {
	function ip_local()
	{
	 var ip = false;
	 window.RTCPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection || false;

	 if (window.RTCPeerConnection)
	 {
	  ip = [];
	  var pc = new RTCPeerConnection({iceServers:[]}), noop = function(){};
	  pc.createDataChannel('');
	  pc.createOffer(pc.setLocalDescription.bind(pc), noop);

	  pc.onicecandidate = function(event)
	  {
	   if (event && event.candidate && event.candidate.candidate)
	   {
	    var s = event.candidate.candidate.split('\n');
	    ip.push(s[0].split(' ')[4]);
	   }
	  }
	 }
console.log(ip)
	 return ip;
	}
	ip_local()
});
</script>
</html>