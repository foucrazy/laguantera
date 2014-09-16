<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="invitar" method="POST">
    <s:textfield label="%{getText('msg.email')}" id="email" name="email" required="true" />
    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
