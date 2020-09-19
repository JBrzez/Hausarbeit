package org.Hausarbeit.model.dao;

import com.vaadin.ui.Notification;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO extends AbstractDAO {
    private static AutoDAO dao = null;

    private AutoDAO() {
    }

    public static AutoDAO getInstance() {
        if (dao == null) {
            dao = new AutoDAO();
        }
        return dao;
    }

    //Erzeugt die Stellenanezeigen, die ein Unternehmen erstellt hat
    public List<AutoDTO> getAutoForVertriebler(UserDTO userDTO) throws SQLException {
        String sql = "SELECT id, beschreibung, marke, baujahr, vertriebler_id " +
                "FROM carlook.auto " +
                "WHERE vertriebler_id = ?";
        PreparedStatement statement = this.getPreparedStatement(sql);

        ResultSet rs = null;
        try {
            statement.setInt(1, userDTO.getId());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        List<AutoDTO> list = new ArrayList<>();
        assert rs != null;
        buildList(rs, list);
        return list;
    }


    //Erstellt eine neue Auto in der Datenbank
    public boolean createAuto(AutoDTO Auto, UserDTO userDTO) {
        String sql = "INSERT INTO carlook.auto (beschreibung, marke, baujahr, vertriebler_id) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.getPreparedStatement(sql);

        try {
            statement.setString(1, Auto.getBeschreibung());
            statement.setString(2, Auto.getMarke());
            statement.setString(3, Auto.getBaujahr());
            statement.setInt(4, Auto.getVertriebler_id());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }
    public boolean createOrUpdateAuto(AutoDTO autoDTO, UserDTO userDTO) {
        if(autoDTO.getId() == 0)
        {
            return createAuto(autoDTO, userDTO);
        } else {
            return updateAuto(autoDTO);
        }
    }

    //Verändert eine bestehende Auto in der Datenbank
    public boolean updateAuto(AutoDTO auto) {
        String sql = "UPDATE carlook.auto " +
                "SET beschreibung = ?, marke = ?,  baujahr = ?, vertriebler_id = ? " +
                "WHERE id = ? ;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setString(1, auto.getBeschreibung());
            statement.setString(2, auto.getMarke());
            statement.setString(3, auto.getBaujahr());
            statement.setInt(4, auto.getVertriebler_id());
            statement.setInt(5, auto.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }


    //Löscht eine Auto aus der Datenbank
    public boolean deleteAuto(AutoDTO Auto) {
        String sql = "DELETE " +
                "FROM carlook.auto " +
                "WHERE id = ? ;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, Auto.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<AutoDTO> getAutoForSearch(String suchtext, String filter) throws SQLException {
        filter = filter.toLowerCase();
        PreparedStatement statement;
        ResultSet rs = null;
        if (suchtext == null || suchtext.equals("")) {
            String sql = "SELECT id, beschreibung, marke, baujahr, vertriebler_id " +
                    "FROM carlook.auto ;";
            statement = this.getPreparedStatement(sql);
            try {
                rs = statement.executeQuery();
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            }
        } else {
            String sql = "SELECT id, beschreibung, marke, baujahr, vertriebler_id " +
                    "FROM carlook.auto " +
                    "WHERE LOWER(" + filter.toLowerCase() + ") like LOWER(?) ;";
            statement = this.getPreparedStatement(sql);


            try {
                statement.setString(1, "%" + suchtext + "%");
                rs = statement.executeQuery();
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            }
        }

        List<AutoDTO> list = new ArrayList<>();

        assert rs != null;
        buildList(rs, list);
        return list;
    }

    //Alle Auto's, die ein User reserviert hat
    public List<AutoDTO> getAutosForEndkunde(UserDTO user) throws SQLException {
        String sql = "SELECT id, beschreibung, marke, baujahr, vertriebler_id " +
                "FROM carlook.auto " +
                "WHERE id IN ( SELECT auto_id from carlook.user_to_auto where user_id = ?)";
        System.out.println(user);
        PreparedStatement statement = this.getPreparedStatement(sql);
        System.out.println(statement);
        List<AutoDTO> listAuto = new ArrayList<>();
        ResultSet rs = null;
        try {
            statement.setInt(1, user.getId());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        List<AutoDTO> list = new ArrayList<>();

        assert rs != null;
        buildList(rs, list);
        return list;
    }

    private void buildList(ResultSet rs, List<AutoDTO> listAuto) throws SQLException {
        if (rs == null) return;
        AutoDTO autoDTO;
        try {
            while (rs.next()) {

                autoDTO = new AutoDTO();
                autoDTO.setId(rs.getInt(1));
                autoDTO.setBeschreibung(rs.getString(2));
                autoDTO.setMarke(rs.getString(3));
                autoDTO.setBaujahr(rs.getString(4));
                autoDTO.setVertriebler_id(rs.getInt(5));

                try {
                    autoDTO.setAnzahl_Reservierung(AutoControlProxy.getInstance().getAnzahlReservierung(autoDTO));
                } catch (DatabaseException e) {
                    Notification.show("Es ist ein Datenbankfehler aufgetreten. Bitte informieren Sie einen Administrator!");
                }
                listAuto.add(autoDTO);
            }
        } catch (SQLException e) {
            Notification.show("Es ist ein schwerer SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
    }


}


