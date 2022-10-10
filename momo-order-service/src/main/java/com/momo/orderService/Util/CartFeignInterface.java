package com.momo.orderService.Util;

import com.momo.orderService.Model.CartDisplayDAO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="momo-cart-service",url="localhost:8082/cartService")
public interface CartFeignInterface {

    @GetMapping("/viewCart/{emailId}")
    CartDisplayDAO viewCart(@PathVariable ("emailId") String emailId);

    @GetMapping("/removeALL/{emailId}")
    String removeAll(@PathVariable ("emailId") String emailId);
}
