package org.Hausarbeit.model.objects.dto;

import java.time.LocalDate;

public class EndkundeDTO extends UserDTO {
    private String anrede;




    public EndkundeDTO(UserDTO userDTO) {
        super(userDTO);
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }







}
