package org.Hausarbeit.process.control;

import com.vaadin.ui.UI;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
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

    public List<AutoDTO> getAnzeigenForUser() throws SQLException {
        UserDTO userDTO = ( (MyUI) UI.getCurrent() ).getUserDTO();
        if (userDTO.isEndkunde()) {
            EndkundeDTO EndkundeDTO = new EndkundeDTO(userDTO);
            return AutoControl.getInstance().getAnzeigenForEndkunde(EndkundeDTO);
        }
        VertrieblerDTO VertrieblerDTO = new VertrieblerDTO(userDTO);
        return AutoControl.getInstance().getAnzeigenForVertriebler(VertrieblerDTO);
    }

    public List<AutoDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException {
        if (filter == null) {
            filter = "name";
        }
        return AutoControl.getInstance().getAnzeigenForSearch(suchtext, filter);
    }
}
