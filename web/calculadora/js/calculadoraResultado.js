function enviarFormulario(tipo){

    switch(tipo){
        case '1':
            $('#datosResultado input[name="toPrinter"]').val("false");
            $('#datosResultado input[name="toMail"]').val("false");
            break;
        case '2':
            $('#datosResultado input[name="toPrinter"]').val("false");
            $('#datosResultado input[name="toMail"]').val("true");
            break;
       case '3':
            $('#datosResultado input[name="toPrinter"]').val("true");
            $('#datosResultado input[name="toMail"]').val("false");
            break;
    }

    $("#datosResultado").submit();
}

function envioCorreo(){
    $("#envioCorreo").dialog("open");
}

//Díalogo envio correcto
var cancel = function() {
    $("#envioCorreo").dialog("close");
}

var getResponse = function(){
    var email = $("#info").val();

    if(email != ""){
        var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
        if(emailRegex.test(email)){

            $('#datosResultado input[name="email"]').val(email);
            $('#datosResultado input[name="toPrinter"]').val("false");
            $('#datosResultado input[name="toMail"]').val("true");

            $.ajax(
                {
                    type:'POST', 
                    url: '/laguantera/Exportar', 
                    data:$('#datosResultado').serialize(), 
                    success: function(response) {
                        $("#envioCorreo").dialog("close");
                        
                        if(response == "email"){
                             $("#operacionCorrecta").dialog("open");
                        }else{
                             $("#operacionErronea").dialog("open");
                        }
                    },
                    error:function (xhr, ajaxOptions, thrownError){
                        $("#envioCorreo").dialog("close");
                        $("#operacionErronea").dialog("open");
                    }
                }
            );
            
        }else{
            $("#errorDialogo").children().remove();
            $("#errorDialogo").append("<p>Email no válido</p>");
            $("#errorDialogo").removeClass("Ocultar");
        }
    }else{
        $("#errorDialogo").children().remove();
        $("#errorDialogo").append("<p>Escriba un Email</p>");
        $("#errorDialogo").removeClass("Ocultar");
    }
}

var dialogOpts = {
    modal: true,
    buttons: {
    "Enviar": getResponse,
    "Cancelar": cancel
    },
    autoOpen: false
};

//Dialogo envio correcto
$("#operacionCorrecta").dialog({
        height: 200,
        modal: true,
        buttons: { 
            "Salir": function(){ 
                            $(this).dialog("close"); 
                        } 
            },
        closeOnEscape: false,
        resizable: false,
        autoOpen: false,
        closeText: ''
});

//Dialogo envio incorrecto
$("#operacionErronea").dialog({
        height: 200,
        modal: true,
        buttons: { 
            "Salir": function(){ 
                            $(this).dialog("close"); 
                        } 
            },
        closeOnEscape: false,
        resizable: false,
        autoOpen: false,
        closeText: ''
});

//Lanzamos los diferentes script una vez se haya cargado la página
$(document).ready(function () {
    
    //Google Maps
    var polyLineIda, polyLineVuelta;
    var origen = $("#origen").text().trim();
    var destino = $("#destino").text().trim();
    var idaYVuelta = $("#idayvuelta").text().trim();
    var directionsService = new google.maps.DirectionsService();
    var mapOptions = {
        center: new google.maps.LatLng(40.435241,-3.739014),//Madrid
        zoom:10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var requestIda = {
        origin:origen,
        destination:destino,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    var requestVuelta = {
        origin:destino,
        destination:origen,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    var rendererOptionsIda = {
        draggable: false,
        polylineOptions: {
            strokeColor: '#A5BFDD',
            strokeWeight: '8'
        }
    };
    var rendererOptionsVuelta = {
        draggable: false,
        polylineOptions: {
            strokeColor: '#FF0000',
            strokeWeight: '5'
        }
    };
    var map = new google.maps.Map($("#mapaGoogle").get(0), mapOptions);
    var directionsDisplayIda = new google.maps.DirectionsRenderer(rendererOptionsIda);    
    var directionsDisplayVuelta = new google.maps.DirectionsRenderer(rendererOptionsVuelta);
    
    directionsDisplayIda.setMap(map);
    directionsDisplayIda.setPanel($("#indicacionesIda").get(0));
   
    directionsService.route(requestIda, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplayIda.setDirections(response);
            
            polyLineIda = response.routes[0].overview_path;
            var encodeStringIda = google.maps.geometry.encoding.encodePath(polyLineIda);
            $('#datosResultado').append('<input type="hidden" name="encodeStringIda" value="'+encodeStringIda+'" />');
        }
    });
    
    if(idaYVuelta == "Ida y Vuelta"){
        directionsDisplayVuelta.setMap(map);
        directionsDisplayVuelta.setPanel($("#indicacionesVuelta").get(0));

        directionsService.route(requestVuelta, function(response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplayVuelta.setDirections(response);
                
                polyLineVuelta = response.routes[0].overview_path;
                var encodeStringVuelta = google.maps.geometry.encoding.encodePath(polyLineVuelta);
                $('#datosResultado').append('<input type="hidden" name="encodeStringVuelta" value="'+encodeStringVuelta+'" />');
            }
        });
    }
    
    $("#envioCorreo").dialog(dialogOpts);
    
});