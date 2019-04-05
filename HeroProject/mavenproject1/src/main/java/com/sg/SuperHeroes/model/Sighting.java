/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ShellyAnneIsaacs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Sighting {
    
    private int id;
    private List<Hero> heroes;
    private Location location;
    private LocalDateTime date;

    public void setDate(LocalDateTime date) {
        String ldt = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.date = LocalDateTime.parse(ldt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public void setDateFromString(String ldt) {
        this.date = LocalDateTime.parse(ldt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getDate() {
        return date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public LocalDateTime getDateAsDate() {
        String ldt = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.date = LocalDateTime.parse(ldt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return date;
    }

}
