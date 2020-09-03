package org.Hausarbeit.model.dao;

import com.vaadin.ui.Notification;

import org.Hausarbeit.model.factory.ReservierungDTOFactory;

import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;

import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservierungDAO extends AbstractDAO {
    private static ReservierungDAO reservierungDAO = null;

    private ReservierungDAO() {

    }

    public static ReservierungDAO getInstance() {
        if (reservierungDAO == null) {
            reservierungDAO = new ReservierungDAO();
        }
        return reservierungDAO;
    }

    public ReservierungDTO getReservierung(int id_reservierung) throws DatabaseException, SQLException {
        String sql = "SELECT id_bewerbung, freitext " +
                "FROM collhbrs.bewerbung " +
                "WHERE id_bewerbung = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        ReservierungDTO reservierungDTO = null;
        try {
            statement.setInt(1, id_reservierung);
            rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                reservierungDTO = ReservierungDTOFactory.createReservierungDTO(id, text);
            }
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        } finally {
            assert rs != null;
            rs.close();
        }
        return reservierungDTO;
    }

    public List<ReservierungDTO> getBewerbungenForStudent(EndkundeDTO endkundeDTO) throws SQLException {
        String sql = "SELECT id_bewerbung, freitext " +
                "FROM collhbrs.bewerbung " +
                "WHERE id = ? ;";
        List<ReservierungDTO> list = new ArrayList<>();
        PreparedStatement statement = this.getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            statement.setInt(1, endkundeDTO.getId());
            rs = statement.executeQuery();
        } catch (SQLException ex) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        ReservierungDTO reservierungDTO;
        try {
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                int id = rs.getInt(1);
                String text = rs.getString(2);
                reservierungDTO = ReservierungDTOFactory.createReservierungDTO(id, text);
                list.add(reservierungDTO);

            }
        } catch (SQLException ex) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        finally{
            assert rs != null;
            rs.close();
        }
        return list;
    }

    public boolean createBewerbung(String text, EndkundeDTO endkundeDTO) {
        String sql = "INSERT INTO collhbrs.bewerbung (id, freitext) " +
                "VALUES (?, ?); ";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, endkundeDTO.getId());
            statement.setString(2, text);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean deleteReservierung(ReservierungDTO reservierungDTO) {
        String sql = "DELETE " +
                "FROM collhbrs.bewerbung " +
                "WHERE id_bewerbung = ?";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, reservierungDTO.getId());
            statement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
