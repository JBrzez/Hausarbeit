package org.Hausarbeit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.Hausarbeit.model.dao.RoleDAO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class RoleDTOTest {#

    private RoleDAO roleDAO;

    @Test
    public void testCreate() {
        roleDAO =  RoleDAO.getInstance();
        assertNotNull(roleDAO);
    }
    @Test
    public void setStudent() {
        UserDTO userDTO = new UserDTO();
        assertFalse(RoleDAO.getInstance().setRolesForEndkunde(userDTO));

    }
    @Test
    public void setUnternehmen() {
        UserDTO userDTO = new UserDTO();
        assertFalse(RoleDAO.getInstance().setRolesForVertriebler(userDTO));
    }
    @Test
    public void testDelete() {

    }

}