package org.Hausarbeit.gui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.process.exceptions.ReservierungException;
import org.Hausarbeit.process.proxy.ReservierungControlProxy;
import org.Hausarbeit.services.util.Views;

public class DeleteReservierungWindow extends DeleteWindow {
    //Window zum Löschen von Reservierungen auf Stellenanzeigen

    public DeleteReservierungWindow(ReservierungDTO ReservierungDTO) {
        this.setText("Sind Sie sicher, dass Sie Ihre Reservierung auf diese Stellenanzeige löschen wollen? <br>" +
                "Dieser Vorgang ist unumkehrbar!");
        this.setDto(ReservierungDTO);
        this.setListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    ReservierungControlProxy.getInstance().deleteReservierung(ReservierungDTO);
                } catch (ReservierungException e) {
                    Notification.show("DB-Fehler", "Löschen war nicht erfolgreich!", Notification.Type.ERROR_MESSAGE);
                }
                Notification.show("Reservierung gelöscht!", Notification.Type.HUMANIZED_MESSAGE);
                UI.getCurrent().getNavigator().navigateTo(Views.RESERVIERUNG);
                for (Window w : UI.getCurrent().getWindows()) {
                    w.close();
                }
            }
        });
    }
}

