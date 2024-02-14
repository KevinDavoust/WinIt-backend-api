/*INSERT TO TABLE SPORT */
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Football', 'https://example.com/sport_image.jpg', 11);
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Volley', 'https://images.pexels.com/photos/1263426/pexels-photo-1263426.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 6);
INSERT INTO sport (name, image_url, number_of_players)
VALUES ('Rugby', 'https://images.pexels.com/photos/3639065/pexels-photo-3639065.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 15);

/*INSERT TO TABLE TEAM */
INSERT INTO team (name, sport_id)
VALUES ('One', 1);
INSERT INTO team (name, sport_id)
VALUES ('Two', 1);

/*INSERT TO TABLE TOURNAMENT */
INSERT INTO Tournament (
    name,
    created_At,
    inscription_limit_date,
    city,
    number_of_participants,
    sport_id,
    level,
    type,
    format,
    image_url
)
VALUES (
           'Tournois test1',
           '2024-02-01 12:00:00',
           '2024-02-15',
           'Bordeaux',
           20,
           1,
           'easy',
           'test',
           'test',
           'https://example.com/sport_image.jpg'
       );
INSERT INTO Tournament (
    name,
    created_At,
    inscription_limit_date,
    city,
    number_of_participants,
    sport_id,
    level,
    type,
    format,
    image_url
)
VALUES (
           'Tournois test2',
           '2024-02-01 12:00:00',
           '2024-02-15',
           'Bordeaux',
           20,
           1,
           'easy',
           'test',
           'test',
           'https://example.com/sport_image.jpg'
       );