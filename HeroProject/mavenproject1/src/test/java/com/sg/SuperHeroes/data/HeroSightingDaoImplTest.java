/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes.data;

import com.sg.SuperHeroes.dao.HeroDaoImpl;
import com.sg.SuperHeroes.model.Hero;
import com.sg.SuperHeroes.model.Location;
import com.sg.SuperHeroes.model.Organization;
import com.sg.SuperHeroes.model.Power;
import com.sg.SuperHeroes.model.Sighting;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ShellyAnneIsaacs
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:testApplication.properties")
public class HeroSightingDaoImplTest {

    @Autowired
    HeroDaoImpl dao;

    @Autowired
    JdbcTemplate heySql;

    private Hero hero;
    private Hero hero2;
    private Location location;
    private Location location2;
    private Organization org;
    private Organization org2;
    private Sighting sighting;
    private Sighting sighting2;
    private Power power;
    private Power power2;

    public HeroSightingDaoImplTest() {
        Location aLocation = new Location();
        aLocation.setName("Stark Industries");
        aLocation.setDescription("Ironman's place");
        aLocation.setCity("Somewhere");
        aLocation.setCountry("USA");
        aLocation.setPhoneNumber("555-555-5555");
        aLocation.setStreet("123 Swanky Ave.");
        aLocation.setZipCode("99999");
        aLocation.setState("California");
        BigDecimal longitude = new BigDecimal("34.0259");
        aLocation.setLongitude(longitude);
        BigDecimal latitude = new BigDecimal("-118.731316");
        aLocation.setLatitude(latitude);
        this.location = aLocation;
        Location bLocation = new Location();
        bLocation.setName("Stark Industries");
        bLocation.setDescription("Ironman's place");
        bLocation.setCity("Somewhere");
        bLocation.setCountry("USA");
        bLocation.setPhoneNumber("555-555-5555");
        bLocation.setStreet("123 Swanky Ave.");
        bLocation.setZipCode("99999");
        bLocation.setState("California");
        BigDecimal longitude2 = new BigDecimal("34.0259");
        bLocation.setLongitude(longitude2);
        BigDecimal latitude2 = new BigDecimal("-110.731316");
        bLocation.setLatitude(latitude2);
        this.location2 = bLocation;
        Power aPower = new Power();
        aPower.setName("flying");
        aPower.setDescription("goes up in the air and such");
        this.power = aPower;
        Power bPower = new Power();
        bPower.setName("power walking");
        bPower.setDescription("walks faster than other people");
        this.power2 = bPower;
        Hero aHero = new Hero();
        aHero.setName("Ironman");
        aHero.setDescription("Metal robot suit, lots of money");
        aHero.setIsEvil(false);
        this.hero = aHero;
        Hero bHero = new Hero();
        bHero.setName("Speedy");
        bHero.setDescription("walks away from unpleasant situations, quickly");
        bHero.setIsEvil(true);
        this.hero2 = bHero;
        Organization anOrg = new Organization();
        anOrg.setName("Stark Industries");
        anOrg.setDescription("Tony's company");
        this.org = anOrg;
        Organization bOrg = new Organization();
        bOrg.setName("PowerWalkers Club");
        bOrg.setDescription("Where the speedy villains gather");
        this.org2 = bOrg;
        Sighting aSighting = new Sighting();
        aSighting.setDate(LocalDateTime.now());
        this.sighting = aSighting;
        Sighting bSighting = new Sighting();
        bSighting.setDate(LocalDateTime.now().minusDays(1));
        this.sighting2 = bSighting;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        heySql.update("DELETE FROM hero_to_power");
        heySql.update("DELETE FROM hero_to_sighting");
        heySql.update("DELETE FROM hero_to_org");
        heySql.update("DELETE FROM hero");
        heySql.update("DELETE FROM power");
        heySql.update("DELETE FROM org");
        heySql.update("DELETE FROM sighting");
        heySql.update("DELETE FROM location");

    }

    @After
    public void tearDown() {
    }

