package de.htw_berlin.imi.db.web;

/**
 * Data transfer object (DTO) class:
 * decouples representation from the entity class.
 * <p>
 * NB: we cannot create BueroRaum objects without an id.
 * Objects of this class simply hold field values
 */
public class BueroDto {

    private long id;
    private String name;

    private String raumnummer;

    private int kapazitaet;

    private double flaeche;

    private double hoehe;

    // TODO: add missing fields
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRaumnummer() {
        return raumnummer;
    }

    public void setRaumnummer(final String raumnummer) {
        this.raumnummer = raumnummer;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(final int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public double getFlaeche() {
        return flaeche;
    }

    public void setFlaeche(final double flaeche) {
        this.flaeche = flaeche;
    }

    public double getHoehe() {
        return hoehe;
    }

    public void setHoehe(final double hoehe) {
        this.hoehe = hoehe;
    }
}
