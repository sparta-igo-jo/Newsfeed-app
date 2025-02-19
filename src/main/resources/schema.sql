create table if not exists users
(
    id               bigint auto_increment
        primary key,
    email            varchar(255) not null,
    name             varchar(20)  not null,
    password         varchar(255) not null,
    profile_image    varchar(255) null,
    description      varchar(255)  null,
    followers_count  bigint       null,
    followings_count bigint       null,
    created_at       datetime(6)  not null,
    deleted_at       datetime(6)  null,
    updated_at       datetime(6)  not null,
    constraint PK_users
        primary key (id),
    constraint UK_users_name
        unique (name),
    constraint UK_users_email
        unique (email)
);

create table if not exists feeds
(
    id         bigint auto_increment
        primary key,
    user_id    bigint       null,
    title      varchar(255) not null,
    contents   varchar(255) null,
    feed_image varchar(255) null,
    like_count bigint       null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    constraint PK_feeds
        primary key (id),
    constraint FK_feeds_users
        foreign key (user_id) references users (id)
);

create table if not exists comments
(
    id         bigint auto_increment
        primary key,
    content    varchar(255) not null,
    user_id    bigint       not null,
    feed_id    bigint       not null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    constraint PK_comments
        primary key (id),
    constraint FK_comments_users
        foreign key (user_id) references users (id),
    constraint FK_comments_feeds
        foreign key (feed_id) references feeds (id)
);

create table if not exists follows
(
    id           bigint auto_increment
        primary key,
    follower_id  bigint      not null,
    following_id bigint      not null,
    created_at   datetime(6) not null,
    constraint PK_follows
        primary key (id),
    constraint UK_follows_follower_following
        unique (follower_id, following_id),
    constraint FK_follows_following_users
        foreign key (following_id) references users (id),
    constraint FK_follows_follower_users
        foreign key (follower_id) references users (id)
);

create table if not exists likes
(
    id         bigint auto_increment
        primary key,
    user_id    bigint      not null,
    feed_id    bigint      not null,
    created_at datetime(6) not null,
    constraint PK_likes
        primary key (id),
    constraint UK_likes_user_feed
        unique (user_id, feed_id),
    constraint FK_likes_feeds
        foreign key (feed_id) references feeds (id),
    constraint FK_likes_users
        foreign key (user_id) references users (id)
);

create index IDX_follows_follower_following on follows (follower_id, following_id);
create index IDX_likes_user_feed on likes (user_id, feed_id);