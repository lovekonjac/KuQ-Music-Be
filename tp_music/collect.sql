create table tp_music.collect
(
    id           int unsigned auto_increment
        primary key,
    user_id      int unsigned not null,
    type         tinyint      not null,
    song_id      int unsigned null,
    song_list_id int unsigned null,
    create_time  datetime     not null
)
    charset = utf8mb3;

