/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author pj
 */
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String Home() {
//        return "redirect:/app/index";
//        return "redirect:https://www.google.com";
        return "forward:/app/index";
    }
    
}
