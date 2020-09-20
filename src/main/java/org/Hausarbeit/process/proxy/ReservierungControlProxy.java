package org.Hausarbeit.process.proxy;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ReservierungControlInterface;
import org.Hausarbeit.process.control.ReservierungControl;
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

    public boolean reserveAuto(AutoDTO autoDTO, UserDTO userDTO) throws DatabaseException {
        return ReservierungControl.getInstance().reserveAuto(autoDTO, userDTO);
    }


    public boolean userCanReserve(UserDTO userDTO, AutoDTO AutoDTO) throws DatabaseException, SQLException {
        return ReservierungControl.getInstance().userCanReserve(userDTO, AutoDTO);

    }
    public void checkAllowed(AutoDTO Auto, UserDTO userDTO, Button bewerbenButton) {
        ReservierungControl.getInstance().checkAllowed(Auto, userDTO, bewerbenButton);
    }

    public void createReservierung(AutoDTO autoDTO, UserDTO userDTO) throws DatabaseException {
        ReservierungControl.getInstance().reserveAuto(autoDTO, userDTO);
    }

    public List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) throws SQLException {
        return ReservierungControl.getInstance().getReservierungForEndkunde(userDTO);
    }

    public void deleteReservierung(ReservierungDTO ReservierungDTO) throws ReservierungException {
        ReservierungControl.getInstance().deleteReservierung(ReservierungDTO);
    }

    @Override
    public ReservierungDTO getReservierungForAuto(AutoDTO selektiert, UserDTO userDTO) {
        return ReservierungControl.getInstance().getReservierungForAuto(selektiert, userDTO);
    }
}
