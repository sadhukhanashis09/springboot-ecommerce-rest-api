package com.app.ecom.controller;

import com.app.ecom.dto.userdto.UserRequestDTO;
import com.app.ecom.dto.userdto.UserResponseDTO;
import com.app.ecom.service.userservice.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> getAllUser(@PathVariable Long id) {
        UserResponseDTO userResponse = userService.findById(id);
        if (userResponse != null) {
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        return ResponseEntity.status(NOT_FOUND).body("User Not found");

    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO createUserRequest) {
        UserResponseDTO createdUser=userService.createUser(createUserRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/users/" + createdUser.getId())
                .body(createdUser);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userUpdateRequest) {
        boolean isUpdated = userService.updateUser(id, userUpdateRequest);
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
