                <%@include file="../../init.jsp" %>

                <div class="content">
                    <c:choose>
                        <c:when test="${vehiculo!=null}" >
                                <div class="vehiculo">
                                    <h1><s:text name="laguantera.jsp.usuario.gestionVehiculos.common.matricula"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.matricula}</h1>
                                    <div class="datosGenerales">
                                        <div>
                                            <img src="
                                                 <c:choose>
                                                     <c:when test="${vehiculo.imagen!=null && not empty vehiculo.imagen}">
                                                         ${vehiculo.imagen}
                                                     </c:when>
                                                     <c:otherwise>
                                                         imagenes/defecto/coche.jpg
                                                     </c:otherwise>
                                                 </c:choose>
                                                 " alt="<s:text name="laguantera.jsp.usuario.gestionVehiculos.common.imagenDescpcion"/>"
                                             />
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.marca"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.modelos.marcas.nombreMarca}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.modelo"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.modelos.nombreModelo}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.motor"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.motor}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.tara"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.tara}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.neumaticos"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.neumaticos}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.asientos"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.asientos}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindros"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.cilindros}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cilindrada"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.cilindrada}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.cv"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.cv}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.kw"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.kw}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.tipoCombustible"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.tiposCombustible.nombre}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaMatriculacion"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.fechaMatriculacion}
                                        </div>
                                        <div class="datosVehiculo">
                                            <s:text name="laguantera.jsp.usuario.gestionVehiculos.common.fechaFabricacion"/><s:text name="laguantera.jsp.common.puntos"/> ${vehiculo.fechaFabricacion}
                                        </div>
                                        <div class="operacionesVehiculo">
                                            <ul>
                                                <c:url var="editarDatosVehiculo" value="VistaEditarVehiculo.action">
                                                    <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                                </c:url>
                                                <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.text"/></a> </li>
                                                
                                                <c:url var="borrarDatosVehiculo" value="VistaBorrarVehiculo.action">
                                                    <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                                </c:url>
                                                <c:url var="borrarVehiculoRealizar" value="BorrarVehiculoRealizar.action">
                                                    <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                                </c:url>
                                                <li><a href="${borrarDatosVehiculo}" title="" onclick="submitDelete('<s:text name="laguantera.jsp.usuario.gestionVehiculos.borrar.text"/>','<s:text name="laguantera.jsp.usuario.gestionVehiculos.borrar.tituloConfirmacion"/>',aBorrarUsuario,'href',${borrarVehiculoRealizar},'&confirmacion=true','&confirmacion=false')"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.text"/></a></li>
                                            </ul>
                                        </div>
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
                                        <div class="operacionesDetalles">
                                        <ul>
                                            <c:url var="VistaDatosVehiculo" value="VistaDatosVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${VistaDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.descripcion"/>">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.text"/>
                                                </a>
                                            </li>
                                            <c:url var="editarDatosVehiculo" value="VistaEditarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.text"/></a> </li>
                                            <c:url var="editarDatosVehiculo" value="VistaBorrarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.text"/></a> </li>
                                        </ul>
                                    </div>
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
                                    <div class="operacionesAviss">
                                        <ul>
                                            <c:url var="VistaDatosVehiculo" value="VistaDatosVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${VistaDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.descripcion"/>">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.text"/>
                                                </a>
                                            </li>
                                            <c:url var="editarDatosVehiculo" value="VistaEditarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.text"/></a> </li>
                                            <c:url var="editarDatosVehiculo" value="VistaBorrarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.text"/></a> </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="galerias">
                                    Zona de las galerias
                                    <div class="operacionesGalerias">
                                        <ul>
                                            <c:url var="VistaDatosVehiculo" value="VistaDatosVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${VistaDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.descripcion"/>">
                                                    <s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.datosVehiculo.text"/>
                                                </a>
                                            </li>
                                            <c:url var="editarDatosVehiculo" value="VistaEditarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.editarVehiculo.text"/></a> </li>
                                            <c:url var="editarDatosVehiculo" value="VistaBorrarVehiculo.action">
                                                <c:param name="idVehiculo" value='${vehiculo.idVehiculo}'/>
                                            </c:url>
                                            <li><a href="${editarDatosVehiculo}" title="<s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.descripcion"/>"><s:text name="laguantera.jsp.usuario.gestionVehiculos.listadoVehiculos.avisos.link.borrarVehiculo.text"/></a> </li>
                                        </ul>
                                    </div>
                                </div>
                        </c:when>
                        <c:otherwise>
                            <p class="sinElementos">
                                <s:text name="laguantera.jsp.usuario.gestionVehiculos.vehiculo.noExiste" />
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
