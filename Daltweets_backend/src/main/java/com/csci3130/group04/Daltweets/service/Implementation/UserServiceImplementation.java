package com.csci3130.group04.Daltweets.service.Implementation;

import com.csci3130.group04.Daltweets.model.User;
import com.csci3130.group04.Daltweets.model.User.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csci3130.group04.Daltweets.repository.UserRepository;
import com.csci3130.group04.Daltweets.service.UserService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (user == null)
        {
            throw new IllegalArgumentException("given user is null");
        }

        if ( user.getUsername() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("user details are empty");
        }
        User find_user = getUserByName(user.getUsername());
        if ( find_user != null ) {
            System.out.println("There is already an user with this name");
            return find_user;
        }
        if (user.getDateCreated() == null){
            user.setDateCreated(LocalDateTime.now());
        }
        user.setStatus(User.Status.PENDING);
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }
    
    @Override
    public User getUserByName(String name) {
        if ( name == null ) {
            throw new IllegalArgumentException("name in get user is null");
        }
        return userRepository.findByUsername(name);
    }

    @Override
    public User updateUser(User updateUser) {
        if ( updateUser == null  ) {
            throw new IllegalArgumentException("user in update is NULL");
        }
        User user = getUserByName(updateUser.getUsername());
        if ( user == null ) {
            throw new IllegalArgumentException("there is no user to update");
        }
        user.setBio(updateUser.getBio());
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        user.setAccountDeleted(updateUser.isAccountDeleted());
        user.setRole(updateUser.getRole());
        user.setStatus(updateUser.getStatus());
        User updated_user = userRepository.save(user);
        return updated_user;
    }

    @Override
    public List<User> getRecommendedUsers(String name){
      return userRepository.findByUsernameNot(name);
    }

    @Override
    public User addExistingUser(String name) {
       if(name == null || name.isEmpty()) return null;
       User user = userRepository.findByUsernameRawSearch(name);
       if (user == null) return null;
       user.setAccountDeleted(false);
       return userRepository.save(user);
    }

    @Override
    public User softDeleteUser(String name) {
        if (name == null || name.isEmpty()) return null;
        User user = userRepository.findByUsernameRawSearch(name);
       if (user == null) return null;
       user.setAccountDeleted(true);
       return userRepository.save(user);
    }   

    @Override
    public List<User> getSignupRequests(){
      return userRepository.getSignupRequests();
    }

    @Override
    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    @Override
    public User changeUserStatus(String username, Status status) {
        if ( username == null ) return null;
        User user = userRepository.findByUsernameRawSearch(username);
        if (user == null ) return null;
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public boolean isValidName(String name) {
        if ( name == null || name.isBlank() ) return false;
        return true;
    }
}
