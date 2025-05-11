-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : dim. 11 mai 2025 à 19:21
-- Version du serveur : 8.0.42-0ubuntu0.22.04.1
-- Version de PHP : 8.1.2-1ubuntu2.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `info_doctolib`
--

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
  `idLieu` int NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `ville` varchar(100) NOT NULL,
  `code_postal` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `lieu`
--

INSERT INTO `lieu` (`idLieu`, `adresse`, `ville`, `code_postal`) VALUES
(2, '1200 chemin de la foux', 'Mouans-Sartoux', '06370'),
(3, '3 rue du bob', 'bob', '5050'),
(4, '16 rue victor lagrange', 'Lyon', '69007');

-- --------------------------------------------------------

--
-- Structure de la table `lieu_specialiste`
--

CREATE TABLE `lieu_specialiste` (
  `idSpecialiste` int NOT NULL,
  `idLieu` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `lieu_specialiste`
--

INSERT INTO `lieu_specialiste` (`idSpecialiste`, `idLieu`) VALUES
(9, 2),
(1, 3),
(7, 4),
(8, 4);

-- --------------------------------------------------------

--
-- Structure de la table `rdv`
--

CREATE TABLE `rdv` (
  `idRdv` int NOT NULL,
  `idUser` int DEFAULT NULL,
  `idSpecialiste` int DEFAULT NULL,
  `heure` datetime NOT NULL,
  `note` double DEFAULT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `specialisation`
--

CREATE TABLE `specialisation` (
  `idSpecialisation` int NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `specialisation`
--

INSERT INTO `specialisation` (`idSpecialisation`, `nom`) VALUES
(4, 'Generaliste'),
(5, 'Dentiste'),
(6, 'Orthoptiste'),
(7, 'Chirurgien'),
(8, 'Orthopediste'),
(9, 'Orthophoniste');

-- --------------------------------------------------------

--
-- Structure de la table `specialisation_doc`
--

CREATE TABLE `specialisation_doc` (
  `idSpecialiste` int NOT NULL,
  `idSpecialisation` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `specialisation_doc`
--

INSERT INTO `specialisation_doc` (`idSpecialiste`, `idSpecialisation`) VALUES
(1, 4),
(9, 4),
(7, 5),
(8, 8);

-- --------------------------------------------------------

--
-- Structure de la table `specialiste`
--

CREATE TABLE `specialiste` (
  `idSpecialiste` int NOT NULL,
  `idUser` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tarif` decimal(10,2) DEFAULT NULL,
  `moyenneNote` decimal(3,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `specialiste`
--

INSERT INTO `specialiste` (`idSpecialiste`, `idUser`, `description`, `tarif`, `moyenneNote`) VALUES
(1, 1, 'bob le bricoleur', '20.00', '5.00'),
(7, 22, 'Dentiste', '100.00', '3.80'),
(8, 23, 'Orthopediste', '80.00', '4.50'),
(9, 18, 'Docteur', '500.00', '5.00');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `idUser` int NOT NULL,
  `name` varchar(25) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`idUser`, `name`, `password`, `admin`) VALUES
(1, 'bob', '1234', 0),
(2, 'root', 'a', 1),
(18, 'Benjamin', 'rtfm', 1),
(21, 'maxime', '1234', 0),
(22, 'Vianney', 'boubou', 0),
(23, 'Eugene', 'souls', 0),
(24, 'Jade', 'jeux', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
  ADD PRIMARY KEY (`idLieu`);

--
-- Index pour la table `lieu_specialiste`
--
ALTER TABLE `lieu_specialiste`
  ADD PRIMARY KEY (`idSpecialiste`,`idLieu`),
  ADD KEY `id_lieu` (`idLieu`);

--
-- Index pour la table `rdv`
--
ALTER TABLE `rdv`
  ADD PRIMARY KEY (`idRdv`),
  ADD KEY `id_user` (`idUser`),
  ADD KEY `id_specialiste` (`idSpecialiste`);

--
-- Index pour la table `specialisation`
--
ALTER TABLE `specialisation`
  ADD PRIMARY KEY (`idSpecialisation`);

--
-- Index pour la table `specialisation_doc`
--
ALTER TABLE `specialisation_doc`
  ADD PRIMARY KEY (`idSpecialiste`,`idSpecialisation`),
  ADD KEY `id_specialisation` (`idSpecialisation`);

--
-- Index pour la table `specialiste`
--
ALTER TABLE `specialiste`
  ADD PRIMARY KEY (`idSpecialiste`),
  ADD KEY `id_user` (`idUser`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
  MODIFY `idLieu` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `rdv`
--
ALTER TABLE `rdv`
  MODIFY `idRdv` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `specialisation`
--
ALTER TABLE `specialisation`
  MODIFY `idSpecialisation` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `specialiste`
--
ALTER TABLE `specialiste`
  MODIFY `idSpecialiste` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `lieu_specialiste`
--
ALTER TABLE `lieu_specialiste`
  ADD CONSTRAINT `lieu_specialiste_ibfk_1` FOREIGN KEY (`idSpecialiste`) REFERENCES `specialiste` (`idSpecialiste`),
  ADD CONSTRAINT `lieu_specialiste_ibfk_2` FOREIGN KEY (`idLieu`) REFERENCES `lieu` (`idLieu`);

--
-- Contraintes pour la table `rdv`
--
ALTER TABLE `rdv`
  ADD CONSTRAINT `rdv_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `rdv_ibfk_2` FOREIGN KEY (`idSpecialiste`) REFERENCES `specialiste` (`idSpecialiste`);

--
-- Contraintes pour la table `specialisation_doc`
--
ALTER TABLE `specialisation_doc`
  ADD CONSTRAINT `specialisation_doc_ibfk_1` FOREIGN KEY (`idSpecialiste`) REFERENCES `specialiste` (`idSpecialiste`),
  ADD CONSTRAINT `specialisation_doc_ibfk_2` FOREIGN KEY (`idSpecialisation`) REFERENCES `specialisation` (`idSpecialisation`);

--
-- Contraintes pour la table `specialiste`
--
ALTER TABLE `specialiste`
  ADD CONSTRAINT `specialiste_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;