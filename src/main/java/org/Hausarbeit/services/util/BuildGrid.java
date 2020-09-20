package org.Hausarbeit.services.util;

import com.vaadin.ui.Grid;
import org.Hausarbeit.model.objects.dto.AutoDTO;

public class BuildGrid {
    public static void buildGrid(Grid<AutoDTO> grid) {
        grid.removeAllColumns();
        grid.addColumn(AutoDTO::getMarke).setCaption("Marke");
        grid.addColumn(AutoDTO::getBaujahr).setCaption("Baujahr");
        grid.addColumn(AutoDTO::getBeschreibung).setCaption("Beschreibung");
        grid.addColumn(AutoDTO::getVertriebler_id).setCaption("Ansprechpartner-ID");
    }
}
