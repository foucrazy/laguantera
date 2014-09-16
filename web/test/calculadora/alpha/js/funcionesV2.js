
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var initialLocation;
var map;

function initialize()
{
    var rendererOptions = {
        draggable: false
    };
    directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
    var myOptions = {
        zoom:3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("mapa"), myOptions);
    map.setCenter(new google.maps.LatLng(40.435241,-3.739014));//Madrid
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById("directionsPanel"));
    map.setCenter(new google.maps.LatLng(40.435241,-3.739014));//Madrid
}

function calcRoute()
{
    document.getElementById("directionsPanel").innerHTML="";
    $('#resultados').css("display","block");
    initialize();
    var start = document.getElementById("origen").value;
    var end = document.getElementById("destino").value;
    var request = {
        origin:start,
        destination:end,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
            var route = response.routes[0];
            var distance = route.legs[0].distance.value;
            document.getElementById("distancia").value=distance;
        }
    });
}

$(function(){
    $("#demoForm").formwizard({
        textNext:'Siguiente',
        textBack:'Anterior',
        textSubmit:'Calcular',
        disableUIStyles:true,
        formPluginEnabled: true,
        historyEnabled : true,
        validationEnabled: true,
        focusFirstInput : true,
        formOptions :{
            success: processJson,
            beforeSubmit: validate,
            dataType: 'json',
            resetForm: true
        },
        validationOptions : {
            rules: {
                origen: "required",
                destino: "required",
                pasajeros:"required",
                bultos:"required"
            },
            messages: {
                origen: "Escriba un origen",
                destino: "Escriba un destino",
                pasajeros:"Indique ocupantes",
                bultos:"Indique bultos"
            }
        }
    }
    );
});

function processJson(data) {
    
    $("#cargando").dialog("close");
    debugger;
    $('#pdf_fecha').val(data.fecha);
    $('#pdf_distancia').val(data.distancia);
    $('#pdf_origen').val(data.origen);
    $('#pdf_destino').val(data.destino);
    $('#pdf_pasajeros').val(data.pasajeros);
    $('#pdf_bultos').val(data.bultos);
    $('#pdf_tipoConduccion').val(data.tipoConduccion);
    $('#pdf_idayvuelta').val(data.idayvuelta);
    $('#pdf_consumo').val(data.consumo);
    $('#pdf_idCombustible').val(data.idCombustible);
    $('#pdf_nombreVehiculo').val(data.nombreVehiculo);
    $('#pdf_cilindrada').val(data.cilindrada);
    $('#pdf_peso').val(data.peso);
    $('#pdf_potencia').val(data.potencia);
    $('#pdf_litrosConsumidos').val(data.litrosConsumidos);
    $('#pdf_precioCombustible').val(data.precioCombustible);
    $('#pdf_costeTotal').val(data.costeTotal);
    $('#pdf_ultimaActualizacion').val(data.ultimaActualizacion);
    $('#pdf_rutasCalculadas').val(data.rutasCalculadas);

    document.getElementById("datos.origen").innerHTML=data.origen;
    document.getElementById("datos.destino").innerHTML=data.destino;
    document.getElementById("datos.pasajeros").innerHTML=data.pasajeros;
    document.getElementById("datos.bultos").innerHTML=data.bultos;
    document.getElementById("datos.precioCombustible").innerHTML=data.precioCombustible;
    document.getElementById("datos.consumo").innerHTML=data.consumo;
    document.getElementById("datos.costeTotal").innerHTML=data.costeTotal;
    document.getElementById("datos.fecha").innerHTML=data.fecha;
    document.getElementById("datos.rutasCalculadas").innerHTML=data.rutasCalculadas;
    document.getElementById("datos.ultimaActualizacion").innerHTML=data.ultimaActualizacion;

    var distancia=0;
    distancia=data.distancia;
    document.getElementById("datos.distancia").innerHTML=distancia/1000;

    $('#instrucciones').css("display","block");
    $('#informe').css("display","block");
    $('html, body').animate({scrollTop:400}, 'slow');
}

function validate(formData, jqForm, options) {

    var form = jqForm[0];

    if (!form.origen.value || !form.destino.value) {
        alert('Porfavor rellene los datos de origen y destino');
        return false;
    }
    else{
        if (form.idCombustible.value==-1){
            alert('Seleccione un tipo de combustible');
            return false;
        }

        if (!form.pasajeros.value){
            form.pasajeros.value=1;
        }
        if (!form.bultos.value){
            form.bultos.value=0;
        }
        if (!form.tipoConduccion.value){
            form.tipoConduccion.value=2;
        }
        if (!form.cilindrada.value){
            form.cilindrada.value=0;
        }
        if (!form.peso.value){
            form.peso.value=0;
        }
        if (!form.potencia.value){
            form.potencia.value=0;
        }
        if (!form.consumo.value){
            form.consumo.value=0;
        }
        if (!form.idayvuelta.value){
            form.idayvuelta.value=false;
        }
    }
    
    $("#cargando").dialog("open");
}

function verAvanzado(){
    $('#avanzado').css("display","block");
    $('#consumo').attr('disabled',true);
    $('#labelConsumo').css("text-decoration","line-through");
}

function verMensajeConsumo(){
    $('#avanzado').css("display","none");
    $('#mensajeConsumo').css("display","block");
}

function nuevaBusqueda(){
    $('#formBus').clearForm();
    $('#avanzado').css("display","none");
    $('#consumo').attr('disabled',false);
    $('#instrucciones').css("display","none");
    $('#informe').css("display","none");
    directionsDisplay.setMap(null);
}

function verDetallesRuta(){        
    var estado=$("#directionsPanel").css("display");
    
    if (estado=="block"){
        document.getElementById("botonDetalles").innerHTML="Ver detalles de la ruta";
    }else{
        document.getElementById("botonDetalles").innerHTML="Ocultar detalles de la ruta";
    }
    $('#directionsPanel').toggle( "explode", {}, 500 );
}

function controlarStep(){
    var estado=$("#demoForm").formwizard("state");

    if (estado.currentStep=="ruta"){
        var origen=document.getElementById("origen").value;
        var destino=document.getElementById("destino").value;
        if (origen!=null&&origen!=""&&destino!=null&&destino!=""){
            calcRoute();
        }
    }
}

$(function() {
    $( "#nombreVehiculo" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "/laguantera/ACVehiculos",
                dataType: "json",
                data: {
                    consulta: request.term,
                    combustible:document.getElementById("idCombustible").value
                },
                success: function( data ) {
                    response( $.map( data.vehiculos, function( item ){
                        return {
                            label: item.label,
                            value: item.value
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {                        
            document.getElementById("consumo").value=ui.item.value;
            document.getElementById("nombreVehiculo").value=ui.item.label;
            return false;
        }
    });

    $("#cargando").dialog({
            height: 200,
            modal: true,
            closeOnEscape: false,
            resizable: false,
            autoOpen: false,
            closeText: "[X]"
    });    
});