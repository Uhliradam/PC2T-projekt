package projekt;

public class DatovyAnalytik extends Zamestnanec {

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void provedDovednost() {
        System.out.println("Datový analytik hledá nejvíce společných spolupracovníků.");

        if (getSpolupracovnici().isEmpty()) {
            System.out.println("Žádní spolupracovníci.");
            return;
        }

        Zamestnanec nejlepsi = null;
        int maxSpolecnych = -1;

        for (Spoluprace s1 : getSpolupracovnici()) {

            Zamestnanec kolega = s1.getKolega();

            int spolecni = 0;

            for (Spoluprace s2 : kolega.getSpolupracovnici()) {

                if (s2.getKolega().getId() == this.getId()) {
                    continue;
                }

                for (Spoluprace muj : getSpolupracovnici()) {

                    if (muj.getKolega().getId() == s2.getKolega().getId()) {
                        spolecni++;
                        break;
                    }
                }
            }

            if (spolecni > maxSpolecnych) {
                maxSpolecnych = spolecni;
                nejlepsi = kolega;
            }
        }

        if (nejlepsi != null) {
            System.out.println("Nejvíce společných spolupracovníků (" + maxSpolecnych + ") sdílím s:");
            System.out.println(nejlepsi);
        }
    }
}
