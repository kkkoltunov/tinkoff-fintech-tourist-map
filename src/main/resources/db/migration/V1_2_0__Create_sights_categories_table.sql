CREATE TABLE sights_categories
(
    SIGHT_ID    BIGINT NOT NULL,
    CATEGORY_ID BIGINT NOT NULL,
    PRIMARY KEY (SIGHT_ID, CATEGORY_ID),

    FOREIGN KEY (SIGHT_ID)
        REFERENCES sights (ID)
        ON DELETE CASCADE,

    FOREIGN KEY (CATEGORY_ID)
        REFERENCES categories (ID)
        ON DELETE CASCADE
) PARTITION BY LIST (CATEGORY_ID);