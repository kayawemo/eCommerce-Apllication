package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp () {

        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);


    }

    @Test
    public void  createUserHappyPath() throws Exception {

        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());

    }

    @Test
    public void testGetUserByUsername () {

        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setUsername("firstUser");

        final ResponseEntity<User> res = userController.findByUserName(newUser.getUsername());

        assertNotNull(res);


    }

    @Test
    public void testGetUserByUserId () {

//        CreateUserRequest r1 = new CreateUserRequest();
//        r1.setUsername("test");
//        r1.setPassword("testPassword");
//        r1.setConfirmPassword("testPassword");

        //final ResponseEntity<User> res = userController.findById(0L);

        User u1 = new User();
        userRepository.save(u1);
        User u2 = new User();
        userRepository.save(u2);
        User u3 = new User();
        userRepository.save(u3);

        assertEquals(0, u1.getId());
        assertEquals(0, u2.getId());
        assertEquals(0, u3.getId());

    }
}
