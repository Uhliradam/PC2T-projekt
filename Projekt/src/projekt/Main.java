package projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // dynamický seznam zaměstnanců
    static List<Zamestnanec> zamestnanci = new ArrayList<>();

    // automatické generování ID
    static int nextId = 1;

    // scanner pro vstup z klávesnice
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean konec = false;

        // hlavní smyčka programu
        while (!konec) {

            vypisMenu();

            int volba = sc.nextInt();
            sc.nextLine(); // vyčištění bufferu

            switch (volba) {

                case 1:
                    pridejZamestnance();
                    break;

                case 2:
                    vypisZamestnance();
                    break;

                case 3:
                    pridejSpolupraci();
                    break;
                
                case 4:
                    vypisSpolupracovniky();
                    break;
                    
                case 5:
                    odeberZamestnance();
                    break;
                    
                case 6:
                    vyhledejZamestnance();
                    break;
                    
                case 7:
                    spustDovednost();
                    break;
                    
                case 8:
                    abecedniVypis();
                    break;

                case 0:
                    konec = true;
                    break;

                default:
                    System.out.println("Neplatná volba");
            }
        }

        System.out.println("Program ukončen.");
    }

    // výpis menu
    static void vypisMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Přidat zaměstnance");
        System.out.println("2 - Vypsat zaměstnance");
        System.out.println("3 - Přidat spolupráci");
        System.out.println("4 - Vypsat spolupracovníky");
        System.out.println("5 - Odebrat zaměstnance");
        System.out.println("6 - Vyhledat zaměstnance podle ID");
        System.out.println("7 - Spustit dovednost zaměstnance");
        System.out.println("8 - Abecední výpis podle skupin");
        System.out.println("0 - Konec");
        System.out.print("Volba: ");
    }

    // přidání zaměstnance
    static void pridejZamestnance() {

        System.out.println("Vyber skupinu:");
        System.out.println("1 - Datový analytik");
        System.out.println("2 - Bezpečnostní specialista");

        int typ = sc.nextInt();
        sc.nextLine();

        System.out.print("Zadej jméno: ");
        String jmeno = sc.nextLine();

        System.out.print("Zadej příjmení: ");
        String prijmeni = sc.nextLine();

        System.out.print("Zadej rok narození: ");
        int rok = sc.nextInt();
        sc.nextLine();

        Zamestnanec z;

        // vytvoření podle typu
        if (typ == 1) {
            z = new DatovyAnalytik(nextId++, jmeno, prijmeni, rok);
        } else {
            z = new BezpecnostniSpecialista(nextId++, jmeno, prijmeni, rok);
        }

        zamestnanci.add(z);

        System.out.println("Zaměstnanec přidán.");
    }

    // výpis zaměstnanců
    static void vypisZamestnance() {

        if (zamestnanci.isEmpty()) {
            System.out.println("Žádní zaměstnanci.");
            return;
        }

        for (Zamestnanec z : zamestnanci) {
            System.out.println(z);
        }
    }

    // přidání spolupráce
    static void pridejSpolupraci() {

        System.out.print("Zadej ID zaměstnance: ");
        int id1 = sc.nextInt();

        System.out.print("Zadej ID kolegy: ");
        int id2 = sc.nextInt();

        System.out.println("Úroveň spolupráce:");
        System.out.println("1 - Špatná");
        System.out.println("2 - Průměrná");
        System.out.println("3 - Dobrá");

        int volba = sc.nextInt();
        sc.nextLine();

        Zamestnanec z1 = najdiZamestnance(id1);
        Zamestnanec z2 = najdiZamestnance(id2);

        if (z1 == null || z2 == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        UrovenSpoluprace uroven;

        switch (volba) {
            case 1:
                uroven = UrovenSpoluprace.SPATNA;
                break;
            case 2:
                uroven = UrovenSpoluprace.PRUMERNA;
                break;
            default:
                uroven = UrovenSpoluprace.DOBRA;
        }

        // přidání spolupráce
        z1.pridejSpolupraci(z2, uroven);

        System.out.println("Spolupráce přidána");
    }

    // hledání zaměstnance podle ID
    static Zamestnanec najdiZamestnance(int id) {

        for (Zamestnanec z : zamestnanci) {
            if (z.getId() == id) {
                return z;
            }
        }

        return null;
    }
    
    static void vypisSpolupracovniky() {

        System.out.print("Zadej ID zaměstnance: ");
        int id = sc.nextInt();
        sc.nextLine();

        Zamestnanec z = najdiZamestnance(id);

        // kontrola existence
        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        // kontrola prázdného seznamu
        if (z.getSpolupracovnici().isEmpty()) {
            System.out.println("Nemá žádné spolupracovníky");
            return;
        }

        System.out.println("Spolupracovníci:");

        for (Spoluprace s : z.getSpolupracovnici()) {

            Zamestnanec kolega = s.getKolega();
            UrovenSpoluprace uroven = s.getUroven();

            System.out.println(
                    kolega.getId() + " "
                    + kolega.getJmeno() + " "
                    + kolega.getPrijmeni()
                    + " - " + uroven
            );
        }
    }
    
    static void odeberZamestnance() {

        System.out.print("Zadej ID zaměstnance k odebrání: ");
        int id = sc.nextInt();
        sc.nextLine();

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        // odstranění vazeb u ostatních zaměstnanců
        for (Zamestnanec ostatni : zamestnanci) {

            // removeIf = smaže podle podmínky
            ostatni.getSpolupracovnici().removeIf(
                    s -> s.getKolega().getId() == id
            );
        }

        // odstranění ze seznamu
        zamestnanci.remove(z);

        System.out.println("Zaměstnanec odebrán.");
    }
    
    static void vyhledejZamestnance() {

        System.out.print("Zadej ID zaměstnance: ");
        int id = sc.nextInt();
        sc.nextLine();

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        // základní info
        System.out.println("Zaměstnanec:");
        System.out.println(z);

        int pocet = z.getSpolupracovnici().size();

        System.out.println("Počet spolupracovníků: " + pocet);

        if (pocet == 0) {
            return;
        }

        int spatna = 0;
        int prumerna = 0;
        int dobra = 0;

        // počítání úrovní
        for (Spoluprace s : z.getSpolupracovnici()) {

            switch (s.getUroven()) {

                case SPATNA:
                    spatna++;
                    break;

                case PRUMERNA:
                    prumerna++;
                    break;

                case DOBRA:
                    dobra++;
                    break;
            }
        }

        // Kvalita spolupráce
        System.out.print("Celková kvalita spolupráce: ");

        if (dobra >= prumerna && dobra >= spatna) {
            System.out.println("DOBRÁ");
        }
        else if (prumerna >= dobra && prumerna >= spatna) {
            System.out.println("PRŮMĚRNÁ");
        }
        else {
            System.out.println("ŠPATNÁ");
        }
    }
    
    static void spustDovednost() {

        System.out.print("Zadej ID zaměstnance: ");
        int id = sc.nextInt();
        sc.nextLine();

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        // polymorfismus - zavolá správnou implementaci
        z.provedDovednost();
    }
    
    static void abecedniVypis() {

        List<Zamestnanec> analytici = new ArrayList<>();
        List<Zamestnanec> bezpecaci = new ArrayList<>();

        // rozdělení do skupin
        for (Zamestnanec z : zamestnanci) {

            if (z instanceof DatovyAnalytik) {
                analytici.add(z);
            }
            else if (z instanceof BezpecnostniSpecialista) {
                bezpecaci.add(z);
            }
        }

        // seřazení podle příjmení
        analytici.sort((a, b) -> a.getPrijmeni().compareTo(b.getPrijmeni()));
        bezpecaci.sort((a, b) -> a.getPrijmeni().compareTo(b.getPrijmeni()));

        // výpis analytiků
        System.out.println("\n--- Datoví analytici ---");
        for (Zamestnanec z : analytici) {
            System.out.println(z);
        }

        // výpis bezpečáků
        System.out.println("\n--- Bezpečnostní specialisté ---");
        for (Zamestnanec z : bezpecaci) {
            System.out.println(z);
        }
    }
}