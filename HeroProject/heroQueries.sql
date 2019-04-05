USE Superheroes;

SELECT * FROM `power`; 
SELECT * FROM hero;
SELECT * FROM location;
SELECT * FROM org;
SELECT * FROM sighting;

SELECT * FROM `power` WHERE power_id = ?; 
SELECT * FROM hero WHERE hero_id = ?;
SELECT * FROM location WHERE location_id = ?;
SELECT * FROM org WHERE org_id = ?;
SELECT * FROM siting WHERE siting_id = ?;


SELECT * FROM `power` 
INNER JOIN hero_to_power ON `power`.power_id = hero_to_power.power_id
WHERE hero_id = 1;

SELECT * FROM hero 
INNER JOIN hero_to_power ON `power`.power_id = hero_to_power.power_id
WHERE hero_id = 1;

SELECT * FROM location 
INNER JOIN org ON location.location_id = org.location_id
WHERE org_id = 1;

SELECT * FROM hero
INNER JOIN hero_to_siting ON hero.hero_id = hero_to_siting.hero_id
INNER JOIN siting ON hero_to_siting.siting_id = siting.siting_id
WHERE location_id = 1;

SELECT * FROM location
INNER JOIN siting ON location.location_id = siting.location_id
INNER JOIN hero_to_siting ON siting.siting_id = hero_to_siting.siting_id
WHERE hero_id = 1;

SELECT * FROM siting
WHERE siting_date = "2018-02-06";

USE Superheroes;

INSERT INTO Superheroes.hero (hero_id, hero_name, hero_description, is_evil)
VALUES ( 1, "Johnny", "Amazing sunshine man", 1);

INSERT INTO location (location_id, location_name, location_description, phone_number, latitude, longitude, street, city, state, country, zip_code) VALUES (99, "1", "1", "1", 1, 1, "1", "1", "1", "1", "1");
UPDATE location SET location_name = "2", location_description = "2", phone_number = "2", latitude = 2, 
longitude = 2, street = "2", city = "2", state = "2", country = "2", zip_code = "2" WHERE location_id = 99;



SELECT LAST_INSERT_ID();