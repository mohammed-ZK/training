package com.example.training.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "هذا endpoint مفتوح للجميع 🚀";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "مرحباً USER 🙋‍♂️";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "مرحباً ADMIN 👑";
    }
}

