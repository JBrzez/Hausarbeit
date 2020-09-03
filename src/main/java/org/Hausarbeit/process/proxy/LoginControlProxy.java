package org.Hausarbeit.process.proxy;

import org.Hausarbeit.process.Interfaces.LoginControlInterface;
import org.Hausarbeit.process.control.LoginControl;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.NoSuchUserOrPassword;

import java.sql.SQLException;

public class LoginControlProxy implements LoginControlInterface {
    private static LoginControlProxy loginControl = null;

    private LoginControlProxy(){
    }
    public static LoginControlProxy getInstance(){
        if(loginControl == null){
            loginControl = new LoginControlProxy();
        }
        return loginControl;
    }

    public void checkAuthentification(String email, String password) throws NoSuchUserOrPassword, DatabaseException, SQLException {
        LoginControl.getInstance().checkAuthentification(email, password);
    }

    public void logoutUser() {
        LoginControl.getInstance().logoutUser();
    }
}
