package org.Hausarbeit.process.control;

import com.vaadin.ui.UI;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.SearchControlInterface;

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
        return getAnzeigenForUser(( (MyUI) UI.getCurrent() ).getUserDTO());
    }

    public List<AutoDTO> getAnzeigenForUser(UserDTO userDTO) throws SQLException {
        if (userDTO.isEndkunde()) {
            //  EndkundeDTO EndkundeDTO = new EndkundeDTO(userDTO);
            return AutoControl.getInstance().getAnzeigenForEndkunde(userDTO);
        }
        //VertrieblerDTO VertrieblerDTO = new VertrieblerDTO(userDTO);
        return AutoControl.getInstance().getAnzeigenForVertriebler(userDTO);
    }

    public List<AutoDTO> getAnzeigenForSearch(String suchtext, String filter) throws SQLException {
        if (filter == null) {
            filter = "marke";
        }
        return AutoControl.getInstance().getAnzeigenForSearch(suchtext, filter);
    }
}
