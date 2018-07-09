-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-06-2018 a las 22:30:21
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
-- Estructura de tabla para la tabla `prestamos`
--

CREATE TABLE `prestamos` (
  `idPrestamo` int(11) NOT NULL,
  `autorizo` int(11) NOT NULL DEFAULT '0',
  `fecha_autorizacion` datetime DEFAULT NULL,
  `entrego` int(11) NOT NULL DEFAULT '0',
  `fecha_entrega` datetime DEFAULT NULL,
  `num_contrato` int(11) NOT NULL DEFAULT '0',
  `fecha_contrato` date DEFAULT NULL,
  `cliente` int(11) NOT NULL DEFAULT '0',
  `total_prestado` int(11) NOT NULL DEFAULT '0',
  `capital` int(11) NOT NULL DEFAULT '0',
  `interes` decimal(8,2) NOT NULL DEFAULT '0.00',
  `plazo` tinyint(2) NOT NULL DEFAULT '0',
  `tarifa` decimal(8,2) NOT NULL DEFAULT '0.00',
  `multa` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `prestamos`
--

INSERT INTO `prestamos` (`idPrestamo`, `autorizo`, `fecha_autorizacion`, `entrego`, `fecha_entrega`, `num_contrato`, `fecha_contrato`, `cliente`, `total_prestado`, `capital`, `interes`, `plazo`, `tarifa`, `multa`) VALUES
(1, 2, '2018-06-26 14:51:52', 0, NULL, 0, NULL, 1, 3460, 2000, '1460.00', 20, '173.00', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD PRIMARY KEY (`idPrestamo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `idPrestamo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
