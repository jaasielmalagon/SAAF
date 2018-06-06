-- phpMyAdmin SQL Dump
-- version 4.0.10.18
-- https://www.phpmyadmin.net
--
-- Servidor: localhost:3306
-- Tiempo de generación: 06-06-2018 a las 10:42:57
-- Versión del servidor: 5.6.39-cll-lve
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `avanteDB`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas_staff_adc`
--

CREATE TABLE IF NOT EXISTS `personas_empleados_adc` (
  `idAdc` int(11) NOT NULL AUTO_INCREMENT,
  `sucursal` int(11) NOT NULL,
  `idStaff` int(11) NOT NULL,
  `agencia` smallint(6) NOT NULL COMMENT 'zona local a la que pertenece',
  `vacante` tinyint(2) NOT NULL COMMENT '1 a 12 por zona',
  `nivel` tinyint(1) NOT NULL COMMENT '1=novel, 2=consolidado, 3 = master, 4 = elite',
  PRIMARY KEY (`idAdc`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=49 ;

--
-- Volcado de datos para la tabla `personas_staff_adc`
--

INSERT INTO `personas_empleados_adc` (`idAdc`, `sucursal`, `idStaff`, `agencia`, `vacante`, `nivel`) VALUES
(1, 1, 0, 1, 1, 0),
(2, 1, 0, 1, 2, 0),
(3, 1, 0, 1, 3, 0),
(4, 1, 0, 1, 4, 0),
(5, 1, 0, 1, 5, 0),
(6, 1, 0, 1, 6, 0),
(7, 1, 0, 1, 7, 0),
(8, 1, 0, 1, 8, 0),
(9, 1, 0, 1, 9, 0),
(10, 1, 0, 1, 10, 0),
(11, 1, 0, 1, 11, 0),
(12, 1, 0, 1, 12, 0),
(13, 1, 0, 2, 1, 0),
(14, 1, 0, 2, 2, 0),
(15, 1, 0, 2, 3, 0),
(16, 1, 0, 2, 4, 0),
(17, 1, 0, 2, 5, 0),
(18, 1, 0, 2, 6, 0),
(19, 1, 0, 2, 7, 0),
(20, 1, 0, 2, 8, 0),
(21, 1, 0, 2, 9, 0),
(22, 1, 0, 2, 10, 0),
(23, 1, 0, 2, 11, 0),
(24, 1, 0, 2, 12, 0),
(25, 1, 0, 3, 1, 0),
(26, 1, 0, 3, 2, 0),
(27, 1, 0, 3, 3, 0),
(28, 1, 0, 3, 4, 0),
(29, 1, 0, 3, 5, 0),
(30, 1, 0, 3, 6, 0),
(31, 1, 0, 3, 7, 0),
(32, 1, 0, 3, 8, 0),
(33, 1, 0, 3, 9, 0),
(34, 1, 0, 3, 10, 0),
(35, 1, 0, 3, 11, 0),
(36, 1, 0, 3, 12, 0),
(37, 1, 0, 4, 1, 0),
(38, 1, 0, 4, 2, 0),
(39, 1, 0, 4, 3, 0),
(40, 1, 0, 4, 4, 0),
(41, 1, 0, 4, 5, 0),
(42, 1, 0, 4, 6, 0),
(43, 1, 0, 4, 7, 0),
(44, 1, 0, 4, 8, 0),
(45, 1, 0, 4, 9, 0),
(46, 1, 0, 4, 10, 0),
(47, 1, 0, 4, 11, 0),
(48, 1, 0, 4, 12, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
