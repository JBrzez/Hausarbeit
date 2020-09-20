package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.UserDAO;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


public class UserDTOTest {
    private UserDAO userDAO;

    @Test
    public void testCreate() {
        userDAO =  UserDAO.getInstance();
        assertNotNull(userDAO);
    }

}