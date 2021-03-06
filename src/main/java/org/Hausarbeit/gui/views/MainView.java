package org.Hausarbeit.gui.views;

import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.Hausarbeit.gui.components.TopPanel;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.gui.windows.AutoWindow;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.proxy.SearchControlProxy;
import org.Hausarbeit.services.util.BuildGrid;

import java.sql.SQLException;
import java.util.List;

public class MainView extends VerticalLayout implements View {

    private AutoDTO selektiert = null;
    private List<AutoDTO> list;
    private String suchtext;


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        //User user = (User) VaadinSession.getCurrent().getAttribute(Roles.CURRENT_USER);
//        UserDTO userDTO = ( (MyUI) UI.getCurrent() ).getUserDTO();

        this.setUp();
    }

    private void setUp() {
        UserDTO userDTO = ( (MyUI) UI.getCurrent() ).getUserDTO();
        //Top Layer
        this.addComponent( new TopPanel() );
        Label line = new Label("<hr>", ContentMode.HTML);
        this.addComponent(line);
        line.setSizeFull();
        setStyleName("schrift-profil");
        //Tabelle
        final Grid<AutoDTO> grid = new Grid<>("Ihre Treffer");

        grid.setHeightMode(HeightMode.ROW);
//        grid.setHeightMode(HeightMode.UNDEFINED);
        grid.setSizeFull();
        BuildGrid.buildGrid(grid);
        SingleSelect<AutoDTO> selection = grid.asSingleSelect();
        grid.setStyleName("schrift-tabelle");
        //DetailButton
        Button detailButton = new Button("Details", VaadinIcons.ENTER);
        detailButton.setEnabled(false);
        detailButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (selection.getValue() == null) {
                    detailButton.setEnabled(false);
                }
                selektiert = selection.getValue();
                UI.getCurrent().addWindow( new AutoWindow(selektiert, userDTO) );
            }
        });

        //SearchButton
        Button searchButton = new Button("Suchen", VaadinIcons.SEARCH);
        searchButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        //Combobox
        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPlaceholder("Filtern nach");
        comboBox.setItems("Marke", "Baujahr", "Beschreibung");

        //SelectionListener Tabelle
        grid.addSelectionListener(new SelectionListener<AutoDTO>() {
            @Override
            public void selectionChange(SelectionEvent<AutoDTO> event) {
                if (selection.getValue() == null) {
                    detailButton.setEnabled(false);
                } else {
                    System.out.println("Zeile selektiert: " + selection.getValue());
                    selektiert = selection.getValue();
                    detailButton.setEnabled(true);
                }
            }
        });

        //Suchfeld
        final TextField search = new TextField();
        search.setWidth("300");
        search.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                search(search, comboBox, grid, detailButton);
            }
        });

        //SearchButton Config
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                suchtext = search.getValue();
                if(suchtext.equals("")) {
                    try {
                        list = SearchControlProxy.getInstance().getAnzeigenForSearch(suchtext, comboBox.getValue());
                    } catch (SQLException e) {
                        Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
                    }
                    grid.setItems(list);
                    addComponent(grid);
                    addComponent(detailButton);
                    setComponentAlignment(detailButton, Alignment.MIDDLE_CENTER);
                } else {
                    search(search, comboBox, grid, detailButton);
                }
            }
        });

        //Horizontal Layout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(comboBox);
        horizontalLayout.addComponent(search);
        horizontalLayout.addComponent(searchButton);
        horizontalLayout.setComponentAlignment(search, Alignment.MIDDLE_CENTER);
        horizontalLayout.setComponentAlignment(searchButton, Alignment.MIDDLE_CENTER);

        //Darstellen
        this.addComponent(horizontalLayout);
        this.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

        try {
            grid.setItems(SearchControlProxy.getInstance().getAnzeigenForSearch("", null));
        } catch (SQLException throwables) {
            Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            throwables.printStackTrace();
        }
            addComponent(grid);
            if(((MyUI) UI.getCurrent()).getUserDTO() != null)
            {
                addComponent(detailButton);
                setComponentAlignment(detailButton, Alignment.MIDDLE_CENTER);
            }
    }

    private void search(TextField search, ComboBox<String> comboBox, Grid<AutoDTO> grid, Button detailButton) {
        if (search.getValue().length() > 1) {
            suchtext = search.getValue();
            System.out.println(suchtext);
            String filter = comboBox == null ? null : comboBox.getValue();

            filter = filter == null ? "marke" : filter.toLowerCase() == "vertriebler-id" ? "vertriebler_id" : filter;
            System.out.println(comboBox.getValue());
            try {
                list = SearchControlProxy.getInstance().getAnzeigenForSearch(suchtext, filter);
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!");
            }
//            grid.setItems();
            grid.setItems(list);
            addComponent(grid);
            addComponent(detailButton);
            setComponentAlignment(detailButton, Alignment.MIDDLE_CENTER);
        } else {
            removeComponent(grid);
            removeComponent(detailButton);
        }
    }


}
