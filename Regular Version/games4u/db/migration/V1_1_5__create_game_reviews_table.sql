CREATE TABLE reviews(
       review_id NUMBER(10) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
       review VARCHAR2(4000),
       game_id NUMBER(6),
       user_id NUMBER(6),
       created_at TIMESTAMP(6)
);

ALTER TABLE reviews ADD(
    CONSTRAINT constraint_game_id_for_review_fk FOREIGN KEY(game_id) REFERENCES video_games(game_id),
    CONSTRAINT constraint_user_id_for_review_fk FOREIGN KEY(user_id) REFERENCES users(user_id)
    );

commit;