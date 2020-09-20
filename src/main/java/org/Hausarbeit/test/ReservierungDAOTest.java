package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.ReservierungDAO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
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
    public void createBewerbung() {
        UserDTO userDTO = new UserDTO();
        EndkundeDTO endkundeDTO = new EndkundeDTO(userDTO);
        assertFalse(ReservierungDAO.getInstance().createReservierung("Lachs",endkundeDTO));
    }



}