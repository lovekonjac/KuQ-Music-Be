create table tp_music.song
(
    id           int unsigned auto_increment
        primary key,
    singer_id    int unsigned not null,
    name         varchar(45)  not null,
    introduction varchar(255) null,
    create_time  datetime     not null comment '发行时间',
    update_time  datetime     not null,
    pic          varchar(255) null,
    lyric        text         null,
    url          varchar(255) not null
)
    charset = utf8mb3;

