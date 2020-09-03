package org.Hausarbeit.process.proxy;

import com.vaadin.ui.Button;
import org.Hausarbeit.model.objects.dto.BewerbungDTO;
import org.Hausarbeit.model.objects.dto.StellenanzeigeDTO;
import org.Hausarbeit.model.objects.dto.StudentDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.BewerbungControlInterface;
import org.Hausarbeit.process.control.BewerbungControl;
import org.Hausarbeit.process.exceptions.BewerbungException;
import org.Hausarbeit.process.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class ReservierungControlProxy implements BewerbungControlInterface {
    private static BewerbungControlProxy bewerbungControl = null;

    private BewerbungControlProxy() {

    }

    public static BewerbungControlProxy getInstance() {
        if (bewerbungControl == null) {
            bewerbungControl = new BewerbungControlProxy();
        }
        return bewerbungControl;
    }

    public int getLatestApply(UserDTO userDTO) throws DatabaseException, SQLException {
        return BewerbungControl.getInstance().getLatestApply(userDTO);
    }

    public void applyForStellenanzeige(StellenanzeigeDTO stellenanzeige, int id_bewerbung) throws DatabaseException {
        BewerbungControl.getInstance().applyForStellenanzeige(stellenanzeige, id_bewerbung);
    }

    public void applyingIsAllowed() throws DatabaseException, BewerbungException, SQLException {
        BewerbungControl.getInstance().applyingIsAllowed();
    }

    public void checkAlreadyApplied(StellenanzeigeDTO stellenanzeigeDTO, UserDTO userDTO) throws BewerbungException, DatabaseException, SQLException {
        BewerbungControl.getInstance().checkAlreadyApplied(stellenanzeigeDTO, userDTO);

    }
    public void checkAllowed(StellenanzeigeDTO stellenanzeige, UserDTO userDTO, Button bewerbenButton) {
        BewerbungControl.getInstance().checkAllowed(stellenanzeige, userDTO, bewerbenButton);
    }

    public void createBewerbung(String bewerbungstext, UserDTO userDTO) throws BewerbungException {
        BewerbungControl.getInstance().createBewerbung(bewerbungstext, userDTO);
    }

    public BewerbungDTO getBewerbungForStellenanzeige(StellenanzeigeDTO selektiert, StudentDTO studentDTO) throws SQLException, DatabaseException {
        return BewerbungControl.getInstance().getBewerbungForStellenanzeige(selektiert, studentDTO);
    }

    public List<BewerbungDTO> getBewerbungenForStudent(StudentDTO studentDTO) throws SQLException {
        return BewerbungControl.getInstance().getBewerbungenForStudent(studentDTO);
    }

    public void deleteBewerbung(BewerbungDTO bewerbungDTO) throws BewerbungException {
        BewerbungControl.getInstance().deleteBewerbung(bewerbungDTO);
    }
}
