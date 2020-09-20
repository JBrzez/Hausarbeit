package org.Hausarbeit.model.objects.dto;

public class AutoDTO extends AbstractDTO {
    private int id ;
    private String beschreibung = "";
    private String marke = "";
    private String baujahr = "";
    private int vertriebler_id = 0;
    private int anzahl_reservierung = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }

    public int getVertriebler_id() {
        return vertriebler_id;
    }

    public void setVertriebler_id(int vertriebler_id) {
        this.vertriebler_id = vertriebler_id;
    }

    public String toString(){
        return "ID: " + this.getId() + "\n" +
                "Beschreibung: " + this.getBeschreibung() + "\n" +
                "Marke: " + this.getMarke() + "\n" +
                "Baujahr: " + this.getBaujahr() + "\n" +
                "Vertriebler_ID: " + this.getVertriebler_id() + "\n";
    }

    public int setAnzahl_Reservierung() {
        return anzahl_reservierung;
    }

    public void setAnzahl_Reservierung(int anzahl_reservierung) {
        this.anzahl_reservierung = anzahl_reservierung;
    }
}
