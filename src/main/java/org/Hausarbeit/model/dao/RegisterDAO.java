package org.Hausarbeit.model.dao;

import org.Hausarbeit.model.objects.dto.UserDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterDAO extends AbstractDAO {
    private static RegisterDAO dao = null;

    private RegisterDAO() {

    }

    public static RegisterDAO getInstance() {
        if (dao == null) {
            dao = new RegisterDAO();
        }
        return dao;
    }

    public boolean addUser(UserDTO userDTO) {
        String sql = "INSERT INTO carlook.user (id, email, password_hash, vorname, nachname, anrede, rolle_id) VALUES (default,?,?,?,?,?,?)";
        PreparedStatement statement = this.getPreparedStatement(sql);

        try {
            statement.setString(1, userDTO.getEmail());
            statement.setString(2, userDTO.getPassword());
            statement.setString(3, userDTO.getVorname());
            statement.setString(4, userDTO.getNachname());
            statement.setString(5, userDTO.getAnrede());
            statement.setInt(6, RoleDAO.getInstance().getIDFromRole(userDTO.getRolle()));
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    //Lösche User
    public void deleteUser(UserDTO userDTO) {
        String sql = "DELETE " +
                "FROM carlook.user u " +
                "WHERE u.id = ? ;";
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            statement.setInt(1, userDTO.getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger((RegisterDAO.class.getName())).log(Level.SEVERE, null, ex);
        }
    }
}
