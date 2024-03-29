-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-01-2024 a las 09:47:45
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
  `data_total_price` decimal(38,2) NOT NULL,
  `date` date NOT NULL,
  `minutes_consumed` int(11) DEFAULT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `contract_id` bigint(20) NOT NULL,
  `call_total_price` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `bill`
--

INSERT INTO `bill` (`id`, `data_consumed`, `data_total_price`, `date`, `minutes_consumed`, `total_price`, `contract_id`, `call_total_price`) VALUES
(32, 17247, 847.35, '2024-01-01', 227, 877.80, 9, 0.00),
(33, 0, 0.00, '2024-01-01', 0, 50.00, 11, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `call_record`
--

CREATE TABLE `call_record` (
  `id` bigint(20) NOT NULL,
  `date_time` datetime(6) NOT NULL,
  `destination_phone_number` varchar(255) NOT NULL,
  `seconds` int(11) NOT NULL,
  `contract_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `call_record`
--

INSERT INTO `call_record` (`id`, `date_time`, `destination_phone_number`, `seconds`, `contract_id`) VALUES
(270, '2023-12-26 15:16:00.000000', '271272935', 246, 9),
(271, '2024-01-02 13:22:00.000000', '288515784', 1077, 9),
(272, '2023-12-30 21:57:00.000000', '880096288', 146, 9),
(273, '2023-12-31 10:08:00.000000', '757501640', 363, 9),
(274, '2023-12-27 15:37:00.000000', '502770685', 1116, 9),
(275, '2023-12-24 09:50:00.000000', '880096288', 41, 9),
(276, '2023-12-29 20:11:00.000000', '911631704', 63, 9),
(277, '2024-01-01 22:16:00.000000', '956201198', 223, 9),
(278, '2023-12-25 21:43:00.000000', '065867335', 114, 9),
(279, '2023-12-31 14:44:00.000000', '358700256', 907, 9),
(280, '2023-12-31 19:47:00.000000', '409929303', 610, 9),
(281, '2023-12-24 13:16:00.000000', '123456789', 763, 9),
(282, '2024-01-02 21:08:00.000000', '502770685', 1032, 9),
(283, '2024-01-03 13:26:00.000000', '271272935', 1025, 9),
(284, '2023-12-27 08:45:00.000000', '756212447', 318, 9),
(285, '2024-01-02 22:34:00.000000', '351758243', 227, 9),
(286, '2023-12-30 08:48:00.000000', '358700256', 130, 9),
(287, '2023-12-24 10:25:00.000000', '123456789', 1051, 9),
(288, '2023-12-24 16:46:00.000000', '123456789', 464, 9),
(289, '2024-01-04 16:34:00.000000', '288515784', 942, 9),
(290, '2024-01-01 17:07:00.000000', '191732904', 533, 9),
(291, '2023-12-30 14:55:00.000000', '409929303', 244, 9),
(292, '2023-12-28 19:49:00.000000', '668554494', 706, 9),
(293, '2023-12-29 15:33:00.000000', '433075246', 483, 9),
(294, '2023-12-30 10:58:00.000000', '956201198', 983, 9),
(295, '2023-12-27 16:15:00.000000', '123456789', 643, 9),
(296, '2023-12-23 11:12:31.000000', '123456789', 1, 9),
(297, '2024-01-03 22:12:00.000000', '757501640', 545, 9),
(298, '2024-01-03 19:38:00.000000', '869378602', 858, 9),
(299, '2023-12-27 17:26:00.000000', '351758243', 303, 9),
(300, '2024-01-01 15:45:00.000000', '640991939', 1095, 9),
(301, '2024-01-04 10:16:00.000000', '259755326', 280, 9),
(302, '2023-12-29 19:03:00.000000', '956201198', 328, 9),
(303, '2024-01-02 17:12:00.000000', '058488090', 811, 9),
(304, '2023-12-31 12:28:00.000000', '640991939', 901, 9),
(305, '2023-12-25 13:29:00.000000', '880096288', 143, 9),
(306, '2023-12-26 08:18:00.000000', '668554494', 895, 9),
(307, '2023-12-28 09:55:00.000000', '911631704', 68, 9),
(308, '2024-01-02 09:02:00.000000', '191732904', 468, 9),
(309, '2023-12-28 14:29:00.000000', '866860165', 587, 9),
(310, '2024-01-03 13:04:00.000000', '756212447', 1150, 9),
(311, '2023-12-24 19:15:00.000000', '668554494', 290, 9),
(312, '2023-12-31 13:22:00.000000', '502770685', 333, 9),
(313, '2024-01-03 10:58:00.000000', '015214565', 37, 9),
(314, '2023-12-25 21:20:00.000000', '351758243', 413, 9),
(315, '2024-01-04 14:33:00.000000', '654026297', 426, 11),
(316, '2023-12-29 21:15:00.000000', '121849387', 160, 11),
(317, '2023-12-31 14:56:00.000000', '005282468', 515, 11),
(318, '2024-01-02 12:58:00.000000', '150538118', 879, 11),
(319, '2023-12-30 18:19:00.000000', '474925133', 976, 11),
(320, '2023-12-31 22:18:00.000000', '930122554', 96, 11),
(321, '2023-12-28 12:14:13.000000', '123456789', 1, 11),
(322, '2024-01-04 15:39:00.000000', '126823860', 615, 11),
(323, '2024-01-01 09:02:00.000000', '350420349', 551, 11),
(324, '2024-01-03 08:45:00.000000', '176860999', 836, 11),
(325, '2024-01-02 15:13:00.000000', '123456789', 584, 11),
(326, '2024-01-02 17:12:00.000000', '831458992', 827, 11),
(327, '2023-12-31 16:21:00.000000', '930122554', 1119, 11),
(328, '2023-12-29 11:29:00.000000', '930122554', 713, 11),
(329, '2024-01-02 21:00:00.000000', '123456789', 647, 11),
(330, '2023-12-29 20:03:00.000000', '123456789', 611, 11),
(331, '2024-01-02 17:50:00.000000', '121849387', 1009, 11),
(332, '2023-12-31 20:19:00.000000', '121849387', 816, 11),
(333, '2023-12-31 08:39:00.000000', '176860999', 809, 11),
(334, '2024-01-03 09:55:00.000000', '654026297', 1111, 11),
(335, '2024-01-01 11:52:00.000000', '121849387', 32, 11),
(336, '2024-01-04 19:57:00.000000', '364123302', 1115, 11),
(337, '2023-12-30 20:28:00.000000', '121849387', 1057, 11),
(338, '2024-01-05 13:20:00.000000', '358700256', 602, 9),
(339, '2024-01-06 17:20:00.000000', '123456789', 470, 9),
(340, '2024-01-06 16:20:00.000000', '351758243', 890, 9),
(341, '2024-01-05 10:01:00.000000', '015214565', 123, 9),
(342, '2024-01-05 16:31:00.000000', '351758243', 145, 9),
(343, '2024-01-05 18:29:00.000000', '724362008', 162, 9),
(344, '2024-01-05 21:04:00.000000', '306989905', 922, 9),
(345, '2024-01-06 16:42:00.000000', '895381978', 904, 9),
(346, '2024-01-06 21:18:00.000000', '880096288', 1103, 9),
(347, '2024-01-06 21:25:00.000000', '123456789', 205, 11),
(348, '2024-01-05 20:30:00.000000', '831458992', 341, 11),
(349, '2024-01-05 22:31:00.000000', '350420349', 749, 11),
(350, '2024-01-05 10:28:00.000000', '384178991', 536, 11),
(351, '2024-01-06 21:38:00.000000', '793396212', 175, 11),
(352, '2024-01-06 22:20:00.000000', '150538118', 261, 11),
(353, '2024-01-05 20:40:00.000000', '150330697', 278, 11),
(354, '2024-01-06 17:20:00.000000', '123456789', 470, 9),
(355, '2024-01-06 16:20:00.000000', '351758243', 890, 9),
(356, '2024-01-06 16:42:00.000000', '895381978', 904, 9),
(357, '2024-01-06 21:18:00.000000', '880096288', 1103, 9),
(358, '2024-01-06 21:25:00.000000', '123456789', 205, 11),
(359, '2024-01-06 21:38:00.000000', '793396212', 175, 11),
(360, '2024-01-06 22:20:00.000000', '150538118', 261, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `complaint`
--

CREATE TABLE `complaint` (
  `id` bigint(20) NOT NULL,
  `comments` varbinary(255) DEFAULT NULL,
  `creation_date` date NOT NULL,
  `status` enum('EN_ESPERA','EN_PROCESO','RESUELTA') NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) NOT NULL
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
  `last_call_data_update` datetime(6) DEFAULT NULL,
  `last_bill_update` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contract`
--

INSERT INTO `contract` (`id`, `api_id`, `end_date`, `phone_number`, `start_date`, `product_id`, `user_id`, `last_call_data_update`, `last_bill_update`) VALUES
(9, 0x8bb7e83d12e94a29b3026a4cdb597f34, '2024-12-24 12:12:33.000000', '528790116', '2023-12-24 12:12:33.000000', 1, 3, '2024-01-06 19:43:55.000000', '2024-01-01 00:00:00.000000'),
(11, 0x88b28a2d07544b7a907a9b0949c41a93, '2024-12-29 13:14:11.000000', '898535785', '2023-12-29 13:14:11.000000', 3, 3, '2024-01-06 19:43:55.000000', '2024-01-01 00:00:00.000000');

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
(198, '2023-12-30 01:00:00.000000', 762, 9),
(199, '2024-01-04 01:00:00.000000', 1040, 9),
(200, '2023-12-25 01:00:00.000000', 991, 9),
(201, '2023-12-27 01:00:00.000000', 1586, 9),
(202, '2023-12-28 01:00:00.000000', 3160, 9),
(203, '2023-12-31 01:00:00.000000', 3825, 9),
(204, '2024-01-01 01:00:00.000000', 1959, 9),
(205, '2023-12-23 01:00:00.000000', 1, 9),
(206, '2024-01-03 01:00:00.000000', 3797, 9),
(207, '2023-12-26 01:00:00.000000', 217, 9),
(208, '2023-12-24 01:00:00.000000', 3703, 9),
(209, '2023-12-29 01:00:00.000000', 3002, 9),
(210, '2024-01-02 01:00:00.000000', 219, 9),
(211, '2024-01-03 01:00:00.000000', 3694, 11),
(212, '2023-12-28 01:00:00.000000', 1, 11),
(213, '2024-01-04 01:00:00.000000', 2629, 11),
(214, '2023-12-30 01:00:00.000000', 898, 11),
(215, '2024-01-02 01:00:00.000000', 3702, 11),
(216, '2024-01-01 01:00:00.000000', 2259, 11),
(217, '2023-12-29 01:00:00.000000', 3537, 11),
(218, '2023-12-31 01:00:00.000000', 107, 11),
(219, '2024-01-05 01:00:00.000000', 3471, 9),
(220, '2024-01-06 01:00:00.000000', 116, 9),
(221, '2024-01-05 01:00:00.000000', 3444, 11),
(222, '2024-01-06 01:00:00.000000', 3318, 11),
(223, '2024-01-06 01:00:00.000000', 116, 9),
(224, '2024-01-06 01:00:00.000000', 3318, 11);

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
  `call_limit` int(11) NOT NULL,
  `data_usage_limit` int(11) NOT NULL,
  `call_penalty_price` decimal(38,2) DEFAULT NULL,
  `data_penalty_price` decimal(38,2) DEFAULT NULL,
  `router_speed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`id`, `available`, `description`, `image`, `name`, `price`, `call_limit`, `data_usage_limit`, `call_penalty_price`, `data_penalty_price`, `router_speed`) VALUES
(1, b'1', 'Tarifa especial para programadores', 'https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw', 'Hola mundo', 30.45, 300, 300, 1.00, 0.05, 0),
(3, b'1', 'Tarifa de verano para el uso de la piscina', 'https://fotografias.antena3.com/clipping/cmsimages01/2023/05/31/F2862410-C9ED-4F82-B784-E07D81FEA58D/playa-verano_97.jpg?crop=910,512,x114,y0&width=1600&height=900&optimize=low&format=webply', 'Tarifa de verano', 50.00, 1000, 500, 1.00, 0.05, 0),
(7, b'1', 'fdfsdfsd', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-EMkkcc5hklm2mNp4bn2bpWFVgLGgS2peaSp5ZbSQcNZEiK5cpXrabI0hvUXqedhq6jg&usqp=CAU', 'PhoneNEt', 50.00, 500, 51200, 0.05, 0.01, 0);

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
(7, 'FIBRA'),
(7, 'FIJO'),
(7, 'MOVIL');

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
(3, '32094459Y', 'admin@gmail.com', 'Admin', '$2a$10$09hljkZaT7lUxzcHINcE2OM2WHUghQf3vKOFJrH.njzWFHrrc7t/O', '679109619', 'Admin', 'Admin'),
(4, '23432343R', 'user111@gmail.com', 'userr', '$2a$10$m.85tdlmJf4wBRhmwuw9Le8uYIrMc/2yVwarIrTwy/IsjvsuGupee', '566767678', 'userr userr', 'user1');

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
(1, 'ADMIN'),
(2, 'USER');

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
(3, 1),
(4, 2);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `call_record`
--
ALTER TABLE `call_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=361;

--
-- AUTO_INCREMENT de la tabla `complaint`
--
ALTER TABLE `complaint`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `contract`
--
ALTER TABLE `contract`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `data_usage_record`
--
ALTER TABLE `data_usage_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=225;

--
-- AUTO_INCREMENT de la tabla `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
