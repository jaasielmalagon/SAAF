-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-06-2018 a las 22:05:12
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `avante`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos_solicitudes`
--

CREATE TABLE `prestamos_solicitudes` (
  `idSolicitud` int(11) NOT NULL,
  `monto` int(11) NOT NULL,
  `tasa` decimal(4,2) NOT NULL,
  `plazo` int(11) NOT NULL,
  `cliente` int(11) NOT NULL,
  `usuario` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `prestamos_solicitudes`
--

INSERT INTO `prestamos_solicitudes` (`idSolicitud`, `monto`, `tasa`, `plazo`, `cliente`, `usuario`, `sucursal`, `fecha`, `hora`, `estado`) VALUES
(1, 1000, '14.50', 20, 1, 2, 1, '2018-06-14', '12:30:55', 2),
(2, 2000, '13.50', 24, 1, 2, 1, '2018-06-18', '11:50:25', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `prestamos_solicitudes`
--
ALTER TABLE `prestamos_solicitudes`
  ADD PRIMARY KEY (`idSolicitud`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `prestamos_solicitudes`
--
ALTER TABLE `prestamos_solicitudes`
  MODIFY `idSolicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
