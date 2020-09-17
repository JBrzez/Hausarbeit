package org.Hausarbeit.process.proxy;

import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.AutoControlInterface;
import org.Hausarbeit.process.control.AutoControl;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.AutoException;

import java.sql.SQLException;
import java.util.List;

public class AutoControlProxy implements AutoControlInterface {
    private static AutoControlProxy search = null;

    public static AutoControlProxy getInstance() {
        if (search == null) {
            search = new AutoControlProxy();
        }
        return search;
    }

    private AutoControlProxy() {

    }

    public List<AutoDTO> getAnzeigenForVertriebler(UserDTO vertrieblerDTO) throws SQLException {
        return AutoControl.getInstance().getAnzeigenForVertriebler(vertrieblerDTO);
    }

    public List<AutoDTO> getAnzeigenForEndkunde(UserDTO studentDTO) throws SQLException {
        return AutoControl.getInstance().getAnzeigenForEndkunde(studentDTO);
    }
    public void createAuto(AutoDTO AutoDTO) throws AutoException {
        AutoControl.getInstance().createAuto(AutoDTO);
    }
    public void updateAuto(AutoDTO AutoDTO) throws AutoException {
        AutoControl.getInstance().updateAuto(AutoDTO);
    }

    public void deleteAuto(AutoDTO AutoDTO) throws AutoException {
        AutoControl.getInstance().deleteAuto(AutoDTO);
    }

    public List<AutoDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException {
        return AutoControl.getInstance().getAnzeigenForSearch(suchtext, filter);
    }

    public int getAnzahlReservierung(AutoDTO autoDTO) throws DatabaseException, SQLException {
        return AutoControl.getInstance().getAnzahlReservierung(autoDTO);
    }
}
