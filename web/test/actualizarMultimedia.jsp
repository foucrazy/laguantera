<%@ include file="tags.jsp" %>
<html>
    <%@ include file="head.jsp" %>
    <body>
        <%@include file="superior.jsp" %>
        <div id="centro">
            <h2>Subir una imagen</h2>
            <s:form acceptcharset="UTF-8" action="updMultimedia.action" method="post">
                <s:textfield name="titulo" />
                <s:textfield name="descripcion" />
                <s:textfield name="idGaleria" />
                <s:textfield name="idMultimedia" />
                <s:submit type="button" label="%{getText('msg.subir')}"/>
            </s:form>
        </div>
        <%@include file="inferior.jsp" %>
    </body>
</html>
