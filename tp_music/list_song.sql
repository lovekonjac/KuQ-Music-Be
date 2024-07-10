create table tp_music.list_song
(
    id           int unsigned auto_increment
        primary key,
    song_id      int unsigned not null,
    song_list_id int unsigned not null
)
    charset = utf8mb3;

