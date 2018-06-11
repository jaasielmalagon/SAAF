-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-05-2018 a las 21:48:41
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
-- Estructura de tabla para la tabla `calles`
--

CREATE TABLE `calles` (
  `idCalle` int(11) NOT NULL,
  `calle` varchar(150) COLLATE utf8_spanish_ci NOT NULL,
  `colonia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `calles`
--

INSERT INTO `calles` (`idCalle`, `calle`, `colonia`) VALUES
(1, '6 oriente', 206),
(2, 'PRIVADA JUAN PABLO II', 216),
(3, 'calle 8', 215),
(4, 'calle 7', 215),
(5, 'calle 18', 215),
(6, 'calle 22', 215),
(7, 'privada san juaquin', 216),
(8, '28 sur', 224),
(9, 'tlaloc', 224),
(10, '24 sur', 224),
(11, 'benito juarez', 224),
(12, 'avenida  enrique  mont solorzano', 224),
(13, 'vicente suarez', 156),
(14, '26 sur', 166),
(15, '25 poniente', 2),
(16, '14 sur', 2),
(17, 'gustavo diaz ordaz', 2),
(18, 'heroes de nacozari', 2),
(19, 'calle constituyentes', 2),
(20, '8 sur', 15),
(21, 'republica de peru', 15),
(22, '23 poniente', 15),
(23, 'republica de panama', 15),
(24, 'privada mariano matamoros', 22),
(25, '9  poniente', 100),
(26, 'miguel lerdo de tejada', 100),
(27, '6 sur', 100),
(28, '9 poniente', 100),
(29, 'alamo', 149),
(30, 'esmeralda', 67),
(31, 'francisco i madero', 6),
(32, '27 poniente', 6),
(33, '24 sur', 6),
(34, 'cerrada aldama', 98),
(35, '25 poniente', 18),
(36, 'lazaro cardenas', 60),
(37, '1 poniente', 200),
(38, '1 sur', 200),
(39, 'Santa Isabel ', 200),
(40, 'Avenida Chilac', 200),
(41, 'Hidalgo ', 200),
(42, 'Avenida Nacional ', 200),
(43, 'Victoria ', 200),
(44, ' Callejon Victoria ', 200),
(45, 'Cajeron Nacional ', 200),
(46, ' Primer Cajeron ', 200),
(47, ' Prolongacion  2 oriente', 200),
(48, '2  Poniente ', 200),
(49, 'Privada Nacional ', 200),
(50, '5 Norte ', 200),
(51, '6 sur ', 200),
(52, '5 Sur ', 200),
(53, '4 Sur ', 200),
(54, '3 Sur ', 200),
(55, '2 sur', 200),
(56, '19  sur ', 117),
(57, '17  sur', 117),
(58, '19  sur', 81),
(59, '17 sur', 81),
(60, '15 sur', 81),
(61, '23  sur', 81),
(62, '25 oriente ', 81),
(63, '17 oriente ', 135),
(64, 'Mexico ', 135),
(65, 'Privada 11 sur ', 117),
(66, '17 oriente', 117),
(67, 'Privada 3 oriente', 117),
(68, 'Hector  Lezama  Surroca', 117),
(69, '3 oriente', 117),
(70, 'tercera privada de la 3 oriente', 117),
(71, '13 sur ', 114),
(72, 'La paz ', 114),
(73, '13 oriente ', 114),
(74, 'Gabino Barrera ', 92),
(75, 'Gabino Barrera ', 142),
(76, 'Gabino Barrera ', 184),
(77, ' Prolongacion Gabino Barrera ', 92),
(78, '7 sur ', 123),
(79, '23 sur', 31),
(80, 'Privada Indio Pipila ', 27),
(81, '27sur ', 123),
(82, 'Imbiras ', 170),
(83, 'Fresnos ', 170),
(84, 'Arboledas', 170),
(85, 'Gigante  Valdivia ', 153),
(86, 'Camino Viejo a san Diego ', 153),
(87, 'Estrella Polar ', 68),
(88, 'Hacienda el Carmen ', 128),
(89, 'Callejon Campeche ', 253),
(90, 'Quintana Roo', 253),
(91, 'Privada  Algodon ', 253),
(92, 'Coahuila  ', 253),
(93, 'callejon guerrerro ', 253),
(94, 'Nuevo Leon ', 253),
(95, 'Sonora Sur ', 253),
(96, '32 sur ', 253),
(97, 'Sonora ', 253),
(98, 'Avenida Campeche ', 253),
(99, '23 Poniente ', 253),
(100, 'Tabasco ', 253),
(101, '5 de septiembre ', 253),
(102, '21 poniente  ', 253),
(103, '25 poniente  ', 253),
(104, '19 poniente  ', 253),
(105, 'callejon Lomas Bonita', 253),
(106, 'Privada  25 Poniente', 253),
(107, 'Privada 30 sur ', 166),
(108, '30 sur ', 166),
(109, 'Cantera ', 226),
(110, 'Miguel Hidalgo  ', 226),
(111, 'Sonora', 254),
(112, 'Tercera privada de campeche', 254),
(113, 'Tamaulipas ', 254),
(114, 'Sonora Norte', 254),
(115, 'Coahulia', 254),
(116, 'Primera Privada 25 Poniente', 254),
(117, ' Privada  Maestro Joaquin ', 254),
(118, 'pino', 6),
(119, 'Jael ', 6),
(120, 'Privada Cristobal Colon ', 255),
(121, 'calle 19', 255),
(122, 'Retorno B ', 255),
(123, 'Calle 18', 255),
(124, 'Calle 24', 255),
(125, 'Privada  Independencia', 255),
(126, 'Privada  Independencia', 256),
(127, 'Durango ', 253),
(128, 'segunda privada de  nayarit', 254),
(129, 'Avenida  del Maestro  Aron Joaquin ', 253),
(130, 'Morelos ', 256),
(131, 'Privada Benito Juarez', 256),
(132, 'Guadalupe ', 256),
(133, 'Michoacan', 254),
(134, '15 poniente', 59),
(135, 'Avenida Rio Misisipi', 59),
(136, '16 sur ', 59),
(137, 'Santa Elvia ', 59),
(138, '20  sur ', 59),
(139, '25 sur ', 59),
(140, '11 poniente ', 59),
(141, 'Benito Juarez ', 59),
(142, 'Salinas de Gortari ', 59),
(143, 'Salinas', 59),
(144, '17 poniente', 59),
(145, 'Privada 5 de mayo ', 59),
(146, 'Privada  Benito Juarez ', 59),
(147, '22 sur', 59),
(148, '20 sur', 59),
(149, '22 SUR', 224),
(150, '19 Poniente ', 67),
(151, 'FRANCISCO I MADERO  ', 59),
(152, '13 PONIENTE', 156),
(153, 'Andador 10 sur ', 32),
(154, '12 SUR', 32),
(155, ' 17 poniente', 32),
(156, '10 sur', 98),
(157, 'PASCUAS ', 174),
(158, 'Hortencia ', 174),
(159, 'Alcatraces ', 174),
(160, 'privada gardenias ', 257),
(161, 'tulipanes', 257),
(162, 'Alcatraces ', 257),
(163, 'Alhuelican', 10),
(164, 'Axoxopan', 10),
(165, 'Casa Blanca ', 10),
(166, 'Agua las Flores', 10),
(167, 'Agua Ahuelican ', 11),
(168, 'Agua la virgen  ', 11),
(169, 'Agua los Martinez  ', 11),
(170, 'Agua las flores', 11),
(171, 'Agua san Miguel ', 11),
(172, 'Agua 5 señores', 11),
(173, 'Primer andador  15 poniente ', 32),
(174, 'PRIVADA BENITO JUAREZ', 59),
(175, '12 SUR', 59),
(177, 'Avenida del Maestro ', 98),
(178, '17 PONIENTE ', 32),
(179, 'Orquidias ', 167),
(180, 'tulipanes ', 167),
(181, 'Geranios ', 167),
(182, 'ORQUIDEAS', 202),
(183, 'PRIVADA  GARDENIAS', 257),
(184, 'Orquideas ', 167),
(185, 'Orquidea', 167),
(186, 'Av. Juventud ', 167),
(187, 'ITALIA ', 140),
(188, 'ITALIA ', 255),
(189, 'ITALIA ', 258),
(190, 'Alcatraz', 168),
(191, 'molino ', 258),
(192, 'Cuarta  cerrada de gladiola', 84),
(193, 'Geranios ', 168),
(195, 'TORRE LA VEGA', 245),
(196, 'Bastablado', 157),
(197, 'Hacienda  Santa Ana', 258),
(198, 'Emiliano Zapata ', 259),
(199, 'Privada Emiliano Zapata ', 259),
(200, 'Prolongacion  juarez', 259),
(201, '5 DE MAYO', 146),
(202, 'PRIVADA EMILIANO ZAPATA', 199),
(203, 'AVENIDA NACIONAL', 199),
(204, '3ER CALLEJON IGANACIO ZARAGOZA ', 199),
(205, '5 DE MAYO ', 199),
(206, 'IGNACIO ALLENDE', 199),
(207, '16 DE SEPTIEMBRE', 146),
(209, 'Juarez', 261),
(210, 'Juarez Poniente ', 261),
(211, 'Benito Juarez ', 261),
(212, 'Allende', 261),
(213, 'Revolucion ', 261),
(214, '1 de Mayo ', 261),
(215, 'Indio Pipila ', 261),
(216, 'Callejon Cristobal  Colon ', 261),
(217, 'Independencia ', 261),
(218, 'Cuauhtemoc  Sur ', 260),
(219, 'Hidalgo Sur  ', 260),
(220, 'Mariano Matamoros ', 260),
(221, 'Francisco I Madero ', 260),
(222, 'Privada  Leona Vicario ', 260),
(223, 'Emiliano Zapata ', 260),
(224, 'Morelos ', 260),
(225, 'Privada Morelos ', 260),
(226, '5 de Febrero ', 260),
(227, '3 Poniente ', 260),
(228, 'Libertad ', 260),
(229, 'Privada  Xochico ', 260),
(230, 'Calixto Barbosa', 260),
(231, 'Guerrero  Sur', 260),
(232, 'Narciso  Mendoza ', 262),
(233, 'Emiliano Zapata', 264),
(234, 'Privada Emiliano Zapata', 264),
(235, 'Prolongacion Juarez ', 264),
(236, 'Antonio Roanova', 263),
(237, 'Cuahutemoc', 263),
(238, 'Privada Primero de Mayo ', 265),
(239, 'Amador Nervo ', 263),
(240, 'Cuahutemoc', 262),
(241, 'Juarez', 266),
(242, 'Cuahutemoc', 266),
(243, 'Critobal Colon ', 261),
(244, 'Callejon  Mariano  Abasolo ', 261),
(245, 'Democracia ', 263),
(246, 'Libertad ', 263),
(247, 'Privada  Francisco I Madero ', 263),
(248, 'Privada Antonio  Ruonova ', 263),
(249, ' Antonio  Ruonova Vargas', 263),
(250, 'Rafael  Avila Camacho ', 263),
(251, 'Morelos Oriente ', 263),
(252, '5 de Mayo', 266),
(253, 'Morelos ', 266),
(254, '1 Norte ', 266),
(255, 'Rafael Avila Camacho  Oriente ', 262),
(256, 'Rafael Avila Camacho ', 262),
(257, 'Benito Juarez  Oriente ', 262),
(258, 'Privada Rafael  Avila Camacho ', 262),
(259, 'Callejon  Democracia ', 262),
(260, 'Guillermo Prieto ', 262),
(261, '11 Norte', 262),
(262, 'Av.  Juarez ', 261),
(263, 'Prolongacion Benito Juarez ', 264),
(264, 'Narciso Mendoza', 267),
(265, 'Juventud ', 268),
(266, 'Hidalgo Norte ', 266),
(267, 'Hidalgo Norte ', 262),
(268, 'Privada  2 de Abril ', 261),
(269, 'Abasolo ', 261),
(270, 'San Fernando ', 268),
(271, 'San Fernando  y Juventud ', 268),
(272, 'San Lucas', 268),
(273, '16 de Septiembre ', 268),
(274, 'Prolongacion Guerrero  Norte  ', 268),
(275, 'AV. Juventud ', 268),
(276, 'Privada Cristobal Colon ', 262),
(277, 'Prolongacion  Cristobal el  Colon ', 261),
(278, 'Moctezuma', 261),
(279, 'Guillerno Prieto ', 262),
(280, 'Morelos  ', 262),
(281, 'Revolucion Oriente  ', 262),
(282, 'Morelos', 263),
(283, 'Hidalgo Norte', 269),
(284, 'Cristobal Colon', 262),
(285, 'Guerrero Esquina Zaragoza ', 262),
(286, 'Revolucion ', 262),
(287, 'Privada 5 de Mayo ', 262),
(288, 'Privada  Revolucion ', 262),
(289, 'AV. Rafael Avila Camacho ', 262),
(290, 'Privada  Rafael Avila Camacho ', 262),
(291, 'Callejon  Rafael Avila  Camacho ', 262),
(292, 'Privada Revolucion ', 262),
(293, 'Privada Juarez', 262),
(294, 'Tercer Callejon Francisco I Madero ', 263),
(295, 'Privada Javier  Mina ', 263),
(296, 'Revolucion Poniente ', 262),
(297, 'Hidalgo  ', 262),
(298, 'Privada 20 de Noviembre ', 264),
(299, 'Hidalgo ', 266),
(300, '5  de febrero ', 261),
(301, 'Privada Libertad', 266),
(302, 'Leona Vicario ', 260),
(303, 'Independencia Sur', 260),
(304, 'Benito Juarez ', 266),
(305, 'Privada  Rafael Avila Camacho ', 266),
(306, 'Democracia  Sur ', 263),
(307, 'Antonio Ruanova Vargas', 263),
(308, 'Callejo  Mariano Abasolo ', 261),
(309, 'Antonio Roanova Oriente ', 263),
(310, 'Primera de Mayo  ', 261),
(311, '5 de Mayo ', 270),
(312, '5  de Mayo ', 270),
(313, 'Porfirio  Diaz', 279),
(314, 'Av. Independencia ', 279),
(315, '20 de Noviembre ', 279),
(316, 'Albaro Obregon  ', 279),
(317, 'Josefa Ortiz de Dominguez ', 279),
(318, 'Baja California  ', 279),
(319, 'Guadalupe Victoria ', 279),
(320, 'California Norte ', 279),
(321, 'Privada Albaro Obregon ', 279),
(322, 'Santa Florentina ', 279),
(323, 'BENITO JUAREZ', 280),
(324, '3 PONIENTE ', 280),
(325, 'AV. HIDALGO ', 280),
(326, '3 ORIENTE', 280),
(327, '5 PONIENTE', 280),
(328, 'ZARAGOZA ', 280),
(329, '2 PONIENTE', 280),
(330, 'MIGUEL HIDALGO ', 280),
(331, 'VICENTE GUERRERO ', 280),
(332, 'CUAHUTEMOC', 280),
(333, '10 SUR ', 280),
(334, 'AV.  MIGUEL HIDALGO ', 280),
(335, 'FRESNOS', 280),
(336, 'CUAHUTEMOC NORTE', 280),
(337, 'AV. NACIONAL ', 280),
(338, '5 ORIENTE ', 280),
(339, ' AV. JOSE MARIA PINO SUAREZ', 280),
(340, 'JOSE MARIA MORELOS', 280),
(341, 'AV. PINO SUAREZ ', 280),
(342, '45 PONIENTE ', 280),
(343, '4 ORIENTE ', 280),
(344, 'DESVIACION A CARNERO ', 280),
(345, 'CARRETERA FEDERAL ', 280),
(346, 'GUATEMA ', 283),
(347, 'INSURGENTES', 283),
(348, 'EL ARENAL ', 284),
(349, 'MELCHOR O CAMPO', 285),
(350, 'AV. MELCHOR O CAMPO ', 285),
(351, '5 DE MAYO ', 283),
(352, 'NACIONAL PONIENTE ', 283),
(353, 'AV. NACIONAL PONIENTE ', 283),
(354, 'AV. NACIONAL PONIENTE ', 287),
(355, 'PRIVADA  SAN SALVADOR ', 283),
(356, 'PRIVADA  SAN SALVADOR ', 286),
(357, 'CONSTITUCION ', 283),
(358, 'PRIVADA BENITO JUAREZ ', 286),
(359, 'LIBERTAD', 288),
(360, 'AV. 5  DE MAYO ', 288),
(361, '5 DE MAYO ', 288),
(362, 'CALLEJON 5 DE MAYO ', 288),
(363, 'VICENTE GUERRERO ', 286),
(364, 'CORREGIDORA ', 283),
(365, 'CANAL 12', 283),
(366, 'BENITO JUAREZ ', 283),
(368, 'AV. MELCHOR OCAMPOS', 285),
(369, 'AV. MELCHOR OCAMPOS', 294),
(370, 'EL ARENAL ', 283),
(371, 'MELCHOR O CAMPO', 293),
(372, 'MELCHOR OCAMPO', 293),
(373, 'AV.  NACIONAL  PONIENTE ', 287),
(374, 'PRIVADA SAN SALVADOR ', 286),
(375, 'CONSTIUCION ', 287),
(376, '5 DE MAYO', 292),
(377, 'SANTA CRUZ ', 292),
(378, 'LIBERTAD ', 292),
(379, 'CALLEGON VICTORIANO ', 200),
(380, '3 PONIENTE ', 200),
(381, 'LIRIO', 189),
(382, 'NUVE', 189),
(383, 'HORTENCIA ', 189),
(384, 'GLADIOLA', 189),
(385, 'NARDO ', 189),
(386, 'SEGUNDA CERRADA', 295),
(387, 'AGUA SANTA ', 298),
(388, '20 ORIENTE ', 297),
(389, 'PRIMERA PRIV. 30A ORIENTE ', 296),
(390, 'JOSE MARIA MORELOS ', 159),
(391, 'PRIVADA 40 ORIENTE ', 159),
(392, '9  NORTE ', 159),
(393, 'LAS FLORES', 159),
(394, '16 DE SEPTIEMBRE ', 159),
(395, 'NIÑOS HEROES  ', 159),
(396, 'PRIVADA 2 DE MAYO ', 159),
(397, '28 ORIENTE  ', 127),
(398, 'JOSE MARIA MOLEROS ', 219),
(399, '52 ORIENTE ', 213),
(400, 'PORFIRIO DIAZ ', 279),
(401, 'PRIVADA NACIONAL ', 272),
(402, 'SOLIDARIDAD', 273),
(403, 'ALVARO OBREGON ', 127),
(404, 'MIGUEL HIDALGO ', 219),
(405, '26 ORIENTE ', 127),
(406, 'DEL EJIDO ', 127),
(407, '1NORTE', 127),
(408, '34ORIENTE', 127),
(409, '16DE SEPTIEMBRE', 219),
(410, 'LOS FLORES', 219),
(411, 'ALVARO OBREGON', 77),
(412, 'AV.EJERCITO MEXICANO ', 77),
(413, 'PRIVADA EJERCITO MEXICANO ', 77),
(414, '26ORIENTE', 237),
(415, '1ERA PRIV. ARCUCARIAS', 237),
(416, '24DE FEBRERO ', 212),
(417, '6 PONIENTE', 252),
(418, '4 NORTE', 252),
(419, '2 NORTE', 252),
(420, 'MIGUEL HIDALGO', 127),
(421, '5 NORTE', 127),
(422, '7 NORTE', 127);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colonias`
--

CREATE TABLE `colonias` (
  `idColonia` int(11) NOT NULL,
  `colonia` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `asentamiento` int(11) NOT NULL,
  `cp` int(11) NOT NULL,
  `municipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `colonias`
--

INSERT INTO `colonias` (`idColonia`, `colonia`, `asentamiento`, `cp`, `municipio`) VALUES
(1, '15 de Mayo', 1, 75796, 167),
(2, '16 de Marzo', 2, 75766, 167),
(3, '18 de Marzo', 3, 75770, 167),
(4, '21 de Marzo', 2, 75859, 167),
(5, '24 de Febrero', 2, 75742, 167),
(6, '3 de Mayo', 2, 75765, 167),
(7, 'Aeropuerto', 2, 75718, 167),
(8, 'Aeropuerto 2a Seccion', 2, 75718, 167),
(9, 'Agricola el Porvenir', 2, 75758, 167),
(10, 'Agua Blanca I', 3, 75796, 167),
(11, 'Agua Blanca II', 3, 75793, 167),
(12, 'Agua Santa', 1, 75719, 167),
(13, 'Alatriste', 2, 75855, 167),
(14, 'Allende', 1, 75740, 167),
(15, 'America', 2, 75766, 167),
(16, 'Anahuac', 2, 75710, 167),
(17, 'Aquiles Serdan', 2, 75750, 167),
(18, 'Arboledas', 2, 75765, 167),
(19, 'Arcangel', 4, 75710, 167),
(20, 'Aviacion', 2, 75718, 167),
(21, 'Bellavista', 3, 75793, 167),
(22, 'Benito Juarez', 2, 75770, 167),
(23, 'Benito Juarez', 1, 75730, 167),
(24, 'Bosques de Reforma', 3, 75719, 167),
(25, 'Bosques de San Pedro', 3, 75740, 167),
(26, 'Buenos Aires', 2, 75730, 167),
(27, 'Centro de la Ciudad', 2, 75700, 167),
(28, 'Cerrada del Pirul', 3, 75730, 167),
(29, 'Cerritos', 2, 75731, 167),
(30, 'Chapultepec', 3, 75763, 167),
(31, 'Concepcion', 2, 75780, 167),
(32, 'Concordia', 3, 75763, 167),
(33, 'Constituyentes', 2, 75710, 167),
(34, 'Constituyentes 2a Seccion', 2, 75710, 167),
(35, 'Constituyentes 3a Seccion', 2, 75710, 167),
(36, 'Cuatro Caminos', 3, 75758, 167),
(37, 'Cuauhtemoc', 2, 75740, 167),
(38, 'Cultural', 3, 75795, 167),
(39, 'Del Empleado', 2, 75799, 167),
(40, 'Del Empleado Municipal', 2, 75743, 167),
(41, 'Del Valle', 2, 75740, 167),
(42, 'Del Valle 2a Seccion', 2, 75740, 167),
(43, 'Del Valle 3a Seccion', 2, 75740, 167),
(44, 'E S Mint Atlanta', 1, 75760, 167),
(45, 'El Calvario', 2, 75790, 167),
(46, 'El Carmen', 2, 75760, 167),
(47, 'El Convento', 3, 75710, 167),
(48, 'El Dorado', 3, 75730, 167),
(49, 'El Eden', 2, 75720, 167),
(50, 'El Eden 2a Seccion', 2, 75719, 167),
(51, 'El Humilladero', 3, 75790, 167),
(52, 'El Lucero', 3, 75758, 167),
(53, 'El Mirador', 2, 75780, 167),
(54, 'El Molino', 3, 75780, 167),
(55, 'El Palmar', 3, 75726, 167),
(56, 'El Paseo', 3, 75726, 167),
(57, 'El Pedregal', 3, 75726, 167),
(58, 'El Riego', 2, 75763, 167),
(59, 'El Riego', 1, 75760, 167),
(60, 'El Riego 1a Seccion', 2, 75763, 167),
(61, 'El Riego 2da Seccion', 2, 75763, 167),
(62, 'El Riego INFONAVIT', 1, 75760, 167),
(63, 'El Rosario', 3, 75780, 167),
(64, 'El Vergel', 2, 75758, 167),
(65, 'Electricistas', 2, 75741, 167),
(66, 'Emiliano Zapata', 2, 75730, 167),
(67, 'Esmeralda', 2, 75763, 167),
(68, 'Estrella del Sur', 3, 75790, 167),
(69, 'Europa', 3, 75793, 167),
(70, 'Ex-Hacienda', 6, 75760, 167),
(71, 'Ex-Hacienda San Lorenzo', 3, 75758, 167),
(72, 'FOVISSSTE El Rosario', 1, 75743, 167),
(73, 'FOVISSSTE Garci Crespo', 3, 75730, 167),
(74, 'Frailes II', 3, 75710, 167),
(75, 'Frailes III', 3, 75710, 167),
(76, 'Framboyanes', 1, 75740, 167),
(77, 'Francisco Sarabia', 2, 75730, 167),
(78, 'Francisco Villa', 2, 75750, 167),
(79, 'Garambuyo', 2, 75740, 167),
(80, 'Garci Crespo', 5, 75855, 167),
(81, 'Granjas de Oriente', 2, 75790, 167),
(82, 'Guadalupe', 2, 75730, 167),
(83, 'Guerrero', 1, 75740, 167),
(84, 'Hacienda El Humilladero', 3, 75796, 167),
(85, 'Hacienda El Humilladero II', 3, 75796, 167),
(86, 'Hacienda La Herradura IV', 3, 75743, 167),
(87, 'Hacienda Peñafiel', 1, 75710, 167),
(88, 'Hacienda San Vicente', 3, 75718, 167),
(89, 'Herradura', 3, 75740, 167),
(90, 'Ibero Tabachines', 3, 75710, 167),
(91, 'Ignacio Zaragoza', 2, 75770, 167),
(92, 'Independencia', 2, 75780, 167),
(93, 'INFONAVIT Granada', 3, 75760, 167),
(94, 'INFONAVIT Jardines de Tehuacan', 1, 75760, 167),
(95, 'INFONAVIT San Francisco', 1, 75758, 167),
(96, 'INFONAVIT San Nicolas', 1, 75710, 167),
(97, 'INFONAVIT San Rafael', 1, 75758, 167),
(98, 'INFONAVIT Venustiano Carranza', 1, 75760, 167),
(99, 'Ingeniero Pastor Rouaix', 2, 75710, 167),
(100, 'Insurgentes', 2, 75770, 167),
(101, 'Insurgentes', 6, 75740, 167),
(102, 'Insurgentes II', 3, 75740, 167),
(103, 'Insurgentes III', 3, 75740, 167),
(104, 'Jacarandas', 3, 75730, 167),
(105, 'Jardin', 2, 75720, 167),
(106, 'Jardines de Tehuacan', 2, 75769, 167),
(107, 'Jardines de Tehuacan (Ascote)', 3, 75769, 167),
(108, 'Jardines del Valle', 3, 75796, 167),
(109, 'Jorge Carreño', 7, 75766, 167),
(110, 'Juquilita', 2, 75859, 167),
(111, 'La Arcadia', 2, 75760, 167),
(112, 'La Huizachera', 2, 75718, 167),
(113, 'La Joya', 3, 75730, 167),
(114, 'La Paz', 2, 75790, 167),
(115, 'La Pedrera', 8, 75770, 167),
(116, 'La Presita', 2, 75730, 167),
(117, 'La Purisima', 2, 75784, 167),
(118, 'La Purisima 1a Secc', 2, 75784, 167),
(119, 'Las Flores', 2, 75786, 167),
(120, 'Las Garzas', 4, 75758, 167),
(121, 'Lazaro Cardenas Norte', 2, 75710, 167),
(122, 'Lazaro Cardenas Sur', 2, 75859, 167),
(123, 'Leyes de Reforma', 2, 75780, 167),
(124, 'Libertad', 2, 75768, 167),
(125, 'Lobera', 2, 75720, 167),
(126, 'Loma Dorada', 2, 75726, 167),
(127, 'Lomas de la Soledad', 2, 75719, 167),
(128, 'Los Alamos', 3, 75790, 167),
(129, 'Los Cipreses', 3, 75740, 167),
(130, 'Los Delfines', 3, 75796, 167),
(131, 'Los Frailes I', 3, 75710, 167),
(132, 'Los Fresnos', 3, 75857, 167),
(133, 'Los Laureles', 3, 75758, 167),
(134, 'Los Manantiales', 2, 75855, 167),
(135, 'Los Pinos', 2, 75797, 167),
(136, 'Los Pinos', 1, 75710, 167),
(137, 'Los Pirules', 3, 75720, 167),
(138, 'Los Reyes', 2, 75725, 167),
(139, 'Luis Donaldo Colosio Murrieta', 2, 75726, 167),
(140, 'Magdalena Cuayucatepec', 9, 75853, 167),
(141, 'Maravillas', 2, 75859, 167),
(142, 'Maridol', 1, 75719, 167),
(143, 'Mazateca', 2, 75859, 167),
(144, 'Mexico 68', 2, 75764, 167),
(145, 'Miguel Hidalgo', 2, 75790, 167),
(146, 'Miguel Romero', 2, 75758, 167),
(147, 'Mision Madrid', 2, 75859, 167),
(148, 'Moctezuma', 2, 75740, 167),
(149, 'Morelos', 2, 75770, 167),
(150, 'Morelos', 1, 75780, 167),
(151, 'Municipio Libre', 2, 75859, 167),
(152, 'Nacional', 2, 75794, 167),
(153, 'Nicolas Bravo', 2, 75790, 167),
(154, 'Nicolas Bravo 2a Seccion', 2, 75796, 167),
(155, 'Nicolas Bravo 3a Seccion', 3, 75790, 167),
(156, 'Niños Heroes', 2, 75760, 167),
(157, 'Nueva España', 3, 75793, 167),
(158, 'Obrero Peñafiel', 2, 75740, 167),
(159, 'Observatorio', 2, 75720, 167),
(160, 'Palmas', 2, 75720, 167),
(161, 'Paraiso de Jesus', 2, 75726, 167),
(162, 'Paseo de San Pedro', 3, 75740, 167),
(163, 'Paseos de Reforma', 3, 75719, 167),
(164, 'Plaza', 3, 75758, 167),
(165, 'Primavera', 3, 75758, 167),
(166, 'Puebla', 2, 75768, 167),
(167, 'Puerta del Sol', 2, 75796, 167),
(168, 'Puesta del Sol', 3, 75760, 167),
(169, 'Quinta Guadalupe', 2, 75740, 167),
(170, 'Rancho Grande', 3, 75790, 167),
(171, 'Rancho Grande II', 3, 75790, 167),
(172, 'Rancho Grande III', 3, 75790, 167),
(173, 'Rancho Las Flores I', 3, 75796, 167),
(174, 'Rancho Las Flores II', 3, 75793, 167),
(175, 'Rancho Viejo', 3, 75790, 167),
(176, 'Rancho Viejo II', 3, 75790, 167),
(177, 'Real de Santa Fe', 3, 75710, 167),
(178, 'Real San Felipe', 3, 75710, 167),
(179, 'Reforma', 3, 75760, 167),
(180, '4 el Paseo', 10, 75758, 167),
(181, '4 San Pedro', 3, 75740, 167),
(182, 'Resurecci?n', 2, 75858, 167),
(183, 'Revolucion', 2, 75796, 167),
(184, 'Ricardo Flores Magon', 2, 75780, 167),
(185, 'Rinc?n de los Cipreses', 2, 75740, 167),
(186, 'San Angel', 2, 75718, 167),
(187, 'San Angel Acoquiaco', 1, 75740, 167),
(188, 'San Antonio', 4, 75760, 167),
(189, 'San Antonio Viveros', 2, 75758, 167),
(190, 'San Cristobal Tepetiopan', 9, 75853, 167),
(191, 'San Diego Chalma', 9, 75859, 167),
(192, 'San Diego I', 3, 75859, 167),
(193, 'San Diego II', 3, 75859, 167),
(194, 'San Francisco', 2, 75726, 167),
(195, 'San Isidro Sur', 2, 75859, 167),
(196, 'San Jose Tochapa', 2, 75730, 167),
(197, 'San Lorenzo', 1, 75855, 167),
(198, 'San Lorenzo', 11, 75750, 167),
(199, 'San Lorenzo Teotipilco', 9, 75855, 167),
(200, 'San Marcos Necoxtla', 9, 75859, 167),
(201, 'San Martin', 2, 75726, 167),
(202, 'San Miguel', 2, 75793, 167),
(203, 'San Nicolas', 3, 75726, 167),
(204, 'San Nicolas Tetitzintla', 2, 75710, 167),
(205, 'San Pablo Tepetzingo', 9, 75859, 167),
(206, 'San Pedro Acoquiaco', 2, 75740, 167),
(207, 'San Rafael', 2, 75758, 167),
(208, 'San Vicente Ferrer', 2, 75718, 167),
(209, 'Santa Ana Teloxtoc', 9, 75856, 167),
(210, 'Santa Catarina de Siena', 2, 75855, 167),
(211, 'Santa Catarina Otzolotepec', 9, 75855, 167),
(212, 'Santa Cecilia', 2, 75855, 167),
(213, 'Santa Cruz', 2, 75720, 167),
(214, 'Santa Cruz Acapa', 9, 75859, 167),
(215, 'Santa Maria', 3, 75857, 167),
(216, 'Santa Maria Coapan', 9, 75857, 167),
(217, 'Santa Maria VI', 3, 75770, 167),
(218, 'Santa Monica', 3, 75790, 167),
(219, 'Santiago de Tula', 12, 75720, 167),
(220, 'Santiago de Tula', 8, 75740, 167),
(221, 'Santo Domingo', 2, 75770, 167),
(222, 'Sarabia', 6, 75730, 167),
(223, 'Sotolin', 2, 75784, 167),
(224, 'Tehuacan', 2, 75768, 167),
(225, 'Tehuacan (Tehuacan)', 13, 75717, 167),
(226, 'Tehuantepec', 2, 75855, 167),
(227, 'Tepeyac', 2, 75726, 167),
(228, 'Valle del Sol', 3, 75758, 167),
(229, 'Valle Sur', 3, 75795, 167),
(230, 'Venustiano Carranza', 2, 75760, 167),
(231, 'Vicente Suarez', 3, 75760, 167),
(232, 'Villa Alhuelican', 3, 75725, 167),
(233, 'Villa Benavente', 3, 75719, 167),
(234, 'Villa Granadas', 2, 75732, 167),
(235, 'Villa Manantiales', 3, 75857, 167),
(236, 'Villa Verona II', 3, 75719, 167),
(237, 'Villas Campestre', 3, 75720, 167),
(238, 'Villas de la Loma', 3, 75859, 167),
(239, 'Villas del Sol', 3, 75730, 167),
(240, 'Villas Esmeralda', 3, 75790, 167),
(241, 'Villas las Palmas', 2, 75719, 167),
(242, 'Villas Paraiso', 3, 75726, 167),
(243, 'Villas Puerta del Sol II', 3, 75796, 167),
(244, 'Villas Reforma', 3, 75719, 167),
(245, 'Villas Universidad', 3, 75793, 167),
(246, 'Villas Verona I', 3, 75719, 167),
(247, 'Villas Virginia', 3, 75726, 167),
(248, 'Vista Hermosa', 2, 75858, 167),
(249, 'Vivir', 3, 75859, 167),
(250, 'Xochipilli', 3, 75770, 167),
(251, 'Zona Alta', 3, 75750, 167),
(252, 'Zaragoza', 2, 75700, 167),
(253, 'Mexico sur ', 2, 75764, 167),
(254, 'MEXICO ', 2, 75754, 167),
(255, ' Fraccionamiento  Santa Maria Coapan ', 2, 75857, 167),
(256, 'La Asuncion  ', 2, 75857, 167),
(257, 'fraccionamiento puerta del sol ', 2, 75796, 167),
(258, 'Mayorazgo', 2, 75793, 167),
(259, 'San Miguel', 2, 75910, 167),
(260, 'Coculco', 8, 0, 21),
(261, 'Guadalupe', 8, 0, 21),
(262, 'Fatima ', 8, 0, 21),
(263, 'Gonzalisco ', 8, 0, 21),
(264, 'San Miguel ', 8, 0, 21),
(265, 'Las Plamas ', 2, 0, 21),
(266, 'Centro Ajalpan ', 2, 0, 21),
(267, 'Santa Cruz ', 2, 0, 21),
(268, 'San Antonio ', 2, 0, 21),
(269, 'Teopuxco ', 8, 0, 21),
(270, 'Doctor Miguel Romero ', 2, 0, 167),
(271, 'San Lorenzo ', 9, 0, 167),
(272, 'Manantiales Tehuacan ', 2, 0, 167),
(273, 'Juan Pablo II', 2, 0, 167),
(274, 'Santa Catalina  de Siena ', 2, 0, 167),
(275, 'Lucero San Rafael ', 3, 0, 167),
(276, 'Francisco I Madero ', 9, 0, 167),
(277, 'Cristo  Rey', 9, 0, 167),
(278, '3 de Mayo ', 9, 0, 167),
(279, 'Monte Chiquito ', 2, 0, 167),
(280, 'FRANCISCO I. MADERO', 9, 0, 172),
(281, 'CRISTO REY ', 2, 0, 172),
(282, '3 DE MAYO ', 2, 0, 172),
(283, 'SAN BARTOLO TEONTEPEC ', 9, 0, 172),
(284, 'BARRIO 2 SAN BARTOLO ', 9, 0, 172),
(285, 'JOSE MA. PINO SUAREZ', 9, 0, 172),
(286, 'SANTA CRUZ ', 9, 0, 172),
(287, 'PINO SUAREZ ', 9, 0, 172),
(288, '5 DE MAYO ', 2, 0, 172),
(289, 'SANTA CRUZ ', 2, 0, 172),
(290, ' CUARTO SAN BARTOLO ', 8, 0, 172),
(291, 'LA CAÑADA ', 8, 0, 172),
(293, 'JOSE MARIA PINO SUAREZ ', 9, 0, 172),
(294, 'JOSE MARIA MORELOS', 9, 0, 172),
(295, 'CAMINOS  EL PORVENIR ', 3, 0, 167),
(296, 'VILLAS VERONA II', 3, 0, 167),
(297, 'UNIDAD JARDIN ', 3, 0, 167),
(298, 'AGUA SANTA', 3, 0, 167);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `idDomicilio` int(11) NOT NULL,
  `direccion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `latitud` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `longitud` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `tipo` int(11) NOT NULL COMMENT '1=propia, 2=rentada, 3=prestada',
  `propietario` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `vigencia_contrato` date NOT NULL,
  `tiempoResidencia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `domicilios`
--

INSERT INTO `domicilios` (`idDomicilio`, `direccion`, `latitud`, `longitud`, `tipo`, `propietario`, `vigencia_contrato`, `tiempoResidencia`) VALUES
(1, 'CALLE 1 SUR 108 INT 203, CENTRO DE LA CIUDAD, 75700 TEHUACÁN, PUE., MÉXICO', '18.4623504', '-97.3934058', 1, '', '0000-01-00', 0),
(2, 'CALLE 6 PTE 217, CENTRO, 75700 TEHUACÁN, PUE., MÉXICO', '18.4691768', '-97.3971585', 1, '', '0000-01-00', 0);

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
-- Estructura de tabla para la tabla `historial_domicilios`
--

CREATE TABLE `historial_domicilios` (
  `idHistorialDomicilio` int(11) NOT NULL,
  `idDomicilio` int(11) NOT NULL,
  `sucursal` int(11) NOT NULL,
  `calle` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `calle1` int(11) NOT NULL,
  `calle2` int(11) NOT NULL,
  `colonia` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `propietario` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `vigencia_contrato` date NOT NULL,
  `tiempoResidencia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
(10, 4, '2018-05-21 14:58:20', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 1, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_personas_clientes`
--

CREATE TABLE `historial_personas_clientes` (
  `idHistorialClientes` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL DEFAULT '0',
  `sucursal` int(11) NOT NULL DEFAULT '0',
  `usuario` int(11) NOT NULL DEFAULT '0',
  `registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `adc` int(11) NOT NULL DEFAULT '0',
  `idPersona` int(11) NOT NULL DEFAULT '0',
  `ingresos` decimal(8,2) NOT NULL DEFAULT '0.00',
  `egresos` decimal(8,2) NOT NULL DEFAULT '0.00',
  `dependientes` int(11) NOT NULL DEFAULT '1',
  `ocupacion` int(11) NOT NULL DEFAULT '0',
  `estudios` int(11) NOT NULL DEFAULT '0',
  `empresa` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `domicilio_empresa` varchar(150) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tel_empresa` varchar(10) COLLATE utf8_spanish_ci NOT NULL DEFAULT '----------',
  `horario_entrada` time NOT NULL DEFAULT '08:00:00',
  `horario_salida` time NOT NULL DEFAULT '20:00:00',
  `score` int(3) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `actividad` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
-- Estructura de tabla para la tabla `numerosdomiciliares`
--

CREATE TABLE `numerosdomiciliares` (
  `idNumero` int(11) NOT NULL,
  `numero` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `calle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `numerosdomiciliares`
--

INSERT INTO `numerosdomiciliares` (`idNumero`, `numero`, `calle`) VALUES
(1, '1801', 3),
(2, '3144', 4),
(3, '1266', 5),
(4, '1606', 6),
(5, '706', 8),
(6, '913', 8),
(7, '913', 9),
(8, '2618', 11),
(9, '620', 13),
(10, '10', 37),
(11, '7', 38),
(12, '4', 38),
(13, '9', 38),
(14, '3', 38),
(15, '10', 41),
(16, '7', 41),
(17, '23', 41),
(18, '30', 41),
(19, '4', 44),
(20, '12', 42),
(21, '6', 52),
(22, '17', 48),
(23, '2', 53),
(24, '8', 42),
(25, '68', 54),
(26, '11', 40),
(27, '4', 55),
(28, '20', 42),
(29, '1713', 58),
(30, '1713', 44),
(31, '1906 ', 59),
(32, '1720', 59),
(33, '1701', 60),
(34, '1102', 59),
(35, '2322', 61),
(36, '2322', 62),
(37, '1121', 57),
(38, '1713', 59),
(39, '1117', 63),
(40, '11', 65),
(41, '1533', 71),
(42, '1517', 72),
(43, '320  ', 67),
(44, '1531', 73),
(45, '1513', 74),
(46, '1513', 75),
(47, '307', 68),
(48, '713', 76),
(49, '1517', 64),
(50, '909', 69),
(51, '300', 78),
(52, '160', 79),
(53, '732', 80),
(54, '300', 81),
(55, '1510', 70),
(56, '2344', 82),
(57, '2314', 82),
(58, '2333', 83),
(59, '500', 85),
(60, '2114', 87),
(61, '532', 84),
(62, '1520', 72),
(63, '1115', 63),
(64, '8', 65),
(65, '914', 86),
(66, '1105', 29),
(67, '1913', 88),
(68, '1913', 44),
(69, '1604', 120),
(70, '9', 89),
(71, '111', 109),
(72, '6', 107),
(73, '3207', 90),
(74, '1', 91),
(75, '1', 44),
(76, '1627', 121),
(77, '2515', 33),
(78, '2327', 115),
(79, '2112', 108),
(80, '3005', 93),
(81, '2501', 97),
(82, '1702', 97),
(83, '1323', 127),
(84, '2314', 14),
(85, '9', 112),
(86, '2500', 111),
(87, '2111', 111),
(88, '2111', 44),
(89, '3408', 113),
(90, '1715', 111),
(91, '1140', 114),
(92, '2529', 96),
(93, '2310', 98),
(94, '2331', 115),
(95, '6', 128),
(96, '4', 107),
(97, '3430', 99),
(98, '2513', 118),
(99, '3414', 100),
(100, '3429', 100),
(101, '2324', 111),
(102, '3210', 100),
(103, '7', 116),
(104, '3430', 101),
(105, '2322', 129),
(106, '3408', 102),
(107, '2500', 102),
(108, '3211', 104),
(109, '1909', 105),
(110, '3415', 106),
(111, '2322', 117),
(112, '2530', 119),
(113, '60', 126),
(114, '3416', 104),
(115, '16', 130),
(116, '17', 112),
(117, '1903', 96),
(118, '3307-B', 122),
(119, '3423', 99),
(120, '3415', 103),
(121, '3415', 99),
(122, '9', 131),
(123, '2946', 123),
(124, '11', 132),
(125, '11 A', 44),
(126, '5001', 110),
(127, '3425', 133),
(128, '2946 A', 123),
(129, '2946 A', 44),
(130, '2364-A', 44),
(131, '2008', 134),
(132, '2004', 135),
(133, '1713', 136),
(134, '1315', 10),
(135, '1605', 150),
(136, '1019-C', 173),
(137, '1509', 136),
(138, '1527-D', 153),
(139, '2011', 137),
(140, '1110', 148),
(141, '915', 139),
(142, '838', 140),
(143, '821-4', 141),
(144, '1507', 142),
(145, '2021', 134),
(146, '1504-A', 143),
(147, '1609', 144),
(148, '7', 145),
(149, '1510', 10),
(150, '8214', 174),
(151, '2016', 134),
(152, '1711', 147),
(153, '1523-B', 154),
(154, '809', 141),
(155, '1304', 149),
(156, '1108', 151),
(157, '2008', 137),
(158, '1004-78', 177),
(159, 'EDF 525-2', 156),
(160, '622', 152),
(161, '703', 138),
(162, '1002-B', 178),
(163, '5109-A', 157),
(164, '4912-B', 183),
(165, '5112-B', 157),
(166, '5109-C', 157),
(167, '2314', 185),
(168, '4709- B', 186),
(169, '5109-A', 158),
(170, '2339-B', 161),
(171, '6501 PTO4', 189),
(172, '6501- 4', 154),
(173, '6501- 4', 189),
(174, '2514- A', 167),
(175, '2159- A', 182),
(176, '4714-A', 190),
(177, '3912- A', 168),
(178, '2337-B', 161),
(179, '3904-B', 169),
(180, '2514-A', 163),
(181, '3718-A', 171),
(182, '2109-B', 164),
(183, '3909- B', 166),
(184, '2349', 182),
(185, '2108-B', 165),
(186, '3716-B', 171),
(187, '5111-B', 158),
(188, '5110-B', 157),
(189, '5110-B', 154),
(190, '5114- C', 157),
(191, '3906', 166),
(192, '2503', 182),
(193, '2147', 182),
(194, '2143', 154),
(195, '2163', 182),
(196, '2151', 182),
(197, '6911-A', 191),
(198, '5114-C', 159),
(199, '2343-B', 161),
(200, '51110-C', 159),
(201, '5114-C', 157),
(202, '3702-A', 168),
(203, '3901-A', 172),
(204, '5106-B', 157),
(205, '5108-C', 158),
(206, '2505-A', 192),
(207, '4909-B', 193),
(208, '4114', 195),
(209, '4114', 154),
(210, '3917-B', 196),
(211, '2107-A', 197),
(212, '8', 210),
(213, '906', 218),
(214, '504', 219),
(215, '313', 220),
(216, '424', 221),
(217, '204', 223),
(218, '487', 221),
(219, '572', 236),
(220, '57', 224),
(221, '107', 238),
(222, '10', 225),
(223, '123', 225),
(224, '35', 212),
(225, '19', 239),
(226, '411', 227),
(227, '418', 221),
(228, '49', 213),
(229, '615', 228),
(230, '205', 228),
(231, '411', 221),
(232, '823', 215),
(233, '407', 240),
(234, '314 INT.2', 300),
(235, '20', 221),
(236, '420', 221),
(237, '911', 301),
(238, '5-A ', 302),
(239, '114', 241),
(240, '155', 214),
(241, '13', 303),
(242, '103', 230),
(243, '211', 304),
(244, '542', 302),
(245, '348', 221),
(246, '211', 241),
(247, '455', 231),
(248, '465', 237),
(249, '422', 221),
(250, '3', 217),
(251, '12', 215),
(252, '308', 245),
(253, '260', 212),
(254, '17', 252),
(255, '917- B', 255),
(256, '121', 262),
(257, '911', 256),
(258, '705', 257),
(259, '103', 209),
(260, '111', 305),
(261, '308', 306),
(262, '595', 307),
(263, '313', 247),
(264, '111', 290),
(265, '904', 250),
(266, '7', 243),
(267, '312', 250),
(268, '301', 253),
(269, '301', 252),
(270, '32', 256),
(271, '304', 253),
(272, '411', 259),
(273, '402', 213),
(274, '113', 308),
(275, '434', 254),
(276, '442', 309),
(277, '126', 260),
(278, '126', 261),
(279, '809', 212),
(280, '637', 266),
(281, '443', 267),
(282, '49', 214),
(283, '639', 267),
(284, '66', 277),
(285, '16', 267),
(286, '47', 310),
(287, '836', 267),
(288, '25', 214),
(289, '1104', 276),
(290, '623', 283),
(291, '830', 279),
(292, '22', 213),
(293, '754', 251),
(294, '754', 280),
(295, '713', 281),
(296, '39', 267),
(297, '35', 267),
(298, '810', 215),
(299, '2', 284),
(300, '211', 286),
(301, '831', 287),
(302, '627', 267),
(303, '211', 296),
(304, '122', 298),
(305, '806', 267),
(306, '726', 284),
(307, '810', 297),
(308, '415', 279),
(309, '416', 242),
(310, '612', 267),
(311, '412', 293),
(312, '416', 240),
(313, '127', 260),
(314, '311', 323),
(315, '406', 324),
(316, '44', 325),
(317, '103', 326),
(318, '106', 327),
(319, '211', 328),
(320, '109', 329),
(321, '410', 324),
(322, '158', 330),
(323, '808-1', 329),
(324, '517', 332),
(325, '501', 325),
(326, '105', 333),
(327, '2', 323),
(328, '502', 332),
(329, '313-2', 326),
(330, '525', 336),
(331, '108', 337),
(332, '3-313', 326),
(333, '307', 326),
(334, '508', 332),
(335, '302', 323),
(336, '407', 324),
(337, '212', 341),
(338, '313-1', 326),
(339, '406', 343),
(340, '120', 340),
(341, '504', 332),
(342, '104', 330),
(343, '503-3', 334),
(344, '3-2', 367),
(345, '4', 40),
(346, '3', 55),
(347, '13', 55),
(348, '5', 38),
(349, '6', 54),
(350, '4', 379),
(351, '9', 39),
(352, '14', 42),
(353, '4400', 381),
(354, '5005', 382),
(355, '4407', 383),
(356, '4812', 383),
(357, '4005', 384),
(358, '4606', 385),
(359, '7-37', 386),
(360, '106', 388),
(361, '2408- B', 387),
(362, '3002-B', 389),
(363, '4022', 390),
(364, '4032', 390),
(365, '909', 396),
(366, '4027', 390),
(367, '4019', 391),
(368, '722', 395),
(369, '3807', 394),
(370, '4043', 390),
(371, '4022', 391),
(372, '4011', 392),
(373, '4007', 393),
(374, '700', 396),
(375, '700307 INT-1', 397),
(376, '4017', 398),
(377, '712-2', 399),
(378, '7', 201),
(379, '124', 400),
(380, '132', 314),
(381, '132', 315),
(382, '6', 316),
(383, '23', 317),
(384, '36', 318),
(385, '414-3', 203),
(386, '1013', 318),
(387, '121', 319),
(388, '4', 320),
(389, '22', 322),
(390, '5', 402),
(391, '2806', 403),
(392, '3004', 404),
(393, '2416', 404),
(394, '727', 404),
(395, '731', 404),
(396, '322', 405),
(397, '3025', 406),
(398, '3010', 407),
(399, '112', 408),
(400, '310', 405),
(401, '310', 410),
(402, '2438', 409),
(403, '1808-4', 411),
(404, '144', 412),
(405, '141', 412),
(406, '335', 412),
(407, '1402', 413),
(408, '1808,INT.4A', 411),
(409, '322', 397),
(410, '133-A', 414),
(411, '117-B', 415),
(412, '130-A', 414),
(413, '3-2', 346),
(414, '63', 373),
(415, '6', 355),
(416, '7', 312),
(417, '1497-6', 401),
(418, '306', 416),
(419, '217', 417),
(420, '2416', 420);

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
(1, 1, '2018-04-19 11:14:53', 1, 'JAASIEL', 'MENDEZ', 'MALAGON', '1994-01-22', 21, 'MEMJ940122HPLNLS02', '1954093563992', 'H', 0, '----------', '2381721972', 2, 0, 0, 0),
(2, 1, '2018-04-19 11:16:21', 1, 'EPIFANIA', 'PASTOR', 'CRISTINO', '1979-04-07', 21, 'PACE790407MPLSRP09', '1947076645955', 'M', 1, '----------', '2381512594', 0, 0, 0, 0),
(3, 1, '2018-04-19 11:33:44', 3, 'ALMA ELIA', 'VALERIO', 'LECHUGA', '1969-07-20', 21, 'VALA690720MPLLCL00', '1992075813527', 'M', 1, '----------', '2381682735', 1, 0, 0, 0),
(4, 1, '2018-05-03 14:36:13', 2, 'NUEVA', 'PERSONA', 'DE PRUEBA', '1990-12-20', 21, 'NSD89SUD89SD989J3J', '9028910980289', 'M', 0, '----------', '2381209130', 2, 0, 0, 0),
(5, 1, '2018-05-19 12:06:58', 2, 'ANGELINA', 'CONTRERA', 'MARTINEZ', '1972-03-28', 20, 'NSANDOXKSMDKOKN3IO', '1545615665867', 'M', 0, '----------', '2381209502', 0, 0, 0, 0);

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
  `score` int(3) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  `actividad` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `personas_clientes`
--

INSERT INTO `personas_clientes` (`idCliente`, `sucursal`, `usuario`, `registro`, `adc`, `idPersona`, `ingresos`, `egresos`, `dependientes`, `ocupacion`, `estudios`, `empresa`, `domicilio_empresa`, `tel_empresa`, `horario_entrada`, `horario_salida`, `score`, `status`, `actividad`) VALUES
(1, 1, 2, '2018-05-16 12:56:20', 'Z1-1', 1, '1000.00', '500.00', 1, 3, 7, 'GRUPO AVANTE', '1 SUR 108 COL. CENTRO, TEHUACÁN PUEBLA', '2381234555', '10:00:00', '00:00:00', 0, 0, 1);

--
-- Disparadores `personas_clientes`
--
DELIMITER $$
CREATE TRIGGER `copiar_historial_personas_clientes` BEFORE UPDATE ON `personas_clientes` FOR EACH ROW INSERT INTO `historial_personas_clientes`(`idCliente`, `sucursal`, `usuario`, `registro`, `adc`, `idPersona`, `ingresos`, `egresos`, `dependientes`, `ocupacion`, `estudios`, `empresa`, `domicilio_empresa`, `tel_empresa`, `horario_entrada`, `horario_salida`, `score`, `status`, `actividad`) VALUES (old.`idCliente`, old.`sucursal`, old.`usuario`, old.`registro`, old.`adc`, old.`idPersona`, old.`ingresos`, old.`egresos`, old.`dependientes`, old.`ocupacion`, old.`estudios`, old.`empresa`, old.`domicilio_empresa`, old.`tel_empresa`, old.`horario_entrada`, old.`horario_salida`, old.`score`, old.`status`, old.`actividad`)
$$
DELIMITER ;

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
(1, 1, 3, '2018-05-17 14:09:03', 1, 1, 7, 7, 1500, '10:00:00', '15:00:00', 'Lunes-Martes-Miércoles-Jueves-Viernes-Sábado', 'PASTORA CELIA MALAGON RAMOS (MADRE) 2381314177', '2017-06-20', 500, '01C-O'),
(2, 1, 3, '2018-05-18 14:44:28', 3, 1, 6, 3, 1000, '09:00:00', '14:00:00', 'Lunes-Miércoles-Jueves-Viernes-Sábado', 'SU ESPOSO RENAUT', '2018-07-01', 0, '01C-O');

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

--
-- Volcado de datos para la tabla `personas_empleados_adc`
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
-- Estructura de tabla para la tabla `tipo_asentamiento`
--

CREATE TABLE `tipo_asentamiento` (
  `idTipoAsentamiento` int(11) NOT NULL,
  `tipoAsentamiento` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_asentamiento`
--

INSERT INTO `tipo_asentamiento` (`idTipoAsentamiento`, `tipoAsentamiento`) VALUES
(1, 'Unidad habitacional'),
(2, 'Colonia'),
(3, 'Fraccionamiento'),
(4, 'Residencial'),
(5, 'Estacion'),
(6, 'Conjunto habitacional'),
(7, 'Zona industrial'),
(8, 'Barrio'),
(9, 'Pueblo'),
(10, 'Condominio'),
(11, 'Exhacienda'),
(12, 'Ejido'),
(13, 'Aeropuerto');

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
(1, 'Gerente Administrativo', 1),
(2, 'Gerente Operativo', 0),
(3, 'Gerente de Zona', 0),
(4, 'Gerente de Sucursal', 1),
(5, 'Admin. de Cartera', 0),
(6, 'Auxiliar administrativo', 1),
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

--Calendario de actividades
CREATE TABLE ´cronograma´(
    ´idActividad´ INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ´fecha´ DATE NOT NULL,
    ´descripcion´ VARCHAR(100) NOT NULL DEFAULT NULL;
);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `calles`
--
ALTER TABLE `calles`
  ADD PRIMARY KEY (`idCalle`),
  ADD KEY `idCalle` (`idCalle`);

--
-- Indices de la tabla `colonias`
--
ALTER TABLE `colonias`
  ADD PRIMARY KEY (`idColonia`),
  ADD KEY `municipio` (`municipio`,`asentamiento`),
  ADD KEY `asentamiento` (`asentamiento`);

--
-- Indices de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD PRIMARY KEY (`idDomicilio`),
  ADD KEY `idDomicilio` (`idDomicilio`),
  ADD KEY `tipo` (`tipo`);

--
-- Indices de la tabla `estados`
--
ALTER TABLE `estados`
  ADD PRIMARY KEY (`idEstado`),
  ADD KEY `idEstado` (`idEstado`);

--
-- Indices de la tabla `historial_domicilios`
--
ALTER TABLE `historial_domicilios`
  ADD PRIMARY KEY (`idHistorialDomicilio`);

--
-- Indices de la tabla `historial_personas`
--
ALTER TABLE `historial_personas`
  ADD PRIMARY KEY (`idHistorialPersonas`);

--
-- Indices de la tabla `historial_personas_clientes`
--
ALTER TABLE `historial_personas_clientes`
  ADD PRIMARY KEY (`idHistorialClientes`);

--
-- Indices de la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD PRIMARY KEY (`idMunicipio`),
  ADD KEY `estado` (`estado`);

--
-- Indices de la tabla `numerosdomiciliares`
--
ALTER TABLE `numerosdomiciliares`
  ADD PRIMARY KEY (`idNumero`),
  ADD KEY `calle_fk` (`calle`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`idPersona`);

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
-- Indices de la tabla `tipo_asentamiento`
--
ALTER TABLE `tipo_asentamiento`
  ADD PRIMARY KEY (`idTipoAsentamiento`),
  ADD KEY `idTipoAsentamiento` (`idTipoAsentamiento`);

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
-- AUTO_INCREMENT de la tabla `calles`
--
ALTER TABLE `calles`
  MODIFY `idCalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=423;

--
-- AUTO_INCREMENT de la tabla `colonias`
--
ALTER TABLE `colonias`
  MODIFY `idColonia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=299;

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `idDomicilio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `historial_domicilios`
--
ALTER TABLE `historial_domicilios`
  MODIFY `idHistorialDomicilio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `historial_personas`
--
ALTER TABLE `historial_personas`
  MODIFY `idHistorialPersonas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `historial_personas_clientes`
--
ALTER TABLE `historial_personas_clientes`
  MODIFY `idHistorialClientes` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `municipios`
--
ALTER TABLE `municipios`
  MODIFY `idMunicipio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=229;

--
-- AUTO_INCREMENT de la tabla `numerosdomiciliares`
--
ALTER TABLE `numerosdomiciliares`
  MODIFY `idNumero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=421;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `personas_clientes`
--
ALTER TABLE `personas_clientes`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `personas_empleados`
--
ALTER TABLE `personas_empleados`
  MODIFY `idStaff` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `personas_empleados_adc`
--
ALTER TABLE `personas_empleados_adc`
  MODIFY `idAdc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

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
-- AUTO_INCREMENT de la tabla `tipo_asentamiento`
--
ALTER TABLE `tipo_asentamiento`
  MODIFY `idTipoAsentamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
-- Filtros para la tabla `colonias`
--
ALTER TABLE `colonias`
  ADD CONSTRAINT `colonias_ibfk_1` FOREIGN KEY (`municipio`) REFERENCES `municipios` (`idMunicipio`) ON UPDATE CASCADE,
  ADD CONSTRAINT `colonias_ibfk_2` FOREIGN KEY (`asentamiento`) REFERENCES `tipo_asentamiento` (`idTipoAsentamiento`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD CONSTRAINT `municipios_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`idEstado`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
