package org.Hausarbeit.process.control;

import com.vaadin.ui.UI;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.model.objects.dto.StellenanzeigeDTO;
import org.Hausarbeit.model.objects.dto.StudentDTO;
import org.Hausarbeit.model.objects.dto.UnternehmenDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.SearchControlInterface;
import org.Hausarbeit.services.util.Roles;

import java.sql.SQLException;
import java.util.List;

public class SearchControl implements SearchControlInterface {
    private static SearchControl search = null;

    public static SearchControl getInstance() {
        if (search == null) {
            search = new SearchControl();
        }
        return search;
    }

    private SearchControl() {

    }

    public List<StellenanzeigeDTO> getAnzeigenForUser() throws SQLException {
        UserDTO userDTO = ( (MyUI) UI.getCurrent() ).getUserDTO();
        if (userDTO.hasRole(Roles.STUDENT)) {
            StudentDTO studentDTO = new StudentDTO(userDTO);
            return StellenanzeigeControl.getInstance().getAnzeigenForStudent(studentDTO);
        }
        UnternehmenDTO unternehmenDTO = new UnternehmenDTO(userDTO);
        return StellenanzeigeControl.getInstance().getAnzeigenForUnternehmen(unternehmenDTO);
    }

    public List<StellenanzeigeDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException {
        if (filter == null) {
            filter = "name";
        }
        return StellenanzeigeControl.getInstance().getAnzeigenForSearch(suchtext, filter);
    }
}
