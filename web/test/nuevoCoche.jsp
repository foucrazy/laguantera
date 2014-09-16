<%@ include file="tags.jsp" %>
<s:form acceptcharset="UTF-8" action="addVehiculo" method="POST">
    <s:select list="listaModelos" listKey="idModelo" listValue="nombreModelo"/>
    <s:textfield label="motor" id="motor" name="motor" required="true" />
    <s:textfield label="tara" name="tara" required="true" />
    <s:textfield label="neumaticos" name="neumaticos" required="true" />
    <s:textfield label="asientos" name="asientos" required="true" />
    <s:textfield label="cilindros" name="cilindros" required="true" />
    <s:textfield label="cilindrada" name="cilindrada" required="true" />
    <s:textfield label="cv" name="cv" required="true" />
    <s:textfield label="kw" name="kw" required="true" />
    <s:textfield label="combustible" name="combustible" required="true" />
    <s:textfield label="fechaMatriculacion" name="fechaMatriculacion" required="true" />
    <s:textfield label="fechaFabricacion" name="fechaFabricacion" required="true" />
    <s:textfield label="matricula" name="matricula" required="true" />

    <s:submit type="button" cssClass="boton" label="%{getText('msg.enviar')}"/>
</s:form>
