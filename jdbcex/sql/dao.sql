--
-- TOC entry 13 (OID 34716)
-- Name: books; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE books (
    id integer NOT NULL,
    title text NOT NULL,
    author_id integer,
    subject_id integer
);


--
-- TOC entry 14 (OID 34724)
-- Name: publishers; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE publishers (
    id integer NOT NULL,
    name text,
    address text
);


--
-- TOC entry 15 (OID 34731)
-- Name: authors; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE authors (
    id integer NOT NULL,
    last_name text,
    first_name text
);


--
-- TOC entry 16 (OID 34738)
-- Name: states; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE states (
    id integer NOT NULL,
    name text,
    abbreviation character(2)
);


--
-- TOC entry 17 (OID 34745)
-- Name: my_list; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE my_list (
    todos text
);


--
-- TOC entry 18 (OID 34750)
-- Name: stock; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE stock (
    isbn text NOT NULL,
    cost numeric(5,2),
    retail numeric(5,2),
    stock integer
);


--
-- TOC entry 5 (OID 34757)
-- Name: subject_ids; Type: SEQUENCE; Schema: public; Owner: kgary
--

CREATE SEQUENCE subject_ids
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 19 (OID 34759)
-- Name: numeric_values; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE numeric_values (
    num numeric(30,6)
);


--
-- TOC entry 20 (OID 34761)
-- Name: daily_inventory; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE daily_inventory (
    isbn text,
    is_stocked boolean
);


--
-- TOC entry 21 (OID 34766)
-- Name: money_example; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE money_example (
    money_cash money,
    numeric_cash numeric(6,2)
);


--
-- TOC entry 22 (OID 34768)
-- Name: shipments; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE shipments (
    id integer DEFAULT nextval('"shipments_ship_id_seq"'::text) NOT NULL,
    customer_id integer,
    isbn text,
    ship_date timestamp with time zone
);


--
-- TOC entry 23 (OID 34774)
-- Name: customers; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE customers (
    id integer NOT NULL,
    last_name text,
    first_name text
);


--
-- TOC entry 7 (OID 34781)
-- Name: book_ids; Type: SEQUENCE; Schema: public; Owner: kgary
--

CREATE SEQUENCE book_ids
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 24 (OID 34783)
-- Name: book_queue; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE book_queue (
    title text NOT NULL,
    author_id integer,
    subject_id integer,
    approved boolean
);


--
-- TOC entry 67 (OID 34788)
-- Name: title(integer); Type: FUNCTION; Schema: public; Owner: kgary
--

CREATE FUNCTION title(integer) RETURNS text
    AS 'SELECT title from books where id = $1'
    LANGUAGE sql;


--
-- TOC entry 25 (OID 34789)
-- Name: stock_backup; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE stock_backup (
    isbn text,
    cost numeric(5,2),
    retail numeric(5,2),
    stock integer
);


--
-- TOC entry 26 (OID 34796)
-- Name: stock_view; Type: VIEW; Schema: public; Owner: kgary
--

CREATE VIEW stock_view AS
    SELECT stock.isbn, stock.retail, stock.stock FROM stock;


--
-- TOC entry 27 (OID 34797)
-- Name: favorite_books; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE favorite_books (
    employee_id integer,
    books text[]
);


--
-- TOC entry 9 (OID 34802)
-- Name: shipments_ship_id_seq; Type: SEQUENCE; Schema: public; Owner: kgary
--

CREATE SEQUENCE shipments_ship_id_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 28 (OID 34804)
-- Name: employees; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE employees (
    id integer NOT NULL,
    last_name text NOT NULL,
    first_name text,
    CONSTRAINT employees_id CHECK ((id > 100))
);


--
-- TOC entry 29 (OID 34812)
-- Name: editions; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE editions (
    isbn text NOT NULL,
    book_id integer,
    edition integer,
    publisher_id integer,
    publication date,
    "type" character(1),
    CONSTRAINT integrity CHECK (((book_id IS NOT NULL) AND (edition IS NOT NULL)))
);


--
-- TOC entry 11 (OID 34820)
-- Name: author_ids; Type: SEQUENCE; Schema: public; Owner: kgary
--

CREATE SEQUENCE author_ids
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 30 (OID 34822)
-- Name: distinguished_authors; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE distinguished_authors (
    award text
)
INHERITS (authors);


--
-- TOC entry 68 (OID 34827)
-- Name: isbn_to_title(text); Type: FUNCTION; Schema: public; Owner: kgary
--

CREATE FUNCTION isbn_to_title(text) RETURNS text
    AS 'SELECT title FROM books
                                 JOIN editions AS e (isbn, id)
                                 USING (id)
                                 WHERE isbn = $1'
    LANGUAGE sql;


--
-- TOC entry 31 (OID 34828)
-- Name: favorite_authors; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE favorite_authors (
    employee_id integer,
    authors_and_titles text[]
);


--
-- TOC entry 32 (OID 34833)
-- Name: text_sorting; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE text_sorting (
    letter character(1)
);


--
-- TOC entry 33 (OID 34835)
-- Name: subjects; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE subjects (
    id integer NOT NULL,
    subject text,
    "location" text
);


--
-- TOC entry 69 (OID 34842)
-- Name: sum(text); Type: AGGREGATE; Schema: public; Owner: kgary
--

CREATE AGGREGATE sum (
    BASETYPE = text,
    SFUNC = textcat,
    STYPE = text,
    INITCOND = ''
);


--
-- TOC entry 34 (OID 34843)
-- Name: alternate_stock; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE alternate_stock (
    isbn text,
    cost numeric(5,2),
    retail numeric(5,2),
    stock integer
);


--
-- TOC entry 35 (OID 34848)
-- Name: book_backup; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE book_backup (
    id integer,
    title text,
    author_id integer,
    subject_id integer
);


--
-- TOC entry 36 (OID 34853)
-- Name: schedules; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE schedules (
    employee_id integer NOT NULL,
    schedule text
);


--
-- TOC entry 37 (OID 34862)
-- Name: recent_shipments; Type: VIEW; Schema: public; Owner: kgary
--

CREATE VIEW recent_shipments AS
    SELECT count(*) AS num_shipped, max(shipments.ship_date) AS max, b.title FROM ((shipments JOIN editions USING (isbn)) NATURAL JOIN books b(book_id)) GROUP BY b.title ORDER BY count(*) DESC;


--
-- TOC entry 38 (OID 524725)
-- Name: editor; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE editor (
    editor_id bigint NOT NULL,
    first_name character varying,
    middle_name character varying,
    last_name character varying
);


--
-- TOC entry 39 (OID 524725)
-- Name: editor; Type: ACL; Schema: public; Owner: kgary
--

REVOKE ALL ON TABLE editor FROM PUBLIC;
GRANT ALL ON TABLE editor TO PUBLIC;


