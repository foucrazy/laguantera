<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <b>Cantidad de vehiculos:</b> <s:property value="total"/><br><br><hr>
                    <b>Lista de vehiculos</b><br>
                    (idVehiculo - marca - modelo - matricula)<br>
                        <s:iterator id="listaVehiculos" value="listaVehiculos">
                            <s:property value="idVehiculo" /> - <s:property value="marca" /> - <s:property value="modelo" /> - <s:property value="matricula" /><br>
                        </s:iterator>
                    <hr>

                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
