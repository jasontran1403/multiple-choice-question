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

DELETE FROM ListStudent;

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

INSERT INTO ListStudent VALUES 
('PS14692', N'Trần Nguyên Hải', 'hai@gmail.com', '0934513968', 'Nam', N'123 ở đâu?'),
('PS14693', N'Nguyễn Ngọc Anh', 'anh@gmail.com', '0939892031', N'Nữ', N'345 còn lâu mới'),
('PS14694', N'Trần Phương Linh', 'linh@gmail.com', '0935453631', N'Nữ', N'123 nói nha hehe'),
('PS14695', N'Nguyễn Trần Lan Anh', 'lananh@gmai.com', '0987345312', N'Nữ', N'21 không biết đúng ko');

INSERT INTO StudentResult VALUES (null, 'PS14692', 'Jason Tran', 9.5, 9.9, 9.6, 9.67);

delimiter //

CREATE PROCEDURE SP_DELETE (IN stuid VARCHAR(10))
  BEGIN
      DELETE FROM StudentResult WHERE studentid = stuid;
      DELETE FROM ListStudent WHERE studentid = stuid;
  END//

delimiter ;

CREATE TRIGGER TRG_DELETE BEFORE DELETE ON ListStudent
BEGIN
	DELETE FROM ListStudent WHERE studentid = (SELECT studentid FROM DELETED)
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