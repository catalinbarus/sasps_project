CREATE TABLE my_game_list(
    my_game_list_id NUMBER(10) GENERATED BY DEFAULT ON NULL AS IDENTITY NOCACHE,
    game_id NUMBER(6),
    user_id NUMBER(6),
    status_id NUMBER(6),
    created_at TIMESTAMP(6)
);

ALTER TABLE my_game_list ADD(
    CONSTRAINT constraint_game_id_for_game_list_fk FOREIGN KEY(game_id) REFERENCES video_games
        (game_id),
    CONSTRAINT constraint_user_id_for_game_list_fk FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT constraint_status_id_for_game_list_fk FOREIGN KEY(status_id) REFERENCES
        game_list_status(status_id)
);

commit;