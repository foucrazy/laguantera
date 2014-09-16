<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                   <b>Lista de modelos</b><br>
                        <s:iterator id="listaModelos" value="listaModelos">
                            <s:property value="nombreModelo" /><br>
                        </s:iterator>
                    <hr>

                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
