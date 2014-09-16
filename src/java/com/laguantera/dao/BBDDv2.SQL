/*
Created: 12/07/2010
Modified: 16/11/2010
Model: MySQL 5.0
Database: laguantera
*/

-- Drop tables section ---------------------------------------------------

DROP TABLE IF EXISTS `config`;
DROP TABLE IF EXISTS `ope_ele`;
DROP TABLE IF EXISTS `elementos`;
DROP TABLE IF EXISTS `operaciones`;
DROP TABLE IF EXISTS `costes_derivados`;
DROP TABLE IF EXISTS `multimedias`;
DROP TABLE IF EXISTS `galerias`;
DROP TABLE IF EXISTS `vehiculos`;
DROP TABLE IF EXISTS `modelos`;
DROP TABLE IF EXISTS `marcas`;
DROP TABLE IF EXISTS `usuarios`;
DROP TABLE IF EXISTS `tipos_operacion`;
DROP TABLE IF EXISTS `tipos_elemento`;
DROP TABLE IF EXISTS `tipos_combustible`;
DROP TABLE IF EXISTS `tipos_usuario`;
DROP TABLE IF EXISTS `tipos_multimedia`;
DROP TABLE IF EXISTS `tipos_coste`;
DROP TABLE IF EXISTS `eess`;
DROP TABLE IF EXISTS `info_vehiculos`;

-- Create tables section -------------------------------------------------

CREATE TABLE `neumaticos`
(
    `idNeumatico` INT NOT NULL AUTO_INCREMENT,
    `marca` Varchar(30),
    `modelo` Varchar(150),
    `ancho` Int,
    `alto` Int,
    `diametro` Int,
    `carga` Int,
    `velocidad` Varchar(10),
    `tipo` Varchar(25),
    `extra` Varchar(20),
    `proveedor` Varchar(50),
    `precio` Double,
    `enlace` Text,
PRIMARY KEY  (`idNeumatico`)
);

CREATE TABLE `info_vehiculos`
(
  `idInfoVehiculo` INT NOT NULL AUTO_INCREMENT,
  `idModelo` Int NOT NULL,
  `motor` Varchar(300),
  `tara` Int,
  `cilindrada` Int,
  `cv` Float,
  `kw` Float,
  `idTipoCombustible` Int NOT NULL,
  `emisiones` Double,
  `consCiudad` Double,
  `consMedio` Double,
  `consCarretera` Double,
PRIMARY KEY  (`idInfoVehiculo`)  
);

-- Table eess
CREATE TABLE `eess`
(
    `idEESS` INT NOT NULL AUTO_INCREMENT,
    `provincia` Varchar(50),
    `localidad` Varchar(50),
    `direccion` Varchar(200),
    `rotulo` Varchar(100),
    `horario` Varchar(125),
    `latitud` Double,
    `longitud` Double,
    `precio95` Double,
    `precio98` Double,
    `precioA` Double,
    `precioAExtra` Double,
PRIMARY KEY  (`idEESS`)
);

ALTER TABLE `eess` ADD UNIQUE `idEESS` (`idEESS`);

-- Table config
CREATE TABLE `config`
(
  `nombre` Varchar(20)NOT NULL,
  `valor` Varchar(300) NOT NULL,
PRIMARY KEY  (`nombre`)
);

ALTER TABLE `config` ADD UNIQUE `nombre` (`nombre`);

-- Table usuarios

CREATE TABLE `usuarios`
(
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `alias` Varchar(20) NOT NULL,
  `password` Varchar(20) NOT NULL,
  `idTipoUsuario` Int NOT NULL,
  `codigoPostal` Int,
  `email` Varchar(200) NOT NULL,
PRIMARY KEY  (`idUsuario`)
);


ALTER TABLE `usuarios` ADD UNIQUE `idUsuario` (`idUsuario`);

ALTER TABLE `usuarios` ADD UNIQUE `alias` (`alias`);

ALTER TABLE `usuarios` ADD UNIQUE `email` (`email`);

-- Table vehiculos

