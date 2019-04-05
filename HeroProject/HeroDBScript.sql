DROP DATABASE IF EXISTS Superheroes;
CREATE DATABASE Superheroes;
USE Superheroes;

CREATE TABLE `power` (
power_id int(11) PRIMARY KEY AUTO_INCREMENT,
power_name varchar(200) NOT NULL,
power_description varchar(500) NOT NULL
);

CREATE TABLE hero (
hero_id int(11) PRIMARY KEY AUTO_INCREMENT,
hero_name varchar(200) NOT NULL,
hero_description varchar(500) NOT NULL,
is_evil boolean NOT NULL
);

CREATE TABLE hero_to_power (
hero_id int(11) NOT NULL,
power_id int(11) NOT NULL,
PRIMARY KEY (hero_id, power_id)
);

CREATE TABLE location (
location_id int(11) PRIMARY KEY AUTO_INCREMENT,
location_name varchar(200) NOT NULL,
location_description varchar(500) NOT NULL,
phone_number varchar(35) NOT NULL,
latitude DECIMAL(11,8) NOT NULL,
longitude DECIMAL(11,8) NOT NULL,
street varchar(250) NOT NULL,
city varchar(100) NOT NULL,
state varchar(100) NOT NULL,
country varchar(100) NOT NULL,
zip_code varchar(20) NOT NULL
);

CREATE TABLE org (
org_id int(11) PRIMARY KEY AUTO_INCREMENT,
org_name varchar(200) NOT NULL,
org_description varchar(500) NOT NULL,
location_id int(11) NOT NULL
);

CREATE TABLE hero_to_org (
hero_id int(11) NOT NULL,
org_id int(11) NOT NULL,
PRIMARY KEY (hero_id, org_id)
);

CREATE TABLE sighting (
sighting_id int(11) PRIMARY KEY AUTO_INCREMENT,
sighting_date datetime NOT NULL,
location_id int(11) NOT NULL
);

CREATE TABLE hero_to_sighting (
sighting_id int(11) NOT NULL,
hero_id int(11) NOT NULL,
PRIMARY KEY (hero_id, sighting_id)
);

ALTER TABLE hero_to_sighting
ADD CONSTRAINT hero_to_sighting_sighting_id FOREIGN KEY (sighting_id) REFERENCES sighting (sighting_id);

ALTER TABLE hero_to_sighting
ADD CONSTRAINT hero_to_sighting_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE hero_to_org
ADD CONSTRAINT hero_to_org_org_id FOREIGN KEY (org_id) REFERENCES org (org_id);

ALTER TABLE hero_to_org
ADD CONSTRAINT hero_to_org_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE hero_to_power
ADD CONSTRAINT hero_to_power_power_id FOREIGN KEY (power_id) REFERENCES `power` (power_id);

ALTER TABLE hero_to_power
ADD CONSTRAINT hero_to_power_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE sighting
ADD CONSTRAINT sighting_location_id FOREIGN KEY (location_id) REFERENCES location (location_id);

ALTER TABLE org
ADD CONSTRAINT org_location_id FOREIGN KEY (location_id) REFERENCES location (location_id);

DROP DATABASE IF EXISTS SuperheroesTest;
CREATE DATABASE SuperheroesTest;
USE SuperheroesTest;

CREATE TABLE `power` (
power_id int(11) PRIMARY KEY AUTO_INCREMENT,
power_name varchar(200) NOT NULL,
power_description varchar(500) NOT NULL
);

CREATE TABLE hero (
hero_id int(11) PRIMARY KEY AUTO_INCREMENT,
hero_name varchar(200) NOT NULL,
hero_description varchar(500) NOT NULL,
is_evil boolean NOT NULL
);

CREATE TABLE hero_to_power (
hero_id int(11) NOT NULL,
power_id int(11) NOT NULL,
PRIMARY KEY (hero_id, power_id)
);

CREATE TABLE location (
location_id int(11) PRIMARY KEY AUTO_INCREMENT,
location_name varchar(200) NOT NULL,
location_description varchar(500) NOT NULL,
phone_number varchar(35) NOT NULL,
latitude DECIMAL(11,8) NOT NULL,
longitude DECIMAL(11,8) NOT NULL,
street varchar(250) NOT NULL,
city varchar(100) NOT NULL,
state varchar(100) NOT NULL,
country varchar(100) NOT NULL,
zip_code varchar(20) NOT NULL
);

