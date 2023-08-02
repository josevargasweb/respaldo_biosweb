<div id="ModalDatosPaciente" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body d-flex flex-wrap justify-content-center align-items-center">
                <h3 id="" class="text-center">Datos Paciente </h3>
                <div class="col-12 row line-height-1-5 mt-3">
                    <div class="col-4">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Rut</label>
                        </div>
                        <span class="" id="RutPaciente"></span>
                      </div>
                    <div class="col-8 strong-data">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Nombre Paciente</label>
                        </div>
                        <span class="" id="tituloPacienteModal"></span>
                    </div>
                    <div class="col-4 mt-4">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Tipo de Atenci&oacute;n</label>
                        </div>
                        <span class="" id="TipoAntencionModal"></span>
                      </div>
                    <div class="col-4 mt-4">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Procedencia</label>
                        </div>
                        <span class="" id="ProcedenciaModal"></span>
                    </div>
                    <div class="col-4 mt-4">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Servicio</label>
                        </div>
                        <span class="" id="ServicioModal"></span>
                    </div>
                    <div class="col-12 mt-4">
                        <div class="col-12 pl-0 pr-0">
                            <label class="text-label">Observaci&oacute;n</label>
                        </div>
                        <span class="" id="ObservacionModal"></span>
                      </div>
                    </div>
                    <div class="col-12 row">
                        <div id="" class="col-12 mt-4">
                            <label class="text-label">Patolog&iacute;as</label> 
                            <table id="PatologiasModal" class="table no-margin">
                                <thead id="PatologiasModalHead">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Patolog&iacute;a</th>
                                        <th scope="col">Observaci&oacute;n</th>
                                    </tr>
                                </thead>
                                <tbody id="PatologiasModalBody">

                                </tbody>
                            </table>
                        </div>
                </div>
                <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse">
                    <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 " data-dismiss="modal">Cerrar</button>
                </div>
            </div>
            <input name="" type="text"  class="form-control ml-22 " autocomplete="off" placeholder="" style="display:none "/>
            <ul style="display:none ">
            </ul>
        </div>
    </div>
</div>