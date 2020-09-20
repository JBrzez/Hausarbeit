package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.ReservierungDAO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class ReservierungDAOTest {

    @Test
    public void testCreate() {
        ReservierungDAO rservierungDAO = ReservierungDAO.getInstance();
        assertNotNull(rservierungDAO);
    }
    @Test
    public void createReservierung() {
        UserDTO userDTO = new UserDTO();
        AutoDTO auto = new AutoDTO();
        assertFalse(ReservierungDAO.getInstance().createReservierung(userDTO,auto));
    }



}