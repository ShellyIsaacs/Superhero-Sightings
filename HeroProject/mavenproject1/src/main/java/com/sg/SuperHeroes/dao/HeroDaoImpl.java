/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes.dao;

import com.sg.SuperHeroes.model.Hero;
import com.sg.SuperHeroes.model.Location;
import com.sg.SuperHeroes.model.Organization;
import com.sg.SuperHeroes.model.Power;
import com.sg.SuperHeroes.model.Sighting;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ShellyAnneIsaacs
 */
@Repository
public class HeroDaoImpl implements HeroDao {

    @Autowired
    JdbcTemplate heySql;

    //---------------------SPECIFIC DATA REQUESTS------------------------//
    //
    @Override
    public List<Hero> getHeroesByLocation(int locationId) {
        List<Hero> heroes = heySql.query("SELECT * FROM hero "
                + "INNER JOIN hero_to_sighting "
                + "ON hero.hero_id = hero_to_sighting.hero_id "
                + "INNER JOIN sighting "
                + "ON hero_to_sighting.sighting_id = sighting.sighting_id "
                + "WHERE location_id = ?", new HeroMapper(), locationId);
        Set<Hero> uniqueHeroes = new HashSet<>();
        List<Hero> finalHeroes = new ArrayList<>();
        if (null != heroes) {
            for (Hero hero : heroes) {
                this.setHeroPowers(hero);
                uniqueHeroes.add(hero);
            }
        }

        for (Hero hero : uniqueHeroes) {
            finalHeroes.add(hero);
        }

        return finalHeroes;
    }

