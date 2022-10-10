package com.momo.orderService.Repository;

import com.momo.orderService.Model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderDetails,Long> {
    @Query(value = "SELECT * from order_details o where o.user_id =?1",nativeQuery = true)
    OrderDetails retriveId(String userEmailId);

    @Query(value = "SELECT * from order_details o where o.order_scheduled =?1 && o.scheduled_date = ?2 && o.status != 'ACTIVE'",nativeQuery = true)
    List<OrderDetails> findAllScheduled(char scheduler, LocalDate s);

    @Transactional
    @Modifying
    @Query(value = "UPDATE order_details o SET o.status ='ACTIVE' where o.order_id = ?1",nativeQuery = true)
    void updateStatus(long orderId);
}
