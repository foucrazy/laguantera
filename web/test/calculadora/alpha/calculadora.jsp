<!--
#666D75 Gris
#F2A81D Naranja
#62BF06 Verde oscuro
#6FD904 Verde claro
#3E5964 Azulado
-->

<html>
    <head>

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/i18n/jquery-ui-i18n.min.js" type="text/javascript"></script>

        <script type="text/javascript" src="test/calculadora/alpha/js/js/jquery.form.js"></script>
        <script type="text/javascript" src="test/calculadora/alpha/js/js/jquery.validate.js"></script>
        <script type="text/javascript" src="test/calculadora/alpha/js/js/bbq.js"></script>
        <script type="text/javascript" src="test/calculadora/alpha/js/js/jquery.form.wizard.js"></script>

        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&region=es&language=es"></script>
        <script type="text/javascript" src="http://code.google.com/apis/gears/gears_init.js"></script>
        
        <link type="text/css" href="test/calculadora/alpha/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="test/calculadora/alpha/css/estiloV2.css"/>

    </head>
    <body>
        <div id="cargando" title="Calculando costes">
                <p>Estamos calculando los costes del trayecto, por favor espere un instante.</p>
                <img src="test/calculadora/alpha/css/ajax-loader.gif" alt="Cargando..."/>
        </div>

        <img src="test/calculadora/alpha/css/beta.gif" border="0" width="150" height="150" style="position:absolute; right:0px; top:0px; margin:0px; padding:0px" />
        <div id="cabecera">
            <img src="test/calculadora/alpha/css/logo.png"/>
        </div>
        <div id="contenido">
            <div id="info" style="display:none;">
                &Uacute;ltima actualizaci&oacute;n: <span id="datos.ultimaActualizacion"></span>
                <br>Rutas calculadas hasta hoy: <span id="datos.rutasCalculadas"></span>
            </div>
            <div id="buscador">
                <div id="formulario">
                    <form id="demoForm" method="post" action="Calcular" class="bbq">
                        <div id="fieldWrapper">
                            <span class="step" id="ruta">
                                <label for="origen">Origen:</label><input type="text" id="origen" name="origen"/><br>
                                <label for="destino">Destino:</label><input type="text" id="destino" name="destino"/><br>
                                <input type="hidden" id="distancia" name="distancia"/>
                            </span>
                            <span id="condiciones" class="step">
                                <label for="pasajeros">Nº Ocupantes:</label><input type="text" id="pasajeros" name="pasajeros"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Cantidad de personas que realizarán el trayecto."/><br>
                                <label for="bultos">Nº Bultos:</label><input type="text" id="bultos" name="bultos"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Cantidad de maletas, bolsas, bolsos,etc. que se transportarán."/><br>
                                <label for="tipoConduccion">Conducci&oacute;n:</label>
                                <select id="tipoConduccion" name="tipoConduccion">
                                    <option value="1">Relajada</option>
                                    <option value="2" selected="selected">Normal</option>
                                    <option value="3">Agresiva</option>
                                </select><br>
                                <label for="idayvuelta">Ida y vuelta:</label><input type="checkbox" name="idayvuelta" id="idayvuelta" value="idayvuelta"/>
                            </span>
                            <span id="vehiculo" class="step">
                                <label for="idCombustible">Combustible:</label>
                                <select id="idCombustible" name="idCombustible">
                                    <option value="-1" selected="selected">--Selecciona--</option>
                                    <option value="1">Gasolina 95</option>
                                    <option value="2">Gasolina 98</option>
                                    <option value="3">Gasoleo A</option>
                                    <option value="4">Gasoleo A Extra</option>
                                </select><br>
                                <span id="labelConsumo"><label for="nombreVehiculo">Veh&iacute;culo:</label></span><input type="text" id="nombreVehiculo" name="nombreVehiculo"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Escriba el nombre de su vehiculo."/><br>
                                <span id="labelConsumo"><label for="consumo">Consumo medio:</label></span><input type="text" id="consumo" name="consumo"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Consumo medio del vehículo medido en litros a los 100km."/><br>
                                <a href="#_demoForm=vehiculo" onclick="verAvanzado()" title="Pinche aquí si desconoce el consumo medio de su vehículo">No conozco el consumo</a><br>
                                
                                <div id="avanzado">
                                    <label for="cilindrada">Cilindrada:</label><input  type="text" id="cilindrada" name="cilindrada"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Cilindrada del vehiculo que se va a utilizar."/><br>
                                    <label for="peso">Peso:</label><input type="text" id="peso" name="peso"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Peso en vacío del vehiculo que se va a utilizar."/><br>
                                    <label for="potencia">Potencia:</label><input type="text" id="potencia" name="potencia"/><img src="test/calculadora/alpha/css/ayudaIconV2.png" class="ayudaIcon" title="Potencia en caballos de vapor del vehículo que se va a utilizar."/><br>
                                    <a href="#_demoForm=vehiculo" onclick="verMensajeConsumo()" title="Pinche aquí si desconoce la información de su vehículo">Desconozco estos datos</a><br>
                                </div>
                                
                                <div id="mensajeConsumo">
                                    <p>Dado que desconoce el consumo medio de su vehículo y sus especificaciones técnicas utilizaremos un consumo medio para calcular sus gastos.</p>
                                </div>
                            </span>
                        </div>
                        <div id="nav">
                            <input class="navigation_button" id="back" value="Back" type="reset" />
                            <input class="navigation_button" id="next" value="Next" type="submit" onclick="controlarStep()"/>
                        </div>
                    </form>
                </div>
            </div>
            <div id="resultados">
                <div id="mapa"></div>
                <div id="instrucciones">
                    <div id="boton"><button id="botonDetalles" onclick="verDetallesRuta()">Ver detalles de la ruta</button></div>
                    <div id="directionsPanel"></div>
                </div>
                <div id="informe">
                    <div id="datos">
                        <div id="fecha"><span id="datos.fecha"></span></div>
                        - Ruta desde <span id="datos.origen"></span>&nbsp;hasta <span id="datos.destino"></span><br>
                        - Con <span id="datos.pasajeros"></span>&nbsp;pasajero(s) y <span id="datos.bultos"></span>&nbsp;bulto(s)<br>
                        - <span id="datos.precioCombustible"></span>&nbsp;&euro;/l<br>
                        - <span id="datos.consumo"></span>&nbsp;l/100Km
                        <div id="resultado"><span id="datos.distancia"></span>&nbsp;Km...........<b><span id="datos.costeTotal"></span></b>&nbsp;&euro;</div>
                    </div>
                    <div id="opciones">                        
                        <form action="GenerarPDF.action" id="formPDF" name="formPDF">
                            <input type="hidden" id="pdf_fecha" name="pdf_fecha" />
                            <input type="hidden" id="pdf_distancia" name="pdf_distancia" />
                            <input type="hidden" id="pdf_origen" name="pdf_origen" />
                            <input type="hidden" id="pdf_destino" name="pdf_destino" />
                            <input type="hidden" id="pdf_pasajeros" name="pdf_pasajeros" />
                            <input type="hidden" id="pdf_bultos" name="pdf_bultos" />
                            <input type="hidden" id="pdf_tipoConduccion" name="pdf_tipoConduccion" />
                            <input type="hidden" id="pdf_idayvuelta" name="pdf_idayvuelta" />
                            <input type="hidden" id="pdf_consumo" name="pdf_consumo" />
                            <input type="hidden" id="pdf_idCombustible" name="pdf_idCombustible" />
                            <input type="hidden" id="pdf_nombreVehiculo" name="pdf_nombreVehiculo" />
                            <input type="hidden" id="pdf_cilindrada" name="pdf_cilindrada" />
                            <input type="hidden" id="pdf_peso" name="pdf_peso" />
                            <input type="hidden" id="pdf_potencia" name="pdf_potencia" />
                            <input type="hidden" id="pdf_litrosConsumidos" name="pdf_litrosConsumidos" />
                            <input type="hidden" id="pdf_precioCombustible" name="pdf_precioCombustible" />
                            <input type="hidden" id="pdf_costeTotal" name="pdf_costeTotal" />
                            <input type="hidden" id="pdf_ultimaActualizacion" name="pdf_ultimaActualizacion" />
                            <input type="hidden" id="pdf_rutasCalculadas" name="pdf_rutasCalculadas" />
                        </form>                        
                        <a onclick="document.getElementById('formPDF').submit()" title="Guarda los resultados en tu ordenador en formato PDF"><img src="test/calculadora/alpha/css/pdf.png" alt="Guardar en PDF"/></a>

                        <a href="#" title="Imprime los resultados calculados" onclick="window.print();"><img src="test/calculadora/alpha/css/impresora.png" alt="Imprimir"/></a>
                        <a href="#" title="Enviar los resultados por correo electrónico"><img src="test/calculadora/alpha/css/correo.png" alt="Enviar por e-mail"/></a>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="test/calculadora/alpha/js/funcionesV2.js"></script>
        <script type="text/javascript">

            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-20341512-1']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
            })();

        </script>
    </body>
</html>