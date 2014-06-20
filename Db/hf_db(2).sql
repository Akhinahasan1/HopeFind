-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 10, 2014 at 12:23 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hf_db`
--
CREATE DATABASE IF NOT EXISTS `hf_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `hf_db`;

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE IF NOT EXISTS `member` (
  `id_member` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(12) NOT NULL,
  `password` varchar(8) NOT NULL,
  `email` varchar(30) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `alamat` tinytext,
  `jk` varchar(2) DEFAULT NULL,
  `no_tlpn` int(12) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `hak` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_member`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_adm`
--

CREATE TABLE IF NOT EXISTS `tb_adm` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `hak_akses` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_content`
--

CREATE TABLE IF NOT EXISTS `tb_content` (
  `id_member` int(11) NOT NULL,
  `kategori_id` int(11) NOT NULL,
  `id_lokasi` int(11) DEFAULT NULL,
  `content_id` int(11) NOT NULL AUTO_INCREMENT,
  `content_title` varchar(50) DEFAULT NULL,
  `content_type` varchar(20) NOT NULL,
  `content_sn` varchar(50) DEFAULT NULL,
  `content_gmbr` varchar(75) DEFAULT NULL,
  `content_detail` tinytext,
  PRIMARY KEY (`content_id`),
  KEY `kategori_id` (`kategori_id`),
  KEY `id_lokasi` (`id_lokasi`),
  KEY `id_member` (`id_member`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_kategori`
--

CREATE TABLE IF NOT EXISTS `tb_kategori` (
  `kategori_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama_kategori` varchar(70) NOT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tb_kategori`
--

INSERT INTO `tb_kategori` (`kategori_id`, `nama_kategori`) VALUES
(1, 'Motor'),
(2, 'Mobil');

-- --------------------------------------------------------

--
-- Table structure for table `tb_laporan`
--

CREATE TABLE IF NOT EXISTS `tb_laporan` (
  `id_laporan` int(11) NOT NULL AUTO_INCREMENT,
  `content_id` int(11) DEFAULT NULL,
  `isi_laporan` tinytext NOT NULL,
  `status_lapoaran` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_laporan`),
  KEY `content_id` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_lokasi`
--

CREATE TABLE IF NOT EXISTS `tb_lokasi` (
  `id_lokasi` int(11) NOT NULL AUTO_INCREMENT,
  `nama_lokasi` varchar(75) NOT NULL,
  PRIMARY KEY (`id_lokasi`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_pesan`
--

CREATE TABLE IF NOT EXISTS `tb_pesan` (
  `id_pesan` int(11) NOT NULL AUTO_INCREMENT,
  `id_member` int(11) NOT NULL,
  `subject` varchar(256) DEFAULT NULL,
  `to` varchar(80) NOT NULL,
  `tanggal` time DEFAULT NULL,
  `isi` tinytext,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_pesan`),
  KEY `id_member` (`id_member`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_content`
--
ALTER TABLE `tb_content`
  ADD CONSTRAINT `tb_content_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `tb_kategori` (`kategori_id`),
  ADD CONSTRAINT `tb_content_ibfk_2` FOREIGN KEY (`id_lokasi`) REFERENCES `tb_lokasi` (`id_lokasi`),
  ADD CONSTRAINT `tb_content_ibfk_3` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`);

--
-- Constraints for table `tb_laporan`
--
ALTER TABLE `tb_laporan`
  ADD CONSTRAINT `tb_laporan_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `tb_content` (`content_id`);

--
-- Constraints for table `tb_pesan`
--
ALTER TABLE `tb_pesan`
  ADD CONSTRAINT `tb_pesan_ibfk_1` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
