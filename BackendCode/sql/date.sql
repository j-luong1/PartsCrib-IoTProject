DROP table Hours;
create table Hours
 (
	Day varchar(10),
	Time varchar(14)
 );

Insert into Hours (Day, Time) values 	("Monday","Closed"),
					("Tuesday","11:11A-11:11P"),
					("Wednesday","11:11A-11:11P"),
					("Thursday","11:11A-11:11P"),
					("Friday","11:11A-11:11P"),
					("Saturday","Closed"),
					("Sunday","Closed");
