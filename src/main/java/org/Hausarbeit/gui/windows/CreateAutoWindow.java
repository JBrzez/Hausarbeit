package org.Hausarbeit.gui.windows;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.SQLException;
import java.util.List;

public class CreateAutoWindow extends Window {

    public CreateAutoWindow(AutoDTO auto, Grid<AutoDTO> grid, UserDTO userDTO) { // Vorher Vertriebler
        super("Auto erstellen:");
        center();

        //Marke
        TextField marke = new TextField("Marke");
        marke.setValue(auto.getMarke());
        marke.setRequiredIndicatorVisible(true);
        new Binder<AutoDTO>().forField(marke).withValidator(txt -> txt.length() > 1, "Mindestens 2 Zeichen").bind(AutoDTO::getMarke, AutoDTO::setMarke);


        //Baujahr
        TextField baujahr = new TextField("Baujahr");
        baujahr.setValue(auto.getBaujahr());
        baujahr.setRequiredIndicatorVisible(true);
        new Binder<AutoDTO>().forField(baujahr).withValidator(txt -> txt.length() == 4, "Genau 4 Zeichen").bind(AutoDTO::getBaujahr, AutoDTO::setBaujahr);

        //Beschreibung
        TextArea beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(auto.getBeschreibung());


        //saveButton Config
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                auto.setMarke(marke.getValue());
                auto.setBaujahr(baujahr.getValue());
                auto.setVertriebler_id(userDTO.isVertriebler() ? userDTO.getId() : -1);
                auto.setBeschreibung(beschreibung.getValue());

                if(marke.getValue().length() < 2)
                {
                    Notification.show("Es muss eine Marke angegeben mit mindestens zwei Zeichen.", Notification.Type.ERROR_MESSAGE);
                    return;
                }
                if(baujahr.getValue().length() != 4)
                {
                    Notification.show("Es muss eine Baujahr angegeben, genau vier Zeichen.", Notification.Type.ERROR_MESSAGE);
                    return;
                }

                try {
                    AutoControlProxy.getInstance().createAuto(auto);
                } catch (AutoException e) {
                    Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                }
                UI.getCurrent().addWindow(new ConfirmationWindow("Auto erfolgreich gespeichert"));
                List<AutoDTO> list = null;
                try {
                    list = AutoControlProxy.getInstance().getAnzeigenForVertriebler(userDTO);
                } catch (SQLException e) {
                    Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!", Notification.Type.ERROR_MESSAGE);
                }
                grid.setItems();
                grid.setItems(list);
                close();
            }
        });

        //abortButton Config
        Button abortButton = new Button("Abbrechen");
        abortButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

        //Horizontal
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(saveButton);
        horizontalLayout.addComponent(abortButton);

        //Vertikal
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(marke);
        verticalLayout.addComponent(baujahr);
        verticalLayout.addComponent(beschreibung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);
    }
}