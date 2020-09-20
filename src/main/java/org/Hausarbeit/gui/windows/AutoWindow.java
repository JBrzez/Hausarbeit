package org.Hausarbeit.gui.windows;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.control.ReservierungControl;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.SQLException;
import java.util.List;

public class AutoWindow extends Window {
    private TextField marke;
    private TextField baujahr;
    private TextField ansprechpartner_id;
    private TextArea beschreibung;

    public AutoWindow(AutoDTO autoDTO, UserDTO userDTO) { // Endkunde
        super("Auto Übersicht");
        center();

        //Marke
        marke = new TextField("Marke");
        marke.setValue(autoDTO.getMarke());
        marke.setReadOnly(true);

        //Baujahr
        baujahr = new TextField("Baujahr");
        baujahr.setValue(autoDTO.getBaujahr());
        baujahr.setReadOnly(true);

        //Ansprechpartner_ID
        ansprechpartner_id = new TextField("Ansprechpartner-ID");
        ansprechpartner_id.setValue("" + autoDTO.getVertriebler_id());
        ansprechpartner_id.setReadOnly(true);

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

        //Reservierenutton
        Button reservierenButton = new Button("Reservieren");
        ReservierungControlProxy.getInstance().checkAllowed(autoDTO, userDTO, reservierenButton);
        reservierenButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                boolean canReserve = false;
                try {
                    canReserve = ReservierungControlProxy.getInstance().userCanReserve(userDTO, autoDTO);
                    if(canReserve) ReservierungControlProxy.getInstance().reserveAuto(autoDTO, userDTO);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(canReserve) Notification.show("Sie haben das Auto erfolgreich reserviert.", Notification.Type.HUMANIZED_MESSAGE);
                if(!canReserve) Notification.show("Sie haben das Auto bereits reserviert.", Notification.Type.ERROR_MESSAGE);
            }
        });

        //Horizontal
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(reservierenButton);

        //Vertikal
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout = this.buildVerticalLayout(verticalLayout, marke, baujahr, ansprechpartner_id, beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }

    public AutoWindow(AutoDTO autoDTO, Grid<AutoDTO> grid, UserDTO userDTO, boolean edit) {
        super("Auto Übersicht");
        center();

        //Marke
        marke = new TextField("Marke");
        marke.setValue(autoDTO.getMarke());
        if(!edit) marke.setReadOnly(true);

        //Baujahr
        baujahr = new TextField("Baujahr");
        baujahr.setValue(autoDTO.getBaujahr());
        if(!edit) baujahr.setReadOnly(true);

        //Beschreibung
        beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(autoDTO.getBeschreibung());
        if(!edit) beschreibung.setReadOnly(true);

        //SaveButton
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                autoDTO.setMarke(marke.getValue());
                autoDTO.setBaujahr(baujahr.getValue());
                autoDTO.setVertriebler_id(userDTO.isVertriebler() ? userDTO.getId() : -1);
                autoDTO.setBeschreibung(beschreibung.getValue());

                try {
                    System.out.println(autoDTO);
                    System.out.println(autoDTO);
                    System.out.println(autoDTO);
                    System.out.println(autoDTO);
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
//                grid.setHeightMode(HeightMode.ROW);
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
        verticalLayout = this.buildVerticalLayout(verticalLayout, marke, baujahr, null,  beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }

    public VerticalLayout buildVerticalLayout(VerticalLayout verticalLayout, TextField marke, TextField baujahr, TextField ansprechpartner_id, TextArea beschreibung, HorizontalLayout horizontalLayout ){
        verticalLayout.addComponent(marke);
        verticalLayout.addComponent(baujahr);
        if(ansprechpartner_id != null) verticalLayout.addComponent(ansprechpartner_id);
        verticalLayout.addComponent(beschreibung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
        return verticalLayout;
    }
}
