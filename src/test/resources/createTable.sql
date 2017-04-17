drop table if exists user;
create table `user`(
	`id` int(16) unsigned not null auto_increment,
    `name` varchar(64) not null,
    `password` varchar(128) not null,
    `salt` varchar(32) not null default '',
    `header_url` varchar(256) not null default '',
    primary key(`id`),
    unique key `name`(`name`)
)engine=Innodb default charset=utf8;


drop table if exists question;
create table question(
	id int(16) unsigned not null auto_increment,
    title varchar(64) not null,
	content text not null,
    user_id int(16) not null,
    create_date datetime not null,
    comment_count int null,
    primary key(id),
    index `date_index`(create_date asc)

    drop table if exists `login_ticket`;
create table `login_ticket`(
	id int not null auto_increment,
    user_id int(16) unsigned not null,
    ticket varchar(45) not null,
    expired datetime not null ,
    `status` int not null default 0,
    primary key(id),
    unique index ticket_UNIQUE (ticket)
)engine=InnoDB,charset=utf8
);


drop table if exists `comment`;
create table `comment`(
	id int not null auto_increment,
    `content` text not null,
    entity_id int not null,
    entity_type varchar(64) not null,
    create_date datetime not null,
    user_id int not null,
    `status` int not null default 0,
    primary key(id),
    unique index `entity_id_index` (entity_id),
    unique index `entity_type_index` (entity_type)
)engine=InnoDB,charset=utf8;


drop table if exists `message`;
create table `message`(

id int not null auto_increment,
from_id int not null ,
to_id int not null,
has_read int not null default 0,
content text not null,
create_date datetime not null,
conversation_id varchar(128) not null,
primary key(id),
index from_id_index(from_id),
index to_id_index(to_id)

)engine=InnoDB,charset=utf8
