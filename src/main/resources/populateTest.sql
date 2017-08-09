delete from article;
delete from user;
delete from role;
delete from usergroup;

insert into Role(RoleId, roleName) VALUES (1, 'ADMIN');
insert into Role(RoleId, roleName) VALUES (2, 'User');

delete from city;

insert into city(cityId, cityName) VALUES (1, 'Kyiv');
insert into city(cityId, cityName) VALUES (2, 'Odessa');


delete from achievement;

insert into achievement(achievementId, achievementName, description, imageurl) VALUES (1, 'Result 1', 'Competition result 1', '');
insert into achievement(achievementId, achievementName, description, imageurl) VALUES (2, 'Result 2', 'Competition result 2', '');


delete from club;

insert into club(clubId, clubName, address) VALUES (1, 'Club 1', 'Kyiv');
insert into club(clubId, clubName, address) VALUES (2, 'Club 2', 'Odessa');

delete from combatart;

insert into combatart(combatartId, combatartName) VALUES (1, 'Art 1');
insert into combatart(combatartId, combatartName) VALUES (2, 'Art 2');

delete from event;

insert into event(id, name, start, finish, created, eventurl, description, place, connectall) VALUES (1, 'Event 1', '2017-01-01 00:00:00','2017-02-01 00:00:00', '2017-01-01 00:00:00', '', '', '', false);
insert into event(id, name, start, finish, created, eventurl, description, place, connectall) VALUES (2, 'Event 2', '2017-02-01 00:00:00','2017-03-01 00:00:00', '2017-02-01 00:00:00', '', '', '', true);

delete from mark;

insert into mark(markid, markname, about, imageurl) VALUES (1, 'Mark 1', 'About mark 1', '');
insert into mark(markid, markname, about, imageurl) VALUES (2, 'Mark 2', 'About mark 2', '');

delete from post;

insert into post(postid, body, create_date, title, url) VALUES (1, 'Body 1', '2017-01-01 00:00:00', 'Post 1', '');
insert into post(postid, body, create_date, title, url) VALUES (2, 'Body 2', '2017-02-01 00:00:00', 'Post 2', '');

delete from resourceGroupType;

insert into resourceGroupType(resourceGroupTypeId, caption, groupName) VALUES (1, 'Type 1', 'Type1');
insert into resourceGroupType(resourceGroupTypeId, caption, groupName) VALUES (2, 'Type 2', 'Type2');

delete from resourceGroupTypeChapter;

insert into resourceGroupTypeChapter(chapterId, chapterName, resourceGroupTypeId) VALUES (1, 'Chapter 1', null);
insert into resourceGroupTypeChapter(chapterId, chapterName, resourceGroupTypeId) VALUES (2, 'Chapter 2', null);


insert into user(userId, firstName, lastName, middleName, password, email, avatarURL, profileVK, profileFB, profileGoogle, profileInstagram, sex, cityId, clubId, combatArtId, mentor_userId, roleId, aboutMe, enabled)
        VALUES  (1, 'Ivan', 'Ivanovich', 'Ivanov', 1, 'ivan@gmail.com', 'av1', 'vk1', 'fb1', 'google1', 'instagram1', 'm', 1, 1, 1, null, null, 'Me1', true);
insert into user(userId, firstName, lastName, middleName, password, email, avatarURL, profileVK, profileFB, profileGoogle, profileInstagram, sex, cityId, clubId, combatArtId, mentor_userId, roleId, aboutMe, enabled)
        VALUES  (2, 'Petr', 'Petrovich', 'Petrov', 2, 'petr@gmail.com', 'av2', 'vk2', 'fb2', 'google2', 'instagram2', 'm', 1, 1, 1, null, null, 'Me2', true);

insert into usergroup(id, name) VALUES (1, 'Group1');
insert into usergroup(id, name) VALUES (2, 'Group2');


insert into article(articleId, articleName, context, chapterId, resourceGroupTypeId, roleId) VALUES (1, 'Article 1', 'Context 1', 1, 1, 1);
insert into article(articleId, articleName, context, chapterId, resourceGroupTypeId, roleId) VALUES (2, 'Article 2', 'Context 2', 2, 1, 2);
