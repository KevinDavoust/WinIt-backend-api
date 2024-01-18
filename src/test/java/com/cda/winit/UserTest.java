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
        user.setFirstName(firstname);
        assertEquals(firstname, user.getFirstName());
    }

    @Test
    public void testUserLastname() {
        String lastname = "voliotis";
        user.setLastName(lastname);
        assertEquals(lastname, user.getLastName());
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
}
