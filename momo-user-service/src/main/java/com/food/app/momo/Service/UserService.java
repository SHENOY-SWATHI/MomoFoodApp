package com.food.app.momo.Service;

import com.food.app.momo.Model.SignIn;
import com.food.app.momo.Model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    String softDeleteUser(String emailId);

    User updateUser(String emailId, User updateUser);

//    ResponseEntity<UserAddress> createAddress(UserAddress userAddress);
//
//    UserAddress updateUserAddress(String user_id, UserAddress updateUserAddress);
//
//    String DeleteUserAddress(String user_id);

    List<User> getAllUser();

    List<User> getAllActiveUser();

    List<User> getAllInActiveUser();

    User findByEmail(String emailId);

    User findById(long id);

    String forgotPassword(SignIn signIn);
}
