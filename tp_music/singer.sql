create table tp_music.singer
(
    id           int unsigned auto_increment
        primary key,
    name         varchar(45)  not null,
    sex          tinyint      null,
    pic          varchar(255) null,
    birth        datetime     null,
    location     varchar(45)  null,
    introduction varchar(255) null
)
    charset = utf8mb3;

