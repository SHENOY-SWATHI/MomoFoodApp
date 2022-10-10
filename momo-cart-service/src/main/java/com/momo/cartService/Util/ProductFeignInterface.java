package com.momo.cartService.Util;

import com.momo.cartService.Model.MenuItemDAO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="momo-menu-service",url="localhost:8081/menuService")
public interface ProductFeignInterface {

    @GetMapping("/getItembyMenuId/{menuId}")
    MenuItemDAO getItemByMenuId(@PathVariable ("menuId") String menuId);
}
