package org.Hausarbeit.services.util;

import com.vaadin.ui.Grid;
import org.Hausarbeit.model.objects.dto.AutoDTO;

public class BuildGrid {
    public static void buildGrid(Grid<AutoDTO> grid) {
        grid.removeAllColumns();
        grid.addColumn(AutoDTO::getName).setCaption("Name");
        grid.addColumn(AutoDTO::getArt).setCaption("Art");
        grid.addColumn(AutoDTO::getBranche).setCaption("Branche");
        grid.addColumn(AutoDTO::getStudiengang).setCaption("Studiengang");
        grid.addColumn(AutoDTO::getOrt).setCaption("Ort");
        grid.addColumn(AutoDTO::getZeitraum).setCaption("Ende der Ausschreibung");
    }
}
