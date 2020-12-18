/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.web.app.controllers;

import com.bolsadeideas.springboot.web.app.models.Usuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.management.AttributeValueExp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author pj
 */
@Controller
@RequestMapping("/app")
public class IndexController {
    
    @Value("${texto.indexcontroller.index.titulo}")
    private String textoIndex;
    
    @Value("${texto.indexcontroller.perfil.titulo}")
    private String textoPerfil;
    
    @Value("${texto.indexcontroller.listar.titulo}")
    private String textoListar;
    
    @RequestMapping(value = {"/index", "/", "/home"}, method = RequestMethod.GET)
//    @GetMapping(value = "/index") //---Equivalente a @RequestMapping(value = "/index", method = RequestMethod.GET)---
    public String index(Model model) {
        model.addAttribute("titulo", textoIndex);
        return "index";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {

        Usuario usuario = new Usuario();
        usuario.setNombre("Pedro");
        usuario.setApellido("Hernandez");
        usuario.setEmail("pj@gmail.com");

        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", textoPerfil.concat(usuario.getNombre()));

        return "perfil";
    }

    @RequestMapping("/listar")
    public String listar(Model model) {
//        List<Usuario> usuarios = new ArrayList<>();
//        usuarios.add(new Usuario("Eva", "Navas", "evanavasrod@gmail.com"));
//        usuarios.add(new Usuario("Pedro", "Hernandez", "pj@gmail.com"));
//        usuarios.add(new Usuario("Andres", "Hernandez", "pa@gmail.com"));
//        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", textoListar);
        return "listar";
    }

    @ModelAttribute("usuarios")
    public List<Usuario> poblarUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario("Eva", "Navas", "evanavasrod@gmail.com"),
                new Usuario("Pedro", "Hernandez", "pj@gmail.com"),
                new Usuario("Andres", "Hernandez", "pa@gmail.com"));

        return usuarios;
    }
}
