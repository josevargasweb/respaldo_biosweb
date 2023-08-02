
//CREADOR DE TABLA A EXCEL
        		/*AGREGAR ESTAS LINEAS AL JSP
        		<script src="<%=request.getContextPath()%>/resources/js/common/creadorExcell.js"></script>
  				<script  src="<%=request.getContextPath()%>/resources/js/portalEstadistica/xlsx.mini.js"></script>*/

//Nombre excel = a nombre del archivo del EXCEL
function agregarExcelUtiles(nombreExcel) {
	//TABLA DEBE TENER ID DE tb
	var table = document.querySelector("#tb");
	var sheet = XLSX.utils.table_to_sheet(table); // Convertir un objeto de tabla en un objeto de hoja
	
		openDownloadDialogUtiles(sheet2blobUtiles(sheet), nombreExcel + '.xlsx'); // Nombre del archivo
}

function sheet2blobUtiles(sheet, sheetName) {
	sheetName = sheetName || 'sheet1';
	var workbook = {
		SheetNames: [sheetName],
		Sheets: {}
	};
	workbook.Sheets[sheetName] = sheet; // Generar elementos de configuración de Excel

	var wopts = {
		bookType: 'xlsx', // El tipo de archivo que se generará
		bookSST: false, // Ya sea para generar una tabla de cadenas compartidas, la explicación oficial es que si activa la velocidad de generación, disminuirá, pero tiene una mejor compatibilidad en dispositivos IOS inferiores
		type: 'binary'
	};
	var wbout = XLSX.write(workbook, wopts);
	var blob = new Blob([s2ab(wbout)], {
		type: "application/octet-stream"
	}); // Cadena a ArrayBuffer

	function s2ab(s) {
		var buf = new ArrayBuffer(s.length);
		var view = new Uint8Array(buf);
		for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
		return buf;
	}
	return blob;
}




function openDownloadDialogUtiles(url, saveName) {
	if (typeof url == 'object' && url instanceof Blob) {
		url = URL.createObjectURL(url);
	}
	var aLink = document.createElement('a');
	aLink.href = url;
	aLink.download = saveName || '';
	var event;
	if (window.MouseEvent) event = new MouseEvent('click');
	else {
		event = document.createEvent('MouseEvents');
		event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	}
	aLink.dispatchEvent(event);
}