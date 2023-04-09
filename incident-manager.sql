-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 09, 2023 at 07:16 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `incident-manager`
--

-- --------------------------------------------------------

--
-- Table structure for table `confirmation_token`
--

CREATE TABLE `confirmation_token` (
  `token_id` bigint(20) NOT NULL,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(76);

-- --------------------------------------------------------

--
-- Table structure for table `incidents`
--

CREATE TABLE `incidents` (
  `id` int(11) NOT NULL,
  `creation_date` varchar(255) DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `assigne_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `restore_password_token`
--

CREATE TABLE `restore_password_token` (
  `token_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restore_password_token`
--

INSERT INTO `restore_password_token` (`token_id`, `created_date`, `token`, `id`) VALUES
(74, '2023-04-09 05:38:51', 'd0c807ba-f1c1-46cb-bd39-eea3f9d5a680', 50),
(75, '2023-04-09 05:52:37', '88458119-d23d-4dde-bad1-c1ba5f128c4c', 50);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `status_id` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`status_id`, `label`) VALUES
(1, 'enCour'),
(2, 'terminer'),
(3, 'declancher');

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`id`, `expired`, `revoked`, `token`, `token_type`, `user_id`) VALUES
(39, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgwOTc1NTE1LCJleHAiOjE2ODA5NzU1NzV9.D2x0kV66M6WrnogYEM2akXXy5BSXs_5dnYG_j9xlUbU', 'BEARER', 50),
(40, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgwOTc1NTM4LCJleHAiOjE2ODA5NzU1OTh9.9vajabEMQbnpH60MNykmgRr8Dln69fzljcu86HdC_yo', 'BEARER', 50),
(41, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgwOTc1NjYyLCJleHAiOjE2ODA5NzU3MjJ9.rZMzDMaDjhfiTN89FfCqVlwvpUdmG3qV48a8wF8jy2o', 'BEARER', 50),
(42, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDEzMzI2LCJleHAiOjE2ODEwMTMzODZ9.eMBBg2Ngh8byj-VBSkVq1fx8bdsLcYVEpMVQa8G7Z_0', 'BEARER', 50),
(43, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE0Njk1LCJleHAiOjE2ODEwMTQ3NTV9.srN-bzWLOlnMKeUNKlW0S3AYtuVdUZEHX_M-K1jyRlY', 'BEARER', 50),
(44, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE0ODE0LCJleHAiOjE2ODEwMTQ4NzR9.QkSbfXyaMHKLa8uuZ-4ZMDcEbgI7zQfM8DYz6s6eJn0', 'BEARER', 50),
(45, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE0OTUzLCJleHAiOjE2ODEwMTUwMTN9.6gCZxa_g9odAbjI3kGmSQhlwEwx4IDXW89KGUfx-bPA', 'BEARER', 50),
(46, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE1MTkxLCJleHAiOjE2ODEwMTUyNTF9.LE1YbFaseg-Wf87IaDDZDqRf7MIURnMJGp69uCeQ-zo', 'BEARER', 50),
(47, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE1MjQ2LCJleHAiOjE2ODEwMTUzMDZ9.Heri0uDtZw1qDUsO5G9qMmB3NMHdJKb9moAm0Bry4tE', 'BEARER', 50),
(48, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE1MjU4LCJleHAiOjE2ODEwMTUzMTh9.PDfbV5biOO_zbTXp243BnxZDbZz_bHnZG7s5-S0Ktxs', 'BEARER', 50),
(49, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE1NDY5LCJleHAiOjE2ODEwMTU1Mjl9.1X53xOoylp4uxj91w1iJFZLQ08akQdMdbV5j0ZCQ2EI', 'BEARER', 50),
(50, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE1ODU5LCJleHAiOjE2ODEwMTU5MTl9.x1lG-zliiT8GpGfKVLikpPpXTTfMgc3tbrOitF52QoA', 'BEARER', 50),
(51, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWRpaW1zYWxtaTJAZ21haWwuY29tIiwiaWF0IjoxNjgxMDE2MDIwLCJleHAiOjE2ODEwMTYwODB9.4qi8_9JO7UDC6ir-7PMUpsMJPaVR1XGndzy09mjJXlU', 'BEARER', 50);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `firstname`, `lastname`, `password`, `role`, `is_enabled`) VALUES
(2, 'badiimsalmi@gmail.com', 'badii', 'msalmi', '$2a$10$faJUA9lA40w7WiWY3IRkS.P/LERv.F6Op9k94HVvSANQwIgd5yErm', 'USER', b'0'),
(3, 'badiimsalmi2@gma.com', 'admin', 'admin', '$2a$10$Nsek2NTQUVci2WdaA8ndzOtosPTW5Ou8x3VXuxc3V6ppxzs9wnDZW', 'USER', b'0'),
(4, 'oussema@gmail.com', 'oussema', 'achour', '$2a$10$LVmbPVDG8n52PesQkwb0Hegvmiyehkg/fk/QtUMKFxoCCuYoLGfIq', 'USER', b'0'),
(5, 'xxx@gmail.com', 'wael', 'jotam', '$2a$10$e/DVtu/S5dRIHtF1d2/rNuCGIobt23XLug.6mhlZJ.DNSLWnu0Amm', 'USER', b'0'),
(6, 'yyy@gmail.com', 'amine', 'jotam', '$2a$10$POkrUVCu3cnECtoHwdA97OjLkzU.YpVaW14dcxQKEfjsHhmH2tcte', 'USER', b'0'),
(7, 'yas@gmail.com', 'yasmin', 'yyy', '$2a$10$4URc1j54zmxgs.XUrGWp5Oix6KrccQo4L18GF/ty.WRUyzpDD1SgO', 'USER', b'0'),
(8, 'badiimsalmi2@gamil.com', 'admin', 'admin', '$2a$10$94Ki9eHkFIUXNWckE7YcAu6YShd550hJpZ8SumssoU4whxjGEY8Ou', 'USER', b'0'),
(40, 'xxx@gmail.co', 'admin', 'admin', '$2a$10$Q44z6hlwWu54/1b/0LzQO.16QTbOeZ9syAsyD1RTR3L/l/CqdCvDy', 'USER', b'0'),
(41, 'xxxx@gmail.co', 'admin', 'admin', '$2a$10$7NoYu927q58a3JHHW44ORuzTomQldhHAXGfGOvcQdCmbVPYxkJ096', 'USER', b'0'),
(50, 'badiimsalmi2@gmail.com', 'Admin', 'Admin', '$2a$10$eeInyNkbTpKkZbXDZXzud.awCg3SjLzBB/SFbO8XwL5wRT2oQ4KFy', 'USER', b'1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD PRIMARY KEY (`token_id`),
  ADD KEY `FKnnpcujbp8umb3hiqc9ecd9bre` (`id`);

--
-- Indexes for table `incidents`
--
ALTER TABLE `incidents`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqncd8dl8wpw5lrfl3lr2433ms` (`assigne_id`),
  ADD KEY `FKrfjdpic6a3pulmpepr0s4ly1b` (`user_id`),
  ADD KEY `FKnggxi71oaicwq7yb2pxwowqv7` (`status_id`);

--
-- Indexes for table `restore_password_token`
--
ALTER TABLE `restore_password_token`
  ADD PRIMARY KEY (`token_id`),
  ADD KEY `FK5oyryw2vs84ytcatkgap1bjll` (`id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`status_id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `incidents`
--
ALTER TABLE `incidents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD CONSTRAINT `FKnnpcujbp8umb3hiqc9ecd9bre` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `incidents`
--
ALTER TABLE `incidents`
  ADD CONSTRAINT `FKnggxi71oaicwq7yb2pxwowqv7` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`),
  ADD CONSTRAINT `FKqncd8dl8wpw5lrfl3lr2433ms` FOREIGN KEY (`assigne_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKrfjdpic6a3pulmpepr0s4ly1b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `restore_password_token`
--
ALTER TABLE `restore_password_token`
  ADD CONSTRAINT `FK5oyryw2vs84ytcatkgap1bjll` FOREIGN KEY (`id`) REFERENCES `user` (`id`);

--
-- Constraints for table `token`
--
ALTER TABLE `token`
  ADD CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
