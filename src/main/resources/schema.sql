DROP TABLE IF EXISTS AUTHOR CASCADE;
DROP TABLE IF EXISTS BOOK CASCADE;

CREATE TABLE IF NOT EXISTS AUTHOR(
  ID BIGSERIAL PRIMARY KEY NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  SURNAME VARCHAR(255) NOT NULL,
  SEX VARCHAR(10) NOT NULL,
  DATE_OF_BIRTH DATE NOT NULL,
  IS_ALIVE BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS BOOK(
  ID BIGSERIAL PRIMARY KEY NOT NULL,
  AUTHOR_ID INTEGER NOT NULL REFERENCES AUTHOR(ID),
  TITLE VARCHAR(255) NOT NULL,
  PAGES INTEGER,
  CATEGORY VARCHAR(255) NOT NULL,
  READ_BOOK BOOLEAN NOT NULL,
  CREATED_AT DATE
);
