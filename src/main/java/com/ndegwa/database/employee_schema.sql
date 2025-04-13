-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2025 at 04:02 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `employeedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `first_name`, `last_name`, `email`, `department`, `salary`) VALUES
(1, 'Samuel', 'Ndegwa', 'sam@example.com', 'ICT', 400000.00),
(3, 'Jack', 'Kamaru', 'jackm@gmail.com', 'Account', 90000.00),
(4, 'Alexis', 'Wanjiku', 'alexis@gmail.com', 'Admin', 30000.00),
(6, 'Moses', 'Kanyira', 'mose@gmail.com', 'Transport', 30000.00),
(7, 'Eliud', 'Wanyama', 'wanyama@gmail.com', 'ICT', 100000.00),
(8, 'Diana', 'Chemoi', 'dina@gmail.com', 'ICT', 30000.00),
(9, 'Kibaki', 'Mwai', 'kibaki@gmail.com', 'Admin', 1000000.00),
(10, 'Karikui', 'Mwangi', 'kario@gmail.com', 'Account', 90000.00),
(11, 'Stanley', 'Matiba', 'matiba@gmail.com', 'Admin', 1200000.00),
(12, 'Joshua', 'Toro', 'toro@gmail.com', 'Admin', 200000.00),
(13, 'Jose', 'Chameleon', 'jose@gmail.com', 'Entertainment', 120000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
