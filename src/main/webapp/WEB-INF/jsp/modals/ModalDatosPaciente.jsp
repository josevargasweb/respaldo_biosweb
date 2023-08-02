<div id="ModalDatosPaciente" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <h4 id="tituloPacienteModal">Datos Paciente:  </h4>

                <div class="col-12 row mt-15">
                    <div class="col-12">
                        <div id="" class="col-12 row ">
                            <label>Tipo de Atenci�n:</label> 
                            <input disabled id="TipoAntencionModal" name="" type="text" class="form-control ml-5  " autocomplete="off" placeholder="" />

                        </div>
                        <div id="" class="col-12 row mt-5 ">
                            <label>Localizaci�n:</label> 
                            <input disabled id="LocalizacionModal" name="" type="text" class="form-control ml-5  " autocomplete="off" placeholder="" />

                        </div>
                        <div id="" class="col-12 row mt-5 ">
                            <label>Observaci�n:</label> 
                            <input disabled id="ObservacionModal" name="" type="text" class="form-control ml-5 " autocomplete="off" placeholder="" />

                        </div>
                        <div id="" class="col-12 row mt-5 ">
                            <label>Patolog�as:</label> 
                            <table id="PatologiasModal" class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Patolog&iacute;a</th>
                                        <th scope="col">Observaci�n</th>
                                    </tr>
                                </thead>
                                <tbody id="PatologiasModalBody">

                                </tbody>
                            </table>

                        </div>

                    </div>

                </div>
            </div>
            <input name="" type="text"  class="form-control ml-22 " autocomplete="off" placeholder="" style="visibility:hidden "/>
            <ul style="visibility:hidden ">
            </ul>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>