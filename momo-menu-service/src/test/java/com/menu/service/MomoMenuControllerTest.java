package com.menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menu.service.Controller.MomoMenuController;
import com.menu.service.Model.ItemIdentifier;
import com.menu.service.Model.MenuItem;
import com.menu.service.Service.MenuService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MomoMenuController.class)
public class MomoMenuControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MomoMenuControllerTest.class);

    @Autowired
    private MomoMenuController momoMenuController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Test
    public void getAllMenuItemsAdminTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getAllMenuItemsAdminTest()");
        MenuItem menuItemDAO = getMenuItemData();
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(menuItemDAO);

        when(menuService.getAllItems()).thenReturn(menuItemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getMenuItems/admin/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1));

    }

    @Test
    public void getAllMenuItemsTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getAllMenuItemsTest()");
        MenuItem menuItemDAO = getMenuItemData();
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(menuItemDAO);

        when(menuService.getAllItemsActive()).thenReturn(menuItemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getMenuItems/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1));

    }

    @Test
    public void getItemByMenuIdTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getItemByMenuIdTest()");
        MenuItem menuItemDAO = getMenuItemData();

        when(menuService.getItemByMenuId(anyString())).thenReturn(menuItemDAO);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getItembyMenuId/test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.itemName")).value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.menuId")).value(1));

    }

    @Test
    public void getMenuItemsTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getMenuItemsTest()");
        MenuItem menuItemDAO = getMenuItemData();

        when(menuService.getItemById(anyLong())).thenReturn(menuItemDAO);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getItemById/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.itemName")).value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.menuId")).value(1));

    }

    @Test
    public void getMenuItemsByTypeTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getMenuItemsByTypeTest()");
        MenuItem menuItemDAO = getMenuItemData();
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(menuItemDAO);
        String type = "Veg";
        when(menuService.getItemByType(type)).thenReturn(menuItemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getMenuItemsType/Veg"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1));

    }

    @Test
    public void getMenuItemsByAvailabilityTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.getMenuItemsByAvailabilityTest()");
        MenuItem menuItemDAO = getMenuItemData();
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(menuItemDAO);

        when(menuService.getItemByAvailability("Y")).thenReturn(menuItemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/menuService/getMenuItemsAvail/Y"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1));

    }

    @Test
    public void addItemTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.addItemTest()");
        MenuItem menuItemDAO = getMenuItemData();

        when(menuService.addItem(any(MenuItem.class))).thenReturn(menuItemDAO);

        mockMvc.perform(MockMvcRequestBuilders.post("/menuService/getMenuItems/admin/addItem")
                        .content(new ObjectMapper().writeValueAsString(menuItemDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.menuId")).value(1));

    }

    @Test
    public void updateItemAvailabilityTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.updateItemAvailabilityTest()");
        MenuItem menuItemDAO = getMenuItemData();
        ItemIdentifier itemIdentifier = new ItemIdentifier();
        itemIdentifier.setMenuItemId("T101");
        itemIdentifier.setMenuId(1);
        itemIdentifier.setAvailability(true);

        when(menuService.updateItemAvailability(any(ItemIdentifier.class))).thenReturn(menuItemDAO);

        mockMvc.perform(MockMvcRequestBuilders.post("/menuService/admin/updateItemAvailability")
                        .content(new ObjectMapper().writeValueAsString(itemIdentifier))
                        .content(new ObjectMapper().writeValueAsString(menuItemDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.menuId")).value(1));
    }

    @Test
    public void deleteItemTest() throws Exception {
        LOGGER.info("Entered MomoMenuControllerTest.deleteItemTest()");
        MenuItem menuItemDAO = getMenuItemData();
        ItemIdentifier itemIdentifier = new ItemIdentifier();
        itemIdentifier.setMenuItemId("T101");
        itemIdentifier.setMenuId(1);
        itemIdentifier.setAvailability(true);

        when(menuService.updateItemAvailability(any(ItemIdentifier.class))).thenReturn(menuItemDAO);

        mockMvc.perform(MockMvcRequestBuilders.post("/menuService/admin/deleteMenuItems")
                        .content(new ObjectMapper().writeValueAsString(itemIdentifier))
                        .content(new ObjectMapper().writeValueAsString(menuItemDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private MenuItem getMenuItemData() {
        LOGGER.info("Creating Dummy Menu Data");
        MenuItem menuItemDAO = new MenuItem();
        menuItemDAO.setItemName("test");
        menuItemDAO.setCost(40.00);
        menuItemDAO.setMenuItemId("T101");
        menuItemDAO.setMenuId(1);
        menuItemDAO.setDescription("testDescription");
        menuItemDAO.setAvailability(true);
        return menuItemDAO;
    }

}