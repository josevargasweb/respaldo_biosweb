
$(document).ready(function (){
const getctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    $('#showPassword').click(function () {
        if ($('#password').attr('type') === "password"){
            $('#password').attr('type','text');
            $('.icon1').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
        } else {
            $('#password').attr('type','password');
            $('.icon1').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
        }
    });
      $('#login').submit(function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Get the form data
    var formData = {
      username: $('#txtUsuario').val(),
      password: $('#password').val()
    };

    // Send the AJAX request
    $.ajax({
      type: 'POST',
      url: getctx+'/api/login',
      data: JSON.stringify(formData),
      contentType: 'application/json',
      success: function(response) {
		if(response.status == 200){
			window.location.replace(getctx+"/Home");	
		}
		
		if(response.status == 203){
			window.location.replace(getctx+"/CambioPassword");	
		}
		handlerMessageWarning(response.dato)
        
      },
      error: function(xhr, status, error) {
		handlerMessageError(response.dato)
      }
    });
  });
});



