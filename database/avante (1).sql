-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-06-2018 a las 10:09:34
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
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `idDomicilio` int(11) NOT NULL,
  `direccion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `latitud` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `longitud` varchar(20) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `domicilios`
--

INSERT INTO `domicilios` (`idDomicilio`, `direccion`, `latitud`, `longitud`) VALUES
(1, 'CALLE 1 SUR 108 INT 203, CENTRO DE LA CIUDAD, 75700 TEHUACÁN, PUE., MÉXICO', '18.4623504', '-97.3934058'),
(2, 'CALLE 6 PTE 217, CENTRO, 75700 TEHUACÁN, PUE., MÉXICO', '18.4691768', '-97.3971585'),
(4, 'CALLE 6 PTE 1004-1006, AQUILES SERDÁN, TEHUACÁN, PUE., MÉXICO', '18.4691982', '-97.4031335');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados`
--

CREATE TABLE `estados` (
  `idEstado` int(11) NOT NULL,
  `estado` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `estados`
--

INSERT INTO `estados` (`idEstado`, `estado`) VALUES
(1, 'Aguascalientes'),
(2, 'Baja California'),
(3, 'Baja California Sur'),
(4, 'Campeche'),
(5, 'Chiapas'),
(6, 'Chihuahua'),
(7, 'Ciudad de México'),
(8, 'Coahuila'),
(9, 'Colima'),
(10, 'Durango'),
(11, 'Estado de México'),
(12, 'Guanajuato'),
(13, 'Guerrero'),
(14, 'Hidalgo'),
(15, 'Jalisco'),
(16, 'Michoacán'),
(17, 'Morelos'),
(18, 'Nayarit'),
(19, 'Nuevo León'),
(20, 'Oaxaca'),
(21, 'Puebla'),
(22, 'Querétaro'),
(23, 'Quintana Roo'),
(24, 'San Luis Potosí'),
(25, 'Sin Localidad'),
(26, 'Sinaloa'),
(27, 'Sonora'),
(28, 'Tabasco'),
(29, 'Tamaulipas'),
(30, 'Tlaxcala'),
(31, 'Veracruz'),
(32, 'Yucatán'),
(33, 'Zacatecas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_personas`
--

CREATE TABLE `historial_personas` (
  `idHistorialPersonas` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `modificacion` datetime NOT NULL,
  `usuario` int(11) NOT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `apaterno` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `amaterno` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `nacimiento` date NOT NULL,
  `entidad` int(11) NOT NULL,
  `curp` varchar(18) COLLATE utf8_spanish_ci NOT NULL,
  `ocr` varchar(13) COLLATE utf8_spanish_ci NOT NULL,
  `sexo` varchar(1) COLLATE utf8_spanish_ci NOT NULL,
  `edoCivil` int(11) NOT NULL,
  `telefono` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `celular` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `domicilio` int(11) DEFAULT NULL,
  `conyuge` int(11) DEFAULT NULL,
  `aval` int(11) DEFAULT NULL,
  `referencia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `historial_personas`
--

INSERT INTO `historial_personas` (`idHistorialPersonas`, `idPersona`, `modificacion`, `usuario`, `nombre`, `apaterno`, `amaterno`, `nacimiento`, `entidad`, `curp`, `ocr`, `sexo`, `edoCivil`, `telefono`, `celular`, `domicilio`, `conyuge`, `aval`, `referencia`) VALUES
(1, 4, '2018-05-15 15:25:06', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'H', 0, '----------', '2381209130', 0, 0, 0, 0),
(2, 1, '2018-05-19 11:24:30', 1, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 0, 0, 0, 0),
(3, 1, '2018-05-19 11:38:38', 1, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 1, 0, 0, 0),
(4, 1, '2018-05-19 11:41:45', 1, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 1, 0, 0, 0),
(5, 4, '2018-05-19 11:44:15', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 0, 0, 0, 0),
(6, 3, '2018-05-19 11:44:18', 3, 'ALMA ELIA', 'VALERIO', 'LECHUGA', '1969-07-20', 21, 'VALA690720MPLLCL00', '1992075813527', 'M', 1, '----------', '2381682735', 0, 0, 0, 0),
(7, 4, '2018-05-21 14:53:19', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 2, 0, 0, 0),
(8, 4, '2018-05-21 14:53:35', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 0, 0, 0, 0),
(9, 4, '2018-05-21 14:55:25', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 2, 0, 0, 0),
(10, 4, '2018-05-21 14:58:20', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 1, 0, 0, 0),
(11, 4, '2018-05-24 13:07:25', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 2, 0, 0, 0),
(12, 5, '2018-06-01 01:18:58', 2, 'ANGELINA', 'CONTRERA', 'MARTINEZ', '1972-03-28', 20, 'NSANDOXKSMDKOKN3IO', '1545615665867', 'M', 0, '----------', '2381209502', 0, 0, 0, 0),
(13, 1, '2018-06-01 14:34:05', 1, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 2, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipios`
--

CREATE TABLE `municipios` (
  `idMunicipio` int(11) NOT NULL,
  `municipio` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `estado` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `municipios`
--

INSERT INTO `municipios` (`idMunicipio`, `municipio`, `estado`, `sucursal`) VALUES
(1, 'Aguascalientes', 1, 0),
(2, 'Asientos', 1, 0),
(3, 'Calvillo', 1, 0),
(4, 'Cosío', 1, 0),
(5, 'Jesús María', 1, 0),
(6, 'Pabellón de Arteaga', 1, 0),
(7, 'Rincón de Romos', 1, 0),
(8, 'San José de Gracia', 1, 0),
(9, 'Tepezalá', 1, 0),
(10, 'El Llano', 1, 0),
(11, 'San Francisco de los Romo', 1, 0),
(12, 'Acajete', 21, 0),
(13, 'Acateno', 21, 0),
(14, 'Acatlan', 21, 0),
(15, 'Acatzingo', 21, 0),
(16, 'Acteopan', 21, 0),
(17, 'Ahuacatlan', 21, 0),
(18, 'Ahuatlan', 21, 0),
(19, 'Ahuazotepec', 21, 0),
(20, 'Ahuehuetitla', 21, 0),
(21, 'Ajalpan', 21, 1),
(22, 'Albino Zertuche', 21, 0),
(23, 'Aljojuca', 21, 0),
(24, 'Altepexi', 21, 0),
(25, 'Amixtlan', 21, 0),
(26, 'Amozoc', 21, 0),
(27, 'Aquixtla', 21, 0),
(28, 'Atempan', 21, 0),
(29, 'Atexcal', 21, 0),
(30, 'Atlequizayan', 21, 0),
(31, 'Atlixco', 21, 0),
(32, 'Atoyatempan', 21, 0),
(33, 'Atzala', 21, 0),
(34, 'Atzitzihuacan', 21, 0),
(35, 'Atzitzintla', 21, 0),
(36, 'Axutla', 21, 0),
(37, 'Ayotoxco de Guerrero', 21, 0),
(38, 'Calpan', 21, 0),
(39, 'Caltepec', 21, 0),
(40, 'Camocuautla', 21, 0),
(41, 'Cañada Morelos', 21, 0),
(42, 'Caxhuacan', 21, 0),
(43, 'Chalchicomula de Sesma', 21, 0),
(44, 'Chapulco', 21, 0),
(45, 'Chiautla', 21, 0),
(46, 'Chiautzingo', 21, 0),
(47, 'Chichiquila', 21, 0),
(48, 'Chiconcuautla', 21, 0),
(49, 'Chietla', 21, 0),
(50, 'Chigmecatitlan', 21, 0),
(51, 'Chignahuapan', 21, 0),
(52, 'Chignautla', 21, 0),
(53, 'Chila', 21, 0),
(54, 'Chila de la Sal', 21, 0),
(55, 'Chilchotla', 21, 0),
(56, 'Chinantla', 21, 0),
(57, 'Coatepec', 21, 0),
(58, 'Coatzingo', 21, 0),
(59, 'Cohetzala', 21, 0),
(60, 'Cohuecan', 21, 0),
(61, 'Coronango', 21, 0),
(62, 'Coxcatlan', 21, 0),
(63, 'Coyomeapan', 21, 0),
(64, 'Coyotepec', 21, 0),
(65, 'Cuapiaxtla de Madero', 21, 0),
(66, 'Cuautempan', 21, 0),
(67, 'Cuautinchan', 21, 0),
(68, 'Cuautlancingo', 21, 0),
(69, 'Cuayuca de Andrade', 21, 0),
(70, 'Cuetzalan del Progreso', 21, 0),
(71, 'Cuyoaco', 21, 0),
(72, 'Domingo Arenas', 21, 0),
(73, 'Eloxochitlan', 21, 0),
(74, 'Epatlan', 21, 0),
(75, 'Esperanza', 21, 0),
(76, 'Francisco Z. Mena', 21, 0),
(77, 'General Felipe Angeles', 21, 0),
(78, 'Guadalupe', 21, 0),
(79, 'Guadalupe Victoria', 21, 0),
(80, 'Hermenegildo Galeana', 21, 0),
(81, 'Honey', 21, 0),
(82, 'Huaquechula', 21, 0),
(83, 'Huatlatlauca', 21, 0),
(84, 'Huauchinango', 21, 0),
(85, 'Huehuetla', 21, 0),
(86, 'Huehuetlan el Chico', 21, 0),
(87, 'Huehuetlan el Grande', 21, 0),
(88, 'Huejotzingo', 21, 0),
(89, 'Hueyapan', 21, 0),
(90, 'Hueytamalco', 21, 0),
(91, 'Hueytlalpan', 21, 0),
(92, 'Huitzilan de Serdan', 21, 0),
(93, 'Huitziltepec', 21, 0),
(94, 'Ixcamilpa de Guerrero', 21, 0),
(95, 'Ixcaquixtla', 21, 0),
(96, 'Ixtacamaxtitlan', 21, 0),
(97, 'Ixtepec', 21, 0),
(98, 'Izucar de Matamoros', 21, 0),
(99, 'Jalpan', 21, 0),
(100, 'Jolalpan', 21, 0),
(101, 'Jonotla', 21, 0),
(102, 'Jopala', 21, 0),
(103, 'Juan C. Bonilla', 21, 0),
(104, 'Juan Galindo', 21, 0),
(105, 'Juan N. Mendez', 21, 0),
(106, 'La Magdalena Tlatlauquitepec', 21, 0),
(107, 'Lafragua', 21, 0),
(108, 'Libres', 21, 0),
(109, 'Los Reyes de Juarez', 21, 0),
(110, 'Mazapiltepec de Juarez', 21, 0),
(111, 'Mixtla', 21, 0),
(112, 'Molcaxac', 21, 0),
(113, 'Naupan', 21, 0),
(114, 'Nauzontla', 21, 0),
(115, 'Nealtican', 21, 0),
(116, 'Nicolas Bravo', 21, 0),
(117, 'Nopalucan', 21, 0),
(118, 'Ocotepec', 21, 0),
(119, 'Ocoyucan', 21, 0),
(120, 'Olintla', 21, 0),
(121, 'Oriental', 21, 0),
(122, 'Pahuatlan', 21, 0),
(123, 'Palmar de Bravo', 21, 0),
(124, 'Pantepec', 21, 0),
(125, 'Petlalcingo', 21, 0),
(126, 'Piaxtla', 21, 0),
(127, 'Puebla', 21, 0),
(128, 'Quecholac', 21, 0),
(129, 'Quimixtlan', 21, 0),
(130, 'Rafael Lara Grajales', 21, 0),
(131, 'San Andres Cholula', 21, 0),
(132, 'San Antonio Cañada', 21, 0),
(133, 'San Diego la Mesa Tochimiltzingo', 21, 0),
(134, 'San Felipe Teotlalcingo', 21, 0),
(135, 'San Felipe Tepatlan', 21, 0),
(136, 'San Gabriel Chilac', 21, 0),
(137, 'San Gregorio Atzompa', 21, 0),
(138, 'San Jeronimo Tecuanipan', 21, 0),
(139, 'San Jeronimo Xayacatlan', 21, 0),
(140, 'San Jose Chiapa', 21, 0),
(141, 'San Jose Miahuatlan', 21, 0),
(142, 'San Juan Atenco', 21, 0),
(143, 'San Juan Atzompa', 21, 0),
(144, 'San Martin Texmelucan', 21, 0),
(145, 'San Martin Totoltepec', 21, 0),
(146, 'San Matias Tlalancaleca', 21, 0),
(147, 'San Miguel Ixitlan', 21, 0),
(148, 'San Miguel Xoxtla', 21, 0),
(149, 'San Nicolas Buenos Aires', 21, 0),
(150, 'San Nicolas de los Ranchos', 21, 0),
(151, 'San Pablo Anicano', 21, 0),
(152, 'San Pedro Cholula', 21, 0),
(153, 'San Pedro Yeloixtlahuaca', 21, 0),
(154, 'San Salvador el Seco', 21, 0),
(155, 'San Salvador el Verde', 21, 0),
(156, 'San Salvador Huixcolotla', 21, 0),
(157, 'San Sebastian Tlacotepec', 21, 0),
(158, 'Santa Catarina Tlaltempan', 21, 0),
(159, 'Santa Ines Ahuatempan', 21, 0),
(160, 'Santa Isabel Cholula', 21, 0),
(161, 'Santiago Miahuatlan', 21, 0),
(162, 'Santo Tomas Hueyotlipan', 21, 0),
(163, 'Soltepec', 21, 0),
(164, 'Tecali de Herrera', 21, 0),
(165, 'Tecamachalco', 21, 0),
(166, 'Tecomatlan', 21, 0),
(167, 'Tehuacan', 21, 1),
(168, 'Tehuitzingo', 21, 0),
(169, 'Tenampulco', 21, 0),
(170, 'Teopantlan', 21, 0),
(171, 'Teotlalco', 21, 0),
(172, 'Tepanco de Lopez', 21, 1),
(173, 'Tepango de Rodriguez', 21, 0),
(174, 'Tepatlaxco de Hidalgo', 21, 0),
(175, 'Tepeaca', 21, 0),
(176, 'Tepemaxalco', 21, 0),
(177, 'Tepeojuma', 21, 0),
(178, 'Tepetzintla', 21, 0),
(179, 'Tepexco', 21, 0),
(180, 'Tepexi de Rodriguez', 21, 0),
(181, 'Tepeyahualco', 21, 0),
(182, 'Tepeyahualco de Cuauhtemoc', 21, 0),
(183, 'Tetela de Ocampo', 21, 0),
(184, 'Teteles de Avila Castillo', 21, 0),
(185, 'Teziutlan', 21, 0),
(186, 'Tianguismanalco', 21, 0),
(187, 'Tilapa', 21, 0),
(188, 'Tlachichuca', 21, 0),
(189, 'Tlacotepec de Benito Juarez', 21, 0),
(190, 'Tlacuilotepec', 21, 0),
(191, 'Tlahuapan', 21, 0),
(192, 'Tlaltenango', 21, 0),
(193, 'Tlanepantla', 21, 0),
(194, 'Tlaola', 21, 0),
(195, 'Tlapacoya', 21, 0),
(196, 'Tlapanala', 21, 0),
(197, 'Tlatlauquitepec', 21, 0),
(198, 'Tlaxco', 21, 0),
(199, 'Tochimilco', 21, 0),
(200, 'Tochtepec', 21, 0),
(201, 'Totoltepec de Guerrero', 21, 0),
(202, 'Tulcingo', 21, 0),
(203, 'Tuzamapan de Galeana', 21, 0),
(204, 'Tzicatlacoyan', 21, 0),
(205, 'Venustiano Carranza', 21, 0),
(206, 'Vicente Guerrero', 21, 0),
(207, 'Xayacatlan de Bravo', 21, 0),
(208, 'Xicotepec', 21, 0),
(209, 'Xicotlan', 21, 0),
(210, 'Xiutetelco', 21, 0),
(211, 'Xochiapulco', 21, 0),
(212, 'Xochiltepec', 21, 0),
(213, 'Xochitlan de Vicente Suarez', 21, 0),
(214, 'Xochitlan Todos Santos', 21, 0),
(215, 'Yaonahuac', 21, 0),
(216, 'Yehualtepec', 21, 0),
(217, 'Zacapala', 21, 0),
(218, 'Zacapoaxtla', 21, 0),
(219, 'Zacatlan', 21, 0),
(220, 'Zapotitlan', 21, 0),
(221, 'Zapotitlan de Mendez', 21, 0),
(222, 'Zaragoza', 21, 0),
(223, 'Zautla', 21, 0),
(224, 'Zihuateutla', 21, 0),
(225, 'Zinacatepec', 21, 0),
(226, 'Zongozotla', 21, 0),
(227, 'Zoquiapan', 21, 0),
(228, 'Zoquitlan', 21, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `idPersona` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL DEFAULT '0',
  `registro` datetime NOT NULL,
  `usuario` int(11) NOT NULL DEFAULT '0',
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `apaterno` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `amaterno` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `nacimiento` date NOT NULL,
  `entidad` int(11) NOT NULL DEFAULT '0',
  `curp` varchar(18) COLLATE utf8_spanish_ci NOT NULL,
  `ocr` varchar(13) COLLATE utf8_spanish_ci NOT NULL,
  `sexo` varchar(1) COLLATE utf8_spanish_ci NOT NULL,
  `edoCivil` tinyint(4) NOT NULL DEFAULT '0',
  `telefono` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `celular` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `domicilio` int(11) NOT NULL DEFAULT '0',
  `conyuge` int(11) NOT NULL DEFAULT '0',
  `aval` int(11) NOT NULL DEFAULT '0',
  `referencia` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`idPersona`, `sucursal`, `registro`, `usuario`, `nombre`, `apaterno`, `amaterno`, `nacimiento`, `entidad`, `curp`, `ocr`, `sexo`, `edoCivil`, `telefono`, `celular`, `domicilio`, `conyuge`, `aval`, `referencia`) VALUES
(1, 1, '2018-04-19 11:14:53', 3, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 4, 0, 0, 0),
(2, 1, '2018-04-19 11:16:21', 1, 'EPIFANIA', 'PASTOR', 'CRISTINO', '1979-04-07', 21, 'PACE790407MPLSRP09', '1947076645955', 'M', 1, '----------', '2381512594', 0, 0, 0, 0),
(3, 1, '2018-04-19 11:33:44', 3, 'ALMA ELIA', 'VALERIO', 'LECHUGA', '1969-07-20', 21, 'VALA690720MPLLCL00', '1992075813527', 'M', 1, '----------', '2381682735', 1, 0, 0, 0),
(4, 1, '2018-05-03 14:36:13', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 1, 0, 0, 0),
(5, 1, '2018-05-19 12:06:58', 2, 'ANGELINA', 'CONTRERA', 'MARTINEZ', '1972-03-28', 20, 'NSANDOXKSMDKOKN3IO', '1545615665867', 'M', 0, '----------', '2381209502', 1, 0, 0, 0);

--
-- Disparadores `personas`
--
DELIMITER $$
CREATE TRIGGER `copiar_historial_personas` BEFORE UPDATE ON `personas` FOR EACH ROW INSERT INTO historial_personas (`idPersona`, modificacion, usuario, `nombre`, `apaterno`, `amaterno`, `nacimiento`, `entidad`, `curp`, `ocr`, `sexo`, `edoCivil`, `telefono`, `celular`, `domicilio`, `conyuge`, `aval`, `referencia`) VALUES (old.idPersona, now(), old.usuario, old.nombre, old.apaterno, old.amaterno, old.nacimiento, old.entidad, old.curp, old.ocr, old.sexo, old.edoCivil, old.telefono, old.celular, old.domicilio, old.conyuge, old.aval, old.referencia)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas_clientes`
--

CREATE TABLE `personas_clientes` (
  `idCliente` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL DEFAULT '0',
  `usuario` int(11) NOT NULL DEFAULT '0',
  `registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `adc` varchar(4) COLLATE utf8_spanish_ci NOT NULL DEFAULT '0',
  `idPersona` int(11) NOT NULL DEFAULT '0',
  `ingresos` decimal(8,2) NOT NULL DEFAULT '0.00',
  `egresos` decimal(8,2) NOT NULL DEFAULT '0.00',
  `dependientes` int(11) NOT NULL DEFAULT '1',
  `ocupacion` int(11) NOT NULL DEFAULT '0',
  `estudios` int(11) NOT NULL DEFAULT '0',
  `empresa` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `domicilio_empresa` varchar(150) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tel_empresa` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `horario_entrada` time DEFAULT NULL,
  `horario_salida` time DEFAULT NULL,
  `tipo_vivienda` int(11) DEFAULT NULL,
  `propietario` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `vigencia_contrato` date DEFAULT NULL,
  `tiempo_residencia` tinyint(4) DEFAULT NULL,
  `score` int(3) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `actividad` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `personas_clientes`
--

INSERT INTO `personas_clientes` (`idCliente`, `sucursal`, `usuario`, `registro`, `adc`, `idPersona`, `ingresos`, `egresos`, `dependientes`, `ocupacion`, `estudios`, `empresa`, `domicilio_empresa`, `tel_empresa`, `horario_entrada`, `horario_salida`, `tipo_vivienda`, `propietario`, `vigencia_contrato`, `tiempo_residencia`, `score`, `status`, `actividad`) VALUES
(1, 1, 2, '2018-05-31 23:25:54', 'z1-2', 1, '1500.00', '1000.00', 2, 3, 2, 'ABARROTES CADENA', 'INDEPENDENCIA ORIENTE', '2383828734', '08:30:00', '23:23:28', 2, 'DON PEDRO', '2020-01-10', 3, 0, 0, 1),
(2, 1, 2, '2018-06-01 01:15:44', 'Z2-2', 5, '2000.00', '1600.00', 3, 1, 3, 'NINGUNA', 'NINGUNA', '0000000000', '00:00:00', '00:00:00', 2, 'SU MARIDO PEPE', '0001-12-31', 0, 0, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas_empleados`
--

CREATE TABLE `personas_empleados` (
  `idStaff` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL,
  `usuario` int(11) NOT NULL,
  `registro` datetime NOT NULL,
  `idPersona` int(11) NOT NULL,
  `cargo` int(11) NOT NULL,
  `estudios` int(11) NOT NULL,
  `departamento` int(11) NOT NULL,
  `salario` int(11) NOT NULL,
  `entrada` time NOT NULL,
  `salida` time NOT NULL,
  `dias` varchar(100) NOT NULL,
  `llamara` varchar(150) DEFAULT NULL,
  `fecha_incorp` date NOT NULL,
  `efectivo` int(11) NOT NULL,
  `codigo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personas_empleados`
--

INSERT INTO `personas_empleados` (`idStaff`, `sucursal`, `usuario`, `registro`, `idPersona`, `cargo`, `estudios`, `departamento`, `salario`, `entrada`, `salida`, `dias`, `llamara`, `fecha_incorp`, `efectivo`, `codigo`) VALUES
(1, 1, 3, '2018-06-06 03:07:32', 1, 1, 7, 7, 1500, '03:07:12', '03:07:12', 'Lunes-Martes-Miércoles-Jueves-Viernes-Sábado', 'SU MAMÁ', '2017-06-30', 0, '01C-A001-300620'),
(2, 1, 3, '2018-06-06 03:08:39', 4, 1, 9, 2, 1000, '03:08:16', '03:08:16', 'Lunes-Martes-Miércoles-Jueves-Viernes-Sábado', 'KASMLKASLKAMS', '2017-06-30', 0, '01C-A002-300620');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas_empleados_adc`
--

CREATE TABLE `personas_empleados_adc` (
  `idAdc` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL,
  `idStaff` int(11) NOT NULL,
  `agencia` smallint(6) NOT NULL COMMENT 'zona local a la que pertenece',
  `vacante` tinyint(2) NOT NULL COMMENT '1 a 12 por zona',
  `nivel` tinyint(1) NOT NULL COMMENT '1=novel, 2=consolidado, 3 = master, 4 = elite'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos_solicitudes`
--

CREATE TABLE `prestamos_solicitudes` (
  `idSolicitud` int(11) NOT NULL,
  `monto` int(11) NOT NULL,
  `tasa` decimal(10,0) NOT NULL,
  `plazo` int(11) NOT NULL,
  `cliente` int(11) NOT NULL,
  `usuario` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `prestamos_solicitudes`
--

INSERT INTO `prestamos_solicitudes` (`idSolicitud`, `monto`, `tasa`, `plazo`, `cliente`, `usuario`, `sucursal`, `fecha`, `hora`) VALUES
(1, 1000, '13', 20, 6, 2, 1, '2018-04-12', '00:10:05'),
(2, 2000, '13', 20, 6, 2, 1, '2018-04-18', '12:32:11'),
(3, 3000, '13', 20, 7, 2, 1, '2018-04-14', '14:18:48'),
(4, 1000, '13', 20, 7, 2, 1, '2018-04-18', '13:24:37'),
(5, 1000, '13', 20, 5, 2, 1, '2018-04-18', '13:27:44');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursales`
--

CREATE TABLE `sucursales` (
  `idSucursal` int(11) NOT NULL,
  `sucursal` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `sucursales`
--

INSERT INTO `sucursales` (`idSucursal`, `sucursal`) VALUES
(1, 'Tehuacán'),
(2, 'Zacatelco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_cargo`
--

CREATE TABLE `tipo_cargo` (
  `idCargo` int(11) NOT NULL,
  `cargo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `tipo` tinyint(1) NOT NULL COMMENT '0 = admin, 1 = operativo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_cargo`
--

INSERT INTO `tipo_cargo` (`idCargo`, `cargo`, `tipo`) VALUES
(1, 'Gerente Administrativo', 0),
(2, 'Gerente Operativo', 1),
(3, 'Gerente de Zona', 1),
(4, 'Gerente de Sucursal', 0),
(5, 'Admin. de Cartera', 1),
(6, 'Auxiliar administrativo', 0),
(7, 'Intendencia', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_domicilio`
--

CREATE TABLE `tipo_domicilio` (
  `idTipoDomicilio` int(11) NOT NULL,
  `tipo` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_domicilio`
--

INSERT INTO `tipo_domicilio` (`idTipoDomicilio`, `tipo`) VALUES
(1, 'Propia'),
(2, 'Rentada'),
(3, 'Prestada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_estudios`
--

CREATE TABLE `tipo_estudios` (
  `idTipoEstudios` int(11) NOT NULL,
  `estudios` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_estudios`
--

INSERT INTO `tipo_estudios` (`idTipoEstudios`, `estudios`) VALUES
(1, 'Primaria'),
(2, 'Secundaria'),
(3, 'Preparatoria/Bachillerato'),
(4, 'Técnico Bachiller'),
(5, 'Técnico Superior Universitario'),
(6, 'Licenciatura'),
(7, 'Ingeniería'),
(8, 'Maestría'),
(9, 'Doctorado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_ocupaciones`
--

CREATE TABLE `tipo_ocupaciones` (
  `idTipo` int(11) NOT NULL,
  `ocupacion` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_ocupaciones`
--

INSERT INTO `tipo_ocupaciones` (`idTipo`, `ocupacion`) VALUES
(1, 'Ama de casa'),
(2, 'Comerciante'),
(3, 'Empleado'),
(4, 'Estudiante');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_status_cliente`
--

CREATE TABLE `tipo_status_cliente` (
  `idStatus` int(11) NOT NULL,
  `status` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_usuarios`
--

CREATE TABLE `tipo_usuarios` (
  `idTipoUsuario` int(11) NOT NULL,
  `tipo` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo_usuarios`
--

INSERT INTO `tipo_usuarios` (`idTipoUsuario`, `tipo`) VALUES
(1, 'Administación'),
(2, 'Contabilidad'),
(3, 'Recursos Humanos'),
(4, 'Gerencia Operativa'),
(5, 'Diseño Gráfico'),
(6, 'Metodología'),
(7, 'Sistemas informáticos'),
(8, 'Administración de cartera');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `tipo` int(11) NOT NULL,
  `idStaff` int(11) NOT NULL,
  `idSucursal` int(11) NOT NULL,
  `fotografia` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `usuario`, `password`, `tipo`, `idStaff`, `idSucursal`, `fotografia`) VALUES
(1, '', '', 0, 1, 1, NULL),
(2, 'Jaasiel', 'admin', 1, 2, 1, NULL),
(3, 'almarh3', 'rrhh', 3, 3, 1, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD PRIMARY KEY (`idDomicilio`),
  ADD KEY `idDomicilio` (`idDomicilio`);

--
-- Indices de la tabla `estados`
--
ALTER TABLE `estados`
  ADD PRIMARY KEY (`idEstado`),
  ADD KEY `idEstado` (`idEstado`);

--
-- Indices de la tabla `historial_personas`
--
ALTER TABLE `historial_personas`
  ADD PRIMARY KEY (`idHistorialPersonas`);

--
-- Indices de la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD PRIMARY KEY (`idMunicipio`),
  ADD KEY `estado` (`estado`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`idPersona`),
  ADD KEY `sucursal` (`sucursal`);

--
-- Indices de la tabla `personas_clientes`
--
ALTER TABLE `personas_clientes`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `personas_empleados`
--
ALTER TABLE `personas_empleados`
  ADD PRIMARY KEY (`idStaff`);

--
-- Indices de la tabla `personas_empleados_adc`
--
ALTER TABLE `personas_empleados_adc`
  ADD PRIMARY KEY (`idAdc`);

--
-- Indices de la tabla `prestamos_solicitudes`
--
ALTER TABLE `prestamos_solicitudes`
  ADD PRIMARY KEY (`idSolicitud`);

--
-- Indices de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD PRIMARY KEY (`idSucursal`);

--
-- Indices de la tabla `tipo_cargo`
--
ALTER TABLE `tipo_cargo`
  ADD PRIMARY KEY (`idCargo`);

--
-- Indices de la tabla `tipo_domicilio`
--
ALTER TABLE `tipo_domicilio`
  ADD PRIMARY KEY (`idTipoDomicilio`),
  ADD KEY `idTipoDomicilio` (`idTipoDomicilio`);

--
-- Indices de la tabla `tipo_estudios`
--
ALTER TABLE `tipo_estudios`
  ADD PRIMARY KEY (`idTipoEstudios`);

--
-- Indices de la tabla `tipo_ocupaciones`
--
ALTER TABLE `tipo_ocupaciones`
  ADD PRIMARY KEY (`idTipo`);

--
-- Indices de la tabla `tipo_status_cliente`
--
ALTER TABLE `tipo_status_cliente`
  ADD PRIMARY KEY (`idStatus`);

--
-- Indices de la tabla `tipo_usuarios`
--
ALTER TABLE `tipo_usuarios`
  ADD PRIMARY KEY (`idTipoUsuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `idDomicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `historial_personas`
--
ALTER TABLE `historial_personas`
  MODIFY `idHistorialPersonas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `municipios`
--
ALTER TABLE `municipios`
  MODIFY `idMunicipio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=229;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `personas_clientes`
--
ALTER TABLE `personas_clientes`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `personas_empleados`
--
ALTER TABLE `personas_empleados`
  MODIFY `idStaff` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `personas_empleados_adc`
--
ALTER TABLE `personas_empleados_adc`
  MODIFY `idAdc` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `prestamos_solicitudes`
--
ALTER TABLE `prestamos_solicitudes`
  MODIFY `idSolicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  MODIFY `idSucursal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipo_cargo`
--
ALTER TABLE `tipo_cargo`
  MODIFY `idCargo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tipo_domicilio`
--
ALTER TABLE `tipo_domicilio`
  MODIFY `idTipoDomicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipo_estudios`
--
ALTER TABLE `tipo_estudios`
  MODIFY `idTipoEstudios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `tipo_ocupaciones`
--
ALTER TABLE `tipo_ocupaciones`
  MODIFY `idTipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tipo_status_cliente`
--
ALTER TABLE `tipo_status_cliente`
  MODIFY `idStatus` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipo_usuarios`
--
ALTER TABLE `tipo_usuarios`
  MODIFY `idTipoUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD CONSTRAINT `municipios_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`idEstado`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `personas`
--
ALTER TABLE `personas`
  ADD CONSTRAINT `personas_ibfk_1` FOREIGN KEY (`sucursal`) REFERENCES `sucursales` (`idSucursal`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
