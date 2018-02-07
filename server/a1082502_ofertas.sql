
-- phpMyAdmin SQL Dump
-- version 2.11.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 09, 2012 at 03:53 PM
-- Server version: 5.1.57
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `a1082502_ofertas`
--

-- --------------------------------------------------------

--
-- Table structure for table `compras`
--

CREATE TABLE `compras` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(64) CHARACTER SET utf8 NOT NULL,
  `OWNER_ID` int(20) NOT NULL,
  `OFERTA_ID` int(20) NOT NULL,
  `TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODIGO` (`CODIGO`),
  KEY `OWNER_ID` (`OWNER_ID`,`OFERTA_ID`),
  KEY `OFERTA_ID` (`OFERTA_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `compras`
--

INSERT INTO `compras` VALUES(1, '1UUAYCS8J62GQ', 6, 6, '2012-09-08 08:14:56');
INSERT INTO `compras` VALUES(2, '1GFUKHLOGYR14', 5, 5, '2012-09-08 08:17:07');
INSERT INTO `compras` VALUES(3, 'W1BQKMFV4907', 2, 2, '2012-09-08 09:04:39');

-- --------------------------------------------------------

--
-- Table structure for table `ofertas`
--

CREATE TABLE `ofertas` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(256) NOT NULL,
  `DESCRIPCION` varchar(512) NOT NULL,
  `DESCUENTO` varchar(32) NOT NULL,
  `PRECIO` varchar(32) NOT NULL,
  `NEGOCIO` varchar(256) NOT NULL,
  `DIRECCION` varchar(256) NOT NULL,
  `LATITUD` varchar(128) NOT NULL,
  `LONGITUD` varchar(128) NOT NULL,
  `FECHA_CADUCIDAD` datetime NOT NULL,
  `IMAGEN` varchar(256) DEFAULT NULL,
  `CIUDAD` varchar(128) NOT NULL,
  `DISTRITO` varchar(128) DEFAULT NULL,
  `OWNER_ID` int(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `ofertas`
--

INSERT INTO `ofertas` VALUES(1, 'Menú Pasta o Pizza', 'Este restaurante se especializa en pastas y pizzas. Con una elaboración cuidada y sana disfrutarás de increíbles productos elaborados con materias primas de primera calidad.', '20 %', '16,44 €', 'Peccato di Gola', 'Avda. Paral·lel, 163	Barcelona, 08004 ', '41.375118', '2.156729', '2012-10-26 00:00:00', 'pecatoDiGola.jpg', 'Barcelona', 'Eixample', 1);
INSERT INTO `ofertas` VALUES(8, 'Menú para grupos', 'El arte de tapear ha llegado a Zaragoza gracias al restaurante L’Autentika, un local situado en el Camino de las Torres, en el distrito centro de la ciudad, que ofrece a sus clientes las mejores tapas, raciones y bocadillos de la zona', '10 %', '13,50 €', 'L''Autentika', 'Camino de las Torres, 15 (entrada por Gonzalo de Berceo)	Zaragoza, 50008 ', '41.645835', '-0.87494', '2012-10-28 00:00:00', 'lautentika.jpg', 'Zaragoza', 'Centro', 8);
INSERT INTO `ofertas` VALUES(2, 'Menú Philharmonic', 'The Philharmonic es un pub restaurante con ideología inglesa que fue inaugurado el 17 de mayo de 1998. Está situado en el centro de Barcelona y aquí se puede comer a cualquier hora del día, sentir la esencia de los pubs británicos tomando una cerveza inglesa', '50 %', '9,80 € (IVA incluido)', 'The Philharmonic', 'Calle Mallorca, 204	Barcelona, 08036 ', '41.390268', '2.157844', '2012-10-28 00:00:00', 'thePhilarmonic.jpg', 'Barcelona', 'Eixample', 2);
INSERT INTO `ofertas` VALUES(3, 'Menú Carne a la Piedra', 'El restaurante parrillada El Caliu de l''Eixample es un lugar muy acogedor donde podrás disfrutar de una de las mejores parrilladas en Barcelona, pasando una velada agradable en el centro de Barcelona, en plena ruta modernista.', '55 %', '9,82 € (IVA incluido)', 'El Caliu de l''Eixample', 'Calle València, 329	Barcelona, 08009 ', '41.397045', '2.169088', '2012-10-18 00:00:00', 'elCaliuEixample.jpg', 'Barcelona', 'Eixample', 3);
INSERT INTO `ofertas` VALUES(4, 'Cenas a la Carta con Descuento', 'Jugar con los ingredientes y ofrecer mil sabores a paladares exigentes. Ésta es una de las pretensiones del restaurante Antigua que, desde hace más de un lustro, se esmera por ofrecer cocina mediterránea de autor en un entorno único.', '18 %', '35 €', 'Antigua', 'Marià Cubí, 59	Barcelona, 08006', '41.398011', '2.149261', '2012-10-16 00:00:00', 'laAntigua.jpg', 'Barcelona', ' Sarrià-Sant Gervasi', 4);
INSERT INTO `ofertas` VALUES(5, 'Menú Tapas Locas', 'Tapas Locas es tu restaurante de arroces y tapas mediterráneas en el Puerto Olímpico.Situado en el corazón del Puerto Olímpico', '43 %', '19,25 €', 'Restaurante Tapas Locas', 'Moll de Mestral, 8-9 (Port Olímpic) Barcelona, 08005 ', '41.388529', '2.200481', '2012-10-27 00:00:00', 'tapasLocas.jpg', 'Barcelona', 'Sant Marti', 5);
INSERT INTO `ofertas` VALUES(6, 'Menú para grupos (mínimo 12 peronas)', 'El restaurante La Puda Can Manel ofrece lo que el paisaje y la personalidad que la Barceloneta requiere, el sabor y la naturalidad de los productos de nuestro mar y de nuestra playa. Ambiente familiar y acogedor para encontrarse como en casa.', '10 %', '32,50 €', 'Restautante La Puda Can Manel', 'Passeig Joan de Borbó, 60-61 Barcelona, 08003', '41.377098', '2.188979', '2012-10-07 00:00:00', 'canManel.jpg', 'Barcelona', 'Ciutat Vella', 6);
INSERT INTO `ofertas` VALUES(7, 'Menú Degustación', 'Celebra tus reuniones de amigos en Hola Manito, un lugar cool y divertido donde podrás disfrutar de la mejor cocina mexicana acompañada de una colección de cervezas y de sus famosos mojitos y margaritas, unos de los mejores de Barcelona.', '20 %', '20 €', 'Hola Manito', 'Gran de Gràcia, 167	Barcelona, 08012', '41.403081', '2.152222', '2012-10-24 00:00:00', 'holaManito.jpg', 'Barcelona', 'Gràcia', 7);
INSERT INTO `ofertas` VALUES(9, 'Buffet de Tapas y Raciones', 'El local situado en la Plaza del Pilar destaca, en primer lugar, por su ubicación: se encuentra en uno de los lugares más emblemáticos de las tierras aragonesas y uno de los puntos de mayor afluencia cultural y religiosa de España.', '10 %', '30 €', 'Las Palomas (Plaza del Pilar)', 'Plaza del Pilar (esq. Don Jaime I)	Zaragoza, 50003', '41.655365', '-0.878566', '2012-10-28 00:00:00', 'lasPalomas.jpg', 'Zaragoza', 'Casco Antiguo', 9);
INSERT INTO `ofertas` VALUES(10, 'Menú especial Amaranto', 'El restaurante Amaranto, de reciente apertura, ubicado en el Hotel Boston, ha nacido con una clara vocación de liderazgo, apostando por el disfrute de la cocina de una forma global, sin prisas. La carta incluye una selección de mariscos, entrantes, pescados y carnes.', '10 %', '37 €', 'Restaurante Amaranto', 'Camino de laTorres, 28 (Hotel Boston) Zaragoza, 50008 ', '41.645707', '-0.876163', '2012-10-27 00:00:00', 'amaranto.jpg', 'Zaragoza', 'Centro', 10);
INSERT INTO `ofertas` VALUES(11, 'Comidas y Cenas a la Carta', ' La cocina del restaurante Anonimatto se basa principalmente en pastas frescas y carnes a la parrilla. Consta de una barra de mármol en la entrada donde degustar aperitivos, desde buen jamón ibérico, tostas, tapitas, hasta ver eventos deportivos o tomar un mojito o unas cañas. ', '20 %', '23 €', 'Anonimatto', 'Callejón de Álvarez Gato, 4	Madrid, 28012', '40.415136', '-3.70195', '2012-10-18 00:00:00', 'anonimato.jpg', 'Madrid', 'Centro', 11);
INSERT INTO `ofertas` VALUES(12, 'Menú Terraza', 'En Aravaca, a escasos 5 minutos de la Plaza España, se encuentra el restaurante Kira. Con un sorprendente interior, a caballo entre la sencillez y la sofisticación, basada en una decoración moderna y acogedora, Kira es un espacio cómodo y agradable donde disfrutar de una velada tranquila', '50 %', '26,60 €', 'Restaurante-Terraza Kira', 'Osa Mayor, 70	Madrid, 28023 ', '40.459095', '-3.783184', '2012-10-18 00:00:00', 'terrazaKira.jpg', 'Madrid', 'Moncloa', 12);
INSERT INTO `ofertas` VALUES(13, 'Cóctel Jai Alai', 'Jai Alai es uno de los restaurantes clásicos que forma parte de la historia de Madrid. Fundado en 1922 por los Bustingorri Vega, en la calle Alfonso XI, 6, en los bajos del frontón del  mismo nombre, que después de la guerra se convirtió en sede social del Real Madrid F.C.', '10 %', '33 €', 'Jai Alai', 'Balbina Valverde, 2	Madrid, 28002', '40.446457', '-3.689564', '2012-10-28 00:00:00', 'jaiAlai.jpg ', 'Madrid', 'Valverde', 13);

-- --------------------------------------------------------

--
-- Table structure for table `owners`
--

CREATE TABLE `owners` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(128) CHARACTER SET utf8 NOT NULL,
  `APELLIDO` varchar(128) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=14 ;

--
-- Dumping data for table `owners`
--

INSERT INTO `owners` VALUES(1, 'Pedro', 'González');
INSERT INTO `owners` VALUES(2, 'Carlos', 'Pérez');
INSERT INTO `owners` VALUES(3, 'Laia', 'Serra');
INSERT INTO `owners` VALUES(4, 'Manuel', 'López');
INSERT INTO `owners` VALUES(5, 'Sergio', 'Díaz');
INSERT INTO `owners` VALUES(6, 'Ramón', 'Hernández');
INSERT INTO `owners` VALUES(7, 'Juan', 'Ramos');
INSERT INTO `owners` VALUES(8, 'Pablo', 'Méndez');
INSERT INTO `owners` VALUES(9, 'Javier', 'Rodríguez');
INSERT INTO `owners` VALUES(10, 'Maria', 'Molina');
INSERT INTO `owners` VALUES(11, 'Luis', 'Serrano');
INSERT INTO `owners` VALUES(12, 'Gonzalo', 'Pineda');
INSERT INTO `owners` VALUES(13, 'Ana', 'Méndez');
