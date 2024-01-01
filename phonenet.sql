-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-12-2023 a las 19:48:52
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
-- Estructura de tabla para la tabla `bill`
--

CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL,
  `data_consumed` int(11) NOT NULL,
  `date` datetime(6) NOT NULL,
  `minutes_consumed` int(11) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `contract_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `bill`
--

INSERT INTO `bill` (`id`, `data_consumed`, `date`, `minutes_consumed`, `total_price`, `contract_id`) VALUES
(2, 200, '2023-12-01 00:00:32.000000', 100, 30.45, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `call_record`
--

CREATE TABLE `call_record` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) NOT NULL,
  `destination_phone_number` varchar(255) NOT NULL,
  `seconds` int(11) NOT NULL,
  `contract_id` bigint(20) NOT NULL,
  `date_time` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `complaint`
--

CREATE TABLE `complaint` (
  `id` bigint(20) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `creation_date` date NOT NULL,
  `descripción` varchar(255) NOT NULL,
  `status` enum('EN_ESPERA','EN_PROCESO','RESUELTA') NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contract`
--

CREATE TABLE `contract` (
  `id` bigint(20) NOT NULL,
  `api_id` binary(16) DEFAULT NULL,
  `end_date` datetime(6) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `last_call_data_update` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contract`
--

INSERT INTO `contract` (`id`, `api_id`, `end_date`, `phone_number`, `start_date`, `product_id`, `user_id`, `last_call_data_update`) VALUES
(9, 0x8bb7e83d12e94a29b3026a4cdb597f34, '2024-12-24 12:12:33.000000', '528790116', '2023-12-24 12:12:33.000000', 1, 3, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `data_usage_record`
--

CREATE TABLE `data_usage_record` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) NOT NULL,
  `mega_bytes` int(11) NOT NULL,
  `contract_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `data_usage_record`
--

INSERT INTO `data_usage_record` (`id`, `date`, `mega_bytes`, `contract_id`) VALUES
(49, '2023-12-23 01:00:00.000000', 1, 9),
(50, '2023-12-24 01:00:00.000000', 3703, 9),
(53, '2023-12-25 01:00:00.000000', 991, 9),
(55, '2023-12-26 01:00:00.000000', 217, 9);

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
  `data_usage_price` decimal(38,2) DEFAULT NULL,
  `call_limit` int(11) NOT NULL,
  `data_usage_limit` int(11) NOT NULL,
  `call_penalty_price` decimal(38,2) DEFAULT NULL,
  `data_penalty_price` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`id`, `available`, `description`, `image`, `name`, `price`, `call_price`, `data_usage_price`, `call_limit`, `data_usage_limit`, `call_penalty_price`, `data_penalty_price`) VALUES
(1, b'1', 'Tarifa especial para programadores', 'https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw', 'Hola mundo', 30.45, 0.50, 0.01, 300, 300, 1.00, 0.05),
(3, b'1', 'Tarifa de verano para el uso de la piscina', 'https://fotografias.antena3.com/clipping/cmsimages01/2023/05/31/F2862410-C9ED-4F82-B784-E07D81FEA58D/playa-verano_97.jpg?crop=910,512,x114,y0&width=1600&height=900&optimize=low&format=webply', 'Tarifa de verano', 50.00, 0.50, 0.02, 1000, 500, 1.00, 0.05);

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
(2, 2),
(3, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhij4ejan0w4gqcwon406rbamc` (`contract_id`);

--
-- Indices de la tabla `call_record`
--
ALTER TABLE `call_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm2wmsbmx3sd1ua0nvs46af6kk` (`contract_id`);

--
-- Indices de la tabla `complaint`
--
ALTER TABLE `complaint`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh8dg5n7ibjyack6pn6e71djj6` (`user_id`);

--
-- Indices de la tabla `contract`
--
ALTER TABLE `contract`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6gt1bchn8d85oaq1srbhk31n4` (`product_id`),
  ADD KEY `FK5s7r1nq49s36ndq7nlri6hxf7` (`user_id`);

--
-- Indices de la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhxw73sl89lo1p61fouhpc7dno` (`contract_id`);

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
-- AUTO_INCREMENT de la tabla `bill`
--
ALTER TABLE `bill`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `call_record`
--
ALTER TABLE `call_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `complaint`
--
ALTER TABLE `complaint`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `contract`
--
ALTER TABLE `contract`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

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
-- Filtros para la tabla `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `FKhij4ejan0w4gqcwon406rbamc` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`);

--
-- Filtros para la tabla `call_record`
--
ALTER TABLE `call_record`
  ADD CONSTRAINT `FKm2wmsbmx3sd1ua0nvs46af6kk` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`);

--
-- Filtros para la tabla `complaint`
--
ALTER TABLE `complaint`
  ADD CONSTRAINT `FKh8dg5n7ibjyack6pn6e71djj6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `contract`
--
ALTER TABLE `contract`
  ADD CONSTRAINT `FK5s7r1nq49s36ndq7nlri6hxf7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK6gt1bchn8d85oaq1srbhk31n4` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

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
