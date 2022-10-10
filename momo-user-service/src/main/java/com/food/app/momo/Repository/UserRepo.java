package com.food.app.momo.Repository;

import com.food.app.momo.Model.SignIn;
import com.food.app.momo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u where u.email_Id = ?1",nativeQuery = true)
    User findByEmail(String emailId);
    @Query(value = "SELECT u.* FROM user u where u.active_user = 'Y'", nativeQuery = true)
    List<User> findActiveUser();
    @Query(value = "SELECT u.* FROM user u where u.active_user = 'N'", nativeQuery = true)
    List<User> findInactiveUser();
    @Transactional
    @Modifying
    @Query(value = "UPDATE user u SET u.active_user = ?1, u.updated_date =?2 where u.email_Id = ?3 ", nativeQuery = true)
    void update(String n, Date date, String emailId);
    @Query(value = "SELECT * FROM user u where u.email_Id = ?1 && u.id = ?2",nativeQuery = true)
    User findByIdEmail(String emailId, long id);
}
