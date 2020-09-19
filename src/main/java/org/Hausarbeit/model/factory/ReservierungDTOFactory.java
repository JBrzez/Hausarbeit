package org.Hausarbeit.model.factory;

import org.Hausarbeit.model.objects.dto.ReservierungDTO;

public class ReservierungDTOFactory {

    public static ReservierungDTO createReservierungDTO(int user_id, int auto_id) {
        ReservierungDTO reservierungDTO = new ReservierungDTO();
        reservierungDTO.setUser_id(user_id);
        reservierungDTO.setAuto_id(auto_id);

        return reservierungDTO;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Dieses Object kann nicht geclont werden!");
    }
}
