package org.Hausarbeit.process.proxy;

import org.Hausarbeit.model.objects.dto.BewerbungDTO;
import org.Hausarbeit.model.objects.dto.StudentDTO;
import org.Hausarbeit.model.objects.dto.UnternehmenDTO;
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


    public void updateStudentData(StudentDTO studentDTO) throws ProfileException {
        ProfileControl.getInstance().updateStudentData(studentDTO);
    }

    public void updateUnternehmenData(UnternehmenDTO unternehmenDTO) throws ProfileException {
        ProfileControl.getInstance().updateUnternehmenData(unternehmenDTO);
    }

    public StudentDTO getStudent(UserDTO userDTO) throws SQLException {
        return ProfileControl.getInstance().getStudent(userDTO);
    }

    public UnternehmenDTO getUnternehmen(UserDTO userDTO) throws SQLException {
        return ProfileControl.getInstance().getUnternehmen(userDTO);
    }

    public void setBewerbung(String text, StudentDTO studentDTO) throws ProfileException {
        ProfileControl.getInstance().setBewerbung(text, studentDTO);
    }

    public List<BewerbungDTO> getBewerbung(StudentDTO studentDTO) throws SQLException {
        return ProfileControl.getInstance().getBewerbung(studentDTO);
    }
}
