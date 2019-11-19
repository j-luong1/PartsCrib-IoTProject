create table Transaction
 (
    TID varchar(50),
    UID varchar(50),
    SID numeric(3,0),
    Quantity numeric(3,0),
    TOut TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );