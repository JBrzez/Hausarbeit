package org.Hausarbeit.process.Interfaces;

import org.Hausarbeit.model.objects.dto.StellenanzeigeDTO;
import org.Hausarbeit.model.objects.dto.StudentDTO;
import org.Hausarbeit.model.objects.dto.UnternehmenDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.StellenanzeigeException;

import java.sql.SQLException;
import java.util.List;

public interface AutoControlInterface {

    List<StellenanzeigeDTO> getAnzeigenForUnternehmen(UnternehmenDTO unternehmenDTO) throws SQLException;

    List<StellenanzeigeDTO> getAnzeigenForStudent(StudentDTO studentDTO) throws SQLException;

    void createStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigeException;

    void updateStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigeException;

    void deleteStellenanzeige(StellenanzeigeDTO stellenanzeigeDTO) throws StellenanzeigeException;

    List<StellenanzeigeDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException;

    int getAnzahlBewerber(StellenanzeigeDTO stellenanzeigeDTO) throws DatabaseException, SQLException;
}
