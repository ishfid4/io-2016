-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 04 Sty 2017, 01:08
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
(776, 2, 1, 4),
(777, 2, 1, 8),
(778, 2, 2, 4),
(779, 2, 2, 7),
(780, 2, 2, 12),
(781, 2, 3, 5),
(782, 2, 3, 10),
(783, 2, 4, 3),
(784, 2, 4, 4),
(785, 2, 4, 7),
(786, 2, 4, 11),
(787, 2, 5, 5),
(788, 2, 5, 6),
(789, 2, 5, 11),
(847, 1, 1, 5),
(848, 1, 1, 11),
(849, 1, 2, 4),
(850, 1, 2, 7),
(851, 1, 3, 5),
(852, 1, 3, 9),
(853, 1, 3, 11),
(854, 1, 3, 13),
(855, 1, 4, 5),
(856, 1, 4, 11),
(857, 1, 5, 3),
(858, 1, 5, 6),
(859, 1, 5, 12);

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
(3, 7, 1, 2),
(227, 2, 1, 6),
(228, 2, 1, 10),
(229, 2, 2, 5),
(230, 2, 2, 8),
(231, 2, 2, 11),
(232, 2, 3, 6),
(233, 2, 3, 10),
(234, 2, 4, 4),
(235, 2, 4, 10),
(236, 2, 5, 6),
(237, 2, 5, 8);

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
(173, 201, 2),
(174, 10, 2),
(175, 24, 2),
(188, 302, 1),
(189, 401, 1),
(190, 501, 1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=860;
--
-- AUTO_INCREMENT dla tabeli `preferred_hours_students`
--
ALTER TABLE `preferred_hours_students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=238;
--
-- AUTO_INCREMENT dla tabeli `preferred_rooms`
--
ALTER TABLE `preferred_rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=191;
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