--
-- TOC entry 40 (OID 524732)
-- Name: title; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE title (
    title_id bigint NOT NULL,
    title_descr character varying,
    title_cost real
);


--
-- TOC entry 41 (OID 524732)
-- Name: title; Type: ACL; Schema: public; Owner: kgary
--

REVOKE ALL ON TABLE title FROM PUBLIC;
GRANT ALL ON TABLE title TO PUBLIC;


--
-- TOC entry 42 (OID 525842)
-- Name: title_editor; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE title_editor (
    title_id bigint NOT NULL,
    editor_id bigint NOT NULL
);


--
-- TOC entry 43 (OID 525842)
-- Name: title_editor; Type: ACL; Schema: public; Owner: kgary
--

REVOKE ALL ON TABLE title_editor FROM PUBLIC;
GRANT ALL ON TABLE title_editor TO PUBLIC;


SET SESSION AUTHORIZATION 'demo';

--
-- TOC entry 44 (OID 526720)
-- Name: author; Type: TABLE; Schema: public; Owner: demo
--

CREATE TABLE author (
    author_id bigint NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    pen_name character varying(50)
);


SET SESSION AUTHORIZATION 'kgary';

--
-- TOC entry 45 (OID 538848)
-- Name: atm; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE atm (
    id integer NOT NULL,
    cash integer
);


--
-- TOC entry 46 (OID 538854)
-- Name: account; Type: TABLE; Schema: public; Owner: kgary
--

CREATE TABLE account (
    client character varying(25) NOT NULL,
    money integer
);


--
-- Data for TOC entry 70 (OID 34716)
-- Name: books; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO books VALUES (7808, 'The Shining', 4156, 9);
INSERT INTO books VALUES (4513, 'Dune', 1866, 15);
INSERT INTO books VALUES (4267, '2001: A Space Odyssey', 2001, 15);
INSERT INTO books VALUES (1608, 'The Cat in the Hat', 1809, 2);
INSERT INTO books VALUES (1590, 'Bartholomew and the Oobleck', 1809, 2);
INSERT INTO books VALUES (25908, 'Franklin in the Dark', 15990, 2);
INSERT INTO books VALUES (1501, 'Goodnight Moon', 2031, 2);
INSERT INTO books VALUES (190, 'Little Women', 16, 6);
INSERT INTO books VALUES (1234, 'The Velveteen Rabbit', 25041, 3);
INSERT INTO books VALUES (2038, 'Dynamic Anatomy', 1644, 0);
INSERT INTO books VALUES (156, 'The Tell-Tale Heart', 115, 9);
INSERT INTO books VALUES (41473, 'Programming Python', 7805, 4);
INSERT INTO books VALUES (41477, 'Learning Python', 7805, 4);
INSERT INTO books VALUES (41478, 'Perl Cookbook', 7806, 4);
INSERT INTO books VALUES (41472, 'Practical PostgreSQL', 1212, 4);


--
-- Data for TOC entry 71 (OID 34724)
-- Name: publishers; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO publishers VALUES (150, 'Kids Can Press', 'Kids Can Press, 29 Birch Ave. Toronto, ON  M4V 1E2');
INSERT INTO publishers VALUES (91, 'Henry Holt & Company, Inc.', 'Henry Holt & Company, Inc. 115 West 18th Street New York, NY 10011');
INSERT INTO publishers VALUES (113, 'O''Reilly & Associates', 'O''Reilly & Associates, Inc. 101 Morris St, Sebastopol, CA 95472');
INSERT INTO publishers VALUES (62, 'Watson-Guptill Publications', '1515 Boradway, New York, NY 10036');
INSERT INTO publishers VALUES (105, 'Noonday Press', 'Farrar Straus & Giroux Inc, 19 Union Square W, New York, NY 10003');
INSERT INTO publishers VALUES (99, 'Ace Books', 'The Berkley Publishing Group, Penguin Putnam Inc, 375 Hudson St, New York, NY 10014');
INSERT INTO publishers VALUES (101, 'Roc', 'Penguin Putnam Inc, 375 Hudson St, New York, NY 10014');
INSERT INTO publishers VALUES (163, 'Mojo Press', 'Mojo Press, PO Box 1215, Dripping Springs, TX 78720');
INSERT INTO publishers VALUES (171, 'Books of Wonder', 'Books of Wonder, 16 W. 18th St. New York, NY, 10011');
INSERT INTO publishers VALUES (102, 'Penguin', 'Penguin Putnam Inc, 375 Hudson St, New York, NY 10014');
INSERT INTO publishers VALUES (75, 'Doubleday', 'Random House, Inc, 1540 Broadway, New York, NY 10036');
INSERT INTO publishers VALUES (65, 'HarperCollins', 'HarperCollins Publishers, 10 E 53rd St, New York, NY 10022');
INSERT INTO publishers VALUES (59, 'Random House', 'Random House, Inc, 1540 Broadway, New York, NY 10036');


--
-- Data for TOC entry 72 (OID 34731)
-- Name: authors; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO authors VALUES (1111, 'Denham', 'Ariel');
INSERT INTO authors VALUES (1212, 'Worsley', 'John');
INSERT INTO authors VALUES (15990, 'Bourgeois', 'Paulette');
INSERT INTO authors VALUES (25041, 'Bianco', 'Margery Williams');
INSERT INTO authors VALUES (16, 'Alcott', 'Louisa May');
INSERT INTO authors VALUES (4156, 'King', 'Stephen');
INSERT INTO authors VALUES (1866, 'Herbert', 'Frank');
INSERT INTO authors VALUES (1644, 'Hogarth', 'Burne');
INSERT INTO authors VALUES (2031, 'Brown', 'Margaret Wise');
INSERT INTO authors VALUES (115, 'Poe', 'Edgar Allen');
INSERT INTO authors VALUES (7805, 'Lutz', 'Mark');
INSERT INTO authors VALUES (7806, 'Christiansen', 'Tom');
INSERT INTO authors VALUES (1533, 'Brautigan', 'Richard');
INSERT INTO authors VALUES (1717, 'Brite', 'Poppy Z.');
INSERT INTO authors VALUES (2112, 'Gorey', 'Edward');
INSERT INTO authors VALUES (2001, 'Clarke', 'Arthur C.');
INSERT INTO authors VALUES (1213, 'Brookins', 'Andrew');


--
-- Data for TOC entry 73 (OID 34738)
-- Name: states; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO states VALUES (42, 'Washington', 'WA');
INSERT INTO states VALUES (51, 'Oregon', 'OR');


--
-- Data for TOC entry 74 (OID 34745)
-- Name: my_list; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO my_list VALUES ('Pick up laundry.');
INSERT INTO my_list VALUES ('Send out bills.');
INSERT INTO my_list VALUES ('Wrap up Grand Unifying Theory for publication.');
INSERT INTO my_list VALUES ('Pick up laundry.');
INSERT INTO my_list VALUES ('Send out bills.');
INSERT INTO my_list VALUES ('Wrap up Grand Unifying Theory for publication.');


