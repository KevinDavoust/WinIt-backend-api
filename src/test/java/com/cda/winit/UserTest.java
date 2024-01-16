package com.cda.winit;

import com.cda.winit.auth.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testUserFirstname() {
        String firstname = "elea";
        user.setFirstname(firstname);
        assertEquals(firstname, user.getFirstname());
    }

    @Test
    public void testUserLastname() {
        String lastname = "voliotis";
        user.setLastname(lastname);
        assertEquals(lastname, user.getLastname());
    }

    @Test
    public void testUserUsername() {
        String username = "elea";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testUserEmail() {
        String email = "elea@gmail.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserPassword() {
        String password = "1234";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testUserIsEnabled() {
        Boolean isEnabled = false;
        user.setEnabled(isEnabled);
        assertEquals(isEnabled, user.getIsEnabled());
    }
}
