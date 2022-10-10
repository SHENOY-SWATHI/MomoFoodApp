package com.momo.cartService.Model;

import com.momo.cartService.Util.BooleanToStringConverter;
import lombok.Data;

import javax.persistence.*;

@Data
public class MenuItemDAO {
    private long menuId;
    private String menuItemId;
    private String itemName;
    private String description;
    private double cost;
    private String itemType;
    @Convert(converter = BooleanToStringConverter.class)
    private boolean availability;
}

