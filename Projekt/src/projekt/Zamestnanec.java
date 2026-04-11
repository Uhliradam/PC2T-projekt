package projekt;

public abstract class Zamestnanec {

    // atributy zaměstnance (private = zapouzdření)
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;

    // konstruktor
    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    // gettery - přístup k private atributům
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

    // abstraktní metoda - každá skupina implementuje jinak
    public abstract void provedDovednost();

    // výpis zaměstnance
    @Override
    public String toString() {
        return id + ": " + jmeno + " " + prijmeni + " (" + rokNarozeni + ")";
    }
}