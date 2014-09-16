
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var initialLocation;
var map;

function initialize()
{
	  var rendererOptions = {
		draggable: true
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

	// Try W3C Geolocation method (Preferred)
	if(navigator.geolocation)
	{
		navigator.geolocation.getCurrentPosition(function(position)
		{
		  initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
		  map.setCenter(initialLocation);
		  map.setZoom(12);
		}, function() {
		  handleNoGeolocation();
		});
	}
	else if (google.gears)
	{
		// Try Google Gears Geolocation
		var geo = google.gears.factory.create('beta.geolocation');
		geo.getCurrentPosition(function(position)
		{
		  initialLocation = new google.maps.LatLng(position.latitude,position.longitude);
		  map.setCenter(initialLocation);
		  map.setZoom(12);
		}, function() {
		  handleNoGeolocation();
		});
	} else {
		handleNoGeolocation();
	}
}

function handleNoGeolocation()
{
  map.setCenter(new google.maps.LatLng(40.435241,-3.739014));//Madrid
}

function calcRoute()
{
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

$(document).ready(function() { 
	$('#formBus').ajaxForm({ 
		dataType:  'json', 		 
		success:   processJson,
		beforeSubmit: validate				
	}); 
}); 

function processJson(data) { 		
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
		
		if (!form.distancia.value){
			alert("distancia nula");						
		}			
				
		if (!form.pasajeros.value){
			form.pasajeros.value=0;
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
