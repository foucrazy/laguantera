<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@include file="../init.jspf" %>

<tiles:importAttribute name="titulo" scope="request"/>
<tiles:importAttribute name="palabrasClave" scope="request"/>
<tiles:importAttribute name="descripcion" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><fmt:message key="${titulo}"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <meta name="Keywords" content='<fmt:message key="${palabrasClave}"/>' />
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
        
        <tiles:insertAttribute name="menu" />

        <tiles:insertAttribute name="cuerpo" />        
       
        <div class="Ocultar">
           <tiles:useAttribute id="listaJs" name="listaJs" classname="java.util.List" />
           <c:forEach var="js" items="${listaJs}">
                <script type="text/javascript" src="<tiles:insertAttribute value="${js}" flush="true" />" ></script>
           </c:forEach>
       </div>
    </body>
</html>
