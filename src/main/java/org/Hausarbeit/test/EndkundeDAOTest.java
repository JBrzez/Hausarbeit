package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.EndkundeDAO;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class EndkundeDAOTest {

    private EndkundeDAO endkundeDAO;

    @Test
    public void testCreate() {
        endkundeDAO =  endkundeDAO.getInstance();
        assertNotNull(endkundeDAO);
    }
    @Test
    public void testRead() {

    }



}