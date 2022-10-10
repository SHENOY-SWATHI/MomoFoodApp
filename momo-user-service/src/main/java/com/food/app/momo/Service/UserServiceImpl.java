package com.food.app.momo.Service;

import com.food.app.momo.Exceptions.UserDoesnotExistsException;
import com.food.app.momo.Model.SignIn;
import com.food.app.momo.Model.User;
import com.food.app.momo.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User user) throws NullPointerException {
        LOGGER.info("Entered in UserServiceImpl.createUser()");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setActiveUser("Y");
        return userRepo.save(user);
    }

    @Override
    public String softDeleteUser(String emailId) {
        LOGGER.info("Entered in UserServiceImpl.softDeleteUser()");
        userRepo.update("N",new Date(),emailId);
        return "User Deleted";
    }

    @Override
    public User updateUser(String emailId, User updateUser) {
        LOGGER.info("Entered in UserServiceImpl.updateUser()");
        User user = userRepo.findByEmail(emailId);
        try{
            user.setLastName(updateUser.getLastName());
            user.setFirstName(updateUser.getFirstName());
            user.setEmailId(updateUser.getEmailId());
            user.setPassword(updateUser.getPassword());
            user.setUpdatedDate(new Date());
            return userRepo.save(user);
        } catch (NullPointerException e) {
            throw new UserDoesnotExistsException("User was not found");
        }

    }

//    @Override
//    public ResponseEntity<UserAddress> createAddress(UserAddress userAddress) {
//        LOGGER.info("Entered in UserServiceImpl.createAddress()");
//        userAddress.setCreatedDate(new Date());
//        userAddress.setUpdatedDate(new Date());
//        userAddressRepo.save(userAddress);
//        return new ResponseEntity<>(userAddress, HttpStatus.OK);
//    }
//
//    @Override
//    public UserAddress updateUserAddress(String user_id, UserAddress updateUserAddress) {
//        LOGGER.info("Entered in UserServiceImpl.updateUserAddress()");
//        UserAddress userAddress = userAddressRepo.findByUserId(user_id);
//        try{
//            userAddress.setAddressLine1(updateUserAddress.getAddressLine1());
//            userAddress.setAddressLine2(updateUserAddress.getAddressLine2());
//            userAddress.setDistrict(updateUserAddress.getDistrict());
//            userAddress.setState(updateUserAddress.getState());
//            userAddress.setAddressType(updateUserAddress.getAddressType());
//            userAddress.setUpdatedDate(new Date());
//            return userAddressRepo.save(userAddress);
//        } catch (NullPointerException e) {
//            throw new UserDoesnotExistsException("User was not found");
//        }
//    }
//
//    @Override
//    public String DeleteUserAddress(String user_id) {
//        LOGGER.info("Entered in UserServiceImpl.DeleteUserAddress()");
//        userAddressRepo.deleteByUserId(user_id);
//        return "Address Deleted";
//    }

    @Override
    public List<User> getAllUser() {
        LOGGER.info("Entered in UserServiceImpl.getAllUser()");
        return userRepo.findAll();
    }

    @Override
    public List<User> getAllActiveUser() {
        LOGGER.info("Entered in UserServiceImpl.getAllActiveUser()");
        return userRepo.findActiveUser();
    }

    @Override
    public List<User> getAllInActiveUser() {
        LOGGER.info("Entered in UserServiceImpl.getAllInActiveUser()");
        return userRepo.findInactiveUser();
    }

    @Override
    public User findByEmail(String emailId) {
        LOGGER.info("Entered in UserServiceImpl.findByEmail()");
        return userRepo.findByEmail(emailId);
    }

    @Override
    public User findById(long id) {
        LOGGER.info("Entered in UserServiceImpl.findByEmail()");
        return userRepo.findById(id).orElseThrow(() ->new UserDoesnotExistsException("User was not found"));
    }

    @Override
    public String forgotPassword(SignIn signIn) {
        LOGGER.info("Entered in UserServiceImpl.forgotPassword()");
        User user = userRepo.findByIdEmail(signIn.getEmailId(),signIn.getId());
        try {
            user.setPassword(signIn.getPassword());
            user.setUpdatedDate(new Date());
            userRepo.save(user);
            return "User Password Updated";
        } catch (NullPointerException e) {
            throw new UserDoesnotExistsException("User was not found");
        }
    }

}
