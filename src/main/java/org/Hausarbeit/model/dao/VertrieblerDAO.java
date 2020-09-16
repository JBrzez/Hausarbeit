package org.Hausarbeit.model.dao;

import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VertrieblerDAO extends AbstractDAO {

    private static VertrieblerDAO dao = null;

    private VertrieblerDAO() {

    }

    public static VertrieblerDAO getInstance() {
        if (dao == null) {
            dao = new VertrieblerDAO();
        }
        return dao;
    }

    public boolean updateVertriebler(VertrieblerDTO vertrieblerDTO) {
        String sql = "UPDATE collhbrs.unternehmen " +
                "SET name_unternehmen = ?, ansprechpartner = ?, strasse = ?, plz = ?, " +
                "hausnr = ?, zusatz = ?, ort = ?, branche = ? " +
                "WHERE collhbrs.unternehmen.id = ? ;";

        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setString(1, vertrieblerDTO.getNachname());

            statement.setString(3, vertrieblerDTO.getStrasse());
            statement.setInt(4, vertrieblerDTO.getPlz());
            statement.setInt(5, vertrieblerDTO.getHaus_nr());
            statement.setString(6, vertrieblerDTO.getZusatz());

            statement.setInt(9, vertrieblerDTO.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public VertrieblerDTO getAllDataVertriebler(UserDTO userDTO) throws SQLException {
        String sql = "SELECT * " +
                "FROM collhbrs.unternehmen " +
                "WHERE collhbrs.unternehmen.id = ? ;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        ResultSet rs;

        try {
            statement.setInt(1,userDTO.getId());
            rs = statement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger((VertrieblerDAO.class.getName())).log(Level.SEVERE, null, ex);
            return null;
        }
        VertrieblerDTO un = new VertrieblerDTO(userDTO);
        try {
            while (rs.next()) {

                un.setNachname(rs.getString(2));

                un.setStrasse(rs.getString(4));
                un.setPlz(rs.getInt(5));
                un.setHaus_nr(rs.getInt(6));
                un.setZusatz(rs.getString(7));

            }

        } catch (SQLException ex) {
            Logger.getLogger((VertrieblerDAO.class.getName())).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            rs.close();
        }
        return un;
    }
}
