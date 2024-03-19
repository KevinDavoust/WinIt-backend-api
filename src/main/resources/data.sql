/*INSERT TO TABLE SPORT */
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Football', 'https://example.com/sport_image.jpg', 11);
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Volley', 'https://images.pexels.com/photos/1263426/pexels-photo-1263426.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 6);
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Rugby', 'https://images.pexels.com/photos/3639065/pexels-photo-3639065.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 15);

-- Remplir la base de données avec des données factices

-- Insertion d'équipes
INSERT INTO team (is_validated, lead_team_id, sport_id, name) VALUES
                                                                  (1, 1, 1, 'Equipe A'),
                                                                  (1, 2, 1, 'Equipe B'),
                                                                  (1, 3, 2, 'Equipe C'),
                                                                  (1, 4, 2, 'Equipe D'),
                                                                  (1, 5, 3, 'Equipe E'),
                                                                  (1, 6, 3, 'Equipe F');

-- Insertion de tournois
INSERT INTO tournament (game_length, max_number_of_teams, players_per_team, created_at, current_number_of_participants, date, inscription_limit_date, sport_id, name, place, player_category, privacy) VALUES
                                                                                                                                                                                                           (90, 8, 11, NOW(), 8, NOW(), NOW(), 1, 'Tournoi de Football 1', 'Stade 1', 'Professionnel', 'Public'),
                                                                                                                                                                                                           (90, 8, 11, NOW(), 8, NOW(), NOW(), 1, 'Tournoi de Football 2', 'Stade 2', 'Amateur', 'Privé'),
                                                                                                                                                                                                           (90, 8, 5, NOW(), 8, NOW(), NOW(), 2, 'Tournoi de Basketball 1', 'Gymnase 1', 'Professionnel', 'Public'),
                                                                                                                                                                                                           (90, 8, 5, NOW(), 8, NOW(), NOW(), 2, 'Tournoi de Basketball 2', 'Gymnase 2', 'Amateur', 'Privé'),
                                                                                                                                                                                                           (90, 8, 6, NOW(), 8, NOW(), NOW(), 3, 'Tournoi de Tennis 1', 'Court 1', 'Professionnel', 'Public'),
                                                                                                                                                                                                           (90, 8, 6, NOW(), 8, NOW(), NOW(), 3, 'Tournoi de Tennis 2', 'Court 2', 'Amateur', 'Privé');

-- Insertion de matchs
INSERT INTO `match` (is_draw, loser_team_id, team1_id, team2_id, tournament_id, winner_team_id, score) VALUES
                                                                                                           (0, NULL, 1, 2, 1, 1, '2-1'),
                                                                                                           (0, NULL, 3, 4, 2, 3, '3-2'),
                                                                                                           (0, NULL, 5, 6, 3, 5, '6-4'),
                                                                                                           (0, NULL, 1, 3, 1, 1, '2-0'),
                                                                                                           (0, NULL, 2, 4, 2, 2, '1-0'),
                                                                                                           (0, NULL, 3, 5, 3, 3, '3-3'),
                                                                                                           (0, NULL, 4, 6, 4, 6, '2-1'),
                                                                                                           (0, NULL, 5, 1, 5, 5, '3-2'),
                                                                                                           (0, NULL, 6, 2, 6, 6, '4-1'),
                                                                                                           (0, NULL, 1, 4, 1, 4, '2-2'),
                                                                                                           (0, NULL, 2, 5, 2, 5, '3-1'),
                                                                                                           (0, NULL, 3, 6, 3, 6, '2-0'),
                                                                                                           (0, NULL, 4, 1, 4, 4, '1-0'),
                                                                                                           (0, NULL, 5, 2, 5, 5, '2-1'),
                                                                                                           (0, NULL, 6, 3, 6, 6, '3-2'),
                                                                                                           (0, NULL, 1, 6, 1, 1, '2-1'),
                                                                                                           (0, NULL, 2, 3, 2, 2, '3-0'),
                                                                                                           (0, NULL, 3, 1, 3, 3, '2-1'),
                                                                                                           (0, NULL, 4, 2, 4, 4, '1-0'),
                                                                                                           (0, NULL, 5, 3, 5, 5, '3-3'),
                                                                                                           (0, NULL, 6, 4, 6, 6, '2-1');

