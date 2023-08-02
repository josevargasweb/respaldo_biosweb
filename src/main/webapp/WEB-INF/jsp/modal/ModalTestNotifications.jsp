<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="TestCanvasNotifications">
	<input type="hidden" id="nOrden" /> <input type="hidden" id="examenId" />
	<input type="hidden" id="testId" /> <input type="hidden"
		id="idFichaTestNotificacion" />
	<div class="row">
		<div class="col">
			<div class="row col-12 solo-texto">
				<h6>Notificaciones</h6>
				<div class='col-12 pl-0 pr-0 mb-2 form-row line-height-1'>

					<div class="col-6 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Resultado</label> <span
							id="idTestCanvasNotificationsResult"></span>
					</div>
					<div class="col-6 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Resultado
							Crítico</label> <span id="idTestCanvasNotificationsCriticalResult"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Médico</label> <span
							id="idTestCanvasNotificationsPhysician"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Teléfono</label> <span
							id="idTestCanvasNotificationsPhysicianPhone"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">e-mail</label> <span
							id="idTestCanvasNotificationsPhysicianEmail"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Paciente</label> <span
							id="idTestCanvasNotificationsPatient"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">Teléfono</label> <span
							id="idTestCanvasNotificationsPatientPhone"></span>
					</div>
					<div class="col-4 form-group mb-0">
						<label class="col-form-label pb-0 col-12 pl-0">e-mail</label> <span
							id="idTestCanvasNotificationsPatientEmail"></span>
					</div>
				</div>
				<!-- <h6 class="mt-4">Mensaje de notificación</h6> -->
			</div>
			<div class="row col-12 m-0 p-0">
				<ul class="nav nav-tabs col-12" id="myTabNotify" role="tablist">
					<li class="nav-item condition" role="presentation"
						id="presentacion">
						<button class="nav-link active tab-notificacion" id="condicion" data-toggle="tab"
							data-target="#condicion-sub" type="button" role="tab"
							aria-controls="condicion-sub" aria-selected="true">Notificaci&oacute;n
							1</button>
					</li>
					<li class="nav-item" id="nav-item-agregar">
						<button type="button" class="nav-link formConditional"
							id="agregar-tab" onclick="agregarTabs(2)">+</button>
					</li>
				</ul>
				<div class="tab-content border col-12" id="myTabNotifyContent">
					<div class="tab-pane fade col-12 p-0 show active"
						id="condicion-sub" role="tabpanel" aria-labelledby="condicion">
						<div class="container-fluid row">
							<div class="col-4 pl-0">
								<div class="col-12 p-0 h-100">
									<label
										class="col-form-label pb-0 col-12 pl-0 h-100 d-flex align-items-end pb-1">E-Mail
										de Notificación</label>
								</div>
							</div>
							<div class="col-8 p-1 form-row">
								<div
									class="form-group col-md-6 mb-0 testCanvasNotificationsContainer ocultar">
									<label class="col-form-label pb-0 col-12 pl-0">Fecha
										notificaci&oacute;n</label>
									<div class="col-lg pl-0">
										<input class="form-control" placeholder=""
											id="idTestCanvasNotificationsDate" type="text" disabled />
									</div>
								</div>
								<div
									class="form-group col-md-6 mb-0 testCanvasNotificationsContainer ocultar">
									<label class="col-form-label pb-0 col-12 pl-0">Usuario
										notifica</label>
									<div class="col-lg pl-0">
										<input class="form-control" placeholder=""
											id="idTestCanvasNotificationsUser" type="text" disabled />
									</div>
								</div>
							</div>
							<div class="col-4">
								<div class="form-group row">
									<div class="col-12 pl-0">
										<textarea class="form-control"
											id="idTestCanvasNotificationsMesage" type="text" rows=9></textarea>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label text-left col-lg-3 pl-0">Enviar
										a</label>
									<div class="col-lg pl-0">
										<input class="form-control classTestCanvasNotificationsSendTo" placeholder=""
											id="idTestCanvasNotificationsSendTo" type="text" />
									</div>
								</div>
							</div>
							<div class="col-5">
								<div class="form-group row">
									<label class="col-form-label pb-0 col-12 pl-0">Personal
										notificado *</label>
									<div class="col-lg pl-0">
										<select id="selectNotificacionTipoReceptor"
											class="form-control classNotificacionTipoReceptor">
										</select>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label pb-0 col-12 pl-0">Nombre *</label>
									<div class="col-lg pl-0">
										<input class="form-control classTestCanvasNotificationsName" placeholder=""
											id="idTestCanvasNotificationsName" type="text" />
									</div>
								</div>
								<div class="form-group row">
									<label class="col-form-label pb-0 col-12 pl-0">V&iacute;a
										de comunicaci&oacute;n</label>
									<div class="col-lg pl-0">
										<select id="idTestCanvasNotificationsComunicationChannel"
											class="form-control">
										</select>
									</div>
								</div>

							</div>
							<div class="col-3">
								<div class="form-group row">
									<label class="col-form-label pb-0 col-12 pl-0">Nota</label>
									<div class="col-lg pl-0">
										<textarea class="form-control"
											id="idTestCanvasNotificationsNote" type="text" rows=4></textarea>
									</div>
								</div>
								<div class="form-group row">
								<input
										id="idTestCanvasNotificationsConfirmsReception"
										type="checkbox" class="">
										<span></span>
									<label class="checkbox checkbox-primary ml-2"> Notificaci&oacute;n efectiva
										
									</label>
								</div>
								<!-- <div class="form-group row">
                                    <label
                                    class="col-form-label pb-0 col-12 pl-0">Intentos de comunicación</label>
                                    <div class="col-lg pl-0">
                                        <input class="form-control" placeholder="" id="idTestCanvasNotificationsCommunicationAttempts" type="number" min="1" max="99"/>
                                    </div>
                                </div> -->
							</div>
							<div class="col-12 p-0">
								<div class="form-row">
									<div class="col-4 mb-3 d-flex align-items-center">
										<button type="button"
											class="btn btn-blue-primary testSendMailButton btn-lg font-weight-bold mr-2">
											<i class="fas fa-paper-plane" style="color: #fff;"></i>
											Enviar e-mail
										</button>
									</div>
									<div class="col mb-3">
										<div class="form-row h-100">
											<div class="col">
												<div class="form-row h-100">
													<div class="col-3 d-flex align-items-center">
														<div class="chkErrorContainer ocultar">
															<label class="checkbox checkbox-primary m-0"> <input
																type="checkbox" class='chckTestCanvasNotificationsError' id="chckTestCanvasNotificationsError">Error
																<span></span>
															</label>
														</div>
													</div>
													<div class="col typeErrorContainer ocultar">
														<label class="col-form-label pb-0 col-12 pl-0">Tipo
															error</label> <select class='form-control' id="idTestCanvasNotificationsTypeError"
															class="form-control">
															<option value="" disabled selected>Seleccione</option>
															<option value="A">Anulado</option>
															<option value="E">Error</option>
														</select>
													</div>
												</div>
											</div>
											<div class="col typeErrorContainer ocultar">
												<label class="col-form-label pb-0 col-12 pl-0">Nota</label>
												<input class="form-control" placeholder=""
													id="idTestCanvasNotificationsErrorNote" type="text" />

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row mt-4">
        <div class="col">
          <label class="checkbox checkbox-primary m-0 notiExamen"> <input
            type="checkbox" id="chckTestCanvasNotificationsExamen">Inclu&iacute;r test cr&iacute;ticos del examen
            <span></span>
          </label>
        </div>
        <div class="col">
        <input
            type="checkbox" id="chckTestCanvasNotificationsOrden">
          <label class="checkbox checkbox-primary m-0 notiOrden"> Inclu&iacute;r test cr&iacute;ticos de la orden
          </label>
        </div>

				<div class="col">

					<button type="button" id="testNotificationSaveButton"
						class="btn btn-blue-primary btn-lg font-weight-bold mr-2">
						<i class="far fa-save" aria-hidden="true"></i> Guardar
					</button>
				</div>
				<div class="col d-flex justify-content-end">
					<button id="btnSalirModalNotificacion" type="button"
						class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2 "
						data-dismiss="modal">Cerrar</button>
				</div>
			</div>
		</div>
	</div>
</div>