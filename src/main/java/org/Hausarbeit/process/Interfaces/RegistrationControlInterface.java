package org.Hausarbeit.process.Interfaces;

import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.EmailInUseException;
import org.Hausarbeit.process.exceptions.EmptyFieldException;
import org.Hausarbeit.process.exceptions.NoEqualPasswordException;

import java.sql.SQLException;

public interface RegistrationControlInterface {

    void checkValid(String email, boolean emailBool, String password1, String password2, boolean password1Bool, boolean password2Bool, boolean checkBox) throws NoEqualPasswordException, DatabaseException, EmailInUseException, EmptyFieldException, SQLException;

    //User registrieren
    void registerUser(String email, String password, String regAs, String vorname, String nachname, String anrede ) throws DatabaseException, SQLException;

    //User Löschen
    void deleteUser(UserDTO userDTO);
}