CREATE TABLE org (
org_id int(11) PRIMARY KEY AUTO_INCREMENT,
org_name varchar(200) NOT NULL,
org_description varchar(500) NOT NULL,
location_id int(11) NOT NULL
);

CREATE TABLE hero_to_org (
hero_id int(11) NOT NULL,
org_id int(11) NOT NULL,
PRIMARY KEY (hero_id, org_id)
);

CREATE TABLE sighting (
sighting_id int(11) PRIMARY KEY AUTO_INCREMENT,
sighting_date datetime NOT NULL,
location_id int(11) NOT NULL
);

CREATE TABLE hero_to_sighting (
sighting_id int(11) NOT NULL,
hero_id int(11) NOT NULL,
PRIMARY KEY (hero_id, sighting_id)
);

ALTER TABLE hero_to_sighting
ADD CONSTRAINT hero_to_sighting_sighting_id FOREIGN KEY (sighting_id) REFERENCES sighting (sighting_id);

ALTER TABLE hero_to_sighting
ADD CONSTRAINT hero_to_sighting_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE hero_to_org
ADD CONSTRAINT hero_to_org_org_id FOREIGN KEY (org_id) REFERENCES org (org_id);

ALTER TABLE hero_to_org
ADD CONSTRAINT hero_to_org_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE hero_to_power
ADD CONSTRAINT hero_to_power_power_id FOREIGN KEY (power_id) REFERENCES `power` (power_id);

ALTER TABLE hero_to_power
ADD CONSTRAINT hero_to_power_hero_id FOREIGN KEY (hero_id) REFERENCES hero (hero_id);

ALTER TABLE sighting
ADD CONSTRAINT sighting_location_id FOREIGN KEY (location_id) REFERENCES location (location_id);

ALTER TABLE org
ADD CONSTRAINT org_location_id FOREIGN KEY (location_id) REFERENCES location (location_id);



INSERT INTO `Superheroes`.`location`
(`location_id`,
`location_name`,
`location_description`,
`phone_number`,
`latitude`,
`longitude`,
`street`,
`city`,
`state`,
`country`,
`zip_code`)
VALUES
(5, "Starbucks", "A coffee shop", "(919)595-7514", 38.328732, -85.764771, 
"12975 Shelbyville Rd", "Louisville", "KY", "USA", "40234"), 
(2, "Trader Joe's", "A grocery store", "(502)895-1361", 38.328732, -85.764771, 
"4600 Shelbyville Rd Ste 111", "Louisville", "KY", "USA", "40207"), 
(3, "Princess Elisabeth Station", "Science station in Antarctica", "+61 406 302 797", -71.9499, 23.3470, 
"Utsteinen Nunatak", "Queen Maud Land", "Antarctica", "Belgium", "NA"), 
(4, "The Software Guild", "Where super cool coders gather", "(855) 599-9584", 38.2552, -85.7587, 
"200 S 5th St Suite A-10", "Louisville", "KY", "USA", "40202"), 
(1, "Unknown", "", "", 999, 999, 
"", "", "", "", "")
;

INSERT INTO `Superheroes`.`org`
(`org_id`,
`org_name`,
`org_description`,
`location_id`)
VALUES
(1, "League of Coolest Heroes", "Only the COOLEST heroes are part of this league", 4), 
(2, "League of Coldest Heroes", "Heroes who like being cold", 3), 
(3, "The Friendly Friends Foundation", "The heroes who always make your day better", 2), 
(4, "The Barista Baddies", "Villains who make a great capuccino", 5)
;

