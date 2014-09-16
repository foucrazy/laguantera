<%@ include file="tags.jsp" %>
<html>    
    <body>        
        <div id="centro">
            <h2>Subir una imagen</h2>
            <s:form acceptcharset="UTF-8" action="addMultimedia.action" method="post" enctype="multipart/form-data">
                Titulo:<s:textfield name="titulo" value="TITULO"/>
                Descripcion:<s:textfield name="descripcion"  value="DESCRIPCION"/>
                idTipoMultimedia:<s:textfield name=""/>(1 imagen 2 video)
                ruta:<s:textfield name="ruta"/>(solo si es un video)
                idGaleria:<s:textfield name="idGaleria"/>

                <s:file label="%{getText('msg.imagen')}" name="imagen" id="imagen"/>
                <s:submit type="button" label="%{getText('msg.subir')}"/>
            </s:form>
        </div>      
    </body>
</html>
