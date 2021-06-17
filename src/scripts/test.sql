CREATE TABLE Credit_Card_Name
(
    Credit_Card_Information varchar2(20) PRIMARY KEY,
    Name varchar2(20)
);
CREATE TABLE Seller_Account
(
    Seller_ID               INTEGER PRIMARY KEY,
    Email                   varchar2(20),
    Credit_Card_Information varchar2(20),
    Phone_Number            INTEGER
);
CREATE TABLE Location
(
    Postal_Code     varchar2(20),
    Building_Number INTEGER,
    PRIMARY KEY (Postal_Code, Building_Number)
);
INSERT INTO Location
VALUES ('V4A 5G6', 13);
