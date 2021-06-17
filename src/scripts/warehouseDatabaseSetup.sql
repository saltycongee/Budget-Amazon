CREATE TABLE Credit_Card_Name
(
    Credit_Card_Information varchar2(20) PRIMARY KEY,
    Name varchar2(20),
    Last_Name varchar2(20)
);
CREATE TABLE Seller_Account
(
    Seller_ID               INTEGER PRIMARY KEY,
    Email                   varchar2(20),
    Password                varchar2(20),
    Credit_Card_Information varchar2(20),
    Phone_Number            INTEGER
);
CREATE TABLE Postal_Code_Locations
(
    Postal_Code varchar2(20) PRIMARY KEY,
    City        varchar2(20),
    Street      varchar2(20)
);

CREATE TABLE Location
(
    Postal_Code     varchar2(20),
    Building_Number INTEGER,
    PRIMARY KEY (Postal_Code, Building_Number)
);
CREATE TABLE Warehouse
(
    Warehouse_ID    INTEGER,
    Postal_Code     varchar2(20),
    Building_Number INTEGER,
    PRIMARY KEY (Warehouse_ID),
    FOREIGN KEY (Postal_Code, Building_Number) REFERENCES Location
        ON DELETE CASCADE
);
CREATE TABLE Transportation_Methods
(
    Liscence_Plate varchar2(20),
    Shipping_Price INTEGER,
    PRIMARY KEY (Liscence_Plate)
);


CREATE TABLE Location_Transportation_Methods
(
    Postal_Code varchar2(20),
    Building_Number INTEGER,
    Liscence_Plate  varchar2(20),
    PRIMARY KEY (Postal_Code, Building_Number, Liscence_Plate),
    FOREIGN KEY (Liscence_Plate) REFERENCES Transportation_Methods
        ON DELETE CASCADE,
    FOREIGN KEY (Postal_Code, Building_Number) REFERENCES Location
        ON DELETE CASCADE
);

CREATE TABLE Item
(
    Item_Name    varchar2(20),
    Item_ID      INTEGER PRIMARY KEY,
    Count        INTEGER,
    Weight       REAL,
    Price        REAL,
    Seller_ID    INTEGER NOT NULL,
    Warehouse_ID INTEGER,
    FOREIGN KEY (Seller_ID) REFERENCES Seller_Account
        ON DELETE CASCADE,
    FOREIGN KEY (Warehouse_ID) REFERENCES Warehouse
        ON DELETE CASCADE
);
CREATE TABLE Item_Transportation_Methods
(
    Item_ID        INTEGER,
    Liscence_Plate varchar2(20),
    PRIMARY KEY (Item_ID, Liscence_Plate),
    FOREIGN KEY (Liscence_Plate) REFERENCES Transportation_Methods
        ON DELETE CASCADE,
    FOREIGN KEY (Item_ID) REFERENCES Item
        ON DELETE CASCADE
);
CREATE TABLE Customer_Accounts
(
    Email                   varchar2(20) UNIQUE,
    Password                varchar2(20),
    Phone_Number            INTEGER,
    Credit_Card_Information varchar2(20),
    Customer_ID             INTEGER,
    Order_List_ID           INTEGER,
    Postal_Code             varchar2(20),
    Building_Number         INTEGER,
    PRIMARY KEY (Customer_ID),
    FOREIGN KEY (Postal_Code, Building_Number) REFERENCES Location
        ON DELETE CASCADE
);
CREATE TABLE Order_List
(
    Order_List_ID INTEGER,
    Customer_ID   INTEGER,
    PRIMARY KEY (Order_List_ID),
    FOREIGN KEY (Customer_ID) REFERENCES Customer_Accounts
        ON DELETE CASCADE
);

CREATE TABLE Items_Ordered
(
    Item_ID INTEGER,
    Order_List_ID INTEGER,
    PRIMARY KEY (Order_List_ID, Item_ID),
    FOREIGN KEY (Order_List_ID) REFERENCES Order_List
        ON DELETE CASCADE,
    FOREIGN KEY (Item_ID) REFERENCES Item
        ON DELETE CASCADE
);

