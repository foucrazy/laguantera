//Codigo JavaScript desarrollado por Felix Gonzalez en 2008


String.prototype.tokenize = tokenize;

//Funcion encargada de analizar los puntos de una ruta que se deben marcara la hora de obtener los detalles y modificaciones.
function tokenize(codigoIdioma)
  {     
     var input = dojo.byId("listaPuntos").value;
     var array = input.split("|");
 
        for (var j=0; j<array.length; j++)
        {
               var inputs = document.getElementById("puntosParaSelecionar").getElementsByTagName("input");
               for (var i=0;i<inputs.length;i++)
               {
                   
                    var idiomaInput = inputs[i].parentNode.getElementsByTagName("span")[0].innerHTML;
                    var idiomaCheck = idiomaInput.substring(idiomaInput.length-5,idiomaInput.length-3);                                                              
                    
                    if((inputs[i].value==array[j])&&(idiomaCheck == codigoIdioma))
                    {
                        inputs[i].checked = "checked";
                    }               
               }
        }        
  }  
  
//Funcion encargada de comprobar que se ha seleccionado algun elemento de los select.  
function comprobarSelect(nombreSelect)
{
   var select = document.getElementById(nombreSelect);
   if (select.value=="-1")
   {
        alert("Le falta seleccionar un elemento de la lista.");
        ocultar();
        return false;
   }
   
   return true;
}
  
//Funcion encargada de preparar los datos del envio de carga masiva de puntos.
//Primero envia los datos por AJAX y cuando termine se mandan los datos a un action para 
//procesar la peticion final.
function submitCargaMasiva()
{          
     document.getElementById("falsoSubmit").disabled="true";        
     dojo.xhrGet({
                url:'upload',
                handleAs:"text",
                content:{
                        archivos:' '
                },
                load: function(response, ioArgs) 
                {                     

                    var valorXML = document.getElementById("xmlUpload").value.split("\\");
                    var valorZIP = document.getElementById("zipUpload").value.split("\\");
                    document.getElementById("ficheroXML").value=valorXML[valorXML.length-1];
                    document.getElementById("ficheroZIP").value=valorZIP[valorZIP.length-1];                
                    //Al terminar de escribir los ficheros mandamos los datos en modo texto al action.
                    document.getElementById("submitCargaMasiva").click();                
                  return response; 
                },
                error: function(response, ioArgs) 
                {
                  console.error("HTTP status code: ", ioArgs.xhr.status); 
                  alert(response);
                  return response;
                  }                        
        });            
}

//Funcion utilizada para realizar las peticiones de estadisticas en funcion de la ciudad y
//estadistico seleccionado, mediante AJAX.
function pedirEstadisticas(img)
{
    if (img.src!="/Cicerone/css/images/verGris.png")
    {
        img.src="/Cicerone/css/images/verGris.png";
        var selectCiudad = document.getElementById("ciudad");
        var codCiudad = selectCiudad.value;
        var selectEstadistico = document.getElementById("estadistico");
        var codEstadistico = selectEstadistico.value;

        dojo.xhrGet({
                url:"verEstadisticas.do",
                handleAs:"text",
                content:{
                        codCiudad: codCiudad,
                        codEstadistico: codEstadistico
                },
                load: function(response) 
                {                        
                    var ul = dojo.byId("ulEstadisticos");                
                    //Eliminamos los hijos para evitar superponer varias peticiones
                    while (ul.hasChildNodes())
                    {
                        ul.removeChild(ul.lastChild);    
                    }
                    if (response=="ERROR")
                    {
                        alert('Ocurrio un problema consultando las estadisticas solicitadas, el error se puede ver detallado en el log.');
                    }
                    else if (response!="")
                    {
                        var nombres =response.split("|");

                           for (var i=0;i<nombres.length;i++)
                           {
                                var li = document.createElement('li');
                                li.innerHTML=i+1+".- "+nombres[i];
                                li.style.display='block';                        
                                li.style.background='#f'+i+'b'+i+ i+'b';
                                ul.appendChild(li);                        
                           }                
                     }
                     else //No hay estadisticas
                     {
                        var li = document.createElement('li');
                        li.innerHTML="No exiten datos";
                        li.style.display='block';                        
                        li.style.background='#F2B13B';
                        ul.appendChild(li);                       
                     }

                  img.src="/Cicerone/css/images/ver.png";
                  return response; 
                },
                error: function(response, ioArgs) 
                {
                  console.error("HTTP status code: ", ioArgs.xhr.status); 
                  alert(response);
                  return response;
                  }                        
        });      
    }

}

