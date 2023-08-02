const intervalMs = 60000; //1 minuto
const minsExpiraSesion = 25;
var idleTime = 0; //tiempo de inactividad
//esta variable es para que no afecte la var idleTime cuando se haga un movimiento con mouse o se presione una tecla cuando se activa la alerta de expiración de sesión (true)
var alerta = false; 
$(document).ready(function () {
    $("#lblIdleTime").text("0");
    // Se incrementa el contador de idleTime cada intervalo de tiempo (definido en un la variable intervalMs) 
    var idleInterval = setInterval(timerIncrement, intervalMs); // 1 minuto //3600000

    // Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
        if (alerta === false){
            idleTime = 0;
            $("#lblIdleTime").text("0");
        }
    });
    $(this).keypress(function (e) {
        if (alerta === false){
            idleTime = 0;
            $("#lblIdleTime").text("0");
        }
    });
    
});

function timerIncrement() {
    idleTime = idleTime + 1;
    $("#lblIdleTime").text(idleTime);
   
    //cuando queda un minuto para que la sesión expire
   if (idleTime === minsExpiraSesion - 1){	
        alerta = true;
        $("#alertExpiraSesion").modal("show");
    }
    //cuando ya se cumplió el tiempo máximo de la sesión (definido en la const minsExpiraSesion)
    if (idleTime >= minsExpiraSesion) {
        $("#alertExpiraSesion").modal("hide");
        //$("#alertLogout").modal("show");
        alert("Su sesión ha expirado");
        destroySesion();
    }
}

//se realiza el logout una vez que se cumple el tiempo de expiración de sesión
function destroySesion(){
    $.ajax({
        type: "post",
        url: getctx + "/Logout",
        data: "sesionExpired=1",
        success: function () {
            window.location.href = getctx + "/Login";
        }
    });
}

$("#btnMantenerSesion").click(function(){
    idleTime = 0;
    alerta = false;
});

$("#btnCerrarSesion").click(function(){
    destroySesion();
});