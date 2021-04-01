package com.chitra.chitralimbuapi;

import com.chitra.TestMain;
import com.chitra.cleanup.CleanUp;
import com.chitra.domain.git.GitRepository;
import com.chitra.repository.git.GitRepositoryContentsRepository;
import com.chitra.repository.git.GitRepositoryRecursiveRepository;
import com.chitra.repository.git.GitRepositoryRepository;
import com.chitra.service.utils.GenericUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = TestMain.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GitControllerTest {

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
    private GitRepositoryRepository gitRepository;

    @Autowired
    private GitRepositoryContentsRepository gitContentsRepository;

    @Autowired
    private GitRepositoryRecursiveRepository gitRecursiveRepository;

    @Autowired
    private CleanUp cleanUp;

    private GitRepository repo;

    @Test
    public void testGetRepository() throws Exception {
        String auth = String.format("%s%s:%s", testUsernameAdmin,TestMain.EPOCH,testPassword);
        mockMvc.perform(get("/api/git/"+testGitRepo)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthorized() throws Exception {
        String auth = String.format("%s%s:%s", testUsernameUser,TestMain.EPOCH,testPassword);
        mockMvc.perform(post("/api/git/new/"+testGitRepo)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testNewAndRefreshRepo() throws Exception {
        String auth = String.format("%s%s:%s", testUsernameAdmin,TestMain.EPOCH,testPassword);
        MvcResult result = mockMvc.perform(post("/api/git/new/"+testGitRepo)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + genericUtils.b64Encode(auth))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        repo = gson.fromJson(resultJson, new TypeToken<GitRepository>(){}.getType());
        assertEquals(repo.getName(), "JSSH");
        assertEquals(repo.getHtml_url(), "https://github.com/chitralimbu/JSSH");
    }

    @AfterAll
    public void cleanUp(){
        cleanUp.deleteRepo(repo);
    }
}
