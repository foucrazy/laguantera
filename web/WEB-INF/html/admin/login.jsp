<%@include file="tags.jspf" %>
<html>
    <%@include file="head.jspf" %>
	<body>
            <div id="body">
                <%@include file="superior.jspf" %>                

                <div id="content">
                    <form accept-charset="UTF-8" action="/laguantera/LoginRealizar">
                        <div class="Campo">
                            <label class="LoginLabel" for="alias">
                                <s:text name="laguantera.jsp.alias"/>
                            </label> 
                            <input type="text" class="LoginCampo" value=""  maxlength="20" name="alias"/>
                        </div>
                        <div class="Campo">
                            <label class="LoginLabel" for="password">
                                <s:text name="laguantera.jsp.contrasegna"/>
                            </label> 
                            <input type="password" class="LoginCampo" value="" maxlength="20" name="password" />
                        </div>
                        <div class="Campo">
                            <input type="submit" title="enviar" value="login" class="LoginBoton" />
                        </div>
                    </form>                    
                </div>

                <%@include file="inferior.jspf" %>
            </div>
	</body>
</html>
