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

        int pocet = getSpolupracovnici().size();

        if (pocet == 0) {
            System.out.println("Žádní spolupracovníci - skóre 0.");
            return;
        }

        int body = 0;

        for (Spoluprace s : getSpolupracovnici()) {

            switch (s.getUroven()) {

                case SPATNA:
                    body += 3;
                    break;

                case PRUMERNA:
                    body += 2;
                    break;

                case DOBRA:
                    body += 1;
                    break;
            }
        }

        double prumer = (double) body / pocet;
        double skore = pocet * prumer;

        System.out.println("Počet spolupracovníků: " + pocet);
        System.out.printf("Průměr: %.2f%n", prumer);
        System.out.printf("Rizikové skóre: %.2f%n", skore);

        if (skore < 5) {
            System.out.println("Riziko: NÍZKÉ");
        }
        else if (skore < 15) {
            System.out.println("Riziko: STŘEDNÍ");
        }
        else {
            System.out.println("Riziko: VYSOKÉ");
        }
    }
}
