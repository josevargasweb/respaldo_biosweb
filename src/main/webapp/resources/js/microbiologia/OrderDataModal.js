function showOrderDataModal(orderId) {
    $.getJSON("Microbiologia/api/getOrderData", { orderId: orderId })
        .done(function (order) {
            $("#idOrderDataModalName").val(order.patient.name);
            $("#idOrderDataModalGender").val(order.patient.gender);
            $("#idOrderDataModalAge").val(order.patient.age);
            $("#idOrderDataModalDateOfBirth").val(order.patient.dateOfBirth);
            $("#idOrderDataModalPhoneNumber").val(order.patient.phoneNumber);
            $("#idOrderDataModalAtentionType").val(order.patient.atentionType);
            $("#idOrderDataModalLocalization").val(order.patient.localization);
            $("#idOrderDataModalPatientObservation").val(order.patient.observation);
            $("#idOrderDataModalPathologies").val(order.patient.pathologies);

            $("#idOrderDataModalOrderId").val(order.order.id);
            $("#idOrderDataModalDate").val(order.order.date);
            $("#idOrderDataModalContract").val(order.order.contract);
            $("#idOrderDataModalPhysician").val(order.order.physician);
            $("#idOrderDataModalDiagnostic").val(order.order.diagnostic);
            $("#idOrderDataModalOrderObservation").val(order.order.observation);

            $("#idOrderDataModalPreviousOrders").val(order.previousOrders.join(", "));

        })
        .fail(function (jqxhr, textStatus, error) {
            var err = textStatus + ", " + error;
            console.log("Request Failed: " + err);
        });
}