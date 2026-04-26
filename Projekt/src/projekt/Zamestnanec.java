package projekt;

import java.util.ArrayList;
import java.util.List;

public abstract class Zamestnanec {

    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;

    private List<Spoluprace> spolupracovnici = new ArrayList<>();

    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    public void pridejSpolupraci(Zamestnanec kolega, UrovenSpoluprace uroven) {
        spolupracovnici.add(new Spoluprace(kolega, uroven));
    }

    public List<Spoluprace> getSpolupracovnici() {
        return spolupracovnici;
    }

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

    public abstract void provedDovednost();

    @Override
    public String toString() {
        return id + ": " + jmeno + " " + prijmeni + " (" + rokNarozeni + ")";
    }
}