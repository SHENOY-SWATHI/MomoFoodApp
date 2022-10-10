package com.menu.service.Service;

import com.menu.service.Model.ItemIdentifier;
import com.menu.service.Model.MenuItem;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<MenuItem> getAllItems();

    MenuItem getItemByMenuId(String menuId);

    MenuItem getItemById(long id);

    List<MenuItem> getItemByType(String type);

    List<MenuItem> getItemByAvailability(String availability);

    MenuItem addItem(MenuItem menuItem);

    MenuItem updateItemAvailability(ItemIdentifier itemIdentifier);

    String deleteItem(ItemIdentifier itemIdentifier);

    List<MenuItem> getAllItemsActive();
}
