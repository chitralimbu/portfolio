package com.chitra.config;

import com.chitra.TestMain;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
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
