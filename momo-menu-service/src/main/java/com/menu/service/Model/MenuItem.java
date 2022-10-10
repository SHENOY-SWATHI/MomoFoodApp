package com.menu.service.Model;

import com.menu.service.Utils.BooleanToStringConverter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"menuItemId"})
})
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long menuId;
    @Column
    @NotBlank
    private String menuItemId;
    @Column
    @NotBlank
    private String itemName;
    @Column
    private String description;
    @Column
    private double cost;
    @Column
    @NotBlank
    private String itemType;
    @Column
    @Convert(converter = BooleanToStringConverter.class)
    private boolean availability;
}
