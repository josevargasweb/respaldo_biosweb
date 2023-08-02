<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="modal fade" id="modOrderDataModal" tabindex="-1" role="dialog"
                    aria-labelledby="datosPacienteModal" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title" id="exampleModalLabel">Datos Paciente</h1>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <i aria-hidden="true" class="ki ki-close"></i>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col">
                                        <div class="row col-12 form-group">
                                            <label class="col-form-label text-left col-lg-4 "><b>Datos del
                                                    Paciente</b></label>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col-8">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-2 ">Nombre</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalName" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-6 ">Sexo</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalGender" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-6 ">Edad</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalAge" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4">Fecha de
                                                        Nacimiento</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalDateOfBirth" disabled type="text" />
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Tipo de
                                                        Atención</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalAtentionType" disabled type="text" />
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label
                                                        class="col-form-label text-left col-lg-4 ">Observación</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalPatientObservation" disabled
                                                            type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4">Teléfono</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalPhoneNumber" disabled type="text" />
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label
                                                        class="col-form-label text-left col-lg-4">Localización</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalLocalization" disabled type="text" />
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Patologías</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalPathologies" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row col-12 form-group">
                                            <label class="col-form-label text-left col-lg-4 "><b>Datos de la
                                                    Orden</b></label>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-6 ">Id Orden</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalOrderId" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4">Fecha</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalDate" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4 ">Convenio</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalContract" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-3">Medico</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalPhysician" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-4">Diagnóstico</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalDiagnostic" disabled type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <label class="col-form-label text-left col-lg-2">Observacion</label>
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalOrderObservation" disabled
                                                            type="text" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row col-12 form-group">
                                            <label class="col-form-label text-left col-lg-4 "><b>Órdenes
                                                    previas</b></label>
                                        </div>
                                        <div class="row col-12">
                                            <div class="col">
                                                <div class="form-group row">
                                                    <div class="col-lg pl-0">
                                                        <input class="form-control" placeholder=""
                                                            id="idOrderDataModalPreviousOrders" disabled type="text" />
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