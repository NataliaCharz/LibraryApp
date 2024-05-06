insert into author(id, name, surname, sex, age, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq), 'Arthur', 'Conan Doyle', 'MALE', 71, 0);
insert into author(id, name, surname, sex, age, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'Joseph', 'Heller', 'MALE', 76, 0);
insert into author(id, name, surname, sex, age, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'George', 'Orwell', 'MALE', 47, 0);
insert into author(id, name, surname, sex, age, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'Paolo', 'Coehlo', 'MALE', 76, 1);


insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 1, 'Sherlock Holmes', 140, 'ADVENTURE', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 2, 'Paragraf 22', 480, 'WAR', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 3, '1984', 368, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Alchemik', 210, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 3, 'Folwark zwierzęcy', 80, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Jedenaście minut', 240, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Pielgrzym', 345, 'PSYCHOLOGY', 0);

