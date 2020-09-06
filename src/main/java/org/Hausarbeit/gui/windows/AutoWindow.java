package org.Hausarbeit.gui.windows;

import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.SQLException;
import java.util.List;

public class AutoWindow extends Window {
    private TextField name;
    private TextField art;
    private TextField branche;
    private TextField studiengang;
    private TextField ort;
    private TextArea beschreibung;

    public AutoWindow(AutoDTO autoDTO, UserDTO userDTO) {
        super(autoDTO.getName());
        center();

        //Name
        name = new TextField("Titel");
        name.setValue(autoDTO.getName());
        name.setReadOnly(true);

        //Art
        art = new TextField("Art");
        art.setValue(autoDTO.getArt());
        art.setReadOnly(true);

        //Branche
        branche = new TextField("Branche");
        branche.setValue(autoDTO.getBranche());
        branche.setReadOnly(true);

        //Studiengang
        studiengang = new TextField("Studiengang");
        studiengang.setValue(autoDTO.getStudiengang());
        studiengang.setReadOnly(true);

        //Ort
        ort = new TextField("Ort");
        ort.setValue(autoDTO.getOrt());
        ort.setReadOnly(true);

        //Zeitraum
        DateField zeitraum = new DateField("Ende der Ausschreibung");
        zeitraum.setValue(autoDTO.getZeitraum());
        zeitraum.setReadOnly(true);

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
        Button bewerbenButton = new Button("Bewerben");
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
        verticalLayout = this.buildVerticalLayout(verticalLayout, name, art, branche, studiengang, ort, zeitraum, beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }

    public AutoWindow(AutoDTO Auto, Grid<AutoDTO> grid, VertrieblerDTO vertrieblerDTO) {
        super(Auto.getName());
        center();

        //Name
        name = new TextField("Titel");
        name.setValue(Auto.getName());

        //Art
        art = new TextField("Art");
        art.setValue(Auto.getArt());

        //Branche
        branche = new TextField("Branche");
        branche.setValue(Auto.getBranche());

        //Studiengang
        studiengang = new TextField("Studiengang");
        studiengang.setValue(Auto.getStudiengang());

        //Ort
        ort = new TextField("Ort");
        ort.setValue(Auto.getOrt());

        //Zeitraum
        DateField zeitraum = new DateField("Ende der Ausschreibung");
        zeitraum.setValue(Auto.getZeitraum());

        //Beschreibung
        beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(Auto.getBeschreibung());

        //SaveButton
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Auto.setName(name.getValue());
                Auto.setArt(art.getValue());
                Auto.setBranche(branche.getValue());
                Auto.setStudiengang(studiengang.getValue());
                Auto.setOrt(ort.getValue());
                Auto.setZeitraum(zeitraum.getValue());
                Auto.setBeschreibung(beschreibung.getValue());

                try {
                    AutoControlProxy.getInstance().updateAuto(Auto);
                } catch (AutoException e) {
                    Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                }
                UI.getCurrent().addWindow(new ConfirmationWindow("Auto erfolgreich gespeichert"));
                List<AutoDTO> list = null;
                try {
                    list = AutoControlProxy.getInstance().getAnzeigenForVertriebler(vertrieblerDTO);
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
        verticalLayout = this.buildVerticalLayout(verticalLayout, name, art, branche, studiengang, ort, zeitraum, beschreibung, horizontalLayout);
        setContent(verticalLayout);
    }
    public VerticalLayout buildVerticalLayout(VerticalLayout verticalLayout, TextField name, TextField art, TextField branche, TextField studiengang,
                                              TextField ort, DateField zeitraum, TextArea beschreibung, HorizontalLayout horizontalLayout ){
        verticalLayout.addComponent(name);
        verticalLayout.addComponent(art);
        verticalLayout.addComponent(branche);
        verticalLayout.addComponent(studiengang);
        verticalLayout.addComponent(ort);
        verticalLayout.addComponent(zeitraum);
        verticalLayout.addComponent(beschreibung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
        return verticalLayout;
    }
}
