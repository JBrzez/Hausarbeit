package org.Hausarbeit.process.proxy;

import org.Hausarbeit.model.objects.dto.ReservierungDTO;
import org.Hausarbeit.model.objects.dto.EndkundeDTO;
import org.Hausarbeit.model.objects.dto.VertrieblerDTO;
import org.Hausarbeit.model.objects.dto.UserDTO;
import org.Hausarbeit.process.Interfaces.ProfileControlInterface;
import org.Hausarbeit.process.control.ProfileControl;
import org.Hausarbeit.process.exceptions.ProfileException;

import java.sql.SQLException;
import java.util.List;

public class ProfileControlProxy implements ProfileControlInterface {
    private static ProfileControlProxy profileControl = null;

    private ProfileControlProxy() {
    }

    public static ProfileControlProxy getInstance() {
        if (profileControl == null) {
            profileControl = new ProfileControlProxy();
        }
        return profileControl;
    }


    public void updateEndkundeData(EndkundeDTO EndkundeDTO) throws ProfileException {
        ProfileControl.getInstance().updateEndkundeData(EndkundeDTO);
    }

    public void updateVertrieblerData(VertrieblerDTO VertrieblerDTO) throws ProfileException {
        ProfileControl.getInstance().updateVertrieblerData(VertrieblerDTO);
    }

    public EndkundeDTO getEndkunde(UserDTO userDTO) throws SQLException {
        return ProfileControl.getInstance().getEndkunde(userDTO);
    }

    public VertrieblerDTO getVertriebler(UserDTO userDTO) throws SQLException {
        return ProfileControl.getInstance().getVertriebler(userDTO);
    }

    public void setBewerbung(String text, EndkundeDTO EndkundeDTO) throws ProfileException {
        ProfileControl.getInstance().setReservierung(text, EndkundeDTO);
    }

    public List<ReservierungDTO> getBewerbung(EndkundeDTO EndkundeDTO) throws SQLException {
        return ProfileControl.getInstance().getReservierung(EndkundeDTO);
    }
}
