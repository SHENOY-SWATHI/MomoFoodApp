package com.menu.service.Controller;

import com.menu.service.Model.ItemIdentifier;
import com.menu.service.Model.MenuItem;
import com.menu.service.Service.MenuService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@Data
@RequestMapping("/menuService")
public class MomoMenuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MomoMenuController.class);

    @Autowired
    MenuService menuService;

    @GetMapping(value = "/getMenuItems/admin/all")
    public List<MenuItem> getAllMenuItemsAdmin(){
        LOGGER.info("Entered MomoMenuController.getAllMenuItemsAdmin()");
        return menuService.getAllItems();
    }

    @GetMapping(value = "/getMenuItems/all")
    public List<MenuItem> getAllMenuItems(){
        LOGGER.info("Entered MomoMenuController.getAllMenuItems()");
        return menuService.getAllItemsActive();
    }

    @GetMapping(value = "/getItembyMenuId/{menuId}")
    public MenuItem getItemByMenuId(@PathVariable String menuId){
        LOGGER.info("Entered MomoMenuController.getItemByMenuId()");
        return menuService.getItemByMenuId(menuId);
    }

    @GetMapping(value = "/getItemById/{id}")
    public MenuItem getMenuItems(@PathVariable long id){
        LOGGER.info("Entered MomoMenuController.getMenuItems()");
        return menuService.getItemById(id);
    }

    @GetMapping(value = "/getMenuItemsType/{type}")
    public List<MenuItem> getMenuItemsByType(@PathVariable String type){
        LOGGER.info("Entered MomoMenuController.getMenuItemsByType()");
        return menuService.getItemByType(type);
    }

    @GetMapping(value = "/getMenuItemsAvail/{availability}")
    public List<MenuItem> getMenuItemsByAvailability(@PathVariable String availability){
        LOGGER.info("Entered MomoMenuController.getMenuItemsByAvailability()");
        return menuService.getItemByAvailability(availability);
    }

    @PostMapping(value = "/getMenuItems/admin/addItem")
    public ResponseEntity<MenuItem> addItem(@RequestBody @NotNull MenuItem menuItem){
        LOGGER.info("Entered MomoMenuController.addItem()");
        return new ResponseEntity<>(menuService.addItem(menuItem), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/updateItemAvailability")
    public MenuItem updateItemAvailability(@RequestBody @NotNull ItemIdentifier itemAvailability){
        LOGGER.info("Entered MomoMenuController.updateUserAddress()");
        return menuService.updateItemAvailability(itemAvailability);
    }

    @PostMapping(value = "/admin/deleteMenuItems")
    public String deleteItem(@RequestBody @NotNull ItemIdentifier itemIdentifier){
        LOGGER.info("Entered MomoMenuController.deleteItem()");
        return menuService.deleteItem(itemIdentifier);
    }

}
