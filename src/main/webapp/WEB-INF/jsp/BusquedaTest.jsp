<div id="panelSuperior" class="card">
    <div class="card-header" id="registro-paciente-title">
       <div class="card-title d-flex justify-content-between">
            <h3 class="mb-0 btn-block text-left pl-0 col-10 icon-accordion" data-toggle="collapse"
            data-target="#collapseOne8" aria-expanded="true"
            aria-controls="collapseOne8">B&uacute;squeda de Test </h3>
            <div class="card-buttons col-2 d-flex justify-content-end">
                <a id="nuevoTest" class="navi-link"> <span
                    class='symbol symbol-30 symbol-circle '> <span
                        id="circuloAgregarTest"
                        class='symbol-label bg-hover-blue  hoverIcon' href="#"
                        data-toggle="tooltip" title="Nuevo test"> <i
                            id="iAgregarTest" class='fas fa-plus  text-blue'></i>
                    </span>
                </span>
                </a> <a id="btnLimpiarFiltro" class="navi-link"> <span
                    class="symbol symbol-30 symbol-circle "> <span
                        id="circuloLimpiarFiltro"
                        class="symbol-label bg-hover-blue  hoverIcon" href="#"
                        data-toggle="tooltip" title="Limpiar"> <i
                            id="iLimpiarFiltro" class="la la-broom icon-xl text-blue"></i>
                    </span>
                </span> <span class="navi-text"></span>
                </a>
            </div>
        </div>
    </div>
    <div id="collapseOne8"
    aria-labelledby="registro-paciente-title"
    data-parent="#registro-paciente-main"
        class="container-content">
        <div class="card-body d-flex">
            <!-- FORMULARIO FILTRO -->
            <div class="col">
                <div class=" row">
                    <label class="col-form-label text-left col-4 ">C&oacute;digo</label>
                    <div class="col-7">
                        <input id="txtCodigoFiltro" class="form-control" name="" placeholder="" type="text"  />
                    </div>
                </div>
                <div class=" row">
                    <label class="col-form-label text-left col-4 ">Nombre</label>
                    <div class="col-7">
                        <input id="txtNombreTestFiltro" class="form-control" name="" placeholder=""  type="text" />
                    </div>
                </div>
                <div class=" row">
                    <label class="col-form-label text-left col-4 ">Secci&oacute;n</label>
                    <div class="col-7">
                        <select id="selectSeccionFiltro" class="form-control selectpicker" data-live-search="true">
                                 
                        </select>
                    </div>
                </div>
                <div class=" row">
                    <label class="col-form-label text-left col-4 ">Examen</label>
                    <div class="col-7">
                        <select id="selectExamenFiltro" class="form-control selectpicker" data-live-search="true">
                                 
                        </select>
                    </div>
                </div>
                
                <div class=" row">
                    <div class="col-4">
                        <label class="checkbox checkbox-primary">
                            <input id="chkMostrarActivos" type="checkbox"/> Mostrar solo activos
                            <span></span>
                        </label>
                    </div>
                </div>
                <a id="btnBuscarFiltro" class="btn btn-blue-primary btn-lg font-weight-bold mt-4 mr-2"><i class="la la-search"></i>Buscar</a>
            </div>

            <!-- FIN FORMULARIO FILTRO -->
            <!-- TABLA FILTRO -->
            <div class="col ">
            	<div class="table-container mb-3">
	                <table id="tableFiltro" class="table table-hover table-striped">
	                    <thead>
	                        <tr id="trHeader">
	                            <th scope="col">#</th>
	                            <th id="thIdentificador" scope="col">C&oacute;digo</th>
	                            <th id="thNombre" scope="col">Nombre del Examen</th>
	                        </tr>
	                    </thead>
	                    <tbody id="tbodyFiltro">
	
	                    </tbody>
	                </table>
	                <label id="lblErrorFiltro" class="textError text-red ocultar">No se encontraron test con los datos provistos.</label>
                </div>
                <label id="lblTotalFiltro" class="ocultar" style="">Tests encontrados: <span id="totalFiltro">0</span></label>
          	  </div>
            
            
            <!-- FIN TABLA FILTRO -->
        </div>
    </div>
</div>