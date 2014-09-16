<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="laguantera" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="com.laguantera.resources.messages" />
<%
// must re-set the response header because the f:setBundle tag can sometimes override it
response.setContentType("text/html; charset=utf-8");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>La Guantera</title>
        
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
        <meta content="La guantera" name="Keywords" />
        <meta content="La guantera" name="Description" />
        <meta content="ver los valores" name="Robots" />
        
        <link media="screen" type="text/css" rel="stylesheet" href="calculadora/css/main.css"/>
        <link media="screen" type="text/css" rel="stylesheet" href="calculadora/css/jquery-ui-1.8.16.custom.css"/>
    </head>
    <body>
        <img src="test/calculadora/alpha/css/beta.gif" style="position:absolute; right:0px; top:0px; margin:0px; padding:0px" border="0" height="150" width="150">
        <div id="cabecera">
            <img src="test/calculadora/alpha/css/logo.png">
        </div>
        <div id="contenido">
           <div id="planificaTuViaje" class="Mostrar">
                <h1 class="TituloPagina">
                    <s:text name="laguantera.jsp.anonimo.menu.planificaTuViaje.texto"/>
                </h1>
                <div class="CuerpoPagina">
                    <p>
                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.intro"/>
                        <br />
                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.parrafo2"/>
                    </p>

                    <div id="buscadorPlanificaTuViaje" class="Buscador">
                        <div class="Pasos">
                            <ul>
                                <li>
                                    <div class="PasoSeleccionado" id="rutaPaso">
                                        <label class="NumeroPaso"><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.1.numero"/></label>
                                        <span class="DescripcionPaso">
                                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.1.nombre"/><br/>
                                            <span><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.1.descripcion"/></span>
                                        </span>
                                        <div class="Cleaner"></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="PasoDeshabilatado" id="viajePaso">
                                        <label class="NumeroPaso"><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.2.numero"/></label>
                                        <span class="DescripcionPaso">
                                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.2.nombre"/><br/>
                                            <span><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.2.descripcion"/></span>
                                        </span>
                                        <div class="Cleaner"></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="PasoDeshabilatado" id="vehiculoPaso">
                                        <label class="NumeroPaso"><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.3.numero"/></label>
                                        <span class="DescripcionPaso">
                                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.3.nombre"/><br/>
                                            <span><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.paso.3.descripcion"/></span>
                                        </span>
                                        <div class="Cleaner"></div>
                                    </div>

                                </li>
                            </ul>
                            <div class="Cleaner"></div>
                        </div>
                        <form action="Resultado" method="post" id="formularioPlanificaTuViaje" >
                            <div id="fieldWrapper">
                                <fieldset class="step" id="ruta">
                                    <legend>
                                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.trayecto"/><s:text name="laguantera.jsp.dosPuntos"/>
                                    </legend>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="origen">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.origen"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <input type="text" id="origen" name="origen" />
                                    </div>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="destino">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.destino"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <input type="text" id="destino" name="destino" />
                                    </div>

                                    <input type="hidden" id="distancia" name="distancia"/>
                                    <input type="hidden" id="duracionIda" name="duracionIda"/>
                                    <input type="hidden" id="duracionVuelta" name="duracionVuelta"/>

                                </fieldset>

                                <fieldset class="step" id="viaje">
                                    <legend>
                                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.viaje"/><s:text name="laguantera.jsp.dosPuntos"/>
                                    </legend>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="pasajeros">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nOcupantes"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <input type="text" id="pasajeros" name="pasajeros" />
                                        <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nOcupantes.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nOcupantes.descripcion"/>'/>
                                    </div>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="bultos">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nBultos"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <input type="text" id="bultos" name="bultos" />
                                        <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nBultos.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nBultos.descripcion"/>'/>
                                    </div>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="tipoConduccion">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <select id="tipoConduccion" name="tipoConduccion">
                                            <option value="1">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion.1"/>
                                            </option>
                                            <option value="2" selected="selected">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion.2"/>
                                            </option>
                                            <option value="3">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion.3"/>
                                            </option>
                                        </select>
                                    </div>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="idayvuelta">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.idayVuelta"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <input type="checkbox" name="idayvuelta" id="idayvuelta" value="idayvuelta" />
                                    </div>
                                </fieldset>

                                <fieldset class="step" id="vehiculo">
                                    <legend>
                                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.vehiculo"/><s:text name="laguantera.jsp.dosPuntos"/>
                                    </legend>
                                    <div class="fieldHorizontalDerecha">
                                        <label for="idCombustible">
                                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible"/><s:text name="laguantera.jsp.dosPuntos"/>
                                        </label>
                                        <select id="idCombustible" name="idCombustible">
                                            <option value="1">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible.1"/>
                                            </option>
                                            <option value="2">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible.2"/>
                                            </option>
                                            <option value="3">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible.3"/>
                                            </option>
                                            <option value="4">
                                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible.4"/>
                                            </option>
                                        </select>
                                    </div>
                                    <div class="fieldHorizontalDerecha" id="divVehiculo">
                                            <span class="labelConsumo">
                                                <label for="marcaVehiculo">
                                                    <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.marcaVehiculo"/><s:text name="laguantera.jsp.dosPuntos"/>
                                                </label>
                                            </span>
                                            <input type="text" id="marcaVehiculo" name="marcaVehiculo"/>
                                            <input type="hidden" id="idMarcaVehiculo" name="idMarcaVehiculo"/>
                                            <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.marcaVehiculo.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.marcaVehiculo.descripcion"/>'/>
                                        </div>
                                        <div class="fieldHorizontalDerecha" id="divVehiculo">
                                            <span class="labelConsumo">
                                                <label for="modeloVehiculo">
                                                    <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.modeloVehiculo"/><s:text name="laguantera.jsp.dosPuntos"/>
                                                </label>
                                            </span>
                                            <input type="text" id="modeloVehiculo" name="modeloVehiculo"/>
                                            <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.modeloVehiculo.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.modeloVehiculo.descripcion"/>'/>
                                        </div>
                                    <div class="fieldHorizontalDerecha" id="divConsumo">
                                        <span class="labelConsumo">
                                            <label for="consumo">
                                                <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.consumoMedio"/><s:text name="laguantera.jsp.dosPuntos"/>
                                            </label>
                                        </span>
                                        <input type="text" id="consumo" name="consumo"/>
                                        <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.consumoMedio.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.consumoMedio.descripcion"/>' />
                                    </div>
                                    <div class="Cleaner VerMasOpciones" id="noConozcoConsumo">
                                        <a href="" title="Pinche aquí si desconoce el consumo medio de su vehículo" onclick="verMasOpciones('1');return false;">
                                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.noConozcoConsumo"/>
                                        </a>
                                    </div>
                                    <div id="consumoEstandar">
                                        <div class="fieldHorizontalDerecha">
                                            <label for="cilindrada">
                                                <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.cilindrada"/><s:text name="laguantera.jsp.dosPuntos"/>
                                            </label>
                                            <input  type="text" id="cilindrada" name="cilindrada" class="Ignorar"/>
                                            <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.cilindrada.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.cilindrada.descripcion"/>' />
                                        </div>
                                        <div class="fieldHorizontalDerecha">
                                            <label for="peso">
                                                <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.peso"/><s:text name="laguantera.jsp.dosPuntos"/>
                                            </label>
                                            <input type="text" id="peso" name="peso" class="Ignorar"/>
                                            <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.peso.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.peso.descripcion"/>' />
                                        </div>
                                        <div class="fieldHorizontalDerecha">
                                            <label for="potencia">
                                                <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.potencia"/><s:text name="laguantera.jsp.dosPuntos"/>
                                            </label>
                                            <input type="text" id="potencia" name="potencia" class="Ignorar"/>
                                            <img src="./img/anonimo/html/segmentosWeb/planificaTuViaje/iconoAyuda.png" class="ayudaIcon" alt='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.potencia.descripcion"/>' title='<s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.potencia.descripcion"/>'/>
                                        </div>
                                        <div class="VerMasOpciones Cleaner" id="desconozcoDatos">
                                            <a href="" title="Pinche aquí si desconoce la información de su vehículo" onclick="verMasOpciones('2');return false;">
                                                <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.desconozcoDatos"/>
                                            </a>
                                        </div>
                                    </div>
                                    <div id="desconoceDatos" class="Cleaner DesconoceDatos">
                                        <p>
                                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.datosDefecto"/>
                                        </p>
                                    </div>
                                </fieldset>

                            </div>
                            <div class="Navegacion">
                                <input type="reset" id="back"/>
                                <input type="submit" id="next" onclick="envioFormulario();"/>
                            </div>
                            <div class="Cleaner"></div>
                        </form>
                    </div>

                    <div id="calculandoDistancia" title='<fmt:message key="laguantera.jsp.anonimo.menu.planificaTuViaje.calculandoDistancia.titulo"/>'>
                        <p>
                            <s:text name="laguantera.jsp.anonimo.menu.planificaTuViaje.calculandoDistancia.cuerpo"/>
                        </p>
                        
                        <img alt="Cargando Estamos calculando datos de la ruta del trayecto, por favor espere un instante." src="img/comun/segmentosWeb/cargando.gif">
                    </div>

                    <div id="calculandoViaje" title='<fmt:message key="laguantera.jsp.anonimo.menu.planificaTuViaje.calculandoViaje.titulo"/>'>
                       <p>
                           <s:text name="laguantera.jsp.anonimo.menu.planificaTuViaje.calculandoViaje.cuerpo"/>
                       </p>
            
                       <img alt="Cargando Estamos calculando los costes del trayecto, por favor espere un instante." src="img/comun/segmentosWeb/cargando.gif">
                    </div>

                </div>
            </div>
        </div>
                    
        <div class="Ocultar">
            <script src="./js/jquery/core/jquery-1.7.1.js" type="text/javascript"></script>
            <script src="./js/jquery/core/jquery-ui-1.8.17.js" type="text/javascript"></script>
            
            <script src="http://maps.google.com/maps/api/js?sensor=true" type="text/javascript"></script>
            
            <script src="./js/jquery/pluginsExternos/jquery.validate.js" type="text/javascript"></script>
            <script src="./js/jquery/pluginsExternos/jquery.form.wizard.js" type="text/javascript"></script>
            
            <script src="calculadora/js/main.js" type="text/javascript"></script>
        </div>
    </body>
</html>
