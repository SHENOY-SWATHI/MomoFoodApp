package com.food.app.momo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.app.momo.Controller.MomoController;
import com.food.app.momo.Model.SignIn;
import com.food.app.momo.Model.User;
import com.food.app.momo.Repository.UserRepo;
import com.food.app.momo.Service.UserService;
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
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MomoController.class)
public class MomoControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MomoController.class);

    @Autowired
    private MomoController momoController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void getAllUserTest() throws Exception {
        LOGGER.info("Entered MomoControllerTest.getAllUserTest()");
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        userList.add(user);


        when(userService.getAllUser()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/userService/admin/getAllUser"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].firstName")).value("firstTest"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].lastName")).value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].emailId")).value("test@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].password")).value("1234test"));

    }

    @Test
    public void getActiveUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.getActiveUser()");
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setActiveUser("Y");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        userList.add(user);

        when(userService.getAllActiveUser()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/userService/admin/getAllActiveUser"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].firstName")).value("firstTest"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].lastName")).value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].emailId")).value("test@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].password")).value("1234test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].activeUser")).value("Y"));
    }

    @Test
    public void getInActiveUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.getInActiveUser()");
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setActiveUser("N");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        userList.add(user);

        when(userService.getAllInActiveUser()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/userService/admin/getAllInactiveUser"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].firstName")).value("firstTest"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].lastName")).value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].emailId")).value("test@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].password")).value("1234test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$[0].activeUser")).value("N"));
    }

    @Test
    public void getUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.getUser()");
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());


        when(userService.findByEmail(anyString())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/userService/getUserEmail/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("test@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234test"));

    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.info("Entered MomoControllerTest.getUserById()");
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());


        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/userService/getUserId/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("test@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234test"));

    }
    @Test
    public void signUp() throws Exception {
        LOGGER.info("Entered MomoControllerTest.signUp()");
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());


        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/userService/signUp")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void softDeleteUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.softDeleteUser()");
        mockMvc.perform(MockMvcRequestBuilders.get("/userService/deleteUser/test@test.mail"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAllUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.deleteAllUser()");
        mockMvc.perform(MockMvcRequestBuilders.get("/userService/deleteAllUser"))
                .andExpect(MockMvcResultMatchers.content().string("All Users are Deleted"));
    }

    @Test
    public void forgotPassword() throws Exception {
        LOGGER.info("Entered MomoControllerTest.forgotPassword()");
        User user = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/userService/forgotPassword")
                .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.info("Entered MomoControllerTest.updateUser()");
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());


        when(userService.updateUser(anyString(),any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/userService/updateUser/test@test.mail")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());
    }
    @Test
    public void signIn() throws Exception {
        LOGGER.info("Entered MomoControllerTest.signIn()");
        SignIn signIn = new SignIn();
        signIn.setId(1);
        signIn.setEmailId("test@test.mail");
        signIn.setPassword("1234qwer");
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstTest");
        user.setLastName("lastName");
        user.setEmailId("test@test.com");
        user.setPassword("1234test");
        user.setPhoneNumber(1234567890);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        mockMvc.perform(MockMvcRequestBuilders.post("/userService/signIn")
                        .content(new ObjectMapper().writeValueAsString(signIn))
                        .content(when(userService.findByEmail(anyString())).thenReturn(user).toString())
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

