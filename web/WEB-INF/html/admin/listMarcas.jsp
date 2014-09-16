<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <b>Lista de marcas</b><br>
                        <s:iterator id="listaMarcas" value="listaMarcas">
                            <s:property value="nombreMarca" /><br>
                        </s:iterator>
                    <hr>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
