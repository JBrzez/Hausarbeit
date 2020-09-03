package org.Hausarbeit.process.Interfaces;

import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.NoSuchUserOrPassword;

import java.sql.SQLException;

public interface LoginControlInterface {

    void checkAuthentification(String email, String password) throws NoSuchUserOrPassword, DatabaseException, SQLException;
    void logoutUser();
}
