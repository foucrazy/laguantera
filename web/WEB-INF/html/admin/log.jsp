<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <b>LOG</b>
                    <br>Ruta:<s:property value="nombreFichero" /><br><br>
                    <s:property value="contenido" escapeHtml="false"/>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>

