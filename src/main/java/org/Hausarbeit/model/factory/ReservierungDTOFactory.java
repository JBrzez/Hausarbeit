package org.Hausarbeit.model.factory;

import org.Hausarbeit.model.objects.dto.ReservierungDTO;

public class ReservierungDTOFactory {

    public static ReservierungDTO createReservierungDTO(int id, String text) {
        ReservierungDTO reservierungDTO = new ReservierungDTO();
        reservierungDTO.setId(id);
        reservierungDTO.setFreitext(text);
        return reservierungDTO;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Dieses Object kann nicht geclont werden!");
    }
}