CREATE TABLE Seller_Location
(
    Seller_ID       INTEGER,
    Postal_Code     varchar2(20),
    Building_Number INTEGER,
    PRIMARY KEY (Seller_ID, Postal_Code, Building_Number),
    FOREIGN KEY (Seller_ID) REFERENCES Seller_Account
        ON DELETE CASCADE,
    FOREIGN KEY (Postal_Code, Building_Number) REFERENCES Location
        ON DELETE CASCADE
);

CREATE TABLE Workers
(
    Worker_ID    INTEGER PRIMARY KEY,
    Manager_ID   INTEGER,
    Name         varchar2(20),
    Phone_Number INTEGER,
    Wage         INTEGER
);

CREATE TABLE Warehouse_Worker
(
    Worker_ID    INTEGER PRIMARY KEY,
    Warehouse_ID INTEGER,
    FOREIGN KEY (Worker_ID) references Workers
        ON DELETE CASCADE
);

CREATE TABLE Manages_Warehouse
(
    Worker_ID    INTEGER,
    Warehouse_ID INTEGER,
        PRIMARY KEY (Worker_ID, Warehouse_ID),
    FOREIGN KEY (Worker_ID) references Workers
        ON DELETE CASCADE,
    FOREIGN KEY (Warehouse_ID) references Warehouse
        ON DELETE CASCADE
);

CREATE TABLE Driver_Has
(
    Worker_ID     INTEGER,
    License_Plate varchar2(20) NOT NULL,
    PRIMARY KEY (Worker_ID, License_Plate),
    FOREIGN KEY (Worker_ID) references Workers
        ON DELETE CASCADE,
    FOREIGN KEY (License_Plate) references Transportation_Methods
        ON DELETE CASCADE
);

INSERT INTO Location
VALUES ('V4A 5G6', 13);
INSERT INTO Location
VALUES ('V4A 5G6', 14);
INSERT INTO Location
VALUES ('V4A 5G0', 14);
INSERT INTO Location
VALUES ('V3F 6J6', 22);
INSERT INTO Location
VALUES ('V3K 7J8', 5);
INSERT INTO Location
VALUES ('V6F 0G5', 2);
INSERT INTO Location
VALUES ('T3K 7G3', 420);
INSERT INTO Location
VALUES ('V7D 9T0', 42);
INSERT INTO Location
VALUES ('Y3K 7S5', 49);
INSERT INTO Location
VALUES ('B2I 7L1', 69);
INSERT INTO Location
VALUES ('N8D 6R8', 1001);

INSERT INTO Postal_Code_Locations
VALUES ('V4A 5G6', 'Vancouver', 'King Street');
INSERT INTO Postal_Code_Locations
VALUES ('V4A 5G0', 'Burnaby', 'Cool Street');
INSERT INTO Postal_Code_Locations
VALUES ('V3F 6J6', 'Richmond', 'Nice Avenue');
INSERT INTO Postal_Code_Locations
VALUES ('V3K 7J8', 'Vancouver', 'Fake Street');
INSERT INTO Postal_Code_Locations
VALUES ('V6F 0G5', 'Vancouver', 'Yep Street');
INSERT INTO Postal_Code_Locations
VALUES ('T3K 7G3', 'Toronto', 'UFT street');
INSERT INTO Postal_Code_Locations
VALUES ('V7D 9T0', 'Waterloo', 'CCC Street');
INSERT INTO Postal_Code_Locations
VALUES ('Y3K 7S5', 'Montreal', 'McGill Street');
INSERT INTO Postal_Code_Locations
VALUES ('B2I 7L1', 'Burnaby', 'University Drive');
INSERT INTO Postal_Code_Locations
VALUES ('N8D 6R8', 'Burnaby', 'Kensington Street');


