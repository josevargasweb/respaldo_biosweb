<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!--begin: Wizard-->
                <div class="wizard wizard-3" id="kt_wizard_v3" data-wizard-state="step-first"
                    data-wizard-clickable="false">
                    <!--begin: Wizard Body-->
                    <div class="card card-custom mt-5">
                        <!--
                        <div class="card-header">
                            <div class="card-title">
                                <h3 class="card-label">Orden</h3>
                            </div>
                        </div>
                        -->
                        <div class="card-body p-0">
                            <div class="row justify-content-center pt-0 pb-5">
                                <div class="wizard-nav col-12">
                                    <div class="wizard-steps px-8 py-8 px-lg-15 py-lg-3">
                                        <!--begin::Wizard Step 1 Nav  data-wizard-state="current"-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>1.</span>Recepción de Muestra
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 1 Nav-->
                                        <!--begin::Wizard Step 2 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>2.</span>Identificación de Muestra
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 2 Nav-->
                                        <!--begin::Wizard Step 3 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>3.</span>Siembra
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 3 Nav-->
                                        <!--begin::Wizard Step 4 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>4.</span>Incubación
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 4 Nav-->
                                        <!--begin::Wizard Step 5 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>5.</span>Revisión de Placas
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 5 Nav-->
                                        <!--begin::Wizard Step 6 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>6.</span>Pruebas
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 6 Nav-->
                                        <!--begin::Wizard Step 7 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>7.</span>Identificación de Microorganismo
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 7 Nav-->
                                        <!--begin::Wizard Step 8 Nav-->
                                        <div class="wizard-step" data-wizard-type="step">
                                            <div class="wizard-label">
                                                <h3 class="wizard-title">
                                                    <span>8.</span>Sensibilidad
                                                </h3>
                                                <div class="wizard-bar"></div>
                                            </div>
                                        </div>
                                        <!--end::Wizard Step 8 Nav-->
                                    </div>
                                </div>
                                <div class="col-xxl-12 pt-5" id="idTasksWizardContent" hidden>

                                    <!--begin: Wizard Form-->

                                    <form method="post" class="form" id="kt_form">
                                        <!--begin: Wizard Step 1-->
                                        <!-- *************************************** Datos del Paciente ***************************************************** -->
                                        <jsp:include page="TasksStep1.jsp" />
                                        <!-- *************************************** Datos de la  Orden ***************************************************** -->
                                        <!--end: Wizard Step 1-->
                                        <!--begin: Wizard Step 2-->
                                        <jsp:include page="TasksStep2.jsp" />
                                        <!--end: Wizard Step 2-->
                                        <!-- *************************************** Seleccion de ex&aacute;menes ***************************************************** -->
                                        <!--begin: Wizard Step 3-->
                                        <jsp:include page="TasksStep3.jsp" />
                                        <!--end: Wizard Step 3-->
                                        <!-- *************************************** Confirmacion ***************************************************** -->
                                        <!--begin: Wizard Step 4-->
                                        <jsp:include page="TasksStep4.jsp" />
                                        <!--end: Wizard Step 4-->
                                        <!--begin: Wizard Step 5-->
                                        <jsp:include page="TasksStep5.jsp" />
                                        <!--end: Wizard Step 5-->
                                        <!--begin: Wizard Step 6-->
                                        <jsp:include page="TasksStep6.jsp" />
                                        <!--end: Wizard Step 6-->
                                        <!--begin: Wizard Step 7-->
                                        <jsp:include page="TasksStep7.jsp" />
                                        <!--end: Wizard Step 7-->
                                        <!--begin: Wizard Step 8-->
                                        <jsp:include page="TasksStep8.jsp" />
                                        <!--begin: Wizard Actions-->
                                        <div class="d-flex justify-content-between border-top mt-5 pt-10">
                                            <div class="mr-2">
                                                <button id="antButton"
                                                    class="btn btn-light-primary font-weight-bold px-9 py-4 ml-5"
                                                    data-wizard-type="action-prev">Anterior</button>
                                            </div>
                                            <div>
                                                <button id="saveStepButton" class="btn btn-primary font-weight-bold  px-9 py-4 mr-5"
                                                    data-wizard-type="action-submit" type="button">Guardar</button>
                                                <button id="sgtButton"
                                                    class="btn btn-primary font-weight-bold px-9 py-4 mr-5"
                                                    data-wizard-type="action-next" type="button">Siguiente</button>
                                            </div>
                                        </div>
                                        <!--end: Wizard Actions-->
                                    </form>
                                    <!--end: Wizard Form-->
                                </div>
                            </div>
                            <!--end: Wizard Body-->
                        </div>
                    </div>
                </div>
                <!--end: Wizard-->
                <!-- FIN FORMULARIO PACIENTE -->