insert into authors (id, `name`, lastname) values (1, 'TestNameAuthor_1', 'TestLastnameAuthor_1');
insert into authors (id, `name`, lastname) values (2, 'TestNameAuthor_2', 'TestLastnameAuthor_2');

insert into genres (id, `name`) values (1, 'TestGenre_1');
insert into genres (id, `name`) values (2, 'TestGenre_2');
insert into genres (id, `name`) values (3, 'TestGenre_3');

insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (1, 'TestBookName_1', 1, 1);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (2, 'TestBookName_2', 2, 2);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (3, 'TestBookName_3', 2, 1);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (4, 'TestBookName_4', 1, 2);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (5, 'TestBookName_5', 2, 3);

insert into comments (ID_BOOK, `text`, USER_NAME) values (1, 'TestComment1', 'TEstUserComment1');
insert into comments (ID_BOOK, `text`, USER_NAME) values (1, 'TestComment2', 'TEstUserComment2');
insert into comments (ID_BOOK, `text`, USER_NAME) values (3, 'TestComment3', 'TEstUserComment3)');

