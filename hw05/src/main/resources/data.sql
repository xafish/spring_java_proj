insert into authors (id, `name`, lastname) values (1, 'Stiven', 'Kong');
insert into authors (id, `name`, lastname) values (2, 'Maria', 'Doncsova');
insert into authors (id, `name`, lastname) values (3, 'Grigoriy', 'Wells');

insert into genres (id, `name`) values (1, 'Horror');
insert into genres (id, `name`) values (2, 'Detective');
insert into genres (id, `name`) values (3, 'Fantastic');


insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (1, 'Children of popcorn', 1, 1);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (2, 'Its', 1, 1);

insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (3, 'The missing police donut', 2, 1);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (4, 'Brilliant and something else', 2, 2);
insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (5, 'Assassin butler', 2, 3);

insert into books (id, `name`, ID_AUTHOR, ID_GENRE) values (6, 'Alien in Chinas government', 3, 3);
