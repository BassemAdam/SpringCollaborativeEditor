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
        FoundedUser.setPassword(user.getPassword());
        FoundedUser.setEmail(user.getEmail());
        FoundedUser.setRole(user.getRole());

        return userRepo.save(FoundedUser);
    }

    public String deleteUser(String id){
        userRepo.deleteById(id);
        return id +" Deleted Successfully!";
    }

}
