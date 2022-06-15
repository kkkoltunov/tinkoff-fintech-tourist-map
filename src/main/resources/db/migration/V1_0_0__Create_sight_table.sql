CREATE TABLE sights
(
    ID           BIGSERIAL PRIMARY KEY NOT NULL,
    NAME         VARCHAR               NOT NULL,
    DESCRIPTION  VARCHAR               NOT NULL,
    WEBSITE      VARCHAR,
    COST         money,
    OPENING_TIME TIME                  NOT NULL,
    CLOSING_TIME TIME                  NOT NULL,
    LONGITUDE    REAL                  NOT NULL,
    LATITUDE     REAL                  NOT NULL
);