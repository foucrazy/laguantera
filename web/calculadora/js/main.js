function ocultar(id){
    $('#'+id).css("display","none");
}

function mostrar(id){
    $('#'+id).css("display","block");
}

function deshabilitar(id){
    $('#'+id).attr("disabled",true);
}

function verMasOpciones(opcion){
    switch (opcion){
        case '1':
            $('#marcaVehiculo').addClass("Ignorar");
            $('#modeloVehiculo').addClass("Ignorar");
            $('#consumo').addClass("Ignorar");
            $('#cilindrada').removeClass("Ignorar");
            $('#peso').removeClass("Ignorar");
            $('#potencia').removeClass("Ignorar");
            ocultar('divVehiculo');
            ocultar('divConsumo');
            ocultar('noConozcoConsumo');
            mostrar('consumoEstandar');
            break;
       case '2':
            $('#cilindrada').addClass("Ignorar");
            $('#peso').addClass("Ignorar");
            $('#potencia').addClass("Ignorar");
            ocultar('consumoEstandar');
            mostrar('desconoceDatos')
            break;
       default:
           alert("Opción incorrecta");
           
    }
}

function envioFormulario(){
    if($("#formularioPlanificaTuViaje").formwizard("state").currentStep == "vehiculo"){
         $("#calculandoViaje").dialog("open");
    }
}

function calcularDistancia(){
    $("#calculandoDistancia").dialog("open");

    var distanceMatrixService = new google.maps.DistanceMatrixService();

    var origen = $("#origen").val();
    var destino = $("#destino").val();
    var idaYVuelta = $("#idayvuelta").is(':checked');
    var distancia;

    var request = {
        origins:[origen],
        destinations:[destino],
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };

    distanceMatrixService.getDistanceMatrix(request, function (response, status) {

        if (status == google.maps.DistanceMatrixStatus.OK) {
            distancia = response.rows[0].elements[0].distance.value;
            $("#duracionIda").val(response.rows[0].elements[0].duration.value);

            if(!idaYVuelta){
                $("#distancia").val(distancia);
                $("#calculandoDistancia").dialog("close");
            }
        }
    })

    if(idaYVuelta){
        var requestVuelta = {
            origins:[destino],
            destinations:[origen],
            travelMode: google.maps.DirectionsTravelMode.DRIVING
        };

        distanceMatrixService.getDistanceMatrix(requestVuelta, function (response, status) {
            if (status == google.maps.DistanceMatrixStatus.OK) {
                distancia += response.rows[0].elements[0].distance.value;
                $("#duracionVuelta").val(response.rows[0].elements[0].duration.value);
                $("#distancia").val(distancia);
                $("#calculandoDistancia").dialog("close");
            }
        })
    }
}

