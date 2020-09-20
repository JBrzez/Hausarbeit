package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.AutoDAO;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class AutoDAOTest {

    @Test
    public void testCreate() {
        AutoDAO stellenanzeigeDAO =  AutoDAO.getInstance();
        assertNotNull(stellenanzeigeDAO);
    }


}