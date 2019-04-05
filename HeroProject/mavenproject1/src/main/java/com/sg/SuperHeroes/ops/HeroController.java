/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes.ops;

import com.sg.SuperHeroes.dao.HeroDao;
import com.sg.SuperHeroes.model.Hero;
import com.sg.SuperHeroes.model.Location;
import com.sg.SuperHeroes.model.Organization;
import com.sg.SuperHeroes.model.Power;
import com.sg.SuperHeroes.model.Sighting;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ShellyAnneIsaacs
 */
@Controller
public class HeroController {

    @Autowired
    HeroDao dao;

    @GetMapping("/home")
    public String DisplayHomePage(Model model) {
        List<Sighting> sightings = dao.getAllSightings();
        List<Sighting> mostRecentSightings = new ArrayList<>();
        int numSightings = sightings.size();
        for (int i = 0; i < 10; i++) {
            numSightings -= 1;
            if (numSightings < 0) {
                break;
            }
            Sighting aSighting = sightings.get(i);
            mostRecentSightings.add(aSighting);
        }
        model.addAttribute("sightings", mostRecentSightings);
        return "home";
    }

    @GetMapping("/locations")
    public String DisplayLocationPage(Model model) {
        model.addAttribute("locations", dao.getAllLocations());
        return "locations";
    }

    @GetMapping("/organizations")
    public String DisplayOrganizationPage(Model model) {
        model.addAttribute("orgs", dao.getAllOrgs());
        return "organizations";
    }

    @GetMapping("/powers")
    public String DisplayPowerPage(Model model) {
        model.addAttribute("powers", dao.getAllPowers());
        return "powers";
    }

    @GetMapping("/supers")
    public String DisplayHeroPage(Model model) {
        model.addAttribute("heroes", dao.getAllHeroes());
        return "supers";
    }

    @GetMapping("/sightings")
    public String DisplaySightingPage(Model model) {
        model.addAttribute("sightings", dao.getAllSightings());
        return "sightings";
    }

    @GetMapping("/viewSuper")
    public String DisplayOneHero(Integer id, Model model) {
        model.addAttribute("hero", dao.getOneHero(id));
        model.addAttribute("orgs", dao.getOrgsByHero(id));
        model.addAttribute("locations", dao.getLocationsByHero(id));
        return "viewSuper";
    }

    @GetMapping("/viewOrg")
    public String DislayOneOrg(Integer id, Model model) {
        model.addAttribute("org", dao.getOneOrg(id));
        return "viewOrg";
    }

    @GetMapping("/viewPower")
    public String DisplayOnePower(Integer id, Model model) {
        model.addAttribute("power", dao.getOnePower(id));
        return "viewPower";
    }

    @GetMapping("/viewLocation")
    public String DislayOneLocation(Integer id, Model model) {
        model.addAttribute("location", dao.getOneLocation(id));
        model.addAttribute("heroes", dao.getHeroesByLocation(id));
        return "viewLocation";
    }

    @GetMapping("/viewSighting")
    public String DisplayOneSighting(Integer id, Model model) {
        model.addAttribute("sighting", dao.getOneSighting(id));
        return "viewSighting";
    }

    @GetMapping("/deleteSuper")
    public String deleteStudent(Integer id) {
        dao.deleteHero(id);
        return "redirect:/supers";
    }

    @GetMapping("/deleteOrg")
    public String deleteOrg(Integer id) {
        dao.deleteOrg(id);
        return "redirect:/organizations";
    }

