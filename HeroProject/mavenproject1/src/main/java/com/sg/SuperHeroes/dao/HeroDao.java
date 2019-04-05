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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ShellyAnneIsaacs
 */
public interface HeroDao {
    
    //specific user functionality requested
    List<Hero> getHeroesByLocation(int locationId);    
    List<Location> getLocationsByHero(int heroId);    
    List<Sighting> getSightingsByDate(LocalDateTime date);    
    List<Hero> getHeroesByOrg(int orgId);    
    List<Organization> getOrgsByHero(int heroId);
    Location getUnknownLocation();
    List<Location> getSightingLocations();
  
    //create
    Hero addHero(Hero hero);
    Sighting addSighting(Sighting sighting);
    Power addPower(Power power);
    Organization addOrg(Organization org);
    Location addLocation(Location location);
 
    //read
    List<Hero> getAllHeroes();
    List<Sighting> getAllSightings();
    List<Power> getAllPowers();
    List<Organization> getAllOrgs();
    List<Location> getAllLocations();
    
    Hero getOneHero(int heroId);
    Sighting getOneSighting(int sightingId);
    Power getOnePower(int powerId);
    Organization getOneOrg(int orgId);
    Location getOneLocation(int locationId);
     
    //update
    void updateHero(int heroId, Hero hero);
    void updateSighting(int sightingId, Sighting sighting);
    void updatePower(int powerId, Power power);
    void updateOrg(int orgId, Organization org);
    void updateLocation(int locationId, Location location);
    
    //delete
    void deleteHero(int heroId);
    void deleteSighting(int sightingId);
    void deletePower(int powerId);
    void deleteOrg(int orgId);
    void deleteLocation(int locationId);
    
}
