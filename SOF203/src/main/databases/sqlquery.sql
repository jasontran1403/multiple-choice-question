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
sex VARCHAR(5),
address VARCHAR(50),
imgpath VARCHAR(100)
);

DELETE FROM ListStudent;
INSERT INTO ListStudent VALUES ('PS14692', 'Jason Tran', 'jasont14692@gmail.com', '84 934513968', 'Male', '123 I Dont know', 'C:\Users\Admin\Desktop\multiple-choice-question\SOF203\src\main\image\kaka');

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
INSERT INTO ListStudent VALUES (3, 'PS14692', 'Jason Tran', 9.5, 9.9, 9.6, 9.67);



SELECT * FROM ListAccount;
SELECT * FROM ListStudent;
SELECT * FROM StudentResult;
DROP TABLE ListAccount;
DROP TABLE ListStudent;
DROP TABLE StudentResult;