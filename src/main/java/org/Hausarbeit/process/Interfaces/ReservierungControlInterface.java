package org.Hausarbeit.process.Interfaces;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.BewerbungDTO;
import org.Hausarbeit.model.objects.dto.StellenanzeigeDTO;
import org.Hausarbeit.model.objects.dto.StudentDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.BewerbungException;
import org.Hausarbeit.process.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public interface ReservierungControlInterface {

    int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException;

    void applyForStellenanzeige(StellenanzeigeDTO stellenanzeige, int id_bewerbung) throws DatabaseException;

    void applyingIsAllowed() throws DatabaseException, BewerbungException, SQLException;

    void checkAlreadyApplied(StellenanzeigeDTO stellenanzeigeDTO, UserDTO userDTO) throws BewerbungException, DatabaseException, SQLException;

    void checkAllowed(StellenanzeigeDTO stellenanzeige, UserDTO userDTO, Button bewerbenButton);

    void createBewerbung(String bewerbungstext, UserDTO userDTO) throws BewerbungException;

    BewerbungDTO getBewerbungForStellenanzeige(StellenanzeigeDTO selektiert, StudentDTO studentDTO) throws SQLException, DatabaseException;

    List<BewerbungDTO> getBewerbungenForStudent(StudentDTO studentDTO) throws SQLException;

    void deleteBewerbung(BewerbungDTO bewerbungDTO) throws BewerbungException;
}
