package com.momo.cartService.Repository;

import com.momo.cartService.Model.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartDetails,Long> {


    @Query(value = "Select * from cart_details c where c.user_email_id = ?1",nativeQuery = true)
    List<CartDetails> findAllById(String emailId);
    @Transactional
    @Modifying
    @Query(value = "DELETE from cart_details c where c.menu_item_id = ?1 && c.user_email_id = ?2", nativeQuery = true)
    void removeItem(String menuItemId, String userEmailId);
    @Query(value = "Select * from cart_details c where c.menu_item_id = ?1 && c.user_email_id = ?2",nativeQuery = true)
    CartDetails getItem(String menuItemId, String userEmailId);

    @Transactional
    @Modifying
    @Query(value = "DELETE from cart_details c where c.user_email_id = ?1", nativeQuery = true)
    void removeAllItem(String userEmailId);
}
