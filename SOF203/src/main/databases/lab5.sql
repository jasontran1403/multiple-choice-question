create database quanlysinhvien;
use quanlysinhvien;
create table Student 
(
	MaSV VARCHAR(50) PRIMARY KEY,
    HoTen NVARCHAR(50),
    Email VARCHAR(50),
    SoDT NVARCHAR(12),
    GioiTinh NVARCHAR(5),
    DiaChi NVARCHAR(100)
);

insert into Student VALUES
('PS14692', N'Trần Nguyên Hải', 'hai@gmail.com', '0934513968', 'Nam', N'123 ở đâu?'),
('PS14693', N'Nguyễn Ngọc Anh', 'anh@gmail.com', '0939892031', N'Nữ', N'345 còn lâu mới'),
('PS14694', N'Trần Phương Linh', 'linh@gmail.com', '0935453631', N'Nữ', N'123 nói nha hehe'),
('PS14695', N'Nguyễn Trần Lan Anh', 'lananh@gmai.com', '0987345312', N'Nữ', N'21 không biết đúng ko');

select * from Student;
DROP TABLE Student;