    //--------------------------ADD GET TESTS---------------------------//
    @Test
    public void addGetLocationsTest() {

        //act
        Location addedLocation = this.dao.addLocation(location);
        Location returnedLocation = this.dao.getOneLocation(addedLocation.getId());
        //assert
        Assert.assertEquals("Location added and returned should be same.",
                addedLocation, returnedLocation);
    }

    @Test
    public void addGetPowerTest() {

        //act
        Power addedPower = this.dao.addPower(power);
        Power returnedPower = this.dao.getOnePower(addedPower.getId());
        //assert
        Assert.assertEquals("Power added should match returned one",
                addedPower, returnedPower);
    }

    @Test
    public void addGetHeroTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        //act
        Hero addedHero = this.dao.addHero(hero);
        Hero returnedHero = this.dao.getOneHero(addedHero.getId());
        //assert
        Assert.assertEquals("Hero added should match returned one",
                addedHero, returnedHero);
    }

    @Test
    public void addGetOrgTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        org.setLocation(location);
        org.setHeroes(heroes);
        //act
        Organization addedOrg = this.dao.addOrg(org);
        Organization returnedOrg = this.dao.getOneOrg(addedOrg.getId());
        //assert
        Assert.assertEquals("Org added should match returned one",
                addedOrg, returnedOrg);
    }

    @Test
    public void addGetSightingTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        //act
        Sighting addedSighting = this.dao.addSighting(sighting);
        Sighting returnedSighting = this.dao.getOneSighting(addedSighting.getId());
        //assert
        Assert.assertEquals("Sighting should match", addedSighting, returnedSighting);
    }

    //--------------------------GET ALL TESTS---------------------------//
    @Test
    public void getAllLocationsTest() {
        //arrange
        Location addedLocation = this.dao.addLocation(location);
        Location addedLocation2 = this.dao.addLocation(location2);
        //act
        List<Location> locations = this.dao.getAllLocations();
        //assert
        Assert.assertFalse(locations.isEmpty());
        Assert.assertEquals(2, locations.size());
        Assert.assertTrue(locations.contains(addedLocation));
        Assert.assertTrue(locations.contains(addedLocation2));
    }

    @Test
    public void getAllPowersTest() {
        //arrange
        Power addedPower = this.dao.addPower(power);
        Power addedPower2 = this.dao.addPower(power2);
        //act
        List<Power> powers = this.dao.getAllPowers();
        //assert
        Assert.assertFalse(powers.isEmpty());
        Assert.assertEquals(2, powers.size());
        Assert.assertTrue(powers.contains(addedPower));
        Assert.assertTrue(powers.contains(addedPower2));
    }

    @Test
    public void getAllHeroesTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        hero2.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        Hero addedHero2 = this.dao.addHero(hero2);
        //act
        List<Hero> heroes = this.dao.getAllHeroes();
        //assert
        Assert.assertFalse(heroes.isEmpty());
        Assert.assertEquals(2, heroes.size());
        Assert.assertTrue(heroes.contains(addedHero));
        Assert.assertTrue(heroes.contains(addedHero2));
    }

    @Test
    public void getAllOrgsTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        org.setLocation(location);
        org.setHeroes(heroes);
        Organization addedOrg = this.dao.addOrg(org);
        org2.setLocation(location);
        org2.setHeroes(heroes);
        Organization addedOrg2 = this.dao.addOrg(org2);
        //act
        List<Organization> orgs = this.dao.getAllOrgs();
        //assert
        Assert.assertFalse(orgs.isEmpty());
        Assert.assertEquals(2, orgs.size());
        Assert.assertTrue(orgs.contains(addedOrg));
        Assert.assertTrue(orgs.contains(addedOrg2));
    }

    @Test
    public void getAllSightingsTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        sighting2.setHeroes(heroes);
        sighting2.setLocation(location);
        Sighting addedSighting = this.dao.addSighting(sighting);
        Sighting addedSighting2 = this.dao.addSighting(sighting2);
        //act
        List<Sighting> sightings = this.dao.getAllSightings();
        //assert
        Assert.assertFalse(sightings.isEmpty());
        Assert.assertEquals(2, sightings.size());
        Assert.assertTrue(sightings.contains(addedSighting));
        Assert.assertTrue(sightings.contains(addedSighting2));
    }

    //--------------------------DELETE TESTS---------------------------//
    @Test
    public void deleteLocationTest() {
        //arrange
        Location addedLocation = this.dao.addLocation(location);
        Location addedLocation2 = this.dao.addLocation(location2);
        //act
        List<Location> addedLocations = this.dao.getAllLocations();
        this.dao.deleteLocation(addedLocation.getId());
        List<Location> returnedLocations = this.dao.getAllLocations();
        //assert
        Assert.assertEquals(2, addedLocations.size());
        Assert.assertEquals(1, returnedLocations.size());
        Assert.assertFalse(returnedLocations.contains(addedLocation));
        Assert.assertTrue(returnedLocations.contains(addedLocation2));
    }

    @Test
    public void deletePowerTest() {
        //arrange
        Power addedPower = this.dao.addPower(power);
        Power addedPower2 = this.dao.addPower(power2);
        //act
        List<Power> addedPowers = this.dao.getAllPowers();
        this.dao.deletePower(addedPower.getId());
        List<Power> returnedPowers = this.dao.getAllPowers();
        //assert
        Assert.assertEquals(2, addedPowers.size());
        Assert.assertEquals(1, returnedPowers.size());
        Assert.assertFalse(returnedPowers.contains(addedPower));
        Assert.assertTrue(returnedPowers.contains(addedPower2));
    }

    @Test
    public void deleteHeroTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        hero2.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        Hero addedHero2 = this.dao.addHero(hero2);
        //act
        List<Hero> addedHeroes = this.dao.getAllHeroes();
        this.dao.deleteHero(addedHero.getId());
        List<Hero> returnedHeroes = this.dao.getAllHeroes();
        //assert
        Assert.assertEquals(2, addedHeroes.size());
        Assert.assertEquals(1, returnedHeroes.size());
        Assert.assertFalse(returnedHeroes.contains(addedHero));
        Assert.assertTrue(returnedHeroes.contains(addedHero2));
    }

    @Test
    public void deleteOrgTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        org.setLocation(location);
        org.setHeroes(heroes);
        org2.setLocation(location);
        org2.setHeroes(heroes);
        Organization addedOrg = this.dao.addOrg(org);
        Organization addedOrg2 = this.dao.addOrg(org2);
        //act
        List<Organization> addedOrgs = this.dao.getAllOrgs();
        this.dao.deleteOrg(addedOrg.getId());
        List<Organization> returnedOrgs = this.dao.getAllOrgs();
        //assert
        Assert.assertEquals(2, addedOrgs.size());
        Assert.assertEquals(1, returnedOrgs.size());
        Assert.assertFalse(returnedOrgs.contains(addedOrg));
        Assert.assertTrue(returnedOrgs.contains(addedOrg2));
    }

    @Test
    public void deleteSightingTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        sighting2.setHeroes(heroes);
        sighting2.setLocation(location);
        Sighting addedSighting = this.dao.addSighting(sighting);
        Sighting addedSighting2 = this.dao.addSighting(sighting2);
        //act
        List<Sighting> addedSightings = this.dao.getAllSightings();
        this.dao.deleteSighting(addedSighting.getId());
        List<Sighting> returnedSightings = this.dao.getAllSightings();
        //assert
        Assert.assertEquals(2, addedSightings.size());
        Assert.assertEquals(1, returnedSightings.size());
        Assert.assertFalse(returnedSightings.contains(addedSighting));
        Assert.assertTrue(returnedSightings.contains(addedSighting2));
    }

    //--------------------------UPDATE TESTS---------------------------//
    @Test
    public void updateLocationTest() {
        //arrange
        Location addedLocation = this.dao.addLocation(location);
        int id = addedLocation.getId();
        location2.setId(id);
        //act
        this.dao.updateLocation(id, location2);
        Location returnedLocation = this.dao.getOneLocation(id);
        List<Location> returnedLocations = this.dao.getAllLocations();
        //assert
        Assert.assertEquals(location2, returnedLocation);
        Assert.assertFalse(returnedLocations.contains(addedLocation));
        Assert.assertTrue(returnedLocations.contains(returnedLocation));
    }

    @Test
    public void updatePowerTest() {
        //arrange
        Power addedPower = this.dao.addPower(power);
        int id = addedPower.getId();
        power2.setId(id);
        //act
        this.dao.updatePower(id, power2);
        Power returnedPower = this.dao.getOnePower(id);
        List<Power> returnedPowers = this.dao.getAllPowers();
        //assert
        Assert.assertEquals(power2, returnedPower);
        Assert.assertFalse(returnedPowers.contains(addedPower));
        Assert.assertTrue(returnedPowers.contains(returnedPower));
    }

    @Test
    public void updateHeroTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        int id = addedHero.getId();
        hero2.setPowers(powers);
        hero2.setId(id);
        //act
        this.dao.updateHero(id, hero2);
        Hero returnedHero = this.dao.getOneHero(id);
        List<Hero> returnedHeroes = this.dao.getAllHeroes();
        //assert
        Assert.assertEquals(hero2, returnedHero);
        Assert.assertFalse(returnedHeroes.contains(addedHero));
        Assert.assertTrue(returnedHeroes.contains(returnedHero));
    }

    @Test
    public void updateOrgTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        Location addedLocation = this.dao.addLocation(location);
        org.setHeroes(heroes);
        org.setLocation(addedLocation);
        org2.setHeroes(heroes);
        org2.setLocation(addedLocation);
        Organization addedOrg = this.dao.addOrg(org);
        int id = addedOrg.getId();
        org2.setId(id);
        //act
        this.dao.updateOrg(id, org2);
        Organization returnedOrg = this.dao.getOneOrg(id);
        List<Organization> returnedOrgs = this.dao.getAllOrgs();
        //assert
        Assert.assertEquals(org2, returnedOrg);
        Assert.assertFalse(returnedOrgs.contains(addedOrg));
        Assert.assertTrue(returnedOrgs.contains(returnedOrg));
    }

    @Test
    public void updateSightingTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        Location addedLocation = this.dao.addLocation(location);
        sighting.setHeroes(heroes);
        sighting.setLocation(addedLocation);
        sighting2.setHeroes(heroes);
        sighting2.setLocation(addedLocation);
        Sighting addedSighting = this.dao.addSighting(sighting);
        int id = addedSighting.getId();
        sighting2.setId(id);
        //act
        this.dao.updateSighting(id, sighting2);
        Sighting returnedSighting = this.dao.getOneSighting(id);
        List<Sighting> returnedSightings = this.dao.getAllSightings();
        //assert
        Assert.assertEquals(sighting2, returnedSighting);
        Assert.assertFalse(returnedSightings.contains(addedSighting));
        Assert.assertTrue(returnedSightings.contains(returnedSighting));
    }

    //--------------------------SPECIAL REQUEST TESTS---------------------------//
    @Test
    public void getHeroesByLocationTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        Location addedLocation = this.dao.addLocation(location);
        sighting.setHeroes(heroes);
        sighting.setLocation(addedLocation);
        this.dao.addSighting(sighting);
        //act
        List<Hero> returnedHeroes = this.dao.getHeroesByLocation(addedLocation.getId());
        //assert
        Assert.assertFalse(returnedHeroes.isEmpty());
        Assert.assertEquals(1, returnedHeroes.size());
        Assert.assertTrue(returnedHeroes.contains(addedHero));
        Assert.assertFalse(returnedHeroes.contains(hero2));
    }

    @Test
    public void getLocationsByHeroTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        Location addedLocation = this.dao.addLocation(location);
        Location addedLocation2 = this.dao.addLocation(location2);
        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        sighting2.setHeroes(heroes);
        sighting2.setLocation(location2);
        this.dao.addSighting(sighting);
        this.dao.addSighting(sighting2);
        //act
        List<Location> returnedLocations = this.dao.getLocationsByHero(addedHero.getId());
        //assert
        Assert.assertFalse(returnedLocations.isEmpty());
        Assert.assertEquals(2, returnedLocations.size());
        Assert.assertTrue(returnedLocations.contains(addedLocation));
        Assert.assertTrue(returnedLocations.contains(addedLocation2));
    }

    @Test
    public void getSightingsByDateTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        Location addedLocation = this.dao.addLocation(location);
        Location addedLocation2 = this.dao.addLocation(location2);
        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        sighting.setDate(LocalDateTime.now());
        sighting2.setDate(LocalDateTime.now().minusWeeks(1));
        sighting2.setHeroes(heroes);
        sighting2.setLocation(location2);
        this.dao.addSighting(sighting);
        this.dao.addSighting(sighting2);
        //act
        List<Sighting> returnedSightings = this.dao.getSightingsByDate(
                LocalDateTime.now());
        List<Sighting> returnedSightings2 = this.dao.getSightingsByDate(
                LocalDateTime.now().minusWeeks(1));
        List<Sighting> returnedSightings3 = this.dao.getSightingsByDate(
                LocalDateTime.now().minusMonths(1));
        //assert
        Assert.assertFalse(returnedSightings.isEmpty());
        Assert.assertFalse(returnedSightings2.isEmpty());
        Assert.assertTrue(returnedSightings3.isEmpty());
        Assert.assertEquals(1, returnedSightings.size());
        Assert.assertEquals(1, returnedSightings2.size());
        Assert.assertTrue(returnedSightings.contains(sighting));
        Assert.assertTrue(returnedSightings2.contains(sighting2));
    }

    @Test
    public void getHeroesByOrgTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        this.dao.addLocation(location);
        org.setHeroes(heroes);
        org.setLocation(location);
        Organization addedOrg = this.dao.addOrg(org);
        //act
        List<Hero> returnedHeroes = this.dao.getHeroesByOrg(addedOrg.getId());
        //assert
        Assert.assertFalse(returnedHeroes.isEmpty());
        Assert.assertEquals(1, returnedHeroes.size());
        Assert.assertTrue(returnedHeroes.contains(addedHero));
        Assert.assertFalse(returnedHeroes.contains(hero2));
    }

    @Test
    public void getOrgsByHeroTest() {
        //arrange
        this.dao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        hero.setPowers(powers);
        hero2.setPowers(powers);
        Hero addedHero = this.dao.addHero(hero);
        Hero addedHero2 = this.dao.addHero(hero2);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        List<Hero> heroes2 = new ArrayList<>();
        heroes2.add(hero);
        heroes2.add(hero2);
        Location addedLocation = this.dao.addLocation(location);
        Location addedLocation2 = this.dao.addLocation(location2);
        org.setHeroes(heroes);
        org.setLocation(location);
        org2.setHeroes(heroes2);
        org2.setLocation(location2);
        Organization addedOrg = this.dao.addOrg(org);
        Organization addedOrg2 = this.dao.addOrg(org2);
        //act
        List<Organization> returnedOrgs = this.dao.getOrgsByHero(addedHero.getId());
        List<Organization> returnedOrgs2 = this.dao.getOrgsByHero(addedHero2.getId());
        //assert
        Assert.assertNotNull(returnedOrgs);
        Assert.assertNotNull(returnedOrgs2);
        Assert.assertEquals(2, returnedOrgs.size());
        Assert.assertEquals(1, returnedOrgs2.size());
        Assert.assertTrue(returnedOrgs.contains(addedOrg));
        Assert.assertTrue(returnedOrgs.contains(addedOrg2));
        Assert.assertTrue(returnedOrgs2.contains(addedOrg2));
    }
}