package com.menu.service.Repository;

import com.menu.service.Model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepo extends JpaRepository<MenuItem, Long> {

    @Query(value = "SELECT * FROM menu_item m where m.menu_item_id = ?1", nativeQuery = true)
    Optional<MenuItem> findByMenuId(String menuId);
    @Query(value = "SELECT * FROM menu_item m where m.item_type = ?1", nativeQuery = true)
    List<MenuItem> findByType(String type);
    @Query(value = "SELECT * FROM menu_item m where m.availability = ?1", nativeQuery = true)
    List<MenuItem> findByAvailability(String availability);
    @Query(value = "SELECT * FROM menu_item m where m.menu_id = ?1 && m.menu_item_id = ?2", nativeQuery = true)
    MenuItem findItemById(long menuId, String menuItemId);
    @Transactional
    @Modifying
    @Query(value = "DELETE from menu_item m where menu_id = ?1 && menu_item_id = ?2", nativeQuery = true)
    void deleteItem(long menuId, String menuItemId);
}
