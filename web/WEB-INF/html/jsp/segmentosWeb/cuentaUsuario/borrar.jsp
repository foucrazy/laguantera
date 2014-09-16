<%@include file="../../init.jsp" %>

<s:form id="Formalio" acceptcharset="UTF-8" action="BorrarCuentaUsuarioEjecutar" method="POST" theme="simple">
    <s:text name="laguantera.jsp.usuario.cuentaUusario.borrar.text"/><br />
    <s:hidden name="idUsuario" value="%{#session.idUsuario}"/><br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="inpuntSi" type="radio" name="confirmacion" value="true" /><s:text name="laguantera.jsp.common.borrar.si"/><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="inpuntNo" type="radio" name="confirmacion" value="false" /><s:text name="laguantera.jsp.common.borrar.no"/><br/>
    <s:submit label='<s:text name="laguantera.jsp.common.borrar.boton"/>' value="Borrar" cssClass="botton"/>
</s:form>
