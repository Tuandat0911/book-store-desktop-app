CREATE DATABASE bookStore;
USE bookStore;

CREATE TABLE accounts 
	(
      id INT PRIMARY KEY AUTO_INCREMENT,
      email VARCHAR(255) UNIQUE,
      password VARCHAR(255)
    );

CREATE TABLE author
	(
     id INT PRIMARY KEY AUTO_INCREMENT,
     author_name VARCHAR(255),
     gender VARCHAR(255),
     story TEXT,
     birthdate DATE
    );



CREATE TABLE category
	(
     id INT PRIMARY KEY AUTO_INCREMENT,
     category_name VARCHAR(255) UNIQUE
    );
    
CREATE TABLE publisher
		(
         id INT PRIMARY KEY AUTO_INCREMENT,
         publisher_name VARCHAR(255) UNIQUE
        );
        
CREATE TABLE books
	(
	 book_id INT PRIMARY KEY AUTO_INCREMENT,
     book_title VARCHAR(255),
     publication_year INT,
     selling_price DOUBLE,
     import_price DOUBLE,
     ISBN VARCHAR(13) UNIQUE,
     page_number INT,
     publisher_id INT,
     category_id INT,
     author_id INT,
     foreign key(publisher_id) references publisher(id),
     foreign key(category_id) references category(id),
     foreign key(author_id) references author(id)
    );

