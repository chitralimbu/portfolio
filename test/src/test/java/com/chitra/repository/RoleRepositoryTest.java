package com.chitra.repository;

import com.chitra.TestMain;
import com.chitra.domain.role.Role;
import com.chitra.repository.role.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMain.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindAllRoleContains(){
        List<Role> allRoleContains = roleRepository.findAllByRoleContaining("USER");
        assertNotNull(allRoleContains);
        assertTrue(allRoleContains.size() == 1);
        Role userRole = allRoleContains.get(0);
        assertEquals("ROLE_USER", userRole.getRole());
    }

}
