                <%@include file="../../init.jsp" %>

                <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.modelo"/><s:text name="laguantera.jsp.common.requerido.simbolo"/>
                <c:choose>
                    <c:when test="${listaModelos==null || empty listaModelos}">
                        <select id="selectWithReloadTopic" class="textInput" name="modelo">
                            <option value="-1"><s:text name="laguantera.jsp.common.seleccionaModelo"/></option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <s:select headerKey="-1" headerValue="%{getText('laguantera.jsp.common.seleccionaModelo')}"
                                  list="listaModelos"
                                  listKey="idModelo"
                                  listValue="nombreModelo"
                                  cssClass="textInput"
                                  name="idModelo"
                                  theme="simple"
                                  required="true"
                        />
                    </c:otherwise>
                </c:choose>
