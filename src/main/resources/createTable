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
    create_time datetime not null,
    comment_count int null,
    primary key(id),
    index `date_index`(create_time asc)
);
