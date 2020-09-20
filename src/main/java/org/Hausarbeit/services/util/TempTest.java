package org.Hausarbeit.services.util;

import org.Hausarbeit.model.dao.RegisterDAO;
import org.Hausarbeit.model.dao.RoleDAO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.control.LoginControl;
import org.Hausarbeit.process.control.ReservierungControl;
import org.Hausarbeit.process.control.SearchControl;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.NoSuchUserOrPassword;
import org.Hausarbeit.services.db.JDBCConnection;

import java.sql.SQLException;
import java.util.List;

public class TempTest {
    public static void main(String[] args) {
        UserDTO user = new UserDTO();

        user.setPassword("Bananensaft");
        user.setEmail("test2@test.de");
        user.setNachname("Apfel");
        user.setVorname("Saft");
        user.setAnrede("Heli");

//        System.out.println(user.getPassword());
//        System.out.println(user.checkpassword("Bananensaft"));
//        System.out.println(user.getId());
        //RegisterDAO.getInstance().addUser(user);
//        RegisterDAO.getInstance().deleteUser(user);
//        System.out.println(RoleDAO.getInstance().getRoleFromID(1));
//        try {
//            LoginControl.getInstance().checkAuthentification("test@test.test", "test");
//        } catch (NoSuchUserOrPassword noSuchUserOrPassword) {
//            noSuchUserOrPassword.printStackTrace();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        user.setRolle(Roles.ENDKUNDE);
        user.setId(31);
        AutoDTO autoDTO = new AutoDTO();
        autoDTO.setId(3);
        try {
            System.out.println(ReservierungControl.getInstance().userCanReserve(user, autoDTO));
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        List<AutoDTO> autos = null;
//        try {
//            autos = SearchControl.getInstance().getAnzeigenForSearch("AUA A6", null);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        for (AutoDTO auto :autos) {
//            System.out.println(auto);
//        }
    }
}
