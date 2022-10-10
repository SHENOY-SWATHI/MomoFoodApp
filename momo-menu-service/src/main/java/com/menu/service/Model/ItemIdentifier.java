package com.menu.service.Model;

import com.menu.service.Utils.BooleanToStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;

@Data
public class ItemIdentifier {
    @Column
    @NotBlank
    private long menuId;
    @Column
    @NotBlank
    private String menuItemId;
    @Column
    @Convert(converter = BooleanToStringConverter.class)
    private boolean availability;
}
