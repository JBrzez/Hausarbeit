package org.Hausarbeit.process.Interfaces;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public interface ReservierungControlInterface {

    boolean reserveAuto(AutoDTO autoDTO, UserDTO userDTO) throws DatabaseException;

    boolean userCanReserve(UserDTO userDTO, AutoDTO autoDTO) throws DatabaseException, SQLException;

    void checkAllowed(AutoDTO auto, UserDTO userDTO, Button bewerbenButton);

    List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) throws SQLException;

    void deleteReservierung(ReservierungDTO ReservierungDTO) throws ReservierungException;

    ReservierungDTO getReservierungForAuto(AutoDTO selektiert, UserDTO userDTO);
}
