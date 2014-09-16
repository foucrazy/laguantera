<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="../init.jspf" %>

<tiles:importAttribute name="titulo" scope="request"/>
<tiles:importAttribute name="descripcion" scope="request"/>
<tiles:importAttribute name="barraDerechaURLDescripcion" scope="request"/>
<tiles:importAttribute name="barralaterlaDescripcion" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
    
    <head>
        <title>
            <fmt:message key="laguantera.jsp.logueado.comun.tituloPagina">
                <fmt:param>
                    <fmt:message key='${titulo}'/>        
                </fmt:param>
            </fmt:message>
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <meta name="Description" content='<fmt:message key="${descripcion}"/>' />

        <meta name="Robots" content="ver los valores" />
        <meta http-equiv="Window-target" content="_top" />
        
        <tiles:useAttribute id="listaCss" name="listaCss" classname="java.util.List" />
        <c:forEach var="css" items="${listaCss}">
            <link rel="stylesheet" type="text/css" href="<tiles:insertAttribute value="${css}" flush="true"/>" media="screen" />
        </c:forEach>

    </head>
    
    <body>
        <tiles:insertAttribute name="cabecera" />
        <div class="Cleaner"> </div>

        <div class="EnvolturaCuerpo">
            <div class="Menu">
                <tiles:insertAttribute name="menu" />
            </div>

            <div class="Contenido">
                <div class="MenuSecundario">
                    <tiles:insertAttribute name="menuSecundario" />
                </div>
                <div class="Seccion">
                    <laguantera:resultadoAction errorResultClass="Error" correctoResulClass="Exito"/>
                    
                    <tiles:insertAttribute name="seccion" />
                </div>
            </div>
                
            <div class="BarraDerecha">
                <div class="divImag">
                    <img src="<tiles:insertAttribute name="barraDerechaURLImagen" />" alt="<fmt:message key="${barraDerechaURLDescripcion}"/>" />
                </div>
                <p>
                    <fmt:message key="${barralaterlaDescripcion}"/>
                </p>
            </div>

            <div class="Cleaner"></div>

            <tiles:insertAttribute name="pie" />
        </div>

        <div class="Ocultar">
           <tiles:useAttribute id="listaJs" name="listaJs" classname="java.util.List" />
           <c:forEach var="js" items="${listaJs}">
                <script type="text/javascript" src="<tiles:insertAttribute value="${js}" flush="true" />" ></script>
           </c:forEach>
       </div>

    </body>
</html>