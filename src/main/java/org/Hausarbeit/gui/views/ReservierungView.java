package org.Hausarbeit.gui.views;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.Hausarbeit.gui.components.TopPanel;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.gui.windows.DeleteReservierungWindow;
import org.Hausarbeit.gui.windows.DeleteWindow;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;
import org.Hausarbeit.process.proxy.AutoControlProxy;
import org.Hausarbeit.services.util.BuildGrid;

import java.sql.SQLException;
import java.util.List;

public class ReservierungView extends VerticalLayout implements View {

    private AutoDTO selektiert;
    private List<AutoDTO> list;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp(((MyUI) UI.getCurrent()).getUserDTO());
    }

    private void setUp(UserDTO userDTO) {

        //Top Layer
        this.addComponent( new TopPanel() );
        Label line = new Label("<hr>", ContentMode.HTML);
        this.addComponent(line);
        line.setSizeFull();
        setStyleName("schrift-profil");
        //Tabelle
        final Grid<AutoDTO> grid = new Grid<>("Ihre Reservierungen");
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.ROW);
        SingleSelect<AutoDTO> selection = grid.asSingleSelect();
        grid.setStyleName("schrift-tabelle");
        //Tabelle füllen
        try {
            list = AutoControlProxy.getInstance().getAnzeigenForEndkunde(userDTO);
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        BuildGrid.buildGrid(grid);
        grid.setItems(list);

        //DeleteButton
        Button deleteButton = new Button("Löschen");
        deleteButton.setEnabled(false);

        //Tabellen Select Config
        grid.addSelectionListener(new SelectionListener<AutoDTO>() {
            @Override
            public void selectionChange(SelectionEvent<AutoDTO> event) {
                if (selection.getValue() == null) {
                    deleteButton.setEnabled(false);
                }
                else {
                    selektiert = selection.getValue();
                    deleteButton.setEnabled(true);
                }
            }
        });

        //deleteButton Config
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ReservierungDTO reservierungDTO = ReservierungControlProxy.getInstance().getReservierungForAuto(selektiert, userDTO);
                DeleteReservierungWindow deleteReservierungWindow = new DeleteReservierungWindow(reservierungDTO);
                UI.getCurrent().addWindow( new DeleteWindow(deleteReservierungWindow) );
            }
        });

        //HorizontalLayout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(deleteButton);

        //Darstellung
        addComponent(grid);
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }
}
