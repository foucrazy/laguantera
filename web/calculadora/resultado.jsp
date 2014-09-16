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
            <div class="PlanificaTuViajeResultado CenterDivH5">

                <div id="buscadorPlanificaTuViajeResultado" class="Buscador">
                    <fieldset>
                        <legend>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.trayecto"/><s:text name="laguantera.jsp.dosPuntos"/>
                        </legend>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.origen"/><s:text name="laguantera.jsp.dosPuntos"/> 
                            <span id="origen">
                                ${data.origen}
                            </span>
                        </div>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.destino"/><s:text name="laguantera.jsp.dosPuntos"/> 
                            <span id="destino">
                                ${data.destino}
                            </span>
                        </div>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.distancia"/><s:text name="laguantera.jsp.dosPuntos"/> 
                            <span id="distancia">
                                ${data.distancia}
                            </span>
                        </div>
                    </fieldset>

                    <fieldset>
                        <legend>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.viaje"/><s:text name="laguantera.jsp.dosPuntos"/>
                        </legend>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nOcupantes"/><s:text name="laguantera.jsp.dosPuntos"/> ${data.pasajeros}
                        </div>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.nBultos"/><s:text name="laguantera.jsp.dosPuntos"/> ${data.bultos}
                        </div>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion"/><s:text name="laguantera.jsp.dosPuntos"/> <s:text name='laguantera.jsp.anonimo.planificaTuViaje.formulario.conduccion.%{#parameters.tipoConduccion}'/>
                        </div>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.idayVuelta"/><s:text name="laguantera.jsp.dosPuntos"/>
                            <span id="idayvuelta">
                                <c:choose>
                                    <c:when test="${data.idayvuelta}">
                                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.idayVuelta.idayVuelta"/>
                                    </c:when>
                                    <c:otherwise>
                                        <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.idayVuelta.ida"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </fieldset>

                    <fieldset>
                        <legend>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.vehiculo"/><s:text name="laguantera.jsp.dosPuntos"/>
                        </legend>

                        <div>
                            <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible"/><s:text name="laguantera.jsp.dosPuntos"/> <s:text name='laguantera.jsp.anonimo.planificaTuViaje.formulario.Combustible.%{#parameters.idCombustible}'/>

                        </div>

                        <c:if test="${ !empty data.nombreVehiculo }">
                            <div>
                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.vehiculo"/><s:text name="laguantera.jsp.dosPuntos"/>${data.nombreVehiculo}
                            </div>
                        </c:if>

                        <c:if test="${ data.consumo != 0 }">
                            <div>
                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.consumoMedio"/><s:text name="laguantera.jsp.dosPuntos"/>${data.consumo}
                            </div>
                        </c:if>

                        <c:if test="${ data.cilindrada != 0 }">
                            <div>
                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.cilindrada"/><s:text name="laguantera.jsp.dosPuntos"/>${data.cilindrada}
                            </div>
                        </c:if>

                        <c:if test="${ data.peso != 0 }">
                            <div>
                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.peso"/><s:text name="laguantera.jsp.dosPuntos"/>${data.peso}
                            </div>
                        </c:if>

                        <c:if test="${ data.potencia != 0 }">
                            <div>
                                <s:text name="laguantera.jsp.anonimo.planificaTuViaje.formulario.potencia"/><s:text name="laguantera.jsp.dosPuntos"/>${data.potencia}
                            </div>
                        </c:if>

                    </fieldset>

                </div>

                <div class="Datos">
                    <div class="Fecha">
                        13-mar-2012 20:18:57
                    </div>
                    <s:text name="laguantera.jsp.anonimo.planificaTuViajeResultado.ticket.trayecto" var="trayecto">
                        <s:param>${data.origen}</s:param>
                        <s:param>${data.destino}</s:param>
                    </s:text>
                    - ${trayecto}<br />

                    <s:text name="laguantera.jsp.anonimo.planificaTuViajeResultado.ticket.viaje" var="viaje">
                        <s:param>${data.pasajeros}</s:param>
                        <s:param>${data.bultos}</s:param>
                    </s:text>
                    - ${viaje}<br />

                    - ${data.precioCombustible} <s:text name="laguantera.jsp.moneda"/>/<s:text name="laguantera.jsp.litro"/><br/>
                    - ${data.consumo} <s:text name="laguantera.jsp.litro"/>/100<s:text name="laguantera.jsp.medidaDistancia"/>

                    <div class="Resultado">
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${data.distancia/1000}"/> <s:text name="laguantera.jsp.medidaDistancia"/>...........${data.costeTotal} <s:text name="laguantera.jsp.moneda"/>
                    </div>
                </div>

                <div class="Buscador Descarga">
                    <form action="Exportar" class="Oultar" methods="post" id="datosResultado">
                        <input type="hidden" name="r_origen" value="${data.origen}"/>
                        <input type="hidden" name="r_destino" value="${data.destino}"/>                            
                        <input type="hidden" name="r_fecha" value="${data.fecha}"/>
                        <input type="hidden" name="r_distancia" value="${data.distancia}"/>
                        <input type="hidden" name="r_pasajeros" value="${data.pasajeros}"/>
                        <input type="hidden" name="r_bultos" value="${data.bultos}"/>
                        <input type="hidden" name="r_tipoConduccion" value="${data.tipoConduccion}"/>
                        <input type="hidden" name="r_idayvuelta" value="${data.idayvuelta}"/>
                        <input type="hidden" name="r_consumo" value="${data.consumo}"/>
                        <input type="hidden" name="r_idCombustible" value="${data.idCombustible}"/>
                        <input type="hidden" name="r_nombreVehiculo" value="${data.nombreVehiculo}"/>
                        <input type="hidden" name="r_cilindrada" value="${data.cilindrada}"/>
                        <input type="hidden" name="r_peso" value="${data.peso}"/>
                        <input type="hidden" name="r_potencia" value="${data.potencia}"/>
                        <input type="hidden" name="r_litrosConsumidos" value="${data.litrosConsumidos}"/>
                        <input type="hidden" name="r_precioCombustible" value="${data.precioCombustible}"/>
                        <input type="hidden" name="r_costeTotal" value="${data.costeTotal}"/>
                        <input type="hidden" name="r_ultimaActualizacion" value="${data.ultimaActualizacion}"/>
                        <input type="hidden" name="r_rutasCalculadas" value="${data.rutasCalculadas}"/>
                        <input type="hidden" name="toPrinter" value=""/>
                        <input type="hidden" name="toMail" value=""/>
                        <input type="hidden" name="email" value=""/>
                    </form>

                    <a title="Guarda los resultados en tu ordenador en formato PDF" id="pdf" onclick='enviarFormulario("1")'>
                        <img alt="Guardar en PDF" src="img/anonimo/html/segmentosWeb/planificaTuViaje/pdf.png" />
                    </a>

                    <a title="Enviar los resultados via e-mail" id="correo" onclick='envioCorreo()'>
                        <img alt="Guardar en PDF" src="img/anonimo/html/segmentosWeb/planificaTuViaje/correo.png" />
                    </a>

                    <a title="Imprimir los resultados" id="imprimir" onclick='enviarFormulario("3")'>
                        <img alt="Guardar en PDF" src="img/anonimo/html/segmentosWeb/planificaTuViaje/impresora.png" />
                    </a>
                </div>

                <div class="Cleaner"></div>

            </div>

            <div id="resultadoGoogleMaps" class="PlanificaTuViajeResultado  CenterDivH5">
                <div id="mapaGoogle" class="ResultadosGoogle"></div>

                <c:choose>
                    <c:when test="${!data.idayvuelta}">
                        <c:set var="indicacionesIda" value="ResultadosGoogle"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="indicacionesIda" value="ResultadosGoogleIda"/>
                    </c:otherwise>
                </c:choose>
                <div id="indicacionesIda" class="${indicacionesIda}"></div>

                <c:if test="${data.idayvuelta}">
                    <div id="indicacionesVuelta" class="ResultadosGoogleVuelta"></div>
                </c:if>

                <div class="Cleaner"></div>
                
                <div id="envioCorreo" title="<fmt:message key="laguantera.jsp.anonimo.planificaTuViajeResultado.enviandoCorreo.titulo"/>">
                    <div class="fieldHorizontalDerecha">
                        <p>
                            <fmt:message key="laguantera.jsp.anonimo.planificaTuViajeResultado.enviandoCorreo.mensaje"/>
                        </p>
                        <label for="info">
                            <s:text name="laguantera.jsp.campoObligatorio.simbolo"/><fmt:message key="laguantera.jsp.email.texto"/><s:text name="laguantera.jsp.dosPuntos"/>
                        </label>
                        <input type="text" id="info" name="info" />
                        <div id="errorDialogo" class="Ocultar Error"></div>
                    </div>
                </div>
                        
                <div id="operacionCorrecta" title='<fmt:message key="laguantera.jsp.anonimo.planificaTuViajeResultado.operacionCorrecta.titulo"/>'>
                    <p>
                        <s:text name="laguantera.jsp.anonimo.planificaTuViajeResultado.operacionCorrecta.cuerpo"/>
                    </p>
                </div>
                        
                <div id="operacionErronea" title='<fmt:message key="laguantera.jsp.anonimo.planificaTuViajeResultado.operacionErronea.titulo"/>'>
                    <p>
                        <s:text name="laguantera.jsp.anonimo.planificaTuViajeResultado.operacionErronea.cuerpo"/>
                    </p>
                </div>
            </div>
        </div>
                    
        <div class="Ocultar">
            <script src="./js/jquery/core/jquery-1.7.1.js" type="text/javascript"></script>
            <script src="./js/jquery/core/jquery-ui-1.8.17.js" type="text/javascript"></script>
            
            <script src="http://maps.google.com/maps/api/js?sensor=true" type="text/javascript"></script>
            
            <script src="calculadora/js/calculadoraResultado.js" type="text/javascript"></script>
        </div>
    </body>
</html>
