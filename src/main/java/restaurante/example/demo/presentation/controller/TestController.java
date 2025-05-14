/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@PreAuthorize("denyAll()")
public class TestController {
        
    @GetMapping("/get")
    private String helloGet(){
        return "Hello Word - GGET";
    }
    
    @PostMapping("/post")
    private String helloPst(){
        return "Hello Word - POST";
    }
    
    @PutMapping("/put")
    private String helloPut(){
        return "Hello Word - PUT";
    }
    
    @DeleteMapping("/delete")
    private String helloSet(){
        return "Hello Word - DELETE";
    }
    
    @PatchMapping("/patch")
    private String helloDelete(){
        return "Hello Word - PATCH";
    }
    
    private String helloPatch(){
        return "Hello Word - GGET";
    }
        
    @GetMapping("/hello")
    public String saludar(){
        return "Buenos dias";
    }
    
    @GetMapping("/hello-secured")
    public String saludarSecured(){
        return "Buenos dias con seguridad";
    }
    
    @GetMapping("/hello-secured2")
    public String saludarSecuredDos(){
        return "Buenos dias con seguridad dos";
    }
}
