INSERT INTO tipos_multimedia(`nombre`)values('Imagen');
INSERT INTO tipos_multimedia(`nombre`)values('Video');

INSERT INTO tipos_combustible(`nombre`)values('Gasolina');
INSERT INTO tipos_combustible(`nombre`)values('Diesel');

INSERT INTO tipos_usuario(`nombre`,`descripcion`)values('Normal','Usuario normal.');
INSERT INTO tipos_usuario(`nombre`,`descripcion`)values('Admin','Usuario administrador.');

INSERT INTO tipos_coste(`nombre`,`descripcion`)values('Aceite','Costes asociados al aceite del vehículo: Aceite, filtro de aceite,...');
INSERT INTO tipos_coste(`nombre`,`descripcion`)values('Neumáticos','Costes asociados a los neumáticos: Neumático, equilibrado,válvulas,...');

INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Frenado','Piezas implicadas en el sistema de frenado:Pastillas, discos, tambores, hidráulica, cables,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Dirección','Piezas implicadas en el sistema de la dirección:Rotulas, brazos, ');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Suspensión','Piezas implicadas en el sistema de suspensión:Amortiguadores, Muelles, Barras estabilizadoras,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Electricidad','Piezas del sistema eléctrico: Alternador, Motor de arranque, bombillas, interruptores,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Motor','Piezas del motor:Filtros, correas, bujias, embrague, bombas,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Escape','Piezas que forman el sistema de escape: Colectores, catalizador, silenciador,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Calefacción/Climatización','Piezas relacionadas con la aclimatación del habitáculo:Radiador, compresor,...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Accesorios','Cables, limpiaparabrisas, retrovisores, ...');
INSERT INTO tipos_elemento(`nombre`,`descripcion`)values('Otros','Mano de obra, revisiones,etc...');

INSERT INTO tipos_operacion(`nombre`,`descripcion`)values('Mantenimiento','Operaciones realizadas para mantener en buenas condiciones el vehículo.');
INSERT INTO tipos_operacion(`nombre`,`descripcion`)values('Reparación','Operaciones necesarias para arreglar alguna deficiencia/avería del vehículo.');
INSERT INTO tipos_operacion(`nombre`,`descripcion`)values('Mejora','Operaciones opcionales realizadas para mejorar algún aspecto del vehículo.');

INSERT INTO operaciones(`idVehiculo`,`idTipoOperacion`,`fecha`,`importeTotal`,`kmVehiculo`,`responsable`,`lugar`)
	values(1,4,"2010-11-10",56.80,164760,"Vazquez","Talleres Vazquez Azaña, S.L.");

INSERT INTO elementos(`idTipoElemento`,`concepto`)values(25,"Aceite Sintetico 10w40");
INSERT INTO elementos(`idTipoElemento`,`concepto`)values(25,"Filtro de Aceite");
INSERT INTO elementos(`idTipoElemento`,`concepto`)values(29,"Mano de obra");

INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(1,1,30,1);
INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(1,2,12.80,1);
INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(1,3,14,1);

INSERT INTO operaciones(`idVehiculo`,`idTipoOperacion`,`fecha`,`importeTotal`,`kmVehiculo`,`responsable`,`lugar`)
	values(1,4,"2010-09-01",0,159500,"Felix","Nave Ajalvir");

INSERT INTO elementos(`idTipoElemento`,`concepto`)values(25,"Filtro de aire");
INSERT INTO elementos(`idTipoElemento`,`concepto`)values(25,"Tratamiento antifricción");
INSERT INTO elementos(`idTipoElemento`,`concepto`)values(27,"Liquido anti-fugas");

INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(2,4,12,1);
INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(2,5,8,1);
INSERT INTO ope_ele(`idOperacion`,`idElemento`,`precioUnidad`,`cantidad`)values(2,6,3,1);