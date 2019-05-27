CREATE DATABASE StudyCase;

USE StudyCase;

CREATE TABLE User (
   id INT NOT NULL auto_increment,
   `username` VARCHAR(20) default NULL,
   `password`  VARCHAR(20) default NULL,
   PRIMARY KEY (id)
);