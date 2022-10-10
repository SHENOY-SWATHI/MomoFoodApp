package com.momo.orderService.Repository;

import com.momo.orderService.Model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory,Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE order_history o SET o.order_status ='CANCELLED' where Date(o.order_date) = ?1",nativeQuery = true)
    void updateByDate(String currentDate);

    @Query(value = "SELECT * from order_history o where o.user_email_id = ?1",nativeQuery = true)
    List<OrderHistory> getAllOrders(String emailId);
}
