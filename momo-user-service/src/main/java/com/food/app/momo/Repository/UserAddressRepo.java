//package com.food.app.momo.Repository;
//
//import com.food.app.momo.Model.UserAddress;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Repository
//public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
//    @Query(value = "SELECT * FROM user_address u where u.user_id = ?1",nativeQuery = true)
//    UserAddress findByUserId(String user_id);
//    @Modifying
//    @Query(value = "DELETE user_address u where u.user_id = ?1 ", nativeQuery = true)
//    void deleteByUserId(String user_id);
//}
