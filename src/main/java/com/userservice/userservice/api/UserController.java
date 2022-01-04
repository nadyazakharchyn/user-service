package com.userservice.userservice.api;

import com.userservice.userservice.repo.model.User;
import com.userservice.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<com.userservice.userservice.repo.model.User>> index(){
        final List<com.userservice.userservice.repo.model.User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.userservice.userservice.repo.model.User> showById(@PathVariable long id){
        try {
            final com.userservice.userservice.repo.model.User user = userService.fetchUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.userservice.userservice.api.dto.User user){
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String email = user.getEmail();
        final long userId = userService.createUser(firstName, lastName, email);
        final String userUri = String.format("/users/%d", userId);
        return ResponseEntity.created(URI.create(userUri)).build();

    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<com.userservice.userservice.api.dto.User> getDtoById(@PathVariable Long id) {
        try {
            User user = userService.fetchUserById(id);
            com.userservice.userservice.api.dto.User userDto = new com.userservice.userservice.api.dto.User(user.getFirstName(),user.getLastName(), user.getEmail());

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
