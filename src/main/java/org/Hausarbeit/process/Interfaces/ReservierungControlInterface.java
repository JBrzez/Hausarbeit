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

    int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException;

    void applyForAuto(AutoDTO auto, int id_Reservierung) throws DatabaseException;

    void applyingIsAllowed() throws DatabaseException, ReservierungException, SQLException;

    void checkAlreadyApplied(AutoDTO auto, UserDTO userDTO) throws ReservierungException, DatabaseException, SQLException;

    void checkAllowed(AutoDTO auto, UserDTO userDTO, Button bewerbenButton);

    void createReservierung(String Reservierungstext, UserDTO userDTO) throws ReservierungException;

    ReservierungDTO getReservierungForAuto(AutoDTO selektiert, UserDTO userDTO) throws SQLException, DatabaseException;

    List<ReservierungDTO> getReservierungForEndkunde(UserDTO userDTO) throws SQLException;

    void deleteReservierung(ReservierungDTO ReservierungDTO) throws ReservierungException;
}
