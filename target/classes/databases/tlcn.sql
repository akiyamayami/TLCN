create table user(email varchar(50) primary key,
				  password varchar(30),
				  name nvarchar(50),
				  phone char(15),
				  roleID char(5));
create table role(roleID char(5) primary key,
				  name nvarchar(20))
create table rights(rightID char(5),
				   name nvarchar(20),
				   detail nvarchar(40))
create table user_right(email varchar(50),
						rightID char(5))
create table role_right(roleID char(5),
						rightID char(5))

alter table user add foreign key roleID REFERENCES role(rightID)

alter table user_right add foreign key email REFERENCES user(email)
alter table user_right add foreign key rightID REFERENCES rights(rightID)

alter table role_right add foreign key roleID REFERENCES role(roleID)
alter table role_right add foreign key rightID REFERENCES rights(rightID)

create table driver(email nvarchar(50) primary key,
				   name nvarchar(40),
				   birthday date,
				   phone char(15),
				   experience int,
				   license char(5),
				   address nvarchar(100),
				   sttdriverID int)
create table sttdriver(sttdriverID int primary key,
					   name nvarchar(50))
alter table driver add foreign key sttdriverID REFERENCES sttdriver(sttdriverID)

create table car(carID char(5) primary key,
				 numberplace char(20),
				 type char(10),
				 seat int,
				 sttcarID int)
create table sttcar(sttcarID int primary key,
					name nvarchar(30))
alter table car add foreign key sttcarID REFERENCES sttcar(sttcarID)

-- tài xế có thể chạy nhiều xe, 1 xe chỉ 1 người chạy chính
create table driver_car(email nvarchar(50),
						carID char(5))
alter table driver_car add foreign key email REFERENCES driver(email)
alter table driver_car add foreign key carID REFERENCES car(carID)

alter table driver_car add primary key(carID)
-----------------------------------------------------------
create table proposal(proposalID char(5) primary key,
					  typeID int,
					  name nvarchar(40),
					  detail nvarchar(200),
					  usefromdate date,
					  usefromtime time,
					  usetodate date,
					  usetotime time,
					  file nvarchar(20),
					  stt int)
create table typeproposal(typeID int primary key,
						  name nvarchar(15))
alter table proposal add foreign key typeID REFERENCES typeproposal(typeID)
--kết nối 2 bảng car va proposal 1 đề nghị chỉ được mượn 1 xe
create table registercar(proposalID char(5),
						 carID char(5))

alter table registercar add foreign key proposalID REFERENCES proposal(proposalID)
alter table registercar add foreign key carID REFERENCES car(carID)

alter table registercar add primary key(proposalID,carID)


create table registerproposal(email nvarchar(50),
							  proposalID char(5),
							  dateregister date,
							  timeregister time)