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
    private static ReservierungControl bewerbungControl = null;

    private ReservierungControl() {

    }

    public static ReservierungControl getInstance() {
        if (bewerbungControl == null) {
            bewerbungControl = new ReservierungControl();
        }
        return bewerbungControl;
    }

    public int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException {
        int id_bewerbung = 0;
        String sql = "SELECT max(id_bewerbung) " +
                "FROM collhbrs.bewerbung " +
                "WHERE id = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            statement.setInt(1, userDTO.getId());
            rs = statement.executeQuery();
            if (rs.next()) {
                id_bewerbung = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
        } finally {
            assert rs != null;
            rs.close();
        }
        return id_bewerbung;
    }

    public void applyForStellenanzeige(StellenanzeigeDTO stellenanzeige, int id_bewerbung) throws DatabaseException {
        String sql = "INSERT INTO collhbrs.bewerbung_to_stellenanzeige (id_bewerbung, id_anzeige) " +
                "VALUES (?, ?);";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        try {
            statement.setInt(1, id_bewerbung);
            statement.setInt(2, stellenanzeige.getId_anzeige());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
        }
    }

    public void applyingIsAllowed() throws DatabaseException, SQLException, ReservierungException {
        String sql = "SELECT sichtbar " +
                "FROM collhbrs.stellenanzeige_on_off";
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
        List<ReservierungDTO> list = ReservierungDAO.getInstance().getBewerbungenForStudent(studentDTO);
        String sql = "SELECT id_anzeige " +
                "FROM collhbrs.bewerbung_to_stellenanzeige " +
                "WHERE id_bewerbung = ? " +
                "AND id_anzeige = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        ResultSet rs = null;
        for (ReservierungDTO reservierungDTO : list) {
            int id_bewerbung = reservierungDTO.getId();
            try {
                statement.setInt(1, id_bewerbung);
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
        if (userDTO == null || !userDTO.hasRole(Roles.STUDENT)) {
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

    public void createReservierung(String bewerbungstext, UserDTO userDTO) throws ReservierungException {
        EndkundeDTO endkundeDTO = new EndkundeDTO(userDTO);
        boolean result = ReservierungDAO.getInstance().createReservierung(bewerbungstext, endkundeDTO);
        if (!result) {
            throw new ReservierungException();
        }
    }

    public ReservierungDTO getBewerbungForStellenanzeige(AutoDTO selektiert, EndkundeDTO endkundeDTO) throws SQLException, DatabaseException {
        List<ReservierungDTO> list = getBewerbungenForStudent(endkundeDTO);
        ReservierungDTO reservierungDTO = new ReservierungDTO();
        String sql = "SELECT id_bewerbung " +
                "FROM collhbrs.bewerbung_to_stellenanzeige " +
                "WHERE id_anzeige = ? " +
                "AND id_bewerbung = ? ";
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
        return bewerbungDTO;
    }

    public List<ReservierungDTO> getBewerbungenForStudent(EndkundeDTO endkundeDTO) throws SQLException {
        return ReservierungDAO.getInstance().getBewerbungenForStudent(endklundeDTO);
    }

    public void deleteReservierung(ReservierungDTO reservierungDTO) throws ReservierungException {
        boolean result = ReservierungDAO.getInstance().deleteReservierung(reservierungDTO);
        if (result) {
            return;
        }
        throw new ReservierungException();
    }
}
