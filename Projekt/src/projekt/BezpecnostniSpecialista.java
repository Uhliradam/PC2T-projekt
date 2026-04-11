package projekt;

public class BezpecnostniSpecialista extends Zamestnanec {

    // konstruktor
    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    // vlastní implementace
    @Override
    public void provedDovednost() {
        System.out.println("Bezpečnostní specialista počítá rizikové skóre.");
    }
}
