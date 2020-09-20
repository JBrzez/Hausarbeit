package org.Hausarbeit.model.dao;

import com.vaadin.ui.Notification;

import org.Hausarbeit.model.factory.ReservierungDTOFactory;

import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;

import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.proxy.AutoControlProxy;
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

    public boolean createReservierung(UserDTO userDTO, AutoDTO autoDTO) {
        String sql = "INSERT INTO carlook.user_to_auto (user_id, auto_id) " +
                "VALUES (?, ?); ";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, userDTO.getId());
            statement.setInt(2, autoDTO.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean deleteReservierung(ReservierungDTO reservierungDTO) {
        String sql = "DELETE " +
                "FROM carlook.user_to_auto " +
                "WHERE auto_id = ? AND user_id = ?";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, reservierungDTO.getAuto_id());
            statement.setInt(2, reservierungDTO.getUser_id());
            statement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger((ReservierungDAO.class.getName())).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) {
        String sql = "SELECT auto_id " +
                "FROM carlook.user_to_auto " +
                "WHERE user_id = ?";
        PreparedStatement statement = this.getPreparedStatement(sql);
        ResultSet rs = null;
        try {
            statement.setInt(1, userDTO.getId());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        List<ReservierungDTO> list = new ArrayList<>();
        assert rs != null;


        try {
            while (rs.next()) {
                ReservierungDTO reservierungDTO;
                reservierungDTO = new ReservierungDTO();
                reservierungDTO.setAuto_id(rs.getInt(1));
                reservierungDTO.setUser_id(userDTO.getId());

                list.add(reservierungDTO);
            }
        } catch (SQLException e) {
            Notification.show("Es ist ein schwerer SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        } finally{
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return list;
    }
}
