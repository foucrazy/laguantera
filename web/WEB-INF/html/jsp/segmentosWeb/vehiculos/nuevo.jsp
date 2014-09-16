                <%@include file="../../init.jsp" %>
                <%@taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>

                <s:url value="GetModelos.action" var="getModelos"/>
                <s:form action="AgnadirVehiculoRealizar.action" method="POST" enctype="multipart/form-data" cssClass="formulario" theme="simple" acceptcharset="UTF-8" validate="true">
                    <div class="noticeBlock">
                        <p>
                            <s:text name="laguantera.jsp.common.requerido.texto"/>
                        </p>
                    </div>

                    <div class="errorBlock">
                        <p>
                            <s:actionerror />
                        </p>
                        <s:fielderror cssClass="error"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.marca"/><s:text name="laguantera.jsp.common.requerido.simbolo"/>
                        <s:select id="marca"
                                  headerKey="-1" headerValue="%{getText('laguantera.jsp.common.seleccionaMarca')}"
                                  list="listaMarcas"
                                  listKey="idMarca"
                                  listValue="nombreMarca"
                                  cssClass="textInput"
                                  onchange='peticionAjax("%{getModelos}","marca","modelo","idMarca")'
                                  name="idMarca"
                                  required="false"
                        />
                    </div>

                    <div class="fieldBlock" id="modelo">
                        <s:url id="cargarModelos" action="GetModelos" />
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.modelo"/><s:text name="laguantera.jsp.common.requerido.simbolo"/>
                        <select id="selectWithReloadTopic" class="textInput" name="idModelo">
                            <option value="-1"><s:text name="laguantera.jsp.common.seleccioneMarcaPrimero"/></option>
                        </select>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.motor"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield id="motor" name="motor" required="true" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.tara"/> <s:textfield name="tara" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.neumaticos"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="neumaticos" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.asientos"/> <s:textfield name="asientos" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindros"/> <s:textfield name="cilindros" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindrada"/> <s:textfield name="cilindrada" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cv"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield required="true" name="cv"  cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.kw"/> <s:textfield name="kw" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.tipoCompbustible"/><s:text name="laguantera.jsp.common.requerido.simbolo"/>
                        <s:select headerKey='-1' headerValue="%{getText('laguantera.jsp.common.seleccionaCombustible')}"
                                  list="listaCombustibles"
                                  listKey="idTipoCombustible"
                                  listValue="nombre"
                                  cssClass="textInput"
                                  name="idTipoCombustible"
                                  required="true"
                        />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.matricula"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="matricula" required="true" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaMatriculacion"/> <s:textfield name="fechaMatriculacion" cssClass="textInput" onclick="calendario()"/>
                    </div>

                    <div class="fieldBlock">
                       <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaFabricacion"/> <s:textfield name="fechaFabricacion" cssClass="textInput" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.imagen"/> <s:file name="imagen" cssClass="textInput" />
                    </div>

                    <div class="captchaBlock">
                        <img src="/laguantera/stickyImg.servlet" alt="captcha"/> <br/>
                        <s:label cssClass="labelForm"><s:text name="laguantera.jsp.common.captcha.texto"/><s:text name="laguantera.jsp.common.requerido.simbolo"/></s:label> <s:textfield name="answer" maxLength="50" cssClass="textInput" required="true"/>
                    </div>

                    <div class="bottonBlock">
                        <s:submit label='<s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.boton"/>' cssClass="botton" value="Añadir"/>
                    </div>

                    <div class="clear"></div>
                </s:form>