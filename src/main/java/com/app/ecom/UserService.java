package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> userList = new ArrayList<>();
    private long idCounter =1;
    public List<User> getAllUsers() {
        return userList;
    }

    public void createUser(User user) {
        user.setId(idCounter++);
        userList.add(user);
    }

    public User findById(Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean updateUser(Long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(exsitingUser -> {
                            exsitingUser.setFirstName(updatedUser.getFirstName());
                            exsitingUser.setLastName(updatedUser.getLastName());
                            return true;
                        }
                )
                .orElse(false);
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
        return userList.removeIf(user -> user.getId().equals(id));
}

}
