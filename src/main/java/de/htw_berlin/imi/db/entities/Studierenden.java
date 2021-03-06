package de.htw_berlin.imi.db.entities;

public class Studierenden extends Entity {

    private long id;
    private long matr_nr;
    private String name;
    private String vorname;
    private String geburtsdatum;
    private String geburtsort;
    private int anzahl_semester;
    private String studienbeginn;

    public Studierenden(long id) {
        super(id);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getMatr_nr() {
        return matr_nr;
    }

    public void setMatr_nr(final long matr_nr) {
        this.matr_nr = matr_nr;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(final String vorname) {
        this.vorname = vorname;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(final String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getGeburtsort() {
        return geburtsort;
    }

    public void setGeburtsort(final String geburtsort) {
        this.geburtsort = geburtsort;
    }

    public int getAnzahl_semester() {
        return anzahl_semester;
    }

    public void setAnzahl_semester(final int anzahl_semester) {
        this.anzahl_semester = anzahl_semester;
    }

    public String getStudienbeginn() {
        return studienbeginn;
    }

    public void setStudienbeginn(final String studienbeginn) {
        this.studienbeginn = studienbeginn;
    }

}
