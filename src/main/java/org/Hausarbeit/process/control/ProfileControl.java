package org.Hausarbeit.process.control;


import org.Hausarbeit.model.dao.EndkundeDAO;
import org.Hausarbeit.model.dao.ReservierungDAO;

import org.Hausarbeit.model.dao.VertrieblerDAO;

import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.ReservierungDTO;

import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.process.Interfaces.ProfileControlInterface;
import org.Hausarbeit.process.exceptions.ProfileException;

import java.sql.SQLException;
import java.util.List;

public class ProfileControl implements ProfileControlInterface {
    private static ProfileControl profileControl = null;

    private ProfileControl() {
    }

    public static ProfileControl getInstance() {
        if (profileControl == null) {
            profileControl = new ProfileControl();
        }
        return profileControl;
    }


    public void updateEndkundeData(EndkundeDTO endkundeDTO) throws ProfileException {
        boolean result =  EndkundeDAO.getInstance().updateEndkunde(endkundeDTO);
        if (result) {
            return;
        }
        throw new ProfileException();
    }

    public void updateVertrieblerData(VertrieblerDTO vertrieblerDTO) throws ProfileException {
        boolean result = VertrieblerDAO.getInstance().updateVertriebler(vertrieblerDTO);
        if (result) {
            return;
        }
        throw new ProfileException();
    }

    public EndkundeDTO getEndkunde(UserDTO userDTO) throws SQLException {
        return EndkundeDAO.getInstance().getAllDataEndkunde(userDTO);
    }

    public VertrieblerDTO getVertriebler(UserDTO userDTO) throws SQLException {
        return VertrieblerDAO.getInstance().getAllDataVertriebler(userDTO);
    }

    public void setReservierung(String text, EndkundeDTO endkundeDTO) throws ProfileException {
        //boolean result =  EndkundeDAO.getInstance().createReservierung(text, endkundeDTO);
        //if (result) {
      //      return;
      //  }
      //  throw new ProfileException();
    }

    public List<ReservierungDTO> getReservierung(EndkundeDTO endkundeDTO) throws SQLException {
        return ReservierungDAO.getInstance().getReservierungForEndkunde(endkundeDTO);
    }
}
