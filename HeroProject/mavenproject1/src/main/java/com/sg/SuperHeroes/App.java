/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroes;

import com.sg.SuperHeroes.ops.HeroController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author ShellyAnneIsaacs
 */
@SpringBootApplication
public class App {

    @Autowired
    HeroController controller;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}