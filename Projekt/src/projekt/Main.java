package projekt;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.io.File;

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

            int volba;

            while (true) {
                try {
                    volba = Integer.parseInt(sc.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.print("Zadej číslo: ");
                }
            }

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
                
                case 9:
                    statistiky();
                    break;
               
                case 10:
                    pocetVeSkupinach();
                    break;
               
                case 11:
                    ulozDoSouboru();
                    break;
                    
                case 12:
                    nactiZeSouboru();
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
        System.out.println("9 - Statistiky");
        System.out.println("10 - Počet zaměstnanců ve skupinách");
        System.out.println("11 - Uložit do souboru");
        System.out.println("12 - Načíst ze souboru");
        System.out.println("0 - Konec");
        System.out.print("Volba: ");
    }

    // přidání zaměstnance
    static void pridejZamestnance() {

        System.out.println("Vyber skupinu:");
        System.out.println("1 - Datový analytik");
        System.out.println("2 - Bezpečnostní specialista");

        int typ;
        while (true) {
            try {
                typ = Integer.parseInt(sc.nextLine());

                if (typ == 1 || typ == 2) {
                    break;
                } else {
                    System.out.println("Zadej 1 nebo 2:");
                }

            } catch (Exception e) {
                System.out.println("Zadej číslo 1 nebo 2:");
            }
        }

        // jméno
        String jmeno;
        while (true) {

            System.out.print("Zadej jméno: ");
            jmeno = sc.nextLine();

            // kontrola že neobsahuje čísla
            if (jmeno.matches("[a-zA-Zá-žÁ-Ž]+")) {
                break;
            } else {
                System.out.println("Jméno nesmí obsahovat čísla!");
            }
        }

        // příjmení
        String prijmeni;
        while (true) {

            System.out.print("Zadej příjmení: ");
            prijmeni = sc.nextLine();

            if (prijmeni.matches("[a-zA-Zá-žÁ-Ž]+")) {
                break;
            } else {
                System.out.println("Příjmení nesmí obsahovat čísla!");
            }
        }

        // rok narození
        int rok;
        while (true) {

            System.out.print("Zadej rok narození: ");

            try {
                rok = Integer.parseInt(sc.nextLine());

                if (rok > 1900 && rok < 2100) {
                    break;
                } else {
                    System.out.println("Neplatný rok!");
                }

            } catch (Exception e) {
                System.out.println("Rok musí být číslo!");
            }
        }

        Zamestnanec z;

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

        int id1 = nactiId("Zadej ID zaměstnance: ");
        int id2 = nactiId("Zadej ID kolegy: ");   

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

    	int id = nactiId("Zadej ID zaměstnance k odebrání: ");

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

    	int id = nactiId("Zadej ID zaměstnance: ");

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

    	int id = nactiId("Zadej ID zaměstnance: ");

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
    
    static void statistiky() {

        int spatna = 0;
        int prumerna = 0;
        int dobra = 0;

        Zamestnanec nejvice = null;
        int max = 0;

        for (Zamestnanec z : zamestnanci) {

            int pocet = z.getSpolupracovnici().size();

            // hledání zaměstnance s nejvíce vazbami
            if (pocet > max) {
                max = pocet;
                nejvice = z;
            }

            // počítání kvalit spolupráce
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
        }

        System.out.println("---- Statistiky ----");

        // převažující kvalita
        System.out.print("Převažující kvalita: ");

        if (dobra >= prumerna && dobra >= spatna) {
            System.out.println("DOBRÁ");
        }
        else if (prumerna >= dobra && prumerna >= spatna) {
            System.out.println("PRŮMĚRNÁ");
        }
        else {
            System.out.println("ŠPATNÁ");
        }

        // zaměstnanec s nejvíce vazbami
        if (nejvice != null) {
            System.out.println("Nejvíce vazeb má:");
            System.out.println(nejvice);
            System.out.println("Počet vazeb: " + max);
        }
    }
    
    static void pocetVeSkupinach() {

        int analytici = 0;
        int bezpecaci = 0;

        for (Zamestnanec z : zamestnanci) {

            if (z instanceof DatovyAnalytik) {
                analytici++;
            }
            else if (z instanceof BezpecnostniSpecialista) {
                bezpecaci++;
            }
        }

        System.out.println("---- Počet zaměstnanců ----");
        System.out.println("Datoví analytici: " + analytici);
        System.out.println("Bezpečnostní specialisté: " + bezpecaci);
    }
    
    static void ulozDoSouboru() {

        try {
            PrintWriter writer = new PrintWriter("zamestnanci.txt");

            for (Zamestnanec z : zamestnanci) {

                String typ;

                if (z instanceof DatovyAnalytik) {
                    typ = "A";
                } else {
                    typ = "B";
                }

                writer.println(
                        typ + ";" +
                        z.getId() + ";" +
                        z.getJmeno() + ";" +
                        z.getPrijmeni() + ";" +
                        z.getRokNarozeni()
                );
            }

            writer.close();

            System.out.println("Uloženo do souboru.");

        } catch (Exception e) {
            System.out.println("Chyba při ukládání.");
        }
    }
    
    static void nactiZeSouboru() {

        try {

            File file = new File("zamestnanci.txt");
            Scanner reader = new Scanner(file);

            zamestnanci.clear(); // vymaže aktuální seznam

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                String[] data = line.split(";");

                String typ = data[0];
                int id = Integer.parseInt(data[1]);
                String jmeno = data[2];
                String prijmeni = data[3];
                int rok = Integer.parseInt(data[4]);

                Zamestnanec z;

                if (typ.equals("A")) {
                    z = new DatovyAnalytik(id, jmeno, prijmeni, rok);
                } else {
                    z = new BezpecnostniSpecialista(id, jmeno, prijmeni, rok);
                }

                zamestnanci.add(z);

                // aktualizace nextId
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
            reader.close();

            System.out.println("Načteno ze souboru.");

        } catch (Exception e) {
            System.out.println("Chyba při načítání.");
        }
    }
    
    static int nactiId(String text) {

        int id;

        while (true) {
            try {
                System.out.print(text);
                id = Integer.parseInt(sc.nextLine());

                if (id >= 0) {
                    return id;
                } else {
                    System.out.println("ID musí být kladné číslo!");
                }

            } catch (Exception e) {
                System.out.println("Zadej číslo!");
            }
        }
    }
}