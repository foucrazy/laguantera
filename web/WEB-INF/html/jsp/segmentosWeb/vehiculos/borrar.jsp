<%@include file="../../init.jsp" %>

<s:form acceptcharset="UTF-8" action="BorrarVehiculoRealizar" method="POST" theme="simple">
    <s:text name="laguantera.jsp.usuario.gestionVehiculos.borrar.text">
        <s:param>${matricula}</s:param>
    </s:text><br /><br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="confirmacion" value="true" /><s:text name="laguantera.jsp.common.borrar.si"/><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="confirmacion" value="false" /><s:text name="laguantera.jsp.common.borrar.no"/><br/>
    <s:hidden name="idVehiculo" value="%{#parameters['idVehiculo']}"/>
    <s:submit label='<s:text name="laguantera.jsp.common.borrar.boton"/>' value="Borrar" cssClass="botton"/>
</s:form>
