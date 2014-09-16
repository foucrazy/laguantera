<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>

                <%@include file="menu.jspf" %>

                <div id="content">
                    <img src="/imagenes/admin/error.png" />
                    <p><s:actionerror /></p>
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
