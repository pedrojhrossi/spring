/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author pj
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model, Principal principal, RedirectAttributes flash) {
        if (principal != null) {
            flash.addFlashAttribute("info", "Ya ha iniciado sesion anteriormente");
            return "redirect:/";
        }
        
        if (error != null) {
            model.addAttribute("error", "Error al ingresar: Nombre de usuario o contraseña incorrecta. Por favor vuelva a intentarlo!");
        }
        
        if (logout != null) {
            model.addAttribute("success", "Ha cerrado sesion con exito!");
        }
        return "login";
    }
}
