USE peresvitdb;
# Spring Social Connection Repository
# create table UserConnection (user_id varchar(255) not null,
#                              providerId varchar(255) not null,
#                              provideruser_id varchar(255),
#                              rank int not null,
#                              displayName varchar(255),
#                              profileUrl varchar(512),
#                              imageUrl varchar(512),
#                              accessToken varchar(512) not null,
#                              secret varchar(512),
#                              refreshToken varchar(512),
#                              expireTime bigint,
#   primary key (user_id, providerId, provideruser_id));
# create unique index UserConnectionRank on UserConnection(user_id, providerId, rank);
drop table if exists userconnection;
create table UserConnection (user_id varchar(255) not null,providerId varchar(255) not null,provideruser_id varchar(255),rank int not null,displayName varchar(255),profileUrl varchar(512),imageUrl varchar(512),accessToken varchar(512) not null,secret varchar(512),refreshToken varchar(512),expireTime bigint,primary key (user_id, providerId, provideruser_id));
create unique index UserConnectionRank on UserConnection(user_id, providerId, rank);


delete from user_role;
delete from roles;
delete from users;

INSERT INTO roles(role_id,role_name) VALUES (1,'ROLE_ADMIN'), (2,'ROLE_USER'), (3,'LEVEL_1'), (4,'LEVEL_2'), (5,'LEVEL_3');
INSERT INTO users(user_id, email, password, first_name, last_name,avatarURL ) VALUES (1, 'admin@mail', '123456', 'SuperAdmin', 'Peresvit', 'https://cdn3.iconfinder.com/data/icons/users-6/100/654853-user-men-2-512.png');
INSERT INTO users(user_id, email, password, first_name, last_name,avatarURL ) VALUES (2, 'user1@mail', '123456', 'Степан', 'Степаненко', 'http://s3.amazonaws.com/37assets/svn/765-default-avatar.png');
INSERT INTO users(user_id, email, password, first_name, last_name,avatarURL ) VALUES (3, 'user2@mail', '123456', 'Василь', 'Петренко', 'http://s3.amazonaws.com/37assets/svn/765-default-avatar.png');
INSERT INTO user_role (user_id, role_id) VALUES (1,1), (2,2), (3,2);


