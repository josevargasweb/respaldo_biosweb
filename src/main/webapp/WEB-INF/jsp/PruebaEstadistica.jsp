<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Prueba Estadistica</title>
  
   <style>
        table {
            border-collapse: collapse;
            text-align: center;
            vertical-align: middle;
            width: 800px;
            font-size: 20px;
        }
        
        button {
            height: 30px;
            width: 100px;
            margin: 20px 20px;
            background: yellowgreen;
            border-radius: 10px;
            outline: none;
        }
        
        input {
            height: 30px;
            padding-left: 10px;
            margin: 10px;
        }
    </style>
</head>

<body>
    <div id="wrap" style="width:900px;margin:20px auto;">
        <h3>Estadistica</h3>
        <table id="tb" border="1" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</td>
                        <th>Edad</td>
                            <th>Cargo</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Juan</td>
                    <td>18</td>
                    <td>Ingeniero</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Pedro</td>
                    <td>88</td>
                    <td>Medico</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Ivan</td>
                    <td>81</td>
                    <td>Portero</td>
                </tr>
            </tbody>
        </table>

      
        <button id="btnEstadistica">Crear Excel</button>
         <button id="btnCancelar">Volver Sin Crearr</button>
    </div>
   
   
   
   
	<jsp:include page="common/Scripts.jsp"/>

<script
    src="<%=request.getContextPath()%>/resources/js/pruebasCristian/xlsx.mini.js"></script>
    <script
    src="<%=request.getContextPath()%>/resources/js/pruebasCristian/EstadisticaExcel.js"></script>
      <script
    src="<%=request.getContextPath()%>/resources/js/PruebasCristian/xlsx.core.min.js"></script>
  
   

</body>
</html>