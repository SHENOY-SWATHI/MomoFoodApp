package com.menu.service;

import com.menu.service.Exceptions.ItemNotFound;
import com.menu.service.Model.ItemIdentifier;
import com.menu.service.Model.MenuItem;
import com.menu.service.Repository.MenuRepo;
import com.menu.service.Service.MenuServiceImpl;
import com.menu.service.Utils.BooleanToStringConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class MenuServiceApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceApplicationTests.class);

	@InjectMocks
	private MenuServiceImpl menuService;

	@Mock
	private MenuRepo menuRepo;

	@InjectMocks
	BooleanToStringConverter booleanToStringConverter;

	private MenuItem getMenuItemData() {
		LOGGER.info("Creating Dummy Menu Data");
		MenuItem menuItemDAO = new MenuItem();
		menuItemDAO.setItemName("test");
		menuItemDAO.setCost(40.00);
		menuItemDAO.setMenuItemId("T101");
		menuItemDAO.setMenuId(1);
		menuItemDAO.setDescription("testDescription");
		menuItemDAO.setAvailability(true);
		menuItemDAO.setItemType("Veg");
		return menuItemDAO;
	}

	@Test
	public void getAllItemsTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getAllItemsTest()");
		List<MenuItem> menuList = new ArrayList<>();
		menuList.add(getMenuItemData());

		given(menuRepo.findAll()).willReturn(menuList);

		List<MenuItem> testList = menuService.getAllItems();
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
	}
	@Test
	public void getItemByMenuIdTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getItemByMenuIdTest()");
		MenuItem menuListDao = getMenuItemData();

		given(menuRepo.findByMenuId("T101")).willReturn(Optional.of(menuListDao));
		MenuItem testList = menuService.getItemByMenuId("T101");
		assertThat(testList).isNotNull();
		assertThat(testList.getMenuItemId()).isEqualTo("T101");
	}

	@Test
	public void getItemByIdTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getItemByIdTest()");
		MenuItem menuListDao = getMenuItemData();

		given(menuRepo.findById(anyLong())).willReturn(Optional.of(menuListDao));
		MenuItem testList = menuService.getItemById(1);
		assertThat(testList).isNotNull();
		assertThat(testList.getMenuId()).isEqualTo(1);
	}

	@Test
	public void getItemByTypeTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getItemByTypeTest()");
		MenuItem menuListDao = getMenuItemData();
		List<MenuItem> menuList = new ArrayList<>();
		menuList.add(menuListDao);

		given(menuRepo.findByType("Veg")).willReturn(menuList);
		List<MenuItem> testList = menuService.getItemByType("Veg");
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
		assertThat(testList.get(0).getMenuId()).isEqualTo(1);
	}

	@Test
	public void getAllItemsActiveTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getAllItemsActiveTest()");
		MenuItem menuListDao = getMenuItemData();
		List<MenuItem> menuList = new ArrayList<>();
		menuList.add(menuListDao);
		given(menuRepo.findByAvailability("Y")).willReturn(menuList);
		List<MenuItem> testList = menuService.getAllItemsActive();
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
		assertThat(testList.get(0).getMenuId()).isEqualTo(1);
	}

	@Test
	public void getItemByAvailabilityTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.getItemByAvailabilityTest()");
		MenuItem menuListDao = getMenuItemData();
		List<MenuItem> menuList = new ArrayList<>();
		menuList.add(menuListDao);

		given(menuRepo.findByAvailability("Y")).willReturn(menuList);
		List<MenuItem> testList = menuService.getItemByAvailability("Y");
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
		assertThat(testList.get(0).getMenuId()).isEqualTo(1);
	}

	@Test
	public void addItemTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.addItemTest()");
		MenuItem menuListDao = getMenuItemData();

		given(menuRepo.save(any(MenuItem.class))).willReturn(menuListDao);
		MenuItem testList = menuService.addItem(menuListDao);
		assertThat(testList).isNotNull();
		assertThat(testList.getMenuId()).isEqualTo(1);
	}
	@Test
	public void updateItemAvailabilityTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.updateItemAvailabilityTest()");
		MenuItem menuListDao = getMenuItemData();
		ItemIdentifier itemIdentifier = new ItemIdentifier();
		itemIdentifier.setMenuItemId("T101");
		itemIdentifier.setMenuId(1);
		itemIdentifier.setAvailability(true);

		given(menuRepo.findItemById(itemIdentifier.getMenuId(),itemIdentifier.getMenuItemId())).willReturn(menuListDao);
		given(menuRepo.save(any(MenuItem.class))).willReturn(menuListDao);
		MenuItem testList = menuService.updateItemAvailability(itemIdentifier);
		assertThat(testList).isNotNull();
		assertThat(testList.getMenuId()).isEqualTo(1);
	}
	@Test
	public void deleteItemTest() throws Exception {
		LOGGER.info("Entered MenuServiceApplicationTests.deleteItemTest()");

		ItemIdentifier itemIdentifier = new ItemIdentifier();
		itemIdentifier.setMenuItemId("T101");
		itemIdentifier.setMenuId(1);
		itemIdentifier.setAvailability(true);

		MenuItem menuListDao = getMenuItemData();
		List<MenuItem> menuList = new ArrayList<>();
		menuList.add(menuListDao);

		given(menuRepo.findItemById(itemIdentifier.getMenuId(),itemIdentifier.getMenuItemId())).willReturn(menuListDao);

		String testListString = menuService.deleteItem(itemIdentifier);
		assertThat(testListString).isNotNull();
		assertThat(testListString).isEqualTo("item in MenuList Deleted");
	}


	@Test
	public void convertToDatabaseColumnTest() throws Exception {
		LOGGER.info("Entered convertToDatabaseColumnTest()");
		String test = booleanToStringConverter.convertToDatabaseColumn(true);
		assertThat(test).isEqualTo("Y");
	}

	@Test
	public void convertToEntityAttributeTest() throws Exception {
		LOGGER.info("Entered convertToEntityAttributeTest()");
		Boolean test = booleanToStringConverter.convertToEntityAttribute("N");
		assertThat(test).isEqualTo(false);
	}

}
