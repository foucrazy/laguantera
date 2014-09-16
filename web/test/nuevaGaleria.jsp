<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="addGaleria" method="POST">
    <s:textfield label="idVehiculo" name="idVehiculo" required="true" />
    <s:textfield label="titulo" id="titulo" name="titulo" required="true" />
    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
