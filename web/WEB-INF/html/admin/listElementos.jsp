<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">

                    <b>Lista de tipos de elementos</b><br>
                        <s:iterator id="listaTiposElemento" value="listaTiposElemento">
                            <s:property value="nombre" /> - <s:property value="descripcion" /><br>
                        </s:iterator>
                    <hr>

                    <b>Lista de elementos</b><br>
                        <s:iterator id="listaElementos" value="listaElementos">
                            <s:property value="idTipoElemento" /> - <s:property value="concepto" /><br>
                        </s:iterator>
                    <hr>
                    
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
