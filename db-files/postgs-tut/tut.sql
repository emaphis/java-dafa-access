-- PostgreSQL Tutorial: <https://www.postgresql.org/docs/9.5/static/tutorial.html>


-- create a table
CREATE TABLE weather (
    city          varchar(80),
    temp_lo       int,               -- low temperature
    temp_hi       int,               -- high temperture
    prcp          real,              -- percipitation
    date          date
);


CREATE TABLE cities (
    name         varchar(80),
    location     point
);


-- To drop a table:
DROP TABLE tablename;


-- inerting data:

INSERT INTO weather VALUES ('San Francisco', 46, 50, 0.25, '1994-11-27');

INSERT INTO cities VALUES ('San Francisco', '(-194.0, 53.0)');

INSERT INTO weather (city, temp_lo, temp_hi, prcp, date)
    VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');

INSERT INTO weather (date, city, temp_hi, temp_lo)
    VALUES ('1994-11-29', 'Hayward', 54, 37);


-- Querying a table

SELECT * FROM weather;

SELECT city, temp_lo, temp_hi, prcp, date FROM weather;

SELECT city, (temp_hi+temp_lo)/2 AS temp_avg, date FROM weather;

SELECT * FROM weather
    WHERE city = 'San Francisco' AND PRCP > 0.0;

SELECT * FROM weather
    ORDER BY city;

SELECT * FROM weather
    ORDER BY city, temp_lo;

-- request duplicate rows be removed:
SELECT DISTINCT city
    FROM weather;


SELECT DISTINCT city
    FROM weather
    ORDER BY city;


-- Joins between tables:

SELECT *
    FROM weather, cities
    WHERE city = name;

-- show city name only once:
SELECT city, temp_lo, temp_hi, prcp, date, location
    FROM weather, cities
    WHERE city = name;


SELECT weather.city, weather.temp_lo, weather.temp_hi,
       weather.prcp, weather.date, cities.location
    FROM weather, cities
    WHERE cities.name = weather.city;


SELECT *
    FROM weather INNER JOIN cities ON (weather.city = cities.name);


-- outer join to include Hayward
SELECT *
    FROM weather LEFT OUTER JOIN cities ON (weather.city = cities.name)
    ORDER BY cities.name;

SELECT W1.city, W1.temp_lo AS low, W1.temp_hi AS high,
    W2.city, W2.temp_lo AS low, W2.temp_hi AS high
    FROM weather W1, weather W2
    WHERE W1.temp_lo < W2.temp_lo
    AND W1.temp_hi > W2.temp_hi;


SELECT *
    FROM weather w, cities c
    WHERE w.city = c.name;


-- 2.6 Agragate Functions

-- find the highest low temperature

SELECT max(temp_lo) FROM weather;

SELECT city FROM weather
    WHERE temp_lo = (SELECT max(temp_lo) FROM weather);

-- the maximum low temperature observed in each city
SELECT city, max(temp_lo)
    FROM weather
    GROUP BY city;


SELECT city, max(temp_lo)
    FROM weather
    GROUP BY city
    HAVING max(temp_lo) < 40;

SELECT city, max(temp_lo)
    FROM weather
    WHERE city LIKE 'S%'
    GROUP BY city
    HAVING max(temp_lo) > 40;


-- Updates

UPDATE weather
    SET temp_hi = temp_hi - 2, temp_lo = temp_lo -2
    WHERE date > '1994-11-28';


-- deletions
DELETE FROM weather WHERE city = 'Hayward';

SELECT * FROM weather;

-- delete all records from a table !!!
DELETE FROM tablename;


-- dropping tables:

DROP TABLE weather, cities;
