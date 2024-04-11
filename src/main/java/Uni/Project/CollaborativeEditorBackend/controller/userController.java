package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService service;

    public userController(userService service) {
        this.service = service;
    }

    @RequestMapping("/add")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id) {
        return service.findUserById(id);
    }

     @PutMapping("/update/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        // First, find the user by ID
        User existingUser = service.findUserById(id);

        // Then, update the existing user with the new data
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());

        // Finally, save the updated user and return it
        return service.updateUser(existingUser);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        return service.deleteUser(id);
    }




}