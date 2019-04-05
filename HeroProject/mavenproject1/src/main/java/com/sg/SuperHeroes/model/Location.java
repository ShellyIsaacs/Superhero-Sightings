/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes.model;

import java.math.BigDecimal;
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
public class Location {

    private int id;
    private String name;
    private String description;
    private String phoneNumber;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public void setLatitude(BigDecimal latitude) {
        BigDecimal scaled = latitude.setScale(8);
        this.latitude = scaled;
    }

    public void setLongitude(BigDecimal longitude) {
        BigDecimal scaled = longitude.setScale(8);
        this.longitude = scaled;
    }
  
}
