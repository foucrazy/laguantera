                <%@include file="../../init.jsp" %>

                <s:form action="EditarVehiculoRealizar.action" method="POST" enctype="multipart/form-data" cssClass="formulario" theme="simple" acceptcharset="UTF-8" validate="true">
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

                    <s:hidden name="idVehiculo" value="%{vehiculo.idVehiculo}"/>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.marca"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.modelos.marcas.nombreMarca}
                    </div>

                    <div class="fieldBlock" id="modelo">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.modelo"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.modelos.nombreModelo}
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.motor"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield id="motor" name="motor" required="true" cssClass="textInput" value="%{vehiculo.motor}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.tara"/> <s:textfield name="tara" cssClass="textInput" value="%{vehiculo.tara}" />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.neumaticos"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="neumaticos" required="true" cssClass="textInput" value="%{vehiculo.neumaticos}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.asientos"/> <s:textfield name="asientos" cssClass="textInput" value="%{vehiculo.asientos}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindros"/> <s:textfield name="cilindros" cssClass="textInput" value="%{vehiculo.cilindros}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindrada"/> <s:textfield name="cilindrada" cssClass="textInput" value="%{vehiculo.cilindrada}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cv"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield required="true" name="cv"  cssClass="textInput" value="%{vehiculo.cv}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.kw"/> <s:textfield name="kw" cssClass="textInput" value="%{vehiculo.kw}"/>
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
                                  value="%{vehiculo.tiposCombustible.idTipoCombustible}"
                        />
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.matricula"/><s:text name="laguantera.jsp.common.requerido.simbolo"/> <s:textfield name="matricula" required="true" cssClass="textInput" value="%{vehiculo.matricula}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaMatriculacion"/> <s:textfield name="fechaMatriculacion" cssClass="textInput" value="%{vehiculo.fechaMatriculacion}"/>
                    </div>

                    <div class="fieldBlock">
                       <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaFabricacion"/> <s:textfield name="fechaFabricacion" cssClass="textInput" value="%{vehiculo.fechaFabricacion}"/>
                    </div>

                    <div class="fieldBlock">
                        <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.imagen"/> <s:file name="imagen" cssClass="textInput" />
                    </div>

                    <div class="bottonBlock">
                        <s:submit label='<s:text name="laguantera.jsp.usuario.cuentaUusario.modificar.boton"/>' cssClass="botton" value="Añadir"/>
                    </div>

                    <div class="clear"></div>
                </s:form>