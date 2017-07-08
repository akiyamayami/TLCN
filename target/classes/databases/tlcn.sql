create table user(email varchar(50) primary key,
				  password varchar(30),
				  name nvarchar(50),
				  phone char(15),
				  birthday date;
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
				 licenseplate char(20),
				 type char(10),
				 seats int,
				 sttcarID int)
create table sttcar(sttcarID int primary key,
					name nvarchar(30))
alter table car add foreign key sttcarID REFERENCES sttcar(sttcarID)

-- tài xế có thể chạy nhiều xe, 1 xe chỉ 1 người chạy chính
create table driver_car(email nvarchar(50),
						carID char(5) )
alter table driver_car add foreign key email REFERENCES driver(email)
alter table driver_car add foreign key carID REFERENCES car(carID)

alter table driver_car add primary key(carID)
-----------------------------------------------------------
create table proposal(proposalID char(5) primary key,
					  typeID int,
					  name nvarchar(40),
					  detail nvarchar(200),
					  usefromdate date,
					  usetodate date,
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


create table registerproposal(emailuser nvarchar(50),
							  proposalID char(5),
							  dateregister date)

alter table registerproposal add foreign key emailuser REFERENCES user(email)
alter table registerproposal add foreign key proposalID REFERENCES proposal(proposalID)

alter table driver_car add primary key(proposalID)


#setup some data


insert into rights(detail,name) values("","FIND_PROPOSAL");
insert into rights(detail,name) values("","CREATE_PROPOSAL");
insert into rights(detail,name) values("","CONFIRM_PROPOSAL");
insert into rights(detail,name) values("","CHANGE_PROPOSAL");
insert into rights(detail,name) values("","CHECK_OR_FIND_CARS");

insert into role(name) values("USER");
insert into role(name) values("P.TBVT");
insert into role(name) values("BGM");

insert into role_right values(1,1);
insert into role_right values(1,2);
insert into role_right values(1,4);
insert into role_right values(1,5);
insert into role_right values(3,1);
insert into role_right values(3,3);
insert into role_right values(2,1);
insert into role_right values(2,3);
insert into role_right values(2,5);

insert into user values("12345@gmail.com","","123456","","",3);
insert into user values("1234@gmail.com","","123456","","",2);
insert into user values("123@gmail.com","","123456","","",1);

insert into sttcar(name) values("Bình Thường");
insert into sttcar(name) values("Bảo trì");

insert into sttdriver(name) values("Bình Thường");
insert into sttdriver(name) values("Bệnh");

insert into typeproposal(name) values("Tạo");
insert into typeproposal(name) values("Chỉnh Sữa");
insert into typeproposal(name) values("Hủy");

insert into car(licenseplate,seats,type,sttcar) 
	values("00-00 6875",4,"BWM",1);
insert into car(licenseplate,seats,type,sttcar) 
	values("00-00 6876",6,"Lexus",1);
insert into car(licenseplate,seats,type,sttcar) 
	values("00-00 6877",10,"Audi",1);
insert into car(licenseplate,seats,type,sttcar) 
	values("00-00 6878",16,"Kia",1);
insert into car(licenseplate,seats,type,sttcar) 
	values("00-00 6879",30,"Land Rover",1);


insert into driver values("400@gmail.com","","24-10-1996",3,"Nguyen Van B","0123456789",1)
insert into driver values("401@gmail.com","","24-10-1996",3,"Nguyen Van B","0123456789",1)

insert into driver_car values("400@gmail.com",1)
insert into driver_car values("400@gmail.com",2)
insert into driver_car values("400@gmail.com",3)
insert into driver_car values("401@gmail.com",4)
insert into driver_car values("401@gmail.com",5)