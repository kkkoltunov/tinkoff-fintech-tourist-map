CREATE TABLE categories
(
    ID        BIGSERIAL PRIMARY KEY NOT NULL,
    PARENT_ID BIGINT,
    NAME      VARCHAR UNIQUE        NOT NULL,

    FOREIGN KEY (PARENT_ID)
        REFERENCES categories (ID)
        ON DELETE CASCADE
);
