<div id="ModalDatosOrden" class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-body">
                <h4 class="text-center">Datos Orden </h4>

                <div class="col-12 mt-5">
                    <div class="row">
                        <div class="col-8 pr-0 line-height-1-5">
                            <div class="row">
                                <div class="col-4 strong-data">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">N Orden</label>
                                    </div>
                                    <span class="" id="DatosOrdenModalTitulo"></span>
                                </div>
                                <div class="col-4">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">Host</label>
                                    </div>
                                    <span class="" id="ModalDatosHost"></span>
                                </div>
                                <div class="col-4">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">Fecha</label>
                                    </div>
                                    <span class="" id="ModalDatosFecha"></span>
                                </div>
                                <div class="col-5 mt-4">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">Paciente</label>
                                    </div>
                                    <span class="" id="ModalDatosOrdenNombrePac"></span>
                                </div>
                                <div class="col-7 mt-4">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">Diagn&oacute;stico</label>
                                    </div>
                                    <span class="" id="ModalDatosOrdenDiagnostico"></span>
                                </div>
                                <div class="col-12 mt-4">
                                    <div class="col-12 pl-0 pr-0">
                                        <label class="text-label">Observaci&oacute;n</label>
                                    </div>
                                    <span class="" id="ModalDatosOrdenObs"></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-md-4 d-flex flex-column justify-content-start line-height-1-5">
                            <div class="mb-2 d-flex align-items-end justify-content-center">
                                <button id="btnDocumentosOrden" type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2">
                                    <img src="<%=request.getContextPath()%>/resources/img/view-files.png" width="30" style="filter: brightness(0) invert(1);" />
                                    <label for="" style="font-size:1rem;color:#fff !important;">Orden M&eacute;dica</label>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 pl-0 pr-0">
                        <div id="ModalDatosOrdenTablaExamenes" class="mt-3 col-12 pl-0 pr-0">
                            <table class='table table-hover table-striped nowrap display' id="tablaExamanesOrden" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>Examen</th>
                                        <th>C&oacute;digo</th>
                                        <th>Centro</th>
                                        <th>Laboratorio</th>
                                        <th>Secci&oacute;n</th>
                                        <th>Muestra</th>
                                        <th>Contenedor</th>
                                        <th>Compartir Muestra</th>
                                        <th>Derivador</th>
                                    </tr>
                                </thead>
                            </table>

                        </div>
                    </div>
                </div>
                <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                </div>
            </div>
            <input name="" type="text"  class="form-control ml-22 " autocomplete="off" placeholder="" style="display:none"/>
            <ul style="display:none">
            </ul>
            
        </div>
    </div>
</div>