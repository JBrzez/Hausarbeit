package org.Hausarbeit.process.proxy;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ReservierungControlInterface;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class ReservierungControlProxy implements ReservierungControlInterface {
    private static ReservierungControlProxy reservierungControl = null;

    private ReservierungControlProxy() {

    }

    public static ReservierungControlProxy getInstance() {
        if (reservierungControl == null) {
            reservierungControl = new ReservierungControlProxy();
        }
        return reservierungControl;
    }

    public int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException {
        return reservierungControl.getInstance().getLatestApply(userDTO);
    }

    public void applyForAuto(AutoDTO Auto, int id_Reservierung) throws DatabaseException {
        reservierungControl.getInstance().applyForAuto(Auto, id_Reservierung);
    }

    public void applyingIsAllowed() throws DatabaseException, ReservierungException, SQLException {
        reservierungControl.getInstance().applyingIsAllowed();
    }

    public void checkAlreadyApplied(AutoDTO AutoDTO, UserDTO userDTO) throws ReservierungException, DatabaseException, SQLException {
        reservierungControl.getInstance().checkAlreadyApplied(AutoDTO, userDTO);

    }
    public void checkAllowed(AutoDTO Auto, UserDTO userDTO, Button bewerbenButton) {
        reservierungControl.getInstance().checkAllowed(Auto, userDTO, bewerbenButton);
    }

    public void createReservierung(String Reservierungstext, UserDTO userDTO) throws ReservierungException {
        reservierungControl.getInstance().createReservierung(Reservierungstext, userDTO);
    }

    public ReservierungDTO getReservierungForAuto(AutoDTO selektiert, UserDTO userDTO) throws SQLException, DatabaseException {
        return reservierungControl.getInstance().getReservierungForAuto(selektiert, userDTO);
    }

    public List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) throws SQLException {
        return reservierungControl.getInstance().getReservierungForEndkunde(userDTO);
    }

    public void deleteReservierung(ReservierungDTO ReservierungDTO) throws ReservierungException {
        reservierungControl.getInstance().deleteReservierung(ReservierungDTO);
    }
}