    @Override
    public List<Location> getLocationsByHero(int heroId) {
        List<Location> locations = heySql.query("SELECT * FROM location "
                + "INNER JOIN sighting "
                + "ON location.location_id = sighting.location_id "
                + "INNER JOIN hero_to_sighting "
                + "ON sighting.sighting_id = hero_to_sighting.sighting_id "
                + "WHERE hero_id = ?", new LocationMapper(), heroId);
        Set<Location> uniqueLocations = new HashSet<>();
        List<Location> finalLocations = new ArrayList<>();
        if (null != locations) {
            for (Location location : locations) {
                uniqueLocations.add(location);
            }
        }
        for (Location location : uniqueLocations) {
            finalLocations.add(location);
        }

        return finalLocations;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        List<Sighting> sightings = heySql.query("SELECT * FROM sighting "
                + "WHERE sighting_date = ?", new SightingMapper(),
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (null != sightings) {
            for (Sighting sighting : sightings) {
                this.setSightingLocation(sighting);
                this.setSightingHeroes(sighting);
            }
        }

        return sightings;
    }

    @Override
    public List<Hero> getHeroesByOrg(int orgId) {
        List<Hero> heroes = heySql.query("SELECT * FROM hero "
                + "INNER JOIN hero_to_org ON hero.hero_id = hero_to_org.hero_id "
                + "WHERE org_id = ?", new HeroMapper(), orgId);
        Set<Hero> uniqueHeroes = new HashSet<>();
        List<Hero> finalHeroes = new ArrayList<>();
        if (null != heroes) {
            for (Hero hero : heroes) {
                this.setHeroPowers(hero);
                uniqueHeroes.add(hero);
            }
        }
        for (Hero hero : uniqueHeroes) {
            finalHeroes.add(hero);
        }

        return finalHeroes;
    }

    @Override
    public List<Organization> getOrgsByHero(int heroId) {
        List<Organization> orgs = heySql.query("SELECT * FROM org "
                + "INNER JOIN hero_to_org ON org.org_id = hero_to_org.org_id "
                + "WHERE hero_id = ?", new OrgMapper(), heroId);
        Set<Organization> uniqueOrgs = new HashSet<>();
        List<Organization> finalOrgs = new ArrayList<>();

        if (null != orgs) {
            for (Organization org : orgs) {
                this.setOrgHeroes(org);
                this.setOrgLocation(org);
                uniqueOrgs.add(org);
            }
        }
        for (Organization org : uniqueOrgs) {
            finalOrgs.add(org);
        }

        return finalOrgs;
    }
    
    @Override
    public List<Location> getSightingLocations(){
        List<Location> locations = heySql.query(
                "SELECT * FROM location "
                        + "WHERE NOT location_name='Unknown' "
                        + "ORDER BY location_name;",
                new LocationMapper());
        return locations;
    }

    //---------------------GET ALL------------------------//
    //
    @Override
    public List<Hero> getAllHeroes() {
        List<Hero> heroes = heySql.query(
                "SELECT * FROM hero ORDER BY hero_name",
                new HeroMapper());
        if (null != heroes) {
            for (Hero hero : heroes) {
                setHeroPowers(hero);
            }
        }

        return heroes;
    }

    private void setHeroPowers(Hero hero) {
        List<Power> powers = heySql.query("SELECT * FROM `power` "
                + "INNER JOIN hero_to_power "
                + "ON `power`.power_id = hero_to_power.power_id "
                + "WHERE hero_id = ?", new PowerMapper(), hero.getId());
        hero.setPowers(powers);
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = heySql.query(
                "SELECT * FROM sighting ORDER BY sighting_date DESC",
                new SightingMapper());
        if (null != sightings) {
            for (Sighting sighting : sightings) {
                setSightingHeroes(sighting);
                setSightingLocation(sighting);
            }
        }

        return sightings;
    }

    private void setSightingHeroes(Sighting sighting) {
        List<Hero> heroes = heySql.query("SELECT * FROM hero "
                + "INNER JOIN hero_to_sighting "
                + "ON hero.hero_id = hero_to_sighting.hero_id "
                + "WHERE sighting_id = ?", new HeroMapper(), sighting.getId());
        if (null != heroes) {
            for (Hero hero : heroes) {
                this.setHeroPowers(hero);
            }
        }

        sighting.setHeroes(heroes);
    }

    private void setSightingLocation(Sighting sighting) {
        Location location = heySql.queryForObject("SELECT * FROM location "
                + "INNER JOIN sighting "
                + "ON location.location_id = sighting.location_id "
                + "WHERE sighting_id = ?", new LocationMapper(),
                sighting.getId());
        sighting.setLocation(location);
    }

    @Override
    public List<Power> getAllPowers() {
        List<Power> powers = heySql.query(
                "SELECT * FROM `power` ORDER BY power_name",
                new PowerMapper());
        return powers;
    }

    @Override
    public List<Organization> getAllOrgs() {
        List<Organization> orgs = heySql.query(
                "SELECT * FROM org ORDER BY org_name",
                new OrgMapper());
        if (null != orgs) {
            for (Organization org : orgs) {
                setOrgLocation(org);
                setOrgHeroes(org);
            }
        }

        return orgs;
    }

    private void setOrgHeroes(Organization org) {
        List<Hero> heroes = heySql.query("SELECT * FROM hero "
                + "INNER JOIN hero_to_org "
                + "ON hero.hero_id = hero_to_org.hero_id "
                + "WHERE org_id = ?;", new HeroMapper(), org.getId());
        if (null != heroes) {
            for (Hero hero : heroes) {
                this.setHeroPowers(hero);
            }
        }

        org.setHeroes(heroes);
    }

    private void setOrgLocation(Organization org) {
        Location location = heySql.queryForObject("SELECT * FROM location "
                + "INNER JOIN org "
                + "ON location.location_id = org.location_id "
                + "WHERE org_id = ?;", new LocationMapper(), org.getId());
        org.setLocation(location);
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = heySql.query(
                "SELECT * FROM location ORDER BY location_name",
                new LocationMapper());
        return locations;
    }

    //---------------------GET ONE------------------------//
    //
    @Override
    public Hero getOneHero(int heroId) {
        Hero hero = heySql.queryForObject("SELECT * FROM hero WHERE hero_id = ?",
                new HeroMapper(), heroId);
        setHeroPowers(hero);
        return hero;
    }

    @Override
    public Sighting getOneSighting(int sightingId) {
        Sighting sighting = heySql.queryForObject(
                "SELECT * FROM sighting WHERE sighting_id = ?",
                new SightingMapper(), sightingId);
        setSightingLocation(sighting);
        setSightingHeroes(sighting);
        return sighting;
    }

    @Override
    public Power getOnePower(int powerId) {
        Power power = heySql.queryForObject(
                "SELECT * FROM `power` WHERE power_id = ?",
                new PowerMapper(), powerId);
        return power;
    }

    @Override
    public Organization getOneOrg(int orgId) {
        Organization org = heySql.queryForObject(
                "SELECT * FROM org WHERE org_id = ?",
                new OrgMapper(), orgId);
        setOrgHeroes(org);
        setOrgLocation(org);
        return org;
    }

    @Override
    public Location getOneLocation(int locationId) {
        Location location = heySql.queryForObject(
                "SELECT * FROM location WHERE location_id = ?",
                new LocationMapper(), locationId);
        return location;
    }

    //---------------------ADDING DATA TO TABLES------------------------//
    //
    //NOTE THAT NEW ID IS NOT SET UNTIL IT IS INSERTED INTO THE DATABASE
    //
    //manages hero_to_power relationship
    @Transactional
    @Override
    public Hero addHero(Hero hero) {
        heySql.update("INSERT INTO hero(hero_name, hero_description, is_evil) "
                + "VALUES(?, ?, ?)", hero.getName(),
                hero.getDescription(), hero.getIsEvil());

        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        hero.setId(newId);
        List<Power> powers = hero.getPowers();
        if (null != powers) {
            for (Power power : powers) {
                heySql.update("INSERT INTO hero_to_power(hero_id, power_id) "
                        + "VALUES(?, ?)", hero.getId(), power.getId());
            }
        }

        Hero returnedHero = this.getOneHero(hero.getId());
        return returnedHero;
    }

    //manages hero_to_sighting relationship
    @Transactional
    @Override
    public Sighting addSighting(Sighting sighting) {
        Location location = sighting.getLocation();
        heySql.update("INSERT INTO sighting(sighting_date, location_id) "
                + "VALUES(?, ?)", sighting.getDate(),
                location.getId());

        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        sighting.setId(newId);
        List<Hero> heroes = sighting.getHeroes();
        if (null != heroes) {
            for (Hero hero : heroes) {
                heySql.update("INSERT INTO hero_to_sighting(sighting_id, hero_id) "
                        + "VALUES(?, ?)", sighting.getId(), hero.getId());
            }
        }

        Sighting returnedSighting = this.getOneSighting(sighting.getId());
        return returnedSighting;
    }

    @Override
    public Power addPower(Power power) {
        heySql.update("INSERT INTO power(power_name, power_description) "
                + "VALUES(?, ?)", power.getName(),
                power.getDescription());

        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        power.setId(newId);
        Power returnedPower = this.getOnePower(power.getId());
        return returnedPower;
    }

    //manages hero_to_org relationship
    @Transactional
    @Override
    public Organization addOrg(Organization org) {
        Location location = org.getLocation();
        heySql.update("INSERT INTO org(org_name, org_description, location_id) "
                + "VALUES(?, ?, ?)", org.getName(),
                org.getDescription(), location.getId());

        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        org.setId(newId);
        List<Hero> heroes = org.getHeroes();
        if (null != heroes) {
            for (Hero hero : heroes) {
                heySql.update("INSERT INTO hero_to_org(hero_id, org_id) "
                        + "VALUES(?, ?)", hero.getId(), org.getId());
            }
        }

        Organization returnedOrg = this.getOneOrg(org.getId());
        return returnedOrg;
    }

    @Override
    public Location addLocation(Location location) {
        heySql.update("INSERT INTO location (location_name, "
                + "location_description, phone_number, latitude, longitude, "
                + "street, city, state, country, zip_code) VALUES (?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?)", location.getName(),
                location.getDescription(), location.getPhoneNumber(),
                location.getLatitude(), location.getLongitude(),
                location.getStreet(), location.getCity(), location.getState(),
                location.getCountry(), location.getZipCode());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        location.setId(newId);
        Location returnedLocation = this.getOneLocation(location.getId());
        return returnedLocation;
    }

    //---------------------UPDATE------------------------//
    //
    //manages hero_to_power relationship
    @Transactional
    @Override
    public void updateHero(int heroId, Hero hero) {
        heySql.update("UPDATE hero SET hero_name = ?, hero_description = ?, "
                + "is_evil = ? WHERE hero_id = ?", hero.getName(),
                hero.getDescription(), hero.getIsEvil(), heroId);
        List<Power> powers = hero.getPowers();
        heySql.update("DELETE FROM hero_to_power WHERE hero_id = ?", heroId);
        if (null != powers) {
            for (Power power : powers) {
                heySql.update("INSERT INTO hero_to_power(hero_id, power_id) "
                        + "VALUES(?, ?)", heroId, power.getId());
            }
        }

    }

    //manages hero_to_sighting relationship
    @Transactional
    @Override
    public void updateSighting(int sightingId, Sighting sighting) {
        Location location = sighting.getLocation();
        heySql.update("UPDATE sighting SET sighting_date = ?, location_id = ? "
                + "WHERE sighting_id = ?", sighting.getDate(), location.getId(),
                sightingId);
        List<Hero> heroes = sighting.getHeroes();
        heySql.update("DELETE FROM hero_to_sighting WHERE sighting_id = ?",
                sightingId);
        if (null != heroes) {
            for (Hero hero : heroes) {
                heySql.update("INSERT INTO hero_to_sighting(sighting_id, hero_id) "
                        + "VALUES(?, ?)", sighting.getId(), hero.getId());
            }
        }
    }

    @Override
    public void updatePower(int powerId, Power power) {
        heySql.update("UPDATE power SET power_name = ?, power_description = ? "
                + "WHERE power_id = ?", power.getName(), power.getDescription(),
                powerId);
    }

    //manages hero_to_org relationship
    @Transactional
    @Override
    public void updateOrg(int orgId, Organization org) {
        Location location = org.getLocation();
        heySql.update("UPDATE org SET org_name = ?, org_description = ?, "
                + "location_id = ? WHERE org_id = ?", org.getName(),
                org.getDescription(), location.getId(), orgId);
        List<Hero> heroes = org.getHeroes();
        heySql.update("DELETE FROM hero_to_org WHERE org_id = ?", orgId);
        if (null != heroes) {
            for (Hero hero : heroes) {
                heySql.update("INSERT INTO hero_to_org(hero_id, org_id) "
                        + "VALUES(?, ?)", hero.getId(), org.getId());
            }
        }

    }

    @Override
    public void updateLocation(int locationId, Location location) {
        heySql.update("UPDATE location SET location_name = ?, "
                + "location_description = ?, phone_number = ?, latitude = ?, "
                + "longitude = ?, street = ?, city = ?, state = ?, country = ?, "
                + "zip_code = ? WHERE location_id = ?", location.getName(),
                location.getDescription(), location.getPhoneNumber(),
                location.getLatitude(), location.getLongitude(),
                location.getStreet(), location.getCity(), location.getState(),
                location.getCountry(), location.getZipCode(), locationId);
    }

    //---------------------DELETE------------------------//
    //
    //manages hero_to_power relationship
    @Transactional
    @Override
    public void deleteHero(int heroId) {
        
        List<Sighting> sightings = this.getSightingsByHeroId(heroId);
        heySql.update("DELETE FROM hero_to_sighting WHERE hero_id = ?", heroId);
        for (Sighting sighting : sightings) {
            if (null == sighting.getHeroes()){
                heySql.update("DELETE FROM hero_to_sighting WHERE sighting_id = ?",
                        sighting.getId());
                heySql.update("DELETE FROM sighting WHERE sighting_id = ?", 
                        sighting.getId());
            }
        }
        heySql.update("DELETE FROM hero_to_power WHERE hero_id = ?", heroId);
        heySql.update("DELETE FROM hero_to_org WHERE hero_id = ?", heroId);
        heySql.update("DELETE FROM hero WHERE hero_id = ?", heroId);
    }

    private List<Sighting> getSightingsByHeroId(int heroId) {
        List<Sighting> sightings = heySql.query("SELECT * FROM sighting "
                + "INNER JOIN hero_to_sighting "
                + "ON sighting.sighting_id = hero_to_sighting.sighting_id "
                + "INNER JOIN hero "
                + "ON hero_to_sighting.hero_id = hero.hero_id "
                + "WHERE hero.hero_id = ?", new SightingMapper(), heroId);
        return sightings;
    }

    //manages hero_to_sighting relationship
    @Transactional
    @Override
    public void deleteSighting(int sightingId) {
        heySql.update("DELETE FROM hero_to_sighting WHERE sighting_id = ?",
                sightingId);
        heySql.update("DELETE FROM sighting WHERE sighting_id = ?", sightingId);
    }

    @Transactional
    @Override
    public void deletePower(int powerId) {
        heySql.update("DELETE FROM hero_to_power WHERE power_id = ?", powerId);
        heySql.update("DELETE FROM power WHERE power_id = ?", powerId);
    }

    //manages hero_to_org relationship
    @Transactional
    @Override
    public void deleteOrg(int orgId) {
        heySql.update("DELETE FROM hero_to_org WHERE org_id = ?", orgId);
        heySql.update("DELETE FROM org WHERE org_id = ?", orgId);
    }

    //if location is deleted, update org location to unspecified, delete sighting
    @Transactional
    @Override
    public void deleteLocation(int locationId) {
        //do not allow to delete unknown location
        if (locationId != 1) {
            List<Sighting> sightings = this.getSightingsByLocationId(locationId);
            if (null != sightings) {
                for (Sighting sighting : sightings) {
                    int sightingId = sighting.getId();
                    this.deleteSighting(sightingId);
                }
            }

            List<Organization> orgs = this.getOrgsByLocationId(locationId);
            if (null != orgs) {
                for (Organization org : orgs) {
                    //set unknown location from database
                    org.setLocation(this.getUnknownLocation());
                    this.updateOrg(org.getId(), org);
                }
            }
            heySql.update("DELETE FROM location WHERE location_id = ?", locationId);
        }
    }

    @Override
    public Location getUnknownLocation() {
        Location unknown = heySql.queryForObject("SELECT * FROM location "
                + "WHERE location_name = 'Unknown'", new LocationMapper());
        return unknown;
    }

    private List<Organization> getOrgsByLocationId(int locationId) {
        List<Organization> orgs = heySql.query("SELECT * FROM org "
                + "WHERE location_id = ?", new OrgMapper(), locationId);
        return orgs;
    }

    private List<Sighting> getSightingsByLocationId(int locationId) {
        List<Sighting> sightings = heySql.query("SELECT * FROM sighting "
                + "WHERE location_id = ?", new SightingMapper(), locationId);
        return sightings;
    }

    //---------------------MAPPERS------------------------//
    //
    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setId(rs.getInt("power_id"));
            power.setName(rs.getString("power_name"));
            power.setDescription(rs.getString("power_description"));
            return power;
        }
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("hero_id"));
            hero.setName(rs.getString("hero_name"));
            hero.setDescription(rs.getString("hero_description"));
            hero.setIsEvil(rs.getBoolean("is_evil"));
            return hero;
        }
    }

    public static final class OrgMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("org_id"));
            org.setName(rs.getString("org_name"));
            org.setDescription(rs.getString("org_description"));
            return org;
        }
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("location_id"));
            location.setName(rs.getString("location_name"));
            location.setDescription(rs.getString("location_description"));
            location.setPhoneNumber(rs.getString("phone_number"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));
            location.setStreet(rs.getString("street"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setCountry(rs.getString("country"));
            location.setZipCode(rs.getString("zip_code"));
            return location;
        }
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sighting_id"));
            String unformattedDate = rs.getString("sighting_date");
            LocalDateTime date = LocalDateTime.parse(
                    unformattedDate, DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm:ss"));
            sighting.setDate(date);
            return sighting;
        }
    }
}
