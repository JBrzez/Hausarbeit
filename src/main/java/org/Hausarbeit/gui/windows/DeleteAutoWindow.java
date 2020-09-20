package org.Hausarbeit.gui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.Hausarbeit.process.exceptions.AutoException;
import org.Hausarbeit.process.proxy.AutoControlProxy;
import org.Hausarbeit.services.util.Views;

public class DeleteAutoWindow extends DeleteWindow {
    //Window zum Löschen von Auton

    public DeleteAutoWindow(AutoDTO AutoDTO) {
        this.setText("Sind Sie sicher, dass Sie das Auto löschen wollen? <br>" +
                "Dieser Vorgang ist unumkehrbar!");
        this.setDto(AutoDTO);
        this.setListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    AutoControlProxy.getInstance().deleteAuto(AutoDTO);
                } catch (AutoException e) {
                    Notification.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Notification.Type.ERROR_MESSAGE);
                }
                UI.getCurrent().getNavigator().navigateTo(Views.AUTO);
                for (Window w : UI.getCurrent().getWindows()) {
                    w.close();
                }
            }
        });
    }
}