CREATE TABLE `vehiculos`
(
  `idVehiculo` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` Int NOT NULL,
  `idModelo` Int NOT NULL,
  `motor` Char(100) NOT NULL,
  `tara` Int,
  `neumaticos` Char(20),
  `asientos` Int,
  `cilindros` Int,
  `cilindrada` Int,
  `cv` Float NOT NULL,
  `kw` Float,
  `idTipoCombustible` Int NOT NULL,
  `matricula` Char(7),
  `fechaMatriculacion` Date,
  `fechaFabricacion` Date,
  `imagen` Char(100),
PRIMARY KEY  (`idVehiculo`)
);

ALTER TABLE `vehiculos` ADD UNIQUE `idVehiculo` (`idVehiculo`);

ALTER TABLE `vehiculos` ADD UNIQUE `matricula` (`matricula`);

-- Table marcas

CREATE TABLE `marcas`
(
  `idMarca` INT NOT NULL AUTO_INCREMENT,
  `nombreMarca` Varchar(30),
PRIMARY KEY  (`idMarca`)
);

-- Table multimedias

CREATE TABLE `multimedias`
(
  `idMultimedia` INT NOT NULL AUTO_INCREMENT,
  `titulo` Varchar(20),
  `idTipoMultimedia` Int NOT NULL,
  `ruta` Varchar(30),
  `descripcion` Varchar(200),
  `idGaleria` Int NOT NULL,
PRIMARY KEY  (`idMultimedia`)
);

-- Table operaciones

CREATE TABLE `operaciones`
(
  `idOperacion` INT NOT NULL AUTO_INCREMENT,
  `idVehiculo` Int NOT NULL,
  `idTipoOperacion` Int NOT NULL,
  `fecha` Date,
  `importeTotal` Float,
  `kmVehiculo` Int,  
  `responsable` Char(100),
  `lugar` Char(200),
PRIMARY KEY  (`idOperacion`)
);

-- Table elementos

CREATE TABLE `elementos`
(
  `idElemento` INT NOT NULL AUTO_INCREMENT,
  `idTipoElemento` Int NOT NULL,  
  `concepto` Text,
PRIMARY KEY  (`idElemento`)
);

-- Table tiposOperacion

CREATE TABLE `tipos_operacion`
(
  `idTipoOperacion` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(40),
  `descripcion` Char(100),
PRIMARY KEY  (`idTipoOperacion`)
);

-- Table tiposOperacion

CREATE TABLE `tipos_elemento`
(
  `idTipoElemento` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(40),
  `descripcion` Char(100),
PRIMARY KEY  (`idTipoElemento`)
);

-- Table operaciones - elementos

CREATE TABLE `ope_ele`
(
	`idOperacion` Int NOT NULL,
	`idElemento` Int NOT NULL,
	`precioUnidad` Float,
	`cantidad` Int,
PRIMARY KEY (`idOperacion`,`idElemento`)
);

-- Table costes_derivados

CREATE TABLE `costes_derivados`
(
  `idTipoCoste` INT NOT NULL,
  `idVehiculo` Int NOT NULL,  
  `valor` Float,
PRIMARY KEY  (`idTipoCoste`,`idVehiculo`)
);

-- Table tiposCoste

CREATE TABLE `tipos_coste`
(
  `idTipoCoste` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(40),
  `descripcion` Char(100),
PRIMARY KEY  (`idTipoCoste`)
);

-- Table tipos_combustible

CREATE TABLE `tipos_combustible`
(
  `idTipoCombustible` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(30),
PRIMARY KEY  (`idTipoCombustible`)
);



-- Table tipos_usuario

CREATE TABLE `tipos_usuario`
(
  `idTipoUsuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(10),
  `descripcion` Char(50),
  `permisos` Text,
PRIMARY KEY  (`idTipoUsuario`)
);

-- Table tipos_multimedia

CREATE TABLE `tipos_multimedia`
(
  `idTipoMultimedia` INT NOT NULL AUTO_INCREMENT,
  `nombre` Char(15),
PRIMARY KEY  (`idTipoMultimedia`)
);

-- Table galerias

CREATE TABLE `galerias`
(
  `idGaleria` INT NOT NULL AUTO_INCREMENT,
  `titulo` Varchar(30),
  `idVehiculo` Int NOT NULL,
PRIMARY KEY  (`idGaleria`)
);

-- Table modelos

CREATE TABLE `modelos`
(
  `idModelo` INT NOT NULL AUTO_INCREMENT,
  `nombreModelo` Varchar(30),
  `idMarca` Int NOT NULL,
PRIMARY KEY  (`idModelo`)
);

ALTER TABLE `modelos` ADD UNIQUE `idModelo` (`idModelo`);

-- Create relationships section -------------------------------------------------

ALTER TABLE `vehiculos` ADD CONSTRAINT `es propietario de` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `operaciones` ADD CONSTRAINT `le realizan` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculos` (`idVehiculo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `operaciones` ADD CONSTRAINT `es de un` FOREIGN KEY (`idTipoOperacion`) REFERENCES `tipos_operacion` (`idTipoOperacion`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `elementos` ADD CONSTRAINT `pertenece a un` FOREIGN KEY (`idTipoElemento`) REFERENCES `tipos_elemento` (`idTipoElemento`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ope_ele` ADD CONSTRAINT `miembros de` FOREIGN KEY (`idOperacion`) REFERENCES `operaciones` (`idOperacion`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ope_ele` ADD CONSTRAINT `formado por` FOREIGN KEY (`idElemento`) REFERENCES `elementos` (`idElemento`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `costes_derivados` ADD CONSTRAINT `asociado a` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculos` (`idVehiculo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `costes_derivados` ADD CONSTRAINT `es` FOREIGN KEY (`idTipoCoste`) REFERENCES `tipos_coste` (`idTipoCoste`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `multimedias` ADD CONSTRAINT `contiene` FOREIGN KEY (`idGaleria`) REFERENCES `galerias` (`idGaleria`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `multimedias` ADD CONSTRAINT `son de tipo` FOREIGN KEY (`idTipoMultimedia`) REFERENCES `tipos_multimedia` (`idTipoMultimedia`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `galerias` ADD CONSTRAINT `puede tener` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculos` (`idVehiculo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `modelos` ADD CONSTRAINT `pertenece a una` FOREIGN KEY (`idMarca`) REFERENCES `marcas` (`idMarca`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `vehiculos` ADD CONSTRAINT `es un` FOREIGN KEY (`idModelo`) REFERENCES `modelos` (`idModelo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `vehiculos` ADD CONSTRAINT `usa` FOREIGN KEY (`idTipoCombustible`) REFERENCES `tipos_combustible` (`idTipoCombustible`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `usuarios` ADD CONSTRAINT `con role` FOREIGN KEY (`idTipoUsuario`) REFERENCES `tipos_usuario` (`idTipoUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `info_vehiculos` ADD CONSTRAINT `modelo de` FOREIGN KEY (`idModelo`) REFERENCES `modelos` (`idModelo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `info_vehiculos` ADD CONSTRAINT `utiliza combustible` FOREIGN KEY (`idTipoCombustible`) REFERENCES `tipos_combustible` (`idTipoCombustible`) ON DELETE CASCADE ON UPDATE CASCADE;
