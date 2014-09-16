<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">

                    <b>Lista de tipos de costes</b><br>
                        <s:iterator id="listaTiposCoste" value="listaTiposCoste">
                            <s:property value="nombre" /> - <s:property value="descripcion" /><br>
                        </s:iterator>
                    <hr>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