    @GetMapping("/deleteLocation")
    public String deleteLocation(Integer id) {
        dao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("/deletePower")
    public String deletePower(Integer id) {
        dao.deletePower(id);
        return "redirect:/powers";
    }

    @GetMapping("/deleteSighting")
    public String deleteSighting(Integer id) {
        dao.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("/location")
    public String AddLocationPage(Model model) {
        return "addLocation";
    }

    @PostMapping("/location")
    public String postLocation(HttpServletRequest request) {
        Location location = new Location();
        String name = request.getParameter("locName");
        if (null != name) {
            location.setName(name);
        }
        String description = request.getParameter("locDescription");
        if (null != description) {
            location.setDescription(description);
        }
        String phone = request.getParameter("phone");
        if (null != phone) {
            location.setPhoneNumber(phone);
        }
        String street = request.getParameter("street");
        if (null != street) {
            location.setStreet(street);
        }
        String city = request.getParameter("city");
        if (null != city) {
            location.setCity(city);
        }
        String state = request.getParameter("state");
        if (null != state) {
            location.setState(state);
        }
        String country = request.getParameter("country");
        if (null != country) {
            location.setCountry(country);
        }
        String zip = request.getParameter("zip");
        if (null != zip) {
            location.setZipCode(zip);
        }
        String latitude = request.getParameter("lat");
        String longitude = request.getParameter("long");
        if (null != latitude) {
            BigDecimal bdLat = new BigDecimal(latitude);
            location.setLatitude(bdLat);
        }
        if (null != longitude) {
            BigDecimal bdLong = new BigDecimal(longitude);
            location.setLongitude(bdLong);
        }
        dao.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("/organization")
    public String AddOrganizationPage(Model model) {
        List<Hero> heroes = dao.getAllHeroes();
        model.addAttribute("heroes", heroes);
        List<Location> locations = dao.getAllLocations();
        model.addAttribute("locations", locations);
        return "addOrganization";
    }

    @GetMapping("/power")
    public String AddPowerPage(Model model) {
        return "addPower";
    }

    @GetMapping("/super")
    public String AddHeroPage(Model model) {
        List<Power> powers = dao.getAllPowers();
        model.addAttribute("powers", powers);
        return "addSuper";
    }

    @PostMapping("/organization")
    public String postOrganization(HttpServletRequest request) {
        Organization org = new Organization();
        String description = request.getParameter("orgDescription");
        if (null != description) {
            org.setDescription(description);
        }
        String name = request.getParameter("orgName");
        if (null != name) {
            org.setName(name);
        }
        String stringLocationId = request.getParameter("location");
        if (null != stringLocationId) {
            Location location = dao.getOneLocation(Integer.parseInt(stringLocationId));
            org.setLocation(location);
        }

        List<Hero> heroes = new ArrayList<>();
        String[] heroIdsArray = request.getParameterValues("heroes");
        if (null != heroIdsArray) {
            for (String heroIdString : heroIdsArray) {
                int heroId = Integer.parseInt(heroIdString);
                Hero hero = dao.getOneHero(heroId);
                heroes.add(hero);
            }
        }
        org.setHeroes(heroes);
        dao.addOrg(org);
        return "redirect:/organizations";
    }

    @PostMapping("/power")
    public String postPower(HttpServletRequest request) {
        Power power = new Power();
        String name = request.getParameter("powerName");
        if (null != name) {
            power.setName(name);
        }
        String description = request.getParameter("powerDescription");
        if (null != description) {
            power.setDescription(description);
        }
        dao.addPower(power);
        return "redirect:/powers";
    }

    @PostMapping("/super")
    public String postHero(HttpServletRequest request) {
        Hero hero = new Hero();
        String name = request.getParameter("heroName");
        if (null != name) {
            hero.setName(name);
        }
        String description = request.getParameter("heroDescription");
        if (null != description) {
            hero.setDescription(description);
        }
        List<Power> powers = new ArrayList<>();
        String[] powerIdsArray = request.getParameterValues("powers");
        if (null != powerIdsArray) {
            for (String powerIdString : powerIdsArray) {
                int powerId = Integer.parseInt(powerIdString);
                Power power = dao.getOnePower(powerId);
                powers.add(power);
            }
        }
        hero.setPowers(powers);
        String isEvilString = request.getParameter("isEvil");
        boolean isEvil = false;
        if (null != isEvilString && isEvilString.equals("true")) {
            isEvil = true;
        }
        hero.setIsEvil(isEvil);
        dao.addHero(hero);
        return "redirect:/supers";
    }

    @GetMapping("/sighting")
    public String AddSightingPage(Model model) {
        List<Hero> heroes = dao.getAllHeroes();
        List<Location> locations = dao.getSightingLocations();
        LocalDateTime date = LocalDateTime.now();
        String stringDate = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("date", stringDate);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("now", LocalDateTime.now());
        return "addSighting";
    }

    @PostMapping("/sighting")
    public String postSighting(HttpServletRequest request) {
        Sighting sighting = new Sighting();

        String stringLocationId = request.getParameter("location");
        if (null != stringLocationId) {
            Location location = dao.getOneLocation(Integer.parseInt(stringLocationId));
            sighting.setLocation(location);
        }

        List<Hero> heroes = new ArrayList<>();
        String[] heroIdsArray = request.getParameterValues("heroes");
        if (null != heroIdsArray) {
            for (String heroIdString : heroIdsArray) {
                int heroId = Integer.parseInt(heroIdString);
                Hero hero = dao.getOneHero(heroId);
                heroes.add(hero);
            }
        }
        sighting.setHeroes(heroes);

        String stringDate = request.getParameter("date");
        if (null != stringDate) {
            LocalDateTime date = LocalDateTime.parse(stringDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            sighting.setDate(date);
        }
        if (null == stringDate) {
            LocalDateTime date = LocalDateTime.now();
            sighting.setDate(date);
        }
        dao.addSighting(sighting);

        return "redirect:/viewSighting?id=" + sighting.getId();
    }

    @GetMapping("/editSuper")
    public String editSuper(Integer id, Model model) {
        Hero hero = dao.getOneHero(id);
        List<Power> powers = dao.getAllPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);
        return "editSuper";
    }

    @PostMapping("/editSuper")
    public String postEditSuper(HttpServletRequest request) {
        String stringId = request.getParameter("id");
        if (null != stringId) {
            int id = Integer.parseInt(stringId);
            Hero hero = dao.getOneHero(id);
            String name = request.getParameter("heroName");
            if (null != name) {
                hero.setName(name);
            }
            String description = request.getParameter("heroDescription");
            if (null != description) {
                hero.setDescription(description);
            }
            List<Power> powers = new ArrayList<>();
            String[] powerIdsArray = request.getParameterValues("powers");
            if (null != powerIdsArray) {
                for (String powerIdString : powerIdsArray) {
                    int powerId = Integer.parseInt(powerIdString);
                    Power power = dao.getOnePower(powerId);
                    powers.add(power);
                }
            }
            hero.setPowers(powers);
            dao.updateHero(id, hero);
        }
        return "redirect:/supers";
    }

    @GetMapping("/editOrg")
    public String editOrg(Integer id, Model model) {
        List<Hero> heroes = dao.getAllHeroes();
        model.addAttribute("heroes", heroes);
        List<Location> locations = dao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("org", dao.getOneOrg(id));
        return "editOrg";
    }

    @PostMapping("/editOrg")
    public String postEditOrg(HttpServletRequest request) {
        String stringId = request.getParameter("id");
        if (null != stringId) {
            int id = Integer.parseInt(stringId);
            Organization org = dao.getOneOrg(id);
            String description = request.getParameter("orgDescription");
            if (null != description) {
                org.setDescription(description);
            }
            String name = request.getParameter("orgName");
            if (null != name) {
                org.setName(name);
            }
            String stringLocationId = request.getParameter("location");
            if (null != stringLocationId) {
                Location location = dao.getOneLocation(Integer.parseInt(stringLocationId));
                org.setLocation(location);
            }
            List<Hero> heroes = new ArrayList<>();

            String[] heroIdsArray = request.getParameterValues("heroes");
            if (null != heroIdsArray) {
                for (String heroIdString : heroIdsArray) {
                    int heroId = Integer.parseInt(heroIdString);
                    Hero hero = dao.getOneHero(heroId);
                    heroes.add(hero);
                }
            }
            org.setHeroes(heroes);
            dao.updateOrg(id, org);
        }
        return "redirect:/organizations";
    }

    @GetMapping("/editPower")
    public String editPower(Integer id, Model model) {
        model.addAttribute(dao.getOnePower(id));
        return "editPower";
    }

    @PostMapping("/editPower")
    public String postEditPower(HttpServletRequest request) {
        String stringId = request.getParameter("id");
        if (null != stringId) {
            int id = Integer.parseInt(stringId);
            Power power = dao.getOnePower(id);
            String description = request.getParameter("powerDescription");
            if (null != description) {
                power.setDescription(description);
            }
            String name = request.getParameter("powerName");
            if (null != name) {
                power.setName(name);
            }
            dao.updatePower(id, power);
        }
        return "redirect:/powers";
    }

    @GetMapping("/editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = dao.getOneSighting(id);
        model.addAttribute("currentSighting", sighting);
        LocalDateTime date = sighting.getDateAsDate();
        String stringDate = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("date", stringDate);
        List<Hero> heroes = dao.getAllHeroes();
        List<Location> locations = dao.getSightingLocations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("now", LocalDateTime.now());
        return "editSighting";
    }

    @PostMapping("/editSighting")
    public String postEditSighting(HttpServletRequest request) {
        String stringId = request.getParameter("id");
        if (null != stringId) {
            int id = Integer.parseInt(stringId);
            Sighting sighting = dao.getOneSighting(id);

            String stringLocationId = request.getParameter("location");
            if (null == stringLocationId) {
                sighting.setLocation(dao.getUnknownLocation());
            }
            if (null != stringLocationId) {
                Location location = dao.getOneLocation(
                        Integer.parseInt(stringLocationId));
                sighting.setLocation(location);
            }

            List<Hero> heroes = new ArrayList<>();
            String[] heroIdsArray = request.getParameterValues("heroes");
            if (null != heroIdsArray) {
                for (String heroIdString : heroIdsArray) {
                    int heroId = Integer.parseInt(heroIdString);
                    Hero hero = dao.getOneHero(heroId);
                    heroes.add(hero);
                }
            }
            sighting.setHeroes(heroes);

            String stringDate = request.getParameter("date");
            if (null != stringDate) {
                LocalDateTime date = LocalDateTime.parse(stringDate,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                sighting.setDate(date);
            }

            dao.updateSighting(id, sighting);
        }
        return "redirect:/sightings";
    }

    @GetMapping("/editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = dao.getOneLocation(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("/editLocation")
    public String postEditLocation(HttpServletRequest request) {
        String stringId = request.getParameter("id");
        int id;
        if (null != stringId) {
            id = Integer.parseInt(stringId);

            Location location = new Location();
            String name = request.getParameter("locName");
            if (null != name) {
                location.setName(name);
            }
            String description = request.getParameter("locDescription");
            if (null != description) {
                location.setDescription(description);
            }
            String number = request.getParameter("phone");
            if (null != number) {
                location.setPhoneNumber(number);
            }
            String street = request.getParameter("street");
            if (null != street) {
                location.setStreet(street);
            }
            String city = request.getParameter("city");
            if (null != city) {
                location.setCity(city);
            }
            String state = request.getParameter("state");
            if (null != state) {
                location.setState(state);
            }
            String country = request.getParameter("country");
            if (null != country) {
                location.setCountry(country);
            }
            String zip = request.getParameter("zip");
            if (null != zip) {
                location.setZipCode(zip);
            }
            String latitude = request.getParameter("lat");
            String longitude = request.getParameter("long");
            if (null != latitude) {
                BigDecimal bdLat = new BigDecimal(latitude);
                location.setLatitude(bdLat);
            }
            if (null != longitude) {
                BigDecimal bdLong = new BigDecimal(longitude);
                location.setLongitude(bdLong);
            }
            dao.updateLocation(id, location);
        }
        return "redirect:/locations";
    }

}