//Comprueba mediante expresiones regulares la validez de un correo electronico
function comprobarEmail(input)
{
    var correo = input.value;
    var filter=/^[A-Za-z][A-Za-z0-9_.\-]*@[A-Za-z0-9_\-]+\.[A-Za-z0-9_.]+[A-za-z]$/;
    
    if (correo.length == 0 ) return true;
    if (filter.test(correo))
        return true;
    else
        alert("Ingrese una direccion de correo valida");
        input.focus();
        return false;
}

//Funcion que comprueba que todo este correcto en el alta de un punto, que todos los select esten seleccionados, que no existan ficheros sin subir y que la extension sea valida.
function comprobarDatosAltaPunto()
{
    if (analizarCampos() && comprobarSelect('selectIdioma') && comprobarSelect('selectTipo') && comprobarSelect('selectCiudad') && avisarFicherosNoSubidos())
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Funcion que comprueba que todo este correcto en el alta de un punto en otro idioma, que todos los select esten seleccionados, que no existan ficheros sin subir y que la extension sea valida.
function comprobarDatosAltaIdiomaPunto()
{
    if (analizarCampos() && comprobarTipo() && comprobarSelect('selectIdioma') && comprobarSelect('selectTipo') && avisarFicherosNoSubidos())
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Funcion que comprueba que todo este correcto en la modificacion de un punto, que todos los select esten seleccionados, que no existan ficheros sin subir y que la extension sea valida.
function comprobarDatosModificacionPunto()
{
    if (comprobarSelect('selectTipo') && avisarFicherosNoSubidos())
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Funcion que comprueba que todo este correcto en el alta de una ruta
function comprobarDatosAltaRuta()
{
    if (analizarCampos() && comprobarSelect('selectIdioma'))
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Funcion que comprueba que todo este correcto en el alta de un evento
function comprobarDatosAltaEvento()
{
    if (analizarCampos() && comprobarSelect('selectIdioma'))
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Funcion que comprueba que todo este correcto en el alta de un tipo de punto
function comprobarDatosAltaTipoPunto()
{
    if (analizarCampos() && comprobarSelect('selectIdioma'))
        {
            mostrar();
            return true;
        }
    else
        return false;    
}


//Funcion que comprueba que todo este correcto en el alta de un tipo de punto
function comprobarDatosAltaTipoPuntoIdioma()
{
    if (analizarCampos() && comprobarSelect('selectIdioma') && comprobarSelect('codigoTipo') &&  comprobarIdiomaTipoPunto())
        {
            mostrar();
            return true;
        }
    else
        return false;    
}

//Compruebas si has seleccionado un fichero en el alta de punto y no lo has subido, despues comprueba si idioma del punto y el del tipo son correctos.
function avisarFicherosNoSubidos()
{    
    var inputFichero = dojo.byId("fileToUpload");
    
    if (inputFichero.value!="")
    {
        if(!confirm('Has seleccionado un fichero que no has subido, si continuas no se incluira.'))return false         
        else return comprobarTipo();
    }
    else 
        return true;
}

// Esta función permitirá validar la fecha
// En el objeto text hacemos lo Siguiente
/*
   <input type='text' name=cajaFecha onChange='fechas(this); this.value=borrar'>
*/
function fechas(input)
{ 
   var caja = input.value;
   if (caja)
   {  
      borrar = caja;
      if ((caja.substr(2,1) == "/") && (caja.substr(5,1) == "/"))
      {      
         for (i=0; i<10; i++)
	     {	
            if (((caja.substr(i,1)<"0") || (caja.substr(i,1)>"9")) && (i != 2) && (i != 5))
			{
               borrar = '';
               break;  
			}  
         }
	     if (borrar)
	     {
                    a = caja.substr(6,4);
		    m = caja.substr(3,2);
		    d = caja.substr(0,2);
		    if((a < 2000) || (caja.length>10) || (m < 1) || (m > 12) || (d < 1) || (d > 31))
		       borrar = '';
		    else
		    {
		       if((a%4 != 0) && (m == 2) && (d > 28))	   
		          borrar = ''; // Año no viciesto y es febrero y el dia es mayor a 28
			   else	
			   {
		          if ((((m == 4) || (m == 6) || (m == 9) || (m==11)) && (d>30)) || ((m==2) && (d>29)))
			         borrar = '';	      				  	 
			   }  // else
		    } // fin else
         } // if (error)
      } // if ((caja.substr(2,1) == \"/\") && (caja.substr(5,1) == \"/\"))			    			
	  else
	     borrar = '';
	  if (borrar == '')
	     alert('Fecha erronea');
   } // if (caja)   
} // FUNCION

//Funcion encargada de habilitar o deshabilitar los campos de tipo File.
function controlVideo(evt, nombreInputVideo)
{    
    evt = (evt) ? evt : event;                    

    if (window.event)
      {                       
        var check = evt.srcElement;
      }
    else if (evt)
    {                       
       var check = evt.target;
    }
    
    var inputVideo = dojo.byId(nombreInputVideo);
    if (check.checked)    
        inputVideo.disabled = true;    
    else
        inputVideo.disabled = false;        
}

//Funcion encargada de revisar que no se tienen puntos seleccionados de un idioma y la 
//ruta que se esta dando de alta o modificando sea de otro.
function revisarPuntosSeleccionados()
{
    var lista = dojo.byId("listaPuntos");       
    if (lista.value != "")
    {
        alert ("Tienes puntos seleccionados correspondientes a otro idioma, revisalos o no se realizara el alta correctamente.")
        return false;
    }
    else     
        return comprobarSelect('selectIdioma');
}


//Funcion encargada de controlar cuando se marcan o desmarcan los checkes de puntos de una ruta para controlar los valores de los puntos
// seleccionados.
function listarPuntosSeleccionados(input)
{      
    var lista = dojo.byId("listaPuntos");           
    var idiomaInput = input.parentNode.getElementsByTagName("span")[0].innerHTML;
    var idiomaCheck = idiomaInput.substring(idiomaInput.length-5,idiomaInput.length-3);   
    var selectIdioma = dojo.byId("selectIdioma");
    
    if(input.checked)
    {
        if (selectIdioma.value == idiomaCheck)
           {
                if(lista.value == "")
                    lista.value += input.value;
                else
                    lista.value += "|"+input.value;
            }
         else 
            {
                alert("El idioma del punto no coincide con el de la ruta.")
                input.checked = false;
            }
    }
        
    else
        {
           lista.value = "";           
           var inputs = document.getElementById("puntosParaSelecionar").getElementsByTagName("input");
           for (var i=0;i<inputs.length;i++)
           {
                if((inputs[i].checked)&&(inputs[i]!=input))
                {
                    if(lista.value == "")
                        lista.value = inputs[i].value;
                    else
                        lista.value += "|"+inputs[i].value;
                }               
           }
        }    
}

function comprobarIdiomaTipoPunto()
{
    var selectTipo = dojo.byId("codigoTipo");
    var valueTipo = selectTipo.options[selectTipo.options.selectedIndex].text;
    var idiomaTipo = valueTipo.substring(valueTipo.length-3,valueTipo.length-1);
    var selectIdioma = dojo.byId("selectIdioma");  
        
    if(selectIdioma.value == idiomaTipo)
        {
            alert("El idioma seleccionado ya esta dado de alta.");            
            selectTipo.options.selectedIndex = 0;
            return false;
        }        
}

//Funcion encargada de comprobar que el idioma y el idioma del tipo de punto sean iguales
function comprobarTipo()
{
    var selectTipo = dojo.byId("selectTipo");
    var idiomaTipo = selectTipo.value.substring(selectTipo.value.length-2,selectTipo.value.length);
    
    var idioma = "";
    var selectIdioma = dojo.byId("selectIdioma");             
    
    if (selectIdioma == null )//Utilizado para el alta y para la modificacion que el select se sustituye por un input oculto
        selectIdioma = dojo.byId("codigoIdioma");
        
    idioma = selectIdioma.value;
        
    if(idioma != idiomaTipo)
        {
            alert("El idioma seleccionado no se corresponde con el idioma del tipo de punto, el cual esta anotado entre parentesis.");
            return false;
        }        
    return true;
}

//Hace invisible un div con un mensaje de realizando peticion XXXX 
function ocultar()
{
    //'altaCiudad' se llamaran asi todos los divs de este tipo
    var divCargando = document.getElementById('altaCiudad');
    divCargando.style.display = 'none';    
}

//Hace visible un div con un mensaje de realizando peticion XXXX 
function mostrar()
{
    //'altaCiudad' se llamaran asi todos los divs de este tipo
    var divCargando = document.getElementById('altaCiudad');
    divCargando.style.display = 'block';    
}

//Hace invisible la barra superior de carga, utilizado para 'volver atras' y evitar que siempre aparezca la barra cargando.
function ocultarBarra()
{    
    var divBarra = document.getElementById('loading');
    divBarra.style.display = 'none';    
}

//Muestra una barra de cargando en la parte superior de la web
function mostrarBarra(control)
{
    var divBarra = document.getElementById('loading');
    divBarra.style.display = 'block';            
}

//Comprueba que el fichero seleccionado tenga la extension pasada por parametro.
function comprobarExtensionDada(input,extensionProbar)
{
    
    var extension = (input.value.substring(input.value.lastIndexOf("."))).toLowerCase();
    if (input.value!="")
        {
            if(extension!=extensionProbar && extension!=extensionProbar.toUpperCase())
                {                                           
                    //input.value="";     //IE no permite esto en los input de tipo file, usamos un boton reset oculto 
                    document.getElementById("botonResetFicheros").click();                    
                    alert("Tipo de fichero incorrecto.");                                
                }
        }  
}

//Comprueba que la extension del fichero seleccionado se corresponda con una imagen jpg o un video wmv
function comprobarExtension(input)
{
    var extension = (input.value.substring(input.value.lastIndexOf("."))).toLowerCase();
    if (input.value!="")
        {
            if((extension!=".jpg" && extension!=".JPG")&&(extension!=".wmv" && extension!=".WMV"))
                {                       
                    //input.value="";     //IE no permite esto en los input de tipo file, usamos un boton reset oculto 
                    dojo.byId("botonResetFicheros").click();                    
                    alert("Debe de ser una imagen en formato JPG o un video en formato WMV");                                
                }
        }  
}

//Comprueba que la extension del fichero seleccionado se corresponda con una imagen jpg
function comprobarExtensionImagen(input)
{
    var extension = (input.value.substring(input.value.lastIndexOf("."))).toLowerCase();
    if (input.value!="")
        {
            if(extension!=".jpg" && extension!=".JPG")
                {                                
                    input.value="";                                
                    alert("Debe de ser una imagen en formato JPG");                                
                }
        }                        
}

//Comprueba que la extension del fichero seleccionado se corresponda con un video wmv
function comprobarExtensionVideo(input)
{
    var extension = (input.value.substring(input.value.lastIndexOf("."))).toLowerCase();
    if (input.value!="")
        {
            if(extension!=".wmv" && extension!=".WMV")
                {
                    input.value="";
                    alert("Debe de ser un video en formato WMV");                                
                }
        }                        
}

//Convierte los campos de latitudes y longitudes a float para evitar problemas posteriores
function parseDouble(evt)
{                                        
    evt = (evt) ? evt : event;                    

    if (window.event)
      {                       
        var campo = evt.srcElement;
      }
    else if (evt)
    {                       
       var campo = evt.target;
    }

    var valorOriginal = campo.value;                    
    var valorParseado = parseFloat(valorOriginal);

    if (valorParseado != valorOriginal && valorOriginal!= "")
    {
        campo.value = parseFloat(valorOriginal);
        alert ('Se ha formateado el campo al formato adecuado, revisa que sea correcto. Ej: 5.236987');
    }                                        
}

function analizarCampos()
{    
    var inputs = document.getElementsByTagName('input');
    var textoInput="";
    
    for (var i=0;i<inputs.length;i++)
    {
        textoInput = inputs[i].value;
        for (var j=0;j<textoInput.length;j++)
            {
                if((textoInput[j]=='\'')||(textoInput[j]=='\"')||(textoInput[j]=='#')||(textoInput[j]=='$')||(textoInput[j]=='|'))
                    {
                        alert("No se permite la utilizacion de \" , ' , # , $ ni | .");
                        return false;
                    }                                        
            }
    }
    
    inputs = document.getElementsByTagName('textarea');
    textoInput="";
    
    for (var i=0;i<inputs.length;i++)
    {
        textoInput = inputs[i].value;
        for (var j=0;j<textoInput.length;j++)
            {
                if((textoInput[j]=='\'')||(textoInput[j]=='\"')||(textoInput[j]=='#')||(textoInput[j]=='$')||(textoInput[j]=='|'))
                    {
                        alert("No se permite la utilizacion de \" , ' , # , $ ni | .");
                        return false;
                    }                                        
            }
    }    
    return true;
}

//Control de caracteres invalidos por ser utilizados como control de la aplicacion
function soloCaracteresValidos(evt) {                
    evt = (evt) ? evt : event;
    
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : 
        ((evt.which) ? evt.which : 0));
        
    if (charCode == 34 || charCode == 39 || charCode == 36 ||charCode == 35 || charCode == 124) {
        alert("No se permite la utilizacion de \" , ' , # , $ ni | .");
        return false;
    }            
        
    return true;
 }

//Limita la introduccion de solo numeros en los input
function soloNumeros(evt) {                
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : 
        ((evt.which) ? evt.which : 0));
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        alert("Solo se permiten numeros en este campo.");
        return false;
    }
    return true;
 }

//Comprueba si el valor introducido es un double
function esDouble(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : 
        ((evt.which) ? evt.which : 0));                
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
        alert("Solo se permiten numeros en formato decimal (Ej 4.598).");
        return false;
    }
    return true;
 }

