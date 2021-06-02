DROP table ListItems;
DROP table ListHead;
create table ListItems
 (
	TID varchar(50),
	UID varchar(50),
	Name varchar(30),
	SID numeric(3,0),
	Quantity numeric(3,0)
 );

create table ListHead
 (
	TID varchar(50),
	UID varchar(50),
	Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Title varchar(40),
	Course varchar(10),
	Description varchar(200)	
 );	
