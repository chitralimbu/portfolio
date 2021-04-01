package com.chitra.config;

import com.chitra.TestMain;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestMain.class)
public class HelperBeanTest {

    @Autowired
    private Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testNonNull(){
        assertNotNull(gson);
        assertNotNull(restTemplate);
    }
}
