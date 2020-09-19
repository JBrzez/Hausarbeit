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
import org.Hausarbeit.gui.windows.CreateAutoWindow;
import org.Hausarbeit.gui.windows.DeleteAutoWindow;
import org.Hausarbeit.gui.windows.DeleteWindow;
import org.Hausarbeit.gui.windows.AutoWindow;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.proxy.SearchControlProxy;
import org.Hausarbeit.services.util.BuildGrid;

import java.sql.SQLException;
import java.util.List;

public class AutoView extends VerticalLayout implements View {

    private AutoDTO selektiert;
    private List<AutoDTO> list;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        //User user = (User) VaadinSession.getCurrent().getAttribute(Roles.CURRENT_USER);
        UserDTO userDTO = new UserDTO(((MyUI) UI.getCurrent()).getUserDTO());
        this.setUp(userDTO);
    }

    private void setUp(UserDTO userDTO) {

        //Top Layer
        this.addComponent(new TopPanel());
        setStyleName("schrift-profil");
        Label line = new Label("<hr>", ContentMode.HTML);
        this.addComponent(line);
        line.setSizeFull();
        //Tabelle
        final Grid<AutoDTO> grid = new Grid<>("Ihre Autos");
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.ROW);
        grid.setStyleName("schrift-tabelle");
        SingleSelect<AutoDTO> selection = grid.asSingleSelect();

        //Tabelle befüllen
        try {
            list = SearchControlProxy.getInstance().getAnzeigenForUser();
        } catch (SQLException e) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
        }
        BuildGrid.buildGrid(grid);
        //grid.addColumn(AutoDTO::getAnzahl_Reservierung).setCaption("Anzahl der Reservierungen");
        grid.setItems(list);

        //ShowButton
        Button editButton = new Button("Bearbeiten");
        editButton.setEnabled(false);

        //CreateButton
        Button createButton = new Button("Erstellen");

        //DeleteButton
        Button deleteButton = new Button("Löschen");
        deleteButton.setEnabled(false);

        //Tabellen Select Config
        grid.addSelectionListener(new SelectionListener<AutoDTO>() {
            @Override
            public void selectionChange(SelectionEvent<AutoDTO> event) {
                if (selection.getValue() == null) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    System.out.println("Zeile selektiert: " + selection.getValue());
                    selektiert = selection.getValue();
                    deleteButton.setEnabled(true);
                    editButton.setEnabled(true);
                }
            }
        });

        //ShowButton Config Auto Bearbeiten
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                AutoWindow window = new AutoWindow(selektiert, grid, userDTO, true);
                UI.getCurrent().addWindow(window);
            }
        });

        //CreateButton Config
        createButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CreateAutoWindow window = new CreateAutoWindow(new AutoDTO(), grid, userDTO);
                UI.getCurrent().addWindow(window);
            }
        });

        //deleteButton Config
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DeleteAutoWindow deleteAutoWindow = new DeleteAutoWindow(selektiert);
                UI.getCurrent().addWindow(new DeleteWindow(deleteAutoWindow));
            }
        });

        //HorizontalLayout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(editButton);
        horizontalLayout.addComponent(createButton);
        horizontalLayout.addComponent(deleteButton);

        //Darstellung
        addComponent(grid);
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }
}
