package com.app.ecom.service;

import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    /*private final List<User> userList = new ArrayList<>();
    private long idCounter =1; */

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        /*return userList;*/
        return userRepository.findAll();
    }

    /*public void createUser(User user) {
        user.setId(idCounter++);
        userList.add(user);
    }*/
    public void createUser(User user) {
        userRepository.save(user);

    }

    public User findById(Long id) {
        /*return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);*/
        return userRepository.findById(id).orElse(null);
    }

    public boolean updateUser(Long id, User updatedUser) {
       /* return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(exsitingUser -> {
                            exsitingUser.setFirstName(updatedUser.getFirstName());
                            exsitingUser.setLastName(updatedUser.getLastName());
                            return true;
                        }
                )
                .orElse(false); */
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    /* public boolean updateUser(Long id, User updatedUser) {

        for (User user : userList) {
            if (user.getId().equals(id)) {
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                return true;
            }
        }

        return false;
    }
    */
    public boolean deleteById(Long id) {
        /*return userList.removeIf(user -> user.getId().equals(id));*/
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
