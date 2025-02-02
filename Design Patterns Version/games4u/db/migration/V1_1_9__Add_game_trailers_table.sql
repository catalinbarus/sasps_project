CREATE TABLE trailers(
     trailer_id NUMBER(10) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
     youtube_id VARCHAR2(4000),
     game_id NUMBER(6)
);

ALTER TABLE trailers ADD(
    CONSTRAINT game_id_for_trailer_fk FOREIGN KEY(game_id) REFERENCES video_games(game_id)
    );

commit;