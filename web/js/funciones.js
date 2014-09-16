
//Cuando termina de cargar la web añade eventos a los links existentes.
window.onload=onLoadWeb;
var debug=false;


//Tareas a realizar cuando se termina de cargar la web
function onLoadWeb()
{
    ocultar("cargando");
    //Funcion utilizada para añadir eventos
    var addEvent = function(){
      if (window.addEventListener) {
        return function(el, type, fn) {
          el.addEventListener(type, fn, false);
        };
      } else if (window.attachEvent) {
        return function(el, type, fn) {
          var f = function() {
            fn.call(el, window.event);
          };
          el.attachEvent('on' + type, f);
        };
      }
    }();

    if(debug)
        console.debug('Enlazando links sin gestion javascript con control de cargando.');
    //Añade el control onclick a todos los links de la web
    var links = document.getElementsByTagName('a');    
    var link;
    for (var i=0;i<links.length;i++)
        {
            link=links[i];
            //Para no añadir el control para aquellos links que ya tienen asociadas otras funciones javascript, como los mensajes.
            //Normalmente el href de un link que trabaja con javascript tiene algun caracter #
            if (link.href.indexOf('#')==-1)
            {
                addEvent(link, 'click', function(e) {
                     if(debug)
                        console.debug('Click');
                      mostrar("cargando");
                });
            }
        }
}

//Selecciona en la lista a que pais pertenece el usuario/grupo
function seleccionarPais(pais)
{    
    var select = document.getElementById('pais');
    var imagenBandera= document.getElementById('bandera');

    if(pais==null)
    {        
        imagenBandera.src="css/images/banderas/"+select.value.toLowerCase()+".png";
        imagenBandera.alt=select.value;
    }
    else
    {
        imagenBandera.src="css/images/banderas/"+pais+".png";
        imagenBandera.alt=select.value;
        var options = select.childNodes;

        for (var i=0;i<options.length;i++)
        {
            if (options.item(i).value==pais ||options.item(i).value==pais.toUpperCase())
            {
                options.item(i).selected='true';
                 if (debug)
                    console.debug('Seleccionando pais:'+pais);
            }
        }
    }
}

//Actualiza el estado de un mensaje de no leido a leido mediante peticion AJAX
function leerMensaje(idMensaje)
{
    //Peticion AJAX para cambiar el estado del mensaje
    var xhrArgs = {
        url:"leerMensajeAJ.servlet",
        content:{idMensaje:idMensaje},
        handleAs: "text",
        timeout:3000,
        load: function(data) {
            if(data!="ok"){
                dojo.byId('cuerpo'+idMensaje).innerHTML = "[ERROR]No se ha podido mostrar el mensaje.";
            }
        },
        error: function(error) {
            dojo.byId('cuerpo'+idMensaje).innerHTML = "[ERROR]No se ha podido mostrar el mensaje.";
        }
    }

    var deferred = dojo.xhrPost(xhrArgs);
    mostrar('cuerpo'+idMensaje);
}

function ocultar(objeto)
{
    if(debug)
        console.debug("Ocultar:"+objeto);
    var elemento=document.getElementById(objeto);
    elemento.style.display='none';
    elemento.style.visibility='hidden';
}

function mostrar(objeto)
{
    if(debug)
        console.debug("Mostrar:"+objeto);
    var elemento=document.getElementById(objeto);
    elemento.style.display='block';
    elemento.style.visibility='visible';    
}

function validarFecha(){
    var errores=document.getElementById("errorFecha");
    errores.style.visibility='hidden';
    var dia = document.getElementById("dia").value;
    var mes = document.getElementById("mes").value;
    var anio = document.getElementById("anio").value;

    if (dia<1||dia>31||mes<1||mes>12||anio<2009||anio>2100 || (mes==2 && dia>28) ||
        ((mes==2||mes==4||mes==6||mes==12)&& dia>30 ))
    {
        errores.style.display='block';
        errores.style.visibility='visible';
        return false;
    }
    else
        return true
}

function soloNumeros(evt)
{
    var nav4 = window.Event ? true : false;
    // Backspace = 8, Enter = 13, ‘0′ = 48, ‘9′ = 57, ‘.’ = 46
    var key = nav4 ? evt.which : evt.keyCode;
    return (key <= 13 || (key >= 48 && key <= 57) || key == 46);
}

function desactivar(elemento)
{
    elemento.disabled=true;
}

function controlImagen()
{
    var file=document.getElementById("imagen");
    if (file.disabled==true)
        file.disabled=false;
    else
        file.disabled=true;
}

function comprobarContrasenas()
{    
    var password1=document.getElementById("passwordNueva1").value;
    var password2=document.getElementById("passwordNueva2").value;

    if(password1!=password2)
        {
            var errores=document.getElementById("errorPass");
            errores.style.display='block';
            errores.style.visibility='visible';            
            return false;
        }
    return true;
}


function validarEmail()
{
    var email=document.getElementById("email").value;
    
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
    {     
        return true;
    } else
    {
        var errores=document.getElementById("errorEmail");
        errores.style.display='block';
        errores.style.visibility='visible';
        return false;
    }
}
