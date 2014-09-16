<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="updVehiculo" method="POST">
    <s:textfield label="idVehiculo" name="idVehiculo" required="true" />
    <s:textfield label="motor" id="motor" name="motor" required="true" />
    <s:textfield label="tara" name="tara" />
    <s:textfield label="neumaticos" name="neumaticos" />
    <s:textfield label="asientos" name="asientos" />
    <s:textfield label="cilindros" name="cilindros" />
    <s:textfield label="cilindrada" name="cilindrada" />
    <s:textfield label="cv" name="cv" required="true" />
    <s:textfield label="kw" name="kw" />
    <s:textfield label="combustible" name="combustible" required="true" />
    <s:textfield label="fechaMatriculacion" name="fechaMatriculacion" />
    <s:textfield label="fechaFabricacion" name="fechaFabricacion" />
    <s:textfield label="matricula" name="matricula" />

    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
