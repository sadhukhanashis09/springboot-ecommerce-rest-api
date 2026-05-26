package com.app.ecom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> getAllUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.status(NOT_FOUND).body("User Not found");

    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(CREATED).body("User Created Successfully");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean isUpdated = userService.updateUser(id, user);
        if(!isUpdated){
            return ResponseEntity.status(NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long
                              id){
        boolean isDeleted= userService.deleteById(id);
        if(!isDeleted){
            return ResponseEntity.status(NOT_FOUND).body("User not found for deleting");
        }
        return ResponseEntity.ok()
            .body("User deleted Successfully");
    }
}
