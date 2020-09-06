package org.Hausarbeit.gui.windows;

import com.vaadin.ui.*;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.process.proxy.RegistrationControlProxy;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;

import java.sql.SQLException;

public class FreitextWindow extends Window {

    public FreitextWindow(AutoDTO stellenanzeige, UserDTO userDTO) {
        super(stellenanzeige.getName());
        center();

        //Reservierungstext
        TextArea Reservierung = new TextArea("Reservierungsstext");
        Reservierung.setSizeUndefined();

        //ApplyButton
        Button applyButton = new Button("Einreichen");
        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String Reservierungstext = Reservierung.getValue();

                try {
                    ReservierungControlProxy.getInstance().createReservierung(Reservierungstext, userDTO);
                    int id_Reservierung = ReservierungControlProxy.getInstance().getLatestApply(userDTO);
                    ReservierungControlProxy.getInstance().applyForAuto(stellenanzeige, id_Reservierung);
                }
                catch (DatabaseException db) {
                    Notification.show("Es ist ein Fehler bei der Reservierung aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                    return;
                }
                catch (ReservierungException e) {
                    Notification.show("Reservierung konnte nicht eingereicht werden.", Notification.Type.WARNING_MESSAGE);
                    return;
                } catch (SQLException e) {
                    Notification.show("Es ist ein SQL-Fehler aufgetreten. Bitte kontaktieren Sie den Administrator!", Notification.Type.ERROR_MESSAGE);
                }
                UI.getCurrent().addWindow(new ConfirmationWindow("Reservierung erfolgreich abgegeben!"));
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
        horizontalLayout.addComponent(applyButton);
        horizontalLayout.addComponent(abortButton);

        //Vertikal
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(Reservierung);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);

    }
}
