<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="recuperarPass" method="POST">
    <s:textfield label="%{getText('msg.email')}" id="email" name="email" required="true" />
    <s:textfield name="answer" label="%{getText('msg.introduceCaptcha')}"/>
    <tr>
        <td><img src="/mis4ruedas/stickyImg.servlet" alt="captcha"/></td>
    </tr>
    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
