package org.Hausarbeit.process.control;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.model.dao.AutoDAO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.AutoControlInterface;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AutoControl implements AutoControlInterface {
    private static AutoControl search = null;

    public static AutoControl getInstance() {
        if (search == null) {
            search = new AutoControl();
        }
        return search;
    }

    private AutoControl() {

    }

    public List<AutoDTO> getAnzeigenForVertriebler(UserDTO userDTO) throws SQLException {
        return AutoDAO.getInstance().getAutoForVertriebler(userDTO);
    }

    public List<AutoDTO> getAnzeigenForEndkunde(UserDTO userDTO) throws SQLException {
        return AutoDAO.getInstance().getAutosForEndkunde(userDTO);

    }

    public void createAuto(AutoDTO AutoDTO) throws AutoException {
        UserDTO userDTO = ((MyUI) UI.getCurrent()).getUserDTO();
        boolean result = AutoDAO.getInstance().createAuto(AutoDTO, userDTO);
        if (result) {
            return;
        }
        throw new AutoException();
    }

    public void updateAuto(AutoDTO AutoDTO) throws AutoException {
        boolean result = AutoDAO.getInstance().updateAuto(AutoDTO);
        if (result) {
            return;
        }
        throw new AutoException();
    }

    public void deleteAuto(AutoDTO autoDTO) throws AutoException {
        boolean result = AutoDAO.getInstance().deleteAuto(autoDTO);
        if (result) {
            return;
        }
        throw new AutoException();
    }

    public List<AutoDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException {
        return AutoDAO.getInstance().getAutoForSearch(suchtext, filter);
    }

    public int getAnzahlReservierung(AutoDTO autoDTO) throws DatabaseException, SQLException {
        int anzahlReservierungen = 0;
        String sql = "SELECT count(user_id) " +
                "FROM carlook.user_to_auto " +
                "WHERE auto_id = ?";

        ResultSet rs;
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        try {
            statement.setInt(1, autoDTO.getId());
            rs = statement.executeQuery();
        } catch (SQLException throwables) {
            throw new DatabaseException("Fehler im SQL-Befehl: Bitte den Programmierer informieren!");
        }
        try {
            if (rs.next()) {
                anzahlReservierungen = rs.getInt(1);
            }
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!", Notification.Type.ERROR_MESSAGE);
        } finally {
            //JDBCConnection.getInstance().closeConnection();
            rs.close();
        }

        return anzahlReservierungen;
    }
}
