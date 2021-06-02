DROP table accountsJRC;
CREATE table accountsJRC
	( UID VARCHAR(28) NOT NULL,
	  ID VARCHAR(20),
	  NAME VARCHAR(20),
	  LAST VARCHAR(20),
	  EMAIL VARCHAR(30),
	  CREATED TIMESTAMP,
	  ADMIN CHAR(1) DEFAULT 'N',
	  KEYCODE VARCHAR(40),
	  PRIMARY KEY (UID)
	);

INSERT into accountsJRC values ('111100001111','n01156096','Jon','Luong','jonathan.luong@hotmail.ca','2007-2-12','Y','515f5da3f9416f7c5c918a48f129223f511ca567');
INSERT into accountsJRC values ('22220000','def','Alice','Noelle','alice.noelle@gmail.com','2010-8-10','N','cat');
