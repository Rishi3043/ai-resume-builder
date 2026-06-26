package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsById(3L)) {
            User user = new User();
            user.setId(3L);
            user.setName("Rishik Daggula");
            user.setEmail("rishik@example.com");
            userRepository.save(user);
            System.out.println("✅ User with id=3 created: Rishik Daggula");
        } else {
            System.out.println("ℹ️ User with id=3 already exists, skipping creation");
        }
    }
}
