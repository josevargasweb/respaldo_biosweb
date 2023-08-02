<div id="ModalAntecedentesPaciente" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <h4 id="tituloAntecendetesPaciente">Antecedentes Paciente:  </h4>

                <div class="col-12 row mt-15" >
                    <div class="col-6" id="formAntecedentes1">
                    </div>
                </div>
            </div>
            <input id="nOrdenAntecedentes" name="" type="text"  class="form-control ml-22 " autocomplete="off" placeholder="" style="visibility:hidden "/>
            <ul id="idAntecedentes" style="visibility:hidden ">
            </ul>
            <div class="modal-footer">
                <button id="guardarAntecedentes" onclick="guardarAntecedentes()" type="button" class="btn btn-primary">Guardar</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>