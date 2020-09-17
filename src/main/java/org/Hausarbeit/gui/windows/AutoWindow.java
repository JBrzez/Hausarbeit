package org.Hausarbeit.gui.windows;

import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.SQLException;
import java.util.List;

public class AutoWindow extends Window {
    private TextField name;
    private TextField art;
    private TextField studiengang;
    private TextArea beschreibung;

    public AutoWindow(AutoDTO autoDTO, UserDTO userDTO, String endkunde) {
        super(autoDTO.getmarke() + " - " + autoDTO.getbaujahr());
        center();

        //Name
        name = new TextField("Marke");
        name.setValue(autoDTO.getmarke());
        name.setReadOnly(true);

        //Art
        art = new TextField("Baujahr");
        art.setValue(autoDTO.getbaujahr());
        art.setReadOnly(true);

        //Studiengang
        studiengang = new TextField("Ansprechpartner-ID");
        studiengang.setValue("" + autoDTO.getVertriebler_id());
        studiengang.setReadOnly(true);

        //Beschreibung
        beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(autoDTO.getBeschreibung());
        beschreibung.setReadOnly(true);

        //OkButton
        Button okButton = new Button("Ok");
        okButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

        //BewerbenButton
        Button bewerbenButton = new Button("Reservieren");
        ReservierungControlProxy.getInstance().checkAllowed(autoDTO, userDTO, bewerbenButton);
        bewerbenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new FreitextWindow(autoDTO, userDTO));
                close();
            }
        });

        //Horizontal
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(bewerbenButton);

        //Vertikal
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout = this.buildVerticalLayout(verticalLayout, name, art, studiengang, beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }

    public AutoWindow(AutoDTO autoDTO, Grid<AutoDTO> grid, UserDTO userDTO) {
        super(autoDTO.getmarke() + " - " + autoDTO.getbaujahr());
        center();

        //Name
        name = new TextField("Marke");
        name.setValue(autoDTO.getmarke());
        name.setReadOnly(true);

        //Art
        art = new TextField("Baujahr");
        art.setValue(autoDTO.getbaujahr());
        art.setReadOnly(true);

        //Studiengang
        studiengang = new TextField("Ansprechpartner-ID");
        studiengang.setValue("" + autoDTO.getVertriebler_id());
        studiengang.setReadOnly(true);

        //Beschreibung
        beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(autoDTO.getBeschreibung());
        beschreibung.setReadOnly(true);

        //SaveButton
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                autoDTO.setmarke(name.getValue());
                autoDTO.setbaujahr(art.getValue());
                autoDTO.setVertriebler_id(Integer.parseInt(studiengang.getValue()));
                autoDTO.setBeschreibung(beschreibung.getValue());

                try {
                    AutoControlProxy.getInstance().updateAuto(autoDTO);
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
        verticalLayout = this.buildVerticalLayout(verticalLayout, name, art, studiengang,  beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }

    public VerticalLayout buildVerticalLayout(VerticalLayout verticalLayout, TextField name, TextField art, TextField studiengang, TextArea beschreibung, HorizontalLayout horizontalLayout ){
        verticalLayout.addComponent(name);
        verticalLayout.addComponent(art);
        verticalLayout.addComponent(studiengang);
        verticalLayout.addComponent(beschreibung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
        return verticalLayout;
    }
}
