<%@include file="tags.jspf" %>
<%@page import="com.laguantera.util.Servicios" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <c:if test="<%=Servicios.isInDevelopment%>"><h3>Utilizando configuracion para desarrollo!!</h3></c:if>                    
                    <br>Esta configurado en base de datos:<s:property value="estaConfigurado" /><br>
                    <s:form action="reloadConfiguracion">
                        <label>Recargar configuración:</label>
                        <input type="submit" value="Recargar" />
                    </s:form>
                    <hr>
                    <br>Cargar parametros por defecto (desde el JSON) a base de datos:<br>
                    <s:form action="restoreConfiguracion">
                        <label>Volcar configuración:</label>
                        <input type="submit" value="Volcar"/>
                    </s:form>
                    <hr>
                        <b>Lista de configuraciones</b><br>
                        <s:form action="setConfiguracion">
                            <input type="hidden" name="nueva" value="true"/>
                            <label>Añadir nuevo parámetro de configuración:</label><br>
                            <label>Nombre:</label><input type="text" name="nombre" /><br>
                            <label>Valor:</label><input type="text" name="valor" /><br>
                            <input type="submit" value="Guardar"/>
                        </s:form>
                            <br>
                        <s:iterator id="config" value="listaConfiguraciones">
                            <s:form action="setConfiguracion">
                                <label><s:property value="nombre" /></label>:
                                <input type="hidden" name="nombre" value="<s:property value="nombre" />"/>
                                <input type="text" name="valor" size="40" value="<s:property value="valor" />"/>
                                <input type="submit" value="Actualizar"/>
                            </s:form>
                            <s:form action="removeConfiguracion">
                                <input type="hidden" name="nombre" value="<s:property value="nombre" />"/>
                                <input type="hidden" name="valor"  value="<s:property value="valor" />"/>
                                <input type="submit" value="Eliminar" />
                            </s:form>
                        </s:iterator>
                    <hr>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