--
-- Data for TOC entry 75 (OID 34750)
-- Name: stock; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO stock VALUES ('0385121679', 29.00, 36.95, 65);
INSERT INTO stock VALUES ('039480001X', 30.00, 32.95, 31);
INSERT INTO stock VALUES ('0394900014', 23.00, 23.95, 0);
INSERT INTO stock VALUES ('044100590X', 36.00, 45.95, 89);
INSERT INTO stock VALUES ('0441172717', 17.00, 21.95, 77);
INSERT INTO stock VALUES ('0451160916', 24.00, 28.95, 22);
INSERT INTO stock VALUES ('0451198492', 36.00, 46.95, 0);
INSERT INTO stock VALUES ('0451457994', 17.00, 22.95, 0);
INSERT INTO stock VALUES ('0590445065', 23.00, 23.95, 10);
INSERT INTO stock VALUES ('0679803335', 20.00, 24.95, 18);
INSERT INTO stock VALUES ('0694003611', 25.00, 28.95, 50);
INSERT INTO stock VALUES ('0760720002', 18.00, 23.95, 28);
INSERT INTO stock VALUES ('0823015505', 26.00, 28.95, 16);
INSERT INTO stock VALUES ('0929605942', 19.00, 21.95, 25);
INSERT INTO stock VALUES ('1885418035', 23.00, 24.95, 77);
INSERT INTO stock VALUES ('0394800753', 16.00, 16.95, 4);


