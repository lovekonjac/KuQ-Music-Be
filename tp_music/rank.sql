create table tp_music.`rank`
(
    id         bigint unsigned auto_increment
        primary key,
    songListId bigint unsigned          not null,
    consumerId bigint unsigned          not null,
    score      int unsigned default '0' not null,
    constraint consumerId
        unique (consumerId, songListId)
)
    charset = utf8mb3;

