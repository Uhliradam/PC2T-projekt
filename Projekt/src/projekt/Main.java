package projekt;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Zamestnanec> zamestnanci = new ArrayList<>();

    static int nextId = 1;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        List<Zamestnanec> nactene = SqlDatabaze.nacti();

        if (!nactene.isEmpty()) {

            zamestnanci.addAll(nactene);

            for (Zamestnanec z : zamestnanci) {

                if (z.getId() >= nextId) {
                    nextId = z.getId() + 1;
                }
            }
        }

        boolean konec = false;

        while (!konec) {

            vypisMenu();

            int volba;

            while (true) {
                try {
                    volba = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
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

        SqlDatabaze.uloz(zamestnanci);

        System.out.println("Program ukončen.");
    }

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

    static void pridejZamestnance() {
    	System.out.println("\n--- Přidání zaměstnance ---");
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

            } catch (NumberFormatException e) {
                System.out.println("Zadej číslo 1 nebo 2:");
            }
        }

        String jmeno;
        while (true) {

            System.out.print("Zadej jméno: ");
            jmeno = sc.nextLine();

            if (jmeno.matches("[a-zA-Zá-žÁ-Ž]+")) {
                break;
            } else {
                System.out.println("Jméno nesmí obsahovat čísla!");
            }
        }

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

            } catch (NumberFormatException e) {
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

    static void vypisZamestnance() {
    	System.out.println("\n--- Výpis zaměstnanců ---");
        if (zamestnanci.isEmpty()) {
            System.out.println("Žádní zaměstnanci.");
            return;
        }

        for (Zamestnanec z : zamestnanci) {
            System.out.println(z);
        }
    }

    static void pridejSpolupraci() {
    	System.out.println("\n---- Přidání spolupráce ----");
    	
        int id1 = nactiId("Zadej ID zaměstnance: ");
        int id2 = nactiId("Zadej ID kolegy: ");   

        if (id1 == id2) {
            System.out.println("Nelze přidat spolupráci se sebou samým.");
            return;
        }
        
        System.out.println("Úroveň spolupráce:");
        System.out.println("1 - Špatná");
        System.out.println("2 - Průměrná");
        System.out.println("3 - Dobrá");

        int volba;
        while (true) {
            try {
                volba = Integer.parseInt(sc.nextLine());
                if (volba >= 1 && volba <= 3) {
                    break;
                } else {
                    System.out.print("Zadej 1, 2 nebo 3: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Zadej číslo (1-3): ");
            }
        }

        Zamestnanec z1 = najdiZamestnance(id1);
        Zamestnanec z2 = najdiZamestnance(id2);

        if (z1 == null || z2 == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        for (Spoluprace s : z1.getSpolupracovnici()) {
            if (s.getKolega().getId() == id2) {
                System.out.println("Spolupráce již existuje.");
                return;
            }
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

        z1.pridejSpolupraci(z2, uroven);
        z2.pridejSpolupraci(z1, uroven);

        System.out.println("Spolupráce přidána");
    }

    static Zamestnanec najdiZamestnance(int id) {

        for (Zamestnanec z : zamestnanci) {
            if (z.getId() == id) {
                return z;
            }
        }

        return null;
    }
    
    static void vypisSpolupracovniky() {
    	System.out.println("\n--- Výpis spolupracovníků ---");
        
    	int id = nactiId("Zadej ID zaměstnance: ");

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

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
    	System.out.println("\n---- Odebrání zaměstnance ----");
    	
    	int id = nactiId("Zadej ID zaměstnance k odebrání: ");

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        System.out.println("Nalezen:");
        System.out.println(z);

        System.out.print("Opravdu smazat? (ano/ne): ");
        String odp = sc.nextLine();

        if (!odp.equalsIgnoreCase("ano")) {
            System.out.println("Mazání zrušeno.");
            return;
        }
        
        for (Zamestnanec ostatni : zamestnanci) {

            ostatni.getSpolupracovnici().removeIf(
                    s -> s.getKolega().getId() == id
            );
        }

        zamestnanci.remove(z);

        System.out.println("Zaměstnanec odebrán.");
    }
    
    static void vyhledejZamestnance() {
    	System.out.println("\n---- Vyhledání zaměstnance podle ID ----");
    	
    	int id = nactiId("Zadej ID zaměstnance: ");

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

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
    	System.out.println("\n---- Dovednost zaměstnance ----");
    	
    	int id = nactiId("Zadej ID zaměstnance: ");

        Zamestnanec z = najdiZamestnance(id);

        if (z == null) {
            System.out.println("Zaměstnanec nenalezen");
            return;
        }

        z.provedDovednost();
    }
    
    static void abecedniVypis() {
    	System.out.println("\n---- Abecední výpis zaměstnanců ----");
    	
        List<Zamestnanec> analytici = new ArrayList<>();
        List<Zamestnanec> bezpecaci = new ArrayList<>();

        for (Zamestnanec z : zamestnanci) {

            if (z instanceof DatovyAnalytik) {
                analytici.add(z);
            }
            else if (z instanceof BezpecnostniSpecialista) {
                bezpecaci.add(z);
            }
        }

        analytici.sort((a, b) -> a.getPrijmeni().compareTo(b.getPrijmeni()));
        bezpecaci.sort((a, b) -> a.getPrijmeni().compareTo(b.getPrijmeni()));

        System.out.println("\n--- Datoví analytici ---");
        for (Zamestnanec z : analytici) {
            System.out.println(z);
        }

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

        List<String> zapoctene = new ArrayList<>();
        
        for (Zamestnanec z : zamestnanci) {

            int pocet = z.getSpolupracovnici().size();

            if (pocet > max) {
                max = pocet;
                nejvice = z;
            }

         for (Spoluprace s : z.getSpolupracovnici()) {
             int id1 = z.getId();
             int id2 = s.getKolega().getId();

             String klic = Math.min(id1, id2) + "-" + Math.max(id1, id2);

             if (zapoctene.contains(klic)) {
                  continue;
             }

                 zapoctene.add(klic);

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

        System.out.println("\n---- Statistiky ----");

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

        System.out.println("\n---- Počet zaměstnanců ve skupinách ----");
        System.out.println("Datoví analytici: " + analytici);
        System.out.println("Bezpečnostní specialisté: " + bezpecaci);
    }
    
    static void ulozDoSouboru() {

        try (PrintWriter writer = new PrintWriter("zamestnanci.txt")) {

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

            System.out.println("Uloženo do souboru.");

        } catch (FileNotFoundException e) {
            System.out.println("Chyba při ukládání: " + e.getMessage());
        }
    }
    
    static void nactiZeSouboru() {

        File file = new File("zamestnanci.txt");

        if (!file.exists()) {
            System.out.println("Soubor zamestnanci.txt neexistuje.");
            return;
        }

        try (Scanner reader = new Scanner(file)) {

            zamestnanci.clear(); 
            nextId = 1;

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                try {
                    String[] data = line.split(";");

                    if (data.length != 5) {
                        System.out.println("Vynechávám vadný řádek: " + line);
                        continue;
                    }

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

                    if (id >= nextId) {
                        nextId = id + 1;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Vynechávám řádek s chybným číslem: " + line);
                }
            }

            System.out.println("Načteno ze souboru.");

        } catch (FileNotFoundException e) {
            System.out.println("Chyba při načítání: " + e.getMessage());
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

            } catch (NumberFormatException e) {
                System.out.println("Zadej číslo!");
            }
        }
    }
}
