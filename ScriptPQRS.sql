-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-04-2019 a las 01:08:12
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pqrssystem`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `documento_identidad` varchar(12) NOT NULL,
  `contrasena` varchar(15) NOT NULL,
  `nombres` varchar(20) DEFAULT NULL,
  `apellidos` varchar(20) DEFAULT NULL,
  `estado_sistema` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`documento_identidad`, `contrasena`, `nombres`, `apellidos`, `estado_sistema`) VALUES
('2021', 'manu123', 'manuel', 'rosario', 0),
('2022', 'andoc', 'andrea', 'ocampo', 0),
('2023', '12345', 'eduardo manuel', 'ruiz torres', 0),
('2468', '123456', 'admin', 'admin', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dependencia`
--

CREATE TABLE `dependencia` (
  `codigo` varchar(12) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `dependencia`
--

INSERT INTO `dependencia` (`codigo`, `nombre`) VALUES
('0001', 'Recursos Humanos'),
('001', 'Recursos Humanos'),
('002', 'Contratacion'),
('003', 'Enfermeria'),
('004', 'Farmaceuticos'),
('005', 'Administracion'),
('221', 'tencologia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encargadodependencia`
--

CREATE TABLE `encargadodependencia` (
  `documento_laboral` varchar(12) NOT NULL,
  `contrasena` varchar(15) NOT NULL,
  `nombres` varchar(20) DEFAULT NULL,
  `apellidos` varchar(20) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `ip_acceso` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `encargadodependencia`
--

INSERT INTO `encargadodependencia` (`documento_laboral`, `contrasena`, `nombres`, `apellidos`, `fecha_ingreso`, `ip_acceso`) VALUES
('00110', '1212', 'mama', 'papa', '2014-01-02', '11.2'),
('111', 'aaa', 'aaaa', 'aaa', '2000-12-11', '000.00'),
('115544', 'assa', 'ñaño', 'namo', '2016-01-12', '192.168.14.3'),
('65210', '112233', 'ricardo', 'milos', '2015-01-25', '192.168.14.12'),
('65211', 'abc123', 'gustavo', 'arjona', '2016-05-05', '192.168.14.13'),
('65212', 'kiko23', 'francisco', 'marin', '2018-12-03', '192.168.14.14'),
('65213', 'mari69', 'maria antonieta', 'de las nieves', '2000-10-25', '192.168.14.15'),
('65214', 'javijavi', 'javier', 'tijeras', '2019-01-02', '192.168.14.16'),
('65215', 'nomelase', 'daniel', 'restrepo', '2015-01-26', '192.168.14.17'),
('987654321', '123456', 'carlitos', 'murillo', '2018-10-16', '192.168.5.26');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encarg_dep`
--

CREATE TABLE `encarg_dep` (
  `codigo_dep` varchar(12) NOT NULL,
  `dl_encargado` varchar(12) NOT NULL,
  `estado` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `encarg_dep`
--

INSERT INTO `encarg_dep` (`codigo_dep`, `dl_encargado`, `estado`) VALUES
('0001', '00110', 'activo'),
('0001', '987654321', 'inactivo'),
('001', '65210', 'activo'),
('001', '65215', 'inactivo'),
('002', '65211', 'activo'),
('003', '65212', 'activo'),
('003', '65215', 'activo'),
('004', '65213', 'activo'),
('005', '111', 'activo'),
('005', '65214', 'activo'),
('221', '987654321', 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pqrs`
--

CREATE TABLE `pqrs` (
  `codigo` int(11) NOT NULL,
  `titulo` varchar(30) NOT NULL,
  `decripcion` varchar(300) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `fecha` date NOT NULL,
  `dni_usuario` varchar(12) NOT NULL,
  `codigo_dependencia` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pqrs`
--

INSERT INTO `pqrs` (`codigo`, `titulo`, `decripcion`, `tipo`, `fecha`, `dni_usuario`, `codigo_dependencia`) VALUES
(1, 'mendigo', 'puto qll que lo lea', 'Queja', '2019-03-26', '123456789', '0001'),
(2, 'aaaaa', 'asadddddddddddddddddddddd', 'Petición', '2019-03-26', '123456789', '0001'),
(3, '2132121211', 'asdsadsdadsdñññ', 'Reclamo', '2019-03-26', '123456789', '0001'),
(4, 'dasdad', 'asdadavv ae rqar arae anweABwABUdAIUDAD', 'Petición', '2019-03-27', '123456789', '0001'),
(5, 'fhgfgfgfg', 'gdshfxsfejxrx   132131323', 'Petición', '2019-03-29', '123456789', '0001'),
(6, 'mas cmas', 'una descripcion del PQRS', 'Sugerencia', '2019-04-06', '112233', '221'),
(7, 'mal trato', 'mal trato del hospital hacia mi persona', 'Queja', '2019-03-26', '202050', '001'),
(8, 'falta mas personal', 'contratar mas personal para la agilzacion del hospital', 'Sugerencia', '2019-03-29', '202051', '002'),
(9, 'reclamo de drogas', 'pido el favor de rellenar de stock de la droga que necesito', 'Peticion', '2019-03-30', '202052', '004'),
(10, 'enfermera enojona', 'en el area de enfermeria hay una enfermera enojona que hace la vida imposible', 'Queja', '2019-02-22', '202053', '003'),
(11, 'no me atirizan', 'llevo varias semanas y no me autorizan mi examen', 'Peticion', '2019-03-01', '202051', '005');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuesta`
--

CREATE TABLE `respuesta` (
  `codigo` int(11) NOT NULL,
  `hora` time DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descripcion` varchar(400) NOT NULL,
  `cod_pqrs` int(11) DEFAULT NULL,
  `encargado` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `respuesta`
--

INSERT INTO `respuesta` (`codigo`, `hora`, `fecha`, `descripcion`, `cod_pqrs`, `encargado`) VALUES
(1, '11:17:00', '2019-03-27', 'vale chaval', 1, '987654321'),
(2, '15:31:06', '2019-02-12', 'hhhhhhhhhhhhhhhhhhhhhhhhhh  jhh hjjjjjjjjjjjjj             jhj j jhjhjhjhjhjhjhjhjhjhk               jhhbb hfbs fs <iufg hs<uidfs<i dfgsuf su ifs<ui fgs< fg<si fg s<ifug s<iufgs<iufg is<uf gs<ui fgis<u fg iu <sfgius< fgiusgf ius<efg ius<fgiu s<fgsui fgsui<fgs<uifg isufgs<uifg s<uifg i<us fgisu fgs<uifg s<uifgiu<s', 1, '987654321'),
(3, '13:07:59', '2019-03-29', 'asdsadsdadsdñññ', 3, '987654321'),
(4, '13:09:30', '2019-03-29', 'Esta es una respuesta válida.', 3, '987654321'),
(5, '13:12:08', '2019-03-29', 'otra respuesta posible ...', 4, '987654321'),
(6, '16:20:42', '2019-04-11', '22155422', 6, '987654321'),
(7, '12:00:51', '2019-03-30', 'a nombre del hospital le pedimos excusas acerca de lo sucedido', 7, '65210'),
(8, '09:16:12', '2019-04-02', 'estamos trabajando en ello', 8, '65211'),
(9, '10:19:12', '2019-04-01', 'el servicio de enfermeria hace lo que puedo, por favor paciencia', 9, '65213'),
(10, '16:16:16', '2019-03-01', 'ya estamos investigando el caso', 10, '65212'),
(11, '11:01:06', '2019-03-02', 'fue identificada la enfermera y se le dio su respectivo regaño', 10, '65215');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `DNI` varchar(12) NOT NULL,
  `tipo_documento` varchar(15) NOT NULL,
  `nombres` varchar(20) DEFAULT NULL,
  `apellidos` varchar(20) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `eps` varchar(20) DEFAULT NULL,
  `direccion` varchar(15) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `contrasena` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`DNI`, `tipo_documento`, `nombres`, `apellidos`, `fecha_nacimiento`, `eps`, `direccion`, `telefono`, `contrasena`) VALUES
('112233', 'CC', 'antonio', 'murillo', '1995-03-02', 'manuelita', 'calle 0', '2153624', '112233'),
('123456789', 'CC', 'paquita', 'la del barrio', '1990-06-29', 'cafesalud', 'carrera 84', '3201547895', '123456'),
('202050', 'CC', 'julian', 'manzano', '1990-05-26', 'cafesalud', 'calle 14', '3106513294', 'juliabc'),
('202051', 'CC', 'juliana', 'valencia', '1995-10-13', 'cafesalud', 'avenida 23', '3169541806', 'mina54'),
('202052', 'CC', 'marcos', 'valenciaga', '1999-06-16', 'cafesalud', 'calle sarmiento', '3194102360', '1por1'),
('202053', 'TI', 'andrea', 'leon', '2002-08-01', 'cafesalud', 'calle 14', '3130215014', 'mibebe'),
('9876543210', 'CC', 'carlitos', 'buenavida', '2018-11-06', 'coopeser', 'calle 2', '3215648954', '654321');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`documento_identidad`);

--
-- Indices de la tabla `dependencia`
--
ALTER TABLE `dependencia`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `encargadodependencia`
--
ALTER TABLE `encargadodependencia`
  ADD PRIMARY KEY (`documento_laboral`);

--
-- Indices de la tabla `encarg_dep`
--
ALTER TABLE `encarg_dep`
  ADD PRIMARY KEY (`codigo_dep`,`dl_encargado`),
  ADD KEY `FK5` (`dl_encargado`);

--
-- Indices de la tabla `pqrs`
--
ALTER TABLE `pqrs`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FK1` (`dni_usuario`),
  ADD KEY `FK2` (`codigo_dependencia`);

--
-- Indices de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FK3` (`cod_pqrs`),
  ADD KEY `FK4` (`encargado`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`DNI`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pqrs`
--
ALTER TABLE `pqrs`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `encarg_dep`
--
ALTER TABLE `encarg_dep`
  ADD CONSTRAINT `FK5` FOREIGN KEY (`dl_encargado`) REFERENCES `encargadodependencia` (`documento_laboral`),
  ADD CONSTRAINT `FK6` FOREIGN KEY (`codigo_dep`) REFERENCES `dependencia` (`codigo`);

--
-- Filtros para la tabla `pqrs`
--
ALTER TABLE `pqrs`
  ADD CONSTRAINT `FK1` FOREIGN KEY (`dni_usuario`) REFERENCES `usuario` (`DNI`),
  ADD CONSTRAINT `FK2` FOREIGN KEY (`codigo_dependencia`) REFERENCES `dependencia` (`codigo`);

--
-- Filtros para la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD CONSTRAINT `FK3` FOREIGN KEY (`cod_pqrs`) REFERENCES `pqrs` (`codigo`),
  ADD CONSTRAINT `FK4` FOREIGN KEY (`encargado`) REFERENCES `encargadodependencia` (`documento_laboral`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
