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
        if (userRepository.findByEmail("rishik@example.com").isPresent()) {
            System.out.println("User already exists, skipping");
        } else {
            User user = new User();
            user.setName("Rishik Daggula");
            user.setEmail("rishik@example.com");
            User savedUser = userRepository.save(user);
            System.out.println("User created with ID: " + savedUser.getId());
        }
    }
}
