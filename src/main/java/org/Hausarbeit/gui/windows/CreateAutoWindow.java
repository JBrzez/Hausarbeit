package org.Hausarbeit.gui.windows;

import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.proxy.AutoControlProxy;

import java.sql.SQLException;
import java.util.List;

public class CreateAutoWindow extends Window {

    public CreateAutoWindow(AutoDTO Auto, Grid<AutoDTO> grid, VertrieblerDTO vertrieblerDTO) {
        super("Ihre Auto");
        center();

        //Name
        TextField marke = new TextField("Marke");
        marke.setValue(Auto.getMarke());

        //Art
        TextField baujahr = new TextField("Baujahr");
        baujahr.setValue(Auto.getBaujahr());

        //Zeitraum
        DateField zeitraum = new DateField("Ende der Ausschreibung");
        zeitraum.setValue(Auto.getZeitraum());

        //Beschreibung
        TextArea beschreibung = new TextArea("Beschreibung");
        beschreibung.setValue(Auto.getBeschreibung());


        //saveButton Config
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Auto.setMarke(marke.getValue());
                Auto.setBaujahr(baujahr.getValue());
                Auto.setZeitraum(zeitraum.getValue());
                Auto.setBeschreibung(beschreibung.getValue());

                try {
                    AutoControlProxy.getInstance().createAuto(Auto);
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
        verticalLayout.addComponent(zeitraum);
        verticalLayout.addComponent(beschreibung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);
    }
}
