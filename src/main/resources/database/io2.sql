-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 17 Gru 2016, 15:07
-- Wersja serwera: 10.1.19-MariaDB
-- Wersja PHP: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `io2`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `lecturers`
--

CREATE TABLE `lecturers` (
  `id` int(11) NOT NULL,
  `firstname` varchar(32) COLLATE utf8mb4_polish_ci NOT NULL,
  `lastname` varchar(32) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `lecturers`
--

INSERT INTO `lecturers` (`id`, `firstname`, `lastname`) VALUES
(1, 'Kreglarz', 'Lysy'),
(2, 'Henryk', 'Metalowy');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `preferred_hours_lecturers`
--

CREATE TABLE `preferred_hours_lecturers` (
  `id` int(11) NOT NULL,
  `lecturer_id` int(11) NOT NULL,
  `day_id` int(11) NOT NULL,
  `hour_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `preferred_hours_lecturers`
--

INSERT INTO `preferred_hours_lecturers` (`id`, `lecturer_id`, `day_id`, `hour_id`) VALUES
(1, 1, 3, 5),
(2, 2, 5, 12),
(3, 2, 1, 9),
(4, 2, 2, 7),
(5, 2, 2, 11),
(6, 2, 3, 5),
(7, 2, 3, 10),
(8, 2, 4, 5),
(9, 2, 4, 11),
(10, 2, 5, 4),
(11, 2, 5, 9),
(12, 1, 1, 4),
(13, 1, 1, 5),
(14, 1, 1, 6),
(15, 1, 1, 7),
(16, 1, 1, 8),
(17, 1, 1, 9),
(18, 2, 1, 8),
(19, 1, 1, 6),
(20, 1, 1, 8),
(21, 1, 1, 11),
(22, 1, 2, 8),
(23, 1, 3, 11),
(24, 1, 4, 4),
(25, 1, 4, 11),
(26, 1, 5, 4),
(27, 1, 5, 12),
(28, 2, 1, 10),
(29, 2, 2, 7),
(30, 2, 3, 11),
(31, 2, 4, 7),
(32, 2, 5, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `preferred_hours_students`
--

CREATE TABLE `preferred_hours_students` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `day_id` int(11) NOT NULL,
  `hour_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `preferred_hours_students`
--

INSERT INTO `preferred_hours_students` (`id`, `user_id`, `day_id`, `hour_id`) VALUES
(1, 1, 1, 2),
(2, 2, 1, 2),
(3, 7, 1, 2),
(4, 2, 3, 2),
(5, 2, 3, 2),
(6, 2, 3, 2),
(7, 2, 1, 2),
(8, 2, 1, 2),
(9, 2, 1, 2),
(10, 2, 3, 2),
(11, 2, 2, 4),
(12, 2, 2, 5),
(13, 2, 2, 7),
(14, 2, 2, 9),
(15, 2, 2, 11),
(16, 2, 2, 12),
(17, 2, 1, 5),
(18, 2, 2, 8),
(19, 2, 3, 5),
(20, 2, 4, 10),
(21, 2, 5, 8),
(22, 2, 1, 5),
(23, 2, 2, 8),
(24, 2, 3, 5),
(25, 2, 4, 10),
(26, 2, 5, 8),
(27, 6, 1, 9),
(28, 6, 2, 8),
(29, 6, 2, 9),
(30, 6, 2, 10),
(31, 6, 2, 11),
(32, 6, 3, 9),
(33, 6, 4, 10),
(34, 6, 5, 4),
(35, 6, 5, 10),
(36, 2, 5, 6),
(37, 2, 5, 7),
(38, 2, 5, 8),
(39, 2, 5, 9),
(40, 2, 1, 8),
(41, 2, 1, 12),
(42, 2, 2, 6),
(43, 2, 3, 8),
(44, 2, 4, 5),
(45, 2, 4, 11),
(46, 2, 5, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `preferred_rooms`
--

CREATE TABLE `preferred_rooms` (
  `id` int(11) NOT NULL,
  `room_number` int(11) NOT NULL,
  `lecturer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `preferred_rooms`
--

INSERT INTO `preferred_rooms` (`id`, `room_number`, `lecturer_id`) VALUES
(1, 103, 1),
(2, 203, 2),
(3, 10, 2),
(4, 100, 1),
(5, 10, 1),
(6, 10, 1),
(7, 401, 2),
(8, 10, 2),
(9, 501, 2),
(10, 24, 2),
(11, 12, 2),
(12, 103, 1),
(13, 102, 1),
(14, 501, 1),
(15, 24, 1),
(16, 12, 1),
(17, 10, 1),
(18, 12, 1),
(19, 10, 2),
(20, 24, 2),
(21, 1, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `room_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `room`
--

INSERT INTO `room` (`id`, `room_number`) VALUES
(1, 103),
(2, 102),
(3, 201),
(4, 302),
(5, 401),
(6, 10),
(7, 501),
(8, 24),
(9, 12),
(10, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `firstname` varchar(32) COLLATE utf8mb4_polish_ci NOT NULL,
  `lastname` varchar(32) COLLATE utf8mb4_polish_ci NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `students`
--

INSERT INTO `students` (`id`, `firstname`, `lastname`, `group_id`) VALUES
(2, 'Radek', 'Dupa', 2),
(3, 'Janusz', 'Korwin-Mikke', 3),
(4, 'Marcin', 'Putas', 3),
(5, 'Dupa', 'Pierdolona', 2),
(6, 'Kutas', 'Kutasowski', 4),
(7, 'Spierdol', 'Stulejarski', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `preferred_hours_lecturers`
--
ALTER TABLE `preferred_hours_lecturers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `preferred_hours_students`
--
ALTER TABLE `preferred_hours_students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `preferred_rooms`
--
ALTER TABLE `preferred_rooms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `lecturers`
--
ALTER TABLE `lecturers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `preferred_hours_lecturers`
--
ALTER TABLE `preferred_hours_lecturers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT dla tabeli `preferred_hours_students`
--
ALTER TABLE `preferred_hours_students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT dla tabeli `preferred_rooms`
--
ALTER TABLE `preferred_rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT dla tabeli `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT dla tabeli `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
