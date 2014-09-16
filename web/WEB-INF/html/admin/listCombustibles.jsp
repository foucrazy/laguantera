<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">

                    <b>Lista de tipos de combustibles</b><br>
                        <s:iterator id="listaTiposCombustible" value="listaTiposCombustible">
                            <s:property value="nombre" /><br>
                        </s:iterator>
                    <hr>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
