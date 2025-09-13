package com.example.training.service;

import com.example.training.entity.Role;
import com.example.training.entity.RoleEnum;
import com.example.training.entity.User;
import com.example.training.repository.RoleRepository;
import com.example.training.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // تسجيل مستخدم جديد (يشفّر الباسورد قبل الحفظ)
    public User registerUser(String username, String rawPassword, RoleEnum roleEnum) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseGet(() -> roleRepository.save(new Role(null, roleEnum)));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // <-- تشفير
        user.setEnabled(true);
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    // migration: إعادة ترميز كلمات المرور غير المشفّرة
    @Transactional
    public int migratePlainPasswords() {
        List<User> users = userRepository.findAll();
        int updated = 0;
        for (User u : users) {
            String pw = u.getPassword();
            if (pw == null) continue;
            // فحص بسيط: هل يبدو مشفّرًا بواسطة BCrypt؟
            if (!pw.startsWith("$2a$") && !pw.startsWith("$2b$") && !pw.startsWith("$2y$")) {
                u.setPassword(passwordEncoder.encode(pw));
                userRepository.save(u);
                updated++;
            }
        }
        return updated;
    }
}