INSERT INTO Customer_Accounts
VALUES ('aaa@gmail.com', 'aaa', 6041111111, '1234123412341234',01, 11, 'V4A 5G6', 13);
INSERT INTO Customer_Accounts
VALUES ('bbb@gmail.com','bbb', 6042222222, '2345234523452345', 02, 12, 'V4A 5G6', 14);
INSERT INTO Customer_Accounts
VALUES ('ccc@gmail.com', 'ccc', 6043333333, '3456345634563456', 03, 13, 'V3F 6J6', 22);
INSERT INTO Customer_Accounts
VALUES ('ddd@gmail.com', 'ddd', 6044444444, '4567456745674567', 04, 14, 'V3K 7J8', 05);
INSERT INTO Customer_Accounts
VALUES ('eee@gmail.com', 'eee', 6045555555, '678567856785678', 05, 15, 'V6F 0G5', 02);



INSERT INTO Credit_Card_Name
VALUES ('1234123412341234', 'Alex','Turner');
INSERT INTO Credit_Card_Name
VALUES ('2345234523452345', 'Rivers','Cuomo');
INSERT INTO Credit_Card_Name
VALUES ('3456345634563456', 'Liam','Gallagher');
INSERT INTO Credit_Card_Name
VALUES ('4567456745674567', 'Julian','Casablancas');
INSERT INTO Credit_Card_Name
VALUES ('5678567856785678', 'Thom','Yorke');


INSERT INTO Transportation_Methods
VALUES ('392 THR', 5);
INSERT INTO Transportation_Methods
VALUES ('202 KIF', 10.5);
INSERT INTO Transportation_Methods
VALUES ('132 GOW', 7.25);
INSERT INTO Transportation_Methods
VALUES ('735 MCJ', 20);
INSERT INTO Transportation_Methods
VALUES ('193 JSP', 15.5);

INSERT INTO Location_Transportation_Methods
VALUES ('V4A 5G6', 13, '392 THR');
INSERT INTO Location_Transportation_Methods
VALUES ('V4A 5G0', 14, '202 KIF');
INSERT INTO Location_Transportation_Methods
VALUES ('V3F 6J6', 22, '132 GOW');
INSERT INTO Location_Transportation_Methods
VALUES ('V3K 7J8', 5, '735 MCJ');
INSERT INTO Location_Transportation_Methods
VALUES ('V6F 0G5', 2, '193 JSP');

INSERT INTO Seller_Account
VALUES (111, 'ttt@gmail.com', 'ttt', '1234123412341234', 6041111111);
INSERT INTO Seller_Account
VALUES (112, 'fff@gmail.com', 'fff', '2345234523452345', 6042222222);
INSERT INTO Seller_Account
VALUES (113, 'hhh@gmail.com', 'hhh', '3456345634563456', 6043333333);
INSERT INTO Seller_Account
VALUES (114, 'ooo@gmail.com', 'ooo', '4567456745674567', 6044444444);
INSERT INTO Seller_Account
VALUES (115, 'mmm@gmail.com', 'mmm', '5678567856785678', 6045555555);

INSERT INTO Warehouse
VALUES (505, 'T3K 7G3', 420);
INSERT INTO Warehouse
VALUES (506, 'V7D 9T0', 42);
INSERT INTO Warehouse
VALUES (507, 'Y3K 7S5', 49);
INSERT INTO Warehouse
VALUES (508, 'B2I 7L1', 69);
INSERT INTO Warehouse
VALUES (509, 'N8D 6R8', 1001);

INSERT INTO Item
VALUES ('Waterbottle', 1534, 25, 11.3, 15.99, 111, 505);
INSERT INTO Item
VALUES ('Coffee Mug', 5621, 1, 57.6, 30.99, 112, 506);
INSERT INTO Item
VALUES ('Chewing Gum', 4325, 11, 1.5, 2.99, 113, 507);
INSERT INTO Item
VALUES ('Laptop', 8469, 3, 33, 2599.99, 114, 508);
INSERT INTO Item
VALUES ('Kitchen Knife', 7514, 5, 25.4, 24.99, 115, 509);
insert into item values('Gun',222,2,3,499.99,111,505);
insert into item values('Headset',1223,2,3,199.99,111,506);
insert into item values('$25 Amazon Giftcard',35,2,3,24.99,111,507);
insert into item values('Desk Fan',486,2,3,49.99,111,508);
insert into item values('Pencil',123,2,3,.99,111,509);

