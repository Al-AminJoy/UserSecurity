package com.alamin.usersecurity;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Controller {
    @Autowired
    UserRepository repository;
    @PostMapping()
    public User create(@RequestBody User user){
        String bcrypt=hashPassword(user.getPassword());
        user.setPassword(bcrypt);
      User saved=repository.save(user);
        return saved;
    }
    @GetMapping("")
    public ResponseEntity<String> read(@RequestBody User user) {
        try {
            User readUser=repository.findByUserName(user.getUsername());
            /***
             *Checking the normal password is equal or not with encrypted password
             * if it match then return a string response "matched"
             * otherwise return a string response "matched"
             */
            if (BCrypt.checkpw(user.getPassword(), readUser.getPassword())){
                return ResponseEntity.ok("matched");
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e){

        }

        return ResponseEntity.notFound().build();

    }

    /***
     *This method used for encrypting the password
     */
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
