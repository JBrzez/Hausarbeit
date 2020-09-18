package org.Hausarbeit.services.util;

import com.vaadin.ui.Grid;
import org.Hausarbeit.model.objects.dto.AutoDTO;

public class BuildGrid {
    public static void buildGrid(Grid<AutoDTO> grid) {
        grid.removeAllColumns();
        grid.addColumn(AutoDTO::getMarke).setCaption("Name");
        grid.addColumn(AutoDTO::getBaujahr).setCaption("Art");
        grid.addColumn(AutoDTO::getZeitraum).setCaption("Ende der Ausschreibung");
    }
}
