package org.Hausarbeit.services.util;

import org.Hausarbeit.model.dao.RegisterDAO;
import org.Hausarbeit.model.dao.RoleDAO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.services.db.JDBCConnection;

public class TempTest {
    public static void main(String[] args) {
        UserDTO user = new UserDTO();
        user.setPassword("Bananensaft");
        user.setEmail("test@test.de");
        user.setNachname("Kevin");
        user.setVorname("Gully");
        user.setAnrede("Heli");
        user.setRolle(Roles.ENDKUNDE);

//        System.out.println(user.getPassword());
//        System.out.println(user.checkpassword("Bananensaft"));

        RegisterDAO.getInstance().addUser(user);
//        System.out.println(RoleDAO.getInstance().getRoleFromID(1));

    }
}
