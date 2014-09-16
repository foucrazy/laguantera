<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="updUsuario" method="POST">
    <s:textfield label="%{getText('msg.alias')}" name="alias" required="true" />
    <s:password label="%{getText('msg.contrasena')}" id="oldPassword" name="oldPassword" required="true"/>
    <s:password label="%{getText('msg.contrasena')}" id="password" name="password" required="true"/>
    <s:password label="%{getText('msg.repetirContrasena')}" id="rePassword" name="rePassword" required="true"/>
    <s:textfield label="%{getText('msg.codigoPostal')}" id="codigoPostal" name="codigoPostal" required="true" onkeypress="return soloNumeros(event);" />
    <s:textfield label="%{getText('msg.email')}" id="email" name="email" required="true" />
    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