INSERT INTO `Superheroes`.`hero`
(`hero_id`,
`hero_name`,
`hero_description`,
`is_evil`)
VALUES
(1, "Johnny", "Giant beard, booming voice", 0),
(2, "Bradley", "Musician, Nordic appearance", 0),
(3, "Awa", "Gambian gal with great hair", 0),
(4, "Meredith", "Ballerina, never looses her cool", 0),
(5, "Chas", "Devious handlebar mustache", 1),
(6, "Karen", "Deceptively bubbly demeanor", 1),
(7, "Simon", "Dark lank hair that hides his shifty eyes", 1),
(8, "Jude", "A glare that can kill", 1),
(9, "Ice man", "Turns into ice", 0),
(10, "Superman", "Loner who likes the ridiculous cold", 0),
(11, "Frozone", "Makes ice to slide around on", 0),
(12, "Austyn", "Comes with llama sidekick", 0),
(13, "Phil", "Comes with dog sidekick", 0),
(14, "Jacob", "Comes with Cthulu sidekick", 0),
(15, "Ashley", "Comes with mini jersey cow sidekick", 0),
(16, "Andrew", "Comes with racoon sidekick", 0),
(17, "Matt", "Comes with cat sidekick", 0),
(18, "Shelly", "Comes with squirrel sidekick", 0)
;

INSERT INTO `Superheroes`.`sighting`
(`sighting_id`,
`sighting_date`,
`location_id`)
VALUES
(1, "2019-01-01 12:00:00", 4), 
(2, "2019-01-05 16:57:00", 3), 
(3, "2019-01-10 12:29:00", 4), 
(4, "2019-01-12 16:00:00", 4), 
(5, "2019-01-17 12:12:00", 5), 
(6, "2019-02-01 15:11:00", 5), 
(7, "2019-02-01 09:24:00", 4), 
(8, "2019-02-05 10:00:00", 2), 
(9, "2019-02-10 14:55:00", 2), 
(10, "2019-02-15 07:49:00", 4), 
(11, "2019-02-15 23:46:00", 3), 
(12, "2019-02-16 22:28:00", 3), 
(13, "2019-03-05 17:20:00", 5), 
(14, "2019-03-05 09:45:00", 4), 
(15, "2019-03-18 10:35:00", 4), 
(16, "2019-03-19 11:17:00", 5), 
(17, "2019-03-20 12:13:00", 2), 
(18, "2019-03-25 04:14:00", 2), 
(19, "2019-03-27 15:10:00", 2), 
(20, "2019-03-27 14:05:00", 4)
;

INSERT INTO `Superheroes`.`hero_to_sighting`
(`sighting_id`,
`hero_id`)
VALUES
(1, 12),
(2, 10),
(3, 14),
(4, 15),
(5, 5),
(6, 6),
(7, 16),
(8, 1),
(9, 1),
(10, 13),
(11, 11),
(12, 12),
(13, 7),
(14, 17),
(15, 18),
(16, 8),
(17, 2),
(18, 3),
(19, 4),
(20, 12),
(1, 17),
(2, 11),
(3, 16),
(4, 14),
(5, 6),
(6, 5),
(11, 10),
(13, 8),
(15, 13),
(17, 1),
(19, 1)
;

INSERT INTO `Superheroes`.`power`
(`power_id`,
`power_name`,
`power_description`)
VALUES
(1, "Flying", "Goes up in the air without even trying"), 
(2, "Vast Riches", "Having stacks on stacks on stacks on stacks of cash"), 
(3, "Superhuman Strength", "Able to lift pretty heavy things"), 
(4, "Impenetrable Skin", "Nothing gets through this"), 
(5, "Laser Eyes", "Shoots lasers from their eyes!!!!!!!"),
(6, "Ice Powers", "Can ice people and things"),
(7, "Genius", "Really really really smart, like Einstein-level smarts")
;

INSERT INTO `Superheroes`.`hero_to_power`
(`hero_id`,
`power_id`)
VALUES
(5, 4),
(6, 6),
(7, 5),
(8, 5),
(9, 6),
(10, 1),
(10, 3),
(11, 6),
(1, 3),
(1, 4),
(2, 1),
(2, 5),
(3, 3),
(3, 6),
(4, 1),
(4, 7),
(13, 1),
(13, 7),
(14, 3),
(14, 7),
(15, 4),
(15, 7),
(16, 5),
(16, 7),
(17, 6),
(17, 7),
(18, 2), 
(18, 7),
(12, 1),
(12, 3),
(12, 4),
(12, 5),
(12, 7)
;

INSERT INTO `Superheroes`.`hero_to_org`
(`hero_id`,
`org_id`)
VALUES
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 4),
(6, 4),
(7, 4),
(8, 4),
(9, 2),
(10, 2),
(11, 2),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1);

