-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 15, 2020 at 12:20 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12585906_tapin`
--

-- --------------------------------------------------------

--
-- Table structure for table `DRIVER_NEW`
--

CREATE TABLE `DRIVER_NEW` (
  `Driver_Id` int(6) UNSIGNED NOT NULL,
  `Firstname` varchar(20) DEFAULT NULL,
  `Lastname` varchar(20) DEFAULT NULL,
  `Phone` varchar(13) NOT NULL,
  `Fb_Id` varchar(50) DEFAULT NULL,
  `Address` varchar(120) DEFAULT NULL,
  `Gender` enum('Male','Female') DEFAULT NULL,
  `Dob` varchar(10) DEFAULT 'Dob',
  `License` varchar(18) DEFAULT 'License',
  `Verified` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `DRIVER_NEW`
--

INSERT INTO `DRIVER_NEW` (`Driver_Id`, `Firstname`, `Lastname`, `Phone`, `Fb_Id`, `Address`, `Gender`, `Dob`, `License`, `Verified`) VALUES
(14, 'Adil', 'Chauhan', '+911234567891', 'oNrwuK2X11QEHQxh2G5PaNonQyo1', 'Alfalah', 'Male', '24-01-1999', 'MH-00-2020-2345678', 1),
(16, 'Ishan', 'Gajjar', '+911234567890', 'mfZHdQHeaQUUoVZukI75104P1wg2', 'Law garden Ahmedabad', 'Male', '14-01-2000', 'GJ-01-2018-1234567', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ORDERS`
--

