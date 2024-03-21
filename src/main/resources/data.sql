-- Insertion des sports
INSERT INTO sport (name, image_url, number_of_players)
VALUES
    ('Football', 'https://example.com/sport_image.jpg', 11),
    ('Volley', 'https://images.pexels.com/photos/1263426/pexels-photo-1263426.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 6),
    ('Rugby', 'https://images.pexels.com/photos/3639065/pexels-photo-3639065.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 15);

-- Création des utilisateurs
INSERT INTO `user` (is_enabled, created_at, date_of_birth, updated_at, city, email, first_name, image_url, last_name, password, role)
VALUES
    (true, NOW(), '1990-01-01', NOW(), 'Paris', 'admin1@winit.com', 'Admin', NULL, 'One', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_ADMIN'),
    (true, NOW(), '1990-01-02', NOW(), 'Paris', 'admin2@winit.com', 'Admin', NULL, 'Two', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_ADMIN'),
    (true, NOW(), '1990-01-03', NOW(), 'Paris', 'user1@winit.com', 'User', NULL, 'One', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_USER'),
    (true, NOW(), '1990-01-04', NOW(), 'Paris', 'user2@winit.com', 'User', NULL, 'Two', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_USER'),
    (true, NOW(), '1990-01-05', NOW(), 'Paris', 'user3@winit.com', 'User', NULL, 'Three', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_USER'),
    (true, NOW(), '1990-01-06', NOW(), 'Paris', 'user4@winit.com', 'User', NULL, 'Four', '$2a$10$8UwnyZng3YvB6ka1zr.WGuAkn/W5F2P7lTzjSRNSi6Z7vfjjDjD.m', 'ROLE_USER');

-- Création des équipes et attribution des administrateurs
INSERT INTO team (is_validated, lead_team_id, sport_id, name)
VALUES
    (true, 1, 1, 'Équipe A'),
    (true, 2, 1, 'Équipe B'),
    (true, 3, 2, 'Équipe C'),
    (true, 4, 2, 'Équipe D'),
    (true, 1, 3, 'Équipe E'),
    (true, 2, 3, 'Équipe F'),
    (true, 3, 1, 'Équipe G'),
    (true, 3, 1, 'Équipe H'),
    (true, 3, 1, 'Équipe I'),
    (true, 1, 1, 'Équipe J'),
    (true, 1, 2, 'Équipe K'),
    (true, 1, 2, 'Équipe L'),
    (true, 3, 2, 'Équipe M'),
    (true, 3, 3, 'Équipe N');

-- Remplissage de la table de jointure user_team avec plusieurs utilisateurs par équipe
INSERT INTO user_team (team_id, user_id)
SELECT
    team_id,
    user_id
FROM (
         SELECT
             t.id AS team_id,
             u.id AS user_id,
             ROW_NUMBER() OVER (PARTITION BY t.id ORDER BY RAND()) AS rn
         FROM
             team t
                 CROSS JOIN
             `user` u
         WHERE
             t.id <= 14 -- Limiter aux 14 premières équipes (ou ajuster selon le nombre d'équipes)
         ORDER BY
             RAND() -- Ordre aléatoire
     ) AS sub
WHERE
    rn <= 3; -- Nombre d'utilisateurs par équipe (ou ajuster selon vos besoins)

-- Création des tournois
INSERT INTO tournament (game_length, max_number_of_teams, min_number_of_teams, players_per_team, created_at, current_number_of_participants, date, inscription_limit_date, sport_id, format, image_url, name, place, player_category, privacy)
VALUES
    (90, 8, 2, 11, NOW(), 0, '2023-04-01 10:00:00.000000', '2023-03-30 23:59:59.000000', 1, 'Format 3', NULL, 'Tournoi de football', 'Marseille', 'Amateur', 'Public'),

    (90, 8, 2, 6, NOW(), 0, '2023-04-10 15:00:00.000000', '2023-04-08 23:59:59.000000', 2, 'Format 3', NULL, 'Tournoi de volleyball', 'Paris', 'Amateur', 'Public'),

    (90, 8, 2, 15, NOW(), 0, '2024-09-05 14:00:00.000000', '2024-09-03 23:59:59.000000', 3, 'Format 3', NULL, 'Tournoi de rugby', 'Bordeaux', 'Amateur', 'Public'),

    (90, 8, 2, 5, NOW(), 0, '2024-10-20 13:00:00.000000', '2024-10-18 23:59:59.000000', 1, 'Format 3', NULL, 'Tournoi de football', 'Bordeaux', 'Amateur', 'Public'),

    (90, 8, 2, 10, NOW(), 0, '2022-05-01 11:00:00.000000', '2022-04-29 23:59:59.000000', 2, 'Format 3', NULL, 'Tournoi de volleyball', 'Bordeaux', 'Amateur', 'Public'),

    (90, 8, 2, 7, NOW(), 0, '2025-05-10 12:00:00.000000', '2025-05-08 23:59:59.000000', 3, 'Format 3', NULL, 'Tournoi de rugby', 'Paris', 'Amateur', 'Public'),

    (90, 8, 2, 6, NOW(), 0, '2025-05-15 17:00:00.000000', '2025-05-13 23:59:59.000000', 1, 'Format 3', NULL, 'Tournoi de football', 'Marseille', 'Amateur', 'Public'),

    (90, 8, 2, 9, NOW(), 0, '2024-05-25 09:00:00.000000', '2024-05-23 23:59:59.000000', 2, 'Format 3', NULL, 'Tournoi de volleyball', 'Bordeaux', 'Amateur', 'Public');




