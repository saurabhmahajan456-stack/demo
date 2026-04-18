package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://65.1.86.10")
@RestController
@RequestMapping("/users")
public class UserController {

    private static List<User> users = new ArrayList<>();
    private static Long idCounter = 1L;

    static {
        users.add(new User(idCounter++, "John Doe", "john@example.com"));
        users.add(new User(idCounter++, "Jane Smith", "jane@example.com"));
    }

    // CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(idCounter++);
        users.add(user);
        return user;
    }

    // READ ALL
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // READ BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {

        User existingUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        return existingUser;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(u -> u.getId().equals(id));
        return "User deleted successfully";
    }
}