INSERT INTO Item_Transportation_Methods
VALUES (1534, '392 THR');
INSERT INTO Item_Transportation_Methods
VALUES (5621, '202 KIF');
INSERT INTO Item_Transportation_Methods
VALUES (4325, '132 GOW');
INSERT INTO Item_Transportation_Methods
VALUES (8469, '735 MCJ');
INSERT INTO Item_Transportation_Methods
VALUES (7514, '193 JSP');

INSERT INTO Order_List
VALUES (11, 01);
INSERT INTO Order_List
VALUES (12, 02);
INSERT INTO Order_List
VALUES (13, 03);
INSERT INTO Order_List
VALUES (14, 04);
INSERT INTO Order_List
VALUES (15, 05);

INSERT INTO Items_Ordered
VALUES (1534, 11);
INSERT INTO Items_Ordered
VALUES (5621, 12);
INSERT INTO Items_Ordered
VALUES (4325, 13);
INSERT INTO Items_Ordered
VALUES (8469, 14);
INSERT INTO Items_Ordered
VALUES (7514, 15);

INSERT INTO Seller_Location
VALUES (111, 'V4A 5G6', 13);
INSERT INTO Seller_Location
VALUES (112, 'V4A 5G6', 14);
INSERT INTO Seller_Location
VALUES (113, 'V3F 6J6', 22);
INSERT INTO Seller_Location
VALUES (114, 'V3K 7J8', 5);
INSERT INTO Seller_Location
VALUES (115, 'V6F 0G5', 2);

INSERT INTO Workers
VALUES (101, NULL, 'Andy Lee', 604123456, 43.5);
INSERT INTO Workers
VALUES (102, 101, 'Marisha Thompson', 604123457, 17.83);
INSERT INTO Workers
VALUES (103, 111, 'Jay Quong', 604123458, 17.83);
INSERT INTO Workers
VALUES (104, 108, 'John Johnson', 604123459, 17.83);
INSERT INTO Workers
VALUES (105, NULL, 'Barack Obama', 604123460, 43.5);
INSERT INTO Workers
VALUES (106, 114, 'Phil Philson', 604123461, 17.83);
INSERT INTO Workers
VALUES (107, 101, 'Timothy Jimothy', 604123462, 17.83);
INSERT INTO Workers
VALUES (108, NULL, 'Tom Thompson', 604123463, 17.83);
INSERT INTO Workers
VALUES (109, 105, 'Janet Huang', 604123464, 17.83);
INSERT INTO Workers
VALUES (110, 111, 'Nameless Person', 604123465, 17.83);
INSERT INTO Workers
VALUES (111, NULL, 'Matt Mercer', 604123466, 43.5);
INSERT INTO Workers
VALUES (112, 108, 'Michael Ming', 604123467, 17.83);
INSERT INTO Workers
VALUES (113, 114, 'Jerry Jimmy', 604123468, 17.83);
INSERT INTO Workers
VALUES (114, NULL, 'Sapphire Fire', 604123469, 43.5);
INSERT INTO Workers
VALUES (115, 101, 'Skype Zoomie', 604123470, 17.83);

INSERT INTO Warehouse_Worker
VALUES (102, 505);
INSERT INTO Warehouse_Worker
VALUES (103, 506);
INSERT INTO Warehouse_Worker
VALUES (104, 507);
INSERT INTO Warehouse_Worker
VALUES (106, 508);
INSERT INTO Warehouse_Worker
VALUES (107, 509);

INSERT INTO Manages_Warehouse
VALUES (101, 505);
INSERT INTO Manages_Warehouse
VALUES (105, 506);
INSERT INTO Manages_Warehouse
VALUES (108, 507);
INSERT INTO Manages_Warehouse
VALUES (111, 508);
INSERT INTO Manages_Warehouse
VALUES (114, 509);

INSERT INTO Driver_Has
VALUES (109, '392 THR');
INSERT INTO Driver_Has
VALUES (110, '202 KIF');
INSERT INTO Driver_Has
VALUES (112, '132 GOW');
INSERT INTO Driver_Has
VALUES (113, '735 MCJ');
INSERT INTO Driver_Has
VALUES (115, '193 JSP');
