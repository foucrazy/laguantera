<%@include file="../../init.jsp" %>
<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>

<s:form action="EditarCuentaUsuarioEjecutar.action" method="POST" cssClass="formulario" theme="simple" acceptcharset="UTF-8" validate="true">
    <div class="noticeBlock">
        <p>
            <s:text name="laguantera.jsp.common.requerido.texto"/>
        </p>
    </div>

    <div class="errorBlock">
        <p>
            <s:actionerror />
        </p>
        <s:fielderror cssClass="error"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.common.email.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="email" maxLength="50" cssClass="textInput" required="true" value="%{usuario.email}"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.common.reemail.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="reEmail" maxLength="20" cssClass="textInput" required="true" value="%{usuario.email}"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.contrasegnaVieja"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:password name="oldPassword" maxLength="20" cssClass="textInput" required="true"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.contrasegnaNueva"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:password name="password" maxLength="20" cssClass="textInput" required="true"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.contrasegnaNueva"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:password name="rePassword" maxLength="20" cssClass="textInput" required="true"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.common.alias.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="alias" maxLength="50" cssClass="textInput" required="true" value="%{usuario.alias}"/>
    </div>

    <div class="fieldBlock">
        <s:text name="laguantera.jsp.common.codigopostal.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="codigoPostal" maxLength="50" cssClass="textInput" required="true" value="%{usuario.codigoPostal}"/>
    </div>

    <div class="captchaBlock">
        <img src="/laguantera/stickyImg.servlet" alt="captcha"/> <br/>
        <s:text name="laguantera.jsp.common.captcha.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="answer" maxLength="50" cssClass="textInput" required="true" value=''/>
    </div>

    <div class="bottonBlock">
        <s:submit label='<s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.boton"/>' cssClass="botton" value="Actualizar"/>
    </div>

    <div class="clear"></div>
</s:form>
