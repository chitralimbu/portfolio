package com.chitra.dataresttest;

import com.chitra.TestMain;
import com.chitra.cleanup.CleanUp;
import com.chitra.domain.git.GitRepository;
import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import com.chitra.service.utils.GenericUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = TestMain.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataRestTest {

    @Value("${test_git_repo}")
    private String testGitRepo;

    @Value("${test_username_admin}")
    private String testUsernameAdmin;

    @Value("${test_username_user}")
    private String testUsernameUser;

    @Value("${test_password}")
    private String testPassword;

    @Autowired
    private GenericUtils genericUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CleanUp cleanUp;

    private User adminUser;
    private Role roleAdmin;
    private String auth;
    private GitRepository repo;


    @BeforeAll
    public void setup() throws Exception {
        roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
        adminUser = userRepository.findAllByRolesId(roleAdmin.getId())
                                    .orElseThrow(() -> new UsernameNotFoundException("No users found"))
                                    .get(0);
        auth = String.format("%s%s:%s", testUsernameAdmin,TestMain.EPOCH,testPassword);
        MvcResult result = mockMvc.perform(post("/api/git/new/"+testGitRepo)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        repo = gson.fromJson(resultJson, new TypeToken<GitRepository>(){}.getType());
    }

    @Test
    public void addNewUserTest(){

    }

    @AfterAll
    public void cleanUp(){
        cleanUp.deleteRepo(repo);
    }

}
