package Uni.Project.CollaborativeEditorBackend.service;


import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class userService {

    @Autowired
    private userRepository userRepo;

    //CRUD Create,Read,Update,Delete

    public User addUser(User user){
       return userRepo.save(user);
    }

    public List<User> findAllUsers(){
      return userRepo.findAll();
    }

    public User findUserById(String id){
        return userRepo.findById(id).orElse(null); //.get() is not recommended
    }

    public User updateUser(User user){
        User FoundedUser =userRepo.findById(user.getUserID()).orElse(null);
        assert FoundedUser != null;
        FoundedUser.setPassword(user.getPassword());
        FoundedUser.setEmail(user.getEmail());
        return userRepo.save(FoundedUser);
    }

    public String deleteUser(String id){
        userRepo.deleteById(id);
        return id +" Deleted Successfully!";
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
