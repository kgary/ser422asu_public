drop table title_author;
drop table title;
drop table editor;
drop table author;

CREATE TABLE author (
    author_id integer NOT NULL,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    pen_name VARCHAR(32)
);
CREATE SEQUENCE author_ids
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 0
    CACHE 1;

CREATE TABLE editor (
    editor_id bigint NOT NULL,
    first_name character varying,
    middle_name character varying,
    last_name character varying
);

CREATE TABLE title (
    title_id bigint NOT NULL,
    title_descr character varying NOT NULL,
    title_cost real,
    editor_id bigint
);

CREATE TABLE title_author (
    title_id bigint NOT NULL,
    author_id bigint NOT NULL
);

ALTER TABLE ONLY title
    ADD CONSTRAINT title_pkey PRIMARY KEY (title_id);
ALTER TABLE ONLY editor
    ADD CONSTRAINT editor_pkey PRIMARY KEY (editor_id);
ALTER TABLE ONLY author
    ADD CONSTRAINT author_pkey PRIMARY KEY (author_id);
ALTER TABLE ONLY title
    ADD CONSTRAINT title_editor_fk FOREIGN KEY (editor_id) REFERENCES editor(editor_id);
ALTER TABLE ONLY title_author
    ADD CONSTRAINT title_author_fk FOREIGN KEY (title_id) REFERENCES title(title_id);
ALTER TABLE ONLY title_author
    ADD CONSTRAINT author_title_fk FOREIGN KEY (author_id) REFERENCES author(author_id);

INSERT INTO author VALUES (16, 'Barnard', 'A.M.', 'Louisa May Alcott');
INSERT INTO author VALUES (4156, 'Bachman', 'Richard', 'Stephen King');
INSERT INTO author VALUES (115, 'Pfaal', 'Hans', 'Edgar Allen Poe');
INSERT INTO author VALUES (2112, 'Samuel', 'Clemens', 'Mark Twain');

INSERT INTO editor VALUES (1112662122822, 'Bill', 'E', 'Bobb');
INSERT INTO editor VALUES (1112662122823, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112662122824, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112662122825, 'John', '', 'Kerry');

INSERT INTO title VALUES (1112662122826, 'Advanced Databases', 39.990002, 1112662122823);
INSERT INTO title VALUES (1112662122827, 'Beginning Servlets', 19.950001, 1112662122825);

INSERT INTO title_author VALUES (1112662122826, 4156);
INSERT INTO title_author VALUES (1112662122827, 16);
INSERT INTO title_author VALUES (1112662122827, 115);
