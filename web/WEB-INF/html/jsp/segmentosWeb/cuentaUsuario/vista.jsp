                <%@include file="../../init.jsp" %>

                <div class="content">
                    <ul>
                        <li><s:text name="laguantera.jsp.common.alias.texto"/>: ${usuario.alias}</li>
                        <li><s:text name="laguantera.jsp.common.codigopostal.texto"/>: ${usuario.codigoPostal}</li>
                        <li><s:text name="laguantera.jsp.common.email.texto"/>: ${usuario.email}</li>
                    </ul>
                    <div class="operacionesUsuario">
                        <s:url action="BorrarCuentaUsuarioJSP" var="BorrarCuentaUsuarioJSP"/>
                        <s:url action="BorrarCuentaUsuario" var="BorrarCuentaUsuario"/>
                        <a id="aBorrarUsuario" href="${BorrarCuentaUsuario}" title="" onclick="dialogoFormBorrar('#aBorrarUsuario','${BorrarCuentaUsuarioJSP}','<s:text name="laguantera.jsp.usuario.cuentaUusario.borrar.tituloConfirmacion"/>','#dialogo')">
                            <s:text name="laguantera.jsp.common.menuVertical.cuentaUsuario.borrar" />
                        </a>
                         - <a href="<s:url action="EditarCuentaUsuario"/>" title=""><s:text name="laguantera.jsp.common.menuVertical.cuentaUsuario.editar" /></a>
                        <div id="dialogo" class="Dialogo"></div>
                    </div>
                </div>
