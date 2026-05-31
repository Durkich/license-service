package com.example.license.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AnalyticController {
    @GetMapping("/analytic")
    public String Analytic(Model model, Principal principal)  {
        model.addAttribute("username", principal.getName());
        return "Analytic.html";
    }
}