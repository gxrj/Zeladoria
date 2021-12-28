package com.femass.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController( value = "account")
public class ContaController {

    @GetMapping( value = "auth" )
    public String login(){
        return "Welocome!!";
    }

    @GetMapping( value = "session-out" )
    public String logout(){
        return "Bye!!";
    }

    @GetMapping( value = "new" )
    public String createAccount(){
        return "Nice to meet you!!";
    }
    
}
