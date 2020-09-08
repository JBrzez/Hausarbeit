package org.Hausarbeit.model.dao;

import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndkundeDAO extends AbstractDAO {

    private static EndkundeDAO dao = null;

    private EndkundeDAO() {

    }

    public static EndkundeDAO getInstance() {
        if (dao == null) {
            dao = new EndkundeDAO();
        }
        return dao;
    }

    public boolean updateEndkunde(EndkundeDTO endkundeDTO) {
        String sql = "UPDATE carlook.endkunde " +
                "SET anrede = ?, vorname = ?, name = ?,  " +

                "WHERE carlook.endkunde.id = ?;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setString(1, endkundeDTO.getAnrede());
            statement.setString(2, endkundeDTO.getVorname());
            statement.setString(3, endkundeDTO.getName());

            statement.setInt(4, endkundeDTO.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public EndkundeDTO getAllDataEndkunde(UserDTO userDTO) throws SQLException {
        String sql = "SELECT * " +
                "FROM carlook.endkunde " +
                "WHERE carlook.endkunde.id = ? ;";

        PreparedStatement statement = this.getPreparedStatement(sql);
        ResultSet rs;
        try {
            statement.setInt(1,userDTO.getId());
            rs = statement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger((EndkundeDAO.class.getName())).log(Level.SEVERE, null, ex);
            return null;
        }

        try {
            EndkundeDTO endkundeDTO = new EndkundeDTO(userDTO);
            while (rs.next()) {
                endkundeDTO.setVorname(rs.getString(2));
                endkundeDTO.setName(rs.getString(3));
                endkundeDTO.setAnrede(rs.getString(4));



            }
            return endkundeDTO;
        } catch (SQLException ex) {
            Logger.getLogger((EndkundeDAO.class.getName())).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            rs.close();
        }


    }

}
