<div id="modalAntecedentesPac" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog">
	<div class="modal-content">
            <div class="modal-body">
                <h4 id="tituloAntecendetesPaciente" class="text-center">Antecedentes Paciente</h4>
                <h5 class="text-center">Orden <label id="nOrdenAntecedentes"></label></h5>
                <div  class="col-12 mt-3">
                    <form class="d-flex flex-wrap justify-content-center align-items-center">
                        <div id="formAntecedentes"></div>
                        <!--
			<div id="divAntPacEdad"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Edad</label> 
                                                <input id="antecedentesPacienteEdad"
                                                        type="text" class="form-control" autocomplete="off"
                                                        placeholder="" style="width: 50%" disabled />
                                        </div>
                        </div>
                        <div id="divAntPacPeso"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-4 pl-0 pr-0">Peso</label> 
                                                <input id="antecedentesPacientePeso"
                                                        type="text" class="form-control mr-1" autocomplete="off"
                                                        placeholder="" style="width: 50%" disabled /> <label> kg</label>
                                        </div>
                        </div>
                        <div id="divAntPacEstatura"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Estatura</label>
                                                <input
                                                   id="antecedentesPacienteEstatura" type="text"
                                                   class="form-control mr-1" autocomplete="off" placeholder=""
                                                   style="width: 50%" disabled /> <label>m</label>
                                        </div>
                        </div>
                        <div id="divAntPacDiuresis"
                                class="col-6  ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Diuresis</label>
                                                <input
                                                        id="antecedentesPacienteDiuresis" type="text"
                                                        class="form-control mr-1" autocomplete="off" placeholder=""
                                                        style="width: 50%" disabled /> <label>ml</label>
                                        </div>
                        </div>
                        <div id="divAntPacTemperatura"
                                class="col-6  ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Temperatura</label> 
                                                <input
                                                        id="antecedentesPacienteTemperatura" type="text"
                                                        class="form-control mr-1" autocomplete="off" placeholder=""
                                                        style="width: 50%" disabled /> <label>&ordm;C</label>
                                        </div>
                        </div>
                        <div id="divAntPacFi02"
                                class="col-6  ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">FiO&#x2082;</label>
                                                <input id="antecedentesPacienteFiO2"
                                                        type="text" class="form-control mr-1" autocomplete="off"
                                                        placeholder="" style="width: 50%" disabled /> <label>%</label>
                                        </div>
                        </div>
                        <div id="divAntPacFUR"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                  <div class="row">
                                          <label class="col-5 pl-0 pr-0" for="antecedentesPacienteFUR">F.U.R.</label> <input
                                                  id="antecedentesPacienteFUR" type="text" class="form-control "
                                                  autocomplete="off" placeholder="" style="width: 50%" disabled />
                                  </div>
                        </div>
                        <div id="divAntPacGestante"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Gestante</label>
                                                <input
                                                        id="antecedentesPacienteGestante" type="text"
                                                        class="form-control mr-1" autocomplete="off" placeholder=""
                                                        style="width: 30%" disabled /> <label class="ml-1">Semanas</label>
                                        </div>
                        </div>
                        <div id="divAntPacMedicacion"
                                class="col-6  ocultarTodoModalAntecedentes mb-2">
                                <div class="row">
                                        <label class="col-5 pl-0 pr-0">Medicaci&oacute;n</label> <input
                                        id="antecedentesPacienteMedicacion" type="text"
                                        class="form-control " autocomplete="off" placeholder=""
                                        style="width: 50%" disabled />
                                </div>
                        </div>
                        <div id="divAntPacUltimaDosis"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">&Uacute;ltima dosis</label> <input
                                                        id="antecedentesPacienteUltimaDosis" type="text"
                                                        class="form-control  " autocomplete="off" placeholder=""
                                                        style="width: 50%" disabled />
                                        </div>
                        </div>
                        <div id="divAntPacSexo"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Sexo</label> <input
                                                        id="antecedentesPacienteSexo" type="text"
                                                        class="form-control  " autocomplete="off" placeholder=""
                                                        style="width: 50%" disabled />
                                        </div>
                        </div>
                        <div id="divAntPacAfroamericano"
                                class="col-6 ocultarTodoModalAntecedentes mb-2">
                                        <div class="row">
                                                <label class="col-5 pl-0 pr-0">Afroamericano</label> <input
                                                        id="antecedentesPacienteAfro" type="text"
                                                        class="form-control  " autocomplete="off" placeholder=""
                                                        style="width: 50%" disabled />
                                        </div>
                        </div>
                        -->
                        <div id="sinAntecedentes"
                                class="ocultar col-12 pl-0 pr-0 ">
                                <b>No requiere antecedentes</b>
                        </div>
                
                <!--<input id="nOrdenAntecedentes" name="" type="hidden"
                        class="form-control ml-22 " autocomplete="off" placeholder=""
                        style="visibility: hidden" />-->
                <input id="listAntecedentes"
                        type="hidden" />
                        <div class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between">
                                <button id="guardarAntecedentes" 
                                type="submit" class="btn btn-blue-primary btn-lg font-weight-bold">Guardar</button>
                                <button type="button" class="btn btn-blue-primary btn-lg font-weight-bold ml-2"
                                data-dismiss="modal">Cerrar</button>
                        </div>
                </form>
                </div>
            </div>
	</div>
    </div>
</div>