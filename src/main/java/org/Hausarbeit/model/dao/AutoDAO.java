package org.Hausarbeit.model.dao;

import com.vaadin.ui.Notification;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
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
        String sql = "SELECT id_anzeige, beschreibung, art, name, zeitraum, branche, studiengang, ort " +
                "FROM collhbrs.Auto " +
                "WHERE id = ? ;";
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
        String sql = "INSERT INTO collhbrs.Auto(id, beschreibung, art, name, zeitraum, branche, studiengang, ort)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.getPreparedStatement(sql);

        try {
            statement.setInt(1, userDTO.getId());
            statement.setString(2, Auto.getBeschreibung());
            statement.setString(3, Auto.getArt());
            statement.setString(4, Auto.getName());
            statement.setObject(5, Auto.getZeitraum());
            statement.setString(6, Auto.getBranche());
            statement.setString(7, Auto.getStudiengang());
            statement.setString(8, Auto.getOrt());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    //Verändert eine bestehende Auto in der Datenbank
    public boolean updateAuto(AutoDTO Auto) {
        String sql = "UPDATE collhbrs.Auto " +
                "SET beschreibung = ?, art = ?,  name = ?, zeitraum = ?, branche = ?, studiengang = ?, ort = ?  " +
                "WHERE collhbrs.Auto.id_anzeige = ? ;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setString(1, Auto.getBeschreibung());
            statement.setString(2, Auto.getArt());
            statement.setString(3, Auto.getName());
            statement.setObject(4, Auto.getZeitraum());
            statement.setString(5, Auto.getBranche());
            statement.setString(6, Auto.getStudiengang());
            statement.setString(7, Auto.getOrt());
            statement.setInt(8, Auto.getId_anzeige());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }


    //Löscht eine Auto aus der Datenbank
    public boolean deleteAuto(AutoDTO Auto) {
        String sql = "DELETE " +
                "FROM collhbrs.Auto " +
                "WHERE collhbrs.Auto.id_anzeige = ? ;";
        PreparedStatement statement = this.getPreparedStatement(sql);
        try {
            statement.setInt(1, Auto.getId_anzeige());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<AutoDTO> getAutonForSearch(String suchtext, String filter) throws SQLException {
        filter = filter.toLowerCase();
        PreparedStatement statement;
        ResultSet rs = null;
        if (suchtext.equals("")) {
            String sql = "SELECT id_anzeige, beschreibung, art, name, zeitraum, branche, studiengang, ort " +
                    "FROM collhbrs.Auto ;";
            statement = this.getPreparedStatement(sql);
            try {
                rs = statement.executeQuery();
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            }
        } else {
            String sql = "SELECT id_anzeige, beschreibung, art, name, zeitraum, branche, studiengang, ort " +
                    "FROM collhbrs.Auto " +
                    "WHERE " + filter + " like ? ;";
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

    //Zeigt alle Auton an, auf die sich ein Student beworben hat
    public List<AutoDTO> getAutoforEndKunde(EndkundeDTO endkundeDTO) throws SQLException {
        String sql = "SELECT id_anzeige, beschreibung, art, name, zeitraum, branche, studiengang, ort " +
                "FROM collhbrs.Auto " +
                "WHERE id_anzeige = ( SELECT id_anzeige " +
                "FROM collhbrs.bewerbung_to_Auto " +
                "WHERE id_bewerbung = ? );";
        PreparedStatement statement = this.getPreparedStatement(sql);
        List<ReservierungDTO> list = ReservierungDAO.getInstance().getReservierungForEndkunde(endkundeDTO);
        List<AutoDTO> listAuto = new ArrayList<>();
        ResultSet rs = null;
        for (ReservierungDTO bewerbungDTO : list) {
            int id_bewerbung = bewerbungDTO.getId();
            try {
                statement.setInt(1, id_bewerbung);
                rs = statement.executeQuery();
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            }
            assert rs != null;
            buildList(rs, listAuto);
        }

        return listAuto;
    }

    private void buildList(ResultSet rs, List<AutoDTO> listAuto) throws SQLException {

        AutoDTO autoDTO;
        try {
            while (rs.next()) {

                autoDTO = new AutoDTO();
                autoDTO.setId_anzeige(rs.getInt(1));
                autoDTO.setBeschreibung(rs.getString(2));
                autoDTO.setArt(rs.getString(3));
                autoDTO.setName(rs.getString(4));
                autoDTO.setZeitraum(rs.getDate(5).toLocalDate());
                autoDTO.setBranche(rs.getString(6));
                autoDTO.setStudiengang(rs.getString(7));
                autoDTO.setOrt(rs.getString(8));
                try {

                    autoDTO.setAnzahl_Reservierung(AutoControlProxy.getInstance().getAnzahlReservierung(autoDTO));

                } catch (DatabaseException e) {

                    Notification.show("Es ist ein Datenbankfehler aufgetreten. Bitte informieren Sie einen Administrator!");

                }
                listAuto.add(autoDTO);
            }
        } catch (SQLException e) {
            Notification.show("Es ist ein schwerer SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        } finally{
            assert rs != null;
            rs.close();
        }
    }


}


