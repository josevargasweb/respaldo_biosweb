<div id="ModalDatosOrden" class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-xl" style="max-width: 70%!important;">
        <div class="modal-content">
            <div class="modal-body">
                <h4 id="DatosOrdenModalTitulo">Datos Orden:  </h4>

                <div class="col-12 row mt-15">
                    <div class="col-12">
                        <div id="" class="col-12  ">
                            <label>Paciente:</label> 
                            <input disabled id="ModalDatosOrdenNombrePac" name="" type="text" class="form-control ml-5  " autocomplete="off" placeholder="" />

                        </div>
                        <div id="" class="col-12  mt-5 ">
                            <label>Diagn�stico:</label> 
                            <input disabled id="ModalDatosOrdenDiagnostico" name="" type="text" class="form-control ml-5  " autocomplete="off" placeholder="" />

                        </div>
                        <div id="" class="col-12  mt-5 ">
                            <label>Observaci�n:</label> 
                            <input disabled id="ModalDatosOrdenObs" name="" type="text" class="form-control ml-5  " autocomplete="off" placeholder="" />

                        </div>

                        <div id="ModalDatosOrdenTablaExamenes" class="col-12  mt-5 " style="overflow-x:auto;">
                            <table class='table-fixed table table-hover  table-separate table-head-custom table-checkable dataTable dtr-inline collapsed' id="tablaPrioridadAtencion3">

                                <thead id="">
                                    <tr>
                                        <td>Ex�men</td>
                                        <td>C�digo</td>
                                        <td>Centro</td>
                                        <td>Laboratorio</td>
                                        <td>Secci�n</td>
                                        <td>Muestra</td>
                                        <td>Contenedor</td>
                                        <td>Compartir Muestra</td>
                                        <td>Tests Opcionales</td>
                                        <td>Derivador</td>
                                    </tr>
                                </thead>
                                <tbody id="tablaOrden">


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