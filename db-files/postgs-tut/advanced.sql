-- Advanced features
-- https://www.postgresql.org/docs/9.5/static/tutorial-advanced.html

-- create a tables

CREATE TABLE cities (
    city         varchar(80) primary key,
    location     point
);

CREATE TABLE weather (
    city          varchar(80) references cities(city),
    temp_lo       int,               -- low temperature
    temp_hi       int,               -- high temperture
    prcp          real,              -- percipitation
    date          date
);



-- inerting data:
INSERT INTO cities VALUES ('San Francisco', '(-194.0, 53.0)');

INSERT INTO weather VALUES ('San Francisco', 46, 50, 0.25, '1994-11-27');


INSERT INTO weather (city, temp_lo, temp_hi, prcp, date)
    VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');

--INSERT INTO weather (date, city, temp_hi, temp_lo)
--    VALUES ('1994-11-29', 'Hayward', 54, 37);


-- Querying a table

SELECT * FROM weather;


-- A view
CREATE VIEW myview AS
    SELECT c.city, w.temp_lo, w.temp_hi, w.prcp, w.date, c.location
        FROM weather w, cities c
        WHERE c.city = w.city;

SELECT * FROM myview;


-- produces and error
INSERT INTO weather VALUES ('Berkeley', 45, 53, 0.0, '1994-11-28');
