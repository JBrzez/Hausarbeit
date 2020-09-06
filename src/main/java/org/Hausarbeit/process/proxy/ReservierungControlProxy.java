package org.Hausarbeit.process.proxy;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ReservierungControlInterface;
import org.Hausarbeit.process.control.ReservierungControl;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class ReservierungControlProxy implements ReservierungControlInterface {
    private static ReservierungControlProxy ReservierungControl = null;

    private ReservierungControlProxy() {

    }

    public static ReservierungControlProxy getInstance() {
        if (ReservierungControl == null) {
            ReservierungControl = new ReservierungControlProxy();
        }
        return ReservierungControl;
    }

    public int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException {
        return ReservierungControl.getInstance().getLatestApply(userDTO);
    }

    public void applyForAuto(AutoDTO Auto, int id_Reservierung) throws DatabaseException {
        ReservierungControl.getInstance().applyForAuto(Auto, id_Reservierung);
    }

    public void applyingIsAllowed() throws DatabaseException, ReservierungException, SQLException {
        ReservierungControl.getInstance().applyingIsAllowed();
    }

    public void checkAlreadyApplied(AutoDTO AutoDTO, UserDTO userDTO) throws ReservierungException, DatabaseException, SQLException {
        ReservierungControl.getInstance().checkAlreadyApplied(AutoDTO, userDTO);

    }
    public void checkAllowed(AutoDTO Auto, UserDTO userDTO, Button bewerbenButton) {
        ReservierungControl.getInstance().checkAllowed(Auto, userDTO, bewerbenButton);
    }

    public void createReservierung(String Reservierungstext, UserDTO userDTO) throws ReservierungException {
        ReservierungControl.getInstance().createReservierung(Reservierungstext, userDTO);
    }

    public ReservierungDTO getReservierungForAuto(AutoDTO selektiert, EndkundeDTO EndkundeDTO) throws SQLException, DatabaseException {
        return ReservierungControl.getInstance().getReservierungForAuto(selektiert, EndkundeDTO);
    }

    public List<ReservierungDTO> getReservierungForEndkunde(EndkundeDTO EndkundeDTO) throws SQLException {
        return ReservierungControl.getInstance().getReservierungForEndkunde(EndkundeDTO);
    }

    public void deleteReservierung(ReservierungDTO ReservierungDTO) throws ReservierungException {
        ReservierungControl.getInstance().deleteReservierung(ReservierungDTO);
    }
}
