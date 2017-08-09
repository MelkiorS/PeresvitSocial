USE peresvitDB;
# Spring Social Connection Repository
# create table UserConnection (userId varchar(255) not null,
#                              providerId varchar(255) not null,
#                              providerUserId varchar(255),
#                              rank int not null,
#                              displayName varchar(255),
#                              profileUrl varchar(512),
#                              imageUrl varchar(512),
#                              accessToken varchar(512) not null,
#                              secret varchar(512),
#                              refreshToken varchar(512),
#                              expireTime bigint,
#   primary key (userId, providerId, providerUserId));
# create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
drop table if exists userconnection;
create table UserConnection (userId varchar(255) not null,providerId varchar(255) not null,providerUserId varchar(255),rank int not null,displayName varchar(255),profileUrl varchar(512),imageUrl varchar(512),accessToken varchar(512) not null,secret varchar(512),refreshToken varchar(512),expireTime bigint,primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
INSERT INTO  resourceType(typeName) VALUES ('TEXT'),('PHOTO'),('VIDEO'),('OTHER');
INSERT INTO  resourceGroupType(groupName, caption) VALUES ('BASE_TECHNIQUE', 'Базовая техника'),('BASE_TECH_COMPLEX', 'Базовая техника комплекс'),('SPECIAL_PHYSICAL_TRAININGS', 'Специальные упражнения'),('GENERAL_PHYSICAL_TRAININGS', 'Базовые упражнения'),('ANOTHER_SUBJECTS', 'Прочее'),('COMPETITION', 'Соревнования');
INSERT INTO  role(roleName) VALUES ('LEVEL_1'),('LEVEL_2'),('LEVEL_3'),('USER'), ('ADMIN');
INSERT INTO  combatArt(combatArtName) VALUES ('Фрі-Файт'),('Тайдзі-Цюань'),('Шаолінь Кунг-фу');
INSERT INTO  city(cityName) VALUES ('Київ'),('Львів'),('Дніпро');
INSERT INTO  club(clubName) VALUES ('Клуб1'),('Клуб2');
INSERT INTO  mark(markName) VALUES ('Пояс101'),('Пояс202'),('Награда303'),('Призер2016');
INSERT INTO  user(firstName, lastName, email, password, avatarURL , roleId, combatArtId) VALUES ('Admin', 'Peresvit', 'admin@mail', '123456','https://cdn3.iconfinder.com/data/icons/users-6/100/654853-user-men-2-512.png', 5, 1);
INSERT INTO  user(firstName, lastName, email, password, avatarURL , roleId, combatArtId) VALUES ('Степан', 'Степаненко', 'user1@mail', '123456','http://s3.amazonaws.com/37assets/svn/765-default-avatar.png', 4, 1);
INSERT INTO  user(firstName, lastName, email, password, avatarURL , roleId, combatArtId, cityId, clubId, mentor_userId) VALUES ('Василь', 'Петренко', 'user@mail', '123456','http://s3.amazonaws.com/37assets/svn/765-default-avatar.png', 4, 1, 1, 1, 2);
INSERT INTO chat(chatTitle, ownerId) VALUES ('Chat', 1);
INSERT INTO chat(chatTitle, ownerId) VALUES ('Chat #2', 1);
INSERT INTO chat(chatTitle, ownerId) VALUES ('dialog', 1);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('Hello', '2002-06-04 18:25:08', FALSE, ',', 1, 1);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('Its functional message', '2007-06-04 18:25:08', TRUE, ',', 1, 2);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('just some message', '2009-06-04 18:25:08', FALSE, ',', 1, 3);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('just some message', '2009-06-04 18:29:08', FALSE, ',', 2, 3);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('just some message', '2009-06-04 18:27:08', FALSE, ',', 2, 3);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('just some message', '2009-06-04 18:28:08', FALSE, ',', 3, 3);
INSERT INTO message(content, createdAt, functional, readStatus, chatId, senderId) VALUES ('just some message', '2009-06-04 18:24:08', FALSE, ',', 3, 3);
INSERT INTO  resourceGroup(roleId,resourceGroupTypeId) VALUES (2,2);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_photo1',1,2);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_photo2',1,2);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_doc1',1,1);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_doc2',1,1);
INSERT INTO  resource(url,title,resourceGroupId,resourceTypeId) VALUES ('some_url','some_video',1,3);
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (1,'CHAPTER 1.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (1,'CHAPTER 1.2');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (2,'CHAPTER 2.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (2,'CHAPTER 2.2');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (3,'CHAPTER 3.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (3,'CHAPTER 3.2');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (4,'CHAPTER 4.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (4,'CHAPTER 4.2');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (5,'CHAPTER 5.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (5,'CHAPTER 5.2');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (6,'CHAPTER 6.1');
INSERT INTO  resourceGroupTypeChapter(resourceGroupTypeId,chapterName) VALUES (6,'CHAPTER 6.2');

INSERT into article(articleName, chapterId, roleId, resourceGroupTypeId, context) VALUES ('Статья 1.1', 1, 1, 1, "Содержимое статьи 1.1");
INSERT into article(articleName, chapterId, roleId, resourceGroupTypeId, context) VALUES ('Статья 1.2', 1, 1, 1, "Содержимое статьи 1.2");
INSERT into article(articleName, chapterId, roleId, resourceGroupTypeId, context) VALUES ('Статья 2.1', 2, 1, 1, "Содержимое статьи 2.1");

INSERT into event(id, name, start, finish) VALUES (1, 'Новый год', '2016-12-31','2016-12-31');
INSERT into event(id, name, start, finish) VALUES (2, '8 марта', '2017-03-08','2017-03-08');
INSERT into event(id, name, start, finish) VALUES (3, '1 мая', '2017-05-01','2017-05-01');

INSERT INTO achievement(achievementName, description, userId) VALUES ('Досягнення 1', 'Опис досягнення 1', 1);
INSERT INTO achievement(achievementName, description, userId) VALUES ('Досягнення 2', 'Опис досягнення 2', 2);

INSERT into userGroup(id, name) VALUES (1, 'Group level 1');
INSERT into userGroup(id, name) VALUES (2, 'Group level 2');

INSERT into user_groups(usergroup_id, user_id) VALUES (1, 1);
INSERT into user_groups(usergroup_id, user_id) VALUES (1, 2);
INSERT into user_groups(usergroup_id, user_id) VALUES (2, 1);
INSERT into user_groups(usergroup_id, user_id) VALUES (2, 3);

INSERT INTO user_chat(userId, chatId) VALUES (1, 1);
INSERT INTO user_chat(userId, chatId) VALUES (2, 1);
INSERT INTO user_chat(userId, chatId) VALUES (3, 1);
INSERT INTO user_chat(userId, chatId) VALUES (1, 2);
INSERT INTO user_chat(userId, chatId) VALUES (2, 2);
INSERT INTO user_chat(userId, chatId) VALUES (3, 2);
INSERT INTO user_chat(userId, chatId) VALUES (1, 3);
INSERT INTO user_chat(userId, chatId) VALUES (3, 3);

