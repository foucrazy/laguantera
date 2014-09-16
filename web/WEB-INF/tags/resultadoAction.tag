<%@tag description="Taglib for the Form Error display" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@attribute name="errorResultClass" required="true" description="captcha in image format"%>
<%@attribute name="correctoResulClass" required="true" description="captcha in image format"%>

<fmt:setBundle basename="com.laguantera.resources.messages" />

<s:if test="hasActionErrors()">
    <div class="${errorResultClass}">
        <p>
            <s:actionerror />
        </p>
        <s:fielderror/>
    </div>
</s:if>
    
<s:if test="hasActionMessages()">
    <div class="${correctoResulClass}">
        <p>
            <s:actionmessage />
        </p>
    </div>
</s:if>