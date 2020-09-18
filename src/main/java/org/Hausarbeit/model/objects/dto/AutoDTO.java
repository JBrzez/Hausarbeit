package org.Hausarbeit.model.objects.dto;

import java.time.LocalDate;

public class AutoDTO extends AbstractDTO {
    private int id ;
    private int id_anzeige;
    private String beschreibung = "";
    private String baujahr = "";
    private String marke = "";
    private LocalDate zeitraum = LocalDate.now();
    private int anzahl_reservierung = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_anzeige() {
        return id_anzeige;
    }

    public void setId_anzeige(int id_anzeige) {
        this.id_anzeige = id_anzeige;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(String art) {
        this.baujahr = art;
    }

    public String getMarke() {
        return marke;
    }
    public void setMarke(String name) {
        this.marke = name;
    }

    public LocalDate getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(LocalDate zeitraum) {
        this.zeitraum = zeitraum;
    }

    public String toString(){
        return "ID: " + this.getId() + "\n" +
                "ID Anzeige: " + this.getId_anzeige() + "\n" +
                "Beschreibung: " + this.getBeschreibung() + "\n" +
                "Marke: " + this.getMarke() + "\n" +
                "Baujahr: " + this.getBaujahr() + "\n" +
                "Zeitraum: " + this.getZeitraum().toString() + "\n";
    }

    public int setAnzahl_Reservierung() {
        return anzahl_reservierung;
    }

    public void setAnzahl_Reservierung(int anzahl_reservierung) {
        this.anzahl_reservierung = anzahl_reservierung;
    }
}
