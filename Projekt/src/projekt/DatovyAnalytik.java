package projekt;

public class DatovyAnalytik extends Zamestnanec {

    // konstruktor volá konstruktor rodiče
    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    // implementace abstraktní metody
    @Override
    public void provedDovednost() {
        System.out.println("Datový analytik hledá nejvíce společných spolupracovníků.");
    }
}
