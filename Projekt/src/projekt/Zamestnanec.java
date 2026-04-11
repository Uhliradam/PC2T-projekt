package projekt;

public class Zamestnanec {

    // atributy (vlastnosti zaměstnance)
    private int id;              // unikátní ID zaměstnance
    private String jmeno;        // křestní jméno
    private String prijmeni;     // příjmení
    private int rokNarozeni;     // rok narození

    // konstruktor - používá se při vytvoření objektu
    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;                    // this = aktuální objekt
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    // gettery - slouží pro získání hodnot (zapouzdření)
    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRokNarozeni() {
        return rokNarozeni;
    }

    // přepis metody toString -> jak se objekt vypíše
    @Override
    public String toString() {
        return id + ": " + jmeno + " " + prijmeni + " (" + rokNarozeni + ")";
    }
}
