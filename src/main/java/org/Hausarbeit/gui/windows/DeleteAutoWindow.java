package org.Hausarbeit.gui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.Hausarbeit.model.objects.dto.StellenanzeigeDTO;
import org.Hausarbeit.process.exceptions.StellenanzeigeException;
import org.Hausarbeit.process.proxy.StellenanzeigeControlProxy;
import org.Hausarbeit.services.util.Views;

public class DeleteAutoWindow extends DeleteWindow {
    //Window zum Löschen von Stellenanzeigen

    public DeleteAutoWindow(StellenanzeigeDTO stellenanzeigeDTO) {
        this.setText("Sind Sie sicher, dass Sie die Stellenanzeige löschen wollen? <br>" +
                "Dieser Vorgang ist unumkehrbar!");
        this.setDto(stellenanzeigeDTO);
        this.setListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    StellenanzeigeControlProxy.getInstance().deleteStellenanzeige(stellenanzeigeDTO);
                } catch (StellenanzeigeException e) {
                    Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                }
                UI.getCurrent().getNavigator().navigateTo(Views.STELLENANZEIGE);
                for (Window w : UI.getCurrent().getWindows()) {
                    w.close();
                }
            }
        });
    }
}

