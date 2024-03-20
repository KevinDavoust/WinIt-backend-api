create table `match` (is_draw bit, id bigint not null auto_increment, loser_team_id bigint, team1_id bigint not null, team2_id bigint not null, tournament_id bigint not null, winner_team_id bigint, score varchar(255), primary key (id)) engine=InnoDB;
create table sport (number_of_players integer not null, id bigint not null auto_increment, image_url varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table team (is_validated bit, id bigint not null auto_increment, lead_team_id bigint not null, sport_id bigint not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table tournament (game_length integer not null, max_number_of_teams integer not null, min_number_of_teams integer, players_per_team integer not null, created_at datetime(6) not null, current_number_of_participants bigint, date datetime(6) not null, id bigint not null auto_increment, inscription_limit_date datetime(6) not null, sport_id bigint not null, updated_at datetime(6), format varchar(255), image_url varchar(255), name varchar(255) not null, place varchar(255) not null, player_category varchar(255), privacy varchar(255), primary key (id)) engine=InnoDB;
create table `user` (is_enabled bit not null, created_at datetime(6), date_of_birth datetime(6), id bigint not null auto_increment, updated_at datetime(6), city varchar(255), email varchar(255) not null, first_name varchar(255) not null, image_url varchar(255), last_name varchar(255) not null, password varchar(255) not null, role varchar(255), primary key (id)) engine=InnoDB;
create table `user_team` (id bigint not null auto_increment, team_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB;
alter table sport add constraint UK_63em0e8bcxm3pqjooy08wquan unique (name);
alter table team add constraint UK_g2l9qqsoeuynt4r5ofdt1x2td unique (name);
alter table `user` add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table `match` add constraint FKbbm3l3xkk829oeihnomn2kcle foreign key (team1_id) references team (id);
alter table `match` add constraint FKi37tx90k8lwwwnr4fwheamp4o foreign key (team2_id) references team (id);
alter table `match` add constraint FKfxkrtlgs3ojsjb7xrc5pw1v55 foreign key (tournament_id) references tournament (id);
alter table tournament add constraint FKcd9y3gisxdycg7kp7pe6tfo1b foreign key (sport_id) references sport (id);
alter table `user_team` add constraint FK6u2lboj1xewj3j1rr8exiibvu foreign key (team_id) references team (id);
alter table `user_team` add constraint FK3t667uscjym3wwslwfvoo91h3 foreign key (user_id) references `user` (id);
create table `match` (is_draw bit, id bigint not null auto_increment, loser_team_id bigint, team1_id bigint not null, team2_id bigint not null, tournament_id bigint not null, winner_team_id bigint, score varchar(255), primary key (id)) engine=InnoDB;
create table sport (number_of_players integer not null, id bigint not null auto_increment, image_url varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table team (is_validated bit, id bigint not null auto_increment, lead_team_id bigint not null, sport_id bigint not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table tournament (game_length integer not null, max_number_of_teams integer not null, min_number_of_teams integer, players_per_team integer not null, created_at datetime(6) not null, current_number_of_participants bigint, date datetime(6) not null, id bigint not null auto_increment, inscription_limit_date datetime(6) not null, sport_id bigint not null, updated_at datetime(6), format varchar(255), image_url varchar(255), name varchar(255) not null, place varchar(255) not null, player_category varchar(255), privacy varchar(255), primary key (id)) engine=InnoDB;
create table `user` (is_enabled bit not null, created_at datetime(6), date_of_birth datetime(6), id bigint not null auto_increment, updated_at datetime(6), city varchar(255), email varchar(255) not null, first_name varchar(255) not null, image_url varchar(255), last_name varchar(255) not null, password varchar(255) not null, role varchar(255), primary key (id)) engine=InnoDB;
create table `user_team` (id bigint not null auto_increment, team_id bigint not null, user_id bigint not null, primary key (id)) engine=InnoDB;
alter table sport add constraint UK_63em0e8bcxm3pqjooy08wquan unique (name);
alter table team add constraint UK_g2l9qqsoeuynt4r5ofdt1x2td unique (name);
alter table `user` add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table `match` add constraint FKbbm3l3xkk829oeihnomn2kcle foreign key (team1_id) references team (id);
alter table `match` add constraint FKi37tx90k8lwwwnr4fwheamp4o foreign key (team2_id) references team (id);
alter table `match` add constraint FKfxkrtlgs3ojsjb7xrc5pw1v55 foreign key (tournament_id) references tournament (id);
alter table tournament add constraint FKcd9y3gisxdycg7kp7pe6tfo1b foreign key (sport_id) references sport (id);
alter table `user_team` add constraint FK6u2lboj1xewj3j1rr8exiibvu foreign key (team_id) references team (id);
alter table `user_team` add constraint FK3t667uscjym3wwslwfvoo91h3 foreign key (user_id) references `user` (id);