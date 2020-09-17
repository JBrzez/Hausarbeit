package org.Hausarbeit.services.util;

import org.Hausarbeit.model.dao.RegisterDAO;
import org.Hausarbeit.model.dao.RoleDAO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.services.db.JDBCConnection;

public class TempTest {
    public static void main(String[] args) {
        UserDTO user = new UserDTO();

        user.setPassword("Bananensaft");
        user.setEmail("test2@test.de");
        user.setNachname("Apfel");
        user.setVorname("Saft");
        user.setAnrede("Heli");
        user.setRolle(Roles.ENDKUNDE);

//        System.out.println(user.getPassword());
//        System.out.println(user.checkpassword("Bananensaft"));
        System.out.println(user.getId());
        //RegisterDAO.getInstance().addUser(user);
        RegisterDAO.getInstance().deleteUser(user);
//        System.out.println(RoleDAO.getInstance().getRoleFromID(1));

    }
}
