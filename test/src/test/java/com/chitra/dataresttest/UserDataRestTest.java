package com.chitra.dataresttest;

import com.chitra.TestMain;
import com.chitra.domain.user.User;
import com.chitra.repository.user.UserRepository;
import com.chitra.service.utils.GenericUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = TestMain.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDataRestTest {

    @Value("${test_username_admin}")
    private String testUsernameAdmin;

    @Value("${test_password}")
    private String testPassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenericUtils genericUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private String auth;
    private User newUser;

    @BeforeAll
    public void setup() {
        auth = String.format("%s%s:%s", testUsernameAdmin,TestMain.EPOCH,testPassword);
        newUser = User.builder()
                .username("NewTestUser")
                .password("NewPassword")
                .accountExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    @Test
    public void addNewUserGetAndDeleteTest() throws Exception {

        String newUserJson = gson.toJson(newUser);
        log.info("NewUserJson:\n" + newUserJson);
        mockMvc.perform(post("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        MvcResult searchByUsername = mockMvc.perform(get("/api/users/search/findByUsername?username=NewTestUser")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        String searchUser = searchByUsername.getResponse().getContentAsString();
        log.info("SearchUser:\n" + searchUser);
        User userResult = gson.fromJson(searchUser, new TypeToken<User>() {}.getType());
        assertEquals(newUser.getUsername(), userResult.getUsername());
        deleteUser(userResult);
    }


    @Test
    public void userExistsTest() throws Exception {
        String newUserJson = gson.toJson(newUser);
        mockMvc.perform(post("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        log.info("adding duplicate user");
        mockMvc.perform(post("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError());

        deleteUser(newUser);
    }

    private void deleteUser(User user) throws Exception {
        User fromDb = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new IllegalStateException());
        log.info("Deleting user");
        mockMvc.perform(delete("/api/users/" + fromDb.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateDetails(){

    }

    @AfterAll
    public void cleanUp(){
    }
}
