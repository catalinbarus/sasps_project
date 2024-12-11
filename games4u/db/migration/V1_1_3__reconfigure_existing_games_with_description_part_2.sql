INSERT INTO video_games VALUES (1, 'Pokemon Platinum', 1, 1, TO_DATE('13-09-2008', 'dd-MM-yyyy'),
                                1, 'ESRB: Everyone', 'An additional chapter in the Pokemon ' ||
                                                     'franchise, expanding on the Pokemon Diamond/Pearl series with new story elements to further enjoy the saga of Nintendo''s "collect ''em all" RPG series.');
INSERT INTO video_games VALUES (2, 'Fire Emblem Awakening', 2, 1, TO_DATE('19-04-2012',
    'dd-MM-yyyy'), 17, 'ESRB: Teen', 'In the visually stunning world of the Fire Emblem Awakening game, you command and fight alongside an army of spirited heroes standing against an enemy with the power to destroy empires; a dark dragon whose agents include armies of the undead.');

commit;