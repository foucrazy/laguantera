<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="updGaleria" method="POST">
    <s:textfield label="idGaleria" name="idGaleria" required="true" />
    <s:textfield label="titulo" id="titulo" name="titulo" required="true" />
    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
