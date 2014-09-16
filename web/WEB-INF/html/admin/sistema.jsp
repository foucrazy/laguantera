<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <b>Información del sistema</b><br>
                    <s:iterator id="listaPropiedades" value="listaPropiedades">
                        <s:property/><br><hr>
                    </s:iterator>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
