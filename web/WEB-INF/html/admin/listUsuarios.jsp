<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <b>Buscador de usuarios</b><br>
                    <form>
                        Alias:<input type="text" name="alias" /><br>
                        Email:<input type="text" name="email" /><br>
                        Codigo Postal:<input type="text" name="codigoPostal" /><br>
                        Que se cumplan todas las condiciones:<input type="checkbox" name="todas"/><br>
                        <input type="submit" value="Buscar"/>
                    </form>
                    <hr>
                    <b>Lista tipos de usuarios</b><br> (idTipoUsuario - nombre - descripcion)<br><br>
                        <s:iterator id="listaTiposUsuarios" value="listaTiposUsuarios">
                            <s:property value="idTipoUsuario" /> - <s:property value="nombre" /> - <s:property value="descripcion" /><br>
                        </s:iterator>
                    <hr>
                    <b>Lista de usuarios</b><br> (idUsuario - alias - email) <br><br>
                        <s:iterator id="listaUsuarios" value="listaUsuarios">
                            <s:property value="idUsuario" /> - <s:property value="alias" /> - <s:property value="email" /> - <input type="button" value="Editar"/> - <input type="button" value="Contactar"/><br>
                        </s:iterator>
                    <hr>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
                