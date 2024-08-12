insert into author(id, name, surname, sex, date_of_birth, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq), 'Arthur Conan', 'Doyle', 'MALE', '1859-05-22', 0);
insert into author(id, name, surname, sex, date_of_birth, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'Joseph', 'Heller', 'MALE', '1923-05-01', 0);
insert into author(id, name, surname, sex, date_of_birth, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'George', 'Orwell', 'MALE', '1903-06-25', 0);
insert into author(id, name, surname, sex, date_of_birth, is_alive) values ((VALUES NEXT VALUE FOR public.author_seq),'Paolo', 'Coehlo', 'MALE', '1947-08-24', 1);


insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 1, 'Sherlock Holmes', 140, 'ADVENTURE', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 2, 'Paragraf 22', 480, 'WAR', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 3, '1984', 368, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Alchemik', 210, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 3, 'Folwark zwierzęcy', 80, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Jedenaście minut', 240, 'PSYCHOLOGY', 0);
insert into book(id, author_id, title, pages, category, read_book) values ((VALUES NEXT VALUE FOR public.book_seq), 4, 'Pielgrzym', 345, 'PSYCHOLOGY', 0);