--
-- Data for TOC entry 76 (OID 34759)
-- Name: numeric_values; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO numeric_values VALUES (68719476736.000000);
INSERT INTO numeric_values VALUES (68719476737.000000);
INSERT INTO numeric_values VALUES (6871947673778.000000);
INSERT INTO numeric_values VALUES (999999999999999999999999.999900);
INSERT INTO numeric_values VALUES (999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (-999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (-100000000000000000000000.999999);
INSERT INTO numeric_values VALUES (1.999999);
INSERT INTO numeric_values VALUES (2.000000);
INSERT INTO numeric_values VALUES (2.000000);
INSERT INTO numeric_values VALUES (999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (999999999999999999999999.000000);
INSERT INTO numeric_values VALUES (68719476736.000000);
INSERT INTO numeric_values VALUES (68719476737.000000);
INSERT INTO numeric_values VALUES (6871947673778.000000);
INSERT INTO numeric_values VALUES (999999999999999999999999.999900);
INSERT INTO numeric_values VALUES (999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (-999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (-100000000000000000000000.999999);
INSERT INTO numeric_values VALUES (1.999999);
INSERT INTO numeric_values VALUES (2.000000);
INSERT INTO numeric_values VALUES (2.000000);
INSERT INTO numeric_values VALUES (999999999999999999999999.999999);
INSERT INTO numeric_values VALUES (999999999999999999999999.000000);


--
-- Data for TOC entry 77 (OID 34761)
-- Name: daily_inventory; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO daily_inventory VALUES ('039480001X', true);
INSERT INTO daily_inventory VALUES ('044100590X', true);
INSERT INTO daily_inventory VALUES ('0451198492', false);
INSERT INTO daily_inventory VALUES ('0394900014', false);
INSERT INTO daily_inventory VALUES ('0441172717', true);
INSERT INTO daily_inventory VALUES ('0451160916', false);
INSERT INTO daily_inventory VALUES ('0385121679', NULL);
INSERT INTO daily_inventory VALUES ('039480001X', true);
INSERT INTO daily_inventory VALUES ('044100590X', true);
INSERT INTO daily_inventory VALUES ('0451198492', false);
INSERT INTO daily_inventory VALUES ('0394900014', false);
INSERT INTO daily_inventory VALUES ('0441172717', true);
INSERT INTO daily_inventory VALUES ('0451160916', false);
INSERT INTO daily_inventory VALUES ('0385121679', NULL);


--
-- Data for TOC entry 78 (OID 34766)
-- Name: money_example; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO money_example VALUES ('$12.24', 12.24);
INSERT INTO money_example VALUES ('$12.24', 12.24);


--
-- Data for TOC entry 79 (OID 34768)
-- Name: shipments; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO shipments VALUES (375, 142, '039480001X', '2001-08-06 09:29:21-07');
INSERT INTO shipments VALUES (323, 671, '0451160916', '2001-08-14 10:36:41-07');
INSERT INTO shipments VALUES (998, 1045, '0590445065', '2001-08-12 12:09:47-07');
INSERT INTO shipments VALUES (749, 172, '0694003611', '2001-08-11 10:52:34-07');
INSERT INTO shipments VALUES (662, 655, '0679803335', '2001-08-09 07:30:07-07');
INSERT INTO shipments VALUES (806, 1125, '0760720002', '2001-08-05 09:34:04-07');
INSERT INTO shipments VALUES (102, 146, '0394900014', '2001-08-11 13:34:08-07');
INSERT INTO shipments VALUES (813, 112, '0385121679', '2001-08-08 09:53:46-07');
INSERT INTO shipments VALUES (652, 724, '1885418035', '2001-08-14 13:41:39-07');
INSERT INTO shipments VALUES (599, 430, '0929605942', '2001-08-10 08:29:42-07');
INSERT INTO shipments VALUES (969, 488, '0441172717', '2001-08-14 08:42:58-07');
INSERT INTO shipments VALUES (433, 898, '044100590X', '2001-08-12 08:46:35-07');
INSERT INTO shipments VALUES (660, 409, '0451457994', '2001-08-07 11:56:42-07');
INSERT INTO shipments VALUES (310, 738, '0451198492', '2001-08-15 14:02:01-07');
INSERT INTO shipments VALUES (510, 860, '0823015505', '2001-08-14 07:33:47-07');
INSERT INTO shipments VALUES (997, 185, '039480001X', '2001-08-10 13:47:52-07');
INSERT INTO shipments VALUES (999, 221, '0451160916', '2001-08-14 13:45:51-07');
INSERT INTO shipments VALUES (56, 880, '0590445065', '2001-08-14 13:49:00-07');
INSERT INTO shipments VALUES (72, 574, '0694003611', '2001-08-06 07:49:44-07');
INSERT INTO shipments VALUES (146, 270, '039480001X', '2001-08-13 09:42:10-07');
INSERT INTO shipments VALUES (981, 652, '0451160916', '2001-08-08 08:36:44-07');
INSERT INTO shipments VALUES (95, 480, '0590445065', '2001-08-10 07:29:52-07');
INSERT INTO shipments VALUES (593, 476, '0694003611', '2001-08-15 11:57:40-07');
INSERT INTO shipments VALUES (977, 853, '0679803335', '2001-08-09 09:30:46-07');
INSERT INTO shipments VALUES (117, 185, '0760720002', '2001-08-07 13:00:48-07');
INSERT INTO shipments VALUES (406, 1123, '0394900014', '2001-08-13 09:47:04-07');
INSERT INTO shipments VALUES (340, 1149, '0385121679', '2001-08-12 13:39:22-07');
INSERT INTO shipments VALUES (871, 388, '1885418035', '2001-08-07 11:31:57-07');
INSERT INTO shipments VALUES (1000, 221, '039480001X', '2001-09-14 16:46:32-07');
INSERT INTO shipments VALUES (1001, 107, '039480001X', '2001-09-14 17:42:22-07');
INSERT INTO shipments VALUES (754, 107, '0394800753', '2001-08-11 09:55:05-07');
INSERT INTO shipments VALUES (458, 107, '0394800753', '2001-08-07 10:58:36-07');
INSERT INTO shipments VALUES (189, 107, '0394800753', '2001-08-06 11:46:36-07');
INSERT INTO shipments VALUES (720, 107, '0394800753', '2001-08-08 10:46:13-07');
INSERT INTO shipments VALUES (1002, 107, '0394800753', '2001-09-22 11:23:28-07');
INSERT INTO shipments VALUES (2, 107, '0394800753', '2001-09-22 20:58:56-07');


--
-- Data for TOC entry 80 (OID 34774)
-- Name: customers; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO customers VALUES (107, 'Jackson', 'Annie');
INSERT INTO customers VALUES (112, 'Gould', 'Ed');
INSERT INTO customers VALUES (142, 'Allen', 'Chad');
INSERT INTO customers VALUES (146, 'Williams', 'James');
INSERT INTO customers VALUES (172, 'Brown', 'Richard');
INSERT INTO customers VALUES (185, 'Morrill', 'Eric');
INSERT INTO customers VALUES (221, 'King', 'Jenny');
INSERT INTO customers VALUES (270, 'Bollman', 'Julie');
INSERT INTO customers VALUES (388, 'Morrill', 'Royce');
INSERT INTO customers VALUES (409, 'Holloway', 'Christine');
INSERT INTO customers VALUES (430, 'Black', 'Jean');
INSERT INTO customers VALUES (476, 'Clark', 'James');
INSERT INTO customers VALUES (480, 'Thomas', 'Rich');
INSERT INTO customers VALUES (488, 'Young', 'Trevor');
INSERT INTO customers VALUES (574, 'Bennett', 'Laura');
INSERT INTO customers VALUES (652, 'Anderson', 'Jonathan');
INSERT INTO customers VALUES (655, 'Olson', 'Dave');
INSERT INTO customers VALUES (671, 'Brown', 'Chuck');
INSERT INTO customers VALUES (723, 'Eisele', 'Don');
INSERT INTO customers VALUES (724, 'Holloway', 'Adam');
INSERT INTO customers VALUES (738, 'Gould', 'Shirley');
INSERT INTO customers VALUES (830, 'Robertson', 'Royce');
INSERT INTO customers VALUES (853, 'Black', 'Wendy');
INSERT INTO customers VALUES (860, 'Owens', 'Tim');
INSERT INTO customers VALUES (880, 'Robinson', 'Tammy');
INSERT INTO customers VALUES (898, 'Gerdes', 'Kate');
INSERT INTO customers VALUES (964, 'Gould', 'Ramon');
INSERT INTO customers VALUES (1045, 'Owens', 'Jean');
INSERT INTO customers VALUES (1125, 'Bollman', 'Owen');
INSERT INTO customers VALUES (1149, 'Becker', 'Owen');
INSERT INTO customers VALUES (1123, 'Corner', 'Kathy');


--
-- Data for TOC entry 81 (OID 34783)
-- Name: book_queue; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO book_queue VALUES ('Learning Python', 7805, 4, true);
INSERT INTO book_queue VALUES ('Perl Cookbook', 7806, 4, true);
INSERT INTO book_queue VALUES ('Learning Python', 7805, 4, true);
INSERT INTO book_queue VALUES ('Perl Cookbook', 7806, 4, true);


--
-- Data for TOC entry 82 (OID 34789)
-- Name: stock_backup; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO stock_backup VALUES ('0385121679', 29.00, 36.95, 65);
INSERT INTO stock_backup VALUES ('039480001X', 30.00, 32.95, 31);
INSERT INTO stock_backup VALUES ('0394800753', 16.00, 16.95, 0);
INSERT INTO stock_backup VALUES ('0394900014', 23.00, 23.95, 0);
INSERT INTO stock_backup VALUES ('044100590X', 36.00, 45.95, 89);
INSERT INTO stock_backup VALUES ('0441172717', 17.00, 21.95, 77);
INSERT INTO stock_backup VALUES ('0451160916', 24.00, 28.95, 22);
INSERT INTO stock_backup VALUES ('0451198492', 36.00, 46.95, 0);
INSERT INTO stock_backup VALUES ('0451457994', 17.00, 22.95, 0);
INSERT INTO stock_backup VALUES ('0590445065', 23.00, 23.95, 10);
INSERT INTO stock_backup VALUES ('0679803335', 20.00, 24.95, 18);
INSERT INTO stock_backup VALUES ('0694003611', 25.00, 28.95, 50);
INSERT INTO stock_backup VALUES ('0760720002', 18.00, 23.95, 28);
INSERT INTO stock_backup VALUES ('0823015505', 26.00, 28.95, 16);
INSERT INTO stock_backup VALUES ('0929605942', 19.00, 21.95, 25);
INSERT INTO stock_backup VALUES ('1885418035', 23.00, 24.95, 77);
INSERT INTO stock_backup VALUES ('0385121679', 29.00, 36.95, 65);
INSERT INTO stock_backup VALUES ('039480001X', 30.00, 32.95, 31);
INSERT INTO stock_backup VALUES ('0394800753', 16.00, 16.95, 0);
INSERT INTO stock_backup VALUES ('0394900014', 23.00, 23.95, 0);
INSERT INTO stock_backup VALUES ('044100590X', 36.00, 45.95, 89);
INSERT INTO stock_backup VALUES ('0441172717', 17.00, 21.95, 77);
INSERT INTO stock_backup VALUES ('0451160916', 24.00, 28.95, 22);
INSERT INTO stock_backup VALUES ('0451198492', 36.00, 46.95, 0);
INSERT INTO stock_backup VALUES ('0451457994', 17.00, 22.95, 0);
INSERT INTO stock_backup VALUES ('0590445065', 23.00, 23.95, 10);
INSERT INTO stock_backup VALUES ('0679803335', 20.00, 24.95, 18);
INSERT INTO stock_backup VALUES ('0694003611', 25.00, 28.95, 50);
INSERT INTO stock_backup VALUES ('0760720002', 18.00, 23.95, 28);
INSERT INTO stock_backup VALUES ('0823015505', 26.00, 28.95, 16);
INSERT INTO stock_backup VALUES ('0929605942', 19.00, 21.95, 25);
INSERT INTO stock_backup VALUES ('1885418035', 23.00, 24.95, 77);


--
-- Data for TOC entry 83 (OID 34797)
-- Name: favorite_books; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO favorite_books VALUES (102, '{"The Hitchhiker''s Guide to the Galaxy","The Restauraunt at the End of the Universe"}');
INSERT INTO favorite_books VALUES (103, '{"There and Back Again: A Hobbit''s Holiday","Kittens Squared"}');
INSERT INTO favorite_books VALUES (102, '{"The Hitchhiker''s Guide to the Galaxy","The Restauraunt at the End of the Universe"}');
INSERT INTO favorite_books VALUES (103, '{"There and Back Again: A Hobbit''s Holiday","Kittens Squared"}');


--
-- Data for TOC entry 84 (OID 34804)
-- Name: employees; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO employees VALUES (101, 'Appel', 'Vincent');
INSERT INTO employees VALUES (102, 'Holloway', 'Michael');
INSERT INTO employees VALUES (105, 'Connoly', 'Sarah');
INSERT INTO employees VALUES (104, 'Noble', 'Ben');
INSERT INTO employees VALUES (103, 'Joble', 'David');
INSERT INTO employees VALUES (106, 'Hall', 'Timothy');
INSERT INTO employees VALUES (1008, 'Williams', NULL);


--
-- Data for TOC entry 85 (OID 34812)
-- Name: editions; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO editions VALUES ('039480001X', 1608, 1, 59, '1957-03-01', 'h');
INSERT INTO editions VALUES ('0451160916', 7808, 1, 75, '1981-08-01', 'p');
INSERT INTO editions VALUES ('0394800753', 1590, 1, 59, '1949-03-01', 'p');
INSERT INTO editions VALUES ('0590445065', 25908, 1, 150, '1987-03-01', 'p');
INSERT INTO editions VALUES ('0694003611', 1501, 1, 65, '1947-03-04', 'p');
INSERT INTO editions VALUES ('0679803335', 1234, 1, 102, '1922-01-01', 'p');
INSERT INTO editions VALUES ('0760720002', 190, 1, 91, '1868-01-01', 'p');
INSERT INTO editions VALUES ('0394900014', 1608, 1, 59, '1957-01-01', 'p');
INSERT INTO editions VALUES ('0385121679', 7808, 2, 75, '1993-10-01', 'h');
INSERT INTO editions VALUES ('1885418035', 156, 1, 163, '1995-03-28', 'p');
INSERT INTO editions VALUES ('0929605942', 156, 2, 171, '1998-12-01', 'p');
INSERT INTO editions VALUES ('0441172717', 4513, 2, 99, '1998-09-01', 'p');
INSERT INTO editions VALUES ('044100590X', 4513, 3, 99, '1999-10-01', 'h');
INSERT INTO editions VALUES ('0451457994', 4267, 3, 101, '2000-09-12', 'p');
INSERT INTO editions VALUES ('0451198492', 4267, 3, 101, '1999-10-01', 'h');
INSERT INTO editions VALUES ('0823015505', 2038, 1, 62, '1958-01-01', 'p');
INSERT INTO editions VALUES ('0596000855', 41473, 2, 113, '2001-03-01', 'p');


--
-- Data for TOC entry 86 (OID 34822)
-- Name: distinguished_authors; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO distinguished_authors VALUES (25043, 'Simon', 'Neil', 'Pulitzer Prize');
INSERT INTO distinguished_authors VALUES (1809, 'Geisel', 'Theodor Seuss', 'Pulitzer Prize');
INSERT INTO distinguished_authors VALUES (25043, 'Simon', 'Neil', 'Pulitzer Prize');
INSERT INTO distinguished_authors VALUES (1809, 'Geisel', 'Theodor Seuss', 'Pulitzer Prize');


--
-- Data for TOC entry 87 (OID 34828)
-- Name: favorite_authors; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO favorite_authors VALUES (102, '{{"J.R.R. Tolkien","The Silmarillion"},{"Charles Dickens","Great Expectations"},{"Ariel Denham","Attic Lives"}}');
INSERT INTO favorite_authors VALUES (102, '{{"J.R.R. Tolkien","The Silmarillion"},{"Charles Dickens","Great Expectations"},{"Ariel Denham","Attic Lives"}}');


--
-- Data for TOC entry 88 (OID 34833)
-- Name: text_sorting; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO text_sorting VALUES ('0');
INSERT INTO text_sorting VALUES ('1');
INSERT INTO text_sorting VALUES ('2');
INSERT INTO text_sorting VALUES ('3');
INSERT INTO text_sorting VALUES ('A');
INSERT INTO text_sorting VALUES ('B');
INSERT INTO text_sorting VALUES ('C');
INSERT INTO text_sorting VALUES ('D');
INSERT INTO text_sorting VALUES ('a');
INSERT INTO text_sorting VALUES ('b');
INSERT INTO text_sorting VALUES ('c');
INSERT INTO text_sorting VALUES ('d');
INSERT INTO text_sorting VALUES ('0');
INSERT INTO text_sorting VALUES ('1');
INSERT INTO text_sorting VALUES ('2');
INSERT INTO text_sorting VALUES ('3');
INSERT INTO text_sorting VALUES ('A');
INSERT INTO text_sorting VALUES ('B');
INSERT INTO text_sorting VALUES ('C');
INSERT INTO text_sorting VALUES ('D');
INSERT INTO text_sorting VALUES ('a');
INSERT INTO text_sorting VALUES ('b');
INSERT INTO text_sorting VALUES ('c');
INSERT INTO text_sorting VALUES ('d');


--
-- Data for TOC entry 89 (OID 34835)
-- Name: subjects; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO subjects VALUES (0, 'Arts', 'Creativity St');
INSERT INTO subjects VALUES (1, 'Business', 'Productivity Ave');
INSERT INTO subjects VALUES (2, 'Children''s Books', 'Kids Ct');
INSERT INTO subjects VALUES (3, 'Classics', 'Academic Rd');
INSERT INTO subjects VALUES (4, 'Computers', 'Productivity Ave');
INSERT INTO subjects VALUES (5, 'Cooking', 'Creativity St');
INSERT INTO subjects VALUES (6, 'Drama', 'Main St');
INSERT INTO subjects VALUES (7, 'Entertainment', 'Main St');
INSERT INTO subjects VALUES (8, 'History', 'Academic Rd');
INSERT INTO subjects VALUES (9, 'Horror', 'Black Raven Dr');
INSERT INTO subjects VALUES (10, 'Mystery', 'Black Raven Dr');
INSERT INTO subjects VALUES (11, 'Poetry', 'Sunset Dr');
INSERT INTO subjects VALUES (12, 'Religion', NULL);
INSERT INTO subjects VALUES (13, 'Romance', 'Main St');
INSERT INTO subjects VALUES (14, 'Science', 'Productivity Ave');
INSERT INTO subjects VALUES (15, 'Science Fiction', 'Main St');


--
-- Data for TOC entry 90 (OID 34843)
-- Name: alternate_stock; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO alternate_stock VALUES ('0385121679', 29.00, 36.95, 65);
INSERT INTO alternate_stock VALUES ('039480001X', 30.00, 32.95, 31);
INSERT INTO alternate_stock VALUES ('0394900014', 23.00, 23.95, 0);
INSERT INTO alternate_stock VALUES ('044100590X', 36.00, 45.95, 89);
INSERT INTO alternate_stock VALUES ('0441172717', 17.00, 21.95, 77);
INSERT INTO alternate_stock VALUES ('0451160916', 24.00, 28.95, 22);
INSERT INTO alternate_stock VALUES ('0451198492', 36.00, 46.95, 0);
INSERT INTO alternate_stock VALUES ('0451457994', 17.00, 22.95, 0);
INSERT INTO alternate_stock VALUES ('0590445065', 23.00, 23.95, 10);
INSERT INTO alternate_stock VALUES ('0679803335', 20.00, 24.95, 18);
INSERT INTO alternate_stock VALUES ('0694003611', 25.00, 28.95, 50);
INSERT INTO alternate_stock VALUES ('0760720002', 18.00, 23.95, 28);
INSERT INTO alternate_stock VALUES ('0823015505', 26.00, 28.95, 16);
INSERT INTO alternate_stock VALUES ('0929605942', 19.00, 21.95, 25);
INSERT INTO alternate_stock VALUES ('1885418035', 23.00, 24.95, 77);
INSERT INTO alternate_stock VALUES ('0394800753', 16.00, 16.95, 4);
INSERT INTO alternate_stock VALUES ('0385121679', 29.00, 36.95, 65);
INSERT INTO alternate_stock VALUES ('039480001X', 30.00, 32.95, 31);
INSERT INTO alternate_stock VALUES ('0394900014', 23.00, 23.95, 0);
INSERT INTO alternate_stock VALUES ('044100590X', 36.00, 45.95, 89);
INSERT INTO alternate_stock VALUES ('0441172717', 17.00, 21.95, 77);
INSERT INTO alternate_stock VALUES ('0451160916', 24.00, 28.95, 22);
INSERT INTO alternate_stock VALUES ('0451198492', 36.00, 46.95, 0);
INSERT INTO alternate_stock VALUES ('0451457994', 17.00, 22.95, 0);
INSERT INTO alternate_stock VALUES ('0590445065', 23.00, 23.95, 10);
INSERT INTO alternate_stock VALUES ('0679803335', 20.00, 24.95, 18);
INSERT INTO alternate_stock VALUES ('0694003611', 25.00, 28.95, 50);
INSERT INTO alternate_stock VALUES ('0760720002', 18.00, 23.95, 28);
INSERT INTO alternate_stock VALUES ('0823015505', 26.00, 28.95, 16);
INSERT INTO alternate_stock VALUES ('0929605942', 19.00, 21.95, 25);
INSERT INTO alternate_stock VALUES ('1885418035', 23.00, 24.95, 77);
INSERT INTO alternate_stock VALUES ('0394800753', 16.00, 16.95, 4);


--
-- Data for TOC entry 91 (OID 34848)
-- Name: book_backup; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO book_backup VALUES (7808, 'The Shining', 4156, 9);
INSERT INTO book_backup VALUES (4513, 'Dune', 1866, 15);
INSERT INTO book_backup VALUES (4267, '2001: A Space Odyssey', 2001, 15);
INSERT INTO book_backup VALUES (1608, 'The Cat in the Hat', 1809, 2);
INSERT INTO book_backup VALUES (1590, 'Bartholomew and the Oobleck', 1809, 2);
INSERT INTO book_backup VALUES (25908, 'Franklin in the Dark', 15990, 2);
INSERT INTO book_backup VALUES (1501, 'Goodnight Moon', 2031, 2);
INSERT INTO book_backup VALUES (190, 'Little Women', 16, 6);
INSERT INTO book_backup VALUES (1234, 'The Velveteen Rabbit', 25041, 3);
INSERT INTO book_backup VALUES (2038, 'Dynamic Anatomy', 1644, 0);
INSERT INTO book_backup VALUES (156, 'The Tell-Tale Heart', 115, 9);
INSERT INTO book_backup VALUES (41472, 'Practical PostgreSQL', 1212, 4);
INSERT INTO book_backup VALUES (41473, 'Programming Python', 7805, 4);
INSERT INTO book_backup VALUES (41477, 'Learning Python', 7805, 4);
INSERT INTO book_backup VALUES (41478, 'Perl Cookbook', 7806, 4);
INSERT INTO book_backup VALUES (7808, 'The Shining', 4156, 9);
INSERT INTO book_backup VALUES (4513, 'Dune', 1866, 15);
INSERT INTO book_backup VALUES (4267, '2001: A Space Odyssey', 2001, 15);
INSERT INTO book_backup VALUES (1608, 'The Cat in the Hat', 1809, 2);
INSERT INTO book_backup VALUES (1590, 'Bartholomew and the Oobleck', 1809, 2);
INSERT INTO book_backup VALUES (25908, 'Franklin in the Dark', 15990, 2);
INSERT INTO book_backup VALUES (1501, 'Goodnight Moon', 2031, 2);
INSERT INTO book_backup VALUES (190, 'Little Women', 16, 6);
INSERT INTO book_backup VALUES (1234, 'The Velveteen Rabbit', 25041, 3);
INSERT INTO book_backup VALUES (2038, 'Dynamic Anatomy', 1644, 0);
INSERT INTO book_backup VALUES (156, 'The Tell-Tale Heart', 115, 9);
INSERT INTO book_backup VALUES (41473, 'Programming Python', 7805, 4);
INSERT INTO book_backup VALUES (41477, 'Learning Python', 7805, 4);
INSERT INTO book_backup VALUES (41478, 'Perl Cookbook', 7806, 4);
INSERT INTO book_backup VALUES (41472, 'Practical PostgreSQL', 1212, 4);
INSERT INTO book_backup VALUES (7808, 'The Shining', 4156, 9);
INSERT INTO book_backup VALUES (4513, 'Dune', 1866, 15);
INSERT INTO book_backup VALUES (4267, '2001: A Space Odyssey', 2001, 15);
INSERT INTO book_backup VALUES (1608, 'The Cat in the Hat', 1809, 2);
INSERT INTO book_backup VALUES (1590, 'Bartholomew and the Oobleck', 1809, 2);
INSERT INTO book_backup VALUES (25908, 'Franklin in the Dark', 15990, 2);
INSERT INTO book_backup VALUES (1501, 'Goodnight Moon', 2031, 2);
INSERT INTO book_backup VALUES (190, 'Little Women', 16, 6);
INSERT INTO book_backup VALUES (1234, 'The Velveteen Rabbit', 25041, 3);
INSERT INTO book_backup VALUES (2038, 'Dynamic Anatomy', 1644, 0);
INSERT INTO book_backup VALUES (156, 'The Tell-Tale Heart', 115, 9);
INSERT INTO book_backup VALUES (41472, 'Practical PostgreSQL', 1212, 4);
INSERT INTO book_backup VALUES (41473, 'Programming Python', 7805, 4);
INSERT INTO book_backup VALUES (41477, 'Learning Python', 7805, 4);
INSERT INTO book_backup VALUES (41478, 'Perl Cookbook', 7806, 4);
INSERT INTO book_backup VALUES (7808, 'The Shining', 4156, 9);
INSERT INTO book_backup VALUES (4513, 'Dune', 1866, 15);
INSERT INTO book_backup VALUES (4267, '2001: A Space Odyssey', 2001, 15);
INSERT INTO book_backup VALUES (1608, 'The Cat in the Hat', 1809, 2);
INSERT INTO book_backup VALUES (1590, 'Bartholomew and the Oobleck', 1809, 2);
INSERT INTO book_backup VALUES (25908, 'Franklin in the Dark', 15990, 2);
INSERT INTO book_backup VALUES (1501, 'Goodnight Moon', 2031, 2);
INSERT INTO book_backup VALUES (190, 'Little Women', 16, 6);
INSERT INTO book_backup VALUES (1234, 'The Velveteen Rabbit', 25041, 3);
INSERT INTO book_backup VALUES (2038, 'Dynamic Anatomy', 1644, 0);
INSERT INTO book_backup VALUES (156, 'The Tell-Tale Heart', 115, 9);
INSERT INTO book_backup VALUES (41473, 'Programming Python', 7805, 4);
INSERT INTO book_backup VALUES (41477, 'Learning Python', 7805, 4);
INSERT INTO book_backup VALUES (41478, 'Perl Cookbook', 7806, 4);
INSERT INTO book_backup VALUES (41472, 'Practical PostgreSQL', 1212, 4);


--
-- Data for TOC entry 92 (OID 34853)
-- Name: schedules; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO schedules VALUES (102, 'Mon - Fri, 9am - 5pm');


--
-- Data for TOC entry 93 (OID 524725)
-- Name: editor; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO editor VALUES (1112662122822, 'Bill', 'E', 'Bobb');
INSERT INTO editor VALUES (1112662122823, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112662122824, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112662122825, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112662351416, 'Bill', 'E', 'Bobb');
INSERT INTO editor VALUES (1112662351417, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112662351418, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112662351419, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112662415510, 'Bill', 'E', 'Bobb');
INSERT INTO editor VALUES (1112662415511, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112662415512, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112662415513, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112666611683, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112666611684, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112666611685, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112666611682, 'Bill', 'E', 'Barry');
INSERT INTO editor VALUES (1112666648338, 'Bill', 'E', 'Bobb');
INSERT INTO editor VALUES (1112666648339, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112666648340, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112666648341, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112667947511, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112667947512, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112667947513, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112667947510, 'Bill', 'E', 'Barry');
INSERT INTO editor VALUES (1112725727666, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112725727667, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112725727668, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112725727665, 'Bill', 'E', 'Barry');
INSERT INTO editor VALUES (1112725794369, 'Susie', 'J', 'Queue');
INSERT INTO editor VALUES (1112725794370, 'George', 'W', 'Bush');
INSERT INTO editor VALUES (1112725794371, 'John', '', 'Kerry');
INSERT INTO editor VALUES (1112725794368, 'Bill', 'E', 'Barry');


--
-- Data for TOC entry 94 (OID 524732)
-- Name: title; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO title VALUES (1112662122826, 'Advanced Databases', 39.990002);
INSERT INTO title VALUES (1112662122827, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112662351420, 'Advanced Databases', 39.990002);
INSERT INTO title VALUES (1112662351421, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112662415514, 'Advanced Databases', 39.990002);
INSERT INTO title VALUES (1112662415515, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112666611687, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112666611686, 'Advanced Databases', 49.990002);
INSERT INTO title VALUES (1112666648342, 'Advanced Databases', 39.990002);
INSERT INTO title VALUES (1112666648343, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112667947515, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112667947514, 'Advanced Databases', 49.990002);
INSERT INTO title VALUES (1112725727670, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112725727669, 'Advanced Databases', 49.990002);
INSERT INTO title VALUES (1112725794373, 'Beginning Servlets', 19.950001);
INSERT INTO title VALUES (1112725794372, 'Advanced Databases', 49.990002);


--
-- Data for TOC entry 95 (OID 525842)
-- Name: title_editor; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO title_editor VALUES (1112662122826, 1112662122823);
INSERT INTO title_editor VALUES (1112662122826, 1112662122822);
INSERT INTO title_editor VALUES (1112662122827, 1112662122824);
INSERT INTO title_editor VALUES (1112662122827, 1112662122825);
INSERT INTO title_editor VALUES (1112662351420, 1112662351416);
INSERT INTO title_editor VALUES (1112662351420, 1112662351417);
INSERT INTO title_editor VALUES (1112662351421, 1112662351418);
INSERT INTO title_editor VALUES (1112662351421, 1112662351419);
INSERT INTO title_editor VALUES (1112662415514, 1112662415511);
INSERT INTO title_editor VALUES (1112662415514, 1112662415510);
INSERT INTO title_editor VALUES (1112662415515, 1112662415513);
INSERT INTO title_editor VALUES (1112662415515, 1112662415512);
INSERT INTO title_editor VALUES (1112666611686, 1112666611682);
INSERT INTO title_editor VALUES (1112666611686, 1112666611683);
INSERT INTO title_editor VALUES (1112666611687, 1112666611685);
INSERT INTO title_editor VALUES (1112666611687, 1112666611684);
INSERT INTO title_editor VALUES (1112666648342, 1112666648338);
INSERT INTO title_editor VALUES (1112666648342, 1112666648339);
INSERT INTO title_editor VALUES (1112666648343, 1112666648341);
INSERT INTO title_editor VALUES (1112666648343, 1112666648340);
INSERT INTO title_editor VALUES (1112667947514, 1112667947511);
INSERT INTO title_editor VALUES (1112667947514, 1112667947510);
INSERT INTO title_editor VALUES (1112667947515, 1112667947512);
INSERT INTO title_editor VALUES (1112667947515, 1112667947513);


SET SESSION AUTHORIZATION 'demo';

--
-- Data for TOC entry 96 (OID 526720)
-- Name: author; Type: TABLE DATA; Schema: public; Owner: demo
--



SET SESSION AUTHORIZATION 'kgary';

--
-- Data for TOC entry 97 (OID 538848)
-- Name: atm; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO atm VALUES (1, 500);


--
-- Data for TOC entry 98 (OID 538854)
-- Name: account; Type: TABLE DATA; Schema: public; Owner: kgary
--

INSERT INTO account VALUES ('john_doe', 100);
INSERT INTO account VALUES ('jane_doe', 600);


--
-- TOC entry 50 (OID 35138)
-- Name: unique_publisher_idx; Type: INDEX; Schema: public; Owner: kgary
--

CREATE UNIQUE INDEX unique_publisher_idx ON publishers USING btree (name);


--
-- TOC entry 54 (OID 35139)
-- Name: shipments_ship_id_key; Type: INDEX; Schema: public; Owner: kgary
--

CREATE UNIQUE INDEX shipments_ship_id_key ON shipments USING btree (id);


--
-- TOC entry 48 (OID 35140)
-- Name: books_title_idx; Type: INDEX; Schema: public; Owner: kgary
--

CREATE INDEX books_title_idx ON books USING btree (title);


--
-- TOC entry 58 (OID 35141)
-- Name: text_idx; Type: INDEX; Schema: public; Owner: kgary
--

CREATE INDEX text_idx ON text_sorting USING btree (letter);


--
-- TOC entry 47 (OID 34721)
-- Name: books_id_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY books
    ADD CONSTRAINT books_id_pkey PRIMARY KEY (id);


--
-- TOC entry 49 (OID 34729)
-- Name: publishers_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY publishers
    ADD CONSTRAINT publishers_pkey PRIMARY KEY (id);


--
-- TOC entry 51 (OID 34736)
-- Name: authors_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 52 (OID 34743)
-- Name: state_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY states
    ADD CONSTRAINT state_pkey PRIMARY KEY (id);


--
-- TOC entry 53 (OID 34755)
-- Name: stock_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (isbn);


--
-- TOC entry 55 (OID 34779)
-- Name: customers_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- TOC entry 56 (OID 34810)
-- Name: employees_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- TOC entry 57 (OID 34818)
-- Name: pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY editions
    ADD CONSTRAINT pkey PRIMARY KEY (isbn);


--
-- TOC entry 59 (OID 34840)
-- Name: subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id);


--
-- TOC entry 60 (OID 34858)
-- Name: schedules_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY schedules
    ADD CONSTRAINT schedules_pkey PRIMARY KEY (employee_id);


--
-- TOC entry 62 (OID 525816)
-- Name: title_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY title
    ADD CONSTRAINT title_pkey PRIMARY KEY (title_id);


--
-- TOC entry 61 (OID 525818)
-- Name: editor_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY editor
    ADD CONSTRAINT editor_pkey PRIMARY KEY (editor_id);


--
-- TOC entry 63 (OID 525844)
-- Name: title_editor_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY title_editor
    ADD CONSTRAINT title_editor_pkey PRIMARY KEY (title_id, editor_id);


SET SESSION AUTHORIZATION 'demo';

--
-- TOC entry 64 (OID 526722)
-- Name: author_pkey; Type: CONSTRAINT; Schema: public; Owner: demo
--

ALTER TABLE ONLY author
    ADD CONSTRAINT author_pkey PRIMARY KEY (author_id);


SET SESSION AUTHORIZATION 'kgary';

--
-- TOC entry 65 (OID 538850)
-- Name: atm_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY atm
    ADD CONSTRAINT atm_pkey PRIMARY KEY (id);


--
-- TOC entry 66 (OID 538856)
-- Name: account_pkey; Type: CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (client);


--
-- TOC entry 99 (OID 525846)
-- Name: $1; Type: FK CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY title_editor
    ADD CONSTRAINT "$1" FOREIGN KEY (title_id) REFERENCES title(title_id);


--
-- TOC entry 100 (OID 525850)
-- Name: $2; Type: FK CONSTRAINT; Schema: public; Owner: kgary
--

ALTER TABLE ONLY title_editor
    ADD CONSTRAINT "$2" FOREIGN KEY (editor_id) REFERENCES editor(editor_id);


--
-- TOC entry 105 (OID 35144)
-- Name: RI_ConstraintTrigger_35144; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER INSERT OR UPDATE ON schedules
    FROM employees
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_check_ins"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 101 (OID 35145)
-- Name: RI_ConstraintTrigger_35145; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER DELETE ON employees
    FROM schedules
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_noaction_del"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 102 (OID 35146)
-- Name: RI_ConstraintTrigger_35146; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER UPDATE ON employees
    FROM schedules
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_noaction_upd"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 106 (OID 35265)
-- Name: RI_ConstraintTrigger_35265; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER INSERT OR UPDATE ON schedules
    FROM employees
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_check_ins"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 103 (OID 35266)
-- Name: RI_ConstraintTrigger_35266; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER DELETE ON employees
    FROM schedules
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_noaction_del"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 104 (OID 35267)
-- Name: RI_ConstraintTrigger_35267; Type: TRIGGER; Schema: public; Owner: kgary
--

CREATE CONSTRAINT TRIGGER valid_employee
    AFTER UPDATE ON employees
    FROM schedules
    NOT DEFERRABLE INITIALLY IMMEDIATE
    FOR EACH ROW
    EXECUTE PROCEDURE "RI_FKey_noaction_upd"('valid_employee', 'schedules', 'employees', 'FULL', 'employee_id', 'id');


--
-- TOC entry 107 (OID 35147)
-- Name: sync_stock_with_editions; Type: RULE; Schema: public; Owner: kgary
--

CREATE RULE sync_stock_with_editions AS ON UPDATE TO editions DO UPDATE stock SET isbn = new.isbn WHERE (stock.isbn = old.isbn);


--
-- TOC entry 6 (OID 34757)
-- Name: subject_ids; Type: SEQUENCE SET; Schema: public; Owner: kgary
--

SELECT pg_catalog.setval('subject_ids', 15, true);


--
-- TOC entry 8 (OID 34781)
-- Name: book_ids; Type: SEQUENCE SET; Schema: public; Owner: kgary
--

SELECT pg_catalog.setval('book_ids', 41478, true);


--
-- TOC entry 10 (OID 34802)
-- Name: shipments_ship_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kgary
--

SELECT pg_catalog.setval('shipments_ship_id_seq', 1011, true);


--
-- TOC entry 12 (OID 34820)
-- Name: author_ids; Type: SEQUENCE SET; Schema: public; Owner: kgary
--

SELECT pg_catalog.setval('author_ids', 25044, true);
