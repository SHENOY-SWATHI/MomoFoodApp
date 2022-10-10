package com.food.app.momo;

import com.food.app.momo.Controller.MomoController;
import com.food.app.momo.Exceptions.UserDoesnotExistsException;
import com.food.app.momo.Model.User;
import com.food.app.momo.Repository.UserRepo;
import com.food.app.momo.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodAppMoMoJunctionApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(FoodAppMoMoJunctionApplicationTests.class);

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepo userRepo;

	Exception exception;

	public User creatingUser(){
		LOGGER.info("Creating User for Test");
		User user = new User();
		user.setId(1L);
		user.setFirstName("firstTest");
		user.setLastName("lastName");
		user.setEmailId("test@test.com");
		user.setPassword("1234test");
		user.setPhoneNumber(1234567890);
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		return user;
	}

	@Test
	public void getAllUserTest() throws Exception {
		LOGGER.info("Entered getAllUserTest()");
		List<User> userList = new ArrayList<>();
		userList.add(creatingUser());
		given(userRepo.findAll()).willReturn(userList);

		List<User> testList = userService.getAllUser();
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
	}

	@Test
	public void getAllActiveUserTest() throws Exception {
		LOGGER.info("Entered getAllActiveUserTest()");
		List<User> userList = new ArrayList<>();
		User user = creatingUser();
		user.setActiveUser("Y");
		userList.add(user);
		given(userRepo.findActiveUser()).willReturn(userList);

		List<User> testList = userService.getAllActiveUser();
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
	}

	@Test
	public void getAllInActiveUserTest() throws Exception {
		LOGGER.info("Entered getAllInActiveUserTest()");
		List<User> userList = new ArrayList<>();
		User user = creatingUser();
		user.setActiveUser("N");
		userList.add(user);
		given(userRepo.findInactiveUser()).willReturn(userList);
		List<User> testList = userService.getAllInActiveUser();
		assertThat(testList).isNotNull();
		assertThat(testList.size()).isEqualTo(1);
	}

	@Test
	public void findByEmail() throws Exception {
		LOGGER.info("Entered findByEmail()");
		User user = creatingUser();
		given(userRepo.findByEmail(anyString())).willReturn(user);
		User testList = userService.findByEmail("test@test.com");
		assertThat(testList).isNotNull();
		assertThat(testList.getEmailId()).isEqualTo("test@test.com");
	}

	@Test
	public void findById() throws Exception {
		LOGGER.info("Entered findById()");
		User user = creatingUser();
		user.setId(1);
		given(userRepo.findById(anyLong())).willReturn(Optional.ofNullable(user));
		User testList = userService.findById(1);
		assertThat(testList).isNotNull();
		assertThat(testList.getId()).isEqualTo(1);
		given(userRepo.findById(anyLong())).willReturn( Optional.empty());
		assertThrows(UserDoesnotExistsException.class,() -> userService.findById(1));
	}

	@Test
	public void createUser() throws Exception {
		LOGGER.info("Entered createUser()");
		User user = creatingUser();
		user.setId(1);
		user.setActiveUser("Y");
		given(userRepo.save(any(User.class))).willReturn(user);
		User testList = userService.createUser(user);
		assertThat(testList).isNotNull();
		assertThat(testList.getId()).isEqualTo(1);
		assertThat(testList.getActiveUser()).isEqualTo("Y");
	}

	@Test
	public void softDeleteUser() throws Exception {
		LOGGER.info("Entered softDeleteUser()");
		String testString = userService.softDeleteUser("test@test.mail");
		assertThat(testString).isEqualTo("User Deleted");
	}

	@Test
	public void updateUser() throws Exception {
		LOGGER.info("Entered updateUser()");
		User user = creatingUser();
		user.setId(1);
		user.setActiveUser("Y");
		when(userRepo.findByEmail(anyString())).thenReturn(user);
		given(userRepo.save(any(User.class))).willReturn(user);
		User testList = userService.updateUser("test",user);
		assertThat(testList).isNotNull();
		assertThat(testList.getId()).isEqualTo(1);
		assertThat(testList.getActiveUser()).isEqualTo("Y");
	}
	@Test
	public void updateUserNotExist() throws Exception {
		LOGGER.info("Entered updateUserNotExist()");
		User user = creatingUser();
		user.setId(1);
		user.setActiveUser("Y");
		given(userRepo.findByEmail(anyString())).willReturn(null);
		assertThrows(UserDoesnotExistsException.class,() -> userService.updateUser("test",user));
	}

}
