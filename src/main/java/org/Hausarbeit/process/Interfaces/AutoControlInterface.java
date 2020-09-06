package org.Hausarbeit.process.Interfaces;

import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.AutoException;

import java.sql.SQLException;
import java.util.List;

public interface AutoControlInterface {

    List<AutoDTO> getAnzeigenForVertriebler(VertrieblerDTO vertrieblerDTO) throws SQLException;

    List<AutoDTO> getAnzeigenForEndkunde(EndkundeDTO endkundeDTO) throws SQLException;

    void createAuto(AutoDTO AutoDTO) throws AutoException;

    void updateAuto(AutoDTO AutoDTO) throws AutoException;

    void deleteAuto(AutoDTO AutoDTO) throws AutoException;

    List<AutoDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException;

    int getAnzahlReservierung(AutoDTO AutoDTO) throws DatabaseException, SQLException;
}
