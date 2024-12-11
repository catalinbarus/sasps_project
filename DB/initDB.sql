CREATE TABLE roles(
	role_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	role_name VARCHAR2(20) CONSTRAINT role_name_nn NOT NULL
);


ALTER TABLE roles ADD(
    CONSTRAINT role_id_pk PRIMARY KEY(role_id)
);

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_MODERATOR');
INSERT INTO roles VALUES (3, 'ROLE_ADMIN');

CREATE TABLE users(
	user_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	username VARCHAR2(20) CONSTRAINT username_nn NOT NULL,
	email VARCHAR2(50) CONSTRAINT email_nn NOT NULL,
	password VARCHAR2(120) CONSTRAINT password_nn NOT NULL
);

ALTER TABLE users ADD(
    CONSTRAINT user_id_pk PRIMARY KEY(user_id),
    CONSTRAINT username_unique UNIQUE (username),
    CONSTRAINT email_unique UNIQUE (email)
);


CREATE TABLE user_roles(
	user_id NUMBER(6),
	role_id NUMBER(6)
);

ALTER TABLE user_roles ADD(
    CONSTRAINT user_id_fk FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT role_id_fk FOREIGN KEY(role_id) REFERENCES roles(role_id)
);


CREATE TABLE game_publishers(
	publisher_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	publisher_name VARCHAR2(50) CONSTRAINT publisher_name_nn NOT NULL,
	establish_date DATE CONSTRAINT publisher_establish_date_nn NOT NULL
);

ALTER TABLE game_publishers ADD(
    CONSTRAINT publisher_id_pk PRIMARY KEY(publisher_id)
);

INSERT INTO game_publishers VALUES (1, 'Nintendo', TO_DATE('23-09-1889', 'dd-MM-yyyy'));
INSERT INTO game_publishers VALUES (2, 'Game Freak', TO_DATE('26-04-1989', 'dd-MM-yyyy'));
INSERT INTO game_publishers VALUES (3, 'Sony Interactive Entertainment', TO_DATE('16-11-1993', 'dd-MM-yyyy'));
INSERT INTO game_publishers VALUES (4, 'SEGA', TO_DATE('03-06-1960', 'dd-MM-yyyy'));
INSERT INTO game_publishers VALUES (5, 'ATLUS', TO_DATE('07-04-1986', 'dd-MM-yyyy'));
INSERT INTO game_publishers VALUES (6, 'Riot Games', TO_DATE('01-09-2006', 'dd-MM-yyyy'));

CREATE TABLE game_developers(
	developer_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	developer_name VARCHAR2(50) CONSTRAINT developer_name_nn NOT NULL,
	establish_date DATE CONSTRAINT developer_establish_date_nn NOT NULL
);

ALTER TABLE game_developers ADD(
    CONSTRAINT developer_id_pk PRIMARY KEY(developer_id)
);

INSERT INTO game_developers VALUES (1, 'Game Freak', TO_DATE('26-04-1989', 'dd-MM-yyyy'));
INSERT INTO game_developers VALUES (2, 'Intelligent Systems', TO_DATE('01-12-1983', 'dd-MM-yyyy'));

CREATE TABLE genres(
	genre_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	genre_name VARCHAR2(100) CONSTRAINT genre_name_nn NOT NULL
);

ALTER TABLE genres ADD(
    CONSTRAINT genre_id_pk PRIMARY KEY(genre_id)
);

INSERT INTO genres VALUES (1, 'Role-Playing');
INSERT INTO genres VALUES (2, 'Action-Adventure');
INSERT INTO genres VALUES (3, 'Sandbox');
INSERT INTO genres VALUES (4, 'Sports');
INSERT INTO genres VALUES (5, 'VR');
INSERT INTO genres VALUES (6, 'Shooter');
INSERT INTO genres VALUES (7, 'Platformer');
INSERT INTO genres VALUES (8, 'MMO');
INSERT INTO genres VALUES (9, 'MOBA');
INSERT INTO genres VALUES (10, 'Simulation');
INSERT INTO genres VALUES (11, 'Racing');
INSERT INTO genres VALUES (12, 'Visual Novel');
INSERT INTO genres VALUES (13, 'Rougelike');
INSERT INTO genres VALUES (14, 'Metroidvania');
INSERT INTO genres VALUES (15, 'Party');
INSERT INTO genres VALUES (16, 'Puzzle');
INSERT INTO genres VALUES (17, 'Tactical Role-Playing');

CREATE TABLE video_games(
	game_id NUMBER(6) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
	game_name VARCHAR2(100) CONSTRAINT game_name_nn NOT NULL,
	developer_id NUMBER(6),
	publisher_id NUMBER(6),
	release_date DATE, -- Can be null for unreleased games
	genre_id NUMBER(6),
	rating VARCHAR2(50)
);

ALTER TABLE video_games ADD(
    CONSTRAINT game_id_pk PRIMARY KEY(game_id),
    CONSTRAINT publisher_id_fk FOREIGN KEY(publisher_id) REFERENCES game_publishers(publisher_id),
    CONSTRAINT developer_id_fk FOREIGN KEY(developer_id) REFERENCES game_developers(developer_id),
    CONSTRAINT genre_id_fk FOREIGN KEY(genre_id) REFERENCES genres(genre_id)
);


INSERT INTO video_games VALUES (1, 'Pokemon Platinum', 1, 1, TO_DATE('13-09-2008', 'dd-MM-yyyy'),
                                1, 'ESRB: Everyone');
INSERT INTO video_games VALUES (2, 'Fire Emblem Awakening', 2, 1, TO_DATE('19-04-2012',
    'dd-MM-yyyy'), 17, 'ESRB: Teen');





















