<%@include file="../../init.jsp" %>

<s:form action="InvitarCuentaUsuarioEjecutar" method="POST" cssClass="formulario" theme="simple" acceptcharset="UTF-8" validate="true">
    <div class="fieldBlock">
        <s:label cssClass="labelForm"><s:text name="laguantera.jsp.common.email.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/></s:label> <s:textfield name="email" maxLength="50" cssClass="textInput" required="true"/>
    </div>
    <div class="bottonBlock">
        <s:submit label='<s:text name="laguantera.jsp.common.boton.invitar"/>' value="Invitar" cssClass="botton"/>
    </div>
    <div class="captchaBlock">
        <img src="/laguantera/stickyImg.servlet" alt="captcha"/> <br/>
        <s:text name="laguantera.jsp.common.captcha.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="answer" maxLength="50" cssClass="textInput" required="true" value=''/>
    </div>

    <div class="clear"></div>
</s:form>
