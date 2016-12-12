CREATE TABLE BANK_CUSTOMERS
(
    BANK_CUSTOMERS_PK BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USERNAME VARCHAR(200) NOT NULL,
    PASSWORD VARCHAR(200) NOT NULL,
    PRIMARY KEY(BANK_CUSTOMERS_PK),
    UNIQUE(USERNAME)
) ;
 
INSERT INTO BANK_CUSTOMERS(USERNAME, PASSWORD) VALUES ('MadMax', 'passwd123');

CREATE TABLE BANK_ACCT_TYPE
(
    BANK_ACCT_TYPE_PK BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ACCOUNT_TYPE VARCHAR(100) NOT NULL,
    PRIMARY KEY(BANK_ACCT_TYPE_PK),
    UNIQUE(ACCOUNT_TYPE)
);
 
INSERT INTO BANK_ACCT_TYPE(ACCOUNT_TYPE) VALUES ('SAVINGS');
INSERT INTO BANK_ACCT_TYPE(ACCOUNT_TYPE) VALUES ('CHECKING');


CREATE TABLE BANK_ACCOUNT
(
    BANK_ACCOUNT_PK BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ACCOUNT_NO VARCHAR(200) NOT NULL,
    BANK_ACCT_TYPE_PK BIGINT NOT NULL,
    BANK_CUSTOMERS_PK BIGINT NOT NULL,
    BALANCE DECIMAL(20, 2) NOT NULL,
    PRIMARY KEY(BANK_ACCOUNT_PK),
    UNIQUE(ACCOUNT_NO),
   
    FOREIGN KEY(BANK_CUSTOMERS_PK) REFERENCES BANK_CUSTOMERS(BANK_CUSTOMERS_PK)
);
INSERT INTO BANK_ACCOUNT(ACCOUNT_NO, BANK_ACCT_TYPE_PK, BANK_CUSTOMERS_PK, BALANCE) VALUES
(
    'BK-001-09',
    (SELECT BANK_ACCT_TYPE_PK FROM BANK_ACCT_TYPE WHERE ACCOUNT_TYPE = 'SAVINGS'),
    (SELECT BANK_CUSTOMERS_PK FROM BANK_CUSTOMERS WHERE USERNAME = 'MadMax'),
    9000000
);
 
INSERT INTO BANK_ACCOUNT(ACCOUNT_NO, BANK_ACCT_TYPE_PK, BANK_CUSTOMERS_PK, BALANCE) VALUES
(
    'BK-012-10',
    (SELECT BANK_ACCT_TYPE_PK FROM BANK_ACCT_TYPE WHERE ACCOUNT_TYPE = 'CHECKING'),
    (SELECT BANK_CUSTOMERS_PK FROM BANK_CUSTOMERS WHERE USERNAME = 'MadMax'),
    1000000
);


