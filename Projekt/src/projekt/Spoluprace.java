package projekt;

public class Spoluprace {

    private Zamestnanec kolega;
    private UrovenSpoluprace uroven;

    public Spoluprace(Zamestnanec kolega, UrovenSpoluprace uroven) {
        this.kolega = kolega;
        this.uroven = uroven;
    }

    public Zamestnanec getKolega() {
        return kolega;
    }

    public UrovenSpoluprace getUroven() {
        return uroven;
    }
}
