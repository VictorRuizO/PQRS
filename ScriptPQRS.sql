-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-03-2019 a las 21:27:44
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
  `apellidos` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dependencia`
--

CREATE TABLE `dependencia` (
  `codigo` varchar(12) NOT NULL,
  `nombre` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encarg_dep`
--

CREATE TABLE `encarg_dep` (
  `codigo_dep` varchar(12) NOT NULL,
  `dl_encargado` varchar(12) NOT NULL,
  `estado` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `DNI` varchar(12) NOT NULL,
  `tipo_documento` varchar(15) NOT NULL,
  `nombres` varchar(10) DEFAULT NULL,
  `apellidos` varchar(10) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `eps` varchar(20) DEFAULT NULL,
  `direccion` varchar(15) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `contrasena` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT;

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
