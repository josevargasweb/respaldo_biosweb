const idUsuarioSession = $('#idUsuarioSession').val();

$.ajax({
    type: "get",
    url: getctx + "/api/permisos/usuario/" + idUsuarioSession,
    datatype: "json",
    async: false,
    success: function (json) {
        $("#accordion-items").empty();

        json.dato.forEach(modulo=>{
            let url = (modulo.url !== null) ? getctx + "/" + modulo.url : "#";
            //URLs de módulo de pacientes
            if (modulo.cbmIdbioslismodulo === 2){
                url += "?origen=nuevo";
            }
            if (modulo.cbmIdbioslismodulo === 3){
                url += "?origen=antiguo";
            }
            const str = modulo.cbmnombremodulo.toLowerCase();
            const nommodulo = str.replaceAll(/(?:^|\s)\S{4,}/g, word => word[0].toUpperCase() + word.slice(1));
            
            if (modulo.cbmprimernivel > 0) {
                if (modulo.cbmsegundonivel === 0 && modulo.cbmtercernivel === 0){
                    $("#accordion-items")
                            .append('<div class="card">\n\
                                        <div class="card-header" id="item-'+modulo.cbmidbioslismodulo+'">\n\
                                           <h2 class="mb-0">\n\
                                               <button class="btn btn-link btn-block text-left btn-main arrow-menu collapsed" type="button" data-toggle="collapse" data-target="#item-'+modulo.cbmidbioslismodulo+'-contenido" aria-expanded="false" aria-controls="#item-'+modulo.cbmIdbioslismodulo+'-contenido">\n\
                                                   <div><i class=""></i>\n\
                                                       <span class="ml-4 ">'+nommodulo+'</span></div>\n\
                                               </button>\n\
                                           </h2>\n\
                                        </div>\n\
                                        <div id="item-'+modulo.cbmidbioslismodulo+'-contenido" class="collapse item-nivel-1" aria-labelledby="atencion-pacientes" data-parent="#item-'+modulo.cbmidbioslismodulo+'">\n\
                                           <div class="card-body">\n\
                                               <ul class="menu-subnav" id="menu-subnav-'+modulo.cbmprimernivel+'"></ul>\n\
                                           </div>\n\
                                       </div>\n\
                                    </div>');
                }
                if (modulo.cbmsegundonivel > 0){
                    if (modulo.cbmtercernivel === 0){
                        $("#menu-subnav-"+modulo.cbmprimernivel)
                                .append('<li class="menu-item" data-toggle="collapse" data-target="#subitem-'+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel+'" aria-haspopup="true">\n\
                                            <a href="'+url+'" class="menu-link">\n\
                                                <i class="menu-bullet menu-bullet-dot"><span></span></i>\n\
                                                <span class="menu-text">'+nommodulo+'</span>\n\
                                            </a>\n\
                                            <ul id="subitem-'+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel+'" class="subitem ocultar"></ul>\n\
                                         </li>');
                        
                    } else {
                        $("#subitem-"+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel).siblings().addClass("icon-1")
                        $("#subitem-"+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel).removeClass("ocultar");
                        $("#subitem-"+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel).addClass("collapse");
                        $("#subitem-"+modulo.cbmprimernivel+'-'+modulo.cbmsegundonivel)
                                .append('<li class="sub-menu-item" aria-haspopup="true">\n\
                                    <a href="'+url+'" class="sub-menu-link">\n\
                                        <i class="menu-bullet menu-bullet-dot"><span></span></i>\n\
                                        <span class="menu-text">'+nommodulo+'</span>\n\
                                    </a></li>');
                    }
                }
            }
        });
    },
    error: function (err) {
        console.log(err.responseText);
        handlerMessageError("Error en el menú");
    }
});



$(function() {
    
    /* funciones menu */
    jQuery.fn.clickOutside = function (callback) {
        var $me = this;
        $(document).mouseup(function (e) {
            if (!$me.is(e.target) && $me.has(e.target).length === 0) {
                callback.apply($me);
            }
        });
    };


    $('#menu-lateral-contenido,#menu-nube').clickOutside(function () {
        $('#menu-lateral-contenido').collapse('hide');
    });

    $(document).ready(function () {

        $('#menu-lateral-contenido').on('show.bs.collapse', function () {
            $("#menu-lateral").removeClass("menu-lateral-hide");
            
        });
        $('#menu-lateral-contenido').on('hide.bs.collapse', function () {
            $("#menu-lateral").addClass("menu-lateral-hide");
        });
      
        
    
        //obtiene el sector donde se realizo el click y luego le asigna alguna clase o color
        $(".btn-main").click(function () {
            if ($(this).attr('aria-expanded')) {
                $(this).parent().parent().addClass("active-card-header");
                $(this).parent().parent().parent().siblings().children(".card-header").removeClass("active-card-header");
                $(this).parent().parent().parent().siblings().children(".item-nivel-1").collapse('hide');
                // $(this).parent().parent().parent().siblings().children(".card-header").removeClass("active-card-header");
            }
        });

        $(".menu-link").click(function() {
            $(this).addClass("active-card-header");
            $(this).parent().siblings().find(".menu-link").removeClass("active-card-header");
            console.log( $(this).parent().siblings().children(".subitem"));
            $(this).parent().siblings().children(".subitem").collapse('hide');
        });

    });

 
});