$(document).ready(function () {
       //Cargando el dialogo
       $("#calculandoDistancia").dialog({
                height: 200,
                modal: true,
                closeOnEscape: false,
                resizable: false,
                autoOpen: false,
                closeText: ''
        });
        
        $("#calculandoViaje").dialog({
                height: 200,
                modal: true,
                closeOnEscape: false,
                resizable: false,
                autoOpen: false,
                closeText: ''
        });
        
	//Iniciamos el widget del formulario
	$("#formularioPlanificaTuViaje").formwizard({
		textNext:'Siguiente',
		textBack:'Anterior',
		textSubmit:'Calcular',
		formPluginEnabled: false,
		validationEnabled: true,
		focusFirstInput : true,
                disableUIStyles : true,
		validationOptions : {
                        ignore: ".Ignorar",
			rules: {
				origen: "required",
				destino: "required",
				pasajeros:{
                                          required: true,
                                          digits: true
                                        },
				bultos:{
                                          required: true,
                                          digits: true
                                        },
                                marcaVehiculo:"required",
                                modeloVehiculo:"required",
                                consumo:{
                                          required: true,
                                          number: true
                                        },
                                cilindrada:{
                                          required: true,
                                          digits: true
                                        },
                                peso:{
                                          required: true,
                                          number: true
                                        },
                                potencia:{
                                          required: true,
                                          digits: true
                                        }
			},
			messages: {
				origen: "Escriba un origen",
				destino: "Escriba un destino",
				pasajeros:{
                                          required: "Indique ocupantes",
                                          digits: "Tiene que ser un numero"
                                        },
				bultos:{
                                          required: "Indique bultos",
                                          digits: "Tiene que ser un numero"
                                        },
                                marcaVehiculo:"Indique la marca de su vehículo",
                                modeloVehiculo:"Indique el modelo de su vehículo",
                                consumo:{
                                          required: "Indique el consumo",
                                          number: "Tiene que ser un numero"
                                        },
                                cilindrada:{
                                          required: "Indique la cilindrada",
                                          digits: "Tiene que ser un numero"
                                        },
                                peso:{
                                          required: "Indique el peso",
                                          number: "Tiene que ser un numero"
                                        },
                                potencia:{
                                          required: "Indique la potencia",
                                          digits: "Tiene que ser un numero"
                                        }
			}
		}
	});
        
        $("#formularioPlanificaTuViaje").bind("step_shown", function(event, data){
            //Cambiamos las clases de los pasos
            $("#"+data.previousStep+"Paso").switchClass("PasoSeleccionado","PasoDeshabilatado");
            $("#"+data.currentStep+"Paso").switchClass("PasoDeshabilatado","PasoSeleccionado");
           
           //Calulamos las distancia
           if(data.isLastStep){
               calcularDistancia();
           }
        });
	
	 $( "#marcaVehiculo" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "/laguantera/marcaVehiculo.ser",
                    dataType: "json",
                    data: {
                        consulta: request.term
                    },
                    success: function( data ) {
                        response( $.map( data.marcas, function( item ){
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
                $("#marcaVehiculo").val(ui.item.label);
                $("#idMarcaVehiculo").val(ui.item.value);
                return false;
            }
        });
        
        $( "#modeloVehiculo" ).autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "/laguantera/modeloVehiculo.ser",
                    dataType: "json",
                    data: {
                        consulta: request.term,
                        combustible:$("#idCombustible option:selected").val(),
                        marca:$("#idMarcaVehiculo").val()
                    },
                    success: function( data ) {
                        response( $.map( data.modelos, function( item ){
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
                $("#consumo").val(ui.item.value);
                $("#modeloVehiculo").val(ui.item.label);
                return false;
            }
        });
        
        $("#formularioRegistro").validate({ 
            rules: {
                emailRegistro: {
                    required: true, 
                    email: true 
                },
                reEmailRegistro: {
                    required: true, 
                    email: true,
                    equalTo: "#emailRegistro"
                },
                passwordRegistro: "required",
                rePasswordRegistro: {
                    required: true, 
                    equalTo: "#passwordRegistro"
                },
                aliasRegistro: "required",
                codigoPostalRegistro: {
                    required: true,
                    digits: true,
                    range: [01000, 52999]
                }
            }, 
            messages: { 
                emailRegistro: {
                    required: "Escriba un Email", 
                    email: "Email erroneo" 
                },
                reEmailRegistro: {
                    required: "Vuelva a escribir el Email", 
                    email: "Email erroneo",
                    equalTo: "El Email y el Email de confirmación son distintos"
                },
                passwordRegistro: "Escriba una contraseña",
                rePasswordRegistro: {
                    required: "Escriba de nuevo la contraseña", 
                    equalTo: "La contraseña y la contraseña de confirmación son distintos"
                },
                aliasRegistro: "Escribe un alias",
                codigoPostalRegistro: {
                    required: "Escribe un código postal",
                    digits: "El código postal ha de ser un número",
                    range: "Código Portal erroneo"
                }
            } 
        }); 
});