CREATE TABLE `ORDERS` (
  `Order_Id` int(10) UNSIGNED NOT NULL,
  `Weight` varchar(5) DEFAULT NULL,
  `Description` varchar(20) DEFAULT NULL,
  `Comment_Src` varchar(50) DEFAULT NULL,
  `Receiver_Name` varchar(20) DEFAULT NULL,
  `Receiver_Phone` varchar(13) DEFAULT NULL,
  `Receiver_Address` varchar(100) DEFAULT NULL,
  `Comment_Dest` varchar(50) DEFAULT NULL,
  `User_Id` int(6) UNSIGNED DEFAULT NULL,
  `Driver_Id` int(6) UNSIGNED DEFAULT NULL,
  `Source` varchar(50) DEFAULT NULL,
  `Destination` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ORDERS`
--

INSERT INTO `ORDERS` (`Order_Id`, `Weight`, `Description`, `Comment_Src`, `Receiver_Name`, `Receiver_Phone`, `Receiver_Address`, `Comment_Dest`, `User_Id`, `Driver_Id`, `Source`, `Destination`) VALUES
(14, '5kg', NULL, NULL, NULL, NULL, NULL, NULL, 11, NULL, NULL, NULL),
(20, '5KG', 'A', 'A', 'A', '9722275786', 'A', 'A', 11, NULL, NULL, NULL),
(44, '5KG', 'B', 'B', 'B', '+911', 'B', 'B', 11, NULL, NULL, NULL),
(45, '5KG', 'C', 'C', 'C', '+918', 'C', 'C', 11, NULL, NULL, NULL),
(46, '5KG', 'D', 'D', 'D', '+919', 'D', 'D', 11, NULL, NULL, NULL),
(47, '5KG', 'G', 'g', 'h', '+91123', 'g', 'g', 11, NULL, NULL, NULL),
(48, '5KG', 'document', 'knock door', 'fardeen', '+919876543210', 'danilimda', 'text please', 13, NULL, '22.995841840544333;72.59259592741728', '23.012290349674075;72.58686102926731'),
(49, '10KG', 'doc', '', 'gg', '+911', 'gg', '', 11, NULL, '22.99432891403995;72.59292785078287', '22.995416852498053;72.59284537285566');

-- --------------------------------------------------------

--
-- Table structure for table `USER_NEW`
--

CREATE TABLE `USER_NEW` (
  `User_Id` int(6) UNSIGNED NOT NULL,
  `Firstname` varchar(20) DEFAULT NULL,
  `Lastname` varchar(20) DEFAULT NULL,
  `Phone` varchar(13) NOT NULL,
  `Fb_Id` varchar(50) DEFAULT NULL,
  `Address` varchar(120) DEFAULT NULL,
  `Gender` enum('Male','Female') DEFAULT NULL,
  `Dob` varchar(10) DEFAULT 'Dob'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `USER_NEW`
--

INSERT INTO `USER_NEW` (`User_Id`, `Firstname`, `Lastname`, `Phone`, `Fb_Id`, `Address`, `Gender`, `Dob`) VALUES
(3, NULL, NULL, '+910011223344', 'qwertypoiuyt', NULL, NULL, 'Dob'),
(4, 'Abid', 'Chauhan', '+911122334455', 'qwertyabcddefg', NULL, NULL, 'Dob'),
(5, 'Altaf', 'Shaikh', '+910000000000', 'qwertytrewq', NULL, NULL, 'Dob'),
(6, 'Adil', 'Chauhan', '+919722275786', 'YB5KlSE41OTWrnvQLgAgZirMcaT2', 'My address is my home ...', 'Male', '24-01-1999'),
(8, 'Tipu', 'Bhalak', '+911234567891', 'PI4l3S0VbreDaRiBHhQHCk8Oyqw2', 'Shahalam', 'Male', '02-01-1997'),
(9, 'Umang', 'Sharma', '+917990522734', 'WKaLzSshwwff6pbfeeYGufl309D2', NULL, NULL, 'Dob'),
(10, 'Altaf', 'Shaikh', '+918200656845', 'T2d7tcnRxXP7hccrZv4RTBxm5Zn1', NULL, NULL, 'Dob'),
(11, 'Altaf', 'Shaikh', '+911234567890', 'eJvdJZ2jLRhw27ZQSpqqVxMFq2c2', 'somewhere , on the earth,  i dont wanna tell you...', 'Male', '06-03-2003'),
(12, 'Sajid', 'Shaikh', '+919601113601', 'rgU1CIPDOMZKYrun1tqgER1FSj23', NULL, NULL, 'Dob'),
(13, 'shabaz ', 'pathan', '+919898039080', 'kQFybhU4tTOzasJ2YoPwz36JiMm2', 'abivwib\n', 'Male', '03-08-1998');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DRIVER_NEW`
--
ALTER TABLE `DRIVER_NEW`
  ADD PRIMARY KEY (`Driver_Id`),
  ADD UNIQUE KEY `Phone` (`Phone`),
  ADD UNIQUE KEY `Fb_Id` (`Fb_Id`);

--
-- Indexes for table `ORDERS`
--
ALTER TABLE `ORDERS`
  ADD PRIMARY KEY (`Order_Id`),
  ADD KEY `User_Id` (`User_Id`),
  ADD KEY `Driver_Id` (`Driver_Id`);

--
-- Indexes for table `USER_NEW`
--
ALTER TABLE `USER_NEW`
  ADD PRIMARY KEY (`User_Id`),
  ADD UNIQUE KEY `Phone` (`Phone`),
  ADD UNIQUE KEY `Fb_Id` (`Fb_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `DRIVER_NEW`
--
ALTER TABLE `DRIVER_NEW`
  MODIFY `Driver_Id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `ORDERS`
--
ALTER TABLE `ORDERS`
  MODIFY `Order_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `USER_NEW`
--
ALTER TABLE `USER_NEW`
  MODIFY `User_Id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ORDERS`
--
ALTER TABLE `ORDERS`
  ADD CONSTRAINT `ORDERS_ibfk_2` FOREIGN KEY (`User_Id`) REFERENCES `USER_NEW` (`User_Id`),
  ADD CONSTRAINT `ORDERS_ibfk_3` FOREIGN KEY (`Driver_Id`) REFERENCES `DRIVER_NEW` (`Driver_Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
