package com.userservice.userservice.service;

import com.userservice.userservice.repo.UserRepo;
import com.userservice.userservice.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public List<User> fetchAllUsers(){
        return userRepo.findAll();
    }

    public User fetchUserById(long id) throws IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isPresent())
            return maybeUser.get();
        else
            throw new IllegalArgumentException("Invalid doctor ID");
    }

    public long createUser(String firstName, String lastName, String email){
        final User user = new User(firstName, lastName, email);
        final User savedUser = userRepo.save(user);
        return savedUser.getId();
    }

    public void deleteUser(long id){
        userRepo.deleteById(id);
    }
}
