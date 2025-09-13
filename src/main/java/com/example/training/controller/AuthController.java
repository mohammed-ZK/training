package com.example.training.controller;

import com.example.training.entity.RoleEnum;
import com.example.training.entity.User;
import com.example.training.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created: " + user.getUsername());
    }

    // Endpoint آمن فقط للأدمن لإعادة ترميز كلمات DB (استخدم بحذر)
    @PostMapping("/migrate-passwords")
    public ResponseEntity<?> migratePasswords() {
        int count = userService.migratePlainPasswords();
        return ResponseEntity.ok("Passwords updated: " + count);
    }

    // DTO
    public static class RegisterRequest {
        private String username;
        private String password;
        private RoleEnum role;

        public String getUsername () {
            return username;
        }

        public String getPassword () {
            return password;
        }

        public RoleEnum getRole () {
            return role;
        }

        public void setUsername (String username) {
            this.username = username;
        }

        public void setPassword (String password) {
            this.password = password;
        }

        public void setRole (RoleEnum role) {
            this.role = role;
        }
    }
}

