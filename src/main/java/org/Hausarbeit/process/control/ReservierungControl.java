package org.Hausarbeit.process.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import org.Hausarbeit.model.dao.ReservierungDAO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ReservierungControlInterface;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservierungControl implements ReservierungControlInterface {
    private static ReservierungControl ReservierungControl = null;

    private ReservierungControl() {

    }

    public static ReservierungControl getInstance() {
        if (ReservierungControl == null) {
            ReservierungControl = new ReservierungControl();
        }
        return ReservierungControl;
    }

    // Auto reservieren
    public boolean reserveAuto(AutoDTO autoDTO, UserDTO userDTO) throws DatabaseException {
        String sql = "INSERT INTO carlook.user_to_auto (auto_id, user_id) " +
                "VALUES (?, ?);";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        try {
            statement.setInt(1, autoDTO.getId());
            statement.setInt(2, userDTO.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean userCanReserve(UserDTO userDTO, AutoDTO autoDTO) throws DatabaseException, SQLException {
        String sql = "SELECT CASE WHEN user_id IS NULL THEN 'allowed' ELSE 'nope' END " +
                "FROM carlook.user_to_auto WHERE user_id = ? AND auto_id = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            statement.setInt(1, userDTO.getId());
            statement.setInt(2, autoDTO.getId());
            rs = statement.executeQuery();
            if ( rs.next() ) {
                return rs.getString(1).equals("allowed");
            }
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            assert rs != null;
            rs.close();
        }
        return true;
    }


    public void checkAllowed(AutoDTO auto, UserDTO userDTO, Button bewerbenButton) {
        if (userDTO == null || !userDTO.isEndkunde()) {
            bewerbenButton.setVisible(false);
            return;
        }
        System.out.println("reached");
        try {
            boolean canReserve = userCanReserve(userDTO, auto);
            if(!canReserve) bewerbenButton.setVisible(false);
        } catch (DatabaseException e) {
            Notification.show("Es ist ein Datenbankfehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator!", Notification.Type.ERROR_MESSAGE);
        }
    }

    public List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) throws SQLException {
        return ReservierungDAO.getInstance().getReservierungForEndkunde(userDTO);
    }

    public void deleteReservierung(ReservierungDTO reservierungDTO) throws ReservierungException {
        boolean result = ReservierungDAO.getInstance().deleteReservierung(reservierungDTO);
        if (result) {
            return;
        }
        throw new ReservierungException();
    }

    public ReservierungDTO getReservierungForAuto(AutoDTO autoDTO, UserDTO userDTO) {
        ReservierungDTO result = new ReservierungDTO();
        result.setUser_id(userDTO.getId());
        result.setAuto_id(autoDTO.getId());
        return result;
    }
}
