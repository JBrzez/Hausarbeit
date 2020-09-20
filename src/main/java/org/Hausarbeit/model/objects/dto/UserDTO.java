package org.Hausarbeit.model.objects.dto;

import com.vaadin.ui.Notification;
import org.Hausarbeit.model.dao.RoleDAO;
import org.Hausarbeit.services.util.BCrypt;
import org.Hausarbeit.services.util.Roles;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UserDTO extends AbstractDTO implements Serializable {
    private int id;
    private String vorname;
    private String nachname;
    private String email;
    private String password;
    private String anrede;
    private String rolle;

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        if(Pattern.matches("[0-9]+", rolle)){
            rolle = RoleDAO.getInstance().getRoleFromID(Integer.parseInt(rolle));
        }
        this.rolle = rolle;
    }

    public boolean isVertriebler() {
        return this.rolle.toLowerCase().equals(Roles.VERTRIEBLER.toLowerCase());
    }

    public boolean isEndkunde() {
        return this.rolle.toLowerCase().equals(Roles.ENDKUNDE.toLowerCase());
    }



    public UserDTO() {}
    public UserDTO(UserDTO userDTO) {
        this.id = userDTO.id;
        this.vorname = userDTO.vorname;
        this.nachname = userDTO.nachname;
        this.email = userDTO.email;
        this.password = userDTO.password;
        this.anrede = userDTO.anrede;
        this.rolle = userDTO.rolle;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String id) {
        this.email = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() == 0) {
            return;
        }

        this.password = (isHashed(password) ? password : BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    private boolean isHashed(String password) {
        return password.length() == 60 && password.charAt(0) == '$' && password.charAt(3) == '$' && password.charAt(6) == '$';
    }
    public boolean checkpassword(String password) {
        return BCrypt.checkpw(password, getPassword());
    }

}
