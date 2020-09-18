package org.Hausarbeit.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.Hausarbeit.gui.components.TopPanel;
import org.Hausarbeit.gui.ui.MyUI;
import org.Hausarbeit.gui.windows.ConfirmationWindow;
import org.Hausarbeit.gui.windows.DeleteProfileWindow;
import org.Hausarbeit.gui.windows.DeleteWindow;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.ProfileException;
import org.Hausarbeit.process.proxy.ProfileControlProxy;
import org.Hausarbeit.services.util.Roles;

import java.sql.SQLException;
import java.time.LocalDate;

public class ProfileView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UserDTO userDTO = ((MyUI) UI.getCurrent()).getUserDTO();
        this.setUp();
    }

    private void setUp() {
        //Top Layer
        this.addComponent(new TopPanel());
        Label line = new Label("<hr>", ContentMode.HTML);
        this.addComponent(line);
        line.setSizeFull();
        this.addStyleName("schrift-profil");
        UserDTO userDTO = ((MyUI) UI.getCurrent()).getUserDTO();
        setStyleName("schrift-profil");
        //Felder Endkunde erzeugen

        final NativeSelect<String> anrede = new NativeSelect<>("Anrede");
        anrede.setItems("Herr", "Frau");

        final TextField vorname = new TextField("Vorname");
        vorname.setPlaceholder("Max");

        TextField name = new TextField("Name");
        name.setPlaceholder("Mustermann");

        DateField gebDatum = new DateField("Geburtsdatum");
        gebDatum.setValue(LocalDate.now());

        Label meinProfil = new Label("Mein Profil");
        //Felder Unternehmen erzeugen
        TextField firmenname = new TextField("Firmenname");
        firmenname.setPlaceholder("Firma Exa");

        TextField ansprechpartner = new TextField("Ansprechpartner");
        ansprechpartner.setPlaceholder("Herr Max Mustermann");

        TextField strasse = new TextField("Strasse");
        strasse.setPlaceholder("Beispielweg");

        TextField haus_nr = new TextField("Hausnummer");
        haus_nr.setPlaceholder("1");

        TextField zusatz = new TextField("Zusatz");
        zusatz.setPlaceholder("a");

        TextField ort = new TextField("Ort");
        ort.setPlaceholder("Bonn");

        TextField plz = new TextField("Plz");
        plz.setPlaceholder("53123");

        TextField branche = new TextField("Branche");
        branche.setPlaceholder("IT");

        Label meinUnternehmen = new Label("Mein Unternehmensprofil");

        if (userDTO.hasRole(Roles.ENDKUNDE)) {
            //Werte einsetzen
            EndkundeDTO EndkundeDTO = new EndkundeDTO(userDTO);
            try {
                EndkundeDTO = ProfileControlProxy.getInstance().getEndkunde(userDTO);
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!", Notification.Type.ERROR_MESSAGE);
            }
            if (EndkundeDTO.getAnrede() != null) {
                anrede.setValue(EndkundeDTO.getAnrede());
            }
            if (EndkundeDTO.getVorname() != null) {
                vorname.setValue(EndkundeDTO.getVorname());
            }
            if (EndkundeDTO.getName() != null) {
                name.setValue(EndkundeDTO.getName());
            }
        } else {
            //Werte Setzen
            VertrieblerDTO VertrieblerDTO = new VertrieblerDTO(userDTO);
            try {
                VertrieblerDTO = ProfileControlProxy.getInstance().getVertriebler(userDTO);
            } catch (SQLException e) {
                Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte informieren Sie einen Administrator!", Notification.Type.ERROR_MESSAGE);
            }
            if (VertrieblerDTO.getName() != null) {
                firmenname.setValue(VertrieblerDTO.getName());
            }
            if (VertrieblerDTO.getStrasse() != null) {
                strasse.setValue(VertrieblerDTO.getStrasse());
            }
            if (VertrieblerDTO.getPlz() != null) {
                plz.setValue(String.valueOf(VertrieblerDTO.getPlz()));
            }
            if (VertrieblerDTO.getHaus_nr() != null) {
                haus_nr.setValue(String.valueOf(VertrieblerDTO.getHaus_nr()));
            }
            if (VertrieblerDTO.getZusatz() != null) {
                zusatz.setValue(VertrieblerDTO.getZusatz());
            }
        }

        //Event Nutzer löschen
        Button deleteButton = new Button("Profil löschen");
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DeleteProfileWindow deleteProfileWindow = new DeleteProfileWindow();
                UI.getCurrent().addWindow( new DeleteWindow(deleteProfileWindow) );
            }
        });

        //Event Nutzerdaten updaten
        Button overwriteBtn = new Button("Daten aktualisieren");
        overwriteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //UI.getCurrent().addWindow(new ConfirmationWindow("Sollen alle Daten aktualisiert werden?"));
                if (userDTO.hasRole(Roles.ENDKUNDE)) {
                    EndkundeDTO EndkundeDTO = new EndkundeDTO(userDTO);
                    EndkundeDTO.setAnrede(anrede.getValue());
                    EndkundeDTO.setVorname(vorname.getValue());
                    EndkundeDTO.setName(name.getValue());

                    try {
                        ProfileControlProxy.getInstance().updateEndkundeData(EndkundeDTO);
                        UI.getCurrent().addWindow(new ConfirmationWindow("Ihr Profil wurde erfolgreich aktualisiert!"));
                    } catch (ProfileException e) {
                        Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                    }

                } else {
                    VertrieblerDTO VertrieblerDTO = new VertrieblerDTO(userDTO);
                    VertrieblerDTO.setName(firmenname.getValue());
                    VertrieblerDTO.setStrasse(strasse.getValue());
                    VertrieblerDTO.setPlz(Integer.valueOf(plz.getValue()));
                    VertrieblerDTO.setHaus_nr(Integer.valueOf(haus_nr.getValue()));
                    VertrieblerDTO.setZusatz(zusatz.getValue());

                    try {
                        ProfileControlProxy.getInstance().updateVertrieblerData(VertrieblerDTO);
                        UI.getCurrent().addWindow(new ConfirmationWindow("Ihr Profil wurde erfolgreich aktualisiert!"));
                    } catch (ProfileException e) {
                        Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                    }

                }
            }
        });

        // Horizontal Strasse
        HorizontalLayout horizontalLayoutStrasse = new HorizontalLayout();
        horizontalLayoutStrasse.addComponent(strasse);
        horizontalLayoutStrasse.addComponent(haus_nr);
        horizontalLayoutStrasse.addComponent(zusatz);

        //Horizontal Ort
        HorizontalLayout horizontalLayoutOrt = new HorizontalLayout();
        horizontalLayoutOrt.addComponent(ort);
        horizontalLayoutOrt.addComponent(plz);

        //Horizontal Name
        HorizontalLayout horizontalLayoutName = new HorizontalLayout();
        horizontalLayoutName.addComponent(vorname);
        horizontalLayoutName.addComponent(name);

        //horizontal Uni
        HorizontalLayout horizontalLayoutUni = new HorizontalLayout();
        if (userDTO.hasRole(Roles.ENDKUNDE)) {
            this.addComponent(meinProfil);
            this.addComponent(anrede);
            this.addComponent(horizontalLayoutName);
            this.addComponent(horizontalLayoutUni);
            this.addComponent(gebDatum);
            this.addComponent(overwriteBtn);
            this.addComponent(deleteButton);
        } else {
            this.addComponent(meinUnternehmen);
            this.addComponent(firmenname);
            this.addComponent(ansprechpartner);
            this.addComponent(horizontalLayoutStrasse);
            this.addComponent(horizontalLayoutOrt);
            this.addComponent(branche);
            this.addComponent(overwriteBtn);
            this.addComponent(deleteButton);
        }
    }
}
