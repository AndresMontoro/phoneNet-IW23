-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-12-2023 a las 19:59:45
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `phonenet`
--
CREATE DATABASE IF NOT EXISTS `phonenet` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `phonenet`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `call_record`
--

CREATE TABLE `call_record` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) NOT NULL,
  `detination_phone_number` varchar(255) DEFAULT NULL,
  `seconds` int(11) DEFAULT NULL,
  `destination_phone_number` varchar(255) NOT NULL,
  `api_id` binary(16) NOT NULL,
  `contract_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contract`
--

CREATE TABLE `contract` (
  `id` bigint(20) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `product__id` bigint(20) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `api_id` binary(16) DEFAULT NULL,
  `carrier` varchar(255) DEFAULT NULL,
  `phone_number_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer_line`
--

CREATE TABLE `customer_line` (
  `id` bigint(20) NOT NULL,
  `api_id` binary(16) NOT NULL,
  `carrier` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `contract_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `data_usage_record`
--

CREATE TABLE `data_usage_record` (
  `id` bigint(20) NOT NULL,
  `api_id` binary(16) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `mega_bytes` int(11) DEFAULT NULL,
  `contract_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `phone_number`
--

CREATE TABLE `phone_number` (
  `id` bigint(20) NOT NULL,
  `available` bit(1) NOT NULL,
  `number` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `phone_number`
--

INSERT INTO `phone_number` (`id`, `available`, `number`) VALUES
(1, b'1', '679109619'),
(2, b'1', '601488514');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `available` bit(1) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `call_price` decimal(38,2) DEFAULT NULL,
  `data_usage_price` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`id`, `available`, `description`, `image`, `name`, `price`, `call_price`, `data_usage_price`) VALUES
(1, b'1', 'Tarifa especial para programadores', 'https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw', 'Hola mundo', 30.45, NULL, NULL),
(3, b'1', 'Tarifa de verano para el uso de la piscina', 'https://fotografias.antena3.com/clipping/cmsimages01/2023/05/31/F2862410-C9ED-4F82-B784-E07D81FEA58D/playa-verano_97.jpg?crop=910,512,x114,y0&width=1600&height=900&optimize=low&format=webply', 'Tarifa de verano', 50.00, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product_type`
--

CREATE TABLE `product_type` (
  `product_id` bigint(20) NOT NULL,
  `type` enum('FIBRA','FIJO','MOVIL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `product_type`
--

INSERT INTO `product_type` (`product_id`, `type`) VALUES
(1, 'FIBRA'),
(1, 'MOVIL'),
(3, 'FIBRA'),
(3, 'MOVIL');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `dni`, `email`, `name`, `password`, `phone_number`, `surname`, `username`) VALUES
(2, '32094459Y', 'andresmontoro@gmail.com', 'Andrés', '$2a$10$DP2DCySRiFaTZEdAiEe4IOrpAiSEpUU0.ACutg.8jdsjS0480qsRm', NULL, 'Montoro Venegas', 'andres'),
(3, '32094459Y', 'admin@gmail.com', 'Admin', '$2a$10$5cwNdFjywr6vOV.wK5t2nOIkUILpymQnuhHtcSv3F6lDPoQ8EgLny', '679109619', 'Admin', 'Admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_role`
--

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user_role`
--

INSERT INTO `user_role` (`id`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES
(2, 1),
(3, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `call_record`
--
ALTER TABLE `call_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm2wmsbmx3sd1ua0nvs46af6kk` (`contract_id`);

--
-- Indices de la tabla `contract`
--
ALTER TABLE `contract`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_or21ekgxodfolo2hkrtsx4oy7` (`phone_number_id`),
  ADD KEY `FK6gt1bchn8d85oaq1srbhk31n4` (`product_id`),
  ADD KEY `FK5s7r1nq49s36ndq7nlri6hxf7` (`user_id`);

--
-- Indices de la tabla `customer_line`
--
ALTER TABLE `customer_line`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pvgty5n8hinwevwgl5n22i6r5` (`contract_id`);

--
-- Indices de la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jayfl53ri6elrq91upj31u6an` (`contract_id`);

--
-- Indices de la tabla `phone_number`
--
ALTER TABLE `phone_number`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_q2n3melweyrl5d4rqkg7pq6ra` (`description`),
  ADD UNIQUE KEY `UK_k522ciwe39etspfr4ncb790m2` (`image`),
  ADD UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`name`);

--
-- Indices de la tabla `product_type`
--
ALTER TABLE `product_type`
  ADD PRIMARY KEY (`product_id`,`type`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- Indices de la tabla `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`roles_id`),
  ADD KEY `FK5i6gd32hnpr2nyf5edlvl9nhw` (`roles_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `call_record`
--
ALTER TABLE `call_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `contract`
--
ALTER TABLE `contract`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `customer_line`
--
ALTER TABLE `customer_line`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `phone_number`
--
ALTER TABLE `phone_number`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `call_record`
--
ALTER TABLE `call_record`
  ADD CONSTRAINT `FKm2wmsbmx3sd1ua0nvs46af6kk` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`);

--
-- Filtros para la tabla `contract`
--
ALTER TABLE `contract`
  ADD CONSTRAINT `FK5s7r1nq49s36ndq7nlri6hxf7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK6gt1bchn8d85oaq1srbhk31n4` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKpgpoda17t2qo5ymdgprtenugn` FOREIGN KEY (`phone_number_id`) REFERENCES `phone_number` (`id`);

--
-- Filtros para la tabla `customer_line`
--
ALTER TABLE `customer_line`
  ADD CONSTRAINT `FKtg12hpq85o8c7mf1lxyfscshv` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`);

--
-- Filtros para la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  ADD CONSTRAINT `FKhxw73sl89lo1p61fouhpc7dno` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`);

--
-- Filtros para la tabla `product_type`
--
ALTER TABLE `product_type`
  ADD CONSTRAINT `FKo6cvd7sivyf4ji66lnpfnc5pt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Filtros para la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK5i6gd32hnpr2nyf5edlvl9nhw` FOREIGN KEY (`roles_id`) REFERENCES `user_role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
