-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2023 at 04:27 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `volley_uas`
--

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id` int(11) NOT NULL,
  `image` text NOT NULL,
  `judul` varchar(100) NOT NULL,
  `kategori` varchar(100) NOT NULL,
  `penerbit` varchar(100) NOT NULL,
  `pengarang` varchar(100) NOT NULL,
  `tahun` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id`, `image`, `judul`, `kategori`, `penerbit`, `pengarang`, `tahun`) VALUES
(1, 'images/20-05-2023-121942-4583.jpg', 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'Biografi', 'Sleman', 'Weda S Atma', '2017'),
(2, 'images/20-05-2023-122120-4843.jpg', 'Habis Gelap Terbitlah Terang', 'Biografi', 'Pustaka Narasi', 'R.A.Kartini', '2017'),
(3, 'images/20-05-2023-122305-2580.jpg', 'Kamus Besar Bahasa Indonesia Edisi III', 'Kamus', 'Balai Pustaka', 'Alwi, Hasan', '2002'),
(4, 'images/20-05-2023-122548-8345.jpg', 'Kamus Lenkap Bahasa Jepang', 'Kamus', 'Andaliman Books', 'Diramoti Benedict, S.S.', '2021'),
(9, 'images/20-05-2023-015215-608.jpg', 'asdf', 'Ensikopedia', 'asdf', 'asdf', '2341'),
(13, 'images/21-05-2023-054858-5633.jpg', 'sad', 'Ensikopedia', 'asdf', 'asdf', '2555'),
(14, 'images/21-05-2023-054914-4337.jpg', 'sa', 'Biografi', '23ss', '32342343', '234');

-- --------------------------------------------------------

--
-- Table structure for table `kembali`
--

CREATE TABLE `kembali` (
  `id` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `peminjam` varchar(100) NOT NULL,
  `denda` int(11) NOT NULL,
  `rent_at` date NOT NULL,
  `return_at` date NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kembali`
--

INSERT INTO `kembali` (`id`, `judul`, `peminjam`, `denda`, `rent_at`, `return_at`, `status`) VALUES
(1, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'takooo', 8000, '2023-05-20', '2023-05-31', 'Dikembalikan'),
(3, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'dfgdfg', 0, '2023-05-20', '2023-05-22', 'Dikembalikan'),
(4, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'lkjkj;k', 0, '2023-05-20', '2023-05-23', 'Dikembalikan'),
(7, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'sadfsdf', 5000, '2023-05-21', '2023-05-29', 'Dikembalikan'),
(9, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'dsasda', 0, '2023-05-21', '2023-05-24', 'Dikembalikan'),
(10, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'dss', 0, '2023-05-21', '2023-05-26', 'Dikembalikan'),
(11, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'sdaas', 8000, '2023-05-21', '2023-05-31', 'Dikembalikan');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(200) NOT NULL,
  `login_timestamp` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `email`, `password`, `login_timestamp`) VALUES
('asd', 'asd', 'asd', '2023-05-21 16:16:19.881112'),
('asd', 'asd', 'asd', '2023-05-21 16:16:56.720111'),
('asd', 'asd', 'asd', '2023-05-21 16:19:14.265634'),
('davin', 'davinvin', 'asd', '2023-05-21 17:41:41.412285'),
('davin', 'davinvin', 'asd', '2023-05-21 17:42:50.879376'),
('asd', 'asd', 'asd', '2023-05-21 17:47:40.715629'),
('asd', 'asd', 'asd', '2023-05-21 18:41:29.433378'),
('asd', 'asd', 'asd', '2023-05-21 18:42:20.753296'),
('asd', 'asd', 'asd', '2023-05-21 18:50:54.097943'),
('asd', 'asd', 'asd', '2023-05-21 18:53:52.865403'),
('asd', 'asd', 'asd', '2023-05-21 18:54:59.633748'),
('asd', 'asd', 'asd', '2023-05-21 19:33:52.367271'),
('asd', 'asd', 'asd', '2023-05-21 19:34:52.280931'),
('asd', 'asd', 'asd', '2023-05-21 19:38:34.394705'),
('asd', 'asd', 'asd', '2023-05-21 19:44:22.239228'),
('asd', 'asd', 'asd', '2023-05-21 19:47:45.384887'),
('asd', 'asd', 'asd', '2023-05-21 19:52:58.904196'),
('asd', 'asd', 'asd', '2023-05-21 20:09:21.025839'),
('asd', 'asd', 'asdf', '2023-05-21 20:13:51.694558'),
('asd', 'asd', 'asdf', '2023-05-21 20:20:16.663366'),
('asd', 'asd', 'asdfghjK', '2023-05-21 20:32:32.781865');

-- --------------------------------------------------------

--
-- Table structure for table `pinjam`
--

CREATE TABLE `pinjam` (
  `id` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `peminjam` varchar(100) NOT NULL,
  `denda` int(11) NOT NULL,
  `rent_at` date NOT NULL,
  `return_at` date NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pinjam`
--

INSERT INTO `pinjam` (`id`, `judul`, `peminjam`, `denda`, `rent_at`, `return_at`, `status`) VALUES
(5, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'tako', 0, '2023-05-20', '2023-05-30', 'Dipinjam'),
(6, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'Finda', 0, '2023-05-21', '2023-05-24', 'Dipinjam'),
(8, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'sdfsd', 0, '2023-05-21', '2023-05-25', 'Dipinjam'),
(12, 'Kisah, Perjuangan & Inspirasi B.J.Habibie', 'sad', 0, '2023-05-21', '2023-05-24', 'Dipinjam');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(200) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `created_at`, `update_at`) VALUES
(1, 'asd', 'asd', 'asdfghjK', '2023-05-21 17:27:15', '2023-05-21 20:21:50'),
(4, 'davin', 'davinvin', 'asd', '2023-05-21 17:27:15', '0000-00-00 00:00:00'),
(6, 'kenzo', 'kenzo@gmail.com', 'asdsdfsdfsdf', '2023-05-21 17:27:15', '2023-05-21 20:09:03'),
(61, 'asdasdasd', 'asdasd@gmail.com', 'Davin123', '2023-05-21 17:27:15', '0000-00-00 00:00:00'),
(62, 'sdfwrewrrt', 'werq@gmail.com', 'Asdfghjk', '2023-05-21 17:27:15', '0000-00-00 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kembali`
--
ALTER TABLE `kembali`
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `pinjam`
--
ALTER TABLE `pinjam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `pinjam`
--
ALTER TABLE `pinjam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