INSERT INTO  resource_Type(type_name) VALUES ('TEXT'),('PHOTO'),('VIDEO'),('OTHER');
INSERT INTO  resource_group_type(group_name, caption) VALUES ('BASE_TECHNIQUE', 'Базовая техника'),('BASE_TECH_COMPLEX', 'Базовая техника комплекс'),('SPECIAL_PHYSICAL_TRAININGS', 'Специальные упражнения'),('GENERAL_PHYSICAL_TRAININGS', 'Базовые упражнения'),('ANOTHER_SUBJECTS', 'Прочее'),('COMPETITION', 'Соревнования');
# INSERT INTO  roles(roleName) VALUES ('LEVEL_1'),('LEVEL_2'),('LEVEL_3'),('USER'), ('ADMIN');
INSERT INTO  combat_art(combat_art_name) VALUES ('Фрі-Файт'),('Тайдзі-Цюань'),('Шаолінь Кунг-фу');
INSERT INTO  city(city_name) VALUES ('Київ'),('Львів'),('Дніпро');
INSERT INTO  club(club_name) VALUES ('Клуб1'),('Клуб2');
INSERT INTO  mark(mark_name) VALUES ('Пояс101'),('Пояс202'),('Награда303'),('Призер2016');
# INSERT INTO  users(first_name, last_name, email, password, avatarURL , , combat_art_id) VALUES ('Admin', 'Peresvit', 'admin@mail', '123456','https://cdn3.iconfinder.com/data/icons/users-6/100/654853-user-men-2-512.png', 5, 1);
# INSERT INTO  users(first_name, last_name, email, password, avatarURL , role_id, combat_art_id) VALUES ('Степан', 'Степаненко', 'user1@mail', '123456','http://s3.amazonaws.com/37assets/svn/765-default-avatar.png', 4, 1);
# INSERT INTO  users(first_name, last_name, email, password, avatarURL , role_id, combat_art_id, city_id, club_id, users.userId) VALUES ('Василь', 'Петренко', 'user@mail', '123456','http://s3.amazonaws.com/37assets/svn/765-default-avatar.png', 4, 1, 1, 1, 2);
INSERT INTO chat(chat_title, owner_id) VALUES ('Chat', 1);
INSERT INTO chat(chat_title, owner_id) VALUES ('Chat #2', 1);
INSERT INTO chat(chat_title, owner_id) VALUES ('dialog', 1);
INSERT INTO message(content, created_at, functional, read_status, chat_id, sender_id) VALUES ('Hello', '2002-06-04 18:25:08', FALSE, ',', 1, 1);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('Its functional message', '2007-06-04 18:25:08', TRUE, ',', 1, 2);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('just some message', '2009-06-04 18:25:08', FALSE, ',', 1, 3);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('just some message', '2009-06-04 18:29:08', FALSE, ',', 2, 3);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('just some message', '2009-06-04 18:27:08', FALSE, ',', 2, 3);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('just some message', '2009-06-04 18:28:08', FALSE, ',', 3, 3);
INSERT INTO message(content, created_At, functional, read_status, chat_id, sender_id) VALUES ('just some message', '2009-06-04 18:24:08', FALSE, ',', 3, 3);
INSERT INTO  resource_group(role_id,resource_group_type_id) VALUES (2,2);
INSERT INTO  resource(url,title,resource_group_id,resource_type_id) VALUES ('some_url','some_photo1',1,2);
INSERT INTO  resource(url,title,resource_group_id,resource_type_id) VALUES ('some_url','some_photo2',1,2);
INSERT INTO  resource(url,title,resource_group_id,resource_type_id) VALUES ('some_url','some_doc1',1,1);
INSERT INTO  resource(url,title,resource_group_id,resource_type_id) VALUES ('some_url','some_doc2',1,1);
INSERT INTO  resource(url,title,resource_group_id,resource_type_id) VALUES ('some_url','some_video',1,3);
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (1,'CHAPTER 1.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (1,'CHAPTER 1.2');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (2,'CHAPTER 2.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (2,'CHAPTER 2.2');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (3,'CHAPTER 3.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (3,'CHAPTER 3.2');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (4,'CHAPTER 4.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (4,'CHAPTER 4.2');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (5,'CHAPTER 5.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (5,'CHAPTER 5.2');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (6,'CHAPTER 6.1');
INSERT INTO  resource_group_type_chapter(resource_group_type_id,chapter_name) VALUES (6,'CHAPTER 6.2');

INSERT into article(article_name, chapter_id, role_id, resource_group_type_id, context) VALUES ('Статья 1.1', 1, 1, 1, 'Содержимое статьи 1.1');
INSERT into article(article_name, chapter_id, role_id, resource_group_type_id, context) VALUES ('Статья 1.2', 1, 1, 1, 'Содержимое статьи 1.2');
INSERT into article(article_name, chapter_id, role_id, resource_group_type_id, context) VALUES ('Статья 2.1', 2, 1, 1, 'Содержимое статьи 2.1');

INSERT into event(id, name, start, finish) VALUES (1, 'Новый год', '2016-12-31','2016-12-31');
INSERT into event(id, name, start, finish) VALUES (2, '8 марта', '2017-03-08','2017-03-08');
INSERT into event(id, name, start, finish) VALUES (3, '1 мая', '2017-05-01','2017-05-01');

INSERT INTO achievement(achievement_name, description, user_id) VALUES ('Досягнення 1', 'Опис досягнення 1', 1);
INSERT INTO achievement(achievement_name, description, user_id) VALUES ('Досягнення 2', 'Опис досягнення 2', 2);

INSERT into user_group(id, name) VALUES (1, 'Group level 1');
INSERT into user_group(id, name) VALUES (2, 'Group level 2');

INSERT into user_groups(usergroup_id, user_id) VALUES (1, 1);
INSERT into user_groups(usergroup_id, user_id) VALUES (1, 2);
INSERT into user_groups(usergroup_id, user_id) VALUES (2, 1);
INSERT into user_groups(usergroup_id, user_id) VALUES (2, 3);

INSERT INTO user_chat(user_id, chat_id) VALUES (1, 1);
INSERT INTO user_chat(user_id, chat_id) VALUES (2, 1);
INSERT INTO user_chat(user_id, chat_id) VALUES (3, 1);
INSERT INTO user_chat(user_id, chat_id) VALUES (1, 2);
INSERT INTO user_chat(user_id, chat_id) VALUES (2, 2);
INSERT INTO user_chat(user_id, chat_id) VALUES (3, 2);
INSERT INTO user_chat(user_id, chat_id) VALUES (1, 3);
INSERT INTO user_chat(user_id, chat_id) VALUES (3, 3);

