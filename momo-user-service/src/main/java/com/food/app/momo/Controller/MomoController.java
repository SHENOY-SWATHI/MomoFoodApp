package com.food.app.momo.Controller;

import com.food.app.momo.Exceptions.UserAlreadyExistsException;
import com.food.app.momo.Exceptions.UserDoesnotExistsException;
import com.food.app.momo.Model.SignIn;
import com.food.app.momo.Model.User;
import com.food.app.momo.Repository.UserRepo;
import com.food.app.momo.Service.UserService;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/userService")
public class MomoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MomoController.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin/getAllUser")
    public List<User> getAllUser(){
        LOGGER.info("Entered in MomoController.getAllUser()");
        return userService.getAllUser();
    }

    @GetMapping(value = "/admin/getAllActiveUser")
    public List<User> getAllActiveUser(){
        LOGGER.info("Entered in MomoController.getAllActiveUser()");
        return userService.getAllActiveUser();
    }

    @GetMapping(value = "/admin/getAllInactiveUser")
    public List<User> getAllInactiveUser(){
        LOGGER.info("Entered in MomoController.getAllInactiveUser()");
        return userService.getAllInActiveUser();
    }

    @GetMapping(value = "/getUserEmail/{emailId}")
    public User getUser(@PathVariable String emailId){
        LOGGER.info("Entered in MomoController.getUser()");
        User user = userService.findByEmail(emailId);
        if(user != null){
            return user;
        } else throw new UserDoesnotExistsException("User was not found");
    }

    @GetMapping(value = "/getUserId/{id}")
    public User getUserById(@PathVariable long id){
        LOGGER.info("Entered in MomoController.getUserById()");
        return userService.findById(id);
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<?> signUp(@RequestBody @NotNull User newUser){
        LOGGER.info("Entered in MomoController.signUp()");
            if(userService.findByEmail(newUser.getEmailId())==null){
                //createUser
                return new ResponseEntity<>(userService.createUser(newUser),HttpStatus.CREATED);
            }else throw new UserAlreadyExistsException("User With EmailId exists");
    }

    @GetMapping(value = "/deleteUser/{emailId}")
    public String softDeleteUser(@PathVariable String emailId){
        LOGGER.info("Entered in MomoController.softDeleteUser()");
        return userService.softDeleteUser(emailId);
    }

    @GetMapping(value = "/deleteAllUser")
    public String deleteAllUser(){
        LOGGER.info("Entered in MomoController.deleteAllUser()");
        userRepo.deleteAll();
        return "All Users are Deleted";
    }

    @PutMapping(value = "/updateUser/{emailId}")
    public User updateUser(@PathVariable String emailId,@RequestBody @NotNull User updateUser){
        LOGGER.info("Entered in MomoController.updateUser()");
        return userService.updateUser(emailId,updateUser);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignIn signIn){
        LOGGER.info("Entered in MomoController.signIn()");
        User user = signIn.getEmailId()!= null ? getUser(signIn.getEmailId()) : getUserById(signIn.getId());
         return user.getPassword().equals(signIn.getPassword()) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>("Invalid password", HttpStatus.OK);
    }

    @PostMapping(value = "/forgotPassword")
    public String forgotPassword(@RequestBody SignIn signIn){
        LOGGER.info("Entered in MomoController.forgotPassword()");
        return userService.forgotPassword(signIn);
    }

//    @PostMapping(value = "/userAddress")
//    public ResponseEntity<?> userAddress(@RequestBody UserAddress userAddress){
//        LOGGER.info("Entered in MomoController.userAddress()");
//        return userService.createAddress(userAddress);
//    }
//
//    @PutMapping(value = "/updateUserAddress/{user_id}")
//    public UserAddress updateUserAddress(@PathVariable String user_id,@RequestBody @NotNull UserAddress updateUserAddress){
//        LOGGER.info("Entered in MomoController.updateUserAddress()");
//        return userService.updateUserAddress(user_id,updateUserAddress);
//    }
//
//    @GetMapping(value = "/deleteUserAddress/{user_id}")
//    public String deleteUserAddress(@PathVariable String user_id){
//        LOGGER.info("Entered in MomoController.deleteUserAddress()");
//        return userService.DeleteUserAddress(user_id);
//    }

}
