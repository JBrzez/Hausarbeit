package org.Hausarbeit.model.objects.dto;

public class VertrieblerDTO extends UserDTO {

    private String strasse;
    private Integer plz;
    private Integer haus_nr;
    private String zusatz;



    public VertrieblerDTO(UserDTO userDTO) {
        super(userDTO);
    }



    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public Integer getHaus_nr() {
        return haus_nr;
    }

    public void setHaus_nr(Integer haus_nr) {
        this.haus_nr = haus_nr;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }



}