//Comprueba que se introducen las letras validas como letra de latitud
function esLetraLatitud(input) {                                                                                                  
    var letraOriginal = document.getElementById(input).value;                

    if (letraOriginal!="")
    {
        if (letraOriginal=='N' || letraOriginal=='n' || letraOriginal=='S' || letraOriginal=='s') 
        {                    
            document.getElementById(input).value = letraOriginal.toUpperCase();  
            return true;                                
        }
        else
        {                    
            alert("La letra de la latitud es N o S");
            document.getElementById(input).value="";                    
            return false;    
        }                                                              
    }
 }

//Comprueba que se introducen las letras validas como letra de longitud
function esLetraLongitud(input) {
    var letraOriginal = document.getElementById(input).value;                

    if (letraOriginal!="")
    {
        if (letraOriginal=='E' || letraOriginal=='e' || letraOriginal=='W' || letraOriginal=='w') 
        {                    
            document.getElementById(input).value = letraOriginal.toUpperCase();  
            return true;                                
        }
        else
        {                    
            alert("La letra de la longitud es E o W");
            document.getElementById(input).value="";                    
            return false;    
        }  
    }
 }  

//Muestra div para la sustitucion de video en una ciudad
function mostrarSustituir(link)
{                                                     
    var divForm = link.parentNode.getElementsByTagName("div")[0];
    divForm.style.display = 'block';
}

//Funcion para pedir confirmacion de borrado de cualquier tipo de elemento.
function confirmaBorrado(evt)
{    
    if(!confirm('Se borrara el elemento seleccionado.'))return false 
    else mostrarBarra();
}
