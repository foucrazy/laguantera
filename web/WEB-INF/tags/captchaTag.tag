<%@tag description="Taglib for captcha" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="captchaImg" required="true" description="captcha in image format"%>
<%@attribute name="captchAud" required="true" description="captcha in audio format"%>
<%@attribute name="divClass" required="true" description="css class for the div that hold the entire captcha"%>
<%@attribute name="idInput" required="true" description="id for input that will hold the answer"%>

<fmt:setBundle basename="com.laguantera.resources.messages" />

<div class="Cleaner"></div>

<fieldset class="${divClass}">
    <legend>
        <fmt:message key="laguantera.jsp.campoObligatorio.simbolo"/><fmt:message key="laguantera.tagLib.captcha.inputText"/><fmt:message key="laguantera.jsp.dosPuntos"/>
    </legend>
    <div id="captcha">
        <img src="${captchaImg}" alt="captcha" /> <br/>
        <input type="text" id="${idInput}" name="${idInput}" size="30" />
    </div>
    
    <div id="auxDiv">
        <button type="button" onclick="generateText()" id="recargar">
            <img src="./img/tags/captcha/recargar.png" alt='<fmt:message key="laguantera.tagLib.captcha.reloadDescription"/>' title='<fmt:message key="laguantera.tagLib.captcha.reloadDescription"/>' id="reload" />
        </button>       
        <br />
        <button type="button" onclick="changeToAud()" id="cambiar">
            <img src="./img/tags/captcha/cambioAudio.png" alt='<fmt:message key="laguantera.tagLib.captcha.changeToAudioDescription"/>' title='<fmt:message key="laguantera.tagLib.captcha.changeToAudioDescription"/>' id="changeToAudio" />
        </button>
    </div>
    
    <div class="Cleaner"></div>
</fieldset>
    
<div class="Cleaner"></div>