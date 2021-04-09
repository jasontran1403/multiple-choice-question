CREATE DATABASE Account;
USE Account;

CREATE TABLE ListAccount (
username VARCHAR(32), 
password VARCHAR(16), 
role VARCHAR(20));

CREATE TABLE ListStudent (
studentid VARCHAR(10) PRIMARY KEY,
fullname VARCHAR(30),
email VARCHAR(30),
phonenum VARCHAR(14),
sex VARCHAR(6),
address VARCHAR(50),
imgpath VARCHAR(100)
);


CREATE TABLE StudentResult (
id INT PRIMARY KEY auto_increment,
studentid VARCHAR(10),
fullname VARCHAR(30),
java double,
javascript double,
htmlcss double,
average double,
FOREIGN KEY (studentid) REFERENCES ListStudent(studentid)
);

delimiter //

CREATE PROCEDURE SP_DELETE (IN stuid VARCHAR(10))
  BEGIN
      DELETE FROM StudentResult WHERE studentid = stuid;
      DELETE FROM ListStudent WHERE studentid = stuid;
  END//

delimiter ;


SELECT * FROM ListAccount;
SELECT * FROM ListStudent;
SELECT * FROM StudentResult;
DELETE FROM ListStudent WHERE studentid = '3';
CALL SP_DELETE('');

DROP TABLE ListAccount;
DROP TABLE StudentResult;
DROP TABLE ListStudent;