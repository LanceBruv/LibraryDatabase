create schema library;

create table book
(Book_id char(10) NOT NULL,
Title varchar(200) NOT NULL,
PRIMARY KEY(Book_id)
);

--FOREIGN KEY (book_id) references book(Book_id)
create table book_authors
(book_id char(10) NOT NULL,
author_name varchar(50) NOT NULL,
fname varchar(15) DEFAULT NULL,
minit varchar (5) DEFAULT NULL,
lname varchar (15) DEFAULT NULL,
PRIMARY KEY(book_id,author_name)
);

create table library_branch
(branch_id char(2) NOT NULL,
Branch_name varchar(20),
address varchar(50),
PRIMARY KEY(branch_id)
);

create table book_copies
(book_id char(10) NOT NULL,
branch_id char(2) NOT NULL,
no_of_copies INT DEFAULT  '0',
FOREIGN KEY (book_id) references book(book_id),
FOREIGN KEY (branch_id) references library_branch(branch_id),
PRIMARY KEY (book_id,branch_id)
);

create table borrower 
(card_no char(5) NOT NULL,
fname varchar(15),
lname varchar(15),
address varchar(50),
city varchar(15),
state varchar(10),
phone varchar(15),
PRIMARY KEY (card_no)
);

create table book_loans
(loan_id INT NOT NULL AUTO_INCREMENT,
book_id char(10) NOT NULL,
branch_id char(2) NOT NULL,
card_no char(5) NOT NULL,
Date_out DATETIME DEFAULT CURRENT_TIMESTAMP,
Due_date DATETIME DEFAULT CURRENT_TIMESTAMP,
Date_in DATETIME,
PRIMARY KEY(loan_id) 
); 

create trigger set_date
before insert on book_loans
for each row 
set new.due_date = Date_Add(due_date,INTERVAL 14 DAY);

create trigger full_name
before insert on book_authors
for each row
set new.author_name = fname+' '+minit+' '+lname;

LOAD DATA LOCAL INFILE 'C:/Users/Ajay/Desktop/SQL_library_data/library_branch.csv'
INTO TABLE library_branch
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(branch_id, branch_name, address);

-- create a temporary table and then copy the data from here to the
-- required tables
create table book_copies_authors
(book_id char(10) NOT NULL,
author_name varchar(50),
title varchar (50) NOT NULL,
FOREIGN KEY (book_id) references book(book_id),
FOREIGN KEY (branch_id) references library_branch(branch_id),
PRIMARY KEY (book_id,branch_id)
);

--remove foreign key on book_copies_authors
--find foreign key name
show create table book_copies;
--delete foreign key
ALTER TABLE book_copies
  DROP FOREIGN KEY book_copies_ibfk_1;

LOAD DATA LOCAL INFILE 'C:/Users/Ajay/Desktop/SQL_library_data/book_copies_new.csv'
INTO TABLE book_copies
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(book_id, branch_id,no_of_copies);

LOAD DATA LOCAL INFILE 'C:/Users/Ajay/Desktop/SQL_library_data/borrowers.csv'
INTO TABLE borrower
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(card_no, fname,lname,address,city,state,phone);

-- create a temporary table and then copy the data from here to the
-- required tables
create table temp_author
(book_id char(10) NOT NULL,
author_name varchar(200),
title varchar(200) NOT NULL,
PRIMARY KEY (book_id,author_name)
);

LOAD DATA LOCAL INFILE 'C:/Users/Ajay/Desktop/SQL_library_data/books_authors.csv'
INTO TABLE temp_author
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(book_id, author_name,title);

--set all the Foreign key references that haven't already been set
Alter table book_authors 
add constraint book_id_fk foreign key (book_id) references book(book_id);

Alter table book_copies
add constraint book_copies_book_id_fk foreign key (book_id) references book(book_id);

Alter table book_copies
add constraint book_copies_branch_id_fk foreign key (branch_id) references library_branch(branch_id);

Alter table book_loans
add constraint book_loans_book_id_fk foreign key (book_id) references book(book_id);

Alter table book_loans
add constraint book_loans_branch_id_fk foreign key (branch_id) references library_branch(branch_id);

Alter table book_loans
add constraint book_loans_card_no_fk foreign key (card_no) references borrower(card_no);

--add column for available books
ALTER table book_copies
add available_copies INT;

UPDATE book_copies
SET available_copies = no_of_copies;

ALTER TABLE borrower 
ADD COLUMN no_borrowed INT DEFAULT '0';

--begin not used
--create trigger for check out	
CREATE TRIGGER update_book_count_borrower
AFTER INSERT ON book_loans
FOR EACH ROW
UPDATE borrower
SET no_borrowed = no_borrowed+1
WHERE card_no = new.card_no;	

--cannot create multiple triggers
CREATE TRIGGER update_book_count
AFTER INSERT ON book_loans
FOR EACH ROW
UPDATE book_copies
SET no_available = no_available-1
WHERE book_id = new.book_id AND
branch_id = new.branch_id;
--cannot create multiple triggers

--create a cascading trigger
CREATE TRIGGER update_book_count_copies
AFTER UPDATE ON borrower
FOR EACH ROW
UPDATE book_copies
SET book_copies = book_copies -1
WHERE book_id = new.book_id AND
branch_id = new.branch_id;
--cannot trigger view
CREATE TRIGGER update_book_count_borrower
AFTER INSERT ON book_loans
FOR EACH ROW
UPDATE update_copies
SET no_borrowed = no_borrowed+1,
no_of_copies = no_of_copies -1
WHERE card_no = new.card_no AND
book_id = new.book_id AND
branch_id = new.branch_id;
-- end not used

--remove available_books and no_borrwed (make these values robust)
ALTER TABLE borrower DROP no_borrowed;
ALTER TABLE book_copies DROP no_available;

--try to simplify
--create checkouts view
CREATE VIEW checkouts AS
SELECT book_id,branch_id,count(*) AS num_checkouts FROM book_loans 
GROUP BY book_id,branch_id;

--create a view for available books
CREATE VIEW num_checked_out AS
SELECT book_copies.book_id,book_copies.branch_id,no_of_copies,count(*) as test1,no_of_copies - IFNULL(count(*),0) AS num_checked_out 
FROM book_copies LEFT JOIN checkouts ON (book_copies.book_id = checkouts.book_id) AND 
book_copies.branch_id = checkouts.branch_id
GROUP BY num_checkouts;

--test
SELECT book_copies.book_id,book_copies.branch_id,no_of_copies,IFNULL(count(*),0) as test1
FROM book_copies LEFT JOIN checkouts ON (book_copies.book_id = checkouts.book_id) AND 
book_copies.branch_id = checkouts.branch_id
GROUP BY num_checkouts;

--create view for difference
CREATE VIEW num_available AS
SELECT book_id,branch_id,(no_of_copies - num_checked_out ) AS num_available
FROM (book_copies NATURAL JOIN num_checked_out);
--end try to simplify


--drop temp authors as its not in 1NF
DROP TABLE temp_authors;
--create a view for combined authors
CREATE VIEW combined_authors AS
SELECT book_id,title, GROUP_CONCAT(author_name SEPARATOR ',') AS author_list
FROM (book_authors NATURAL JOIN book)
GROUP BY book_id;
