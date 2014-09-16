                <%@include file="../../init.jsp" %>

                <div class="content">
                    <a href="<s:url action="VistaAgnadirVehiculo"/>" title=""><s:text name="laguantera.jsp.common.menuVertical.administracionCoche.agnadir" /></a>
                    <br><br>
                    <c:choose>
                        <c:when test="${not empty listVehiculos}" >
                            <c:forEach items="${listVehiculos}" var="vehiculoResumen" varStatus="contador">
                                <div class="vehiculo">
                                    <h1><s:text name="laguantera.jsp.usuario.gestionVehiculos.common.matricula"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculoResumen.matricula}</h1>
                                    <div class="datosGenerales">
                                        <div>
                                            <img src="imagenes/defecto/coche.jpg" alt="<s:text name="laguantera.jsp.usuario.gestionVehiculos.common.imagenDescpcion"/>"/>
                                        </div>
                                        <div>
                                            <ul>
                                                <li><s:text name="laguantera.jsp.usuario.gestionVehiculos.common.marca"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculoResumen.marca}</li>
                                                <li><s:text name="laguantera.jsp.usuario.gestionVehiculos.common.modelo"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculoResumen.modelo}</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="clearFloat"></div>
                                    <div class="detalles">
                                        <table summary="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.tablaDetalles"/>">
                                            <caption>Detalles</caption>
                                            <thead>
                                                <tr>
                                                    <th><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.detalles.fecha"/></th>
                                                    <th><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.detalles.nombre"/></th>
                                                    <th><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.detalles.concepto"/></th>
                                                    <th><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.detalles.cantidad"/></th>
                                                    <th><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.detalles.importe"/></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                </tr>
                                                <tr>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                    <td>nada</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="avisos">
                                        <h2><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.titulo"/></h2>
                                        <dl>
                                            <dt>Aviso1:</dt>
                                            <dd>Descripcion del aviso</dd>
                                            <dt>Aviso1:</dt>
                                            <dd>Descripcion del aviso</dd>
                                            <dt>Aviso1:</dt>
                                            <dd>Descripcion del aviso</dd>
                                        </dl>
                                    </div>
                                    <div class="operacionesVehiculo">
                                        <ul>

                                            <li>
                                                <c:url var="VistaDatosVehiculo" value="VistaDatosVehiculo.action">
                                                    <c:param name="idVehiculo" value='${vehiculoResumen.idVehiculo}'/>
                                                </c:url>
                                                <a href="${VistaDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.descripcion"/>">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.text"/>
                                                </a>
                                            </li>
                                            
                                            <li>
                                                <c:url var="editarDatosVehiculo" value="VistaEditarVehiculo.action">
                                                    <c:param name="idVehiculo" value='${vehiculoResumen.idVehiculo}'/>
                                                </c:url>
                                                <a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.descripcion"/>">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.text"/>
                                                </a>
                                            </li>

                                            <li>
                                                <c:url var="borrarDatosVehiculo" value="VistaBorrarVehiculo.action">
                                                    <c:param name="idVehiculo" value='${vehiculoResumen.idVehiculo}'/>
                                                    <c:param name="matricula" value='${vehiculoResumen.matricula}'/>
                                                </c:url>
                                                <c:url var="VistaBorrarVehiculoJsp" value="VistaBorrarVehiculoJsp.action">
                                                    <c:param name="idVehiculo" value='${vehiculoResumen.idVehiculo}'/>
                                                    <c:param name="matricula" value='${vehiculoResumen.matricula}'/>
                                                </c:url>
                                                <a id="aBorrarVehiculo${vehiculoResumen.idVehiculo}" href="${borrarDatosVehiculo}" title="" onclick="dialogoFormBorrar('#aBorrarVehiculo${vehiculoResumen.idVehiculo}','${VistaBorrarVehiculoJsp}','<s:text name="laguantera.jsp.usuario.gestionVehiculos.borrar.tituloConfirmacion"><s:param>${vehiculoResumen.matricula}</s:param></s:text>','#dialogo${vehiculoResumen.idVehiculo}')">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.text"/>
                                                </a>
                                                <div id="dialogo${vehiculoResumen.idVehiculo}" class="Dialogo"></div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="sinElementos">
                                <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.sinElemento" />
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
