package org.Hausarbeit.process.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import org.Hausarbeit.model.dao.ReservierungDAO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ReservierungControlInterface;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.services.db.JDBCConnection;
import org.Hausarbeit.services.util.Roles;

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

    public int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException {
        int id_Reservierung = 0;
        String sql = "SELECT max(id_Reservierung) " +
                "FROM collhbrs.Reservierung " +
                "WHERE id = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            statement.setInt(1, userDTO.getId());
            rs = statement.executeQuery();
            if (rs.next()) {
                id_Reservierung = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
        } finally {
            assert rs != null;
            rs.close();
        }
        return id_Reservierung;
    }

    public void applyForAuto(AutoDTO Auto, int id_Reservierung) throws DatabaseException {
        String sql = "INSERT INTO collhbrs.Reservierung_to_Auto (id_Reservierung, id_anzeige) " +
                "VALUES (?, ?);";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        try {
            statement.setInt(1, id_Reservierung);
            statement.setInt(2, Auto.getId_anzeige());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
        }
    }

    public void applyingIsAllowed() throws DatabaseException, SQLException, ReservierungException {
        String sql = "SELECT sichtbar " +
                "FROM collhbrs.Auto_on_off";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            if ( rs.next() ) {
                if (rs.getBoolean(1)) {
                    return;
                }
                throw new ReservierungException();
            }
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
        } finally {
            assert rs != null;
            rs.close();
        }
    }

    public void checkAlreadyApplied(AutoDTO autoDTO, UserDTO userDTO) throws DatabaseException, SQLException, ReservierungException {
        EndkundeDTO endkundeDTO = new EndkundeDTO(userDTO);
        List<ReservierungDTO> list = ReservierungDAO.getInstance().getReservierungForEndkunde(endkundeDTO);
        String sql = "SELECT id_anzeige " +
                "FROM collhbrs.Reservierung_to_Auto " +
                "WHERE id_Reservierung = ? " +
                "AND id_anzeige = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        for (ReservierungDTO reservierungDTO : list) {
            int id_Reservierung = reservierungDTO.getId();
            try {
                statement.setInt(1, id_Reservierung);
                statement.setInt(2, autoDTO.getId_anzeige());
                rs = statement.executeQuery();
                if (rs.next()) {
                    throw new ReservierungException();
                }
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator!", Notification.Type.ERROR_MESSAGE);
            } finally {
                assert rs != null;
                rs.close();
            }
        }

    }
    public void checkAllowed(AutoDTO auto, UserDTO userDTO, Button bewerbenButton) {
        if (userDTO == null || !userDTO.isEndkunde()) {
            bewerbenButton.setVisible(false);
            return;
        }
        try {
            applyingIsAllowed();
            checkAlreadyApplied(auto, userDTO);
        } catch (DatabaseException e) {
            Notification.show("Es ist ein Datenbankfehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
        } catch (ReservierungException e) {
            bewerbenButton.setVisible(false);
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator!", Notification.Type.ERROR_MESSAGE);
        }
    }

    public void createReservierung(String Reservierungstext, UserDTO userDTO) throws ReservierungException {
        EndkundeDTO endkundeDTO = new EndkundeDTO(userDTO);
        boolean result = ReservierungDAO.getInstance().createReservierung(Reservierungstext, endkundeDTO);
        if (!result) {
            throw new ReservierungException();
        }
    }

    public ReservierungDTO getReservierungForAuto(AutoDTO selektiert, EndkundeDTO endkundeDTO) throws SQLException, DatabaseException {
        List<ReservierungDTO> list = getReservierungForEndkunde(endkundeDTO);
        ReservierungDTO reservierungDTO = new ReservierungDTO();
        String sql = "SELECT id_Reservierung " +
                "FROM collhbrs.Reservierung_to_Auto " +
                "WHERE id_anzeige = ? " +
                "AND id_Reservierung = ? ";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        for (ReservierungDTO reservierung :list ) {
            try {
                statement.setInt(1, selektiert.getId_anzeige());
                statement.setInt(2, reservierung.getId());
                rs = statement.executeQuery();
                if ( rs.next() ) {
                    reservierungDTO = reservierung;
                    break;
                }
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator!", Notification.Type.ERROR_MESSAGE);
            } finally{
                assert rs != null;
                rs.close();
            }
        }
        return reservierungDTO;
    }

    public List<ReservierungDTO> getReservierungForEndkunde(EndkundeDTO endkundeDTO) throws SQLException {
        return ReservierungDAO.getInstance().getReservierungForEndkunde(endkundeDTO);
    }

    public void deleteReservierung(ReservierungDTO reservierungDTO) throws ReservierungException {
        boolean result = ReservierungDAO.getInstance().deleteReservierung(reservierungDTO);
        if (result) {
            return;
        }
        throw new ReservierungException();
    }
}
