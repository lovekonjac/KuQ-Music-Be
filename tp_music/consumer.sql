create table tp_music.consumer
(
    id           int unsigned auto_increment
        primary key,
    username     varchar(255) not null,
    password     varchar(100) not null,
    sex          tinyint      null,
    phone_num    char(15)     null,
    email        char(30)     null,
    birth        datetime     null,
    introduction varchar(255) null,
    location     varchar(45)  null,
    avator       varchar(255) null,
    create_time  datetime     not null,
    update_time  datetime     not null,
    constraint email_UNIQUE
        unique (email),
    constraint phone_num_UNIQUE
        unique (phone_num),
    constraint username_UNIQUE
        unique (username)
)
    charset = utf8mb3;

