package com.menu.service.Service;

import com.menu.service.Controller.MomoMenuController;
import com.menu.service.Exceptions.ItemNotFound;
import com.menu.service.Model.ItemIdentifier;
import com.menu.service.Model.MenuItem;
import com.menu.service.Repository.MenuRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Autowired
    MenuRepo menuRepo;

    @Override
    public List<MenuItem> getAllItems() {
        LOGGER.info("Entered MenuServiceImpl.getAllItems()");
        return menuRepo.findAll();
    }

    @Override
    public MenuItem getItemByMenuId(String menuItemId) {
        LOGGER.info("Entered MenuServiceImpl.getItemByMenuId()");
        return menuRepo.findByMenuId(menuItemId).orElseThrow(() ->new ItemNotFound("Item not Found in Inventory"));
    }

    @Override
    public MenuItem getItemById(long menuid) {
        LOGGER.info("Entered MenuServiceImpl.getItemById()");
        return menuRepo.findById(menuid).orElseThrow(() ->new ItemNotFound("Item not Found in Inventory"));
    }

    @Override
    public List<MenuItem> getItemByType(String type) {
        LOGGER.info("Entered MenuServiceImpl.getItemByType()");
        return menuRepo.findByType(type);
    }

    @Override
    public List<MenuItem> getItemByAvailability(String availability) {
        LOGGER.info("Entered MenuServiceImpl.getItemByAvailability()");
        return menuRepo.findByAvailability(availability);
    }

    @Override
    public MenuItem addItem(MenuItem menuItem) {
        LOGGER.info("Entered MenuServiceImpl.addItem()");
        return menuRepo.save(menuItem);
    }

    @Override
    public MenuItem updateItemAvailability(ItemIdentifier itemIdentifier) {
        LOGGER.info("Entered MenuServiceImpl.updateItemAvailability()");
        MenuItem menuItem = menuRepo.findItemById(itemIdentifier.getMenuId(), itemIdentifier.getMenuItemId());
        try
        {
            menuItem.setAvailability(itemIdentifier.isAvailability());
            return menuRepo.save(menuItem);
        } catch (NullPointerException e) {
            throw new ItemNotFound("Item not Found in Inventory");
        }
    }

    @Override
    public String deleteItem(ItemIdentifier itemIdentifier) {
        LOGGER.info("Entered MenuServiceImpl.deleteItem()");
        MenuItem menuItem = menuRepo.findItemById(itemIdentifier.getMenuId(), itemIdentifier.getMenuItemId());
        try
        {
            menuRepo.deleteItem(itemIdentifier.getMenuId(), itemIdentifier.getMenuItemId());
            return "item in MenuList Deleted";
        } catch (NullPointerException e) {
            throw new ItemNotFound("Item not Found in Inventory");
        }
    }

    @Override
    public List<MenuItem> getAllItemsActive() {
        LOGGER.info("Entered MenuServiceImpl.getAllItemsActive()");
        return getItemByAvailability("Y");
    }

}
