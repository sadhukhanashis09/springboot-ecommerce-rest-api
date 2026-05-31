package com.app.ecom.service;

import com.app.ecom.dto.userdto.UserRequestDTO;
import com.app.ecom.dto.userdto.UserResponseDTO;
import com.app.ecom.mapper.AddressMapper;
import com.app.ecom.mapper.UserMapper;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;


    /*private final List<User> userList = new ArrayList<>();
    private long idCounter =1; */

    public UserService(UserRepository userRepository, UserMapper userMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    //Method for getting all users
    public List<UserResponseDTO> getAllUsers() {
        /*return userList;*/
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponseDTO).toList();
    }

    /*public void createUser(User user) {
        user.setId(idCounter++);
        userList.add(user);
    }*/
    //Method for Create user
    public UserResponseDTO  createUser(UserRequestDTO createUserRequestDTO){
        User user = userMapper.toUserModel(createUserRequestDTO);
        User savedUser= userRepository.save(user);
        return userMapper.toUserResponseDTO(savedUser);

    }

    public UserResponseDTO findById(Long id) {
        /*return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);*/
        return userRepository.findById(id).map(userMapper::toUserResponseDTO
        ).orElse(null);

    }

    public boolean updateUser(Long id, UserRequestDTO userUpdateRequest) {
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
            userMapper.updateUserFromDTO(userUpdateRequest, existingUser);
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
